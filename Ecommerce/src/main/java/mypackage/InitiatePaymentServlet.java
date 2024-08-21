package mypackage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

@WebServlet("/initiatePayment")
public class InitiatePaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;
    
    public InitiatePaymentServlet() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "samantha");
    }

    private static final String PAYSTACK_SECRET_KEY = "sk_test_d62ed4eecdb2943d6fda0f1a2ef86b4b290fea0d";
    private static final String PAYSTACK_INITIALIZE_URL = "https://api.paystack.co/transaction/initialize";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userid = (Integer) session.getAttribute("userid");
        if (userid == null) {
            response.sendRedirect("signin.jsp");
            return;
        }

        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        double totalAmount = Double.parseDouble(request.getParameter("total")) * 100; // Convert to kobo

        CartDAO cartDAO = null;
        try {
            cartDAO = new CartDAO();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        List<CartItem> cartItems = cartDAO.getCartItems(userid);

        // Create payment initialization request payload
        Map<String, Object> paystackRequestData = new HashMap<>();
        paystackRequestData.put("email", email);
        paystackRequestData.put("amount", totalAmount);

        // Convert the payload to JSON string
        Gson gson = new Gson();
        String jsonPayload = gson.toJson(paystackRequestData);

        try {
            // Create and configure the HTTP connection
            URL url = new URL(PAYSTACK_INITIALIZE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + PAYSTACK_SECRET_KEY);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Write the JSON payload to the request body
            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonPayload.getBytes());
                os.flush();
            }

            // Read the response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Successful response
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder responseContent = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        responseContent.append(inputLine);
                    }

                    // Parse the JSON response
                    Map<String, Object> responseMap = gson.fromJson(responseContent.toString(), Map.class);
                    Map<String, String> dataMap = (Map<String, String>) responseMap.get("data");
                    String authorizationUrl = dataMap.get("authorization_url");

                    // Save the order details in the database
                    createOrder(userid, totalAmount, email, address, phone, cartItems);

                    // Redirect the user to the Paystack payment page
                    response.sendRedirect(authorizationUrl);
                }
            } else {
                // Error response
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    String inputLine;
                    StringBuilder responseContent = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        responseContent.append(inputLine);
                    }

                    // Log the error response for debugging
                    System.err.println("Paystack API error response: " + responseContent.toString());

                    // Set the error message as a request attribute and forward to an error page
                    request.setAttribute("errorMessage", "Payment initiation request failed: " + responseContent.toString());
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while initiating payment.");
        }
    }

    public int createOrder(int userid, double total, String email, String address, String phone, List<CartItem> cartItems) throws SQLException, ClassNotFoundException {
    	CartDAO cartDAO = new CartDAO();
        String orderSql = "INSERT INTO orders (userid, email, address, phone, total_amount) VALUES (?, ?, ?, ?, ?)";
        String orderItemsSql = "INSERT INTO order_items (order_id, product_id, name, price, quantity, size) VALUES (?, ?, ?, ?, ?, ?)";

        connection.setAutoCommit(false);

        try (PreparedStatement orderPs = connection.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement orderItemsPs = connection.prepareStatement(orderItemsSql)) {

            orderPs.setInt(1, userid);
            orderPs.setString(2, email);
            orderPs.setString(3, address);
            orderPs.setString(4, phone);
            orderPs.setDouble(5, total);
            orderPs.executeUpdate();

            ResultSet rs = orderPs.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            for (CartItem item : cartItems) {
                orderItemsPs.setInt(1, orderId);
                orderItemsPs.setInt(2, item.getProductId());
                orderItemsPs.setString(3, item.getName());
                orderItemsPs.setDouble(4, item.getPrice());
                orderItemsPs.setInt(5, item.getQuantity());
                orderItemsPs.setString(6, item.getSize());
                orderItemsPs.addBatch();
            }

            orderItemsPs.executeBatch();
            connection.commit();

            // Clear the cart after successful order creation
            cartDAO.clearCart(userid);

            return orderId;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}

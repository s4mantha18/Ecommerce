package mypackage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/VerifyPaymentServlet")
public class VerifyPaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userid = (Integer) session.getAttribute("userid");
        String reference = request.getParameter("paystackReference");

        if (userid == null || reference == null) {
            response.sendRedirect("signin.jsp");
            return;
        }

        try {
            // Verify the payment with Paystack
            URL url = new URL("https://api.paystack.co/transaction/verify/" + reference);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "sk_test_d62ed4eecdb2943d6fda0f1a2ef86b4b290fea0d");

            int statusCode = connection.getResponseCode();
            if (statusCode == 200) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                JsonObject jsonResponse = JsonParser.parseReader(reader).getAsJsonObject();
                reader.close();

                boolean success = jsonResponse.get("status").getAsBoolean();

                if (success) {
                    // Payment verified successfully
                    // Save the order to the database
                    CartDAO cartDAO = new CartDAO();
                    List<CartItem> cartItems = cartDAO.getCartItems(userid);
                    double Total = cartDAO.getTotal(userid);
               
                   
                    // Extracting additional user information
                    String email = request.getParameter("email");
                    String address = request.getParameter("address");
                    String phone = request.getParameter("phone");


                    OrderDAO orderDAO = new OrderDAO();
                    orderDAO.createOrder(userid, Total, email, address, phone, cartItems);
                    
                    // Clear the cart
                    cartDAO.clearCart(userid);

                    session.setAttribute("notification", "Order placed successfully!");
                    response.sendRedirect("Userpage.jsp");
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Payment verification failed.");
                }
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Payment verification request failed.");
            }
        } catch (Exception e) {
            throw new ServletException("Error verifying payment", e);
        }
    }
}

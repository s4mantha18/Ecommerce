package mypackage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.google.gson.Gson;

@WebServlet("/paystackCallback")
public class PaystackCallbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Connection connection;

    public PaystackCallbackServlet() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "samantha");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder jsonPayload = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonPayload.append(line);
        }

        Gson gson = new Gson();
        Map<String, Object> payload = gson.fromJson(jsonPayload.toString(), Map.class);
        Map<String, Object> eventData = (Map<String, Object>) payload.get("data");

        String reference = (String) eventData.get("reference");
        String status = (String) eventData.get("status");
        Map<String, Object> metadata = (Map<String, Object>) eventData.get("metadata");
        int orderId = Integer.parseInt((String) metadata.get("order_id"));

        if ("success".equals(status)) {
            updateOrderStatus(orderId, "COMPLETED");
        } else {
            updateOrderStatus(orderId, "FAILED");
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

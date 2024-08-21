package mypackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    private Connection connection;

    public CartDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "samantha");
    }

    public List<CartItem> getCartItems(int userid) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT * FROM cart_items WHERE userid = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem(
                    rs.getInt("productId"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getString("size")
                );
                cartItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartItems;
    }

    public void addToCart(int userid, int productId, int quantity, String size) throws SQLException {
        String productSql = "SELECT name, price FROM products WHERE id = ?";
        String cartSql = "INSERT INTO cart_items (userid, productId, name, price, quantity, size) VALUES (?, ?, ?, ?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE quantity = quantity + ?, size = ?";

        try (PreparedStatement productPs = connection.prepareStatement(productSql);
             PreparedStatement cartPs = connection.prepareStatement(cartSql)) {

            productPs.setInt(1, productId);
            ResultSet productRs = productPs.executeQuery();

            if (productRs.next()) {
                String name = productRs.getString("name");
                double price = productRs.getDouble("price");

                cartPs.setInt(1, userid);
                cartPs.setInt(2, productId);
                cartPs.setString(3, name);
                cartPs.setDouble(4, price);
                cartPs.setInt(5, quantity);
                cartPs.setString(6, size);
                cartPs.setInt(7, quantity);
                cartPs.setString(8, size);
                cartPs.executeUpdate();
            }
        }
    }

    public void removeFromCart(int userid, int productId) throws SQLException {
        String sql = "DELETE FROM cart_items WHERE userid = ? AND productId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userid);
            ps.setInt(2, productId);
            ps.executeUpdate();
        }
    }

    public double getTotal(int userid) {
        double total = 0.0;
        String sql = "SELECT SUM(price * quantity) AS total FROM cart_items WHERE userid = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }
    
    public void clearCart(int userId) throws SQLException {
        String sql = "DELETE FROM cart_items WHERE userId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

}

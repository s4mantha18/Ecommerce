package mypackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private Connection connection;

    public OrderDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "samantha");
    }

    public int createOrder(int userid, double total, String email, String address, String phone, List<CartItem> cartItems) throws SQLException {
        String orderSql = "INSERT INTO orders (userid, email, address, phone, total_amount) VALUES (?, ?, ?, ?, ?)";
        String orderItemsSql = "INSERT INTO order_items (order_id, product_id, name, price, quantity, size, userid) VALUES (?, ?, ?, ?, ?, ?, ?)";

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

          
            System.out.println("Order ID: " + orderId);
            System.out.println("User ID: " + userid);

            for (CartItem item : cartItems) {
                orderItemsPs.setInt(1, orderId);
                orderItemsPs.setInt(2, item.getProductId());
                orderItemsPs.setString(3, item.getName());
                orderItemsPs.setDouble(4, item.getPrice());
                orderItemsPs.setInt(5, item.getQuantity());
                orderItemsPs.setString(6, item.getSize());
                orderItemsPs.setInt(7, userid);

              
                System.out.println("Inserting item: " + item.getName() + " for user ID: " + userid);

                orderItemsPs.addBatch();
            }

            int[] batchResults = orderItemsPs.executeBatch();
            for (int result : batchResults) {
                if (result == PreparedStatement.EXECUTE_FAILED) {
                    throw new SQLException("Failed to insert order item");
                }
            }

            connection.commit();
            System.out.println("Order created with ID: " + orderId + " and items inserted successfully.");

            return orderId;
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }


    
    public List<Order> selectAllOrders() {
        List<Order> orders = new ArrayList<>();
        try (
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM orders;")) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
            	int id = rs.getInt("userid");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                Double total = rs.getDouble("total_amount");
                String createdat = rs.getString("created_at");
                String status = rs.getString("status");
                Order order = new Order(id, email, address, phone, total, createdat, status);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    public List<OrderItems> selectAllOrderItems() {
        List<OrderItems> orderitems = new ArrayList<>();
        try (
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM order_items;")) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
            	int orderid = rs.getInt("order_id");
                int productid = rs.getInt("product_id");
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String size = rs.getString("size");
                int userid = rs.getInt("userid");
                OrderItems orderitem = new OrderItems(orderid, productid, name, price, quantity, size, userid);
                orderitems.add(orderitem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderitems;
    }
}

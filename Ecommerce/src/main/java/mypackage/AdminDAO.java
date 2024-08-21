package mypackage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    	private Connection connection;
        public AdminDAO() throws ClassNotFoundException, SQLException{
      		 Class.forName("com.mysql.cj.jdbc.Driver");
      		 connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "samantha");
               
      	 }

    public void insertProduct(Admin admin) throws SQLException {
        try (
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO products (name, price, image, category) VALUES (?, ?, ?, ?);")) {
            preparedStatement.setString(1, admin.getName());
            preparedStatement.setDouble(2, admin.getPrice());
            preparedStatement.setBytes(3, admin.getImagepath());
            preparedStatement.setString(4, admin.getCategory());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int id) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = connection.prepareStatement("delete from products where id = ?");
        statement.setInt(1,id);
        statement.execute();
    }
    
    public void update(Admin admin, int id) throws ClassNotFoundException, SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE products SET name = ?, price = ?, image = ? WHERE id = ?");
        statement.setString(1, admin.getName());
        statement.setDouble(2, admin.getPrice());
        statement.setBytes(3, admin.getImagepath());
        statement.setInt(4, id);
        statement.executeUpdate();
    }


    
    public List<Admin> selectAllProducts() {
        List<Admin> products = new ArrayList<>();
        try (
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM products;")) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
            	int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                byte[] imagePath = rs.getBytes("image");
                String category = rs.getString("category");
                Admin product = new Admin(id, name, price, imagePath, category);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    
    public List<Admin> selectProductsByCategory(String category) {
        List<Admin> products = new ArrayList<>();
        try (
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM products WHERE category = ?")) {
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                byte[] imagePath = rs.getBytes("image");
                String prodcategory = rs.getString("category");
                Admin product = new Admin(id, name, price, imagePath, prodcategory);
                products.add(product);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return products;
    }
    
    public List<Admin> searchProducts(String query) {
        List<Admin> products = new ArrayList<>();
        try (
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM products WHERE name LIKE ?")) {
            ps.setString(1, "%" + query + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
            	int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                byte[] imagePath = rs.getBytes("image");
                String prodcategory = rs.getString("category");
                Admin product = new Admin(id, name, price, imagePath, prodcategory);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return products;
    }

    
    public List<Admin> selectAllProductsbyid(int id) {
        List<Admin> productbyid = new ArrayList<>();
        try (
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM products where id = ?;")) {
        	preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
            	int userid = id;
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                byte[] imagePath = rs.getBytes("image");
                String category = rs.getString("category");
                Admin product = new Admin(userid, name, price, imagePath, category);
                productbyid.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productbyid;
    }

}



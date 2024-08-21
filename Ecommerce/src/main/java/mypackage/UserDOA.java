package mypackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDOA {

     private Connection connection;
   	 public UserDOA() throws ClassNotFoundException, SQLException{
   		 Class.forName("com.mysql.cj.jdbc.Driver");
   		 connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "samantha");
            
   	 }
   	    public void saveUSER(User user) throws SQLException {
   	        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users(username, password) VALUES (?, ?)")) {
   	            statement.setString(1, user.getUsername());
   	            statement.setString(2, user.getPassword());
   	            statement.execute();
   	        }
   	    }
   	    
   
   	 
   	public List<User> read() throws SQLException, ClassNotFoundException {
   		List<User> user = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from users");
        statement.execute();
        ResultSet result = statement.getResultSet();
        while (result.next()) {
            String username = result.getString("username");
            String password = result.getString("password");
            
            User user1 = new User(username, password);
           user.add(user1);
            

        }
        return user;
    }
   	
   
    public int getUserId(String username) throws SQLException, ClassNotFoundException {
    	int userid = -1;
    	User user = null;
    	PreparedStatement statement = connection.prepareStatement("select id from users where username = ?");
         statement.setString(1, username);
         statement.execute();
         ResultSet result = statement.getResultSet();
         
		if (result.next()) {
         userid = result.getInt("id");


                }
        
		return userid;
    }
 

   	public static void main(String[] args) throws SQLException, ClassNotFoundException {
   		
   		UserDOA user = new UserDOA();
   		
   		List<User> user1 = user.read();
   		for (User users : user1) {
   			System.out.println(users);
   	   		
   		}
   		
   	}
   	 
   	}

  


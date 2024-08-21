package mypackage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



@WebServlet("/Signup")
public class Signup  extends HttpServlet{
	private static final long serialVersionUID = 1L;
    private UserDOA doa;
    private Connection connection;
  	 public Signup() throws ClassNotFoundException, SQLException{
  		 Class.forName("com.mysql.cj.jdbc.Driver");
  		 connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "samantha");
           
  	 }
    @Override
    public void init() {
        try {
			doa = new UserDOA();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Override
	public void doPost(	HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if (username != null && password != null ) {
			try {
				User user= new User(username, password);
				int userid = doa.getUserId(username);
				PreparedStatement statement = connection.prepareStatement("SELECT id, password FROM users WHERE username = ?");{
		        statement.setString(1, username);
		        ResultSet resultSet = statement.executeQuery();
		                if (resultSet.next()) {
		                    int id = resultSet.getInt("id");
		                    if (userid == id) {
		                    	response.sendRedirect("Sucess.jsp");
		    				}
				
		                } else{
	    					doa.saveUSER(user);
				HttpSession session = request.getSession();
				session.setAttribute("userid", userid);
				session.setAttribute("username", username);
				response.sendRedirect("signin.jsp");
			}
				}}
				
				
				
	
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		

} else {
	
}

}
}
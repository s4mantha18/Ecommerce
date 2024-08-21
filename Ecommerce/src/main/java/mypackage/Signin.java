package mypackage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/Signin")
public class Signin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDOA dao;
    private Connection connection;
    private static final Logger LOGGER = Logger.getLogger(Signin.class.getName());

    @Override
    public void init() throws ServletException {
        try {
            dao = new UserDOA();
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "samantha");
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Failed to initialize Signin servlet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        LOGGER.log(Level.INFO, "Attempting to login user: {0}", username);

        if (username == null || password == null) {
            LOGGER.log(Level.WARNING, "Username or password missing");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Username and password are required.");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT id, password FROM users WHERE username = ?")) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int userid = resultSet.getInt("id");
                    String storedPassword = resultSet.getString("password");

                    if (username.equals("admin") && password.equals(storedPassword)) {
                        LOGGER.log(Level.INFO, "Admin login successful");
                        response.sendRedirect("Adminpage.jsp");
                    } else if (password.equals(storedPassword)) {
                        HttpSession session = request.getSession();
                        session.setAttribute("userid", userid);
                        session.setAttribute("username", username);
                        LOGGER.log(Level.INFO, "User login successful for username: {0}", username);
                        response.sendRedirect("Userpage.jsp");
                    } else {
                        LOGGER.log(Level.WARNING, "Invalid password for username: {0}", username);
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid username or password.");
                    }
                } else {
                    LOGGER.log(Level.WARNING, "Invalid username: {0}", username);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid username or password.");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error during signin", e);
            throw new ServletException("Database error during signin", e);
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

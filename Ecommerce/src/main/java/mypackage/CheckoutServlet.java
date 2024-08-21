package mypackage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CartDAO cartDAO;

    @Override
    public void init() throws ServletException {
        try {
            cartDAO = new CartDAO();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Failed to initialize CheckoutServlet", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userid = (Integer) session.getAttribute("userid");
        if (userid == null) {
            response.sendRedirect("signin.jsp");
            return;
        }

        List<CartItem> cartItems = cartDAO.getCartItems(userid);
        double total = cartDAO.getTotal(userid);
        // Extracting additional user information
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");

        try {
            OrderDAO orderDAO = new OrderDAO();
            int orderId = orderDAO.createOrder(userid, total, email, address, phone, cartItems);
            cartDAO.clearCart(userid); // Clear the cart after checkout
            response.sendRedirect("orderConfirmation.jsp?orderId=" + orderId);
        } catch (SQLException e) {
            throw new ServletException("Database error during checkout", e);
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

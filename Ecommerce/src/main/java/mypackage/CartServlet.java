package mypackage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/addToCart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userid = (Integer) session.getAttribute("userid");
        if (userid == null) {
            response.sendRedirect("signin.jsp"); // Redirect to login if user is not logged in
            return;
        }

        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String size = request.getParameter("size");

        try {
            CartDAO cartDAO = new CartDAO();
            cartDAO.addToCart(userid, productId, quantity, size);
            session.setAttribute("notification", "Item added to cart successfully!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            session.setAttribute("notification", "Failed to add item to cart.");
        }

        response.sendRedirect("ProductDetails.jsp");
    }
}

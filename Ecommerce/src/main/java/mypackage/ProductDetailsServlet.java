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

@WebServlet("/ProductDetailsServlet")
public class ProductDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        AdminDAO dao;
		try {
			dao = new AdminDAO();
			 List<Admin> products = dao.selectAllProductsbyid(productId);
			 Admin product = null;
		        for (Admin p : products) {
		            if (p.getId() == productId) {
		                product = p;
		                break;
		            }
		        }

		        if (product != null) {
		            HttpSession session = request.getSession();
		            session.setAttribute("product", product);
		            response.sendRedirect("ProductDetails.jsp");
		        } else {
		            // Handle case where product is not found
		            response.sendRedirect("errorPage.jsp"); // Or some appropriate error handling
		        }
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       

        
    }
}

package mypackage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;



@WebServlet("/Delete")
public class Delete  extends HttpServlet{
	private static final long serialVersionUID = 1L;
    private AdminDAO dao;
    
    @Override
    public void init() {
        try {
			dao = new AdminDAO();
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
		String id = request.getParameter("id");		
		if (id != null && !id.isEmpty()) {
			int userid = Integer.parseInt(id);
			try {
				dao.delete(userid);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println("null");
		}
		
		response.sendRedirect("Adminpage.jsp");

}

}

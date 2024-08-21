package mypackage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import mypackage.AdminDAO;

@WebServlet("/uploadServlet")
@MultipartConfig(maxFileSize = 16177215) // 16MB
public class Upload extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminDAO adminDAO;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("image");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String category = request.getParameter("category");

        byte[] imageBytes = null;
        if (filePart != null) {
            try (InputStream inputStream = filePart.getInputStream();
                 ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
                int nRead;
                byte[] data = new byte[16384];
                while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                imageBytes = buffer.toByteArray();
            }
        }

        String message = null;
        try {
            
			try {
				adminDAO = new AdminDAO();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            Admin admin = new Admin(0, name, Double.parseDouble(price), imageBytes, category);
            adminDAO.insertProduct(admin);
            message = "File uploaded and saved into database";
        } catch (SQLException ex) {
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        }

        request.setAttribute("Message", message);
        getServletContext().getRequestDispatcher("/Adminpage.jsp").forward(request, response);
    }
}

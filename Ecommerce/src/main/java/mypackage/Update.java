package mypackage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@WebServlet("/Update")
@MultipartConfig
public class Update extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminDAO dao;

    @Override
    public void init() {
        try {
            dao = new AdminDAO();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        Part filePart = request.getPart("image");
        String category = request.getParameter("category");

        if (id != null && name != null && price != null && filePart != null && category != null) {
            try {
                int userid = Integer.parseInt(id);
                double userprice = Double.parseDouble(price);

                // Convert the uploaded file to byte array
                InputStream fileContent = filePart.getInputStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[16384];
                while ((nRead = fileContent.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
                byte[] userimage = buffer.toByteArray();

                Admin admin = new Admin(userid, name, userprice, userimage, category);
                dao.update(admin, userid);
                response.sendRedirect("Adminpage.jsp");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while updating the product.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
        }
    }
}

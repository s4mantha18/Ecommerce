<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="mypackage.AdminDAO" %>
<%@ page import="mypackage.Admin" %>
<!DOCTYPE html>
<html>
<head>
    <title>Update Product</title>
    <link rel="stylesheet" href="upload.css">
</head>
<body>
    <main>
        <%
            String id = request.getParameter("id");
            if (id != null) {
                int userid = Integer.parseInt(id);
                AdminDAO dao = new AdminDAO();
                List<Admin> admins = dao.selectAllProductsbyid(userid);
                if (admins != null && !admins.isEmpty()) {
                    Admin admin = admins.get(0); // Assuming one product per id
        %>
        <div class="Update">
            <form action="Update" method="post" enctype="multipart/form-data" class="upload-form">
                <div class="img-container">
                    <label for="file-upload" class="choose">Choose an Image</label>
                    <input type="file" id="file-upload" name="image" accept="image/*" />
                    <div class="image-preview">
                        <img id="img-preview" src="#" alt="Image Preview" style="display: none;"/>
                    </div>
                </div>
                <div class="info">
                    <input type="hidden" name="id" value="<%= userid %>">
                    <label for="name" class="label">Name:</label>
                    <input type="text" name="name" value="<%= admin.getName() %>" required>
                    <label for="price" class="label">Price:</label>
                    <input type="text" name="price" value="<%= admin.getPrice() %>" required>
                    <label for="category" class="label">Category:</label>
                    <input type="text" name="category" value="<%= admin.getCategory() %>" required>
                    <input type="submit" value="Update" />
                </div>
            </form>
        </div>
        <%
                } else {
        %>
        <div class="error">
            Product not found. Please try again.
        </div>
        <%
                }
            } else {
        %>
        <div class="error">
            User ID is not available. Please try again.
        </div>
        <%
            }
        %>
    </main>
    <script src="upload.js"></script>
</body>
</html>

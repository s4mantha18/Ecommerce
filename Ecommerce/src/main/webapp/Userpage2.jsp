<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="mypackage.AdminDAO" %>
<%@ page import="mypackage.Admin" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <%
            Integer userId = (Integer) session.getAttribute("userid");
            String username = (String) session.getAttribute("username");
            if (userId != null) {
                out.print("Welcome, " + username);
            } else {
                response.sendRedirect("signin.jsp");
            }
        %>
    </title>
    <link rel="stylesheet" href="userpage.css">
</head>
<body>
    <main>
        <div class="top">
            <div class="logoimg"></div>
            <div class="cart">
                <a href="Cart.jsp" ></a>
            </div>
            <div class="navbarcontainer">
                <div class="navbar">
                    <div class="nav">
                    <a href="Userpage.jsp">HOME</a>
                </div>
                    <div class="nav">
                    <a href="contact.jsp"  id="nav">CONTACT US</a>
                </div>
                    <div class="nav">
                        <form class="search-container" action="Userpage2.jsp" method="get">
                            <input type="text" name="query" placeholder="Search...">
                            <button type="submit">Search</button>
                        </form>
                    </div>
                </div>
                <div class="little-line"></div>
            </div>
            <div class="userimg">
                <a href="signin.jsp"></a>
            </div>
        </div>
        <div class="main">
        <div class="category-container">
        <div class="category">
            <form action="Userpage2.jsp" method="get" class="filter-container">
                <select name="category" onchange="this.form.submit()">
                    <option value="">SELECT CATEGORY</option>
                    <option value="basketball">Basketball</option>
                    <option value="hockey">Hockey</option>
                    <option value="baseball">Baseball</option>
                    <option value="soccer">Soccer</option>
                </select>
            </form>
        </div>
    </div>
        <div class="products-container">
            
                <%
                    AdminDAO dao = new AdminDAO();
                    String category = request.getParameter("category");
                    String query = request.getParameter("query");
                    List<Admin> admins;

                    if (query != null && !query.isEmpty()) {
                        admins = dao.searchProducts(query);
                    } else if (category != null && !category.isEmpty()) {
                        admins = dao.selectProductsByCategory(category);
                    } else {
                        admins = dao.selectAllProducts();
                    }

                    if (admins.isEmpty()) {
                %>
                <div class="no-results">
                    No results found.
                </div>
                <%
                    } else {
                        for (Admin admin : admins) {
                %>
                <div class="product-card">
                    <div class="image" style="background-image: url('data:image/jpeg;base64,<%= java.util.Base64.getEncoder().encodeToString(admin.getImagepath()) %>'); background-size: cover;"></div>
                    <div class="info">
                        <div class="item"><%= admin.getName() %></div>
                        <div class="price">#<%= admin.getPrice() %></div>
                    </div>
                    <form action="ProductDetailsServlet" method="post">
                        <input type="hidden" name="productId" value="<%= admin.getId() %>">
                        <button type="submit" class="buy">BUY NOW!</button>
                    </form>
                </div>
                <%
                        }
                    }
                %>
           
        </div>
        
    </main>
    <script src="script.js"></script>
</body>
</html>

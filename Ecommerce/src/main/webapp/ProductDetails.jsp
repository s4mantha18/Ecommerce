<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="mypackage.AdminDAO" %>
<%@ page import="mypackage.Admin" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Details</title>
    <link rel="stylesheet" href="details.css">
    <script>
        function showNotification(message) {
            var notification = document.getElementById('notification');
            notification.innerText = message;
            notification.style.display = 'block';
            setTimeout(function() {
                notification.style.display = 'none';
            }, 3000);
        }
    </script>
    <style>
        #notification {
            display: none;
            position: fixed;
            bottom: 20px;
            right: 20px;
            background-color: #4CAF50;
            color: white;
            padding: 15px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.2);
        }
    </style>
</head>
<body>
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
    <div id="notification"></div>
    <main>
        <%
            Admin product = (Admin) session.getAttribute("product");
        %>
        <div class="productcontainer">
            <div class="productimage" style="background-image: url('data:image/jpeg;base64,<%= java.util.Base64.getEncoder().encodeToString(product.getImagepath()) %>'); background-size: cover;"></div>
            <div class="details">
                <div class="productname"><%= product.getName() %></div>
                <div class="productprice"><%= product.getPrice() %></div>
                <div class="size">
                    <label for="size">Select Size:</label>
                    <select id="size" name="size" form="addToCartForm">
                        <option value="Small">S</option>
                        <option value="Medium">M</option>
                        <option value="Large">L</option>
                        <option value="Extra-Large">XL</option>
                    </select>
                </div>
                <div class="addtocart">
                    <form action="addToCart" method="post" class="cartcontainer" id="addToCartForm">
                        <input type="hidden" name="productId" value="<%= product.getId() %>">
                        <input type="hidden" name="name" value="<%= product.getName() %>">
                        <input type="hidden" name="price" value="<%= product.getPrice() %>">
                        <input type="number" name="quantity" value="1" min="1" class="number">
                        <button type="submit" class="cartbutton">Add to Cart</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="morelikethis">
            <div class="more">More Like This</div>
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
                    
                        for (Admin admin : admins) {
                %>
                <div class="product-card">
                    <div class="image" style="background-image: url('data:image/jpeg;base64,<%= java.util.Base64.getEncoder().encodeToString(admin.getImagepath()) %>'); background-size: cover;"></div>
                    <div class="info">
                        <div class="item"><%= admin.getName() %></div>
                        <div class="price"><%= admin.getPrice() %></div>
                    </div>
                    <form action="ProductDetailsServlet" method="post">
                        <input type="hidden" name="productId" value="<%= admin.getId() %>">
                        <button type="submit" class="buy">BUY NOW!</button>
                    </form>
                </div>
                <%
                        }
                    
                %>
            </div>
            </div>
        </div>
    </main>
    <% 
        String notification = (String) session.getAttribute("notification");
        if (notification != null) {
    %>
    <script>
        showNotification("<%= notification %>");
    </script>
    <% 
        session.removeAttribute("notification");
        }
    %>
    <footer>
        <div class="footer-container">
            <div class="footer-section about">
                <h2>About Us</h2>
                <p>We offer a wide range of fashion jerseys for all your favorite sports. Our products are made from high-quality materials to ensure durability and comfort.</p>
            </div>
            <div class="footer-section links">
                <h2>Quick Links</h2>
                <ul>
                    <li><a href="Userpage.jsp">Home</a></li>
                    <li><a href="contact.jsp">Contact Us</a></li>
                </ul>
            </div>
            <div class="footer-section social">
                <h2>Follow Us</h2>
                <a href="#"><img src="icons8-facebook-48.png" alt="Facebook"></a>
                <a href="#"><img src="icons8-twitter-48.png" alt="Twitter"></a>
                <a href="#"><img src="icons8-instagram-48.png" alt="Instagram"></a>
            </div>
        </div>
        <div class="footer-bottom">
            &copy; 2024 Sam's Fashion Jersey | Designed by Samantha
        </div>
    </footer>
</body>
</html>

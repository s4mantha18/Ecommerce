<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="mypackage.CartItem" %>
<%@ page import="mypackage.CartDAO" %>
<%@ page import="java.util.List" %>
<%
    Integer userid = (Integer) session.getAttribute("userid");
    if (userid == null) {
        response.sendRedirect("signin.jsp"); // Redirect to login if user is not logged in
        return;
    }

    CartDAO cartDAO = new CartDAO();
    List<CartItem> cartItems = cartDAO.getCartItems(userid);
    double total = cartDAO.getTotal(userid);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cart</title>
    <link rel="stylesheet" href="cart.css">
</head>
<body>
    <main>
        <h1>Your Cart</h1>
        <div class="cart-items">
            <%
                if (cartItems.isEmpty()) {
            %>
                <p>No items in cart.</p>
            <%
                } else {
                    for (CartItem item : cartItems) {
            %>
            <div class="cart-item">
                <div class="item-name"><%= item.getName() %></div>
                <div class="item-price"><%= item.getPrice() %></div>
                <div class="item-quantity"><%= item.getQuantity() %></div>
                <div class="item-size"><%= item.getSize() %></div>
                <div class="item-total"><%= item.getTotalPrice() %></div>
                <form action="removeFromCart" method="post">
                    <input type="hidden" name="productId" value="<%= item.getProductId() %>">
                    <button type="submit">Remove</button>
                </form>
            </div>
            <%
                    }
                }
            %>
        </div>
        <div class="cart-total">
            Total: <%= total %>
        </div>
        <a href="checkout.jsp">Proceed to Checkout</a>
    </main>
</body>
</html>

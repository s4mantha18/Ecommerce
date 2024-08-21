<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="mypackage.CartDAO" %>
<%@ page import="mypackage.CartItem" %>
<%@ page import="java.util.List" %>
<%
    Integer userid = (Integer) session.getAttribute("userid");
    if (userid == null) {
        response.sendRedirect("signin.jsp");
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
    <title>Checkout</title>
    <link rel="stylesheet" href="checkout.css">
</head>
<body>
    <main>
        <h1>Checkout</h1>
        <div class="cart-items">
            <%
                for (CartItem item : cartItems) {
            %>
            <div class="cart-item">
                <div class="item-name"><%= item.getName() %></div>
                <div class="item-price"><%= item.getPrice() %></div>
                <div class="item-quantity"><%= item.getQuantity() %></div>
                <div class="item-size"><%= item.getSize() %></div>
                <div class="item-total"><%= item.getTotalPrice() %></div>
            </div>
            <%
                }
            %>
        </div>
        <div class="cart-total">
            Total: <%= total %>
        </div>
        <form action="initiatePayment" method="post" class="checkout-form">
            <input type="email" name="email" placeholder="Email" required>
            <input type="hidden" name="total" value="<%= total %>">
            <input type="text" name="address" placeholder="Address" required>
            <input type="text" name="phone" placeholder="Phone" required>
            <button type="submit">Confirm Order</button>
        </form>
    </main>
</body>
</html>

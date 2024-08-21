<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
    Integer orderId = (Integer) session.getAttribute("orderId");
    if (orderId == null) {
        response.sendRedirect("Userpage.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmation</title>
    <link rel="stylesheet" href="orderConfirmation.css">
</head>
<body>
    <main>
        <h1>Order Confirmation</h1>
        <p>Thank you for your order! Your order ID is <%= orderId %>.</p>
        <a href="Userpage.jsp">Continue Shopping</a>
    </main>
</body>
</html>

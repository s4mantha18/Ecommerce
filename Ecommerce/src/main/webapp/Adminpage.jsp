<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="mypackage.AdminDAO" %>
<%@ page import="mypackage.Admin" %>
<%@ page import="mypackage.OrderDAO" %>
<%@ page import="mypackage.Order" %>
<%@ page import="mypackage.OrderItems"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Page</title>
    <link rel="stylesheet" href="adminpage.css">
</head>
<body>
    <div class="heading">
        <h1>Products</h1>
        <form action="UploadProduct.jsp" method="post" style="display:inline">
            <input type="submit" value="" style="background-image: url(icons8-add-32.png); background-repeat: no-repeat; background-position: center;">
        </form>
    </div>
    <table>
        <thead>
            <tr>
                <th>Product name</th>
                <th class="price">Price</th>
                <th class="actions">Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                AdminDAO dao = new AdminDAO();
                List<Admin> admins = dao.selectAllProducts();
                if (admins.isEmpty()) {
            %>
            <tr>
                <td colspan="3">No products available.</td>
            </tr>
            <%
                } else {
                    for (Admin admin : admins) {
            %>
            <tr>
                <td>
                    <div class="container">
                        <div class="image" style="background-image: url('data:image/jpeg;base64,<%= java.util.Base64.getEncoder().encodeToString(admin.getImagepath()) %>'); background-size: cover;"></div>
                        <div class="name"><%= admin.getName() %></div>
                    </div>
                </td>
                <td class="price">#<%=admin.getPrice() %></td>
                <td class="actions">
                    <div class="buttons">
                        <form action="Delete" method="post" style="display:inline">
                            <input type="hidden" name="id" value="<%= admin.getId() %>">
                            <input type="submit" value="" style="background-image: url(icons8_delete_sign_50px.png); background-repeat: no-repeat; background-position: center;">
                        </form>
                        <form action="Update.jsp" method="post" style="display:inline">
                            <input type="hidden" name="id" value="<%= admin.getId() %>">
                            <input type="submit" value="" style="background-image: url(icons8-edit-32.png); background-repeat: no-repeat; background-position: center;">
                        </form>
                    </div>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>
    <div class="heading">
        <h1>Orders</h1>
    </div>
     <table>
        <thead>
            <tr>
                <th class="price">User id</th>
                <th>Email</th>
                <th>Address</th>
                <th>Phone</th>
                <th>Total Amount</th>
                <th>Created At</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            <%
                OrderDAO orderdao = new OrderDAO();
                List<Order> orders = orderdao.selectAllOrders();
                if (orders.isEmpty()) {
            %>
            <tr>
                <td colspan="3">No orders available.</td>
            </tr>
            <%
                } else {
                    for (Order order : orders) {
            %>
            <tr>
                <td><%= order.getUserid() %></td>
                <td><%= order.getEmail() %></td>
                <td><%= order.getAddress() %></td>
                <td><%= order.getPhone()%></td>
                <td><%= order.getTotalamount() %></td>
                <td><%= order.getCreatedat()%></td>
                <td><%= order.getStatus()%></td>
            </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>
    <div class="heading">
        <h1>Order Items</h1>
    </div>
     <table>
        <thead>
            <tr>
                <th class="price">Order id</th>
                <th class="price">Product id</th>
                <th>name</th>
                <th class="price">price</th>
                <th class="price">quantity</th>
                <th class="price">size</th>
                <th class="price">User id</th>
            </tr>
        </thead>
        <tbody>
            <%
                OrderDAO orderitemdao = new OrderDAO();
                List<OrderItems> orderitems = orderitemdao.selectAllOrderItems();
                if (admins.isEmpty()) {
            %>
            <tr>
                <td colspan="3">No Orders available.</td>
            </tr>
            <%
                } else {
                    for (OrderItems orderitem : orderitems) {
            %>
            <tr>
                <td><%= orderitem.getOrderid()%></td>
                <td><%= orderitem.getProductid() %></td>
                <td><%= orderitem.getName()%></td>
                <td><%= orderitem.getPrice() %></td>
                <td><%= orderitem.getQuantity() %></td>
                <td><%= orderitem.getSize() %></td>
                <td><%= orderitem.getUserid() %></td>
            </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>
</body>
</html>

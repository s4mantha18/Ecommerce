<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign in!</title>
    <link rel="stylesheet" href="signup.css">
</head>
<body>
    <div class="mainbox">
        <div class="image"></div>
        <div class="signupbox">
            <h1>
                Welcome!
            </h1>
            <form action="Signin" method="post" id="SignUpForm">
                <input type="text" id="username" name="username" placeholder="Username" required>
                <input type="password" id="password" name="password" placeholder="Password" required>
                <button type="submit">Sign In</button>
            </form>
            <p id="Sign Up Message"></p>
            <p>Don't have an account? <a href="signup.jsp">Sign Up</a></p>
        </div>
    </div>
</body>
</html>
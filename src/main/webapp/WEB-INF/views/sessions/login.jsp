<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<div style="max-width: 500px; margin: 0 auto">
    <h1>Login</h1>
    <h4 style="color: red">${errorMsg}</h4>
    <form method="post" action="${pageContext.request.contextPath}/login">
        Login:  <input type="text" name="login" required>
        <hr>
        <br>
        Password:   <input type="password" name="password" required>
        <hr>
        <br>
        <button type="submit">Login</button>
        <hr>

    </form>
    <h4><a href="${pageContext.request.contextPath}/users/add">Register</a></h4>
</div>
</body>
</html>

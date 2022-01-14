<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='/WEB-INF/views/css/table_dark.css' %>
</style>
<html>
<head>
    <title>All users</title>
</head>
<body>
<form method="post" id="user" action="${pageContext.request.contextPath}/users/add"></form>
<h1 class="table_dark">Add user:</h1>
<table border="1" class="table_dark">
    <tr>
        <th>Name</th>
        <th>License number</th>
        <th>Login</th>
        <th>Password</th>
        <th>Add</th>
    </tr>
    <tr>
        <td>
            <input type="text" name="name" form="user" required>
        </td>
        <td>
            <input type="text" name="license_number" form="user" required>
        </td>
        <td>
            <input type="text" name="login" form="user" required>
        </td>
        <td>
            <input type="password" name="password" form="user" required>
        </td>
        <td>
            <input type="submit" name="add" form="user">
        </td>
    </tr>
</table>
</body>
</html>

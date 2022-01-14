<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='/WEB-INF/views/css/table_dark.css' %>
</style>
<html>
<head>
    <title>Bicycle Service</title>
</head>
<body>
<form method="post" id="redirect"></form>
<h1 class="table_dark">Bicycle Service</h1>
<%@include file="header.jsp"%>
<table class="table_dark">
    <tr>
        <th>Go to:</th>
    </tr>
    <tr><td><a href="${pageContext.request.contextPath}/users/">Display All Users</a></td></tr>
    <tr><td><a href="${pageContext.request.contextPath}/bicycles/">Display All Bicycles</a></td></tr>
    <tr><td><a href="${pageContext.request.contextPath}/manufacturers/">Display All Manufacturers</a></td></tr>
    <tr><td><a href="${pageContext.request.contextPath}/users/add">Create new Users</a></td></tr>
    <tr><td><a href="${pageContext.request.contextPath}/bicycles/add">Create new Bicycle</a></td></tr>
    <tr><td><a href="${pageContext.request.contextPath}/manufacturers/add">Create new Manufacturer</a></td></tr>
    <tr><td><a href="${pageContext.request.contextPath}/bicycles/users/add">Add User to Bicycle</a></td></tr>
    <tr><td><a href="${pageContext.request.contextPath}/bicycles/by_user">All my Bicycle</a></td></tr>
</table>
</body>
</html>

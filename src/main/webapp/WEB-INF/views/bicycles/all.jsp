<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='/WEB-INF/views/css/table_dark.css' %>
</style>
<html>
<head>
    <title>All bicycles</title>
</head>
<body>
<h1 class="table_dark">All bicycles:</h1>
<table border="1" class="table_dark">
    <tr>
        <th>ID</th>
        <th>Model</th>
        <th>Manufacturer name</th>
        <th>Manufacturer country</th>
        <th>Users</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="bicycle" items="${bicycles}">
        <tr>
            <td>
                <c:out value="${bicycle.id}"/>
            </td>
            <td>
                <c:out value="${bicycle.model}"/>
            </td>
            <td>
                <c:out value="${bicycle.manufacturer.name}"/>
            </td>
            <td>
                <c:out value="${bicycle.manufacturer.country}"/>
            </td>
            <td>
                <c:forEach var="user" items="${bicycle.users}">
                    ${user.id} ${user.name} ${user.licenseNumber} ${user.login}<br>
                </c:forEach>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/bicycles/delete?id=${bicycle.id}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

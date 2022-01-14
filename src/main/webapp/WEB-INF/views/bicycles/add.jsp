<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='/WEB-INF/views/css/table_dark.css' %>
</style>
<html>
<head>
    <title>Add bicycle</title>
</head>
<body>
<form method="post" id="bicycle" action="${pageContext.request.contextPath}/bicycles/add"></form>
<h1 class="table_dark">Add bicycle:</h1>
<table border="1" class="table_dark">
    <tr>
        <th>Model</th>
        <th>Manufacturer ID</th>
        <th>Add</th>
    </tr>
    <tr>
        <td>
            <input type="text" name="model" form="bicycle" required>
        </td>
        <td>
            <input type="number" name="manufacturer_id" form="bicycle" required>
        </td>
        <td>
            <input type="submit" name="add" form="bicycle">
        </td>
    </tr>
</table>
</body>
</html>

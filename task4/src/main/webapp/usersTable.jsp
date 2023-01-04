<%@ page isELIgnored="false" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Users</title>
<link rel="stylesheet" href="/css/usersPage.css" />
</head>
<body>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="userDao" class="task4.UserDao"/>
<div id="divHeader" align=center>
<h2>Users Table</h2>
</div>
<div id="divTable" align=center>
<table>
<thead><td>Login</td><td>Name</td></thead>
<c:forEach items="${userDao.getUsers()}" var="user">
<tr>
<td>${user.login}</td>
<td>${user.name}</td>
</tr>
</c:forEach>
</table>
</div>
</body>
</html>
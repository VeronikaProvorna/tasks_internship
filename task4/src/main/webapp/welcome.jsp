<%@ page isELIgnored="false" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome</title>
<link rel="stylesheet" href="/css/welcomePage.css" />
</head>
<body>
<%
String login = (String) session.getAttribute("login");
%>
<div id="loginDiv">
<table>
<tr>
<td><a href="/users">Show Users</a></td>
<td><a href="/logout">Logout</a></td>
</tr>
</table>
<span id="welcomeHeader"><h2 align=center>Welcome, <%=login %>!</h2></span>
</div>
</body>
</html>
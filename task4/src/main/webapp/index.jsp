<%@ page isELIgnored="false" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<link rel="stylesheet" href="/css/loginPage.css" />
</head>
<body>
<div id="divHeader" align=center>
<h2>Login</h2>
</div>
<div align=center>
<form action="/login" method=post>
<table id="loginForm">
<tr>
<td>Enter Name:</td><td><input type=text name=userName required></td>
</tr>
<tr>
<td>Enter Password:</td><td><input type=password name=userPwd required></td>
</tr>
<tr>
<td colspan="2"><input type=submit value=Login></td>
</tr>
</table>
</form>
</div>
</body>
</html>

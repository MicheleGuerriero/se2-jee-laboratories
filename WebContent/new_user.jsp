<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<h1>Register a New User</h1>
	<div class="error">
		<c:out value="${requestScope.error}" />
	</div>

	<form method="post" action="signup" id="direct_signup">
		<label>Username: </label><input type="text" name="username"
			value="<c:out value="${username}" />" /><br /> <label>Email:
		</label><input type="text" name="email" value="<c:out value="${email}" />" />
		<label>Password: </label><input type="password" name="psw" /> <input
			type="hidden" name="submit" value="1" /> <input type="submit"
			value="Register" />
	</form>
<body>
</html>

<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<h1>Login</h1>
        <%
		if(request.getAttribute("error") != null) {
			out.println("<h2>" + request.getAttribute("error") + "</h2>");
		}
	%>
	<div id="direct_login">
		<form method="post" action="login">
			<label>Email: </label><input type="text" name="userEmail" id="email" /><br />
			<label>Password: </label><input type="password" name="psw" /><br />
			<input type="submit" value="Login" />
		</form>
	</div>
	<div id="signup">
		<form method="get" action="signup">
			Directly sign up with us <input type="submit" value="Signup" />
		</form>
	</div>
</body>
</html>
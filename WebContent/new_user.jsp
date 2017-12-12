<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<h1>Register a New User</h1>
	<%
		if(request.getAttribute("error") != null) {
			out.println("<h2>" + request.getAttribute("error") + "</h2>");
		}
	%>
	<form method="post" action="signup" id="direct_signup">
		<label>Username: </label><input type="text" name="username" /><br /> 
		<label>Email: </label><input type="text" name="email" /><br /> 
		<label>Password: </label><input type="password" name="psw" /> 
		<input type="submit" value="Register" />
	</form>
<body>
</html>

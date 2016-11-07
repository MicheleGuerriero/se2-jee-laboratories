<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="it.polimi.seiiexamples.entities.User"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login ok!</title>
</head>
<body>
	<%
		User u = (User) request.getSession().getAttribute("user");
		out.println("<h1>Welcome " + u.getName() + "!</h1>");
		if(request.getAttribute("message") != null) {
			out.println("<h2>" + request.getAttribute("message") + "</h2>");
		}
	%>
</body>
</html>
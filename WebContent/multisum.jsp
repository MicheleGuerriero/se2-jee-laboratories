<%@ page import="it.polimi.seiiexamples.beans.ListElement"%>
<%@ page import="javax.naming.InitialContext"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!private static ListElement values;

	public void jspInit() {
		try {
			InitialContext ic = new InitialContext();
			values = (ListElement) ic.lookup("java:global/EJBStateful/ListElement");

		} catch (Exception e) {
			System.out.println(e);
		}
	}%>
<%
	if (request.getParameter("addNum") != null) {
		values.addElement(Integer.parseInt(request.getParameter("t1")));
	}

	if (request.getParameter("remNum") != null) {
		values.removeElement(Integer.parseInt(request.getParameter("t1")));
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Addition list elements JSP page</title>
</head>
<body>
	<h1>Welcome</h1>
	<form name="abc" method="post">
		<input type="text" name="t1"><br> <input type="submit"
			value="Add" name="addNum"><br> <input type="submit"
			value="Remove" name="remNum"><br>
		<%
			if (values != null) {
				List<Integer> nums = values.getElements();
				for (int value : nums) {
					out.println("<br>" + value);
				}

				out.println("<br> Size=" + nums.size());
			}
		%>
	</form>
</body>
</html>
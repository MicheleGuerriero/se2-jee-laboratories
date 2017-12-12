<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="it.polimi.seiiexamples.entities.Reservation"%>
<%@ page import="it.polimi.seiiexamples.entities.Car"%>
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
	<form method="post" action="car" id="direct_signup">
		Insert new car! <label>Name: </label><input type="text" name="carName" /><br />
		<label>Number of spots: </label><input type="text" name="nSpots" /> <input
			type="hidden" name="submit" value="1" /> <input type="submit"
			value="InsertCar" />
	</form>
	<form method="get" action="reservation" id="insert_reservation">
		Insert new reservation! <label>Car: </label><input type="text"
			name="carName" /><br /> 
			<input type="submit" value="InsertReservation" />
	</form>
	
		<form method="post" action="reservation" id="finalize_reservation">
		Release car! <label>Car: </label><input type="text"
			name="carName" /><br /> 
			<input type="submit" value="FinalizeReservation" />
	</form>

	<h1>User reservations.</h1>
	<div id="userReservations">
		<%
			List<Reservation> userReservations = (List<Reservation>) request.getAttribute("userReservations");
			for (Reservation r : userReservations) {
				out.println("<br>" + r.getCar().getCarName() + " " + r.getIsActive());
			}
		%>
	</div>
	<h1>Available cars.</h1>
	<div id="cars">
		<%
			List<Car> cars = (List<Car>) request.getAttribute("cars");
			for (Car c : cars) {
				out.println("<br>" + c.getCarName());
			}
		%>
	</div>
</body>
</html>
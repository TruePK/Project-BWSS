<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Employee</title>
</head>
<body>
<DIV ALIGN=CENTER>
	<table>
		<td>
			<form action="/BWSS/addEmployeeView"></form>
			<form action="/BWSS/addEmployee">
					<tr><td><label>General User Information</label></td></tr>
				<tr><td><label>UserID:</label> <input type='text' id ="employeeID" name='employeeID' /></td></tr>
				<tr><td><label>Users First Name:</label> <input type='text' id ="firstName" name='firstName' /></td></tr>
				<tr><td><label>Users Last Name:</label> <input type='text' id ="lastName" name='lastName' /></td></tr>
				<tr><td><label>Users Home Location:</label> <input type='text' id ="home" name='home' /></td></tr>
				<tr><td><label>enabled for scheduling:</label> <input type='text' id ="enabled" name='enabled' /></td></tr>
					<tr><td></td></tr>
				<tr><td><label>Phone Number:</label> <input type='text' id ="phoneNumber" name='phoneNumber' /></td></tr>
					<tr><td></td></tr>
				<tr><td><label>User's Role:</label> <input type='text' id ="role" name='role' /></td></tr>
					<tr><td></td></tr>
				<tr><td><label>User Name:</label> <input type='text' id ="userName" name='userName' /></td></tr>
				<tr><td><label>User Password:</label> <input type='text' id ="userPassword" name='userPassword' /></td></tr>
					<tr><td></td></tr>	
				<tr><td><label>Users Availability: enter Y(can work) or N(can not work)</label></td></tr>
					<tr><td></td></tr>
				<tr><td><label>Monday:</label> <input type='text' id ="monday" name='monday' /></td></tr>
				<tr><td><label>Tuesday:</label> <input type='text' id ="Tuesday" name='Tuesday' /></td></tr>
				<tr><td><label>Wednesday:</label> <input type='text' id ="Wednesday" name='Wednesday' /></td></tr>
				<tr><td><label>Thursday:</label> <input type='text' id ="Thursday" name='Thursday' /></td></tr>
				<tr><td><label>Friday:</label> <input type='text' id ="Friday" name='Friday' /></td></tr>
				<tr><td><label>Saturday:</label> <input type='text' id ="Saturday" name='Saturday' /></td></tr>
				<tr><td><label>Sunday:</label> <input type='text' id ="Sunday" name='Sunday' /></td></tr>

				<tr><td><input type="submit" value="Add New Employee"/></td></tr>
				</form>
		</td>
	</table>
	</DIV>
</body>
</html>
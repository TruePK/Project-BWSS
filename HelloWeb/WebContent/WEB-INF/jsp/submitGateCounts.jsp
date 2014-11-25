<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Submit Daily Gate Counts</title>
</head>
<body>
<DIV ALIGN=CENTER>
	<table><td>
	<form action="/BWSS/gateCountView"></form>
			<form action="/BWSS/applyGateCounts">
	<tr><td><label>Thursday:</label> <input type='text' id ="THURCount" name='THURCount' /></td></tr>
	<tr><td><label>Friday:</label> <input type='text' id ="FRICount" name='FRICount' /></td></tr>
	<tr><td><label>Saturday:</label> <input type='text' id ="SATCount" name='SATCount' /></td></tr>
	<tr><td><label>Sunday:</label> <input type='text' id ="SUNCount" name='SUNCount' /></td></tr>
	<tr><td><label>Monday:</label> <input type='text' id ="MONCount" name='MONCount' /></td></tr>
	<tr><td><label>Tuesday:</label> <input type='text' id ="TUESCount" name='TUESCount' /></td></tr>
	<tr><td><label>Wednesday:</label> <input type='text' id ="WEDCount" name='WEDCount' /></td></tr>
			
	<tr><td><input type="submit" value="Submit Gate Counts"/></td></tr>
	</form>
	<td>
	</table>
	</DIV>
</body>
</html>
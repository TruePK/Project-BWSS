<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Remove a Employee</title>
</head>
<body>
<DIV ALIGN=CENTER>
<form action="/BWSS/menuEDeleteView"></form>
	<table>
	<tr><td>!WARNING! verify the ID before submitting!</td></tr>
    <tr>
     <td>
    	<form action="/BWSS/removeID">
    	<label>UserID:</label> <input type='text' id ="employeeID" name='employeeID' />
    	<input type="submit" value="REMOVE"/>
    	</form>
     </td>
    </tr>
    </table>
    </DIV>
</body>
</html>
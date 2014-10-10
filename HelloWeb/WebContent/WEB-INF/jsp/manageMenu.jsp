<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Employee Management</title>
</head>
<body>
<form:form method="GET" action="/BWSS/menuEManage"> 
       <h2>${name}</h2> 
   	<table>
   		<tr><form action="/BWSS/menuEView" method = "POST">
   		<td><input type="submit" value="View"/></td></form></tr>
   		
   		<form action="/BWSS/sendToAdd">
   		<tr><td><input type="submit" value="Add"/></td></form></tr>
   		
   		<form action="/BWSS/sendToRemove">
   		<tr><td><input type="submit" value="Remove"/></td></form></tr>
    </table>
</form:form>
</body>
</html>
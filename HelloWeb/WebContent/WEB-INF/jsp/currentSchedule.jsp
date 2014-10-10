<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Current Schedule</title>
</head>
<body>
	<form:form method="GET" action="/BWSS/current"> 
  		<h2>${name}</h2> 
   		<table>
   			<tr><form action="/BWSS/openPDF">
   		<td><input type="submit" value="OpenPDF"/></td></form></tr>
   		<tr><form action="/BWSS/UploadPDFMenu">
   			<td><input type="submit" value="UploadPDF"/></td></form></tr>
   		</table>
	</form:form>
</body>
</html>
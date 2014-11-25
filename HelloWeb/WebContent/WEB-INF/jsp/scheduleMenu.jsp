<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Employee Management</title>
</head>
<body>
<DIV ALIGN=CENTER>
<form:form method="GET" action="/BWSS/menuSManage"> 
       <h2>${name}</h2> 
   <table>
   <tr><form action="/BWSS/sendToTemplateView">
   		<td><input type="submit" value="Manual"/></td></form></tr>
   <tr><tr><form action="/BWSS/sendToCreate">
   	<td><input type="submit" value="Generate"/></td></form></tr>
   <tr><form action="/BWSS/menuUploadView">
   		<td><input type="submit" value="Upload"/></td></form></tr>
      </table>
   </form:form>
   </DIV>
</body>
</html>
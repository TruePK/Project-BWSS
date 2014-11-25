<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<form:form method="GET" action="/HelloWeb/uploadView"> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload a New Work Week Schedule</title>
</head>
<body>
<DIV ALIGN=CENTER>
<table>
	<tr>
		<td>
		<form  action="/BWSS/upload" method="POST" enctype="multipart/form-data" >
		<input type="file" id="file" name="file"/></td></tr>
   		<input type="submit" value="Submit"/></form></td></tr>
   		
   		</table>
   		</DIV>
</body>
</form:form>
</html>
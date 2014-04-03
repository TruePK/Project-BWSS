<html>
<head>
<title>Menu</title>
</head>
<body>
      <form:form method="GET" action="/HelloWeb/menu"> 
       <h2>${name}</h2> 
   <table>
   <tr><form action="/HelloWeb/menuCurr">
   		<td><input type="submit" value="Current"/></td></form></tr>
   <tr><td><input type="button" value="Create"/></td></tr>
   <tr><td><input type="button" value="Manage Employees"/></td></tr>
      </table>
   </form:form>
</body>
</html>
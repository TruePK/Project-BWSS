<html>
<head>
<title>Menu</title>
</head>
<body>
      <form:form method="GET" action="/BWSS/menu"> 
       <h2>${name}</h2> 
   <table>
   <tr><form action="/BWSS/menuCurr">
   		<td><input type="submit" value="Current"/></td></form></tr>
   <tr><td><input type="submit" value="Create"/></td></tr>
      </table>
   </form:form>
</body>
</html>
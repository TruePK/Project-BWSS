<html>
<head>
<title>Menu</title>
</head>
<body>
<DIV ALIGN=CENTER>
      <form:form method="GET" action="/BWSS/menu"> 
       <h2>${name}</h2> 
   <table>
   <tr><form action="/BWSS/menuCurr">
   		<td><input type="submit" value="Current"/></td></form></tr>
   
   <tr><form action="/BWSS/menuCre">
   		<td><input type="submit" value="Create"/></td></form></tr>
   		
   <tr><form action="/BWSS/menuMan">
   		<td><input type="submit" value="Manage"/></td></form></tr>
      </table>
   </form:form>
   </DIV>
</body>
</html>
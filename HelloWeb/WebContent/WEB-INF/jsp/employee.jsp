<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title>BWSS</title>
</head>
<body>

<h2>Biltmore Weekly Scheduling System</h2>
<form:form method="POST" action="/HelloWeb/addStudent">
   <table>
    <tr>
        <td><form:label path="name">Name</form:label></td>
        <td><form:input path="name" /></td>
    </tr>
    <tr>
        <td><form:label path="password">Password</form:label></td>
        <td><form:input path="password" /></td>
    </tr>
    <tr>
    <form:form method="GET" action="/HelloWeb/addStudent">
        <td colspan="2">
            <input type="submit" value="Submit"/>
        </td>
       </form:form>
    </tr>
</table>  
</form:form>
</body>
</html>
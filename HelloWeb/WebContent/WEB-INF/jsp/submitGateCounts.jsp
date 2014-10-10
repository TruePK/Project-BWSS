<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Submit Daily Gate Counts</title>
</head>
<body>
	<table><td>
	<form action="/BWSS/gateCountView"></form>
			<form action="/BWSS/applyGateCounts">
	<tr><td><label>Day 1:</label> <input type='text' id ="day1Count" name='day1Count' /></td></tr>
	<tr><td><label>Day 2:</label> <input type='text' id ="day2Count" name='day2Count' /></td></tr>
	<tr><td><label>Day 3:</label> <input type='text' id ="day3Count" name='day3Count' /></td></tr>
	<tr><td><label>Day 4:</label> <input type='text' id ="day4Count" name='day4Count' /></td></tr>
	<tr><td><label>Day 5:</label> <input type='text' id ="day5Count" name='day5Count' /></td></tr>
	<tr><td><label>Day 6:</label> <input type='text' id ="day6Count" name='day6Count' /></td></tr>
	<tr><td><label>Day 7:</label> <input type='text' id ="day7Count" name='day7Count' /></td></tr>
			
	<tr><td><input type="submit" value="Submit Gate Counts"/></td></tr>
	</form>
	<td>
	</table>
</body>
</html>
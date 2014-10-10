package bwss.project;


import java.sql.ResultSet;
import java.sql.SQLException;
 
import org.springframework.jdbc.core.RowMapper;
 
public class EmployeeMapper implements RowMapper
{
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		EmployeeLogin employee = new EmployeeLogin();
		employee.setId(rs.getInt("EmployeeID"));
		employee.setName(rs.getString("First_Name"));
		return employee;
	}
 
}
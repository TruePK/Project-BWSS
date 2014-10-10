package bwss.project;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@EnableWebMvc
public class addEmployeeController {
	private EmployeeLogin employee;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private DataSource datasource;
	
	public void setDataSource(DataSource dataSource) {
		this.datasource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		}
	public void setEmployee(EmployeeLogin tempEmp){
		this.employee = tempEmp;
	}

	@RequestMapping(value= "/addEmployeeView")
	 public String addView(@ModelAttribute("Employee") EmployeeLogin emp,
			 ModelMap model) {
		setEmployee(emp);
		 
		 return "addEmployee";
	   }
	
	@RequestMapping(value= "/addEmployee")
	 public String addEmployee(RedirectAttributes redirectAttributes,
			 @RequestParam("employeeID")int ID,
			 @RequestParam("firstName")String firstName,
			 @RequestParam("lastName")String lastName,
			 @RequestParam("home")String home,
			 @RequestParam("enabled")String enabled,
			 @RequestParam("phoneNumber")String phoneNumber,
			 @RequestParam("role")String role,
			 @RequestParam("userName")String userName,
			 @RequestParam("userPassword")String userPassword,
			 @RequestParam("monday")String Monday,
			 @RequestParam("Tuesday")String Tuesday,
			 @RequestParam("Wednesday")String Wednesday,
			 @RequestParam("Thursday")String Thursday,
			 @RequestParam("Friday")String Friday,
			 @RequestParam("Saturday")String Saturday,
			 @RequestParam("Sunday")String Sunday) {
		String sqlFkDis = "set foreign_key_checks=0;";
		String sqlAvalabilty = "INSERT INTO bwss.avalabilty"
				+ "(EmployeeID,Monday,Tuesday,"
				+ "Wednesday,Thursday,Friday,Saturday,Sunday)"
				+ " VALUES("+ID+",'"+Monday+"','"+Tuesday+"','"+Wednesday
				+ "','"+Thursday+"','"+Friday+"','"+Saturday+"','"+Sunday+"');";
		String sqlContact = "INSERT INTO bwss.contactinfo"
				+ " (EmployeeID,PhoneNumber)" 
				+ " Values("+ID+",'"+phoneNumber+"');";
		String sqlemployee = "INSERT INTO bwss.employee"
				+ " (EmployeeID,First_Name,Last_Name,home,enabled)" 
				+ " Values("+ID+",'"+firstName+"','"
				+ lastName+"','"+home+"','"+enabled+"');";
		String sqlRole = "INSERT INTO bwss.roles"
				+ " (EmployeeID,Roles)" 
				+ " Values("+ID+",'"+role+"');";
		String sqluserLogin = "INSERT INTO bwss.userlogin"
				+ " (EmployeeID,UserName,Password)" 
				+ " Values("+ID+",'"+userName+"','"+userPassword+"');";
		String sqlFkEnabled = "set foreign_key_checks=1;";
		
		String[] sqlAva = {sqlFkDis,sqlAvalabilty,sqlFkEnabled};
		jdbcTemplate.batchUpdate(sqlAva);
		
		String[] sqlCon = {sqlFkDis,sqlContact,sqlFkEnabled};
		jdbcTemplate.batchUpdate(sqlCon);
		
		String[] sqlRol = {sqlFkDis,sqlRole,sqlFkEnabled};
		jdbcTemplate.batchUpdate(sqlRol);
		
		String[] sqllog = {sqlFkDis,sqluserLogin,sqlFkEnabled};
		jdbcTemplate.batchUpdate(sqllog);

		String[] sqlemp = {sqlFkDis,sqlemployee,sqlFkEnabled};
		jdbcTemplate.batchUpdate(sqlemp);

		redirectAttributes.addFlashAttribute("ContEmployee", employee);
		 return "redirect:menu";
	   }
}

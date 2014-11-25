package bwss.project;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@EnableWebMvc
@SessionAttributes({"employee","dataSource","jdbcTemplate"})
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
	 public String addView(
			 ModelMap model) {
		EmployeeLogin emp = (EmployeeLogin) model.get("employee");
		 if(emp == null){
			return "redirect:/";
		 }
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
		
		PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
		
		String tempPassword = passwordEncoder.encode(userPassword);
		
		String sqlAvalabilty = "INSERT INTO bwss.avalabilty"
				+ "(EmployeeID,Monday,Tuesday,"
				+ "Wednesday,Thursday,Friday,Saturday,Sunday)"
				+ " VALUES(?,?,?,?,?,?,?,?);";
		String sqlContact = "INSERT INTO bwss.contactinfo"
				+ " (EmployeeID,PhoneNumber)" 
				+ " Values(?,?);";
		String sqlemployee = "INSERT INTO bwss.employee"
				+ " (EmployeeID,First_Name,Last_Name,home,enabled)" 
				+ " Values(?,?,?,?,?);";
		String sqlRole = "INSERT INTO bwss.roles"
				+ " (EmployeeID,Roles)" 
				+ " Values(?,?);";
		String sqluserLogin = "INSERT INTO bwss.userlogin"
				+ " (EmployeeID,UserName,Password)" 
				+ " Values(?,?,?);";
		
		
		jdbcTemplate.update(sqlAvalabilty,new Object[]{ID,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday});
		
		jdbcTemplate.update(sqlContact,new Object[]{ID,phoneNumber});

		jdbcTemplate.update(sqlRole,new Object[]{ID,role});

		jdbcTemplate.update(sqluserLogin,new Object[]{ID,userName,tempPassword});
		
		jdbcTemplate.update(sqlemployee,new Object[]{ID,firstName,lastName,home,enabled});

		redirectAttributes.addFlashAttribute("ContEmployee", employee);
		 return "redirect:menu";
	   }
}

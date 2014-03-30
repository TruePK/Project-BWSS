package com.tutorialspoint;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import com.tutorialspoint.EmployeeLogin;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.ui.ModelMap;

@Controller
@EnableWebMvc 
public class HelloController{
	
	private JdbcTemplate jdbcTemplate;
	
	private EmployeeLogin employee;
	private DataSource dataSource;
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void setEmployee(EmployeeLogin employeeTemp) {
		this.employee = employeeTemp;
	}
	
   @RequestMapping(value= "/hello", method = RequestMethod.GET)
   public String printHello(@ModelAttribute("ContEmployee") EmployeeLogin employee,
		   @ModelAttribute("jdbcTemplate") JdbcTemplate jdbcTemplate,
		   ModelMap model) {
	   setEmployee(employee);
	   jdbcTemplate.setDataSource(dataSource);
	   System.out.print(employee.getName() + " IS to be to Posted");
	   String sqlUserName = "SELECT UserName FROM bwss.userlogin where UserName = '" 
				+ employee.getName() + "';";
      model.addAttribute("message", "Hello Spring MVC Framework!" + jdbcTemplate.queryForObject(sqlUserName, String.class));

      return "hello";
   }

}
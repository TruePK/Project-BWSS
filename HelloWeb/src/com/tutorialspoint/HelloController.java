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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@EnableWebMvc 
@SessionAttributes({"employee"})
public class HelloController{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private EmployeeLogin employee;
	private DataSource dataSource;
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void setEmployee(EmployeeLogin employeeTemp) {
		this.employee = employeeTemp;
	}
	
	
   @RequestMapping(value= "/hello", method = RequestMethod.GET)
   public String printHello(@ModelAttribute("Employee") EmployeeLogin emp,
		   @ModelAttribute("contJTemp") JdbcTemplate jdbcTemplate,
		   RedirectAttributes redirectAttributes,
		   ModelMap model) {
	  System.out.print("Name: " + emp.getName() + " ID: "+ emp.getId() + " and role: "+ emp.getRole());
      model.addAttribute("message", "Is a emp: " +  emp.getName());

      return "hello";
   }

}
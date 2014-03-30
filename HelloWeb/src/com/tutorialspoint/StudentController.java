package com.tutorialspoint;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tutorialspoint.bwssDAO;


@Controller
@EnableWebMvc 
@SessionAttributes({"employee"})
public class StudentController {

	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	private bwssDAO daObj;
	public EmployeeLogin employee;
	
	
   @RequestMapping(value = "/", method = RequestMethod.GET)
   public ModelAndView Employee() {
	   
      return new ModelAndView("employee", "command", new EmployeeLogin());
   }
   
   public void setEmployee(EmployeeLogin tempEmployee) {
	   
	     this.employee = tempEmployee;
	   }
   
  
   public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		}
   
   public void setdaObj(bwssDAO obj){
	   daObj = obj;
	   
   }
   
   @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
   public String addStudent(@ModelAttribute("SpringWeb")EmployeeLogin student,
		   RedirectAttributes redirectAttributes,ModelMap model) {
	   redirectAttributes.addFlashAttribute("ContEmployee", student);
	   setEmployee(student);
	String sqlUserName = "SELECT UserName FROM bwss.userlogin where UserName = '" 
			+ student.getName() + "';";
	String sqlPassword = "SELECT Password FROM bwss.userlogin where UserName = '" 
			+ student.getName() + "';";
	
	String userNameEntered = jdbcTemplate.queryForObject(sqlUserName, String.class);
	String userPassDB = jdbcTemplate.queryForObject(sqlPassword, String.class);
	   
	  if( (student.getName().equals(userNameEntered)) == true && 
			  (student.getPassword().equals(userPassDB)) == true ){
				  return "redirect:hello";
	  }
      return "redirect:";
	   
   
}
}
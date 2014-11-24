package bwss.project;

import java.io.File;
import java.io.IOException;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
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

import bwss.project.bwssDAO;


@Controller
@EnableWebMvc 
@SessionAttributes({"employee","dataSource","jdbcTemplate"})
public class EmpController {

	
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
   
   @RequestMapping(value = "/addEmp", method = RequestMethod.POST)
   public String addEmp(@ModelAttribute("SpringWeb")EmployeeLogin Emp,
		   RedirectAttributes redirectAttributes,ModelMap model) {
	   
	   redirectAttributes.addFlashAttribute("contDB", dataSource);
	   redirectAttributes.addFlashAttribute("contJTemplate", jdbcTemplate);
	   setEmployee(Emp);
	   
		
	  String sqlID = "SELECT EmployeeID FROM bwss.userlogin where UserName = '" 
		+ Emp.getName() + "';";
	String sqlUserName = "SELECT UserName FROM bwss.userlogin where UserName = '" 
			+ Emp.getName() + "';";
	String sqlPassword = "SELECT Password FROM bwss.userlogin where UserName = '" 
			+ Emp.getName() + "';";
	try{
	if(!Emp.getName().equals("") || !Emp.getPassword().equals("")){
	String EmployeeID = jdbcTemplate.queryForObject(sqlID, String.class);
	String userNameEntered = jdbcTemplate.queryForObject(sqlUserName, String.class);
	String userPassDB = jdbcTemplate.queryForObject(sqlPassword, String.class);
	String sqlRole = "SELECT roles FROM bwss.roles where EmployeeID = '" 
					+ EmployeeID + "';";
	
	
	
	
	  if( (Emp.getName().equals(userNameEntered)) == true && 
			  (Emp.getPassword().equals(userPassDB)) == true ){
			String EmployeeRole = jdbcTemplate.queryForObject(sqlRole, String.class);
		  	Emp.setId((Integer.parseInt(EmployeeID)));
			Emp.setName(userNameEntered);
			
			
		  if(EmployeeRole.equals("ADMIN")){
			  Emp.setRole(EmployeeRole);
			  redirectAttributes.addFlashAttribute("ContEmployee", Emp);
				  return "redirect:menu";
		  }else if(EmployeeRole.equals("MOD")){
			  Emp.setRole(EmployeeRole);
			  redirectAttributes.addFlashAttribute("ContEmployee", Emp);
				  return "redirect:menu";
		  }else if(EmployeeRole.equals("USER")){
			  Emp.setRole(EmployeeRole);
			  redirectAttributes.addFlashAttribute("ContEmployee", Emp);
				  return "redirect:menu";
		  }
		  
	  }
	}
	}catch(Exception ex){
		return "redirect:/";
	}
      return "redirect:/";
}
}
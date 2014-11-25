package bwss.project;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
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
   public ModelAndView Employee(SessionStatus status,ModelMap model,HttpServletRequest request) {
	   model.clear();
	   status.setComplete();
	   HttpSession session = request.getSession();
		session.setAttribute("employee", null);
      return new ModelAndView("employee", "command", new EmployeeLogin());
   }
   
   @RequestMapping(value = "/reset", method = RequestMethod.POST)
   public void reset(SessionStatus status,ModelMap model,HttpServletRequest request) {
	   model.clear();
	   HttpSession session = request.getSession();
		session.setAttribute("employee", null);
		status.setComplete();
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
		   RedirectAttributes redirectAttributes,ModelMap model,HttpServletRequest request) {
	   HttpSession session = request.getSession();
		session.setAttribute("employee", null);
	   
	   redirectAttributes.addFlashAttribute("contDB", dataSource);
	   redirectAttributes.addFlashAttribute("contJTemplate", jdbcTemplate);
	   setEmployee(Emp);
	   
	   PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
		
	  String sqlID = "SELECT EmployeeID FROM bwss.userlogin where UserName = ?;";
	String sqlUserName = "SELECT UserName FROM bwss.userlogin where UserName = ?;";
	String sqlPassword = "SELECT Password FROM bwss.userlogin where UserName = ?;";
	try{
	if(!Emp.getName().equals("") || !Emp.getPassword().equals("")){
		
		
		
	String EmployeeID = jdbcTemplate.queryForObject(sqlID,new Object[]{Emp.getName()}, String.class);
	String userNameEntered = jdbcTemplate.queryForObject(sqlUserName,new Object[]{Emp.getName()}, String.class);
	String userPassDB = jdbcTemplate.queryForObject(sqlPassword,new Object[]{Emp.getName()}, String.class);
	
	Emp.setId((Integer.parseInt(EmployeeID)));
	
	String sqlRole = "SELECT roles FROM bwss.roles where EmployeeID = ?;";
	String sqlFName = "SELECT First_Name FROM bwss.employee where EmployeeID = ?;";
	String userName= jdbcTemplate.queryForObject(sqlFName,new Object[]{Emp.getId()}, String.class);
	
		//Emp.getPassword().equals(userPassDB))    This was old pass verification
	  if( (Emp.getName().equals(userNameEntered)) == true && 
			  (passwordEncoder.matches(Emp.getPassword(), userPassDB) == true )){
			
		  String EmployeeRole = jdbcTemplate.queryForObject(sqlRole,new Object[]{Emp.getId()}, String.class);
		  				Emp.setName(userName);
			
			//HttpSession session = request.getSession();
			//Emp = (EmployeeLogin)request.getAttribute("employee");
			session.setAttribute("employee", Emp);
			session.setAttribute("jdbcTemplate", jdbcTemplate);
			session.setAttribute("dataSource", dataSource);
			
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
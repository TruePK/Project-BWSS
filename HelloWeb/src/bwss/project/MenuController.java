package bwss.project;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
@Controller
@EnableWebMvc
@SessionAttributes({"employee","dataSource","jdbcTemplate"})
public class MenuController {
	private EmployeeLogin employee;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public DataSource datasource;
	
	
	 @RequestMapping(value= "/menu", method = RequestMethod.GET)
	 public String Employee(@ModelAttribute("ContEmployee") EmployeeLogin emp,
			 @ModelAttribute("ContJTemplate") JdbcTemplate jtemplate, 
			 ModelMap model) {
		 try{
			 
				setEmp(emp);
			 
			 if(emp.getRole().equals("ADMIN")){
			 model.addAttribute("name", "Hello " +  employee.getName());
		      return "menuAdmin";
			 }
			 if(emp.getRole().equals("MOD")){
				 model.addAttribute("name", "Hello " +  employee.getName());
			      return "menuMod";
				 }
			 if(emp.getRole().equals("USER")){
				 model.addAttribute("name", "Hello " +  employee.getName());
			      return "menuUser";
				 }
			 return "redirect:";
		 }catch(Exception e){
			 return "redirect:";
		 }
		
	   }
	
	public void setEmp(EmployeeLogin emp){
		employee = emp;
	}
	
	
	   @RequestMapping(value= "/menuCurr")
	   public String Curr(RedirectAttributes redirectAttributes,
			   ModelMap model) {
		  
	      
	      redirectAttributes.addFlashAttribute("Employee", employee);
	      redirectAttributes.addFlashAttribute("jdbcTemplate", jdbcTemplate);
	      return "redirect:hello";
	   }
	   @RequestMapping(value= "/menuCre")
	   public String Cre(RedirectAttributes redirectAttributes,
			   ModelMap model) {
		   
		   
	      redirectAttributes.addFlashAttribute("Employee", employee);
	      redirectAttributes.addFlashAttribute("jdbcTemplate", jdbcTemplate);
	      redirectAttributes.addFlashAttribute("datasource", datasource);
	      return "redirect:menuSManage";
	   }
	   @RequestMapping(value= "/menuMan")
	   public String oMan(RedirectAttributes redirectAttributes,
			   ModelMap model) {
		  
	      redirectAttributes.addFlashAttribute("Employee", employee);
	      redirectAttributes.addFlashAttribute("jdbcTemplate", jdbcTemplate);

	      return "redirect:menuEManage";
	   }
}

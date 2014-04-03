package com.tutorialspoint;

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

public class MenuController {
	private EmployeeLogin employee;
	 @RequestMapping(value= "/menu", method = RequestMethod.GET)
	 public String Employee(@ModelAttribute("ContEmployee") EmployeeLogin emp,ModelMap model) {
		 setEmp(emp);
		 model.addAttribute("name", "Hello " +  employee.getName());
	      return "menuAdmin";
	   }
	
	public void setEmp(EmployeeLogin emp){
		employee = emp;
	}
	
	   @RequestMapping(value= "/menuCurr")
	   public String printHello(
			   @ModelAttribute("jdbcTemplate") JdbcTemplate jdbcTemplate,
			   RedirectAttributes redirectAttributes,
			   ModelMap model) {
		  System.out.print("Menu! Name: " + employee.getName() +
				  " ID: "+ employee.getId() + " and role: "+ employee.getRole());
	      
	      redirectAttributes.addFlashAttribute("Employee", employee);
	      return "redirect:hello";
	   }
}

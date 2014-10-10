package bwss.project;

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
public class CreateSController {
	private EmployeeLogin employee;
	private JdbcTemplate jdbcTemplate;
	 @RequestMapping(value= "/menuSManage", method = RequestMethod.GET)
	 public String Employee(@ModelAttribute("Employee") EmployeeLogin emp,
			 @ModelAttribute("jdbcTemplate") JdbcTemplate jtemplate, 
			 ModelMap model) {
		 try {
			setEmp(emp);
		 setJTemplate(jtemplate);
		//System.out.print(" Name: " + employee.getName() +
		// " ID: "+ employee.getId() + " and role: "+ employee.getRole());
		 return "scheduleMenu";
		} catch (Exception e) {
			return"redirect:emploee";
		}
		 
	   }
	
	public void setEmp(EmployeeLogin emp){
		employee = emp;
	}
	public void setJTemplate(JdbcTemplate template){
		jdbcTemplate = template;
	}
	
	   @RequestMapping(value= "/menuSView")
	   public String Start(RedirectAttributes redirectAttributes,
			   ModelMap model) {
		  
	      
	      redirectAttributes.addFlashAttribute("Employee", employee);
	      redirectAttributes.addFlashAttribute("jdbcTemplate", jdbcTemplate);
	      return "redirect:hello";
	   }
	   @RequestMapping(value= "/menuUploadView")
	   public String uploadRedirect(RedirectAttributes redirectAttributes,
			   ModelMap model) {
		  
	      
	      redirectAttributes.addFlashAttribute("Employee", employee);
	      redirectAttributes.addFlashAttribute("jdbcTemplate", jdbcTemplate);
	      return "redirect:uploadView";
	   }
	   @RequestMapping(value= "/sendToTemplateView")
	   public String sendToTemplate(RedirectAttributes redirectAttributes,
			   ModelMap model) {
		
		redirectAttributes.addFlashAttribute("Employee", employee);
	      redirectAttributes.addFlashAttribute("jdbcTemplate", jdbcTemplate);
		return "redirect:templateView";
	}
	   @RequestMapping(value= "/sendToCreate")
	   public String sendToCreate(RedirectAttributes redirectAttributes,
			   ModelMap model) {
		
		redirectAttributes.addFlashAttribute("Employee", employee);
	      redirectAttributes.addFlashAttribute("jdbcTemplate", jdbcTemplate);
		return "redirect:gateCountView";
	}
	   
}

package bwss.project;


import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@EnableWebMvc
@SessionAttributes({"employee","dataSource","jdbcTemplate"})
public class gateCountController {
	@RequestMapping(value= "/gateCountView")
	 public String addView(
			 ModelMap model) {
		EmployeeLogin emp = (EmployeeLogin) model.get("employee");
		 if(emp == null){
			return "redirect:/";
		 }
		
		return "submitGateCounts";
	   }
	@RequestMapping(value= "/applyGateCounts")
	public String sendToGenration(RedirectAttributes redirectAttributes,
			@ModelAttribute("jdbcTemplate") JdbcTemplate jdbcTemplate,
			 @RequestParam("THURCount")int THUR,
			 @RequestParam("FRICount")int FRI,
			 @RequestParam("SATCount")int SAT,
			 @RequestParam("SUNCount")int SUN,
			 @RequestParam("MONCount")int MON,
			 @RequestParam("TUESCount")int TUES,
			 @RequestParam("WEDCount")int WED) {
		Map<String,Integer> gateCount = new LinkedHashMap<String,Integer>();
		
		gateCount.put("THUR", THUR);
		gateCount.put("FRI", FRI);
		gateCount.put("SAT", SAT);
		gateCount.put("SUN", SUN);
		gateCount.put("MON", MON);
		gateCount.put("TUES", TUES);
		gateCount.put("WED", WED);
		
		redirectAttributes.addFlashAttribute("gateCount", gateCount);
		redirectAttributes.addFlashAttribute("jdbcTemplate", jdbcTemplate);

		return"redirect:generateDownloadView";	
	}
}

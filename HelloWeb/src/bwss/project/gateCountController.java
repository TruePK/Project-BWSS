package bwss.project;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@EnableWebMvc
public class gateCountController {
	@RequestMapping(value= "/gateCountView")
	 public String addView(@ModelAttribute("Employee") EmployeeLogin emp,
			 ModelMap model) {
		
		return "submitGateCounts";
	   }
	@RequestMapping(value= "/applyGateCounts")
	public String sendToGenration(RedirectAttributes redirectAttributes,
			 @RequestParam("day1Count")int day1,
			 @RequestParam("day2Count")int day2,
			 @RequestParam("day3Count")int day3,
			 @RequestParam("day4Count")int day4,
			 @RequestParam("day5Count")int day5,
			 @RequestParam("day6Count")int day6,
			 @RequestParam("day7Count")int day7) {
		int[] gateCount = {day1,day2,day3,day4,day5,day6,day7};
		
		System.out.print(gateCount[0]);
		return"redirect:generateDownloadView";
		
	}

}

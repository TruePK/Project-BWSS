package bwss.project;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

public class currScheduleController {
	private EmployeeLogin employee;
	
	private void setEmp(EmployeeLogin tempEmp){
		employee = tempEmp;
	}
	
	public String Employee(@ModelAttribute("ContEmployee") EmployeeLogin emp,ModelMap model) {
		 setEmp(emp);
		 model.addAttribute("name", "Hello " +  employee.getName());
	      return "menuAdmin";
	   }
}

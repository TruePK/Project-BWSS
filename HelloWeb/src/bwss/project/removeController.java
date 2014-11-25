package bwss.project;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@EnableWebMvc
@SessionAttributes({"employee","dataSource","jdbcTemplate"})
public class removeController {
	private EmployeeLogin employee;
	@Autowired
	private JdbcTemplate jdbctemplate;
	private DataSource datasource;
	
	public void setDataSource(DataSource dataSource) {
		this.datasource = dataSource;
		this.jdbctemplate = new JdbcTemplate(dataSource);
		}
	public void setEmployee(EmployeeLogin tempEmp){
		this.employee = tempEmp;
	}

	 @RequestMapping(value = "/menuEDeleteView")
	 public String employeeDelete(
			 RedirectAttributes redirectAttributes,
			 ModelMap model){
		 JdbcTemplate jdbcTemplate = (JdbcTemplate) model.get("jdbcTemplate");
		 EmployeeLogin emp = (EmployeeLogin) model.get("employee");
		 if(emp == null){
			return "redirect:/";
		 }else{
			 setDataSource(jdbcTemplate.getDataSource());
		 }
		 setEmployee(emp);
	
		 return "removeEmployee";
   }
	 @RequestMapping(value="/removeID")
	 public String remove(@RequestParam("employeeID")int ID,
			 RedirectAttributes redirectAttributes){
		String sql1 = "DELETE FROM avalabilty WHERE EmployeeID = ?;";
		String sql2 = "DELETE FROM contactinfo WHERE EmployeeID = ?;";
		String sql3 = "DELETE FROM roles WHERE EmployeeID = ?;";
		String sql4 = "DELETE FROM userlogin WHERE EmployeeID = ?;";
		String sql5 = "DELETE FROM employee WHERE EmployeeID = ?;";
		
		jdbctemplate.update(sql1,new Object[]{ID});
		jdbctemplate.update(sql2,new Object[]{ID});
		jdbctemplate.update(sql3,new Object[]{ID});
		jdbctemplate.update(sql4,new Object[]{ID});
		jdbctemplate.update(sql5,new Object[]{ID});
		redirectAttributes.addFlashAttribute("ContEmployee", employee);
	      redirectAttributes.addFlashAttribute("ContJTemplate", jdbctemplate);
		 return "redirect:menu"; 
	 }
}

package bwss.project;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.MapAccessor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import javax.sql.DataSource;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@EnableWebMvc
@SessionAttributes({"employee","dataSource","jdbcTemplate"})
public class MenuEController {
	private EmployeeLogin employee;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	 DataSource datasource;
	 
	 

	 @RequestMapping(value= "/menuEManage", method = RequestMethod.GET)
	 public String Employee(
			 ModelMap model) {
		 EmployeeLogin emp = (EmployeeLogin) model.get("employee");
		 JdbcTemplate jtemplate = (JdbcTemplate) model.get("jdbcTemplate");
		 if(emp == null){
			return "redirect:/";
		 }
		 try{		
			 setEmp(emp);
		 return "manageMenu";
		 }catch(Exception e){
			 return "redirect:";
		 }
		 
	   }
	
	 public void setDataSource(DataSource dataSource) {
			this.datasource = dataSource;
			this.jdbcTemplate = new JdbcTemplate(dataSource);
			}

	public void setEmp(EmployeeLogin emp){
		employee = emp;
	}
	public List<EmployeeLogin> findAll(){
		 
		String sql = "SELECT employee.*,contactinfo.PhoneNumber FROM bwss.contactinfo,"
		   		+ " bwss.employee "
		   		+ "where bwss.contactinfo.EmployeeID = bwss.employee.EmployeeID;";
	 
		List<EmployeeLogin> Employees = new ArrayList<EmployeeLogin>();
	 
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map row : rows) {
			EmployeeLogin employee = new EmployeeLogin();
			employee.setId((Integer)(row.get("EmployeeID")));
			employee.setFirstN((String)row.get("First_Name"));
			employee.setLastN((String)row.get("Last_Name"));
			employee.setHome((String)row.get("home"));
			employee.setEnabled((String)row.get("enabled"));
			employee.setPhoneNumber((String)row.get("PhoneNumber"));
			Employees.add(employee);
		}
	 
		return Employees;
	}
	
	
	@RequestMapping(value= "/sendToRemove")
	   public String sendToRemove(RedirectAttributes redirectAttributes,
			   ModelMap model) {
		
		redirectAttributes.addFlashAttribute("Employee", employee);
	      redirectAttributes.addFlashAttribute("jdbcTemplate", jdbcTemplate);
		return "redirect:menuEDeleteView";
	}
	
	@RequestMapping(value= "/sendToAdd")
	   public String sendToAdd(RedirectAttributes redirectAttributes,
			   ModelMap model) {
		
		redirectAttributes.addFlashAttribute("Employee", employee);
	      redirectAttributes.addFlashAttribute("jdbcTemplate", jdbcTemplate);
		return "redirect:addEmployeeView";
	}
	
	   @RequestMapping(value= "/menuEView",method = RequestMethod.POST)
	   public @ResponseBody String printHello(
			   ModelMap model) {
		  
		   
		   
		   String Output;
		   
			   String sqlQuery = "SELECT employee.*,contactinfo.PhoneNumber FROM bwss.contactinfo,"
			   		+ " bwss.employee "
			   		+ "where bwss.contactinfo.EmployeeID = bwss.employee.EmployeeID;";
			   List<EmployeeLogin> empList = findAll();
		   int curr = 0;
		   
		   Output = ("<br> ID	|	First	|	Last	|	Home	|	Enabled	|	Phone	|<br>");
		   Output = Output + ("<br>________________________________________________________________<br>");
		   while(curr < empList.size()){
		   EmployeeLogin e = empList.get(curr);
		   Output = Output + ("<pre>" + e.getId()+ " "
				   + e.getFirst()+" "+
				   e.getLast()+" " +
				   e.getHomeLoc()+ " "
				   + e.getEnabled()+" "
				   	+e.getPhone()+" "+ "<br>");
		   curr++;
		   }
		   
			
	      //redirectAttributes.addFlashAttribute("Employee", employee);
	      //redirectAttributes.addFlashAttribute("jdbcTemplate", jdbcTemplate);
	      return Output;
	   } 
}

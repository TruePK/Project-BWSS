package bwss.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Controller
@EnableWebMvc
public class genrateSchedule {
	private EmployeeLogin employee;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public DataSource datasource;
	
	
	
	private int[] currCell = {0,3};
	
	public void setDataSource(DataSource dataSource) {
		this.datasource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		}
	public void setJTemplate(JdbcTemplate template){
		jdbcTemplate = template;
	}
	
	@RequestMapping(value= "/generateDownloadView")
	 public String addView(@ModelAttribute("Employee") EmployeeLogin emp,
			 ModelMap model) {
		
		return "generateDownload";
	   }
	
	
	public List<EmployeeLogin> findAllOfLoc(String homeLoc){
		 
		String sql = "SELECT employee.* " +
				"FROM  bwss.employee " +
				"where bwss.employee.home='" + homeLoc + "';";
	 
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
	
	public List<EmployeeLogin>  findEmpAvalabilty(List<EmployeeLogin> Employees){
		
		int sizeOfList = Employees.size();
		
		for(int i = 0; i <= sizeOfList; i++){
			String sql = "select bwss.avalabilty.* from bwss.avalabilty where EmployeeID = "+
					Employees.get(i).getId() +";";
			
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
			for(int k = 1; k <=7; k++){
				List<String> tempList = null;
				tempList.set(k-1, rows.get(k).toString());
				Employees.get(i).setAvalabilty(tempList);
			}
		
		}
		return Employees;
		
	}
	
	public void setupVarsForGenration(){
		
		
	}

}
 
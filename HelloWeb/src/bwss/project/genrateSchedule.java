package bwss.project;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Controller
@EnableWebMvc

public class genrateSchedule {
	
	private EmployeeLogin employee;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public DataSource datasource;
	Map<String,Integer> gateCount = new LinkedHashMap<String,Integer>();
	
	
	
	private int[] currCell = {0,3};
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		}
	
	@RequestMapping(value= "/generateDownloadView")
	 public String addView(@ModelAttribute("Employee") EmployeeLogin emp,
			 @ModelAttribute("gateCount")Map<String,Integer> gateCount,
			 @ModelAttribute("jdbcTemplate") JdbcTemplate jdbcTemplate,
			 ModelMap model) {
		this.gateCount = gateCount;
		setDataSource(jdbcTemplate.getDataSource());
		
		//Returns a list of the areas
		String sqlForAreas = "select home from employee group by home;";	
		List<String> areasInList = (List<String>) jdbcTemplate.queryForList(sqlForAreas, String.class);
		
		//Returns a list of employees
		List<Integer> listOfEmployeesByArea = new LinkedList<Integer>();
		listOfEmployeesByArea = findEmpByArea(areasInList.get(0));
		return "generateDownload";
	   }
	
	@RequestMapping(value= "/TestConnection")
	public List findEmpByArea(String homeLoc){
		 
		String sql = "Select EmployeeID from employee where employee.home = \""
				+ homeLoc+"\" and enabled=\"Y\";";		
	    List<Integer> empIDByHome = new LinkedList<Integer>();		
	    
	    empIDByHome = jdbcTemplate.queryForList(sql, Integer.class);
		return empIDByHome;
	}
	public void setupVarsForGenration(){
		
		
	}

}
 
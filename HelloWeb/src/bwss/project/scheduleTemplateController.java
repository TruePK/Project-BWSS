package bwss.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
@Controller
@EnableWebMvc
public class scheduleTemplateController {
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

	@RequestMapping(value= "/templateView")
	   public String setup(
			   RedirectAttributes redirectAttributes){

		return "manualTemplate";
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
			String sql = "select bwss.avalabilty.* from bwss.avalabilty where EmployeeID ="+
					Employees.get(0).getId() +";";
			
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
			for(int k = 1; k <=7; k++){
				List<String> tempList = null;
				tempList.set(k-1, rows.get(k).toString());
				Employees.get(i).setAvalabilty(tempList);
			}
		
		}
		return Employees;
		
	}
	@RequestMapping(value="/getXLS")
	public void createWorkbook(HttpSession session,HttpServletResponse response) throws IOException, Exception, WriteException{
		List<EmployeeLogin> employeesBake = findAllOfLoc("BAKE");
		List<EmployeeLogin> employeesCYM	= findAllOfLoc("CYM");
		List<EmployeeLogin> employeesICP	= findAllOfLoc("ICP");
		List<EmployeeLogin> employeesKIT	= findAllOfLoc("KIT");
		
		WritableWorkbook wworkbook;
	      wworkbook = Workbook.createWorkbook(new File("C:/schedule/output.xls"));
	      WritableSheet wsheet = wworkbook.createSheet("Schedule", 0);
	      
	      Label labelDate = new Label(0, 0, "DATE");
          wsheet.addCell(labelDate);
          
          Label labelCount = new Label(0, 1, "Gate Count");
          wsheet.addCell(labelCount);
          
	      Label labelBake = new Label(0, currCell[1], "VBS");
          wsheet.addCell(labelBake);

          currCell[1] = currCell[1] +1;
	      for(int y = 0; y < employeesBake.size(); y++){
	    	  
	    	  Label label = new Label(0, currCell[1], employeesBake.get(y).getFirst());
	          wsheet.addCell(label);
	          currCell[1] = currCell[1] + 1;
	          
	      }
	      currCell[1] = currCell[1] +1;
	      
	      Label labelCYM= new Label(0, currCell[1], "CYM");
	      currCell[1] = currCell[1] +1;
          wsheet.addCell(labelCYM);
	      for(int y = 0; y < employeesCYM.size(); y++){
	    	  
	    	  Label label = new Label(0, currCell[1], employeesCYM.get(y).getFirst());
	          wsheet.addCell(label);
	          currCell[1] = currCell[1] + 1;
	          
	      }
	      currCell[1] = currCell[1] +1;
	      
	      Label labelICP= new Label(0, currCell[1], "ICP");
	      currCell[1] = currCell[1] +1;
          wsheet.addCell(labelICP);
	      for(int y = 0; y < employeesICP.size(); y++){
	    	  
	    	  Label label = new Label(0, currCell[1], employeesICP.get(y).getFirst());
	          wsheet.addCell(label);
	          currCell[1] = currCell[1] + 1;
	          
	      }
	      currCell[1] = currCell[1] +1;
	      Label labelKIT= new Label(0, currCell[1], "KIT");
	      currCell[1] = currCell[1] +1;
	      
	     
          wsheet.addCell(labelKIT);
	      for(int y = 0; y < employeesKIT.size(); y++){
	    	  
	    	  Label label = new Label(0, currCell[1], employeesKIT.get(y).getFirst());
	          wsheet.addCell(label);
	          currCell[1] = currCell[1] + 1;
	          
	      }
	      currCell[1] = currCell[1] +1;
	      
          wworkbook.write();
	      wworkbook.close();
	      InputStream inputStream =new FileInputStream("C:/schedule/output.xls");
	      response.setContentType("application/force-download");
          response.setHeader("Content-Disposition", "attachment; filename= "+"C:/schedule/output.xls");
          IOUtils.copy(inputStream, response.getOutputStream());
	
	}
	
}

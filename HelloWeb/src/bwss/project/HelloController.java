package bwss.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;








import bwss.project.EmployeeLogin;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@EnableWebMvc 
@SessionAttributes({"employee"})
public class HelloController{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private EmployeeLogin employee;
	private DataSource dataSource;
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void setEmployee(EmployeeLogin employeeTemp) {
		this.employee = employeeTemp;
	}
	@RequestMapping(value= "/getPDF")
	public void getPDF(HttpSession session,HttpServletResponse response) throws Exception{
        try{ 	
            String filePathToBeServed = "C:/schedule/curr.pdf"; //complete file name with path;
                    File fileToDownload = new File(filePathToBeServed);
                    InputStream inputStream =new FileInputStream(fileToDownload);
                    response.setContentType("application/force-download");
                    response.setHeader("Content-Disposition", "attachment; filename="+"curr.pdf"); 
                    IOUtils.copy(inputStream, response.getOutputStream());
                    inputStream.close();
                    inputStream = null;
                    
                      response.flushBuffer();
                      response = null;
                      System.gc();

        }catch(Exception e){
            e.printStackTrace();
        }
	}
	
	
   @RequestMapping(value= "/hello", method = RequestMethod.GET)
   public String printHello(@ModelAttribute("Employee") EmployeeLogin emp,
		   @ModelAttribute("contJTemp") JdbcTemplate jdbcTemplate,
		   RedirectAttributes redirectAttributes,
		   ModelMap model) {
      model.addAttribute("message", "Welcome: " +  emp.getName() + " this is the current schedule.");
      return "hello";
   }
}
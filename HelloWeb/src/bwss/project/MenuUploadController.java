package bwss.project;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@EnableWebMvc
@SessionAttributes({"employee","dataSource","jdbcTemplate"})
public class MenuUploadController {
	private MultipartFile file;
	private String stringName ;
	
private EmployeeLogin employee;
private JdbcTemplate jdbcTemplate;
	
	private void setEmp(EmployeeLogin tempEmp){
		employee = tempEmp;
	}
	private void setjdbc(JdbcTemplate temp){
		jdbcTemplate = temp;
	}
	@RequestMapping(value= "/uploadView", method = RequestMethod.GET)
	public String Start(
			ModelMap model) {
		EmployeeLogin emp = (EmployeeLogin) model.get("employee");
		 if(emp == null){
			return "redirect:/";
		 }
		 setEmp(emp);
	      return "uploadMenu";
	   }
	@RequestMapping(value= "/upload", method = RequestMethod.POST )
	public @ResponseBody
		String upload(MultipartHttpServletRequest request,
			ModelMap model) {
		this.file = request.getFile("file");
		stringName = (request.getFile("file").getOriginalFilename());;
	      if(!file.isEmpty()){
		try{	
	      File serverFile = new File("C:/schedule/curr.pdf");
          BufferedOutputStream stream = new BufferedOutputStream(
                  new FileOutputStream(serverFile));
          stream.write(file.getBytes());
          stream.close();
          return "uploaded Sucsseful: " + file.getOriginalFilename();
		}catch( Exception e){
			return "uploaded Fail";
		}}
	      return "upload Fail";
	   }
	@RequestMapping(value= "/redirect")
	   public String redirect(RedirectAttributes redirectAttributes){
      System.out.print(stringName + " is null?");
		
		redirectAttributes.addFlashAttribute("Employee", employee);
	      redirectAttributes.addFlashAttribute("contJTemplate", jdbcTemplate);
		  return "redirect:hello";
	   }
}


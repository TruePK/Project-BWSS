package bwss.project;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;


public class EmployeeLogin {
	

private String password;
	
   private String name;
	
   private Integer id;
   
   private String role;
   private String First_Name;
   private String Last_Name;
   private String home;
   private String enabled;
   private String PhoneNumber;

   
   public String getPhone() {
	      return PhoneNumber;
	   }
   public String getEnabled() {
	      return enabled;
	   }
   public String getHomeLoc(){
	   return home;
   }
   public String getLast() {
	      return Last_Name;
	   }
   public String getFirst() {
	      return First_Name;
	   }
   public void setPhoneNumber(String num) {
	      this.PhoneNumber = num;
	   }
   public void setEnabled(String enabled) {
	      this.enabled = enabled;
	   }
   public void setHome(String home) {
	      this.home = home;
	   }
   public void setLastN(String name) {
	      this.Last_Name = name;
	   }

   public void setFirstN(String name) {
	      this.First_Name = name;
	   }
   public void setPassword(String pass) {
      this.password = pass;
   }
   public String getPassword() {
      return password;
   }

   public void setName(String i) {
      this.name = i;
   }
   public String getName() {
      return name;
   }

   public void setId(Integer id) {
      this.id = id;
   }
   public Integer getId() {
      return id;
   }
   public void setRole(String i) {
	      this.role = i;
   }
   public String getRole() {
	      return role;
   }
}
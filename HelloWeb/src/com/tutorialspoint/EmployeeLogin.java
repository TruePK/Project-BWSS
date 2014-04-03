package com.tutorialspoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;


public class EmployeeLogin {
	
   private String password;
	
   private String name;
	
   private Integer id;
   
   private String role;

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
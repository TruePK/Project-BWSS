package com.tutorialspoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;


public class EmployeeLogin {
	
   private String password;
	
   private String name;
	
   private Integer id;

   public void setPassword(String pass) {
      this.password = pass;
   }
   public String getPassword() {
      return password;
   }

   public void setName(String name) {
      this.name = name;
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
}
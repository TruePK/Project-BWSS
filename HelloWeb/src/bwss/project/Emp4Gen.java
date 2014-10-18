package bwss.project;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class Emp4Gen {
	private Integer ID;
	private String firstName;
	private String lastName;
	private String homeLoc;
	     //Day/Availability(Y/N)
	Map<String,String> empAvalMap;
	private JdbcTemplate jdbctemplate;
	private DataSource datasource;

public Emp4Gen(DataSource dataS){
		datasource = dataS;
		this.jdbctemplate = new JdbcTemplate(datasource);
}

public Emp4Gen(DataSource dataS,Integer ID){
	datasource = dataS;
	this.jdbctemplate = new JdbcTemplate(datasource);
	setID(ID);
}

public void setID(Integer ID){
	this.ID = ID;
}
public void setfirstName(String fName){
	firstName = fName;
}
public void setlastName(String lName){
	lastName = lName;
}
public void setHomeLoc(String Home){
	homeLoc=Home;
}
public void setEmpAvalMap(Map<String,String> Aval){
	empAvalMap = Aval;
}
public Integer getID(){
	return ID;
}
public String getName(){
	return firstName+" "+lastName;
}
public String getHome(){
	return homeLoc;
}
public Map<String,String> getAvalMap(){
	return empAvalMap;
}









}
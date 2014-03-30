package com.tutorialspoint;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.SessionAttributes;
@SessionAttributes({"jbdcTemplate"})
public class bwssDAO {
	
	
	private JdbcTemplate jdbcTemplate;

	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.setJdbcTemplate(new JdbcTemplate(dataSource));
		}
	public  DataSource getdataSource(){
		return dataSource;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		testQuery();
	}
	public  void testQuery(){
		
		System.out.print(
		(jdbcTemplate.queryForObject("SELECT UserName FROM bwss.userlogin where UserName "
				+ "='TheTruePK';",String.class) + " TESTED"));
	}
}

package bwss.project;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

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
@SessionAttributes({"employee","dataSource","jdbcTemplate"})
public class genrateSchedule {
	
	private EmployeeLogin employee;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	 DataSource datasource;
	Map<String,Integer> gateCount = new LinkedHashMap<String,Integer>();
	
	private int[] currCell = {0,3};
	
	public void setDataSource(DataSource dataSource) {
		this.datasource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		}
	
	@RequestMapping(value= "/generateDownloadView")
	 public String addView(@ModelAttribute("Employee") EmployeeLogin emp,
			 @ModelAttribute("gateCount")Map<String,Integer> gateCount,
			 @ModelAttribute("jdbcTemplate") JdbcTemplate jdbcTemplate,
			 ModelMap model) {
		System.out.print("Starting Generates view \n");
		this.gateCount = gateCount;
		//setDataSource(jdbcTemplate);
		
		//Returns a list of the areas
		String sqlForAreas = "select home from employee group by home;";	
		List<String> listOfAreas = (List<String>) jdbcTemplate.queryForList(sqlForAreas, String.class);
		
		//Returns a list of employees
		List<Integer> listOfEmployeesByArea = new LinkedList<Integer>();
		
		
		Map<Integer,List> avalabiltyByEmployeeID = new HashMap<Integer,List>();
		
		int thursdayCount = gateCount.get("THUR");
		int fridayCount = gateCount.get("FRI");
		int saturdayCount = gateCount.get("SAT");
		int sundayCount = gateCount.get("SUN");
		int mondayCount = gateCount.get("MON");
		int tuesdayCount = gateCount.get("TUES");
		int wednesdayCount = gateCount.get("WED");
		
		int[] gateCounts = {thursdayCount,fridayCount,saturdayCount,
				sundayCount,mondayCount,tuesdayCount,wednesdayCount};
		System.out.print("Starting Generation \n");
		for(int posArea = 0; posArea < listOfAreas.size(); posArea++){
			listOfEmployeesByArea = new LinkedList(findEmpByArea(listOfAreas.get(posArea)));
			for(int posEmp = 0; posEmp < listOfEmployeesByArea.size(); posEmp++){
				avalabiltyByEmployeeID.put(listOfEmployeesByArea.get(posEmp),
				findAvalabiltyForEmployee(posEmp));	
				//System.out.print("findAvalabilty \n");
				//System.out.print(avalabiltyByEmployeeID.size()+ ": # of emp in area\n");
				Map<Integer,List> pdfGrid = new HashMap<Integer,List>();
			}		
			System.out.print("Sending to method Area: "+listOfAreas.get(posArea) +"\n");
			
			System.out.print(generateWorkGridByArea(avalabiltyByEmployeeID,gateCounts,listOfAreas.get(posArea))+"\n");
			avalabiltyByEmployeeID.clear();
		}
		
		
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
	public List findAvalabiltyForEmployee(Integer employeeID){
		List<String> avalabiltyByEmployeeID = new LinkedList<String>();	
		String sqlThursday = "Select avalabilty.Thursday from avalabilty where avalabilty.EmployeeID = " +employeeID+";";		
		avalabiltyByEmployeeID.addAll(jdbcTemplate.queryForList(sqlThursday, String.class));
		String sqlFriday = "Select avalabilty.Friday from avalabilty where avalabilty.EmployeeID = " +employeeID+";";		
		avalabiltyByEmployeeID.addAll(jdbcTemplate.queryForList(sqlFriday, String.class));
		String sqlSaturday = "Select avalabilty.Saturday from avalabilty where avalabilty.EmployeeID = " +employeeID+";";		
		avalabiltyByEmployeeID.addAll(jdbcTemplate.queryForList(sqlSaturday, String.class));
		String sqlSunday = "Select avalabilty.Sunday from avalabilty where avalabilty.EmployeeID = " +employeeID+";";		
		avalabiltyByEmployeeID.addAll(jdbcTemplate.queryForList(sqlSunday, String.class));
		String sqlMonday = "Select avalabilty.Monday from avalabilty where avalabilty.EmployeeID = " +employeeID+";";		
		avalabiltyByEmployeeID.addAll(jdbcTemplate.queryForList(sqlMonday, String.class));
		String sqlTuesday = "Select avalabilty.Tuesday from avalabilty where avalabilty.EmployeeID = " +employeeID+";";		
		avalabiltyByEmployeeID.addAll(jdbcTemplate.queryForList(sqlTuesday, String.class));
		String sqlWednesday = "Select avalabilty.Wednesday from avalabilty where avalabilty.EmployeeID = " +employeeID+";";		
		avalabiltyByEmployeeID.addAll(jdbcTemplate.queryForList(sqlWednesday, String.class));
	    
	    return avalabiltyByEmployeeID;
	}
	
	public Map generateWorkGridByArea(Map<Integer,List> avalabiltyByEmployeeID, int[] gateCounts,String area){
		ArrayList<Integer> arrayOfEmp = new ArrayList<Integer>(avalabiltyByEmployeeID.keySet());
		
		//The Map that will be returned. This will be setup with a grid of work
		Map<Integer,List> toReturn = new HashMap<Integer,List>();
		
		int i = 0;
		while(i < arrayOfEmp.size()){
			toReturn.put(arrayOfEmp.get(i),new ArrayList<String>(Arrays.asList("X","X","X","X","X","X","X")));

		i++;}
		
		
		// This will check area then process each day and area according needs
		if(area.equals("BAKE")){
			int pos = 0;
			boolean dayDone= false;
			for(int day = 0; day < 7;day++ ){
				dayDone = false;
				//Count for current day generating for
				int countForTheDay = gateCounts[day];
				if(countForTheDay <= 3000){
					//2open 2close 1 mid
					int randomRange = (arrayOfEmp.size());
					Deque<Integer> IDUsed = new ArrayDeque<Integer>();
					while(pos <= 4){
						int randomNum = (int)(Math.random() * randomRange);
							if(IDUsed.contains(randomNum)==false){
								IDUsed.push(randomNum);
								pos++;
							}}
					
					List<String> temp = new LinkedList<String>();
					temp=toReturn.get(arrayOfEmp.get(IDUsed.peek()));
					temp.set(day, "730");
					toReturn.put((arrayOfEmp.get(IDUsed.pop())), temp);
					
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "730");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "10");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));					
					temp.set(day, "11");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));					
					temp.set(day, "11");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
			
					pos = 0;
					dayDone = true;
				}else if(countForTheDay > 3000 && dayDone != true){
					//2open 2close 2 mid
					int randomRange = (arrayOfEmp.size());
					Deque<Integer> IDUsed = new ArrayDeque<Integer>();
					while(pos <= 5){
						int randomNum = (int)(Math.random() * randomRange);
							if(IDUsed.contains(randomNum)==false){
								IDUsed.push(randomNum);
								pos++;
							}}
					List<String> temp = new LinkedList<String>();
					temp=toReturn.get(arrayOfEmp.get(IDUsed.peek()));
					temp.set(day, "730");
					toReturn.put((arrayOfEmp.get(IDUsed.pop())), temp);
					
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "730");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "10");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "10");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));					
					temp.set(day, "11");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));					
					temp.set(day, "11");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					pos = 0;
					dayDone = true;
				}
				}//for
			//System.out.print("Bake: "+toReturn.toString()+"\n");
		}//bake
		if(area.equals("CYM")){
			int pos = 0;
			boolean dayDone= false;
			for(int day = 0; day < 7;day++ ){
				dayDone=false;
					//Count for current day generating for
				int countForTheDay = gateCounts[day];
				if(countForTheDay <= 1000){
					//3 All Day
					int randomRange = (arrayOfEmp.size());
					Deque<Integer> IDUsed = new ArrayDeque<Integer>();
					while(pos <= 2){
						int randomNum = (int)(Math.random() * randomRange);
							if(IDUsed.contains(randomNum)==false){
								IDUsed.push(randomNum);
								pos++;
							}}
					List<String> temp = new LinkedList<String>();
					temp=toReturn.get(arrayOfEmp.get(IDUsed.peek()));
					temp.set(day, "9");
					toReturn.put((arrayOfEmp.get(IDUsed.pop())), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "9");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "10");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					pos = 0;
					dayDone=true;
				}else if(countForTheDay <= 3000 && dayDone != true){
					//4 All Day
					int randomRange = (arrayOfEmp.size());
					Deque<Integer> IDUsed = new ArrayDeque<Integer>();
					while(pos <= 3){
						int randomNum = (int)(Math.random() * randomRange);
							if(IDUsed.contains(randomNum)==false){
								IDUsed.push(randomNum);
								pos++;
							}}
					List<String> temp = new LinkedList<String>();
					temp=toReturn.get(arrayOfEmp.get(IDUsed.peek()));
					temp.set(day, "9");
					toReturn.put((arrayOfEmp.get(IDUsed.pop())), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "9");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "10");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "10");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					pos = 0;
					dayDone=true;
				}else if((countForTheDay <= 4000 || countForTheDay >4000) && dayDone!=true){
					//4 All Day
					int randomRange = (arrayOfEmp.size());
					Deque<Integer> IDUsed = new ArrayDeque<Integer>();
					while(pos <= 4){
						int randomNum = (int)(Math.random() * randomRange);
							if(IDUsed.contains(randomNum)==false){
								IDUsed.push(randomNum);
								pos++;
							}}
					List<String> temp = new LinkedList<String>();
					temp=toReturn.get(arrayOfEmp.get(IDUsed.peek()));
					temp.set(day, "9");
					toReturn.put((arrayOfEmp.get(IDUsed.pop())), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "9");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "9");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "10");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "10");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					pos = 0;
					dayDone=true;
				}
			}
			//System.out.print("CYM: "+toReturn.toString()+"\n");
			return toReturn;
		}
		if(area.equals("ICP")){
			int pos = 0;
			boolean dayDone = false;
			for(int day = 0; day < 7;day++ ){
				dayDone=false;
					//Count for current day generating for
				int countForTheDay = gateCounts[day];
				if(countForTheDay <= 2000 && dayDone != true){
					//3 All Day
					int randomRange = (arrayOfEmp.size());
					Deque<Integer> IDUsed = new ArrayDeque<Integer>();
					while(pos <= 2){
						int randomNum = (int)(Math.random() * randomRange);
							if(IDUsed.contains(randomNum)==false){
								IDUsed.push(randomNum);
								pos++;
							}}
					List<String> temp = new LinkedList<String>();
					temp=toReturn.get(arrayOfEmp.get(IDUsed.peek()));
					temp.set(day, "9");
					toReturn.put((arrayOfEmp.get(IDUsed.pop())), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "9");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "11");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					pos = 0;
					dayDone=true;
				}else if(countForTheDay <= 3000 && dayDone != true){
					//4 All Day
					int randomRange = (arrayOfEmp.size());
					Deque<Integer> IDUsed = new ArrayDeque<Integer>();
					while(pos <= 3){
						int randomNum = (int)(Math.random() * randomRange);
							if(IDUsed.contains(randomNum)==false){
								IDUsed.push(randomNum);
								pos++;
							}}
					List<String> temp = new LinkedList<String>();
					temp=toReturn.get(arrayOfEmp.get(IDUsed.peek()));
					temp.set(day, "9");
					toReturn.put((arrayOfEmp.get(IDUsed.pop())), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "9");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "11");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "11");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					pos = 0;
					dayDone=true;
				}else if(countForTheDay >= 5000 && dayDone != true){
					//5 All Day
					int randomRange = (arrayOfEmp.size());
					Deque<Integer> IDUsed = new ArrayDeque<Integer>();
					while(pos <= 4){
						int randomNum = (int)(Math.random() * randomRange);
							if(IDUsed.contains(randomNum)==false){
								IDUsed.push(randomNum);
								pos++;
							}}
					List<String> temp = new LinkedList<String>();
					temp=toReturn.get(arrayOfEmp.get(IDUsed.peek()));
					temp.set(day, "9");
					toReturn.put((arrayOfEmp.get(IDUsed.pop())), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "9");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "10");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "11");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					temp = toReturn.get(arrayOfEmp.get((IDUsed.peek())));
					temp.set(day, "11");
					toReturn.put(arrayOfEmp.get(IDUsed.pop()), temp);
					
					pos = 0;
					dayDone=true;
				}else {
					
				}
			}
			//System.out.print("ICP: " +toReturn.toString()+"\n");
			return toReturn;
		}
		if(area.equals("KIT")){
				int sizeOfArray = arrayOfEmp.size() * 2;
				Deque<Integer> daysOff = new ArrayDeque<Integer>();
				int randomRange = sizeOfArray;
				
				for(int iterate = 0;iterate <sizeOfArray;iterate++){
					int randomNum = (int)(Math.random() * randomRange);
					if(daysOff.contains(randomNum)!= true){
						daysOff.push(randomNum);
					}else{
						while(daysOff.contains(randomNum)){
						 randomNum = (int)(Math.random() * randomRange);
						}//while
						daysOff.push(randomNum);
					}//else
				}//for
				Map<Integer,ArrayList<Integer>> mapOfOffTime = new HashMap<Integer, ArrayList<Integer>>();
				
				for(int iter = 0; iter < arrayOfEmp.size(); iter++){
					
					mapOfOffTime.put(arrayOfEmp.get(iter), 
							new ArrayList<Integer>(Arrays.asList(daysOff.pop(),daysOff.pop())));
				}
				ArrayList<Integer> mapOffID = new ArrayList<Integer>(mapOfOffTime.keySet());
				
				for(int iterate = 0; iterate<7;iterate++){
					if(iterate!=1 && iterate!=5){						
						if(iterate == 1 || iterate == 5)System.out.print(" Truck Day\n");

						int randomEmp = (int)(Math.random() * arrayOfEmp.size());
						
						if(mapOfOffTime.get(mapOffID.get(randomEmp)).contains(iterate) == false){
							List<String> temp = new LinkedList<String>();
							temp=toReturn.get(mapOffID.get(randomEmp));
							temp.set(iterate, "730");
							toReturn.put(mapOffID.get(randomEmp), temp);
						}else{
							while(mapOfOffTime.get(mapOffID.get(randomEmp)).contains(iterate)){
								randomEmp = (int)(Math.random() * mapOffID.size());
							}
							List<String> temp = new LinkedList<String>();
							temp=toReturn.get(mapOffID.get(randomEmp));
							temp.set(iterate, "730");
							toReturn.put(mapOffID.get(randomEmp), temp);
						}
							for(int x = 0; x < mapOffID.size();x++){
								if( mapOffID.get(x)!=(mapOffID.get(randomEmp)) && !mapOfOffTime.get(mapOffID.get(x)).contains(iterate)){
									List<String> temp = new LinkedList<String>();
									temp=toReturn.get(mapOffID.get(x));
									temp.set(iterate, "830");
									toReturn.put(mapOffID.get(x), temp);
								}//if
							}//for
					}else{
						int randomEmp = (int)(Math.random() * mapOffID.size());
						if(mapOfOffTime.get(mapOffID.get(randomEmp)).contains(iterate) == false){
							List<String> temp = new LinkedList<String>();
							temp=toReturn.get(mapOffID.get(randomEmp));
							temp.set(iterate, "630");
							toReturn.put(mapOffID.get(randomEmp), temp);
						}else{
							while(mapOfOffTime.get(mapOffID.get(randomEmp)).contains(iterate)){
								randomEmp = (int)(Math.random() * mapOffID.size());
							}
							List<String> temp = new LinkedList<String>();
							temp=toReturn.get(mapOffID.get(randomEmp));
							temp.set(iterate, "630");
							toReturn.put(mapOffID.get(randomEmp), temp);
						}
							for(int x = 0; x < mapOffID.size();x++){
								if(mapOffID.get(x)!=(mapOffID.get(randomEmp))  && !mapOfOffTime.get(mapOffID.get(x)).contains(iterate)){
									List<String> temp = new LinkedList<String>();
									temp=toReturn.get(mapOffID.get(x));
									temp.set(iterate, "830");
									toReturn.put(mapOffID.get(x), temp);
								}
							}
					}
				}
				
			
			//System.out.print("Kit: "+toReturn.toString()+"\n");
			return toReturn;
		}//kit
		return toReturn;
	}
}
 
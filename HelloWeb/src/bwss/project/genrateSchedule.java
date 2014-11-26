package bwss.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.io.IOUtils;
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
	private Map<Integer,List> bakeGenMap = new HashMap<Integer,List>();
	private Map<Integer,List> cymGenMap = new HashMap<Integer,List>();
	private Map<Integer,List> icpGenMap = new HashMap<Integer,List>();
	private Map<Integer,List> kitGenMap = new HashMap<Integer,List>();
	
	private int[] currCell = {0,3};
	
	public void setDataSource(DataSource dataSource) {
		this.datasource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		}
	
	@RequestMapping(value= "/generateDownloadView")
	 public String addView(
			 @ModelAttribute("gateCount")Map<String,Integer> gateCount,
			 @ModelAttribute("jdbcTemplate") JdbcTemplate jdbcTemplate,
			 ModelMap model) {
		EmployeeLogin emp = (EmployeeLogin) model.get("employee");
		 if(emp == null){
			return "redirect:/";
		 }
		System.out.print("Starting Generates view \n");
		this.gateCount = gateCount;
		//setDataSource(jdbcTemplate);
		
		//Returns a list of the areas
		String sqlForAreas = "select home from employee group by home;";	
		List<String> listOfAreas = new ArrayList<String>();
			listOfAreas.add("BAKE");
			listOfAreas.add("CYM");
			listOfAreas.add("ICP");
			listOfAreas.add("KIT");
		
		//Arrays.asList("BAKE","CYM","ICP","KIT");
		
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
				findAvalabiltyForEmployee(listOfEmployeesByArea.get(posEmp)));	
				//System.out.print("findAvalabilty \n");
				//System.out.print(avalabiltyByEmployeeID.size()+ ": # of emp in area\n");
				Map<Integer,List> pdfGrid = new HashMap<Integer,List>();
			}
			System.out.print(avalabiltyByEmployeeID+"\n");
			//System.out.print(generateWorkGridByArea(avalabiltyByEmployeeID,gateCounts,listOfAreas.get(posArea))+"\n");
			if(listOfAreas.get(posArea).equals("BAKE"))bakeGenMap = generateWorkGridByArea(avalabiltyByEmployeeID,gateCounts,listOfAreas.get(posArea));
			if(listOfAreas.get(posArea).equals("CYM"))cymGenMap = generateWorkGridByArea(avalabiltyByEmployeeID,gateCounts,listOfAreas.get(posArea));
			if(listOfAreas.get(posArea).equals("ICP"))icpGenMap = generateWorkGridByArea(avalabiltyByEmployeeID,gateCounts,listOfAreas.get(posArea));
			if(listOfAreas.get(posArea).equals("KIT"))kitGenMap = generateWorkGridByArea(avalabiltyByEmployeeID,gateCounts,listOfAreas.get(posArea));System.out.print(avalabiltyByEmployeeID+"\n");
			avalabiltyByEmployeeID.clear();
			
		}
		System.out.print("BAKE: " + bakeGenMap+"\n");
		System.out.print("CYM: " + cymGenMap+"\n");
		System.out.print("ICP: " + icpGenMap+"\n");
		System.out.print("KIT: " + kitGenMap+"\n");
		return "generateDownload";
	   }
	
	@RequestMapping(value= "/TestConnection")
	public List findEmpByArea(String homeLoc){
		 
		String sql = "Select EmployeeID from employee where employee.home = ? and enabled=\"Y\";";		
	    List<Integer> empIDByHome = new LinkedList<Integer>();		
	    
	    empIDByHome = jdbcTemplate.queryForList(sql,new Object[]{homeLoc}, Integer.class);
		return empIDByHome;
	}
	public void setupVarsForGenration(){
	}
	public List findAvalabiltyForEmployee(Integer employeeID){
		List<String> avalabiltyByEmployeeID = new LinkedList<String>();	
		String sqlThursday = "Select avalabilty.Thursday from avalabilty where avalabilty.EmployeeID = ? ;";		
		avalabiltyByEmployeeID.addAll(jdbcTemplate.queryForList(sqlThursday,new Object[]{employeeID}, String.class));
		
		String sqlFriday = "Select avalabilty.Friday from avalabilty where avalabilty.EmployeeID = ?;";		
		avalabiltyByEmployeeID.addAll(jdbcTemplate.queryForList(sqlFriday,new Object[]{employeeID}, String.class));
		
		String sqlSaturday = "Select avalabilty.Saturday from avalabilty where avalabilty.EmployeeID = ?;";		
		avalabiltyByEmployeeID.addAll(jdbcTemplate.queryForList(sqlSaturday,new Object[]{employeeID}, String.class));
		
		String sqlSunday = "Select avalabilty.Sunday from avalabilty where avalabilty.EmployeeID = ?;";		
		avalabiltyByEmployeeID.addAll(jdbcTemplate.queryForList(sqlSunday,new Object[]{employeeID}, String.class));
		
		String sqlMonday = "Select avalabilty.Monday from avalabilty where avalabilty.EmployeeID = ?;";		
		avalabiltyByEmployeeID.addAll(jdbcTemplate.queryForList(sqlMonday,new Object[]{employeeID}, String.class));
		
		String sqlTuesday = "Select avalabilty.Tuesday from avalabilty where avalabilty.EmployeeID = ?;";		
		avalabiltyByEmployeeID.addAll(jdbcTemplate.queryForList(sqlTuesday,new Object[]{employeeID}, String.class));
		
		String sqlWednesday = "Select avalabilty.Wednesday from avalabilty where avalabilty.EmployeeID = ?;";		
		avalabiltyByEmployeeID.addAll(jdbcTemplate.queryForList(sqlWednesday,new Object[]{employeeID}, String.class));
	    
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
							if(IDUsed.contains(randomNum)==false && avalabiltyByEmployeeID.get(arrayOfEmp.get(randomNum)).get(day).equals("Y") ){
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
							if(IDUsed.contains(randomNum)==false && avalabiltyByEmployeeID.get(arrayOfEmp.get(randomNum)).get(day).equals("Y") ){
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
							if(IDUsed.contains(randomNum)==false && avalabiltyByEmployeeID.get(arrayOfEmp.get(randomNum)).get(day).equals("Y") ){
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
							if(IDUsed.contains(randomNum)==false && avalabiltyByEmployeeID.get(arrayOfEmp.get(randomNum)).get(day).equals("Y") ){
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
							if(IDUsed.contains(randomNum)==false && avalabiltyByEmployeeID.get(arrayOfEmp.get(randomNum)).get(day).equals("Y") ){
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
							if(IDUsed.contains(randomNum)==false&& avalabiltyByEmployeeID.get(arrayOfEmp.get(randomNum)).get(day).equals("Y") ){
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
							if(IDUsed.contains(randomNum)==false&& avalabiltyByEmployeeID.get(arrayOfEmp.get(randomNum)).get(day).equals("Y") ){
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
				}else if((countForTheDay <= 5000&& dayDone != true) || (countForTheDay > 4000 && dayDone != true)){
					//5 All Day
					int randomRange = (arrayOfEmp.size());
					Deque<Integer> IDUsed = new ArrayDeque<Integer>();
					while(pos <= 4){
						int randomNum = (int)(Math.random() * randomRange);
							if(IDUsed.contains(randomNum)==false && avalabiltyByEmployeeID.get(arrayOfEmp.get(randomNum)).get(day).equals("Y") ){
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

						int randomEmp = (int)(Math.random() * arrayOfEmp.size());
																											//change
						if(mapOfOffTime.get(mapOffID.get(randomEmp)).contains(iterate) == false && avalabiltyByEmployeeID.get(mapOffID.get(randomEmp)).get(iterate).equals("Y") ){
							List<String> temp = new LinkedList<String>();
							temp=toReturn.get(mapOffID.get(randomEmp));
							temp.set(iterate, "730");
							toReturn.put(mapOffID.get(randomEmp), temp);
						}else{																		//change
							while(mapOfOffTime.get(mapOffID.get(randomEmp)).contains(iterate)|| avalabiltyByEmployeeID.get(mapOffID.get(randomEmp)).get(iterate).equals("N")){
								randomEmp = (int)(Math.random() * mapOffID.size());
							}
							List<String> temp = new LinkedList<String>();
							temp=toReturn.get(mapOffID.get(randomEmp));
							temp.set(iterate, "730");
							toReturn.put(mapOffID.get(randomEmp), temp);
						}
							for(int x = 0; x < mapOffID.size();x++){
								if( mapOffID.get(x)!=(mapOffID.get(randomEmp)) && !mapOfOffTime.get(mapOffID.get(x)).contains(iterate) && 
										avalabiltyByEmployeeID.get(mapOffID.get(x)).get(iterate).equals("Y")){
									List<String> temp = new LinkedList<String>();
									temp=toReturn.get(mapOffID.get(x));
									temp.set(iterate, "830");
									toReturn.put(mapOffID.get(x), temp);
								}//if
							}//for
					}else{
						int randomEmp = (int)(Math.random() * mapOffID.size());
						if(mapOfOffTime.get(mapOffID.get(randomEmp)).contains(iterate) == false && avalabiltyByEmployeeID.get(mapOffID.get(randomEmp)).get(iterate).equals("Y")){
							List<String> temp = new LinkedList<String>();
							temp=toReturn.get(mapOffID.get(randomEmp));
							temp.set(iterate, "630");
							toReturn.put(mapOffID.get(randomEmp), temp);
						}else{
							while(mapOfOffTime.get(mapOffID.get(randomEmp)).contains(iterate)|| avalabiltyByEmployeeID.get(mapOffID.get(randomEmp)).get(iterate).equals("N")){
								randomEmp = (int)(Math.random() * mapOffID.size());
							}
							List<String> temp = new LinkedList<String>();
							temp=toReturn.get(mapOffID.get(randomEmp));
							temp.set(iterate, "630");
							toReturn.put(mapOffID.get(randomEmp), temp);
						}
							for(int x = 0; x < mapOffID.size();x++){
								if(mapOffID.get(x)!=(mapOffID.get(randomEmp))  && !mapOfOffTime.get(mapOffID.get(x)).contains(iterate)&& 
										avalabiltyByEmployeeID.get(mapOffID.get(x)).get(iterate).equals("Y")){						
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

	@RequestMapping(value="/downloadSchedule")
	public void createGeneratedWorkbook(HttpSession session,HttpServletResponse response) throws IOException, Exception, WriteException{
		List<EmployeeLogin> employeesBake = findAllOfLoc("BAKE");
		List<EmployeeLogin> employeesCYM	= findAllOfLoc("CYM");
		List<EmployeeLogin> employeesICP	= findAllOfLoc("ICP");
		List<EmployeeLogin> employeesKIT	= findAllOfLoc("KIT");
		
		WritableWorkbook wworkbook;
	      wworkbook = Workbook.createWorkbook(new File("C:/schedule/generatedOutput.xls"));
	      WritableSheet wsheet = wworkbook.createSheet("Generated-Schedule", 0);
	      
	      Label labelDate = new Label(0, 0, "DATE");
          wsheet.addCell(labelDate);
          
          Label labelThur = new Label(1, 0, "Thursday");
          wsheet.addCell(labelThur);
          Label labelFri = new Label(2, 0, "Friday");
          wsheet.addCell(labelFri);
          Label labelSat = new Label(3, 0, "Saturday");
          wsheet.addCell(labelSat);
          Label labelSun = new Label(4, 0, "Sunday");
          wsheet.addCell(labelSun);
          Label labelMon = new Label(5, 0, "Monday");
          wsheet.addCell(labelMon);
          Label labelTues = new Label(6, 0, "Tuesday");
          wsheet.addCell(labelTues);
          Label labelWed= new Label(7, 0, "Wednesday");
          wsheet.addCell(labelWed);
          
          Label labelCount = new Label(0, 1, "Gate Count");
          wsheet.addCell(labelCount);
          Label labelCountTHUR = new Label(1, 1, gateCount.get("THUR").toString());
          wsheet.addCell(labelCountTHUR);
          Label labelCountFRI = new Label(2, 1, gateCount.get("FRI").toString());
          wsheet.addCell(labelCountFRI);
          Label labelCountSAT = new Label(3, 1, gateCount.get("SAT").toString());
          wsheet.addCell(labelCountSAT);
          Label labelCountSUN = new Label(4, 1, gateCount.get("SUN").toString());
          wsheet.addCell(labelCountSUN);
          Label labelCountMON = new Label(5, 1, gateCount.get("MON").toString());
          wsheet.addCell(labelCountMON);
          Label labelCountTUES = new Label(6, 1, gateCount.get("TUES").toString());
          wsheet.addCell(labelCountTUES);
          Label labelCountWED = new Label(7, 1, gateCount.get("WED").toString());
          wsheet.addCell(labelCountWED);
          
	      Label labelBake = new Label(0, currCell[1], "VBS");
          wsheet.addCell(labelBake);
          currCell[1] = currCell[1] +1;
          
	      for(int y = 0; y < employeesBake.size(); y++){
	    		  if(bakeGenMap.containsKey(employeesBake.get(y).getId())){
	    			  Label label = new Label(0, currCell[1], employeesBake.get(y).getFirst());
	    			  wsheet.addCell(label);
	    			  
	    			  for(int x = 1; x <= 7; x++){
	    			  Label labelWorkDay = new Label(x, currCell[1], bakeGenMap.get(employeesBake.get(y).getId()).get(x-1).toString());
	    			  wsheet.addCell(labelWorkDay);
	    			  }
	    			  currCell[1] = currCell[1] + 1;
	    		  
	    	  } 
	      }
	      currCell[1] = currCell[1] +1;
	      Label labelCym = new Label(0, currCell[1], "CYM");
          wsheet.addCell(labelCym);
          currCell[1] = currCell[1] +1;
	      
	      for(int y = 0; y < employeesCYM.size(); y++){
    		  if(cymGenMap.containsKey(employeesCYM.get(y).getId())){
    			  Label label = new Label(0, currCell[1], employeesCYM.get(y).getFirst());
    			  wsheet.addCell(label);
    			  
    			  for(int x = 1; x <= 7; x++){
    			  Label labelWorkDay = new Label(x, currCell[1], cymGenMap.get(employeesCYM.get(y).getId()).get(x-1).toString());
    			  wsheet.addCell(labelWorkDay);
    			  }
    			  currCell[1] = currCell[1] + 1;
    		  
    	  } 
      }
	      currCell[1] = currCell[1] +1;
	      Label labelIcp = new Label(0, currCell[1], "ICP");
          wsheet.addCell(labelIcp);
          currCell[1] = currCell[1] +1;
          
	      for(int y = 0; y < employeesICP.size(); y++){
    		  if(icpGenMap.containsKey(employeesICP.get(y).getId())){
    			  Label label = new Label(0, currCell[1], employeesICP.get(y).getFirst());
    			  wsheet.addCell(label);
    			  
    			  for(int x = 1; x <= 7; x++){
    			  Label labelWorkDay = new Label(x, currCell[1], icpGenMap.get(employeesICP.get(y).getId()).get(x-1).toString());
    			  wsheet.addCell(labelWorkDay);
    			  }
    			  currCell[1] = currCell[1] + 1;
    		  
    	  } 
      }
	      currCell[1] = currCell[1] +1;
	      Label labelKit = new Label(0, currCell[1], "KIT");
          wsheet.addCell(labelKit);
          currCell[1] = currCell[1] +1;
          
	      for(int y = 0; y < employeesKIT.size(); y++){
    		  if(kitGenMap.containsKey(employeesKIT.get(y).getId())){
    			  Label label = new Label(0, currCell[1], employeesKIT.get(y).getFirst());
    			  wsheet.addCell(label);
    			  
    			  for(int x = 1; x <= 7; x++){
    			  Label labelWorkDay = new Label(x, currCell[1], kitGenMap.get(employeesKIT.get(y).getId()).get(x-1).toString());
    			  wsheet.addCell(labelWorkDay);
    			  }
    			  currCell[1] = currCell[1] + 1;
    		  
    	  } 
      }
	      currCell[1] = currCell[1] +1;
	      
          wworkbook.write();
	      wworkbook.close();
	      InputStream inputStream =new FileInputStream("C:/schedule/generatedOutput.xls");
	      response.setContentType("application/force-download");
          response.setHeader("Content-Disposition", "attachment; filename= "+"C:/schedule/generatedOutput.xls");
          IOUtils.copy(inputStream, response.getOutputStream());
	}
	public List<EmployeeLogin> findAllOfLoc(String homeLoc){
		 
		String sql = "SELECT employee.* " +
				"FROM  bwss.employee " +
				"where bwss.employee.home=?;";
	 
		List<EmployeeLogin> Employees = new ArrayList<EmployeeLogin>();
	 
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[]{homeLoc});
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
	

}

 
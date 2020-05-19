package com.copa.RESscripts;

import java.util.Set;
import java.util.logging.Level;

import com.copa.Util.url_path;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;

/**
  *
  * Java program to compare two XML files using XMLUnit example

  * @author Javin Paul
  */
public class demo {
	
	@Test
	public void headless(){
		
		
		String ssss="(123)";
		if(ssss.contains("("))
			System.out.println("yes");
		else
			System.out.println("No");

	}
	
	
	
	public void bagcal() throws ParseException, IOException{
		
	
	/*	double Bag_BalanceAMT=Double.parseDouble("0");
		double Bagcalucate_SUM=275;
		double diffAmt=Bag_BalanceAMT-Bagcalucate_SUM;
		System.out.println(diffAmt);
		if(diffAmt<100 && diffAmt>0)
			System.out.println("less than 100");
		else
			System.out.println("grater than 100");
		if(true)
			return;*/
		/*String Category="FFP_PAX_Only_Silver";
		  List<Integer> Bag_wait = new ArrayList<>();
		 
		  List<String> Bag_overSize = new ArrayList<>(); 
		 // Bag_wait.add(45);
		  Bag_overSize.add("No");Bag_overSize.add("No");  Bag_overSize.add("No");  Bag_overSize.add("yes"); 
		//  Bag_wait.add(23);Bag_wait.add(30);Bag_wait.add(23);Bag_wait.add(19);Bag_wait.add(18);
		//  Bag_wait.add(23);Bag_wait.add(23);Bag_wait.add(32);Bag_wait.add(33);
		  Bag_wait.add(23);Bag_wait.add(23);Bag_wait.add(32);Bag_wait.add(32);
		  List<String> Bag_value = new ArrayList<>(Bag_overSize);
		  
		 // Bag_wait.add(18);Bag_wait.add(19);Bag_wait.add(21);Bag_wait.add(22);Bag_wait.add(23);
		//  Bag_wait.add(23); Bag_wait.add(33);
		  //Bag_wait.add(34); Bag_wait.add(33);Bag_wait.add(34);
	//	  Bag_wait.add(23); Bag_wait.add(33);Bag_wait.add(33);
		//  Bag_wait.add(21); Bag_wait.add(21);
		 // Bag_wait.add(18); Bag_wait.add(23);Bag_wait.add(18);Bag_wait.add(19);Bag_wait.add(19);
		 // Bag_wait.add(1);Bag_wait.add(2);Bag_wait.add(3);Bag_wait.add(4);Bag_wait.add(5);Bag_wait.add(6);Bag_wait.add(7);
		 // Bag_wait.add(32); Bag_wait.add(32); Bag_wait.add(1);
		// Bag_wait.add(24); Bag_wait.add(24);Bag_wait.add(30);Bag_wait.add(19);Bag_wait.add(24);
		
		  bagcal(Bag_wait, Bag_value, Bag_overSize, Category);
			/////////////////////////////////////////////
			System.out.println();
			System.out.println("--------------------------------------");
			System.out.print("Delete Bagg process started ::");
			// Delete Bag
			  List<Integer> Delete_Bag_wait = new ArrayList<>(Bag_wait);
			  List<String> Delete_Bag_value = new ArrayList<>(Bag_value);
			  
			  List<String> Paid_Bag_Delete_value = new ArrayList<>();
			  
			  List<String> Paid_Bag_Temp = new ArrayList<>();
			  
			String delete_Bag_Index="3-3";
			String New_Bag_Asber_Index="23!os-32!os";
			
			String morethan_OneTime_Delete="yes";
			//System.out.println(delete_Bag_Index);
			String[] morethan_OneTime_Delete_arrydelete_Bag_Index = delete_Bag_Index.split("-");
			String[] morethan_OneTime_Delete_arry_New_Bag_Asber_Index = New_Bag_Asber_Index.split("-");
			
			for (int i = 0; i < morethan_OneTime_Delete_arrydelete_Bag_Index.length; i++) {
				
				// write return to check in---
				// click on Bag icon
				// Delete Bag as per bag tag number(store bag tag in ARRAY)
				
				System.out.println("");
				System.out.println(" morethan_OneTime_Delete :"+i+"");
				//System.out.println(delete_Bag_Index);
				String[] arrydelete_Bag_Index = morethan_OneTime_Delete_arrydelete_Bag_Index[i].split(";");
				String[] arry_New_Bag_Asber_Index = morethan_OneTime_Delete_arry_New_Bag_Asber_Index[i].split(";");
				
				List<Integer>int_arrydelete_Bag_Index= new ArrayList<>(); 
				for(String s : arrydelete_Bag_Index) int_arrydelete_Bag_Index.add(Integer.valueOf(s));
				
				for (int Dle = 0; Dle < arrydelete_Bag_Index.length; Dle++) {
					
					int Delete_Array_idex=Integer.parseInt(arrydelete_Bag_Index[Dle]);
					// over size bag........
					String bag_wat_After_del=arry_New_Bag_Asber_Index[Dle];
					String Oversize="";
					if (bag_wat_After_del.contains("OS")) {
						bag_wat_After_del = bag_wat_After_del.replace("!OS", "");
						Oversize="Y";
					}
					// over size bag........
					int new_Bag_wait=Integer.parseInt(bag_wat_After_del);
					
					// need to add New_Bag_wait ....
					
					if(Delete_Array_idex<(Delete_Bag_wait.size()-1))
	
						for (int index = 0; index < Delete_Bag_wait.size(); index++) {
							if(Delete_Array_idex==index){
								Delete_Bag_wait.set(index, new_Bag_wait);
								if(i> 0)
									Paid_Bag_Delete_value.add(Paid_Bag_Temp.get(index));
								else
									Paid_Bag_Delete_value.add(Delete_Bag_value.get(index));
								Delete_Bag_value.set(index, "kishore");
							}
							
						}
					else{
						Delete_Bag_wait.add( new_Bag_wait);
						Delete_Bag_value.add("kishore");
						Bag_overSize.add("No");
					}
						
			
				}
				// Delete Bag
				
				
				for(int p=0;p<Delete_Bag_wait.size();p++){
					System.out.print(Delete_Bag_wait.get(p)+"   ");
					
				}
				System.out.println();
				for(int p=0;p<Delete_Bag_value.size();p++){
					System.out.print(Delete_Bag_value.get(p)+" ");
				}
				System.out.println();
				System.out.println("--------------------------------------");
				System.out.println("Paid array");
				for(int p=0;p<Paid_Bag_Delete_value.size();p++){
					System.out.print(Paid_Bag_Delete_value.get(p)+" ");
				}
				
				System.out.println();
				System.out.println("--------------------------------------");
				System.out.println("After Delete check bag amt");
				
				 bagcal(Delete_Bag_wait, Delete_Bag_value, Bag_overSize, Category);
				 
				 Paid_Bag_Temp = new ArrayList<>(Delete_Bag_value);
				 
				 System.out.println();
				System.out.println("--------------------------------------");
				System.out.println("Added bag vs paid bag");
				 
				for (int Dle = 0; Dle < arrydelete_Bag_Index.length; Dle++) {
					
					int Delete_Array_idex=Integer.parseInt(arrydelete_Bag_Index[Dle]);
					//int new_Bag_wait=Integer.parseInt(arry_New_Bag_Asber_Index[Dle]);
					
	//				if(arrydelete_Bag_possition(p))
					for (int index = 0; index < Delete_Bag_wait.size(); index++) {
						String Added_Bag_valu=Delete_Bag_value.get(index);
						if(Delete_Array_idex==index && (!Added_Bag_valu.equalsIgnoreCase("free"))){
							Delete_Bag_wait.set(index, new_Bag_wait);
							Delete_Bag_value.set(index, "kishore");
							int int_Added_Bag_valu=Integer.parseInt(Added_Bag_valu);
							if(Paid_Bag_Delete_value.contains(Added_Bag_valu)){
								int getidex=Paid_Bag_Delete_value.indexOf(Added_Bag_valu);
								Delete_Bag_value.set(index, "PAID");
								Paid_Bag_Delete_value.set(getidex, "0");
								
							}
													
						}
						else if(Dle == 0 && (!Delete_Bag_value.get(index).equalsIgnoreCase("free")) && (!int_arrydelete_Bag_Index.contains(index))){
							Delete_Bag_value.set(index, "Done");
						}
						
					}	
			
				}
				 System.out.println();
				for(int p=0;p<Delete_Bag_wait.size();p++){
					System.out.print(Delete_Bag_wait.get(p)+"   ");
					
				}
				System.out.println();
				for(int p=0;p<Delete_Bag_value.size();p++){
					System.out.print(Delete_Bag_value.get(p)+" ");
				}
				
				// need to do validation of amount  .....
			
			}
		}  //Business_Classd
		
		
	

	public static String envRead_Bagg(int row,int column) throws IOException{

		String FilePath = url_path.pEnvExcelPath;

		FileInputStream input=new FileInputStream(FilePath);
		XSSFWorkbook wb=new XSSFWorkbook(input);
		XSSFSheet sh=wb.getSheet("Bagcost");
		XSSFCell c=sh.getRow(row).getCell(column);
		DataFormatter format = new DataFormatter();

		input.close();
		return format.formatCellValue(c);

	}
	 
	public void bagcal(List<Integer> Bag_wait,List<String> Bag_value,List<String> Bag_overSize,String Category ){
		if(Category.equalsIgnoreCase("FFP_PAX_Only_Silver")){
			int free_BagCount=1;
			for(int s=0;s<Bag_wait.size() ; s++){

				if(((Bag_wait.get(s)>0) && (Bag_wait.get(s)<24)) && (free_BagCount <= 2)){   // category check for 24 to 33  means free
					if(Bag_overSize.get(s).equalsIgnoreCase("No"))
						//Bag_value.add("free");
						Bag_value.set(s,"free");
					else
						Bag_value.set( s,"150");
						//Bag_value.add("150");
					free_BagCount=free_BagCount+1;
				}else
					Bag_value.set(s,"kishore");	
			}
			
			
			if(free_BagCount<= 2  ){
				for(int s=0;s<Bag_wait.size() ; s++){
					if(free_BagCount< 3  ){
						if((Bag_wait.get(s)> 23) && (Bag_wait.get(s)<33)){
							if(Bag_overSize.get(s).equalsIgnoreCase("No"))
								Bag_value.set(s, "100");
							else
								Bag_value.set(s, "250");
							free_BagCount=free_BagCount+1;
						}
						if((Bag_wait.get(s)> 32) && Bag_wait.get(s)<46 ){
							if(Bag_overSize.get(s).equalsIgnoreCase("No"))
								Bag_value.set(s, "200");
							else
								Bag_value.set(s, "350");
							free_BagCount=free_BagCount+1;
						}
					}
				}
			}
			
			
			
			for(int p=0;p<Bag_value.size();p++){
				
				if(Bag_value.get(p).equalsIgnoreCase("kishore") ){
					if((Bag_wait.get(p)< 24))
						if(Bag_overSize.get(p).equalsIgnoreCase("No"))
							Bag_value.set(p, "175");
						else
							Bag_value.set(p, "325");
					if((Bag_wait.get(p)> 23) && (Bag_wait.get(p)<33))
						if(Bag_overSize.get(p).equalsIgnoreCase("No"))
							Bag_value.set(p, "275");
						else
							Bag_value.set(p, "425");
					if((Bag_wait.get(p)> 32) && Bag_wait.get(p)<46 )
						if(Bag_overSize.get(p).equalsIgnoreCase("No"))
							Bag_value.set(p, "375");
						else
							Bag_value.set(p, "525");
				}
	
			}
				
			for(int p=0;p<Bag_wait.size();p++){
				System.out.print(Bag_wait.get(p)+"   ");
				
			}
			System.out.println();
			for(int p=0;p<Bag_value.size();p++){
				System.out.print(Bag_value.get(p)+" ");
			}
			
			
			
			
		}*/
	}
}
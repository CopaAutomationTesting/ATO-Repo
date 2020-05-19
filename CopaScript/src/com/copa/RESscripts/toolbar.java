package com.copa.RESscripts;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.Status;

import FrameworkCode.BFrameworkQueryObjects;

public class toolbar {
	public static String pnrNum = null;
	public static void Ishare(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException
	{
		/*try
		{*/
		driver.findElement(By.xpath("//div[@id='toolbar']")).click();
		driver.findElement(By.xpath("//div[contains(text(),'Native SHARES')]")).click();
		pnrNum =Login.PNRNUM.trim();
		
		driver.findElement(By.xpath("//input[contains(@aria-label,'Enter Input Shares Command')]")).sendKeys("*"+pnrNum);
		driver.findElement(By.xpath("//button[contains(@translate,'pssgui.submit')]")).click();
/*		}
		catch(Exception e)
		{
			
		}*/
		
		
	}
	
   public void FeeServicesMileageauxiliary(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException
   {   try
       {
	     driver.findElement(By.xpath("//div[@id='toolbar']")).click();
		 //driver.findElement(By.xpath("//div[contains(text(),'Native SHARES')]")).click();
		 driver.findElement(By.xpath("//div[contains(text(),'Fee Services & Other Charges')]")).click();
		 driver.findElement(By.xpath("//md-select[@ng-model='auxiliaryFare.model.selectedAuxType']")).click();
		 Thread.sleep(1000);
		 driver.findElement(By.xpath("//md-option//div[contains(text(),'Mileage auxiliary display')]")).click();
		 driver.findElement(By.xpath("//input[@name='destination']")).sendKeys("LAX");
		 driver.findElement(By.xpath("//input[@name='destination']")).sendKeys(Keys.ENTER);
		 driver.findElement(By.xpath("//button[@aria-label='Search']")).click();
		 FlightSearch.loadhandling(driver);
		 String city=driver.findElement(By.xpath("//div[contains(@ng-repeat,'DistanceData in auxiliaryFare')]//tr[3]/td[1]")).getText().trim();
		 String TPMmilage=driver.findElement(By.xpath("//div[contains(@ng-repeat,'DistanceData in auxiliaryFare')]//tr[3]/td[2]")).getText().trim();
		 String MpmMilage=driver.findElement(By.xpath("//div[contains(@ng-repeat,'DistanceData in auxiliaryFare')]//tr[3]/td[3]")).getText().trim();
		 String cumulativemilage=driver.findElement(By.xpath("//div[contains(@ng-repeat,'DistanceData in auxiliaryFare')]//tr[3]/td[4]")).getText().trim();
		 String gd=driver.findElement(By.xpath("//div[contains(@ng-repeat,'DistanceData in auxiliaryFare')]//tr[3]/td[5]")).getText().trim();
		 String highlevel=driver.findElement(By.xpath("//div[contains(@ng-repeat,'DistanceData in auxiliaryFare')]//tr[3]/td[8]")).getText().trim();
		 if(city.equalsIgnoreCase("LAX") &&TPMmilage.equalsIgnoreCase("3009") && MpmMilage.equalsIgnoreCase("3009"))
		 {
		    queryObjects.logStatus(driver, Status.PASS, "checking Mileage auxiliary display","Milage auxilary detail is displayin",null);
		 }
			 
		 else
		 {
			queryObjects.logStatus(driver, Status.FAIL, "checking Mileage auxiliary display", "Milage auxilary detail is not displayin",null);
		 }
          }
      catch(Exception e)
	   {
    	  queryObjects.logStatus(driver, Status.FAIL, "error while checking Mileage auxiliary display","" ,null);	  
	   }
   
   }
   
   public void FeeServicesPassengerfacilitydisplay(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException
   {   try
       {
	     driver.findElement(By.xpath("//div[@id='toolbar']")).click();
		 //driver.findElement(By.xpath("//div[contains(text(),'Native SHARES')]")).click();
		 driver.findElement(By.xpath("//div[contains(text(),'Fee Services & Other Charges')]")).click();
		 driver.findElement(By.xpath("//md-select[@ng-model='auxiliaryFare.model.selectedAuxType']")).click();
		 Thread.sleep(1000);
		 driver.findElement(By.xpath("//md-option//div[contains(text(),'Passenger facility charge display')]")).click();
		 driver.findElement(By.xpath("//input[@name='airport']")).sendKeys("LAX");
		 driver.findElement(By.xpath("//input[@name='airport']")).sendKeys(Keys.ENTER);
		 driver.findElement(By.xpath("//button[@aria-label='Search']")).click();
		 FlightSearch.loadhandling(driver);
		 
		 
		 if(driver.findElements(By.xpath("//div[@translate='pssgui.pfc.Information']//following-sibling::div")).size()>0)
		 {
		    queryObjects.logStatus(driver, Status.PASS, "checking Passenger facility charge display","Passenger facility charge display is displayin",null);
		 }
			 
		 else
		 {
			queryObjects.logStatus(driver, Status.FAIL, "checking Passenger facility charge display", "Passenger facility detail is not displayin",null);
		 }
          }
      catch(Exception e)
	   {
    	  queryObjects.logStatus(driver, Status.FAIL, "error while checking Mileage auxiliary display","" ,null);	  
	   }
   
   }
   
 //meenu
   public void customerSearch(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException
   {  
	   try
       {
		  queryObjects.logStatus(driver, Status.PASS, "Customer Search functionality checking", "Email Search Started" , null);
          driver.findElement(By.xpath("//div[@id='toolbar']")).click();
          driver.findElement(By.xpath("//md-menu-content/md-menu-item[15]/button/div[1]/div")).click();
          Thread.sleep(1000);
          String Search_By=FlightSearch.getTrimTdata(queryObjects.getTestData("Search_By"));
          if(Search_By.equalsIgnoreCase("EMail"))
          {   
        	  String Email=FlightSearch.getTrimTdata(queryObjects.getTestData("EMail"));
              driver.findElement(By.xpath("//input[@name='email']")).sendKeys(Email);
              Thread.sleep(1000);
              driver.findElement(By.xpath("//form[2]/div/div[2]/button")).click();
              FlightSearch.loadhandling(driver);
              //validation begins              
              String errormsg=FlightSearch.getErrorMSGfromAppliction(driver, queryObjects);
              if(errormsg.contains("Record not found"))
            	  queryObjects.logStatus(driver, Status.WARNING, "EMail search Completed","Record not Found ",null);
              else 
            	  if(driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div[1]/div/table/tbody/tr/td[2]")).getText().toString().equals(Email))
            	  {
            		  queryObjects.logStatus(driver, Status.PASS, "EMail search Completed","Email Id fetched matches with the email input ",null);  
            	  }
              
            	  else
            	  {
            			queryObjects.logStatus(driver, Status.FAIL, "EMail search Completed","Email Id fetched matches with the email input",null);
            	  }
          }
          else if(Search_By.equalsIgnoreCase("Name"))
          {   
        	  String Surname=FlightSearch.getTrimTdata(queryObjects.getTestData("Surname"));
        	  String GivenName=FlightSearch.getTrimTdata(queryObjects.getTestData("GivenName"));
        	  String DOB=FlightSearch.getTrimTdata(queryObjects.getTestData("DateofBirth"));
              driver.findElement(By.xpath("//input[@name=\"surname\"]")).sendKeys(Surname);
              driver.findElement(By.xpath("//input[@name=\"givenName\"]")).sendKeys(GivenName);
              driver.findElement(By.xpath("//md-dialog//div/md-datepicker/div[1]/input")).sendKeys(DOB);
              Thread.sleep(2000);
              driver.findElement(By.xpath("//form[1]/div/div[2]/button")).click();
              //validation to be written             
                      
          }
        
       }
	   catch(Exception e)
	   {
		   queryObjects.logStatus(driver, Status.FAIL, "error while fetching Customer details","" ,null);	  
	   }
   
   }

}

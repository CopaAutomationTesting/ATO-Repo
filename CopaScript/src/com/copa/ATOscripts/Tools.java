package com.copa.ATOscripts;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import com.aventstack.extentreports.Status;
import com.copa.RESscripts.*;

import com.copa.Util.ATOflowPageObjects;
import com.copa.Util.url_path;
import FrameworkCode.BFrameworkQueryObjects;
import freemarker.core.ReturnInstruction.Return;
public class Tools extends ATOflowPageObjects {
	
	public String Menu;
	public String Destination;
	public String AuxType;
	public String strDate;
	public String StrAdd;
	public String From;
	public String Amount;
	public String FlNum;
	public String TaxCode;
	public String Help;
	public String Country_Location;
	BFrameworkQueryObjects queryObjects;
	
	
	public void Tools(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		
		
		try {
			Menu = FlightSearch.getTrimTdata(queryObjects.getTestData("Menu"));
			Destination = FlightSearch.getTrimTdata(queryObjects.getTestData("Destination"));
			AuxType = FlightSearch.getTrimTdata(queryObjects.getTestData("AuxType"));
			strDate = FlightSearch.getTrimTdata(queryObjects.getTestData("strDate"));
			StrAdd = FlightSearch.getTrimTdata(queryObjects.getTestData("StrAdd"));
			From =  FlightSearch.getTrimTdata(queryObjects.getTestData("From"));
			Amount = FlightSearch.getTrimTdata(queryObjects.getTestData("Amount"));
			FlNum = FlightSearch.getTrimTdata(queryObjects.getTestData("FlNum"));
			TaxCode = FlightSearch.getTrimTdata(queryObjects.getTestData("TaxCode"));
			Help = FlightSearch.getTrimTdata(queryObjects.getTestData("Help"));
			Country_Location = FlightSearch.getTrimTdata(queryObjects.getTestData("Country_Location"));
			
		if(!Menu.isEmpty()) {
				
			
			driver.findElement(By.xpath("//i[contains(@ng-class,'toolbar')]")).click();  // clicking tools menu bar
			Thread.sleep(2000);
			if(driver.findElement(By.xpath("//div[contains(text(),'"+ Menu +"')]")).isDisplayed()) {
				queryObjects.logStatus(driver, Status.PASS,"Menu inside tool"+ Menu,"Menu was avialble "+Menu,null);
				driver.findElement(By.xpath("//div[contains(text(),'"+ Menu +"')]")).click();
				FlightSearch.loadhandling(driver);
				
				if (Menu.contains("Fee Services & Other Charges")) {
					driver.findElement(By.xpath("//md-select[contains(@ng-model,'selectedAuxType')]")).click();
					FlightSearch.loadhandling(driver);
					if(driver.findElement(By.xpath("//div[contains(text(),'"+ AuxType +"')]")).isDisplayed()) {
					driver.findElement(By.xpath("//div[contains(text(),'"+ AuxType +"')]")).click();
					queryObjects.logStatus(driver, Status.PASS,"Auxilary Fare Type","Auxilary Fare Type was Clicked "+AuxType,null);
				
						if (AuxType.contains("Mileage auxiliary display")) {
						FlightSearch.loadhandling(driver);
						SendValueswithEnter(driver,"//input[contains(@name,'destination')]",Destination);
						FlightSearch.loadhandling(driver);
						ClickOn(driver,"//button[contains(text(),'Search')]");
						FlightSearch.loadhandling(driver);
						VerifyDisplay(driver,"//th[contains(text(),'TPM Mileage')]","TPM Mileage");
						VerifyDisplay(driver,"//tr[contains(@ng-repeat,'DistanceData')]",Destination+ " distance");
						}
						else if(TaxCode=="") {
							ClickOn(driver,"//md-select[contains(@ng-model,'auxiliaryFare.model.taxType')]");
							FlightSearch.loadhandling(driver);		
							ClickOn(driver,"//div[contains(text(),'Taxes by country display')]");
							FlightSearch.loadhandling(driver);	
							SendValueswithEnter(driver,"//input[contains(@name,'country')]",Destination);
							FlightSearch.loadhandling(driver);
							ClickOn(driver,"//button[contains(text(),'Search')]");
							FlightSearch.loadhandling(driver);
							VerifyDisplay(driver,"//span[contains(text(),'"+Destination+"')]",Destination);
						}
						else if(TaxCode!="") {
							ClickOn(driver,"//md-select[contains(@ng-model,'auxiliaryFare.model.taxType')]");
							FlightSearch.loadhandling(driver);		
							ClickOn(driver,"//div[contains(text(),'Tax by tax code display')]");
							FlightSearch.loadhandling(driver);	
							SendValueswithEnter(driver,"//input[contains(@name,'country')]",Destination);
							FlightSearch.loadhandling(driver);
							SendValueswithEnter(driver,"//input[contains(@ng-model,'taxCode')]",TaxCode);
							Thread.sleep(2000);
							ClickOn(driver,"//button[contains(text(),'Search')]");
							FlightSearch.loadhandling(driver);
							VerifyDisplay(driver,"//span[contains(text(),'"+Destination+"')]",Destination);
							VerifyDisplay(driver,"//td[contains(text(),'"+TaxCode+"')]",TaxCode);
						}
						
					}
					else {
						queryObjects.logStatus(driver, Status.FAIL,"Auxilary Fare Type","Auxilary Fare Type was not displayed",null);	
					}
					//auxiliaryFare.model.taxType
				}
				else if (Menu.contains("Timatic")) {
					ClickOn(driver,"//md-select[contains(@ng-model,'timaticInfo')]");
					FlightSearch.loadhandling(driver);
					if (!AuxType.isEmpty())
					{
						ClickOn(driver,"//div[contains(text(),'"+AuxType+"')]");	
					}
					else
						ClickOn(driver,"//div[contains(text(),'Full Text Display')]");
					FlightSearch.loadhandling(driver);
					//SendValues(driver,"//input[contains(@name,'country')]",Destination);
					//Atul- 5Mar (Line 92 - 99) 					
					driver.findElement(By.xpath("//input[contains(@name,'country')]")).click();
					driver.findElement(By.xpath("//input[contains(@name,'country')]")).clear();
					driver.findElement(By.xpath("//input[contains(@name,'country')]")).sendKeys(Destination);
					Thread.sleep(2000);
					driver.findElement(By.xpath("//input[contains(@name,'country')]")).sendKeys(Keys.TAB);
					FlightSearch.loadhandling(driver);
					if (AuxType.contains("Itinerary Specific Visa Information") ||AuxType.contains("Health Information"))
					{
						//driver.findElement(By.xpath("//div[3]/pssgui-autocomplete[2]//md-input-container")).click();
						Thread.sleep(1000);
						driver.findElement(By.xpath("//input[contains(@aria-label,'Country Location')]")).sendKeys(Country_Location);
						Thread.sleep(3000);
						driver.findElement(By.xpath("//input[contains(@aria-label,'Country Location')]")).sendKeys(Keys.TAB);
					}
					
					ClickOn(driver,"//button[contains(text(),'Display')]");
					FlightSearch.loadhandling(driver);
					
				}
				
				else if (Menu.contains("Calendar with Count Functions")) {
					SendValueswithEnter(driver,"//input[contains(@class,'md-datepicker-input')]",strDate);
					FlightSearch.loadhandling(driver);
					SendValueswithEnter(driver,"//input[contains(@ng-model,'calendar.model.inputText')]",StrAdd);
					FlightSearch.loadhandling(driver);
					ClickOn(driver,"//button[contains(text(),'OK')]");
					FlightSearch.loadhandling(driver);
							}
				
				else if (Menu.contains("Converter")) {
					ClickOn(driver,"//md-select[contains(@ng-model,'currencyConverter.model.converterMethods')]");
					FlightSearch.loadhandling(driver);
					ClickOn(driver,"//div[contains(text(),'Currency by NUC / ROE')]");
					FlightSearch.loadhandling(driver);
					SendValueswithEnter(driver,"//input[contains(@ng-model,'currencyConverter.model.fromCurrency')]",From);
					Thread.sleep(2000);
					SendValueswithEnter(driver,"//input[contains(@ng-model,'currencyConverter.model.toCurrency')]",Destination);
					Thread.sleep(2000);
					SendValueswithEnter(driver,"//input[contains(@ng-model,'currencyConverter.model.amount')]",Amount);
					ClickOn(driver,"//button[contains(text(),'Display')]");
					FlightSearch.loadhandling(driver);
					VerifyDisplay(driver,"//div[text()='NUC']","Rate Type NUC ");
					VerifyDisplay(driver,"//div[text()='Converted Amount']","Converted Amount ");
				}
				

				else if (Menu.contains("Schedule Frequency")) {
					SendValueswithEnter(driver,"//input[contains(@ng-model,'schedule.model.flightNumber')]",FlNum);
					Thread.sleep(2000);
					
					
					Calendar cal = Calendar.getInstance();
										
					java.util.Date newdate = cal.getTime();
					String ckDate = new SimpleDateFormat("MM/dd/yyyy").format(newdate);
					String strnewdate= ckDate.toString();
					
					
					SendValueswithEnter(driver,"//input[contains(@class,'md-datepicker-input')]",strnewdate);
					FlightSearch.loadhandling(driver);	
					ClickOn(driver,"//button[contains(text(),'Search')]");
					FlightSearch.loadhandling(driver);
					VerifyDisplay(driver,"//span[text()='Schedule Frequency Search Results :']", "Schedule Frequency");
					VerifyDisplay(driver,"//span[text()=' "+FlNum+"']","Flight "+FlNum);
					
				}
				else if(Menu.contains("Seat Map")) {
					
					SendValueswithEnter(driver,"//input[contains(@ng-model,'Seatmap.model.flightNumber')]",FlNum);
					Thread.sleep(2000);
					ClickOn(driver,"//button[contains(text(),'Display')]");
					FlightSearch.loadhandling(driver);
					VerifyDisplay(driver,"//div[text()='Business']","Business ");
					VerifyDisplay(driver,"//div[text()='Economy']","Economy ");
					VerifyDisplay(driver,"//span[text()='V']","Premium seats ");
				}
				else if(Menu.contains("Flifo Search")) {
					
					SendValueswithEnter(driver,"//input[contains(@ng-model,'flightSearch.model.flightNumber')]",FlNum);
					Thread.sleep(2000);
					ClickOn(driver,"//div[contains(@class,'flifo-popup')]//i[@class='icon-search active-state']");
					FlightSearch.loadhandling(driver);
					if(driver.findElements(By.xpath("//tbody[contains(@ng-repeat,'legsDetails in flifoData.Legs')]")).size()>0) {
						queryObjects.logStatus(driver, Status.PASS, "FLIFO Search Display", "Flifo Search is displayed", null);
					}
					else {
						queryObjects.logStatus(driver, Status.FAIL, "FLIFO Search Display", "Flifo Search is not displayed", null);
					}
				}
				
			}else {
				queryObjects.logStatus(driver, Status.FAIL,"Menu inside tool"+ Menu,"Menu was not avialble "+Menu,null);
			}
			
		}
		
		
		else if (Help.equalsIgnoreCase("Y")) {
			ClickOn(driver,"//i[contains(@class,'icon-help')]");
			FlightSearch.loadhandling(driver);
			VerifyDisplay(driver,"//div[text()='Direct Reference System']","Direct Reference System");
			VerifyDisplay(driver,"//span[text()='Categories']","Categories ");
			
			
		}
		
		}
		catch(Exception e){
			BFrameworkQueryObjects.logStatus(driver, Status.FAIL,"Tools validation","Validation failed due to exception ",e);
		}
		
	}

	public static void SendValueswithEnter(WebDriver driver, String Xpath, String Value) throws IOException {
		try {
			if (driver.findElement(By.xpath(Xpath)).isDisplayed()) {
				BFrameworkQueryObjects.logStatus(driver, Status.PASS,"Field Display" +Xpath,"Field was displayed",null);
				driver.findElement(By.xpath(Xpath)).sendKeys(Value);
				Thread.sleep(3000);
				driver.findElement(By.xpath(Xpath)).sendKeys(Keys.ENTER);
				BFrameworkQueryObjects.logStatus(driver, Status.PASS,"Value entered" +Value,"Value was entered",null);
			}
		}
		catch(Exception e){
			BFrameworkQueryObjects.logStatus(driver, Status.FAIL,"Exception occured while send value","Exception for xpath "+Xpath,e);
		} 
		
	}
	public static void SendValues(WebDriver driver, String Xpath, String Value) throws IOException {
		try {
			if (driver.findElement(By.xpath(Xpath)).isDisplayed()) {
				BFrameworkQueryObjects.logStatus(driver, Status.PASS,"Field Display" +Xpath,"Field was displayed",null);
				driver.findElement(By.xpath(Xpath)).sendKeys(Value);
				Thread.sleep(3000);				
				BFrameworkQueryObjects.logStatus(driver, Status.PASS,"Value entered" +Value,"Value was entered",null);
			}
		}
		catch(Exception e){
			BFrameworkQueryObjects.logStatus(driver, Status.FAIL,"Exception occured while send value","Exception for xpath "+Xpath,e);
		} 
		
	}

	public static void ClickOn(WebDriver driver, String Xpath) throws IOException {
		try {
			if (driver.findElement(By.xpath(Xpath)).isDisplayed()) {
				BFrameworkQueryObjects.logStatus(driver, Status.PASS,"Field Display" +Xpath,"Field was displayed",null);
				driver.findElement(By.xpath(Xpath)).click();
				BFrameworkQueryObjects.logStatus(driver, Status.PASS,"Click performed","Click was performed",null);
			}
		}
		catch(Exception e){
			BFrameworkQueryObjects.logStatus(driver, Status.FAIL,"Exception occured while send value","Exception for xpath "+Xpath,e);
		} 
		
	}
	
	public void VerifyDisplay(WebDriver driver, String Xpath, String Displayname) throws IOException {
		try {
			if (driver.findElement(By.xpath(Xpath)).isDisplayed()) {
				BFrameworkQueryObjects.logStatus(driver, Status.PASS,"Field Display" +Displayname,Displayname+" was displayed ",null);
				
			}
		}
		catch(Exception e){
			BFrameworkQueryObjects.logStatus(driver, Status.FAIL,"Exception occured while send value","Exception for xpath "+Xpath,e);
		} 
		
	}
}

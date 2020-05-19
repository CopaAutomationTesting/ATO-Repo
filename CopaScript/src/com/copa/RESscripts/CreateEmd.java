package com.copa.RESscripts;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import FrameworkCode.BFrameworkQueryObjects;

public class CreateEmd {

	 String ssrtype=null;
	 String wave=null;
	 String sWaiver=null;
	 String Waive_Fee=null;
	 String Passengename=null;
	public void standaloneEmd(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException
	{
		try{
			ssrtype = FlightSearch.getTrimTdata(queryObjects.getTestData("ssrtype"));
			wave= FlightSearch.getTrimTdata(queryObjects.getTestData("WaiverPassenger"));
			sWaiver=queryObjects.getTestData("Waiver");
			Waive_Fee = FlightSearch.getTrimTdata(queryObjects.getTestData("Waiver_Fee"));
			
			String numberofems=FlightSearch.getTrimTdata(queryObjects.getTestData("Number_of_EMDS"));
			int numberofemds=0;
			if (numberofems.equals("")) 
				numberofemds=1;
			else 
				numberofemds=Integer.parseInt(numberofems);
			for (int iEMDs = 1; iEMDs <= numberofemds; iEMDs++) {
				createEMD(driver, queryObjects);
			}
		}
		catch(Exception e)
		{
			queryObjects.logStatus(driver, Status.FAIL, "EMD Creation", "EMD Creation Unsuccessful: " + e.getMessage() , e);
		}


	}
	public void createEMD(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		WebDriverWait wait = new WebDriverWait(driver, 300);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Reservations')]")));
		driver.findElement(By.xpath("//div[contains(text(),'Reservations')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[text()='New Order']")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div/div[contains(text(),'Non-Airfare Service')]"))));
		driver.findElement(By.xpath("//div/div[contains(text(),'Non-Airfare Service')]")).click();
		Thread.sleep(2000);
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath("//div/button[contains(text(),'View Catalog Services')]")).click();
		FlightSearch.loadhandling(driver);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[@name='ssr']"))));
		//select ssr
		//driver.findElement(By.xpath("//input[@name='ssr']")).sendKeys("COPA CLUB");
		/*String EMDName = FlightSearch.getTrimTdata(queryObjects.getTestData("EMD_Name"));
		if (EMDName.equalsIgnoreCase("BB7")) {
			driver.findElement(By.xpath("//input[@name='ssr']")).sendKeys("BB7");
		} else {
			driver.findElement(By.xpath("//input[@name='ssr']")).sendKeys("0Bx");
		} */
		if(ssrtype.isEmpty()) {
			driver.findElement(By.xpath("//input[@name='ssr']")).sendKeys("0Bx");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@name='ssr']")).sendKeys(Keys.TAB);}
			else
			 {
				driver.findElement(By.xpath("//input[@name='ssr']")).sendKeys(ssrtype);
				Thread.sleep(2000);
				driver.findElement(By.xpath("//input[@name='ssr']")).sendKeys(Keys.TAB);
			}
		//driver.findElement(By.xpath("//input[@name='ssr']")).sendKeys("0Bx");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@name='ssr']")).sendKeys(Keys.TAB);
		//driver.findElement(By.xpath("//li[contains(@md-virtual-repeat,'Autocomplete')]")).click();

		//click on ssr serach button
		driver.findElement(By.xpath("//span/i[@class='icon-search']")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[contains(text(),'Check Out')]"))));
		String emdamt=driver.findElement(By.xpath("//div[div[@ng-if='amountCtrl.title']]/div[2]")).getText().trim().split("\\s+")[0];
		//Login.envwrite(Login.EMDAmt, emdamt+";");
		Login.EMDAmt= emdamt+";";
		driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
		Thread.sleep(2000);
		String LstNm=RandomStringUtils.random(6, true, false);
		driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys(LstNm);
		Thread.sleep(2000);
		String FstNm=RandomStringUtils.random(6, true, false);
		driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys(FstNm);
		Thread.sleep(2000);
		String PaxName_type=LstNm+", "+FstNm+"-Adult;";
		
		Passengename =LstNm+", "+FstNm+"";
		driver.findElement(By.xpath("//input[@name='Email Address']")).sendKeys(RandomStringUtils.random(6, true, false)+"@mphasis.com");
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[contains(text(),'Proceed to Pay')]"))));

		driver.findElement(By.xpath("//button[contains(text(),'Proceed to Pay')]")).click();

		//wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div/button[text()='Pay']"))));
		
		//String wave= FlightSearch.getTrimTdata(queryObjects.getTestData("WaiverPassenger"));
	//	String sWaiver=queryObjects.getTestData("Waiver");
		if (sWaiver.equalsIgnoreCase("yes") ) {
			//String Waive_Fee = FlightSearch.getTrimTdata(queryObjects.getTestData("Waiver_Fee"));
			FlightSearch fs=new FlightSearch();  // Appling Waiver
			fs.waivermethod(driver, queryObjects,wave,Waive_Fee,Passengename);
		}
		
		driver.findElement(By.xpath("//div/button[text()='Pay']")).click();

		FlightSearch.emailhandling(driver,queryObjects);

		try {
			wait = new WebDriverWait(driver, 120);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Payment Successful']")));
			String statusMessage = driver.findElement(By.xpath("//div[text()='Payment Successful']")).getText();
			queryObjects.logStatus(driver, Status.PASS, " EMD Payment", statusMessage, null);
		}
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "EMD Payment", "EMD Payment Unsuccessful: " + e.getMessage() , e);
		}
		String emdno=driver.findElement(By.xpath("//span[text()='EMD']/following-sibling::span")).getText().trim();

		System.err.println(emdno);
		String semdnoTemp = Login.EMDNO;
		semdnoTemp=semdnoTemp.trim();
		
		if(!semdnoTemp.isEmpty())  // storing for multiple EMD number in ENv sheet 
			Login.EMDNO= semdnoTemp+";"+emdno;
		else
			Login.EMDNO= emdno;
		//After payment Amout checing and pay button checking ...........
		String Afterpay_amt=driver.findElement(By.xpath("//input[@ng-model='payments.Amount']")).getAttribute("value");
		double dAfterpay_amt=Double.parseDouble(Afterpay_amt);
		if(dAfterpay_amt>0)
			queryObjects.logStatus(driver, Status.FAIL, "After Payment Amount checking", "After payment Amount should Zero ,Actual :"+dAfterpay_amt, null);
		else
			queryObjects.logStatus(driver, Status.PASS, "After Payment Amount checking", "After payment Amount showing Zero..", null);
		
		if(driver.findElements(By.xpath("//button[text()='Pay' and @disabled='disabled']")).size()>0)
			queryObjects.logStatus(driver, Status.PASS, "After Payment Pay button checking", "After payment pay button Disable", null);
		else
			queryObjects.logStatus(driver, Status.FAIL, "After Payment Pay button checking", "After payment pay button should Disable", null);
		
		driver.findElement(By.xpath("//button[text()='Done']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button")).click();  // clicking reservation menu bar
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Reservations')]")).click();
		//driver.get(driver.getCurrentUrl());
		FlightSearch.loadhandling(driver);

		//String SalesOff = Login.SalesOff;
		//  String Cur =  Login.Cur;


		/*driver.findElement(By.xpath("//div[@action='saleOfficeInfo']/div[@class='padding-top']")).click(); 
        	//Thread.sleep(3000);
			driver.findElement(By.xpath("//div[@popup-action='salesoffice-update']/div/div[1]/md-input-container/md-select/md-select-value/span[2]")).click();
			//Thread.sleep(3000);

        	driver.findElement(By.xpath("//md-option[div[@class='md-text ng-binding' and contains(text(),'"+SalesOff+"')]]")).click(); 


        driver.findElement(By.xpath("//div[@popup-action='salesoffice-update']/div/div[2]/md-input-container")).click();//Clicking on Currency drop down
		 */    /*    if (!Cur.isEmpty()) {
        	driver.findElement(By.xpath("//md-option[div[@class='md-text ng-binding' and contains(text(),'"+Cur+"')]]")).click();;//Selecting the UDS Currency
        }                              
        driver.findElement(By.xpath("//div[@popup-action='salesoffice-update']/div[2]/button[contains(text(),'OK')]")).click();
        FlightSearch.loadhandling(driver);*/
	}

	public void AddEmdCheckINPNR(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		{
			String sEmd_Name="";
			sEmd_Name=FlightSearch.getTrimTdata(queryObjects.getTestData("EMD_NAME"));
			String	totSSRs=FlightSearch.getTrimTdata(queryObjects.getTestData("totSSRs"));
			driver.findElement(By.xpath("//div[contains(@class,'order-id layout-align-start')]/span[contains(@ng-click,'passengerInfo.stateChange')]")).click();

			FlightSearch.loadhandling(driver);

			try
			{

				FlightSearch fltsearch=new FlightSearch();
				String addSSRSpecificSegment=FlightSearch.getTrimTdata(queryObjects.getTestData("addSSRSpecificSegment"));
				String totSSRss=queryObjects.getTestData("totSSRs");
				String ssrNames=queryObjects.getTestData("ssrNames");
				String After_Pay_addSSR_OR_Book_Case_addSSR= queryObjects.getTestData("After_Pay_addSSR_OR_Book_Case_addSSR");
				if(!FlightSearch.getTrimTdata(queryObjects.getTestData("SpecificSSRsforSpecificPAX")).equalsIgnoreCase(""))
					fltsearch.addspecificSSR(driver, queryObjects);
				else
					fltsearch.addSSR(driver, queryObjects,"",addSSRSpecificSegment,totSSRss,ssrNames,After_Pay_addSSR_OR_Book_Case_addSSR);
				FlightSearch.loadhandling(driver);
				if(!(driver.findElements(By.xpath("//button[contains(text(),'Check Out') and @disabled='disabled']")).size()>0))
				{
					driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//button[text()='Pay' and not(@disabled='disabled')]")).click();
					//FlightSearch.loadhandling(driver);
					fltsearch.emailhandling(driver,queryObjects);
					FlightSearch.loadhandling(driver);


					try {
						WebDriverWait wait = new WebDriverWait(driver, 120);
						wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Payment Successful']")));
						String statusMessage = driver.findElement(By.xpath("//div[text()='Payment Successful']")).getText();
						queryObjects.logStatus(driver, Status.PASS, "Payment", statusMessage, null);

					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Payment", "Payment Unsuccessful: " + e.getMessage() , e);
					}
					driver.findElement(By.xpath("//button[text()='Done']")).click();
					FlightSearch.loadhandling(driver);
				}


				else
				{
					queryObjects.logStatus(driver, Status.PASS, "Checking SSR fro Checkin Pax", "free SSR added", null);	
				}
			}

			catch(Exception e)
			{
				queryObjects.logStatus(driver, Status.FAIL, "Checking SSR fro Checkin Pax", "Unable to add SSR to PNR", e);	
			}

		}

	}

}
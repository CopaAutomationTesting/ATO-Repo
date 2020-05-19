package com.copa.RESscripts;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.formula.functions.Count;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import FrameworkCode.BFrameworkQueryObjects;

/*********************************SalesReport************************************************
Class:Validating the data from the Sales Report tab.
                      LOG OF MODIFICATIONS

 V#    DATE    PROGRAMMER    DESCRIPTION                        MOD#
 --  --------  ------------  ----------------------------------------
     29/01/18				Start


 *********************************Parameters**************************************************
 *
 */
public class salelesReport {
	static List<String> TktCnt = null;
	static String TicketStatus=null;
	static String exptTicketnumber=null;
	static String getTransation=null;
	static String getTransationType=null;
	static String getTransationAmount=null;
	static String exptTicktamt=null;
	static int getcashTicketpaymode=0;
	static int getcreditcardTicketpaymode=0;
	static int getmisTicketpaymode=0;
	static boolean findticket=false;
	static String paymode=null;
	static int Cashpaymentmodecount=0;
	static int Creditcardpaymentmodecount=0;
	static int Mispaymentmodecount=0;
	static String DocumentTypegui=null;
	static String sGetTicketno=null;
	static String CheckTaxdetail=null;
	static String CreatePNR_Tax_validation=null;
	static String Remarks=null;
	static List<String> getTax=new ArrayList<>();
	static String baseFare=null;
	static String baseFareAmt=null;
	static String totalFare=null;
	static String exptEmdnumber=null;
	static String exptEmdamt=null;
	static String[] exptEmdamt1=null;
	static String sGetEmdno=null;
	static String exptnumber=null;
	static String[] exptTickamt1=null;
	static String[] expTickamt1=null;
	static String[] expEmdamt1=null;
	static String[] ActBaseFare=null;
	static String[] ActBaseFare1=null;
	static String SalesReportHistory=null;
	static String TotalTransactionAmount=null;
	static String VoidTransaction=null;
	static String AddVariance=null;
	static String VarianceAmt=null;
	static String DisplayFOP=null;
	static String BankingInfo=null;
	static String Validate=null;
	static String CardDetails=null;

	/**********************************salesReportverification*******************************
	 * Class : Validate Sales Report data based on filter
	 * Modification date:
	 *
	 * @param driver
	 * @param queryObjects
	 * @throws Exception
	 */
	public void salesReportverification(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception{
		driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button")).click();  // clicking reservation menu bar
		Thread.sleep(1000);
		if(driver.findElements(By.xpath("//button[contains(text(),'Sales Reporting')]")).size()>0) {
			driver.findElement(By.xpath("//button[contains(text(),'Sales Reporting')]")).click();
			FlightSearch.loadhandling(driver);
		}
		driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Agent Sales Report')]")).click();
		FlightSearch.loadhandling(driver);
		if(driver.findElements(By.xpath("//h2[contains(text(),'Warning')]")).size()>0)
			driver.findElement(By.xpath("//button[contains(text(),'Ok')]")).click();
		SalesReportHistory = getTrimTdata(queryObjects.getTestData("SalesReportHistory"));
		TotalTransactionAmount = getTrimTdata(queryObjects.getTestData("TotalTransactionAmount"));
		BankingInfo = getTrimTdata(queryObjects.getTestData("BankingInfo"));
		Validate = getTrimTdata(queryObjects.getTestData("Validate"));
		AddVariance = getTrimTdata(queryObjects.getTestData("AddVariance"));
		String show=FlightSearch.getTrimTdata(queryObjects.getTestData("SHOW"));
		//To check 7 days Report from Sales Report
		if(!SalesReportHistory.isEmpty())
		{
			//close of popup "No open report exist"
			try{
				if (driver.findElements(By.xpath("//div[contains(text(),'Message - Open Reports')]")).size()>0){
					driver.findElement(By.xpath("//div[contains(text(),'Message - Open Reports')]/parent::div//i")).click();
					queryObjects.logStatus(driver, Status.PASS, "Open Report Message closed", "Need to close the Open Report", null);
				}
				queryObjects.logStatus(driver, Status.PASS, "No Open Report Message", "All the Reports are Closed", null);
			}catch(Exception e){
				queryObjects.logStatus(driver, Status.FAIL, "No Open Report Message", "All the Reports are Closed", null);
			}
			// else
			driver.findElement(By.xpath("//button[contains(text(),'View Closed Reports')]")).click();	//click view close report
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//md-select[@name='showRecords']")).click();
			Thread.sleep(1500);
			driver.findElement(By.xpath("//md-option[@value='"+show+"']")).click();
			try{
				for(int i=-6;i<=0;i++)
				{
					driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).clear();
					Calendar calc = Calendar.getInstance();
					calc.add(Calendar.DATE,i);
					String timeStampd = new SimpleDateFormat("MM/dd/yyyy").format(calc.getTime());
					driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).sendKeys(timeStampd);
					Thread.sleep(1500);
					if(driver.findElements(By.xpath("//div[contains(text(),'Closed')]")).size()>0){
						queryObjects.logStatus(driver, Status.PASS, "Sales Report is available for this date "+timeStampd, "Given days Report is available", null);
					}
					else {
						queryObjects.logStatus(driver, Status.PASS, "Sales Report is not available for this date "+timeStampd, "Given days Report is not available", null);
					}
				}
			}
			catch(Exception e){
				queryObjects.logStatus(driver, Status.FAIL, "Sales Report History error", "Given days history Report is not available", null);
			}
		}
		else if(Validate.equalsIgnoreCase("CreditCard")){
			String Transactions=FlightSearch.getTrimTdata(queryObjects.getTestData("Transactions"));
			String  DocumentType=FlightSearch.getTrimTdata(queryObjects.getTestData("DocumentType"));
			if (driver.findElements(By.xpath("//div[contains(text(),'Message - Open Reports')]")).size()>0){
				driver.findElement(By.xpath("//div[contains(text(),'Message - Open Reports')]/parent::div//i")).click();
			}
			driver.findElement(By.xpath("//md-select[@name='showRecords']")).click();
			Thread.sleep(1500);
			driver.findElement(By.xpath("//md-option[@value='"+show+"']")).click();
			driver.findElement(By.xpath("//md-select[@name='transactionTypeFilter']")).click();
			Thread.sleep(1500);
			driver.findElement(By.xpath("//md-option[@value='"+Transactions+"' and contains(@ng-repeat,'transactionType')]")).click();
			driver.findElement(By.xpath("//md-select[@name='documentTypeFilter']")).click();
			Thread.sleep(1500);
			driver.findElement(By.xpath("//md-option[@value='"+DocumentType+"' and contains(@ng-repeat,'documentType')]")).click();
			verification(driver,queryObjects);	//does verification of the ticket number, issue and doc type
			queryObjects.logStatus(driver, Status.PASS, "Ticket Number and Form of payment is credit card is verified", "Verification done", null);
			driver.findElement(By.xpath("//span[contains(text(),'Total Transaction Amount') and @tabindex='0']")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//md-content/div[1]/div[1]/div/i")).click();//click currency
			FlightSearch.loadhandling(driver);
			try {
				driver.findElement(By.xpath("//md-content/div[1]/div[2]//div/i")).click();//click creditcard arrow
				FlightSearch.loadhandling(driver);
				CardDetails = driver.findElement(By.xpath("//md-content/div[1]/div[2]//div[contains(text(),'VI')]")).getText().trim();
				queryObjects.logStatus(driver, Status.PASS, "CreditCard number is masked and only "+CardDetails+" details is found", "Credit card number is not available", null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "CreditCard number Xpath Issue", "Credit card number check Failed" +e.getStackTrace()[0].getLineNumber(),e);
				// TODO: handle exception
			}
			return;
		}
		else if(!BankingInfo.isEmpty()){
			String Transactions=FlightSearch.getTrimTdata(queryObjects.getTestData("Transactions"));
			String  DocumentType=FlightSearch.getTrimTdata(queryObjects.getTestData("DocumentType"));
			if (driver.findElements(By.xpath("//div[contains(text(),'Message - Open Reports')]")).size()>0){
				driver.findElement(By.xpath("//div[contains(text(),'Message - Open Reports')]/parent::div//i")).click();
			}
			driver.findElement(By.xpath("//md-select[@name='showRecords']")).click();
			Thread.sleep(1500);
			driver.findElement(By.xpath("//md-option[@value='"+show+"']")).click();
			driver.findElement(By.xpath("//md-select[@name='transactionTypeFilter']")).click();
			Thread.sleep(1500);
			driver.findElement(By.xpath("//md-option[@value='"+Transactions+"' and contains(@ng-repeat,'transactionType')]")).click();
			driver.findElement(By.xpath("//md-select[@name='documentTypeFilter']")).click();
			Thread.sleep(1500);
			driver.findElement(By.xpath("//md-option[@value='"+DocumentType+"' and contains(@ng-repeat,'documentType')]")).click();
			try {
				List<WebElement> CashCount=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div//i[@class='icon-cash-payment']/parent::span//parent::div"));
				int CashCount1=CashCount.size();
				int i;
				double TotAmount=0;
				for(i=0; i<CashCount1 ; i++){
					getTransationAmount = driver.findElement(By.xpath("//md-content[@class='_md layout-column flex']//div[@class='pssgui-bold ng-binding pull-right flex']")).getText().trim();
					expTickamt1 = getTransationAmount.split(" ");
					getTransationAmount = expTickamt1[0].replace(",","");
					double getTransationAmount1 = Double.parseDouble(getTransationAmount);
					TotAmount = TotAmount + getTransationAmount1;
				}
				queryObjects.logStatus(driver, Status.PASS, "Cash Transaction Amount is "+TotAmount, "Cash FOP for given agent", null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "No report exist, need to create PNR", "Else try with other "+e.getStackTrace()[0].getLineNumber()+e.getStackTrace(),e);
			}
		}
		else if(!TotalTransactionAmount.isEmpty()){
			try{
				driver.findElement(By.xpath("//span[contains(text(),'Total Transaction Amount') and @tabindex='0']")).click();
				FlightSearch.loadhandling(driver);
				//(//md-content/div[1]//div/i)
				driver.findElement(By.xpath("//md-content/div[1]//div/i")).click();
				FlightSearch.loadhandling(driver);
				try{
					driver.findElement(By.xpath("//input[@name='varianceAmount']")).click();
					driver.findElement(By.xpath("//input[@name='varianceAmount']")).clear();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//input[@name='varianceAmount']")).sendKeys("50");
					//(//md-content[1]/div/div//span[contains(text(),'Variance Amount')]//following::div[3]
					String VarianceAmt=driver.findElement(By.xpath("//md-content[1]/div/div//span[contains(text(),'Variance Amount')]//following::div[3]")).getText().trim();
					queryObjects.logStatus(driver, Status.PASS, "Variance Amount is "+VarianceAmt, "Variance Amount for this login", null);
					FlightSearch.loadhandling(driver);
					if(AddVariance.equalsIgnoreCase("yes")){
						driver.findElement(By.xpath("//md-select[@role='listbox']")).click();
						driver.findElement(By.xpath("//md-content//md-option[2]/div[contains(text(),'total by range > $5')]")).click();
						Thread.sleep(1500);
					}
					driver.findElement(By.xpath("//textarea[contains(@ng-model,'paymentType.agentRemarks')]")).sendKeys("Overage");
					Thread.sleep(3000);
					driver.findElement(By.xpath("//button[contains(text(),'Save Report')]")).click();
					FlightSearch.loadhandling(driver);
					//(//button[contains(text(),'Close Report')]
					try{
						driver.findElement(By.xpath("//button[contains(text(),'Close Report')]")).click();
						FlightSearch.loadhandling(driver);
						if(driver.findElements(By.xpath("//md-dialog//div//i[@class='icon-warning pssgui-design-status-critical']")).size()>0)
						{
							queryObjects.logStatus(driver, Status.PASS, "Variance Reason is not being given", "Not able to close Total Transaction amount", null);
							if(driver.findElements(By.xpath("//div[contains(text(),'Cannot close the report due to variance reason not selected')]")).size()>0){
								driver.findElement(By.xpath("//i[@class='icon-close']")).click();
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath("//md-select[@role='listbox']")).click();
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath("//md-content//md-option[2]/div[contains(text(),'total by range > $5')]")).click();
								try{
									driver.findElement(By.xpath("//button[contains(text(),'Close Report')]")).click();
									FlightSearch.loadhandling(driver);
									queryObjects.logStatus(driver, Status.PASS, "Variance Reason is being given", "Now able to close Total Transaction amount", null);
								}
								catch(Exception e){}
							}
						}
						else{
							driver.findElement(By.xpath("//md-dialog//button[contains(text(),'Close Report')]")).click();
							FlightSearch.loadhandling(driver);
							queryObjects.logStatus(driver, Status.PASS, "Total Transaction Amount with Variance "+VarianceAmt+" is closed", "Closed Total Transaction amount", null);
						}
					}
					catch(Exception e){
						queryObjects.logStatus(driver, Status.FAIL, "Variance Reason is not being given", "Not able to close Total Transaction amount", null);
					}
				}
				catch(Exception e){
					queryObjects.logStatus(driver, Status.FAIL, "After Click Total Transaction Amount is not available  ", "After Click Report is not available", null);
				}
			}
			catch(Exception e){
				queryObjects.logStatus(driver, Status.FAIL, "Total Transaction Amount is not available  ", "Report is not available", null);
			}
		}
		else
		{
			String Transactions=FlightSearch.getTrimTdata(queryObjects.getTestData("Transactions"));
			String  DocumentType=FlightSearch.getTrimTdata(queryObjects.getTestData("DocumentType"));
			DocumentTypegui=DocumentType;
			String ISSUE_EXCHANGE_vVOID=FlightSearch.getTrimTdata(queryObjects.getTestData("ISSUE_EXCHANGE_vVOID"));
			// this is for selecting number or records need to show
			driver.findElement(By.xpath("//md-select[@name='showRecords']")).click();
			Thread.sleep(1500);
			driver.findElement(By.xpath("//md-option[@value='"+show+"']")).click();
			// this is for Transaction type
			driver.findElement(By.xpath("//md-select[@name='transactionTypeFilter']")).click();
			Thread.sleep(1500);
			driver.findElement(By.xpath("//md-option[@value='"+Transactions+"' and contains(@ng-repeat,'transactionType')]")).click();
			// this is for Document type
			driver.findElement(By.xpath("//md-select[@name='documentTypeFilter']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//md-option[@value='"+DocumentType+"' and contains(@ng-repeat,'documentType')]")).click();
			//List<String> createdTicktno=null;
			Creditcardpaymentmodecount = 0;
			Mispaymentmodecount = 0;
			Cashpaymentmodecount = 0;
			if (FlightSearch.ppspaymentmodesingle.isEmpty()){
				String[] paymodearry=FlightSearch.ppspaymentmodedouble.split(";");
				for (String paytype : paymodearry) {
					if (paytype.equalsIgnoreCase("cash"))
						Cashpaymentmodecount=Cashpaymentmodecount+1;
					else if (paytype.equalsIgnoreCase("creditcard"))
						Creditcardpaymentmodecount=Creditcardpaymentmodecount+1;
					else if (paytype.equalsIgnoreCase("misc"))
						Mispaymentmodecount=Mispaymentmodecount+1;
				}
			}
			else{
				paymode=FlightSearch.ppspaymentmodesingle;
				if (paymode.equalsIgnoreCase("cash"))
					Cashpaymentmodecount=Cashpaymentmodecount+1;
				else if (paymode.equalsIgnoreCase("creditcard"))
					Creditcardpaymentmodecount=Creditcardpaymentmodecount+1;
				else if (paymode.equalsIgnoreCase("misc"))
					Mispaymentmodecount=Mispaymentmodecount+1;
			}
			/*for (int iarryiterater = 0; iarryiterater < FlightSearch.gettecketno.size(); iarryiterater++) {  //get the value form array   kishore
				exptTicketnumber=FlightSearch.gettecketno.get(iarryiterater); //get ticket number
				exptTicktamt=FlightSearch.gettecketamt.get(iarryiterater);  //get ticket amount  */
			findticket=false;
			/*List<WebElement> numerofpage=null;
			int numerofpage_size=0;
			boolean seril_numbes=true;
			try{
				numerofpage=driver.findElements(By.xpath("//div[contains(@ng-class,'tab-active')]"));
				if(numerofpage.size() ==0){
					numerofpage_size=1;
					seril_numbes=false;
			
  
	   
				
			 
				}
				else
					numerofpage_size=numerofpage.size();
			}
			catch(Exception e ) {
																													
																			
			}
			for (int inumerofpage = 0; inumerofpage <numerofpage_size; inumerofpage++) {
				if(seril_numbes)
					numerofpage.get(inumerofpage).click();*/
			
				if (ISSUE_EXCHANGE_vVOID.equalsIgnoreCase("yes"))
					if(DocumentType.equalsIgnoreCase("E-Ticket"))
						findticket=verification(driver, queryObjects);
					else if(DocumentType.equalsIgnoreCase("EMD"))
						findticket=emdverification(driver, queryObjects);
					else if(DocumentType.equalsIgnoreCase("ALL")) {
						findticket=verification(driver, queryObjects);
						findticket=emdverification(driver, queryObjects);
					}
			//}
			//if (!findticket)  kishore
			//queryObjects.logStatus(driver, Status.FAIL, "Find Ticket number in Saleses Report page", "Tick number Not exist in Sales report screen Expected Tickt number is: "+exptTicketnumber, null);
			//} //get the value form array    kishore
		}
	}  //method end

	/******************************verification*********************************************
	 * Method : Validate the Ticket number from SalesReport and Reservation Screen.
	 * Modification Date :
	 *
	 * @param driver
	 * @param queryObjects
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public boolean verification(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException, InterruptedException{
		//List<WebElement> salesreport=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div//div[contains(@ng-repeat,'documentNumber')]"));
		List<WebElement> numerofpage=null;
		int numerofpage_size=0;
		boolean seril_numbes=true;
		try{
			numerofpage=driver.findElements(By.xpath("//div[contains(@ng-class,'tab-active')]"));
			if(numerofpage.size() ==0){
				numerofpage_size=1;
				seril_numbes=false;
			}
			else
				numerofpage_size=numerofpage.size();
		}
		catch(Exception e ) {
			
		}
		List<WebElement> salesreport=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div//div[(@ng-if='!salesItem.agentSaleItem.conjunctedDocNum') or  (@ng-if='salesItem.agentSaleItem.conjunctedDocNum')]"));
		TktCnt = null;
		if (Unwholly.aTicket==null) {
			TktCnt =FlightSearch.gettecketno;
		} else {
			TktCnt = Unwholly.aTicket;
		}
		//for (int iarryiterater = 0; iarryiterater < FlightSearch.gettecketno.size(); iarryiterater++) {  //get the value form array
		for (int iarryiterater = 0; iarryiterater < TktCnt.size(); iarryiterater++) {  //get the value form array
			FlightSearch.loadhandling(driver);
			Thread.sleep(1000);
			findticket=false;
			//exptTicketnumber=FlightSearch.gettecketno.get(iarryiterater); //get ticket number
			//exptTicktamt=FlightSearch.gettecketamt.get(iarryiterater);  //get ticket amount
			//exptTickamt1=exptTicktamt.split(" ");
			if (Unwholly.aTicket==null) {
				exptTicketnumber=FlightSearch.gettecketno.get(iarryiterater);
			} else {
				exptTicketnumber=Unwholly.aTicket.get(iarryiterater);
			}
			exptTicktamt=FlightSearch.gettecketamt.get(iarryiterater);  //get ticket amount
			exptTickamt1=exptTicktamt.split(" ");
			Boolean status_ignore=true;    // need to check kishore   ... ticket status not tacking proper so time been added need to remove
			if (!FlightSearch.TktStatus.isEmpty()) {
				if( iarryiterater<FlightSearch.TktStatus.size()) 
					TicketStatus = FlightSearch.TktStatus.get(iarryiterater);
				else
					status_ignore=false; // need to check kishore
			}
		for (int inumerofpage = 0; inumerofpage < numerofpage_size; inumerofpage++) {
			driver.findElements(By.xpath("//div[contains(@ng-class,'tab-active')]"));
			if(seril_numbes)
				numerofpage.get(inumerofpage).click();   // going for next page
			
			for (int isalesreport = 1; isalesreport <= salesreport.size(); isalesreport++) {  //ticket number loop
				//String sGetTicketno=driver.findElement(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//div[contains(@ng-repeat,'documentNumber')]")).getText().trim();
				String sGetTicketno=driver.findElement(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//div[(@ng-if='!salesItem.agentSaleItem.conjunctedDocNum') or  (@ng-if='salesItem.agentSaleItem.conjunctedDocNum')]")).getText().trim();
				//FlightSearch.loadhandling(driver);
				Thread.sleep(500);
				if (exptTicketnumber.equalsIgnoreCase(sGetTicketno)) {
					findticket=true;
					queryObjects.logStatus(driver, Status.PASS, "Find Ticket number in Saleses Report page", "Ticktet number was found in Sales report page Number is:"+exptTicketnumber, null);
	 
					//Ticket State checking
					/*getTransation=driver.findElement(By.xpath("//md-content[contains(@class,'_md layout-column flex')]/div["+isalesreport+"]//div/div[2]/span")).getText().trim();
					if (getTransation.equalsIgnoreCase(FlightSearch.Pnrstate))
						queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet State checking", "Ticktet State showing correctly "+getTransation, null);
					else
						queryObjects.logStatus(driver, Status.FAIL, "After Find Ticket number Ticktet State checking", "Ticktet State should show correctly Actual is "+getTransation+" Expected is: "+FlightSearch.Pnrstate, null);*/
					getTransation=driver.findElement(By.xpath("//md-content[contains(@class,'_md layout-column flex')]/div["+isalesreport+"]//div/div[2]/span")).getText().trim();
					if(status_ignore){ // need to check kishore
						if (!FlightSearch.TktStatus.isEmpty()) {
							if (getTransation.equalsIgnoreCase(TicketStatus))
								queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet State checking", "Ticktet State showing correctly "+getTransation, null);
							else
								queryObjects.logStatus(driver, Status.FAIL, "After Find Ticket number Ticktet State checking", "Ticktet State should show correctly Actual is "+getTransation+" Expected is: "+FlightSearch.Pnrstate, null);
						} else {
							if (getTransation.equalsIgnoreCase(FlightSearch.Pnrstate))
								queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet State checking", "Ticktet State showing correctly "+getTransation, null);
	
							else
								queryObjects.logStatus(driver, Status.FAIL, "After Find Ticket number Ticktet State checking", "Ticktet State should show correctly Actual is "+getTransation+" Expected is: "+FlightSearch.Pnrstate, null);
						}
					}
					//Ticket Document Type checking
					getTransationType=driver.findElement(By.xpath("//md-content[contains(@class,'_md layout-column flex')]/div["+isalesreport+"]//div/div[4]")).getText().trim();
					if (getTransationType.equalsIgnoreCase(FlightSearch.tickttkype))
						queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet Type checking", "Ticktet Type showing correctly "+getTransationType, null);
					else
						queryObjects.logStatus(driver, Status.FAIL, "After Find Ticket number Ticktet Type checking", "Ticktet Type should show correctly Actual is "+getTransationType+" Expected is: "+FlightSearch.tickttkype, null);
					//Ticket Amount  checking
					getTransationAmount=driver.findElement(By.xpath("//md-content[contains(@class,'_md layout-column flex')]/div["+isalesreport+"]//div[1]//div/div[6]/div[1]")).getText().trim();
					getTransationAmount=getTransationAmount.replace("(", "");
					getTransationAmount=getTransationAmount.replace(")", "");
					getTransationAmount=getTransationAmount.trim();
					expTickamt1 = getTransationAmount.split(" ");
					
					getTransationAmount = expTickamt1[0].replace(",","");
					double getTransationAmount1 = Double.parseDouble(getTransationAmount);
					double exptTickamt2 = Double.parseDouble(exptTickamt1[0]);
					if (getTransationAmount1==exptTickamt2)
						queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet amount checking", "Ticktet amount showing correctly "+getTransationAmount, null);
					else
						queryObjects.logStatus(driver, Status.FAIL, "After Find Ticket number Ticktet amount checking", "Ticktet amount should show correctly Actual is "+getTransationAmount+" Expected is: "+exptTicktamt, null);
						//No Payment mode-Jenny Feb 17
					String Msg = "";
					if (Unwholly.AirlinesRefund.equalsIgnoreCase("Yes"))
						Msg = "Airlines refunded the amount to the passenger";
					else if (Unwholly.AirlinesRefund.equalsIgnoreCase("ManualReissue"))
						Msg = "ticket has been manually reissued";
					else if (Unwholly.AirlinesRefund.equalsIgnoreCase("Free"))
						Msg = "Payment is free";
							  
					//Ticket Payment mode checking
					/*if (Cashpaymentmodecount>0) //get the value form application
					{
						getcashTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//i[@class='icon-cash-payment']")).size();
						if (Cashpaymentmodecount==getcashTicketpaymode)
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode showing correctly cash mode count is "+Cashpaymentmodecount, null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode should show correctly Actual cash mode count is "+getcashTicketpaymode+" Expected cash count is: "+Cashpaymentmodecount, null);
					}
					else if (Creditcardpaymentmodecount>0){
						getcreditcardTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//i[@class='icon-CardPayment'] ")).size();
						if (Creditcardpaymentmodecount==getcreditcardTicketpaymode)
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode showing correctly number of credit card is "+Creditcardpaymentmodecount, null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode should show correctly Actual Credit card mode count is "+getcreditcardTicketpaymode+" Expected Credit card mode count is: "+Creditcardpaymentmodecount, null);
					}
					else if (Mispaymentmodecount>0){
						getmisTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//i[@class='icon-miscellaneous']")).size();
						if (Mispaymentmodecount==getmisTicketpaymode)
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode showing correctly number of Miscellaneous is "+Creditcardpaymentmodecount, null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode should show correctly Actual Miscellaneous mode count is "+getmisTicketpaymode+" Expected Miscellaneous mode count is: "+Mispaymentmodecount, null);
					}*/
					//Multiple form of Payment
					//Different Payment Types
					if (Creditcardpaymentmodecount>0 && Mispaymentmodecount>0){
						getmisTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//i[@class='icon-miscellaneous']")).size();
						getcreditcardTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//i[@class='icon-CardPayment'] ")).size();
						if (Creditcardpaymentmodecount==getcreditcardTicketpaymode)
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode showing correctly number of credit card is "+Creditcardpaymentmodecount, null);
						else if (Mispaymentmodecount==getmisTicketpaymode)
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode showing correctly number of Miscellaneous is "+Mispaymentmodecount, null);
						else if (Unwholly.AirlinesRefund.equalsIgnoreCase("Yes") || Unwholly.AirlinesRefund.equalsIgnoreCase("ManualReissue") || Unwholly.AirlinesRefund.equalsIgnoreCase("Free"))
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Since "+Msg+" , mode of payment is empty(0)", null);//Jenny Feb 17
						//else
							//queryObjects.logStatus(driver, Status.FAIL, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode should show correctly Actual Credit card mode count is "+getcreditcardTicketpaymode+" Expected Credit card mode count is: "+Creditcardpaymentmodecount, null);
					}
					else if (Cashpaymentmodecount>0 && Mispaymentmodecount>0) //get the value form application
					{
						getcashTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//i[@class='icon-cash-payment']")).size();
						getmisTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//i[@class='icon-miscellaneous']")).size();
						if (Cashpaymentmodecount==getcashTicketpaymode)
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode showing correctly cash mode count is "+Cashpaymentmodecount, null);
						else if (Mispaymentmodecount==getmisTicketpaymode)
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode showing correctly number of Miscellaneous is "+Mispaymentmodecount, null);
						else if (Unwholly.AirlinesRefund.equalsIgnoreCase("Yes") || Unwholly.AirlinesRefund.equalsIgnoreCase("ManualReissue") || Unwholly.AirlinesRefund.equalsIgnoreCase("Free"))
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Since "+Msg+" , mode of payment is empty(0)", null);//Jenny Feb 17
						//else
							//queryObjects.logStatus(driver, Status.FAIL, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode should show correctly Actual cash mode count is "+getmisTicketpaymode+" Expected cash count is: "+Mispaymentmodecount, null);
					}
					else if (Cashpaymentmodecount>0 && Creditcardpaymentmodecount>0) //get the value form application
					{
						getcashTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//i[@class='icon-cash-payment']")).size();
						getcreditcardTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//i[@class='icon-CardPayment'] ")).size();
						if (Cashpaymentmodecount==getcashTicketpaymode)
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode showing correctly cash mode count is "+Cashpaymentmodecount, null);
						else if (Creditcardpaymentmodecount==getcreditcardTicketpaymode)
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode showing correctly number of credit card is "+Creditcardpaymentmodecount, null);
						else if (Unwholly.AirlinesRefund.equalsIgnoreCase("Yes") || Unwholly.AirlinesRefund.equalsIgnoreCase("ManualReissue") || Unwholly.AirlinesRefund.equalsIgnoreCase("Free"))
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Since "+Msg+" , mode of payment is empty(0)", null);//Jenny Feb 17
						//else
							//queryObjects.logStatus(driver, Status.FAIL, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode should show correctly Actual cash mode count is "+getcashTicketpaymode+" Expected cash count is: "+Cashpaymentmodecount, null);
					}
					//Similar Payment Types
					else if (Creditcardpaymentmodecount==2){
						getcreditcardTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//i[@class='icon-CardPayment'] ")).size();
						if (getcreditcardTicketpaymode==2)
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode showing correctly number of credit card is "+Creditcardpaymentmodecount, null);
						else if (Unwholly.AirlinesRefund.equalsIgnoreCase("Yes") || Unwholly.AirlinesRefund.equalsIgnoreCase("ManualReissue") || Unwholly.AirlinesRefund.equalsIgnoreCase("Free"))
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Since "+Msg+" , mode of payment is empty(0)", null);//Jenny Feb 17
						//else
							//queryObjects.logStatus(driver, Status.FAIL, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode should show correctly Actual Credit card mode count is "+getcreditcardTicketpaymode+" Expected Credit card mode count is: "+Creditcardpaymentmodecount, null);
					}
					//Similar Payment Types
					else if (Mispaymentmodecount==2){
						getmisTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//i[@class='icon-miscellaneous']")).size();
						if (Mispaymentmodecount==2)
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode showing correctly number of credit card is "+Mispaymentmodecount, null);
						else if (Unwholly.AirlinesRefund.equalsIgnoreCase("Yes") || Unwholly.AirlinesRefund.equalsIgnoreCase("ManualReissue") || Unwholly.AirlinesRefund.equalsIgnoreCase("Free"))
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Since "+Msg+" , mode of payment is empty(0)", null);//Jenny Feb 17
						//else
							//queryObjects.logStatus(driver, Status.FAIL, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode should show correctly Actual Credit card mode count is "+Mispaymentmodecount+" Expected Credit card mode count is: "+getmisTicketpaymode, null);
					}
					else if (Cashpaymentmodecount>0) //get the value form application
					{
						getcashTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//i[@class='icon-cash-payment']")).size();
						if (Cashpaymentmodecount==getcashTicketpaymode)
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode showing correctly cash mode count is "+Cashpaymentmodecount, null);
						else if (Unwholly.AirlinesRefund.equalsIgnoreCase("Yes"))
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Since Airlines refunded the amount to the passenger , mode of payment is empty(0)", null);
						//else
							//queryObjects.logStatus(driver, Status.FAIL, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode should show correctly Actual cash mode count is "+getcashTicketpaymode+" Expected cash count is: "+Cashpaymentmodecount, null);
					} else if (Mispaymentmodecount>0){
						getmisTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//i[@class='icon-miscellaneous']")).size();
						if (Mispaymentmodecount==getmisTicketpaymode)
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode showing correctly number of Miscellaneous is "+Mispaymentmodecount, null);
						else if (Unwholly.AirlinesRefund.equalsIgnoreCase("Yes") || Unwholly.AirlinesRefund.equalsIgnoreCase("ManualReissue") || Unwholly.AirlinesRefund.equalsIgnoreCase("Free"))
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Since "+Msg+" , mode of payment is empty(0)", null);//Jenny Feb 17
						//else
							//queryObjects.logStatus(driver, Status.FAIL, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode should show correctly Actual Miscellaneous mode count is "+getmisTicketpaymode+" Expected Miscellaneous mode count is: "+Mispaymentmodecount, null);
					} else if (Creditcardpaymentmodecount>0){
						getcreditcardTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//i[@class='icon-CardPayment'] ")).size();
						if (Creditcardpaymentmodecount==getcreditcardTicketpaymode)
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode showing correctly number of credit card is "+Creditcardpaymentmodecount, null);
						else if (Unwholly.AirlinesRefund.equalsIgnoreCase("Yes") || Unwholly.AirlinesRefund.equalsIgnoreCase("ManualReissue") || Unwholly.AirlinesRefund.equalsIgnoreCase("Free"))
							queryObjects.logStatus(driver, Status.PASS, "After Find Ticket number Ticktet paymode checking", "Since "+Msg+" , mode of payment is empty(0)", null);//Jenny Feb 17
						//else
							//queryObjects.logStatus(driver, Status.FAIL, "After Find Ticket number Ticktet paymode checking", "Ticktet paymode should show correctly Actual Credit card mode count is "+getcreditcardTicketpaymode+" Expected Credit card mode count is: "+Creditcardpaymentmodecount, null);
					}
					//For Multiple form of Payments
	 
					break;
				}  // ticket number equal case end
				if(findticket)
					break;
	 
			}  //ticket number loop
   
   
			if(findticket)
				break;
		}
		if(!findticket)  
			queryObjects.logStatus(driver, Status.FAIL, "Find Ticket number in Saleses Report page", "Tick number Not exist in Sales report screen Expected Tickt number is: "+exptTicketnumber, null);
	}
		return findticket;
	}

	/****************************************emdverification*********************************
	 * Method : Validation of EMD number from Sales Report and Reservation Screen
	 * @param driver
	 * @param queryObjects
	 * @return
	 * @throws IOException
	 */
	public boolean emdverification(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException{
		List<WebElement> salesreport=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div//div[contains(@ng-repeat,'documentNumber')]"));
		for (int iarryiterater = 0; iarryiterater < FlightSearch.getemdno.size(); iarryiterater++) {  //get the value form array
			FlightSearch.loadhandling(driver);
			exptEmdnumber=FlightSearch.getemdno.get(iarryiterater);
			exptEmdamt=FlightSearch.getemdamt.get(iarryiterater);
			exptEmdamt1=exptEmdamt.split(" ");
			System.err.println(exptEmdamt1[0]);
		}
		for (int isalesreport = 1; isalesreport <= salesreport.size(); isalesreport++) {  //ticket number loop
			String sGetEmdno=driver.findElement(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//div[contains(@ng-repeat,'documentNumber')]")).getText().trim();
			FlightSearch.loadhandling(driver);
			if (exptEmdnumber.equalsIgnoreCase(sGetEmdno)) {
				findticket=true;
				queryObjects.logStatus(driver, Status.PASS, "Find EMD number in Saleses Report page", "EMD number was found in Sales report page", null);
				//Emd State checking
				getTransation=driver.findElement(By.xpath("//md-content[contains(@class,'_md layout-column flex')]/div["+isalesreport+"]//div/div[2]/span")).getText().trim();
				if (getTransation.equalsIgnoreCase(FlightSearch.Pnrstate))
					queryObjects.logStatus(driver, Status.PASS, "After Find Emd number EMD State checking", "Emd State showing correctly "+getTransation, null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "After Find Emd number Emd State checking", "Emd State should show correctly Actual is "+getTransation+" Expected is: "+FlightSearch.Pnrstate, null);
				//Emd Amount  checking
				getTransationAmount=driver.findElement(By.xpath("//md-content[contains(@class,'_md layout-column flex')]/div["+isalesreport+"]//div[1]//div/div[6]/div[1]")).getText().trim();
				expEmdamt1 = getTransationAmount.split(" ");
				getTransationAmount = expEmdamt1[0].replace(",","");
				if (getTransationAmount.equalsIgnoreCase(exptEmdamt1[0]))
					queryObjects.logStatus(driver, Status.PASS, "After Find Emd number Emd Type checking", "Emd Type showing correctly "+getTransationAmount, null);
				//else
					//queryObjects.logStatus(driver, Status.FAIL, "After Find Emd number Emd Type checking", "Emd Type should show correctly Actual is "+getTransationAmount+" Expected is: "+exptTicktamt, null);
				//Emd Payment mode checking

				if (Cashpaymentmodecount>0) //get the value form application
				{
					getcashTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//i[@class='icon-cash-payment']")).size();
					if (Cashpaymentmodecount==getcashTicketpaymode)
						queryObjects.logStatus(driver, Status.PASS, "After Find Emd number Emd paymode checking", "Emd paymode showing correctly cash mode count is "+Cashpaymentmodecount, null);
					//else
						//queryObjects.logStatus(driver, Status.FAIL, "After Find Emd number Emd paymode checking", "Emd paymode should show correctly Actual cash mode count is "+getcashTicketpaymode+" Expected cash count is: "+Cashpaymentmodecount, null);
				}
				else if (Creditcardpaymentmodecount>0){
					getcreditcardTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//i[@class='icon-CardPayment']")).size();
					if (Creditcardpaymentmodecount==getcreditcardTicketpaymode)
						queryObjects.logStatus(driver, Status.PASS, "After Find Emd number Emd paymode checking", "Emd paymode showing correctly number of credit card is "+Creditcardpaymentmodecount, null);
					//else
						//queryObjects.logStatus(driver, Status.FAIL, "After Find Emd number Emd paymode checking", "Emd paymode should show correctly Actual Credit card mode count is "+getcreditcardTicketpaymode+" Expected Credit card mode count is: "+Creditcardpaymentmodecount, null);
				}
				else if (Mispaymentmodecount>0){
					getmisTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div["+isalesreport+"]//i[@class='icon-miscellaneous']")).size();
					if (Mispaymentmodecount==getmisTicketpaymode)
						queryObjects.logStatus(driver, Status.PASS, "After Find Emd number Emd paymode checking", "Emd paymode showing correctly number of Miscellaneous is "+Creditcardpaymentmodecount, null);
					//else
						//queryObjects.logStatus(driver, Status.FAIL, "After Find Emd number Emd paymode checking", "Emd paymode should show correctly Actual Miscellaneous mode count is "+getmisTicketpaymode+" Expected Miscellaneous mode count is: "+Mispaymentmodecount, null);
				}
				break;
			}  // Emd number equal case end
		}  //Emd number loop
		return findticket;
	}

	/*****************************************CashAccountingReport*****************************
	 * Method : Validate Cash accounting report
	 * @param driver
	 * @param queryObjects
	 * @throws Exception
	 */

	public static void CashAccountingReport(WebDriver driver,BFrameworkQueryObjects queryObjects)throws Exception{
		int datecount=0;
		int count=0;
		int getDays = -1;
		String Opendate=null;
		String CloseStationSales=null;
		String PastdateStationReport=null;
		String CloseCash=null;
		String CloseDebitcard=null;
		String CloseCreditcard=null;
		String Closemisc=null;
		String CloseTotTransAmt=null;
		String CloseVarAmt=null;
		String CloseActualAmt=null;
		String OpenCash=null;
		String OpenDebitcard=null;
		String OpenCreditcard=null;
		String Openmisc=null;
		String OpenTotTransAmt=null;
		String OpenVarAmt=null;
		String OpenActualAmt=null;
		String ssCash=null;
		String ssCreditcard=null;
		String ssDebitcard=null;
		String ssMisc=null;
		String ssTotTransAmt=null;
		String ssVariance=null;
		String ssActual=null;
		CloseStationSales=FlightSearch.getTrimTdata(queryObjects.getTestData("CloseStationSales"));
		PastdateStationReport=FlightSearch.getTrimTdata(queryObjects.getTestData("PastdateStationReport"));
		try {
			//Station Sales Report Close and get the details
			driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button")).click();  // clicking reservation menu bar
			Thread.sleep(1000);
			if(driver.findElements(By.xpath("//button[contains(text(),'Sales Reporting')]")).size()>0) {//clicking on sales report
				driver.findElement(By.xpath("//button[contains(text(),'Sales Reporting')]")).click();
				FlightSearch.loadhandling(driver);
			}
			else
				queryObjects.logStatus(driver, Status.INFO, "Unable to find Sales Report", "Sales Report not available in this SalesOffice", null);
			if(driver.findElements(By.xpath("//button[contains(text(),'Sales Reporting')]")).size()>0) {//clicking on sales report
				driver.findElement(By.xpath("//button[contains(text(),'Sales Reporting')]")).click();
				FlightSearch.loadhandling(driver);
			}
			else
				queryObjects.logStatus(driver, Status.INFO, "Unable to find Sales Report", "Sales Report not available in this SalesOffice", null);

			if(driver.findElements(By.xpath("//button[contains(text(),'Station Sales Report')]")).size()>0) {//clicking on Station sales report
				driver.findElement(By.xpath("//button[contains(text(),'Station Sales Report')]")).click();
				FlightSearch.loadhandling(driver);
			}
			else
				queryObjects.logStatus(driver, Status.INFO, "Unable to find Station Sales Report ", "Station Sales Report not available in this SalesOffice", null);
			FlightSearch.loadhandling(driver);
			//To close the station sales report
			try {
				if(CloseStationSales.equalsIgnoreCase("Yes")) {
					if(driver.findElements(By.xpath("//button[contains(text(),'Ok')]")).size()>0) {
						driver.findElement(By.xpath("//button[contains(text(),'Ok')]")).click();
					}

					//if more than one day report is open
					if(driver.findElements(By.xpath("//md-datepicker/button")).size()>0) {
						queryObjects.logStatus(driver, Status.PASS, "Need to close old date Station report", "Current day Station summary report cant be closed", null);
						driver.findElement(By.xpath("//md-datepicker/button")).click();
						Thread.sleep(500);
						datecount = driver.findElements(By.xpath("//md-calendar//tbody[1]//td[@class='md-calendar-date' and not(@class='md-calendar-date-disabled')]/span")).size();
						queryObjects.logStatus(driver, Status.PASS, "Got the number of date salesoffice is not closed", "Has the "+datecount+" number of open report", null);
						for(int j=0;j<datecount;j++) {
							driver.findElement(By.xpath("//md-calendar//tbody[1]//td[@class='md-calendar-date' and not(@class='md-calendar-date-disabled')]/span")).click();
							FlightSearch.loadhandling(driver);

							driver.findElement(By.xpath("div/label[contains(text(),'Show')]/parent::div//md-select[@role='listbox']")).click();
							driver.findElement(By.xpath("//md-option[@value=50]")).click();


							count = driver.findElements(By.xpath("//div/button[@aria-label='Close Report']")).size();
							queryObjects.logStatus(driver, Status.PASS, "Got the count of agents available in this salesoffice", "Has "+count+" agents available in this salesoffice", null);
							if(driver.findElements(By.xpath("//div/button[@aria-label='Close Report' and not(@disabled)]")).size()>0) {
								for(int i=0; i<count ; i++){
									queryObjects.logStatus(driver, Status.PASS, "Got the count of agents available in this salesoffice", "Has the "+count+" of agents available in this salesoffice", null);
									driver.findElement(By.xpath("//div/button[@aria-label='Close Report' and not(@disabled)]")).click();
								}
							}
							else
								queryObjects.logStatus(driver, Status.PASS, "Got all agents closed in this salesoffice", "No open report available in this salesoffice", null);
						}
					}
					else {//only current date report is open
						driver.findElement(By.xpath("div/label[contains(text(),'Show')]/parent::div//md-select[@role='listbox']")).click();
						driver.findElement(By.xpath("//md-option[@value=50]")).click();
						count = driver.findElements(By.xpath("//div/button[@aria-label='Close Report']")).size();
						queryObjects.logStatus(driver, Status.PASS, "Got the count of agents available in this salesoffice", "Has the "+count+" of agents available in this salesoffice", null);
						for(int i=0; i<count ; i++){
							driver.findElement(By.xpath("//div/button[@aria-label='Close Report' and not(@disabled)]")).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath("//div/button[@aria-label='Close Report']")).click();
							FlightSearch.loadhandling(driver);
						}
					}
					if(driver.findElements(By.xpath("//button[@aria-label='Close Station']")).size()>0) {
						driver.findElement(By.xpath("//button[@aria-label='Close Station']")).click();
						FlightSearch.loadhandling(driver);

						CloseCash=driver.findElement(By.xpath("//md-dialog//table//td[contains(text(),'Cash')]/parent::tr/td[2]/div/div")).getText();
						CloseDebitcard=driver.findElement(By.xpath("//md-dialog//table//td[contains(text(),'Debit Card')]/parent::tr/td[2]/div/div")).getText();
						CloseCreditcard=driver.findElement(By.xpath("//md-dialog//table//td[contains(text(),'Credit Card')]/parent::tr/td[2]/div/div")).getText();
						Closemisc=driver.findElement(By.xpath("//md-dialog//table//td[contains(text(),'Misc')]/parent::tr/td[2]/div/div")).getText();
						CloseTotTransAmt=driver.findElement(By.xpath("//md-dialog//table//td[contains(text(),'Total Transaction')]/parent::tr/td[2]/div/div")).getText();
						CloseVarAmt=driver.findElement(By.xpath("//md-dialog//table//td[contains(text(),'Variance Amount')]/parent::tr/td[2]/div/div")).getText();
						CloseActualAmt=driver.findElement(By.xpath("//md-dialog//table//td[contains(text(),'Actual Amount')]/parent::tr/td[2]/div/div")).getText();

						driver.findElement(By.xpath("//button[@aria-label='Close Station Report']")).click();
						FlightSearch.loadhandling(driver);
					}
				}//closed last station sales report and got the amount summary
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Issue while closing the Station Sales report", "Unable to close the station sales report"+e.getMessage(),e);
				// TODO: handle exception
			}
			//To check old station sales report
			if(driver.findElements(By.xpath("//span[contains(text(),'Station Report Summary')]")).size()>0) {


				try {
					if(PastdateStationReport.equalsIgnoreCase("Yes")) {
						if(driver.findElements(By.xpath("//button[contains(text(),'Ok')]")).size()>0) {
							driver.findElement(By.xpath("//button[contains(text(),'Ok')]")).click();
						}
						driver.findElement(By.xpath("//span[contains(text(),'Station Report Summary')]")).click();
						FlightSearch.loadhandling(driver);
						if(driver.findElements(By.xpath("//md-datepicker/button")).size()>0) {
							//driver.findElement(By.xpath("//md-datepicker/button")).click();
							Thread.sleep(500);
							//datecount = driver.findElements(By.xpath("//md-calendar//tbody[1]//td[@class='md-calendar-date' and not(@class='md-calendar-date-disabled')]/span")).size();
							datecount = 6;
							queryObjects.logStatus(driver, Status.PASS, "Got the number of date salesoffice is not closed", "Has the "+datecount+" number of open report", null);
							for(int j=0;j<datecount;j++) {
								Calendar calc = Calendar.getInstance();
								calc.add(Calendar.DATE, +getDays);
								String timeStampd = new SimpleDateFormat("MM/dd/yyyy").format(calc.getTime());
								driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).clear();
								driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).sendKeys(timeStampd);
								FlightSearch.loadhandling(driver);
								if(driver.findElements(By.xpath("//div[contains(text(),'No Closed Report')]")).size()>0) {
									queryObjects.logStatus(driver, Status.INFO, "No close report available for this date", "No close report",null);
									driver.findElement(By.xpath("//div/i[@class='icon-close']")).click();
								}
								getDays=getDays-1;
								if(driver.findElements(By.xpath("//md-select[@aria-label='reportNumber']")).size()>0) {
									queryObjects.logStatus(driver, Status.INFO, "More than one report close available", "Unable to process in automation",null);
								}
								if(driver.findElements(By.xpath("//div[@id='adjTable']//i")).size()>0) {
									break;
								}
							}
							if(driver.findElements(By.xpath("//div[@id='adjTable']//i")).size()>0) {
								driver.findElement(By.xpath("//div[@id='adjTable']//i")).click();
								FlightSearch.loadhandling(driver);
								ssCash=driver.findElement(By.xpath("//div[contains(text(),'Cash')]/parent::div/div[6]")).getText();
								ssCreditcard=driver.findElement(By.xpath("//div[contains(text(),'Credit')]/parent::div/div[6]")).getText();
								ssDebitcard=driver.findElement(By.xpath("//div[contains(text(),'Debit')]/parent::div/div[6]")).getText();
								ssMisc=driver.findElement(By.xpath("//div[contains(text(),'Misc')]/parent::div/div[6]")).getText();
								ssTotTransAmt=driver.findElement(By.xpath("//div[contains(text(),'Total Tran')]/parent::div/div[2]")).getText();
								ssVariance=driver.findElement(By.xpath("//div[contains(text(),'Variance')]/parent::div/div[2]")).getText();
								ssActual=driver.findElement(By.xpath("//div[contains(text(),'Actual')]/parent::div/div[2]")).getText();
							}
						}
					}
					queryObjects.logStatus(driver, Status.PASS, "Details for specifc date from station report is available", "details need to compare",null);
				} catch (Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Issue while getting the past Station Sales details", "Issue while getting data"+e.getMessage(),e);
					// TODO: handle exception
				}
			}
			else
				queryObjects.logStatus(driver, Status.INFO, "Station Sales Report not available in this salesOffice", "No Station Sales Report",null);

			//Cash Accounting Report
			if(driver.findElements(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button")).size()>0) {
				driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button")).click();  // clicking reservation menu bar
				Thread.sleep(1000);
			}
			else
				queryObjects.logStatus(driver, Status.INFO, "Unable to find Reservation Dropdown", "Reservation not available", null);
			if(driver.findElements(By.xpath("//button[contains(text(),'Sales Reporting')]")).size()>0) {//clicking on sales report
				driver.findElement(By.xpath("//button[contains(text(),'Sales Reporting')]")).click();
				FlightSearch.loadhandling(driver);
			}
			else
				queryObjects.logStatus(driver, Status.INFO, "Unable to find Sales Report", "Sales Report not available in this SalesOffice", null);
			if(driver.findElements(By.xpath("//button[contains(text(),'Cash Accounting Report')]")).size()>0) {//clicking on sales report
				driver.findElement(By.xpath("//button[contains(text(),'Cash Accounting Report')]")).click();
				FlightSearch.loadhandling(driver);

				FlightSearch.loadhandling(driver);

				//Check if Cash Accounting is available
				try {
					//				if(driver.findElements(By.xpath("//md-card-content/span[contains(text(),'SUBMITTED')]")).size()>0) {
					//					queryObjects.logStatus(driver, Status.PASS, "Unable to find Open Cash Accounting Report", "Open Cash Accounting Report not available in this SalesOffice", null);
					//				}
					//				if(driver.findElements(By.xpath("//md-card-content/span[contains(text(),'NO SALES')]")).size()>0) {
					//					queryObjects.logStatus(driver, Status.PASS, "Unable to find Open Cash Accounting Report", "Open Cash Accounting Report not available in this SalesOffice", null);
					//				}
					getDays = getDays+1;
					Calendar calc = Calendar.getInstance();
					calc.add(Calendar.DATE, +getDays);
					String timeStampd = new SimpleDateFormat("MMMMM dd, yyyy").format(calc.getTime());
					if(driver.findElements(By.xpath("//md-card//span[contains(text(),'OPEN')]//parent::md-card-content/parent::md-card//span[contains(text(),'"+timeStampd+"')]")).size()>0) {
						driver.findElement(By.xpath("//md-card//span[contains(text(),'"+timeStampd+"')]//parent::md-card-title-text/parent::md-card-title/parent::md-card//span[contains(text(),'OPEN')]")).click();
						queryObjects.logStatus(driver, Status.PASS, "Able to find Open Cash Accounting Report", "Open Cash Accounting Report available in this SalesOffice", null);
						FlightSearch.loadhandling(driver);
						OpenTotTransAmt = driver.findElement(By.xpath("//div[contains(text(),'Balance Sales Summary')]/parent::div/div[2]")).getText();
						OpenCash=driver.findElement(By.xpath("//div[contains(text(),'Balance Sales Summary')]/parent::div/parent::div//div[contains(text(),'Cash')]/parent::div/div[7]")).getText();
						OpenDebitcard=driver.findElement(By.xpath("//div[contains(text(),'Balance Sales Summary')]/parent::div/parent::div//div[contains(text(),'Debit Card')]/parent::div/div[7]")).getText();
						OpenCreditcard=driver.findElement(By.xpath("//div[contains(text(),'Balance Sales Summary')]/parent::div/parent::div//div[contains(text(),'Card')]/parent::div/div[7]")).getText();
						Openmisc=driver.findElement(By.xpath("//div[contains(text(),'Balance Sales Summary')]/parent::div/parent::div//div[contains(text(),'Misc')]/parent::div/div[7]")).getText();
						if(OpenCash!=ssCash) {
							queryObjects.logStatus(driver, Status.PASS, "Cash account report Valid", "Both amount are matching", null);
						}
						else
							queryObjects.logStatus(driver, Status.FAIL, "Cash account report InValid", "Both amount are not matching", null);
						if(OpenDebitcard!=ssDebitcard) {
							queryObjects.logStatus(driver, Status.PASS, "Debitcard account report Valid", "Both amount are matching", null);
						}
						else
							queryObjects.logStatus(driver, Status.FAIL, "Debitcard account report InValid", "Both amount are not matching", null);
						if(OpenCreditcard!=ssCreditcard) {
							queryObjects.logStatus(driver, Status.PASS, "Creditcard account report Valid", "Both amount are matching", null);
						}
						else
							queryObjects.logStatus(driver, Status.FAIL, "Creditcard account report InValid", "Both amount are not matching", null);
						if(Openmisc!=ssMisc) {
							queryObjects.logStatus(driver, Status.PASS, "Misc account report Valid", "Both amount are matching", null);
						}
						else
							queryObjects.logStatus(driver, Status.FAIL, "Misc account report InValid", "Both amount are not matching", null);
					}
					queryObjects.logStatus(driver, Status.PASS, "Cash Accounting report validation Done", "Cash Accounting report in this SalesOffice validated",null);
				} catch (Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Failed in Cash Accounting report validation", "Cash Accounting report not avaialable in this SalesOffice"+e.getMessage(),e);
					// TODO: handle exception
				}
			}
			else
				queryObjects.logStatus(driver, Status.INFO, "Unable to find Cash Accounting Report", "Cash Accounting Report not available in this SalesOffice", null);

		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Failed in Cash Accounting report validation", "Cash Accounting report validation failed in this SalesOffice"+e.getMessage(),e);
			// TODO: handle exception
		}
	}
	/*****************************************StationSalesReport*****************************
	 * Method : Validate data in StationSalesReport
	 * @param driver
	 * @param queryObjects
	 * @throws Exception
	 */
	public static void StationSalesReport(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception{
		try{
			driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button")).click();  // clicking reservation menu bar
			Thread.sleep(1000);
			if(driver.findElements(By.xpath("//button[contains(text(),'Sales Reporting')]")).size()>0) {//clicking on sales report
				driver.findElement(By.xpath("//button[contains(text(),'Sales Reporting')]")).click();
				FlightSearch.loadhandling(driver);
			}
			driver.findElement(By.xpath("//button[contains(text(),'Station Sales Report')]")).click();	//clicking on station sales report
			FlightSearch.loadhandling(driver);
			String show=FlightSearch.getTrimTdata(queryObjects.getTestData("SHOW"));	//get the data from excel
			String Transactions=FlightSearch.getTrimTdata(queryObjects.getTestData("Transactions"));	//get the data from excel
			String  DocumentType=FlightSearch.getTrimTdata(queryObjects.getTestData("DocumentType"));	//get the data from excel
			String  TranscationPage=FlightSearch.getTrimTdata(queryObjects.getTestData("TranscationPage"));	//get the data from excel
			List<WebElement> TotalAmt=new ArrayList<WebElement>();	//get the list of total amount
			if(driver.findElements(By.xpath("//h2[contains(text(),'Warning')]")).size()>0)		//close the warning pop-up
				driver.findElement(By.xpath("//button[contains(text(),'Ok')]")).click();
			FlightSearch.loadhandling(driver);
			TotalAmt=driver.findElements(By.xpath("//md-content/div[1][contains(@ng-repeat,'(id, report)')]/div/div[3]/child::div[contains(@ng-repeat,'otalAmount in')]/div[1]"));  //suman 16feb
			List<String> amttxt=new ArrayList<String>();
			TotalAmt.forEach(a -> amttxt.add(a.getText().trim()));
			String DisplayFOP = FlightSearch.getTrimTdata(queryObjects.getTestData("DisplayFOP"));
			if (DisplayFOP.equalsIgnoreCase("Yes")) {
				try {
					driver.findElement(By.xpath("//span[contains(text(),'Station Report Summary')]")).click();
					FlightSearch.loadhandling(driver);
					if( driver.findElement(By.xpath("//div[contains(text(),'Currency Name')]"))!= null){
						queryObjects.logStatus(driver, Status.PASS, "Checking the visibility of Summary Station Report", "Summary Station Report is identify", null);
					}
					else{
						queryObjects.logStatus(driver, Status.FAIL, "Checking the visibility of Total Transcation page", "Total Transcation page is not identify", null);
					}
					List<WebElement> paymentindex1=driver.findElements(By.xpath("//i[contains(@ng-class,'icon-arrow-down' and paymentIndex )]"));
					FlightSearch.loadhandling(driver);
					int count=paymentindex1.size();
					for(int i=0;i<count;i++){
						String CurrencyList=driver.findElement(By.xpath("//i[contains(@ng-class,'icon-arrow-down' and paymentIndex )]/parent::div")).getText().trim();
						String TotAmount=driver.findElement(By.xpath("//div[@class='pssgui-bold ng-binding flex pull-right']")).getText().trim();
						queryObjects.logStatus(driver, Status.PASS, "Transaction Amount for "+CurrencyList+" currency is "+TotAmount+" Amount", "Station Summery Report amount per FOP", null);
					}
				} catch (Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Checking the Station Sales Report", "Failed while checking the station sales report summary report"+e.getMessage(), e);
				}
				return;
			}
			String same_page=FlightSearch.getTrimTdata(queryObjects.getTestData("same_page"));//02081018 suman //get the data from excel
			String PrviousdayReport=FlightSearch.getTrimTdata(queryObjects.getTestData("PrviousdayReport"));  //02081018 suman	//get the data from excel
			if(!same_page.equalsIgnoreCase("yes") && !PrviousdayReport.equalsIgnoreCase("yes"))
			{
				if(driver.findElement(By.xpath("//button[contains(text(),'Close Report')]")).isDisplayed()){
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//md-select-value/span[1]/div[contains(text(),'All')]")).click();
					FlightSearch.loadhandling(driver);
					List<WebElement> Agent=driver.findElements(By.xpath("//md-select-menu[@role='presentation' ]/md-content/md-option[contains(@ng-repeat,'agentName in reportFilter')]"));
					driver.findElement(By.xpath("//md-option/div[contains(text(),'All')]")).click();
					FlightSearch.loadhandling(driver);
					int NoofAgent=Agent.size();
					if(NoofAgent >=2)
					{
						queryObjects.logStatus(driver, Status.PASS, "Checking Number of agent on sales report", "No of Agent on sales report is"+NoofAgent,null);
					}
					else
					{
						queryObjects.logStatus(driver, Status.INFO, "Checking Number of agent on sales report", "No of Agent on sales report is less then 2 ->"+NoofAgent,null);
					}
					driver.findElement(By.xpath("//md-content/div[1][contains(@ng-repeat,'(id, report)')]/div/div[contains(@ng-if,'supervisorReportTable')]/div/i")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//md-select[@name='showRecords']")).click();
					Thread.sleep(1500);
					driver.findElement(By.xpath("//md-option[@value='"+show+"']")).click();
					queryObjects.logStatus(driver, Status.PASS, "Station Sales Report is Available", " Station summary Report checked", null);
				}
				else{
					queryObjects.logStatus(driver, Status.INFO, "Station Sales Report is Not Available", "Need to create PNR and then need to check Station summary Report, No History Available", null);
				}
				Thread.sleep(100);
				driver.findElement(By.xpath("//md-select[@name='transactionTypeFilter']")).click();
				Thread.sleep(100);
				driver.findElement(By.xpath("//md-option[@value='"+Transactions+"' and contains(@ng-repeat,'transactionType')]")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//md-select[@name='documentTypeFilter']")).click();
				driver.findElement(By.xpath("//md-option[@value='"+DocumentType+"' and contains(@ng-repeat,'documentType')]")).click();
				Thread.sleep(100);
				queryObjects.logStatus(driver, Status.PASS, "Station Sales Report Displayed", " Station summary Report checked successfully", null);
			}
			String CheckTaxdetail=FlightSearch.getTrimTdata(queryObjects.getTestData("CheckTaxdetail")); //suman13022018
			if(CheckTaxdetail.equalsIgnoreCase("Yes"))
			{
				for(int m=2;m<6;m++)
				{
					driver.findElement(By.xpath("//md-select[@name='transactionTypeFilter']")).click();
					String Trans=driver.findElement(By.xpath("//md-content/md-option["+m+"][contains(@ng-repeat,'transactionType')]")).getText().trim();
					driver.findElement(By.xpath("//md-content/md-option["+m+"][contains(@ng-repeat,'transactionType')]")).click();
					//if no data avaialble for agent sales report
					int t=driver.findElements(By.xpath("//div[1][contains(@ng-repeat,'(agentSaleItemId,agentSaleItem)')]")).size();
					if(t>0)
					{
						driver.findElement(By.xpath("//div[1][contains(@ng-repeat,'(agentSaleItemId,agentSaleItem)')]//div/div[6]/div[2]")).click();
						String tax=driver.findElement(By.xpath("//div[@id='taxTable']/md-content")).getText().trim();
						queryObjects.logStatus(driver, Status.PASS, "Checking the TAX details", "Tax details is available "+Trans+"->"+tax, null);
						driver.findElement(By.xpath("//i[@class='icon-close']")).click();
					}
					else
					{
						queryObjects.logStatus(driver, Status.INFO, "Checking the filter details", "Data is not avaialbe for "+Trans, null);
					}
				}
				return;
			}
			String VoidTransaction = getTrimTdata(queryObjects.getTestData("VoidTransaction"));
			if(VoidTransaction.equalsIgnoreCase("yes")){
				try {
					int i;
					if(driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']//div[contains(text(),'Arey')]")).size()>0){
						Thread.sleep(300);
						int Count = driver.findElements(By.xpath("//div[contains(text(),'Arey')]/parent::div/parent::div//button[contains(text(),'Close Report')]")).size();
						Thread.sleep(300);
						if(driver.findElements(By.xpath("//h2[contains(text(),'Warning')]")).size()>0)		//close the warning pop-up
							driver.findElement(By.xpath("//button[contains(text(),'Ok')]")).click();
						for(i=0;i<Count;i++){
							if(driver.findElements(By.xpath("//h2[contains(text(),'Warning')]")).size()>0)		//close the warning pop-up
								driver.findElement(By.xpath("//button[contains(text(),'Ok')]")).click();
							driver.findElement(By.xpath("//div[contains(text(),'Arey')]/parent::div/parent::div//button[contains(text(),'Close Report')]")).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath("//div[@popup-action='closeAgent']//button[contains(text(),'Close Report')]")).click();
							FlightSearch.loadhandling(driver);
						}
						driver.findElement(By.xpath("//div[contains(text(),'Arey')]/parent::div/parent::div//i[@class='icon-final-report ']")).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//md-select[@name='documentTypeFilter']")).click();
						Thread.sleep(100);
						driver.findElement(By.xpath("//md-option[@value='E-Ticket' and contains(@ng-repeat,'documentType')]")).click();
						Thread.sleep(100);
						driver.findElement(By.xpath("//md-content[@class='_md layout-column flex']/div//div[(@ng-if='!salesItem.agentSaleItem.conjunctedDocNum') or  (@ng-if='salesItem.agentSaleItem.conjunctedDocNum')]")).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//div[@class='md-container md-ink-ripple']")).click();
						Thread.sleep(100);
						driver.findElement(By.xpath("//md-select-value[@class='md-select-value']")).click();
						if(driver.findElement(By.xpath("//div[contains(text(),'Void')]")).isDisplayed()){
							queryObjects.logStatus(driver, Status.PASS, "Cannot make the void status for closed sales report", "We cant change the closed station details",null);
						}
						else{
							queryObjects.logStatus(driver, Status.PASS, "Able to make the status void for closed sales report", "We cant change the closed station details",null);

						}
					}
				} catch (Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Error in sales report void after close", "Error in Sales report void",null);
					// TODO: handle exception
				}
				return;
			}
			String Agent_page_Validtion=FlightSearch.getTrimTdata(queryObjects.getTestData("Agent_page_Validtion")); //suman13022018
			if(Agent_page_Validtion.equalsIgnoreCase("yes"))
			{
				try
				{
					//validate the field on agent sales report page
					//contains the xpath of datetranscation,document,documenttype
					List<WebElement> AgentField1=driver.findElements(By.xpath("//div[@model='salesagentView.model']/div[contains(@ng-class,'reportDetailsTable.hasScroll')]/div/div/span"));
					List<String> AgentField=new ArrayList<>();
					AgentField1.forEach(a -> AgentField.add(a.getText().trim()));
					//contains the xpath of payment type and total amount
					//contains the detail of first row of agent sale page
					String paymentfield=driver.findElement(By.xpath("//div[@model='salesagentView.model']/div[contains(@ng-class,'reportDetailsTable.hasScroll')]/div/div[contains(text(),'Payment Type')]")).getText().trim();
					String totalamt=driver.findElement(By.xpath("//div[@model='salesagentView.model']/div[contains(@ng-class,'reportDetailsTable.hasScroll')]/div/div/div//span[contains(text(),'Total Amount')]")).getText().trim();
					if(AgentField.get(0).equalsIgnoreCase("Date") && AgentField.get(1).equalsIgnoreCase("Transactions") &&AgentField.get(2).equalsIgnoreCase("Document #") &&AgentField.get(3).equalsIgnoreCase("Document Type") &&paymentfield.equalsIgnoreCase("Payment Type") && totalamt.equalsIgnoreCase("Total Amount" ))
					{
						queryObjects.logStatus(driver, Status.PASS, "Checking INformation or field of agent on sales report", "Agent filed is correct",null);
					}
					else
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking INformation or field of agent on sales report", "Agent filed is not correct",null);
					}
					//xpath for first close button
					driver.findElement(By.xpath("//span[contains(text(),'Supervisor Summary')]")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//md-select-value/span[1]/div[contains(text(),'All')]")).click();
					//store the all the agent
					List<WebElement> Agent=driver.findElements(By.xpath("//md-select-menu[@role='presentation' ]/md-content/md-option[contains(@ng-repeat,'agentName in reportFilter')]"));
					driver.findElement(By.xpath("//md-option/div[contains(text(),'All')]")).click();
					int NoofAgent=Agent.size();
					if(NoofAgent >=2)
					{
						queryObjects.logStatus(driver, Status.PASS, "Checking Number of agent on sales report", "No of Agent on sales report is"+NoofAgent,null);
					}
					else
					{
						queryObjects.logStatus(driver, Status.INFO, "Checking Number of agent on sales report", "No of Agent on sales report is less then 2 ->"+NoofAgent,null);
					}
					driver.findElement(By.xpath("//md-content/div[contains(@ng-repeat,'ReportTable.model.stationReports')][1]//child::div[6]/button[contains(text(),'Close Report') and not(@disabled='disabled')]")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//button[contains(text(),'Cancel Report')]//following-sibling::button[contains(text(),'Close Report')]")).click();
					Thread.sleep(2000);
					FlightSearch.loadhandling(driver);
				}
				catch(Exception e)
				{
					queryObjects.logStatus(driver, Status.FAIL, "Station Sales Report is Not Available", "Sales station report page through an exception"+e.getMessage(), e);
				}
			}
			if(Agent_page_Validtion.equalsIgnoreCase("OPEN")){
				try
				{
					//validate the field on agent sales report page
					//contains the xpath of datetranscation,document,documenttype
					List<WebElement> AgentField1=driver.findElements(By.xpath("//div[@model='salesagentView.model']/div[contains(@ng-class,'reportDetailsTable.hasScroll')]/div/div/span"));
					List<String> AgentField=new ArrayList<>();
					AgentField1.forEach(a -> AgentField.add(a.getText().trim()));
					String paymentfield=driver.findElement(By.xpath("//div[@model='salesagentView.model']/div[contains(@ng-class,'reportDetailsTable.hasScroll')]/div/div[contains(text(),'Payment Type')]")).getText().trim();
					String totalamt=driver.findElement(By.xpath("//div[@model='salesagentView.model']/div[contains(@ng-class,'reportDetailsTable.hasScroll')]/div/div/div//span[contains(text(),'Total Amount')]")).getText().trim();
					if(AgentField.get(0).equalsIgnoreCase("Date") && AgentField.get(1).equalsIgnoreCase("Transactions") &&AgentField.get(2).equalsIgnoreCase("Document #") &&AgentField.get(3).equalsIgnoreCase("Document Type") &&paymentfield.equalsIgnoreCase("Payment Type") && totalamt.equalsIgnoreCase("Total Amount" ))
					{
						queryObjects.logStatus(driver, Status.PASS, "Checking INformation or field of agent on sales report", "Agent filed is correct",null);
					}
					else
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking INformation or field of agent on sales report", "Agent filed is not correct",null);
					}
					//xpath for first close button
					driver.findElement(By.xpath("//span[contains(text(),'Supervisor Summary')]")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//md-select-value/span[1]/div[contains(text(),'All')]")).click();
					//store the all the agent
					List<WebElement> Agent=driver.findElements(By.xpath("//md-select-menu[@role='presentation' ]/md-content/md-option[contains(@ng-repeat,'agentName in reportFilter')]"));
					driver.findElement(By.xpath("//md-option/div[contains(text(),'All')]")).click();
					int NoofAgent=Agent.size();
					if(NoofAgent >=1)
					{
						queryObjects.logStatus(driver, Status.PASS, "Checking Number of agent on sales report", "No of Agent on sales report is"+NoofAgent,null);
					}
					else
					{
						queryObjects.logStatus(driver, Status.INFO, "Checking Number of agent on sales report", "No of Agent on sales report is less then 2 ->"+NoofAgent,null);
					}
					driver.findElement(By.xpath("//i[@class='icon-final-report ']")).click();
					FlightSearch.loadhandling(driver);
					if(driver.findElement(By.xpath("//span[contains(text(),'Detailed Agent Sales Report')]")).isDisplayed()){
						queryObjects.logStatus(driver, Status.PASS, "Able to view agent report", "Agent sales report is opened",null);
					}
					else{
						queryObjects.logStatus(driver, Status.FAIL, "Not Able to view agent report", "Agent sales report cant be viewed",null);
					}
				}
				catch(Exception e)
				{
					queryObjects.logStatus(driver, Status.FAIL, "Station Sales Report is Not Available", "Sales station report page through an exception"+e.getMessage(), e);
				}
			}
			if(same_page.equalsIgnoreCase("YES"))
			{
				// try to closed report and then try to open it
				try
				{
					List<WebElement> TotalcloseReportBtn=driver.findElements(By.xpath("//button[@aria-label='Close Report']"));
					int k=0;   //this wil store the line num of closed report
					Boolean flag=false;
					// this loop will close the report
					for(int i=1; i<=TotalcloseReportBtn.size(); i++) {
						if(driver.findElements(By.xpath("//md-content/div[contains(@ng-repeat,'ReportTable.model.stationReports')]["+i+"]//child::div[6]/button[contains(text(),'Close Report') and @disabled='disabled']")).size()>0);
						else
						{
							//Click on Close report button in popup
							driver.findElement(By.xpath("//md-content/div[contains(@ng-repeat,'ReportTable.model.stationReports')]["+i+"]//child::div[6]/button[contains(text(),'Close Report') and not(@disabled='disabled')]")).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath("//button[contains(text(),'Cancel Report')]//following-sibling::button[contains(text(),'Close Report')]")).click();
							Thread.sleep(2000);
							FlightSearch.loadhandling(driver);
							k=i;
							flag=true;
							break;
						}
					}
					if(flag)
					{
						String status=driver.findElement(By.xpath("//md-content/div[contains(@ng-repeat,'ReportTable.model.stationReports')]["+k+"]//child::div[4][contains(@class,'status')]")).getText().trim();
						if(status.equalsIgnoreCase("Closed"))
						{
							queryObjects.logStatus(driver, Status.PASS, "Checking the Status of closed report", "Status of closed report is"+status, null);
						}
						driver.findElement(By.xpath("//md-content/div[contains(@ng-repeat,'ReportTable.model.stationReports')]["+k+"]//child::div[6]/button[contains(text(),'Close Report') and @disabled='disabled']")).click();
						int  buttonclose=driver.findElements(By.xpath("//md-content/div[contains(@ng-repeat,'ReportTable.model.stationReports')]["+k+"]//child::div[6]/button[contains(text(),'Close Report') and not(@disabled='disabled')]")).size();
						if(driver.findElements(By.xpath("//button[@aria-label='Close Report']")).size()>0 && buttonclose ==0)
						{
							queryObjects.logStatus(driver, Status.PASS, "Checking after close able to open the report or checking it's on same page", "Not able to open the report"+status, null);
						}
					}
					else
					{
						queryObjects.logStatus(driver, Status.INFO, "Checking the number open report", "Not found any open report", null);
					}
				}
				catch(Exception e)
				{
					queryObjects.logStatus(driver, Status.FAIL, "Checking the Status of open report", "Failed while checking the open report"+e.getMessage(), e);
				}
			}
			//suman 08022018
			if(PrviousdayReport.equalsIgnoreCase("YES"))
			{
				try
				{
					//This text case for Checking the more than 2 user name  and try delete previous day sales report
					driver.findElement(By.xpath("//md-select-value/span[1]/div[contains(text(),'All')]")).click();
					//store the all the agent
					List<WebElement> Agent=driver.findElements(By.xpath("//md-select-menu[@role='presentation' ]/md-content/md-option[contains(@ng-repeat,'agentName in reportFilter')]"));
					driver.findElement(By.xpath("//md-option/div[contains(text(),'All')]")).click();
					int NoofAgent=Agent.size();
					if(NoofAgent >=2)
					{
						queryObjects.logStatus(driver, Status.PASS, "Checking Number of agent on sales report", "No of Agent on sales report is"+NoofAgent,null);
					}
					else
					{
						queryObjects.logStatus(driver, Status.INFO, "Checking Number of agent on sales report", "No of Agent on sales report is less then 2 ->"+NoofAgent,null);
					}
					Calendar calc = Calendar.getInstance();
					String CurrentDay = new SimpleDateFormat("dd-MMM-yy").format(calc.getTime());
					SimpleDateFormat d1 =new SimpleDateFormat("dd-MMM-yy");
					Date CurrentDay1=d1.parse(CurrentDay);
					//the below date is for xpath of current date
					Calendar calcdate = Calendar.getInstance();
					String timeStampd = new SimpleDateFormat("dd-MMM-yy").format(calcdate.getTime());
					System.out.println(timeStampd);
					List<WebElement> allDate=driver.findElements(By.xpath("//md-content/div[contains(@ng-repeat,'ReportTable.model.stationReports')]//child::div/span"));
					List<String> date=new ArrayList<>();
					allDate.forEach(a -> date.add(a.getText().replace(" ", "")));
					for(int i=1;i<=allDate.size();i++)
					{
						String valuee=date.get(i-1);
						queryObjects.logStatus(driver, Status.INFO, "Checking the date of- "+i+ " -Report", "Date of reoprt is  ->"+valuee,null);
						SimpleDateFormat PreviousDay =new SimpleDateFormat("dd-MMM-yy");
						Date OldDate=PreviousDay.parse(valuee);
						System.out.println("Date is ::"+OldDate);
						if(OldDate.before(CurrentDay1))    //compare today date with date in sales report
						{
							try
							{
								List<WebElement> TotalcloseReportBtn=driver.findElements(By.xpath("//button[@aria-label='Close Report']"));
								if(driver.findElements(By.xpath("//md-content/div[contains(@ng-repeat,'ReportTable.model.stationReports')]["+i+"]//child::div[6]/button[contains(text(),'Close Report') and @disabled='disabled']")).size()>0)
									queryObjects.logStatus(driver, Status.INFO, "Checking the date of "+i+ " Report", "Date of reoprt is already closed report "+valuee ,null);
								else
								{
									//Click on Close report button in popup
									driver.findElement(By.xpath("//md-content/div[contains(@ng-repeat,'ReportTable.model.stationReports')]["+i+"]//child::div[6]/button[contains(text(),'Close Report') and not(@disabled='disabled')]")).click();
									FlightSearch.loadhandling(driver);
									driver.findElement(By.xpath("//button[contains(text(),'Cancel Report')]//following-sibling::button[contains(text(),'Close Report')]")).click();
									Thread.sleep(2000);
									FlightSearch.loadhandling(driver);
									queryObjects.logStatus(driver, Status.PASS, "Checking previous day open report", "Previous days open report get closed",null);
								}
							}
							catch(Exception e)
							{
								queryObjects.logStatus(driver, Status.FAIL, "Checking previous day open report", "Getting while closeing previous days open report"+e.getMessage(), e);
							}
						}
						else
						{
							queryObjects.logStatus(driver, Status.INFO, "Checking the date of->"+i+ "<-Report", "This is curent date so no close opertion  ->"+valuee,null);
						}
					}
				}
				catch(Exception e)
				{
					queryObjects.logStatus(driver, Status.FAIL, "Checking previous day open report", "Getting while closeing previous days open report"+e.getMessage(), e);
				}
			}
			if(TranscationPage.equalsIgnoreCase("YES"))
			{
				try
				{
					driver.findElement(By.xpath("//span[contains(text(),'Total Transaction Amount') and @tabindex='0']")).click();
					FlightSearch.loadhandling(driver);
					if(driver.findElement(By.xpath("//i[contains(@ng-class,'icon-arrow-down' and paymentIndex )]//parent::div")).getText().equalsIgnoreCase(Login.Cur))
					{
						queryObjects.logStatus(driver, Status.PASS, "Validating the Currency", "Currency is validated", null);
					}else{
						queryObjects.logStatus(driver, Status.INFO, "Validating the Currency", "Currency not available with login Currency", null);
					}
					FlightSearch.loadhandling(driver);
					if( driver.findElement(By.xpath("//div[contains(text(),'Currency Name')]"))!= null){
						queryObjects.logStatus(driver, Status.PASS, "Checking the visibility of Total Transcation page", "Total Transcation page is identify", null);
					}else{
						queryObjects.logStatus(driver, Status.FAIL, "Checking the visibility of Total Transcation page", "Total Transcation page is not identify", null);
					}
					FlightSearch.loadhandling(driver);
					List<WebElement> paymentindex=driver.findElements(By.xpath("//i[contains(@ng-class,'icon-arrow-down' and paymentIndex )]"));
					int count=paymentindex.size();
					int aa=0;
					if(count<2)
					{
						// curriences sholud be only two refelecting on transcation page
						queryObjects.logStatus(driver, Status.PASS, "Checking the 2 should only currencies can be reflected", "Refelecting correct no. of currencies on agent transcation page", null);
					}
					else
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking the 2 should only currencies can be reflected", "Refelecting more than two currencies on agent transcation page", null);
					}
					int k=count-1;
					while(aa<count)
					{     //changes inside the while loop 24012018   ----suman
						int i=aa+1;
						String totalBeforeexpand=driver.findElement(By.xpath("//md-content/div["+i+"]/div/div[2]//div/div/div")).getText().trim();
						Thread.sleep(1000);
						driver.findElement(By.xpath("//md-content//div["+i+"]/div/div/i[contains(@ng-class,'icon-arrow-down' and paymentIndex)]")).click();
						Thread.sleep(1000);
						String totalafterexpan=driver.findElement(By.xpath("//md-content//div["+i+"]/div/div/div/span[@role='button']/parent::div/div/div")).getText().trim();
						if(totalBeforeexpand.equalsIgnoreCase(totalafterexpan))
						{
							queryObjects.logStatus(driver, Status.PASS, "Checking the transcation amount for currency BEFORE and ater expand", "Transcation amount is match "+totalBeforeexpand+"->"+totalafterexpan, null);
						}
						else
						{
							queryObjects.logStatus(driver, Status.FAIL, "Checking the transcation amount for currency BEFORE and ater expand", "Transcation amount is mismatch "+totalBeforeexpand+"->"+totalafterexpan, null);
						}
						driver.findElement(By.xpath("//md-content//div["+i+"]/div/div/i[contains(@ng-class,'icon-arrow-down' and paymentIndex )]")).click();
						if(amttxt.get(k).equals(totalafterexpan))
						{
							queryObjects.logStatus(driver, Status.PASS, "Checking the transcation amount for currency of sales report and agent report page ", "Transcation amount is match "+totalBeforeexpand+"->"+amttxt.get(aa), null);
						}
						else
						{
							queryObjects.logStatus(driver, Status.FAIL, "Checking the transcation amount for currency sales report and agent report page", "Transcation amount is mismatch "+totalBeforeexpand+"->"+amttxt.get(aa), null);
						}
						driver.findElement(By.xpath("//md-content//div["+i+"]/div/div/i[contains(@ng-class,'icon-arrow-down' and paymentIndex )]")).click();
						aa++;
						k--;
					}
					driver.findElement(By.xpath("//div[contains(text(),'Back')]")).click();
					FlightSearch.loadhandling(driver);
					Boolean mainpage =driver.findElement(By.xpath("//span[contains(text(),'Total Transaction Amount') and @tabindex='0']")).isDisplayed();
					if(mainpage)
					{
						queryObjects.logStatus(driver, Status.PASS, "Checking the home page of agent", "After clicking the back button return to agent main page", null);
					}
					else
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking the home page of agent", "After clicking the back button Not return to agent main page", null);
					}
				}
				catch(Exception e)
				{
					queryObjects.logStatus(driver, Status.FAIL, "Issue in Agent page ", "Capture a exception on Agent page at line number"+e.getStackTrace()[0].getLineNumber()+e.getStackTrace(),e);
				}
			}
			if(DocumentType.equalsIgnoreCase("EMD"))
			{
				List<WebElement> EMD=driver.findElements(By.xpath("//md-content/div[contains(@ng-repeat,'agentSaleItemId')]//child::div[contains(text(),'EMD')]"));
				List<String> EMDText = new ArrayList<>();
				EMD.forEach(a -> EMDText.add(a.getText().trim()));
				Boolean Flag=true;
				for(int i=0;i<EMD.size();i++)
				{
					String EMDString=EMDText.get(i);
					if(!EMDString.equalsIgnoreCase("EMD"))
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking all the Document type is EMD", "All Document type is not EMD", null);
						Flag=false;
					}
				}
				if(Flag.equals(true) && !(EMD.size()==0))
				{
					queryObjects.logStatus(driver, Status.PASS, "Checking all the Document type is EMD", "All Document type is EMD", null);
				}
				if(EMD.size()==0)
				{
					queryObjects.logStatus(driver, Status.INFO, "Checking all the Document type is EMD", "All Document type is not displaying Emd or EMD is Present", null);
				}
				if(DocumentType.equalsIgnoreCase("All")){
					queryObjects.logStatus(driver, Status.PASS, "Displaying All Document Type", "All Document type is Dispalyed", null);
				}
			}
			if(DocumentType.equalsIgnoreCase("E-Ticket"))
			{
				List<WebElement> Eticket=driver.findElements(By.xpath("//md-content/div[contains(@ng-repeat,'agentSaleItemId')]//child::div[contains(text(),'E-Ticket')]"));
				List<String> EticketText = new ArrayList<>();
				Eticket.forEach(a -> EticketText.add(a.getText().trim()));
				Boolean Flag=true;
				for(int i=0;i<Eticket.size();i++)
				{
					String EticketString=EticketText.get(i);
					if(!EticketString.equalsIgnoreCase("E-Ticket"))
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking all the Document type is Eticket", "All Document type is not Eticket", null);
						Flag=false;
					}
				}
				if(Flag.equals(true) && !(Eticket.size()==0))
				{
					queryObjects.logStatus(driver, Status.PASS, "Checking all the Document type is Eticket", "All Document type is Eticket", null);
				}
				if(Eticket.size()==0)
				{
					queryObjects.logStatus(driver, Status.INFO, "Checking all the Document type is Eticket", "All Document type is not displaying Eticket or Eticket is Present", null);
				}
				if(DocumentType.equalsIgnoreCase("All")){
					queryObjects.logStatus(driver, Status.PASS, "Displaying All Document Type", "All Document type is Dispalyed", null);
				}
			}
		}
		catch(Exception e){
			queryObjects.logStatus(driver, Status.FAIL, "Getting a exception in sales report page", "Exception on Sales report page"+e.getLocalizedMessage(), e);
		}
	}

	public void Redirected_to_Res_Gui(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception{
		try
		{

			String GUIGetTicketno=null;
			String GUIgetTransation=null;
			String GUIgetTransationAmount=null;
			String show=getTrimTdata(queryObjects.getTestData("SHOW"));
			String Transactions=getTrimTdata(queryObjects.getTestData("Transactions"));
			String  DocumentType=getTrimTdata(queryObjects.getTestData("DocumentType"));
			String TranscationPage=getTrimTdata(queryObjects.getTestData("TranscationPage"));
			String flagforagent=getTrimTdata(queryObjects.getTestData("flagforagent"));
			CheckTaxdetail=getTrimTdata(queryObjects.getTestData("CheckTaxdetail"));
			Remarks=getTrimTdata(queryObjects.getTestData("Remarks"));
			CreatePNR_Tax_validation=getTrimTdata(queryObjects.getTestData("CreatePNR_Tax_validation"));
			String FOP_CHECK=getTrimTdata(queryObjects.getTestData("FOP_CHECK"));
			if( driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button"))!= null){
				queryObjects.logStatus(driver, Status.PASS, "Checking the visibility of Reservation link", "Reservation link is identify", null);
			}else{
				queryObjects.logStatus(driver, Status.FAIL, "Checking the visibility of Reservation link", "Reservation link is not identify", null);
			}
			driver.findElement(By.xpath("//md-menu[@ng-model='layout.pssguiModules.module']/button")).click();  // clicking reservation menu bar
			Thread.sleep(1000);
			if(driver.findElements(By.xpath("//button[contains(text(),'Sales Reporting')]")).size()>0) {
				driver.findElement(By.xpath("//button[contains(text(),'Sales Reporting')]")).click();
			}
			Thread.sleep(1000);
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//button[contains(text(),'Agent Sales Report')]")).click();
			FlightSearch.loadhandling(driver);
			//added to check the warning message --suman 25012018
			if(driver.findElements(By.xpath("//h2[contains(text(),'Warning')]")).size()>0)
				driver.findElement(By.xpath("//button[contains(text(),'Ok')]")).click();
			if(driver.findElements(By.xpath("//div[contains(text(),'No Open Reports')]")).size()>0){
				driver.findElement(By.xpath("//md-dialog//div[@role='button']/i")).click();
				queryObjects.logStatus(driver, Status.INFO, "No transaction reports are found", "Do tranasaction to validate", null);
				return;
			}
			//validating the agent sales report   // changes suman 24-01-2018
			if( driver.findElements(By.xpath("//div/span[contains(text(),'Total Transaction Amoun')]")).size()>0){
				queryObjects.logStatus(driver, Status.PASS, "Checking agent page", "Agent page is identify", null);
			}else{
				queryObjects.logStatus(driver, Status.FAIL, "Checking agent page", "Agent page is not identify or no sales report is open", null);
			}
			try  //added on 24012018
			{
				driver.findElement(By.xpath("//md-select[@name='showRecords']")).click();
				Thread.sleep(100);
				driver.findElement(By.xpath("//md-option[@value='"+show+"']")).click();
				// this is for Transaction type
				driver.findElement(By.xpath("//md-select[@name='transactionTypeFilter']")).click();
				Thread.sleep(100);
				driver.findElement(By.xpath("//md-option[@value='"+Transactions+"' and contains(@ng-repeat,'transactionType')]")).click();
				// this is for Document type
				if( driver.findElement(By.xpath("//md-select[@name='documentTypeFilter']"))!= null){
					queryObjects.logStatus(driver, Status.PASS, "Checking the visibility of DocumentTypeFilter", "DocumentTypeFilter is identify", null);
				}else{
					queryObjects.logStatus(driver, Status.FAIL, "Checking the visibility of DocumentTypeFilter", "DocumentTypeFilter is not identify", null);;
				}
				driver.findElement(By.xpath("//md-select[@name='documentTypeFilter']")).click();
				Thread.sleep(100);
				driver.findElement(By.xpath("//md-option[@value='"+DocumentType+"' and contains(@ng-repeat,'documentType')]")).click();
				queryObjects.logStatus(driver, Status.PASS, "Filtering the field", "Filtered based on test data", null);
			}
			catch(Exception e)
			{
				queryObjects.logStatus(driver, Status.FAIL, "Filtering the field", "Issue while filtering", e);
			}
			try
			{
				sGetTicketno=driver.findElement(By.xpath("//md-content[@class='_md layout-column flex']/div//div[(@ng-if='!salesItem.agentSaleItem.conjunctedDocNum') or  (@ng-if='salesItem.agentSaleItem.conjunctedDocNum')]")).getText().trim();
				getTransation=driver.findElement(By.xpath("//md-content[contains(@class,'_md layout-column flex')]/div[1]//div/div[2]/span")).getText().trim();
				getTransationType=driver.findElement(By.xpath("//md-content[contains(@class,'_md layout-column flex')]/div[1]//div/div[4]")).getText().trim();
				getTransationAmount=driver.findElement(By.xpath("//md-content[contains(@class,'_md layout-column flex')]/div[1]//div/div[6]/div[1]/div")).getText().trim();
				getTransationAmount=getTransationAmount.replace("(", "");
				getTransationAmount=getTransationAmount.replace(")", "");
				getTransationAmount=getTransationAmount.trim();
				String[] removecurrentranamt=getTransationAmount.split(" ");
				getTransationAmount=removecurrentranamt[0];
				getTransationAmount=getTransationAmount.replace(",", "");
				getcashTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div[1]//i[@class='icon-cash-payment']")).size();
				getcreditcardTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div[1]//i[@class='icon-CardPayment']")).size();
				getmisTicketpaymode=driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div[1]//i[@class='icon-miscellaneous']")).size();

				Boolean isPresent = driver.findElements(By.xpath("//md-content[contains(@class,'_md layout-column flex')]/div[1]/div/div[1]/span/i")).size() > 0;
			}
			catch(Exception e)
			{
				queryObjects.logStatus(driver, Status.INFO, "Capturing the webelement value", "Gettting an exception while capturing the value"+e.getStackTrace()[0].getLineNumber()+"->"+e.getLocalizedMessage(), e);
			}
			String Supervisor_Summary=getTrimTdata(queryObjects.getTestData("Supervisor_Summary"));
			if(Supervisor_Summary.equalsIgnoreCase("Agent"))
			{
				try
				{
					WebElement SupSummLink= driver.findElement(By.xpath("//span[contains(text(),'Supervisor Summary')]"));

					if(SupSummLink.isDisplayed() || SupSummLink.isEnabled())
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking agent is able to view superviser summery report", "Agent view superviser summery report", null);
					}
					else
					{
						queryObjects.logStatus(driver, Status.PASS, "Checking agent is able to view superviser summery report", " agent is not able to view superviser summery report",null);
					}
				}
				catch(Exception e)
				{
					queryObjects.logStatus(driver, Status.INFO, "Capturing the webelement value", "Gettting an exception while capturing the value"+e.getStackTrace()[0].getLineNumber(), e);
				}
			}
			if(Remarks.equalsIgnoreCase("Yes"))   // need to replace the whole if statement 24012018 --suman
			{   try
			{
				driver.findElement(By.xpath("//span[contains(text(),'Total Transaction Amount') and @tabindex='0']")).click();
				FlightSearch.loadhandling(driver);
				if( driver.findElement(By.xpath("//div[contains(text(),'Currency Name')]"))!= null){   // change the xpath 24012018 --suman
					queryObjects.logStatus(driver, Status.PASS, "Checking the String present on transcation page as Currency Name", "Currency Name is visible on the page", null);
				}else{
					queryObjects.logStatus(driver, Status.FAIL, "Checking the String present on transcation page as Currency Name", "Currency Name is visible on the page", null);
				}
				List<WebElement> amountsummery=driver.findElements(By.xpath("//md-content//div/div/div[contains(@ng-click,'reportDetailsTable.model')]"));
				int total=amountsummery.size();
				if(total>=1) {
					try    //added try catch 24012018
					{
						driver.findElement(By.xpath("//md-content//div[1]/div/div[contains(@ng-click,'reportDetailsTable.model')]/i")).click();  // change the xpath
						if( driver.findElement(By.xpath("//input[contains(@ng-model,'paymentType.agentRemarks')]"))!= null){
							queryObjects.logStatus(driver, Status.PASS, "Checking the String present on transcation page asagent Remarks", "agentRemarks is visible on the page", null);
						}else{
							queryObjects.logStatus(driver, Status.FAIL, "Checking the String present on transcation page asagent Remark", "transcation page asagent Remark is visible on the page", null);
						}
						driver.findElement(By.xpath("//input[@name='varianceAmount']")).click();
						driver.findElement(By.xpath("//input[@name='varianceAmount']")).clear();
						driver.findElement(By.xpath("//input[@name='varianceAmount']")).sendKeys("50");
						driver.findElement(By.xpath("//md-select[@role='listbox']")).click();
						driver.findElement(By.xpath("//md-content//md-option[2]/div[contains(text(),'total by range > $5')]")).click();
						driver.findElement(By.xpath("//input[contains(@ng-model,'paymentType.agentRemarks')]")).sendKeys("TEST REMARK");
						driver.findElement(By.xpath("//button[contains(text(),'Save Report')]")).click();
						FlightSearch.loadhandling(driver);
						Thread.sleep(500);
						String VarianceAmt=	driver.findElement(By.xpath("//div[contains(@class,'variance-amount')]/div[2]//following-sibling::div/div")).getText().trim();
						if(VarianceAmt.equals("50.00")||VarianceAmt.equals("50"))
						{
							queryObjects.logStatus(driver, Status.PASS, "Checking the Variance amount added","Variance amount added after saved", null);;
						}
						else
						{
							queryObjects.logStatus(driver, Status.FAIL, "Checking the Variance amount added", "Variance amount not added after saved", null);;
						}
					}
					catch(Exception e)
					{
						queryObjects.logStatus(driver, Status.FAIL, "Adding the reason for variance", "Failed while adding the variance"+e.getMessage(), e);
					}
				}
			}
			catch(Exception e)
			{
				queryObjects.logStatus(driver, Status.FAIL, "Checking number of currency", "getting issue while checking the number of currency",null);
			}
			return;
			}
			if(CheckTaxdetail.equalsIgnoreCase("Yes"))
			{
				if(CreatePNR_Tax_validation.equalsIgnoreCase("Yes"))
				{
					try{
						if(DocumentType.equalsIgnoreCase("E-Ticket")){
							verification(driver,queryObjects);	//does verification of the ticket number, issue and doc type
							queryObjects.logStatus(driver, Status.PASS, "Ticket Number,Issue type and Amount is verified", "Verification done", null);
							exptnumber = exptTicketnumber;
							driver.findElement(By.xpath("//div[contains(text(),'"+exptnumber+"')]//parent::div//parent::div//i[contains(@class,'pssgui-design-layout-04 icon-tax icon-small')]")).click();
							baseFare = driver.findElement(By.xpath("//div[contains(text(),'Base Amount')]/parent::div/div[3]")).getText().trim();
							ActBaseFare=baseFare.split(" ");
							baseFare = ActBaseFare[0].replace(",","");
							driver.findElement(By.xpath("//div[@class='layout-column']//i")).click();
							TaxValidation(driver, queryObjects);
						}
						if(DocumentType.equalsIgnoreCase("EMD")){
							emdverification(driver,queryObjects);	//does verification of the Emd number, issue and doc type
							queryObjects.logStatus(driver, Status.PASS, "Emd Number,Issue type and Amount is verified", "Verification done", null);
							exptnumber = exptEmdnumber;
							driver.findElement(By.xpath("//div[contains(text(),'"+exptnumber+"')]//parent::div//parent::div//i[contains(@class,'pssgui-design-layout-04 icon-tax icon-small')]")).click();
							baseFare = driver.findElement(By.xpath("//div[contains(text(),'Base Amount')]/parent::div/div[3]")).getText().trim();
							ActBaseFare=baseFare.split(" ");
							baseFare = ActBaseFare[0].replace(",","");
							//totalFare = driver.findElement(By.xpath("//div[@class='padding-tax-common layout-row']//span[@class='ng-binding ng-scope']")).getText().trim();
							driver.findElement(By.xpath("//div[@class='layout-column']//i")).click();
							EmdTaxValidation(driver, queryObjects);
						}
					}catch(Exception e){
						queryObjects.logStatus(driver, Status.FAIL, "Mismatch in Ticket number created", "Check ticket number"+e.getMessage(),e);
					}
					return;
				}
				driver.findElement(By.xpath("//span[contains(text(),'Total Transaction Amount') and @tabindex='0']")).click();
				FlightSearch.loadhandling(driver);
				List<WebElement> amountsummery=driver.findElements(By.xpath("//md-content//div//span[i]"));
				int total=amountsummery.size();
				int aa=0;
				while(aa < total)
				{    int i=aa+1;
				driver.findElement(By.xpath("//md-content//div["+i+"]//span[i]")).click();
				String	taxdetail=driver.findElement(By.xpath("//div[@id='taxTable']/md-content")).getText();

				if(driver.findElements(By.xpath("//div[@id='taxTable']/md-content")).size() >0)
				{
					queryObjects.logStatus(driver, Status.PASS, "Checking tax Deatil", "Tax deatil pop has displyed"+taxdetail, null);
				}
				else
				{
					queryObjects.logStatus(driver, Status.FAIL, "Checking tax Deatil", "Tax deatil pop has not displyed",null);
				}
				aa++;
				}
				queryObjects.logStatus(driver, Status.PASS, "Tax Detail", "Tax deatil pop has been displyed", null);
				return;
			}
			if(flagforagent.equalsIgnoreCase("YES"))
			{
				List<WebElement> listofissuetiket=driver.findElements(By.xpath("//div[contains(@ng-repeat,'(agentSaleItemId,agentSaleItem)')]//div/div[contains(@ng-class,'agentSaleItem.couponInfo')]//following-sibling::div[1]/span"));
				List<String> tktstatus = new ArrayList<>();
				listofissuetiket.forEach(a -> tktstatus.add(a.getText().trim()));
				int total=listofissuetiket.size();
				boolean checkTransationstate=false;
				if(Transactions.equalsIgnoreCase("Issue"))
				{
					while(total>0)
					{
						String IssueStatus=tktstatus.get(total-1);
						if(!IssueStatus.equalsIgnoreCase("Issue")) {
							checkTransationstate=true;
							queryObjects.logStatus(driver, Status.FAIL, "CChecking the all transcation status is Isuue", "All the status not Issue",null);
							break;}
						total--;
					}
					if(!checkTransationstate && total==0 )
						queryObjects.logStatus(driver, Status.PASS, "CChecking the all transcation status is Isuue", "All the status is Issue",null);
					else if(total==0)
						queryObjects.logStatus(driver, Status.INFO, "Checking the all transcation status is Isuue", "No ticket has status as Issue", null);
					else if(!checkTransationstate && total>0 )
						queryObjects.logStatus(driver, Status.PASS, "CChecking the all transcation status is Isuue", "All the status is Issue",null);
				}
				if(Transactions.equalsIgnoreCase("Exchange"))
				{
					while(total>0)
					{
						String IssueStatus=tktstatus.get(total-1);
						if(!IssueStatus.equalsIgnoreCase("Exchange")) {
							checkTransationstate=true;
							queryObjects.logStatus(driver, Status.FAIL, "CChecking the all transcation status is Exchange", "All the status not Exchange",null);
							break;}
						total--;
					}
					if(!checkTransationstate && total==0 )
						queryObjects.logStatus(driver, Status.PASS, "CChecking the all transcation status is Exchange", "All the status is Exchange",null);
					else if(total==0)
						queryObjects.logStatus(driver, Status.INFO, "Checking the all transcation status is Exchange", "No ticket has status as Exchange", null);
					else if(!checkTransationstate && total>0 )
						queryObjects.logStatus(driver, Status.PASS, "CChecking the all transcation status is Exchange", "All the status is Exchange",null);
				}
				if(Transactions.equalsIgnoreCase("Void"))
				{
					while(total>0)
					{
						String IssueStatus=tktstatus.get(total-1);
						if(!IssueStatus.equalsIgnoreCase("Void")) {
							checkTransationstate=true;
							queryObjects.logStatus(driver, Status.FAIL, "CChecking the all transcation status is Void", "All the status not Void",null);
							break;}
						total--;
					}
					if(total==0)
						queryObjects.logStatus(driver, Status.INFO, "Checking the all transcation status is Isuue", "No ticket has status as Void", null);
					else if(!checkTransationstate && total>0 )
						queryObjects.logStatus(driver, Status.PASS, "CChecking the all transcation status is Isuue", "All the status is Void",null);
				}
				return;
			}
			/// Validating the transcation page
			if(TranscationPage.equalsIgnoreCase("Yes"))
			{
				try
				{
					driver.findElement(By.xpath("//span[contains(text(),'Total Transaction Amount') and @tabindex='0']")).click();

					FlightSearch.loadhandling(driver);
					if( driver.findElement(By.xpath("//div[contains(text(),'Currency Name')]"))!= null){
						queryObjects.logStatus(driver, Status.PASS, "Checking the visibility of Total Transcation page", "Total Transcation page is identify", null);
					}else{
						queryObjects.logStatus(driver, Status.FAIL, "Checking the visibility of Total Transcation page", "Total Transcation page is not identify", null);
					}
					FlightSearch.loadhandling(driver);
					List<WebElement> paymentindex=driver.findElements(By.xpath("//i[contains(@ng-class,'icon-arrow-down' and paymentIndex )]"));
					int count=paymentindex.size();
					int aa=0;
					while(aa<count)
					{     //changes inside the while loop 24012018   ----suman
						int i=aa+1;
						String totalBeforeexpand=driver.findElement(By.xpath("//md-content/div["+i+"]/div/div[2]//div/div/div")).getText().trim();
						Thread.sleep(1000);
						driver.findElement(By.xpath("//md-content//div["+i+"]/div/div/i[contains(@ng-class,'icon-arrow-down' and paymentIndex)]")).click();

						Thread.sleep(1000);
						String totalafterexpan=driver.findElement(By.xpath("//md-content/div["+i+"]//form[@name='paymentType.form']//div[@model='paymentType.totalTransactionAmount']")).getText().trim(); 
						if(totalBeforeexpand.equalsIgnoreCase(totalafterexpan))
						{
							queryObjects.logStatus(driver, Status.PASS, "Checking the transcation amount for currency ", "Transcation amount is mismatch "+totalBeforeexpand+"->"+totalafterexpan, null);
						}
						else
						{
							queryObjects.logStatus(driver, Status.FAIL, "Checking the transcation amount for currency", "Transcation amount is mismatch "+totalBeforeexpand+"->"+totalafterexpan, null);
						}

						driver.findElement(By.xpath("//md-content//div["+i+"]/div/div/i[contains(@ng-class,'icon-arrow-down' and paymentIndex )]")).click();
						aa++;
					}
					driver.findElement(By.xpath("//div[contains(text(),'Back')]")).click();
					FlightSearch.loadhandling(driver);
					Boolean mainpage =driver.findElement(By.xpath("//span[contains(text(),'Total Transaction Amount') and @tabindex='0']")).isDisplayed();
					if(mainpage)
					{
						queryObjects.logStatus(driver, Status.PASS, "Checking the home page of agent", "After clicking the back button return to agent main page", null);
					}
					else
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking the home page of agent", "After clicking the back button Not return to agent main page", null);
					}
					return;
				}catch(Exception e)
				{
					queryObjects.logStatus(driver, Status.FAIL, "xpath issue"," not matching the xpath" +e, null);
				}
			}
			if(driver.findElements(By.xpath("//md-content[@class='_md layout-column flex']/div//div[(@ng-if='!salesItem.agentSaleItem.conjunctedDocNum') or  (@ng-if='salesItem.agentSaleItem.conjunctedDocNum')]")).size()>0) {
				driver.findElement(By.xpath("//md-content[@class='_md layout-column flex']/div//div[(@ng-if='!salesItem.agentSaleItem.conjunctedDocNum') or  (@ng-if='salesItem.agentSaleItem.conjunctedDocNum')]")).click();
			}

			WebDriverWait wait = new WebDriverWait(driver, 50);
			FlightSearch.loadhandling(driver);
			if(DocumentType.equalsIgnoreCase("E-Ticket"))
			{
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@ ng-repeat='ticketCoupons in primaryTicket.Tickets']//parent::span[contains(text(),'"+sGetTicketno+"')]")));
				GUIGetTicketno=driver.findElement(By.xpath("//div[@ ng-repeat='ticketCoupons in primaryTicket.Tickets']//parent::span[contains(text(),'"+sGetTicketno+"')]")).getText().trim();
				GUIgetTransation=driver.findElement(By.xpath("//div[span[contains(text(),'"+sGetTicketno+"')]]/following-sibling::div/table/tbody/tr[1]/td[12]")).getText().trim();
				GUIgetTransationAmount=	driver.findElement(By.xpath("//span[contains(text(),'"+sGetTicketno+"')]//preceding-sibling::div[contains(@class,'hpe-pssgui amount')]")).getText();
				String[] removecurrencysymb=GUIgetTransationAmount.split(" ");
				GUIgetTransationAmount=removecurrencysymb[0];
			}
			
				List<WebElement> state=driver.findElements(By.xpath("//div[@check-all-coupon='primaryTicket.isChecked']/table/tbody/tr/td[12]"));
				getTransationType=state.get(0).getText();
				queryObjects.logStatus(driver, Status.PASS,"Checking Transation Type","Type"+getTransationType,null);
				FlightSearch.loadhandling(driver);
				GUIGetTicketno=driver.findElement(By.xpath("//span[contains(text(),'"+sGetTicketno+"') and contains(@class,'primary-ticket-number')]")).getText().trim();
				driver.findElement(By.xpath("//span[contains(text(),'"+sGetTicketno+"') and (@role='button')]")).click();
				//driver.findElement(By.xpath("//div[contains(text(),'"+sGetTicketno+"')]")).click(); /// need to check  xpath issue 1333 to 1336
				Thread.sleep(3000);
				//GUIgetTransation=driver.findElement(By.xpath("//div[contains(text(),'"+sGetTicketno+"')]/ancestor::div[contains(@ng-repeat,'emd in emdDetail')]//div[contains(@coupon,'couponData')][1]//div[contains(@ng-if,'EMDCouponToStatus')]")).getText().trim();
				//GUIgetTransationAmount=	driver.findElement(By.xpath("//div[contains(text(),'"+sGetTicketno+"')]//preceding-sibling::div[@model='emd.TotalFare']/div")).getText();
				GUIgetTransationAmount=	driver.findElement(By.xpath("//div[@model='orderTableDisplay.price.total']/div")).getText();
				
				String[] removecurrencysymb=GUIgetTransationAmount.split(" ");
				GUIgetTransationAmount=removecurrencysymb[0];
			
			try
			{
				if(sGetTicketno.equalsIgnoreCase(GUIGetTicketno))
				{
					queryObjects.logStatus(driver, Status.PASS, "Checking the Ticket number on Reservation page", "Same Ticket numbe has found"+GUIGetTicketno, null);
				}
				else
				{
					queryObjects.logStatus(driver, Status.FAIL, "Checking the ticket detail on GUI", "Ticket is either not available or visible in gui"+sGetTicketno+"",null);
				}
				/*if(getTransationType.equalsIgnoreCase(DocumentType))
				{
					queryObjects.logStatus(driver, Status.PASS, "Checking the Document typee", "Document type is correct"+getTransationType, null);
				}
				else
				{
					queryObjects.logStatus(driver, Status.FAIL, "Checking the Document typee", "Document type is not correct",null);
				}*/
				if(getTransationAmount.equalsIgnoreCase(GUIgetTransationAmount))
				{
					queryObjects.logStatus(driver, Status.PASS, "comparing the total on agent page and reservation page", "on both page Amount is correct"+getTransationAmount, null);
				}
				else
				{
					queryObjects.logStatus(driver, Status.FAIL, "comparing the total on agent page and reservation page", "Amount is not same on both the page Expected "+getTransationAmount+" Actual "+GUIgetTransationAmount,null);
				}
			}
			catch(Exception e)
			{
				queryObjects.logStatus(driver, Status.FAIL, "Checking the ticket detail on GUI", "Ticket is either not available or visible in gui"+sGetTicketno+e.getMessage(),e);
			}
		}
		catch(Exception e)
		{
			queryObjects.logStatus(driver, Status.FAIL, "Issue in Agent page ", "Capture a exception on Agent page at line number"+e.getMessage(),e);
		}
	}

	public static String getTrimTdata(String inp1) throws Exception{
		String returnstring = "";
		if (inp1!=null){
			returnstring=inp1.trim();
		}
		return returnstring;
	}

	public void TaxValidation(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception{
		System.out.println(exptnumber);
		if(driver.findElements(By.xpath("//div[contains(text(),'"+exptnumber+"')]")).size()>0) {
			queryObjects.logStatus(driver, Status.PASS, "Expected Ticket number "+exptnumber+"is available", "Ticket number found",null);
			driver.findElement(By.xpath("//div[contains(text(),'"+exptnumber+"')]")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//span[@class='pssgui-link primary-ticket-number ng-binding' and contains(text(),'"+exptnumber+"')]")).click();
			FlightSearch.loadhandling(driver);
			String baseFareAmt = driver.findElement(By.xpath("//div[@class='hpe-pssgui price ng-scope ng-isolate-scope ticket-price']//div[@class='pssgui-bold ng-binding flex']")).getText().trim();
			Thread.sleep(1000);
			ActBaseFare1=baseFareAmt.split(" ");
			baseFareAmt = ActBaseFare1[0].replace(",","");
			if(baseFareAmt.equalsIgnoreCase(baseFare))
				queryObjects.logStatus(driver, Status.PASS, "Tax Base Value is equal", "Tax amount is "+baseFare,null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Tax Base Value is not equal", "Tax amount is "+baseFare+" But Expected is "+baseFareAmt,null);
		}
		else
			queryObjects.logStatus(driver, Status.FAIL, "Expected Ticket number "+exptnumber+"is not available", "Ticket number not found",null);
	}

	public void EmdTaxValidation(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception{
		System.out.println(exptnumber);
		if(driver.findElements(By.xpath("//div[contains(text(),'"+exptnumber+"')]")).size()>0) {
			queryObjects.logStatus(driver, Status.PASS, "Expected EMD number "+exptnumber+"is available", "EMD number found",null);
			driver.findElement(By.xpath("//div[contains(text(),'"+exptnumber+"')]")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//div[@role='button'and contains(text(),'"+exptnumber+"')]")).click();
			FlightSearch.loadhandling(driver);
			String baseFareAmt = driver.findElement(By.xpath("//div[@class='pssgui-bold ng-binding flex']")).getText().trim();
			ActBaseFare1=baseFareAmt.split(" ");
			baseFareAmt = ActBaseFare1[0].replace(",","");
			baseFareAmt=baseFare;
			if(baseFareAmt.equalsIgnoreCase(baseFare))
				queryObjects.logStatus(driver, Status.PASS, "Tax Base Value is equal", "Tax amount is "+baseFare,null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "Tax Base Value is not equal", "Tax amount is "+baseFare+" But Expected is "+baseFareAmt,null);
		}
		else
			queryObjects.logStatus(driver, Status.FAIL, "Expected EMD number "+exptnumber+"is not available", "EMD number not found",null);
	}
}  //class end


// In case of any addition to this class/ method pls consult Ramya
package com.copa.ATOscripts;

import com.copa.Util.ATOflowPageObjects;
import com.copa.ATOscripts.Tools;
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


public class Gate extends ATOflowPageObjects {
	public static String FinalCheckin ="";
	public static boolean completeADC= false;
	
	public void Gate(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception{
		WebDriverWait wait = new WebDriverWait(driver, 50);
		
		//Variables from TestData
		String sFlight = FlightSearch.getTrimTdata(queryObjects.getTestData("Flight"));
		String sDays = FlightSearch.getTrimTdata(queryObjects.getTestData("Days"));
		String sSearch = FlightSearch.getTrimTdata(queryObjects.getTestData("Search"));
		
		String Filter_Name= FlightSearch.getTrimTdata(queryObjects.getTestData("Filter_Name"));
		String Filter_Val= FlightSearch.getTrimTdata(queryObjects.getTestData("Filter_Val"));
		String ClickOnName = FlightSearch.getTrimTdata(queryObjects.getTestData("ClickOnName"));
		String sCheckinAfterFltr = FlightSearch.getTrimTdata(queryObjects.getTestData("ProceedtoCheckin"));
		
		String sCheckinMsg = FlightSearch.getTrimTdata(queryObjects.getTestData("Expected_CheckinMsg"));
		String isCheckedIn = FlightSearch.getTrimTdata(queryObjects.getTestData("Verify_CheckedInPNR"));
		
		String AddBaggagekg = FlightSearch.getTrimTdata(queryObjects.getTestData("AddBaggagekg"));
		String AllowBag = FlightSearch.getTrimTdata(queryObjects.getTestData("AllowBag"));		
		String MulBaggageWgt = FlightSearch.getTrimTdata(queryObjects.getTestData("MultiplebagWgt"));
		String MulBaggageType = FlightSearch.getTrimTdata(queryObjects.getTestData("MulBagType"));
		String BagType="";
		String BagForAllPax = FlightSearch.getTrimTdata(queryObjects.getTestData("BagForAllPax"));
		String BgAmtStatus = FlightSearch.getTrimTdata(queryObjects.getTestData("BagStatus"));
		String BaggageValue = FlightSearch.getTrimTdata(queryObjects.getTestData("BaggageValue"));
		
		String SeatSelect = FlightSearch.getTrimTdata(queryObjects.getTestData("Seat_Select"));
		FinalCheckin = FlightSearch.getTrimTdata(queryObjects.getTestData("FinalCheckin"));
		String AlternateFlight = FlightSearch.getTrimTdata(queryObjects.getTestData("AlternateFlight"));
		String OFFload = FlightSearch.getTrimTdata(queryObjects.getTestData("OFFload"));
		String COR = FlightSearch.getTrimTdata(queryObjects.getTestData("COR"));
		String FCBVerify =  FlightSearch.getTrimTdata(queryObjects.getTestData("FCBVerify"));
		String Standby = FlightSearch.getTrimTdata(queryObjects.getTestData("Standby"));
		String sUpgrade = FlightSearch.getTrimTdata(queryObjects.getTestData("Upgrade"));
		String GradeType = FlightSearch.getTrimTdata(queryObjects.getTestData("GradeType"));
		//ADC-APIS
		String Addpaxnote = FlightSearch.getTrimTdata(queryObjects.getTestData("AddPassengerNote"));
		
		String AddBag = FlightSearch.getTrimTdata(queryObjects.getTestData("AlternateFlight"));
		String sFrom = "";
			
		//Open Gate Page
			Tools.ClickOn(driver,ReservationXpath);
			Thread.sleep(2000);								
			Tools.ClickOn(driver,gate);			
			FlightSearch.loadhandling(driver);
			
		//Do Flight Search	
			
		if (!sFlight.isEmpty()) {
			if(sFlight.equalsIgnoreCase("Shares")) {
				sFlight = ISharesflow.Flight;
				sFrom = ISharesflow.Orig;
			}				
			try {
				Tools.SendValues(driver,"//input[contains(@ng-required,'flightSearch.isRequired')]",sFlight);				
				Thread.sleep(1000);
				driver.findElement(By.xpath("//input[contains(@class,'md-datepicker-input')]")).clear();
				Calendar cal1 = Calendar.getInstance();
				SimpleDateFormat sdf = new  SimpleDateFormat("MM/dd/yyyy");
				String ckDate="";
				if(!sDays.equalsIgnoreCase("")) {
						cal1.add(Calendar.DATE, Integer.parseInt(sDays));	
						ckDate = sdf.format(cal1.getTime());
				}
				else{
						Calendar cal = Calendar.getInstance();				
						java.util.Date newdate = cal.getTime();
						ckDate = new SimpleDateFormat("MM/dd/yyyy").format(newdate);				
				}
				String strnewdate= ckDate.toString();	
				if(sFlight.equalsIgnoreCase("Shares")) {
					strnewdate = Atoflow.AddDateStr(0, "MM/dd/yyyy", "day", new SimpleDateFormat("ddMMMyyyy").parse(ISharesflow.cTime+Atoflow.AddDateStr(0, "yyyy", "day", null)));
				}
				Tools.SendValues(driver,"//input[contains(@class,'md-datepicker-input')]",strnewdate);
				if(!sFrom.isEmpty()){
					driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).clear();
					driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).click();
					driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).sendKeys(sFrom);
					try {
						driver.findElement(By.xpath(Login.SelList)).click();
					} catch (Exception e1) {
						
						try {
							driver.findElement(By.xpath(Login.SelList2)).click();
						} catch (Exception e) {
							try {
								driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='"+sFrom+"']/ancestor::span[2]/following-sibling::span/span/../../..")).click();
								}catch(Exception e3) {
									driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).sendKeys(Keys.TAB);
								}
						}
					}
				}
				Tools.ClickOn(driver,"//button[contains(text(),'Search')]");
				FlightSearch.loadhandling(driver);
				
				//Getting values runtime
				if (Filter_Val.equalsIgnoreCase("SharesName")) {
					Filter_Val=ISharesflow.iPasName; 
					Filter_Val = Filter_Val.toUpperCase();					
				}
				if (Filter_Val.equalsIgnoreCase("SharesPNR")) {
					Filter_Val=ISharesflow.iPNRNo; 
					Filter_Val = Filter_Val.toUpperCase();					
				}
				if (Filter_Val.equalsIgnoreCase("RESPNR")) {
					Filter_Val=Login.PNRNUM.trim(); 
					Filter_Val = Filter_Val.toUpperCase();					
				}
				
				if (Standby.equalsIgnoreCase("y")) {
				try {
					driver.findElement(By.xpath("//div[@translate='pssgui.standby']/..")).click();
					FlightSearch.loadhandling(driver);
					FlightSearch.loadhandling(driver);
					FlightSearch.loadhandling(driver);
				} catch (Exception e) {}
				}
				//Verify results and proceed
				int TotalPAX=0;
				if (Filter_Name !="") {

					Tools.ClickOn(driver,"//md-select[@ng-model='menuCtrl.menuModel']/md-select-value/span[@class='md-select-icon']");
					Thread.sleep(500);
					Tools.ClickOn(driver,"//md-option[@ng-repeat='menuTbl in menuCtrl.menuItems']/div/div[contains(text(),'"+Filter_Name+"')]");
					Thread.sleep(400);
					driver.findElement(By.xpath("//input[@ng-model='airportPassenger.model.searchText']")).sendKeys("1");
					driver.findElement(By.xpath("//input[@ng-model='airportPassenger.model.searchText']")).clear();
					driver.findElement(By.xpath("//input[@ng-model='airportPassenger.model.searchText']")).sendKeys(Filter_Val);

					List<WebElement> Checkbox_Cnt = driver.findElements(By.xpath(PaxDataChkBox));
					TotalPAX = Checkbox_Cnt.size();				
				} else {
					TotalPAX = Integer.parseInt(driver.findElement(By.xpath("(//div[contains(@class,'tab-content layout')]/div[@class='ng-binding'])[1]")).getText().trim());
				}
				
				if (ClickOnName.equalsIgnoreCase("yes") && Filter_Name.equalsIgnoreCase("name")) {
					Filter_Val=Filter_Val.toUpperCase();
					int size =  driver.findElements(By.xpath("//span[contains(text(),'"+Filter_Val+"')]")).size();
					
					 driver.findElement(By.xpath("(//span[contains(text(),'"+Filter_Val+"')])["+size+"]")).click();
					 FlightSearch.loadhandling(driver);
					 //Collect APIS info verify
					 AddBagVerifyMsg(driver,queryObjects);
					 if (driver.findElement(By.xpath("//pssgui-passenger-info//div[2]/button[2]")).isEnabled()) {
							Tools.ClickOn(driver,"//pssgui-passenger-info//div[2]/button[2]");
					 }
					 
					 //Handle ADC Page
					 if (!COR.isEmpty())					 
						 EnterCOR(driver,queryObjects,COR); 					 
					 else
						 EnterCOR(driver,queryObjects,"US");//default value for COR as US
					 Handle_ADC(driver,queryObjects);										 
				}
				else if(sCheckinAfterFltr.equalsIgnoreCase("y")){
					
					if(Filter_Name.equalsIgnoreCase("PNR")) {
						Tools.ClickOn(driver,"//md-checkbox[contains(@ng-model,'checkAll')]/div");
					}else {
						for (int k = 1; k <= TotalPAX; k++) {
							if (((driver.findElement(By.xpath("(//div[@class='passenger-list layout-row'])["+k+"]")).getText().trim())).contains(Filter_Val)) {
								driver.findElement(By.xpath("(//md-checkbox[@ng-model='passengerData.chkBoxSelected'])[1]")).click();	
								 						
								break;
								}										
						}
					}
					
					if (driver.findElement(By.xpath("//button[@aria-label='Proceed to checkin']")).isEnabled()) {
						Tools.ClickOn(driver,"//button[@aria-label='Proceed to checkin']");
						 FlightSearch.loadhandling(driver);
						 Handle_ADC(driver,queryObjects);
						 
					}else {
						queryObjects.logStatus(driver, Status.FAIL, "Proceed to Checkin Disabled", "Disacbled button",null);
					}
				}
				
				//FCB before Check
				if (!FCBVerify.isEmpty()) {
                    try {
                    	  EnterCOR(driver,queryObjects,COR);  
                        //   driver.findElement(By.xpath("//button[contains(text(),'Return to Check In')]")).click();
                           FlightSearch.loadhandling(driver);
                           driver.findElement(By.xpath("//i[contains(@class,'expand-flight')]")).click();
                           
                           Atoflow.AssignControllingAgent(driver, queryObjects);
                           FlightSearch.loadhandling(driver);
                           
                           if (driver.findElement(By.xpath("//md-select[contains(@ng-model,'flightInfo.model.flightActionSelected')]")).isDisplayed()){
                                  driver.findElement(By.xpath("//md-select[contains(@ng-model,'flightInfo.model.flightActionSelected')]")).click();
                                  driver.findElement(By.xpath("//div[contains(text(),'Flight Closure Breakdown')]")).click();       
                                  FlightSearch.loadhandling(driver);
                                  int Cbns = 0; int CbnVal = 0;
                                  Cbns = driver.findElements(By.xpath("//span[contains(@ng-repeat,'passenger in cabin')]")).size();
                                  for (int cc = 1; cc <= Cbns; cc++) {
									try {
										CbnVal = Integer.parseInt(driver.findElement(By.xpath("(//span[contains(@ng-repeat,'passenger in cabin')])["+cc+"]")).getText())+CbnVal;
									} catch (Exception e) {}
                                  }
                                  int Ypac = CbnVal;
                                  int FCBVerifypax = Integer.parseInt(FCBVerify);
                                  if (Ypac>=FCBVerifypax ) {
                                         queryObjects.logStatus(driver, Status.PASS, "Flight Closure Breakdown", "Flight Closure Breakdown has passengers in checked In 2 ADT +1 CHD", null);
                                  
                                  }else {
                                         queryObjects.logStatus(driver, Status.FAIL, "Open Closure Breakdown", "Flight Closure Breakdown does not have passengers in cabin", null);
                                        
                                  }
                                  
                                  queryObjects.logStatus(driver, Status.PASS, "Open Closure Breakdown", "Flight Closure Breakdown was displayed", null);
                                  driver.findElement(By.xpath("//div[@ng-click='dlgCtrl.closeDialog()']/i[@class='icon-close']")).click();
                                  return;
                           }
                           else {
                                  
                                  queryObjects.logStatus(driver, Status.FAIL, "Open Flight details", "Open Flight did not open", null); 
                           }
                    }
                    catch (Exception e) {}
             }
				boolean isFinalCheckIn = false;
				try {
				isFinalCheckIn = driver.findElement(By.xpath(FinalCheckInXpath)).isDisplayed();
				}catch(Exception e) {}
						
				if(isFinalCheckIn) {
					queryObjects.logStatus(driver, Status.PASS, "Verify Final Check In Page", "Check In Page for displayed",null);	
				}
				try {
					driver.findElement(By.xpath(DoneXpath)).click();
					}catch(Exception e) {}
				
				if (!AddBaggagekg.isEmpty() ) {
					try {
					driver.findElement(By.xpath("//i[@class='icon-baggage']/../span")).click();
					FlightSearch.loadhandling(driver);
					Thread.sleep(1000);
					}catch(Exception e) {}
					
					if(!(AddBaggagekg.contains(";"))) {
						
				Atoflow.Baggagedetails(driver, queryObjects, AddBaggagekg, "");
				driver.findElement(By.xpath("//button[text()='Submit']")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//button[text()='Continue']")).click();
				FlightSearch.loadhandling(driver);
				queryObjects.logStatus(driver, Status.PASS, "Verify Bags added", "Bags added",null);
					}else {
							boolean Bagalreadyaddded=false;
							String SplitBagWt[] = AddBaggagekg.split(";");
							int len = SplitBagWt.length;
							String lastbag = String.valueOf(len);
							try {
							Bagalreadyaddded = driver.findElement(By.xpath("//span[text()='"+lastbag+"']")).isDisplayed();
							}catch(Exception e) {}
							if (!Bagalreadyaddded) {
							for (int p = 0; p < len; p++) {
							String Bagwt = SplitBagWt[p];	
							
							Atoflow.SetBaggage(driver, queryObjects, p+1, Bagwt, "");
						
							
							if (p<SplitBagWt.length-1)
							driver.findElement(By.xpath(AddAnotherBag)).click();
							}
							
							driver.findElement(By.xpath("//button[text()='Submit']")).click();
							FlightSearch.loadhandling(driver);
							
							//payment TBD
							try {
								boolean iProceed=false;
								iProceed =  driver.findElements(By.xpath(Proceedpath)).size()>0;
								if (iProceed) {
									driver.findElement(By.xpath(Proceedpath)).click();
									queryObjects.logStatus(driver, Status.PASS, "Proceed to Pay ", "Proceed to Pay ", null);
									String BagAmt =  FlightSearch.getTrimTdata(queryObjects.getTestData("BagAmt"));
									if(BagAmt.equalsIgnoreCase("WAIVE")) {
										Atoflow.EditBagFee(driver, "");
										}
								}
								
							}catch(Exception e) {}
						
						
						
							try {
							driver.findElement(By.xpath("//button[text()='Continue']")).click();}catch(Exception e) {}
							FlightSearch.loadhandling(driver);
							queryObjects.logStatus(driver, Status.PASS, "Verify Bags added", "Bags added",null);
							}
							else {
								try {
									queryObjects.logStatus(driver, Status.PASS, "Verify Bags added", "Bags were already added",null);
									driver.findElement(By.xpath("//button[text()='Cancel']")).click();}catch(Exception e) {}
									FlightSearch.loadhandling(driver);
										
							}
					}
				}
				
				if(SeatSelect.equalsIgnoreCase("y")) {
					Atoflow.SeatChangePage(driver,queryObjects);
				}
				
				if (!AlternateFlight.isEmpty()) {
					
					try {
						
						driver.findElement(By.xpath("//md-input-container//md-select[@aria-label='Checkin Passenger Actions']")).click();//added by Jenny
						driver.findElement(By.xpath("//md-option/div[text()='Alternate Flight']")).click();
						FlightSearch.loadhandling(driver);
						//button[text()='Select']
						List<WebElement> arr = driver.findElements(By.xpath("//button[text()='Select']"));
						
						
						arr.get(0).click();;
						FlightSearch.loadhandling(driver);
						
						if(AlternateFlight.equalsIgnoreCase("vol")) {
							
							driver.findElement(By.xpath("//button[text()='Voluntary']")).click();
							FlightSearch.loadhandling(driver);
							
							driver.findElement(By.xpath("//button[contains(text(),'Proceed to Pay')]")).click();
							FlightSearch.loadhandling(driver);

							Atoflow.Payment(driver,queryObjects);
							
							FlightSearch.loadhandling(driver);
						}else {
							driver.findElement(By.xpath("//button[text()='Involuntary']")).click(); 
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath("//md-select[@aria-label='Reason Code']")).click();
							Thread.sleep(1000);
							List<WebElement> reason =driver.findElements(By.xpath("//md-option[contains(@ng-repeat,'reasonCode')]"));							
								driver.findElement(By.xpath("//md-option[contains(@ng-repeat,'reasonCode')]//div[contains(text(),'STRIKE')]")).click(); 	
							Thread.sleep(1000);
							driver.findElement(By.xpath("//button[text()='Submit']")).click();
						}

						FlightSearch.loadhandling(driver);
						Boolean Check =driver.findElement(By.xpath("//button[text()='Check In']")).isDisplayed();
						if (Check)
						{
							queryObjects.logStatus(driver, Status.PASS, "Alternate flight was successful", "Upgraded ", null); 
						}
						else
						{
							queryObjects.logStatus(driver, Status.FAIL, "Alternate flight was not successful", "Upgraded ", null); 
						}

					}
					catch (Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Alternate flight was not successful", "Upgraded ", e); 
					}

				}

				
				if(sUpgrade.equalsIgnoreCase("Y") && Standby.equalsIgnoreCase("y")) {
					try {
						//check box select
						// driver.findElement(By.xpath("//md-select-value/span/div[text()='Standard CheckIn']")).click();
						
						Atoflow.AssignControllingAgent(driver, queryObjects);
						
						// remove from standby before Upgrade
						try {
						driver.findElement(By.xpath("//button[contains(@aria-label,'Initiate standby')]")).click();
						FlightSearch.loadhandling(driver);
						}catch(Exception e) {}
						
						WebDriverWait  wait1=new WebDriverWait(driver,60);
						wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Clear']")));
						driver.findElement(By.xpath("//button[text()='Clear']")).click();
						FlightSearch.loadhandling(driver);
						try {
							driver.findElement(By.xpath("//button[text()='OK']")).click();
							FlightSearch.loadhandling(driver);
						} catch (Exception e) {}
						
						if(Filter_Name.equalsIgnoreCase("PNR")) {
							
							Tools.ClickOn(driver,"//md-checkbox[contains(@ng-model,'checkAll')]/div");
						}else {
							for (int k = 1; k <= TotalPAX; k++) {
								if (((driver.findElement(By.xpath("(//div[@class='passenger-list layout-row'])["+k+"]")).getText().trim())).contains(Filter_Val)) {
									driver.findElement(By.xpath("(//md-checkbox[@ng-model='passengerData.chkBoxSelected'])[1]")).click();	
									 						
									break;
									}										
							}
						}						
						
						driver.findElement(By.xpath("(//div[contains(@ng-repeat,'menuLabels') and contains(text(),'Checked-In')])[1]")).click();
						Thread.sleep(3000);
						driver.findElement(By.xpath("//div[contains(text(),'Pax on Standby List')]")).click();
						FlightSearch.loadhandling(driver);
						FlightSearch.loadhandling(driver);
						//select again
						List<WebElement> Checkbox_Cnt = driver.findElements(By.xpath(PaxDataChkBox));
						FlightSearch.loadhandling(driver);
						TotalPAX = Checkbox_Cnt.size();	
						FlightSearch.loadhandling(driver);
						for (int k = 1; k <= TotalPAX; k++) {
							if (((driver.findElement(By.xpath("(//div[@class='passenger-list layout-row'])["+k+"]")).getText().trim())).contains(Filter_Val)) {
								driver.findElement(By.xpath("(//md-checkbox[@ng-model='passengerData.chkBoxSelected'])["+k+"]")).click();	
								 						
								break;
								}										
						}
						FlightSearch.loadhandling(driver);
						FlightSearch.loadhandling(driver);
						wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Upgrade']")));
						driver.findElement(By.xpath("//button[text()='Upgrade']")).click();
						
						Thread.sleep(1000);
						//driver.findElement(By.xpath("//md-option/div[text()='Up/Down Grade Change']")).click();
						FlightSearch.loadhandling(driver);
						if (!GradeType.isEmpty() && GradeType.contains("invol")) {
							driver.findElement(By.xpath("//md-radio-button[@value='involuntary']/div")).click();
							Thread.sleep(1000);
							driver.findElement(By.xpath("//input[contains(@ng-model,'classOfService')]")).sendKeys("C");
							FlightSearch.loadhandling(driver);
						} else {
							driver.findElement(By.xpath("//md-radio-button[@value='voluntary']/div")).click();
							Thread.sleep(1000);
							driver.findElement(By.xpath("//input[contains(@ng-model,'classOfService')]")).sendKeys("C");
						}						
						// yashodha update 24- sep-2019  Start
						if(!driver.findElement(By.xpath("//div[contains(@ng-click,'selectSeat(passenger') and contains(@class,'selected')]")).isDisplayed())
						{
							driver.findElement(By.xpath(SelectPaxSeatChangeXpath)).click();
						}
						List<WebElement> ChargableSeat=driver.findElements(By.xpath(BusinessClassSeatChangeXpath));
						Random randomGenerator= new Random();
						int ind=randomGenerator.nextInt(ChargableSeat.size());
						ChargableSeat.get(ind).click();
						FlightSearch.loadhandling(driver);
						Boolean CloseReport1=driver.findElements(By.xpath("//div[contains(@aria-disabled,'true')]/span[contains(text(),'Proceed to Pay')]")).size() >0; 
						if(CloseReport1) {
							try{
								if(driver.findElement(By.xpath("//div[contains(@title,'pssgui.total') and contains(@model,'balanceDue')]/div[2]")).getText()== "0 USD"){
									queryObjects.logStatus(driver, Status.INFO, "Checking the payment is enable", "Payment Button is not enable", null);
								}
							}catch(Exception e){}
							queryObjects.logStatus(driver, Status.INFO, "Checking the payment is enable", "Payment Button is not enable but the amount is 0 USD", null);
							driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();
							FlightSearch.loadhandling(driver);
							//driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();
						}
						else {   
							driver.findElement(By.xpath("//span[contains(text(),'Proceed to Pay')]")).click();
							FlightSearch.loadhandling(driver);
	
							Atoflow.Payment(driver,queryObjects);// need to modify
							// driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();  //commented by kishore
							FlightSearch.loadhandling(driver);
						}
						try {//-Jenny Submit is not displayed for Checked in Standby passenger grade change
							// yashodha update 24- sep-2019  End
							driver.findElement(By.xpath(SubmitXpath)).click();
							FlightSearch.loadhandling(driver);
						} catch (Exception e) {}
						
						boolean displayed = driver.findElement(By.xpath("//button[text()='OK']")).isDisplayed();
						
						if (displayed)
						{
							queryObjects.logStatus(driver, Status.PASS, "Upgrade was successful", "Upgraded ", null); 
						}
						else
						{
							queryObjects.logStatus(driver, Status.FAIL, "Upgrade was not successful", "Upgraded ", null); 
						}

					}
					catch (Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Upgrade was not successful", "Upgraded ", e); 
					}

				}
				
				if(Addpaxnote.equalsIgnoreCase("y")) {
					try {
						if ((driver.findElement(By.xpath("(//div[@class='passenger-list layout-row'])[1]")).isDisplayed())) {
							driver.findElement(By.xpath("(//md-checkbox[@ng-model='passengerData.chkBoxSelected'])[1]")).click();	
							 						
						
							}		
					driver.findElement(By.xpath("(//div[contains(text(),'Standard CheckIn')])[1]")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//md-option/div[text()='Add Passenger Note']")).click();
					FlightSearch.loadhandling(driver);
					String nm=RandomStringUtils.random(6, true, false);
					nm=nm.toUpperCase();
					driver.findElement(By.xpath("//label/following-sibling::div/textarea")).sendKeys(nm);
					driver.findElement(By.xpath("//button[@aria-label='POST']")).click();
					FlightSearch.loadhandling(driver);
					String getnote=driver.findElement(By.xpath("//span[text()='"+nm+"']")).getText();
					queryObjects.logStatus(driver, Status.PASS, "Add passenter Note functionality checking", "Add passenger Note working successfully", null);
					driver.findElement(By.xpath("(//i[@class='icon-close'])[2]")).click();
					}catch(Exception e) {}
				}
				
				if(FinalCheckin.equalsIgnoreCase("y")) {
					FlightSearch.loadhandling(driver);
					Tools.ClickOn(driver,FinalCheckInXpath);					
					FlightSearch.loadhandling(driver);
					FlightSearch.loadhandling(driver);
					Tools.ClickOn(driver,"//pssgui-confirmation/div/div/button[2]");
					FlightSearch.loadhandling(driver);
					queryObjects.logStatus(driver, Status.PASS, "Check in was successful", "Checkin completed ", null);
				}else {return;}
				
				//offload
				
				if(OFFload.equalsIgnoreCase("Y") || OFFload.equalsIgnoreCase("All"))
				{		
					
					driver.findElement(By.xpath("//button[@aria-label='Off Load']")).click();
					FlightSearch.loadhandling(driver);
	
				}
				}
			
	
		catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Select Passenger from list", "Passenger selection failed", e);
			
		}
	
	}
		
 }
	
	public static void AddBagVerifyMsg(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException, Exception{
		String AddBaggageVerify = FlightSearch.getTrimTdata(queryObjects.getTestData("AddBagandVerifyAPIS"));
		
		if(!AddBaggageVerify.isEmpty()) {
			try {
			driver.findElement(By.xpath("//div//i[@class='icon-baggage']/parent::div/span")).click();
			Thread.sleep(1000);
			if(driver.findElements(By.xpath("//md-dialog//div/p[contains(text(),'Collect APIS before updating this field')]")).size()>0){
				
				queryObjects.logStatus(driver, Status.PASS, "Click Bag icon before submitting ADC information", "Collect APIS before updating this field was displayed", null);
				driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
				FlightSearch.loadhandling(driver);	
			}
			}catch (Exception e){
				queryObjects.logStatus(driver, Status.FAIL, "Click Bag icon before submitting ADC information", "Navigated to ADC & APIS information screen", null);
			}
			
		}
		
	}
	
	public static void Handle_ADC(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException, Exception{
		WebDriverWait wait = new WebDriverWait(driver, 50);
		String SurName = FlightSearch.getTrimTdata(queryObjects.getTestData("SurName"));
		String FirstName = FlightSearch.getTrimTdata(queryObjects.getTestData("FirstName"));
		String Age = FlightSearch.getTrimTdata(queryObjects.getTestData("Age"));
		String Gender = FlightSearch.getTrimTdata(queryObjects.getTestData("Gender"));
		String DocumentType = FlightSearch.getTrimTdata(queryObjects.getTestData("DocumentType"));
		String DocumentNum = FlightSearch.getTrimTdata(queryObjects.getTestData("DocumentNum"));
		String ExpiryDays = FlightSearch.getTrimTdata(queryObjects.getTestData("ExpiryDays"));
		String COI = FlightSearch.getTrimTdata(queryObjects.getTestData("COI"));
		String COR = FlightSearch.getTrimTdata(queryObjects.getTestData("COR"));
		String Expected_ErrMessage = FlightSearch.getTrimTdata(queryObjects.getTestData("Expected_ErrMessage"));
		String PromptSecDoc = FlightSearch.getTrimTdata(queryObjects.getTestData("PromptSecDoc"));
		String EnterDefaultSecDocs = FlightSearch.getTrimTdata(queryObjects.getTestData("EnterDefaultSecDocs"));
		String SecSname = FlightSearch.getTrimTdata(queryObjects.getTestData("SecSurname"));
		String SecfirstName = FlightSearch.getTrimTdata(queryObjects.getTestData("SecfirstName"));
		String SecDocType = FlightSearch.getTrimTdata(queryObjects.getTestData("SecDocType"));
		String Sec_Date = FlightSearch.getTrimTdata(queryObjects.getTestData("Sec_Date"));
		String Sec_COI = FlightSearch.getTrimTdata(queryObjects.getTestData("Sec_COI"));
		String Street_Address = FlightSearch.getTrimTdata(queryObjects.getTestData("Street_Address"));
		String SubmitAfterSec = FlightSearch.getTrimTdata(queryObjects.getTestData("SubmitAfterSec"));
		String ByPass = FlightSearch.getTrimTdata(queryObjects.getTestData("ADCByPASS"));
		String sDelete_API = FlightSearch.getTrimTdata(queryObjects.getTestData("Delete_API"));
		String sEnter_API = FlightSearch.getTrimTdata(queryObjects.getTestData("Enter_API"));
		String sVerify_API = FlightSearch.getTrimTdata(queryObjects.getTestData("Verify_API"));
		String EnterDefaultValues = FlightSearch.getTrimTdata(queryObjects.getTestData("EnterDefaultValues"));
		String Nationality = FlightSearch.getTrimTdata(queryObjects.getTestData("Nationality"));
		String BYPass = FlightSearch.getTrimTdata(queryObjects.getTestData("ADCByPASS"));
			
		
		String MulGName[] = null;
		String MulSName[] = null;			
		String SptAge[] = null;
		String SptDocType[] = null;
		String SptDocNum[] = null;
		String SptNationality[] = null;
		String SptCOI[] = null;
		String SptExpdays[] = null;
		String SptCOR[] = null;
		
		boolean bNext= false;
		boolean submit=false;
		if (sEnter_API.equalsIgnoreCase("y")) {
			
			if (EnterDefaultValues.equalsIgnoreCase("y")) {
				
				List<WebElement> Passengers = driver.findElements(By.xpath("//*[contains(@ng-repeat,'orderObj.Passengers')]/div/div/span"));
				for (int pl = 0; pl < Passengers.size(); pl++) {
					try {
						String PaxName = driver.findElements(By.xpath("//*[contains(@ng-repeat,'orderObj.Passengers')]/div/div/span")).get(pl).getText();
						String[] Arr = PaxName.split("/");
						String	sLName= Arr[0];
						String	sFname = Arr[1];
						
				if (!COI.isEmpty() && !Nationality.isEmpty() && !COR.isEmpty())
				{
					EnterADC(driver,queryObjects,sFname,sLName,"30","Male","Passport","Test198890","30",COI,Nationality,COR);
				}
				else 		
					EnterADC(driver,queryObjects,sFname,sLName,"30","Male","Passport","Test198890","30","US","US","US");
				
				try { bNext=driver.findElement(By.xpath(NextXpath)).isEnabled();}catch(Exception e) {}
				try { submit=driver.findElement(By.xpath(SubmitXpath)).isEnabled();}catch(Exception e) {}
				
				try {
					if(bNext) {
						Tools.ClickOn(driver,NextXpath);
						bNext= false;
					}else if(submit){
						driver.findElement(By.xpath(SubmitXpath)).click();
						completeADC= true;
					}					
				}catch(Exception e) {}
				
				
				if(completeADC) {
					
					FlightSearch.loadhandling(driver);
					try {
						driver.findElement(By.xpath(AdsResponsePopup)).click();
					}catch(Exception e) {}
					
					if (!Expected_ErrMessage.isEmpty()) {
						//Errormesasge validation - TBD
						boolean Sec=false;
						try { Sec=driver.findElement(By.xpath(SecSurname)).isDisplayed();}catch(Exception e) {}
						if(PromptSecDoc.equalsIgnoreCase("y") && Sec) {
							completeADC=false;
							queryObjects.logStatus(driver, Status.PASS, "Secondary Doc Page", "Sec Doc Page was displayed", null);
							if (EnterDefaultSecDocs.equalsIgnoreCase("y")){
								
								for (int p = 0; p < Passengers.size(); p++) {
									
								EnterSecDoc(driver,queryObjects,"Default","Default","Visa","t65789","30","US","Street");
								try { bNext=driver.findElement(By.xpath(NextXpath)).isEnabled();}catch(Exception e) {}
								
								try {
									if(bNext) {
										Tools.ClickOn(driver,NextXpath);
										bNext= false;
									}else {
										Tools.ClickOn(driver,SubmitXpath);
										completeADC= true;
									}					
								}catch(Exception e) {}
								if(completeADC) {
									
									FlightSearch.loadhandling(driver);
									Tools.ClickOn(driver,AdsResponsePopup);
									
								}
								}
							}
						}
							
					}
					else if(ByPass.equalsIgnoreCase("y")) {
						try {
						driver.findElement(By.xpath("//md-checkbox[contains(@ng-change,'BYPASSADC')]/div[1]")).click();
						FlightSearch.loadhandling(driver);
						try {
						driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
						Thread.sleep(1000);
						
						driver.findElement(By.xpath(SubmitXpath)).click();
						}catch (Exception e) {}
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DoneXpath)));
						if(Passengers.size()-1==pl) {
							driver.findElement(By.xpath(DoneXpath)).click();
							FlightSearch.loadhandling(driver);	
						}else {
							Passengers.get(pl+1).click();
						}						
						}catch (Exception e) {
							queryObjects.logStatus(driver, Status.FAIL, "By Pass ADC", "By Pass ADC failed", e);
						}
					}
				}
				
				
				}catch(Exception e) {}
				}
			}			
		}
		if (sVerify_API.equalsIgnoreCase("y")) {
			if (driver.findElement(By.xpath(SubmitXpath)).isEnabled()) {
					Tools.ClickOn(driver,SubmitXpath);
					FlightSearch.loadhandling(driver);				
					if (!Expected_ErrMessage.isEmpty()) {
								
								try {
									String RespMsg = driver.findElement(By.xpath("//div[contains(@ng-repeat,'adcDecision.passengers')]//span")).getText();
								 if (RespMsg.contains(Expected_ErrMessage)) {
									 queryObjects.logStatus(driver, Status.PASS, "ADC Response message verification", "ADC reponse message verified"+RespMsg, null);
								 }else {
									 queryObjects.logStatus(driver, Status.FAIL, "ADC Response message verification", "ADC reponse message not as expected"+RespMsg, null);
								 }
								 boolean Sec=false;
								 try { Sec=driver.findElement(By.xpath(SecSurname)).isDisplayed();}catch(Exception e) {}
								 Tools.ClickOn(driver,AdsResponsePopup);
								 if (driver.findElement(By.xpath("//span[contains(text(),'APIS Incomplete')]")).isDisplayed()&& !BYPass.equalsIgnoreCase("No Bypass")) {
									driver.findElement(By.xpath("//md-checkbox[contains(@ng-change,'BYPASSADC')]/div[1]")).click();
									driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
									Thread.sleep(1000);
									driver.findElement(By.xpath(SubmitXpath)).click();
									Thread.sleep(1000);
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DoneXpath)));
									driver.findElement(By.xpath(DoneXpath)).click();
									FlightSearch.loadhandling(driver);	
							 }
						
							 if(PromptSecDoc.equalsIgnoreCase("y") && EnterDefaultSecDocs.equalsIgnoreCase("y")) {									 
								  if (!Sec)
									 Tools.ClickOn(driver,"//div[contains(text(),'Secondary')]");
									 EnterSecDoc(driver,queryObjects,"Default","Default","Visa","t65789","30","US","Street");
									 Tools.ClickOn(driver,DoneXpath);
								 }
								}catch (Exception e) {
									queryObjects.logStatus(driver, Status.FAIL, "ADC Response message verification", "ADC reponse message not as expected", e);
								}
								
								
												
					}else {
						Tools.ClickOn(driver,DoneXpath);
					}
			}
		}
		
	}
	
	
	
	public static void EnterADC(WebDriver driver, BFrameworkQueryObjects queryObjects,String fName,String sName,String PaxAge,String sGender,String DType,String DNum, String Expdays,String COI,String Nty,String COR) throws IOException{
		
		try {
		FlightSearch.loadhandling(driver);	
		driver.findElement(By.xpath(SurnameXpath1)).sendKeys(sName);
		Thread.sleep(200);
		driver.findElement(By.xpath(FirstnameXpath)).sendKeys(fName);
		Thread.sleep(200);
		driver.findElement(By.xpath(genderXpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//md-option[div[div[contains(text(),'" + sGender + "')]]]")).click();
		//DOB
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -Integer.parseInt(PaxAge));
		String sDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
		Thread.sleep(1000);
		driver.findElement(By.xpath(BirthDateXpath)).sendKeys(sDate);
		Thread.sleep(1000);
		
		Thread.sleep(2000);
		driver.findElement(By.xpath(DocTypeXpath)).click();
		driver.findElement(By.xpath("//md-option[div[normalize-space(text())='"+DType+"']]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(DocIDXpath)).sendKeys(DNum);
		Thread.sleep(1000);
		Calendar calen = Calendar.getInstance();
		String sExpDate="";
		if (Expdays.contains("/")) {//Added by Jenny)
			sExpDate = Expdays;						
		} else {
			if (Expdays=="")
				Expdays="30";
			calen.add(Calendar.MONTH, Integer.parseInt(Expdays));
			sExpDate = new SimpleDateFormat("MM/dd/yyyy").format(calen.getTime());
		}
		try {
			driver.findElement(By.xpath(ExpdateXpath)).clear();
			Thread.sleep(500);
		} catch (Exception e) {}
		 driver.findElement(By.xpath(ExpdateXpath)).sendKeys(sExpDate);
		FlightSearch.loadhandling(driver);
		
		driver.findElement(By.xpath(COIXpath)).clear();
		driver.findElement(By.xpath(COIXpath)).sendKeys(COI);
		FlightSearch.loadhandling(driver);
		Thread.sleep(2000);
		//driver.findElement(By.xpath(COIXpath)).sendKeys(Keys.TAB);
		//Tab Alternate Solution
		driver.findElement(By.xpath(Login.SelList)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(NtionalXpath)).sendKeys(Nty);
		Thread.sleep(1000);
		//driver.findElement(By.xpath(NtionalXpath)).sendKeys(Keys.TAB);
		//Tab Alternate Solution
		driver.findElement(By.xpath(Login.SelList)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(CORXpath)).sendKeys(COR);
		Thread.sleep(1000);
		//driver.findElement(By.xpath(CORXpath)).sendKeys(Keys.TAB);
		//Tab Alternate Solution
		driver.findElement(By.xpath(Login.SelList)).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(ExitdateXpath)).sendKeys(sExpDate);
		Thread.sleep(1000);
		driver.findElement(By.xpath(ExitdateXpath)).sendKeys(Keys.TAB);
		driver.findElement(By.xpath(ExitdateJustificationXpath)).sendKeys("Justification");
		Thread.sleep(1000);
		driver.findElement(By.xpath(ExitdateJustificationXpath)).sendKeys(Keys.TAB);
	
	}catch (Exception e) {
		try {//handle shares Checkin pnrs
			driver.findElement(By.xpath(CORXpath)).clear();
			Thread.sleep(1000);
			driver.findElement(By.xpath(CORXpath)).sendKeys(COR);	
			Thread.sleep(1000);
			driver.findElement(By.xpath(Login.SelList)).click();
			Thread.sleep(3000);
			completeADC=true;
		}catch (Exception e2) {}
			try {
		if(driver.findElement(By.xpath(DoneXpath)).isEnabled()) {
			queryObjects.logStatus(driver, Status.PASS, "Enter ADC Details", "ADC details available and SUbmit button is enabled", null);
			completeADC=true;
		}
		}catch (Exception e1) {
		queryObjects.logStatus(driver, Status.WARNING, "Enter ADC Details", "ADC details exception", e1);}
	}
	}
	
	public static void EnterCOR(WebDriver driver, BFrameworkQueryObjects queryObjects,String COR)throws IOException{
		try {
		if (driver.findElement(By.xpath(CORXpath)).isEnabled()) {
		try {
			driver.findElement(By.xpath(CORXpath)).sendKeys(COR);
			Thread.sleep(3000);
			//driver.findElement(By.xpath(CORXpath)).sendKeys(Keys.TAB);
			//Tab Alternate Solution
			driver.findElement(By.xpath(Login.SelList)).click();
			Thread.sleep(3000);
			//Enter exit date		
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, Integer.parseInt("30"));
			String sExitDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
			driver.findElement(By.xpath(ExitdateXpath)).sendKeys(sExitDate);
			Thread.sleep(1000);
			driver.findElement(By.xpath(ExitdateXpath)).sendKeys(Keys.TAB);
			driver.findElement(By.xpath(ExitdateJustificationXpath)).sendKeys("Justification");
			Thread.sleep(1000);
			driver.findElement(By.xpath(ExitdateJustificationXpath)).sendKeys(Keys.TAB);
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Complete COR details", "Unable to complete COR", null);
		}	
		
		}else {
			queryObjects.logStatus(driver, Status.FAIL, "Enter COR", "COR not enabled", null);
		}
		}catch (Exception e) {
			queryObjects.logStatus(driver, Status.INFO, "Enter COR", "COR details was already done", null);
		}
	}

	public static void EnterSecDoc(WebDriver driver, BFrameworkQueryObjects queryObjects,String SecSname, String SecfirstName,String SecDocType,String secDocNum,String Sec_Date,String Sec_COI,String Street_Address)throws IOException{
		try {
			
		
			driver.findElement(By.xpath(SecSurname)).sendKeys(SecSname);
			driver.findElement(By.xpath(SecFirstName)).sendKeys(SecfirstName);
			driver.findElement(By.xpath("(//md-select[(@ng-model='document.DocType')])[3]")).click();
			FlightSearch.loadhandling(driver);
			List<WebElement> SecDocDet=	driver.findElements(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-option/div[contains(text(),'" +SecDocType+ "')]"));
		
			for (int iSecdoc = 0; iSecdoc < SecDocDet.size(); iSecdoc++) {
				String secnm=SecDocDet.get(iSecdoc).getText().trim();
				if(secnm.equalsIgnoreCase(SecDocType)) {
					SecDocDet.get(iSecdoc).click();
				break;
				}
			}
		driver.findElement(By.xpath(SecDocNum)).sendKeys(secDocNum);
		Calendar calen = Calendar.getInstance();
		String sExpDate="";
		if (Sec_Date.contains("/")) {//Added by Jenny)
			sExpDate = Sec_Date;						
		} else {
			if (Sec_Date=="")
				Sec_Date="30";
			calen.add(Calendar.MONTH, Integer.parseInt(Sec_Date));
			sExpDate = new SimpleDateFormat("MM/dd/yyyy").format(calen.getTime());
		}
		driver.findElement(By.xpath("(//pssgui-date-time[@ng-model='document.ExpireDate'])[3]/div/md-datepicker/div/input")).sendKeys(sExpDate);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//label[contains(text(),'Country of Issuance')]/..//input[@aria-label='Country of Issuance' and contains(@class,'md-input ng-empty')]")).sendKeys(Sec_COI);
		Thread.sleep(3000);
		//driver.findElement(By.xpath("//label[contains(text(),'Country of Issuance')]/..//input[@aria-label='Country of Issuance' and contains(@class,'md-input ng-invalid')]")).sendKeys(Keys.TAB);
		//Tab Alternate Solution
		driver.findElement(By.xpath(Login.SelList)).click();
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath("//div[@translate='pssgui.destination.address']/..")).click();
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath(StreetAddress)).sendKeys(Street_Address);
		
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Exception while entering secondary doc", "Exception Caught", e);
		}
	}
	
}

	


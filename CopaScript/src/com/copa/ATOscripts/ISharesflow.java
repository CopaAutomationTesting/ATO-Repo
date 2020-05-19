package com.copa.ATOscripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.copa.RESscripts.FlightSearch;
import com.copa.RESscripts.Login;
import com.copa.Util.FlightSearchPageObjects;
import com.copa.Util.url_path;

import FrameworkCode.BFrameworkQueryObjects;

public class ISharesflow  extends FlightSearchPageObjects{
	public static String InboundFlight = "";
	public static String InboundDate = "";
	public static String InboundDays = "";
	public static String INRSA_PNR = "";
	public static String Flight = "";
	public static String Orig = "";
	public static String Destn = "";
	public static String fDate = "";
	public static String cTime = "";
	public static String fTime = "";
	public static String fGate = "";
	public static String fOtrInput = "";
	public static String DlyTime = "";
	public static String Shares = "";
	public static String iPNRNo = "";
	public static String iTicketing = "";
	public static String MultiPNR = "";
	public static String iMultiClass = "";
	public static String iCheckin = "";
	public static String iPassengers = "";
	public static String iGrpPax = "";
	public static String pResField = "";
	public static String pSendBtn = "";
	public static String iMulPNR = "";
	public static String ShareEticket = "";
	public static boolean isNewTab = false;
	public static boolean OpenFlight = false;
	public static String sValidation = "";
	public static int PnrCnt = 0;
	public static String CheckinFlight="";
	public static String CheckinDays="";
	public static String BagCount="B0";
	public static String iPasName="";
	public static String FOP="";          //Atul - 10Mar
	public static String CCNum="";        //Atul - 10Mar
	public static String CC_Exp_Date= ""; //Atul - 10Mar
	public static String InfantPricing="";
	public static String iPasChangedName=""; //Atul- 6Apr
	public static String iJumpSeat;
	public static String Cancelseg="";
	public static String UserID = "";
	public static String Password = "";
	
	public static void ishare(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception
	{
		Shares = FlightSearch.getTrimTdata(queryObjects.getTestData("Shares"));
		String iPNRType = FlightSearch.getTrimTdata(queryObjects.getTestData("PNRType"));
		String iClass = FlightSearch.getTrimTdata(queryObjects.getTestData("Class"));
		String iFromTo = FlightSearch.getTrimTdata(queryObjects.getTestData("From_To"));
		String iDays = FlightSearch.getTrimTdata(queryObjects.getTestData("Days"));
		String iPaxName= FlightSearch.getTrimTdata(queryObjects.getTestData("PaxName"));
		String iFlightnum = FlightSearch.getTrimTdata(queryObjects.getTestData("Flightnum"));
		String iPaxCount = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxCnt"));
		String iSSR = FlightSearch.getTrimTdata(queryObjects.getTestData("SSR"));
		String iSSR_Freetext = FlightSearch.getTrimTdata(queryObjects.getTestData("SSR_Freetext"));		
		sValidation = FlightSearch.getTrimTdata(queryObjects.getTestData("Validation"));
		fOtrInput = FlightSearch.getTrimTdata(queryObjects.getTestData("OtherInput"));
		DlyTime = FlightSearch.getTrimTdata(queryObjects.getTestData("DLYHrs"));
		String iSegCnt = FlightSearch.getTrimTdata(queryObjects.getTestData("SegmentCnt"));
		String iAvailType = FlightSearch.getTrimTdata(queryObjects.getTestData("AvailabilityType"));
		String iNxtSegDays = FlightSearch.getTrimTdata(queryObjects.getTestData("NxtSegDays"));
		String iPAXNRCode = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxNRCode"));
		String iSSRSegment = FlightSearch.getTrimTdata(queryObjects.getTestData("SSRSegments"));
		String iSSRPax = FlightSearch.getTrimTdata(queryObjects.getTestData("SSRPax"));
		iTicketing = FlightSearch.getTrimTdata(queryObjects.getTestData("Ticketing"));
		String iPaxType = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxType"));
		String iChildAge = FlightSearch.getTrimTdata(queryObjects.getTestData("ChildAge"));
		iMultiClass = FlightSearch.getTrimTdata(queryObjects.getTestData("MultiClass"));
		iCheckin = FlightSearch.getTrimTdata(queryObjects.getTestData("Checkin"));
		String iCountry = FlightSearch.getTrimTdata(queryObjects.getTestData("Country"));
		iMulPNR = FlightSearch.getTrimTdata(queryObjects.getTestData("MultiPNR"));
		String FQTVNum = FlightSearch.getTrimTdata(queryObjects.getTestData("FQTVUpdate"));
		String EMail = FlightSearch.getTrimTdata(queryObjects.getTestData("EmailID")); //meenu_to add email in PNRcreation
		String Numofpnr = FlightSearch.getTrimTdata(queryObjects.getTestData("Numofpnr"));
		CheckinFlight = FlightSearch.getTrimTdata(queryObjects.getTestData("CheckinFlight"));
		CheckinDays = FlightSearch.getTrimTdata(queryObjects.getTestData("CheckinDays")); 
		BagCount = FlightSearch.getTrimTdata(queryObjects.getTestData("BagCount"));
		iJumpSeat = FlightSearch.getTrimTdata(queryObjects.getTestData("JumpSeat"));
		Cancelseg = FlightSearch.getTrimTdata(queryObjects.getTestData("Cancelseg"));
		String NewFlight = FlightSearch.getTrimTdata(queryObjects.getTestData("NewFlight"));
		FOP = FlightSearch.getTrimTdata(queryObjects.getTestData("FOP")); //Atul - 10Mar
		CCNum = FlightSearch.getTrimTdata(queryObjects.getTestData("CCNum")); //Atul - 10Mar
		InfantPricing = FlightSearch.getTrimTdata(queryObjects.getTestData("InfantPricing"));
		InboundFlight = FlightSearch.getTrimTdata(queryObjects.getTestData("InboundFlight"));
		InboundDays = FlightSearch.getTrimTdata(queryObjects.getTestData("InboundDays"));
		/*UserID = FlightSearch.getTrimTdata(queryObjects.getTestData("UserID"));
		Password = FlightSearch.getTrimTdata(queryObjects.getTestData("Password"));*/
		String ReaccomList = FlightSearch.getTrimTdata(queryObjects.getTestData("ReaccomPax"));
		String CloseSeats = FlightSearch.getTrimTdata(queryObjects.getTestData("CloseSeats")); //meenu
		String Sync = FlightSearch.getTrimTdata(queryObjects.getTestData("Sync"));
		pResField = "//pre[@id='content-wrap']";
		pSendBtn = "//input[@value='Send']";
		String SharesPNR = "";
		UserID = envRead_IsharesCred(url_path.Instance,0);
		Password = envRead_IsharesCred(url_path.Instance,1);
		
		//meenu
		if (CloseSeats.equalsIgnoreCase("yes")) {
			IshareLogin(driver, queryObjects, Shares);
			Thread.sleep(100);
			Thread.sleep(1000);
			int	days =Integer.parseInt(iDays);	
			ISharesflow.Flight = iFlightnum;
			String	SetDate = Atoflow.AddDateStr(days, "ddMMM", "day", null);
			RequestResponse(driver, queryObjects, "VMS"+iFlightnum+"/"+SetDate+".ALL"+"/"+iFromTo+"MAX0");
			RequestResponse(driver, queryObjects, "I");//TRANS IGN
			RequestResponse(driver, queryObjects, "BSO");//LOG OUT
			if (isNewTab) {
				Atoflow.Close_SwitchTab(driver);
			}
			return;
		}		
		
		try {
			Login.MultiplePNR = "";
			Login.FLIGHTNUM = "";
			Login.PNRNUM =  "";
			if (!sValidation.contains("Flifo")) {
				PnrCnt = 0;
				if(Numofpnr.isEmpty()) {
					Numofpnr="1";
				}
				PnrCnt = Integer.parseInt(Numofpnr);
				for(int cp=0;cp<PnrCnt;cp++) {
					SharesPNR = CreatePNR(driver, queryObjects, iPNRType, iClass, iFromTo, iDays, iPaxName, iFlightnum, iPaxCount, iSSR,iSSR_Freetext,
							iSegCnt, iAvailType, iNxtSegDays, iPAXNRCode, iSSRSegment, iSSRPax, iPaxType, iChildAge, iCountry, FQTVNum, EMail);
				}		
			} else if (sValidation.contains("Flifo")) {
				 FLIFO(driver, queryObjects, sValidation, iFlightnum, iFromTo,"","","");
			}
			//Cancel segment
			if(Cancelseg.equalsIgnoreCase("Yes")) {
				String Res = ""; 
				IshareLogin(driver, queryObjects, Shares);
				Thread.sleep(100);
				if(RequestResponse(driver, queryObjects,"*"+SharesPNR+"").contains("RCVD-PSGR"))
				{
					Thread.sleep(100);
					if(RequestResponse(driver, queryObjects,"X1").contains("NEXT REPLACES")) {
					int	days =Integer.parseInt(iDays);	
					String	SetDate = Atoflow.AddDateStr(days, "ddMMM", "day", null);
						 CreateneSeg(driver,queryObjects,NewFlight,iFromTo,SetDate,iClass );						 
					}	
				}
			}
			
			if(Sync.equalsIgnoreCase("Y")) {

                if(RequestResponse(driver, queryObjects,"T-ET1.1/S1").contains("OK")) {

                       Thread.sleep(100);

                       if(RequestResponse(driver, queryObjects,"*ET").contains("ADJUSTED")) {

                              queryObjects.logStatus(driver, Status.PASS, "PNR Synced", "Synced and Adjusted Succesfully" , null);
                       }
                }
          }
			//Atul- 20Apr
			if (ReaccomList.toUpperCase().contains("YES")) {
				String Res = "";
				IshareLogin(driver, queryObjects, Shares);
				Thread.sleep(100);
				RequestResponse(driver, queryObjects, "BBCMPTY");
				//Change the list size to 2
				new Select(driver.findElement(By.xpath("//select[@id='Type']"))).selectByVisibleText("2");
				//Change the Equipment
				JavascriptExecutor myExecutor = ((JavascriptExecutor)driver);
				WebElement TextBox = driver.findElement(By.name("q"));
				TextBox.getAttribute("value");
				myExecutor.executeScript("arguments[1].value = arguments[0]; ", "6-CA"+cTime+"\n"+Flight+Orig+"1564", TextBox);
				driver.findElement(By.xpath(pSendBtn)).click();
				Res = driver.findElement(By.xpath(pResField)).getText();
				if (Res.contains("CURRENT") && Res.contains("NEW")) {
					Res = RequestResponse(driver, queryObjects, "6-CE"+iFlightnum+"/"+cTime+Orig+".YES|B");
					if (Res.contains("SHIP ASSIGNED")) {
						if (ReaccomList.toUpperCase().contains("SEATCHG")) {
							Res = RequestResponse(driver, queryObjects, "6-LD"+Flight+"/"+cTime+Orig+"|REAC/CHG");		
						} else {
							Res = RequestResponse(driver, queryObjects, "6-LD"+Flight+"/"+cTime+Orig+"|REAC");
						}
						
						//Display the list of Re-accommodated passengers
						if (Res.contains("REACCOM/CHANGED") && !Res.contains("TOTAL PASSENGERS:      0")) {
							queryObjects.logStatus(driver, Status.PASS, "Reacommodated Passenger List With Seats", "Reacommodated Passenger List With Seats is displayed" , null);
						} else if (Res.contains("REACCOM") && !Res.contains("TOTAL PASSENGERS:      0")) 
							queryObjects.logStatus(driver, Status.PASS, "Reacommodated Passenger List", "Reacommodated Passenger Lists is displayed" , null);
						else if (Res.contains("REACCOM") && Res.contains("TOTAL PASSENGERS:      0")) 
							queryObjects.logStatus(driver, Status.WARNING, "Reacommodated Passenger List With Seats", "Reacommodated Passenger List With Seats does not have any passengers" , null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "Reacommodated Passenger List With Seats", "Reacommodated Passenger List With Seats is not displayed" , null);			
						RequestResponse(driver, queryObjects, "I");//TRANS IGN
						RequestResponse(driver, queryObjects, "BSO");//LOG OUT
						return;
					}
				}	 
			}
		}catch(Exception e) {}				
	}
	
	
	public static Void CreateneSeg(WebDriver driver, BFrameworkQueryObjects queryObjects,String iFlightnum, String iFromTo,String SetDate, String iClass ) throws IOException {
		
		if (RequestResponse(driver, queryObjects,"0CM"+iFlightnum+iClass+SetDate+iFromTo+"NN1").contains("ETKT ELIGIBLE")) {
			
				if (RequestResponse(driver, queryObjects,"6P").contains("6P *"))	{
					if (VerifyResponse(driver, queryObjects, "ER", "ETKT/PNR MODIFIED- CHK ITIN", "End Manual Pricing", "Manual Pricing is successful", "Manual Pricing failed")) {
						String Res = RequestResponse(driver, queryObjects, "ER");
						if (Res.contains("APIS-DATA COLLECTED ")) {
							queryObjects.logStatus(driver, Status.PASS, "PNR Modified", "Succesfully" , null);	
						}	
					}
				}				
			}
	return null;
		
	}

	public static boolean VerifyResponse(WebDriver driver, BFrameworkQueryObjects queryObjects, String eRequest, String gResponse, String Verification, String PassMsg, String FailMsg) throws IOException {
		boolean isSuccess = false;
		if (gResponse.contains("GENERAL  INFORMATION FOR:")) {
			((JavascriptExecutor)driver).executeScript("arguments[1].value = arguments[0]; ", eRequest, driver.findElement(By.name("q")));
			driver.findElement(By.xpath(pSendBtn)).click();
			if (driver.findElement(By.xpath(pResField)).getText().contains(gResponse)) {
				isSuccess = true;
			}
		} else {
			if (RequestResponse(driver, queryObjects, eRequest).contains(gResponse)) {
				isSuccess = true;
			}
		}
		if (isSuccess) {
			queryObjects.logStatus(driver, Status.PASS, Verification, PassMsg , null);
		} else {
			queryObjects.logStatus(driver, Status.FAIL, Verification, FailMsg , null);
		}
		
		return isSuccess;
	}
	
	public static String CreatePNR(WebDriver driver, BFrameworkQueryObjects queryObjects, String PNRType, String Class, String FromTo, String Days, String PaxName, String Flightnum, String PaxCount,
			String SSR, String SSR_Freetext,String SegCnt, String AvailType, String NxtSegDays, String PAXNRCode, String SSRSegment, String SSRPax, String PaxType, String ChildAge, String Country, String FQTV, String EmailId) throws Exception {
		String ChangeName = FlightSearch.getTrimTdata(queryObjects.getTestData("ChangeName"));
		String Req = "";String Res = ""; String ReqStr = ""; String rEnt = "";
		String SetDate = ""; String SetType = ""; String Surname = "";
		String Firstname = ""; String InfAge = "";  String SetFrmTo = "";
		String SetClass = "";
		
		String SptSName[] = null; String SptFName[] = null;
		String SptPNR[] = null; String SegmentCnt = ""; String SptSSR[] = null;
		String FlightSplt[] = null; String SptFlight2[] = null; String SetFlight = "";
		String FrmToSplt[] = null; String OrigDest[] = null;
		String gDays[] = null; String SptPaxType[] = null; String SptCls[] = null;
		
		String gFlightNum = ""; String gFromTo = "";
		
		String GetTicket = FlightSearch.getTrimTdata(queryObjects.getTestData("GetTicket"));
		
		boolean isProceed = false; int ClsCnt = 0;
		pResField = "//pre[@id='content-wrap']";
		try {
			if (PaxType.isEmpty()) {
				PaxType = "ADT";
			}
			if (ChildAge.isEmpty() && PaxType.contains("CHD")) {
				ChildAge = "09";
			}
			if (SegCnt.isEmpty()) {
				SegCnt = "1";
			}
			//Date
			int getDays=0;
			if (Days.isEmpty()) {
				Days="0";
			}
			getDays=Integer.parseInt(Days);
			//Class
			if (Class.isEmpty()) {
				Class = "Y";
			}
			//if(driver.findElements(By.xpath(pResField)).size()==0) {
				IshareLogin(driver, queryObjects, Shares);
			//}
			
			if (PNRType.equalsIgnoreCase("WEB")) {
				Req = "BBCMWEB";
				Res="B-IN-CM-WEB";
			} else {
				String variable=FromTo.substring(0,3);
				Req = "BBCM"+variable+""; 
				Res="B-IN-CM-"+variable+"";
			}
			ReqStr = RequestResponse(driver, queryObjects, Req);
			if (ReqStr.contains("SINE")) {
				queryObjects.logStatus(driver, Status.INFO, "Check the application is pointed to the location", "Location is not updated" , null);
			} else {
				queryObjects.logStatus(driver, Status.PASS, "Check the application is pointed to the location", "Location is updated" , null);
			}
			//if (VerifyResponse(driver, queryObjects, Req, Res, "Check the application is pointed to "+PNRType, "Location is updated", "Location is not updated")) {
			if (ReqStr.contains("SINE") || ReqStr.contains(Res)) {
				
				if (Shares.equalsIgnoreCase("SharesA")) {
					RequestResponse(driver, queryObjects, "BSIA");
				} else {
					RequestResponse(driver, queryObjects, "BSIB");
				}
				ClsCnt = Integer.parseInt(PaxCount);
				if(PNRType.equalsIgnoreCase("NRSA")) {
					SetType = "MM";
				} else if(PNRType.equalsIgnoreCase("NRPS")){
					SetType = "PNN";
				} else if(PNRType.equalsIgnoreCase("PD")){
					SetType = "PD"; ClsCnt = 0;
				}  else if(PNRType.equalsIgnoreCase("HL")){
					SetType = "LL"; ClsCnt = 0;
				} else if(PNRType.equalsIgnoreCase("Group") || PNRType.equalsIgnoreCase("Corporate")){
					SetType = "GNN";
				} else {
					SetType = "NN";
				}
				
				if (PaxCount.isEmpty()) {
					PaxCount = "1";
				}
				//Flight Availability
				for (int sc = 1; sc <= Integer.parseInt(SegCnt); sc++) {
					if (Flightnum.equalsIgnoreCase("SameFlight")) {
						Flightnum = Flight;
						FromTo = Orig+Destn;
					}
					if (AvailType.contains("LongCell")) {
						if (!Flightnum.contains("-")) {
							Flightnum = Flightnum+"-";
						}
						FlightSplt = Flightnum.split("-");
					}
					if (Class.contains("-")) {
						SptCls = Class.split("-");
						SetClass = SptCls[sc-1];
					} else {
						SetClass = Class;
					}
					if (!FromTo.contains("-")) {
						FromTo = FromTo+"-";
					}
					FlightSplt = Flightnum.split("-");
					FrmToSplt = FromTo.split("-");
					if (sc > 1) {
						if (!NxtSegDays.contains("-")) {
							NxtSegDays = NxtSegDays+"-";
						}
						gDays = NxtSegDays.split("-");
						SetDate = Atoflow.AddDateStr(Integer.parseInt(gDays[sc-2]), "ddMMM", "day", null);
					} else {
						SetDate = Atoflow.AddDateStr(getDays, "ddMMM", "day", null);
						cTime = SetDate;
					}
					if (FrmToSplt[sc-1].contains(";")) {
						if (AvailType.contains("LongCell")) {
							SptFlight2 = FlightSplt[sc-1].split(";");
						}
						OrigDest = FrmToSplt[sc-1].split(";");
						for (int ff = 0; ff < OrigDest.length; ff++) {
							if (FrmToSplt[sc-1].contains(";")) {
								OrigDest = FrmToSplt[sc-1].split(";");
								SetFrmTo = OrigDest[ff];
							} else {
								SetFrmTo = FrmToSplt[sc-1];
							}
							if (AvailType.contains("LongCell")) {
								SetFlight = SptFlight2[ff];
								if((SetType.contains("PD") || SetType.contains("LL")) && !isProceed) {
									RequestResponse(driver, queryObjects, "VMS"+SetFlight+"/"+SetDate+SetClass+"/"+SetFrmTo+"MAX0");
								}
								if ((!SetFlight.contains("UA") && !SetFlight.contains("LH") && !SetFlight.contains("IB")) && !Shares.equalsIgnoreCase("SharesA")) {
									if (Availability_Sell(driver, queryObjects, SetClass, "A"+SetFlight+"/"+SetDate+SetFrmTo, ClsCnt)) {
										isProceed = true;
										if ((sc-1) ==0) {
											/*Login.FLIGHTNUM = Flight = SetFlight;
											Orig = SetFrmTo.substring(0,3);
											Destn = SetFrmTo.substring(3,6);*/
											break;
										}
									}
								} else {
									isProceed = true;
								}
								
							}							
						}						
					} else {
						SetFrmTo = FrmToSplt[sc-1];
						if (AvailType.contains("LongCell")) {
							SetFlight = FlightSplt[sc-1];
							if((SetType.contains("PD") || SetType.contains("LL")) && !isProceed) {
								RequestResponse(driver, queryObjects, "VMS"+SetFlight+"/"+SetDate+SetClass+"/"+SetFrmTo+"MAX0");
							}
							if (!(SetFlight.contains("UA")||SetFlight.contains("LH")||SetFlight.contains("IB")) && !Shares.equalsIgnoreCase("SharesA")) {
								if (Availability_Sell(driver, queryObjects, SetClass, "A"+SetFlight+"/"+SetDate+SetFrmTo, ClsCnt)) {
									isProceed = true;
								}
							} else {
								isProceed = true;
							}
						}
					}
					
														//Set the Entry for Flight Availability
//-------------------------------------------------------------Longsell Entry----------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
					if (AvailType.contains("LongCell")) {
						if ((SetFlight.contains("UA")||SetFlight.contains("LH")||SetFlight.contains("IB")) && Shares.equalsIgnoreCase("SharesB")) {
							rEnt = "0"+SetFlight+SetClass+""+SetDate+""+SetFrmTo+SetType+PaxCount;
						} else {
							rEnt = "0CM"+SetFlight+SetClass+""+SetDate+""+SetFrmTo+SetType+PaxCount;
						}
						if (sc==1) {
							gFlightNum = SetFlight;
							gFromTo = SetFrmTo;
						}
					    isProceed = true;
//----------------------------------------------------Select the flight based on the Segment Number------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
					} else {
						RequestResponse(driver, queryObjects, "A "+SetFrmTo+" "+SetDate);
						String Rownum = FlightSearch.getTrimTdata(queryObjects.getTestData("FlightRow_No"));
						if(!Rownum.isEmpty()) {
						if(!Rownum.contains("-")) {
							Rownum=Rownum+"-";
						}
						String Row[]=Rownum.split("-") ;
						//for(int i=0;i<Row.length;i++) {
						if(PNRType.equalsIgnoreCase("NRSA") || PNRType.equalsIgnoreCase("NRPS") || PNRType.equalsIgnoreCase("PD") || PNRType.equalsIgnoreCase("HL") || PNRType.equalsIgnoreCase("Group") || PNRType.equalsIgnoreCase("Corporate")) {
							rEnt = "N"+PaxCount+SetClass+Row[sc-1]+SetType;
						} else {
							rEnt = "N"+PaxCount+SetClass+Row[sc-1];
						}
						
						isProceed = true;
						
						}
						else {
							if(PNRType.equalsIgnoreCase("NRSA") || PNRType.equalsIgnoreCase("NRPS") || PNRType.equalsIgnoreCase("PD") || PNRType.equalsIgnoreCase("HL") || PNRType.equalsIgnoreCase("Group") || PNRType.equalsIgnoreCase("Corporate")) {
								rEnt = "N"+PaxCount+SetClass+"1"+SetType;
							} else {
								rEnt = "N"+PaxCount+SetClass+"1";
							}
							isProceed = true;
						}
					}
					Res = RequestResponse(driver, queryObjects, rEnt);
					String getVal[] = null; String Sptval[] = null;				 
					
					if (Res.contains("FLT NOOP") || Res.contains("WTL") || Res.contains("INVLD CLASS")) {
						break;
					} else {
						if (sc==1) {
							getVal = Res.split("\\n");
							if (!Res.contains(SetFrmTo)) {
								if (sValidation.contains("InboundRoute")) {
									SetFrmTo = sValidation.substring(sValidation.indexOf("-")+1);
								}
							}
							for (int gf = 0; gf < getVal.length; gf++) {
								if (getVal[gf].toUpperCase().contains(SetFrmTo.toUpperCase())) {
									Sptval = getVal[gf].split("  ");
									try {
										Sptval[2] = Sptval[2].trim();
										gFlightNum = Sptval[2].substring(0, Sptval[2].length()-1);
									} catch (Exception e) {
										Sptval[3] = Sptval[3].trim();
										gFlightNum = Sptval[3].substring(0, Sptval[3].length()-1);
									}
									gFromTo = SetFrmTo;
									break;
								}
							}
						}						
					}
				}
				
				String str = "";
				if(!SetFlight.isEmpty()) {
				 str = SetFlight.substring(2);
				}
				
				if (!Res.contains("FLT NOOP") && (Res.contains("TERMINAL-")|| Res.contains("SECURE FLIGHT")|| Res.contains("OPERATED BY") || Res.contains("ETKT ELIGIBLE") || Res.contains(str+SetClass)) && isProceed) { 
					queryObjects.logStatus(driver, Status.PASS, "Check the Flight Availability ", "Flight is available for the given route and class" , null);
					
					//Give Group or Corporate Name
					if (PNRType.equalsIgnoreCase("Group") || PNRType.equalsIgnoreCase("Corporate")) {
						if (PNRType.equalsIgnoreCase("Group")) {
							if (VerifyResponse(driver, queryObjects, "-G/"+PaxCount+"GROUP/"+RandomStringUtils.random(8, true, false), "*", "Group Name Entry", "Group name entry is successful", "Group name is not created")) {
								isProceed = true;
							}
						} else if (PNRType.equalsIgnoreCase("Corporate")){
							if (VerifyResponse(driver, queryObjects, "-C/"+PaxCount+"CORP/"+RandomStringUtils.random(8, true, false), "*", "Corporate Name Entry", "Corporate name entry is successful", "Corporate name is not created")) {
								isProceed = true;
							}						
						}
						if (isProceed) {
							isProceed = false;
							if (VerifyResponse(driver, queryObjects, "6PSGR|7T/|9LAX/N000|ER", "RCVD-", "End Transaction and Redisplay after giving "+PNRType+" name", PNRType+" name creation is successful", PNRType+" name creation failed")) {
								isProceed = true;
							}
						}
					} else {
						isProceed = true;
					}
					if (isProceed) {
						
//-------------------------------------------------------------Passenger Names----------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
						Req = ""; rEnt = "";
						isProceed = false;

						if (PaxType.contains("INF") || PaxType.contains("INS")) {
							InfAge = Atoflow.AddDateStr(-1, "ddMMMyy", "year", null);
						}
						if (!PaxType.contains("-")) {
							PaxType = PaxType+"-";
						}
						SptPaxType = PaxType.split("-");
						
						if (PaxName.isEmpty()) {
							for (int pn = 1; pn <= Integer.parseInt(PaxCount); pn++) {
								String TempName = "";
								if (SptPaxType[pn-1].contains("INF")) {
									TempName = "*-INF"+RandomStringUtils.random(5, true, false)+"*"+RandomStringUtils.random(5, true, false)+"/"+InfAge;
								} else if (SptPaxType[pn-1].contains("CHD")) {
									TempName = "*-1CHD"+ChildAge;
								} else if (SptPaxType[pn-1].contains("INS")) {
									TempName = "*-INS"+"/"+InfAge;
								} else {
									TempName = "";
								}
								if (Firstname.isEmpty()) {									
									Firstname = RandomStringUtils.random(5, true, false)+TempName;
								} else {
									Firstname = Firstname+","+RandomStringUtils.random(5, true, false)+TempName;
								}
								if (Surname.isEmpty()) {
									if((PNRType.equalsIgnoreCase("NRSA")||PNRType.equalsIgnoreCase("NRPS")) && !PAXNRCode.isEmpty()) {
										Surname = PAXNRCode+"/"+RandomStringUtils.random(6, true, false);
									} else {
										Surname = RandomStringUtils.random(6, true, false);
									}
									
								} else {
									if((PNRType.equalsIgnoreCase("NRSA")||PNRType.equalsIgnoreCase("NRPS")) && !PAXNRCode.isEmpty()) {
										Surname = Surname+","+PAXNRCode+"/"+RandomStringUtils.random(6, true, false);
									} else {
										Surname = Surname+","+RandomStringUtils.random(7, true, false);
									}
								}
							}
							if (Integer.parseInt(PaxCount) > 1) {
								SptFName = Firstname.split(",");
								SptSName = Surname.split(",");
								for (int pa = 0; pa < SptFName.length; pa++) {
									if(PNRType.equalsIgnoreCase("MultiInitial")) {
										if (rEnt.isEmpty()) {
											rEnt = "-"+PaxCount+SptSName[pa]+"/"+SptFName[pa];
										} else {
											rEnt = rEnt+"/"+SptFName[pa];
										}
									} else {
										if (rEnt.isEmpty()) {
											rEnt = "-"+SptSName[pa]+"/"+SptFName[pa];
										} else {
											rEnt = rEnt+"|"+"-"+SptSName[pa]+"/"+SptFName[pa];
										}
									}
									if (pa==4) {
										iGrpPax = rEnt;
										Res = RequestResponse(driver, queryObjects, rEnt);
										Req = rEnt;
										rEnt ="";
									}
									if (pa>4 && pa==SptFName.length-1) {
										PaxName = Req+"|"+rEnt;
									}
									iPassengers = PaxName = rEnt.toUpperCase();
								}								
							} else {
								iPassengers = PaxName = rEnt = ("-"+Surname+"/"+Firstname).toUpperCase();
							}
						} else {
							
							if (PaxName.contains("sINFAGE")) {
								PaxName = PaxName.replace("sINFAGE", InfAge);
							}
							rEnt = PaxName;
							iPassengers = PaxName.toUpperCase();
						}
						
						if (VerifyResponse(driver, queryObjects, rEnt, "*", "Enter the Passenger Name", "Passengers are added", "Unable to add Passengers")) {
							
//-------------------------------------------------------------Add FQTV Number----------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
							if (!FQTV.isEmpty()) {
								RequestResponse(driver, queryObjects, "-1.1@*"+FQTV);
							}
							//Add Email ID
							if (!EmailId.isEmpty()) {
								String Emailfrmt = "5EMAIL RQSTD"+EmailId;
								RequestResponse(driver, queryObjects, Emailfrmt);
							}
							if (PNRType.equalsIgnoreCase("Group") || PNRType.equalsIgnoreCase("Corporate")) {
								rEnt = "6PSGR|ER";
							} else {
								rEnt = "6PSGR|7T/|9LAX/N000|ER";
							}
							//End Transaction and ReDisplay
							if (VerifyResponse(driver, queryObjects, rEnt, "RCVD-", "End Transaction and Redisplay PNR", "PNR creation is successful", "PNR creation failed")) {
								
								//Ignore Transaction and ReDisplay
								Res = RequestResponse(driver, queryObjects, "IR");
								SptPNR = Res.split(" ");
								iPNRNo = SptPNR[0];
								
								if (!CheckinFlight.isEmpty()) {
									Login.FLIGHTNUM =Flight=CheckinFlight;
								}else {
									Login.FLIGHTNUM = Flight = gFlightNum;
								}
							if(!CheckinDays.isEmpty())	{								
								cTime = Atoflow.AddDateStr(Integer.parseInt(CheckinDays), "ddMMM", "day", null);
							}
								if (gFromTo.isEmpty()) {
									Orig = SetFrmTo.substring(0,3);
									Destn = SetFrmTo.substring(3,6);
								} else {
									Orig = gFromTo.substring(0,3);
									Destn = gFromTo.substring(3,6);
								}
								
								SptPNR = null;
								SptPNR = Res.split("\\n");
								for (int rs = 0; rs < SptPNR.length; rs++) {
									if (SptPNR[rs].contains("FONE-")) {
										SegmentCnt = SptPNR[rs-1].trim();
										break;
									}
								}
								SegmentCnt = SegmentCnt.trim().substring(0, 2).trim();
								Res = iPNRNo;
								// added condition for nrsa and nrps pax rushil
								if(PNRType.equalsIgnoreCase("NRSA")|| PNRType.equalsIgnoreCase("NRPS")) {
									String[] paxname = iPassengers.split("/");
									iPasName = paxname[1];
								//iPasName = iPassengers.substring(iPassengers.indexOf("/")+1, iPassengers.indexOf("/")).toUpperCase();
								} else {
								iPasName = iPassengers.substring(iPassengers.indexOf("-")+1, iPassengers.indexOf("/")).toUpperCase();
								}
//-------------------------------------------------------------Add SSR----------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
								if (!SSR_Freetext.isEmpty()) {
									isProceed = false;
									if (SSRSegment.isEmpty()) {
										SSRSegment = "ALL";
									}
									if (SSRPax.isEmpty()) {
										SSRPax = "ALL";
									}
									for (int sg = 1; sg <= Integer.parseInt(SegmentCnt); sg++) {
										if (SSRSegment.equalsIgnoreCase("ALL")) {
											Req = SSR_Freetext+"S"+sg;
										} else if(Integer.parseInt(SSRSegment)==sg) {
											Req = SSR_Freetext+"S"+SSRSegment;
										}
										for (int px = 1; px <= Integer.parseInt(PaxCount); px++) {
											Res = "";
											if (SSRPax.equalsIgnoreCase("All")) {
												rEnt = Req+"N"+px;
												if (VerifyResponse(driver, queryObjects, rEnt, "*", "Add SSR", "SSR is added", "Unable to add SSR")) {
													isProceed = true;
												}
											} else if(Integer.parseInt(SSRPax)==sg) {
												rEnt = Req+"N"+SSRPax;
												if (VerifyResponse(driver, queryObjects, rEnt, "*", "Add SSR", "SSR is added", "Unable to add SSR")) {
													isProceed = true;
												}
											}									
										}								
									}
									Res = RequestResponse(driver, queryObjects, "6PSGR|ER");
								} else {
									isProceed = true;
								}
								if (isProceed) {
									//Single SSR
									if (!SSR.isEmpty()) {
										isProceed = false;
										if (SSR.contains(";;")) {
											SptSSR = SSR.split(";;");
											for (int sr = 0; sr < SptSSR.length; sr++) {
												if (VerifyResponse(driver, queryObjects, SptSSR[sr], "*", "Add SSRs", "SSR is added", "Unable to add SSR")) {
													isProceed = true;
												}
											}
										} else {
											if (VerifyResponse(driver, queryObjects, SSR, "*", "Add SSRs", "SSR is added", "Unable to add SSR")) {
												isProceed = true;
											}
										}
										Res = RequestResponse(driver, queryObjects, "6PSGR|ER");
									}
									
//-------------------------------------------------------------Ticketing----------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------									
									if (isProceed) {
										//Ticketing
										if (iTicketing.equalsIgnoreCase("yes")) {
											if (!sValidation.equalsIgnoreCase("InfPricing") && !sValidation.equalsIgnoreCase("ManualPricing")) {
												//Atul 10Mar - Issue a ticket by CC
												if (FOP.equalsIgnoreCase("creditcard")) {
													CC_Exp_Date = Atoflow.AddDateStr(1, "MMyy", "year", null);
													Res = RequestResponse(driver, queryObjects, "T-$"+CCNum+"/"+CC_Exp_Date+"/N54321|ET");
													
												}
												//Atul 19Mar - Issue a ticket by 2 CC
												else if (FOP.equalsIgnoreCase("2creditcard")) {
													RequestResponse(driver, queryObjects, "$-$-");
													RequestResponse(driver, queryObjects, "FF");
													RequestResponse(driver, queryObjects, "ER");
													RequestResponse(driver, queryObjects, "IR");
													String SplitCCNum[] = CCNum.split(";");
													CC_Exp_Date = Atoflow.AddDateStr(1, "MMyy", "year", null);
													Res = RequestResponse(driver, queryObjects, "T-$"+SplitCCNum[0]+"/"+CC_Exp_Date+"/N54321"+"/1000"+"-"+SplitCCNum[1]+"/"+CC_Exp_Date+"/N54321"+"/1000"+"|ET");
												}
												else {
													Res = RequestResponse(driver, queryObjects, "T-$CASH|ET");													
												}
																						
											} 
											if(Res.contains("NO VALID FARE") || Res.contains("UNABLE TO PRICE")|| Res.contains("NO FARE FOR BOOKING CODE") || Res.contains("USE DISCOUNT PRICING") || sValidation.equalsIgnoreCase("InfPricing") || sValidation.equalsIgnoreCase("ManualPricing")) {
												isProceed = false;
												//Manual Ticketing
												Res = "";
												if (PNRType.equalsIgnoreCase("NRSA")) {
													Res = RequestResponse(driver, queryObjects, "$-$-ZO80P");
													ReqStr = RequestResponse(driver, queryObjects, "md");
													if (Res.contains("80 PERCENT MANUAL DISC APPLIED") ||  ReqStr.contains("80 PERCENT MANUAL DISC APPLIED")) {
														queryObjects.logStatus(driver, Status.PASS, "Give Pricing Entry", "Pricing is successful" , null);
														isProceed = true;
													}
												} 
												else if (PNRType.equalsIgnoreCase("NRPS")) {
													Res = RequestResponse(driver, queryObjects, "$-$-ZO100P");
													ReqStr = RequestResponse(driver, queryObjects, "md");
													if (Res.contains("100 PERCENT MANUAL DISC APPLIED") ||  ReqStr.contains("100 PERCENT MANUAL DISC APPLIED")) {
														queryObjects.logStatus(driver, Status.PASS, "Give Pricing Entry", "Pricing is successful" , null);
														isProceed = true;
													}
												} else if (sValidation.equalsIgnoreCase("InfPricing")) {
													if (InfantPricing.contains("ADT")) {
														RequestResponse(driver, queryObjects, InfantPricing);
													} else {
														RequestResponse(driver, queryObjects, "$-$-PADT/INF");
													}
													
												} else {
													if (VerifyResponse(driver, queryObjects, "FC|CCALC FARE END |Y1234.12/X12.12|-Y1|E1NON-REFUNDABLE", "*", "Manual Ticketing", "PNR is manually ticketed", "Unable to ticket the PNR manually")) {
														isProceed = true;
														//FC#CCALC FARE END#Y1234.12/X12.12#-Y1#E1PENALTY
														/*CHK DECIMALS IN AMOUNT FIELDS
														R	/FLWG DATA NOT ENTERED/PROCESSED:
														R	#Y2000.00/X20.00#-Y1#E1PENALTY*///Error Message

													}
												}
												if (VerifyResponse(driver, queryObjects, "FF", "FARE QUOTE  1 FILED", "File Fare Quote", "Fare Qoute is filed successful", "Unable to file fare quote")) {
													if(PNRType.equalsIgnoreCase("NRSA") || PNRType.equalsIgnoreCase("NRPS") || sValidation.equalsIgnoreCase("InfPricing")) {
														Res = RequestResponse(driver, queryObjects, "ER");
														ReqStr = RequestResponse(driver, queryObjects, "MD");
														if (Res.contains("FARE QUOTE-AUTO PRICED") || Res.contains("FARE QUOTE-AUTO PRICED")) {
															queryObjects.logStatus(driver, Status.PASS, "End Manual Pricing", "Manual Pricing is successful" , null);
																//Atul 10Mar - Issue a ticket by CC
																if (FOP.equalsIgnoreCase("creditcard")) {
																	CC_Exp_Date = Atoflow.AddDateStr(1, "MMyy", "year", null);
																	Res = RequestResponse(driver, queryObjects, "T-$"+CCNum+"/"+CC_Exp_Date+"/N54321|ET");
																	
																}//Atul 19Mar - Issue a ticket by 2 CC
																else if (FOP.equalsIgnoreCase("2creditcard")) {
																	CC_Exp_Date = Atoflow.AddDateStr(1, "MMyy", "year", null);
																	String SplitCCNum[] = CCNum.split(";");													
																	Res = RequestResponse(driver, queryObjects, "T-$"+SplitCCNum[0]+"/"+CC_Exp_Date+"/N54321"+"/1000"+"-"+SplitCCNum[1]+"/"+CC_Exp_Date+"/N54321"+"/1000"+"|ET");
																}
																else {
																	Res = RequestResponse(driver, queryObjects, "T-$CASH|ET");
																}
														}
													}
													else {
														Res = RequestResponse(driver, queryObjects, "ER");
														ReqStr = RequestResponse(driver, queryObjects, "MD");
														if (Res.contains("FARE QUOTE-MANUAL PRICED") || Res.contains("FARE QUOTE-MANUAL PRICED")) {
															queryObjects.logStatus(driver, Status.PASS, "End Manual Pricing", "Manual Pricing is successful" , null);
															//Atul 10Mar - Issue a ticket by CC
															if (FOP.equalsIgnoreCase("creditcard")) {
																Res = RequestResponse(driver, queryObjects, "T-$"+CCNum+"/"+CC_Exp_Date+"/N54321|ET");
															}//Atul 19Mar - Issue a ticket by 2 CC
															else if (FOP.equalsIgnoreCase("2creditcard")) {
																String SplitCCNum[] = CCNum.split(";");
																Res = RequestResponse(driver, queryObjects, "T-$"+SplitCCNum[0]+"/"+CC_Exp_Date+"/N54321"+"/1000"+"-"+SplitCCNum[1]+"/"+CC_Exp_Date+"/N54321"+"/1000"+"|ET");
															}
															else {
																Res = RequestResponse(driver, queryObjects, "T-$CASH|ET");
															}
													}}
												}
											}
											ReqStr = RequestResponse(driver, queryObjects, "IR");
											if (Res.contains("TKT ISSUED") || Res.contains("TKT DTE")|| ReqStr.contains("ETKT") || ReqStr.contains("RCVD")|| Res.contains("USED")) {
												queryObjects.logStatus(driver, Status.PASS, "Issue Ticket", "PNR is ticketed" , null);
												if(GetTicket.equalsIgnoreCase("yes")) {
													if (VerifyResponse(driver, queryObjects, "*EH", "FP CASH", "E-Ticket", "got successful", "failed")) {
														Res = RequestResponse(driver, queryObjects, "*EH");
														String[] eticket=Res.split("\\n");
														eticket=eticket[0].split("230");		 
														String	eticket1=eticket[1].substring(0, 10);
														ShareEticket ="230"+eticket1;
													}
												}
											} else {
												queryObjects.logStatus(driver, Status.FAIL, "Issue Ticket", "Unable to issue ticket" , null);
											}
											ReqStr ="";
//-------------------------------------------------------------Change Name- Atul 6Apr--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------											
											Res = RequestResponse(driver, queryObjects, "IR");
											if (ChangeName.equalsIgnoreCase("yes")) {
												Firstname = RandomStringUtils.random(6, true, false);
												Surname = RandomStringUtils.random(6, true, false);
												Res = RequestResponse(driver, queryObjects, "-1@"+Surname+"/"+Firstname);
												Res = RequestResponse(driver, queryObjects, "6P");
												Res = RequestResponse(driver, queryObjects, "ER");
												if (Res.contains("PNR MODIFIED"))
													queryObjects.logStatus(driver, Status.PASS, "Change Name", "Name is changed successfully" , null);
												iPasChangedName = Firstname;
											}
											
//-------------------------------------------------------------Open Flight--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
											if (!Flight.contains("UA")) {
												Thread.sleep(2000);
												if (!OpenFlight) {
													
													//Navira - 17April
													if(!CheckinFlight.isEmpty()) {
														Orig = SetFrmTo.substring(0,3);
													}
													OpenFlight Open = new OpenFlight(Flight, Orig, cTime, driver, queryObjects);
													Open.run();
													OpenFlight = true;
												}
											}
										}
										
//-------------------------------------------------------------Checkin------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
											if (iCheckin.equalsIgnoreCase("yes")) {
												Thread.sleep(2000);
												Res = RequestResponse(driver, queryObjects, "I");//TRANS IGN
												//Navira - 17April
												if(!CheckinFlight.isEmpty())
													Orig = SetFrmTo.substring(0,3);
												String Chckinchld=FlightSearch.getTrimTdata(queryObjects.getTestData("Chckinchld"));
												if(Chckinchld.equalsIgnoreCase("yes")) {												
													Req="6-"+Flight+"/"+cTime+Orig+iPassengers.toUpperCase()+"\n"+"C1B0";													
													JavascriptExecutor myExecutor = ((JavascriptExecutor)driver);
													WebElement TextBox = driver.findElement(By.name("q"));
													TextBox.getAttribute("value");
													myExecutor.executeScript("arguments[1].value = arguments[0]; ", Req, TextBox);													
													driver.findElement(By.xpath("//input[@value='Send']")).click();													
													queryObjects.logStatus(driver, Status.PASS, "Passenger checked in as CHild", "Succcesful",null);
													
												} else { 
													if (!Flight.contains("UA")) {
														/*if (!OpenFlight) {
															Thread.sleep(2000);
															OpenFlight Open = new OpenFlight(Flight, Orig, cTime, driver, queryObjects);
															Open.run();
															OpenFlight = true;
														}*/
														isProceed = false;												
														String Pax = "";
														String sSpecificCheckin =  FlightSearch.getTrimTdata(queryObjects.getTestData("CheckinSpecificPax"));
														if (!sSpecificCheckin.isEmpty())
															iPassengers = sSpecificCheckin;
	
													//With Bags
													//UAFormat-6-948/13FEBSFO-Milla/nithilam#N//nB2-10653498998-9 && //CMFormat-6-948/13FEBSFO-Milla/nithilam#N//nB1/36-812772
													//Without Bags
													//Format-6-948/13FEBSFO-Milla/nithilam#N//nB0
													String SptChkin[] = null;
													int BagCnt = 0;
													String BagTag = ""; String BagWgt = ""; String Baggage = "";
													if (iPassengers.contains("|")) {
														Pax = iPassengers.replace("|", ",");
														SptChkin = Pax.split(",");
													}
													
													//Set Form Size 2
													Select sel=new Select(driver.findElement(By.xpath("//select[@id='Type']")));
													sel.selectByVisibleText("2");
													if (SptChkin!=null) {
														for (int ck = 0; ck < SptChkin.length; ck++) {
															BagTag = ""; BagWgt = "";
															//With and Without Bags
															if(BagCount.isEmpty()) {
																Baggage ="B0"; //Baggage count Added by Ramya
															} else {
																BagCnt = Integer.parseInt(BagCount.replaceAll("B", ""));
																for (int bc = 1; bc <= BagCnt; bc++) {
																	if (BagTag.isEmpty() && BagWgt.isEmpty()) {
																		BagTag = RandomStringUtils.random(6, false, true);
																		BagWgt = ThreadLocalRandom.current().nextInt(20, 25)+"";
																	} else {
																		BagTag = BagTag+"-"+RandomStringUtils.random(6, false, true);
																		BagWgt = BagWgt+"/"+ThreadLocalRandom.current().nextInt(20, 25);
																	}														
																}
																if (Shares.equalsIgnoreCase("SharesA")) {
																	Baggage = BagCount+"-"+BagTag;
																} else {
																	Baggage = BagCount+"/"+BagWgt+"-"+BagTag;
																}													
															}
															
															if (SptChkin[ck].toUpperCase().contains("-S0") && PNRType.equalsIgnoreCase("NRSA")) {
																SptChkin[ck] = "-"+SptChkin[ck].substring(SptChkin[ck].indexOf("/", 1)+1, SptChkin[ck].length());															
															}
															if (SptChkin[ck].toUpperCase().contains("-P0") && PNRType.equalsIgnoreCase("NRPS")) {
																SptChkin[ck] = "-"+SptChkin[ck].substring(SptChkin[ck].indexOf("/", 1)+1, SptChkin[ck].length());
															}
															if (SptChkin[ck].contains("*-1CHD")) {
																SptChkin[ck] = SptChkin[ck].substring(0, SptChkin[ck].indexOf("*-1CHD"));
															} else if (SptChkin[ck].contains("*-IN")) {
																SptChkin[ck] = SptChkin[ck].substring(0, SptChkin[ck].indexOf("*-IN"));
															}
															if (SetType.contains("PD") || SetType.contains("LL")) {
																rEnt = "6-W"+Flight+"/"+cTime+Orig+SptChkin[ck].toUpperCase()+"\n"+Baggage;
																Res = "CABIN STBY LIST"; Req = "SEATS ASSIGNED";
															} else {
																rEnt = "6-"+Flight+"/"+cTime+Orig+SptChkin[ck].toUpperCase()+"|N"+"\n"+Baggage;
																Res = "SEATS ASSIGNED";
															}
															//For Domestic Flights
															if (gFromTo.toUpperCase().contains("PTYDAV") || gFromTo.toUpperCase().contains("DAVPTY")) {
																RequestResponse(driver, queryObjects, rEnt);
																if (driver.findElement(By.xpath(pResField)).getText().contains(Res)|| driver.findElement(By.xpath(pResField)).getText().contains("END OF API COLLECT MODE") || driver.findElement(By.xpath(pResField)).getText().contains(Req)) {
																	isProceed = false;																
																} else {
																	isProceed = true;
																}
															} else {
																isProceed = true;
															}
															if (isProceed) {
																VerifyResponse(driver, queryObjects, rEnt, "GENERAL  INFORMATION FOR:", "Passenger Check in - APIS display", "Passenger information page(APIS) is displayed", "Passenger information page(APIS) is not displayed");
																FillForm(driver, queryObjects, SptChkin[ck].toUpperCase(), SptPaxType[ck], Country);
																if (driver.findElement(By.xpath(pResField)).getText().contains("DOCUMENT TYPE")) {
																	FillForm(driver, queryObjects, SptChkin[ck].toUpperCase(), SptPaxType[ck], Country);
																	driver.findElement(By.xpath(pSendBtn)).click();
																}
																if (driver.findElement(By.xpath(pResField)).getText().contains("COUNTRY OF RESIDENCE")) {
																		FillForm(driver, queryObjects, "COUNTRY OF RESIDENCE", "", Country);
																}															
																if (driver.findElement(By.xpath(pResField)).getText().contains("STREET ")) {
																	FillForm(driver, queryObjects, "STREET ", "", Country);
																}
																if (driver.findElement(By.xpath(pResField)).getText().contains(Res)|| driver.findElement(By.xpath(pResField)).getText().contains("END OF API COLLECT MODE") || driver.findElement(By.xpath(pResField)).getText().contains(Req)) {
																	isProceed = true;
																	queryObjects.logStatus(driver, Status.PASS, "Passenger Checkin", SptChkin[ck].toUpperCase()+" Passenger Checked in successfully" , null);
																} else {
																	queryObjects.logStatus(driver, Status.FAIL, "Passenger Checkin", SptChkin[ck].toUpperCase()+" Passenger Check in failed" , null);
																}
															}														
															Res = RequestResponse(driver, queryObjects, "I");//TRANS IGN
														}
													} else {
														if(BagCount.isEmpty()) {
															Baggage ="B0"; //Baggage count Added by Ramya
														} else {
															BagCnt = Integer.parseInt(BagCount.replaceAll("B", ""));
															for (int bc = 1; bc <= BagCnt; bc++) {
																if (BagTag.isEmpty() && BagWgt.isEmpty()) {
																	BagTag = RandomStringUtils.random(6, false, true);
																	BagWgt = ThreadLocalRandom.current().nextInt(20, 25)+"";
																} else {
																	BagTag = BagTag+"-"+RandomStringUtils.random(6, false, true);
																	BagWgt = BagWgt+"/"+ThreadLocalRandom.current().nextInt(20, 25);
																}														
															}
															if (Shares.equalsIgnoreCase("SharesA")) {
																Baggage = BagCount+"-"+BagTag;
															} else {
																Baggage = BagCount+"/"+BagWgt+"-"+BagTag;
															}													
														}
														if (iPassengers.toUpperCase().contains("-S0") && PNRType.equalsIgnoreCase("NRSA")) {
															iPassengers = "-"+iPassengers.substring(iPassengers.indexOf("/", 1)+1, iPassengers.length());															
														}
														if (iPassengers.toUpperCase().contains("-P0") && PNRType.equalsIgnoreCase("NRPS")) {
															iPassengers = "-"+iPassengers.substring(iPassengers.indexOf("/", 1)+1, iPassengers.length());															
														}
														if (iPassengers.contains("*-1CHD")) {
															iPassengers = iPassengers.substring(0, iPassengers.indexOf("*-1CHD"));														
														} else if (iPassengers.contains("*-IN")) {
															iPassengers = iPassengers.substring(0, iPassengers.indexOf("*-IN"));
														}else if (iPassengers.contains("-3")) {
															iPassengers = iPassengers.replace("-3", "-");
														}
														if (SetType.contains("PD") || SetType.contains("LL")) {
															rEnt = "6-W"+Flight+"/"+cTime+Orig+iPassengers.toUpperCase()+"\n"+Baggage;
															Res = "CABIN STBY LIST"; Req = "SEATS ASSIGNED";
														} else {
															rEnt = "6-"+Flight+"/"+cTime+Orig+iPassengers.toUpperCase()+"|N"+"\n"+Baggage;
															Res = "SEATS ASSIGNED";
														}
														//For Domestic Flights
														if (gFromTo.toUpperCase().contains("PTYDAV") || gFromTo.toUpperCase().contains("DAVPTY")) {
															RequestResponse(driver, queryObjects, rEnt);
															if (driver.findElement(By.xpath(pResField)).getText().contains(Res)|| driver.findElement(By.xpath(pResField)).getText().contains("END OF API COLLECT MODE") || driver.findElement(By.xpath(pResField)).getText().contains(Req)) {
																isProceed = false;																
															} else {
																isProceed = true;
															}
														} else {
															isProceed = true;
														}
														if (isProceed) {
															VerifyResponse(driver, queryObjects, rEnt, "GENERAL  INFORMATION FOR:", "Passenger Check in - APIS display", "Passenger information page(APIS) is displayed", "Passenger information page(APIS) is not displayed");
															FillForm(driver, queryObjects, iPassengers, SptPaxType[0], Country);
															if (driver.findElement(By.xpath(pResField)).getText().contains("DOCUMENT TYPE")) {
																FillForm(driver, queryObjects, iPassengers, SptPaxType[0], Country);
																driver.findElement(By.xpath(pSendBtn)).click();
															}
															if (driver.findElement(By.xpath(pResField)).getText().contains("COUNTRY OF RESIDENCE")) {
																FillForm(driver, queryObjects, "COUNTRY OF RESIDENCE", "", Country);
															}														
															if (driver.findElement(By.xpath(pResField)).getText().contains("STREET ")) {
																FillForm(driver, queryObjects, "STREET ", "", Country);
															}
															if (driver.findElement(By.xpath(pResField)).getText().contains(Res) || driver.findElement(By.xpath(pResField)).getText().contains(Req) || driver.findElement(By.xpath(pResField)).getText().contains("END OF API COLLECT MODE")) {
																isProceed = true;
																queryObjects.logStatus(driver, Status.PASS, "Passenger Checkin",iPassengers.toUpperCase()+" Passenger Checked in successfully" , null);
															} else {
																queryObjects.logStatus(driver, Status.FAIL, "Passenger Checkin", iPassengers.toUpperCase()+" Passenger Check in failed" , null);
															}
															
															//Himani 10APR
															if(iJumpSeat.equalsIgnoreCase("yes")) {
																String JSeat= "6-JS"+Flight+"/"+cTime+iPassengers+"*FDJ/CM123456."+Destn;
																VerifyResponse(driver, queryObjects, JSeat, "JUMP SEAT ASSIGNED", "Assigning Jump Seat", iPassengers+" is assigned Jump Seat", iPassengers+" is not assigned Jump Seat");
															}
														}													
													}
												}
											}
										}
										if (isProceed) {
											INRSA_PNR = iPNRNo;
											Login.PNRNUM = iPNRNo;
										}
										if (iMulPNR.equalsIgnoreCase("yes")) {
											if (MultiPNR.isEmpty()) {
												MultiPNR = iPNRNo;
											} else {
												MultiPNR = MultiPNR+";"+iPNRNo;
											}
											Login.MultiplePNR = MultiPNR;
											System.out.println(MultiPNR);
										}
										if (!InboundFlight.isEmpty()) {
											InboundDate = Atoflow.AddDateStr(Integer.parseInt(InboundDays), "ddMMM", "day", null);
											OpenFlight Open = new OpenFlight(InboundFlight, Destn, InboundDate, driver, queryObjects);
											Open.run();
										}
									}
								}
							}
						}				
					}					
				} else {
					queryObjects.logStatus(driver, Status.FAIL, "Check the Flight Availability ", "Flight is not available for the given date" , null);
				}

//------------------------------------------------------Ignore and Logout-----------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------
				Res = RequestResponse(driver, queryObjects, "I");//TRANS IGN
				RequestResponse(driver, queryObjects, "VMS"+SetFlight+"/"+SetDate+".ALL"+"/"+SetFrmTo+"MAX");
				RequestResponse(driver, queryObjects, "VML"+SetFlight+"/"+SetDate+"/"+SetFrmTo.substring(0, 3)+"AUL/1.16");
				RequestResponse(driver, queryObjects, "VML"+SetFlight+"/"+SetDate+"/"+SetFrmTo.substring(0, 3)+"AUL/8.138");
				RequestResponse(driver, queryObjects, "I");//TRANS IGN
				Res = RequestResponse(driver, queryObjects, "BSO");//LOG OUT
				
				//CheckinCommand
			}
		}catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Create PNR ", "PNR creation failed" , e);
		}
		if (isNewTab) {
			Atoflow.Close_SwitchTab(driver);
		}
		return iPNRNo;
		
	}

//----------------------------------------------------------------------Flight Update - FLIFO -------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static String FLIFO(WebDriver driver, BFrameworkQueryObjects queryObjects, String Act, String Flights, String OrgDestn, String DteTime, String Cbn, String Cnt) throws Exception {
		String RetVal = ""; String RetVal1 = ""; String Response = ""; String SptRes[] = null; String ResSpt[] = null;
		String cDate = ""; String FormatTime = ""; String Entry = ""; String UpdateTime = "";
		pResField = "//pre[@id='content-wrap']";
		pSendBtn = "//input[@value='Send']";
		//if(driver.findElements(By.xpath(pResField)).size()==0) {
			IshareLogin(driver, queryObjects, Shares);
		//}
		String SptFgts[] = Flights.split(";");
		String SptOrgDestn[] = OrgDestn.split(";");
		for (int gd = 0; gd < 2; gd++) {
			if (DteTime.isEmpty()) {
				cDate = Atoflow.AddDateStr(gd, "ddMMM", "day", null).toUpperCase();
			} else {
				cDate = DteTime.toUpperCase();
			}			
			for (int ff = 0; ff < SptFgts.length; ff++) {
				Response = RequestResponse(driver, queryObjects, "2"+SptFgts[ff]+"/"+cDate+" "+SptOrgDestn[ff].substring(0, 3));
				if (!Response.contains("FLT NOOP")) {
					SptRes = Response.split("\\n");
					for (int srs = 0; srs < SptRes.length; srs++) {
						if (SptRes[srs].contains("SKED") && SptRes[srs].contains("ORIG")) {
							ResSpt = SptRes[srs].split("    ");
							if (ResSpt[1].isEmpty()) {
								ResSpt = SptRes[srs].split("   ");
							}
							break;
						}
					}
					if (ResSpt[1].length()==4) {
						ResSpt[1] = "0"+ResSpt[1];
					}
					//To Open the flight
					if (!OpenFlight) {
						OpenFlight Open = new OpenFlight(SptFgts[ff], SptOrgDestn[ff].substring(0, 3), cDate, driver, queryObjects);
						Open.run();
						OpenFlight = true;
					}					
					if (!DlyTime.isEmpty()) {
						FormatTime = Atoflow.AddDateStr(Integer.parseInt(DlyTime), "hhmma", "minute", new SimpleDateFormat("ddMMMhhmmaa").parse(cDate+ResSpt[1]+"M"));
					}
					if (FormatTime.length()>5) {
						UpdateTime = FormatTime.substring(0, FormatTime.length()-1);
					} else {
						UpdateTime = FormatTime;
					}
					if (Act.contains("WgtBalRestrict")) {
						Entry = "6:CW"+SptFgts[ff]+"/"+cDate+SptOrgDestn[ff].substring(0, 3)+"."+Cbn+"|"+Cnt;
						RetVal ="WEIGHT AND BALANCE RESTRICTION APPLIED"; RetVal1 = "RELEASE";
					}
					if (Act.contains("Delay")) {
						Entry = "2P"+SptFgts[ff]+"/"+cDate+" OUT "+SptOrgDestn[ff].substring(0, 3)+" "+UpdateTime+" FLIFOCheck";
						RetVal ="*"; RetVal1 = "RPLCD FLWG MSG";
					}
					if (Act.contains("OnTime")) {
						FormatTime = ResSpt[1];
						//RequestResponse(driver, queryObjects, "2P"+SptFgts[ff]+"/"+cDate+" OFF "+SptOrgDestn[ff].substring(0, 3)+" "+ResSpt[1]+" FLIFOCheck");
						Entry = "2P"+SptFgts[ff]+"/"+cDate+" OUT "+SptOrgDestn[ff].substring(0, 3)+" "+ResSpt[1]+" FLIFOCheck";
						RetVal ="*"; RetVal1 = "RPLCD FLWG MSG";
					}
					if (Act.contains("Flifo_Delay")) {
						Entry = "2P"+SptFgts[ff]+"/"+cDate+" ETD "+SptOrgDestn[ff].substring(0, 3)+" "+UpdateTime+" FLIFOCheck";
						RetVal ="*"; RetVal1 = "RPLCD FLWG MSG";
					}
					if (Act.contains("Reinstate")) {
						FormatTime = ResSpt[1];
						Entry = "2X"+SptFgts[ff]+"/"+cDate+"|"+SptOrgDestn[ff].substring(0, 3)+"/ FX REINSTATEFLIGHT";
						RetVal ="CNLD FLWG MSG";
					}
					if (Act.contains("FXCancel")) {//"RPLCD FLWG MSG"
						Entry = "2N"+SptFgts[ff]+"/"+cDate+" | "+SptOrgDestn[ff].substring(0, 3)+"/ FX CANCELEDFLIGHT";
						RetVal ="RPLCD FLWG MSG"; FormatTime = "";
					}
					if (Act.contains("LXCancel")) {//"RPLCD FLWG MSG"
						Entry = "2N"+SptFgts[ff]+"/"+cDate+" | "+SptOrgDestn[ff].substring(0, 3)+"/ LX CANCELEDFLIGHT";
						RetVal ="*"; RetVal1 = "RPLCD FLWG MSG"; FormatTime = "";
					}
					if (Act.contains("Flifo_Forecast")) {
						Entry = "2F"+SptFgts[ff]+"/"+cDate+" | "+SptOrgDestn[ff].substring(0, 3)+" / WX FLIGHT DELAYED BY ETD "+UpdateTime;
						RetVal ="*"; RetVal1 = "RPLCD FLWG MSG";
					}
					//If any update after Delay
					if (Act.contains(";")) {
						RequestResponse(driver, queryObjects, Entry);
					}
					if (Entry.contains("OUT")) {
						RequestResponse(driver, queryObjects, "2P"+SptFgts[ff]+"/"+cDate+" OFF "+SptOrgDestn[ff].substring(0, 3)+" "+UpdateTime+" FLIFOCheck");
					}			 
					if (Act.contains("GateUpdate")) {
						Entry = "6:FM3"+"/"+cDate+SptOrgDestn[ff].substring(0, 3)+"@"+SptFgts[ff]+"GTD"+fOtrInput;
						RetVal ="GTD - "+fOtrInput;
					}
					if (Act.contains("GateReturn")) {
						Entry = "2P"+SptFgts[ff]+"/"+cDate+" RR "+SptOrgDestn[ff].substring(0, 3)+" "+UpdateTime;
						RetVal ="*"; RetVal1 ="RPLCD FLWG MSG";
					}
					String sResponse = RequestResponse(driver, queryObjects, Entry);
					if (sResponse.contains(RetVal) || sResponse.contains(RetVal1)) {
						if (FormatTime.contains("M")) {
							FormatTime = FormatTime.replace("M", "");
						}
						Flight = SptFgts[ff];
						Orig = SptOrgDestn[ff].substring(0,3);
						Destn = SptOrgDestn[ff].substring(3,6);
						fDate = cDate;
						cTime = ResSpt[1];
						fTime = FormatTime;
						fGate = fOtrInput;
						RetVal = SptFgts[ff]+"-"+SptOrgDestn[ff]+"-"+cDate+"-"+ResSpt[1]+"-"+FormatTime;
						break;
					}
				}
			}
			if (!RetVal.isEmpty()) {
				break;
			}
		}
		RequestResponse(driver, queryObjects, "BSO");//LOG OUT
		if (isNewTab) {
			Atoflow.Close_SwitchTab(driver);
		}
		return RetVal;
	}
	
//---------------------------------------------------------------------- Send Request & Response ----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public static String RequestResponse(WebDriver driver, BFrameworkQueryObjects queryObjects, String SharesEntry) throws IOException {
		String Res = "";
		driver.findElement(By.name("q")).sendKeys(SharesEntry);
		driver.findElement(By.xpath(pSendBtn)).click();
		Res = driver.findElement(By.xpath(pResField)).getText();
		return Res;
	}

//---------------------------------------------------------------------- FILL APIS FORM -------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void FillForm(WebDriver driver, BFrameworkQueryObjects queryObjects, String PaxName, String PaxType, String Country) throws Exception {
		String gForm = ""; String SplitFm[] = null;
		int randomInt = 0;
		if (PaxName.contains("*-IN")) {
			PaxName = PaxName.substring(0, PaxName.indexOf("*-IN"));
		}
		if (PaxType.contains("ADT")) {
			randomInt = ThreadLocalRandom.current().nextInt(30, 50);
		} else if (PaxType.contains("CHD")) {
			randomInt = ThreadLocalRandom.current().nextInt(7, 10);
		} else if (PaxType.contains("INS")) {
			randomInt = ThreadLocalRandom.current().nextInt(1, 2);
		}
		//gForm = driver.findElement(By.xpath(pResField)).getText();
		gForm = driver.findElement(By.xpath("//form/textarea")).getText();
		SplitFm = gForm.split("\\n");		
		for (int fm = 2; fm < SplitFm.length; fm++) {
			if (PaxName.contains("COUNTRY OF RESIDENCE")) {
				if (SplitFm[fm].contains("COUNTRY OF RESIDENCE")) {
					SetValue(driver, Country, fm, SplitFm[fm]);
				}
			} else if (SplitFm[fm].contains("STREET ")) {
				if (SplitFm[fm].contains("STREET ")) {
					SetValue(driver,"STREET11", fm, SplitFm[fm]);
				}				
			} else if (!gForm.contains("GIVEN NAMES")) {
				if (SplitFm[fm].contains("DOCUMENT TYPE")) {
					SetValue(driver, "P", fm, SplitFm[fm]);
				} else if (SplitFm[fm].contains("DOCUMENT NUMBER")) {
					SetValue(driver, RandomStringUtils.randomNumeric(8), fm, SplitFm[fm]);
				} else if (SplitFm[fm].contains("EXPIRATION DATE")) {
					SetValue(driver, Atoflow.AddDateStr(ThreadLocalRandom.current().nextInt(1, 5), "yyMMdd", "year", null), fm, SplitFm[fm]);
				}
			} else {
				if (SplitFm[fm].contains("GIVEN NAMES")) {
					SetValue(driver, PaxName.substring(PaxName.indexOf("/")+1, PaxName.length()), fm, SplitFm[fm]);
				} else if (SplitFm[fm].contains("LAST NAME")) {
					SetValue(driver, PaxName.substring(0, PaxName.indexOf("/")), fm, SplitFm[fm]);
				} else if (SplitFm[fm].contains("DATE OF BIRTH")) {
					SetValue(driver, Atoflow.AddDateStr(-randomInt, "yyMMdd", "year", null), fm, SplitFm[fm]);
				} else if (SplitFm[fm].contains("GENDER")) {
					SetValue(driver, "MALE", fm, SplitFm[fm]);
				} else if (SplitFm[fm].contains("NATIONALITY")) {
					SetValue(driver, Country, fm, SplitFm[fm]);
				} else if (SplitFm[fm].contains("DOCUMENT TYPE")) {
					SetValue(driver, "P", fm, SplitFm[fm]);
				} else if (SplitFm[fm].contains("DOCUMENT NUMBER")) {
					SetValue(driver, RandomStringUtils.randomNumeric(8), fm, SplitFm[fm]);
				} else if (SplitFm[fm].contains("COUNTRY OF ISSUANCE")) {
					SetValue(driver, Country, fm, SplitFm[fm]);
				} else if (SplitFm[fm].contains("EXPIRATION DATE")) {
					SetValue(driver, Atoflow.AddDateStr(ThreadLocalRandom.current().nextInt(1, 5), "yyMMdd", "year", null), fm, SplitFm[fm]);
				}
			}
		}
		driver.findElement(By.name("q")).sendKeys(Keys.PAGE_DOWN);
		driver.findElement(By.xpath(pSendBtn)).click();
	}

//---------------------------------------------------------------------- Set the value in the FORM ----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void SetValue(WebDriver driver, String Val, int LineNum, String LineText) throws IOException {
		WebElement TextBox = driver.findElement(By.name("q"));
		TextBox.sendKeys(Keys.PAGE_UP);
		for (int ln = 0; ln < LineNum; ln++) {
			TextBox.sendKeys(Keys.DOWN);
		}
		TextBox.sendKeys(Keys.HOME);
		if (Val.contains("-")) {
			Val = Val.replace("-", "");
		}
		for (int del = 0; del < Val.length(); del++) {
			TextBox.sendKeys(Keys.DELETE);
		}		
		TextBox.sendKeys(Val);
	}
	
//---------------------------------------------------------------------- Check the Availability in Flights ------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	public static boolean Availability_Sell(WebDriver driver, BFrameworkQueryObjects queryObjects, String pClass, String Entry, int Count) throws IOException {
		//A629/23DECLAXMIA
		boolean isClass = false; String Avail = "";
		String SptCls[] = null; String Resp = ""; String Sptline[] = null; String retClass = "";
		String MulCls[] = null; int rLoop = 0;
		String ChangeStat = ""; String Orig = ""; String gdte = "";
		Orig = Entry.substring(Entry.length()-6, Entry.length()-3);
		gdte = Entry.substring(Entry.indexOf("/")+1, Entry.length()-6).toUpperCase();
		if (!iMultiClass.isEmpty()) {
			MulCls = iMultiClass.split("-");
			rLoop = MulCls.length;
		} else {
			rLoop = 1;
		}
		Resp = RequestResponse(driver, queryObjects, Entry);
		if (Resp.toUpperCase().contains("CANCELLED")) {	
			
			ChangeStat = "2X"+Entry.substring(1, Entry.indexOf("/"))+"/"+gdte+"|"+Orig+"/LX REINSTATEFLIGHT";
			RequestResponse(driver, queryObjects, ChangeStat);
			Resp = RequestResponse(driver, queryObjects, Entry);
		}
		if (Count>0) {
			Random rand = new Random();
			// Generate random integers in range 0 to 150,300 
            int rand_int1 = rand.nextInt(100)+200;
            int rand_int2 = rand.nextInt(100)+100;
            //Clear the max before giving VML
            //VMS124/15APR.ALL/PTYLAXMAX
            RequestResponse(driver, queryObjects, "VMS"+Entry.substring(1, Entry.indexOf("/"))+"/"+gdte+".ALL/"+Entry.substring(Entry.length()-6, Entry.length())+"MAX");
            String incAvY = "VML"+Entry.substring(1, Entry.indexOf("/"))+"/"+gdte+"/"+Orig+"AUL/8."+rand_int1;
            String incAvC = "VML"+Entry.substring(1, Entry.indexOf("/"))+"/"+gdte+"/"+Orig+"AUL/1."+rand_int2;
            RequestResponse(driver, queryObjects, incAvY);
            RequestResponse(driver, queryObjects, incAvC);
            if (sValidation.equalsIgnoreCase("Zclass")) {
            	RequestResponse(driver, queryObjects, "VML"+Entry.substring(1, Entry.indexOf("/"))+"/"+gdte+"/"+Orig+"AUL/6.100");
			}
            if (sValidation.equalsIgnoreCase("Pclass")) {
            	RequestResponse(driver, queryObjects, "VML"+Entry.substring(1, Entry.indexOf("/"))+"/"+gdte+"/"+Orig+"AUL/2.100");
			}
		}
		if (!Resp.toUpperCase().contains("CANCELLED")&&!Resp.toUpperCase().contains("NO MORE")) {
			Sptline =Resp.split("\\n");
			for (int mcl = 0; mcl < rLoop; mcl++) {
				for (int sl = 0; sl < Sptline.length; sl++) {
					if (Sptline[sl].contains("ALTERNATE SERVICE")) {
						break;
					} else {
						SptCls = Sptline[sl].split(" ");				
						for (int sc = 2; sc < SptCls.length; sc++) {					
							if (SptCls[sc].matches("^[a-zA-Z]*$")) {
								if (pClass.isEmpty()) {
									pClass = SptCls[sc].trim();
								} else if (!iMultiClass.isEmpty()) {
									pClass = MulCls[mcl];
								}
								if (SptCls[sc].contains(pClass)) {
									if (!SptCls[(sc+1)].isEmpty() && SptCls[(sc+1)].length()>=3) {
										retClass = SptCls[(sc+1)].substring(0, 3);
									} else if (!SptCls[(sc+2)].isEmpty() && SptCls[(sc+2)].length()>=3) {
										retClass = SptCls[(sc+2)].substring(0, 3);
									}
									if (Count==0) {
										if (Integer.parseInt(retClass)== Count) {
											isClass = true;
										} else {
											Avail = "No";
										}
										break;
									} else {
										if (Integer.parseInt(retClass)>= Count) {
											isClass = true;
										} else {
											Avail = "No";
										}
										break;
									}
								}							
							}
						}//Class lines loop
						if (Avail.equalsIgnoreCase("No") || isClass) {
							break;
						}
					}
				}
				if (Avail.equalsIgnoreCase("No")) {
					break;
				}
				if (isClass && (mcl+1) == rLoop) {
					break;
				}
				isClass = false;
			}//Mutiple Class loop
		}		
		return isClass;
	}

//---------------------------------------------------------------------- EMAIL Validations ----------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void EmailValidation(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException {
		IshareLogin(driver, queryObjects, Shares);
		String PNRFROMCOMM="*"+Compensation.PnrForEmailValidation;
		driver.findElement(By.name("q")).sendKeys(PNRFROMCOMM);
		driver.findElement(By.xpath(pSendBtn)).click();		
		String output=driver.findElement(By.xpath(pResField)).getText();
		driver.findElement(By.name("q")).sendKeys("MD");
		driver.findElement(By.xpath(pSendBtn)).click();
		if(output.contains("AUTOMATION.COM") || driver.findElement(By.xpath(pResField)).getText().contains("AUTOMATION.COM")) {
			queryObjects.logStatus(driver, Status.PASS, "Checking email update ", "Email update successfully FOR PNR in Share"+Compensation.PnrForEmailValidation , null);	
		} else {
			queryObjects.logStatus(driver, Status.FAIL, "Checking email update ", "Email update successfully FOR PNR in share"+Compensation.PnrForEmailValidation , null);
		}
		if (isNewTab) {
			Atoflow.Close_SwitchTab(driver);
		}
	}
	
	public static boolean FlightBypass(WebDriver driver, BFrameworkQueryObjects queryObjects, String FlightNbr, String POS, String sDate) throws Exception {
		String Res = ""; boolean fStatus = false;
		IshareLogin(driver, queryObjects, Shares);
		OpenFlight Open = new OpenFlight(FlightNbr,POS, Atoflow.AddDateStr(0, "ddMMM", "day", null).toUpperCase(),driver,queryObjects);
		Open.run();
		RequestResponse(driver, queryObjects, "6-CR"+FlightNbr+"/"+sDate+POS);
		RequestResponse(driver, queryObjects, "6-C*"+FlightNbr+"/"+sDate+POS);
		Res = RequestResponse(driver, queryObjects, "6-CC"+FlightNbr+"/"+sDate+POS+".BYPASS");
		if(Res.contains("POST DEPARTURE BYPASSED")) {
			fStatus = true;
			queryObjects.logStatus(driver, Status.PASS, "Bypass the flight in Shares", "Flight is bypassed" , null);	
		} else {
			queryObjects.logStatus(driver, Status.WARNING, "Bypass the flight in Shares", "Flight is not bypassed, since the current timing is not close to the flight departure time" , null);
		}
		return fStatus;
	}

//---------------------------------------------------------------------- Open Flights and Clear Standby ---------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void ClearAllStandbyPax(WebDriver driver, BFrameworkQueryObjects queryObjects, String FlightNbr, String POS, String sDate) throws Exception {
		String Res = "";String Spt[] = null; String sLniata = "";
		IshareLogin(driver, queryObjects, "SharesB");
		try {
			Res = RequestResponse(driver, queryObjects, "W*");
			Spt = Res.split("\\n");
			sLniata = Spt[1].replace("TERM", "").trim();
			Res = RequestResponse(driver, queryObjects, "W"+sLniata+"C3"+POS+"CM");
			RequestResponse(driver, queryObjects, "BSIB");
			Res = RequestResponse(driver, queryObjects, "6-CO"+FlightNbr+"/"+sDate+POS+"."+sLniata);
			Res = RequestResponse(driver, queryObjects, "6-CK"+FlightNbr+"/"+sDate+POS);
			if(Res.contains("STANDBY BOARDING COMPLETE")) {
				queryObjects.logStatus(driver, Status.PASS, "Board the Standby Passengers", "STANDBY BOARDING is COMPLETED" , null);	
			} else {
				queryObjects.logStatus(driver, Status.INFO, "Board the Standby Passengers", "Unable to initiate boarding to standby passengers" , null);
			}
			if (driver.getCurrentUrl().contains(".svcs.entsvcs.net")) {
				Atoflow.Close_SwitchTab(driver);
			}
		} catch (Exception e) {}
		
	}

//---------------------------------------------------------------------- Delete Segments ----------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void DeleteSegments(WebDriver driver, BFrameworkQueryObjects queryObjects, String Orders) throws Exception {
		String Spt[] = null;
		IshareLogin(driver, queryObjects, "SharesB");
		try {
			if (Orders.contains(";")) {
				Spt = Orders.split(";");
				for (int del = 0; del < Spt.length; del++) {
					RequestResponse(driver, queryObjects, "*"+Spt[del]);
					RequestResponse(driver, queryObjects, "XI");
					RequestResponse(driver, queryObjects, "6PSGR");
					RequestResponse(driver, queryObjects, "ER");
					RequestResponse(driver, queryObjects, "ET");
					RequestResponse(driver, queryObjects, "I");
				}
			} else {
				RequestResponse(driver, queryObjects, "*"+Orders);
				RequestResponse(driver, queryObjects, "XI");
				RequestResponse(driver, queryObjects, "6PSGR");
				RequestResponse(driver, queryObjects, "ER");
				RequestResponse(driver, queryObjects, "ET");
				RequestResponse(driver, queryObjects, "I");
			}
			if (driver.getCurrentUrl().contains(".svcs.entsvcs.net")) {
				Atoflow.Close_SwitchTab(driver);
			}
		} catch (Exception e) {}
		
	}

//---------------------------------------------------------------------- ISHARE LOGIN ---------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void IshareLogin(WebDriver driver, BFrameworkQueryObjects queryObjects, String SharesA_B) throws IOException {
		try {
			UserID = envRead_IsharesCred(url_path.Instance,0);
			Password = envRead_IsharesCred(url_path.Instance,1);
			pResField = "//pre[@id='content-wrap']";
			pSendBtn = "//input[@value='Send']";
			try {
				SharesA_B = Shares;
			} catch (Exception e) {
				SharesA_B = "";
			}			
			WebDriverWait wait = new WebDriverWait(driver, 100);
			//Check whether Copa Web page is Opened
			if (driver.getCurrentUrl().contains("airservices.svcs.entsvcs.com")) {
				Atoflow.Open_SwitchTab(driver);
				isNewTab = true;
			}
			if(driver.findElements(By.xpath(pResField)).size()==0) {
				if (SharesA_B.contains("SharesA")) {
					driver.navigate().to("https://tpfsa.svcs.entsvcs.net");
				} else {
					driver.navigate().to("https://tpfsb.svcs.entsvcs.net");
				}			
				driver.findElement(By.xpath("//input[@name='ID']")).sendKeys(UserID);
				driver.findElement(By.name("Password")).sendKeys(Password);
				driver.findElement(By.xpath("//input[@value='Login']")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Terminal Emulation')]")));
				driver.findElement(By.xpath("//a[contains(text(),'Terminal Emulation')]")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
			}
			if (!SharesA_B.contains("SharesA")) {
				SharesA_B = "SharesB";
			}
			if (SharesA_B.contains("SharesA")) {
				driver.findElement(By.name("q")).sendKeys("logc uare");
				driver.findElement(By.xpath(pSendBtn)).click();
				driver.findElement(By.name("q")).sendKeys("bsia");
				driver.findElement(By.xpath(pSendBtn)).click();
				if( driver.findElement(By.xpath(pResField)).getText().contains("A-SINE COMPLETE")||driver.findElement(By.xpath(pResField)).getText().contains("A-IN USE")){	//indrajit
					queryObjects.logStatus(driver, Status.PASS, "Login to Ishares A application", "Login is successful", null);
				}else{
					queryObjects.logStatus(driver, Status.FAIL, "Login to Ishares A application", "Getting an error while login to ISHARES A applicatio", null);
				}
			} else {
				driver.findElement(By.name("q")).sendKeys("logc cmre");
				driver.findElement(By.xpath(pSendBtn)).click();
				driver.findElement(By.name("q")).sendKeys("bsia");
				driver.findElement(By.xpath(pSendBtn)).click();
				driver.findElement(By.name("q")).sendKeys("bsib");
				driver.findElement(By.xpath(pSendBtn)).click();
				if( driver.findElement(By.xpath(pResField)).getText().contains("B-SINE COMPLETE")||driver.findElement(By.xpath(pResField)).getText().contains("B-IN USE")){	//indrajit
					queryObjects.logStatus(driver, Status.PASS, "Login to Ishares B application", "Login is successful", null);
				}else{
					queryObjects.logStatus(driver, Status.FAIL, "Login to Ishares B application", "Getting an error while login to ISHARES B applicatio", null);
				}
			}		
			
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Login to IShares", "Login Failed", null);
		}
		
	}
	public static String envRead_IsharesCred(int row,int column) throws IOException{

        String FilePath = url_path.pEnvExcelPath;

        FileInputStream input=new FileInputStream(FilePath);
        XSSFWorkbook wb=new XSSFWorkbook(input);
        XSSFSheet sh=wb.getSheet("IsharesCRED");
        XSSFCell c=sh.getRow(row).getCell(column);
        DataFormatter format = new DataFormatter();

        input.close();
        return format.formatCellValue(c);

 	}
}

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
import org.apache.poi.xdgf.usermodel.section.geometry.RelMoveTo;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hamcrest.core.IsAnything;
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
public class Atoflow extends ATOflowPageObjects{
	public static String MultiPNR = null;
	public static String PaxName="";
	public static String cMonetaryAmt="";
	public static String cOverrideRsn="";
	public static String cCompensationRsn="";
	public static String cAddDetailsTab="";
	public static String cAddDetType="";
	public static String cAddDetValue="";
	public static String cDefaultAmt="";
	public static String BypassDestn = "";
	public static String Paxno= "";
	public static String BypassADC = "";
	public static int CompVol = 0;
	public static int CompInVol = 0;
	public static String cMeal_EMD ="";
	public static String cTransport_EMD ="";
	public static String cHotel_EMD="";

	boolean FLAG;
	boolean ChangeSaetFlag;
	boolean status=false;  ///checked in status
	public String name;
	public static String PnrNum ="";
	static boolean added_agent;
	
	String FLTNO="";
	public String selecteePaxName="";  ///suman selectee
	public static String sExitDays;
	public static String tktemd1=null;
	public static String sCheckinMsg = "";
	static String sKnownTraveller="";
	public static int openflightflag=0;
	public static String sInfantPax;	//cc
	public static String PNRRemarks;
	public static String sErr;
	public static String Control_Agent;
	public static String ControlAgent_Name="";
	
	public static List<Integer> Bag_wait = new ArrayList<>();
	public static List<String> Bag_value = new ArrayList<>();
	public static List<String> Bag_overSize = new ArrayList<>();
	public static List<String> Bag_Tag_Number = new ArrayList<>();
	public static String VolnList;
	public static boolean isSelected = false;
	//Search Passenger in Check  in page 
	@SuppressWarnings({ "deprecation", "static-access" })
	public void SearchPassenger(WebDriver driver, BFrameworkQueryObjects queryObjects)throws Exception{

		WebDriverWait wait = new WebDriverWait(driver, 50);
		String sGateCheckin="";
		ISharesflow.OpenFlight = false;
		Compensation.sOrderNum = "";
		try{

			//Compensation data
			String CompEmail = FlightSearch.getTrimTdata(queryObjects.getTestData("Valprintnemail"));
			cMonetaryAmt = FlightSearch.getTrimTdata(queryObjects.getTestData("MonetaryAmt"));
			cOverrideRsn = FlightSearch.getTrimTdata(queryObjects.getTestData("OverrideReason"));
			cCompensationRsn = FlightSearch.getTrimTdata(queryObjects.getTestData("CompensationReason"));
			cAddDetailsTab = FlightSearch.getTrimTdata(queryObjects.getTestData("AdditionalDetailsTab"));
			cAddDetType = FlightSearch.getTrimTdata(queryObjects.getTestData("AddDetailsType"));
			cAddDetValue = FlightSearch.getTrimTdata(queryObjects.getTestData("AddDetailsValue"));
			cDefaultAmt = FlightSearch.getTrimTdata(queryObjects.getTestData("ExistingAmt"));//
			Control_Agent = FlightSearch.getTrimTdata(queryObjects.getTestData("Control_Agent"));
			ControlAgent_Name = FlightSearch.getTrimTdata(queryObjects.getTestData("ControlAgent_Name"));
			BypassADC = FlightSearch.getTrimTdata(queryObjects.getTestData("ADC_Bypass"));
			cMeal_EMD = FlightSearch.getTrimTdata(queryObjects.getTestData("Meal_EMD"));
			cHotel_EMD = FlightSearch.getTrimTdata(queryObjects.getTestData("Hotel_EMD"));
			cTransport_EMD = FlightSearch.getTrimTdata(queryObjects.getTestData("Transport_EMD"));
			String cUpdateEMD = FlightSearch.getTrimTdata(queryObjects.getTestData("UpdateEMD"));
			String cIssue_NoComp = FlightSearch.getTrimTdata(queryObjects.getTestData("Issue_NoComp"));
			String cReaccDest = FlightSearch.getTrimTdata(queryObjects.getTestData("REaccDest"));
			String cReaccFlight = FlightSearch.getTrimTdata(queryObjects.getTestData("Reaccflghtnum"));
			String cReaccFrom = FlightSearch.getTrimTdata(queryObjects.getTestData("Reaccfrom"));
			String cReaccDays = FlightSearch.getTrimTdata(queryObjects.getTestData("ReaccDate"));

			String sDestn = "";
			String fDate = "";
			String sFlightOnly = FlightSearch.getTrimTdata(queryObjects.getTestData("Search_FlightOnly"));
			sGateCheckin = FlightSearch.getTrimTdata(queryObjects.getTestData("GateCheckin"));
			String FilterFgtList= FlightSearch.getTrimTdata(queryObjects.getTestData("FilterFlightList"));
																				  
			String Seat_Change=FlightSearch.getTrimTdata(queryObjects.getTestData("Seat_Change"));
			String sGName = FlightSearch.getTrimTdata(queryObjects.getTestData("GivenName"));
			String sSName = FlightSearch.getTrimTdata(queryObjects.getTestData("Sur_name"));
			String sFrom = FlightSearch.getTrimTdata(queryObjects.getTestData("From"));
			String sTo = FlightSearch.getTrimTdata(queryObjects.getTestData("To"));
			String sFlight = FlightSearch.getTrimTdata(queryObjects.getTestData("Flight"));
			String sDays = FlightSearch.getTrimTdata(queryObjects.getTestData("Days"));
			String sSearch = FlightSearch.getTrimTdata(queryObjects.getTestData("Search"));
			String sCheckin = FlightSearch.getTrimTdata(queryObjects.getTestData("ProceedtoCheckin"));
			String sOrderNum = FlightSearch.getTrimTdata(queryObjects.getTestData("OrderNumber"));
			String sTicket = FlightSearch.getTrimTdata(queryObjects.getTestData("Ticket"));
			String sFFProgram = FlightSearch.getTrimTdata(queryObjects.getTestData("FFProgram"));
			String sFFnum = FlightSearch.getTrimTdata(queryObjects.getTestData("FFNumber"));
			String sSubmit = FlightSearch.getTrimTdata(queryObjects.getTestData("Submit"));
			sErr = FlightSearch.getTrimTdata(queryObjects.getTestData("Expected_ErrMessage"));

			String SearchBagTag = FlightSearch.getTrimTdata(queryObjects.getTestData("SearchBagTag"));
			String AdditionalCheckIn = FlightSearch.getTrimTdata(queryObjects.getTestData("AdditionalCheckIn"));
			String ValFlight = FlightSearch.getTrimTdata(queryObjects.getTestData("ValFlight"));
			String ValDate = FlightSearch.getTrimTdata(queryObjects.getTestData("ValDate"));
			String TagNumber = FlightSearch.getTrimTdata(queryObjects.getTestData("TagNumber"));
			String  FqtvUpdate = FlightSearch.getTrimTdata(queryObjects.getTestData("FqtvUpdate"));
			String[] TagNumber1 = null;

			String sUpgrade = FlightSearch.getTrimTdata(queryObjects.getTestData("Upgrade"));
			String sAlter = FlightSearch.getTrimTdata(queryObjects.getTestData("AlternateFlight"));
			String sSpecialPass	= FlightSearch.getTrimTdata(queryObjects.getTestData("SpecialPax"));
			String sBlockorUnblock = FlightSearch.getTrimTdata(queryObjects.getTestData("Block_Unblock_Seats"));
			String Select_pax=FlightSearch.getTrimTdata(queryObjects.getTestData("Select_pax"));
			String Recononebyone = FlightSearch.getTrimTdata(queryObjects.getTestData("Reconcile"));
			String Reconcile = FlightSearch.getTrimTdata(queryObjects.getTestData("Reconcile"));
			String Restrict = FlightSearch.getTrimTdata(queryObjects.getTestData("RestrictFlight"));
			String AssignSeats=FlightSearch.getTrimTdata(queryObjects.getTestData("Assign_Seats"));
			String sUnReconcile = FlightSearch.getTrimTdata(queryObjects.getTestData("UnReconcile"));
			String soffload= FlightSearch.getTrimTdata(queryObjects.getTestData("Offload"));
			String sSSREmC= FlightSearch.getTrimTdata(queryObjects.getTestData("SSREmC"));
			String sSelSeatType= FlightSearch.getTrimTdata(queryObjects.getTestData("SeatType"));
			
			String sPax_List_screen_search_by_Pnr = FlightSearch.getTrimTdata(queryObjects.getTestData("sPax_List_screen_search_by_Pnr"));//NS
			String PaxNtList = FlightSearch.getTrimTdata(queryObjects.getTestData("Add_Pax_Note_list"));
			String Filter_Name= FlightSearch.getTrimTdata(queryObjects.getTestData("Filter_Name"));
			String Filter_Val= FlightSearch.getTrimTdata(queryObjects.getTestData("Filter_Val"));
			sCheckinMsg = FlightSearch.getTrimTdata(queryObjects.getTestData("Expected_CheckinMsg"));
			sExitDays =FlightSearch.getTrimTdata(queryObjects.getTestData("ExitDays"));
			String G0_shows=FlightSearch.getTrimTdata(queryObjects.getTestData("Go_show"));
			String Force_sale=FlightSearch.getTrimTdata(queryObjects.getTestData("Force_sale"));
			String Add_Passenger_Note=FlightSearch.getTrimTdata(queryObjects.getTestData("Add_Passenger_Note"));
			
			String sFlightCheckin=FlightSearch.getTrimTdata(queryObjects.getTestData("FlightfromCheckin"));
			String Delete_API= FlightSearch.getTrimTdata(queryObjects.getTestData("Delete_API"));
			String sReconcilePAX=FlightSearch.getTrimTdata(queryObjects.getTestData("ReconcilePAX"));
			Paxno=FlightSearch.getTrimTdata(queryObjects.getTestData("pax_nocheckin"));
			
			String SearchSeatNumber = FlightSearch.getTrimTdata(queryObjects.getTestData("SearchSeatNumber"));
			String SearchBySequence = FlightSearch.getTrimTdata(queryObjects.getTestData("SearchSequenceNumber"));
			String sJumpSeat = FlightSearch.getTrimTdata(queryObjects.getTestData("JumpSeat"));
			//String Standby_Fdj = FlightSearch.getTrimTdata(queryObjects.getTestData("Standby_FDj"));
			String sSearchBookingClass = FlightSearch.getTrimTdata(queryObjects.getTestData("SearchBookingClass"));
			
			String PurOfVisit = FlightSearch.getTrimTdata(queryObjects.getTestData("PurOfVisit"));	//cc

			sInfantPax = FlightSearch.getTrimTdata(queryObjects.getTestData("sInfantPax"));	//cc
			String Clear_API = FlightSearch.getTrimTdata(queryObjects.getTestData("Clear_API"));	//cc
			String BeforeADC = FlightSearch.getTrimTdata(queryObjects.getTestData("BeforeADC"));	//cc
			String Flight_Search_From = FlightSearch.getTrimTdata(queryObjects.getTestData("Flight_Search_From"));
			
			String PaxListOptn = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxListOption"));
			PNRRemarks = FlightSearch.getTrimTdata(queryObjects.getTestData("RemarksUpdate"));//Updated by Jenny - 15 Nov 2019
			String Icon_Validatn =  FlightSearch.getTrimTdata(queryObjects.getTestData("Icon_Validations"));
			
			//Baggage Validations
			String AddBaggagekg = FlightSearch.getTrimTdata(queryObjects.getTestData("AddBaggagekg"));
			String AllowBag = FlightSearch.getTrimTdata(queryObjects.getTestData("AllowBag"));		
			String MulBaggageWgt = FlightSearch.getTrimTdata(queryObjects.getTestData("MultiplebagWgt"));
			String MulBaggageType = FlightSearch.getTrimTdata(queryObjects.getTestData("MulBagType"));
			String BagType="";
			String BagForAllPax = FlightSearch.getTrimTdata(queryObjects.getTestData("BagForAllPax"));
			String BagForSpecificPax = FlightSearch.getTrimTdata(queryObjects.getTestData("BagForSpecificPax"));
			String Catalog = FlightSearch.getTrimTdata(queryObjects.getTestData("CatalogValue"));
			String BgAmtStatus = FlightSearch.getTrimTdata(queryObjects.getTestData("BagStatus"));
			String BaggageValue = FlightSearch.getTrimTdata(queryObjects.getTestData("BaggageValue"));
			
			String Verifications = FlightSearch.getTrimTdata(queryObjects.getTestData("Verifications"));
			String InvolReason = FlightSearch.getTrimTdata(queryObjects.getTestData("Invol_Reason"));
			String CbnCnt = FlightSearch.getTrimTdata(queryObjects.getTestData("Cabin_Cnt"));
			String OpnFlight = FlightSearch.getTrimTdata(queryObjects.getTestData("OpenFlight"));
			String BoardOpt = FlightSearch.getTrimTdata(queryObjects.getTestData("Boarding"));
			String DelBag = queryObjects.getTestData("DelBag_Checkin");
			String AddBag_AftDel = FlightSearch.getTrimTdata(queryObjects.getTestData("AddBag_AftDel"));
			String Category=queryObjects.getTestData("Bag_Category");
			String BagIndex =queryObjects.getTestData("delete_Bag_Index");
			String AddBag_Index =queryObjects.getTestData("New_Bag_Asber_Index");
			String TotStandby = FlightSearch.getTrimTdata(queryObjects.getTestData("TotalStandby"));
			String NotepaxType = FlightSearch.getTrimTdata(queryObjects.getTestData("NotepaxType"));
			String sFromleft = FlightSearch.getTrimTdata(queryObjects.getTestData("Fromleft"));
			String CloseFl = FlightSearch.getTrimTdata(queryObjects.getTestData("CloseFlight"));
			String VolSB = FlightSearch.getTrimTdata(queryObjects.getTestData("AddVolSB"));
			String VolSel_FrmTo = FlightSearch.getTrimTdata(queryObjects.getTestData("VolSel_FrmTo"));
			String OFFload_standby=FlightSearch.getTrimTdata(queryObjects.getTestData("Offload_standbyPAX"));
			String VolSBAct = FlightSearch.getTrimTdata(queryObjects.getTestData("VolSBAct"));
			String sicon_val_befcheckin = FlightSearch.getTrimTdata(queryObjects.getTestData("icon_val_befcheckin"));
			String ClickOnName = FlightSearch.getTrimTdata(queryObjects.getTestData("ClickOnName"));
			String AltFlight_num = FlightSearch.getTrimTdata(queryObjects.getTestData("AltFlight_num"));
			String SeatChngBfr = FlightSearch.getTrimTdata(queryObjects.getTestData("SeatCngBfrChkin"));
			String redirect_resgui = FlightSearch.getTrimTdata(queryObjects.getTestData("Redirect_ResGUI"));
			String sDatechange = FlightSearch.getTrimTdata(queryObjects.getTestData("Date_Change"));
			String sNthsegment=FlightSearch.getTrimTdata(queryObjects.getTestData("changeclass_OR_Date_Nth_Segment"));
			String reissue = FlightSearch.getTrimTdata(queryObjects.getTestData("reissue"));
			String NonTkt_PNR = FlightSearch.getTrimTdata(queryObjects.getTestData("NonTkt_PNR"));
			String NonTktPNR_Chkin = FlightSearch.getTrimTdata(queryObjects.getTestData("NonTktPNR_Chkin"));
			String WgtBalRestrict = FlightSearch.getTrimTdata(queryObjects.getTestData("WeightBalancerest"));
			String CancelStandby = FlightSearch.getTrimTdata(queryObjects.getTestData("CancelStandby"));
			String ClearStandby = FlightSearch.getTrimTdata(queryObjects.getTestData("ClearStandby"));
			String ClearStandby_Name = FlightSearch.getTrimTdata(queryObjects.getTestData("ClearStandby_Name"));
			//String AddBagAft = FlightSearch.getTrimTdata(queryObjects.getTestData("AddBagAft"));
			String GradeType = FlightSearch.getTrimTdata(queryObjects.getTestData("GradeType"));
			String PaxCnts = FlightSearch.getTrimTdata(queryObjects.getTestData("PassengerCnts"));
			String BoardStatus = FlightSearch.getTrimTdata(queryObjects.getTestData("BoardingStatus"));
			String MultiOrder = FlightSearch.getTrimTdata(queryObjects.getTestData("MultiOrder"));
			BypassDestn = FlightSearch.getTrimTdata(queryObjects.getTestData("BypassDestn"));
			String Updatefqtv = FlightSearch.getTrimTdata(queryObjects.getTestData("Updatefqtv"));
			String DocCheck = FlightSearch.getTrimTdata(queryObjects.getTestData("DocCheck"));
			String DocCheck_Bagadd = FlightSearch.getTrimTdata(queryObjects.getTestData("DocCheck_Bagadd"));
			String Release_ASA = FlightSearch.getTrimTdata(queryObjects.getTestData("Release_ASA"));
			String VoidBag = FlightSearch.getTrimTdata(queryObjects.getTestData("VoidBag"));
			String Returntocheckin = FlightSearch.getTrimTdata(queryObjects.getTestData("Returntocheckin"));
			String Deleteseat_Chkin = FlightSearch.getTrimTdata(queryObjects.getTestData("Deleteseat_Chkin"));
			String SyncTicket = FlightSearch.getTrimTdata(queryObjects.getTestData("SyncTicket"));
			String SyncTktPaxName = FlightSearch.getTrimTdata(queryObjects.getTestData("SyncTktPaxName"));
			String Synchronize=FlightSearch.getTrimTdata(queryObjects.getTestData("Synchronize"));
			String RequestType=FlightSearch.getTrimTdata(queryObjects.getTestData("RequestType"));
			String SyncTktVerify = FlightSearch.getTrimTdata(queryObjects.getTestData("SyncTktVerify"));
			String Negative_Case=FlightSearch.getTrimTdata(queryObjects.getTestData("Negative_Case"));
			String DoAction = FlightSearch.getTrimTdata(queryObjects.getTestData("DoAction"));
			String AddPassenger=FlightSearch.getTrimTdata(queryObjects.getTestData("AddPassenger"));
			String sSeatCheck = FlightSearch.getTrimTdata(queryObjects.getTestData("Seat_Validation"));			
			String checkinpaxtype = FlightSearch.getTrimTdata(queryObjects.getTestData("checkinpaxtype"));	
			String Release_Misconnect = FlightSearch.getTrimTdata(queryObjects.getTestData("Release_Misconnect"));
			String Release_Misconnect_flt = FlightSearch.getTrimTdata(queryObjects.getTestData("Release_Misconnect_flt"));
			String Class_of_Service = FlightSearch.getTrimTdata(queryObjects.getTestData("Class_of_Service"));
			String No_of_Standby = FlightSearch.getTrimTdata(queryObjects.getTestData("No_of_Standby"));
			String SwapSeat = FlightSearch.getTrimTdata(queryObjects.getTestData("SwapSeat"));
			String cabinCrew = FlightSearch.getTrimTdata(queryObjects.getTestData("CabinCrew"));
			String Add_MultiPassenger_Note = FlightSearch.getTrimTdata(queryObjects.getTestData("Add_MultiPassenger_Note"));
			String NoOfAdults = FlightSearch.getTrimTdata(queryObjects.getTestData("NoOfAdults"));
			String MulPaxNote = FlightSearch.getTrimTdata(queryObjects.getTestData("MulPaxNote"));
			
			boolean Orderselect = false;
			String Flightnumber=null;
			String[] Flightnumber1=null;
			String RetVal;
			// String sDestn = "";
			//String fDate = "";
			String Flight2 ="";
			String Seatnumber=null;
			String Sequencenumber=null;
			String FlightNum = null;
			String SptMsgs[] = null;
			//Passenger Counts
			String ACCapacity_C = ""; String ACCapacity_Y = "";
			String SeatsRemain_C = ""; String SeatsRemain_Y = "";
			int BkdCbn_C = 0; int BkdCbn_Y = 0; int HKNCnt_Y = 0;
			int HKNCnt_C = 0; int Reconcilepx = 0;
			
			//Get the Flight Number from Reservation tab
			if (sFlight.equalsIgnoreCase("Shareflight")) {
				SharePnrDetails(driver, queryObjects, ISharesflow.iPNRNo);
			}
			if (sFlightOnly.equalsIgnoreCase("SharesInbound")) {//meenu
				
				sFlight = ISharesflow.InboundFlight;
				sDestn = ISharesflow.Destn;
			}				
			//Get the Return Flight based on Order ID
			if (FilterFgtList.equalsIgnoreCase("InboundCompensate")) {
				try {
					String InboundPNR = ""; String tDate="";
					if (sFlightOnly.equalsIgnoreCase("SharesInbound")) {
						if (sOrderNum.equalsIgnoreCase("SHARES") || sOrderNum.equalsIgnoreCase("SMultiPNR")) {
							InboundPNR = ISharesflow.iPNRNo;
						}
						sFlight = ISharesflow.InboundFlight;
						sDestn = ISharesflow.Destn;
						tDate= ISharesflow.InboundDate;
						Flight2 = ISharesflow.Flight;
					} else if (sFlightOnly.equalsIgnoreCase("ResInbound")) {
						InboundPNR = Login.PNRNUM;
					} else if (!sOrderNum.isEmpty()) {
						InboundPNR = sOrderNum;
					}
					if (sFlightOnly.isEmpty()) {
						driver.findElement(By.xpath(ReservationXpath)).click();// clicking reservation menu bar
						Thread.sleep(2000);
						driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Reservations')]")).click();
						driver.findElement(By.xpath("//pssgui-search-panel[@label='pssgui.search.reservations']/div/md-input-container/input[@ng-model='searchPanel.searchText']")).sendKeys(InboundPNR);
						Thread.sleep(500);
						driver.findElement(By.xpath("//pssgui-search-panel[@label='pssgui.search.reservations']/div/i[@class='icon-search']")).click();
						FlightSearch.loadhandling(driver);
						Thread.sleep(500);
						//Click on order tab					
						driver.findElement(By.xpath("//div[@translate='pssgui.order']")).click();
						sFlightOnly = driver.findElement(By.xpath("(//td[@class='flight-name'])[2]")).getText().trim();
						sDestn = driver.findElement(By.xpath("(//div[@airport-code='originDestination.origin'])[2]")).getText().trim();
						tDate= driver.findElement(By.xpath("(//td[@class='date']//div/span)[2]")).getText().trim();
						Flight2 = driver.findElement(By.xpath("(//td[@class='flight-name'])[1]")).getText().trim();
						
						//AddDateStr(0, "MM/dd/yyyy", "day", new SimpleDateFormat("dd-MMM-yyyy").parse(tDate));
						Calendar cal = Calendar.getInstance();
						SimpleDateFormat sdf = new  SimpleDateFormat("dd-MMM-yyyy");
						//Date fDate = sdf.parse(tDate);
						cal.setTime(sdf.parse(tDate));					
						fDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
						Thread.sleep(500);	
						driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
						Thread.sleep(2000);
						driver.findElement(By.xpath(CheckInXpath)).click();
						FlightSearch.loadhandling(driver);

						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LabelPassengerXpath)));
						queryObjects.logStatus(driver, Status.PASS, "Search Inbound Flight", "Search Passenger displayed ",null);
					}					
				} catch (Exception e) {
					queryObjects.logStatus(driver, Status.INFO, "Search Inbound Flight", "Unable to  get the flight",null);
				}
				
			}
			///Get the Manual PNR
			if (sFlightOnly.equalsIgnoreCase("Shares")) {
			//if (sFlightOnly.equalsIgnoreCase("SharesFlight")) {
				sFlightOnly = ISharesflow.Flight;
				fDate = AddDateStr(0, "MM/dd/yyyy", "day", new SimpleDateFormat("ddMMMyyyy").parse(ISharesflow.cTime+AddDateStr(0, "yyyy", "day", null)));
			} else if (!sFlightOnly.isEmpty()&& !(FilterFgtList.equalsIgnoreCase("InboundCompensate"))) {
				try {
					driver.findElement(By.xpath("//div[@translate='pssgui.order']")).click();
					sDestn = driver.findElement(By.xpath("//div[@airport-code='originDestination.origin']")).getText().trim();
					fDate= driver.findElement(By.xpath("//td[@class='date']//div/span")).getText().trim();
					//AddDateStr(0, "MM/dd/yyyy", "day", new SimpleDateFormat("dd-MMM-yyyy").parse(fDate));
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
					//Date fDate = sdf.parse(tDate);
					cal.setTime(sdf.parse(fDate));					
					fDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
			Thread.sleep(2000);
			if(sGateCheckin.equalsIgnoreCase("Yes"))
			{					
				driver.findElement(By.xpath(gate)).click();
				FlightSearch.loadhandling(driver);
			}
			else
			{
				driver.findElement(By.xpath(CheckInXpath)).click();
				FlightSearch.loadhandling(driver);	
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LabelPassengerXpath)));
				queryObjects.logStatus(driver, Status.PASS, "Search Passenger", "Search Passenger displayed ",null);
			}
			//Updated by Jenny
			try {
				//Get the Flight Close to the time to Bypass
				if (sCheckinMsg.contains("FlightBypass")) {
					RetVal = OpenFlightSearch(driver, queryObjects, "GetFlight");
					String sBrd = driver.findElement(By.xpath("//div[contains(@class,'date-time ng-binding')]")).getText().trim();
					sBrd = sBrd.substring(0, 6).replace("-", "").toUpperCase();
					Open_SwitchTab(driver);
					sDestn = RetVal.substring(RetVal.indexOf("/")+1, RetVal.length());
					if (ISharesflow.FlightBypass(driver, queryObjects, RetVal.substring(2, RetVal.indexOf(";")), RetVal.substring(RetVal.indexOf(";")+1, RetVal.indexOf("/")), sBrd)) {
						queryObjects.logStatus(driver, Status.PASS, "Bypass a Flight in Shares", "Flight status is Bypassed",null);
						Close_SwitchTab(driver);
						driver.findElement(By.xpath(lRefresh)).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//input[contains(@ng-model,'searchdestination')]")).sendKeys(sDestn);						
						int FCnt = driver.findElements(By.xpath("(//tr[contains(@ng-repeat,'flightData')])")).size();
						for (int af = 1; af <= FCnt; af++) {
							if (driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'flightData')])["+af+"]/td[1]")).getText().trim().contains(RetVal.substring(2, RetVal.indexOf(";")-1))) {
								if (driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'flightData')])["+af+"]/td[5]")).getText().trim().contains("DEPARTED-BYPASS")) {
									queryObjects.logStatus(driver, Status.PASS, "Check the Bypassed Flight in Check in landing page", "Flight status is DEPARTED-BYPASS",null);
									break;
								} else {
									queryObjects.logStatus(driver, Status.WARNING, "Check the Bypassed Flight in Check in landing page", "Flight status is not bypassed since the current time is not close to teh flight departure time",null);
								}
							}
						}//Loop ends 
					} else {
						queryObjects.logStatus(driver, Status.WARNING, "Bypass a Flight in Shares", "Flight is not bypassed since the current time is not close to the flight departure time",null);
					}
					return;
				}
				
				if (sCheckinMsg.contains("FlightCancel")) {
					driver.findElement(By.xpath(lRefresh)).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//input[contains(@ng-model,'searchdestination')]")).sendKeys(ISharesflow.Destn);
					FlightSearch.loadhandling(driver);
					int FCnt = driver.findElements(By.xpath("(//tr[contains(@ng-repeat,'flightData')])")).size();
					for (int af = 1; af <= FCnt; af++) {
						if (driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'flightData')])["+af+"]/td[1]")).getText().trim().contains(ISharesflow.Flight)) {
							if (driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'flightData')])["+af+"]/td[5]")).getText().trim().contains("CANCELLED")) {
								queryObjects.logStatus(driver, Status.PASS, "Check the Cancelled Flight in Check in landing page", "Flight status is CANCELLED",null);
								Open_SwitchTab(driver);
								ISharesflow.FLIFO(driver, queryObjects, "Reinstate", sFlight+";", sFrom+sTo+";", ISharesflow.fDate, "", "");
								Close_SwitchTab(driver);
								break;
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Check the Cancelled Flight in Check in landing page", "Flight status is not updated, CANCELLED",null);
							}
						}
					}
					return;
				}
				
				if (sCheckinMsg.contains("FlightUpdate")) {
					sFlight = "";
					sFrom = "";
					sTo = "";
					fDate = "";
					String SptVal[] = ValFlight.split(";");
					String SptPage[] = Verifications.split(";");
					for (int sp = 0; sp < SptPage.length; sp++) {
						if (sCheckinMsg.contains("CXL_Reinstate")) {
							if (sp == 1) {
								Open_SwitchTab(driver);
								ISharesflow.FLIFO(driver, queryObjects, "FXCancel", sFlight+";", sFrom+sTo+";", ISharesflow.fDate, "", "");
								Close_SwitchTab(driver);
							} else if (sp == 2) {
								Open_SwitchTab(driver);
								ISharesflow.FLIFO(driver, queryObjects, "Reinstate", sFlight+";", sFrom+sTo+";", ISharesflow.fDate, "", "");
								Close_SwitchTab(driver);
							}
						}
						sFlight = ISharesflow.Flight;
						sFrom = ISharesflow.Orig;
						sTo = ISharesflow.Destn;
						fDate = ISharesflow.fDate;
						String gTime = ISharesflow.fTime;
						String iGate = ISharesflow.fGate;
						if (gTime.isEmpty()) {
							gTime = ISharesflow.cTime;
						}
						gTime = AddDateStr(0, "HH:mm", "minute", new SimpleDateFormat("hhmmaa").parse(gTime+"M"));
						fDate = AddDateStr(0, "MM/dd/yyyy", "day", new SimpleDateFormat("ddMMMyyyy").parse(fDate+ AddDateStr(0, "yyyy", "day", null))).toUpperCase();
						if (SptPage[sp].contains("FLIFO")) {
							FLIFOValidations(driver, queryObjects, sFlight, sFrom, sTo, fDate, AddDateStr(0, "HH:mm", "minute", new SimpleDateFormat("hhmmaa").parse(ISharesflow.cTime+"M")), gTime, SptVal[sp], iGate);
						} else if (SptPage[sp].contains("FlightSearch-CheckHeader")) {
							FlightSearch_CheckinLanding(driver, fDate, sFrom, sFlight);
							FlightSearch.loadhandling(driver);
							FlightHeaderStatus(driver, queryObjects, fDate, sFrom, sFlight, iGate, SptVal[sp], AddDateStr(0, "HH:mm", "minute", new SimpleDateFormat("hhmmaa").parse(ISharesflow.cTime+"M")), gTime);
						} else if (SptPage[sp].contains("GateLanding")) {
							if (Verifications.contains("FlightHeader")) {
								SptVal[sp] = SptVal[sp]+"Select";
							}
							GateLanding_Search(driver, queryObjects, fDate, sTo, "", sFlight, iGate, AddDateStr(0, "HH:mm", "minute", new SimpleDateFormat("hhmmaa").parse(ISharesflow.cTime+"M")), gTime, SptVal[sp], "Search");
						} else if (SptPage[sp].contains("FlightHeader")) {
							FlightSearch.loadhandling(driver);
							FlightHeaderStatus(driver, queryObjects, fDate, sFrom, sFlight, iGate, SptVal[sp], AddDateStr(0, "HH:mm", "minute", new SimpleDateFormat("hhmmaa").parse(ISharesflow.cTime+"M")),gTime);
						} else if (SptPage[sp].contains("NativeShares")) {
							NativeShares(driver, queryObjects, "2"+sFlight, SptVal[sp]);
						}
					}
					if (sCheckinMsg.equalsIgnoreCase("FlightUpdate_CXL")) {
						ISharesflow.FLIFO(driver, queryObjects, "Reinstate", sFlight+";", sFrom+sTo+";", ISharesflow.fDate, "", "");
					}					
					return;
				}
				if (sCheckinMsg.contains("CompensationNavigation")) {
					driver.findElement(By.xpath(ReservationXpath)).click();
					driver.findElement(By.xpath("//button[contains(text(),'Travel Compensation')]")).click();//Select Travel Compensation Menu
					FlightSearch.loadhandling(driver);
					if(driver.findElements(By.xpath("//div[@ng-repeat='tab in tabsCtrl.model.tabs']/div[contains(text(),'Search')]")).size()>0 && driver.findElements(By.xpath("//div[@ng-repeat='tab in tabsCtrl.model.tabs']/div[contains(text(),'Report')]")).size()>0) {
						queryObjects.logStatus(driver, Status.PASS, "Select the Travel Compensation dropdown from Airport page", "Travel Compenesation Search-Reports page is displayed",null); }
					else { queryObjects.logStatus(driver, Status.FAIL, "Select the Travel Compensation dropdown from Airport page", "Travel Compenesation Search-Reports page is not displayed",null); }
					return;
				}				
			} catch (Exception e) {
				// TODO: handle exception
			}
			//passing a only flight number from checkin page
			if(!(sFlightCheckin.isEmpty()))  ///suman 11 june 2108
			{  
				if(sFlightCheckin.equalsIgnoreCase("Res")) {
					sFlightCheckin=Login.FLIGHTNUM;
				}
				if(sFlightCheckin.equalsIgnoreCase("Shares")) {
					sFlightCheckin=ISharesflow.Flight;
				}
				//String sFlightCheckin=FlightSearch.getTrimTdata(queryObjects.getTestData("FlightfromCheckin"));
				//updated navira 4-Nov
				driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(sFlightCheckin);
				driver.findElement(By.xpath("//div[1]/div/form/div[1]/div[2]//label[text()='From']/following-sibling::input")).clear();
				driver.findElement(By.xpath("//div[1]/div/form/div[1]/div[2]//label[text()='From']/following-sibling::input")).sendKeys(sFrom);
				driver.findElement(By.xpath("//div[1]/div/form/div[1]/div[2]//label[text()='From']/following-sibling::input")).sendKeys(Keys.ENTER);
				FlightSearch.loadhandling(driver);
				FLAG=true;
			}

			if(sFlight.equalsIgnoreCase("APP")) {
				sFlight = OpenFlightSearch(driver,queryObjects,"");
				//driver.findElement(By.xpath(FlightXpath)).sendKeys(sFlight);
			}
			
//------------------Clear Data -----------------------------------Get Flight-----------------------PNR--------------------------------------------------------------------------
			if (sCheckinMsg.equalsIgnoreCase("ClearData")) {
				if (sFlight.equalsIgnoreCase("Shares")) {
					if (ISharesflow.Flight.isEmpty()) {
						sFlight = Login.FLIGHTNUM;
						sOrderNum = Login.PNRNUM;						
					} else {
						if (sOrderNum.isEmpty()) {
							sOrderNum = ISharesflow.iPNRNo;
						}
					}
				}
				if (sFlight.isEmpty()) {
					try {
						sFlight = driver.findElement(By.xpath("//div[contains(@ng-class,'flightInfo.model.showFlightInfo')]//div[contains(@class,'flight-details')]/div[1]")).getText().trim();
						if (sFlight.toUpperCase().contains("CM")) {
							sFlight = sFlight.replace("CM", "");
						}
					} catch (Exception e) {}
				}
			}
//------------------Clear Data -----------------------------------Get Flight-----------------------PNR-------------END---------------------------------------------------------
			
			if(sFlight.equalsIgnoreCase("Shareflight") || sFlight.equalsIgnoreCase("Shares")) {
				if(sFlight.equalsIgnoreCase("Shares")) {
					sFlight = ISharesflow.Flight;
					ValDate = AddDateStr(0, "MM/dd/yyyy", "day", new SimpleDateFormat("ddMMMyyyy").parse(ISharesflow.cTime+AddDateStr(0, "yyyy", "day", null)));
					sFrom = ISharesflow.Orig;
					sFlight = ISharesflow.Flight;
					//driver.findElement(By.xpath(FlightXpath)).sendKeys(sFlight);
				}//// enter the store pnr in env var which from share pnr  ///suman 13 june 2018
				if (Login.shareflightnm.isEmpty()) {
					Login.shareflightnm = ISharesflow.Flight;
				}
				sFlight= Login.shareflightnm;
				driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(sFlight);
				
				FLAG=true;
				
				Calendar cal1 = Calendar.getInstance();    ///suman selectee
				SimpleDateFormat sdf = new  SimpleDateFormat("MM/dd/yyyy");
				if (!sDays.equalsIgnoreCase("SharesDte")) {
					if(!sDays.equalsIgnoreCase("")) 
						cal1.add(Calendar.DATE, Integer.parseInt(sDays));	
					else
						cal1.add(Calendar.DATE, 1);
				}
				
				String newDate = sdf.format(cal1.getTime());

				driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).clear();
				driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).click();
				if (!ValDate.isEmpty()) {
					driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).sendKeys(ValDate);
				} else {
					driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).sendKeys(newDate);
				}
				if(!sFrom.isEmpty()){
					driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).clear();
					driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).click();
					driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).sendKeys(sFrom);
					//driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).sendKeys(Keys.TAB);
					//Tab Alternate Solution
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
			}
			else if(sFlight.equalsIgnoreCase("Res")||sFlight.equalsIgnoreCase("RetFlight")) {
				if(sFlight.equalsIgnoreCase("Res"))
				sFlight= Login.FLIGHTNUM;
				if (sFlight.equalsIgnoreCase("RetFlight"))
					sFlight= Login.RETFLIGHTNUM;
				driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(sFlight);
				FLAG=true;
				//  Get as per application date ********************
				String gDate_app= driver.findElement(By.xpath("//pssgui-date-time[@action='display']//div")).getText().trim();
				gDate_app=gDate_app.split(" ")[0];
				Calendar cal = Calendar.getInstance();

				SimpleDateFormat sdf = new  SimpleDateFormat("MM/dd/yyyy");  // set this date formate
				SimpleDateFormat sdf2 = new  SimpleDateFormat("dd-MMM-yyyy");   // get from app 
				
				cal.setTime(sdf2.parse(gDate_app));  
				if (!sDays.equalsIgnoreCase("SharesDte")) {
					if(!sDays.equalsIgnoreCase("")) 
						cal.add(Calendar.DATE, Integer.parseInt(sDays));	
					else
						cal.add(Calendar.DATE, 1);
				}
				//

				String newDate = sdf.format(cal.getTime());
				driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).clear();
				driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).click();
				driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).sendKeys(newDate);
				
				if(!Flight_Search_From.isEmpty()){
					driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).clear();
					driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).click();
					driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).sendKeys(Flight_Search_From);
					//driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).sendKeys(Keys.TAB);
					//Tab Alternate Solution
					try {
						driver.findElement(By.xpath(Login.SelList)).click();
					} catch (Exception e1) {
						try {
							driver.findElement(By.xpath(Login.SelList2)).click();
						} catch (Exception e) {
							try {
								driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='"+Flight_Search_From+"']/ancestor::span[2]/following-sibling::span/span/../../..")).click();
								}catch(Exception e3) {
									driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).sendKeys(Keys.TAB);
								}
						}
					}
				}
				//driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::div[@ng-if='flightSearch.display.gateSearchBtn']/button")).click();	 
			}

			else if(sFlight.equalsIgnoreCase("OpenFlight"))
			{
				if(sTo != "")
				{
					driver.findElement(By.xpath("//label[contains(text(),'Destination Code')]/following-sibling::input")).sendKeys(sTo);
					sFlight= OpenFlightSearch(driver,queryObjects,"");
					Login.FLIGHTNUM=sFlight;
					FlightSearch.loadhandling(driver);
					FLAG=true;
					driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(sFlight);
				}
				else
				{
					sFlight= OpenFlightSearch(driver,queryObjects,"");
					Login.FLIGHTNUM=sFlight;

					FLAG=true;
					driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(sFlight);	 
				}
			}
			else if(sFlight.equalsIgnoreCase("OutSync"))
			{
				FLAG=true;
				sFlight=GetOutofSync(driver,queryObjects);
				driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(sFlight);
				sFlight="OutSync";

			}
			else if (sFlight != "" && sGateCheckin.equalsIgnoreCase("YES")){
				String ckDate="";
				driver.findElement(By.xpath("//input[contains(@ng-required,'flightSearch.isRequired')]")).clear();
				driver.findElement(By.xpath("//input[contains(@ng-required,'flightSearch.isRequired')]")).sendKeys(sFlight);
				Thread.sleep(1000);
				driver.findElement(By.xpath("//input[contains(@class,'md-datepicker-input')]")).clear();
				if (sFlightOnly.equalsIgnoreCase("SharesInbound")) {
					ckDate = AddDateStr(0, "MM/dd/yyyy", "day", new SimpleDateFormat("ddMMMyyyy").parse(ISharesflow.InboundDate+AddDateStr(0, "yyyy", "day", null)));
					driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).clear();
					driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).click();
					driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).sendKeys(sDestn);
					//Tab Alternate Solution
					try {
						driver.findElement(By.xpath(Login.SelList)).click();
					} catch (Exception e1) {
						try {
							driver.findElement(By.xpath(Login.SelList2)).click();
						} catch (Exception e) {
							try {
								driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='"+sDestn+"']/ancestor::span[2]/following-sibling::span/span/../../..")).click();
								}catch(Exception e3) {
									driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).sendKeys(Keys.TAB);
								}
						}
					}
				}
				if (ckDate.isEmpty()) {
					Calendar cal1 = Calendar.getInstance();    ///suman selectee
					SimpleDateFormat sdf = new  SimpleDateFormat("MM/dd/yyyy");
					if (!sDays.equalsIgnoreCase("SharesDte")) {
						if(!sDays.equalsIgnoreCase("")) {
							cal1.add(Calendar.DATE, Integer.parseInt(sDays));	
							 ckDate = sdf.format(cal1.getTime());
						}
						else					
						{				
						 Calendar cal = Calendar.getInstance();
							
							java.util.Date newdate = cal.getTime();
							 ckDate = new SimpleDateFormat("MM/dd/yyyy").format(newdate);
							
						}
					}
					String strnewdate= ckDate.toString();	
						 driver.findElement(By.xpath("//input[contains(@class,'md-datepicker-input')]")).sendKeys(strnewdate);
				} else {
					driver.findElement(By.xpath("//input[contains(@class,'md-datepicker-input')]")).sendKeys(ckDate);
				}
				
					 FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//button[contains(text(),'Search')]")).click();
				FlightSearch.loadhandling(driver);
				
			}
			/* else if (sFlight != ""){
			 driver.findElement(By.xpath(FlightXpath)).sendKeys(sFlight); 
		 }*/

			if (!sFlightOnly.isEmpty() && !(sCheckinMsg.equalsIgnoreCase("Search by seq"))) {

				if(sFlightOnly.equalsIgnoreCase("Res")) {
					if(!FilterFgtList.equalsIgnoreCase("Misconnected Pax"))
						sFlightOnly= Login.FLIGHTNUM;  
					else{
						sFlightOnly=Login.RETFLIGHTNUM;
						sDestn= Login.RETSEGMENT;
					}
				}
				try {
					if ( driver.findElement(By.xpath("//md-content[@on='airportPassenger.model.selectedStatus.value']")).isDisplayed()) {
						
					}
				} catch (Exception e) {
					if (!sFlightOnly.isEmpty() && !(sFlightOnly.equalsIgnoreCase("Res")) && !(sFlightOnly.toLowerCase().contains("shares"))) {
						sFlightCheckin = sFlightOnly;
					}

					FlightSearch_CheckinLanding(driver, fDate, sDestn, sFlightCheckin);
				}				
				if ( driver.findElement(By.xpath("//md-content[@on='airportPassenger.model.selectedStatus.value']")).isDisplayed()) {					 


					if ((FilterFgtList!="") && (FilterFgtList.equalsIgnoreCase("Thru Pax"))) {
						driver.findElement(By.xpath("//div[contains(@class,'ng-isolate-scope segment-2')]")).click();
						FlightSearch.loadhandling(driver);


						//Set Filter Method
						SetPassengerListOption(driver,queryObjects,FilterFgtList);

						List<WebElement> Checkbox_Cnt = driver.findElements(By.xpath(PaxDataChkBox));
						if (Checkbox_Cnt.size()>=1) {
							queryObjects.logStatus(driver, Status.PASS, "Display Thru Passenger List ", "Thru Passenger List is displayed for the selected flight ", null);		
						} else {
							queryObjects.logStatus(driver, Status.FAIL, "Display Thru Passenger List ", "Thru Passenger List is not displayed ", null);
						}						

					} else if ((FilterFgtList!="") && (FilterFgtList.equalsIgnoreCase("Reaccommodated Pax"))) {
						driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).clear();
						for (int i = 0; i < FilterFgtList.length(); i++){

							char c = FilterFgtList.charAt(i);
							String s = new StringBuilder().append(c).toString();
							driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).sendKeys(s);
						}
						driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).sendKeys(Keys.ENTER);
						FlightSearch.loadhandling(driver);
						
						if(driver.findElements(By.xpath("//md-checkbox[@aria-label='pax-chk']")).size()>1) {
							queryObjects.logStatus(driver, Status.PASS, "Display Reaccommodated Pax List ", "Reaccommodated Pax List is displayed for the selected flight ", null);
						}
						else if(driver.findElements(By.xpath("//div[contains(text(),'No Passengers Found')]")).size()>0) {
							queryObjects.logStatus(driver, Status.FAIL, "No Reaccommodated Pax List ", "Reaccommodated Pax List is not displayed for the selected flight ", null);
						}
						else
						{
							queryObjects.logStatus(driver, Status.FAIL, "No Reaccommodated Pax List ", "Reaccommodated Pax List is not present for the selected flight ", null);
						}
						
						if(sCheckinMsg.equalsIgnoreCase("SeatChangeList")) {
							driver.findElement(By.xpath("//md-select[contains(@aria-label,'Reaccommodated Pax')]")).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath("//div[contains(text(),'Seat Changed Pax by Reaccommodation')]/ancestor::md-option[contains(@value,'reacseat')]")).click();
							FlightSearch.loadhandling(driver);
							if(driver.findElements(By.xpath("//md-checkbox[@aria-label='pax-chk']")).size()>1) {
								queryObjects.logStatus(driver, Status.PASS, "Display Seat Changed Pax by Reaccommodated List", "Seat Changed Pax by Reaccommodated List is displayed for the selected flight ", null);
							}
							else if(driver.findElements(By.xpath("//div[contains(text(),'No Passengers Found')]")).size()>0) {
								queryObjects.logStatus(driver, Status.FAIL, "Display Seat Changed Pax by Reaccommodated List", "Seat Changed Pax by Reaccommodated List is not displayed for the selected flight ", null);
							}
							else
							{
								queryObjects.logStatus(driver, Status.FAIL, "Display Seat Changed Pax by Reaccommodated List", "Seat Changed Pax by Reaccommodated List is not present for the selected flight ", null);
							}
						}
						
						return;
						
					}
					else if ((FilterFgtList!="") && (FilterFgtList.equalsIgnoreCase("Seat Changed Pax by Reaccommodation"))) {
						
						driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).clear();
						for (int i = 0; i < FilterFgtList.length(); i++){

							char c = FilterFgtList.charAt(i);
							String s = new StringBuilder().append(c).toString();
							driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).sendKeys(s);
						}
						driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).sendKeys(Keys.ENTER);
						FlightSearch.loadhandling(driver);
						if(driver.findElements(By.xpath("//md-checkbox[@aria-label='pax-chk']")).size()>0) {
							queryObjects.logStatus(driver, Status.PASS, "Display Seat Changed Pax by Reaccommodated Pax List ", "Reaccommodated Pax List is displayed for the selected flight ", null);
						}
						else if(driver.findElements(By.xpath("//div[contains(text(),'No Passengers Found')]")).size()>0) {
							queryObjects.logStatus(driver, Status.WARNING, "No Seat Changed Pax by Reaccommodated Pax List ", "Reaccommodated Pax List is not displayed for the selected flight ", null);
						}
						else
						{
							queryObjects.logStatus(driver, Status.FAIL, "No Seat Changed Pax by Reaccommodated Pax List ", "Reaccommodated Pax List is not present for the selected flight ", null);
						}
						return;
					}
					else if ((FilterFgtList!="") && (FilterFgtList.equalsIgnoreCase("Misconnected Pax"))) {
						driver.findElement(By.xpath(InboundTab)).click();
						FlightSearch.loadhandling(driver);
						List<WebElement> FlightList = driver.findElements(By.xpath(InboundList));
						if (FlightList.size()>=1) {
							for (int FlightCnt = 1; FlightCnt <= FlightList.size(); FlightCnt++) {
								String SplitFgtData[] = (driver.findElement(By.xpath("("+InboundList+")["+FlightCnt+"]")).getText()).split("\\n");
								if (SplitFgtData[1].equalsIgnoreCase("PTY")) {
									driver.findElement(By.xpath("("+InboundList+")["+FlightCnt+"]")).click();
									FlightSearch.loadhandling(driver);
									sOrderNum=SelectFromPassengerList(driver, queryObjects,"",Filter_Name, Filter_Val);
									driver.findElement(By.xpath(MisconnectXpath)).click();
									FlightSearch.loadhandling(driver);
									boolean FligtOpenMsg= false;
									try{
										FligtOpenMsg=driver.findElement(By.xpath("//span[contains(text(),'SHIP NOT ASSIGNED')]")).isDisplayed();
										//
									}
									catch(Exception e){}
									if(FligtOpenMsg && openflightflag==0)
									{
										OpenFlights_Checkin(driver, queryObjects);
									}
									SetPassengerListOption(driver,queryObjects,FilterFgtList);
									SelectFromPassengerList(driver, queryObjects,sOrderNum,Filter_Name, Filter_Val);
									Orderselect=true;
									break;
								}

							}

						}
					} else if ((FilterFgtList!="") && (FilterFgtList.equalsIgnoreCase("InboundCompensate"))) {
						driver.findElement(By.xpath(InboundTab)).click();
						FlightSearch.loadhandling(driver);
						List<WebElement> FlightList = driver.findElements(By.xpath(InboundList));
						if (FlightList.size()>=1) {
							for (int FlightCnt = 1; FlightCnt <= FlightList.size(); FlightCnt++) {
								String SplitFgtData[] = (driver.findElement(By.xpath("("+InboundList+")["+FlightCnt+"]")).getText()).split("\\n");
								if (SplitFgtData[0].toUpperCase().contains("CM")) {
									SplitFgtData[0] = SplitFgtData[0].replace("CM", "");
								}
								if (SplitFgtData[0].equalsIgnoreCase(Flight2)) {
									driver.findElement(By.xpath("("+InboundList+")["+FlightCnt+"]")).click();
									FlightSearch.loadhandling(driver);
									if (sOrderNum.equalsIgnoreCase("SMultiPNR")) {
										PnrNum=sOrderNum;
									}
									Orderselect=true;											
									break;
								}

							}
							if (Orderselect) {
								//driver.findElement(By.xpath("//md-checkbox[@ng-model='airportPanel.model.checkAll ']")).click();
								driver.findElement(By.xpath(MisconnectXpath)).click();
								FlightSearch.loadhandling(driver);
								List<WebElement> Checkbox_Cnt = driver.findElements(By.xpath(PaxDataChkBox));
								int TotalPAX = Checkbox_Cnt.size();
								if (sOrderNum.equalsIgnoreCase("SMultiPNR")) {
									sOrderNum = Atoflow.PnrNum= ISharesflow.MultiPNR;
									for (int k = 1; k <= TotalPAX; k++) {
										if (sOrderNum.contains((driver.findElement(By.xpath("(//div[contains(@ng-repeat,'passengerItinerary.model.displayPaxModel')])["+k+"]//span[contains(@security-validate,'ViewOrder')]")).getText().trim()))) {
											driver.findElement(By.xpath("(//md-checkbox[@ng-model='passengerData.chkBoxSelected'])["+k+"]")).click();
										}
									}
								} else {
									for (int k = 1; k <= TotalPAX; k++) {
										if (((driver.findElement(By.xpath("(//div[contains(@ng-repeat,'passengerItinerary.model.displayPaxModel')])["+k+"]")).getText().trim())).contains(sOrderNum)) {
											driver.findElement(By.xpath("(//md-checkbox[@ng-model='passengerData.chkBoxSelected'])["+k+"]")).click();
										}
									}
								}
								driver.findElement(By.xpath(Compensate)).click();
								FlightSearch.loadhandling(driver);								 
								Compensation.UpdateCompensationReason(driver, queryObjects, " ", "", cCompensationRsn);
								Compensation.IssueCompensation_Verify(driver, queryObjects, " ", MultiOrder,"Y",sFlight,sDestn,sDays, CompEmail,cDefaultAmt, cMonetaryAmt, cOverrideRsn, cMeal_EMD, cTransport_EMD, cHotel_EMD, cUpdateEMD, "",
										cReaccFlight, cReaccFrom, cReaccDest, cReaccDays, "", "", cAddDetailsTab, cAddDetType, cAddDetValue);
								if (!sCheckinMsg.equalsIgnoreCase("InboundOversold")) {
									return;
								}
								driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
								Thread.sleep(2000);
								driver.findElement(By.xpath(CheckInXpath)).click();
								FlightSearch_CheckinLanding(driver, fDate, sDestn, sFlightOnly);
								SetPassengerListOption(driver,queryObjects,"Misconnected Pax");

								for (int k = 1; k <= TotalPAX; k++) {
									if (((driver.findElement(By.xpath("(//div[contains(@ng-repeat,'passengerItinerary.model.displayPaxModel')])["+k+"]")).getText().trim())).contains(sOrderNum)) {
										driver.findElement(By.xpath("(//md-checkbox[@ng-model='passengerData.chkBoxSelected'])["+k+"]")).click();
									}
								}
								// driver.findElement(By.xpath("//md-checkbox[@ng-model='airportPanel.model.checkAll ']")).click();
								//SelectFromPassengerList(driver, queryObjects,sOrderNum);										
							}		
						}						


					} else if (sCheckinMsg.equalsIgnoreCase("Upgrade Downgrade")) {
						sOrderNum = SelectFromPassengerList(driver,queryObjects,sOrderNum,Filter_Name, Filter_Val);//Updated By Jenny
						Orderselect=true;
					}else if ((FilterFgtList!="") && (FilterFgtList.equalsIgnoreCase("OversoldCompensate"))) {
						driver.findElement(By.xpath(OversoldTab)).click();
						FlightSearch.loadhandling(driver);
						if (Filter_Val.equalsIgnoreCase("MultiPNR")) {
							try {
								if (!VolnList.isEmpty()) {
									String[] Sptpnr;
									Sptpnr=VolnList.split(";");
									for(int i=0; i<Sptpnr.length; i++) {
										SelectFromPassengerList( driver,  queryObjects,Sptpnr[i],Filter_Name, Sptpnr[i]);									
									}
								}
							} catch (Exception e) {}							
						}
						Orderselect=true;
					}else {
						//Select single or multiple data from the PAX lists displayed
						if (!sCheckinMsg.equalsIgnoreCase("AddVolOversold")) {
							if (!Filter_Name.equalsIgnoreCase("Name")) {
								sOrderNum = SelectFromPassengerList(driver,queryObjects,"",Filter_Name, Filter_Val);//Updated By Jenny
							}
						}	
						Orderselect=true;
					}					 
				}
			}
			if(sGName != "") {
				// driver.findElement(By.xpath("//input[contains(@ng-model,'givenName')]")).click();
				driver.findElement(By.xpath(GivenNameXpath)).sendKeys(sGName);
			}
			if(sSName != "") {
				//driver.findElement(By.xpath("//input[contains(@ng-model,'surName')]")).click();
				driver.findElement(By.xpath(SurNameXpath)).sendKeys(sSName);

			}

			if(sFrom != "") {
				try {
					driver.findElement(By.xpath(FromXpath)).clear();
					driver.findElement(By.xpath(FromXpath)).sendKeys(sFrom);
					//driver.findElement(By.xpath(FromXpath)).sendKeys(Keys.TAB);
					//Tab Alternate Solution
					try {
						driver.findElement(By.xpath(Login.SelList)).click();
					} catch (Exception e1) {
						try {
							driver.findElement(By.xpath(Login.SelList2)).click();
						} catch (Exception e) {
							try {
								driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='"+sFrom+"']/ancestor::span[2]/following-sibling::span/span/../../..")).click();
								}catch(Exception e3) {
									driver.findElement(By.xpath(FromXpath)).sendKeys(Keys.TAB);
								}
						}
					}
				}catch(Exception e) {}
			}	
			if(sFromleft != "") {
				driver.findElement(By.xpath("//input[@name=\"origin\"]")).clear();
				driver.findElement(By.xpath("//input[@name=\"origin\"]")).sendKeys(sFromleft);
				//driver.findElement(By.xpath("//input[@name=\"origin\"]")).sendKeys(Keys.TAB);
				//Tab Alternate Solution
				try {
					driver.findElement(By.xpath(Login.SelList)).click();
				} catch (Exception e1) {
					try {
						driver.findElement(By.xpath(Login.SelList2)).click();
					} catch (Exception e) {
						try {
							driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='"+sFromleft+"']/ancestor::span[2]/following-sibling::span/span/../../..")).click();
							}catch(Exception e3) {
								driver.findElement(By.xpath("//input[@name=\"origin\"]")).sendKeys(Keys.TAB);
							}
					}
				}
			}
			if(sTo != "" && !sGateCheckin.equalsIgnoreCase("YES")) {
				driver.findElement(By.xpath(ToXpath)).sendKeys(sTo);
				//driver.findElement(By.xpath(ToXpath)).sendKeys(Keys.TAB);
				//Tab Alternate Solution
				try {
					driver.findElement(By.xpath(Login.SelList)).click();
				} catch (Exception e1) {
					try {
						driver.findElement(By.xpath(Login.SelList2)).click();
					} catch (Exception e) {
						try {
							driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='"+sTo+"']/ancestor::span[2]/following-sibling::span/span/../../..")).click();
							}catch(Exception e3) {
								driver.findElement(By.xpath(ToXpath)).sendKeys(Keys.TAB);
							}
					}
				}
			}
			//meenu_to search by pax name and flight num
			if(sFlight != "" && !sFlight.contains("Share") && !sFlight.equalsIgnoreCase("RES")&& !sGateCheckin.equalsIgnoreCase("YES")) {
				try {
					driver.findElement(By.xpath(FlightXpath)).sendKeys(sFlight);
					driver.findElement(By.xpath(FlightXpath)).sendKeys(Keys.TAB);
				} catch (Exception e) {}
				
			}
			if(sOrderNum != "" && Orderselect==false && !sGateCheckin.equalsIgnoreCase("YES") && !sOrderNum.equalsIgnoreCase("SharePNR") && !sOrderNum.equalsIgnoreCase("SplitPNR")){
				
				try {
					if(sOrderNum.equalsIgnoreCase("RESPNR")){
						sOrderNum = Login.PNRNUM.trim();			 
					}
				}catch(Exception e) {}
				driver.findElement(By.xpath(OrderNumXpath)).clear();
				driver.findElement(By.xpath(OrderNumXpath)).sendKeys(sOrderNum);
			}	 
			//SharePNR Check and Search
			
			if(sOrderNum != "" && Orderselect==false && !sGateCheckin.equalsIgnoreCase("YES") && !sOrderNum.equalsIgnoreCase("RESPNR")){
				try {
					Login.shareflightnm=ISharesflow.Flight;
			//		Login.envwrite(Login.SharesPNR,IshareNRSAandNRPA.INRSA_PNR);
					if(sOrderNum.equalsIgnoreCase("SharePNR")){
						sOrderNum=ISharesflow.iPNRNo; 
					}
				}catch(Exception e) {}

				driver.findElement(By.xpath(OrderNumXpath)).sendKeys(sOrderNum);
							
			}
			
			// Split PNR Check and Search- Atul- 7Apr
			if(sOrderNum != "" && Orderselect==false && !sGateCheckin.equalsIgnoreCase("YES") && !sOrderNum.equalsIgnoreCase("SharePNR") && !sOrderNum.equalsIgnoreCase("RESPNR")){
				
				try {
					if(sOrderNum.equalsIgnoreCase("SplitPNR")){
						sOrderNum=PNRsearch.SplitPNR;			 
					}
				}catch(Exception e) {}

				driver.findElement(By.xpath(OrderNumXpath)).sendKeys(sOrderNum);
			}						
			//PnrNum = sOrderNum;
			//SharePNR Check and Search

			if(sTicket != "") {
				if(sTicket.equalsIgnoreCase("ResTicket")) {
					sTicket=FlightSearch.gettecketno.get(0);
				}
			 	if(sTicket.equalsIgnoreCase("ShareTicket")) {
			 		sTicket=ISharesflow.ShareEticket;
			 	}
			 	driver.findElement(By.xpath(TicketXpath)).sendKeys(sTicket);}

			if(sFFProgram != "") {
				driver.findElement(By.xpath(FFXpath)).click();
				driver.findElement(By.xpath("//div[@class='md-text']/span[contains(text(),'"+ sFFProgram +"')]")).click();
			}

			//suman

			if(sDays != "") {
/*				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, Integer.parseInt(sDays));
				String sDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());*/
				
				String gDate_app= driver.findElement(By.xpath("//pssgui-date-time[@action='display']//div")).getText().trim();
				gDate_app=gDate_app.split(" ")[0];
				Calendar cal = Calendar.getInstance();

				SimpleDateFormat sdf = new  SimpleDateFormat("MM/dd/yyyy");  // set this date formate
				SimpleDateFormat sdf2 = new  SimpleDateFormat("dd-MMM-yyyy");   // get from app 
				
				cal.setTime(sdf2.parse(gDate_app));
				if (!sDays.equalsIgnoreCase("SharesDte")) {
					if(!sDays.equalsIgnoreCase("")) 
						cal.add(Calendar.DATE, Integer.parseInt(sDays));	
					else
						cal.add(Calendar.DATE, 1);
				}
				//

				String sDate = sdf.format(cal.getTime());
				
				if(sGateCheckin.equalsIgnoreCase("Yes")) {
					if (!ValDate.isEmpty()) {
						sDate = ValDate;
					}
					driver.findElement(By.xpath("//pssgui-date-time[contains(@ng-model,'flightSearch')]//input[@class='md-datepicker-input']")).clear();
					driver.findElement(By.xpath("//pssgui-date-time[contains(@ng-model,'flightSearch')]//input[@class='md-datepicker-input']")).sendKeys(sDate);
					Thread.sleep(1000);	
					if (sFlight != "" && (sFlight.equalsIgnoreCase("Shareflight") || sFlight.equalsIgnoreCase("Shares"))){
						sFrom = ISharesflow.Orig;
						sFlight = ISharesflow.Flight;
						PaxList_FlightSearch(queryObjects, driver, sDate, sFrom, sFlight);
					}
				}
				else if(sSName != "") {
					if (!ValDate.isEmpty()) {
						sDate = ValDate;
					}
					driver.findElement(By.xpath(DateXpath)).clear();		
					driver.findElement(By.xpath(DateXpath)).sendKeys(sDate);		
					Thread.sleep(1000);		
				}
				/*else {
					driver.findElement(By.xpath(DateXpath)).clear();
					driver.findElement(By.xpath(DateXpath)).sendKeys(sDate);
					Thread.sleep(1000);
				}*/
			}
			if(sFFnum != "") {
				driver.findElement(By.xpath(FFnumXpath)).sendKeys(sFFnum);
			}
			FlightSearch.loadhandling(driver);
			//Submit or Proceed to check in page

			if (sSearch.equalsIgnoreCase("Y")) {       ////suman  UPDATED ON 8th May 2018 
				if(FLAG && sFlight.equalsIgnoreCase("OutSync"))

				{
					driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::div[@ng-if='flightSearch.display.gateSearchBtn']/button")).click();
					FlightSearch.loadhandling(driver); 
					driver.findElement(By.xpath(TktNotSyncStatus)).click();
					List<WebElement> details = driver.findElements(By.xpath("//div[contains(@ng-class,'passengerItinerary.model.Flight')]"));
					details.get(0).click();
					FlightSearch.loadhandling(driver);
				}

				//else if(FLAG && !sFlight.equalsIgnoreCase("OutSync"))
				else if(FLAG && driver.findElements(By.xpath("//div[@class='ng-scope flex']/button[@translate='pssgui.search']")).size()>0) 
				{
					driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::div[@ng-if='flightSearch.display.gateSearchBtn']/button")).click();
					FlightSearch.loadhandling(driver); 
					/* driver.findElement(By.xpath(TktNotSyncStatus)).click();
				 List<WebElement> details = driver.findElements(By.xpath("//div[contains(@ng-class,'passengerItinerary.action')]"));
				 details.get(0).click();*/
				}
				else
				{
					if (driver.findElements(By.xpath("//div[@class='ng-scope flex']/button[@translate='pssgui.search']")).size()>0) {
							driver.findElement(By.xpath(SearchXpath)).click();
							FlightSearch.loadhandling(driver);
					}
				}

			}
			
			//add icon validation before proceed to checkin rushil
			if(!sicon_val_befcheckin.isEmpty()) {
				String ColorVal = ""; String SpltColor[] = null; String  IcnName = "";
				SpltColor = sicon_val_befcheckin.split("-");
				for (int ic = 0; ic < SpltColor.length; ic++) {
					ColorVal = ""; IcnName = "";
					if (SpltColor[ic].contains("ADC")) {
						ColorVal = driver.findElement(By.xpath("//i[contains(@class,'icon-adc')]")).getCssValue("color"); IcnName = "ADC";
					} else if (SpltColor[ic].contains("APIS")) {
						ColorVal = driver.findElement(By.xpath("//i[contains(@class,'icon-apis')]")).getCssValue("color"); IcnName = "APIS";
					} else if (SpltColor[ic].contains("INF")) {
						ColorVal = driver.findElement(By.xpath("//i[contains(@class,'icon-infant')]")).getCssValue("color"); IcnName = "INFANT";
					} else if (SpltColor[ic].contains("PAX")) {
						ColorVal = driver.findElement(By.xpath("//i[contains(@ng-class,'iconPaxChar')]")).getCssValue("color"); IcnName = "PAXCHAR";
					} else if (SpltColor[ic].contains("CHKIN")) {
						ColorVal = driver.findElement(By.xpath("//i[contains(@class,'icon-checked-in in-active-state')]")).getCssValue("color"); IcnName = "CHECKIN";
					}
					if (SpltColor[ic].contains(ColorsValidation(ColorVal))) {
						queryObjects.logStatus(driver, Status.PASS, "Check the "+IcnName+" icon color display in Proceed to Checkin page", IcnName+ " icon displayed in "+SpltColor[ic].replace(IcnName, "")+ " color. Validation successful" , null);
					}else {
						queryObjects.logStatus(driver, Status.FAIL, "Check the "+IcnName+" icon color display in Proceed to Checkin page", IcnName+ "icon is not in "+SpltColor[ic].replace(IcnName, "")+ " color. Validation failed" , null);
					}					
				}
			}
			if (sGateCheckin.equalsIgnoreCase("yes")) {
				if(sOrderNum.equalsIgnoreCase("SMultiPNR")){
					if (Login.MultiplePNR.isEmpty()) {
						Login.MultiplePNR= ISharesflow.MultiPNR;
					}
					sOrderNum= Login.MultiplePNR.trim();
				} else {
					sOrderNum = Login.PNRNUM; //Removed SharePnrSearch class, Update the data in Testdata_ATO file
				}				
			}
			
			try {
				if(!Filter_Name.isEmpty() && Filter_Val.equalsIgnoreCase("SharesPNR")) {
					Filter_Val="";
					Filter_Val=ISharesflow.iPNRNo;					
					SelectFromPassengerList( driver,  queryObjects,Filter_Val,Filter_Name, Filter_Val);	
					queryObjects.logStatus(driver, Status.PASS, "Search by PNR", "PNR Retrieved", null);
				}	
				if(!Filter_Name.isEmpty() && Filter_Val.equalsIgnoreCase("SharesName")) {
					Filter_Val="";
					Filter_Val=ISharesflow.iPasName;
					Filter_Val = Filter_Val.toUpperCase();
				
				SelectFromPassengerList( driver,  queryObjects,Filter_Val,Filter_Name, Filter_Val);	
				queryObjects.logStatus(driver, Status.PASS, "Search by Name", "PNR Retrieved", null);
				} else if (Filter_Name.equalsIgnoreCase("Name") && Filter_Val.equalsIgnoreCase("SharesChangedName")) { //To Filter with changed name - Atul- 6APR
					Filter_Val="";
					Filter_Val = ISharesflow.iPasChangedName;
					Filter_Val = Filter_Val.toUpperCase();
					SelectFromPassengerList( driver,  queryObjects,Filter_Val,Filter_Name, Filter_Val);	
					queryObjects.logStatus(driver, Status.PASS, "Search by Name", "Passenger Retrieved", null);
					driver.findElement(By.xpath("//div[text()='eTKT Not Sync']//parent::div/div[2]/div")).click();
					FlightSearch.loadhandling(driver);
					queryObjects.logStatus(driver, Status.PASS, "Search by Name", "2 Passengers Displayed", null);
					return;
				
				} else if(Filter_Name.equalsIgnoreCase("Name") && !Filter_Val.equalsIgnoreCase("SharesName")) {//check both the else if conditions
						Filter_Val = Filter_Val.toUpperCase();
					SelectFromPassengerList( driver,  queryObjects,Filter_Val,Filter_Name, Filter_Val);	
					queryObjects.logStatus(driver, Status.PASS, "Search by Name", "Passenger Retrieved", null);
				} else if(Filter_Name.equalsIgnoreCase("Name") && !Filter_Val.isEmpty()) {
					Filter_Val = Filter_Val.toUpperCase();
					SelectFromPassengerList( driver,  queryObjects,Filter_Val,Filter_Name, Filter_Val);	
					queryObjects.logStatus(driver, Status.PASS, "Search by Name", "PNR Retrieved", null);
				}				
				if (sCheckinMsg.equalsIgnoreCase("SchedulePNR")) {
					try {
						Filter_Val = sOrderNum = driver.findElement(By.xpath("(//span[contains(@ng-click,'pnr')])[1]")).getText();
						Thread.sleep(500);
						SelectFromPassengerList( driver,  queryObjects,Filter_Val,"PNR", Filter_Val);
					} catch (Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Get Schedule change PNR", "PNR is not available", null);
						return;
					}
					
				}
			} catch(Exception e) {
				queryObjects.logStatus(driver, Status.INFO, "Search by PNR", "PNR search unsuccessful", null);
			}
			try {
				if(!Filter_Name.isEmpty() && Filter_Val.equalsIgnoreCase("MultiPNR"))
				{
					if (Login.MultiplePNR.isEmpty()) {
						Login.MultiplePNR= ISharesflow.MultiPNR;
					}
					String[] mulpnr;
					mulpnr=Login.MultiplePNR.split(";");
					for(int i=0; i<mulpnr.length; i++) {
						Filter_Val=mulpnr[i];
						String retval=SelectFromPassengerList( driver,  queryObjects,Filter_Val,Filter_Name, Filter_Val);	
						
					}
				}
			} catch(Exception e) {
					queryObjects.logStatus(driver, Status.INFO, "Search by PNR", "PNR search unsuccessful", null);
			}
				
			try {
				if(!Filter_Name.isEmpty() && Filter_Val.equalsIgnoreCase("ResPNR"))
				{
					Filter_Val="";
					Filter_Val = Login.PNRNUM;
				
						  
					SelectFromPassengerList( driver,  queryObjects,Filter_Val,Filter_Name, Filter_Val);	
					
				}
			} catch(Exception e) {
					queryObjects.logStatus(driver, Status.INFO, "Search by PNR", "PNR search unsuccessful", null);
			}
			//NS
			// GO_Show create PNR
			if(!sSeatCheck.isEmpty()) {				
				Boolean found = false;
				String Colorchk = "Green";
			try {	
				found= driver.findElement(By.xpath("//md-input-container//input[contains(@aria-label,'Seat Number') and (@value='"+sSeatCheck+"')]")).isDisplayed();
				if(found)
					queryObjects.logStatus(driver, Status.PASS, "Checking Seat", "Found Seat "+sSeatCheck, null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Checking Seat", "Seat not found "+sSeatCheck, null);
				String ColorVal = driver.findElement(By.xpath("//i[contains(@class,'icon-apis')]")).getCssValue("color");
				if (Colorchk.contains(ColorsValidation(ColorVal)))
					queryObjects.logStatus(driver, Status.PASS, "Doc Check", "Doc check Completed - Green", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Doc Check", "Doc check Incomplete", null);
				
				//Check details in standby screen
				queryObjects.logStatus(driver, Status.INFO, "Doc Check & Seat Check", "Checking in Standby Tab", null);						
				try {
					driver.findElement(By.xpath("//div[@translate='pssgui.standby']/..")).click();
					FlightSearch.loadhandling(driver);
				} catch (Exception e) {}
				if(Filter_Name.equalsIgnoreCase("Name") && !Filter_Val.equalsIgnoreCase("SharesName")) {//check both the else if conditions
					Filter_Val = Filter_Val.toUpperCase();
				SelectFromPassengerList( driver,  queryObjects,Filter_Val,Filter_Name, Filter_Val);	
				}
				ColorVal = driver.findElement(By.xpath("//i[contains(@class,'icon-apis')]")).getCssValue("color");
				if (Colorchk.contains(ColorsValidation(ColorVal))) 
					queryObjects.logStatus(driver, Status.PASS, "Doc Check", "Doc check Completed - Green", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Doc Check", "Doc check Incomplete", null);
				
				String seatname=driver.findElement(By.xpath("//md-input-container[contains(@class,'passenger-seat-number')]//input")).getAttribute("value");
				if(seatname.equalsIgnoreCase(sSeatCheck))
					queryObjects.logStatus(driver, Status.PASS, "Checking Seat", "Found Seat "+sSeatCheck, null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Checking Seat", "Seat not found "+sSeatCheck, null);
				
				
				//check passenger name details
				queryObjects.logStatus(driver, Status.INFO, "Doc Check & Seat Check", "Checking in PAX Info", null);						
				driver.findElement(By.xpath("//div[text()='All']")).click();
				FlightSearch.loadhandling(driver);
				if(Filter_Name.equalsIgnoreCase("Name") && !Filter_Val.equalsIgnoreCase("SharesName")) {//check both the else if conditions
					Filter_Val = Filter_Val.toUpperCase();
				SelectFromPassengerList( driver,  queryObjects,Filter_Val,Filter_Name, Filter_Val);	
				}
				driver.findElement(By.xpath("//div[contains(@class,'passenger-list layout-row row-selected')]//div[contains(@ng-class,'checkin-name')]//span")).click();
				FlightSearch.loadhandling(driver);
				found= driver.findElement(By.xpath("//div[contains(@ng-repeat,'seat in segment.travelerInfo.Seats')]//span[contains(text(),'"+sSeatCheck+"')]")).isDisplayed();
				if(found)
					queryObjects.logStatus(driver, Status.PASS, "Checking Seat", "Found Seat "+sSeatCheck, null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Checking Seat", "Seat not found "+sSeatCheck, null);
				ColorVal = driver.findElement(By.xpath("//i[contains(@class,'icon-apis')]")).getCssValue("color");
				if (Colorchk.contains(ColorsValidation(ColorVal))) 
					queryObjects.logStatus(driver, Status.PASS, "Doc Check", "Doc check Completed - Green", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Doc Check", "Doc check Incomplete", null);
				
				driver.findElement(By.xpath("//div[text()='Back']")).click();
				FlightSearch.loadhandling(driver);
			}	
			catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Issue in Seat and Doc check validation", "Exiting", e);
			}
			return;
			}
			
			//Compensation - Oversold List
			if (!sFlightOnly.isEmpty()&& (FilterFgtList.equalsIgnoreCase("OversoldCompensate"))) {
				try {
					if (Orderselect) {
						PnrNum = Login.MultiplePNR;
						driver.findElement(By.xpath(Compensate)).click();
						FlightSearch.loadhandling(driver);
						FlightSearch.loadhandling(driver);
						//Navira - 17April
						if(sCheckinMsg.equalsIgnoreCase("DisplayOnly")) {
							queryObjects.logStatus(driver, Status.PASS, "Checking Compensation Display", "Compensation List is displayed", null);
							return;
						}
						//End
						if (sCheckinMsg.equalsIgnoreCase("IssuedCompensation")) {
							sOrderNum = ISharesflow.MultiPNR;
							for (int ic = 1; ic <= driver.findElements(By.xpath("(//div[contains(@ng-repeat,'reports in ')])")).size(); ic++) {
								if (sOrderNum.contains(driver.findElement(By.xpath("(//div[contains(@ng-repeat,'reports in ')])["+ic+"]//div[contains(@class,'input ng-binding')]")).getText().trim())) {
									if (driver.findElement(By.xpath("(//div[contains(@ng-repeat,'reports in ')])["+ic+"]//div[contains(@class,'passenger-list')]")).getAttribute("class").contains("row-highlight")) {
										queryObjects.logStatus(driver, Status.PASS, "Check the Compensation saved orders are highlighted", "Compensation saved with reason is highlighted", null);
										if (driver.findElement(By.xpath("(//div[contains(@ng-repeat,'reports in ')])["+ic+"]//span[contains(@ng-if,'reports.selected')]")).getText().contains(cCompensationRsn)) {
											queryObjects.logStatus(driver, Status.PASS, "Check the compensation issued reason is displayed for the orders saved", "Compensation Reason is displayed", null);
										} else {
											queryObjects.logStatus(driver, Status.FAIL, "Check the compensation issued reason is displayed for the orders saved", "Compensation Reason is not displayed", null);
										}
									} else {
										queryObjects.logStatus(driver, Status.FAIL, "Check the Compensation saved orders are highlighted", "Compensation saved with reason is not highlighted", null);
									}
								}
							}
							PnrNum = ISharesflow.iPNRNo;
							Compensation.UpdateCompensationReason(driver, queryObjects, "", Verifications, cCompensationRsn);
						} else {
							Compensation.UpdateCompensationReason(driver, queryObjects, " ", Verifications, cCompensationRsn);
						}
						if (Verifications.equalsIgnoreCase("Stop Issue"))//meenu_stop before issuing compensation
						{
							return;
						} else if (!Verifications.equalsIgnoreCase("save")) {
							try {
								CompInVol = driver.findElements(By.xpath("//span[contains(text(),'HKN-SB')]")).size();
								CompVol = driver.findElements(By.xpath("//span[contains(text(),'VOL-SB')]")).size();
							} catch (Exception e) {}
							if (!MultiOrder.equalsIgnoreCase(" "))
							{
								Compensation.IssueCompensation_Verify(driver, queryObjects, " ", MultiOrder,"Y",sFlight,sFrom,sDays, CompEmail, cDefaultAmt, cMonetaryAmt, cOverrideRsn, cMeal_EMD, cTransport_EMD, cHotel_EMD, cUpdateEMD, "",
										cReaccFlight, cReaccFrom, cReaccDest, cReaccDays, "", "", cAddDetailsTab, cAddDetType, cAddDetValue);	//select specifix pax						
							} else 
								Compensation.IssueCompensation_Verify(driver, queryObjects, "Y", MultiOrder,"Y",sFlight,sFrom,sDays, CompEmail, cDefaultAmt, cMonetaryAmt, cOverrideRsn, cMeal_EMD, cTransport_EMD, cHotel_EMD, cUpdateEMD, "",
										cReaccFlight, cReaccFrom, cReaccDest, cReaccDays, "", "", cAddDetailsTab, cAddDetType, cAddDetValue);
							}
							//meenu_update compensation not issued tab
							if(FlightSearch.getTrimTdata(queryObjects.getTestData("NoCompensation")).equalsIgnoreCase("yes"))  //meenu
							{     
								String PaxNotToComm = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxNotToComm"));
								if (!PaxNotToComm.isEmpty())
								{
									Compensation.Compensation_Not_Issued(driver, queryObjects,"",PaxNotToComm,cIssue_NoComp,sFlight,sFrom, sDays, cReaccFlight, cReaccFrom, cReaccDest, cReaccDays, "", "", cAddDetailsTab, cAddDetType, cAddDetValue);	
								}
								else 
									Compensation.Compensation_Not_Issued(driver, queryObjects,"Y","", cIssue_NoComp,sFlight,sFrom, sDays, cReaccFlight, cReaccFrom, cReaccDest, cReaccDays, "", "", cAddDetailsTab, cAddDetType, cAddDetValue);
							}
							if(FlightSearch.getTrimTdata(queryObjects.getTestData("changeEmail")).equalsIgnoreCase("yes"))  //meenu
							{
								Compensation.ChangeEmail(driver, queryObjects);
							}
							/*if (!Verifications.equalsIgnoreCase("save")) {
								Compensation.IssueCompensation_Verify(driver, queryObjects, "Y", MultiOrder,"Y","","","",CompEmail);
							}*/


						}					
				} catch (Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Issue Compensation for Oversold Passenger List", "Unable to issue Compensation", e);
				}
				return;
			}
			
			if (G0_shows.equalsIgnoreCase("yes") || Force_sale.equalsIgnoreCase("yes"))
				GO_Show_ForceSale(driver, queryObjects);
			// GO_Show create PNR
			
			//Release misconnect by rushil 16 april
			if(Release_Misconnect.equalsIgnoreCase("yes"))
			{
				try {
				driver.findElement(By.xpath(InboundTab)).click();
				FlightSearch.loadhandling(driver);
				List<WebElement> FlightList = driver.findElements(By.xpath(InboundList));
				if (FlightList.size()>=1) {
					for (int FlightCnt = 1; FlightCnt <= FlightList.size(); FlightCnt++) {
						String SplitFgtData[] = (driver.findElement(By.xpath("("+InboundList+")["+FlightCnt+"]")).getText()).split("\\n");
						if (SplitFgtData[0].contains(Release_Misconnect_flt)) {
							driver.findElement(By.xpath("("+InboundList+")["+FlightCnt+"]")).click();
							FlightSearch.loadhandling(driver);
							if(driver.findElement(By.xpath("//button[@translate='pssgui.release.misconnects']")).isEnabled()) {
								driver.findElement(By.xpath("//button[@translate='pssgui.release.misconnects']")).click();
								FlightSearch.loadhandling(driver);
								boolean FligtOpenMsg= false;
								try{
									FligtOpenMsg=driver.findElement(By.xpath("//span[contains(text(),'SHIP NOT ASSIGNED')]")).isDisplayed();
									//
								}
								catch(Exception e){}
								if(FligtOpenMsg && openflightflag==0)
								{
									OpenFlights_Checkin(driver, queryObjects);
								}
								queryObjects.logStatus(driver, Status.PASS, "Release misconnects", "Succesfull",null);
								break;
								}
							else
								queryObjects.logStatus(driver, Status.FAIL, "Release misconnects", "Fail",null);
						}
					}	
				}
			}
				catch(Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Release misconnects", "Fail",e);
				}
			}
			
			if(Select_pax.equalsIgnoreCase("Y")) {

				// driver.findElement(By.xpath(DoneXpath)).click();
				// driver.findElement(By.xpath("//div[contains(text(),'Back')]")).click();
				FlightSearch.loadhandling(driver);
				String Selectetext="selectee Pax";   //suman
				driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).clear();
				for (int i = 0; i < Selectetext.length(); i++){

					char c = Selectetext.charAt(i);
					String s = new StringBuilder().append(c).toString();
					driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).sendKeys(s);
					Thread.sleep(200);
				}

				driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).sendKeys(Keys.ENTER);
				FlightSearch.loadhandling(driver);
				// String Filter_Val= FlightSearch.getTrimTdata(queryObjects.getTestData("Filter_Val"));
				SelectFromPassengerList( driver,  queryObjects,Filter_Val,Filter_Name, Filter_Val);


				List<WebElement> selectepax=driver.findElements(By.xpath("//div[contains(@ng-class,'checkin-name')]/div/span"));  //suman 25 april

				Boolean Selecteeflag=true;
				for(int i=0;i<selectepax.size();i++)   ///SUMAN 25 APRIL
				{
					// String name=selecteepax.
					if((selectepax.get(i)).getText().contains(Filter_Val))
					{   Selecteeflag=false;
					queryObjects.logStatus(driver, Status.PASS, "Checking the selectee PAX", "Given selectee pax found+"+selecteePaxName,null);
					return;
					}
				}
				if(Selecteeflag)
				{
					queryObjects.logStatus(driver, Status.FAIL, "Checking the selectee PAX", "Given selectee pax NOT found",null);  
					return;
				}

			}
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("Pax_requiring_seat")).equalsIgnoreCase("y"))
			{
				String Requiringtext="Pax requiring seat";   
				for (int i = 0; i < Requiringtext.length(); i++){

					char c = Requiringtext.charAt(i);
					String s = new StringBuilder().append(c).toString();
					driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).sendKeys(s);
				}
				driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).sendKeys(Keys.ENTER); 

				FlightSearch.loadhandling(driver);

				if(driver.findElements(By.xpath("//span/span[contains(text(),'Pax Requiring Seat')]")).size()>0)  
				{
					//driver.findElement(By.xpath("//span/span[contains(text(),'Pax Requiring Seat')]")).click();
					queryObjects.logStatus(driver, Status.PASS, "Checking the Requiring Seat drop down", "Drop down Requiring Seat selected for filtering",null);
				}
				else
				{
					queryObjects.logStatus(driver, Status.FAIL, "Checking the Pax Requiring Seat drop down", "Drop down No Pax Requiring Seat selected for filtering",null);
				}

				List<WebElement> paxseat=driver.findElements(By.xpath("//div[i[@class='icon-seatmap']]//descendant::span"));
				List<String> paxseatno=new ArrayList<>();
				paxseat.forEach(a->paxseatno.add(a.getText().trim()));
				//String seatEmpty="";
				boolean seatEmpty=true;
				for(String value : paxseatno)
				{
					if(!value.equals("")){
						seatEmpty=false;
						break;
					}
				}

				if(seatEmpty) 
					queryObjects.logStatus(driver, Status.PASS, "Checking Pax Requiring Seat functionality", "All passangers have no seats assigned"+seatEmpty, null);        
				else 
					queryObjects.logStatus(driver, Status.FAIL, "Checking  empty seat passangers", "passanger have seats assigned", null);
			}
			//Seat_Change= 
			if(Seat_Change.equalsIgnoreCase("Y"))
			{
				if(driver.findElements(By.xpath(CheckedInPax)).size()>0)
				{
					/*String Filter_Name= FlightSearch.getTrimTdata(queryObjects.getTestData("Filter_Name"));
					String Filter_Val= FlightSearch.getTrimTdata(queryObjects.getTestData("Filter_Val"));*/
					//Atul 25Mar - To change the seat for one pax
					String SeatChange_OnePax = FlightSearch.getTrimTdata(queryObjects.getTestData("SeatChange_OnePax"));
					if (!SeatChange_OnePax.equalsIgnoreCase("yes")) {
						SelectFromPassengerList(driver, queryObjects,"",Filter_Name, Filter_Val); 
					}
					
					driver.findElement(By.xpath("//md-checkbox[@aria-checked='true']")).click();
					List<WebElement> AlreadyAssigndSeat=driver.findElements(By.xpath(CheckedInPax));
					//Select the Already assign seat
					AlreadyAssigndSeat.get(0).click();
					//driver.findElement(By.xpath("//div[contains(@ng-repeat,'passengerItinerary')]/child::div/div[1]/div[2]/i[contains(@class,'checked-in active-state')]//parent::div/preceding-sibling::md-checkbox/div[1]")).click();	
					ChangeSaetFlag=true;
					status=true;
					queryObjects.logStatus(driver, Status.PASS, "Checking checkin pax", " checkedin pax found ", null);  

				}
				else
				{
					List<WebElement> AssignASeat=driver.findElements(By.xpath(NotCheckedinPax));
					//Select the not assign seat
					AssignASeat.get(0).click();
					//driver.findElement(By.xpath("//div[contains(@ng-repeat,'passengerItinerary')]/child::div/div[1]/div[2]/i[contains(@class,'in-active-state')]//parent::div/preceding-sibling::md-checkbox/div[1]")).click();
					ChangeSaetFlag=true;
					queryObjects.logStatus(driver, Status.INFO, "Checking checkin pax", "No checkedin pax found", null);
				}
			}
			//validation of the search parameters
			boolean MultipleMatches = false;
			boolean MultipleFlight = false;

			try {
				MultipleMatches = driver.findElement(By.xpath(FirstpnrXpath)).isDisplayed();


			} catch(Exception e) {}

			try {
				MultipleFlight = driver.findElement(By.xpath(FirstflightXpath)).isDisplayed();


			} catch(Exception e) {}


			if (MultipleMatches || MultipleFlight) {
				queryObjects.logStatus(driver, Status.PASS, "Search Passenger resulted in multiple passengers", "multiple passenger dispalyed ", null);


				if (MultipleFlight) {
					try {
						FlightSearch.loadhandling(driver);
						List<WebElement> Flights =  driver.findElements(By.xpath(FirstflightXpath));
						Thread.sleep(1000);
						Flights.get(0).click();
						FlightSearch.loadhandling(driver);					
					}
					catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Search Passenger ", "OrderID search not completed: " , e); 
					}
				}
				if (MultipleMatches) {
					try {
						FlightSearch.loadhandling(driver);
						List<WebElement> Orders =  driver.findElements(By.xpath(FirstpnrXpath));
						Thread.sleep(1000);
						Orders.get(0).click();
						//String strPNR = 
						//List<WebElement> pnrs =driver.findElements(By.xpath("//div[contains(text(),'"+strPNR+"')]"));
						//pnrs.get(1).click();
						FlightSearch.loadhandling(driver);

					}
					catch(Exception e) {
						if (MultipleFlight && driver.findElements(By.xpath("//div[contains(@class,'passenger-info-details')]")).size()>0) {
							queryObjects.logStatus(driver, Status.PASS, "Search Passenger ", "OrderID is displayed " , null);
						} else {
							queryObjects.logStatus(driver, Status.FAIL, "Search Passenger ", "OrderID search not completed: " , e);
						} 
					}
				}
			}

			if(sOrderNum != "") {
				try {
					if(sOrderNum.equalsIgnoreCase("RESPNR")){
						sOrderNum = Login.PNRNUM.trim();				 
					}
					boolean OrderId = driver.findElement(By.xpath("//*[contains(text(),'"+sOrderNum+"')]")).isDisplayed();
					if (OrderId) {
						queryObjects.logStatus(driver, Status.PASS, "Search Passenger by Order ID", "OrderID search completed: "+sOrderNum , null);
					}else {
						queryObjects.logStatus(driver, Status.FAIL, "Search Passenger by Order ID "+sOrderNum, "OrderID search not completed: "+sOrderNum , null);
					}

				}catch(Exception e) {}
			}

			if(sFlight != "" && sFlight != "OutSync" && !sGateCheckin.equalsIgnoreCase("yes")) {
				try {
					if (sSearch.equalsIgnoreCase("y")) {
						boolean Flightdisplayed = driver.findElement(By.xpath("//div[contains(@class,'flight-details')]/div[contains(text(),'"+sFlight+"')]")).isDisplayed();
						if (Flightdisplayed) {
							queryObjects.logStatus(driver, Status.PASS, "Search Passenger by Flight details", "Passenger search completed: "+sFlight , null);
						}else {
							queryObjects.logStatus(driver, Status.FAIL, "Search Passenger by Flight details", "Passenger search completed: "+sFlight , null);
						}
					}					
				} catch (Exception e) {}
				
			}

			if(sFlight.equalsIgnoreCase("OutSync")) {
				boolean Errdisplayed = driver.findElement(By.xpath("//span[contains(text(),'out of sync')]")).isDisplayed();
				if (Errdisplayed) {
					queryObjects.logStatus(driver, Status.PASS, "Error displayed", "Passenger search completed: "+sFlight , null);
				}else {
					queryObjects.logStatus(driver, Status.FAIL, "Search Passenger by Flight details", "Passenger search completed: "+sFlight , null);
				}
			}
			if(sFFnum != "" && !NonTkt_PNR.equalsIgnoreCase("yes") ) {
				String sFQTV = driver.findElement(By.xpath(FQTVXpath)).getText().trim();
				
				if (sFQTV.contains(sFFnum)) {
					queryObjects.logStatus(driver, Status.PASS, "Search Passenger by FF number", "Passenger search completed: "+sFQTV , null);
				}else {
					queryObjects.logStatus(driver, Status.FAIL, "Search Passenger by FF number", "Passenger search completed: "+sFQTV , null);
				}
			}
			
			// Expand the Flight Info screen if it is in collapsed state
			try {
				driver.findElement(By.xpath("//div[@class='selected-flight-info']//i")).click();
				Thread.sleep(1000);
			} catch (Exception e) {}
			
			// click on dropdown for flight detail dropdown-- yashodha
			if(PaxListOptn.equalsIgnoreCase("y")) {
			driver.findElement(By.xpath("//input[@name='smartsearch']")).sendKeys("A");
			Thread.sleep(5000);
				driver.findElement(By.xpath("//span[contains(text(),'ll Checked-in Pax')]")).click();		
				Thread.sleep(3000);
				FlightSearch.loadhandling(driver);
				List<WebElement> Checked_inlist=driver.findElements(By.xpath("//md-checkbox[@aria-checked='false']"));	
				Checked_inlist.get(1).click();
				driver.findElement(By.xpath("//button[@aria-label='Offload']")).click();	
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//button[@aria-label='Proceed to checkin']")).click();
				FlightSearch.loadhandling(driver);
				
				String savedVal = driver.findElement(By.xpath("//input[@aria-label='Surname']")).getText();

				if (savedVal.isEmpty()) { 
					queryObjects.logStatus(driver, Status.PASS, "ADC/APIS information was not deleted after offloading", "APIS information was not deleted", null);
					queryObjects.logStatus(driver, Status.PASS, "Checked-in PAX is Offloaded","--->Succefully" , null);}
				else 
					queryObjects.logStatus(driver, Status.FAIL, "ADC/APIS information was deleted after offloading", "APIS information was deleted", null);				
	
	
		} else if (!PaxListOptn.isEmpty() && sCheckinMsg.contains("SmartSearch_SSR")) {
			SetPassengerListOption(driver, queryObjects, PaxListOptn);
			if (SelectFromPassengerList(driver, queryObjects, sOrderNum, "PNR", sOrderNum).contains(sOrderNum)) {
				if (sCheckinMsg.contains("SmartSearch_SSR")) {
					queryObjects.logStatus(driver, Status.PASS, "Passenger List filter option_Filter by SSR - "+PaxListOptn,"Filtered order is displayed " , null);
				}
				
			} else {
				queryObjects.logStatus(driver, Status.FAIL, "Passenger List filter option","Filtered order is not displayed ", null);
			}
		}
			
			// Expand the Flight Info screen if it is in collapsed state
			try {
				driver.findElement(By.xpath("//div[@class='selected-flight-info']//i")).click();
				Thread.sleep(1000);
			} catch (Exception e) {}
			
			if (VolSB.equalsIgnoreCase("Yes")) {
				//Adding all the passengers to Vol Standby
				AddVolStandBy(driver, queryObjects, sOrderNum, VolSB);
				VolnList = ISharesflow.MultiPNR;
			}
			
			
			if(OFFload_standby.equalsIgnoreCase("Y") || OFFload_standby.equalsIgnoreCase("All") || OFFload_standby.contains("Y-"))
			{
				String OffloadPNR = "";
				String Seat = "";
				int Paxdis = 0;
				boolean offloaded_pax = false;
				FlightSearch.loadhandling(driver);
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//div[@translate='pssgui.standby']/..")).click();
				FlightSearch.loadhandling(driver);
				FlightSearch.loadhandling(driver);
				FlightSearch.loadhandling(driver);
				int BfrOffload = driver.findElements(By.xpath("//span[contains(@ng-click,'viewOrder')]")).size();
				int SeatCntBfr = driver.findElements(By.xpath("//div[contains(@class,'cabin-type') and contains(text(),'Business')]/..//div[@class='seat']/div[ contains(@class,'icn-taken')]")).size()+driver.findElements(By.xpath("//div[contains(@class,'cabin-type') and contains(text(),'Economy')]/..//div[@class='seat']/div[ contains(@class,'icn-taken')]")).size();
				int SeatCntAft = 0;
				
				if(OFFload_standby.equalsIgnoreCase("All")) {
					driver.findElement(By.xpath("//md-checkbox[contains(@ng-model,'checkAll ')]")).click();
				} else if (OFFload_standby.contains("Y-")) {
					for (int sl = 1; sl <= BfrOffload; sl++) {
						if (VolnList.contains(driver.findElement(By.xpath("(//span[contains(@ng-click,'viewOrder')])["+sl+"]")).getText().trim())) {
							if (!driver.findElement(By.xpath("(//div[contains(@ng-repeat,'seat in')])["+sl+"]//span[contains(@ng-if,'passengerData.chkBoxSelected')]")).getText().trim().contains("SB")) {
								Seat = driver.findElement(By.xpath("(//div[contains(@ng-repeat,'seat in')])["+sl+"]//span[contains(@ng-if,'passengerData.chkBoxSelected')]")).getText().trim();
								OffloadPNR = driver.findElement(By.xpath("(//span[contains(@ng-click,'viewOrder')])["+sl+"]")).getText().trim();
								driver.findElement(By.xpath("(//md-checkbox[@aria-label='pax-chk'])["+sl+"]")).click();
								break;
							}
						}
					}					
				} else {
					driver.findElement(By.xpath("//span[contains(text(),'"+ISharesflow.INRSA_PNR+"')]/ancestor::div[contains(@ng-repeat,'passengerData in passengerItinerary')]//md-checkbox[@aria-label='pax-chk']")).click();
				}			
				driver.findElement(By.xpath("//button[@translate='pssgui.offload']")).click();
				FlightSearch.loadhandling(driver);
				try {
					if(OFFload_standby.equalsIgnoreCase("All")) {
						Paxdis = driver.findElements(By.xpath("//span[contains(@ng-click,'viewOrder')]")).size();
						for (int op = 1; op <= Paxdis; op++) {
							if (sOrderNum.contains(driver.findElement(By.xpath("(//span[contains(@ng-click,'viewOrder')])["+op+"]")).getText().trim())) {
								offloaded_pax = true;
							}
						}
					} else if (OFFload_standby.contains("Y-")) {
						offloaded_pax=driver.findElement(By.xpath("//span[contains(text(),'"+OffloadPNR+"')]")).isDisplayed();
					} else {
						offloaded_pax=driver.findElement(By.xpath("//span[contains(text(),'"+ISharesflow.INRSA_PNR+"')]")).isDisplayed();
					}
				} catch (Exception e) {
					offloaded_pax = false;
				}				
				
				if(!offloaded_pax)
					queryObjects.logStatus(driver, Status.PASS, "Offload the Checked-in StandBy PAX","Checked-in StandBy PAX is Offloaded succesfully" , null);	
				else
					queryObjects.logStatus(driver, Status.FAIL, "Offload the Checked-in StandBy PAX","Checked-in StandBy PAX is not Offloaded" , null);
				FlightSearch.loadhandling(driver);
				//Check the Standbylist count after Offload
				driver.findElement(By.xpath("//div[@translate='pssgui.standby']/..")).click();
				FlightSearch.loadhandling(driver);
				FlightSearch.loadhandling(driver);
				FlightSearch.loadhandling(driver);
				if (BfrOffload>=driver.findElements(By.xpath("//span[contains(@ng-click,'viewOrder')]")).size()) {
					queryObjects.logStatus(driver, Status.PASS, "Offload the Checked-in StandBy PAX and check the standby count is updated","Passenger is removed from the standby list and the count is updated" , null);
				} else {
					queryObjects.logStatus(driver, Status.FAIL, "Offload the Checked-in StandBy PAX and check the standby count is updated","Standby list count is not updated, Offload is not successful" , null);
				}
				//Check the Seat count After Offload
				SeatCntAft = driver.findElements(By.xpath("//div[contains(@class,'cabin-type') and contains(text(),'Business')]/..//div[@class='seat']/div[ contains(@class,'icn-taken')]")).size()+driver.findElements(By.xpath("//div[contains(@class,'cabin-type') and contains(text(),'Economy')]/..//div[@class='seat']/div[ contains(@class,'icn-taken')]")).size();
				if (OFFload_standby.contains("Y-")) {
					if (SeatCntBfr==SeatCntAft+1) {
						queryObjects.logStatus(driver, Status.PASS, "Offload the Checked-in StandBy PAX and check the seat has been released","One seat is released and available on the seat map" , null);
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Offload the Checked-in StandBy PAX and check the seat has been released","Seat is not released, Offload is not successful" , null);
					}
				}	
			}	// updated by yashodha oct-2019
			
			
			// ClearStandby written by rushil 17 mar 2020
			if (ClearStandby.equalsIgnoreCase("yes")) {
				driver.findElement(By.xpath("//div[@translate='pssgui.standby']/..")).click();
				FlightSearch.loadhandling(driver);
				FlightSearch.loadhandling(driver);
				FlightSearch.loadhandling(driver);
				FlightSearch.loadhandling(driver);
				if(driver.findElements(By.xpath("//button[contains(@aria-label,'Clear')]")).size()==0) {
					AssignControllingAgent(driver, queryObjects);
					FlightSearch.loadhandling(driver);
					try {
						driver.findElement(By.xpath("//button[contains(@aria-label,'Initiate standby')]")).click();
						FlightSearch.loadhandling(driver);
						FlightSearch.loadhandling(driver);
					} catch (Exception e) {}
					boolean inform= false;
					try {
						inform = driver.findElement(By.xpath("//span[contains(text(),'Function restricted to controlling agents')]")).isDisplayed();
					}catch (Exception e) {}
					if(inform) {
							queryObjects.logStatus(driver, Status.INFO, "Initiate Standby Not Done", "Function restricted to controlling agents", null);
							return;
					} else {
							queryObjects.logStatus(driver, Status.PASS, "Initiate Standby Done", "Successful", null);
					}				
				}
				
				if(!ClearStandby_Name.isEmpty() || !No_of_Standby.equalsIgnoreCase("")) {
					if(ClearStandby_Name.equalsIgnoreCase("SharesName")) {
						ClearStandby_Name=ISharesflow.iPasName;
						ClearStandby_Name = ClearStandby_Name.toUpperCase();
					} else if(!No_of_Standby.equalsIgnoreCase("")) {	//Himani 18April
						int Paxcnt = Integer.parseInt(No_of_Standby);
						for(int cp=0;cp<Paxcnt;cp++) {
							driver.findElement(By.xpath("//div[contains(@class,'passenger-list layout-row')]//span/../../..//md-checkbox[contains(@aria-checked,'false')]")).click();
							FlightSearch.loadhandling(driver);
						}
					} else
					ClearStandby_Name = ClearStandby_Name.toUpperCase();
					
				driver.findElement(By.xpath("//div[contains(@class,'passenger-list layout-row')]//span[contains(text(),'"+ClearStandby_Name+"')]/../../..//md-checkbox")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//button[contains(@aria-label,'Clear')]")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//button[contains(@aria-label,'ok')]")).click();
				FlightSearch.loadhandling(driver);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "PAX Name to clear Standby","Pax name not given or pax name not there in standby list" , null);
				}
				return;
			}
			
			if(SwapSeat.equalsIgnoreCase("yes")) {
				
				driver.findElement(By.xpath("//div[@translate='pssgui.oversold']")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//div[contains(@class,'hpe-pssgui airport-panel')]//button[@aria-label='Add VOL']")).click();
				FlightSearch.loadhandling(driver);
				//driver.findElement(By.xpath("//input[@name='search']")).sendKeys("Adding to oversold ");

				driver.findElement(By.xpath("//div[contains(@class,'hpe-pssgui passenger-itinerary')]/div[1]//button[contains(@ng-click,'passengerItinerary.addVolPassenger')]")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//button[@ng-click='airportSearch.closePopup()']")).click();
				FlightSearch.loadhandling(driver);
				Thread.sleep(100);
				driver.findElement(By.xpath("//div[@translate='pssgui.standby']/..")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//button[@translate='pssgui.initiate.standby']")).click();
				FlightSearch.loadhandling(driver);
				Thread.sleep(100);
				driver.findElement(By.xpath("//button[@aria-label='Seat Swap']")).click();
				FlightSearch.loadhandling(driver);
				if(driver.findElement(By.xpath("//div[contains(text(),'Swapped')]")).isDisplayed()) {
				driver.findElement(By.xpath("//button[@translate='pssgui.ok']")).click();
					FlightSearch.loadhandling(driver);
					Thread.sleep(100);
					queryObjects.logStatus(driver, Status.PASS, "Swap Seat","Successful" , null);

				}				
			}
			
			//Seats
			if (!sBlockorUnblock.equalsIgnoreCase("")){
				
				if(openflightflag==0){
					openflightflag=1;
					String Flt=driver.findElement(By.xpath(flighNumXpath)).getText().trim();
					String date=driver.findElement(By.xpath(flightdateXpath)).getText().trim();
					String Orgin=driver.findElement(By.xpath(FlighOrginXpath)).getText().trim();
					OpenFlight opn=new OpenFlight(Flt,Orgin,date,driver,queryObjects);
					opn.run();
					/*Atoflow ato=new Atoflow();
					ato.SearchPassenger(driver, queryObjects);
					return;*/
				}

				if(!(driver.findElements(By.xpath(FlightAct)).size()>0))//expand if flight action is not displayed
				{
					driver.findElement(By.xpath("//div/div[2]/div/div[2]/div[2]/i")).click();
				}
				driver.findElement(By.xpath(FlightAct)).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//md-option[contains(@value,'update-seatmap')]")).click();
				FlightSearch.loadhandling(driver);

				List<WebElement> blocksets=driver.findElements(By.xpath("//div[contains(@class,'icn-blocked')]"));
				List<WebElement> Seatstaken=driver.findElements(By.xpath("//div[contains(@class,'icn-taken')]"));
				int iBlockSeats=blocksets.size();
				int iSeatstaken=Seatstaken.size();
				Thread.sleep(2000);

				if(SwapSeat.equalsIgnoreCase("permanant"))
				driver.findElement(By.xpath("//md-radio-button[@aria-label='Permanent Block']")).click();
				else				
				driver.findElement(By.xpath("//md-radio-button[@aria-label='Local Block']")).click();
				
				if (sBlockorUnblock.equalsIgnoreCase("BLOCK")) {

					Blockseat(driver, queryObjects,iBlockSeats,iSeatstaken, sBlockorUnblock);
				}
				else if(sBlockorUnblock.equalsIgnoreCase("UNBLOCK")){
					Blockseat(driver, queryObjects,iBlockSeats,iSeatstaken, sBlockorUnblock);
					//moved unblock to block seat method by rushil
					/*blocksets=driver.findElements(By.xpath("//div[contains(@class,'icn-blocked')]"));
					iBlockSeats=blocksets.size();
					blocksets=driver.findElements(By.xpath("//div[contains(@class,'icn-blocked')]"));
					blocksets.get(0).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//div[contains(text(),'Undo')]")).click();
					FlightSearch.loadhandling(driver);
					List<WebElement> afterupdateblocksets=driver.findElements(By.xpath("//div[contains(@class,'icn-blocked')]"));
					int iafterupdateBlockSeats=afterupdateblocksets.size();
					if(iafterupdateBlockSeats<iBlockSeats)
						queryObjects.logStatus(driver, Status.PASS, "Checking the Seat has blocked or not", "Seat has unblocked : " , null);
					else
						queryObjects.logStatus(driver, Status.FAIL, "Checking the Seat has blocked or not", "Seat has not unblocked:", null);
						*/
				}
				if (driver.findElements(By.xpath("//toggle-title[@translate='pssgui.seat.map.key']")).size()>0) {
					driver.findElement(By.xpath("//div[@translate='pssgui.back']")).click();
					FlightSearch.loadhandling(driver);
				}
			}
			
			if (Restrict.equalsIgnoreCase("Y") || Restrict.equalsIgnoreCase("N")) {
				//add controllingAgents
				try {
					if(!Control_Agent.equalsIgnoreCase("delete")) {
					AssignControllingAgent(driver, queryObjects);
					}
					if(Control_Agent.equalsIgnoreCase("delete")) {
						DeleteControllingAgent(driver, queryObjects);
					}
					//if (!sCheckinMsg.contains("WeighBalRest")&& !Control_Agent.equalsIgnoreCase("Y") && !Control_Agent.equalsIgnoreCase("delete")) {
					//Updated check condition  Himani
					if (!sCheckinMsg.contains("WeighBalRest")){
						//driver.findElement(By.xpath(FlightAct)).click();
						Thread.sleep(2000);
						if (sCheckinMsg.contains("OVSRestrict")) {
							driver.findElement(By.xpath(FlightAct)).click();
							Thread.sleep(2000);
							ISharesflow.FLIFO(driver, queryObjects, "WgtBalRestrict", sFlight, sFrom+sTo, ISharesflow.cTime, CbnCnt.substring(0, CbnCnt.indexOf(";")), CbnCnt.substring(CbnCnt.indexOf(";")+1, CbnCnt.length()));
						} else if(Restrict.equalsIgnoreCase("Y")){
							try {
								String oFlt=driver.findElement(By.xpath(flighNumXpath)).getText().trim();
								String odate=driver.findElement(By.xpath(flightdateXpath)).getText().trim();
								String oOrgin=driver.findElement(By.xpath(FlighOrginXpath+"[1]")).getText().trim();
								odate = odate.replace("-", "").toUpperCase();
							OpenFlight opn=new OpenFlight(oFlt,oOrgin,odate,driver,queryObjects);
							opn.run();					
						} catch (Exception e) {}							
							driver.findElement(By.xpath(FlightAct)).click();
							Thread.sleep(2000);

						    driver.findElement(By.xpath("//md-option[contains(@value,'restrict')]")).click();
							FlightSearch.loadhandling(driver);
							boolean inform= false;
							try {
								inform = driver.findElement(By.xpath("//span[contains(text(),'is restricted for Check-In')]")).isDisplayed();
							}catch (Exception e) {}
							if(inform) {
								queryObjects.logStatus(driver, Status.PASS, "Restrict flight", "Flight is retricted", null);
							}else
							{
								if(!added_agent && Negative_Case.equalsIgnoreCase("y"))

									queryObjects.logStatus(driver, Status.INFO, "Restrict flight", "Restriction was not done as agent not assigned", null);
								else
									queryObjects.logStatus(driver, Status.FAIL, "Restrict flight", "Restriction was not done", null); 
							}
						}
					}//Main close
				}catch (Exception e) {}

			}
			
			//ReleaseHeld Seats
			if (sCheckinMsg.contains("ReleaseHeld")) {
				driver.findElement(By.xpath("//div[contains(@translate,'pssgui.held.seats')]")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//button[contains(@translate,'pssgui.release.held')]")).click();
				FlightSearch.loadhandling(driver);
				boolean FligtOpenMsg= false;
				try{
					FligtOpenMsg=driver.findElement(By.xpath("//span[contains(text(),'SHIP NOT ASSIGNED')]")).isDisplayed();
				}
				catch(Exception e){}
				if(FligtOpenMsg && openflightflag==0)
				{
					OpenFlights_Checkin(driver, queryObjects);
				}
				if (driver.findElements(By.xpath("//span[contains(text(),'All held seats are released')]")).size()>0) {
					queryObjects.logStatus(driver, Status.PASS, "Release held seats", "All the held seats have been released", null);
				} else {
					queryObjects.logStatus(driver, Status.FAIL, "Release held seats", "Unable to release Held seats", null);
				}
			}
			//Release Seats
			if (sCheckinMsg.contains("Release_All")) {
				String heldno= driver.findElement(By.xpath("//div[contains(@security-validate,'ViewHeldSeatPassengers')]//div//div")).getText();
				if(!heldno.equals("0"))
				{	
					driver.findElement(By.xpath("//div[contains(@translate,'pssgui.held.seats')]")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//md-checkbox[contains(@aria-label,'pax-chk ')]//div")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//div//button[@type='button' and @aria-label='Release']")).click();
					FlightSearch.loadhandling(driver);
					if (driver.findElements(By.xpath("//span[contains(text(),'ALL  SEATS UNHELD')]")).size()>0) {
						queryObjects.logStatus(driver, Status.PASS, "Release held seats", "All the held seats have been released", null);
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Release held seats", "Unable to release Held seats", null);
					}
				}
			}
			
			//String Delete_API= FlightSearch.getTrimTdata(queryObjects.getTestData("Delete_API"));
			if (Delete_API.equalsIgnoreCase("yes")) {  // existing APi Deletion
				
				driver.findElement(By.xpath("//button[@translate='pssgui.delete']")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//button[contains(@class,'md-primary md-confirm-button')]")).click();
				FlightSearch.loadhandling(driver);
				
			}
			if((FlightSearch.getTrimTdata(queryObjects.getTestData("ASKHelp")).equalsIgnoreCase("yes")))  ///suman 12 june 2018
			{
				int i=1;

				String Xpath[]={"","","",};
				do
				{
					driver.findElement(By.xpath("//div/i[@class='icon-help']")).click();
					FlightSearch.loadhandling(driver);
					String PageName=driver.getCurrentUrl();

					List<WebElement> helpcategories=driver.findElements(By.xpath("//md-dialog//div[div[contains(text(),'Direct Reference System')]]//following-sibling::div//div[span[@translate='Categories']]//following::md-content/div"));

					int size=driver.findElements(By.xpath("//md-dialog//div[div[contains(text(),'Direct Reference System')]]//following-sibling::div//div[span[@translate='Categories']]//following::md-content/div")).size();
					int size1=driver.findElements(By.xpath("//md-dialog//div[div[contains(text(),'Direct Reference System')]]")).size();

					if(size >0 && size1 >0)
					{
						queryObjects.logStatus(driver, Status.PASS, "Checking help is available on multiple page ", "Help link is available and clickable on page url-->"+PageName, null); 
					}
					else
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking help is available on multiple page ", "Help link is not available and clickable on page url-->"+PageName, null);  
					}
					/*List<String> helpcategoriestext=new ArrayList<String>();
				 helpcategories.forEach(a->helpcategoriestext.add(a.getText().trim()));*/

					driver.findElement(By.xpath("//div[@ng-click='dlgCtrl.closeDialog()']/i[@class='icon-close']")).click();
					FlightSearch.loadhandling(driver);
					//xpath of select passanger checkbox
					if(i==1)
					{
						driver.findElement(By.xpath("//div[contains(@ng-repeat,'passengerItinerary')][1]//md-checkbox")).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//button[text()='Proceed to Check In']")).click();
						FlightSearch.loadhandling(driver);
					}

					i++;
				}while(i<3);

			}

			//Add Weight Balance Restriction
			if((WgtBalRestrict.equalsIgnoreCase("yes"))) {
				try {
					String oFlt=driver.findElement(By.xpath(flighNumXpath)).getText().trim();
					String odate=driver.findElement(By.xpath(flightdateXpath)).getText().trim();
					String oOrgin=driver.findElement(By.xpath(FlighOrginXpath+"[1]")).getText().trim();
					odate = odate.replace("-", "").toUpperCase();
				OpenFlight opn=new OpenFlight(oFlt,oOrgin,odate,driver,queryObjects);
				opn.run();					
			} catch (Exception e) {}
				Add_RemoveWeightBalRestriction(driver, queryObjects, CbnCnt, WgtBalRestrict);
			}
			
			//if((FlightSearch.getTrimTdata(queryObjects.getTestData("RemarksUpdate")).equalsIgnoreCase("yes"))) //11 june 2108 suman
			if((PNRRemarks.equalsIgnoreCase("yes"))) //11 june 2108 suman
			{
				driver.findElement(By.xpath(FlightAct)).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//md-option[contains(@value,'flightRemarks')]")).click();
				FlightSearch.loadhandling(driver);

				driver.findElement(By.xpath("//md-radio-button[@ng-repeat='type in allRemarks.model.remarksTypes'][2]/div[1]/div[1]")).click();


				driver.findElement(By.xpath("//button[text()='Add']")).click();

				//driver.findElement(By.xpath("//div/textarea[@name='remark']")).sendKeys("Automation Test Remarks");;;;;
				String Remarksflight="Automation Test Remarks";

				for (int i = 0; i < Remarksflight.length(); i++){

					char c = Remarksflight.charAt(i);
					String s = new StringBuilder().append(c).toString();
					driver.findElement(By.xpath("//div/textarea[@name='remark']")).click();
					driver.findElement(By.xpath("//div/textarea[@name='remark']")).sendKeys(s);

				}

				driver.findElement(By.xpath("//button[@aria-label='Ok']")).click();
				Thread.sleep(1000);
				FlightSearch.loadhandling(driver);
				Boolean bl= driver.findElements(By.xpath("//div/textarea[@name='remark']")).size()>0;
				if(driver.findElements(By.xpath("//table//tr[contains(@ng-repeat,'remark in remarklist')]//td[text()='AUTOMATION TEST REMARKS']")).size()>0 && !bl)
				{
					queryObjects.logStatus(driver, Status.PASS, " checking remarks Update", "Remarks Updated Successfully", null);
				}
				else
				{
					queryObjects.logStatus(driver, Status.FAIL, " checking remarks Update", "Remarks not Updated Successfully", null);
				}
			}
			if((FlightSearch.getTrimTdata(queryObjects.getTestData("MealReport")).equalsIgnoreCase("yes")))  //suman kumar 11 june 2018
			{
				driver.findElement(By.xpath(FlightAct)).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//md-option[contains(@value,'finalMealReport')]")).click(); 
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//md-dialog[//div[contains(text(),'Meal - Final Report')]]")).isDisplayed();

				if(driver.findElement(By.xpath("//md-dialog[//div[contains(text(),'Meal - Final Report')]]")).isDisplayed())
					queryObjects.logStatus(driver, Status.PASS, " checking Meal Report page", "checking Meal Report page diplayed", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "checking Meal Report page", "checking Meal Report page not displayed", null);


				if(driver.findElement(By.xpath("//md-dialog[//div[contains(text(),'Meal - Final Report')]]//div[contains(@class,'meal-report-header')]")).isDisplayed())
					queryObjects.logStatus(driver, Status.PASS, " checking Meal Report header page", "checking Meal Report header page diplayed", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "checking Meal Report header page", "checking Meal Report page header not displayed", null);

				if(driver.findElement(By.xpath("//md-dialog[//div[contains(text(),'Meal - Final Report')]]//div/div[contains(text(),'Estimated Meal Count')]//following-sibling::table")).isDisplayed())
					queryObjects.logStatus(driver, Status.PASS, " checking Meal Estimated Meal Count page", "checking Estimated Meal Count page diplayed", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "checking Meal Estimated Meal Count page", "checking Estimated Meal Count not displayed", null);	

				if(driver.findElement(By.xpath("//md-dialog[//div[contains(text(),'Meal - Final Report')]]//div/div[contains(text(),'Special Meal Requests')]//following-sibling::table")).isDisplayed())
				{
					if(driver.findElements(By.xpath(" //div[contains(text(),'Special Meal Requests')]//following-sibling::table//tbody/tr//td[contains(text(),'AUTOMATION/SELENIUM')]")).size()>0)
					{
						queryObjects.logStatus(driver, Status.PASS, " checking Special Meal Requests page FOR PASSAGER", "checking Special Meal Requests PASSANGER FOUND", null); 
					}
					else
					{
						queryObjects.logStatus(driver, Status.FAIL, " checking Special Meal Requests page FOR PASSAGER", "checking Special Meal Requests Passanger NOt found", null); 
					}
					queryObjects.logStatus(driver, Status.PASS, " checking Special Meal Requests page", "checking Special Meal Requests diplayed", null);
				}
				else
				{
					queryObjects.logStatus(driver, Status.FAIL, "checking Special Meal Requests page", "checking Special Meal Requests not displayed", null);	
				}

				if(driver.findElement(By.xpath("//md-dialog[//div[contains(text(),'Meal - Final Report')]]//div/div[contains(text(),'Priority Passengers')]//following-sibling::table")).isDisplayed())
					queryObjects.logStatus(driver, Status.PASS, " checking Priority Passengers Count page", "checking Priority Passengers page diplayed", null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "checking Meal Priority Passengers page", "checking Priority Passengers displayed", null);	

			}
			//new
			if((FlightSearch.getTrimTdata(queryObjects.getTestData("StandByList")).equalsIgnoreCase("yes")))
			{
				String CCbn = ""; String YCbn = ""; String SptPax[] = null;
				try
				{
					//select TktNotSyncStatus checkbox
					// driver.findElement(By.xpath(StanByButton)).click();

					String Oversoldpax="All Booked Standby Pax";   //suman
					driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).click();
					for (int i = 0; i < Oversoldpax.length(); i++){

						char c = Oversoldpax.charAt(i);
						String s = new StringBuilder().append(c).toString();
						driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).sendKeys(s);
					}
					driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).sendKeys(Keys.ENTER); 

					FlightSearch.loadhandling(driver);
					//List<WebElement> PaxName= driver.findElements(By.xpath(PAXListOfStandBy));
					try {
						driver.findElement(By.xpath("//div[@translate='pssgui.standby']/..")).click();
						FlightSearch.loadhandling(driver);
						FlightSearch.loadhandling(driver);
						FlightSearch.loadhandling(driver);
					} catch (Exception e) {}

					List<WebElement> PNR= driver.findElements(By.xpath("//div[contains(@ng-repeat,'passengerData in passengerItinerary')]/div/div[4]/div[1]/span"));
					
					List<String> StandbyPnr=new ArrayList<String>();
					
					PNR.forEach(a->StandbyPnr.add(a.getText().trim()));
					if(PNR.size() > 0) {
						if (BoardStatus.equalsIgnoreCase("HKNCnt")) {
							if (driver.findElements(By.xpath("//div[contains(@ng-hide,'hideBoardingPriority') and contains(text(),'HKN-SB')]")).size()>=(HKNCnt_C+HKNCnt_Y)) {
								queryObjects.logStatus(driver, Status.PASS, "Check the HKN cabin stanby passenger count matches with the counts displyed in passenger counts page", "HKN passenger count matches", null);
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Check the HKN cabin stanby passenger count matches with the counts displyed in passenger counts page", "HKN passenger count does not match", null);
							}
						}
						if (!TotStandby.isEmpty()) {
							if (!TotStandby.contains("-")) {
								TotStandby = TotStandby+"-";
							}
							SptPax = TotStandby.split("-");
							for (int sp = 0; sp < SptPax.length; sp++) {
								if (SptPax[sp].contains("y")) {
									if (driver.findElements(By.xpath("//div[contains(@class,'res-code') and contains(text(),'Y')]")).size()>=Integer.parseInt(SptPax[sp].substring(SptPax[sp].indexOf(";")+1, SptPax[sp].length()))) {
										queryObjects.logStatus(driver, Status.PASS, "Check the 'Y' cabin stanby passenger count ", "Required Y cabin Standby Passengers list displayed", null);
									} else {
										queryObjects.logStatus(driver, Status.FAIL, "Check the 'Y' cabin stanby passenger count ", "Data creation failed,Created Standby Passengers list is not displayed", null);
									}
								}
								if (SptPax[sp].contains("c")) {
									if (driver.findElements(By.xpath("//div[contains(@class,'res-code') and contains(text(),'C')]")).size()>=Integer.parseInt(SptPax[sp].substring(SptPax[sp].indexOf(";")+1, SptPax[sp].length()))) {
										queryObjects.logStatus(driver, Status.PASS, "Check the 'C' cabin stanby passenger count ", "Required C cabin Standby Passengers list displayed", null);
									} else {
										queryObjects.logStatus(driver, Status.FAIL, "Check the 'C' cabin stanby passenger count ", "Data creation failed, Created Standby Passengers list is not displayed", null);
									}
								}
							}
						}
						String PNR1= "";
						if (sOrderNum.contains(";")) {
							PNR1=sOrderNum;
						} else {
							PNR1=Unwholly.pnrfornrps;
							if(StandbyPnr.contains(PNR1)) {
								queryObjects.logStatus(driver, Status.PASS, "CHECKING eTKT of StandByFound ", "eTKT of StandByFound"+PNR1, null);	
							}
							else {
								queryObjects.logStatus(driver, Status.FAIL, "CHECKING eTKT of StandByFound ", "eTKT of not found StandByFound"+PNR1, null);
							}
						}
						//Check the Standby icon colour and checkin icon for the displayed pax
						for (int px = 1; px <= PNR.size(); px++) {
							if (!ColorsValidation(driver.findElement(By.xpath("(//div[contains(@ng-repeat,'passengerData in passengerItinerary')])["+px+"]//i[contains(@class,'icon-checked-in')]")).getCssValue("color")).equalsIgnoreCase("green")) {
								queryObjects.logStatus(driver, Status.INFO, "Check the PNR is checked in and checkin icon displays in green", "Check in icon is not displayed in green", null);
							}else if(px == PNR.size()) {
								queryObjects.logStatus(driver, Status.PASS, "Check the PNR is checked in and checkin icon displays in green", "Check in icon is displayed in green", null);
								if (!ColorsValidation(driver.findElement(By.xpath("(//div[contains(@ng-repeat,'passengerData in passengerItinerary')])["+px+"]//i[contains(@class,'icon-standby')]")).getCssValue("color")).equalsIgnoreCase("yellow")) {
									queryObjects.logStatus(driver, Status.FAIL, "Check the PNR is checked in and standy icon displays in yellow", "Standby icon is not displayed in yellow", null);
								}else if(px == PNR.size()) {
									queryObjects.logStatus(driver, Status.PASS, "Check the PNR is checked in and standy icon displays in yellow", "Standby icon is displayed in yellow", null);
								}
							}
							
						}
						if (sCheckinMsg.contains("InitiateStandby_Clear") || sCheckinMsg.contains("ClearAll_Standby") || sCheckinMsg.contains("InitiateStandby")) {
							String cPNR = "";
							//Check the Oversold list and Standby count matches
							if (Integer.parseInt(driver.findElement(By.xpath("//div[div[contains(text(),'Oversold')]]/div/div")).getText())>=Integer.parseInt(driver.findElement(By.xpath("//div[div[contains(text(),'Standby')]]/div/div")).getText())) {
								queryObjects.logStatus(driver, Status.PASS, "Check the Oversold and Standby passenger count", "Both the tab count matches", null);
								driver.findElement(By.xpath("(//md-checkbox[contains(@ng-model,'passengerData.chkBoxSelected')])[1]")).click();
								//Select a Passenger and Check the Initiate Standby status
								if (driver.findElements(By.xpath("//button[contains(text(),'Initiate Standby') and @disabled]")).size()>0) {
									queryObjects.logStatus(driver, Status.PASS, "Check the Initiate Standby button status when a passenger checkbox is selected", "Initiate Standby button is disabled", null);
								} else if (driver.findElements(By.xpath("//button[contains(text(),'Clear')]")).size()>0) {
									queryObjects.logStatus(driver, Status.INFO, "Check the Clear button is replace with Initiate Standby as the standby is already Initiated", "Clear Standby is button is displayed", null);
								} else {
									queryObjects.logStatus(driver, Status.FAIL, "Check the Initiate Standby button status when a passenger checkbox is selected", "Initiate Standby button is not disabled", null);
								}
								//DeSelect Passenger and Check the Initiate Standby status
								driver.findElement(By.xpath("(//md-checkbox[contains(@ng-model,'passengerData.chkBoxSelected')])[1]")).click();
								int PaxSta = driver.findElements(By.xpath("//span[contains(@ng-click,'viewOrder')]")).size();
								if (driver.findElements(By.xpath("//button[contains(text(),'Initiate Standby') and @disabled]")).size()==0 || driver.findElements(By.xpath("//button[contains(text(),'Clear')]")).size()>0) {
										queryObjects.logStatus(driver, Status.PASS, "Check the Initiate Standby button status when a passenger checkbox is not selected", "Initiate Standby button is enabled", null);
										try {
											driver.findElement(By.xpath("//button[contains(text(),'Initiate Standby')]")).click();
											FlightSearch.loadhandling(driver);
											String ColorVal = "";  String  Checkcolor = "Green";
											ColorVal = driver.findElement(By.xpath("//i[contains(@class,'icon-standby active-state')]")).getCssValue("color");
											if (Checkcolor.contains(ColorsValidation(ColorVal))) {
												queryObjects.logStatus(driver, Status.PASS, "Standby Initiated", "Standby Initiated icon GREEN", null);
					
											}
										} catch (Exception e) {}
										
									if (sCheckinMsg.contains("ClearAll_Standby")) {
										driver.findElement(By.xpath("//md-checkbox[contains(@ng-model,'airportPanel.model.checkAll ')]")).click();
										driver.findElement(By.xpath("//button[contains(@translate,'pssgui.clear')]")).click();
										FlightSearch.loadhandling(driver);
										if (driver.findElements(By.xpath("//span[contains(text(),'PROC INCMPLT-CMPT FULL-CK STDBY LIST')]")).size()>0) {
											queryObjects.logStatus(driver, Status.PASS, "Clear All Standby passengers", "'PROC INCMPLT-CMPT FULL-CK STDBY LIST' message is displayed", null);
										} else {
											queryObjects.logStatus(driver, Status.FAIL, "Clear All Standby passengers", "'PROC INCMPLT-CMPT FULL-CK STDBY LIST' message is not displayed", null);
										}
									} if(sCheckinMsg.contains("Clear")) {
										Add_RemoveWeightBalRestriction(driver, queryObjects,"", "Remove");
										//Navigate to Standby
										driver.findElement(By.xpath("//div[div[contains(text(),'Standby')]]/div/div")).click();
										FlightSearch.loadhandling(driver);
										for (int op = 1; op <= PaxSta; op++) {
											if (sOrderNum.contains(driver.findElement(By.xpath("(//span[contains(@ng-click,'viewOrder')])["+op+"]")).getText().trim())) {
												if (driver.findElement(By.xpath("(//div[contains(@ng-hide,'hideBoardingPriority')])["+op+"]")).getText().trim().contains("HKN-SB")) {
													driver.findElement(By.xpath("(//md-checkbox[contains(@ng-model,'passengerData.chkBox')])["+op+"]")).click();
													cPNR = driver.findElement(By.xpath("(//span[contains(@ng-click,'viewOrder')])["+op+"]")).getText().trim();
													driver.findElement(By.xpath("//button[contains(@translate,'pssgui.clear')]")).click();
													FlightSearch.loadhandling(driver);
													driver.findElement(By.xpath("//button[@ng-click='standbyPopup.closePopup()']")).click();
													FlightSearch.loadhandling(driver);
													if (driver.findElements(By.xpath("//span[contains(text(),'"+cPNR+"')]")).size()==0) {
														queryObjects.logStatus(driver, Status.PASS, "Clear the Standby button passenger", "Passenger is removed from the Stanby tab", null);
														FlightSearch.loadhandling(driver);
														//Check the PNR in oversold
														driver.findElement(By.xpath("//div[div[contains(text(),'Oversold')]]/div/div")).click();
														FlightSearch.loadhandling(driver);
														if (driver.findElements(By.xpath("//span[contains(text(),'"+cPNR+"')]")).size()>0) {
															queryObjects.logStatus(driver, Status.PASS, "Clear the Standby button passenger and the passenger in oversold tab", "Passenger is available in the Oversold tab", null);
															//Navigate to Standby
															driver.findElement(By.xpath("//div[div[contains(text(),'Standby')]]/div/div")).click();
															FlightSearch.loadhandling(driver);
															FlightSearch.loadhandling(driver);
														} else {
															queryObjects.logStatus(driver, Status.FAIL, "Clear the Standby button passenger and the passenger in oversold tab", "Passenger is not available in the Oversold tab", null);	
														}
													} else {
														queryObjects.logStatus(driver, Status.FAIL, "Clear the Standby button passenger", "Passenger is not removed from the Standby tab", null);	
													}
												}
											}
										}
									}
								} else {
									queryObjects.logStatus(driver, Status.FAIL, "Check the Initiate Standby button status when a passenger checkbox is de selected", "Initiate Standby button is not enabked", null);
								}
								try {
									driver.findElement(By.xpath("//button[@ng-click='standbyPopup.closePopup()']")).click();
								} catch (Exception e) {}
										
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Check the Oversold and Standby passenger count", "Oversold and Standby passenger count mismatch, verification failed", null);
							}
							
						}
						
					} else {
						queryObjects.logStatus(driver, Status.INFO, "CHECKING eTKT of StandByFound ", "No  eTKT of StandByFound", null);
					}
				}
				catch(Exception e)
				{
					queryObjects.logStatus(driver, Status.INFO, "CHECKING EXCEPTION while eTKT NOT SYNC ", "Exception got while syncing", e); 
				}
			}
			
			//Change the Voluntary Standby Passenger Status
			if (!VolSBAct.isEmpty() && VolSBAct.contains("ChangeStatus")) {
				String FrmStatus = "";
				String ToStatus = "";
				boolean SelOpt = false;
				String vPNR= "";
				if (!VolSel_FrmTo.isEmpty() && VolSel_FrmTo.contains("-")) {
					FrmStatus = VolSel_FrmTo.substring(0, VolSel_FrmTo.indexOf("-")).trim();
					ToStatus = VolSel_FrmTo.substring(VolSel_FrmTo.indexOf("-")+1).trim();
				} else {//Setting Default status
					FrmStatus = "VOL";
					ToStatus = "HKN";
				}
				driver.findElement(By.xpath("//div[div[contains(text(),'Oversold')]]/div/div")).click();
				FlightSearch.loadhandling(driver);
				int PaxSta = 0;
				if (VolSBAct.contains("ChangeStatus")) {
					//Check the PNR current status and Change Status
					PaxSta = driver.findElements(By.xpath("//span[contains(@ng-click,'viewOrder')]")).size();
					for (int op = 1; op <= PaxSta; op++) {
						if (sOrderNum.contains(driver.findElement(By.xpath("(//span[contains(@ng-click,'viewOrder')])["+op+"]")).getText().trim())) {
							if (driver.findElement(By.xpath("(//div[contains(@ng-hide,'hideBoardingPriority')])["+op+"]")).getText().trim().contains(FrmStatus+"-SB")) {
								if (VolSBAct.contains("WSeat")) {
									if (!driver.findElement(By.xpath("(//div[contains(@ng-repeat,'seat in')])["+op+"]//span[contains(@ng-if,'passengerData.chkBoxSelected')]")).getText().trim().contains("SB")) {
										SelOpt = true;
									}
								} else {
									if (driver.findElement(By.xpath("(//div[contains(@ng-repeat,'seat in')])["+op+"]//div[contains(@class,'res-code')]")).getText().trim().contains("Y")) {
										if (driver.findElement(By.xpath("(//div[contains(@ng-repeat,'seat in')])["+op+"]//span[contains(@ng-if,'passengerData.chkBoxSelected')]")).getText().trim().contains("SB")) {
											SelOpt = true;
										}
									}
								}
								if (SelOpt) {
									driver.findElement(By.xpath("(//md-checkbox[contains(@ng-model,'passengerData.chkBox')])["+op+"]")).click();
									vPNR = driver.findElement(By.xpath("(//span[contains(@ng-click,'viewOrder')])["+op+"]")).getText().trim();
									driver.findElement(By.xpath("//div[contains(@class,'hpe-pssgui airport-panel')]//button[@aria-label='Change Status']")).click();
									FlightSearch.loadhandling(driver);
									try {
										driver.findElement(By.xpath("//button[@ng-click='standbyPopup.closePopup()']")).click();
										FlightSearch.loadhandling(driver);
									} catch (Exception e) {}
									
									if (driver.findElement(By.xpath("//span[contains(text(),'"+vPNR+"')]/../../parent::div//div[contains(@ng-hide,'hideBoardingPriority')]")).getText().trim().contains(ToStatus+"-SB")) {
										queryObjects.logStatus(driver, Status.PASS, "Change the Voluntary Standby Pax Status ", "Passenger status is updated to HKN-SB", null);
									} else if (VolSBAct.contains("WSeat") && sCheckinMsg.contains("ErrorVolSBYtoHKN")) {
										if (driver.findElements(By.xpath("//span[contains(text(),'PASSENGER SEATED-DI ENTRY NOT ALLOWED ')]")).size()>0) {
											queryObjects.logStatus(driver, Status.PASS, "Change the Voluntary Standby Pax Status with Seat", "Unable to change Passenger status with VOL SB with Seat to HKN-SB, error message is displayed", null);
										}
										
									} else {
										queryObjects.logStatus(driver, Status.FAIL, "Change the Voluntary Standby Pax Status ", "Passenger status is not updated to HKN-SB", null);
									}
									break;
								}								
							}
						}
					}
				}
			}
			
			if (CancelStandby.toLowerCase().contains("yes")) {
				String Cbn = CancelStandby.substring(CancelStandby.indexOf("-")+1).toUpperCase();
				String PNR = "";
				driver.findElement(By.xpath("//div[@translate='pssgui.standby']/..")).click();
				FlightSearch.loadhandling(driver);
				FlightSearch.loadhandling(driver);
				FlightSearch.loadhandling(driver);
				try {
					driver.findElement(By.xpath("//button[contains(@aria-label,'Initiate standby')]")).click();
					FlightSearch.loadhandling(driver);
					FlightSearch.loadhandling(driver);
				} catch (Exception e) {}
				int StandbyCnt = driver.findElements(By.xpath("//span[contains(@ng-click,'viewOrder')]")).size();
				for (int sbc = 1; sbc <= StandbyCnt; sbc++) {
					try {
						if (driver.findElement(By.xpath("(//div[contains(@ng-repeat,'seat in')])["+(StandbyCnt-(sbc-1))+"]//div[contains(@class,'res-code')]")).getText().trim().contains(Cbn)) {
							PNR = driver.findElement(By.xpath("(//span[contains(@ng-click,'viewOrder')])["+(StandbyCnt-(sbc-1))+"]")).getText().trim();
							driver.findElement(By.xpath("(//md-checkbox[@aria-label='pax-chk'])["+(StandbyCnt-(sbc-1))+"]")).click();
							driver.findElement(By.xpath("//button[contains(text(),'Cancel Status')]")).click();
							FlightSearch.loadhandling(driver);
							if (driver.findElements(By.xpath("//div[contains(text(),'Passenger(s) removed from standby')]")).size()>0) {
								driver.findElement(By.xpath("//button[@ng-click='standbyPopup.closePopup()']")).click();
								queryObjects.logStatus(driver, Status.PASS, "Cancel the status for a StandBy PAX","Passenger status is updated" , null);
								FlightSearch.loadhandling(driver);
								FlightSearch.loadhandling(driver);
								//Navigate to All tab
								driver.findElement(By.xpath("//div[@translate='pssgui.all']")).click();
								FlightSearch.loadhandling(driver);
								FlightSearch.loadhandling(driver);
								if (!sCheckinMsg.contains("AllPax")) {
									if (sbc == StandbyCnt) {
										SelectFromPassengerList(driver, queryObjects,sOrderNum, "PNR", sOrderNum);
									}
								}else {
									SelectFromPassengerList(driver, queryObjects,PNR,"PNR", PNR);
								}
								isSelected = true;								
								if (sCheckinMsg.contains("SinglePax")) {									
									break;
								}								
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Cancel the status for a StandBy PAX","Unable to cancel the status for the standby Passenger" , null);
							}
						}
					} catch (Exception e) {
						queryObjects.logStatus(driver, Status.INFO, "Cancel the status for a StandBy PAX","Unable to cancel the status for the standby Passenger ORDER- "+PNR, null);
					}
					
				}				
			}
   
			if((FlightSearch.getTrimTdata(queryObjects.getTestData("SyncDisplay")).equalsIgnoreCase("yes")))
			{
				try
				{
					String SyncStatus=driver.findElement(By.xpath(TktNotSyncStatus)).getText();
					if(SyncStatus.equalsIgnoreCase("0"))
					{
						queryObjects.logStatus(driver, Status.INFO, "CHECKING eTKT NOT SYNC ", "Zero Number of eTKT not sync", null);

					}
					else
					{   //select TktNotSyncStatus checkbox
						driver.findElement(By.xpath(eTKTNotSyncButton)).click();
						List<WebElement> PaxName= driver.findElements(By.xpath(eTKTNotSyncPaxName));

						List<String> pnrnum=new ArrayList<String>();
						PaxName.forEach(a->pnrnum.add(a.getText().trim()));
						String PNR="";

						for (WebElement webElement : PaxName) {

							PNR=PNR+"->"+webElement.getText();

						}
						queryObjects.logStatus(driver, Status.INFO, "CHECKING eTKT NOT SYNC ", " eTKT not sync PNR are"+PNR, null);
						String SyncPnr=Login.PNRNUM;

						if(pnrnum.contains(SyncPnr))
						{
							queryObjects.logStatus(driver, Status.PASS, "CHECKING eTKT NOT SYNC ", " eTKT not sync PNR are UPDATED"+SyncPnr, null);
						}
						else
						{
							queryObjects.logStatus(driver, Status.FAIL, "CHECKING eTKT NOT SYNC ", " eTKT not sync PNR are NOT UPDATED"+SyncPnr, null);
						}


					}
				}
				catch(Exception e)
				{
					queryObjects.logStatus(driver, Status.FAIL, "CHECKING EXCEPTION while eTKT NOT SYNC ", "Exception got while syncing", e); 
				}


			}
			
			// etkt not sync written by rushil
			if(SyncTicket.equalsIgnoreCase("yes")) {
				driver.findElement(By.xpath(eTKTNotSyncButton)).click();
				FlightSearch.loadhandling(driver);
				if(SyncTktPaxName.equalsIgnoreCase("SharesName")) {
					SyncTktPaxName=ISharesflow.iPasName;
					SyncTktPaxName = SyncTktPaxName.toUpperCase();
				}
				driver.findElement(By.xpath("//md-select[@ng-model='menuCtrl.menuModel']/md-select-value/span[@class='md-select-icon']")).click();
				Thread.sleep(500);
				driver.findElement(By.xpath("//md-option[@ng-repeat='menuTbl in menuCtrl.menuItems']/div/div[contains(text(),'Name')]")).click();
				Thread.sleep(200);
				driver.findElement(By.xpath("//input[@ng-model='airportPassenger.model.searchText']")).sendKeys("1");
				driver.findElement(By.xpath("//input[@ng-model='airportPassenger.model.searchText']")).clear();
				driver.findElement(By.xpath("//input[@ng-model='airportPassenger.model.searchText']")).sendKeys(SyncTktPaxName);
				Thread.sleep(1000);
				if(SyncTktVerify.equalsIgnoreCase("yes")) {
					if(driver.findElements(By.xpath("//div[contains(@translate,'pssgui.no.passengers.found')]")).size()>0)
						queryObjects.logStatus(driver, Status.PASS, "Verify Ticket is sync for "+SyncTktPaxName+":", "Ticket is synced",null);
					else
						queryObjects.logStatus(driver, Status.FAIL, "Verify Ticket is sync for "+SyncTktPaxName+":", "Ticket is not synced",null);
					
					return;
				}
				driver.findElement(By.xpath(PaxDataChkBox)).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//button[contains(@translate,'pssgui.sync.eticket')]")).click();
				FlightSearch.loadhandling(driver);
				Unwholly.SyncTicket(driver, queryObjects, Synchronize,RequestType);
				
				return;
			}

			if((FlightSearch.getTrimTdata(queryObjects.getTestData("Outbound")).equalsIgnoreCase("yes")))
			{
				try
				{
					String Oversoldpax="outbound Connection pax";   //suman
					driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).click();
					for (int i = 0; i < Oversoldpax.length(); i++){

						char c = Oversoldpax.charAt(i);
						String s = new StringBuilder().append(c).toString();
						driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).sendKeys(s);
					}
					driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).sendKeys(Keys.ENTER); 

					FlightSearch.loadhandling(driver);

					List<WebElement> outboundWebElement=driver.findElements(By.xpath("//div[@class='passenger-list layout-row']/div[8]"));
					List<String>  outboundflight=new ArrayList<>();
					outboundWebElement.forEach(a->outboundflight.add(a.getText().trim()));
					String outboundflightlist="";
					if(outboundflight.isEmpty())
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking inbound pax flight", "There is no inbound pax, Create a pnr in connecting flight and check again in inbound", null);	
					}
					for (String paxname : outboundflight) {

						outboundflightlist=outboundflightlist+"->"+paxname;

					}
					queryObjects.logStatus(driver, Status.INFO, "Displaing all inbound pax flight", "inbound pax flight->"+outboundflightlist, null); 
				}
				catch(Exception e)
				{
					queryObjects.logStatus(driver, Status.FAIL, "Checking inbound pax flight", "Unable to serach inbound pax flight", e);  
				}

			}

			if((FlightSearch.getTrimTdata(queryObjects.getTestData("Inbound")).equalsIgnoreCase("yes")))  //suman 20 may  2018
			{
				try{    
					String Oversoldpax="Inbound Connection pax";   //suman
					driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).click();
					for (int i = 0; i < Oversoldpax.length(); i++){

						char c = Oversoldpax.charAt(i);
						String s = new StringBuilder().append(c).toString();
						driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).sendKeys(s);
					}
					driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).sendKeys(Keys.ENTER); 

					FlightSearch.loadhandling(driver);

					List<WebElement> inboundWebElement=driver.findElements(By.xpath("//div[span[contains(@security-validate,'ViewEMD-passengerItinerary')]]//following-sibling::div[1]/div"));
					List<String>  inboundflight=new ArrayList<>();
					inboundWebElement.forEach(a->inboundflight.add(a.getText().trim()));
					String inboundflightlist="";
					if(inboundflight.isEmpty())
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking inbound pax flight", "There is no inbound pax, Create a pnr in connecting flight and check again in inbound", null);	
					}
					for (String paxname : inboundflight) {

						inboundflightlist=inboundflightlist+"->"+paxname;

					}
					queryObjects.logStatus(driver, Status.INFO, "Displaing all inbound pax flight", "inbound pax flight->"+inboundflightlist, null); 
				}
				catch(Exception e)
				{
					queryObjects.logStatus(driver, Status.FAIL, "Checking inbound pax flight", "Unable to serach inbound pax flight", e);  
				}
			}
			//OpenFlight from Actions Dropdown
			if (OpnFlight.equalsIgnoreCase("yes")) {
				driver.findElement(By.xpath(FlightAct)).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//md-option[contains(@value,'open-flight')]")).click();
				FlightSearch.loadhandling(driver);
				if (driver.findElements(By.xpath("//span[contains(text(),'is open for Check-In')]")).size()>0) {
					queryObjects.logStatus(driver, Status.PASS, "Select Open Flight from Actions dropdown", "Flight is opened for Checkin", null);
				} else {
					queryObjects.logStatus(driver, Status.FAIL, "Select Open Flight from Actions dropdown", "Flight is not opened for Checkin", null);
				}
			}
			if (PaxCnts.toUpperCase().contains("YES")) {
				try {
					driver.findElement(By.xpath(FlightAct)).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//md-option[contains(@value,'passengerCount')]")).click();
					FlightSearch.loadhandling(driver);
					ACCapacity_C = driver.findElement(By.xpath("//td[contains(text(),'CAPACITY AIRCRAFT')]/following-sibling::td[1]")).getText().replace("*", "");
					ACCapacity_Y = driver.findElement(By.xpath("//td[contains(text(),'CAPACITY AIRCRAFT')]/following-sibling::td[contains(@ng-if,'Y')]")).getText().replace("*", "");
					/*SeatsRemain_C = driver.findElement(By.xpath("//td[contains(text(),'SEATS REMAINING')]/following-sibling::td[1]")).getText();
					SeatsRemain_Y = driver.findElement(By.xpath("//td[contains(text(),'SEATS REMAINING')]/following-sibling::td[contains(@ng-if,'Y')]")).getText();*/
					HKNCnt_Y = Integer.parseInt(driver.findElement(By.xpath("//td[contains(text(),'HKN W/O SEATS')]/following-sibling::td[contains(@ng-if,'Y')]")).getText());
					HKNCnt_C = Integer.parseInt(driver.findElement(By.xpath("//td[contains(text(),'HKN W/O SEATS')]/following-sibling::td[1]")).getText());
					BkdCbn_C = Integer.parseInt(driver.findElement(By.xpath("//td[contains(text(),'ETKT PSGRS C/I')]/following-sibling::td[1]")).getText());
					BkdCbn_Y = Integer.parseInt(driver.findElement(By.xpath("//td[contains(text(),'ETKT PSGRS C/I')]/following-sibling::td[contains(@ng-if,'Y')]")).getText());
					driver.findElement(By.xpath("//div[contains(@ng-click,'dlgCtrl.closeDialog')]/i[@class='icon-close']")).click();
				} catch (Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Aircraft Passenger Counts", "Unable to get the booked passenger counts", null);
				}
			}
			//new
			if (Reconcile.equalsIgnoreCase("Y")) {
				driver.findElement(By.xpath("//div[text()='Unreconciled']")).click();
				FlightSearch.loadhandling(driver);
				boolean blnpop = false;
				try {
					//driver.findElement(By.xpath("//div[text()='Initiate Boarding']")).click();
					//FlightSearch.loadhandling(driver);
					blnpop = driver.findElement(By.xpath("//div[text()='Initiate Boarding']")).isDisplayed();
				}catch(Exception e) {
					queryObjects.logStatus(driver, Status.PASS, "Checking Unreconcile tab", "Pop up was not displayed", null);
				}
				if (blnpop) {
					driver.findElement(By.xpath("//button[text()='Initiate Boarding']")).click();
					FlightSearch.loadhandling(driver);
					//handle error
					try {
						boolean blnerr = driver.findElement(By.xpath("//span[text()='Function restricted to controlling agents']")).isDisplayed();
						if (blnerr) {
							AssignControllingAgent(driver, queryObjects);
							driver.findElement(By.xpath("//div[text()='All']")).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath("//div[text()='Unreconciled']")).click();
							FlightSearch.loadhandling(driver);
							queryObjects.logStatus(driver, Status.PASS, "Updated reconcile", "Handling the control agent", null);

							boolean blnpop1 = false;
							try {
								blnpop1 = driver.findElement(By.xpath("//div[text()='Initiate Boarding']")).isDisplayed();
							}catch(Exception e) {
								queryObjects.logStatus(driver, Status.PASS, "Checking Unreconcile tab", "Pop up was not displayed", null);
							}
							if (blnpop1) {
								driver.findElement(By.xpath("//button[text()='Initiate Boarding']")).click();
								FlightSearch.loadhandling(driver);
							}
						}
					}catch(Exception e) {}
					
				}
				if (BoardOpt.equalsIgnoreCase("Abort")) {
					driver.findElement(By.xpath("//div[contains(@ng-click,'tabsCtrl.onTabNavigation')]/div[contains(text(),'Reconciled')] ")).click();
					int ReconcilePx = driver.findElements(By.xpath("//md-content//div[contains(@ng-repeat,'passengerItinerary.orderPassengers')]//div[contains(@class,'word-break')]/span")).size();
					driver.findElement(By.xpath("//button[contains(text(),'Abort')]")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//button[contains(text(),'Re-initiate boarding')]")).click();
					FlightSearch.loadhandling(driver);
					if (driver.findElements(By.xpath("//md-content//div[contains(@ng-repeat,'passengerItinerary.orderPassengers')]//div[contains(@class,'word-break')]/span")).size()==0 && ReconcilePx>0) {
						queryObjects.logStatus(driver, Status.PASS, "Perform Action Abort in Reconcile", "All the passengers have been moved from Reconciled to Unreconciled", null);
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Perform Action Abort in Reconcile", "Passengers are still in Reconciled tab", null);
					}
					
					//Check the Status
					driver.findElement(By.xpath("//div[contains(@ng-if,'tab.labels') and contains(text(),'Status')]")).click();
					FlightSearch.loadhandling(driver);
					
					//Check the Reconciled pax count in the status
					if (Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[2]/td[2]")).getText())==0) {
						queryObjects.logStatus(driver, Status.PASS, "Status tab - Check the Reconciled Economy cabin passengers count", "Passengers count matched", null);
						if (Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[1]/td[2]")).getText())==0) {
							queryObjects.logStatus(driver, Status.PASS, "Status tab - Check the Reconciled Business cabin passengers count", "Passengers count matched", null);
						} else {
							queryObjects.logStatus(driver, Status.FAIL, "Status tab - Check the Reconciled Business cabin passengers count", "Passengers count does not match or count is not updated", null);
						}
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Status tab - Check the Reconciled Economy cabin passengers count", "Passengers count does not match or count is not updated", null);
					}
					
					//Check the Checked In pax count in the status
					if (Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[1]/td[4]")).getText())>=BkdCbn_C) {
						queryObjects.logStatus(driver, Status.PASS, "Status tab - Check the Business cabin Checked In passengers count", "Passengers count matched", null);
						if (Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[2]/td[4]")).getText())>=BkdCbn_Y) {
							queryObjects.logStatus(driver, Status.PASS, "Status tab - Check the Economy cabin Checked In passengers count", "Passengers count matched", null);
						} else {
							queryObjects.logStatus(driver, Status.FAIL, "Status tab - Check the Economy cabin Checked In passengers count", "Passengers count does not match or count is not updated", null);
						}
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Status tab - Check the Business cabin Checked In passengers count", "Passengers count does not match or count is not updated", null);
					}
					/*
					//Check the Unreconciled pax count in the status
					if (Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[1]/td[4]")).getText())==BkdCbn_C) {
						queryObjects.logStatus(driver, Status.PASS, "Status tab - Check the Business cabin Unreconciled passengers count", "Passengers count matched", null);
						if (Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[2]/td[4]")).getText())==BkdCbn_Y) {
							queryObjects.logStatus(driver, Status.PASS, "Status tab - Check the Economy cabin Unreconciled passengers count", "Passengers count matched", null);
						} else {
							queryObjects.logStatus(driver, Status.FAIL, "Status tab - Check the Economy cabin Unreconciled passengers count", "Passengers count does not match or count is not updated", null);
						}
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Status tab - Check the Business cabin Unreconciled passengers count", "Passengers count does not match or count is not updated", null);
					}
					*/
					//Check the seat Map //Check the Not Boarded Passenger Counts
					List<WebElement> ChkdCnt = driver.findElements(By.xpath("//div[contains(@class,'cabin-type') and contains(text(),'Business')]/..//div[@class='seat']/div[contains(@class,'business-seat icn-not-boarded')]"));
					if (ChkdCnt.size()==BkdCbn_C) {
						queryObjects.logStatus(driver, Status.PASS, "Check the Not boarded passengers in business cabin matches with the Checked in passengers counts", "All Checked in passengers moved to Not boarded", null);
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Check the Not boarded passengers in business cabin matches with the Checked in passengers counts", "Checked in passengers are not moved to Not boarded", null);
					}
					ChkdCnt = driver.findElements(By.xpath("//div[contains(@class,'cabin-type') and contains(text(),'Economy')]/..//div[@class='seat']/div[contains(@class,'icn-not-boarded')]"));
					if (ChkdCnt.size()==BkdCbn_Y) {
						queryObjects.logStatus(driver, Status.PASS, "Check the Not boarded passengers in Economy cabin matches with the Checked in passengers counts", "All Checked in passengers moved to Not boarded", null);
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Check the Not boarded passengers in Economy cabin matches with the Checked in passengers counts", "Checked in passengers are not moved to Not boarded", null);
					}
					
				} else if (BoardOpt.equalsIgnoreCase("Close")) {
					driver.findElement(By.xpath("//button[contains(text(),'Close Boarding')]")).click();
				} else if (BoardOpt.equalsIgnoreCase("Reopen")) {
					driver.findElement(By.xpath("//button[contains(text(),'Reopen Boarding')]")).click();
				}
				if(sUnReconcile.equalsIgnoreCase("Y"))
				{
					try
					{
						driver.findElement(By.xpath("//div[@ng-if='tab.labels' and contains(text(),'Unreconciled')]")).click();//Select Unreconcile Tab
						List<WebElement> unconcilePaxList=driver.findElements(By.xpath(unconcilePassanger));
						List<String> unconcilePaxPNR=new ArrayList<>();
						unconcilePaxList.forEach(a->unconcilePaxPNR.add(a.getText().trim()));
						String unconcilePax="";
						for(int i=0;i<unconcilePaxList.size();i++)
						{
							unconcilePax=unconcilePax+"->"+unconcilePaxPNR.get(i);
						}
						if(unconcilePaxPNR.size()<0)
							queryObjects.logStatus(driver, Status.FAIL, "Checking Unreconcile Passanger list", " There is no Unreconcile Passanger to display",null);	
						else {
							queryObjects.logStatus(driver, Status.PASS, "Checking Unreconcile Passanger list", "Unreconcile Passanger are"+unconcilePax+"",null);
							if (sCheckinMsg.contains("NotBoarded_C")) {
								if(driver.findElements(By.xpath("//pssgui-passenger-itinerary//div[contains(@class,'table-alt-row')]//div[contains(@class,'seat-cabin') and contains(text(),'C')]")).size()>=BkdCbn_C) {
									queryObjects.logStatus(driver, Status.PASS, "Checking Unreconcile Passanger list with the booked business cabin count", "Verification sucessful", null);
								} else {
									queryObjects.logStatus(driver, Status.FAIL, "Checking Unreconcile Passanger list with the booked business cabin count", "Verification failed", null);
								}
							} 
						}
					}
					catch(Exception e)
					{
						queryObjects.logStatus(driver, Status.PASS, "Checking Unreconcile Passanger list", "Unable to display the Unreconcile Passanger list", e);	
					}
					//String sReconcilePAX=FlightSearch.getTrimTdata(queryObjects.getTestData("ReconcilePAX"));
					if(sReconcilePAX.equalsIgnoreCase("y"))
					{
						String Paxname= driver.findElement(By.xpath("//md-content//div[contains(@ng-repeat,'passengerItinerary.orderPassengers')][1]//div[contains(@class,'word-break')]/span")).getText().trim();
						driver.findElement(By.xpath("//div[contains(@ng-repeat,'passengerItinerary.orderPassengers')][1]//md-checkbox[contains(@ng-model,'passengerData.chkBoxSelected')]")).click(); 
						queryObjects.logStatus(driver, Status.PASS, "Select first pax", "First pax got selected", null);
						driver.findElement(By.xpath("//button[text()='Reconcile']")).click();

						FlightSearch.loadhandling(driver);
						queryObjects.logStatus(driver, Status.PASS, "Reconcile done for  first pax", "Reconcile first pax", null);
						// driver.findElement(By.xpath("//button[text()='OK']")).click();

						driver.findElement(By.xpath("//div[contains(@ng-click,'tabsCtrl.onTabNavigation')]/div[contains(text(),'Reconciled')] ")).click();

						List<WebElement> reconcilePaxList=driver.findElements(By.xpath("//md-content//div[contains(@ng-repeat,'passengerItinerary.orderPassengers')]//div[contains(@class,'word-break')]/span"));
						List<String> concilePaxName=new ArrayList<>();
						reconcilePaxList.forEach(a->concilePaxName.add(a.getText().trim()));

						if(concilePaxName.contains(Paxname)) {
							queryObjects.logStatus(driver, Status.PASS, "Checking Reconcile done for Passanger", "Reconcile completed fo pax---->"+Paxname, null);  
						} else {
							queryObjects.logStatus(driver, Status.FAIL, "Checking Reconcile done for Passanger", "Reconcile has not done for pax", null);  
						}						
					} else if ((sReconcilePAX.toUpperCase().contains("YES") && sReconcilePAX.contains("-")) || sReconcilePAX.toUpperCase().contains("InputSeq")) {
						String SeqList = ""; String Paxname = "";					
						Reconcilepx = Integer.parseInt(sReconcilePAX.substring(sReconcilePAX.indexOf("-")+1));
						if (sReconcilePAX.toUpperCase().contains("InputSeq")) {
							driver.findElement(By.xpath("//input[@name='Seq']")).sendKeys("2,3,5,7,8");
							SeqList = ";"+"2;3;5;7;8";
							Paxname= driver.findElement(By.xpath("//md-content//div[contains(@ng-repeat,'passengerItinerary.orderPassengers')][2]//div[contains(@class,'word-break')]/span")).getText().trim();
						} else {
							Paxname= driver.findElement(By.xpath("//md-content//div[contains(@ng-repeat,'passengerItinerary.orderPassengers')][1]//div[contains(@class,'word-break')]/span")).getText().trim();
							for (int rp = 1; rp <= driver.findElements(By.xpath("//div[contains(@ng-repeat,'passengerItinerary.orderPassengers')]")).size(); rp++) {
								if (driver.findElement(By.xpath("//div[contains(@ng-repeat,'passengerItinerary.orderPassengers')]["+rp+"]//div[contains(@ng-if,'nonStandardTable.Cabin')]/div")).getText().contains("C")) {
									driver.findElement(By.xpath("//div[contains(@ng-repeat,'passengerItinerary.orderPassengers')]["+rp+"]//md-checkbox[contains(@ng-model,'passengerData.chkBoxSelected')]")).click();
									SeqList = SeqList+";"+driver.findElement(By.xpath("//div[contains(@ng-repeat,'passengerItinerary.orderPassengers')]["+rp+"]//div[contains(@ng-if,'Table.seq')]/div")).getText().trim();
								}							
							}
						}					
						
						driver.findElement(By.xpath("//button[text()='Reconcile']")).click();
						FlightSearch.loadhandling(driver);
						if (driver.findElements(By.xpath("//div[contains(text(),'OK TO BOARD')]")).size()>0) {
							driver.findElement(By.xpath("//button[text()='OK']")).click();
							FlightSearch.loadhandling(driver);
							queryObjects.logStatus(driver, Status.PASS, "Select Reconcile for "+Reconcilepx+" passengers", "Reconcile done successfully", null);
							
							//Check the Reconciled passengers list
							driver.findElement(By.xpath("//div[contains(@ng-click,'tabsCtrl.onTabNavigation')]/div[contains(text(),'Reconciled')] ")).click();
							List<WebElement> reconcilePaxList=driver.findElements(By.xpath("//md-content//div[contains(@ng-repeat,'passengerItinerary.orderPassengers')]//div[contains(@class,'word-break')]/span"));
							List<String> concilePaxName=new ArrayList<>();
							reconcilePaxList.forEach(a->concilePaxName.add(a.getText().trim()));
							if(concilePaxName.contains(Paxname)) {
								queryObjects.logStatus(driver, Status.PASS, "Checking Reconcile done for Passanger", "Reconcile completed fo pax---->"+Paxname, null);  
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Checking Reconcile done for Passanger", "Reconcile has not done for pax", null);  
							}
							for (int sl = 1; sl < SeqList.split(";").length; sl++) {
								for (int rl = 1; rl <= reconcilePaxList.size(); rl++) {
									if (driver.findElement(By.xpath("//div[contains(@ng-repeat,'passengerItinerary.orderPassengers')]["+rl+"]//div[contains(@ng-if,'Table.seq')]/div")).getText().trim().contains(SeqList.split(";")[sl])) {
										queryObjects.logStatus(driver, Status.PASS, "Check the Reconcile is done for all the Passangers", "Reconcile completed for all the selected passengers", null);
										break;
									}
								}
							}
						}
					}
					//Check the Status
					driver.findElement(By.xpath("//div[contains(@ng-if,'tab.labels') and contains(text(),'Status')]")).click();
					FlightSearch.loadhandling(driver);
					
					//Check the Reconciled pax count in the status
					if ((Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[2]/td[2]")).getText().trim())+Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[1]/td[2]")).getText().trim()))>=Reconcilepx) {
						queryObjects.logStatus(driver, Status.PASS, "Status tab - Check the total Reconciled passengers count in status matches with the reconciled passengers", "Passengers count matched", null);
						//Check the Cabinwise count
						if (Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[1]/td[2]")).getText().trim())==(Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[1]/td[4]")).getText().trim())-Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[1]/td[3]")).getText().trim()))) {
							queryObjects.logStatus(driver, Status.PASS, "Status tab - Check the Business cabin Reconciled passengers count with the reconciled pax", "Passengers count does not match or count is not updated", null);
							if (Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[2]/td[2]")).getText().trim())==(Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[2]/td[4]")).getText().trim())-Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[2]/td[3]")).getText().trim()))) {
								queryObjects.logStatus(driver, Status.PASS, "Status tab - Check the Economy cabin Reconciled passengers count with the reconciled pax", "Passengers count does not match or count is not updated", null);
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Status tab - Check the Economy cabin Reconciled passengers count with the reconciled pax", "Passengers count does not match or count is not updated", null);
							}
						} else {
							queryObjects.logStatus(driver, Status.FAIL, "Status tab - Check the Business cabin Reconciled passengers count with the reconciled pax", "Passengers count does not match or count is not updated", null);
						}
								
					} else {
						queryObjects.logStatus(driver, Status.INFO, "Status tab - Check the total Reconciled passengers count in status matches with the reconciled passengers", "Passengers count does not match or count is not updated", null);
					}
					
					//Check the Checked In pax count in the status
					if (Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[1]/td[4]")).getText().trim())==BkdCbn_C) {
						queryObjects.logStatus(driver, Status.PASS, "Status tab - Check the Business cabin Checked In passengers count", "Passengers count matched", null);
						if (Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[2]/td[4]")).getText().trim())==BkdCbn_Y) {
							queryObjects.logStatus(driver, Status.PASS, "Status tab - Check the Economy cabin Checked In passengers count", "Passengers count matched", null);
						} else {
							queryObjects.logStatus(driver, Status.FAIL, "Status tab - Check the Economy cabin Checked In passengers count", "Passengers count does not match or count is not updated", null);
						}
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Status tab - Check the Business cabin Checked In passengers count", "Passengers count does not match or count is not updated", null);
					}
					
					//Check the Unreconciled pax count in the status
					if (Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[1]/td[3]")).getText().trim())==(BkdCbn_C-Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[1]/td[2]")).getText().trim()))) {
						queryObjects.logStatus(driver, Status.PASS, "Status tab - Check the Business cabin Unreconciled passengers count", "Passengers count matched", null);
						if (Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[2]/td[3]")).getText().trim())==(BkdCbn_Y-Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[2]/td[2]")).getText().trim()))) {
							queryObjects.logStatus(driver, Status.PASS, "Status tab - Check the Economy cabin Unreconciled passengers count", "Passengers count matched", null);
						} else {
							queryObjects.logStatus(driver, Status.FAIL, "Status tab - Check the Economy cabin Unreconciled passengers count", "Passengers count does not match or count is not updated", null);
						}
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Status tab - Check the Business cabin Unreconciled passengers count", "Passengers count does not match or count is not updated", null);
					}
				} else {//Reconcile All Passengers
					driver.findElement(By.xpath("//button[text()='Reconcile']")).click();
					FlightSearch.loadhandling(driver);
					Reconcilepx = driver.findElements(By.xpath("//md-content//div[contains(@ng-repeat,'passengerItinerary.orderPassengers')]//div[contains(@class,'word-break')]/span")).size();
					driver.findElement(By.xpath("//md-checkbox[contains(@ng-model,'checkAll')]")).click();
					queryObjects.logStatus(driver, Status.PASS, "Check All", "All seq checked", null);
					driver.findElement(By.xpath("//button[text()='Reconcile']")).click();

					FlightSearch.loadhandling(driver);
					queryObjects.logStatus(driver, Status.PASS, "Reconcile done for  All", "All seq checked", null);
					try{
						driver.findElement(By.xpath("//button[text()='OK']")).click();
						FlightSearch.loadhandling(driver);						
					}catch(Exception e ) {}
					
					//Check Status - All passenger in Reconcile
					//Check the Status
					driver.findElement(By.xpath("//div[contains(@ng-if,'tab.labels') and contains(text(),'Status')]")).click();
					FlightSearch.loadhandling(driver);
					
					//Check the Reconciled pax count in the status
					if (Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[1]/td[2]")).getText().trim())==BkdCbn_C) {
						queryObjects.logStatus(driver, Status.PASS, "Status tab - Check the Reconciled Economy cabinpassengers count", "Passengers count matched", null);
						if (Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[2]/td[2]")).getText().trim())==BkdCbn_Y) {
							queryObjects.logStatus(driver, Status.PASS, "Status tab - Check the Reconciled Business cabin passengers count", "Passengers count matched", null);
						} else {
							queryObjects.logStatus(driver, Status.FAIL, "Status tab - Check the Reconciled Business cabin passengers count", "Passengers count does not match or count is not updated", null);
						}
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Status tab - Check the Reconciled Economy cabin passengers count", "Passengers count does not match or count is not updated", null);
					}
					
					//Check the Checked In pax count in the status
					if (Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[1]/td[4]")).getText().trim())==BkdCbn_C) {
						queryObjects.logStatus(driver, Status.PASS, "Status tab - Check the Business cabin Checked In passengers count", "Passengers count matched", null);
						if (Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[2]/td[4]")).getText().trim())==BkdCbn_Y) {
							queryObjects.logStatus(driver, Status.PASS, "Status tab - Check the Economy cabin Checked In passengers count", "Passengers count matched", null);
						} else {
							queryObjects.logStatus(driver, Status.FAIL, "Status tab - Check the Economy cabin Checked In passengers count", "Passengers count does not match or count is not updated", null);
						}
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Status tab - Check the Business cabin Checked In passengers count", "Passengers count does not match or count is not updated", null);
					}
					
					//Check the Unreconciled pax count in the status
					if (Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[1]/td[3]")).getText().trim())==0) {
						queryObjects.logStatus(driver, Status.PASS, "Status tab - Check the Business cabin Unreconciled passengers count", "Passengers count matched", null);
						if (Integer.parseInt(driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'unreconciledStatus')])[2]/td[3]")).getText().trim())==0) {
							queryObjects.logStatus(driver, Status.PASS, "Status tab - Check the Economy cabin Unreconciled passengers count", "Passengers count matched", null);
						} else {
							queryObjects.logStatus(driver, Status.FAIL, "Status tab - Check the Economy cabin Unreconciled passengers count", "Passengers count does not match or count is not updated", null);
						}
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Status tab - Check the Business cabin Unreconciled passengers count", "Passengers count does not match or count is not updated", null);
					}
					
					//Check the seat Map //Check the Not Boarded Passenger Counts
					List<WebElement> ChkdCnt = driver.findElements(By.xpath("//div[contains(@class,'cabin-type') and contains(text(),'Business')]/..//div[@class='seat']/div[contains(@class,'business-seat icn-not-boarded')]"));
					if (ChkdCnt.size()==0) {
						queryObjects.logStatus(driver, Status.PASS, "Check the Not boarded passengers in business cabin matches with the Checked in passengers counts", "All Checked in passengers moved to Not boarded", null);
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Check the Not boarded passengers in business cabin matches with the Checked in passengers counts", "Checked in passengers are not moved to Not boarded", null);
					}
					ChkdCnt = driver.findElements(By.xpath("//div[contains(@class,'cabin-type') and contains(text(),'Economy')]/..//div[@class='seat']/div[contains(@class,'business-seat icn-not-boarded')]"));
					if (ChkdCnt.size()==0) {
						queryObjects.logStatus(driver, Status.PASS, "Check the Not boarded passengers in Economy cabin matches with the Checked in passengers counts", "All Checked in passengers moved to Not boarded", null);
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Check the Not boarded passengers in Economy cabin matches with the Checked in passengers counts", "Checked in passengers are not moved to Not boarded", null);
					}					
					
					//Check the Boarded Seta Status
					ChkdCnt = driver.findElements(By.xpath("//div[contains(@class,'cabin-type') and contains(text(),'Business')]/..//div[@class='seat']/div[contains(@class,'icn-boarded')]"));
					if (ChkdCnt.size()==BkdCbn_C) {
						queryObjects.logStatus(driver, Status.PASS, "Check All the Business cabin Reconciled passengers are moved to boarded list", "All the Reconciled passengers are moved to boarded list", null);
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Check All the Business cabin Reconciled passengers are moved to boarded list", "Reconciled passengers are not moved to boarded list", null);
					}
					ChkdCnt = driver.findElements(By.xpath("//div[contains(@class,'cabin-type') and contains(text(),'Economy')]/..//div[@class='seat']/div[contains(@class,'icn-boarded')]"));
					if (ChkdCnt.size()==BkdCbn_Y) {
						queryObjects.logStatus(driver, Status.PASS, "Check All the Economy cabin Reconciled passengers are moved to boarded list", "All the Reconciled passengers are moved to boarded list", null);
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Check All the Economy cabin Reconciled passengers are moved to boarded list", "Reconciled passengers are not moved to boarded list", null);
					}
						
				}
				if(!Filter_Name.isEmpty()) {
					try{
										
						Filter_Val=ISharesflow.iPNRNo; 
						driver.findElement(By.xpath("//div[@translate='pssgui.all']")).click();
						FlightSearch.loadhandling(driver);
						SelectFromPassengerList( driver,  queryObjects,Filter_Val,Filter_Name, Filter_Val);	
						queryObjects.logStatus(driver, Status.PASS, "Search by PNR", "PNR Retrieved", null);
						Thread.sleep(100);
						driver.findElement(By.xpath("//button[@translate='pssgui.proceed.to.check.in']")).click();
						FlightSearch.loadhandling(driver);
						Boolean adcmsg=false;
						
						adcmsg= driver.findElement(By.xpath("//div[@translate='pssgui.all']")).isDisplayed();
						if(adcmsg)
							queryObjects.logStatus(driver, Status.PASS, "Reconciled Pax", "APIS appear complete", null);  
						else
							queryObjects.logStatus(driver, Status.FAIL, "Reconciled Pax\"", "APIS is not complete", null);  

					return;		
					}catch(Exception e ) {}
				}
			}
			if(Release_ASA.equalsIgnoreCase("Y"))
			{
				driver.findElement(By.xpath("//div[contains(@translate,'pssgui.held.seats')]")).click();
				FlightSearch.loadhandling(driver);
				//Himani 22March
				
				driver.findElement(By.xpath("//button[@aria-label='Release ASA']")).click();
				FlightSearch.loadhandling(driver);
				FlightSearch.loadhandling(driver);
				boolean inform= false;
				try {
					inform = driver.findElement(By.xpath("//span[contains(text(),'advanced seats are released')][1]")).isDisplayed();
				}catch (Exception e) {}
				if(inform) {
					queryObjects.logStatus(driver, Status.PASS, "Release ASA", "All advanced seats unheld", null);								
				}else
				{
					if(driver.findElements(By.xpath("//span[@class='ng-binding msg-error' and contains(text(),'restricted to controlling agents')]")).size()>0)
						queryObjects.logStatus(driver,Status.INFO, "Release ASA", "Function restricted to a controlling agent or a supervisor", null);									
					else
						queryObjects.logStatus(driver,Status.FAIL, "Release ASA", "Failed to unhold advanced seats", null);
				}			
				if(Control_Agent.equalsIgnoreCase("Y"))
				{
					AssignControllingAgent(driver, queryObjects);
				}
			}
			
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("Held_Seats")).equalsIgnoreCase("yes"))
			{
				driver.findElement(By.xpath("//div[contains(@translate,'pssgui.held.seats')]")).click();

				FlightSearch.loadhandling(driver);

				List<WebElement> hldseat=driver.findElements(By.xpath("//div[i[@class='icon-seatmap']]//descendant::span"));
				List<String> hldseatno=new ArrayList<>();
				hldseat.forEach(a->hldseatno.add(a.getText().trim()));
				String seatOccupied="";
				for(String value : hldseatno)
				{
					if(value.equals(""))
					{

						queryObjects.logStatus(driver, Status.FAIL, "Checking All held seat passanger", "All passanger are not got seat on hwld tab", null);  

						break;
					}
					seatOccupied=seatOccupied+"-->"+value;
				}
				queryObjects.logStatus(driver, Status.PASS, "Checking All held seat passanger", "All passanger got seat on hwld tab"+seatOccupied, null);
			}

			if(FlightSearch.getTrimTdata(queryObjects.getTestData("OverSold")).equalsIgnoreCase("yes"))  //suman 28 may
			{
				AddVolStandBy(driver, queryObjects, "", VolSB);
			}
			
			if (BoardStatus.contains("NotBoarded_C")) {
				//Check the Not Boarded Passenger Counts
				List<WebElement> ChkdCnt = driver.findElements(By.xpath("//div[contains(@class,'cabin-type') and contains(text(),'Business')]/..//div[@class='seat']/div[contains(@class,'business-seat icn-not-boarded')]"));
				if (ChkdCnt.size()==BkdCbn_C) {
					queryObjects.logStatus(driver, Status.PASS, "Check the Not boarded passengers in business cabin matches with the Checked in passengers counts", "All Checked in passengers moved to Not boarded", null);
				} else {
					queryObjects.logStatus(driver, Status.FAIL, "Check the Not boarded passengers in business cabin matches with the Checked in passengers counts", "Checked in passengers are not moved to Not boarded", null);
				}
				
				/*for (int cc = 1; cc <= ChkdCnt.size(); cc++) {
				if (ColorsValidation(ChkdCnt.get(cc).getCssValue("color")).contains("red")) {}
				
			}*/
			}
			
			if (BoardStatus.contains("ReconciledPax")) {
				//Check the Boarded Passenger Counts
				List<WebElement> ChkdCnt = driver.findElements(By.xpath("//div[@class='seat']/div[contains(@class,'icn-boarded')]"));
				try {
					if (ChkdCnt.size()>=Integer.parseInt(sReconcilePAX.substring(sReconcilePAX.indexOf("-")+1))) {
						queryObjects.logStatus(driver, Status.PASS, "Check the Reconciled passengers are moved to boarded list", "All the Reconciled passengers are moved to boarded list", null);
					} else {
						//queryObjects.logStatus(driver, Status.FAIL, "Check the Reconciled passengers are moved to boarded list", "Reconciled passengers are not moved to boarded list", null);
					}
				} catch (Exception e) {
					if (ChkdCnt.size()>=Reconcilepx) {
						queryObjects.logStatus(driver, Status.PASS, "Check the Reconciled passengers are moved to boarded list", "All the Reconciled passengers are moved to boarded list", null);
					} else {
						//queryObjects.logStatus(driver, Status.FAIL, "Check the Reconciled passengers are moved to boarded list", "Reconciled passengers are not moved to boarded list", null);
					}
				}
				//Check the Not Boarded Pax count
				if (driver.findElements(By.xpath("//div[@class='seat']/div[contains(@class,'business-seat icn-not-boarded')]")).size()+driver.findElements(By.xpath("//div[@class='seat']/div[contains(@class,'icn-boarded')]")).size()==BkdCbn_C+BkdCbn_Y) {
					queryObjects.logStatus(driver, Status.PASS, "Check the total Checked in passengers counts with boarded and not boarded passengers", "Count MAtches", null);
				} else {
					queryObjects.logStatus(driver, Status.INFO, "Check the total Checked in passengers counts with boarded and not boarded passengers", "Checked in passengers count does not match", null);
				}
				/*for (int cc = 1; cc <= ChkdCnt.size(); cc++) {
					if (ColorsValidation(ChkdCnt.get(cc).getCssValue("color")).contains("red")) {}
					
				}*/
			}
			/*if (sCheckinMsg.contains("DepartureTime_Red")) {
				String DepartTime = driver.findElement(By.xpath("//div[@class='hpe-pssgui date-time']/div")).getText();
				if (Integer.parseInt(DepartTime.substring(0, DepartTime.indexOf(";")))<15) {
					if (ColorsValidation(driver.findElement(By.xpath("//div[@class='hpe-pssgui date-time']/div")).getCssValue("color")).contains("Red")) {
						queryObjects.logStatus(driver, Status.PASS, "Check the Departure time is displayed in Red when it has less than 15 mins to board", "Departure time is displayed in RED", null);
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Check the Departure time is displayed in Red when it has less than 15 mins to board", "Departure time is not displayed in RED", null);
					}
				} else {
					queryObjects.logStatus(driver, Status.INFO, "Departure time is not yet close to the boarding time", "Unable to validate Departure time color display in RED when it is close to board", null);
					if (ColorsValidation(driver.findElement(By.xpath("//div[@class='hpe-pssgui date-time']/div")).getCssValue("color")).contains("Green")) {
						queryObjects.logStatus(driver, Status.PASS, "Check the Departure time is displayed in Green when it has more than 15 mins to board", "Departure time is displayed in GREEN", null);
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Check the Departure time is displayed in Green when it has more than 15 mins to board", "Departure time is not displayed in GREEN", null);
					}
				}
			}*/
			
			
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("SharedCheckinNegativeScan")).equalsIgnoreCase("yes"))  //suman 13june
			{	 
				Boolean IsEnabled = driver.findElement(By.xpath(SurnameXpath1)).isEnabled();
				Boolean IsEnabledFirstnameXpath = driver.findElement(By.xpath(FirstnameXpath)).isEnabled();
				Boolean IsEnabledCOR = driver.findElement(By.xpath(BirthDateXpath)).isEnabled();
				//Boolean IsEnabledBirthDateXpath = driver.findElement(By.xpath(DocTypeXpath)).isEnabled();
				Boolean IsEnabledDocTypeXpath = driver.findElement(By.xpath(DocIDXpath)).isEnabled();
				Boolean IsEnabledCOIXpath = driver.findElement(By.xpath(COIXpath)).isEnabled();
				Boolean IsEnabledNtionalXpath = driver.findElement(By.xpath(NtionalXpath)).isEnabled();
				Boolean IsEnabledCORXpath = driver.findElement(By.xpath(CORXpath)).isEnabled();

				if(IsEnabled || IsEnabledFirstnameXpath ||  IsEnabledCOR || IsEnabledDocTypeXpath || IsEnabledCOIXpath || IsEnabledNtionalXpath || IsEnabledCORXpath)
				{
					queryObjects.logStatus(driver, Status.FAIL, "Checking Apis Detail is Disable", "All APIS detail NOT is disable",null); 	
				}
				else
				{
					queryObjects.logStatus(driver, Status.PASS, "Checking Apis Detail is Disable", "All APIS detail is disable",null);
					//queryObjects.logStatus(driver, Status.FAIL, "Checking Apis Detail is Disable", "All APIS detail NOT is disable",null); 
				}

			}
			
			//To Remove the weight Balance restriction
			if((WgtBalRestrict.equalsIgnoreCase("Remove"))) {
				Add_RemoveWeightBalRestriction(driver, queryObjects,"", WgtBalRestrict);
			}
			
			//Close Flight - Without Performing Checkin
			//Close Flight
			if (CloseFl.equalsIgnoreCase("CloseFlight")) {
                try {
                	 FlightSearch.loadhandling(driver);
                	driver.findElement(By.xpath(FlightAct)).click();
                	FlightSearch.loadhandling(driver);
                	FlightSearch.loadhandling(driver);
                    driver.findElement(By.xpath("//md-option[@value='close-flight']")).click();
                    FlightSearch.loadhandling(driver);
                    FlightSearch.loadhandling(driver);
                    driver.findElement(By.xpath("//button[contains(@ng-click,'closeFlight') and contains(text(),'Yes')]")).click();
                    FlightSearch.loadhandling(driver);
                    if (driver.findElements(By.xpath("//span[contains(text(),'Closed')]")).size()>0) {
                    	queryObjects.logStatus(driver, Status.PASS, "Close Flight", "Flight Closure Breakdown has passengers in cabin added", null);
                    	if (driver.findElement(By.xpath("//div[contains(@class,'mid-block-third')]//div[contains(@class,'page-title')][1]")).getText().toUpperCase().contains("CLOSED")) {
                    		queryObjects.logStatus(driver, Status.PASS, "Close Flight and check status in the Flight header", "Flight status is 'Closed'", null);
						} else {
							queryObjects.logStatus(driver, Status.INFO, "Close Flight and check status in the Flight header", "Flight status is not updated to 'Closed'", null);
						}
                    } else {
                        queryObjects.logStatus(driver, Status.INFO, "Close Flight", "Flight Closure Breakdown does not have passengers in cabin", null);
                    }
                    try {
                    	driver.findElement(By.xpath("//button[contains(@ng-click,'closeFlight') and contains(text(),'Cancel')]")).click();
                        FlightSearch.loadhandling(driver);
					} catch (Exception e) {}
                }
                catch (Exception e) {
                	try {
                    	driver.findElement(By.xpath("//button[contains(@ng-click,'closeFlight') and contains(text(),'Cancel')]")).click();
                        FlightSearch.loadhandling(driver);
					} catch (Exception e1) {}
                	queryObjects.logStatus(driver, Status.INFO, "Close Flight", "Unable to close the flight", null);
                }
         }
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("CheckinwithoutDocCheck")).equalsIgnoreCase("yes"))  //suman 22June june
			{
				//checkin with bypass feature.
				FlightSearch.loadhandling(driver);
				//driver.findElement(By.xpath(ProceedCheckInXpath)).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//md-checkbox[contains(@ng-change,'BYPASSADC')]/div[1]")).click();
				FlightSearch.loadhandling(driver);

				try {
					driver.findElement(By.xpath("(//md-select[@ng-model='menuCtrl.menuModel']//span[@class='md-select-icon'])[2]")).click();
					FlightSearch.loadhandling(driver);
					
					Thread.sleep(1000);
					if (driver.findElements(By.xpath("//div[contains(@class,'md-active md-clickable')]//div[contains(@ng-repeat,'menuLabels')]")).size()>0) {
						queryObjects.logStatus(driver, Status.PASS, "Click ADC BYPASS button ", "ADC BYPASS reasons menu, Menus are displayed. ", null);
						Thread.sleep(500);
						driver.findElement(By.xpath("(//md-select[@ng-model='menuCtrl.menuModel']//span[@class='md-select-icon'])[2]")).click();
						if(!BypassADC.isEmpty() && !BypassADC.equalsIgnoreCase("ADCdown") && !BypassADC.equalsIgnoreCase("No Bypass")) {
							driver.findElement(By.xpath("//div[contains(@class,'md-active md-clickable')]//div[contains(text(),'"+BypassADC+"')]")).click();
						}
						else{
							driver.findElement(By.xpath("(//div[contains(@class,'md-active md-clickable')]//div[contains(@ng-repeat,'menuLabels')])[1]")).click();
						}
						FlightSearch.loadhandling(driver);
						Thread.sleep(1000);
					}							
				} catch (Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Click ADC BYPASS button ", "ADC BYPASS reasons menu, Menus are not displayed. ", null);
				}

				driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DoneXpath)));
				driver.findElement(By.xpath(DoneXpath)).click();
				FlightSearch.loadhandling(driver);	

			}
			//Search by booking class
			if(!sSearchBookingClass.equalsIgnoreCase("")) {
				try {
					sOrderNum = Login.PNRNUM.trim();
					driver.findElement(By.xpath("//pssgui-menu//md-select-value/span[2]")).click();
					Thread.sleep(3000);
					driver.findElement(By.xpath("//md-option[@value='booking_class']/div/div")).click();
					driver.findElement(By.xpath("//input[@aria-label='Enter']")).sendKeys(sSearchBookingClass);//Booking class
					Thread.sleep(1000);
					if(driver.findElement(By.xpath("//span[@role='button' and contains(text(),'"+sOrderNum+"')]")).isDisplayed()){
						queryObjects.logStatus(driver, Status.PASS, "PNR created is available in created class ", "Found "+sOrderNum+" PNR in "+sSearchBookingClass+" class", null);
					}
					else{
						queryObjects.logStatus(driver, Status.FAIL, "PNR not available created class", "PNR not found", null);
					}
					return;
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
			
			//Gate screen search by PNR written by yashodha
			//if(sOrderNum.equalsIgnoreCase("GATEPNR") && sGateCheckin.equalsIgnoreCase("Yes")){//Check this later - Feb3 2020
			  if(sGateCheckin.equalsIgnoreCase("Yes") && Filter_Name.isEmpty()&& !isSelected){
				  if (sOrderNum.equalsIgnoreCase("SharePNR")|| sOrderNum.equalsIgnoreCase("Shares")) {
					  sOrderNum = ISharesflow.iPNRNo.trim();
						driver.findElement(By.xpath("//pssgui-menu//md-select-value/span[2]")).click();
						Thread.sleep(3000);
						driver.findElement(By.xpath("//md-option[@value='pnr']/div/div")).click();
						Thread.sleep(500);

						FlightSearch.loadhandling(driver);
						try {
							driver.findElement(By.xpath("//input[@aria-label='Enter']")).clear();
						} catch (Exception e) {}
						for (int i = 0; i < sOrderNum.length(); i++){
							char c = sOrderNum.charAt(i);
							String s = new StringBuilder().append(c).toString();
							driver.findElement(By.xpath("//input[@aria-label='Enter']")).sendKeys(s);
						}

						if(driver.findElement(By.xpath("//span[@role='button' and contains(text(),'"+sOrderNum+"')]")).isDisplayed()){
							queryObjects.logStatus(driver, Status.PASS, "PNR created is available while Gate validation", "Found "+sOrderNum+" PNR ", null);
						}
						else{
							queryObjects.logStatus(driver, Status.FAIL, "PNR not available while Gate validation", "PNR not found", null);
						}
						driver.findElement(By.xpath("//md-checkbox[@aria-label='pax-chk']")).click();
				}
			}
			//Navira -20April
			  try {
					if(cabinCrew.equalsIgnoreCase("yes")) {
						driver.findElement(By.xpath("//md-select[@aria-label='Flight Actions']")).click();
						Thread.sleep(3000);
						if (driver.findElements(By.xpath("//div[text()='Cabin Crew Report (Final)']")).size()>0) {
							driver.findElement(By.xpath("//div[text()='Cabin Crew Report (Final)']")).click();
							FlightSearch.loadhandling(driver);
							if(driver.findElements(By.xpath("//div[text()='Cabin Crew - Final Report']")).size()>0)
							queryObjects.logStatus(driver, Status.PASS, "Display Cabin Crew Report", "Cabin Crew Report Displayed", null);
							
						}	
						
					}
				  }
				  catch(Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Display Cabin Crew Report", e.getLocalizedMessage(), e);
				  }
			//Add Passenger Note
			if(PaxNtList.equalsIgnoreCase("yes")) {
				PaxNote_List(driver,queryObjects);
			}
			
			//Add Multiple Passenger Note -- Srini
			
			if(Add_MultiPassenger_Note.equalsIgnoreCase("yes"))
			{
				int noOfAdults = Integer.parseInt(NoOfAdults);
				boolean SelectPax = false;
				try {
					if(!MulPaxNote.isEmpty()) {						
						int TotalPax = driver.findElements(By.xpath("(//div[contains(@class,'passenger-list')])")).size();
						
						String AllChkxpath = "//md-checkbox[@ng-model='airportPanel.model.checkAll ']";
						
						String[] MulNote = MulPaxNote.split(";");
						int Nt = MulNote.length;
						int gv = 0;
						for(int s = 0; s<Nt; s++) {					
							
								if(MulNote[s].equalsIgnoreCase("ADT"))
								{	
									for(int k = 0; k<noOfAdults;k++)
									{	
										for (int an = 1; an <= TotalPax; an++) {
											if (SelectPax) {
												an = an+gv;
											}
											SelectPax = false;
											driver.findElement(By.xpath(AllChkxpath)).click();
											FlightSearch.loadhandling(driver);
											driver.findElement(By.xpath(AllChkxpath)).click();
											FlightSearch.loadhandling(driver);
											try {
												if (driver.findElements(By.xpath("(//div[contains(@class,'passenger-list')])["+an+"]//div[contains(@ng-repeat,'iconPaxChar')]/following-sibling::i[contains(@ng-if,'CHD')]")).size()>0) {
												} else {
													driver.findElement(By.xpath("(//div[contains(@class,'passenger-list')])["+an+"]//md-checkbox")).click();
													Thread.sleep(1000);
													SelectPax = true;
												}
											} catch (Exception e) {
											}
											if (SelectPax) {
												driver.findElement(By.xpath("//md-input-container//md-select[@aria-label='Passenger Actions: Standard CheckIn']")).click();
												Thread.sleep(2000);
												driver.findElement(By.xpath("//md-option/div[text()='Add Passenger Note']")).click();
												FlightSearch.loadhandling(driver);
												String nm=RandomStringUtils.random(6, true, false);
												nm=nm.toUpperCase();
												driver.findElement(By.xpath("//label/following-sibling::div/textarea")).sendKeys(nm);
												driver.findElement(By.xpath("//button[@aria-label='POST']")).click();
												FlightSearch.loadhandling(driver);
												FlightSearch.loadhandling(driver);
												String getnote=driver.findElement(By.xpath("//span[text()='"+nm+"']")).getText();
												queryObjects.logStatus(driver, Status.PASS, "Add passenter Note functionality to Adult/Child passenger", "Passenger Note added successfully for "+MulNote[s], null);
												FlightSearch.loadhandling(driver);
												driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer layout-align-end-center layout-row')]//button[contains(@aria-label,'Cancel')]")).click();
												FlightSearch.loadhandling(driver);
												gv = an;
												break;
											}											
											
										}
										
										
									}
								}
								else if(MulNote[s].contentEquals("CHD"))
								{
									for (int an = 1; an <= TotalPax; an++) {
									SelectPax = false;
									driver.findElement(By.xpath(AllChkxpath)).click();
									FlightSearch.loadhandling(driver);
									driver.findElement(By.xpath(AllChkxpath)).click();
									FlightSearch.loadhandling(driver);
									try {
										if (driver.findElements(By.xpath("(//div[contains(@class,'passenger-list')])["+an+"]//div[contains(@ng-repeat,'iconPaxChar')]/following-sibling::i[contains(@ng-if,'CHD')]")).size()>0) {
											driver.findElement(By.xpath("(//div[contains(@class,'passenger-list')])["+an+"]//md-checkbox")).click();
											Thread.sleep(1000);
											SelectPax = true;
										}
									} catch (Exception e) {}
									if (SelectPax) {
										driver.findElement(By.xpath("//md-input-container//md-select[@aria-label='Passenger Actions: Standard CheckIn']")).click();
										Thread.sleep(2000);
										driver.findElement(By.xpath("//md-option/div[text()='Add Passenger Note']")).click();
										FlightSearch.loadhandling(driver);
										String nm=RandomStringUtils.random(6, true, false);
										nm=nm.toUpperCase();
										driver.findElement(By.xpath("//label/following-sibling::div/textarea")).sendKeys(nm);
										driver.findElement(By.xpath("//button[@aria-label='POST']")).click();
										FlightSearch.loadhandling(driver);
										FlightSearch.loadhandling(driver);
										String getnote=driver.findElement(By.xpath("//span[text()='"+nm+"']")).getText();
										queryObjects.logStatus(driver, Status.PASS, "Add passenter Note functionality to Adult/Child passenger", "Passenger Note added successfully for "+MulNote[s], null);
										FlightSearch.loadhandling(driver);
										driver.findElement(By.xpath("//div[contains(@class,'pssgui-modal-footer layout-align-end-center layout-row')]//button[contains(@aria-label,'Cancel')]")).click();
										FlightSearch.loadhandling(driver);
									}
									break;
								}
									break;
							}
						}
					}
				} catch (Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Add passenter Note functionality to Adult/Child passenger", "Add Passenger Note failed", e);
				}				
				return;
			}
			
			//Add Fqtv Number		
			if(!Updatefqtv.isEmpty()) {		
				try {		
					if(driver.findElement(By.xpath("//div[contains(text(),'No FQTV')]")).isDisplayed())		
					{		
						driver.findElement(By.xpath("//div[contains(text(),'No FQTV')]")).click();		
						Thread.sleep(100);		
						driver.findElement(By.xpath("//input[@aria-label='ffNumber']")).sendKeys(Updatefqtv);		
						Thread.sleep(100);		
						driver.findElement(By.xpath("//button[@aria-label='update']")).click();		
						FlightSearch.loadhandling(driver);		
						//if(driver.findElement(By.xpath("//div[contains(text(),'"+Updatefqtv+"')]")).isDisplayed()) 		
						queryObjects.logStatus(driver, Status.PASS, "Fqtv updated", "Successfully", null);			
					}		
				}catch(Exception e){			
					queryObjects.logStatus(driver, Status.FAIL, "Fqtv updated", "Fail", null);
				}		
								
			}
			//PD Status PNR - Chcek the Filter display and navigate back to checkin landing
			if (Negative_Case.equalsIgnoreCase("NoPNRDisplay")) {
				if (driver.findElement(By.xpath("//input[@ng-model='airportPassenger.model.searchText']")).getAttribute("value").equalsIgnoreCase(Filter_Val)) {
					queryObjects.logStatus(driver, Status.PASS, "Filter with the PNR number", "PNR number is set in the filter value", null);
					if (driver.findElements(By.xpath("//div[text()='No Passengers Found']")).size()>0) {
						queryObjects.logStatus(driver, Status.PASS, "Check the non ticketed PD status PNR display in Passenger list screen", "PD status order is not displayed", null);
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Check the non ticketed PD status PNR display in Passenger list screen", "PD status order is displayed", null);
					}
				} else {
					queryObjects.logStatus(driver, Status.FAIL, "Filter with the PNR number", "PNR number is not set in the filter value field", null);
				}
			}
			if (sCheckinMsg.contains("ReSearch_Order")) {
				driver.findElement(By.xpath(ReservationXpath)).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath(CheckInXpath)).click();
				FlightSearch.loadhandling(driver);
				sOrderNum = Filter_Val;
				sGateCheckin = "";
				driver.findElement(By.xpath(OrderNumXpath)).sendKeys(sOrderNum);
			}
			//Proceed to check in
			if (sCheckin.equalsIgnoreCase("Y")) {
			////to open the flight
				
				boolean Proceed = false;
				try {
					Proceed = driver.findElement(By.xpath(ProceedCheckInXpath)).isDisplayed();
				}catch(Exception e){}
				
				if (ClickOnName.equalsIgnoreCase("yes") && Filter_Name.equalsIgnoreCase("name")) {
					Filter_Val=Filter_Val.toUpperCase();
					int size =  driver.findElements(By.xpath("//span[contains(text(),'"+Filter_Val+"')]")).size();
					//size = size-1; //Check this with Ramya
					 driver.findElement(By.xpath("(//span[contains(text(),'"+Filter_Val+"')])["+size+"]")).click();
					 FlightSearch.loadhandling(driver);
				}
				//BeforeADC seat and bag icon validation	//cc
				if(!BeforeADC.equals("")){
					if(BeforeADC.equalsIgnoreCase("Seat")||BeforeADC.equalsIgnoreCase("Both")){
					//	queryObjects.logStatus(driver, Status.PASS, "Click Seat icon before ADC", "Before ADC Seat icon validation", null);
						queryObjects.logStatus(driver, Status.PASS, "Check the ADC Information page navigation on clicking the Seat icon before submitting APIS information", "", null);
						driver.findElement(By.xpath("//div//i[@class='icon-seatmap']")).click();
						FlightSearch.loadhandling(driver);
						if(driver.findElements(By.xpath("//div[contains(text(),'Security Document Verification - ADC & APIS')]")).size()>0)
							queryObjects.logStatus(driver, Status.PASS, "Click Seat icon before submitting ADC information", "Navigated to ADC & APIS information screen", null);
							//queryObjects.logStatus(driver, Status.PASS, "Asking for APIS information", "Apis data before Bag addition", null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "Click Seat icon before submitting ADC information", "Not Asking for ADC information", null);
							//queryObjects.logStatus(driver, Status.FAIL, "Click Seat icon before ADC", "Not Asking for ADC information", null);
						driver.findElement(By.xpath("//div[contains(text(),'Back')]")).click();
						FlightSearch.loadhandling(driver);
					}

					if(BeforeADC.equalsIgnoreCase("Bag")||BeforeADC.equalsIgnoreCase("Both")){
						queryObjects.logStatus(driver, Status.PASS, "Check the ADC Information page navigation on clicking the Bag icon before submitting APIS information", "", null);
						//queryObjects.logStatus(driver, Status.PASS, "Click Bag icon before ADC", "Before ADC Bag icon validation", null);
						driver.findElement(By.xpath("//div//i[@class='icon-baggage']/parent::div/span")).click();
						Thread.sleep(1000);
						if(driver.findElements(By.xpath("//md-dialog//div/p[contains(text(),'Collect APIS before updating this field')]")).size()>0){
							driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
							queryObjects.logStatus(driver, Status.PASS, "Click Bag icon before submitting ADC information", "Navigated to ADC & APIS information screen", null);
							//queryObjects.logStatus(driver, Status.PASS, "Asking for APIS information", "Apis data before Bag addition", null);
							FlightSearch.loadhandling(driver);
						}
						else
							queryObjects.logStatus(driver, Status.FAIL, "Click Bag icon before submitting ADC information", "Not Asking for ADC information", null);
						//driver.findElement(By.xpath("//div[contains(text(),'Back')]")).click();//Check this
						FlightSearch.loadhandling(driver);
					}

				}
				if (Proceed && DocCheck.isEmpty()) {
					Thread.sleep(1000);
					if((driver.findElements(By.xpath("//button[(text()='Proceed to Check In') and @disabled='disabled']")).size()>0))
					{	
						if (!Paxno.isEmpty()) {
							//String Paxno=FlightSearch.getTrimTdata(queryObjects.getTestData("pax_nocheckin"));
							int no=Integer.parseInt(Paxno);
							// List<WebElement> SelectPnr=driver.findElements(By.xpath("//div[contains(@ng-repeat,'passengerItinerary')]/child::div/div[1]/div[2]/i[contains(@class,'in-active-state')]//parent::div/preceding-sibling::md-checkbox/div"));
							int k=0;
							List<WebElement> SelectPnr=driver.findElements(By.xpath("//div[contains(@ng-repeat,'passengerItinerary')]/child::div/div[1]/div[2]/i[contains(@class,'in-active-state')]//parent::div/preceding-sibling::md-checkbox/div[1]"));
							for(int i=0;i<no;i++)
							{    
								SelectPnr.get(k).click();
								//driver.findElement(By.xpath("")).click(); 
								//SelectPnr.clear();
								Thread.sleep(1000);
								++k;
							}
						}
						// }
					}
					
					//Updated by Jenny - Oct 2019   
					try {
						Actions Act = new Actions(driver);
						Act.moveToElement(driver.findElement(By.xpath(SearchXpath))).perform();
						driver.findElement(By.xpath(SearchXpath)).sendKeys(Keys.chord(Keys.TAB,Keys.ENTER));
					} catch (Exception e) {
						driver.findElement(By.xpath("//button[text()='Proceed to Check In']")).click();
					}
					FlightSearch.loadhandling(driver);
	 
					if(driver.findElements(By.xpath("//div[@translate='pssgui.redirect.confirmation']")).size()>0)
					{
						driver.findElement(By.xpath("//md-dialog//button[contains(text(),'OK')]")).click();
						FlightSearch.loadhandling(driver);
						
					}

					//split pnr_Meenu
					if(driver.findElements(By.xpath("//md-dialog-content/div/p[contains(text(),'Waitlisted Multi-initial/Multi-surname passenger')]")).size()>0)
					{
						driver.findElement(By.xpath("//md-dialog//button[contains(text(),'OK')]")).click();
						FlightSearch.loadhandling(driver);
						
						PNRsearch Split = new PNRsearch();						
						String oldPnr =sOrderNum;
						sOrderNum = Split.SplitBooking(driver,queryObjects,oldPnr, "");
						driver.findElement(By.xpath("//pssgui-reservations-home/div/div[1]/div/div[1]/div[2]/div[2]")).click();
						FlightSearch.loadhandling(driver);
						try {
							driver.findElement(By.xpath(ReservationXpath)).click();
							Thread.sleep(2000);
							driver.findElement(By.xpath(CheckInXpath)).click();
							FlightSearch.loadhandling(driver);
							sOrderNum = Filter_Val;
							sGateCheckin = "";
							driver.findElement(By.xpath(OrderNumXpath)).sendKeys(sOrderNum);
							try {
								Actions Act = new Actions(driver);
								Act.moveToElement(driver.findElement(By.xpath(SearchXpath))).perform();
								driver.findElement(By.xpath(SearchXpath)).sendKeys(Keys.chord(Keys.TAB,Keys.ENTER));
							} catch (Exception e) {
								driver.findElement(By.xpath("//button[text()='Proceed to Check In']")).click();
							}
							FlightSearch.loadhandling(driver);
						} catch (Exception e) {
							// TODO: handle exception
						}
						
					}
					
					if(driver.findElements(By.xpath("//div[@translate='pssgui.redirect.confirmation']")).size()>0)
					{
						driver.findElement(By.xpath("//md-dialog//button[contains(text(),'OK')]")).click();
						FlightSearch.loadhandling(driver);
						
					}							}

					// add handling of non ticketed pnr rushil
					if(NonTkt_PNR.equalsIgnoreCase("yes")) {
						if(sFFnum!= "") {
							//Atul - 22Mar - To close the FF list popup
							if(driver.findElement(By.xpath("//md-dialog[contains(@aria-label,'pssgui-dialog')]")).isDisplayed())
								{
									driver.findElement(By.xpath("//i[contains(@class,'icon-close')]")).click();
						
								}	
						}
						else if(driver.findElement(By.xpath("//md-dialog[contains(@aria-label,'Redirect Confirmation')]")).isDisplayed())

						{
							driver.findElement(By.xpath("//md-dialog[contains(@aria-label,'Redirect Confirmation')]//button[contains(text(),'Cancel')]")).click();

						}
						return;
					}
				//	driver.findElement(By.xpath(ProceedCheckInXpath)).click();
					FlightSearch.loadhandling(driver);
					String Waitlist = FlightSearch.getTrimTdata(queryObjects.getTestData("Waitlist"));
					if(Waitlist.equalsIgnoreCase("Yes")) {
						if(driver.findElements(By.xpath("//div/p[contains(text(),'Waitlisted Passenger')]")).size()>0) {
							driver.findElement(By.xpath("//md-dialog//button[contains(text(),'OK')]")).click();
							FlightSearch.loadhandling(driver);
							queryObjects.logStatus(driver, Status.PASS, "Able to proceed with checkin for waitlisted pax ", "Checkin for waitlist pax", null);
						}
						else
							queryObjects.logStatus(driver, Status.FAIL, "Not displaying alert for waitlist pax checkin ", "Not to checkin for waitlist pax", null);
					}
					String FligtOpenMsg = "";
					try{
						FligtOpenMsg=driver.findElement(By.xpath("//span[contains(text(),'The specified flight is not open')]")).getText().trim();
					}
					catch(Exception e){}
					if(FligtOpenMsg.contains("The specified flight is not open") && openflightflag==0)
					{
						OpenFlights_Checkin(driver, queryObjects);
					}
	  
					// click on doc check in checkin landing page written by rushil 19 Mar 2020
					if(DocCheck.equalsIgnoreCase("yes")) {
						driver.findElement(By.xpath("//span[contains(@role,'button') and contains(text(),'Doc Check')]")).click();
						FlightSearch.loadhandling(driver);																																																																																																	 
					}
					//need to check purpose of visit drop down	//cc
					if(PurOfVisit.equalsIgnoreCase("check")){
						try {
							queryObjects.logStatus(driver,Status.PASS,"Need to check the Purpose of Visit / Province","Validating the two options",null);
							driver.findElement(By.xpath("//div[@action='purpose-of-visit']//md-select//span[2]")).click();
							Thread.sleep(500);
							if((driver.findElements(By.xpath("//md-option[@value='Tourist']")).size()>0) && (driver.findElements(By.xpath("//md-option[@value='Business']")).size()>0)){
								queryObjects.logStatus(driver, Status.PASS, "Both Tourist and Business are available", "By Default Purpose of visit is selected as Tourist", null);
								driver.findElement(By.xpath("//md-option[@value='Tourist']")).click();
							}
							else
								queryObjects.logStatus(driver, Status.FAIL, "PNR is being set with default purpose of visit", "Purpose of visit Option is set by default or not being able to select", null);
						} catch (Exception e) {
							queryObjects.logStatus(driver,Status.FAIL,"Unable to validate purpose of visit","Issue while validating purpose of visit",e);
						}
																																																																																																					  
					}

					if(Clear_API.equalsIgnoreCase("Verify")){	//cc
						if(driver.findElements(By.xpath("//button[@aria-label='Clear']")).size()>0)
							queryObjects.logStatus(driver, Status.PASS, "Clear button available before APIS information collection", "Clear button verified", null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "Clear button not available before APIS information collection", "Clear button Issue", null);
					}
					//Capture pax name
					//suman
					try {
						selecteePaxName=driver.findElement(By.xpath("//div[contains(@ng-if,'docCheckCtrl.activePassenger.OrderPassenger')]/div/div[contains(@ng-repeat,'activePassenger')]")).getText();   //suman selectee
					}catch(Exception e) {}

					try {
						driver.findElement(By.xpath(OKXpath)).click();
						FlightSearch.loadhandling(driver);							  
					  
					}catch (Exception e) {}																											  
					try {
						String PaxNmae=driver.findElement(By.xpath("//div/div[contains(@ng-repeat,'OrderPassenger.finalDocuments')]")).getText().trim();
						String[] lastname=PaxNmae.split("/");
						name=lastname[0];
					}catch(Exception e) {}

					
					//Infant Pax Validation	//cc
					if(sInfantPax.equalsIgnoreCase("Yes")) {
						try {
							if(driver.findElements(By.xpath("//*[contains(@ng-repeat,'orderObj.Passengers')]//span[contains(text(),'INFANT')]")).size()>0) {
								driver.findElement(By.xpath("//*[contains(@ng-repeat,'orderObj.Passengers')]//span[contains(text(),'INFANT')]")).click();
								if(driver.findElements(By.xpath("//button[@aria-label='submit' and @disabled='disabled']")).size()>0) {
									queryObjects.logStatus(driver, Status.PASS, "Able to see Submit disabled before Adding ADC/APIS", "Submit button Grey before ADC completion", null);
									if(sSubmit.equalsIgnoreCase("Y")) {
										queryObjects.logStatus(driver, Status.PASS, "Will proceed to do ADC/APIS", "Adding ADC/APIS", null);
									}
									else
										return;
								}
								else
									queryObjects.logStatus(driver, Status.FAIL, "Not Able to see Submit disabled before Adding ADC/APIS", "Submit button Not Grey", null);
							}
							else
								queryObjects.logStatus(driver, Status.FAIL, "Not having Infant Pax", "Name of infant should be correct in test data", null);
						} catch (Exception e) {
							queryObjects.logStatus(driver, Status.FAIL, "Infant Submit button validation failed", "Need to check infant Checkin validation", null);
							// TODO: handle exception
						}	   
					}
	 
					boolean IsExpectedCondition =false;
					boolean IsEnabled=false;
					boolean IsEnabledCOR=false;
					try {
						driver.findElement(By.xpath("//*[contains(@ng-repeat,'orderObj.Passengers')][1]")).click();
						FlightSearch.loadhandling(driver);
						IsExpectedCondition = driver.findElement(By.xpath(SecurityXpath)).isDisplayed();
						IsEnabled = driver.findElement(By.xpath(SurnameXpath1)).isEnabled();

						IsEnabledCOR = driver.findElement(By.xpath(CORXpath)).isEnabled();
					}catch(Exception e) {}
					//suman
					/*if(IsEnabledCOR && (!IsEnabled))//Check this part
						
					{
						driver.findElement(By.xpath(CORXpath)).sendKeys("US"); 
						driver.findElement(By.xpath(CORXpath)).sendKeys(Keys.ENTER);  
	  
	   

						//suman
						boolean IsEnabledEmername = driver.findElement(By.xpath(EmernameXpath)).isEnabled();
						if(IsEnabledEmername)
						{
							driver.findElement(By.xpath(EmernameXpath)).sendKeys("Jhon"); 
							driver.findElement(By.xpath(EmernameXpath)).sendKeys(Keys.ENTER); 
						}
						//suman
						boolean IsEnabledEmerPh = driver.findElement(By.xpath(EmerPhXpath)).isEnabled();
						if(IsEnabledEmerPh)
						{
							driver.findElement(By.xpath(EmerPhXpath)).sendKeys("2324248656"); 
							driver.findElement(By.xpath(EmerPhXpath)).sendKeys(Keys.ENTER); 
						}
					}*/

					if (IsExpectedCondition && IsEnabled) {
						queryObjects.logStatus(driver, Status.PASS, "Navigated to the Security Document Verification Page", "Expected Page dispalyed ", null);
						EnterDocCheck(driver,queryObjects);

					}
					else if(IsExpectedCondition) {
						queryObjects.logStatus(driver, Status.PASS, "Security Document Verification Page with ADC , APIS completed", "Expected Page dispalyed ", null);
						//boolean IsEnabledEmerPh = driver.findElement(By.xpath(EmerPhXpath)).isEnabled();
						if (IsEnabledCOR) {
							driver.findElement(By.xpath(CORXpath)).clear();
							Thread.sleep(2000);
							driver.findElement(By.xpath(CORXpath)).sendKeys(FlightSearch.getTrimTdata(queryObjects.getTestData("COR"))); 
							Thread.sleep(2000);
							FlightSearch.loadhandling(driver);
							//driver.findElement(By.xpath(CORXpath)).sendKeys(Keys.TAB);
							//Tab Alternate Solution
							try {
								driver.findElement(By.xpath(Login.SelList)).click();
							} catch (Exception e1) {
								try {
									driver.findElement(By.xpath(Login.SelList2)).click();
								} catch (Exception e) {
									try {
										driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='"+FlightSearch.getTrimTdata(queryObjects.getTestData("COR"))+"']/ancestor::span[2]/following-sibling::span/span/../../..")).click();
										}catch(Exception e3) {
											driver.findElement(By.xpath(CORXpath)).sendKeys(Keys.TAB);
										}
								}
								
							}
							FlightSearch.loadhandling(driver);
						}
																																							
	  
						if (sCheckinMsg.equalsIgnoreCase("BYPASS ADC")) // added by rushil.		
						{		
							driver.findElement(By.xpath("//md-checkbox[contains(@ng-change,'BYPASSADC')]/div[1]")).click();		
							FlightSearch.loadhandling(driver);		
							driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();		
							FlightSearch.loadhandling(driver);		
						}	
						boolean IsEnabledEmername = driver.findElement(By.xpath(EmernameXpath)).isEnabled();
						if(IsEnabledEmername)
						{
							driver.findElement(By.xpath(EmernameXpath)).clear(); 
							driver.findElement(By.xpath(EmernameXpath)).sendKeys("Jhon"); 				 

							driver.findElement(By.xpath(EmerPhXpath)).clear();
							driver.findElement(By.xpath(EmerPhXpath)).sendKeys("2324248656"); 
						}
						try {
							driver.findElement(By.xpath(SubmitXpath)).click();
							FlightSearch.loadhandling(driver);	
							} catch(Exception e) {}
						HandleSecDoc(driver,queryObjects);
					}
					else if(!IsExpectedCondition && !IsEnabled )   //suman 28 may
					{
						queryObjects.logStatus(driver, Status.PASS, "Security Document Verification Page with ADC , APIS completed ", "Security Document Verification Page with ADC , APIS Skipped for Domestic flight ", null);
						try{
							driver.findElement(By.xpath("//button[contains(text(),'Check In')]")).click();
							FlightSearch.loadhandling(driver);
							/*try {//Recent Update Coupon Control
								if(driver.findElements(By.xpath("//div//p[contains(text(),'Ticket Status is CNTRL')]")).size()>0){
									FlightSearch.coupon_control(driver, queryObjects, "ALL", "ALL");
								}
							} catch (Exception e) {}*/
							try {
								driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
								FlightSearch.loadhandling(driver);

								if(driver.findElements(By.xpath("//i[@class='icon-checked-in active-state']")).size()>0) {
									queryObjects.logStatus(driver, Status.PASS, "Checking the Checkin without ADC & APIC completion", "Checking is successfull ", null); 
								}
								else {
									queryObjects.logStatus(driver, Status.FAIL, "Checking the Checkin without ADC & APIC completion", "Checking is not successfull ", null);  
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
	  
	  
						catch(Exception e)
						{
							queryObjects.logStatus(driver, Status.FAIL, "Checking the Checkin without ADC & APIC completion", "Checking is not successfull ", e);
						}

					}
					else {

						queryObjects.logStatus(driver, Status.FAIL, "UnExpected Page displayed", "UnExpected Page dispalyed "+sFlight , null); 
					}
				
				}	
				
			//Click on Done and finish check in
			if( sSubmit.equalsIgnoreCase("Y")) {				

				//click on adcbypass
				/*if(Select_pax.equalsIgnoreCase("Y"))
				{
					driver.findElement(By.xpath("//md-checkbox[contains(@ng-change,'BYPASSADC')]/div[1]")).click();
					driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
				}*/


				try {
					if(!ChangeSaetFlag && !status)
						driver.findElement(By.xpath(SubmitXpath)).click();

				}catch(Exception e){}

				boolean isenabled=false;
				try {
					FlightSearch.loadhandling(driver);
					isenabled = driver.findElement(By.xpath(AdsResponsePopup)).isEnabled();
					//driver.findElement(By.xpath(SecondaryDoc)).isDisplayed();
				} catch (Exception e) {
					// TODO: handle exception
				}
				if(Clear_API.equalsIgnoreCase("Verify")){	//cc
					if(driver.findElements(By.xpath("//button[@aria-label='Clear']")).size()==0)
						queryObjects.logStatus(driver, Status.PASS, "Delete button available After APIS information collection", "Clear button verified", null);
					else
						queryObjects.logStatus(driver, Status.FAIL, "Delete button not available after APIS information collection", "Clear button Issue", null);
				}

				if (Delete_API.equalsIgnoreCase("AfterADC")) { 	//cc //delete after ADC/APIS complete

					driver.findElement(By.xpath("//button[@translate='pssgui.delete']")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//button[contains(@class,'md-primary md-confirm-button')]")).click();
					FlightSearch.loadhandling(driver);
					if(driver.findElements(By.xpath("//span[contains(text(),'APIS Incomplete')]")).size()>0)
						queryObjects.logStatus(driver, Status.PASS, "ADC/APIS Date is being deleted", "Deleted the ADC detail", null);
					else
						queryObjects.logStatus(driver, Status.FAIL, "ADC/APIS Date is being not being deleted", "Delete the ADC/APIS failed", null);
					return;
				}
				
				if(isenabled)
				{
					if ((driver.findElements(By.xpath("//td[contains(text(),'ADCNOTAVAILABLE')]")).size()>0 || driver.findElement(By.xpath("//div[contains(@ng-repeat,'adcDecision.passengers')]//span")).getText().contains("ADC NOT OK")) && BypassADC.contains("ADCdown")) {//Jenny -ADCNOTAVILABLE
					ByPassADC_ADCErr_Down(driver, queryObjects);
					} else {
						queryObjects.logStatus(driver, Status.PASS, "Checking Secondary document need to submit:", "Secondary document is mandatory", null);
						driver.findElement(By.xpath(AdsResponsePopup)).click();
					}
				}
				
				//APIS - Secondary Document Verification
				HandleSecDoc(driver,queryObjects);
				try {			 	
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DoneXpath)));
					driver.findElement(By.xpath(DoneXpath)).click();
					FlightSearch.loadhandling(driver);
				}catch(Exception e) {}
				
				//Split Booking and Proceed to Checkin
				if (PNRRemarks.equalsIgnoreCase("SplitPNR")) {
					try {
						MultiPNR = "";
						driver.findElement(By.xpath("//div[contains(@class,'pnr-value') and contains(@ng-click,'pnr')]")).click();		
						FlightSearch.loadhandling(driver);
						PNRsearch Split = new PNRsearch();						
						sOrderNum =ISharesflow.iPNRNo;
						String newPNR = Split.SplitBooking(driver,queryObjects,sOrderNum, "2");
						Login.PNRNUM = newPNR;
						MultiPNR = sOrderNum+";"+newPNR;
						driver.findElement(By.xpath("//pssgui-reservations-home/div/div[1]/div/div[1]/div[2]/div[2]")).click();
						FlightSearch.loadhandling(driver);
						try {
							driver.findElement(By.xpath(ReservationXpath)).click();
							Thread.sleep(2000);
							driver.findElement(By.xpath(CheckInXpath)).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath(OrderNumXpath)).sendKeys(sOrderNum);
							try {
								Actions Act = new Actions(driver);
								Act.moveToElement(driver.findElement(By.xpath(SearchXpath))).perform();
								driver.findElement(By.xpath(SearchXpath)).sendKeys(Keys.chord(Keys.TAB,Keys.ENTER));
							} catch (Exception e) {
								driver.findElement(By.xpath("//button[text()='Proceed to Check In']")).click();
							}
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath(DoneXpath)).click();
							FlightSearch.loadhandling(driver);
						} catch (Exception e) {
							// TODO: handle exception
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
				//split reservation popup when click on baggage icon written by rushil
				if(DocCheck.equalsIgnoreCase("yes") && DocCheck_Bagadd.equalsIgnoreCase("yes")) {
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath(BaggageXpath)).click();
					FlightSearch.loadhandling(driver);
					if(driver.findElement(By.xpath("//pssgui-baggage[contains(@popup-action,'baggage-multi-initial')]")).isDisplayed())
					{
						queryObjects.logStatus(driver, Status.PASS, "Baggage Multi Initial popup", "Baggage Multi Initial popup is displayed ", null);
						driver.findElement(By.xpath("//button[contains(@aria-label,'Baggage Close Popup')]")).click();
						FlightSearch.loadhandling(driver);
					}
					else {
						queryObjects.logStatus(driver, Status.FAIL, "Baggage Multi Initial popup", "Baggage Multi Initial popup is not displayed ", null);
					}
					return;
				}
				//already checked in passenger does not go directly to final check in page - rushil
				if(driver.findElements(By.xpath("//div[contains(@class,'hpe-pssgui passenger-info')]//button[contains(@aria-label,'Proceed to Check In')]")).size()>0)
				{
					driver.findElement(By.xpath("//div[contains(@class,'hpe-pssgui passenger-info')]//button[contains(@aria-label,'Proceed to Check In')]")).click();
					FlightSearch.loadhandling(driver);
					try {			 	
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DoneXpath)));
						driver.findElement(By.xpath(DoneXpath)).click();
						FlightSearch.loadhandling(driver);
					}catch(Exception e) {}
				}
				
				//click on pnr and redirect to res gui rushil						
				if(redirect_resgui.equalsIgnoreCase("yes")) {		
					driver.findElement(By.xpath("//div[contains(@class,'pnr-value') and contains(@ng-click,'pnr')]")).click();		
					FlightSearch.loadhandling(driver);
					if(sDatechange.equalsIgnoreCase("yes")) {		
					String tempXpath = "//table[contains(@ng-table,'flightResult')]//tbody[tr[td[span[md-checkbox[@role='checkbox']]]]][1]";		
						List<WebElement> editdate=new ArrayList<>();		
								
						String xpathofpointtodelet="//table[contains(@ng-table,'flightResult')]//tbody[tr[td[div[span[i[@class='icon-removed']]]]]]";		
						String prevDate = driver.findElement(By.xpath(tempXpath + "//td[@class='date']//span")).getText().trim();		
						//Add two days to the existing date		
						Calendar cal1 = Calendar.getInstance();		
						SimpleDateFormat sdf = new  SimpleDateFormat("MM/dd/yyyy");		
						SimpleDateFormat sdf2 = new  SimpleDateFormat("dd-MMM-yyyy");		
						cal1.setTime(sdf2.parse(prevDate));		
						String days=FlightSearch.getTrimTdata(queryObjects.getTestData("Datechange_days"));		
						int tmpdate=0;		
						if(days.equalsIgnoreCase(""))		
						{		
							tmpdate=Integer.parseInt("2");		
							cal1.add(Calendar.DATE, tmpdate);		
						}		
						else		
						{		
							tmpdate=Integer.parseInt(days);		
							cal1.add(Calendar.DATE, tmpdate);			
						}		
						String newDate = sdf.format(cal1.getTime());		
						//newDate = sdf.format(sdf2.parse(newDate));		
						if (!sNthsegment.isEmpty()) {		
							WebElement segChkBoxa ;		
							List<WebElement> lDeleticons=new ArrayList<>();		
							lDeleticons=driver.findElements(By.xpath(xpathofpointtodelet));		
							if(sNthsegment.equalsIgnoreCase("all")){		
								for (int changeclss = 0; changeclss < lDeleticons.size(); changeclss++) {		
									int val=changeclss+1;		
									segChkBoxa = driver.findElement(By.xpath(xpathofpointtodelet+"["+val+"]" + "//md-checkbox"));		
									if(segChkBoxa.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))		
										segChkBoxa.click();		
									driver.findElement(By.xpath(xpathofpointtodelet+"["+val+"]//td[@class='date']//span")).click();		
									Thread.sleep(2000);		
									editdate=driver.findElements(By.xpath("//input[@class='md-datepicker-input']"));		
									for (int iedit = 0; iedit < editdate.size(); iedit++) {		
										//int vla=iedit+1;		
										editdate.get(iedit).click();		
										editdate.get(iedit).clear();		
										editdate.get(iedit).sendKeys(newDate);		
										cal1.add(Calendar.DATE, tmpdate+1);		
										newDate = sdf.format(cal1.getTime());		
										FlightSearch.loadhandling(driver);		
									}		
									driver.findElement(By.xpath("//span[@class='save']")).click();		
									FlightSearch.loadhandling(driver);		
								}		
								segChkBoxa = driver.findElement(By.xpath("//md-checkbox[contains(@class,'segment-checkbox') and div[contains(@class,'md-ink-ripple')]]"));		
								if(segChkBoxa.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))		
									segChkBoxa.click();		
							}		
							else		
							{		
								//prevDate = driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult')]//tbody[tr[td[span[md-checkbox[@role='checkbox']]]]]["+sNthsegment+"]//td[@class='date']//span")).getText().trim();		
								int intsNthsegment=Integer.parseInt(sNthsegment);		
								segChkBoxa = driver.findElement(By.xpath(xpathofpointtodelet+"["+intsNthsegment+"]" + "//md-checkbox"));		
								if(segChkBoxa.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))		
									segChkBoxa.click();		
								driver.findElement(By.xpath(xpathofpointtodelet+"["+intsNthsegment+"]//td[@class='date']//span")).click();		
								Thread.sleep(2000);		
								editdate=driver.findElements(By.xpath("//input[@class='md-datepicker-input']"));		
								//int vla=0;		
								for (int iedit = 0; iedit < editdate.size(); iedit++) {		
									//int vla=iedit+1;		
									editdate.get(iedit).click();		
									editdate.get(iedit).clear();		
									editdate.get(iedit).sendKeys(newDate);		
									FlightSearch.loadhandling(driver);		
									cal1.add(Calendar.DATE, tmpdate+1);		
									newDate = sdf.format(cal1.getTime());		
								}		
								driver.findElement(By.xpath("//span[@class='save']")).click();		
								FlightSearch.loadhandling(driver);		
								try{		
									List<WebElement>maridseg=driver.findElements(By.xpath("//table[contains(@ng-table,'flightResult')]//tbody[tr[td[div[div[2][div[span[2][starts-with(text(),'"+intsNthsegment+"')]]]]]]]//md-checkbox"));		
									if (maridseg.size()>0) {		
										for (int i = 0; i <maridseg.size(); i++) {		
											maridseg.get(i).click();		
										}		
									}		
									else{		
										segChkBoxa = driver.findElement(By.xpath(xpathofpointtodelet+"["+intsNthsegment+"]" + "//md-checkbox"));		
										if(segChkBoxa.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))		
											segChkBoxa.click();		
									}		
								}		
								catch (Exception e) { 		
									segChkBoxa = driver.findElement(By.xpath(xpathofpointtodelet+"["+intsNthsegment+"]" + "//md-checkbox"));		
									if(segChkBoxa.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))		
										segChkBoxa.click(); }		
							}		
						}		
						else{		
							WebElement segChkBox = driver.findElement(By.xpath(tempXpath + "//md-checkbox"));		
							if(segChkBox.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))		
								segChkBox.click();					
							driver.findElement(By.xpath(tempXpath + "//td[@class='date']//span")).click();		
							editdate=driver.findElements(By.xpath("//input[@class='md-datepicker-input']"));		
							for (int iedit = 0; iedit < editdate.size(); iedit++) {		
								int vla=iedit+1;		
								String datxpath="//table[contains(@ng-table,'flightResult')]//tbody[tr[td[span[md-checkbox[@role='checkbox']]]]]["+vla+"]//input[@class='md-datepicker-input']";		
								driver.findElement(By.xpath(datxpath)).click();		
								driver.findElement(By.xpath(datxpath)).clear();		
								driver.findElement(By.xpath(datxpath)).sendKeys(newDate);		
							}		
							//Click on save button		
							FlightSearch.loadhandling(driver);		
							driver.findElement(By.xpath(tempXpath + "//span[@class='save']")).click();		
							Thread.sleep(2000);		
							//Wait until loading wrapper closed		
							FlightSearch.loadhandling(driver);		
							segChkBox = driver.findElement(By.xpath(tempXpath + "//md-checkbox"));		
							if(segChkBox.getAttribute("aria-checked").trim().equalsIgnoreCase("false"))		
								segChkBox.click();		
						}		
					}		
					return;		
				}
				if(!sSpecialPass.equalsIgnoreCase("")) {   // special passenger selection 
					driver.findElement(By.xpath("//md-input-container//md-select[@aria-label='Checkin Passenger Actions']")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//md-option/div[text()='Special Passenger']")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//pssgui-menu[@startup='SpecialPassengerTypeTable']//md-select[@ng-model='menuCtrl.menuModel']")).click();
					try{
						driver.findElement(By.xpath("//md-select-menu//span[contains(text(),'"+sSpecialPass+"')]")).click(); // As per test data select special passenger
						Thread.sleep(1000);
						driver.findElement(By.xpath("//button[text()='OK']")).click();
						FlightSearch.loadhandling(driver);
						String Check =driver.findElement(By.xpath("//div[@ng-if='passengerData.splpaxtype']")).getText().trim();
						if (!Check.isEmpty())
							queryObjects.logStatus(driver, Status.PASS, "Special Passenger functionality checking", "Special Passenger selected successfully type is "+sSpecialPass, null); 
						else
							queryObjects.logStatus(driver, Status.FAIL, "Special Passenger functionality checking", "selected option not displaying expected is "+sSpecialPass +" Actual displaying is "+Check, null);	
					}
					catch(Exception e){
						queryObjects.logStatus(driver, Status.FAIL, "Selecting Special pax", "Test data given value unable to select in application", null); 
					}
				}
				
				//Single Passenger Note
				if(Add_Passenger_Note.equalsIgnoreCase("yes")){
					try{
						if(!NotepaxType.isEmpty()) 
						{
							driver.findElement(By.xpath("//md-checkbox[@aria-label='pssgui.select.all']//div[@class='md-icon']/parent::div")).click();
							Thread.sleep(100);
							driver.findElement(By.xpath("//md-checkbox[@aria-label='pssgui.select.all']//div[@class='md-icon']/parent::div")).click();
							
							driver.findElement(By.xpath("//i[contains(@ng-if,'"+NotepaxType+"')]/../../md-checkbox")).click();
							Thread.sleep(2000);
						
						}
						driver.findElement(By.xpath("//md-input-container//md-select[@aria-label='Checkin Passenger Actions']")).click();
						Thread.sleep(2000);
						driver.findElement(By.xpath("//md-option/div[text()='Add Passenger Note']")).click();
						FlightSearch.loadhandling(driver);
						FlightSearch.loadhandling(driver);
						String nm=RandomStringUtils.random(6, true, false);
						nm=nm.toUpperCase();
						driver.findElement(By.xpath("//label/following-sibling::div/textarea")).sendKeys(nm);
						driver.findElement(By.xpath("//button[@aria-label='POST']")).click();
						FlightSearch.loadhandling(driver);
						FlightSearch.loadhandling(driver);
						String getnote=driver.findElement(By.xpath("//span[text()='"+nm+"']")).getText();
						queryObjects.logStatus(driver, Status.PASS, "Add passenter Note functionality checking", "Add passenger Note working successfully", null);
						FlightSearch.loadhandling(driver);
						
					}
					catch(Exception e){
						queryObjects.logStatus(driver, Status.FAIL, "Add passenter Note functionality checking", " Unable to add Pax note", e);
					}
					driver.findElement(By.xpath("//md-dialog[@aria-label='pssgui-dialog']//button[@aria-label='Cancel']")).click();
					Thread.sleep(2000);
				}
				
				//Assign Seats
				
				if(sKnownTraveller.equalsIgnoreCase("yes"))
				{
					//*** this condition is to call the reservation page from checkin page to validate the ssr of ktn is updated on not
					driver.findElement(By.xpath("//div[contains(@ng-click,'airportPassenger.stateChange')]")).click();
					FlightSearch.loadhandling(driver);
					ValidateKTN.ktn(driver, queryObjects);
				}
				
				if (sCheckinMsg.contains("AddFQTV")&& !FqtvUpdate.isEmpty()) {
					driver.findElement(By.xpath("//input[@aria-label='ffNumber']")).clear();
					driver.findElement(By.xpath("//input[@aria-label='ffNumber']")).sendKeys("1");
					driver.findElement(By.xpath("//input[@aria-label='ffNumber']")).clear();
					driver.findElement(By.xpath("//input[@aria-label='ffNumber']")).sendKeys(FqtvUpdate);
					Thread.sleep(100);
					driver.findElement(By.xpath("//input[@aria-label='ffNumber']")).sendKeys(Keys.TAB);
					FlightSearch.loadhandling(driver);
					if (driver.findElements(By.xpath("//span[contains(text(),'FQTV successfully updated')]")).size()>0) {
						queryObjects.logStatus(driver, Status.PASS, "Add FQTV in Checkin page", "FQTV is added successfully to the PNR", null);
					} else {
						queryObjects.logStatus(driver, Status.PASS, "Add FQTV in Checkin page", "FQTV updation failed", null);
					}
				}
				
				// delete assigned seat on final checkin page written by rushil				
				if(Deleteseat_Chkin.equalsIgnoreCase("yes")) {
					List<WebElement> Paxcnt = driver.findElements(By.xpath("//div[contains(@ng-repeat,'passengerData in passengerItinerary')]"));
					for (int pax=1;pax<=Paxcnt.size();pax++) {
						driver.findElement(By.xpath("//div[contains(@ng-repeat,'passengerData in passengerItinerary')]["+pax+"]//md-input-container[contains(@class,'passenger-seat-number')]//input")).click();
						Thread.sleep(1000);
						driver.findElement(By.xpath("//div[contains(@ng-repeat,'passengerData in passengerItinerary')]["+pax+"]//md-input-container[contains(@class,'passenger-seat-number')]//input")).clear();
						Thread.sleep(2000);
					}
				}
				
				if (AssignSeats.equalsIgnoreCase("Y")) {
					boolean Assignseat = false;
					driver.findElement(By.xpath("//i[@class='icon-seatmap']")).click();
					FlightSearch.loadhandling(driver);
					List<WebElement> ChkCnt = driver.findElements(By.xpath(FlightLegChkbox));
					if (sCheckinMsg.equalsIgnoreCase("Partial routing check In")) {
						Assignseat=false;
						if ((Boolean.parseBoolean(driver.findElement(By.xpath(FlightLegChkbox+"[1]")).getAttribute("aria-checked")))==true && (Boolean.parseBoolean(driver.findElement(By.xpath(FlightLegChkbox+"[2]")).getAttribute("aria-checked")))==true) {
							driver.findElement(By.xpath(FlightLegChkbox+"[2]")).click();
							if ((Boolean.parseBoolean(driver.findElement(By.xpath(FlightLegChkbox+"[2]")).getAttribute("aria-checked")))==false) {
								Assignseat =true;
								queryObjects.logStatus(driver, Status.PASS, "Select one leg ", "Only one leg is selected, secobnd leg is deselected", null);
							}

						}
					}else {
							 ChkCnt = driver.findElements(By.xpath(FlightLegChkbox));
							for (int i = 1; i <= ChkCnt.size(); i++) {  // assign seats
								Assignseat=false;
								if ((Boolean.parseBoolean(driver.findElement(By.xpath(FlightLegChkbox+"["+i+"]")).getAttribute("aria-checked")))==true) {
									List<WebElement> AvSeat =driver.findElements(By.xpath("//div[contains(@class,'seat-holder ng-scope icn-available') or contains(@class,'seat-holder ng-scope icn-chargeable icn-available')]"));
									//List<WebElement> AvSeat =driver.findElements(By.xpath("//div[contains(@class,'seat-holder ng-scope icn-available') or contains(@class,'seat-holder ng-scope icn-chargeable')]"));
									AvSeat.get(1).click();
									Thread.sleep(1000);//Recent update -UAT
									/*driver.findElement(By.xpath("//md-select[contains(@aria-label,'Passenger Seat Characteristics')]")).click();
									Thread.sleep(100);
									driver.findElement(By.xpath("//div[contains(text(),'Exit Row Seat')]")).click();*/
									FlightSearch.loadhandling(driver); 
									//boolean displayed = driver.findElement(By.xpath("//md-dialog[contains(@aria-label,'Exit Seat Confirmation')]")).isDisplayed();
									if(driver.findElements(By.xpath("//md-dialog[contains(@aria-label,'Exit Seat Confirmation')]")).size()>0)
									{
										driver.findElement(By.xpath("//md-dialog[contains(@aria-label,'Exit Seat Confirmation')]//button[contains(text(),'Yes')]")).click();
										Thread.sleep(1000);
									}
									/*if(displayed)
									{
										driver.findElement(By.xpath("//md-dialog[contains(@aria-label,'Exit Seat Confirmation')]//button[contains(text(),'Yes')]")).click();
										Thread.sleep(1000);
									}*/
									Assignseat =true;
								}else {
									driver.findElement(By.xpath(FlightLegChkbox+"["+i+"]")).click();
									driver.findElement(By.xpath("(//div[contains(@class,'seat-holder ng-scope icn-available') or contains(@class,'seat-holder ng-scope icn-chargeable')]//following-sibling::span)[1]/..")).click();
									Thread.sleep(1000);
									if(driver.findElements(By.xpath("//md-dialog[contains(@aria-label,'Exit Seat Confirmation')]")).size()>0)
									{
										driver.findElement(By.xpath("//md-dialog[contains(@aria-label,'Exit Seat Confirmation')]//button[contains(text(),'Yes')]")).click();
										Thread.sleep(1000);
									}
									Assignseat=true;
								}				
							}
						
					}			
					if (Assignseat) {
						//Trying with Different types of seats in case of Invalid Seat Selection error
						if (driver.findElements(By.xpath(InVldSeat)).size()>0) {
							boolean SeatAssign = false;
							List<WebElement> SeatNm = null;
							String SplitSeat[] = (SeatPath.replace("SeatType", "Business")+";"+SeatPath.replace("SeatType", "Economy")+"//span[contains(text(),'V')]"+";"+SeatPath.replace("SeatType", "Economy")+"//span[contains(text(),'E')]").split(";");
							driver.findElement(By.xpath("//md-menu//i[contains(@class,'icon-close')]")).click();
							for (int ss = 0; ss < SplitSeat.length; ss++) {
								SeatNm = driver.findElements(By.xpath(SplitSeat[ss]));
								//business seat // Premium Economy // Emergency Seat
								for(int sel=0; sel<SeatNm.size(); sel++) {
									SeatNm.get(sel).click();
									Thread.sleep(2000);
									driver.findElement(By.xpath("//div[@translate='pssgui.update']")).click();
									FlightSearch.loadhandling(driver);
									if (driver.findElements(By.xpath(InVldSeat)).size()==0) {
										SeatAssign = true;
										break;
									} else {
										driver.findElement(By.xpath("//md-menu//i[contains(@class,'icon-close')]")).click();
									}
								}
								if (SeatAssign) {
									break;
								}
							}
						}
						if (!sSelSeatType.isEmpty()) {
							if (sCheckinMsg.equalsIgnoreCase("Split Seating")) {
								if (driver.findElements(By.xpath("//md-select[@aria-label='Passenger Seat Characteristics']")).size()==1) 
									queryObjects.logStatus(driver, Status.PASS, "Generic seat drop down for all pax ", "Single Generic seat drop down is available for all pax, Split seating is not possible ", null);
								else 
									queryObjects.logStatus(driver, Status.FAIL, "Generic seat drop down for all pax ", "Single Generic set drop verification failed", null);
							}
							driver.findElement(By.xpath("//md-select[@aria-label='Passenger Seat Characteristics']")).click();
							driver.findElement(By.xpath("//md-option/div[contains(text(),'"+sSelSeatType+"')]")).click();
							try {
								driver.findElement(By.xpath("//button[contains(text(),'Yes')]")).click();
							} catch (Exception e) { 
							// TODO: handle exception
							}
						}

						driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();
						FlightSearch.loadhandling(driver);
						if (sCheckinMsg.equalsIgnoreCase("Partial routing check In")) {
							if ((Boolean.parseBoolean(driver.findElement(By.xpath(FlightLegChkbox+"[1]")).getAttribute("aria-checked")))==true && (Boolean.parseBoolean(driver.findElement(By.xpath(FlightLegChkbox+"[2]")).getAttribute("aria-checked")))==true) {
								queryObjects.logStatus(driver, Status.PASS, "Partial routing checkin ", "Partial routing checkin is not allowed, both the legs got selected ", null);								
							}else {
								queryObjects.logStatus(driver, Status.FAIL, "Partial routing checkin ", "Partial routing checkin is allowed.", null);
							}
						}
						driver.findElement(By.xpath("//button[contains(text(),'Continue')]")).click();
						FlightSearch.loadhandling(driver);
						boolean SeatTypeStatus = false;
						List<WebElement> SeatAssigned = driver.findElements(By.xpath("//input[@ng-model='seat.NewSeatNumber']"));

						for (int j = 0; j < SeatAssigned.size(); j++) {
							SeatAssigned = driver.findElements(By.xpath("//input[@ng-model='seat.NewSeatNumber']"));

							if (!SeatAssigned.get(j).getAttribute("value").trim().isEmpty()) {
								queryObjects.logStatus(driver, Status.PASS, "Check the Seat assigned status for Segment "+j, "Seat is assigned for the route ", null);
								if (SeatTypeStatus == false && sSelSeatType.equalsIgnoreCase("Aisle") && (SeatAssigned.get(j).getAttribute("value").trim().contains("C") || SeatAssigned.get(j).getAttribute("value").trim().contains("D"))) {
									SeatTypeStatus=true;
								}				

							} 
							else 
								queryObjects.logStatus(driver, Status.FAIL, "Check the Seat assigned status for Segment "+j, "Seat is not assigned ", null);

						}
						if (SeatTypeStatus && sSelSeatType.equalsIgnoreCase("Aisle")) {
							queryObjects.logStatus(driver, Status.PASS, "Assign Aisle seat ", "Aisle Seat is assigned for the passenger", null);
						}else if (SeatTypeStatus==false && sSelSeatType.equalsIgnoreCase("Aisle")) {
							queryObjects.logStatus(driver, Status.FAIL, "Assign Aisle seat ", "Aisle Seat is not assigned ", null);
						}
					}		


				}
				
				if (sCheckinMsg.contains("Restrict")) {
					try {
						try {
                            driver.findElement(By.xpath("//div[@class='selected-flight-info']//i")).click();
                            Thread.sleep(1000);
						} catch (Exception e) {}

						AssignControllingAgent(driver, queryObjects);
						driver.findElement(By.xpath(FlightAct)).click();
						Thread.sleep(2000);
						driver.findElement(By.xpath("//md-option[contains(@value,'restrict')]")).click();
						FlightSearch.loadhandling(driver);
						boolean inform= false;
						if (driver.findElements(By.xpath("//span[contains(text(),'Unable to restrict checkin to gate: (1) SHIP NOT ASSIGNED: 10084')]")).size()>0) {
							OpenFlights_Checkin(driver, queryObjects);
							driver.findElement(By.xpath(FlightAct)).click();
							Thread.sleep(2000);
							driver.findElement(By.xpath("//md-option[contains(@value,'restrict')]")).click();
							FlightSearch.loadhandling(driver);
						}
						try {
							inform = driver.findElement(By.xpath("//span[contains(text(),'is restricted for Check-In')]")).isDisplayed();
						}catch (Exception e) {}
						if(inform) {
							queryObjects.logStatus(driver, Status.PASS, "Restrict Flight", "Flight is restricted", null);
						}else
						{
							queryObjects.logStatus(driver, Status.FAIL, "Restrict Flight", "Unable to restrict flight", null); 
						}
	
					}catch (Exception e) {queryObjects.logStatus(driver, Status.FAIL, "Restrict Flight", "Unable to restrict flight", e); }
				}
				
				if (sCheckinMsg.contains("FlightHistory")) {
					try {
						driver.findElement(By.xpath(FlightAct)).click();
						Thread.sleep(1000);
						driver.findElement(By.xpath("//md-option[contains(@value,'flightHistory')]")).click();
						FlightSearch.loadhandling(driver);
						if (driver.findElement(By.xpath("//md-dialog//div[contains(text(),'Flight History')]")).isDisplayed()) {
							
							if (sCheckinMsg.contains("Restrict")) {
								boolean isHistory = false;
								driver.findElement(By.xpath("//md-radio-button//span[contains(text(),'All History')]//../preceding-sibling::div/div")).click();
								FlightSearch.loadhandling(driver);
								sFlight = driver.findElement(By.xpath("//md-dialog//div[contains(@ng-repeat,'flightInformation.Flights')]//div[contains(@class,'pssgui-design-page-title')]")).getText().trim();
								sFrom = driver.findElement(By.xpath("//md-dialog//div[contains(@airport-code,'DepartureAirport.LocationCode')]/div[1]")).getText().trim();
								ValDate = driver.findElement(By.xpath("//md-dialog//div[contains(@class,'pssgui-design-detail-2')]")).getText().trim();
								ValDate = ValDate.substring(ValDate.indexOf("/")+1, ValDate.length());
								ValDate = ValDate.replace("-", "").trim().toUpperCase();
								sFlight = sFlight.substring(2);
								driver.findElement(By.xpath("//md-dialog//input[contains(@ng-model,'searchHistory')]")).sendKeys("CR"+sFlight+"/"+ValDate+"/"+sFrom);
								driver.findElement(By.xpath("//md-dialog//button[contains(text(),'Search')]")).click();
																																									 
								//load
								if (driver.findElements(By.xpath("//md-dialog//tr[contains(@ng-repeat,'historyList')]")).size()>0) {
									for (int sh = 1; sh <= driver.findElements(By.xpath("//md-dialog//tr[contains(@ng-repeat,'historyList')]")).size(); sh++) {
										RetVal = driver.findElement(By.xpath("//md-dialog//tr[contains(@ng-repeat,'historyList')]")).getText();
										if (RetVal.contains("CR"+sFlight+"/"+ValDate+"/"+sFrom) && RetVal.contains(driver.findElement(By.xpath("//md-dialog//div[contains(@airport-code,'DepartureAirport.LocationCode')]/div[1]")).getText().trim()) ) {
											isHistory = true;
										}									
									}
									if (isHistory) {
										queryObjects.logStatus(driver, Status.PASS, "Flight History - Restrict a flight and check the restricted event displays in Flight History", "Flight restricted event is displayed for the agent", null);
										
									} else {
										queryObjects.logStatus(driver, Status.FAIL, "Flight History - Restrict a flight and check the restricted event displays in Flight History", "Flight restricted event is not displayed for the agent", null);
									}
									
								} else {
									queryObjects.logStatus(driver, Status.FAIL, "Flight History - Check the history display", "No History available to the given command", null);
								}
								
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Check the Flight History from Actions dropdown", "Flight history popup is not displayed", null);
							}
							driver.findElement(By.xpath("//md-dialog//i[contains(@class,'icon-close')]")).click();
							
						} else {
							queryObjects.logStatus(driver, Status.FAIL, "Check the Flight History from Actions dropdown", "Flight history popup is not displayed", null);
						}
					} catch (Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Check the Flight History from Actions dropdown", "Flight history popup navigation failed", e);
					}

				}

				if (!(FlightSearch.getTrimTdata(queryObjects.getTestData("PreferUpgrade_FF")).isEmpty())) {
					try {
						//driver.findElement(By.xpath("//md-select-value/span/div[text()='Standard CheckIn']")).click();
						if (driver.findElements(By.xpath("(//md-checkbox[@ng-model='passengerData.chkBoxSelected'])")).size()==2) {
							driver.findElement(By.xpath("(//md-checkbox[@ng-model='passengerData.chkBoxSelected'])[2]")).click();
							Thread.sleep(200);
							driver.findElement(By.xpath("//md-input-container//md-select[@aria-label='Checkin Passenger Actions']")).click();//added by Jenny
							driver.findElement(By.xpath("//md-option/div[text()='Prefer Upgrade']")).click();
							try {
								driver.findElement(By.xpath("//md-radio-button[@value='PrimaryPreferUpgrade']")).click();
								driver.findElement(By.xpath("//md-select[@placeholder='Select companion code']")).click();
								driver.findElement(By.xpath("//div[contains(text(),'PR1')]")).click();
								driver.findElement(By.xpath("//button[@aria-label='Ok']")).click();
								driver.findElement(By.xpath(FinalCheckInXpath)).click();
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath(ReturnXpath)).click();
								Thread.sleep(200);
								driver.findElement(By.xpath("(//md-checkbox[@ng-model='passengerData.chkBoxSelected'])[1]")).click();
								Thread.sleep(200);
								driver.findElement(By.xpath("(//md-checkbox[@ng-model='passengerData.chkBoxSelected'])[2]")).click();
								Thread.sleep(200);
								driver.findElement(By.xpath("//md-input-container//md-select[@aria-label='Checkin Passenger Actions']")).click();//added by Jenny
								driver.findElement(By.xpath("//md-option/div[text()='Prefer Upgrade']")).click();
								driver.findElement(By.xpath("//md-radio-button[@value='CompanionPreferUpgrade']")).click();
								driver.findElement(By.xpath("//md-select[@placeholder='Select companion code']")).click();
								driver.findElement(By.xpath("//div[contains(text(),'PLC')]")).click();
								driver.findElement(By.xpath("//div[@ng-repeat='passenger in preferUpgrade.passengerList']//input[@name='ffNumber']")).sendKeys(FlightSearch.getTrimTdata(queryObjects.getTestData("PreferUpgrade_FF")));
								driver.findElement(By.xpath("//button[@aria-label='Ok']")).click();

								queryObjects.logStatus(driver, Status.PASS, "Prefer Upgrade", "Primary and Companion Updated ", null);
							
							} catch (Exception e) {
								queryObjects.logStatus(driver, Status.FAIL, "Prefer Upgrade", "Primary and Companion not Updated", null);
							}}	

					}
					catch (Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Select - Prefer Upgrade ", "Not successful ", e); 
					}
				}

				//Upggrade


				if(sUpgrade.equalsIgnoreCase("Y")) {
					try {
						// driver.findElement(By.xpath("//md-select-value/span/div[text()='Standard CheckIn']")).click();
						WebDriverWait  wait1=new WebDriverWait(driver,60);
						wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//md-input-container//md-select[@aria-label='Checkin Passenger Actions']")));
						driver.findElement(By.xpath("//md-input-container//md-select[@aria-label='Checkin Passenger Actions']")).click();
						Thread.sleep(1000);
						driver.findElement(By.xpath("//md-option/div[text()='Up/Down Grade Change']")).click();
						FlightSearch.loadhandling(driver);
						if (!GradeType.isEmpty() && GradeType.contains("invol")) {
							driver.findElement(By.xpath("//md-radio-button[@value='involuntary']/div")).click();
							Thread.sleep(1000);
							if(Class_of_Service.equalsIgnoreCase(""))
								driver.findElement(By.xpath("//input[contains(@ng-model,'classOfService')]")).sendKeys("Y");
							else
								driver.findElement(By.xpath("//input[contains(@ng-model,'classOfService')]")).sendKeys(Class_of_Service);																					 
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
	
							Payment(driver,queryObjects);// need to modify
							// driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();  //commented by kishore
							FlightSearch.loadhandling(driver);
						}
						// yashodha update 24- sep-2019  End
						driver.findElement(By.xpath(SubmitXpath)).click();
						FlightSearch.loadhandling(driver);
						List <WebElement>  sList = driver.findElements(By.xpath("//div[contains(text(),'Grade Change') and contains(@ng-if,'ActionSelected')]"));
						if (sList.size() >0)
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
				//Upgrade & Downgrade Multiple PNR

				if(sCheckinMsg.equalsIgnoreCase("Upgrade Downgrade")) {
					try {
						sOrderNum= Login.MultiplePNR.trim();
						if (sOrderNum.contains(";")) {
							String DowngradeOrder = sOrderNum.substring(0, 6);
							String UpgradeOrder = sOrderNum.substring(7, sOrderNum.length());
							String SplitOrders[] = UpgradeOrder.split(";");
							List<WebElement> ChkCnt = driver.findElements(By.xpath("(//div[contains(@ng-repeat,'airportPassenger.model.groupData')])"));
							//driver.findElement(By.xpath("//md-checkbox[@aria-label='pssgui.select.all']")).click();
							for (int i = 1; i <= ChkCnt.size(); i++) {
								driver.findElement(By.xpath("(//md-checkbox[@aria-label='pax-chk'])["+i+"]")).click();
							}//Added temporarily as select all is not working here
							Thread.sleep(100);
							for (int i = 1; i <= ChkCnt.size(); i++) {
								if (driver.findElement(By.xpath("(//div[contains(@ng-repeat,'airportPassenger.model.groupData')])["+i+"]")).getText().trim().contains(DowngradeOrder)) {
									driver.findElement(By.xpath("(//md-checkbox[@aria-label='pax-chk'])["+i+"]")).click();
								}
							}
							Upgrade_Downgrade(driver, queryObjects, "Y", "involuntary");
							driver.findElement(By.xpath("//md-checkbox[@aria-label='pssgui.select.all']")).click();
							driver.findElement(By.xpath("//md-checkbox[@aria-label='pssgui.select.all']")).click();
							for (int j = 0; j < SplitOrders.length; j++) {
								for (int k = 1; k <= ChkCnt.size(); k++) {
									if (driver.findElement(By.xpath("(//div[contains(@ng-repeat,'airportPassenger.model.groupData')])["+k+"]")).getText().trim().contains(SplitOrders[j])) {
										driver.findElement(By.xpath("(//md-checkbox[@aria-label='pax-chk'])["+k+"]")).click();
										break;
									}
								}
							}						 
							Upgrade_Downgrade(driver, queryObjects, "C", "involuntary");
							driver.findElement(By.xpath("//md-checkbox[@aria-label='pssgui.select.all']")).click();
						}	 
					}catch (Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Upgrade was not successful", "Upgraded ", e); 
					}


				}
				if(ChangeSaetFlag && status)
				{

					////suman  UPDATED ON 8th May 2018 

					SeatChangePage(driver,queryObjects);
					/*driver.findElement(By.xpath("//button[text()='Check In']")).click();
					Thread.sleep(2000);
					FlightSearch.loadhandling(driver);
					Boolean confirmok1 = driver.findElement(By.xpath(ConfirmOKXpath)).isDisplayed();*/

					/*if(confirmok1)
					{
						queryObjects.logStatus(driver, Status.PASS, "Checking Checkin confirmation", "Checking Checkin confirmation", null);	
					}
					else
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking Checkin confirmation", "Checking Checkin confirmation", null);
					}*/

					return;               ///seems defect so execution till change seats

				}
				else
				{
					queryObjects.logStatus(driver, Status.INFO, "Checking checked passanger", "Checkedin passanger not available", null);
				}

				//driver.findElement(By.xpath(DoneXpath)).click();

				//FlightSearch.loadhandling(driver);
				try {
					List <WebElement>  sList =  driver.findElements(By.xpath("//md-select[contains(@ng-model,'PassengerTypeCode')]"));
					for (int k = 0; k < sList.size(); k++) {

						
						Thread.sleep(1000);
						 if (AdditionalCheckIn.contains(";")) {
							 String[] AddcheckIn = AdditionalCheckIn.split(";");
							 AdditionalCheckIn = AddcheckIn[k];
						 }

						//driver.findElement(By.xpath("//div[contains(@class,'md-active')]//div[text()='Adult']")).click();
						if(AdditionalCheckIn.equalsIgnoreCase("")){	//indrajit
							//driver.findElement(By.xpath("//div[contains(@class,'md-active')]//div[text()='Adult']")).click();
							FlightSearch.loadhandling(driver);
						}else if (AdditionalCheckIn.equalsIgnoreCase("ADTINF")) {
							sList.get(k).click();
							queryObjects.logStatus(driver, Status.PASS, "Getting infant details ", "Infant details while CheckIn ", null);
							driver.findElement(By.xpath("//div[contains(@class,'md-active')]//div[text()='Adult With Infant']")).click();
							FlightSearch.loadhandling(driver);

							try {//Added by Jenny
								driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
								FlightSearch.loadhandling(driver);

								//added by Ramya
								// FlightSearch.AddINF(driver,queryObjects);
								queryObjects.logStatus(driver, Status.PASS, "Add infant details in Reservation", "Adding infant", null);
								driver.findElement(By.xpath("//span[@role='button']/i[@class='icon-add']")).click();
								Thread.sleep(2000);
								driver.findElement(By.xpath("//input[@name='surname']")).sendKeys("Adultpax");
								Thread.sleep(500);
								driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys("Infant");
								Thread.sleep(500);
								Calendar calc = Calendar.getInstance();
								calc.add(Calendar.DATE, -10);
								String timeStampd = new SimpleDateFormat("MM/dd/yyyy").format(calc.getTime());
								driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).sendKeys(timeStampd);
								driver.findElement(By.xpath("//md-select[contains(@ng-model,'travelsWith')]")).click();
								Thread.sleep(2000);
								driver.findElement(By.xpath("//div[contains(@id,'select_container') and contains(@class,'md-active')]//md-option[1]/div[1]")).click();
								//WebElement element=driver.findElement(By.xpath("//md-select[@aria-label='Travels with']//span[@class]"));
								//JavascriptExecutor executor=(JavascriptExecutor)driver;
								//executor.executeScript("argument[0].click();", element);
								//driver.findElement(By.xpath("//div[@role='presentation' and (@aria-hidden='false')]//md-option/div")).click();
								driver.findElement(By.xpath("//md-select[md-select-value[span [contains(text(),'Gender')] or span[div[div[contains(text(),'Male') or contains(text(),'Female')]]]]]")).click();
								Thread.sleep(1000);
								driver.findElement(By.xpath("//md-option[div[div[contains(text(),'Male')]]]")).click();
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath("//input[@name='country']")).sendKeys("US");
								Thread.sleep(1000);
								FlightSearch.loadhandling(driver);
								//driver.findElement(By.xpath("//input[@name='country']")).sendKeys(Keys.TAB);
								//Tab Alternate Solution
								try {
									driver.findElement(By.xpath(Login.SelList)).click();
								} catch (Exception e1) {
									try {
										driver.findElement(By.xpath(Login.SelList2)).click();
									} catch (Exception e) {
										try {
											driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='US']/ancestor::span[2]/following-sibling::span/span/../../..")).click();

											}catch(Exception e3) {
												driver.findElement(By.xpath("//input[@name='country']")).sendKeys(Keys.TAB);
											}
									}
								}
								Thread.sleep(2000);
								driver.findElement(By.xpath("//button[contains(text(),'Next')]")).click();
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath("//button[contains(text(),'Book & File Fare')]")).click();
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();
								FlightSearch.loadhandling(driver);
								//Himani
								driver.findElement(By.xpath("//div[contains(@class,'amount-container') and @aria-hidden='false']//md-select[@id='paymentIndex.paymentType']")).click();
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Cash')]]")));	
								driver.findElement(By.xpath("//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Cash')]]")).click();
								Thread.sleep(2000);
								driver.findElement(By.xpath("//button[@aria-label='submit payment']")).click();
								FlightSearch.loadhandling(driver);
								// Handling FOID Details::::
								FlightSearch.enterFoiddetails(driver,queryObjects);
								//Handling Email recipients popup
								FlightSearch.emailhandling(driver,queryObjects);
								driver.findElement(By.xpath("//button[text()='Done']")).click();
								FlightSearch.loadhandling(driver);
								queryObjects.logStatus(driver, Status.PASS, "Infant Payment Done", "Added infant to the PNR", null);
								driver.findElement(By.xpath("//div[contains(text(),'Back')]")).click();
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath("//button[text()='Check In']")).click();
								FlightSearch.loadhandling(driver);
								/*try {//Recent Update Coupon Control
									if(driver.findElements(By.xpath("//div//p[contains(text(),'Ticket Status is CNTRL')]")).size()>0){
										FlightSearch.coupon_control(driver, queryObjects, "ALL", "ALL");
									}
								} catch (Exception e) {}*/
								if(driver.findElements(By.xpath("//button[contains(text(),'OK')]")).size()>0){
									driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
									FlightSearch.loadhandling(driver);
								}
								driver.findElement(By.xpath("//button[contains(text(),'Next')]")).click();
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath("//input[@name='Surname']")).sendKeys("Adultpax");
								Thread.sleep(1000);
								driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys("Infant");
								Thread.sleep(1000);
								driver.findElement(By.xpath("//pssgui-date-time[contains(@ng-model,'document.BirthDate')]//input[@class='md-datepicker-input']")).sendKeys(timeStampd);
								Thread.sleep(1000);
								driver.findElement(By.xpath("//md-select[md-select-value[span [contains(text(),'Gender')] or span[div[div[contains(text(),'Male') or contains(text(),'Female')]]]]]")).click();
								Thread.sleep(1000);
								driver.findElement(By.xpath("//md-option[@role='option']//div[contains(text(),'Male')]")).click();
								driver.findElement(By.xpath(DocTypeXpath)).click();
								driver.findElement(By.xpath("//md-option[div[normalize-space(text())='Passport']]")).click();
								Thread.sleep(1000);
								driver.findElement(By.xpath(DocIDXpath)).sendKeys("48563784693");
								Thread.sleep(1000);

								Calendar calc1 = Calendar.getInstance();
								calc1.add(Calendar.DATE, +20);
								String timeStampd1 = new SimpleDateFormat("MM/dd/yyyy").format(calc1.getTime());
								driver.findElement(By.xpath(ExpdateXpath)).sendKeys(timeStampd1);

								//driver.findElement(By.xpath("//span[contains(text(),'Infant')]")).click();
								driver.findElement(By.xpath(COIXpath)).clear();
								driver.findElement(By.xpath(COIXpath)).sendKeys("US");
								FlightSearch.loadhandling(driver);
								Thread.sleep(1000);
								//driver.findElement(By.xpath(COIXpath)).sendKeys(Keys.TAB);
								//Tab Alternate Solution
								try {
									driver.findElement(By.xpath(Login.SelList)).click();
								} catch (Exception e1) {
									try {
										driver.findElement(By.xpath(Login.SelList2)).click();
									} catch (Exception e) {
										try {
											driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='US']/ancestor::span[2]/following-sibling::span/span/../../..")).click();

											}catch(Exception e3) {
												driver.findElement(By.xpath(COIXpath)).sendKeys(Keys.TAB);
											}
									}
								}
								Thread.sleep(2000);
								driver.findElement(By.xpath(NtionalXpath)).clear();
								driver.findElement(By.xpath(NtionalXpath)).sendKeys("US");
								FlightSearch.loadhandling(driver);
								Thread.sleep(1000);
								//driver.findElement(By.xpath(NtionalXpath)).sendKeys(Keys.TAB);
								//Tab Alternate Solution
								try {
									driver.findElement(By.xpath(Login.SelList)).click();
								} catch (Exception e1) {
									try {
										driver.findElement(By.xpath(Login.SelList2)).click();
									} catch (Exception e) {
										try {
											driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='US']/ancestor::span[2]/following-sibling::span/span/../../..")).click();

											}catch(Exception e3) {
												driver.findElement(By.xpath(NtionalXpath)).sendKeys(Keys.TAB);
											}
									}
								}
								Thread.sleep(2000);
								driver.findElement(By.xpath(CORXpath)).clear();
								driver.findElement(By.xpath(CORXpath)).sendKeys("US");
								FlightSearch.loadhandling(driver);
								Thread.sleep(1000);
								//driver.findElement(By.xpath(CORXpath)).sendKeys(Keys.TAB);
								//Tab Alternate Solution
								try {
									driver.findElement(By.xpath(Login.SelList)).click();
								} catch (Exception e1) {
									try {
										driver.findElement(By.xpath(Login.SelList2)).click();
									} catch (Exception e) {
										try {
											driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='US']/ancestor::span[2]/following-sibling::span/span/../../..")).click();

											}catch(Exception e3) {
												driver.findElement(By.xpath(CORXpath)).sendKeys(Keys.TAB);
											}
									}
								}
								Thread.sleep(3000);
								driver.findElement(By.xpath(EmernameXpath)).clear();
								driver.findElement(By.xpath(EmernameXpath)).sendKeys("emergencydefault");
								Thread.sleep(1000);
								driver.findElement(By.xpath(EmerPhXpath)).clear();
								driver.findElement(By.xpath(EmerPhXpath)).sendKeys("12345634");
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath(SubmitXpath)).click();
								FlightSearch.loadhandling(driver);
								Boolean nextbt;
								if(sExitDays.equalsIgnoreCase("No") && driver.findElements(By.xpath("//div[@class='ng-binding msg-error' and contains(text(),'PLEASE PROVIDE EXIT DATE TIME')]")).size()==0) {
									queryObjects.logStatus(driver, Status.PASS, "No need for exit date", "No return segment", null);
								} 
								else {
									if (!sExitDays.isEmpty() || driver.findElements(By.xpath("//div[@class='ng-binding msg-error' and contains(text(),'PLEASE PROVIDE EXIT DATE TIME')]")).size()>0) {
										try {
											driver.findElement(By.xpath("//button[text()='OK']")).click();
											FlightSearch.loadhandling(driver);
										} catch (Exception e) {}
										do{
											if(driver.findElements(By.xpath(ExitdateXpath)).size()>0 && driver.findElement(By.xpath(ExitdateXpath)).isEnabled()){
												Calendar calend = Calendar.getInstance();
												String sExitDate="";
												calend.add(Calendar.DATE, +10);
												sExitDate = new SimpleDateFormat("MM/dd/yyyy").format(calend.getTime());
												try {
												driver.findElement(By.xpath(ExitdateXpath)).sendKeys(sExitDate);
												Thread.sleep(1000);
												driver.findElement(By.xpath(ExitdateXpath)).sendKeys(Keys.TAB);
												//Himani
												driver.findElement(By.xpath(ExitdateJustificationXpath)).sendKeys("Justification");
												}catch(Exception e){}
											}
											nextbt=driver.findElements(By.xpath(NextXpath)).size()>0;
											if (nextbt)
												driver.findElement(By.xpath(NextXpath)).click();
										}while(nextbt);
									}
								}
								
								if (sCheckinMsg.contains("ADC Auto Bypass")) {
									if(driver.findElements(By.xpath("//span[contains(text(),'ADC Auto Bypassed;')]")).size()> 0 && driver.findElements(By.xpath(APISComplete)).size()> 0) {
										queryObjects.logStatus(driver, Status.PASS, "Security Doc Verification screen - Check the ADC Auto bypassed message display for the passenger with Nationality same as Final destination", "ADC Auto Bypassed; APIS Complete message is displayed", null);
									} else {
										queryObjects.logStatus(driver, Status.FAIL, "Security Doc Verification screen - Check the ADC Auto bypassed message display for the passenger with Nationality same as Final destination", "ADC Auto Bypassed; APIS Complete message is not displayed", null);
									}						
								}
								//Himani
								driver.findElement(By.xpath(SubmitXpath)).click();
								FlightSearch.loadhandling(driver);
								if ((driver.findElements(By.xpath("//td[contains(text(),'ADCNOTAVAILABLE')]")).size()>0 || driver.findElement(By.xpath("//div[contains(@ng-repeat,'adcDecision.passengers')]//span")).getText().contains("ADC NOT OK")) && BypassADC.contains("ADCdown")) {//Jenny -ADCNOTAVILABLE
									ByPassADC_ADCErr_Down(driver, queryObjects);
								}
								driver.findElement(By.xpath(DoneXpath)).click();
								FlightSearch.loadhandling(driver);
								queryObjects.logStatus(driver, Status.PASS, "Infant details added sucessfully", "Added infant", null);


							} catch (Exception e) {
								// TODO: handle exception
								queryObjects.logStatus(driver, Status.FAIL, "Issue while adding Infant", "Flow Issue" +e.getStackTrace()[0].getLineNumber(),e);
								return;
							}
							if (!sCheckinMsg.contains("ADC Auto Bypass")) {//Added by Jenny 12 Nov 2019
								try {
									driver.findElement(By.xpath("//button[text()='Check In']")).click();
									FlightSearch.loadhandling(driver);
									/*try {//Recent Update Coupon Control
										if(driver.findElements(By.xpath("//div//p[contains(text(),'Ticket Status is CNTRL')]")).size()>0){
											FlightSearch.coupon_control(driver, queryObjects, "ALL", "ALL");
										}
									} catch (Exception e) {}*/
									if(driver.findElements(By.xpath("//div[contains(text(),'Security Document Verification')]")).size()>0){
										driver.findElement(By.xpath("//div[contains(text(),'Security Document Verification')]")).click();
										EnterDocCheck(driver,queryObjects);
										driver.findElement(By.xpath(SubmitXpath)).click();
										FlightSearch.loadhandling(driver);
										driver.findElement(By.xpath(DoneXpath)).click();
										FlightSearch.loadhandling(driver);
									}
									driver.findElement(By.xpath("//button[text()='Check In']")).click();
									FlightSearch.loadhandling(driver);
									driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
									FlightSearch.loadhandling(driver);
								}catch(Exception e) {}
							}
						}else if (AdditionalCheckIn.equalsIgnoreCase("child")){
							sList.get(k).click();
							driver.findElement(By.xpath("//div[contains(@class,'md-active')]//div[text()='Child']")).click();
						}


					}
				}catch(Exception e) {}
				if (!sCheckinMsg.contains("ADC Auto Bypass")) {//Added by Jenny 12 Nov 2019
					try {
						driver.findElement(By.xpath("//md-select[@ng-model='passengerData.ShortCheckAirportCode']")).click();
						FlightSearch.loadhandling(driver);
																																																																																		 
						driver.findElement(By.xpath("(//div[contains(@class,'md-select-menu-container') and contains(@aria-hidden,'false')]//md-option[@ng-repeat='code in passengerData.shortCheckinCodes']/div/..)[1]")).click();
	
					} catch (Exception e) {
						queryObjects.logStatus(driver, Status.INFO, "Selecting ShortCheck Airport Code", "Selection failed or field does not exist ", e);;
					}
	
					if (FilterFgtList.equalsIgnoreCase("Misconnected Pax")||FilterFgtList.equalsIgnoreCase("InboundCompensate")) {
	
	
						try {
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath(SelTypeIcon)).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath(AltFgtList)).click();
							FlightSearch.loadhandling(driver);
							if (driver.findElement(By.xpath("//div[contains(@selected-flights,'alternateFlight.selectedFlights')]")).isDisplayed()) {
								List<WebElement> AltFgtList = driver.findElements(By.xpath("//td[@class='flight-name']"));
								if (AltFgtList.size()>1) {
									for (int i = 2; i <= AltFgtList.size(); i++) {
										int k = i-1;
										if (driver.findElement(By.xpath("(//td[@class='flight-name'])["+i+"]")).getText().trim().length()<6) {
											// driver.findElement(By.xpath(AltFgtSelButton+k+"]")).click();
											driver.findElement(By.xpath(AltFgtSelButton+"["+k+"]")).click();
											break;
										}								
									}							
								}
								driver.findElement(By.xpath(InvolXpath)).click();    // need add vol case .......kishore
								Thread.sleep(2000);
								driver.findElement(By.xpath(ReasonXpath)).click();
								Thread.sleep(2000);
								driver.findElement(By.xpath("//md-option/div[contains(text(),'DELAY')]")).click();
								Thread.sleep(2000);
								driver.findElement(By.xpath(SubmitBtnXpath)).click();
								Thread.sleep(2000);
							}
						} catch (Exception e) {
							queryObjects.logStatus(driver, Status.FAIL, "exception", "exception", e);
							// TODO: handle exception
						}
	
					}
	
					
	
					if (!AddBaggagekg.isEmpty()|| !MulBaggageWgt.isEmpty()) {			
	
						try {
							if(BagForAllPax.equalsIgnoreCase("Y")) {
								List<WebElement> AddBaggage =  driver.findElements(By.xpath(BaggageXpath));
	
								for (int i = 0; i < AddBaggage.size(); i++) {
									AddBaggage =  driver.findElements(By.xpath(BaggageXpath));
									AddBaggage.get(i).click();
									if(driver.findElements(By.xpath("//div[5]/md-dialog/md-dialog-content/h2[contains(text(),'Update Baggage')]")).size()>0) {
										driver.findElement(By.xpath("//md-dialog/md-dialog-actions/button[2]")).click();
										Thread.sleep(1000);
										driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();
										FlightSearch.loadhandling(driver);
										if(driver.findElements(By.xpath("//div[@class='ng-binding msg-error' and contains(text(),'PLEASE PROVIDE EXIT DATE TIME')]")).size()>0){
											
											driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
											FlightSearch.loadhandling(driver);
											Calendar calend = Calendar.getInstance();
											String sExitDate="";
											calend.add(Calendar.DATE, +10);
											sExitDate = new SimpleDateFormat("MM/dd/yyyy").format(calend.getTime());
											driver.findElement(By.xpath(ExitdateXpath)).sendKeys(sExitDate);
											Thread.sleep(1000);
											driver.findElement(By.xpath(ExitdateXpath)).sendKeys(Keys.TAB);
											driver.findElement(By.xpath(ExitdateJustificationXpath)).sendKeys("Justification");
											Thread.sleep(1000);
											driver.findElement(By.xpath(ExitdateJustificationXpath)).sendKeys(Keys.TAB);
											Thread.sleep(1000);
											driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();
											FlightSearch.loadhandling(driver);
											driver.findElement(By.xpath("//button[contains(text(),'Done')]")).click();		
											FlightSearch.loadhandling(driver);
										}
									}
									CompleteBaggage(driver,queryObjects, AddBaggagekg, AllowBag, MulBaggageWgt, MulBaggageType, BaggageValue, Catalog);
								} //for loop 
							}// /if all pax
	
							if(!BagForSpecificPax.isEmpty()) {
								if (BagForSpecificPax.contains(";")) {
									String SplitMulBag[] = BagForSpecificPax.split(";");
									String SplitMulBagWGt[] = MulBaggageWgt.split(";");
									String SplitBagType[] = MulBaggageType.split(";");
									
									for (int p = 0; p < SplitMulBag.length; p++) {
										driver.findElement(By.xpath("//span[contains(text(),'"+SplitMulBag[p]+"')]/ancestor::div[contains(@ng-repeat,'orderPassengers')]//i[@class='icon-baggage']/../span")).click();
										FlightSearch.loadhandling(driver);
										CompleteBaggagesp(driver,queryObjects, AddBaggagekg, AllowBag, SplitMulBagWGt[p], SplitBagType[p], BaggageValue, Catalog);
									}
								}else {
									driver.findElement(By.xpath("//span[contains(text(),'"+BagForSpecificPax+"')]/ancestor::div[contains(@ng-repeat,'orderPassengers')]//i[@class='icon-baggage']/../span")).click();	 
									FlightSearch.loadhandling(driver);
									CompleteBaggage(driver,queryObjects, AddBaggagekg, AllowBag, MulBaggageWgt, MulBaggageType, BaggageValue, Catalog);
						
								} 
	
																																																																																																																																					 
								}
	
						}catch (Exception e) {
							
							queryObjects.logStatus(driver, Status.FAIL, "Add Baggage", e.getLocalizedMessage(), e);
						}
						
					}
						//Alternate flight
						//if(sAlter.equalsIgnoreCase("Y")) {
						if(!sAlter.isEmpty()) {
							try {
								//driver.findElement(By.xpath("//md-select-value/span/div[text()='Standard CheckIn']")).click();
								driver.findElement(By.xpath("//md-input-container//md-select[@aria-label='Checkin Passenger Actions']")).click();//added by Jenny
								driver.findElement(By.xpath("//md-option/div[text()='Alternate Flight']")).click();
								FlightSearch.loadhandling(driver);
								//button[text()='Select']
								
								List<WebElement> arr = driver.findElements(By.xpath("//button[text()='Select']"));
								if(!AltFlight_num.isEmpty())
								{
										driver.findElement(By.xpath("//tr[contains(@ng-repeat,'flightResult')]//span[contains(text(),'"+AltFlight_num+"')]/following::td[5]//button")).click();
									Thread.sleep(1000);
									ISharesflow.Flight = AltFlight_num;
								/*	List<WebElement> fltnum = driver.findElements(By.xpath("//pssgui-alternate-flight//md-content//td[1]"));
									for(int i= 1;i<=arr.size();i++)
									{
										if(fltnum.get(i).getText().contains(AltFlight_num))
										{
											arr.get(i).click();;
											break;
										}
									}*/
								}
								else
									//pssgui-alternate-flight//md-content//toggle-content//tr[1]/td[1]
									//Select the Open Flight
									for (int af = 0; af < arr.size(); af++) {
										try {
											if (driver.findElements(By.xpath("(//button[text()='Select'])["+(af+1)+"]/../..//preceding-sibling::td[contains(@class,'status')]/div[contains(text(),'Open')]")).size()>0 || driver.findElements(By.xpath("(//button[text()='Select'])["+(af+1)+"]/../..//preceding-sibling::td[contains(@class,'status')]/div[contains(text(),'Gate')]")).size()>0) {
												arr.get(af).click();
												FlightSearch.loadhandling(driver);
											}
										} catch (Exception e) {
											arr.get(1).click();
										}
									}
		
								// driver.findElement(By.xpath("//button[text()='Involuntary']")).click();
								if(sAlter.equalsIgnoreCase("vol")) {
									
									driver.findElement(By.xpath("//button[text()='Voluntary']")).click();
									FlightSearch.loadhandling(driver);
									// vol amount checking for Q class-- yashodha
									String TOtalamount=driver.findElement(By.xpath("//div[@translate='pssgui.total']/following::div[1]")).getText();
									String []amount=TOtalamount.split(" ");
									String TOtalamount1=amount[0];
									double totalamntd =Double.parseDouble(TOtalamount1);
									  
									//TOtalamount1= TOtalamount1
									int totalamnt=(int)totalamntd;
									if(totalamnt>=75) 
										queryObjects.logStatus(driver, Status.PASS, "Alternate  Flght amount", "Amount is more than 75plus tax",null);
									else 
										queryObjects.logStatus(driver, Status.FAIL, "Alternate  Flght amount", "mismatch",null);
									
									driver.findElement(By.xpath("//button[contains(text(),'Proceed to Pay')]")).click();
									FlightSearch.loadhandling(driver);
	
									Payment(driver,queryObjects);
									// driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();
									FlightSearch.loadhandling(driver);
								}else {
									driver.findElement(By.xpath("//button[text()='Involuntary']")).click(); 
									FlightSearch.loadhandling(driver);
									driver.findElement(By.xpath("//md-select[@aria-label='Reason Code']")).click();
									Thread.sleep(1000);
									List<WebElement> reason =driver.findElements(By.xpath("//md-option[contains(@ng-repeat,'reasonCode')]"));
									if (InvolReason.isEmpty()) {
										reason.get(0).click();
									}
									else {
										driver.findElement(By.xpath("//md-option[contains(@ng-repeat,'reasonCode')]//div[contains(text(),'"+InvolReason+"')]")).click(); 	
									}
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
	
					//AddPassenger Func --RAMYA 7-4-20
						
					if(!AddPassenger.equals("")) {
						FlightSearch.loadhandling(driver);
						try {
						driver.findElement(By.xpath("//button[contains(text(),'Add Passenger')]")).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//input[@name='passenger']")).sendKeys(AddPassenger);
						Thread.sleep(3000);
						driver.findElement(By.xpath("(//span[contains(text(),'"+AddPassenger+"')])[1]")).click();
						Thread.sleep(3000);
						driver.findElement(By.xpath("//button[text()='OK']")).click();
						FlightSearch.loadhandling(driver);
						EnterDocCheck(driver,queryObjects);
						Thread.sleep(3000);
						}catch(Exception e){
							queryObjects.logStatus(driver, Status.FAIL,"Exception while add passenger","Exception",e);//
						}
								
					}
					if(!FqtvUpdate.equals("") && !sCheckinMsg.equalsIgnoreCase("FQTV UPDATE")){
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//input[@aria-label='ffNumber']")).clear();
						driver.findElement(By.xpath("//input[@aria-label='ffNumber']")).sendKeys("1");
						driver.findElement(By.xpath("//input[@aria-label='ffNumber']")).clear();
						driver.findElement(By.xpath("//input[@aria-label='ffNumber']")).sendKeys(FqtvUpdate);
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath(FinalCheckInXpath)).click();
						FlightSearch.loadhandling(driver);
						
					}
					String Chargable_seat= FlightSearch.getTrimTdata(queryObjects.getTestData("Chargable_seat"));
					if(Chargable_seat.equalsIgnoreCase("Y"))
					{
						driver.findElement(By.xpath(passangerActionSeat)).click();	 
						driver.findElement(By.xpath(UpgradeSeatXpath)).click();
						FlightSearch.loadhandling(driver);
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//md-radio-button[@value='voluntary']/div")).click();
						queryObjects.logStatus(driver, Status.PASS,"voluntary Upgrade seat","voluntary Upgrade seat",null);//
						Thread.sleep(1000);
	
						driver.findElement(By.xpath("//input[@name='class']")).sendKeys("C");
						if(!driver.findElement(By.xpath("//div[contains(@ng-click,'selectSeat(passenger') and contains(@class,'selected')]")).isDisplayed())
						{
							driver.findElement(By.xpath(SelectPaxSeatChangeXpath)).click();
						}
						List<WebElement> ChargableSeat=driver.findElements(By.xpath(BusinessClassSeatChangeXpath));
						Random randomGenerator= new Random();
						int ind=randomGenerator.nextInt(ChargableSeat.size());
						ChargableSeat.get(ind).click();
						FlightSearch.loadhandling(driver);
						///driver.findElement(By.xpath(ProceedToPayChangeXpath)).click();
						Thread.sleep(1000);
						FlightSearch.loadhandling(driver);
						/*Payment(driver,queryObjects);
					 FlightSearch.loadhandling(driver);
					 driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();
					 FlightSearch.loadhandling(driver);*/
						Boolean CloseReport1=driver.findElements(By.xpath("//div[contains(@aria-disabled,'true')]/span[contains(text(),'Proceed to Pay')]")).size() >0; 
						if(CloseReport1)
	
						{
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
						else
						{   driver.findElement(By.xpath("//span[contains(text(),'Proceed to Pay')]")).click();
						FlightSearch.loadhandling(driver);
	
						Payment(driver,queryObjects);// need to modify
						// driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();  //commented by kishore
						FlightSearch.loadhandling(driver);
	
						}
					}
			//----------------------------Check the ADC, APIS icon color----------------------------------------------//
					if (!Icon_Validatn.isEmpty()) {
						String ColorVal = ""; String SpltColor[] = null; String  IcnName = "";
						SpltColor = Icon_Validatn.split("-");
						for (int ic = 0; ic < SpltColor.length; ic++) {
							ColorVal = ""; IcnName = "";
							if (SpltColor[ic].contains("ADC")) {
								ColorVal = driver.findElement(By.xpath("//i[contains(@class,'icon-adc')]")).getCssValue("color"); IcnName = "ADC";
							} else if (SpltColor[ic].contains("APIS")) {
								ColorVal = driver.findElement(By.xpath("//i[contains(@class,'icon-apis')]")).getCssValue("color"); IcnName = "APIS";
							} else if (SpltColor[ic].contains("INF")) {
								ColorVal = driver.findElement(By.xpath("//i[contains(@class,'icon-infant')]")).getCssValue("color"); IcnName = "INFANT";
							} else if (SpltColor[ic].contains("PAX")) {
								ColorVal = driver.findElement(By.xpath("//i[contains(@ng-class,'iconPaxChar')]")).getCssValue("color"); IcnName = "PAXCHAR";
							}
							if (SpltColor[ic].contains(ColorsValidation(ColorVal))) {
								queryObjects.logStatus(driver, Status.PASS, "Check the "+IcnName+" icon color display in Proceed to Checkin page", IcnName+ " icon displayed in "+SpltColor[ic].replace(IcnName, "")+ " color. Validation successful" , null);
							}else {
								queryObjects.logStatus(driver, Status.FAIL, "Check the "+IcnName+" icon color display in Proceed to Checkin page", IcnName+ "icon is not in "+SpltColor[ic].replace(IcnName, "")+ " color. Validation failed" , null);
							}					
						}
					}
					
					try {
						driver.findElement(By.xpath(ProceedCheckInXpath)).click();
						FlightSearch.loadhandling(driver);
						}catch(Exception e){}
					
						try {


						driver.findElement(By.xpath(DoneXpath)).click();
						FlightSearch.loadhandling(driver);
	
					}catch(Exception e){}
					try{
						if (FlightSearch.getTrimTdata(queryObjects.getTestData("Checkin_Disabled")).equalsIgnoreCase("Y")) {
							if (driver.findElement(By.xpath(FinalCheckInXpath)).isEnabled()) {
								queryObjects.logStatus(driver, Status.FAIL, "PAX with ESTA/ETA doc/INHIBITED verification", "Checkin is not disabled " , null);
							}else {
								queryObjects.logStatus(driver, Status.PASS, "PAX with ESTA/ETA doc/INHIBITED PAX verification", "Checkin is disabled " , null);
							}
						}
	
					}catch(Exception e){
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FinalCheckInXpath)));
						driver.findElement(By.xpath(FinalCheckInXpath)).click();
						FlightSearch.loadhandling(driver);
					}
	
	
					boolean ErrDisplayed = false;
					try {
						ErrDisplayed =  driver.findElement(By.xpath(ErrXpath)).isDisplayed();
	
					}catch(Exception e){}
	
					if(!sErr.isEmpty() && ErrDisplayed ) {
						boolean Err = driver.findElement(By.xpath("//*[contains(text(),'"+sErr+"')]")).isDisplayed();
	
						if (Err) {
	
							queryObjects.logStatus(driver, Status.PASS, " Expected Error Message displayed "+sErr, "Expected Err dispalyed ", null);
						}	
						else {
	
							queryObjects.logStatus(driver, Status.FAIL, "UnExpected Page dispalyed", "UnExpected Page dispalyed " , null); 
						}
	
					}else if (ErrDisplayed) {
						queryObjects.logStatus(driver, Status.FAIL, "UnExpected Err dispalyed", "UnExpected Err dispalyed " , null); 
						driver.findElement(By.xpath(OKXpath)).click();
					}
	
	
					//Click Checkin button if it is not selected
					try {
						if (!FlightSearch.getTrimTdata(queryObjects.getTestData("Checkin_Disabled")).equalsIgnoreCase("Y")) {
							if (!(FlightSearch.getTrimTdata(queryObjects.getTestData("BagAmt")).isEmpty())){
								if (driver.findElement(By.xpath("//i[@class='icon-subtrack in-active-state']")).isDisplayed()){
									queryObjects.logStatus(driver, Status.PASS, "Check baggage minus icon status", "Minus button is disabled for the baggage already paid" , null);
								}
	
							}
							//driver.findElement(By.xpath(FinalCheckInXpath)).click();
							//FlightSearch.loadhandling(driver);
						}
						
		try {
							if(driver.findElement(By.xpath("//md-checkbox[contains(@aria-label,'pssgui.select.all')]")).getAttribute("aria-checked").contains("false")) {
								
								driver.findElement(By.xpath("//md-checkbox[contains(@aria-label,'pssgui.select.all')]//div")).click();
								Thread.sleep(1000);
							}
						} catch (Exception e) {}
						 
						driver.findElement(By.xpath(FinalCheckInXpath)).click();
						FlightSearch.loadhandling(driver);
						// add handling of non ticketed pnr after checkin rushil
						if(NonTktPNR_Chkin.equalsIgnoreCase("yes")) {
						if(driver.findElement(By.xpath("//md-dialog[contains(@aria-label,'Redirect Confirmation')]")).isDisplayed())
						{
							driver.findElement(By.xpath("//md-dialog[contains(@aria-label,'Redirect Confirmation')]//button[contains(text(),'Cancel')]")).click();
						}
						Thread.sleep(3000);
						return;
						}
						// handling out of sync ticket rushil		
						try {		
							ErrDisplayed =  driver.findElement(By.xpath(ErrXpath)).isDisplayed();		
				
						}catch(Exception e){}		
								
						if (ErrDisplayed) {		
							//Atul - 23Mar Return if ticket is out of sync
							String OutOfSync_DoNothing = FlightSearch.getTrimTdata(queryObjects.getTestData("OutOfSync_DoNothing"));
							if (OutOfSync_DoNothing.equalsIgnoreCase("yes")) {
								queryObjects.logStatus(driver, Status.INFO, "Ticket is Out Of Sync", "Ticket is Out Of Sync" , null);
								return;
							} else {
								queryObjects.logStatus(driver, Status.INFO, "UnExpected Err dispalyed", "UnExpected Err dispalyed " , null); 		
								driver.findElement(By.xpath(OKXpath)).click();
							}	
						}		
						FlightSearch.loadhandling(driver);		
						// resgui page voluntary reissue rushil		
						if(!reissue.isEmpty()) {		
							driver.findElement(By.xpath("//div[div[text()='Order']]")).click();		
							FlightSearch.loadhandling(driver);		
							driver.findElement(By.xpath("//md-select[contains(@aria-label,'Actions')]")).click();				
							Thread.sleep(2000);		
							if(reissue.equalsIgnoreCase("vol")) {		
								driver.findElement(By.xpath("//md-option[div[contains(text(),'Voluntary Reissue')]]")).click();		
								FlightSearch.loadhandling(driver);		
								driver.findElement(By.xpath("//md-checkbox[contains(@aria-label,'coupon-check')]")).click();		
								FlightSearch.loadhandling(driver);		
								driver.findElement(By.xpath("//button[contains(text(),'Next')]")).click();			
								//wait till loading closed		
								FlightSearch.loadhandling(driver);		
								driver.findElement(By.xpath("//button[text()='Add To Order']")).click();		
								FlightSearch.loadhandling(driver);		
								driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();		
								FlightSearch.loadhandling(driver);		
							}		
							else if(reissue.equalsIgnoreCase("invol")) {		
								driver.findElement(By.xpath("//div[contains(text(),'Involuntary Reissue')]/parent::md-option")).click();		
								//Select process		
								FlightSearch.loadhandling(driver);		
								driver.findElement(By.xpath("//md-select[@aria-label='waiver process']")).click();		
								FlightSearch.loadhandling(driver);		
								driver.findElement(By.xpath("//md-option[@ng-value='ProcessList' and @role='option']")).click();		
								FlightSearch.loadhandling(driver);		
								//Select Reason Code		
								driver.findElement(By.xpath("//md-select[@aria-label='waiver reason']")).click();		
								FlightSearch.loadhandling(driver);		
								driver.findElement(By.xpath("//div[@aria-hidden='false']//md-select-menu[contains(@class,'md md-overflow')]//md-option[1]")).click();		
								FlightSearch.loadhandling(driver);		
								driver.findElement(By.xpath("//md-checkbox[contains(@aria-label,'coupon-check')]")).click();		
								FlightSearch.loadhandling(driver);		
								driver.findElement(By.xpath("//button[contains(text(),'Quote')]")).click();		
								FlightSearch.loadhandling(driver);		
								driver.findElement(By.xpath("//button[text()='Reissue ticket']")).click();		
								FlightSearch.loadhandling(driver);		
								driver.findElement(By.xpath("//button[contains(text(),'Check Out')]")).click();		
								FlightSearch.loadhandling(driver);		
							}		
							driver.findElement(By.xpath("//button[contains(text(),'Reissue')]")).click();		
							FlightSearch.loadhandling(driver);		
							FlightSearch.loadhandling(driver);		
							driver.findElement(By.xpath("//button[text()='Pay']")).click();		
							FlightSearch.loadhandling(driver);		
							//driver.findElement(By.xpath("//button[contains(text(),'Issue Emd')]")).click();		
							FlightSearch.enterFoiddetails(driver,queryObjects);		
							FlightSearch.loadhandling(driver);		
							FlightSearch.emailhandling(driver, queryObjects);		
							//Handling Email recipients popup		
							driver.findElement(By.xpath("//button[contains(text(),'Done')]")).click();		
							FlightSearch.loadhandling(driver);		
							return;		
						}
					}catch(Exception e){}
					//MEENU					
					//DO SEAT CHANGE JUST BEFORE CONFIRMATION
					if (SeatChngBfr.equalsIgnoreCase("Y"))
					{
					driver.findElement(By.xpath("//md-dialog//button[@aria-label='Cancel']")).click();
					FlightSearch.loadhandling(driver);
					SeatChangePage(driver,queryObjects);
					if(driver.findElement(By.xpath("//md-content//pssgui-passenger-itinerary//md-checkbox[contains(@ng-model,'passengerData.chkBoxSelected')]")).getAttribute("aria-checked").equalsIgnoreCase("false")) {
						driver.findElement(By.xpath("//md-content//pssgui-passenger-itinerary//md-checkbox[contains(@ng-model,'passengerData.chkBoxSelected')]/div[1]")).click();
					}
					driver.findElement(By.xpath(FinalCheckInXpath)).click();
					FlightSearch.loadhandling(driver);
					}	
					//end
					
					//short checkin info message validation and select the short check	//cc
					if(driver.findElements(By.xpath("//md-dialog//span[contains(text(),'NEED BAG DESTINATION CITY')]")).size()>0) {
						queryObjects.logStatus(driver, Status.PASS, "Need to select the short check destination", "Short check checkin for given PNR", null);
						driver.findElement(By.xpath("//md-dialog//button[@aria-label='Cancel']")).click();
						FlightSearch.loadhandling(driver);
						try {
							String sShortcheckin = new String();
							if(driver.findElements(By.xpath("//md-select[@ng-model='passengerData.ShortCheckAirportCode']")).size()>0) {  //updated navira 4-Nov
								driver.findElement(By.xpath("//md-select[@ng-model='passengerData.ShortCheckAirportCode']")).click();
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath("(//md-option[@ng-repeat='code in passengerData.shortCheckinCodes']/div/..)[1]")).click();
								sShortcheckin = driver.findElement(By.xpath("(//md-option[@ng-repeat='code in passengerData.shortCheckinCodes']/div/..)[1]")).getText().trim();
							}
							else if(driver.findElements(By.xpath("//label[text()='Short Check']/following-sibling::input")).size()>0) { //updated navira 4-Nov
								driver.findElement(By.xpath("//label[text()='Short Check']/following-sibling::input")).click();
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath("//label[text()='Short Check']/following-sibling::input")).clear();
								sShortcheckin = FlightSearch.getTrimTdata(queryObjects.getTestData("ShortCheckinDest"));
								if (sShortcheckin.isEmpty()) {
									sShortcheckin = driver.findElement(By.xpath("//div[contains(@class,'passenger-list')]//i[@class='icon-forward icon-forward-arrow']/../span[2]")).getText().trim();
								}
								driver.findElement(By.xpath("//label[text()='Short Check']/following-sibling::input")).sendKeys(sShortcheckin);
							}
							queryObjects.logStatus(driver, Status.PASS, "Short checkin route for the given PNR is "+sShortcheckin+"", "Short checkin done", null);
						} catch (Exception e) {
							queryObjects.logStatus(driver, Status.FAIL, "Short checkin failed", "Unable to process short checkin", e);;
						}
						driver.findElement(By.xpath(FinalCheckInXpath)).click();
						FlightSearch.loadhandling(driver);
					}
					
					//validation of check in success
					boolean confirmok = false;
					try {
						confirmok = driver.findElement(By.xpath(ConfirmOKXpath)).isDisplayed();
	
					}catch (Exception e) {}
	
					if (confirmok) {
						if(SearchBagTag.equalsIgnoreCase("Y")|| (FilterFgtList.equalsIgnoreCase("Misconnected Pax")) || (!(FlightSearch.getTrimTdata(queryObjects.getTestData("PreferUpgrade_FF")).isEmpty())) || ValFlight.equalsIgnoreCase("GetFlightNum")|| SearchSeatNumber.equalsIgnoreCase("Y")){
							//getting the flight from checkin screen
							try{
								Flightnumber = driver.findElement(By.xpath("//div[@class='confirmation-details']//span[contains(text(),'CM')]")).getText();
								Flightnumber1 = Flightnumber.split(" ");
								Flightnumber = Flightnumber1[0].replace("CM","");
							}catch(Exception e){
								queryObjects.logStatus(driver, Status.FAIL, "Not able to fetch the flight number", "Flight number not able to find", null);
								//getting the flight and seat number from checkin screen
								if(SearchSeatNumber.equalsIgnoreCase("Y")) {
									try {
										Seatnumber = driver.findElement(By.xpath("//div/label[contains(text(),'Seat')]//parent::div/div/div")).getText();
										FlightNum = driver.findElement(By.xpath("//div/label[contains(text(),'Flight')]//parent::div/div/span")).getText();
										queryObjects.logStatus(driver, Status.PASS, "Able to fetch the seat number", "Seat number assigned is "+Seatnumber+"", null);
									} catch (Exception e1) {
										queryObjects.logStatus(driver, Status.FAIL, "Not able to fetch the seat number", "Seat number not able to find or not assigned", null);
										// TODO: handle exception
									}
								}
								//getting the bagtag number from checkin screen
							} 
							if(SearchBagTag.equalsIgnoreCase("Y")) {
								try{
									TagNumber = driver.findElement(By.xpath("//div[@class='pssgui-bold ng-binding' and contains(text(),'CM')]")).getText();
									TagNumber1 = TagNumber.split(" ");
									TagNumber = TagNumber1[0].replace("CM","");
								}
								catch(Exception e){
									queryObjects.logStatus(driver, Status.FAIL, "Not able to fetch the bag tag number", "Bag tag number not able to find", null);
								}						
							}
						}
						
						//return to check in by rushil
						if(Returntocheckin.equalsIgnoreCase("yes")) {
						try {
							driver.findElement(By.xpath("//button[contains(@aria-label,'Cancel') and contains(text(),'Return to Check In')]")).click();
							FlightSearch.loadhandling(driver);
							return;
						}catch (Exception e) {}
						}
						
						Boolean checkin_validation=false;
						// check for multiple leg/pax
						//if (driver.findElements(By.xpath("(//i[@class='icon-checked-in icon-small active-state'])[1]")).size()>0) {
						try {
							if (driver.findElement(By.xpath("("+GreenChkMarkIcon+")[1]")).isDisplayed()) {
								if (driver.findElements(By.xpath("//i[@class='icon-checked-in icon-small active-state']")).size()==driver.findElements(By.xpath("//div[@class='pssgui-design-heading-3-bg ng-binding']")).size()) {
									queryObjects.logStatus(driver, Status.PASS, "Checkin green mark status ", "Green check mark is displayed for all the checked in passenger in all flights ", null);
									queryObjects.logStatus(driver, Status.PASS, "Check in successful ", "Check in successful ", null);
									checkin_validation=true;
								}else {
									if (driver.findElement(By.xpath("//div[contains(text(),'SBY')]")).isDisplayed()) {
										queryObjects.logStatus(driver, Status.PASS, "Checkin green mark status ", "Green check mark is not displayed for the checked in Passenger due to SBY status ", null);
										queryObjects.logStatus(driver, Status.PASS, "Check in successful ", "Check in successful ", null);}
									else {
									queryObjects.logStatus(driver, Status.FAIL, "Checkin green mark status ", "Green check mark is not displayed for the checked in passenger in all flights ", null);
									queryObjects.logStatus(driver, Status.FAIL, "Check in Unsuccessful ", "Check in Un successful ", null);}
								}
							}
							else{
								queryObjects.logStatus(driver, Status.INFO, "Checkin green mark status ", "Green check mark is not displayed for the checked in passenger in all flights, Seats are not available", null);
								queryObjects.logStatus(driver, Status.INFO, "Check is Unsuccessful ", "Check is Un successful, Seats are not available ", null);
								try {
									driver.findElement(By.xpath(ConfirmOKXpath)).click();
								} catch (Exception e) {}
							}
						} catch (Exception e) {
							queryObjects.logStatus(driver, Status.INFO, "Checkin green mark status ", "Green check mark is not displayed for the checked in passenger in all flights, Seats are not available", null);
							queryObjects.logStatus(driver, Status.INFO, "Check is Unsuccessful ", "Check is Un successful, Seats are not available ", null);
							try {
								driver.findElement(By.xpath(ConfirmOKXpath)).click();
							} catch (Exception e1) {}
						}
						
						//Close Flight
						if (CloseFl.equalsIgnoreCase("FlightClosureBrk")) {
                            try {
                            	int Cbns = 0; int CbnVal = 0;
                                   driver.findElement(By.xpath("//button[contains(text(),'Return to Check In')]")).click();
                                   FlightSearch.loadhandling(driver);
                                   driver.findElement(By.xpath("//i[contains(@class,'expand-flight')]")).click();
                                   
                                   AssignControllingAgent(driver, queryObjects);
                                   FlightSearch.loadhandling(driver);
                                   
                                   if (driver.findElement(By.xpath("//md-select[contains(@ng-model,'flightInfo.model.flightActionSelected')]")).isDisplayed()){
                                          driver.findElement(By.xpath("//md-select[contains(@ng-model,'flightInfo.model.flightActionSelected')]")).click();
                                          driver.findElement(By.xpath("//div[contains(text(),'Flight Closure Breakdown')]")).click();       
                                          FlightSearch.loadhandling(driver);
                                          Cbns = driver.findElements(By.xpath("//span[contains(@ng-repeat,'passenger in cabin')]")).size();
                                          for (int cc = 1; cc <= Cbns; cc++) {
											try {
												CbnVal = Integer.parseInt(driver.findElement(By.xpath("(//span[contains(@ng-repeat,'passenger in cabin')])["+cc+"]")).getText())+CbnVal;
											} catch (Exception e) {}
                                          }
																																																										
																  
                                          if (CbnVal==0) {
                                                 queryObjects.logStatus(driver, Status.FAIL, "Flight Closure Breakdown", "Flight Closure Breakdown has passengers in cabin added", null);
                                          
                                          }else {
                                                 queryObjects.logStatus(driver, Status.PASS, "Open Closure Breakdown", "Flight Closure Breakdown does not have passengers in cabin", null);
                                                
                                          }
                                          
                                          queryObjects.logStatus(driver, Status.PASS, "Open Closure Breakdown", "Flight Closure Breakdown was displayed", null);
                                          return;
                                   }
                                   else {
                                          
                                          queryObjects.logStatus(driver, Status.FAIL, "Open Flight details", "Open Flight did not open", null); 
                                   }
                            }
                            catch (Exception e) {}
                     }
						if (checkin_validation) {
							try{
								if (!DelBag.isEmpty()) {
									//Note need to get Total wait count ----Pending
									driver.findElement(By.xpath("//button[@translate='pssgui.return.to.check.in']")).click();
									FlightSearch.loadhandling(driver);
									//Delete Bags after Checkin
									List<WebElement> AddBaggage =  driver.findElements(By.xpath(BaggageXpath));
									for (int i = 0; i < AddBaggage.size(); i++) {
										AddBaggage =  driver.findElements(By.xpath(BaggageXpath));
										AddBaggage.get(i).click();
										FlightSearch.loadhandling(driver);
										if (DelBag.equalsIgnoreCase("yes")) {
											Delete_bag(driver, queryObjects,i, Category, BagIndex, AddBag_Index);	
										}
										//Delete and add Bags
										if (DelBag.equalsIgnoreCase("after")) {
											Delete_AddBag(driver, queryObjects,BagIndex);
											if (!AddBag_AftDel.isEmpty()) {
												if (AddBag_AftDel.equalsIgnoreCase("all")) {
													CompleteBaggage(driver,queryObjects, AddBaggagekg, AllowBag, MulBaggageWgt, MulBaggageType, BaggageValue, Catalog);
												} else {
													for (int sptbg = 0; sptbg < MulBaggageWgt.split(";").length; sptbg++) {
														if ((sptbg+1) == Integer.parseInt(AddBag_AftDel)) {
															CompleteBaggage(driver,queryObjects, AddBaggagekg, AllowBag, MulBaggageWgt.split(";")[sptbg], MulBaggageType.split(";")[sptbg], BaggageValue, Catalog);
														}
													}
												}
											}
										}													   
									} 
								}
							}
							catch(Exception e){
								queryObjects.logStatus(driver, Status.FAIL, "Delete Bag check", "Delete Bag check",e);	
							}
						}
						//
						if(SearchBySequence.equalsIgnoreCase("Y")) {
							try {
								driver.findElement(By.xpath("//div[contains(@class,'confirmation')]//button[text()='Return to Check In']")).click();
								FlightSearch.loadhandling(driver);
								Sequencenumber = driver.findElement(By.xpath("//md-checkbox[@aria-label='pax-chk']/following-sibling::div[1]")).getText();
							} catch (Exception e) {
								queryObjects.logStatus(driver, Status.INFO, "Unable to get the sequence number", "need to get the sequence number using flight search", null);
								// TODO: handle exception
							}
							//Search by Sequence number
							try {
								String Checkindate = driver.findElement(By.xpath("(//div[@class='pssgui-design-caption ng-binding'])[1]")).getText().trim();
								Calendar cal1 = Calendar.getInstance();
								cal1.setTime(new  SimpleDateFormat("dd-MMM-yyyy").parse(Checkindate));
								String sValDate = new SimpleDateFormat("MM/dd/yyyy").format(cal1.getTime());
								driver.findElement(By.xpath("//div[contains(text(),'Home')]")).click();
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(FlightNum);
								String sDate = sValDate;
								driver.findElement(By.xpath("//pssgui-date-time[contains(@ng-model,'flightSearch')]//input[@class='md-datepicker-input']")).clear();
								driver.findElement(By.xpath("//pssgui-date-time[contains(@ng-model,'flightSearch')]//input[@class='md-datepicker-input']")).sendKeys(sDate);
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath("//div[@class='ng-scope flex']/button[@translate='pssgui.search']")).click();
								FlightSearch.loadhandling(driver);
	
								//get the sequence number using PNR
								if(Sequencenumber.equalsIgnoreCase("")) {
									driver.findElement(By.xpath("//pssgui-menu//md-select-value/span[2]")).click();
									Thread.sleep(3000);
									driver.findElement(By.xpath("//md-option[@value='pnr']/div/div")).click();
									driver.findElement(By.xpath("//input[@aria-label='Enter']")).sendKeys(sOrderNum);//PNR number
									Thread.sleep(1000);
									Sequencenumber = driver.findElement(By.xpath("//md-checkbox[@aria-label='pax-chk']/following-sibling::div[1]")).getText();
								}
								queryObjects.logStatus(driver, Status.PASS, "Sequence number for "+sOrderNum+" is "+Sequencenumber+"", "Need to search using sequence number", null);
	
								//search using sequence number
								driver.findElement(By.xpath("//pssgui-menu//md-select-value/span[2]")).click();
								Thread.sleep(3000);
								driver.findElement(By.xpath("//md-option[@value='sequence_number']/div/div")).click();
								driver.findElement(By.xpath("//input[@aria-label='Enter']")).sendKeys(Sequencenumber);//bagtag number
								Thread.sleep(1000);
								if(driver.findElement(By.xpath("//span[contains(text(),'"+sOrderNum+"')]")).isDisplayed()){
									queryObjects.logStatus(driver, Status.PASS, "Sequence number validation successful ", "Sequence number correct to specific PNR", null);
								}
								else{
									queryObjects.logStatus(driver, Status.FAIL, "Sequence number validation not successful ", "Sequence number is not matching", null);
								}
							}catch (Exception e) {
								queryObjects.logStatus(driver, Status.FAIL, "Search by Sequence number Failed", "SearchBySequence is not successful ", e);
								// TODO: handle exception
							}
						}
	
						//Jump seat checkin
						if(!sJumpSeat.equalsIgnoreCase("")) {
							try {
								driver.findElement(By.xpath("//div[contains(@class,'confirmation')]//button[text()='Return to Check In']")).click();
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath("//md-input-container//md-select[@aria-label='Checkin Passenger Actions']")).click();
								driver.findElement(By.xpath("//md-option/div[text()='Jump Seat']")).click();
								FlightSearch.loadhandling(driver);
								driver.findElement(By.xpath("//span//div[contains(text(),'CBJ')]")).click();
								Thread.sleep(1000);
								driver.findElement(By.xpath("//md-option//div[contains(text(),'"+sJumpSeat+"')]")).click();
								Thread.sleep(1000);
								driver.findElement(By.xpath("//input[@aria-label='employee']")).sendKeys("123567");
								Thread.sleep(1000);
								driver.findElement(By.xpath("//button[@aria-label='Ok']")).click();
								FlightSearch.loadhandling(driver);
								Seatnumber = driver.findElement(By.xpath("//div/label[contains(text(),'Seat')]//parent::div/div/div")).getText();
								if(driver.findElements(By.xpath("//md-dialog//span[contains(text(),'Jump seat is assigned')]")).size()>0) {
									queryObjects.logStatus(driver, Status.PASS, "Jump Seat assigned ", "Jump seat assigned with "+sJumpSeat+" employee id for "+Seatnumber+" seat number", null);
								}
								else {
									queryObjects.logStatus(driver, Status.PASS, "Jump Seat not assigned ", "Jump seat failed for specific PNR details", null);
								}
								/*if(Standby_Fdj.equalsIgnoreCase("yes")) {//Check with Yashodha
									String jumppax=Login.PNRNUM;
									driver.findElement(By.xpath("//div[@translate=\"pssgui.back\"]")).click();
									FlightSearch.loadhandling(driver);
									driver.findElement(By.xpath("//div[@translate=\"pssgui.standby\"]")).click();
									FlightSearch.loadhandling(driver);
									if(driver.findElement(By.xpath("//div[contains(@ng-repeat,'orderPassengers')]")).getText().trim().equalsIgnoreCase(jumppax)) {
										if(driver.findElement(By.xpath("//div[contains(@ng-repeat,'orderPassengers')]")).getText().trim().equalsIgnoreCase("FDJ")) {
											queryObjects.logStatus(driver, Status.PASS, "Jump Seat assigned ", "Passenger is added to Stand by List With FDJ Seat", null);
										}
										queryObjects.logStatus(driver, Status.FAIL, "Jump Seat assigned ", "Passenger is not added to Stand by List With FDJ Seat", null);
									}	
								}*/
								return;
							} catch (Exception e) {
								queryObjects.logStatus(driver, Status.FAIL, "Jumpseat failed ", "sJumpSeat is not successful ", e);
								// TODO: handle exception
							}
						}
						if (DelBag.isEmpty()) {
							if (FilterFgtList.equalsIgnoreCase("Misconnected Pax")||FilterFgtList.equalsIgnoreCase("InboundCompensate")) {
								String AltFgt = "";
								driver.findElement(By.xpath(ReturnXpath)).click();
								confirmok=false;
							}else {
								try {
									driver.findElement(By.xpath(ConfirmOKXpath)).click();
								} catch (Exception e) {}
								
							}
						}
						//driver.findElement(By.xpath(ConfirmOKXpath)).click();
						FlightSearch.loadhandling(driver);
						// queryObjects.logStatus(driver, Status.PASS, "Check in successful ", "Check in successful ", null);
						/*if (confirmok) {
						 if (driver.findElement(By.xpath("("+GreenChkMarkIcon+")[1]")).isDisplayed()) {
								if (driver.findElements(By.xpath(GreenChkMarkIcon)).size()==driver.findElements(By.xpath("//div[@class='pssgui-design-heading-3 ng-binding']")).size()) {
									queryObjects.logStatus(driver, Status.PASS, "Checkin green mark status ", "Green check mark is displayed for all the checked in passenger in all flights ", null);
									queryObjects.logStatus(driver, Status.PASS, "Check in successful ", "Check in successful ", null);
								}else {
									queryObjects.logStatus(driver, Status.FAIL, "Checkin green mark status ", "Green check mark is not displayed for the checked in passenger in all flights ", null);
									queryObjects.logStatus(driver, Status.FAIL, "Check in Unsuccessful ", "Check in Un successful ", null);
								}
							}
						 confirmok=true; 
					}*/
						if (sCheckinMsg.equalsIgnoreCase("Update Bag Tag")) {
							FlightSearch_CheckinLanding(driver, "", "", Flightnumber);
							SelectFromPassengerList(driver, queryObjects, sOrderNum, "PNR", sOrderNum);
							driver.findElement(By.xpath(BaggageXpath)).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath(AddAnotherBag)).click();
							SetBaggage(driver, queryObjects, 1, "15", "");
							driver.findElement(By.xpath(BagtypeXpath)).click();
							driver.findElement(By.xpath("//md-option[contains(@ng-value,'standard')]")).click();
							Thread.sleep(1000);
							driver.findElement(By.xpath(WeightXpath)).sendKeys(AddBaggagekg);
							driver.findElement(By.xpath(SubmitXpath)).click();
							FlightSearch.loadhandling(driver);
						}
	
						if(sCheckinMsg.equalsIgnoreCase("Passenger Details Update")){
							//Update Seat
							AssignAvailableSeats(driver, queryObjects);
							//Update SSR - Add WCHR
							if (sSSREmC.equalsIgnoreCase("WCHR")) {
								driver.findElement(By.xpath("//span[@translate='pssgui.ssr']")).click();
								FlightSearch.loadhandling(driver);
								if (driver.findElement(By.xpath("//input[@name='ssr']")).isDisplayed()) {
									driver.findElement(By.xpath("//input[@name='ssr']")).sendKeys(sSSREmC);
									driver.findElement(By.xpath("//input[@name='ssr']")).sendKeys(Keys.TAB);
									driver.findElement(By.xpath("//span/i[@class='icon-search']")).click();
									Thread.sleep(100);
									driver.findElement(By.xpath("//md-content[contains(@ng-if,'ssrSubcategory.model.Passengers')]/div[1]//input")).sendKeys("WheelChair");
									driver.findElement(By.xpath("//form[@name='ssrSubcategory.form']//button[contains(text(),'Add To Order')]")).click();
									FlightSearch.loadhandling(driver);
									try {
										driver.findElement(By.xpath("//div[contains(@ng-repeat,'paxInfo in ssrPassenger')]//i[@class='toggle-arrow ng-scope icon-forward']")).click();
									} catch (Exception e) {
										// no action
									}
									if (driver.findElement(By.xpath("//div[contains(text(),'"+sSSREmC+"')]")).isDisplayed()) {
										queryObjects.logStatus(driver, Status.PASS, "Passenger Details Screen - Add SSR - "+sSSREmC, "SSR is added", null);		
									} else {
										queryObjects.logStatus(driver, Status.FAIL, "Passenger Details Screen - Add SSR - "+sSSREmC, "SSR is not added ", null);
									}
								}
							}
	
						}
						if(!FqtvUpdate.equals("") && sCheckinMsg.equalsIgnoreCase("FQTV UPDATE")){
							try {
								String FQTVVal[] = FqtvUpdate.split(";");
								sFFnum="";
								sFFProgram="";
								for (int i = 0; i < FQTVVal.length; i++) {
									sFFnum="";
									sFFProgram="";
									driver.findElement(By.xpath("//div[@ng-if='fqtv.passenger']")).click();
									sFFProgram=FQTVVal[i].substring(0, 2);
									sFFnum=FQTVVal[i].substring(3, FQTVVal[i].length());
									driver.findElement(By.xpath("//div[@ng-repeat='fqtvDetail in fqtv.list']//span[@class='md-select-icon']")).click();
									driver.findElement(By.xpath("//md-option[@value='"+sFFProgram+"']")).click();
									driver.findElement(By.xpath("//input[@name='ffNumber']")).sendKeys("1");
									driver.findElement(By.xpath("//input[@name='ffNumber']")).clear();
									driver.findElement(By.xpath("//input[@name='ffNumber']")).sendKeys(sFFnum);
									driver.findElement(By.xpath("//button[@translate='pssgui.update']")).click();						
									FlightSearch.loadhandling(driver);
									if (driver.findElement(By.xpath("//div[@ng-if='fqtv.passenger']")).getText().trim().contains(sFFnum)) {
										queryObjects.logStatus(driver, Status.PASS, "FQTV update for STAR Partners and Non Partners", sFFProgram+ " airlines FQTV updated ", null);
									} else {
										queryObjects.logStatus(driver, Status.FAIL, "FQTV update for STAR Partners and Non Partners", sFFProgram+ " airlines FQTV update failed ", null);
									}							
								}						 
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
						if (sCheckinMsg.contains("Star Alliance")) {
							driver.findElement(By.xpath("//md-select[contains(@ng-model,'flightInfo.model.flightActionSelected')]")).click();
							driver.findElement(By.xpath("//div[contains(text(),'Cabin Crew Report (Final)')]")).click();
							FlightSearch.loadhandling(driver);
							sFFnum = "";
							String SplitFFVal[] = sCheckinMsg.split(";");
							sFFnum = SplitFFVal[1];
							String PaxNm = FlightSearch.getTrimTdata(queryObjects.getTestData("SurName"))+" / "+FlightSearch.getTrimTdata(queryObjects.getTestData("FirstName"));
							PaxNm = PaxNm.substring(0, 13);
							if (driver.findElement(By.xpath("//div[@translate='pssgui.cabin.crew.final.report']")).isDisplayed()) {
								driver.findElement(By.xpath("//span[@translate='pssgui.prefer']")).click();
								List<WebElement> RCnt = driver.findElements(By.xpath("//tr[@ng-repeat='paxList in crewCtrl.model.CrewReport.NoteworthyPaxLists']"));
								for (int i = 1; i <= RCnt.size(); i++) {
									if (driver.findElement(By.xpath("(//tr[@ng-repeat='paxList in crewCtrl.model.CrewReport.NoteworthyPaxLists'])["+i+"]")).getText().trim().contains(sFFnum.toUpperCase()) && driver.findElement(By.xpath("(//tr[@ng-repeat='paxList in crewCtrl.model.CrewReport.NoteworthyPaxLists'])["+i+"]")).getText().trim().contains(PaxNm)) {
										sFFnum= "present";
									}		
								}
								if (sFFnum=="present") {							
									queryObjects.logStatus(driver, Status.PASS, "Star alliance - Pax details ", "Pax details displayed ", null);
								}else {
									queryObjects.logStatus(driver, Status.FAIL, "Star alliance - Pax details", "Pax details not displayed ", null);
								}
							}
							sFFnum="";
						}
	
						if (sCheckinMsg.equalsIgnoreCase("Search by seq")) {
							FlightSearch_CheckinLanding(driver, "", "", sFlightOnly);
							String SplitVal[] = null;
							String SplitsSName[] = null;
							String SplitsGName[] = null;
							if (Filter_Val.contains(";")) {
								SplitVal = Filter_Val.split(";");
							}
							if (FlightSearch.getTrimTdata(queryObjects.getTestData("SurName")).contains(";") && FlightSearch.getTrimTdata(queryObjects.getTestData("FirstName")).contains(";")) {
								SplitsSName = FlightSearch.getTrimTdata(queryObjects.getTestData("SurName")).split(";");
								SplitsGName = FlightSearch.getTrimTdata(queryObjects.getTestData("FirstName")).split(";");
							}
							for (int i = 0; i < SplitVal.length; i++) {
								sOrderNum = SelectFromPassengerList(driver, queryObjects, SplitsGName[i]+" / "+SplitsSName[i], Filter_Name, SplitVal[i]);
								if (!sOrderNum.isEmpty()) {
									queryObjects.logStatus(driver, Status.PASS, "Filter using sequence number ", "Corresponding passenger is displayed ", null);		
								} else {
									queryObjects.logStatus(driver, Status.FAIL, "Filter using sequence number ", "Corresponding passenger is not displayed ", null);
								}
							}
	
						}
						if (!(FlightSearch.getTrimTdata(queryObjects.getTestData("PreferUpgrade_FF")).isEmpty())) {
							FlightSearch_CheckinLanding(driver, fDate, sDestn, Flightnumber);
							/*driver.findElement(By.xpath("//div[contains(text(),'Home')]")).click();
						 FlightSearch.loadhandling(driver);
						 driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(Flightnumber);
						 driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).clear();
						 driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).sendKeys(sDestn);
						 FlightSearch.loadhandling(driver);
						 driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).sendKeys(Keys.TAB);
						 driver.findElement(By.xpath("//pssgui-date-time[@ng-model='flightSearch.model.departureDate']//input[@class='md-datepicker-input']")).clear();
						 driver.findElement(By.xpath("//pssgui-date-time[@ng-model='flightSearch.model.departureDate']//input[@class='md-datepicker-input']")).sendKeys(fDate);
						 FlightSearch.loadhandling(driver);
						 driver.findElement(By.xpath("//div[@class='ng-scope flex']/button[@translate='pssgui.search']")).click();
						 FlightSearch.loadhandling(driver);*/
							driver.findElement(By.xpath("//md-select[@ng-model='menuCtrl.menuModel']/md-select-value/span[@class='md-select-icon']")).click();
							Thread.sleep(500);					
							driver.findElement(By.xpath("//md-option[@ng-repeat='menuTbl in menuCtrl.menuItems']/div/div[contains(text(),'"+Filter_Name+"')]")).click();
							Thread.sleep(200);
							driver.findElement(By.xpath("//input[@ng-model='airportPassenger.model.searchText']")).sendKeys("1");
							driver.findElement(By.xpath("//input[@ng-model='airportPassenger.model.searchText']")).clear();
							driver.findElement(By.xpath("//input[@ng-model='airportPassenger.model.searchText']")).sendKeys(Filter_Val);
	
						}
						if (!(FlightSearch.getTrimTdata(queryObjects.getTestData("PreferUpgrade_FF")).isEmpty())) {
							if (driver.findElements(By.xpath("//i[@class='icon-checked-in active-state']")).size()== Integer.parseInt("2")) {
								queryObjects.logStatus(driver, Status.PASS, "Check the Pax status ", "Passengers are checked in ", null);		
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Check the Pax status ", "Passenger/s are not checked in ", null);
							}
							if (driver.findElements(By.xpath("//i[@class='icon-standby ng-scope']")).size()== Integer.parseInt("2")) {							
								queryObjects.logStatus(driver, Status.PASS, "Check the Primary and Companion are added to standby list ", "Primary and Companion are added to standby passenger list ", null);		
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Check the Primary and Companion are added to standby list ", "Primary and Companion are not added to standby passenger list ", null);
							}
							if (driver.findElement(By.xpath("(//input[@ng-model='seat.NewSeatNumber'])[1]")).getText().trim()!="") {
								queryObjects.logStatus(driver, Status.PASS, "Check the Seat assigned status for Primary passenger ", "Seat is assigned for Primary passenger ", null);		
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Check the Seat assigned status for Primary passenger ", "Seat is not assigned for Primary passenger ", null);
							}
							if (driver.findElement(By.xpath("(//input[@ng-model='seat.NewSeatNumber'])[2]")).getText().trim()!="") {
								queryObjects.logStatus(driver, Status.PASS, "Check the Seat assigned status for Companion passenger ", "Seat is assigned for Companion passenger ", null);		
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Check the Seat assigned status for Companion passenger ", "Seat is not assigned for Companion passenger ", null);
							}
						}
	
						//driver.findElement(By.xpath(ConfirmOKXpath)).click();
						//FlightSearch.loadhandling(driver);//commented by Jenny as it is not required
						//queryObjects.logStatus(driver, Status.PASS, "Check in successful ", "Check in successful ", null);
	
						if (FlightSearch.getTrimTdata(queryObjects.getTestData("PAXVerify_Res")).equalsIgnoreCase("Y")) {
							PassengerVerification_Res(driver, queryObjects);
						}
						if (FilterFgtList.equalsIgnoreCase("Misconnected Pax")||FilterFgtList.equalsIgnoreCase("InboundCompensate")) {
							driver.findElement(By.xpath("//div[@class='pnr-value pssgui-bold pssgui-design-page-title-link ng-binding']")).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath(OSITabRes)).click();
	
							// if (driver.findElement(By.xpath("//div[contains(text(), 'IRROP-CANCELATION') and contains(text(),'HELD')]")).isDisplayed()) {
							
							if (driver.findElements(By.xpath("//div[contains(text(), 'IRROP-DELAY') and contains(text(),'"+Flightnumber+"')]")).size()>0) {
								queryObjects.logStatus(driver, Status.PASS, "Automatic OSI in PNR ", "Automatic OSI is displayed in reservation of the passenger affected by misconnected using alternate flight functionality ", null);		
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Automatic OSI in PNR ", "Automatic OSI is not displayed ", null);
							}
	
						}				 
	
						if (FilterFgtList.equalsIgnoreCase("Pax with Incomplete APIS")) {
	
							// SelectDocCheckFromList(driver, queryObjects, sOrderNum);
							driver.findElement(By.xpath(ProceedCheckInXpath)).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath("//button[@translate='pssgui.delete']")).click();
							Thread.sleep(200);
							driver.findElement(By.xpath("//button[contains(@class,'md-primary md-confirm-button')]")).click();
							FlightSearch.loadhandling(driver);
							FLAG=false;
							try {
								FLAG = driver.findElement(By.xpath("//span[contains(text(),'APIS Incomplete')]")).isDisplayed();
							} catch (Exception e) {
								FLAG = driver.findElement(By.xpath("//i[@class='icon-warning pssgui-design-status-critical ng-scope']")).isDisplayed();
							}
							if (FLAG) {
								queryObjects.logStatus(driver, Status.PASS, "Delete APIS Information ", "APIS Incomplete message is displayed ", null);
								try {						
									driver.findElement(By.xpath(DoneXpath)).click();
									FlightSearch.loadhandling(driver);
								} catch (Exception e) {
									// TODO: handle exception
								}
								String sflightno=Login.FLIGHTNUM.trim();
								FlightSearch_CheckinLanding(driver, "", "", sflightno);
								/*driver.findElement(By.xpath("//div[contains(text(),'Home')]")).click();
							 FlightSearch.loadhandling(driver);
							 driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(sflightno);
							 Thread.sleep(1000);
							 driver.findElement(By.xpath("//div[@class='ng-scope flex']/button[@translate='pssgui.search']")).click();
							 FlightSearch.loadhandling(driver);*/
	
								// driver.findElement(By.xpath("//span[contains(text(),'"+sflightno+"')]")).click();						 
								//Set Filter Method
								SetPassengerListOption(driver,queryObjects,FilterFgtList);
								List<WebElement> Checkbox_Cnt = driver.findElements(By.xpath(PaxDataChkBox));
								if (Checkbox_Cnt.size()>=1) {
									queryObjects.logStatus(driver, Status.PASS, "Display Pax with Incomplete APIS ", "Pax with Incomplete APIS is displayed for the selected flight ", null);		
								} else {
									queryObjects.logStatus(driver, Status.FAIL, "Display Pax with Incomplete APIS ", "Pax with Incomplete APIS is not displayed ", null);
								}
	
							}else {
								queryObjects.logStatus(driver, Status.FAIL, "Delete APIS Information ", "Deletion failed. APIS Incomplete message is not displayed ", null);
							}		 
	
						}
	
					}else if (FlightSearch.getTrimTdata(queryObjects.getTestData("Checkin_Disabled")).equalsIgnoreCase("Y")) {
						
						//String sCheckinMsg = FlightSearch.getTrimTdata(queryObjects.getTestData("Expected_CheckinMsg"));
						if(!sCheckinMsg.isEmpty() && (!sCheckinMsg.equalsIgnoreCase("PASSENGER INHIBITED - Please Verify ID")))  {
							queryObjects.logStatus(driver, Status.PASS, "Checkin verification", "Pax with ESTS/ETA documention verification is successfully completed  " , null);
							boolean Err = driver.findElement(By.xpath("//*[contains(text(),'"+sCheckinMsg+"')]")).isDisplayed();
							if (Err) {
	
								queryObjects.logStatus(driver, Status.PASS, " Expected Error Message displayed "+sErr, "Expected Err dispalyed ", null);
							}	
							else {
	
								queryObjects.logStatus(driver, Status.FAIL, "UnExpected Page dispalyed", "UnExpected Page dispalyed " , null); 
							} 
						}
	
					}else {
						boolean Err=false;
						try {
							 Err = driver.findElement(By.xpath("//*[contains(text(),'"+sCheckinMsg+"')]")).isDisplayed();	
						}catch (Exception e) {}
						
						queryObjects.logStatus(driver, Status.PASS, "Checkin verification", "Pax with ESTS/ETA documention verification is successfully completed  " , null);
						if (!Negative_Case.equalsIgnoreCase("Y")) {
							queryObjects.logStatus(driver, Status.FAIL, "Check in not successful ", "Check in successful ", null);
						}
						
						else if(Err) {
							queryObjects.logStatus(driver, Status.PASS, "Expected ADC Error ", "Expected ADC error displayed ", null);
						}
						
						if(FlightSearch.getTrimTdata(queryObjects.getTestData("NotSync")).equalsIgnoreCase("Y"))
						{
							String popup=driver.findElement(By.xpath("//md-dialog-content[h2[contains(text(),'Redirect Confirmation')]]//p")).getText();
							queryObjects.logStatus(driver, Status.PASS, "Checking Check in not successful ", "Check in not successful--->"+popup, null);	
						}
					}
	
					if(SearchBagTag.equalsIgnoreCase("Y")){//indrajit
	
						try{
							//String Checkindate = driver.findElement(By.xpath("//div[@class='pssgui-design-caption ng-binding']")).getText().trim();//added by jenny
							String Checkindate = driver.findElement(By.xpath("(//div[@class='pssgui-design-caption ng-binding'])[1]")).getText().trim();//added by jenny
							//ValDate
							Calendar cal1 = Calendar.getInstance();
							cal1.setTime(new  SimpleDateFormat("dd-MMM-yyyy").parse(Checkindate));					
							String sValDate = new SimpleDateFormat("MM/dd/yyyy").format(cal1.getTime());
							driver.findElement(By.xpath("//div[contains(text(),'Home')]")).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(ValFlight);
							//Calendar cal = Calendar.getInstance();
	
							//cal.add(Calendar.DATE, Integer.parseInt(sValDate));
							//String sDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
							String sDate = sValDate;
							//ValFlight = Flightnumber;
							driver.findElement(By.xpath("//pssgui-date-time[contains(@ng-model,'flightSearch')]//input[@class='md-datepicker-input']")).clear();
							driver.findElement(By.xpath("//pssgui-date-time[contains(@ng-model,'flightSearch')]//input[@class='md-datepicker-input']")).sendKeys(sDate);
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath("//div[@class='ng-scope flex']/button[@translate='pssgui.search']")).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath("//pssgui-menu//md-select-value/span[2]")).click();
							Thread.sleep(3000);
	
							driver.findElement(By.xpath("//div[contains(text(),'Bagtag')]")).click();
							driver.findElement(By.xpath("//input[@aria-label='Enter']")).sendKeys(TagNumber);//bagtagnumber
							if(driver.findElement(By.xpath("//span[contains(text(),'"+sOrderNum+"')]")).isDisplayed()){
								queryObjects.logStatus(driver, Status.PASS, "BagCheck successful ", "Bagcheck is successful ", null);
							}
							else{
								queryObjects.logStatus(driver, Status.FAIL, "BagCheck details mismatch", "Bagcheck is not successful ", null);
							}
						}catch(Exception e){
							queryObjects.logStatus(driver, Status.FAIL, "BagCheck failed ", "Bagcheck is not successful ", e);
						}
					}	
					//Search passenger by Seat number of checked in passenger
					if(SearchSeatNumber.equalsIgnoreCase("Y")) {
						try {
							String Checkindate = driver.findElement(By.xpath("(//div[@class='pssgui-design-caption ng-binding'])[1]")).getText().trim();
							Calendar cal1 = Calendar.getInstance();
							cal1.setTime(new  SimpleDateFormat("dd-MMM-yyyy").parse(Checkindate));
							String sValDate = new SimpleDateFormat("MM/dd/yyyy").format(cal1.getTime());
							driver.findElement(By.xpath("//div[contains(text(),'Home')]")).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(FlightNum);
							String sDate = sValDate;
							driver.findElement(By.xpath("//pssgui-date-time[contains(@ng-model,'flightSearch')]//input[@class='md-datepicker-input']")).clear();
							driver.findElement(By.xpath("//pssgui-date-time[contains(@ng-model,'flightSearch')]//input[@class='md-datepicker-input']")).sendKeys(sDate);
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath("//div[@class='ng-scope flex']/button[@translate='pssgui.search']")).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath("//pssgui-menu//md-select-value/span[2]")).click();
							Thread.sleep(3000);
							//driver.findElement(By.xpath("//div[contains(text(),'   Seat')]")).click();
							driver.findElement(By.xpath("//md-option[@value='seat']/div/div")).click();
							driver.findElement(By.xpath("//input[@aria-label='Enter']")).sendKeys(Seatnumber);//seat number
							Thread.sleep(1000);
							if(driver.findElement(By.xpath("//span[contains(text(),'"+sOrderNum+"')]")).isDisplayed()){
								queryObjects.logStatus(driver, Status.PASS, "Seat number validation successful ", "Seat number added to specific PNR", null);
							}
							else{
								queryObjects.logStatus(driver, Status.FAIL, "Seat number validation not successful ", "Seat number not added", null);
							}
	
						} catch (Exception e) {
							queryObjects.logStatus(driver, Status.FAIL, "Search by SeatNumber Failed", "SearchSeatNumber is not successful ", e);
							// TODO: handle exception
						}
					}
				}//Conditon ends here -Jenny 
			}

			String Sync_tkt=FlightSearch.getTrimTdata(queryObjects.getTestData("Sync_tkt"));
			if(Sync_tkt.equalsIgnoreCase("Y"))
			{
				try
				{
					String SyncStatus=driver.findElement(By.xpath(TktNotSyncStatus)).getText();
					if(SyncStatus.equalsIgnoreCase("0"))
					{
						queryObjects.logStatus(driver, Status.INFO, "CHECKING eTKT NOT SYNC ", "Zero Number of eTKT not sync", null);

						List<WebElement> SelectPnr=driver.findElements(By.xpath(OrderpnrXpath));   
						SelectPnr.get(0).click();
						FlightSearch.loadhandling(driver);




					}
					else
					{   //select TktNotSyncStatus checkbox
						driver.findElement(By.xpath(TktNotSyncStatus)).click();
						FlightSearch.loadhandling(driver);
						/*Atoflow atfl=new Atoflow();
						
						atfl.SelectFromPassengerList(driver, queryObjects,"",Filter_Name, Filter_Val);*/
						
						//driver.findElement(By.xpath("//md-checkbox[@aria-checked='true']")).click();	
						
						if (Filter_Name !="") {

							driver.findElement(By.xpath("//md-select[@ng-model='menuCtrl.menuModel']/md-select-value/span[@class='md-select-icon']")).click();
							Thread.sleep(500);
							driver.findElement(By.xpath("//md-option[@ng-repeat='menuTbl in menuCtrl.menuItems']/div/div[contains(text(),'"+Filter_Name+"')]")).click();
							Thread.sleep(200);
							driver.findElement(By.xpath("//input[@ng-model='airportPassenger.model.searchText']")).sendKeys("1");
							driver.findElement(By.xpath("//input[@ng-model='airportPassenger.model.searchText']")).clear();
							driver.findElement(By.xpath("//input[@ng-model='airportPassenger.model.searchText']")).sendKeys(Filter_Val);
							FlightSearch.loadhandling(driver);
						}
						List<WebElement> SelectPnr= driver.findElements(By.xpath(TktNotSyncStatuscheckboxXpath));
						SelectPnr.get(0).click();
						FlightSearch.loadhandling(driver);
						//syncs tkt button
						driver.findElement(By.xpath(SyncButtonXpath)).click();
						FlightSearch.loadhandling(driver);
						ReservationPage(driver, queryObjects);

					}
				}
				catch(Exception e)
				{
					queryObjects.logStatus(driver, Status.FAIL, "CHECKING EXCEPTION while eTKT NOT SYNC ", "Exception got while syncing", e); 
				}


			}

			String Flight_Info_Header=FlightSearch.getTrimTdata(queryObjects.getTestData("Flight_Info_Header"));

			if(Flight_Info_Header.equalsIgnoreCase("Y"))
			{  try
			{
				//checking the left flight header block

				if(driver.findElement(By.xpath(FlightHeaderLeftBlock)).isDisplayed())
				{

					queryObjects.logStatus(driver, Status.PASS, "Checking the Left block of Flight Heade", "Left block of flight is Visible ", null); 

					String FlightOnHeader=driver.findElement(By.xpath(FlightNoOnHeader)).getText();

					if(FlightOnHeader.contains(FLTNO))
					{
						queryObjects.logStatus(driver, Status.PASS, "Checking the Left block of Flight NO", "Fligt No same as Selected", null);   
					}
					else
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking the Left block of Flight NO", "Fligt No is Not same as Selected", null);   
					}
				}
				else
				{
					queryObjects.logStatus(driver, Status.FAIL, "Checking the Left block of Flight Header ", "Left block of flight is Visible ", null);   
				}
				if(driver.findElement(By.xpath(FlightHeaderMiddBlock)).isDisplayed())
				{

					queryObjects.logStatus(driver, Status.PASS, "Checking the Middle block of Flight Heade", "Middle block of flight is Visible ", null); 

					boolean date=driver.findElement(By.xpath(DateHeaderMiddBlock)).isDisplayed();
					String fltOnMiddleBlock=driver.findElement(By.xpath(FlightNoHeaderMiddBlock)).getText();
					boolean flightaction=driver.findElement(By.xpath(flightactionHeaderMiddBlock)).isDisplayed();
					boolean seg=driver.findElement(By.xpath(SeginfoHeaderMiddBlock)).isDisplayed();
					boolean status=driver.findElement(By.xpath(FltStatus)).isDisplayed();
					boolean flttime=driver.findElement(By.xpath(FltStatus)).isDisplayed();
					boolean bookedseat=driver.findElement(By.xpath(FltBookedSeat)).isDisplayed();
					boolean CheckedinSaet=driver.findElement(By.xpath(FltCheckedinSeat)).isDisplayed();
					String HeaderInfo=driver.findElement(By.xpath("//div[contains(@class,'hpe-pssgui flight-info')]")).getText();
					if(fltOnMiddleBlock.contains(FLTNO) && date && flightaction && seg && status && bookedseat && CheckedinSaet)
					{

						queryObjects.logStatus(driver, Status.PASS, "Checking the Left block of Flight information on header", "All the header information is displaying correctly"+HeaderInfo, null);   
					}
					else
					{
						queryObjects.logStatus(driver, Status.FAIL, "Checking the Left block of Flight information on header", "All the header information is not displaying correctly"+HeaderInfo, null);   
					}

				}
				else
				{
					queryObjects.logStatus(driver, Status.FAIL, "Checking the Middle block of Flight Heade ", "Middle block of flight is Visible ", null);   
				}
			} catch(Exception e)
			{
				queryObjects.logStatus(driver, Status.FAIL, "Checking Flight Heade ", "Exception while Flight Header", e);   
			}
			}	

			if (soffload.equalsIgnoreCase("Y")) {
                //  sOrderNum
                String TimeRemaining= driver.findElement(By.xpath("//div[contains(@ng-class,'dateTime.timeType')]")).getText().trim();
                String Time[]= TimeRemaining.split(":");
                int Hour= Integer.parseInt(Time[0]);
                
                if (Hour<3) {
                                                   
                       //     try {
                       //     driver.findElement(By.xpath("//md-content[contains(@class,'airport-passenger')]//div[contains(@ng-repeat,'passengerData')][1]//div[@class='md-container md-ink-ripple'][1]")).click();
                       //     } catch (Exception e) {}                       
                       driver.findElement(By.xpath("//button[contains(text(),'Off Load')]")).click();
                       FlightSearch.loadhandling(driver);

                       //Himani 9Apr
                       boolean inform= false;
                       try {
                              inform = driver.findElement(By.xpath("//span[contains(text(),'Flight is restricted')]")).isDisplayed();
                       }catch (Exception e) {}
                       if(inform && Negative_Case.equalsIgnoreCase("Y")) {
                              queryObjects.logStatus(driver, Status.PASS, "Off Load Case", "Flight is restricted- Negative Case", null);
                              return;
                       } else
                              {
                                     queryObjects.logStatus(driver, Status.PASS, "Off Load Case", "Of Load Successful", null); 
                              }
                
	                       //refresh
	                       driver.findElement(By.xpath("//div[contains(text(),'Home')]")).click();
	                       FlightSearch.loadhandling(driver);
	                       driver.findElement(By.xpath(OrderNumXpath)).sendKeys(sOrderNum);
	                       Thread.sleep(1000);
	                       //proceed to check in
	                       driver.findElement(By.xpath(SearchXpath)).click();
	                       FlightSearch.loadhandling(driver);
	                       String savedVal = driver.findElement(By.xpath(SurnameXpath1)).getText();

                       if (savedVal.isEmpty()) 
                              queryObjects.logStatus(driver, Status.PASS, "ADC/APIS information was deleted after offloading", "APIS infor was deleted", null);
                       else 
                              queryObjects.logStatus(driver, Status.FAIL, "ADC/APIS information was deleted after offloading", "APIS infor was deleted", null);    
                	} else {
                       queryObjects.logStatus(driver, Status.PASS, "The time remaining fo the flight is more than 3 Hours", "Passenger will be offloaded successfully", null);
                       }
          }


			if (sSSREmC.equalsIgnoreCase("Y")) {
				//refresh
				driver.findElement(By.xpath("//div[contains(text(),'Home')]")).click();
				FlightSearch.loadhandling(driver);
				sFlight =  Login.FLIGHTNUM;
				driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(sFlight);
				Thread.sleep(1000);
				Calendar cal1 = Calendar.getInstance();    ///suman selectee
				SimpleDateFormat sdf = new  SimpleDateFormat("MM/dd/yyyy");


				String newDate = sdf.format(cal1.getTime());

				driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).clear();
				driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).click();
				driver.findElement(By.xpath("//input[@class='md-datepicker-input']")).sendKeys(newDate);

				driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::div[@ng-if='flightSearch.display.gateSearchBtn']/button")).click();
				FlightSearch.loadhandling(driver); 

				//filter flight
				driver.findElement(By.xpath("//pssgui-menu//md-select-value/span[2]")).click();
				Thread.sleep(3000);

				driver.findElement(By.xpath("//div[contains(text(),'PNR')]")).click();
				Thread.sleep(1000);
				for (int i = 0; i < sOrderNum.length(); i++){

					char c = sOrderNum.charAt(i);
					String s = new StringBuilder().append(c).toString();
					driver.findElement(By.xpath("//input[@aria-label='Enter']")).sendKeys(s);

				}
				//.sendKeys(sOrderNum);//bagtagnumber
				if(driver.findElement(By.xpath("//span[contains(text(),'"+sOrderNum+"')]")).isDisplayed()){
					queryObjects.logStatus(driver, Status.PASS, "Order was displayed ", "Order was available ", null);
					driver.findElement(By.xpath("//span[text()='SSR']")).click();
					FlightSearch.loadhandling(driver);
					List<WebElement> Arrow = driver.findElements(By.xpath("//i[contains(@ng-class,'icon-arrow-down')]"));
					Arrow.get(0).click();
					driver.findElement(By.xpath("//div[contains(text(),'PCTC')]")).click();
					FlightSearch.loadhandling(driver);
					String emertext = driver.findElement(By.xpath("//input[contains(@ng-model,'ssrSubcategory')]")).getText();
					queryObjects.logStatus(driver, Status.PASS, "Emergency info was available "+emertext, "Emergency info was available ", null);
				}
				else{
					queryObjects.logStatus(driver, Status.FAIL, "Order was not displayed ", "Order was available ", null);
				}

				//click on SSR
			}
			if (VoidBag.equalsIgnoreCase("Y")) {
				VoidBagEMD(driver,queryObjects);
			}
			
	if (BoardStatus.equalsIgnoreCase("SwapHKNSeat")) {
				/*driver.findElement(By.xpath("//div[@translate='pssgui.standby']/..")).click();
				FlightSearch.loadhandling(driver);
				FlightSearch.loadhandling(driver);
				FlightSearch.loadhandling(driver);
				FlightSearch.loadhandling(driver);
				int SeatCntBfr = driver.findElements(By.xpath("//div[contains(@class,'cabin-type') and contains(text(),'Business')]/..//div[@class='seat']/div[ contains(@class,'icn-taken')]")).size()+driver.findElements(By.xpath("//div[contains(@class,'cabin-type') and contains(text(),'Economy')]/..//div[@class='seat']/div[ contains(@class,'icn-taken')]")).size();
			
				
				if(OFFload_standby.equalsIgnoreCase("All")) {
					driver.findElement(By.xpath("//md-checkbox[contains(@ng-model,'checkAll ')]")).click();
				} else if (OFFload_standby.contains("Y-")) {
					for (int sl = 1; sl <= SeatCntBfr; sl++) {
						if (VolnList.contains(driver.findElement(By.xpath("(//span[contains(@ng-click,'viewOrder')])["+sl+"]")).getText().trim())) {
							if (!driver.findElement(By.xpath("(//div[contains(@ng-repeat,'seat in')])["+sl+"]//span[contains(@ng-if,'passengerData.chkBoxSelected')]")).getText().trim().contains("SB")) {
								Seat = driver.findElement(By.xpath("(//div[contains(@ng-repeat,'seat in')])["+sl+"]//span[contains(@ng-if,'passengerData.chkBoxSelected')]")).getText().trim();
								OffloadPNR = driver.findElement(By.xpath("(//span[contains(@ng-click,'viewOrder')])["+sl+"]")).getText().trim();
								driver.findElement(By.xpath("(//md-checkbox[@aria-label='pax-chk'])["+sl+"]")).click();
								break;
							}
						}
					}					
				} else {
					driver.findElement(By.xpath("//span[contains(text(),'"+ISharesflow.INRSA_PNR+"')]/ancestor::div[contains(@ng-repeat,'passengerData in passengerItinerary')]//md-checkbox[@aria-label='pax-chk']")).click();
				}			
				driver.findElement(By.xpath("//button[@translate='pssgui.offload']")).click();
				FlightSearch.loadhandling(driver);				
				
				if(!true)
					queryObjects.logStatus(driver, Status.PASS, "Offload the Checked-in StandBy PAX","Checked-in StandBy PAX is Offloaded succesfully" , null);	
				else
					queryObjects.logStatus(driver, Status.FAIL, "Offload the Checked-in StandBy PAX","Checked-in StandBy PAX is not Offloaded" , null);*/
			}
			
//-----------------------------------------------------REMOVERESTRICT-------------------------------------------------------------------------------------------------   
			if(DoAction.toUpperCase().contains("REMOVERESTRICT")) {
				try {
					driver.findElement(By.xpath(FlightAct)).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//md-option[contains(@value,'open-flight')]")).click();
					FlightSearch.loadhandling(driver);
					FlightSearch.loadhandling(driver);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
//-----------------------------------------------------REMOVERESTRICT--------------END-----------------------------------------------------------------------------------
			
//-----------------------------------------------------CLEAR STANDBY -----------REMOVE OVERSOLD-----------------------------------------------------------------------------------
			try {
				if(DoAction.equalsIgnoreCase("ClearStandby")) {
					//Clear All Standby Pax
					driver.findElement(By.xpath("//div[@translate='pssgui.standby']/..")).click();
					FlightSearch.loadhandling(driver);
					FlightSearch.loadhandling(driver);
					FlightSearch.loadhandling(driver);
					if (driver.findElements(By.xpath("//span[contains(@ng-click,'viewOrder')]")).size()>0) {
						driver.findElement(By.xpath("//md-checkbox[contains(@ng-model,'checkAll ')]")).click();
						driver.findElement(By.xpath("//button[@translate='pssgui.offload']")).click();
						FlightSearch.loadhandling(driver);
						FlightSearch.loadhandling(driver);
					}					
					try {
						driver.findElement(By.xpath("//button[contains(@aria-label,'Clear')]")).click();
						FlightSearch.loadhandling(driver);
					} catch (Exception e) {}
					try {
						driver.findElement(By.xpath("//button[contains(@aria-label,'ok')]")).click();
						FlightSearch.loadhandling(driver);
					} catch (Exception e) {}
					
					//Clear Oversold List
					try {
						driver.findElement(By.xpath(OversoldTab)).click();
						FlightSearch.loadhandling(driver);
						if (driver.findElements(By.xpath("//span[contains(@ng-click,'viewOrder')]")).size()>0) {
							driver.findElement(By.xpath("//md-checkbox[contains(@ng-model,'airportPanel.model.checkAll')]")).click();
							driver.findElement(By.xpath(Compensate)).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath("//md-checkbox[contains(@ng-model,'model.selectAll')]")).click();
							driver.findElement(By.xpath("//md-select[@ng-model='compPassengers.model.compensationReason']")).click();
							driver.findElement(By.xpath("//div[contains(text(),'Baggage Lost')]")).click();
							driver.findElement(By.xpath("//button[@translate='cm.continue']")).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath("//md-checkbox[contains(@ng-model,'model.selecAll')]")).click();
							driver.findElement(By.xpath("//button[@translate='cm.issue.compensation']")).click();
							driver.findElement(By.xpath("//button[@translate='upt.add.ok']")).click();
							FlightSearch.loadhandling(driver);
							Thread.sleep(1000);
						}						
					} catch (Exception e) {}
					
					//Initiate Standby Boarding - Shares
					try {
						String oFlt=driver.findElement(By.xpath(flighNumXpath)).getText().trim();
						String odate=driver.findElement(By.xpath(flightdateXpath)).getText().trim();
						String oOrgin=driver.findElement(By.xpath(FlighOrginXpath+"[1]")).getText().trim();
						odate = odate.replace("-", "").toUpperCase();
						ISharesflow.ClearAllStandbyPax(driver, queryObjects, oFlt, oOrgin, odate);
					} catch (Exception e) {}
					
					//Open Flight
					try {
						driver.findElement(By.xpath(FlightAct)).click();
						Thread.sleep(2000);
						driver.findElement(By.xpath("//md-option[contains(@value,'open-flight')]")).click();
						FlightSearch.loadhandling(driver);
						FlightSearch.loadhandling(driver);
					} catch (Exception e) {}
					
					try {
						//To Remove the weight Balance restriction
						Add_RemoveWeightBalRestriction(driver, queryObjects,"", "Remove");
					} catch (Exception e) {}
					
					//Clearing Static Variables
					Login.MultiplePNR = ""; Login.PNRNUM = ""; PnrNum = ""; VolnList = ""; Compensation.PnrNum =""; Compensation.sOrderNum=""; Compensation.Orders="";
					ISharesflow.fDate = ""; ISharesflow.cTime = ""; ISharesflow.Shares = ""; ISharesflow.iPNRNo = ""; ISharesflow.MultiPNR = ""; ISharesflow.iMulPNR = "";
					ISharesflow.Flight = "";ISharesflow.Orig = "";ISharesflow.Destn = "";
				}
			} catch (Exception e) {}
			
//-----------------------------------------------------CLEAR STANDBY -----------REMOVE OVERSOLD------------END-----------------------------------------------------------------------
			
//-----------------------------------------------------DELETE SEGMENTS ----------------------------------------------------------------------------------------------------------
			try {
				if(DoAction.toLowerCase().contains("deletesegments")) {
					if (sOrderNum.isEmpty()) {
						if (sFlight.equalsIgnoreCase("Shares") || sFlight.equalsIgnoreCase("Shareflight") || sFlightCheckin.equalsIgnoreCase("Shares")) {
							sOrderNum = ISharesflow.iPNRNo;
						}
					}
					ISharesflow.DeleteSegments(driver, queryObjects, sOrderNum);
					if (!MultiPNR.isEmpty()) {
						ISharesflow.DeleteSegments(driver, queryObjects, MultiPNR);
					}
				}
			} catch (Exception e) {}

		try {
			if(DoAction.toLowerCase().contains("displaystandby")) {
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//div[@translate='pssgui.standby']/..")).click();
				FlightSearch.loadhandling(driver);
				if (sOrderNum.isEmpty()) {
					if (sFlight.equalsIgnoreCase("Shares") || sFlight.equalsIgnoreCase("Shareflight") || sFlightCheckin.equalsIgnoreCase("Shares")) {
					sOrderNum = ISharesflow.iPNRNo;
					}
				}
				driver.findElement(By.xpath("//md-select[@ng-model='menuCtrl.menuModel']/md-select-value/span[@class='md-select-icon']")).click();
				Thread.sleep(500);
				driver.findElement(By.xpath("//md-option[@ng-repeat='menuTbl in menuCtrl.menuItems']/div/div[contains(text(),'PNR')]")).click();
				Thread.sleep(200);
				driver.findElement(By.xpath("//input[@ng-model='airportPassenger.model.searchText']")).sendKeys("1");
				driver.findElement(By.xpath("//input[@ng-model='airportPassenger.model.searchText']")).clear();
				driver.findElement(By.xpath("//input[@ng-model='airportPassenger.model.searchText']")).sendKeys(sOrderNum);
				Thread.sleep(5000); 

				if (driver.findElement(By.xpath("//div[contains(text(),'SBY')]")).isDisplayed()) {
				queryObjects.logStatus(driver, Status.PASS, "Checkin green mark status ", "Green check mark is not displayed for the checked in Passenger due to SBY status ", null);
				queryObjects.logStatus(driver, Status.PASS, "Check in successful ", "Check in successful ", null);}
				else {
				queryObjects.logStatus(driver, Status.FAIL, "Checkin green mark status ", "Green check mark is not displayed for the checked in passenger in all flights ", null);
				queryObjects.logStatus(driver, Status.FAIL, "Check in Unsuccessful ", "Check in Un successful ", null);
				}
			}

			} catch (Exception e) {}	
		}
			

//-----------------------------------------------------DELETE SEGMENTS ----------------------END---------------------------------------------------------------------------------

		catch(Exception e) {

			queryObjects.logStatus(driver, Status.FAIL, "Search Passenger", "Search Passenger failed: " + e.getLocalizedMessage() , e);
		}
	}

	public static void Upgrade_Downgrade(WebDriver driver, BFrameworkQueryObjects queryObjects, String Class, String OptionSel) throws IOException {
		try {
			driver.findElement(By.xpath("//md-input-container//md-select[@aria-label='Checkin Passenger Actions']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//md-option/div[text()='Up/Down Grade Change']")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//md-radio-button[@value='"+OptionSel+"']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//input[contains(@ng-model,'classOfService')]")).sendKeys(Class);
			driver.findElement(By.xpath(SubmitXpath)).click();
			FlightSearch.loadhandling(driver);
			List <WebElement>  sList = driver.findElements(By.xpath("//div[contains(text(),'Grade Change') and contains(@ng-if,'ActionSelected')]"));
			if (sList.size() >0)
			{
				queryObjects.logStatus(driver, Status.PASS, "Upgrade was successful", "Upgraded ", null); 
			}
			else
			{
				queryObjects.logStatus(driver, Status.FAIL, "Upgrade was not successful", "Upgraded ", null); 
			}
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Upgrade/Downgrade was not successful", "Failed ", null);
		}								
	}			
	

	public static void EnterDocCheck(WebDriver driver,BFrameworkQueryObjects queryObjects) {
		try {


			String MulGName[] = null;
			String MulSName[] = null;			
			String SptAge[] = null;
			String SptDocType[] = null;
			String SptDocNum[] = null;
			String SptNationality[] = null;
			String SptCOI[] = null;
			String SptExpdays[] = null;
			String SptCOR[] = null;
			boolean nextbt = false;

			String sFName = FlightSearch.getTrimTdata(queryObjects.getTestData("FirstName"));
			String sSNameS = FlightSearch.getTrimTdata(queryObjects.getTestData("SurName"));				
			String sAge = FlightSearch.getTrimTdata(queryObjects.getTestData("Age"));
			String sDocType = FlightSearch.getTrimTdata(queryObjects.getTestData("DocumentType"));
			String sDocNum = FlightSearch.getTrimTdata(queryObjects.getTestData("DocumentNum"));
			String sNationality = FlightSearch.getTrimTdata(queryObjects.getTestData("Nationality"));
			String sCOI = FlightSearch.getTrimTdata(queryObjects.getTestData("COI"));
			String sExpdays = FlightSearch.getTrimTdata(queryObjects.getTestData("ExpiryDays"));
			String sCOR = FlightSearch.getTrimTdata(queryObjects.getTestData("COR"));
			String sSubmit =  FlightSearch.getTrimTdata(queryObjects.getTestData("SubmitEnable"));


			String sMulPax = FlightSearch.getTrimTdata(queryObjects.getTestData("MulPax"));
			String sCollectAPIS = FlightSearch.getTrimTdata(queryObjects.getTestData("CollectAPIS"));
			sCheckinMsg = FlightSearch.getTrimTdata(queryObjects.getTestData("Expected_CheckinMsg"));
			sExitDays =FlightSearch.getTrimTdata(queryObjects.getTestData("ExitDays"));
			String DeleteAPI= FlightSearch.getTrimTdata(queryObjects.getTestData("Delete_API"));
			
			if(driver.findElements(By.xpath("//span[contains(text(),'The specified flight is not open')]")).size()>0 && openflightflag==0)
			{
				OpenFlights_Checkin(driver, queryObjects);
			}

			if (sFName.contains(";")) {
				MulGName = sFName.split(";");
				MulSName = sSNameS.split(";");			
			}
			if ((sAge.contains(";")) && (sDocType.contains(";")) && (sDocNum.contains(";")) && (sNationality.contains(";"))  && (sCOI.contains(";")) && (sExpdays.contains(";")) && (sCOR.contains(";"))) {
				SptAge = sAge.split(";");
				SptDocType = sDocType.split(";");
				SptDocNum = sDocNum.split(";");
				SptNationality = sNationality.split(";");
				SptCOI = sCOI.split(";");
				SptExpdays = sExpdays.split(";");
				SptCOR = sCOR.split(";");
			}

			if ((sFName=="") && (sFName !="SamePAX"))
				sFName="DefaultName";

			if (sSNameS=="")
				sSNameS="DefaultSurName";

			if (sAge=="")
				sAge="30";
			if (FlightSearch.getTrimTdata(queryObjects.getTestData("FilterFlightList")).equalsIgnoreCase("InboundCompensate") || sCheckinMsg.equalsIgnoreCase("ADCBYPASS_MENUS") || sCheckinMsg.equalsIgnoreCase("BYPASS ADC"))
			{
				driver.findElement(By.xpath("//md-checkbox[contains(@ng-change,'BYPASSADC')]/div[1]")).click();
				FlightSearch.loadhandling(driver);
				if (sCheckinMsg.equalsIgnoreCase("ADCBYPASS_MENUS")) {
					try {
						driver.findElement(By.xpath("(//md-select[@ng-model='menuCtrl.menuModel']//span[@class='md-select-icon'])[2]")).click();
						if (driver.findElements(By.xpath("//div[contains(@class,'md-active md-clickable')]//div[contains(@ng-repeat,'menuLabels')]")).size()>0) {
							queryObjects.logStatus(driver, Status.PASS, "Click ADC BYPASS button ", "ADC BYPASS reasons menu, Menus are displayed. ", null);

							if(!BypassADC.isEmpty() && !BypassADC.equalsIgnoreCase("ADCdown") && !BypassADC.equalsIgnoreCase("No Bypass")) {
								driver.findElement(By.xpath("//div[contains(@class,'md-active md-clickable')]//div[contains(text(),'"+BypassADC+"')]")).click();
							}
							else{
								driver.findElement(By.xpath("(//div[contains(@class,'md-active md-clickable')]//div[contains(@ng-repeat,'menuLabels')])[1]")).click();
							}
							//driver.findElement(By.xpath("(//div[contains(@class,'md-active md-clickable')]//div[contains(@ng-repeat,'menuLabels')])[1]")).click();
							Thread.sleep(100);
						}							
					} catch (Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Click ADC BYPASS button ", "ADC BYPASS reasons menu, Menus are not displayed. ", null);
					}
				}
				driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
			}



			//if (sMulPax.equalsIgnoreCase("All") || sMulPax!="") {
				
			List<WebElement> Passengers = driver.findElements(By.xpath("//*[contains(@ng-repeat,'orderObj.Passengers')]/div/div/span"));
				for (int pl = 0; pl < Passengers.size(); pl++) {
					if (PNRRemarks.equalsIgnoreCase("SplitPNR")) {
						if (pl>0) {
							break;
						}
					}
					
					if (sMulPax.equalsIgnoreCase("All") || sMulPax!="") {
						driver.findElement(By.xpath("//div[contains(text(),'Primary Document')]/..")).click();
						FlightSearch.loadhandling(driver);
						PaxName = driver.findElements(By.xpath("//*[contains(@ng-repeat,'orderObj.Passengers')]/div/div/span")).get(pl).getText();
						String[] Arr = PaxName.split("/");
						String	sLName= Arr[0];
						String	sFname = Arr[1];
	
	
						//Have to check - Jenny
						if (sFName.equalsIgnoreCase("SamePAX")) { 
							int Paxitem=0;
							Paxitem = pl+1;	
	
							driver.findElement(By.xpath("(//span[contains(text(),'" +PaxName+ "')])["+Paxitem+"]")).click();				
						} else {
	
							driver.findElement(By.xpath("//span[contains(text(),'" +PaxName+ "')]")).click();
						}
						//driver.findElement(By.xpath("//span[contains(text(),'" +PaxName+ "')]")).click();
						//Updated by Jenny - NOV 28 2019
						if (!sMulPax.equalsIgnoreCase("All")) {
							EnterDocDetails(driver,queryObjects,MulGName[pl],MulSName[pl],SptAge[pl],SptDocType[pl],SptDocNum[pl],SptExpdays[pl],SptCOI[pl],SptNationality[pl],SptCOR[pl], pl);
						}
						else if ( SptAge!=null && SptDocType!=null && (SptDocNum!=null)  && (SptExpdays!=null) && (SptCOI!=null) && (SptNationality!=null)) {	
							EnterDocDetails(driver,queryObjects,sFname,sLName,SptAge[pl],SptDocType[pl],SptDocNum[pl],SptExpdays[pl],SptCOI[pl],SptNationality[pl],SptCOR[pl], pl);
							
						}/*else if (Passengers.size()==1) {//added by Jenny for mulpax
							EnterDocDetails(driver,queryObjects,sFname,sLName,sAge,sDocType,sDocNum,sExpdays,sCOI,sNationality,sCOR);
						}*/
						else if(!sDocType.isEmpty() && !sAge.isEmpty() && !sNationality.isEmpty()){
							EnterDocDetails(driver,queryObjects,sFname,sLName,sAge,sDocType,sDocNum,sExpdays,sCOI,sNationality,sCOR, pl);}
						else {
							EnterDocDetails(driver,queryObjects,sFname,sLName,"25","Passport","1234567","30","US","US","US", pl);							
						}

					} else {//Check with Atul						
						if(!DeleteAPI.equalsIgnoreCase("yes")&& sMulPax =="") {
							EnterDocDetails(driver,queryObjects,sFName,sSNameS,sAge,sDocType,sDocNum,sExpdays,sCOI,sNationality,sCOR, pl);
						}
					}
					
					
					//Select Submit Button
					if (sCollectAPIS.equalsIgnoreCase("Y")) {
						if (driver.findElement(By.xpath(SubmitXpath)).isEnabled()) {
							driver.findElement(By.xpath(SubmitXpath)).click();
							//handle
							Thread.sleep(1000);
							FlightSearch.loadhandling(driver);
							if(driver.findElements(By.xpath("//div[@class='ng-binding msg-error' and contains(text(),'PLEASE PROVIDE EXIT DATE TIME')]")).size()>0){
								if (sErr.contains("ExitDate")) {
									queryObjects.logStatus(driver, Status.PASS, "Submit ADC/APIS information and verify Exit date error popup message display","'PLEASE PROVIDE EXIT DATE TIME' error message is displayed", null);
								}
								driver.findElement(By.xpath("//button[text()='OK']")).click();
								FlightSearch.loadhandling(driver);
								sExitDays =FlightSearch.getTrimTdata(queryObjects.getTestData("ExitDays"));
								nextbt=false;
								if(sExitDays.equalsIgnoreCase("No")) {
									queryObjects.logStatus(driver, Status.PASS, "No need for exit date", "No return segment", null);
								}
								else {
									do{
										if(driver.findElements(By.xpath(ExitdateXpath)).size()>0 && driver.findElement(By.xpath(ExitdateXpath)).isEnabled()){
											Calendar calend = Calendar.getInstance();
											String sExitDate="";
											calend.add(Calendar.DATE, +10);
											sExitDate = new SimpleDateFormat("MM/dd/yyyy").format(calend.getTime());
											driver.findElement(By.xpath(ExitdateXpath)).sendKeys(sExitDate);
											Thread.sleep(1000);
											driver.findElement(By.xpath(ExitdateXpath)).sendKeys(Keys.TAB);	
										}
										nextbt=driver.findElements(By.xpath(NextXpath)).size()>0;
										if (nextbt)
											driver.findElement(By.xpath(NextXpath)).click();
									}while(nextbt);
								}
								driver.findElement(By.xpath(SubmitXpath)).click();
								Thread.sleep(1000);
								FlightSearch.loadhandling(driver);
							}
							try {

								if(driver.findElement(By.xpath(AdsResponsePopup)).isDisplayed())
								{
									if ((driver.findElements(By.xpath("//td[contains(text(),'ADCNOTAVAILABLE')]")).size()>0 || driver.findElement(By.xpath("//div[contains(@ng-repeat,'adcDecision.passengers')]//span")).getText().contains("ADC NOT OK")) && BypassADC.contains("ADCdown")) {//Jenny -ADCNOTAVILABLE
									ByPassADC_ADCErr_Down(driver, queryObjects);
									
									} else {
										queryObjects.logStatus(driver, Status.PASS, "Checking Secondary document need to submit:", "Secondary document is mandatory", null);
										driver.findElement(By.xpath(AdsResponsePopup)).click();
									}

									if(driver.findElement(By.xpath(SecondaryDoc)).isDisplayed())
									{
										EnterSecDoc(driver,queryObjects, pl);
										driver.findElement(By.xpath(SubmitXpath)).click();
									}
									else if(driver.findElement(By.xpath(DestinationAddress)).isDisplayed())
									{
										EnterDestiDoc(driver,queryObjects);
										driver.findElement(By.xpath(SubmitXpath)).click();
									}

								}


							} catch (Exception e) {
								// TODO: handle exception
							}

							FlightSearch.loadhandling(driver);
							if (driver.findElement(By.xpath(APISComplete)).isDisplayed()) {

								if (sCheckinMsg !="" && !(sCheckinMsg.equalsIgnoreCase("ADCBYPASS_MENUS"))) {
									if (driver.findElement(By.xpath("//span[contains(text(),'"+sCheckinMsg+"')]")).isDisplayed()) {
										queryObjects.logStatus(driver, Status.PASS, "Message Validation after APIS collect:", sCheckinMsg+" is displayed", null);
									} else {
										queryObjects.logStatus(driver, Status.FAIL, "Message Validation after APIS collect", sCheckinMsg+" is not displayed", null);
									}	
								} 
								queryObjects.logStatus(driver, Status.PASS, "Select Submit button, ADC status OK:", "APIS data collected", null);							
							}else {
								queryObjects.logStatus(driver, Status.FAIL, "Select Submit button, ADC status not OK:", "APIS data not collected ", null);
							}
						}					
					}// APIS Complete
				}//For Loop Ends-Jenny

			//}
			/*else if (sMulPax!="") {
				List<WebElement> Passengers = driver.findElements(By.xpath("//*[contains(@ng-repeat,'orderObj.Passengers')]/div/div/span"));

				for (int i = 0; i < Passengers.size(); i++) {

					String PaxName = Passengers.get(i).getText();

					driver.findElement(By.xpath("//span[contains(text(),'" +PaxName+ "')]")).click();


					EnterDocDetails(driver,queryObjects,MulGName[pl],MulSName[pl],SptAge[pl],SptDocType[pl],SptDocNum[pl],SptExpdays[pl],SptCOI[pl],SptNationality[pl],SptCOR[pl]);						
				}
			}*/
			


		}catch(Exception e) {
			
			//driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);//60
		}
	}


	public static  void EnterDocDetails(WebDriver driver,BFrameworkQueryObjects queryObjects,String fName,String sName,String PaxAge,String DType,String DNum, String Expdays,String COI,String Nty,String COR, int Itm) throws IOException {
		// TODO Auto-generated method stub
		try {
			String sSubmit =  FlightSearch.getTrimTdata(queryObjects.getTestData("SubmitEnable"));
			String sGender = FlightSearch.getTrimTdata(queryObjects.getTestData("Gender"));
			String sExitDays=FlightSearch.getTrimTdata(queryObjects.getTestData("ExitDays"));
			String Err = FlightSearch.getTrimTdata(queryObjects.getTestData("Expected_ErrMessage"));
			
			String PriDocVerification = FlightSearch.getTrimTdata(queryObjects.getTestData("PriDocVerification"));	//cc
			String SecDocVerify = FlightSearch.getTrimTdata(queryObjects.getTestData("SecDocVerify"));		//cc
			String SecDocSubmit = FlightSearch.getTrimTdata(queryObjects.getTestData("SecDocSubmit"));
			String ADCMsgs =  FlightSearch.getTrimTdata(queryObjects.getTestData("ADCMsgs"));
			String RespMsg = "";
			String OtherMsgs = "";
			String ArrMsg[] = null;
			Calendar cal = Calendar.getInstance();
			String sDate = "";
			boolean isResponse = false;
			if (PaxAge.contains("/")) {//Added by Jenny)
				sDate = PaxAge;					
			} else {
				cal.add(Calendar.YEAR, -Integer.parseInt(PaxAge));
				sDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());


			}
			if(DType.equals(""))				
				DType="Passport";
			
			if(DNum.equals(""))				
				DNum="TEST16734";


			// driver.findElement(By.xpath(SurnameXpath1)).sendKeys(fName);
			driver.findElement(By.xpath(SurnameXpath1)).sendKeys(sName);
			driver.findElement(By.xpath(FirstnameXpath)).sendKeys(fName);
			driver.findElement(By.xpath(BirthDateXpath)).sendKeys(sDate);
			String gender;

			//Selecting Gender
			if (!sGender.isEmpty()) 
				gender = sGender;					 
			else 
				gender = "Male";
			driver.findElement(By.xpath(genderXpath)).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//md-option[div[div[contains(text(),'" + gender + "')]]]")).click();
			Thread.sleep(2000);
			//primary doc list display	//cc
			if(PriDocVerification.equalsIgnoreCase("Yes")){
				try {
					driver.findElement(By.xpath("//md-input-container/label[contains(text(),'Document Type')]//parent::md-input-container//span[2]")).click();
					List<WebElement> lst=driver.findElements(By.xpath("//md-option/div[contains(text(),'Passport')]"));
					List<String> strings = new ArrayList<String>();
					for(WebElement e : lst){
						strings.add(e.getText());
					}
					queryObjects.logStatus(driver, Status.PASS, "Primary Doc list verification has "+strings+"","Primary Doc List", null);
					driver.findElement(By.xpath("//md-option/div[contains(text(),'Passport')]")).click();
				} catch (Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Primary Doc list uanble to click","Primary Doc List unable to get data", e);
					//TODO: handle exception
				}
			}
			//selecting document type
			driver.findElement(By.xpath(DocTypeXpath)).click();
			//Validation of primary doc types CR167B
			// Yashodha 6-Nov-2019 Start
			if(FlightSearch.getTrimTdata(queryObjects.getTestData("Pri_Passport_Type")).equalsIgnoreCase("passport")) 
				PassportTypesvalidation(driver,queryObjects);
			else if(FlightSearch.getTrimTdata(queryObjects.getTestData("Pri_Passport_Type")).equalsIgnoreCase("document"))
				PriDocTypeValidation(driver,queryObjects);
			//Validation of primary doc types CR167B
			// Yashodha 7-Nov-2019 End
			
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
			} catch (Exception e) {}
			 driver.findElement(By.xpath(ExpdateXpath)).sendKeys(sExpDate);
			FlightSearch.loadhandling(driver);
			
			driver.findElement(By.xpath(COIXpath)).clear();
			Thread.sleep(2000);
			if(Err.equalsIgnoreCase("Exp_Date")) {
				String Exp_Datemsg=FlightSearch.getErrorMSGfromAppliction(driver, queryObjects).trim();
				if(Exp_Datemsg.equalsIgnoreCase("Expired document details entered")) {
					queryObjects.logStatus(driver, Status.PASS, "Entered valid expiry date","Unable to proceed for checkin", null);
				}
				else
					queryObjects.logStatus(driver, Status.FAIL, "Did not entered valid expiry date","Should not allow for checkin", null);
			}


			//driver.findElement(By.xpath(ExpdateXpath)).sendKeys(sExpDate);

			driver.findElement(By.xpath(COIXpath)).sendKeys(COI);
			FlightSearch.loadhandling(driver);
			Thread.sleep(1000);
			//driver.findElement(By.xpath(COIXpath)).sendKeys(Keys.TAB);
			//Tab Alternate Solution
			try {
				driver.findElement(By.xpath(Login.SelList)).click();
			} catch (Exception e1) {
				try {
					driver.findElement(By.xpath(Login.SelList2)).click();
				} catch (Exception e) {
					try {
						driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='"+COI+"']/ancestor::span[2]/following-sibling::span/span/../../..")).click();

						}catch(Exception e3) {
							driver.findElement(By.xpath(COIXpath)).sendKeys(Keys.TAB);
						}
				}
			}
			Thread.sleep(1000);
			driver.findElement(By.xpath(NtionalXpath)).clear();
			driver.findElement(By.xpath(NtionalXpath)).sendKeys(Nty);
			FlightSearch.loadhandling(driver);
			Thread.sleep(1000);
			//driver.findElement(By.xpath(NtionalXpath)).sendKeys(Keys.TAB);
			//Tab Alternate Solution
			try {
				driver.findElement(By.xpath(Login.SelList)).click();
			} catch (Exception e1) {
				try {
					driver.findElement(By.xpath(Login.SelList2)).click();
				} catch (Exception e) {
					try {
						driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='"+Nty+"']/ancestor::span[2]/following-sibling::span/span/../../..")).click();

						}catch(Exception e3) {
							driver.findElement(By.xpath(NtionalXpath)).sendKeys(Keys.TAB);
						}
				}
			}
			Thread.sleep(2000);
			driver.findElement(By.xpath(CORXpath)).clear();
			driver.findElement(By.xpath(CORXpath)).sendKeys(COR);
			FlightSearch.loadhandling(driver);
			Thread.sleep(1000);
			//driver.findElement(By.xpath(CORXpath)).sendKeys(Keys.TAB);
			//Tab Alternate Solution
			try {
				driver.findElement(By.xpath(Login.SelList)).click();
			} catch (Exception e1) {
				try {
					driver.findElement(By.xpath(Login.SelList2)).click();
				} catch (Exception e) {
					try {
						driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='"+COR+"']/ancestor::span[2]/following-sibling::span/span/../../..")).click();

						}catch(Exception e3) {
							driver.findElement(By.xpath(CORXpath)).sendKeys(Keys.TAB);
						}
				}
			}
			Thread.sleep(1000);
			if(sExitDays.equalsIgnoreCase("No") && driver.findElements(By.xpath("//div[@class='ng-binding msg-error' and contains(text(),'PLEASE PROVIDE EXIT DATE TIME')]")).size()==0) {
				queryObjects.logStatus(driver, Status.PASS, "No need for exit date", "No return segment", null);
			}
			else {
				if (!sExitDays.isEmpty() || driver.findElements(By.xpath("//div[@class='ng-binding msg-error' and contains(text(),'PLEASE PROVIDE EXIT DATE TIME')]")).size()>0) {
					if(driver.findElements(By.xpath(ExitdateXpath)).size()>0 && driver.findElement(By.xpath(ExitdateXpath)).isEnabled()){
						Calendar calend = Calendar.getInstance();
						String sExitDate="";
						if (sExitDays.trim()!=""){
							calend.add(Calendar.DATE, +Integer.parseInt(sExitDays));
						} else {
							calend.add(Calendar.DATE, +10);
						}						
						sExitDate = new SimpleDateFormat("MM/dd/yyyy").format(calend.getTime());
						driver.findElement(By.xpath(ExitdateXpath)).sendKeys(sExitDate);
						Thread.sleep(1000);
						driver.findElement(By.xpath(ExitdateXpath)).sendKeys(Keys.TAB);
					}
				}
			}
			if(driver.findElements(By.xpath(ExitdateJustificationXpath)).size()>0){
				
				driver.findElement(By.xpath(ExitdateJustificationXpath)).sendKeys("Justification");
				Thread.sleep(1000);
				driver.findElement(By.xpath(ExitdateJustificationXpath)).sendKeys(Keys.TAB);
			}
			driver.findElement(By.xpath(EmernameXpath)).clear();
			driver.findElement(By.xpath(EmernameXpath)).sendKeys("emergencydefault");


			driver.findElement(By.xpath(EmerPhXpath)).clear();
			driver.findElement(By.xpath(EmerPhXpath)).sendKeys("12345634");

			driver.findElement(By.xpath("//md-checkbox[contains(@ng-model,'ApplyToAllPassengers')]/div[@class='md-container md-ink-ripple']")).click();

			Thread.sleep(1000);
			String sKTNum = FlightSearch.getTrimTdata(queryObjects.getTestData("KTNum"));
			String sRedress =  FlightSearch.getTrimTdata(queryObjects.getTestData("Redress"));
			 sKnownTraveller = FlightSearch.getTrimTdata(queryObjects.getTestData("Know_traveler_validation_from_res"));
			   if(sKnownTraveller.equalsIgnoreCase("Yes"))
			   {
				   //*** this condition is to verify the known traveller pass through reservation page and the else condition is for pass the KTN from checkin
				   
				   String textBox = driver.findElement(By.xpath("//input[@name='KnownTravelerNumber']")).getAttribute("value").trim(); 
				   
				   if(textBox.equalsIgnoreCase(FlightSearch.KTNvalue))
					   {
						   queryObjects.logStatus(driver, Status.PASS, "Checking Known Passanger number on checkin page","Known passanger number displaying currectly"+textBox, null);     
						}
				   else 
						{
							queryObjects.logStatus(driver, Status.FAIL, "Checking Known Passanger number on checkin page ","Known passanger number is not displaying currectly", null);
						}
				   
			   }
			   {
				driver.findElement(By.xpath(KTXpath)).sendKeys(sKTNum);
			   }
			driver.findElement(By.xpath(Redress)).sendKeys(sRedress);
			Thread.sleep(500);
			Boolean isSubmit= false;
			Boolean isNext= false;
			try {
				isSubmit = driver.findElement(By.xpath(SubmitXpath)).isEnabled();

			}catch(Exception e) {}
			try{
				isNext = driver.findElement(By.xpath(NextXpath)).isEnabled();
			}catch(Exception e) {}
			
			//Infant Pax Validation 2	//cc
			if(sInfantPax.equalsIgnoreCase("Yes")) {
				try {
					if(driver.findElements(By.xpath("//*[contains(@ng-repeat,'orderObj.Passengers')]//span[contains(text(),'INFANT')]")).size()>0) {
						if((isSubmit || isNext) && sSubmit.equalsIgnoreCase("Y")) {
							queryObjects.logStatus(driver, Status.PASS, "Infant Pax ADC details are entered and Submit or Next button is enabled", "Blue or Green button after Infant ADC/APIS", null);
						}
						else
							queryObjects.logStatus(driver, Status.FAIL, "Infant Pax ADC details are entered and Submit or Next button is enabled", "Blue or Green button after Infant ADC/APIS", null);
					}
				} catch (Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Infant Pax ADC details Validation failed", "Blue or Green button after Infant ADC/APIS not working"+e.getMessage(),e);
					// TODO: handle exception
				}
			}
			
			if (isSubmit && sSubmit.equalsIgnoreCase("Y")) {
				queryObjects.logStatus(driver, Status.PASS, "Submit button was enabled, Document was verfied:"+DType, "Submit button was enabled ", null);
				driver.findElement(By.xpath(SubmitXpath)).click();
				Thread.sleep(1000);
				FlightSearch.loadhandling(driver);
				String scdoc=FlightSearch.getTrimTdata(queryObjects.getTestData("SecDocVerify"));
				if(sCheckinMsg.equalsIgnoreCase("PASSENGER INHIBITED - Please Verify ID") && (scdoc.equalsIgnoreCase(""))) {
					driver.findElement(By.xpath("//md-checkbox[@aria-label='verified id']/div")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath(SubmitXpath)).click();
					Thread.sleep(1000);
					FlightSearch.loadhandling(driver);
					queryObjects.logStatus(driver, Status.PASS, "Inhibited passenger is down with Verify ID", "Submit button was enabled and now we can CheckIn", null);
				} 
				if(driver.findElements(By.xpath("//div[@class='ng-binding msg-error' and contains(text(),'PLEASE PROVIDE EXIT DATE TIME')]")).size()>0){
					driver.findElement(By.xpath("//button[text()='OK']")).click();
					if (sCheckinMsg.contains("ValidateExitError")) {
						queryObjects.logStatus(driver, Status.PASS, "Check the ADC Error response for Exit date is displayed for the APIS data", "'PLEASE PROVIDE EXIT DATE TIME' message is displayed", null);
						sCheckinMsg = sCheckinMsg+"Message Displayed";
					}
					FlightSearch.loadhandling(driver);
					sExitDays =FlightSearch.getTrimTdata(queryObjects.getTestData("ExitDays"));
					boolean nextbt=false;
					if(sExitDays.equalsIgnoreCase("No")) {
						queryObjects.logStatus(driver, Status.PASS, "No need for exit date", "No return segment", null);
					}
					else {
						do{
							if(driver.findElements(By.xpath(ExitdateXpath)).size()>0 && driver.findElement(By.xpath(ExitdateXpath)).isEnabled()){
								Calendar calend = Calendar.getInstance();
								String sExitDate="";
								calend.add(Calendar.DATE, +10);
								sExitDate = new SimpleDateFormat("MM/dd/yyyy").format(calend.getTime());
								driver.findElement(By.xpath(ExitdateXpath)).clear();
								driver.findElement(By.xpath(ExitdateXpath)).sendKeys(sExitDate);
								Thread.sleep(1000);
								driver.findElement(By.xpath(ExitdateXpath)).sendKeys(Keys.TAB);	
							}
							nextbt=driver.findElements(By.xpath(NextXpath)).size()>0;
							if (nextbt)
								driver.findElement(By.xpath(NextXpath)).click();
						}while(nextbt);
					}
					driver.findElement(By.xpath(SubmitXpath)).click();
					Thread.sleep(1000);
					FlightSearch.loadhandling(driver);
					try {						
						 boolean isenabledformulpax =false;					
							try {
									Thread.sleep(2000);
									isNext = driver.findElement(By.xpath(AdsResponsePopup)).isEnabled();
									if(isNext){
									   if ((driver.findElements(By.xpath("//td[contains(text(),'ADCNOTAVAILABLE')]")).size()>0 || driver.findElement(By.xpath("//div[contains(@ng-repeat,'adcDecision.passengers')]//span")).getText().contains("ADC NOT OK"))&& BypassADC.contains("ADCdown")) {//Jenny -ADCNOTAVILABLE
											queryObjects.logStatus(driver, Status.INFO, "Unable to verify ADC message since ADC link is down", "Hence ADC has been bypassed", null);
											ByPassADC_ADCErr_Down(driver, queryObjects);
										} else {
											queryObjects.logStatus(driver, Status.PASS, "ADS/APIS verified", "ADS/APIS verified", null);
											driver.findElement(By.xpath(AdsResponsePopup)).click();
										}
									 } 
									FlightSearch.loadhandling(driver);
									if (driver.findElement(By.xpath("//span[contains(text(),'APIS Incomplete')]")).isDisplayed()&& !BypassADC.equalsIgnoreCase("No Bypass")) {
										driver.findElement(By.xpath("//md-checkbox[contains(@ng-change,'BYPASSADC')]/div[1]")).click();
										FlightSearch.loadhandling(driver);
										driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
										Thread.sleep(1000);
									}
					}catch(Exception e) {}
				}
                catch(Exception e){}
				}
				//if((FlightSearch.getTrimTdata(queryObjects.getTestData("RemarksUpdate")).equalsIgnoreCase("ADCUpdate"))) {
				if((PNRRemarks.equalsIgnoreCase("ADCUpdate"))) {
					String sOrderNum = Login.PNRNUM.trim();
					driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
					Thread.sleep(2000);
					driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Reservations')]")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//pssgui-search-panel[@label='pssgui.search.reservations']/div/md-input-container/input[@ng-model='searchPanel.searchText']")).sendKeys(sOrderNum);
					Thread.sleep(500);
					driver.findElement(By.xpath("//pssgui-search-panel[@label='pssgui.search.reservations']/div/i[@class='icon-search']")).click();
					FlightSearch.loadhandling(driver);
					Thread.sleep(500);
					driver.findElement(By.xpath("//div[contains(text(),'Remarks')]")).click();
					Thread.sleep(2000);
					if(driver.findElements(By.xpath("//div[contains(text(),'ADC/OK')]")).size()>0) {
						queryObjects.logStatus(driver, Status.PASS, "ADC remarks updated in PNR ", "ADC deatails found on remarks TAB in PNR", null);
						driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
						Thread.sleep(2000);
						driver.findElement(By.xpath(CheckInXpath)).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath(OrderNumXpath)).sendKeys(sOrderNum);
						Thread.sleep(2000);
						driver.findElement(By.xpath(SearchXpath)).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath(ProceedCheckInXpath)).click();
						FlightSearch.loadhandling(driver);
					}
					else {
						queryObjects.logStatus(driver, Status.FAIL, "ADC remarks not being updated in PNR ", "ADC deatails missing on remarks TAB in PNR", null);
					}
				}
				//if((FlightSearch.getTrimTdata(queryObjects.getTestData("RemarksUpdate")).equalsIgnoreCase("ADCBypassUpdate"))) {
				if((PNRRemarks.equalsIgnoreCase("ADCBypassUpdate"))) {
					String sOrderNum = Login.PNRNUM.trim();
					if(driver.findElements(By.xpath("//button[contains(text(),'OK')]")).size()>0) {
						driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
						Thread.sleep(1000);
						EnterDestiDoc(driver,queryObjects);
						
						if (driver.findElement(By.xpath("//span[contains(text(),'APIS Incomplete')]")).isDisplayed()&& !BypassADC.equalsIgnoreCase("No Bypass")) {
							driver.findElement(By.xpath("//md-checkbox[contains(@ng-change,'BYPASSADC')]/div[1]")).click();
							FlightSearch.loadhandling(driver);
							Thread.sleep(500);
							driver.findElement(By.xpath("(//md-select[@ng-model='menuCtrl.menuModel']//span[@class='md-select-icon'])[2]")).click();
							Thread.sleep(1000);
							if(!BypassADC.isEmpty() && !BypassADC.equalsIgnoreCase("ADCdown") && !BypassADC.equalsIgnoreCase("No Bypass")) {
								driver.findElement(By.xpath("//div[contains(@class,'md-active md-clickable')]//div[contains(text(),'"+BypassADC+"')]")).click();
							}
							else{
								driver.findElement(By.xpath("(//div[contains(@class,'md-active md-clickable')]//div[contains(@ng-repeat,'menuLabels')])[1]")).click();
							}
							Thread.sleep(1000);
							driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
							FlightSearch.loadhandling(driver);
						}
						driver.findElement(By.xpath(SubmitXpath)).click();
						FlightSearch.loadhandling(driver);
						Thread.sleep(1000);
					}
					driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
					Thread.sleep(2000);
					driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Reservations')]")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//pssgui-search-panel[@label='pssgui.search.reservations']/div/md-input-container/input[@ng-model='searchPanel.searchText']")).sendKeys(sOrderNum);
					Thread.sleep(500);
					driver.findElement(By.xpath("//pssgui-search-panel[@label='pssgui.search.reservations']/div/i[@class='icon-search']")).click();
					FlightSearch.loadhandling(driver);
					Thread.sleep(500);
					driver.findElement(By.xpath("//div[contains(text(),'Remarks')]")).click();
					Thread.sleep(2000);
					if(driver.findElements(By.xpath("//div[contains(text(),'ADC/BYPASSED')]")).size()>0) {
						queryObjects.logStatus(driver, Status.PASS, "ADC remarks updated in PNR ", "ADC details found on remarks TAB in PNR", null);
						driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
						Thread.sleep(2000);
						driver.findElement(By.xpath(CheckInXpath)).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath(OrderNumXpath)).sendKeys(sOrderNum);
						Thread.sleep(2000);
						driver.findElement(By.xpath(SearchXpath)).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath(ProceedCheckInXpath)).click();
						FlightSearch.loadhandling(driver);
					}
					else {
						queryObjects.logStatus(driver, Status.FAIL, "ADC remarks not being updated in PNR ", "ADC deatails missing on remarks TAB in PNR", null);
					}
				}
			}
			else if (sSubmit.equalsIgnoreCase("N")) {
				String sErr = FlightSearch.getTrimTdata(queryObjects.getTestData("Expected_ErrMessage"));
				if(sErr!="") {
					if(sErr.equalsIgnoreCase("Exp_Date")) {
						queryObjects.logStatus(driver, Status.PASS, "Unable to Checkin for invalid Expiry date ","Need to enter valid expiry date", null);
						return;
					}
					driver.findElement(By.xpath(SubmitXpath)).click();
					Thread.sleep(1000);
					FlightSearch.loadhandling(driver);
					if(driver.findElements(By.xpath("//div[@class='ng-binding msg-error' and contains(text(),'PLEASE PROVIDE EXIT DATE TIME')]")).size()>0){
						driver.findElement(By.xpath("//button[text()='OK']")).click();
						FlightSearch.loadhandling(driver);
						boolean nextbt=false;
						do{
							if(driver.findElements(By.xpath(ExitdateXpath)).size()>0 && driver.findElement(By.xpath(ExitdateXpath)).isEnabled()){
								Calendar calend = Calendar.getInstance();
								String sExitDate="";
								calend.add(Calendar.DATE, +10);
								sExitDate = new SimpleDateFormat("MM/dd/yyyy").format(calend.getTime());
								driver.findElement(By.xpath(ExitdateXpath)).sendKeys(sExitDate);
								Thread.sleep(1000);
								driver.findElement(By.xpath(ExitdateXpath)).sendKeys(Keys.TAB);	
							}
							nextbt=driver.findElements(By.xpath(NextXpath)).size()>0;
							if (nextbt)
								driver.findElement(By.xpath(NextXpath)).click();
						}while(nextbt);
						driver.findElement(By.xpath(SubmitXpath)).click();
						Thread.sleep(1000);
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//button[text()='OK']")).click();
						FlightSearch.loadhandling(driver);
					}

					//String sErrdisplayed = driver.findElement(By.xpath("//span[contains(@ng-class,'msg-error')]")).getText();
					//Updated by Jenny - Oct 2019
					String sErrdisplayed = "";
					if (driver.findElements(By.xpath("//span[contains(@ng-class,'msg-error')]")).size()>0) {
						sErrdisplayed = driver.findElement(By.xpath("//span[contains(@ng-class,'msg-error')]")).getText();
					}
					if (sErrdisplayed.contains(sErr)) {
						queryObjects.logStatus(driver, Status.PASS,"Displayed error"+sErr, "Displayed Error", null);
					}
					else {
						queryObjects.logStatus(driver, Status.FAIL,"Displayed error "+sErr, "Displayed Error", null);	

					}

				}

				else if((!isSubmit)){
					queryObjects.logStatus(driver, Status.PASS, "Submit button was not enabled, Document was verfied invalid:"+DType, "Submit button was not enabled ", null);
				}

			}
			else if(isNext && sSubmit.equalsIgnoreCase("Y")){

				driver.findElement(By.xpath(NextXpath)).click();
				FlightSearch.loadhandling(driver);
				queryObjects.logStatus(driver, Status.PASS, "Submit button was enabled, Document was verfied:"+DType, "Submit button was enabled ", null);
			}

			else		
			{
				queryObjects.logStatus(driver, Status.FAIL, "Submit button was not enabled, Document was not verfied:"+DType, "Submit button was not enabled ", null);
			}
			
			//-----------------------------
			//Adding the secondary doc validation with in the loop - Jenny
			if (driver.findElements(By.xpath("//div[@translate='pssgui.adc.response']")).size()==0) {
				if (SecDocVerify.equalsIgnoreCase("Y") || (driver.findElements(By.xpath("//span[@class='ng-binding msg-alert']")).size()>0 && driver.findElements(By.xpath(SecondaryDoc)).size()>0)) {
					driver.findElement(By.xpath("//div[@translate='pssgui.secondary.document']/..")).click();
					String StopExecution = FlightSearch.getTrimTdata(queryObjects.getTestData("StopafterADCMsg"));
					if (!ADCMsgs.isEmpty()&& StopExecution.equalsIgnoreCase("Y"))
					{
						//do nothing
					}
					else {
						EnterSecDoc(driver,queryObjects, Itm);
						if (!FlightSearch.getTrimTdata(queryObjects.getTestData("Street_Address")).isEmpty() || driver.findElements(By.xpath(DestinationAddress)).size()>0) {
							EnterDestiDoc(driver,queryObjects);
						}
						isSubmit = driver.findElements(By.xpath(SubmitXpath)).size()>0;
						isNext=driver.findElements(By.xpath(NextXpath)).size()>0;
						if (isSubmit) {
							driver.findElement(By.xpath(SubmitXpath)).click();
						} else if (isNext) {
							driver.findElement(By.xpath(NextXpath)).click();
						}
						FlightSearch.loadhandling(driver);
						Thread.sleep(1000);	
						
						if ((!isSubmit) && SecDocSubmit.equalsIgnoreCase("N")) {
							queryObjects.logStatus(driver, Status.PASS, "Submit button status ", "Submit button was not enabled, Secondary Document was verIfied invalid ", null);	
						}
					}
					if(sCheckinMsg.equalsIgnoreCase("PASSENGER INHIBITED - Please Verify ID")) {
						driver.findElement(By.xpath("//div[@translate='pssgui.primary.document']")).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("//md-checkbox[@aria-label='verified id']/div")).click();
						Thread.sleep(2000);
						driver.findElement(By.xpath(SubmitXpath)).click();
						FlightSearch.loadhandling(driver);
						queryObjects.logStatus(driver, Status.PASS, "Inhibited passenger is down with Verify ID", "Submit button was enabled and now we can CheckIn", null);
					}
				}
			}
			if (sCheckinMsg.contains("ValidateExitError")){
				String PNR[] = Login.MultiplePNR.trim().split(";");
				if (!sCheckinMsg.contains("Message Displayed")) {
					queryObjects.logStatus(driver, Status.FAIL, "Check the ADC Error response for Exit date is displayed for the APIS data", "'PLEASE PROVIDE EXIT DATE TIME' message is not displayed", null);
				} else {
					driver.findElement(By.xpath("//div[@translate='pssgui.back']/..")).click();
					FlightSearch.loadhandling(driver);
					if (!FlightSearch.getTrimTdata(queryObjects.getTestData("Icon_Validations")).isEmpty()) {
						String ColorVal = ""; String SpltColor[] = null; String  IcnName = "";
						SpltColor = FlightSearch.getTrimTdata(queryObjects.getTestData("Icon_Validations")).split("-");
						for (int ic = 0; ic < SpltColor.length; ic++) {
							ColorVal = ""; IcnName = "";
							if (SpltColor[ic].contains("ADC")) {
								ColorVal = driver.findElement(By.xpath("(//i[contains(@class,'icon-adc')])["+(Itm+1)+"]")).getCssValue("color"); IcnName = "ADC";
							} else if (SpltColor[ic].contains("APIS")) {
								ColorVal = driver.findElement(By.xpath("(//i[contains(@class,'icon-apis')])["+(Itm+3)+"]")).getCssValue("color"); IcnName = "APIS";
							}
							if (SpltColor[ic].contains(ColorsValidation(ColorVal))) {
								queryObjects.logStatus(driver, Status.PASS, "Check the "+IcnName+" icon color display in Proceed to Checkin page", IcnName+ " icon displayed in "+SpltColor[ic].replace(IcnName, "")+ " color. Validation successful" , null);
							}else {
								queryObjects.logStatus(driver, Status.FAIL, "Check the "+IcnName+" icon color display in Proceed to Checkin page", IcnName+ "icon is not in "+SpltColor[ic].replace(IcnName, "")+ " color. Validation failed" , null);
							}					
						}
						driver.findElement(By.xpath("//button[text()='Proceed to Check In']")).click();
						FlightSearch.loadhandling(driver);
					}
				}				
			}
			if (driver.findElements(By.xpath("//div[@translate='pssgui.adc.response']")).size()>0) {
				if (driver.findElements(By.xpath("//td[contains(text(),'ADC Not Available')]")).size()>0 && BypassADC.contains("ADCdown")) {
					ByPassADC_ADCErr_Down(driver, queryObjects);
				}
				
			}
			//Check the message display in ADC response popup
			String StopExecution = FlightSearch.getTrimTdata(queryObjects.getTestData("StopafterADCMsg"));
			if (sCheckinMsg.contains("ADC response popup")) {
				//span[@class='ng-binding msg-error'] -- Top message displayes(PLEASE PROVIDE EXIT DATE TIME1)
				//div[contains(@ng-repeat,'adcDecision.passengers')]//span - Error warning icon(ADC ERROR;)
				//ADC ERROR;APIS Incomplete;
				if (driver.findElements(By.xpath("//div[@translate='pssgui.adc.response']")).size()>0) {
					RespMsg = driver.findElement(By.xpath("//div[contains(@ng-repeat,'adcDecision.passengers')]//span")).getText();
					if (RespMsg.contains(sErr)) {
						queryObjects.logStatus(driver, Status.PASS, "Check the ADC response pop up display after submitting pax details in ADC/APIS screen", sErr+" is displayed", null);
						//Other messages
						String MsgXpath = "(//div[@translate='pssgui.adc.response']/../..//div[@class='ng-binding'])";
						if (!ADCMsgs.isEmpty()) {
							if (driver.findElements(By.xpath(MsgXpath)).size()>0) {
								ArrMsg = ADCMsgs.split("-");
								for (int am = 0; am < ArrMsg.length; am++) {
									isResponse = false;
									for (int md = 1; md <= driver.findElements(By.xpath(MsgXpath)).size(); md++) {
										OtherMsgs = ""; OtherMsgs = driver.findElement(By.xpath(MsgXpath+"["+md+"]")).getText();
										if (OtherMsgs.contains(ArrMsg[am])) {
											isResponse = true;
										}
									}
									if (isResponse) {
										queryObjects.logStatus(driver, Status.PASS, "Validate the expected Response displayed in the ADC pop up after submitting ADC information", ArrMsg[am]+" message is displayed, Validation successful", null);
										if (StopExecution.equalsIgnoreCase("Y"))
										return;
									} else {
										queryObjects.logStatus(driver, Status.FAIL, "Validate the expected Response displayed in the ADC pop up after submitting ADC information", ArrMsg[am]+" message is not displayed, Validation failed", null);
									}
								}
							}else {
								queryObjects.logStatus(driver, Status.FAIL, "Check the Response in the ADC pop up", "No message is displayed, ADC response validation failed", null);
							}
						}						
						//Passport required! Except for: Passengers with an Authorization for Parole of an Alien into the United States (Form I-512).
						//Visa required! Except for: Passengers with an Authorization for Parole of an Alien into the United States (Form I-512).
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Check the ADC response pop up display after submitting pax details in ADC/APIS screen", sErr+" message is not displayed, ADC response displayed is "+RespMsg, null);
					}
					driver.findElement(By.xpath(AdsResponsePopup)).click();
				}
			} else if (driver.findElements(By.xpath("//div[@translate='pssgui.adc.response']")).size()>0) {
				RespMsg = driver.findElement(By.xpath("//div[contains(@ng-repeat,'adcDecision.passengers')]//span")).getText();
				if(sErr.contains("ADC CONDITIONAL OK")) {
					queryObjects.logStatus(driver, Status.PASS, "Check the ADC/APIS is successfully completed", "ADC CONDITIONAL OK message is displayed, ADC/APIS passed", null);
				} else if (driver.findElements(By.xpath("//tr[@ng-repeat='decision in adcPassenger.Decisions']//td[contains(text(),'ADC Not Available')]")).size()>0) {
					queryObjects.logStatus(driver, Status.FAIL, "Check the ADC/APIS is successfully completed", "ADC Not Available message is displayed, ADC/APIS failed", null);
				} else {
					queryObjects.logStatus(driver, Status.FAIL, "Check the ADC/APIS is successfully completed", RespMsg+" message is displayed, ADC/APIS failed", null);
				}
			}
			
			if (FlightSearch.getTrimTdata(queryObjects.getTestData("SecDocVerify")).equalsIgnoreCase("VISASCREEN")) {
				if(driver.findElement(By.xpath(AdsResponsePopup)).isDisplayed()|| driver.findElement(By.xpath("//span[contains(text(),'Enter Visa Details for')]")).isDisplayed()) {
					queryObjects.logStatus(driver, Status.PASS, "Secondary document verify - Visa screen ", "'Enter Visa Details' screen is displayed ", null);
					driver.findElement(By.xpath(AdsResponsePopup)).click();
				}else {
					queryObjects.logStatus(driver, Status.FAIL, "Secondary document verify - Visa screen ", "'Enter Visa Details' screen is not displayed ", null);
				}
			}
			//Updated by Jenny - Oct 2019
			if (sCheckinMsg.contains("ADC Auto Bypass") && !isNext) {
				if(driver.findElements(By.xpath("//span[contains(text(),'ADC Auto Bypassed;')]")).size()> 0 && driver.findElements(By.xpath(APISComplete)).size()> 0) {
					queryObjects.logStatus(driver, Status.PASS, "Security Doc Verification screen - Check the ADC Auto bypassed message display for the passenger with Nationality same as Final destination", "ADC Auto Bypassed; APIS Complete message is displayed", null);
				} else {
					queryObjects.logStatus(driver, Status.FAIL, "Security Doc Verification screen - Check the ADC Auto bypassed message display for the passenger with Nationality same as Final destination", "ADC Auto Bypassed; APIS Complete message is not displayed", null);
				}						
			}
			if (sCheckinMsg.contains("ADC CONDITIONAL OK") && !isNext) {//Added by Jenny 12 Nov 2019
				if(driver.findElements(By.xpath("//span[contains(text(),'ADC CONDITIONAL OK;')]")).size()> 0) {
					queryObjects.logStatus(driver, Status.PASS, "Security Doc Verification screen - Check the ADC response ADC CONDITIONAL OK is displayed for the passenger after submitting the PAX details", "ADC CONDITIONAL OK; message is displayed", null);
				} else {
					queryObjects.logStatus(driver, Status.FAIL, "Security Doc Verification screen - Check the ADC response ADC CONDITIONAL OK is displayed for the passenger after submitting the PAX details", "ADC CONDITIONAL OK; message is not displayed", null);
				}						
			}
			if (sCheckinMsg.contains("APIS Complete") && !isNext) {//Added by Jenny 12 Nov 2019
				if(driver.findElements(By.xpath(APISComplete)).size()> 0) {
					queryObjects.logStatus(driver, Status.PASS, "Security Doc Verification screen - Check APIS Complete is displayed for the passenger after submitting the PAX details", "APIS Complete; message is displayed", null);
				} else {
					queryObjects.logStatus(driver, Status.FAIL, "Security Doc Verification screen - Check APIS Complete is displayed for the passenger after submitting the PAX details", "APIS Complete; message is not displayed", null);
				}						
			}
			if (sCheckinMsg.contains("ADC OK APIS Complete") && !isNext) {//Added by Jenny 12 Nov 2019
				try {
					if(driver.findElements(By.xpath("//span[contains(text(),'ADC OK;')]")).size()> 0 && driver.findElements(By.xpath(APISComplete)).size()> 0|| sErr.contains("ADC CONDITIONAL OK")) {
						queryObjects.logStatus(driver, Status.PASS, "Security Doc Verification screen - Check the ADC OK; APIS Complete message display for the passenger when Nationality differs from Final destination", "ADC OK; APIS Complete; message is displayed", null);
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Security Doc Verification screen - Check the ADC OK message display for the passenger when Nationality differs from Final destination", "ADC OK; APIS Complete; message is not displayed", null);
					}
				}catch(Exception e) {}						
			}
			//Adding the secondary doc validation with in the loop - Jenny
			if (driver.findElements(By.xpath("//div[@translate='pssgui.adc.response']")).size()==0) {
				if (SecDocVerify.equalsIgnoreCase("Y") || (driver.findElements(By.xpath("//span[@class='ng-binding msg-alert']")).size()>0 && driver.findElements(By.xpath(SecondaryDoc)).size()>0)) {
					if (!FlightSearch.getTrimTdata(queryObjects.getTestData("document_number")).isEmpty()) {
						driver.findElement(By.xpath("//div[@translate='pssgui.secondary.document']/..")).click();
						EnterSecDoc(driver,queryObjects, Itm);
						if (!FlightSearch.getTrimTdata(queryObjects.getTestData("Street_Address")).isEmpty() || driver.findElements(By.xpath(DestinationAddress)).size()>0) {
							EnterDestiDoc(driver,queryObjects);
						}
						isSubmit = driver.findElements(By.xpath(SubmitXpath)).size()>0;
						isNext=driver.findElements(By.xpath(NextXpath)).size()>0;
						if (isSubmit) {
							driver.findElement(By.xpath(SubmitXpath)).click();
						} else if (isNext) {
							driver.findElement(By.xpath(NextXpath)).click();
						}
						FlightSearch.loadhandling(driver);
						Thread.sleep(1000);					 
						if ((!isSubmit) && SecDocSubmit.equalsIgnoreCase("N")) {
							queryObjects.logStatus(driver, Status.PASS, "Submit button status ", "Submit button was not enabled, Secondary Document was verIfied invalid ", null);	
						}
						if(sCheckinMsg.equalsIgnoreCase("PASSENGER INHIBITED - Please Verify ID")) {
							driver.findElement(By.xpath("//div[@translate='pssgui.primary.document']")).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath("//md-checkbox[@aria-label='verified id']/div")).click();
							Thread.sleep(2000);
							driver.findElement(By.xpath(SubmitXpath)).click();
							FlightSearch.loadhandling(driver);
							queryObjects.logStatus(driver, Status.PASS, "Inhibited passenger is down with Verify ID", "Submit button was enabled and now we can CheckIn", null);
						}
					}					
				}
			}
			if (PNRRemarks.contains("DELETE") && !isNext) {
				driver.findElement(By.xpath("//button[@translate='pssgui.delete']")).click();
				Thread.sleep(200);
				driver.findElement(By.xpath("//button[contains(@class,'md-primary md-confirm-button')]")).click();
				FlightSearch.loadhandling(driver);
				if (driver.findElement(By.xpath("//span[contains(text(),'APIS Incomplete')]")).isDisplayed()) {
					queryObjects.logStatus(driver, Status.PASS, "Delete APIS Information ", "APIS Incomplete message is displayed ", null);
				} else {
					queryObjects.logStatus(driver, Status.FAIL, "Delete APIS Information ", "Deletion failed, APIS Incomplete message is not displayed ", null);
				}
			}
			if (PNRRemarks.contains("APIS Remarks") && !isNext) {
				//Click the PNR link from Security Document Verification page
				driver.findElement(By.xpath("//span[contains(text(),'"+PnrNum+"')]")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//div[contains(text(),'Remarks')]")).click();
				Thread.sleep(2000);//div[contains(@class,'remark-row')]//div[@class='pssgui-link ng-binding']
				if (PNRRemarks.contains("DELETE")) {
					if(driver.findElements(By.xpath("//div[contains(text(),'ADC')]")).size()==0) {
																																									
						queryObjects.logStatus(driver, Status.PASS, "Delete APIS information and check the Remarks is deleted in the PNR", "PNR remarks is deleted ", null);
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Delete APIS information and check the Remarks is deleted in the PNR", "PNR remarks is not deleted", null);
					}
				} else {
					if(driver.findElements(By.xpath("//div[contains(text(),'"+PNRRemarks.substring(PNRRemarks.indexOf(";")+1, PNRRemarks.length())+"')]")).size()>0) {
						String Remarks = driver.findElement(By.xpath("//div[contains(text(),'"+PNRRemarks.substring(PNRRemarks.indexOf(";")+1, PNRRemarks.length())+"')]")).getText();
						queryObjects.logStatus(driver, Status.PASS, "Check the Remarks updated in the PNR after ADC update", "PNR remarks is updated as "+Remarks, null);
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Check the Remarks updated in the PNR after ADC update", "PNR remarks is not updated", null);
					}
				}
				
			}
			//*------------------------------------------------------------------------------------*//Jenny 2019
			
		} catch (Exception e) {
			// TODO: handle exception
			try {
				Thread.sleep(3000);
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//md-option[div[normalize-space(text())='Diplomatic Passport']]")).click();
				FlightSearch.loadhandling(driver);
				Thread.sleep(1000);
				if (driver.findElements(By.xpath("//span[contains(text(),'UNABL TO PROCESS')]")).size()>0) {
					queryObjects.logStatus(driver, Status.FAIL, "Unable to complete APIS", "Either booked flight is codeshare or Other airline flight, unable to process message is displayed", e);
				}
			} catch (Exception e2) {
				try {
					if(driver.findElement(By.xpath(DoneXpath)).isEnabled()) {
						queryObjects.logStatus(driver, Status.PASS, "Enter ADC Details", "ADC details available and SUbmit button is enabled", null);	
					}
					}catch (Exception e1) {
						queryObjects.logStatus(driver, Status.WARNING, "Enter ADC Details", "ADC details exception", e1);}
			}
			

		}
	}

	///suman
	public static String OpenFlightSearch(WebDriver driver, BFrameworkQueryObjects queryObjects, String Opt) throws IOException
	{   

		String Flightno=""; String POS = ""; String Destn = "";
		try
		{

			List<WebElement> NoofFlight=driver.findElements(By.xpath("//md-content/table[contains(@ng-table,'flightDepartArrival')]/tbody/tr/child::td[5][contains(text(),'OPEN')]//preceding-sibling::td[4]"));
			List<String> Flight=new ArrayList<>();
			NoofFlight.forEach(a ->Flight.add(a.getText().trim()));

			for(int i=0;i<NoofFlight.size();i++)
			{
				NoofFlight=driver.findElements(By.xpath("//md-content/table[contains(@ng-table,'flightDepartArrival')]/tbody/tr/child::td[5][contains(text(),'OPEN')]//preceding-sibling::td[4]"));
				if (Opt.contains("GetFlight")) {
					
					if (!driver.findElement(By.xpath("(//md-content/table[contains(@ng-table,'flightDepartArrival')]/tbody/tr/child::td[5][contains(text(),'OPEN')])["+(i+1)+"]")).getText().contains("RESTRICT")) {
						Flightno=Flight.get(i);
						if (Flightno.length()<6) {
							POS = driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'flightData')])["+(i+1)+"]/td[2]")).getText().trim();
							Destn = POS.substring(6, POS.length());
							POS= POS.substring(0, 3);					
							Flightno = Flightno+";"+POS+"/"+Destn;
							break;
						}						
					}					
				} else {
					NoofFlight.get(i).click();
					FlightSearch.loadhandling(driver);
					if(driver.findElements(By.xpath("//pssgui-passenger-itinerary/div/div[contains(@ng-repeat,'passengerData in passengerItinerary')]")).size()>3)
					{    Flightno=Flight.get(i);
					queryObjects.logStatus(driver, Status.PASS, "Searching a flight", "Flight have Details",null);
					driver.findElement(By.xpath("//div[contains(text(),'Back')]")).click();
					FlightSearch.loadhandling(driver);
					break;
					}
					else
					{
						driver.findElement(By.xpath("//div[contains(text(),'Back')]")).click();
						FlightSearch.loadhandling(driver);

					}
				}
			}
		}
		catch(Exception e)
		{
			queryObjects.logStatus(driver, Status.PASS, "Searching a flight with more than two PNR", "Getting an exception while searching a flight"+e.getMessage(),e); 
		}
		return Flightno;
	}

	public static void ByPassADC_ADCErr_Down(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException, InterruptedException{
		queryObjects.logStatus(driver, Status.INFO, "Unable to verify ADC message since ADC link is down", "Hence ADC has been bypassed", null);
		try {
			driver.findElement(By.xpath(AdsResponsePopup)).click();
			FlightSearch.loadhandling(driver);			
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.INFO, "Select response popup", "Unable to close ADC response popup",null);
		}
		try {
			driver.findElement(By.xpath(NtionalXpath)).clear();
			if (BypassDestn.isEmpty()) {
				BypassDestn = "US";
			}
			driver.findElement(By.xpath(NtionalXpath)).sendKeys(BypassDestn);
			FlightSearch.loadhandling(driver);
			Thread.sleep(1000);
			//driver.findElement(By.xpath(NtionalXpath)).sendKeys(Keys.TAB);
			//Tab Alternate Solution
			try {
				driver.findElement(By.xpath(Login.SelList)).click();
			} catch (Exception e1) {
				try {
					driver.findElement(By.xpath(Login.SelList2)).click();
				} catch (Exception e) {
					try {
						driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='"+BypassDestn+"']/ancestor::span[2]/following-sibling::span/span/../../..")).click();
						}catch(Exception e3) {
							driver.findElement(By.xpath(NtionalXpath)).sendKeys(Keys.TAB);
						}
				}
			}
			Thread.sleep(2000);
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.INFO, "Nationality field is disabled", "",null);
		}
		try {
			driver.findElement(By.xpath("//md-checkbox[contains(@ng-change,'BYPASSADC')]/div[1]")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath(SubmitXpath)).click();
			FlightSearch.loadhandling(driver);
			Thread.sleep(1000);
			queryObjects.logStatus(driver, Status.PASS, "BYPass ADC", "ADC is bypassed",null);
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.INFO, "BYPass ADC", "Unable to bypass ADC",null);
		}	
		
	}

	public static void EnterSecDoc(WebDriver driver, BFrameworkQueryObjects queryObjects, int PaxItm) throws IOException{

//--Updated completely - Jenny
		try{   

			String secFName = FlightSearch.getTrimTdata(queryObjects.getTestData("SecfirstName"));
			String secSName = FlightSearch.getTrimTdata(queryObjects.getTestData("SecSurname"));
			String secDocNum = FlightSearch.getTrimTdata(queryObjects.getTestData("document_number"));
			String Sec_Date=FlightSearch.getTrimTdata(queryObjects.getTestData("Sec_Date"));
			String SecDocType = FlightSearch.getTrimTdata(queryObjects.getTestData("SecDocType"));
			String SecDocCOI = FlightSearch.getTrimTdata(queryObjects.getTestData("Sec_COI"));
			String ADCMsg =  FlightSearch.getTrimTdata(queryObjects.getTestData("ADCMsgs"));
			
			//Added by Jenny
			
			int PaxList = driver.findElements(By.xpath("//*[contains(@ng-repeat,'orderObj.Passengers')]/div/div/span")).size();
			String sdate = "";
			driver.findElement(By.xpath("//div[contains(text(),'Secondary Document')]/..")).click();
			FlightSearch.loadhandling(driver);
			Calendar calen = Calendar.getInstance();
			if (Sec_Date.contains("/")) {//Added by Jenny)
				sdate = Sec_Date;					
			} else {
				calen.add(Calendar.DATE, Integer.parseInt(Sec_Date));
				sdate = new SimpleDateFormat("MM/dd/yyyy").format(calen.getTime());
			}
			
			//boolean Con = driver.findElement(By.xpath(SecSurname)).isEnabled();	
			if (secFName.isEmpty()) {//Updated only for single PAX, yet to include multiple pax //Added by Jenny 12 Nov 2019
				driver.findElement(By.xpath("(//*[contains(@ng-repeat,'orderObj.Passengers')]/div/div/span)["+(PaxItm+1)+"]")).click();
				String SptPaxNm[] = driver.findElement(By.xpath("(//*[contains(@ng-repeat,'orderObj.Passengers')]/div/div/span)["+(PaxItm+1)+"]")).getText().split("/");
				secSName= SptPaxNm[0];
				secFName = SptPaxNm[1];
			}
			Thread.sleep(500);
			try {
			driver.findElement(By.xpath(SecSurname)).sendKeys(secSName);
			driver.findElement(By.xpath(SecFirstName)).sendKeys(secFName);
			}
			catch(Exception e) {}
			
			queryObjects.logStatus(driver, Status.PASS, "SEC-Name entered", "Both SecSurname and FirstName is entered",null); 
			if (SecDocType!="") {
				//driver.findElement(By.xpath("//md-select[@ng-model='document.DocType']//span[@class='md-select-icon']")).click();
				driver.findElement(By.xpath("(//md-select[(@ng-model='document.DocType')])[3]")).click();
				Thread.sleep(2000);
				
				// Yashodha 6-Nov-2019 Start //Validation of primary doc types CR167B				   
				if(FlightSearch.getTrimTdata(queryObjects.getTestData("Pri_Passport_Type")).equalsIgnoreCase("passport")) 
					PassportTypesvalidation(driver,queryObjects);
				else if(FlightSearch.getTrimTdata(queryObjects.getTestData("Pri_Passport_Type")).equalsIgnoreCase("document"))
					PriDocTypeValidation(driver,queryObjects);
				//Validation of primary doc types CR167B
				// Yashodha updated 7-Nov-2019 End
				
				//driver.findElement(By.xpath("//md-option/div[contains(text(),'"+SecDocType+"')]/..")).click();
				//driver.findElement(By.xpath("//div[@role='presentation' and @aria-hidden='false' ]//md-option/div[contains(text(),'" +SecDocType+ "')]")).click();
				FlightSearch.loadhandling(driver);
				List<WebElement> SecDocDet=	driver.findElements(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-option/div[contains(text(),'" +SecDocType+ "')]"));
				
					for (int iSecdoc = 0; iSecdoc < SecDocDet.size(); iSecdoc++) {
						String secnm=SecDocDet.get(iSecdoc).getText().trim();
						if(secnm.toUpperCase().contains(SecDocType.toUpperCase()))
							SecDocDet.get(iSecdoc).click();
						break;
					}
				Thread.sleep(2000);
			}
			else
			{
				driver.findElement(By.xpath("//md-select[(@ng-model='document.DocType')]//child::span/div")).click();
				Thread.sleep(2000);
				//Validation of primary doc types CR167B
				// Yashodha 6-Nov-2019 start
				if(FlightSearch.getTrimTdata(queryObjects.getTestData("Sec_Doc_Type")).equalsIgnoreCase("yes")) 
					SecDoctypevalidation(driver,queryObjects);
				//Validation of primary doc types CR167B
				// Yashodha 7-Nov-2019 End
				driver.findElement(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-select-menu[@role='presentation' and @class='_md md-overflow']//md-option/div[contains(text(),'US/CA Permanent resident card')]")).click();
				Thread.sleep(2000);
				
			}
			try {
			driver.findElement(By.xpath(SecDocNum)).sendKeys(secDocNum);
			}
			catch(Exception e) {}
			
			try {
				driver.findElement(By.xpath(SecDatePicker)).sendKeys(sdate);
			} catch (Exception e) {}
			try {
			driver.findElement(By.xpath("//input[contains(@name,'document_number') and contains(@class,'ng-valid-form-validate ng-valid-maxlength')]")).sendKeys(Keys.chord(Keys.TAB,sdate));
			}catch (Exception e) {}
			queryObjects.logStatus(driver, Status.PASS, "SEC-details entered", "Both Doc and date field entered",null); 
			FlightSearch.loadhandling(driver);
			FlightSearch.loadhandling(driver);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//label[contains(text(),'Country of Issuance')]/..//input[@aria-label='Country of Issuance' and contains(@class,'md-input ng-empty')]")).clear();
			if (!SecDocCOI.isEmpty()) 
				driver.findElement(By.xpath("//label[contains(text(),'Country of Issuance')]/..//input[@aria-label='Country of Issuance' and contains(@class,'md-input ng-empty')]")).sendKeys(SecDocCOI);
			else 
				//driver.findElement(By.xpath("//input[@name='country' and @aria-invalid='true']")).sendKeys("US");
				driver.findElement(By.xpath("//label[contains(text(),'Country of Issuance')]/..//input[@aria-label='Country of Issuance' and contains(@class,'md-input ng-empty')]")).sendKeys("US");

			FlightSearch.loadhandling(driver);
			//driver.findElement(By.xpath("//label[contains(text(),'Country of Issuance')]/..//input[@aria-label='Country of Issuance' and contains(@class,'md-input ng-invalid')]")).sendKeys(Keys.TAB);
			//Tab Alternate Solution
			try {
				driver.findElement(By.xpath(Login.SelList)).click();
			} catch (Exception e1) {
				try {
					driver.findElement(By.xpath(Login.SelList2)).click();
				} catch (Exception e) {
					try {
						driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='US']/ancestor::span[2]/following-sibling::span/span/../../..")).click();

						}catch(Exception e3) {
							driver.findElement(By.xpath("//label[contains(text(),'Country of Issuance')]/..//input[@aria-label='Country of Issuance' and contains(@class,'md-input ng-invalid')]")).sendKeys(Keys.TAB);
						}
				}
			}
			Thread.sleep(200);
		}
		catch(Exception e)
		{
			queryObjects.logStatus(driver, Status.FAIL, "Getting an exception while perfoeming a operation on Secondary documet", ""+e.getMessage(),e); 
		}
	}
	public static void EnterDestiDoc(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException{

		driver.findElement(By.xpath("//div[@translate='pssgui.destination.address']/..")).click();
		try {
			String destiStreet = FlightSearch.getTrimTdata(queryObjects.getTestData("Street_Address"));
			//boolean Con = driver.findElement(By.xpath(StreetAddress)).isEnabled();

			driver.findElement(By.xpath(StreetAddress)).sendKeys(destiStreet);
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.PASS, "Gettin an exception while performing a operation on Destination Address", ""+e.getMessage(),e); 
		}
	}

	public static String SelectFromPassengerList(WebDriver driver,BFrameworkQueryObjects queryObjects, String SearchVal, String FltrName, String FltrVal) throws Exception {
		String RetVal="";
		//Updated by Jenny - Oct 2019
		String FltrList = "";
			FltrList = FlightSearch.getTrimTdata(queryObjects.getTestData("FilterFlightList"));
		try {
			int TotalPAX;
			boolean Selectbutton = false;

			String SearchCriteria = "";
			//String sOrderID = Login.MultiplePNR.trim();
			String sOrderID ="";

			if (SearchVal!="") {
				SearchCriteria=SearchVal;
			}else {
				SearchCriteria = "- 1";
			}
			if (!sOrderID.isEmpty()) {
				SearchCriteria = sOrderID;					
			}
			//Updated by Jenny - Oct 2019
			if (FltrList.equalsIgnoreCase("ADC Auto Bypass")) {
				SearchCriteria = FlightSearch.getTrimTdata(queryObjects.getTestData("OrderNumber"));
			}
			/*String Filter_Name= FlightSearch.getTrimTdata(queryObjects.getTestData("Filter_Name"));
							String Filter_Val= FlightSearch.getTrimTdata(queryObjects.getTestData("Filter_Val"));*/

			if (FltrName !="") {

				driver.findElement(By.xpath("//md-select[@ng-model='menuCtrl.menuModel']/md-select-value/span[@class='md-select-icon']")).click();
				Thread.sleep(500);
				driver.findElement(By.xpath("//md-option[@ng-repeat='menuTbl in menuCtrl.menuItems']/div/div[contains(text(),'"+FltrName+"')]")).click();
				Thread.sleep(200);
				driver.findElement(By.xpath("//input[@ng-model='airportPassenger.model.searchText']")).sendKeys("1");
				driver.findElement(By.xpath("//input[@ng-model='airportPassenger.model.searchText']")).clear();
				driver.findElement(By.xpath("//input[@ng-model='airportPassenger.model.searchText']")).sendKeys(FltrVal);
				Thread.sleep(5000);
				List<WebElement> Checkbox_Cnt = driver.findElements(By.xpath(PaxDataChkBox));
				TotalPAX = Checkbox_Cnt.size();				
			} else {
				TotalPAX = Integer.parseInt(driver.findElement(By.xpath("(//div[contains(@class,'tab-content layout')]/div[@class='ng-binding'])[1]")).getText().trim());
			}

			if (FltrList.equalsIgnoreCase("Pax with Incomplete APIS")) {
				SearchCriteria = "- 1";
			}
			Selectbutton= false;
			try {
				if (TotalPAX>=1) {
					if (SearchCriteria.contains(";")) {
						String Spt_SearchVal[] = SearchCriteria.split(";");
						for (int i = 0; i < Spt_SearchVal.length; i++) {
							for (int k = 1; k <= TotalPAX; k++) {
								if (((driver.findElement(By.xpath("(//div[contains(@class,'passenger-list layout-row')])["+k+"]")).getText().trim())).contains(Spt_SearchVal[i])) {
									driver.findElement(By.xpath("(//md-checkbox[@ng-model='passengerData.chkBoxSelected'])["+k+"]")).click();
									Selectbutton = true;
									break;
								}
							}							
						}						
					}else {
						for (int k = 1; k <= TotalPAX; k++) {
							if (((driver.findElement(By.xpath("(//div[@class='passenger-list layout-row'])["+k+"]")).getText().trim())).contains(SearchCriteria)) {
								driver.findElement(By.xpath("(//md-checkbox[@ng-model='passengerData.chkBoxSelected'])["+k+"]")).click();
								Selectbutton = true;
								RetVal=driver.findElement(By.xpath("(//span[contains(@ng-click,'pnr')])["+k+"]")).getText();//Jenny
								if (Paxno.isEmpty()) {
									break;
								}								
							}
						}
					}
	
				}
			} catch(Exception e) {}
			if (Selectbutton) {
				try {
				if (driver.findElement(By.xpath("//button[@aria-label='Proceed to checkin']")).isEnabled()) {													
				} else {}	
				}catch (Exception e) {}
			} else {}

		}	catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Select Passenger from list", "Passenger selection failed", e);

			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return RetVal;

	}

	public static void Baggagedetails(WebDriver driver,BFrameworkQueryObjects queryObjects, String AddBagWt, String Catalog) throws IOException {
		try {

			String Oversize="";
			if (AddBagWt.contains("OS")) {
				AddBagWt = AddBagWt.replace("-OS", "");
				Oversize="Y";
			}
			driver.findElement(By.xpath(BagtypeXpath)).click();
			//List<WebElement> Options = driver.findElements(By.xpath(TopXpath));

			if(!Catalog.isEmpty()) {

				driver.findElement(By.xpath(TopXpath)).click();
				Thread.sleep(1000);

				driver.findElement(By.xpath("//*[contains(text(),'More')]")).click();
				Thread.sleep(1000);
				if (Catalog.equalsIgnoreCase("SPEQ")) {
					driver.findElement(By.xpath("//md-radio-button/div[contains(text(),'SURFBOARD UPTO 32KG' ) and not(contains(text(),'KITE'))]//parent::md-radio-button")).click();
					
					Thread.sleep(1000);
				}
				else if (Catalog.equalsIgnoreCase("FIRE")) {
					driver.findElement(By.xpath("//md-radio-button/div[contains(text(),'SPORT FIREARMS UP TO 32KG')]//parent::md-radio-button/div")).click();
					Thread.sleep(1000);
				}
				else if (Catalog.equalsIgnoreCase("GOLF")) {
					driver.findElement(By.xpath("//md-radio-button/div[contains(text(),'GOLF EQUIPMENT UP TO 32KG')]//parent::md-radio-button/div")).click();
					Thread.sleep(1000);
				}
				//WINDSURF EQPMT UP TO 45KG
				else if (Catalog.equalsIgnoreCase("SURF")) {
					driver.findElement(By.xpath("//md-radio-button/div[contains(text(),'WINDSURF EQPMT UP TO 45KG')]//parent::md-radio-button/div")).click();
					Thread.sleep(1000);
				}
				//POLE VAULT EQPMT UP TO 45KG
				else if (Catalog.equalsIgnoreCase("POLE")) {
					driver.findElement(By.xpath("//md-radio-button/div[contains(text(),'POLE VAULT EQPMT UP TO 45KG')]//parent::md-radio-button/div")).click();
					Thread.sleep(1000);
				}
				else if (Catalog.equalsIgnoreCase("SPORT45")) {
					driver.findElement(By.xpath("//md-radio-button/div[contains(text(),'SPORT FIREARMS UP TO 45KG')]//parent::md-radio-button/div")).click();
					Thread.sleep(1000);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL, "Please add the correct catalog code in test data", "Code missing for catalog value", null);	
				}
			}else {


				driver.findElement(By.xpath("//md-option[contains(@ng-value,'standard')]")).click();
			}
			driver.findElement(By.xpath(WeightXpath)).sendKeys(AddBagWt);
			int bagval=Integer.parseInt(AddBagWt);
			// if	(bagval>45){
			if(driver.findElements(By.xpath("//span[contains(text(),'Baggage weight exceeds')]")).size()>0){
				queryObjects.logStatus(driver, Status.PASS, "Baggage Exception validation", "Baggage Exception validation pass", null);
				driver.findElement(By.xpath(AdsResponsePopup)).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath(CancleXpath)).click();
				FlightSearch.loadhandling(driver);
				// driver.findElement(By.xpath(WeightXpath)).sendKeys("20");
			}
			// }
			else{
				driver.findElement(By.xpath(BagtagXpath)).click();

				driver.findElement(By.xpath(ManualXpath)).click();
				//driver.findElement(By.xpath("//md-option[div[contains(text(),'Manual')]]")).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath(TNumberXpath)).sendKeys(RandomStringUtils.randomNumeric(6));

				Thread.sleep(1000);
				if (Oversize.equalsIgnoreCase("Y")) {
					driver.findElement(By.xpath("//md-checkbox[@ng-model='bag.IsOversized']")).click();
				}
			}
		}catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Baggage Execption", "Baggage Execption", e);

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void HandleSecDoc(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception {
		boolean isSecDoc=false;
		boolean isDestDoc=false;
		String  IssecFName="";
		String IsdestiStreet="";
		boolean isSecDocSubmit=false;
		
		try{
			IssecFName = FlightSearch.getTrimTdata(queryObjects.getTestData("SecfirstName"));
			isSecDoc= driver.findElement(By.xpath(SecondaryDoc)).isDisplayed();

		}catch(Exception e) {
			// TODO: handle exception
		}
		//srini
		String SecDocVerify = FlightSearch.getTrimTdata(queryObjects.getTestData("SecDocVerify"));
		
		if((isSecDoc==false) && SecDocVerify.equalsIgnoreCase("Y")) 
		{
			queryObjects.logStatus(driver, Status.FAIL, "Checking Secondry document displayed", "Still Pointing to Primary", null);
			return;
		}
				
		try{
			IsdestiStreet = FlightSearch.getTrimTdata(queryObjects.getTestData("Street_Address"));
			isDestDoc= driver.findElement(By.xpath(DestinationAddress)).isDisplayed();

		}catch(Exception e) {
			// TODO: handle exception
		}


		try{
			if(driver.findElement(By.xpath(AdsResponsePopup)).isDisplayed())
			{
				if ((driver.findElements(By.xpath("//td[contains(text(),'ADCNOTAVAILABLE')]")).size()>0 || driver.findElement(By.xpath("//div[contains(@ng-repeat,'adcDecision.passengers')]//span")).getText().contains("ADC NOT OK")) && BypassADC.contains("ADCdown")) {//Jenny -ADCNOTAVILABLE
					queryObjects.logStatus(driver, Status.INFO, "Unable to verify ADC message since ADC link is down", "Hence ADC has been bypassed", null);
					ByPassADC_ADCErr_Down(driver, queryObjects);
				} else {
					queryObjects.logStatus(driver, Status.PASS, "Checking Secondry document need to submit:", "Secondary document is mandatory", null);
					driver.findElement(By.xpath(AdsResponsePopup)).click();
				}
				
			}
			
			}catch(Exception e) {}

		if(isSecDoc && (IssecFName!=""))
		{
			EnterSecDoc(driver,queryObjects, 0);
			isSecDocSubmit = driver.findElement(By.xpath(SubmitXpath)).isEnabled();
			if (FlightSearch.getTrimTdata(queryObjects.getTestData("SecDocSubmit")).equalsIgnoreCase("N") && !isSecDocSubmit) {
				queryObjects.logStatus(driver, Status.PASS, "Secondary document verification for expired date", "Submit button is disabled for the expired secondary document", null);
			} else {
				driver.findElement(By.xpath(SubmitXpath)).click();
				FlightSearch.loadhandling(driver);
				if(driver.findElements(By.xpath("//div[@class='ng-binding msg-error' and contains(text(),'PLEASE PROVIDE EXIT DATE TIME')]")).size()>0){
					driver.findElement(By.xpath("//button[text()='OK']")).click();
					FlightSearch.loadhandling(driver);
					boolean nextbt=false;
					do{
						if(driver.findElements(By.xpath(ExitdateXpath)).size()>0 && driver.findElement(By.xpath(ExitdateXpath)).isEnabled()){
							Calendar calend = Calendar.getInstance();
							String sExitDate="";
							calend.add(Calendar.DATE, +10);
							sExitDate = new SimpleDateFormat("MM/dd/yyyy").format(calend.getTime());
							driver.findElement(By.xpath(ExitdateXpath)).sendKeys(sExitDate);
							Thread.sleep(1000);
							driver.findElement(By.xpath(ExitdateXpath)).sendKeys(Keys.TAB);	
						}
						nextbt=driver.findElements(By.xpath(NextXpath)).size()>0;
						if (nextbt)
							driver.findElement(By.xpath(NextXpath)).click();
					}while(nextbt);

					driver.findElement(By.xpath(SubmitXpath)).click();
					Thread.sleep(1000);
					FlightSearch.loadhandling(driver);
					boolean isenabled1 =false;					
					try {
						Thread.sleep(2000);
						isenabled1 = driver.findElement(By.xpath(AdsResponsePopup)).isEnabled();
						if(isenabled1){
							queryObjects.logStatus(driver, Status.PASS, "ADS/APIS verified", "ADS/APIS verified", null);
							driver.findElement(By.xpath(AdsResponsePopup)).click();
						} 
						FlightSearch.loadhandling(driver);
						if (driver.findElement(By.xpath("//span[contains(text(),'APIS Incomplete')]")).isDisplayed()&& !BypassADC.equalsIgnoreCase("No Bypass")) {
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath(NtionalXpath)).clear();
							if (BypassDestn.isEmpty()) {
								BypassDestn = "US";



							}
							driver.findElement(By.xpath(NtionalXpath)).sendKeys(BypassDestn);
							FlightSearch.loadhandling(driver);
							Thread.sleep(1000);
							//driver.findElement(By.xpath(NtionalXpath)).sendKeys(Keys.TAB);
							//Tab Alternate Solution
							try {
								driver.findElement(By.xpath(Login.SelList)).click();
							} catch (Exception e1) {
								try {
									driver.findElement(By.xpath(Login.SelList2)).click();
								} catch (Exception e) {
									try {
										driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='"+BypassDestn+"']/ancestor::span[2]/following-sibling::span/span/../../..")).click();

										}catch(Exception e3) {
											driver.findElement(By.xpath(NtionalXpath)).sendKeys(Keys.TAB);
										}
								}
							}
							Thread.sleep(2000);
							driver.findElement(By.xpath("//md-checkbox[contains(@ng-change,'BYPASSADC')]/div[1]")).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
							Thread.sleep(1000);
							driver.findElement(By.xpath(SubmitXpath)).click();
							FlightSearch.loadhandling(driver);
							Thread.sleep(1000);
						}
						if ((driver.findElements(By.xpath("//td[contains(text(),'ADCNOTAVAILABLE')]")).size()>0 || driver.findElement(By.xpath("//div[contains(@ng-repeat,'adcDecision.passengers')]//span")).getText().contains("ADC NOT OK")) && BypassADC.contains("ADCdown")) {//Jenny -ADCNOTAVILABLE
							driver.findElement(By.xpath("//md-checkbox[contains(@ng-change,'BYPASSADC')]/div[1]")).click();
							FlightSearch.loadhandling(driver);
							driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
							Thread.sleep(1000);
							driver.findElement(By.xpath(SubmitXpath)).click();
							Thread.sleep(1000);
						}
					}catch(Exception e) {}

				}
			}
			FlightSearch.loadhandling(driver);
			boolean isenabled1 =false;					
			try {
				Thread.sleep(2000);
				isenabled1 = driver.findElement(By.xpath(AdsResponsePopup)).isEnabled();
				if(isenabled1){
					queryObjects.logStatus(driver, Status.PASS, "ADS/APIS verified", null, null);
					//driver.findElement(By.xpath(AdsResponsePopup)).click();//Check this case
				}
				if ((driver.findElements(By.xpath("//td[contains(text(),'ADCNOTAVAILABLE')]")).size()>0 || driver.findElement(By.xpath("//div[contains(@ng-repeat,'adcDecision.passengers')]//span")).getText().contains("ADC NOT OK")) && BypassADC.contains("ADCdown")) {//Jenny -ADCNOTAVILABLE
					ByPassADC_ADCErr_Down(driver, queryObjects);
				}
			}catch(Exception e) {}
			if(isenabled1){
				queryObjects.logStatus(driver, Status.PASS, "ADS/APIS verified", null, null);
				driver.findElement(By.xpath(AdsResponsePopup)).click();
			}
			if (driver.findElements(By.xpath("//td[contains(text(),'ADCNOTAVAILABLE')]")).size()>0 && AdcBypass.contains("ADC_Bypass")) {//Jenny -ADCNOTAVILABLE
				driver.findElement(By.xpath("//md-checkbox[contains(@ng-change,'BYPASSADC')]/div[1]")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath(SubmitXpath)).click();
				FlightSearch.loadhandling(driver);
				Thread.sleep(1000);
			}			
		}

		try{
			IsdestiStreet = FlightSearch.getTrimTdata(queryObjects.getTestData("Street_Address"));
			isDestDoc= driver.findElement(By.xpath(DestinationAddress)).isDisplayed();

		}catch(Exception e) {
			// TODO: handle exception
		}

		if(isDestDoc && (IsdestiStreet!="") )
		{
			try {
			EnterDestiDoc(driver,queryObjects);
			driver.findElement(By.xpath(SubmitXpath)).click();
			FlightSearch.loadhandling(driver);
			}catch(Exception e) {}
		}
		else if(IsdestiStreet!="") {
			queryObjects.logStatus(driver, Status.INFO, "Expected Destination doc Page was not displayed","Expected Destination doc Page was not dispalyed", null);
		}
		//add pop up handle - click ok button
		boolean isenabled =false;					
		try {
			Thread.sleep(2000);
			isenabled = driver.findElement(By.xpath(AdsResponsePopup)).isEnabled();
		}catch(Exception e) {}
		if(isenabled){
			queryObjects.logStatus(driver, Status.PASS, "ADS/APIS verified", "ADS/APIS verified", null);
			driver.findElement(By.xpath(AdsResponsePopup)).click();
		}
		if (isenabled && AdcBypass.contains("ADC_Bypass")) {//Jenny -ADCNOTAVILABLE
			driver.findElement(By.xpath("//md-checkbox[contains(@ng-change,'BYPASSADC')]/div[1]")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath(SubmitXpath)).click();
			FlightSearch.loadhandling(driver);
			Thread.sleep(1000);
		}
		boolean FLAG=false;//Updated by Jenny
		try {
			FLAG = driver.findElement(By.xpath("//span[contains(text(),'APIS Incomplete')]")).isDisplayed();
		} catch (Exception e) {}

		// if(IssecFName.isEmpty() && FLAG) {
		if( FLAG && !BypassADC.equalsIgnoreCase("No Bypass")) {	 

			driver.findElement(By.xpath("//md-checkbox[contains(@ng-change,'BYPASSADC')]/div[1]")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
			Thread.sleep(1000);
			try{
				driver.findElement(By.xpath(SubmitXpath)).click();
				FlightSearch.loadhandling(driver);
				Thread.sleep(1000);
			}
			catch(Exception e) {
				// TODO: handle exception
				queryObjects.logStatus(driver, Status.INFO,"Submit button nor required / submit done","submit",null);
			}
			Thread.sleep(1000); 
		}
		//meenu
		try{
			
			IsdestiStreet = FlightSearch.getTrimTdata(queryObjects.getTestData("Street_Address"));
			isDestDoc= driver.findElement(By.xpath(DestinationAddress)).isDisplayed();

		}catch(Exception e) {
			// TODO: handle exception
		}

		if(isDestDoc && (IsdestiStreet!="") )
		{
			try {
			EnterDestiDoc(driver,queryObjects);
			driver.findElement(By.xpath(SubmitXpath)).click();
			FlightSearch.loadhandling(driver);
			}catch(Exception e) {}
		}
		else if(IsdestiStreet!="") {
			queryObjects.logStatus(driver, Status.INFO, "Expected Destination doc Page was not dispalyed","Expected Destination doc Page was not dispalyed", null);
		}//meenu_end
	}

	//i[@ng-if='!baggageInfo.editFee']


	public static void EditBagFee(WebDriver driver,String sPassengerName) throws IOException {
		try {
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//i[@ng-if='!baggageChargeItem.editFee']")).click();
			FlightSearch.loadhandling(driver);

			driver.findElement(By.xpath("//input[@aria-label='baggage fee']")).clear();
			driver.findElement(By.xpath("//input[@aria-label='baggage fee']")).sendKeys("0");
			FlightSearch.loadhandling(driver);
			//clicking the first element

			//String Xpathwaiverdropdown ="//toggle-title/div[text()='"+sPassengerName+"']//ancestor::div[@ng-if='WaiverReasonInfo']//child::";
			driver.findElement(By.xpath("//md-select[@ng-model='WaiverReasonInfo.process']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[contains(@class,'md-active')]//md-option[@ng-value='ProcessList']/div[1]")).click();

			driver.findElement(By.xpath("//md-select[@ng-model='WaiverReasonInfo.Reason']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[contains(@class,'md-active')]//md-option[@ng-repeat='Reason in WaiverReasonInfo.process.Reason']/div[1]")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//button[contains(text(),'Next')]")).click();
			Thread.sleep(1000);
			//driver.findElement(By.xpath("//button[contains(text(),'Pay')]")).click();//UPDATED BY JENNY
			driver.findElement(By.xpath("//button[@aria-label='submit payment']")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath(DoneXpath)).click();
			FlightSearch.loadhandling(driver);
			//check EMD

			driver.findElement(By.xpath("//button[contains(text(),'Continue')]")).click();
			FlightSearch.loadhandling(driver);
		}catch(Exception e) {
			BFrameworkQueryObjects.logStatus(driver, Status.FAIL, "Exception while waive off", "waive off didnt work",e);

		}

	}
	public static void SeatChangePage(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException
	{   
		try {
			// driver.findElement(By.xpath(ChangeSeatCheckedinPax)).click();
			String SeatBeforeChange=driver.findElement(By.xpath(ChangeSeatCheckedinPax)).getAttribute("value");
			//driver.findElement(By.xpath(SaetChangedIcon)).click();
			driver.findElement(By.xpath("//div[md-checkbox[@aria-checked='true']]//parent::div//child::i[contains(@ng-click,'stateChange') and contains(@class,'icon-seatmap')]")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath(DropDownSelectSeat)).click();
			driver.findElement(By.xpath(WindowSeat)).click();
			driver.findElement(By.xpath(SubmitXpath)).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//button[contains(text(),'Continue')]")).click();
			FlightSearch.loadhandling(driver);
			List<WebElement> AlreadyAssigndSeat=driver.findElements(By.xpath(CheckedInPax));
			//Select the Already assign seat
			//AlreadyAssigndSeat.get(0).click();//Commenting as it is deselecting the seat - Jenny
			FlightSearch.loadhandling(driver);
			String SeatAfterChange=driver.findElement(By.xpath(ChangeSeatCheckedinPax)).getAttribute("value");
			if(!(SeatBeforeChange.equalsIgnoreCase(SeatAfterChange)))
			{
				queryObjects.logStatus(driver, Status.PASS, "Checking the seat after change", "Seat got changed",null); 	
			}
			else
			{
				queryObjects.logStatus(driver, Status.FAIL, "Checking the seat after change", "Seat has not changed",null); 
			}

		}
		catch(Exception e)
		{
			queryObjects.logStatus(driver, Status.PASS, "Searching a flight with more than two PNR", "Getting an exception while searching a flight"+e.getMessage(),e); 
		}

	}

	public void PassengerVerification_Res(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception {

		// TODO Auto-generated method stub
		try {
			driver.findElement(By.xpath(ReservationXpath)).click();  // clicking reservation menu bar
			Thread.sleep(2000);
			driver.findElement(By.xpath(ReservationMenu)).click();		 
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//div[contains(@ng-repeat,'pssguiCarousel.items')]")).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath(PassengerRes)).click();
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath(PersonalInfoRes)).click();
			FlightSearch.loadhandling(driver);
			if ((driver.findElement(By.xpath(SnameRes)).getAttribute("value")).equalsIgnoreCase(FlightSearch.getTrimTdata(queryObjects.getTestData("SurName")))) {
				queryObjects.logStatus(driver, Status.PASS, "Passenger LastName verification in Reservation after checkin", "LastName displayed is correct",null);
			} else {
				queryObjects.logStatus(driver, Status.FAIL, "Passenger LastName verification in Reservation after checkin", "LastName displayed is not correct", null);
			}
			if ((driver.findElement(By.xpath(FNameRes)).getAttribute("value").trim()).equalsIgnoreCase(FlightSearch.getTrimTdata(queryObjects.getTestData("FirstName")))) {
				queryObjects.logStatus(driver, Status.PASS, "Passenger FirstName verification in Reservation after checkin", "FirstName displayed is correct",null);
			} else {
				queryObjects.logStatus(driver, Status.FAIL, "Passenger FirstName verification in Reservation after checkin", "FirstName displayed is not correct", null);
			}
			if ((driver.findElement(By.xpath(EmerNameRes)).getAttribute("value").trim()).equalsIgnoreCase("emergencydefault")) {
				queryObjects.logStatus(driver, Status.PASS, "Passenger EmergencyContact verification in Reservation after checkin", "EmergencyContact displayed is correct",null);
			} else {
				queryObjects.logStatus(driver, Status.FAIL, "Passenger EmergencyContact verification in Reservation after checkin", "EmergencyContact displayed is not correct", null);
			}
			if ((driver.findElement(By.xpath(EmerPhRes)).getAttribute("value").trim()).equalsIgnoreCase("12345634")) {
				queryObjects.logStatus(driver, Status.PASS, "Passenger EmergencyPhone verification in Reservation after checkin", "EmergencyPhone displayed is correct",null);
			} else {
				queryObjects.logStatus(driver, Status.FAIL, "Passenger EmergencyPhone verification in Reservation after checkin", "EmergencyPhone displayed is not correct", null);
			}
			driver.findElement(By.xpath(TravelDocRes)).click();
			Thread.sleep(1000);
			if ((driver.findElement(By.xpath(KTNumRes)).getAttribute("value").trim()).equalsIgnoreCase(FlightSearch.getTrimTdata(queryObjects.getTestData("KTNum")))) {
				queryObjects.logStatus(driver, Status.PASS, "Passenger KnownTravelerNumber verification in Reservation after checkin", "KnownTravelerNumber displayed is correct",null);
			} else {
				queryObjects.logStatus(driver, Status.FAIL, "Passenger KnownTravelerNumber verification in Reservation after checkin", "KnownTravelerNumber displayed is not correct", null);
			}
			if ((driver.findElement(By.xpath(RedressRes)).getAttribute("value").trim()).equalsIgnoreCase(FlightSearch.getTrimTdata(queryObjects.getTestData("Redress")))) {

				queryObjects.logStatus(driver, Status.PASS, "Passenger RedressNumber verification in Reservation after checkin", "RedressNumber displayed is correct",null);
			} else {
				queryObjects.logStatus(driver, Status.FAIL, "Passenger RedressNumber verification in Reservation after checkin", "RedressNumber displayed is not correct", null);
			}

		} catch (InterruptedException e) {
			queryObjects.logStatus(driver, Status.FAIL, "Passenger data verification in Reservation after checkin", "PAX data verification failed", null);

			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}




	public static  void SetPassengerListOption(WebDriver driver, BFrameworkQueryObjects queryObjects, String Filter_ListVal) throws IOException, Exception {
		// TODO Auto-generated method stub
		
		driver.findElement(By.xpath("//input[@name='smartsearch']")).sendKeys(Filter_ListVal);
		driver.findElement(By.xpath("//input[@name='smartsearch']")).clear();
		driver.findElement(By.xpath("//input[@name='smartsearch']")).sendKeys(Filter_ListVal);
		Thread.sleep(3000);
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				 return driver.findElement(By.xpath("//input[@name='smartsearch']")).getAttribute("value").length() == Filter_ListVal.length();
			}
		});
		driver.findElement(By.xpath("//input[@name='smartsearch']")).sendKeys(Keys.TAB);
		FlightSearch.loadhandling(driver);
		try {
				if (driver.findElement(By.xpath("//div[@translate='pssgui.no.passengers.found']")).isDisplayed()) {
					queryObjects.logStatus(driver, Status.FAIL, "Data not available", "No Passenger found message is displayed ", null);
				}					
			} catch (Exception e) {
				// TODO: handle exception
				try {
					if (driver.findElement(By.xpath("//span[@translate='pssgui.passenger.list.options']/following-sibling::span")).getText().contains(Filter_ListVal)) {
						queryObjects.logStatus(driver, Status.PASS, "Filter Passenger List", Filter_ListVal+" list is displayed ", null);
					}
				} catch (Exception e2) {}				
			}

	}
	public void SelectDocCheckFromList(WebDriver driver, BFrameworkQueryObjects queryObjects, String ValueCheck){
		List<WebElement> Checkbox_Cnt = driver.findElements(By.xpath(PaxDataChkBox));
		int PAXCnt = Checkbox_Cnt.size();
		for (int ItmSel = 1; ItmSel <= PAXCnt; ItmSel++) {
			if (((driver.findElement(By.xpath("(//div[@class='passenger-list layout-row'])["+ItmSel+"]")).getText().trim())).contains(ValueCheck)) {
				driver.findElement(By.xpath("(//span[contains(text(),'Doc Check')])["+ItmSel+"]")).click();
				FlightSearch.loadhandling(driver);
				break;
			}
		}
	}



	public static void ReservationPage(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException
	{ 
		try
	{
		///for sync ticket need to implement it is hold
		driver.findElement(By.xpath("//md-checkbox[contains(@class,'passenger-checkbox')]")).click();

		driver.findElement(By.xpath("//md-select-value/span[@class='md-select-icon']")).click();
		driver.findElement(By.xpath("//div[contains(text(),'Sync Ticket')]")).click();
		FlightSearch.loadhandling(driver);

		
		driver.findElement(By.xpath("//md-radio-group/md-radio-button[@value='false']/div[1]")).click();
		driver.findElement(By.xpath("//md-radio-group/md-radio-button[@value='CLASS']/div[1]")).click();
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath("//button[contains(text(),'Sync')]")).click();
		FlightSearch.loadhandling(driver);
		Boolean b=false;
		String  Errormsg="";
		int size=0;
		try
		{
		 Errormsg=driver.findElement(By.xpath("//div[div[contains(text(),'Sync Tickets')]]//following-sibling::pssgui-error-panel//span[contains(text(),'Unable to sync the ticket in its current state')]")).getText();
		}
		catch(Exception e){}
		if(Errormsg.equalsIgnoreCase("Unable to sync the ticket in its current state"))
		{
			driver.findElement(By.xpath("//md-radio-group/md-radio-button[@value='ITINERARY']/div[1]")).click();
			driver.findElement(By.xpath("//button[contains(text(),'Sync')]")).click();
			b=true;
			FlightSearch.loadhandling(driver);
			size=driver.findElements(By.xpath("//div[div[contains(text(),'Sync Tickets')]]//following-sibling::pssgui-error-panel//span[contains(text(),'Unable to sync the ticket in its current state')]")).size();
			try
			{
			 Errormsg=driver.findElement(By.xpath("//div[div[contains(text(),'Sync Tickets')]]//following-sibling::pssgui-error-panel//span[contains(text(),'Unable to sync the ticket in its current state')]")).getText();
			}
			catch(Exception e){}
		}
	    if(Errormsg.equalsIgnoreCase("Unable to sync the ticket in its current state") && b && size>0)
		{
			driver.findElement(By.xpath("//md-radio-group/md-radio-button[@value='PASSENGER']/div[1]")).click();
			driver.findElement(By.xpath("//button[contains(text(),'Sync')]")).click();
		}
		
		List<WebElement> reissueticketnumbers=driver.findElements(By.xpath("//span[contains(@class,'primary-ticket-number')]"));
		
		for (WebElement ele : reissueticketnumbers) {
			String ticketnumber=ele.getText().trim();
			List<WebElement> currentticketstate=driver.findElements(By.xpath("//span[text()='"+ticketnumber+"']/parent::div/parent::div//td[contains(@ng-if,'ticketStatusUpdate')]"));
			boolean bticketstae=true;
			String stickstate=null;
			
				for (WebElement cureele : currentticketstate) {
					stickstate="ADJUSTED";
					if (!cureele.getText().trim().equalsIgnoreCase(stickstate))
						bticketstae=false;
				
			}
			if (bticketstae) 
				queryObjects.logStatus(driver, Status.PASS, "After SYNC particular ticket status checking ticket number is:"+ticketnumber, "After sync this ticket status is showing correctly ticket statu is "+stickstate, null);
			else
				queryObjects.logStatus(driver, Status.FAIL, "After Sync particular ticket status checking ticket number is:"+ticketnumber, "After sync this ticket status should  show correctly ticket statu is "+stickstate, null);

		}			
		
	}
	catch(Exception e)
	{
		queryObjects.logStatus(driver, Status.FAIL, "Checking exception on ", "PNR not getting sync",null); 
	}

	}
	
	public static void Payment(WebDriver driver, BFrameworkQueryObjects queryObjects) throws InterruptedException, IOException
	{
		driver.findElement(By.xpath("//md-select[contains(@aria-label,'Payment Type')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//md-option[div[contains(text(),'Cash')]]")).click();
		Thread.sleep(2000);
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath("//button[text()='Pay']")).click();
		FlightSearch.loadhandling(driver);
		//driver.findElement(By.xpath("//button[contains(text(),'Issue Emd')]")).click();
		FlightSearch.enterFoiddetails(driver,queryObjects);

		//Handling Email recipients popup
		driver.findElement(By.xpath("//button[contains(text(),'Done')]")).click();
		FlightSearch.loadhandling(driver);
		try{
			tktemd1=driver.findElement(By.xpath("//span[contains(text(),'EMD')]//following-sibling::span")).getText();
		}
		catch(Exception e){
			queryObjects.logStatus(driver, Status.INFO, "while pay the amt", e.getLocalizedMessage(),e); 
		}

		FlightSearch.emailhandling(driver,queryObjects);
		
	}	

	public static String GetOutofSync(WebDriver driver, BFrameworkQueryObjects queryObjects)
	{   

		String Flightno="";
		try
		{

			List<WebElement> NoofFlight=driver.findElements(By.xpath("//md-content/table[contains(@ng-table,'flightDepartArrival')]/tbody/tr/child::td[5][contains(text(),'OPEN')]//preceding-sibling::td[4]"));
			List<String> Flight=new ArrayList<>();
			NoofFlight.forEach(a ->Flight.add(a.getText().trim()));

			for(int i=0;i<NoofFlight.size();i++)
			{
				NoofFlight=driver.findElements(By.xpath("//md-content/table[contains(@ng-table,'flightDepartArrival')]/tbody/tr/child::td[5][contains(text(),'OPEN')]//preceding-sibling::td[4]"));
				// String FlightLink=NoofFlight.get(i).toString();
				NoofFlight.get(i).click();
				// driver.findElement(By.xpath(FlightLink)).click();

				FlightSearch.loadhandling(driver);
				if(Integer.parseInt(driver.findElement(By.xpath("//div[text()='eTKT Not Sync']//parent::div/div[2]/div")).getText())>0)
				{    
					Flightno=Flight.get(i);
					queryObjects.logStatus(driver, Status.PASS, "Searching a flight with more than two PNR", "Fligth have more then two PNR",null);
					driver.findElement(By.xpath("//div[contains(text(),'Back')]")).click();
					FlightSearch.loadhandling(driver);
					break;
				}
				else
				{
					driver.findElement(By.xpath("//div[contains(text(),'Back')]")).click();
					FlightSearch.loadhandling(driver);
					String sFlight= Login.FLIGHTNUM;
				}

			}


		}
		catch(Exception e)
		{
			//queryObjects.logStatus(driver, Status.PASS, "Searching a flight with more than two PNR", "Getting an exception while searching a flight"+e.getMessage(),e); 
		}
		return Flightno;
	}

	public static void CompleteBaggage(WebDriver driver, BFrameworkQueryObjects queryObjects, String AddBagkg, String AllowBg, String MulBagWgt, String MulBagType, String BagValue, String Catalog) throws Exception
	{
			String MultipleFOP = FlightSearch.getTrimTdata(queryObjects.getTestData("Bag_MultipleFOP"));
			
			Bag_Tag_Number = new ArrayList<>();
			String BagType="";

			FlightSearch.loadhandling(driver);
			Thread.sleep(1000);
			boolean Bagtag=false;
			boolean AddBag=false;

			if (!MulBagWgt.isEmpty()) {
				String SplitMulBag[] = MulBagWgt.split(";");
				for (int p = 0; p < SplitMulBag.length; p++) {
					try {
						Bagtag = driver.findElement(By.xpath(BagtypeXpath)).isDisplayed();

					}catch (Exception e) {}
					try {
						AddBag = driver.findElement(By.xpath(AddAnotherBag)).isDisplayed();					
					}catch (Exception e) {}				

					if (!MulBagType.isEmpty()) {
						String SplitBagType[] = MulBagType.split(";");
						BagType = SplitBagType[p];							 
					}


					if (Bagtag && (!(driver.findElement(By.xpath("//button[@translate='pssgui.submit']")).isEnabled()))) {
						SetBaggage(driver,queryObjects,p+1,SplitMulBag[p],BagType);				  // select bag type (stand/catalog)	
					}else if(AddBag) {   // need to check

						driver.findElement(By.xpath(AddAnotherBag)).click();
						boolean errdispl= false;
						try {
							errdispl=  driver.findElement(By.xpath("//span[text()='Maximum Checked In baggage reached.']")).isDisplayed();
						}catch(Exception e) {}

						String Negative_Case=FlightSearch.getTrimTdata(queryObjects.getTestData("Negative_Case"));

						if(errdispl && Negative_Case.equalsIgnoreCase("Y")) {
							queryObjects.logStatus(driver, Status.PASS, "Verify Maximum  Checked In baggage reached ", "Message was displayed", null);
							driver.findElement(By.xpath(AdsResponsePopup)).click();
							return;
						}else if(errdispl) {
							queryObjects.logStatus(driver, Status.FAIL, "Maximum  Checked In baggage reached ", "unexpected Message was displayed", null);	 
						}else {
							SetBaggage(driver,queryObjects,p+1,SplitMulBag[p],BagType);   // select bag type (stand/catalog)	
		   
						}
					}

				}  //for loop

			} else {

				try {
					Bagtag = driver.findElement(By.xpath(BagtypeXpath)).isDisplayed();					


				}catch (Exception e) {}
				try {
					AddBag = driver.findElement(By.xpath(AddAnotherBag)).isDisplayed();					
				}catch (Exception e) {}				


				if (Bagtag) {
					Baggagedetails(driver,queryObjects, AddBagkg, Catalog);					
				}else if(AddBag) {					 

					driver.findElement(By.xpath(AddAnotherBag)).click();

					Baggagedetails(driver,queryObjects, AddBagkg, Catalog);
				}				 				 
			}	

			boolean isbutton=false;
			try {
				isbutton = driver.findElement(By.xpath(SubmitXpath)).isEnabled();
			}catch (Exception e) {}
			String balamt = "";
			if (isbutton) {
				driver.findElement(By.xpath(SubmitXpath)).click();
				FlightSearch.loadhandling(driver);
				
				//Check the Bag Status
				String BagStatus = queryObjects.getTestData("BagStatus");
				if (!BagStatus.isEmpty()) {
					String SptStatus[] = BagStatus.split(";");
					for (int sb = 0; sb < SptStatus.length; sb++) {
						if (SptStatus[sb].toUpperCase().equals("AMT")) {
							if (driver.findElement(By.xpath("(//div[contains(@ng-if,'bagChargeTotal.priceType')])["+(sb+1)+"]")).getText().trim().contains("-")) {
								if (!BagValue.isEmpty()) {
									if (driver.findElement(By.xpath("(//div[contains(@ng-if,'bagChargeTotal.priceType')])["+(sb+1)+"]/../div[contains(@ng-if,'Total.Amount')]/div")).getText().trim().contains(BagValue)) {
										queryObjects.logStatus(driver, Status.PASS,"Check the Bag Amount status for the corresponding bag","Bag Amount is "+BagValue,null);
									} else {
										queryObjects.logStatus(driver, Status.FAIL,"Check the Bag Amount for the corresponding bag","Bag Amount is not according to the expected '"+SptStatus[sb].toUpperCase()+"' but the Actual status is "+driver.findElement(By.xpath("(//div[contains(@ng-if,'bagChargeTotal')])["+(sb+1)+"]")).getText(),null);
									}
								} else {
									if (Double.parseDouble(driver.findElement(By.xpath("(//div[contains(@ng-if,'bagChargeTotal.priceType')])["+(sb+1)+"]/../div[contains(@ng-if,'Total.Amount')]/div")).getText().trim())>0) {
										queryObjects.logStatus(driver, Status.PASS,"Check the Bag Amount status for the corresponding bag","Bag Amount is "+BagValue,null);
									} else {
										queryObjects.logStatus(driver, Status.FAIL,"Check the Bag Amount for the corresponding bag","Bag Amount is not according to the expected '"+SptStatus[sb].toUpperCase()+"' but the Actual status is "+driver.findElement(By.xpath("(//div[contains(@ng-if,'bagChargeTotal')])["+(sb+1)+"]")).getText(),null);
									}
								}
							} else {
								queryObjects.logStatus(driver, Status.FAIL,"Check the Bag Amount status for the corresponding bag","Bag Amount status is not according to the expected '"+SptStatus[sb].toUpperCase()+"' but the Actual status is "+driver.findElement(By.xpath("(//div[contains(@ng-if,'bagChargeTotal')])["+(sb+1)+"]")).getText(),null);
							}
						} else {
							if (driver.findElement(By.xpath("(//div[contains(@ng-if,'bagChargeTotal.priceType')])["+(sb+1)+"]")).getText().trim().contains(SptStatus[sb].toUpperCase())) {
								queryObjects.logStatus(driver, Status.PASS,"Check the Bag Amount status for the corresponding bag","Bag Amount status is "+SptStatus[sb].toUpperCase(),null);
							} else if (driver.findElements(By.xpath("(//div[contains(@class,'baggage-product')])["+(sb+1)+"]//span[contains(text(),'Checked In')]")).size()>0) {
								queryObjects.logStatus(driver, Status.PASS,"Check the Bag Amount status for the corresponding bag","Bag is already checked in hence the baggage status is blank",null);
							} else {
								queryObjects.logStatus(driver, Status.FAIL,"Check the Bag Amount status for the corresponding bag","Bag Amount status is not according to the expected '"+SptStatus[sb].toUpperCase()+"' but the Actual status is "+driver.findElement(By.xpath("(//div[contains(@ng-if,'bagChargeTotal')])["+(sb+1)+"]")).getText(),null);
							}
						}
					}
				}				
				balamt=driver.findElement(By.xpath("//div[@title='pssgui.balance.due']/div[2]")).getText().trim();
				balamt=balamt.split("\\s+")[0];				
				// Baggage calculator  add method
				String Category=queryObjects.getTestData("Bag_Category"); 
				if(!Category.equals("")){
					double Bag_BalanceAMT=Double.parseDouble(balamt);
					double Bagcalucate_SUM=Baggage_calculator(driver,queryObjects);
					double diffAmt=Bag_BalanceAMT-Bagcalucate_SUM;
					
					if(Bagcalucate_SUM==Double.parseDouble(balamt)){
						queryObjects.logStatus(driver, Status.PASS,"Baggage Cost checking for '"+Category +"'"," Baggage calculate working fine",null);
					}
					else if (diffAmt<100 && diffAmt>0) {
						String diff=FlightSearch.roundDouble(String.valueOf(diffAmt));
						queryObjects.logStatus(driver, Status.INFO,"Baggage Cost checking for '"+Category +"'"," Baggage calculate working fine but TAX added Different AMT :"+diff,null);
					}
					else {
						queryObjects.logStatus(driver, Status.FAIL,"Baggage Cost checking for '"+Category +"'"," Baggage calculate not working Actual amt is '"+Bag_BalanceAMT +"' Expected AMT is :: " +Bagcalucate_SUM,null);
					}
				}			
				//Baggage calculator
				if (!BagValue.isEmpty()) {
					if (Double.valueOf(balamt).intValue()==Double.valueOf(BagValue).intValue()) {
						queryObjects.logStatus(driver, Status.PASS, "Verify Baggage cost as "+BagValue, "Cost was as expected", null);
					}else {
						queryObjects.logStatus(driver, Status.FAIL, "Verify Baggage cost as "+BagValue, "Cost was as unexpected"+balamt, null);	 
					}

				}
				if(Double.valueOf(balamt).intValue()==0){
					try {
						driver.findElement(By.xpath(ContinueXpath)).click();
						FlightSearch.loadhandling(driver);
					}catch (Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Adding Baggage case ", "After click sumbit Continu button not exist", null); 
		  
					}
				}
			}

			if  (AllowBg.equalsIgnoreCase("Y") ) {
				String TaxAmt = "";
				String SingleBagAmt = "";
				int BagCnt = 0;
				double Totaltax = 0;
				//Check the Tax 
				boolean iProceed=false;
				iProceed =  driver.findElements(By.xpath(Proceedpath)).size()>0;
				if (iProceed) {
					queryObjects.logStatus(driver, Status.PASS, "Proceed to Pay ", "Proceed to Pay ", null);
					driver.findElement(By.xpath(Proceedpath)).click();		
					if (!FlightSearch.getTrimTdata(queryObjects.getTestData("Waive")).equalsIgnoreCase("y")) {
						FlightSearch.loadhandling(driver);
						//div[@ng-repeat='baggageChargeItem in baggageInfo.bagCharges']//div[@ng-if='!baggageChargeItem.editFee']
						
						//Atul 10Mar - Multiple FOP for Adding Baggage						
						if(MultipleFOP.equalsIgnoreCase("yes")) {
							FlightSearch flop=new FlightSearch();
							double totalPayment = Double.parseDouble(balamt);
							String MulFOPType = FlightSearch.getTrimTdata(queryObjects.getTestData("Bag_FOPType"));
							String MulFOPsubType = FlightSearch.getTrimTdata(queryObjects.getTestData("Bag_FOPSubType"));
							String FOPCardNums = FlightSearch.getTrimTdata(queryObjects.getTestData("Bag_FOPCardNums"));
							flop.MulFOP(driver,queryObjects,totalPayment,MulFOPType,MulFOPsubType,FOPCardNums,"","","BaggageCase");
						} else {
							try {
								driver.findElement(By.xpath("//md-select[contains(@aria-label,'Payment Type')]")).click();
								Thread.sleep(1000);
								driver.findElement(By.xpath("//md-option[div[contains(text(),'Cash')]]")).click();
								Thread.sleep(1000);	
							} catch(Exception e) {}						
						}						
						driver.findElement(By.xpath("//button[text()='Pay']")).click();
						FlightSearch.loadhandling(driver);
						FlightSearch.enterFoiddetails(driver, queryObjects);
						FlightSearch.loadhandling(driver);
						String BagAmt = FlightSearch.getTrimTdata(queryObjects.getTestData("BagAmt"));
						if (!(BagAmt.isEmpty())) {
							if (driver.findElement(By.xpath("//div/span[@translate='pssgui.emd']/following-sibling::span")).getText().trim()!="") {
								queryObjects.logStatus(driver, Status.PASS, "Check EMD issued ", "Payment is successful and EMD is issued.", null);
								BagCnt = driver.findElements(By.xpath("//div[@ng-repeat='baggageChargeItem in baggageInfo.bagCharges']//div[@ng-if='!baggageChargeItem.editFee']")).size();
								for (int bg = 1; bg <= BagCnt; bg++) {
									SingleBagAmt = driver.findElement(By.xpath("//div[@ng-repeat='baggageChargeItem in baggageInfo.bagCharges']//div[@ng-if='!baggageChargeItem.editFee']")).getText().trim();
									SingleBagAmt = SingleBagAmt.substring(0, SingleBagAmt.indexOf(" "));
									if(driver.findElements(By.xpath("//div[@ng-repeat='baggageChargeItem in baggageInfo.bagCharges']//div[@ng-if='!TaxInfo.IsTaxEdit']//div[contains(@ng-class,'amountCtrl')]")).size()>0)
									{
										TaxAmt = driver.findElement(By.xpath("//div[@ng-repeat='baggageChargeItem in baggageInfo.bagCharges']//div[@ng-if='!TaxInfo.IsTaxEdit']//div[contains(@ng-class,'amountCtrl')]")).getText().trim();
										TaxAmt = TaxAmt.substring(0, TaxAmt.indexOf(" "));
										Totaltax = Totaltax + Double.valueOf(TaxAmt);
									} else {
										Totaltax=0;
									}
								}
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Check EMD issued ", "Payment is not successful and EMD is not issued.", null);
							}
						}
						driver.findElement(By.xpath("//button[contains(text(),'Done')]")).click();
						FlightSearch.loadhandling(driver);
						//Added by Jenny
						if (!(BagAmt.isEmpty())) {
							String PaidAmt = driver.findElement(By.xpath("//div[@title='pssgui.paid']/div[2]")).getText().trim();
							PaidAmt = PaidAmt.substring(0, PaidAmt.indexOf(" "));
							if (Double.valueOf(PaidAmt).equals((Integer.parseInt(SingleBagAmt)*BagCnt) + Totaltax)) {
								queryObjects.logStatus(driver, Status.PASS, "Check the baggage amount ", "Payment is successful and Amount displayed is correct.", null);
							} else {
								queryObjects.logStatus(driver, Status.WARNING, "Check the baggage amount ", "Payment is successful and Amount displayed is not correct.", null);
							}
						}
						try {
							driver.findElement(By.xpath(ContinueXpath)).click();
							FlightSearch.loadhandling(driver);
						}catch (Exception e) {}
					}
					else if (FlightSearch.getTrimTdata(queryObjects.getTestData("Waive")).equalsIgnoreCase("y")) {
						EditBagFee(driver, PaxName);	  //edit Bag fee Waive
					} 
						
					
				}

			}

			else if ( AllowBg.equalsIgnoreCase("N")) {
				queryObjects.logStatus(driver, Status.PASS, "Add baggage was not allowed ", "Baggage ", null);

			}
			else {
				if(Double.valueOf(balamt).intValue()==0){
					queryObjects.logStatus(driver, Status.PASS, "Proceed to Pay checking", "BAL amount zero so Proceed to Pay button disabled ", null);

				}
				else
					queryObjects.logStatus(driver, Status.FAIL, "Proceed to Pay checking", "BAL amount Not zero but Proceed to Pay button was disabled ", null);
			}
		

	}
public static void CompleteBaggagesp(WebDriver driver, BFrameworkQueryObjects queryObjects, String AddBagkg, String AllowBg, String MulBagWgt, String MulBagType, String BagValue, String Catalog) throws Exception
	{

		String MultipleFOP = FlightSearch.getTrimTdata(queryObjects.getTestData("Bag_MultipleFOP"));
		
		Bag_Tag_Number = new ArrayList<>();
		String BagType="";

		FlightSearch.loadhandling(driver);
		Thread.sleep(1000);
		boolean Bagtag=false;
		boolean AddBag=false;

		if (!MulBagWgt.isEmpty()) {
			//String SplitMulBag[] = MulBagWgt.split(";");
		//	for (int p = 0; p < SplitMulBag.length; p++) {
				try {
					Bagtag = driver.findElement(By.xpath(BagtypeXpath)).isDisplayed();

				}catch (Exception e) {}
				try {
					AddBag = driver.findElement(By.xpath(AddAnotherBag)).isDisplayed();					
				}catch (Exception e) {}				

				
				if (Bagtag && (!(driver.findElement(By.xpath("//button[@translate='pssgui.submit']")).isEnabled()))) {
					SetBaggage(driver,queryObjects,1,MulBagWgt,MulBagType);				  // select bag type (stand/catalog)	
				}

			 		} else {

			try {
				Bagtag = driver.findElement(By.xpath(BagtypeXpath)).isDisplayed();					


			}catch (Exception e) {}
			try {
				AddBag = driver.findElement(By.xpath(AddAnotherBag)).isDisplayed();					
			}catch (Exception e) {}				


			if (Bagtag) {
				Baggagedetails(driver,queryObjects, AddBagkg, Catalog);					
			}else if(AddBag) {					 

				driver.findElement(By.xpath(AddAnotherBag)).click();

				Baggagedetails(driver,queryObjects, AddBagkg, Catalog);
			}				 				 
		}	

		boolean isbutton=false;
		try {
			isbutton = driver.findElement(By.xpath(SubmitXpath)).isEnabled();
		}catch (Exception e) {}
		String balamt = "";
		if (isbutton) {
			driver.findElement(By.xpath(SubmitXpath)).click();
			FlightSearch.loadhandling(driver);
			
			//Check the Bag Status
			String BagStatus = queryObjects.getTestData("BagStatus");
			if (!BagStatus.isEmpty()) {
				String SptStatus[] = BagStatus.split(";");
				for (int sb = 0; sb < SptStatus.length; sb++) {
					if (SptStatus[sb].toUpperCase().equals("AMT")) {
						if (driver.findElement(By.xpath("(//div[contains(@ng-if,'bagChargeTotal.priceType')])["+(sb+1)+"]")).getText().trim().contains("-")) {
							if (!BagValue.isEmpty()) {
								if (driver.findElement(By.xpath("(//div[contains(@ng-if,'bagChargeTotal.priceType')])["+(sb+1)+"]/../div[contains(@ng-if,'Total.Amount')]/div")).getText().trim().contains(BagValue)) {
									queryObjects.logStatus(driver, Status.PASS,"Check the Bag Amount status for the corresponding bag","Bag Amount is "+BagValue,null);
								} else {
									queryObjects.logStatus(driver, Status.FAIL,"Check the Bag Amount for the corresponding bag","Bag Amount is not according to the expected '"+SptStatus[sb].toUpperCase()+"' but the Actual status is "+driver.findElement(By.xpath("(//div[contains(@ng-if,'bagChargeTotal')])["+(sb+1)+"]")).getText(),null);
								}
							} else {
								if (Double.parseDouble(driver.findElement(By.xpath("(//div[contains(@ng-if,'bagChargeTotal.priceType')])["+(sb+1)+"]/../div[contains(@ng-if,'Total.Amount')]/div")).getText().trim())>0) {
									queryObjects.logStatus(driver, Status.PASS,"Check the Bag Amount status for the corresponding bag","Bag Amount is "+BagValue,null);
								} else {
									queryObjects.logStatus(driver, Status.FAIL,"Check the Bag Amount for the corresponding bag","Bag Amount is not according to the expected '"+SptStatus[sb].toUpperCase()+"' but the Actual status is "+driver.findElement(By.xpath("(//div[contains(@ng-if,'bagChargeTotal')])["+(sb+1)+"]")).getText(),null);
								}
							}
						} else {
							queryObjects.logStatus(driver, Status.FAIL,"Check the Bag Amount status for the corresponding bag","Bag Amount status is not according to the expected '"+SptStatus[sb].toUpperCase()+"' but the Actual status is "+driver.findElement(By.xpath("(//div[contains(@ng-if,'bagChargeTotal')])["+(sb+1)+"]")).getText(),null);
						}
					} else {
						if (driver.findElement(By.xpath("(//div[contains(@ng-if,'bagChargeTotal.priceType')])["+(sb+1)+"]")).getText().trim().contains(SptStatus[sb].toUpperCase())) {
							queryObjects.logStatus(driver, Status.PASS,"Check the Bag Amount status for the corresponding bag","Bag Amount status is "+SptStatus[sb].toUpperCase(),null);
						} else if (driver.findElements(By.xpath("(//div[contains(@class,'baggage-product')])["+(sb+1)+"]//span[contains(text(),'Checked In')]")).size()>0) {
							queryObjects.logStatus(driver, Status.PASS,"Check the Bag Amount status for the corresponding bag","Bag is already checked in hence the baggage status is blank",null);
						} else {
							queryObjects.logStatus(driver, Status.FAIL,"Check the Bag Amount status for the corresponding bag","Bag Amount status is not according to the expected '"+SptStatus[sb].toUpperCase()+"' but the Actual status is "+driver.findElement(By.xpath("(//div[contains(@ng-if,'bagChargeTotal')])["+(sb+1)+"]")).getText(),null);
						}
					}
				}
			}				
			balamt=driver.findElement(By.xpath("//div[@title='pssgui.balance.due']/div[2]")).getText().trim();
			balamt=balamt.split("\\s+")[0];				
			// Baggage calculator  add method
			String Category=queryObjects.getTestData("Bag_Category"); 
			if(!Category.equals("")){
				double Bag_BalanceAMT=Double.parseDouble(balamt);
				double Bagcalucate_SUM=Baggage_calculator(driver,queryObjects);
				double diffAmt=Bag_BalanceAMT-Bagcalucate_SUM;
				
				if(Bagcalucate_SUM==Double.parseDouble(balamt)){
					queryObjects.logStatus(driver, Status.PASS,"Baggage Cost checking for '"+Category +"'"," Baggage calculate working fine",null);
				}
				else if (diffAmt<100 && diffAmt>0) {
					String diff=FlightSearch.roundDouble(String.valueOf(diffAmt));
					queryObjects.logStatus(driver, Status.INFO,"Baggage Cost checking for '"+Category +"'"," Baggage calculate working fine but TAX added Different AMT :"+diff,null);
				}
				else {
					queryObjects.logStatus(driver, Status.FAIL,"Baggage Cost checking for '"+Category +"'"," Baggage calculate not working Actual amt is '"+Bag_BalanceAMT +"' Expected AMT is :: " +Bagcalucate_SUM,null);
				}
			}			
			//Baggage calculator
			if (!BagValue.isEmpty()) {
				if (Double.valueOf(balamt).intValue()==Double.valueOf(BagValue).intValue()) {
					queryObjects.logStatus(driver, Status.PASS, "Verify Baggage cost as "+BagValue, "Cost was as expected", null);
				}else {
					queryObjects.logStatus(driver, Status.FAIL, "Verify Baggage cost as "+BagValue, "Cost was as unexpected"+balamt, null);	 
				}

			}
			if(Double.valueOf(balamt).intValue()==0){
				try {
					driver.findElement(By.xpath(ContinueXpath)).click();
					FlightSearch.loadhandling(driver);
				}catch (Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Adding Baggage case ", "After click sumbit Continu button not exist", null); 
	  
				}
			}
		}

		if  (AllowBg.equalsIgnoreCase("Y") ) {
			String TaxAmt = "";
			String SingleBagAmt = "";
			int BagCnt = 0;
			double Totaltax = 0;
			//Check the Tax 
			boolean iProceed=false;
			iProceed =  driver.findElements(By.xpath(Proceedpath)).size()>0;
			if (iProceed) {
				queryObjects.logStatus(driver, Status.PASS, "Proceed to Pay ", "Proceed to Pay ", null);
				driver.findElement(By.xpath(Proceedpath)).click();		
				if (!FlightSearch.getTrimTdata(queryObjects.getTestData("Waive")).equalsIgnoreCase("y")) {
					FlightSearch.loadhandling(driver);
					//div[@ng-repeat='baggageChargeItem in baggageInfo.bagCharges']//div[@ng-if='!baggageChargeItem.editFee']
					
					//Atul 10Mar - Multiple FOP for Adding Baggage						
					if(MultipleFOP.equalsIgnoreCase("yes")) {
						FlightSearch flop=new FlightSearch();
						double totalPayment = Double.parseDouble(balamt);
						String MulFOPType = FlightSearch.getTrimTdata(queryObjects.getTestData("Bag_FOPType"));
						String MulFOPsubType = FlightSearch.getTrimTdata(queryObjects.getTestData("Bag_FOPSubType"));
						String FOPCardNums = FlightSearch.getTrimTdata(queryObjects.getTestData("Bag_FOPCardNums"));
						flop.MulFOP(driver,queryObjects,totalPayment,MulFOPType,MulFOPsubType,FOPCardNums,"","","BaggageCase");
					} else {
						try {
							driver.findElement(By.xpath("//md-select[contains(@aria-label,'Payment Type')]")).click();
							Thread.sleep(1000);
							driver.findElement(By.xpath("//md-option[div[contains(text(),'Cash')]]")).click();
							Thread.sleep(1000);	
						} catch(Exception e) {}						
					}						
					driver.findElement(By.xpath("//button[text()='Pay']")).click();
					FlightSearch.loadhandling(driver);
					FlightSearch.enterFoiddetails(driver, queryObjects);
					FlightSearch.loadhandling(driver);
					String BagAmt = FlightSearch.getTrimTdata(queryObjects.getTestData("BagAmt"));
					if (!(BagAmt.isEmpty())) {
						if (driver.findElement(By.xpath("//div/span[@translate='pssgui.emd']/following-sibling::span")).getText().trim()!="") {
							queryObjects.logStatus(driver, Status.PASS, "Check EMD issued ", "Payment is successful and EMD is issued.", null);
							BagCnt = driver.findElements(By.xpath("//div[@ng-repeat='baggageChargeItem in baggageInfo.bagCharges']//div[@ng-if='!baggageChargeItem.editFee']")).size();
							for (int bg = 1; bg <= BagCnt; bg++) {
								SingleBagAmt = driver.findElement(By.xpath("//div[@ng-repeat='baggageChargeItem in baggageInfo.bagCharges']//div[@ng-if='!baggageChargeItem.editFee']")).getText().trim();
								SingleBagAmt = SingleBagAmt.substring(0, SingleBagAmt.indexOf(" "));
								if(driver.findElements(By.xpath("//div[@ng-repeat='baggageChargeItem in baggageInfo.bagCharges']//div[@ng-if='!TaxInfo.IsTaxEdit']//div[contains(@ng-class,'amountCtrl')]")).size()>0)
								{
									TaxAmt = driver.findElement(By.xpath("//div[@ng-repeat='baggageChargeItem in baggageInfo.bagCharges']//div[@ng-if='!TaxInfo.IsTaxEdit']//div[contains(@ng-class,'amountCtrl')]")).getText().trim();
									TaxAmt = TaxAmt.substring(0, TaxAmt.indexOf(" "));
									Totaltax = Totaltax + Double.valueOf(TaxAmt);
								} else {
									Totaltax=0;
								}
							}
						} else {
							queryObjects.logStatus(driver, Status.FAIL, "Check EMD issued ", "Payment is not successful and EMD is not issued.", null);
						}
					}
					driver.findElement(By.xpath("//button[contains(text(),'Done')]")).click();
					FlightSearch.loadhandling(driver);
					//Added by Jenny
					if (!(BagAmt.isEmpty())) {
						String PaidAmt = driver.findElement(By.xpath("//div[@title='pssgui.paid']/div[2]")).getText().trim();
						PaidAmt = PaidAmt.substring(0, PaidAmt.indexOf(" "));
						if (Double.valueOf(PaidAmt).equals((Integer.parseInt(SingleBagAmt)*BagCnt) + Totaltax)) {
							queryObjects.logStatus(driver, Status.PASS, "Check the baggage amount ", "Payment is successful and Amount displayed is correct.", null);
						} else {
							queryObjects.logStatus(driver, Status.WARNING, "Check the baggage amount ", "Payment is successful and Amount displayed is not correct.", null);
						}
					}
					try {
						driver.findElement(By.xpath(ContinueXpath)).click();
						FlightSearch.loadhandling(driver);
					}catch (Exception e) {}
				}
				else if (FlightSearch.getTrimTdata(queryObjects.getTestData("Waive")).equalsIgnoreCase("y")) {
					EditBagFee(driver, PaxName);	  //edit Bag fee Waive
				} 
					
				
			}

		}

		else if ( AllowBg.equalsIgnoreCase("N")) {
			queryObjects.logStatus(driver, Status.PASS, "Add baggage was not allowed ", "Baggage ", null);

		}
		else {
			if(Double.valueOf(balamt).intValue()==0){
				queryObjects.logStatus(driver, Status.PASS, "Proceed to Pay checking", "BAL amount zero so Proceed to Pay button disabled ", null);

			}
			else
				queryObjects.logStatus(driver, Status.FAIL, "Proceed to Pay checking", "BAL amount Not zero but Proceed to Pay button was disabled ", null);
		}
	


	}
	
	public static void SetBaggage(WebDriver driver, BFrameworkQueryObjects queryObjects, int k, String BagWgt, String BagType) throws InterruptedException {
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath("("+BagtypeXpath+")["+k+"]")).click();
		//Select Standard or catalog
		if (BagType.equalsIgnoreCase("")) {    // bag type not give surf it will select standard bag
			driver.findElement(By.xpath("(//md-option[contains(@ng-value,'standard')])["+k+"]")).click();			
		} else if (BagType.equalsIgnoreCase("SURF")) {
			driver.findElement(By.xpath("("+TopXpath+")["+k+"]")).click();
			Thread.sleep(1000);			
			driver.findElement(By.xpath("//*[contains(text(),'More')]")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//md-radio-button/div[contains(text(),'SURFBOARD UPTO 32KG' ) and not(contains(text(),'KITE'))]//parent::md-radio-button")).click();
			Thread.sleep(1000);			
		} else if (BagType.equalsIgnoreCase("Stroller")) {
			driver.findElement(By.xpath("("+TopXpath+")["+k+"]")).click();
			Thread.sleep(1000);			
			driver.findElement(By.xpath("//*[contains(text(),'More')]")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//md-radio-button/div[contains(text(),'STROLLER OR PUSHCHAIR' ) and not(contains(text(),'KITE'))]//parent::md-radio-button")).click();
			Thread.sleep(1000);
		} else if (BagType.equalsIgnoreCase("BIKE") && BagWgt.equalsIgnoreCase("32")) {
			driver.findElement(By.xpath("("+TopXpath+")["+k+"]")).click();
			Thread.sleep(1000);			
			driver.findElement(By.xpath("//md-radio-button/div[contains(text(),'BICYCLE UP TO 70LB 32 KG' ) and not(contains(text(),'KITE'))]//parent::md-radio-button")).click();
			Thread.sleep(1000);
			//Atul 9Mar - Code to add BIKE as baggage
		} else if (BagType.equalsIgnoreCase("BIKE")) {
			driver.findElement(By.xpath("("+TopXpath+")["+k+"]")).click();
			Thread.sleep(1000);
			Thread.sleep(1000);			
			driver.findElement(By.xpath("//md-radio-button/div[contains(text(),'BICYCLE UP TO  23KG' ) and not(contains(text(),'KITE'))]//parent::md-radio-button")).click();
			Thread.sleep(1000);	
		}
else if(BagType.equalsIgnoreCase("INFCAR")) {
			driver.findElement(By.xpath("("+TopXpath+")["+k+"]")).click();
			Thread.sleep(1000);
			Thread.sleep(1000);			
			driver.findElement(By.xpath("//md-radio-button/div[contains(text(),'INFANT CAR SEAT' ) and not(contains(text(),'KITE'))]//parent::md-radio-button")).click();
			Thread.sleep(1000);
		}
		String Oversize="";
		if (BagWgt.contains("OS")) {
			BagWgt = BagWgt.replace("-OS", "");
			Oversize="Y";
		}
		driver.findElement(By.xpath("("+WeightXpath+")["+k+"]")).sendKeys(BagWgt);	
		if(true){ //manual bag true
			driver.findElement(By.xpath("("+BagTagXpath+")["+k+"]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath(Manual_Bag_Tag_Xpat)).click();
			Thread.sleep(2000);
			String tag_num="CM"+(RandomStringUtils.random(6, false, true));
			Bag_Tag_Number.add(tag_num);
			driver.findElement(By.xpath("("+Manual_Bag_Tag_Number_Xpath+")["+k+"]")).sendKeys(tag_num);
		}
		
		if (Oversize.equalsIgnoreCase("Y")) {
			driver.findElement(By.xpath("(//md-checkbox[@ng-model='bag.IsOversized'])["+k+"]")).click();
		}

		Thread.sleep(1000);
	}
	public static void AssignAvailableSeats(WebDriver driver, BFrameworkQueryObjects queryObjects) {

		try {
			String sSelSeatType = FlightSearch.getTrimTdata(queryObjects.getTestData("SeatType"));
			String sExistingseat = "";
			boolean Assignseat = false;
			if(sCheckinMsg.equalsIgnoreCase("Passenger Details Update")){
				sExistingseat= driver.findElement(By.xpath("//div[@ng-repeat='seat in segment.travelerInfo.Seats']//span[@class='pssgui-design-small-box-2 ng-binding']")).getText().trim();
			}			
			driver.findElement(By.xpath("//i[@class='icon-seatmap']")).click();
			FlightSearch.loadhandling(driver);
			List<WebElement> ChkCnt = driver.findElements(By.xpath("//md-checkbox[@type='checkbox']"));
			for (int i = 1; i <= ChkCnt.size(); i++) {
				Assignseat=false;
				if ((Boolean.parseBoolean(driver.findElement(By.xpath("(//md-checkbox[@type='checkbox'])["+i+"]")).getAttribute("aria-checked")))==true) {
					List<WebElement> AvSeat =driver.findElements(By.xpath("//div[@class='seat-holder ng-scope icn-available']"));
					AvSeat.get(1).click();
					Assignseat =true;
				}else {
					driver.findElement(By.xpath("(//md-checkbox[@type='checkbox'])["+i+"]")).click();
					List<WebElement> AvSeat =driver.findElements(By.xpath("//div[@class='seat-holder ng-scope icn-available']"));
					AvSeat.get(1).click();
					Assignseat =true;

				}					
			}			
			if (Assignseat) {
				if (!sSelSeatType.isEmpty()) {
					driver.findElement(By.xpath("//md-select[@aria-label='Passenger Seat Characteristics']")).click();
					driver.findElement(By.xpath("//md-option/div[contains(text(),'"+sSelSeatType+"')]")).click();
					try {
						driver.findElement(By.xpath("//button[contains(text(),'Yes')]")).click();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}

				driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//button[contains(text(),'Continue')]")).click();
				FlightSearch.loadhandling(driver);
				if(sCheckinMsg.equalsIgnoreCase("Passenger Details Update")){
					String SeatAssigned = driver.findElement(By.xpath("//div[@ng-repeat='seat in segment.travelerInfo.Seats']//span[@class='pssgui-design-small-box-2 ng-binding']")).getText().trim();
					if (SeatAssigned!="" && (SeatAssigned!=sExistingseat)) {
						queryObjects.logStatus(driver, Status.PASS, "Passenger Details Screen - Update Seat ", "Seat is updated ", null);		
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Passenger Details Screen - Update Seat ", "Seat is not updated ", null);
					}
				}else {
					List<WebElement> SeatAssigned = driver.findElements(By.xpath("//input[@ng-model='seat.NewSeatNumber']"));				
					for (int j = 0; j < ChkCnt.size(); j++) {
						SeatAssigned = driver.findElements(By.xpath("//input[@ng-model='seat.NewSeatNumber']"));
						if (SeatAssigned.get(j).getText().trim()!="") {					
							queryObjects.logStatus(driver, Status.PASS, "Check the Seat assigned status for Segment "+j, "Seat is assigned for the route ", null);		
						} else {
							queryObjects.logStatus(driver, Status.FAIL, "Check the Seat assigned status for Segment "+j, "Seat is not assigned ", null);

						}
					}
				}				
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	public static void FlightSearch_CheckinLanding(WebDriver driver, String cDate, String fOrigin, String FlightNum) {
		try {
			driver.findElement(By.xpath("//div[contains(text(),'Home')]")).click();
			FlightSearch.loadhandling(driver);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (!FlightNum.isEmpty()) {
			driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).clear();
			driver.findElement(By.xpath("//form[@name='flightSearch.form']//child::label[contains(text(),'Flight')]/following-sibling::input")).sendKeys(FlightNum);
		}
		if (!fOrigin.isEmpty()) {
			driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).clear();
			driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).sendKeys(fOrigin);
			FlightSearch.loadhandling(driver);
			//driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).sendKeys(Keys.TAB);
			//Tab Alternate Solution
			try {
				driver.findElement(By.xpath(Login.SelList)).click();
			} catch (Exception e1) {
				try {
					driver.findElement(By.xpath(Login.SelList2)).click();
				} catch (Exception e) {
					try {
						driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='"+fOrigin+"']/ancestor::span[2]/following-sibling::span/span/../../..")).click();

						}catch(Exception e3) {
							driver.findElement(By.xpath("//pssgui-autocomplete[@ng-model='flightSearch.model']//input[@name='origin']")).sendKeys(Keys.TAB);
						}
				}
			}
		}
		if (!cDate.isEmpty()) {
			driver.findElement(By.xpath("//pssgui-date-time[@ng-model='flightSearch.model.departureDate']//input[@class='md-datepicker-input']")).clear();
			driver.findElement(By.xpath("//pssgui-date-time[@ng-model='flightSearch.model.departureDate']//input[@class='md-datepicker-input']")).sendKeys(cDate);
		}		 
		FlightSearch.loadhandling(driver);
		driver.findElement(By.xpath("//div[@class='ng-scope flex']/button[@translate='pssgui.search']")).click();
		FlightSearch.loadhandling(driver);

	}


	public void GO_Show_ForceSale(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException{
		
		try
		{	
			String Go_show_paxname_surName=FlightSearch.getTrimTdata(queryObjects.getTestData("Go_show_paxname_surName"));
			String Go_show_paxname_givenName=FlightSearch.getTrimTdata(queryObjects.getTestData("Go_show_paxname_givenName"));
			String Go_show_Class=FlightSearch.getTrimTdata(queryObjects.getTestData("Go_show_Class"));
			String Go_show_FFN=FlightSearch.getTrimTdata(queryObjects.getTestData("Go_show_FFN"));
			String Go_show=FlightSearch.getTrimTdata(queryObjects.getTestData("Go_show"));
			String Force_sale=FlightSearch.getTrimTdata(queryObjects.getTestData("Force_sale"));
			
		driver.findElement(By.xpath("//md-select-value//div[text()='Standard CheckIn']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//md-option//div[text()='Go show / Force Sale']")).click();
		Thread.sleep(2000);
		if (Go_show_paxname_surName.length()>0 )
			driver.findElement(By.xpath("//input[@ng-model='goShow.model.surName']")).sendKeys(Go_show_paxname_surName);
		else
			driver.findElement(By.xpath("//input[@ng-model='goShow.model.surName']")).sendKeys(RandomStringUtils.random(6, true, false));
		Thread.sleep(2000);
		if (Go_show_paxname_givenName.length()>0 )
			driver.findElement(By.xpath("//input[@ng-model='goShow.model.givenName']")).sendKeys(Go_show_paxname_givenName);
		else
			driver.findElement(By.xpath("//input[@ng-model='goShow.model.givenName']")).sendKeys(RandomStringUtils.random(6, true, false));
		if(Go_show_Class.length()>0)
			driver.findElement(By.xpath("//input[@ng-model='goShow.model.class']")).sendKeys(Go_show_Class);
		else
			driver.findElement(By.xpath("//input[@ng-model='goShow.model.class']")).sendKeys("C");
		Thread.sleep(2000);
		if(Go_show_FFN.length()>0)
			driver.findElement(By.xpath("//input[@name='ffNumber']")).sendKeys(Go_show_Class);
		Thread.sleep(2000);
		if (Go_show.equalsIgnoreCase("yes"))
			driver.findElement(By.xpath("//md-radio-button[@value='GoShow' and @aria-checked='false']")).click();
		if (Force_sale.equalsIgnoreCase("yes"))
			driver.findElement(By.xpath("//md-radio-button[@value='ForcedSell' and @aria-checked='false']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[text()='OK']")).click();
		FlightSearch.loadhandling(driver);
		String pnr_Go_show=driver.findElement(By.xpath("//div[@action='pnr']/div/div/div[1]/div[1]")).getText();
		queryObjects.logStatus(driver, Status.PASS, "Create PNR in GO show", "PNR created successfully",null);	
		Login.PNRNUM= pnr_Go_show;
		if (true)
			return;
			}
		catch (Exception e){
			queryObjects.logStatus(driver, Status.FAIL, "Go_show Error", e.getLocalizedMessage(), e);
		}
		
		
	}
	
	
	public void PaxNote_List(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException{
		
		try{
			String Numofmsgs=FlightSearch.getTrimTdata(queryObjects.getTestData("Numofmsgs"));
			String Deletenote=FlightSearch.getTrimTdata(queryObjects.getTestData("Deletenote"));
	 
			int Numofmsgs1=0;
			int Deletenote1=0;
				if(!Numofmsgs.isEmpty()){
					Numofmsgs1=	Integer.parseInt(Numofmsgs);
				}
				if(!Deletenote.isEmpty()){
					 Deletenote1=Integer.parseInt(Deletenote);
				}
			driver.findElement(By.xpath("//md-select-value//div[text()='Standard CheckIn']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//md-option//div[text()='Add Passenger Note']")).click();
			Thread.sleep(2000);
			
			if(driver.findElement(By.xpath("//div[@translate=\"pssgui.message\"]")).isDisplayed()) {
				Thread.sleep(100);
				for(int i=0;i<Numofmsgs1;i++) {
					driver.findElement(By.xpath("//textarea[contains(@ng-model,'')]")).sendKeys("message "+i+"");
					driver.findElement(By.xpath("//button[@translate=\"pssgui.post\"]")).click();
					FlightSearch.loadhandling(driver);
					Thread.sleep(1000);
				}
				queryObjects.logStatus(driver, Status.PASS, ""+Numofmsgs+"Notes are added","succesfully" , null);
			}
			
			if(!Deletenote.isEmpty()) {
				List<WebElement> deleteicon=driver.findElements(By.xpath("//i[@class=\"icon-removed\"]"));
				int j;
				
				for(j=0;j<Deletenote1;j++) {
			
					deleteicon=driver.findElements(By.xpath("//i[@class=\"icon-removed\"]"));
					deleteicon.get(0).click();
					FlightSearch.loadhandling(driver);
					Thread.sleep(1000);
					
				}	
				queryObjects.logStatus(driver, Status.PASS, ""+Deletenote+"Notes are deleted","succesfully" , null);
			
				deleteicon=driver.findElements(By.xpath("//i[@class=\"icon-removed\"]"));
				int Remaining_msgs=deleteicon.size();
				queryObjects.logStatus(driver, Status.INFO, "Passenget Note left", ""+Remaining_msgs+"", null);
				
				driver.findElement(By.xpath("//button[@translate=\"pssgui.cancel\"]")).click();
				Thread.sleep(1000);
				if(driver.findElement(By.xpath("//i[@ng-if=\"passengerData.PassengerMessages\"]")).isDisplayed()) 
					queryObjects.logStatus(driver, Status.PASS, "Passenger note icon is", "Displayed", null);
				
				queryObjects.logStatus(driver, Status.PASS, "Add Passenger Note from Pax list", "Succesfully", null);
				
				return;
			}
		} catch (Exception e){
			queryObjects.logStatus(driver, Status.FAIL, "Add Passenger Note from Pax list", e.getLocalizedMessage(), e);
		}
	}

	public static double Baggage_calculator(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException{
		String Category=queryObjects.getTestData("Bag_Category");

		int iCategory = 0;
		double Sumof_Allbag_Amount=0;
		
		if(Category.equalsIgnoreCase("Central_Nath_America"))  // this is for get baggage fees from Environment sheet...
			iCategory=1;
		else if(Category.equalsIgnoreCase("Flight_form_South_America"))  // this is for get baggage fees from Environment sheet...
			iCategory=2;
		else if(Category.equalsIgnoreCase("Business_Class"))  // this is for get baggage fees from Environment sheet...
			iCategory=3;
		else if (Category.equalsIgnoreCase("FFP_PAX_Only_Silver")) 
			iCategory=4;
		else if (Category.equalsIgnoreCase("FFP_PAX_Otherthan_Silver")) 
			iCategory=5;
		
		   Bag_wait = new ArrayList<>();
		  Bag_value = new ArrayList<>();
		  Bag_overSize = new ArrayList<>(); 
		  
		  List<WebElement> Bagg=driver.findElements(By.xpath(WeightXpath));
		  List<WebElement> Bagg_oversize=driver.findElements(By.xpath("//md-checkbox[@ng-model='bag.IsOversized']"));
		  
		  for (int getbaggs = 0; getbaggs < Bagg.size(); getbaggs++) {	
			  String getbagsiz3=Bagg.get(getbaggs).getAttribute("value");
			 int bagwit= Integer.parseInt(getbagsiz3);
			 
			 Bag_wait.add(bagwit); //Adding Bag wait in Array
			 String bagover=Bagg_oversize.get(getbaggs).getAttribute("aria-checked").trim();
			 if(bagover.equalsIgnoreCase("true")) //Add Bag over Size in Array
				 Bag_overSize.add("yes");
			 else
				 Bag_overSize.add("No");
		  }
		  
		  Bag_value = new ArrayList<>(Bag_overSize);
		  
		  if(Category.equalsIgnoreCase("Central_Nath_America"))     //Central_Nath_America
			  Sumof_Allbag_Amount= Central_Nath_America(Bag_wait,Bag_value,Bag_overSize,iCategory,6,1,2,queryObjects,driver); 
		  else if(Category.equalsIgnoreCase("Flight_form_South_America"))     //Flight_form_South_America
			  Sumof_Allbag_Amount= Flight_form_South_America_and_FFP_PAX_Only_Silver(Bag_wait,Bag_value,Bag_overSize,iCategory,6,2,queryObjects,driver);
		  else if(Category.equalsIgnoreCase("Business_Class"))   // Business_Class
			  Sumof_Allbag_Amount= Business_class_and_FFP_PAX_Otherthan_Silver(Bag_wait,Bag_value,Bag_overSize,iCategory,5,2,queryObjects,driver);  
		  else if(Category.equalsIgnoreCase("FFP_PAX_Only_Silver"))     //FFP_PAX_Only_Silver
			  Sumof_Allbag_Amount= Flight_form_South_America_and_FFP_PAX_Only_Silver(Bag_wait,Bag_value,Bag_overSize,iCategory,6,2,queryObjects,driver); 
		  else if(Category.equalsIgnoreCase("FFP_PAX_Otherthan_Silver"))     //FFP_PAX_Otherthan_Silver
			  Sumof_Allbag_Amount= Business_class_and_FFP_PAX_Otherthan_Silver(Bag_wait,Bag_value,Bag_overSize,iCategory,6,3,queryObjects,driver);
		    
		 String convalu= FlightSearch.roundDouble(String.valueOf(Sumof_Allbag_Amount));
		 Sumof_Allbag_Amount=Double.parseDouble(convalu);
		 
		return Sumof_Allbag_Amount;
		  
		 
	}
	public static double Business_class_and_FFP_PAX_Otherthan_Silver(List<Integer> Bag_wait,List<String> Bag_value,List<String> Bag_overSize,int iCategory,int Farecolumn, int freecount,BFrameworkQueryObjects queryObjects,WebDriver driver) throws IOException{

		int free_BagCount=1;
		double Sumof_Allbag_Amount=0;
		String over_sizeamount=envRead_Bagg(1, 13);  //150
		String over_Waitamount_24_to_33_KG=envRead_Bagg(1, 12);  //100
		String over_Waitamount_33_to_45_KG=envRead_Bagg(2, 12);  //200
		String cost_Bag_Amount=envRead_Bagg(iCategory, Farecolumn);       //175
		
		for(int s=0;s<Bag_wait.size() ; s++){

			if(((Bag_wait.get(s)>23) && (Bag_wait.get(s)<33)) && (free_BagCount <= freecount)){  // category check for 24 to 33  means free
				if(Bag_overSize.get(s).equalsIgnoreCase("No"))
					//Bag_value.add("free");
					Bag_value.set(s,"free");
				else
					Bag_value.set(s,over_sizeamount);
					//Bag_value.add(over_sizeamount);   // this is for over size amount
					//Bag_value.add("150");
				free_BagCount=free_BagCount+1;
			}else
				Bag_value.set(s,"COPA");	
		}
		int temp2 = 100;
		if(free_BagCount<= freecount ){   // Free count not reached so again iterate
			int temp1=0;
			int k=0;
			for(int s=0;s<Bag_wait.size() ; s++){
				if(Bag_wait.get(s)<24){
					if(Bag_wait.get(s) > temp1 && k==0) { // store first value
						temp1=Bag_wait.get(s);   //bag wait
						temp2=s;                 //bag array position
						k=k+1;
					}
					if(temp1 < Bag_wait.get(s) ){
						temp1=Bag_wait.get(s);
						temp2=s;
						
					}
				}
				
			}
			if(temp2 != 100){
				if(Bag_overSize.get(temp2).equalsIgnoreCase("No"))
					Bag_value.set(temp2, "free");
				else
					Bag_value.set(temp2, over_sizeamount);
					//Bag_value.set(temp2, "150");
				free_BagCount=free_BagCount+1;
			}
			
		}
		
		if(free_BagCount<= freecount  ){ 
			int temp1=0;
			int temp3=100;
			int k=0;
			for(int s=0;s<Bag_wait.size() ; s++){
				if(Bag_wait.get(s)<24){
					if(Bag_wait.get(s) > temp1 && k==0  && temp2 != s) { // store first value
						temp1=Bag_wait.get(s);   //bag wait
						temp3=s;   //bag array position
						k=k+1;
					}
					if(temp1 < Bag_wait.get(s) && temp2 != s ){
						temp1=Bag_wait.get(s);
						temp3=s;
						
					}
				}
				
				
			}
			if(temp3 != 100){
				if(Bag_overSize.get(temp3).equalsIgnoreCase("No"))
					Bag_value.set(temp3, "free");
				else
					Bag_value.set(temp3, over_sizeamount);
					//Bag_value.set(temp3, "150");
				free_BagCount=free_BagCount+1;
			}
		}
		
		if(free_BagCount<= freecount  ){
			for(int s=0;s<Bag_wait.size() ; s++){
				if(free_BagCount< 3  ){
					if(Bag_wait.get(s)>32){
						if(Bag_overSize.get(s).equalsIgnoreCase("No"))
							Bag_value.set(s, over_Waitamount_33_to_45_KG);  // only over wait amount
							//Bag_value.set(s, "200");
						else{
							String overSize_overwait_amt=SumBaggAmount(over_sizeamount, over_Waitamount_33_to_45_KG, "0");
							Bag_value.set(s, overSize_overwait_amt);   // over size and over wait amount  350  =200+150
							//Bag_value.set(s, "350");
						}
						free_BagCount=free_BagCount+1;
					}
				}
			}
		}
		
		
		
		for(int p=0;p<Bag_value.size();p++){
			
			if(Bag_value.get(p).equalsIgnoreCase("COPA") ){
				if((Bag_wait.get(p)< 24))
					if(Bag_overSize.get(p).equalsIgnoreCase("No"))
						Bag_value.set(p, cost_Bag_Amount);
						//Bag_value.set(p, "175");
					else{
						String NonFree_Oversize=SumBaggAmount(over_sizeamount, cost_Bag_Amount, "0");
						Bag_value.set(p,NonFree_Oversize);   // 150+175=325
						//Bag_value.set(p, "325");
						}
				else if((Bag_wait.get(p)> 23) && (Bag_wait.get(p)<33))
					if(Bag_overSize.get(p).equalsIgnoreCase("No")){
						String NonFee_and_overwait_23_32=SumBaggAmount(over_Waitamount_24_to_33_KG, cost_Bag_Amount, "0");
						Bag_value.set(p, NonFee_and_overwait_23_32);   //100+175=275
						//Bag_value.set(p, "275");
					}
					else{
						String NonFee_and_overwait_23_32_and_oversize=SumBaggAmount(over_Waitamount_24_to_33_KG, cost_Bag_Amount, over_sizeamount);
						Bag_value.set(p, NonFee_and_overwait_23_32_and_oversize);   // 100+175+150
						//Bag_value.set(p, "425");
						}
				else if((Bag_wait.get(p)> 32) && Bag_wait.get(p)<46 )
					if(Bag_overSize.get(p).equalsIgnoreCase("No")){
						String NonFree_overwait_33_45=SumBaggAmount(over_Waitamount_33_to_45_KG, cost_Bag_Amount, "0");
						Bag_value.set(p, NonFree_overwait_33_45);   //200+175
						//Bag_value.set(p, "375");
						}
					else{
						String NonFree_overwait_33_45_and_oversize=SumBaggAmount(over_Waitamount_33_to_45_KG, cost_Bag_Amount, over_sizeamount);
						Bag_value.set(p, NonFree_overwait_33_45_and_oversize);    //200+175+150
						//Bag_value.set(p, "525");
					}						
			}
		}
			
		String tem_Bag_wait="";	
		for(int p=0;p<Bag_wait.size();p++){
			System.out.print(Bag_wait.get(p)+"   ");
			tem_Bag_wait=tem_Bag_wait+" "+Bag_wait.get(p);
		}
		queryObjects.logStatus(driver, Status.PASS, "Bagg wait Details", "Bag wait details are "+tem_Bag_wait, null);
		System.out.println();
		String tem_Bag_Value="";
		for(int p=0;p<Bag_value.size();p++){
			System.out.print(Bag_value.get(p)+" ");
			String val=Bag_value.get(p);
			tem_Bag_Value=tem_Bag_Value+" "+Bag_value.get(p);
			if(!val.equalsIgnoreCase("free"))
				Sumof_Allbag_Amount=Sumof_Allbag_Amount+Integer.parseInt(val);
		}
		queryObjects.logStatus(driver, Status.PASS, "Bagg Value Details", "Bag Value details are :: "+tem_Bag_Value, null);
		return Sumof_Allbag_Amount;	
	}

	
	public static double Flight_form_South_America_and_FFP_PAX_Only_Silver(List<Integer> Bag_wait,List<String> Bag_value,List<String> Bag_overSize,int iCategory,int Farecolumn, int freecount,BFrameworkQueryObjects queryObjects,WebDriver driver) throws IOException{

		int free_BagCount=1;
		double Sumof_Allbag_Amount=0;
		String over_sizeamount=envRead_Bagg(1, 13);  //150
		String over_Waitamount_24_to_33_KG=envRead_Bagg(1, 12);  //100
		String over_Waitamount_33_to_45_KG=envRead_Bagg(2, 12);  //200
		String cost_Bag_Amount=envRead_Bagg(iCategory, Farecolumn);       //175
		
		for(int s=0;s<Bag_wait.size() ; s++){

			if(((Bag_wait.get(s)>0) && (Bag_wait.get(s)<24)) && (free_BagCount <= freecount)){  // category check for 24 to 33  means free
				if(Bag_overSize.get(s).equalsIgnoreCase("No"))
					//Bag_value.add("free");
					Bag_value.set(s,"free");
				else
					Bag_value.set( s,over_sizeamount);
					//Bag_value.add(over_sizeamount);   // this is for over size amount
					//Bag_value.add("150");
				free_BagCount=free_BagCount+1;
			}else
				Bag_value.set(s,"COPA");	
				//Bag_value.add("COPA");	
		}
		
		if(free_BagCount<= freecount  ){
			for(int s=0;s<Bag_wait.size() ; s++){
				if(free_BagCount<= freecount  ){			
					if((Bag_wait.get(s)> 23) && (Bag_wait.get(s)<33)){
						if(Bag_overSize.get(s).equalsIgnoreCase("No"))
							Bag_value.set(s, over_Waitamount_24_to_33_KG);
							//Bag_value.set(s, "100");
						else{
							String overSize_overwait_amt=SumBaggAmount(over_sizeamount, over_Waitamount_24_to_33_KG, "0");
							Bag_value.set(s, overSize_overwait_amt);
							//Bag_value.set(s, "250");
						}
						free_BagCount=free_BagCount+1;
					}
					if((Bag_wait.get(s)> 32) && Bag_wait.get(s)<46 ){
						if(Bag_overSize.get(s).equalsIgnoreCase("No"))
							Bag_value.set(s, over_Waitamount_33_to_45_KG);
							//Bag_value.set(s, "200");
						else{
							String overSize_overwait_amt=SumBaggAmount(over_sizeamount, over_Waitamount_33_to_45_KG, "0");
							Bag_value.set(s, overSize_overwait_amt);
							//Bag_value.set(s, "350");
						}
						free_BagCount=free_BagCount+1;
					}
				}
			}
		}
		
		for(int p=0;p<Bag_value.size();p++){
			
			if(Bag_value.get(p).equalsIgnoreCase("COPA") ){
				if((Bag_wait.get(p)< 24))
					if(Bag_overSize.get(p).equalsIgnoreCase("No"))
						Bag_value.set(p, cost_Bag_Amount);
						//Bag_value.set(p, "175");
					else{
						String NonFree_Oversize=SumBaggAmount(over_sizeamount, cost_Bag_Amount, "0");
						Bag_value.set(p,NonFree_Oversize);   // 150+175=325
						//Bag_value.set(p, "325");
						}
				else if((Bag_wait.get(p)> 23) && (Bag_wait.get(p)<33))
					if(Bag_overSize.get(p).equalsIgnoreCase("No")){
						String NonFee_and_overwait_23_32=SumBaggAmount(over_Waitamount_24_to_33_KG, cost_Bag_Amount, "0");
						Bag_value.set(p, NonFee_and_overwait_23_32);   //100+175=275
						//Bag_value.set(p, "275");
					}
					else{
						String NonFee_and_overwait_23_32_and_oversize=SumBaggAmount(over_Waitamount_24_to_33_KG, cost_Bag_Amount, over_sizeamount);
						Bag_value.set(p, NonFee_and_overwait_23_32_and_oversize);   // 100+175+150
						//Bag_value.set(p, "425");
						}
				else if((Bag_wait.get(p)> 32) && Bag_wait.get(p)<46 )
					if(Bag_overSize.get(p).equalsIgnoreCase("No")){
						String NonFree_overwait_33_45=SumBaggAmount(over_Waitamount_33_to_45_KG, cost_Bag_Amount, "0");
						Bag_value.set(p, NonFree_overwait_33_45);   //200+175
						//Bag_value.set(p, "375");
						}
					else{
						String NonFree_overwait_33_45_and_oversize=SumBaggAmount(over_Waitamount_33_to_45_KG, cost_Bag_Amount, over_sizeamount);
						Bag_value.set(p, NonFree_overwait_33_45_and_oversize);    //200+175+150
						//Bag_value.set(p, "525");
					}						
			}
		}
		String tem_Bag_wait="";	
		for(int p=0;p<Bag_wait.size();p++){
			System.out.print(Bag_wait.get(p)+"   ");
			tem_Bag_wait=tem_Bag_wait+" "+Bag_wait.get(p);
		}
		queryObjects.logStatus(driver, Status.PASS, "Bagg wait Details", "Bag wait details are "+tem_Bag_wait, null);
		String tem_Bag_Value="";
		System.out.println();
		for(int p=0;p<Bag_value.size();p++){
			System.out.print(Bag_value.get(p)+" ");
			String val=Bag_value.get(p);
			tem_Bag_Value=tem_Bag_Value+" "+Bag_value.get(p);
			if(!val.equalsIgnoreCase("free"))
				Sumof_Allbag_Amount=Sumof_Allbag_Amount+Integer.parseInt(val);
		}
		queryObjects.logStatus(driver, Status.PASS, "Bagg Value Details", "Bag Value details are :: "+tem_Bag_Value, null);
		return Sumof_Allbag_Amount;	
	}

	public static double Central_Nath_America(List<Integer> Bag_wait,List<String> Bag_value,List<String> Bag_overSize,int iCategory,int Farecolumn, int freecount,int SecondbagCost,BFrameworkQueryObjects queryObjects,WebDriver driver) throws IOException{

		int free_BagCount=1;
		int sec_Bag_less_cost=1;
		double Sumof_Allbag_Amount=0;
		String over_sizeamount=envRead_Bagg(1, 13);  //150
		String over_Waitamount_24_to_33_KG=envRead_Bagg(1, 12);  //100
		String over_Waitamount_33_to_45_KG=envRead_Bagg(2, 12);  //200
		String cost_Bag_Amount=envRead_Bagg(iCategory, Farecolumn);       //175
		String cost_2_Bag_Amount=envRead_Bagg(iCategory, SecondbagCost);       //175
		for(int s=0;s<Bag_wait.size() ; s++){

			if(((Bag_wait.get(s)>0) && (Bag_wait.get(s)<24)) && (free_BagCount <= freecount)){  // category check for 24 to 33  means free
				if(Bag_overSize.get(s).equalsIgnoreCase("No"))
					//Bag_value.add("free");
					Bag_value.set(s,"free");
				else
					Bag_value.set(s,over_sizeamount);
					//Bag_value.add(over_sizeamount);   // this is for over size amount
					//Bag_value.add("150");
				free_BagCount=free_BagCount+1;
			}else
				Bag_value.set(s,"COPA");	
		}
		
		
		if(free_BagCount<= freecount  ){
			for(int s=0;s<Bag_wait.size() ; s++){
				if(free_BagCount<= freecount  ){			
					if((Bag_wait.get(s)> 23) && (Bag_wait.get(s)<33)){
						if(Bag_overSize.get(s).equalsIgnoreCase("No"))
							Bag_value.set(s, over_Waitamount_24_to_33_KG);
							//Bag_value.set(s, "100");
						else{
							String overSize_overwait_amt=SumBaggAmount(over_sizeamount, over_Waitamount_24_to_33_KG, "0");
							Bag_value.set(s, overSize_overwait_amt);
							//Bag_value.set(s, "250");
						}
						free_BagCount=free_BagCount+1;
					}
					if((Bag_wait.get(s)> 32) && Bag_wait.get(s)<46 ){
						if(Bag_overSize.get(s).equalsIgnoreCase("No"))
							Bag_value.set(s, over_Waitamount_33_to_45_KG);
							//Bag_value.set(s, "200");
						else{
							String overSize_overwait_amt=SumBaggAmount(over_sizeamount, over_Waitamount_33_to_45_KG, "0");
							Bag_value.set(s, overSize_overwait_amt);
							//Bag_value.set(s, "350");
						}
						free_BagCount=free_BagCount+1;
					}
				}
			}
		}	
		
		for(int p=0;p<Bag_value.size();p++){
			
			if(Bag_value.get(p).equalsIgnoreCase("COPA") ){
				if((Bag_wait.get(p)< 24))
					if(Bag_overSize.get(p).equalsIgnoreCase("No"))
						if(sec_Bag_less_cost==1)
							Bag_value.set(p, cost_2_Bag_Amount);
							//Bag_value.set(p, "40");
						else
							Bag_value.set(p, cost_Bag_Amount);
						//Bag_value.set(p, "175");
					else //if(Bag_overSize.get(p).equalsIgnoreCase("No"))
						if(sec_Bag_less_cost==1){
							String NonFree_2Bag_oversize=SumBaggAmount(over_sizeamount, cost_2_Bag_Amount, "0");
							Bag_value.set(p, NonFree_2Bag_oversize); 
							//Bag_value.set(p, "190");   //40+150
						}
						else{
							String NonFree_Oversize=SumBaggAmount(over_sizeamount, cost_Bag_Amount, "0");
							Bag_value.set(p,NonFree_Oversize);   // 150+175=325
							//Bag_value.set(p, "325");
							}
				else if((Bag_wait.get(p)> 23) && (Bag_wait.get(p)<33))
					if(Bag_overSize.get(p).equalsIgnoreCase("No"))
						if(sec_Bag_less_cost==1){
							String NonFree_2Bag_overwait_23_32=SumBaggAmount(over_Waitamount_24_to_33_KG, cost_2_Bag_Amount, "0");
							Bag_value.set(p, NonFree_2Bag_overwait_23_32);
							//Bag_value.set(p, "140");	//100+40
						}
						else{
						String NonFee_and_overwait_23_32=SumBaggAmount(over_Waitamount_24_to_33_KG, cost_Bag_Amount, "0");
						Bag_value.set(p, NonFee_and_overwait_23_32);   //100+175=275
						//Bag_value.set(p, "275");
						}
					else  //if(Bag_overSize.get(p).equalsIgnoreCase("No"))
						if(sec_Bag_less_cost==1){
							String NonFree_2Bag_overwait_23_32_and_oversize=SumBaggAmount(over_Waitamount_24_to_33_KG, cost_2_Bag_Amount, over_sizeamount);
							Bag_value.set(p, NonFree_2Bag_overwait_23_32_and_oversize);
							//Bag_value.set(p, "290");	//100+40+150
						}
						else{
							String NonFee_and_overwait_23_32_and_oversize=SumBaggAmount(over_Waitamount_24_to_33_KG, cost_Bag_Amount, over_sizeamount);
							Bag_value.set(p, NonFee_and_overwait_23_32_and_oversize);   // 100+175+150
							//Bag_value.set(p, "425");
						}
				else if((Bag_wait.get(p)> 32) && Bag_wait.get(p)<46 )
					if(Bag_overSize.get(p).equalsIgnoreCase("No"))
						if(sec_Bag_less_cost==1){
							String NonFree_2Bag_overwait_33_45=SumBaggAmount(over_Waitamount_33_to_45_KG, cost_2_Bag_Amount, "0");
							Bag_value.set(p, NonFree_2Bag_overwait_33_45);
							//Bag_value.set(p, "240");	//200+40
						}
						else{
							String NonFree_overwait_33_45=SumBaggAmount(over_Waitamount_33_to_45_KG, cost_Bag_Amount, "0");
							Bag_value.set(p, NonFree_overwait_33_45);   //200+175
							//Bag_value.set(p, "375");
						}
					else  //if(Bag_overSize.get(p).equalsIgnoreCase("No"))
						if(sec_Bag_less_cost==1){
							String NonFree_overwait_33_45_and_oversize_2Bag=SumBaggAmount(over_Waitamount_33_to_45_KG, cost_2_Bag_Amount, over_sizeamount);
							Bag_value.set(p, NonFree_overwait_33_45_and_oversize_2Bag);
							//Bag_value.set(p, "390");	//200+40+150
						}
						else{
							String NonFree_overwait_33_45_and_oversize=SumBaggAmount(over_Waitamount_33_to_45_KG, cost_Bag_Amount, over_sizeamount);
							Bag_value.set(p, NonFree_overwait_33_45_and_oversize);    //200+175+150
							//Bag_value.set(p, "525");
						}
				sec_Bag_less_cost=sec_Bag_less_cost+1;		
			}

		}
			
		String tem_Bag_wait="";
		for(int p=0;p<Bag_wait.size();p++){
			System.out.print(Bag_wait.get(p)+"   ");
			tem_Bag_wait=tem_Bag_wait+" "+Bag_wait.get(p);
		}
		queryObjects.logStatus(driver, Status.PASS, "Bag wait Details", "Bag wait details are "+tem_Bag_wait, null);
		System.out.println();
		String tem_Bag_Value="";
		for(int p=0;p<Bag_value.size();p++){
			System.out.print(Bag_value.get(p)+" ");
			String val=Bag_value.get(p);
			tem_Bag_Value=tem_Bag_Value+" "+Bag_value.get(p);
			if(!val.equalsIgnoreCase("free"))
				Sumof_Allbag_Amount=Sumof_Allbag_Amount+Integer.parseInt(val);
		}
		queryObjects.logStatus(driver, Status.PASS, "Bag Value Details", "Bag Value details are :: "+tem_Bag_Value, null);
		return Sumof_Allbag_Amount;	
	}
	
	public static void Delete_AddBag(WebDriver driver,BFrameworkQueryObjects queryObjects, String DelIndex) throws IOException {
		String SptBag[] = null;
		int BagCnt = driver.findElements(By.xpath("//i[@class='icon-baggage bag-bg']")).size();
		if (BagCnt>0) {
			for (int db = 1; db <= BagCnt; db++) {
				if (DelIndex.equalsIgnoreCase("ALL")) {
					driver.findElement(By.xpath("(//i[contains(@class,'icon-removed')])["+db+"]")).click();
				}
				else {
					if (DelIndex.contains(";")) {
						SptBag = DelIndex.split(";");
						for (int sb = 0; sb < SptBag.length; sb++) {
							driver.findElement(By.xpath("(//i[contains(@class,'icon-removed')])["+(SptBag[sb])+"]")).click();
						}
						break;
					}
					else {
						driver.findElement(By.xpath("(//i[contains(@class,'icon-removed')])["+(Integer.parseInt(DelIndex))+"]")).click();
						break;
					}
				}
			}
			driver.findElement(By.xpath("//button[text()='Submit' and contains(@ng-click,'baggage.form')]")).click();
			FlightSearch.loadhandling(driver);
			queryObjects.logStatus(driver, Status.PASS, "Bag is deleted", "Bag is deleted : "+DelIndex, null);
		}
		//String Bag_Tag_Arry=Bag_Tag_Number.get(index);
		//driver.findElement(By.xpath("//div[contains(text(),'"+Bag_Tag_Arry+"')]/ancestor::div[contains(@class,'baggage-product')]//i[contains(@class,'icon-removed')]")).click();
	}
	public static void Delete_bag(WebDriver driver,BFrameworkQueryObjects queryObjects,int iclickBag, String Category, String BagIndex, String AddBag_Index) throws IOException, InterruptedException{

			int iCategory = 0;
			double Sumof_Allbag_Amount=0;
			if(Category.equalsIgnoreCase("Central_Nath_America"))  // this is for get baggage fees from Environment sheet...
				iCategory=1;
			else if(Category.equalsIgnoreCase("Flight_form_South_America"))  // this is for get baggage fees from Environment sheet...
				iCategory=2;
			else if(Category.equalsIgnoreCase("Business_Class"))  // this is for get baggage fees from Environment sheet...
				iCategory=3;
			else if (Category.equalsIgnoreCase("FFP_PAX_Only_Silver")) 
				iCategory=4;
			else if (Category.equalsIgnoreCase("FFP_PAX_Otherthan_Silver")) 
				iCategory=5;
				   Bag_value = new ArrayList<>(Bag_overSize);
				  
				  if(Category.equalsIgnoreCase("Central_Nath_America"))     //Central_Nath_America
					  Sumof_Allbag_Amount= Central_Nath_America(Bag_wait,Bag_value,Bag_overSize,iCategory,6,1,2,queryObjects,driver); 
				  else if(Category.equalsIgnoreCase("Flight_form_South_America"))     //Flight_form_South_America
					  Sumof_Allbag_Amount= Flight_form_South_America_and_FFP_PAX_Only_Silver(Bag_wait,Bag_value,Bag_overSize,iCategory,6,2,queryObjects,driver);
				  else if(Category.equalsIgnoreCase("Business_Class"))   // Business_Class
					  Sumof_Allbag_Amount= Business_class_and_FFP_PAX_Otherthan_Silver(Bag_wait,Bag_value,Bag_overSize,iCategory,5,2,queryObjects,driver);  
				  else if(Category.equalsIgnoreCase("FFP_PAX_Only_Silver"))     //FFP_PAX_Only_Silver
					  Sumof_Allbag_Amount= Flight_form_South_America_and_FFP_PAX_Only_Silver(Bag_wait,Bag_value,Bag_overSize,iCategory,6,2,queryObjects,driver); 
				  else if(Category.equalsIgnoreCase("FFP_PAX_Otherthan_Silver"))     //FFP_PAX_Otherthan_Silver
					  Sumof_Allbag_Amount= Business_class_and_FFP_PAX_Otherthan_Silver(Bag_wait,Bag_value,Bag_overSize,iCategory,6,3,queryObjects,driver);  
				
				 // bagcal(Bag_wait, Bag_value, Bag_overSize, Category);
					/////////////////////////////////////////////
					System.out.println();
					System.out.println("--------------------------------------");
					System.out.print("Delete Bagg process started ::");
					// Delete Bag
					  List<Integer> Delete_Bag_wait = new ArrayList<>(Bag_wait);
					  List<String> Delete_Bag_value = new ArrayList<>(Bag_value);
					  
					  List<String> Paid_Bag_Delete_value = new ArrayList<>();
					  
					  List<String> Paid_Bag_Temp = new ArrayList<>();
					  
					/*String BagIndex="2;6";
					String AddBag_Index="45;23";*/
						
		
					//System.out.println(BagIndex);
					String[] MulBag_Del = BagIndex.split("-");
					String[] Add_Del = AddBag_Index.split("-");
					
					for (int i = 0; i < MulBag_Del.length; i++) {
						
						// write return to check in---
						// click on Bag icon
						queryObjects.logStatus(driver, Status.PASS,"morethan_OneTime_Delete :","morethan_OneTime_Delete : "+(i+1),null);
						
						if(i!=0){
							
							List<WebElement> AddBaggage =  driver.findElements(By.xpath(BaggageXpath));
							AddBaggage.get(iclickBag).click();
							FlightSearch.loadhandling(driver);
						}
						System.out.println("");
						System.out.println(" morethan_OneTime_Delete :"+i+"");
						//System.out.println(BagIndex);
						String[] arrydelete_Bag_Index = MulBag_Del[i].split(";");
						String[] arry_New_Bag_Asber_Index = Add_Del[i].split(";");
						
						// Delete Bag as per bag tag number(store bag tag in ARRAY)
						// need to add New_Bag_wait ....	
						List<Integer>int_arrydelete_Bag_Index= new ArrayList<>(); 
						for(String s : arrydelete_Bag_Index) int_arrydelete_Bag_Index.add(Integer.valueOf(s));
						
						String temp_BagTag_Num=" ";
						for (int Dle = 0; Dle < arrydelete_Bag_Index.length; Dle++) {
							int Delete_Array_idex=Integer.parseInt(arrydelete_Bag_Index[Dle]);	
							int new_Bag_wait=Integer.parseInt(arry_New_Bag_Asber_Index[Dle]);
							if(Delete_Array_idex<(Delete_Bag_wait.size()-1)){
								for (int index = 0; index < Delete_Bag_wait.size(); index++) {
									if(Delete_Array_idex==index){
										Delete_Bag_wait.set(index, new_Bag_wait);
										if(i> 0)
											Paid_Bag_Delete_value.add(Paid_Bag_Temp.get(index));
										else
											Paid_Bag_Delete_value.add(Delete_Bag_value.get(index));
										Delete_Bag_value.set(index, "Copa");
										String Bag_Tag_Arry=Bag_Tag_Number.get(index);
										driver.findElement(By.xpath("//div[contains(text(),'"+Bag_Tag_Arry+"')]/ancestor::div[contains(@class,'baggage-product')]//i[contains(@class,'icon-removed')]")).click();
										/*int ind=index+1;  // this is for deleting in application
										driver.findElement(By.xpath("(//i[contains(@class,'icon-removed')])["+ind+"]")).click();*/
										Thread.sleep(2000);
										queryObjects.logStatus(driver, Status.PASS, "Delete Bag Tag Number", "Bagg Tag Number : "+Bag_Tag_Arry, null);
										String bag_Tag="CM"+(RandomStringUtils.random(6, false, true));
										Bag_Tag_Number.set(index,bag_Tag);
										temp_BagTag_Num=temp_BagTag_Num+";"+bag_Tag;										
									}									
								}
							}
							else{
								Delete_Bag_wait.add( new_Bag_wait);
								Delete_Bag_value.add("Copa");
								Bag_overSize.add("No");
								String bag_Tag="CM"+(RandomStringUtils.random(6, false, true));
								Bag_Tag_Number.add(bag_Tag);
								temp_BagTag_Num=temp_BagTag_Num+";"+bag_Tag;
							}					
						}
						// Delete Bag
						driver.findElement(By.xpath("//button[text()='Submit' and contains(@ng-click,'baggage.form')]")).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath(ContinueXpath)).click();
						FlightSearch.loadhandling(driver);
						List<WebElement> AddBaggage =  driver.findElements(By.xpath(BaggageXpath));
						AddBaggage.get(iclickBag).click();
						FlightSearch.loadhandling(driver);
						for (int AddinApp = 0; AddinApp < arry_New_Bag_Asber_Index.length; AddinApp++) {   // this is for add new bag for application
							driver.findElement(By.xpath(AddAnotherBag)).click();
							Thread.sleep(3000);
							int addone=AddinApp+1;
							driver.findElement(By.xpath("(//md-select[contains(@ng-model,'bag.type')])["+addone+"]")).click();
							Thread.sleep(2000);
							driver.findElement(By.xpath("(//md-option[contains(@ng-value,'standard')])["+addone+"]")).click();
							Thread.sleep(2000);
							driver.findElement(By.xpath("("+WeightXpath+")["+addone+"]")).sendKeys(arry_New_Bag_Asber_Index[AddinApp]);	
							queryObjects.logStatus(driver, Status.PASS, "After Delete Add Bag wait", "After Delete Add Bag wait : "+arry_New_Bag_Asber_Index[AddinApp], null);
							if(true){ //manual bag true
								driver.findElement(By.xpath("("+BagTagXpath+")["+addone+"]")).click();
								Thread.sleep(2000);
								driver.findElement(By.xpath(Manual_Bag_Tag_Xpat)).click();
								Thread.sleep(2000);
								String[] bag_Tag=temp_BagTag_Num.split(";");

								driver.findElement(By.xpath("("+Manual_Bag_Tag_Number_Xpath+")["+addone+"]")).sendKeys(bag_Tag[addone]);
							}
						}
						
						driver.findElement(By.xpath("//button[text()='Submit' and contains(@ng-click,'baggage.form')]")).click();
						FlightSearch.loadhandling(driver);
						String tem_Bag_wait="";
						for(int p=0;p<Delete_Bag_wait.size();p++){
							System.out.print(Delete_Bag_wait.get(p)+"   ");
							tem_Bag_wait=tem_Bag_wait+" "+Delete_Bag_wait.get(p);
						}
						queryObjects.logStatus(driver, Status.PASS, "After Delete, Add Bag wait Details", "After Delete, Add Bag wait Details "+tem_Bag_wait, null);

						String tem_Bag_Value="";
						System.out.println();
						for(int p=0;p<Delete_Bag_value.size();p++){
							System.out.print(Delete_Bag_value.get(p)+" ");
							tem_Bag_Value=tem_Bag_Value+" "+Delete_Bag_value.get(p);
						}
						queryObjects.logStatus(driver, Status.PASS, "After Delete, Add Bag Value Details", "After Delete, Add Bag Value Details :: "+tem_Bag_Value, null);
						System.out.println();
						System.out.println("--------------------------------------");
						System.out.println("Paid array");
						for(int p=0;p<Paid_Bag_Delete_value.size();p++){
							System.out.print(Paid_Bag_Delete_value.get(p)+" ");
						}
						
						System.out.println();
						System.out.println("--------------------------------------");
						System.out.println("After Delete check bag amt");
						queryObjects.logStatus(driver, Status.PASS, "After Delete , Add Bag wait,Value Details", "After Delete , Add Bag wait,Value Details", null);
						 //bagcal(Delete_Bag_wait, Delete_Bag_value, Bag_overSize, Category);
						 
						 if(Category.equalsIgnoreCase("Central_Nath_America"))     //Central_Nath_America
							  Sumof_Allbag_Amount= Central_Nath_America(Delete_Bag_wait,Delete_Bag_value,Bag_overSize,iCategory,6,1,2,queryObjects,driver); 
						  else if(Category.equalsIgnoreCase("Flight_form_South_America"))     //Flight_form_South_America
							  Sumof_Allbag_Amount= Flight_form_South_America_and_FFP_PAX_Only_Silver(Delete_Bag_wait,Delete_Bag_value,Bag_overSize,iCategory,6,2,queryObjects,driver);
						  else if(Category.equalsIgnoreCase("Business_Class"))   // Business_Class
							  Sumof_Allbag_Amount= Business_class_and_FFP_PAX_Otherthan_Silver(Delete_Bag_wait,Delete_Bag_value,Bag_overSize,iCategory,5,2,queryObjects,driver);  
						  else if(Category.equalsIgnoreCase("FFP_PAX_Only_Silver"))     //FFP_PAX_Only_Silver
							  Sumof_Allbag_Amount= Flight_form_South_America_and_FFP_PAX_Only_Silver(Delete_Bag_wait,Delete_Bag_value,Bag_overSize,iCategory,6,2,queryObjects,driver); 
						  else if(Category.equalsIgnoreCase("FFP_PAX_Otherthan_Silver"))     //FFP_PAX_Otherthan_Silver
							  Sumof_Allbag_Amount= Business_class_and_FFP_PAX_Otherthan_Silver(Delete_Bag_wait,Delete_Bag_value,Bag_overSize,iCategory,6,3,queryObjects,driver);
						 
						 Paid_Bag_Temp = new ArrayList<>(Delete_Bag_value);
						 
						 System.out.println();
						System.out.println("--------------------------------------");
						System.out.println("Added bag vs paid bag");
						queryObjects.logStatus(driver, Status.PASS, "After Delete ,Added bag vs paid bag", "After Delete ,Added bag vs paid bag ", null);
						for (int Dle = 0; Dle < arrydelete_Bag_Index.length; Dle++) {
							
							int Delete_Array_idex=Integer.parseInt(arrydelete_Bag_Index[Dle]);
							//int new_Bag_wait=Integer.parseInt(arry_New_Bag_Asber_Index[Dle]);
							
			//				if(arrydelete_Bag_possition(p))
							for (int index = 0; index < Delete_Bag_wait.size(); index++) {
								String Added_Bag_valu=Delete_Bag_value.get(index);
								if(Delete_Array_idex==index && (!Added_Bag_valu.equalsIgnoreCase("free"))){
									/*Delete_Bag_wait.set(index, new_Bag_wait);
									Delete_Bag_value.set(index, "kishore");*/
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
						 String tem_Bag_wait_Add_vs_pad="";
						 int count_Bag_wait=0;
						for(int p=0;p<Delete_Bag_wait.size();p++){
							System.out.print(Delete_Bag_wait.get(p)+"   ");
							tem_Bag_wait_Add_vs_pad=tem_Bag_wait_Add_vs_pad+" "+Delete_Bag_wait.get(p);
							count_Bag_wait=count_Bag_wait+Delete_Bag_wait.get(p);
						}
						queryObjects.logStatus(driver, Status.PASS, "After Delete ,Added bag vs paid bag,  wait detsils ", "After Delete ,Added bag vs paid bag, wait Details"+tem_Bag_wait_Add_vs_pad, null);
						System.out.println();
						int Need_To_Pay_sum=0;
						String tem_Bag_Value_Add_vs_pad="";
						for(int p=0;p<Delete_Bag_value.size();p++){
							System.out.print(Delete_Bag_value.get(p)+" ");
							tem_Bag_Value_Add_vs_pad=tem_Bag_Value_Add_vs_pad+" "+Delete_Bag_value.get(p);
							try{
								Integer.parseInt(Delete_Bag_value.get(p)); 
								Need_To_Pay_sum=Need_To_Pay_sum+Integer.parseInt(Delete_Bag_value.get(p));
							}catch(Exception e){ } // in this case catch can ignore
							
						}
						queryObjects.logStatus(driver, Status.PASS, "After Delete ,Added bag vs paid bag,  Value detsils ", "After Delete ,Added bag vs paid bag, Value Details"+tem_Bag_Value_Add_vs_pad, null);
						
						// need to do validation of amount  .....
						if(Need_To_Pay_sum>0){
							String balamt=driver.findElement(By.xpath("//div[@title='pssgui.balance.due']/div[2]")).getText().trim();
							 balamt=balamt.split("\\s+")[0];
							 
							 Double Balancein_Application= new Double(balamt);
							 Double Need_To_Pay_sum_double= new Double(Need_To_Pay_sum);
							 
							// double diffAmt=Need_To_Pay_sum_double-Balancein_Application;
							 double diffAmt=Balancein_Application-Need_To_Pay_sum_double;
							 
							 if(Need_To_Pay_sum_double==Balancein_Application){
								 queryObjects.logStatus(driver, Status.PASS,"After Delete Bag --Add other bag -->Baggage Cost checking for '"+Category +"'"," Baggage calculate working fine",null);	 
							 }
							 else if (diffAmt<100 && diffAmt>0) {
								String diff=FlightSearch.roundDouble(String.valueOf(diffAmt));
								queryObjects.logStatus(driver, Status.INFO,"After Delete Bag --Add other bag -->Baggage Cost checking for '"+Category +"'"," Baggage calculate working fine but TAX added Different AMT :"+diff,null);
							}
							 else {
									queryObjects.logStatus(driver, Status.FAIL,"Baggage Cost checking for '"+Category +"'"," Baggage calculate not working Actual amt is '"+Balancein_Application +"' Expected AMT is :: " +Need_To_Pay_sum_double,null);
								}
							
							//payment
							 driver.findElement(By.xpath(Proceedpath)).click();
							 driver.findElement(By.xpath("//button[text()='Pay']")).click();
							 FlightSearch.loadhandling(driver);

	 						if (driver.findElement(By.xpath("//div/span[@translate='pssgui.emd']/following-sibling::span")).getText().trim()!="") {
	 							queryObjects.logStatus(driver, Status.PASS, "Check EMD issued ", "Payment is successful and EMD is issued.", null);
	 						} else {
	 							queryObjects.logStatus(driver, Status.FAIL, "Check EMD issued ", "Payment is not successful and EMD is not issued.", null);
	 						}

		 					driver.findElement(By.xpath("//button[contains(text(),'Done')]")).click();
		 					FlightSearch.loadhandling(driver);
							 //payment
						
						}
						
						driver.findElement(By.xpath(ContinueXpath)).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath(FinalCheckInXpath)).click();
						FlightSearch.loadhandling(driver);
						String Get_Count_Bag_wait=driver.findElement(By.xpath("//span[@translate='pssgui.total.weight']/following-sibling::span")).getText().trim();
						Get_Count_Bag_wait=Get_Count_Bag_wait.split("\\s+")[1];
						if(count_Bag_wait==Integer.parseInt(Get_Count_Bag_wait))
							queryObjects.logStatus(driver, Status.PASS, "Bag wait checkin in checkin screen", "Check in screen bag wait displaying correctly ", null);
						else
							queryObjects.logStatus(driver, Status.FAIL, "Bag wait checkin in checkin screen", "Check in screen bag wait should display correctly Actual is : "+Get_Count_Bag_wait+" KG Expected is: "+count_Bag_wait+ " KG", null);
						if(i < (MulBag_Del.length-1)){
							driver.findElement(By.xpath("//button[@translate='pssgui.return.to.check.in']")).click();
							FlightSearch.loadhandling(driver);
						}
						
					}				
		}
	
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
	
	public static String SumBaggAmount(String amt1,String amt2,String amt3){
		
		int sum=(Integer.parseInt(amt1)+Integer.parseInt(amt2)+Integer.parseInt(amt3));
		
		return (String.valueOf(sum));
	}
	//Primary Doc types validation-- CR167B 
		// yashodha update 6-Nov-2019  Start
		public static void PriDocTypeValidation(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException {
			try {
				
			//	Boolean Passport =driver.findElement(By.xpath("//md-option[@selected="selected"]//div[contains(text(),'Passport')]")).isDisplayed();
				Boolean Diplomatic_passport =driver.findElement(By.xpath("//div[contains(text(),'Diplomatic Passport')]")).isDisplayed();
				Boolean Official_passport =driver.findElement(By.xpath("//div[contains(text(),'Official Passport')]")).isDisplayed();
				Boolean Aliens_passport =driver.findElement(By.xpath("//div[contains(text(),'Aliens Passport')]")).isDisplayed();
				Boolean Service_Passport =driver.findElement(By.xpath("//div[contains(text(),'Service Passport')]")).isDisplayed();
				Boolean Special_passport =driver.findElement(By.xpath("//div[contains(text(),'Special Passport')]")).isDisplayed();
				Boolean Laissez_Passport =driver.findElement(By.xpath("//div[contains(text(),'Laissez Passer-Passport')]")).isDisplayed();
				Boolean Emergency_Passport =driver.findElement(By.xpath("//div[contains(text(),'Emergency Passport')]")).isDisplayed();
				
				Boolean Permanent_Resident_Card =driver.findElement(By.xpath("//div[contains(text(),'US/CA Permanent resident card')]")).isDisplayed();
				Boolean Alien_Card =driver.findElement(By.xpath("//div[contains(text(),'US/CA Alien Card')]")).isDisplayed();
				Boolean Border_Crossing_Card =driver.findElement(By.xpath("//div[contains(text(),'Border crossing card')]")).isDisplayed();
				Boolean Naturalization_Certificate =driver.findElement(By.xpath("//div[contains(text(),'Naturalization certificate')]")).isDisplayed();
				Boolean Military_Identificatio =driver.findElement(By.xpath("//div[contains(text(),'Military identification')]")).isDisplayed();
				Boolean Refugee_Travel_Document =driver.findElement(By.xpath("//div[contains(text(),'Refugee travel document')]")).isDisplayed();
				Boolean Pilot_License =driver.findElement(By.xpath("//div[contains(text(),'Pilot license')]")).isDisplayed();
				
				if( Diplomatic_passport & Official_passport & Aliens_passport & Service_Passport & Special_passport & Laissez_Passport & Emergency_Passport &Permanent_Resident_Card
				&Alien_Card &Border_Crossing_Card &Naturalization_Certificate&Military_Identificatio &Refugee_Travel_Document &Pilot_License)
					queryObjects.logStatus(driver, Status.PASS, "PrimaryDocType_Validation", "SPrimaryDocType_Validation Successfull", null);
					
			}catch (Exception e) {

				queryObjects.logStatus(driver, Status.FAIL, "PrimaryDocType_Validation", "SPrimaryDocType_Validation failed", null);
			}
		}
		public static void PassportTypesvalidation(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException {
			try {
				//Boolean Passport =driver.findElement(By.xpath("//md-option//div[contains(text(),'Passport')]")).isDisplayed();
				Boolean Diplomatic_passport =driver.findElement(By.xpath("//div[contains(text(),'Diplomatic Passport')]")).isDisplayed();
				Boolean Official_passport =driver.findElement(By.xpath("//div[contains(text(),'Official Passport')]")).isDisplayed();
				Boolean Aliens_passport =driver.findElement(By.xpath("//div[contains(text(),'Aliens Passport')]")).isDisplayed();
				Boolean Service_Passport =driver.findElement(By.xpath("//div[contains(text(),'Service Passport')]")).isDisplayed();
				Boolean Special_passport =driver.findElement(By.xpath("//div[contains(text(),'Special Passport')]")).isDisplayed();
				Boolean Laissez_Passport =driver.findElement(By.xpath("//div[contains(text(),'Laissez Passer-Passport')]")).isDisplayed();
				Boolean Emergency_Passport =driver.findElement(By.xpath("//div[contains(text(),'Emergency Passport')]")).isDisplayed();		
						
				
				if(Diplomatic_passport & Official_passport & Aliens_passport & Service_Passport & Special_passport & Laissez_Passport & Emergency_Passport) 
					queryObjects.logStatus(driver, Status.PASS, "PassportTypesvalidation", "PassportTypesvalidation Successfull", null);
			
			}catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "PassportTypesvalidation", "PassportTypesvalidation failed", null);
			}
		}	
		
		public static void SecDoctypevalidation(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException {
			try {
				
				Boolean Permanent_Resident_Card =driver.findElement(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-select-menu[@role='presentation' and @class='_md md-overflow']//md-option/div[contains(text(),'US/CA Permanent resident card')]")).isDisplayed();
				Boolean Alien_Card =driver.findElement(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-select-menu[@role='presentation' and @class='_md md-overflow']//md-option/div[contains(text(),'US/CA Alien Card')]")).isDisplayed();
				Boolean Border_Crossing_Card =driver.findElement(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-select-menu[@role='presentation' and @class='_md md-overflow']//md-option/div[contains(text(),'Border crossing card')]")).isDisplayed();
				Boolean Naturalization_Certificate =driver.findElement(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-select-menu[@role='presentation' and @class='_md md-overflow']//md-option/div[contains(text(),'Naturalization certificate')]")).isDisplayed();
				Boolean Military_Identification =driver.findElement(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-select-menu[@role='presentation' and @class='_md md-overflow']//md-option/div[contains(text(),'Military identification')]")).isDisplayed();
				Boolean Refugee_Travel_Document =driver.findElement(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-select-menu[@role='presentation' and @class='_md md-overflow']//md-option/div[contains(text(),'Refugee travel document')]")).isDisplayed();
				Boolean Pilot_License =driver.findElement(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-select-menu[@role='presentation' and @class='_md md-overflow']//md-option/div[contains(text(),'Pilot license')]")).isDisplayed();
				Boolean Visa =driver.findElement(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-select-menu[@role='presentation' and @class='_md md-overflow']//md-option/div[contains(text(),'Seaman Book')]")).isDisplayed();
				Boolean Seaman_Book =driver.findElement(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-select-menu[@role='presentation' and @class='_md md-overflow']//md-option/div[contains(text(),'Immigrant Visa')]")).isDisplayed();
				Boolean Immigrant_Visa =driver.findElement(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-select-menu[@role='presentation' and @class='_md md-overflow']//md-option/div[contains(text(),'Visa')]")).isDisplayed();
				Boolean Residence_Permit =driver.findElement(By.xpath("//div[@class='md-select-menu-container md-active md-clickable']//md-select-menu[@role='presentation' and @class='_md md-overflow']//md-option/div[contains(text(),'Residence Permit')]")).isDisplayed();
				
				if(Permanent_Resident_Card & Alien_Card &Border_Crossing_Card &Naturalization_Certificate&Military_Identification&Refugee_Travel_Document&Pilot_License&Visa&Seaman_Book
					&Immigrant_Visa&Residence_Permit)
				queryObjects.logStatus(driver, Status.PASS, "SecDoctypevalidation", "SecDoctypevalidation Successfull", null);
			}catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "SecDoctypevalidation", "SecDoctypevalidation failed", null);
			}
		}	
		//Primary Doc types validation-- CR167B 
			// yashodha update 7-Nov-2019  End
		public static String ColorsValidation(String IconColor)  {//-jenny
	        if (IconColor.contains("#00a973") || IconColor.contains("rgba(0, 169, 115, 1)") || IconColor.contains("rgb(0, 169, 115)")) {
				return "Green";
			} else if (IconColor.contains("#c1c1c1") || IconColor.contains("rgba(193, 193, 193, 1)") || IconColor.contains("rgb(193, 193, 193)")) {
				return "Grey";
			} else if (IconColor.contains("#deb739") || IconColor.contains("rgba(222, 183, 57, 1)") || IconColor.contains("rgb(222, 183, 57)")) {
				return "Yellow";
			} else if (IconColor.contains("#f8a822") || IconColor.contains("rgba(248, 168, 34, 1)") || IconColor.contains("rgb(248, 168, 34)")) {
				return "Orange";
			} else if (IconColor.contains("#dc3716") || IconColor.contains("rgba(220, 55, 22, 1)") || IconColor.contains("rgb(220, 55, 22)")) {
				return "Red";
			}
	        return "";
	    }
		
		public static void OpenFlights_Checkin(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception {
			openflightflag=1;
			String Flt=driver.findElement(By.xpath(flighNumXpath)).getText().trim();
			String date=driver.findElement(By.xpath(flightdateXpath)).getText().trim();
			String Orgin=driver.findElement(By.xpath(FlighOrginXpath)).getText().trim();
			OpenFlight opn=new OpenFlight(Flt,Orgin,date,driver,queryObjects);
			opn.run();
			Atoflow ato=new Atoflow();
			ato.SearchPassenger(driver, queryObjects);	
			return;
		}
		
		public static void AssignControllingAgent(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException {
			try {
				added_agent=false;
				driver.findElement(By.xpath(FlightAct)).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//md-option[contains(@value,'controllingAgents')]")).click();
				FlightSearch.loadhandling(driver);
				//Himani 19March
				queryObjects.logStatus(driver, Status.PASS, "Control Agent", "Adding control agent", null);				
				//Himani 20March
				if(ControlAgent_Name.equalsIgnoreCase("")) {
					driver.findElement(By.xpath("//input[contains(@name,'agent')]")).sendKeys(Login.Usernm);
					Thread.sleep(2000);
					driver.findElement(By.xpath("//input[contains(@name,'agent')]")).sendKeys(Keys.TAB);
					Thread.sleep(1000);
					driver.findElement(By.xpath("//button[text()='Add']")).click();
					Thread.sleep(1000);
				} else {
					//Himani 8APR
					String All_Agents[] = ControlAgent_Name.split(";");
					for(int num=0; num<All_Agents.length;num++)
					{
						driver.findElement(By.xpath("//input[contains(@name,'agent')]")).sendKeys(All_Agents[num]);
						Thread.sleep(2000);
						driver.findElement(By.xpath("//input[contains(@name,'agent')]")).sendKeys(Keys.TAB);
						Thread.sleep(1000);
						driver.findElement(By.xpath("//button[text()='Add']")).click();
						Thread.sleep(1000);
					}					
					//driver.findElement(By.xpath("//input[contains(@name,'agent')]")).sendKeys(ControlAgent_Name);
				}
				//Thread.sleep(2000);
				//driver.findElement(By.xpath("//input[contains(@name,'agent')]")).sendKeys(Keys.TAB);
				//Thread.sleep(1000);
				//driver.findElement(By.xpath("//button[text()='Add']")).click();
				//Thread.sleep(1000);
				if(driver.findElements(By.xpath("//span[@class='ng-binding msg-error' and contains(text(),'Function restricted to a controlling agent or a supervisor')]")).size()>0) {
					queryObjects.logStatus(driver,Status.INFO, "Assigning Control Agent", "Function restricted to a controlling agent or a supervisor", null);									
				} else {
					added_agent=true;
				}
				//queryObjects.logStatus(driver,Status.INFO, "Assigning Control Agent", "Added control Agent", null);
				driver.findElement(By.xpath("//button[text()='Save']")).click();
				FlightSearch.loadhandling(driver);
				//Himani 19March
				queryObjects.logStatus(driver, Status.PASS, "Control Agent", "Added control agent", null);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Assign Controlling Agent", "Unable to assign Controlling Agent",e);
			}
		}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		public static void DeleteControllingAgent(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException {
			try {
				driver.findElement(By.xpath(FlightAct)).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//md-option[contains(@value,'controllingAgents')]")).click();
				FlightSearch.loadhandling(driver);
				//Himani 19March
				queryObjects.logStatus(driver, Status.PASS, "Control Agent", "Deleting control agent", null);				
				//Himani 20March
				if(ControlAgent_Name.equalsIgnoreCase(""))   //if not specified then delete the first agent
					driver.findElement(By.xpath("//md-checkbox[@ng-model='agent.isDeleted']")).click();
				else
				{   //else check the agent is present and select it to be deleted 
					if(driver.findElements(By.xpath("//tbody[@ng-repeat='agent in ctrlAgentCtrl.model.assignedControllingAgents']//td[contains(text(),'"+ControlAgent_Name+"')]")).size()>0)
						driver.findElement(By.xpath("//tbody[@ng-repeat='agent in ctrlAgentCtrl.model.assignedControllingAgents']//td[contains(text(),'"+ControlAgent_Name+"')]/following::md-checkbox[@ng-model='agent.isDeleted']")).click();
				}
				
				if(driver.findElements(By.xpath("//span[@class='ng-binding msg-error' and contains(text(),'Function restricted to a controlling agent or a supervisor')]")).size()>0)
					queryObjects.logStatus(driver,Status.INFO, "Deleting Control Agent", "Function restricted to a controlling agent or a supervisor", null);									
				else
					queryObjects.logStatus(driver,Status.PASS, "Deleting Control Agent", "Deleted control Agent", null);
					
				driver.findElement(By.xpath("//button[text()='Save']")).click();
				FlightSearch.loadhandling(driver);
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Deleting Controlling Agent", "Unable to delete Controlling Agent",e);
				}
		}																											   		

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		public void Blockseat(WebDriver driver,BFrameworkQueryObjects queryObjects,int iBlockSeats,int iSeatstaken, String sBlockorUnblock ) throws Exception{
			String SeatmapCabin ="";
			try {
				List<WebElement> BusinessCnt = null; List<WebElement> PremiumCnt = null; List<WebElement> EmergencyCnt = null;  int EcoOthersCnt = 0;
				SeatmapCabin = FlightSearch.getTrimTdata(queryObjects.getTestData("SeatmapCabin"));
				if(SeatmapCabin.isEmpty()) {
					if(sBlockorUnblock.equalsIgnoreCase("Unblock")) {
						List<WebElement> blocksets=driver.findElements(By.xpath("//div[contains(@class,'icn-blocked')]"));
						iBlockSeats=blocksets.size();
						blocksets.get(0).click();
						Thread.sleep(2000);
					}
					else {
						List<WebElement> available=driver.findElements(By.xpath("//div[@class='seat']/div[contains(@class,'icn-available') or contains(@class,'icn-chargeable') and not (contains(@class,'icn-held')) and not (contains(@class,'icn-blocked'))]"));
						int iAvilableseats=available.size();
						available.get(0).click();
						Thread.sleep(2000);
					}
				}
				else {
					String[] SeatmapCabinType = SeatmapCabin.split(";");


					String sSeatPath = "";
					if(sBlockorUnblock.equalsIgnoreCase("Unblock")) {
						sSeatPath ="//div[contains(@class,'cabin-type') and contains(text(),'SeatType')]/..//div[@class='seat']/div[contains(@class,'icn-blocked')]";
					}
					else {
					sSeatPath = SeatPath;
					}
					if(SeatmapCabinType[0].contains("Business")) {
						BusinessCnt=driver.findElements(By.xpath(sSeatPath.replace("SeatType", "Business")));
						for(int i=0; i<BusinessCnt.size(); i++)
						{
							BusinessCnt.get(i).click();
							Thread.sleep(2000);
						}
						if(sBlockorUnblock.equalsIgnoreCase("Unblock")) {
							driver.findElement(By.xpath("//div[@translate='pssgui.undo']")).click();
						}
						else {
							driver.findElement(By.xpath("//div[@translate='pssgui.update']")).click();
						}
						FlightSearch.loadhandling(driver);					
						if (driver.findElements(By.xpath("//span[text()='Function restricted to controlling agents']")).size()>0) {
							AssignControllingAgent(driver,queryObjects);
							if(sBlockorUnblock.equalsIgnoreCase("Unblock")) {
								driver.findElement(By.xpath("//div[@translate='pssgui.undo']")).click();
							}
							else {
								driver.findElement(By.xpath("//div[@translate='pssgui.update']")).click();
							}
						}
						FlightSearch.loadhandling(driver);
					}
					if(SeatmapCabinType[1].contains("Premium")) {
						PremiumCnt=driver.findElements(By.xpath(sSeatPath.replace("SeatType", "Economy")+"//span[contains(text(),'V')]"));
						for(int i=0; i<PremiumCnt.size(); i++)
						{
							PremiumCnt.get(i).click();
							Thread.sleep(2000);
						}
						if(sBlockorUnblock.equalsIgnoreCase("Unblock")) {
							driver.findElement(By.xpath("//div[@translate='pssgui.undo']")).click();
						}
						else {
							driver.findElement(By.xpath("//div[@translate='pssgui.update']")).click();
						}
						FlightSearch.loadhandling(driver);
						if (driver.findElements(By.xpath("//span[text()='Function restricted to controlling agents']")).size()>0) {
							AssignControllingAgent(driver,queryObjects);
							if(sBlockorUnblock.equalsIgnoreCase("Unblock")) {
								driver.findElement(By.xpath("//div[@translate='pssgui.undo']")).click();
							}
							else {
								driver.findElement(By.xpath("//div[@translate='pssgui.update']")).click();
							}
						}
						FlightSearch.loadhandling(driver);
					}
					if(SeatmapCabinType[2].contains("EmergencyExit")) {
						EmergencyCnt=driver.findElements(By.xpath(sSeatPath.replace("SeatType", "Economy")+"//span[contains(text(),'E')]"));
						for(int i=0; i<EmergencyCnt.size(); i++)
						{
							EmergencyCnt.get(i).click();
							Thread.sleep(2000);
						}
						if(sBlockorUnblock.equalsIgnoreCase("Unblock")) {
							driver.findElement(By.xpath("//div[@translate='pssgui.undo']")).click();
						}
						else {
							driver.findElement(By.xpath("//div[@translate='pssgui.update']")).click();
						}
						FlightSearch.loadhandling(driver);
						if (driver.findElements(By.xpath("//span[text()='Function restricted to controlling agents']")).size()>0) {
							AssignControllingAgent(driver,queryObjects);
							if(sBlockorUnblock.equalsIgnoreCase("Unblock")) {
								driver.findElement(By.xpath("//div[@translate='pssgui.undo']")).click();
							}
							else {
								driver.findElement(By.xpath("//div[@translate='pssgui.update']")).click();
							}
						}
						FlightSearch.loadhandling(driver);
					}
					if(SeatmapCabinType[3].contains("Economy")) {
						int TotalEcoCnt = 0;
						TotalEcoCnt = driver.findElements(By.xpath(sSeatPath.replace("SeatType", "Economy"))).size();
						for(int es=1; es<=TotalEcoCnt; es++) {
							String SeatType = ""; String SelectedSeat = "";
							SelectedSeat = driver.findElement(By.xpath("("+sSeatPath.replace("SeatType", "Economy")+"//span[contains(@ng-if,'seatmapCtrl')])["+es+"]/..")).getAttribute("class");
							SeatType = driver.findElement(By.xpath("("+sSeatPath.replace("SeatType", "Economy")+"//span[contains(@ng-if,'seatmapCtrl')])["+es+"]")).getText().trim();
							if (!SelectedSeat.contains("icn-taken") && !SeatType.contains("V") && !SeatType.contains("E")) {
								driver.findElement(By.xpath("("+sSeatPath.replace("SeatType", "Economy")+"//span[contains(@ng-if,'seatmapCtrl')])["+es+"]/..")).click();
							}
						}
						if(sBlockorUnblock.equalsIgnoreCase("Unblock")) {
							driver.findElement(By.xpath("//div[@translate='pssgui.undo']")).click();
						}
						else {
							driver.findElement(By.xpath("//div[@translate='pssgui.update']")).click();
						}
						FlightSearch.loadhandling(driver);
						if (driver.findElements(By.xpath("//span[text()='Function restricted to controlling agents']")).size()>0) {
							AssignControllingAgent(driver,queryObjects);
							if(sBlockorUnblock.equalsIgnoreCase("Unblock")) {
								driver.findElement(By.xpath("//div[@translate='pssgui.undo']")).click();
							}
							else {
								driver.findElement(By.xpath("//div[@translate='pssgui.update']")).click();
							}
						}
						FlightSearch.loadhandling(driver);
					}
				}			
				if(sBlockorUnblock.equalsIgnoreCase("Block")) {
					if(driver.findElements(By.xpath("//div[contains(@class,'cabin-type') and contains(text(),'Bu')]/..//div[@class='seat']/div[not(contains(@class,'icn-taken')) and not (contains(@class,'icn-blocked'))and not(contains(@class,'icn-held'))]")).size()>0) {
						
						BusinessCnt=driver.findElements(By.xpath("//div[contains(@class,'cabin-type') and contains(text(),'Bu')]/..//div[@class='seat']/div[not(contains(@class,'icn-taken')) and not (contains(@class,'icn-blocked'))and not(contains(@class,'icn-held'))]"));
						for(int i=0; i<BusinessCnt.size(); i++)
						{
							BusinessCnt.get(i).click();
							Thread.sleep(2000);
						}
						if(sBlockorUnblock.equalsIgnoreCase("Unblock")) {
							driver.findElement(By.xpath("//div[@translate='pssgui.undo']")).click();
						}
						else {
							driver.findElement(By.xpath("//div[@translate='pssgui.update']")).click();
						}
						FlightSearch.loadhandling(driver);					
						if (driver.findElements(By.xpath("//span[text()='Function restricted to controlling agents']")).size()>0) {
							AssignControllingAgent(driver,queryObjects);
							if(sBlockorUnblock.equalsIgnoreCase("Unblock")) {
								driver.findElement(By.xpath("//div[@translate='pssgui.undo']")).click();
							}
							else {
								driver.findElement(By.xpath("//div[@translate='pssgui.update']")).click();
							}
						}
						FlightSearch.loadhandling(driver);
					
						Thread.sleep(100);
					}
					String NewSeatX = "//div[contains(@class,'cabin-type') and contains(text(),'Eco')]/..//div[@class='seat']/div[not(contains(@class,'icn-taken')) and not (contains(@class,'icn-blocked'))and not(contains(@class,'icn-held'))]";
					if(driver.findElements(By.xpath(NewSeatX)).size()>0) {
						
						BusinessCnt=driver.findElements(By.xpath(NewSeatX));
							for(int es=1; es<=BusinessCnt.size(); es++) {
								String SeatType = "";
								SeatType = driver.findElement(By.xpath("("+NewSeatX+"//span[contains(@ng-if,'seatmapCtrl')])["+es+"]")).getText().trim();
								if (!SeatType.contains("V") && !SeatType.contains("E")) {
									driver.findElement(By.xpath("("+NewSeatX+"//span[contains(@ng-if,'seatmapCtrl')])["+es+"]/..")).click();
									Thread.sleep(2000);
								}
							}
							//BusinessCnt.get(i).click();
							//Thread.sleep(2000);
						if(sBlockorUnblock.equalsIgnoreCase("Unblock")) {
							driver.findElement(By.xpath("//div[@translate='pssgui.undo']")).click();
						}
						else {
							driver.findElement(By.xpath("//div[@translate='pssgui.update']")).click();
						}
						FlightSearch.loadhandling(driver);					
						if (driver.findElements(By.xpath("//span[text()='Function restricted to controlling agents']")).size()>0) {
							AssignControllingAgent(driver,queryObjects);
							if(sBlockorUnblock.equalsIgnoreCase("Unblock")) {
								driver.findElement(By.xpath("//div[@translate='pssgui.undo']")).click();
							}
							else {
								driver.findElement(By.xpath("//div[@translate='pssgui.update']")).click();
							}
						}
						FlightSearch.loadhandling(driver);
					
					}
				}
				if(sBlockorUnblock.equalsIgnoreCase("Block")) {
					int iafterupdateBlockSeats=0;
					int iafterupdatetakenSeats=0;
				if(iBlockSeats!=0) {
				List<WebElement> afterupdateblocksets=driver.findElements(By.xpath("//div[contains(@class,'icn-blocked')]"));
				 iafterupdateBlockSeats=afterupdateblocksets.size();
				}
				else {
					List<WebElement> afterupdateblocksets=driver.findElements(By.xpath("//div[contains(@class,'icn-taken')]"));
					iafterupdatetakenSeats=afterupdateblocksets.size();	
				}
				if(iafterupdateBlockSeats>iBlockSeats | iafterupdatetakenSeats>=iSeatstaken)
					queryObjects.logStatus(driver, Status.PASS, "Checking the Seat has blocked or not", "Seat has blocked: " , null);
				else
					queryObjects.logStatus(driver, Status.FAIL, "Checking the Seat has blocked or not", "Seat has not blocked:", null);


}
				} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Block or Unblock of seats", "Seat has not blocked or unblocked", null);
			}
		}
		
		
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		public void ControllingAgent(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException{			
			try {
				boolean blnerr = driver.findElement(By.xpath("//span[text()='Function restricted to controlling agents']")).isDisplayed();
				if (blnerr) {
					driver.findElement(By.xpath("//md-select[@aria-label='Flight Actions']")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//md-option[contains(@value,'controllingAgents')]")).click();
					FlightSearch.loadhandling(driver);

					driver.findElement(By.xpath("//input[contains(@name,'agent')]")).sendKeys(Login.Usernm);
					Thread.sleep(2000);
					driver.findElement(By.xpath("//input[contains(@name,'agent')]")).sendKeys(Keys.TAB);
					Thread.sleep(1000);
					driver.findElement(By.xpath("//button[text()='Add']")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//button[text()='Save']")).click();
					FlightSearch.loadhandling(driver);
					
				}

				//.get(0).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//div[@translate='pssgui.update']")).click();
				FlightSearch.loadhandling(driver);
			}catch (Exception e) {}	
		}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		public void Swapseat(WebDriver driver,BFrameworkQueryObjects queryObjects) throws IOException{
			
			try {
				driver.findElement(By.xpath("//button[@translate='pssgui.offload']")).click();
				driver.findElement(By.xpath("//button[@translate=\"pssgui.initiate.standby\"]")).click();
				FlightSearch.loadhandling(driver);
				driver.findElement(By.xpath("//button[@translate=\"pssgui.swap.seat\"]")).click();
				FlightSearch.loadhandling(driver);
		}catch (Exception e) {}	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		public static void FLIFOValidations(WebDriver driver, BFrameworkQueryObjects queryObjects, String FlightNo, String Orig, String Destn, String sDate, String iSch, String iAct, String iStatus, String iGate) throws InterruptedException, IOException, ParseException {
			String sSchedule = ""; String sActual = ""; String sDelay =  ""; String sStatus = ""; String sGate = "";
			String TimeDiff = "";
			driver.findElement(By.xpath("//i[@class='icon-tools']")).click();
			driver.findElement(By.xpath("//div[contains(text(),'Flifo Search')]/..")).click();
			Thread.sleep(200);
			if (!FlightNo.isEmpty()) {
				driver.findElement(By.xpath("//div[contains(@class,'flifo-popup')]//input[@name='Flight']")).clear();
				driver.findElement(By.xpath("//div[contains(@class,'flifo-popup')]//input[@name='Flight']")).sendKeys(FlightNo);
			}
			if (!Orig.isEmpty()) {
				driver.findElement(By.xpath("//div[contains(@class,'flifo-popup')]//input[@name='origin']")).clear();
				driver.findElement(By.xpath("//div[contains(@class,'flifo-popup')]//input[@name='origin']")).sendKeys(Orig);
				//driver.findElement(By.xpath("//div[contains(@class,'flifo-popup')]//input[@name='origin']")).sendKeys(Keys.TAB);
				//Tab Alternate Solution
				try {
					driver.findElement(By.xpath(Login.SelList)).click();
				} catch (Exception e1) {
					try {
						driver.findElement(By.xpath(Login.SelList2)).click();
					} catch (Exception e) {
						try {
							driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='"+Orig+"']/ancestor::span[2]/following-sibling::span/span/../../..")).click();

							}catch(Exception e3) {
								driver.findElement(By.xpath("//div[contains(@class,'flifo-popup')]//input[@name='origin']")).sendKeys(Keys.TAB);
							}
					}
				}
			}
			if (!Destn.isEmpty()) {
				driver.findElement(By.xpath("//div[contains(@class,'flifo-popup')]//input[@name='destination']")).clear();
				driver.findElement(By.xpath("//div[contains(@class,'flifo-popup')]//input[@name='destination']")).sendKeys(Destn);
				//driver.findElement(By.xpath("//div[contains(@class,'flifo-popup')]//input[@name='destination']")).sendKeys(Keys.TAB);
				//Tab Alternate Solution
				try {
					driver.findElement(By.xpath(Login.SelList)).click();
				} catch (Exception e1) {
					try {
						driver.findElement(By.xpath(Login.SelList2)).click();
					} catch (Exception e) {
						try {
							driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='"+Destn+"']/ancestor::span[2]/following-sibling::span/span/../../..")).click();

							}catch(Exception e3) {
								driver.findElement(By.xpath("//div[contains(@class,'flifo-popup')]//input[@name='destination']")).sendKeys(Keys.TAB);
							}
					}
				}
			}
			if (!sDate.isEmpty()) {
				driver.findElement(By.xpath("//div[contains(@class,'flifo-popup')]//input[@class='md-datepicker-input']")).clear();
				driver.findElement(By.xpath("//div[contains(@class,'flifo-popup')]//input[@class='md-datepicker-input']")).sendKeys(sDate);
			}
			FlightSearch.loadhandling(driver);
			driver.findElement(By.xpath("//div[contains(@class,'flifo-popup')]//i[@class='icon-search active-state']")).click();
			FlightSearch.loadhandling(driver);
			String Flight = driver.findElement(By.xpath("//div[contains(@class,'flight-details')]/div[contains(@class,'ng-binding')][1]")).getText().trim();
			Flight = Flight.substring(2);
			if (Flight.contains(FlightNo)) {
				sSchedule = driver.findElement(By.xpath("(//tr/th[contains(@airport-code,'DepartureAirport') and contains(text(),'"+Orig+"')]/../..//td[contains(text(),'Scheduled')]/../td)[2]")).getText().trim();
				sActual = driver.findElement(By.xpath("(//tr/th[contains(@airport-code,'DepartureAirport') and contains(text(),'"+Orig+"')]/../..//td[contains(text(),'Estimated')]/../td)[2]")).getText().trim();
				sDelay =  driver.findElement(By.xpath("(//tr/th[contains(@airport-code,'DepartureAirport') and contains(text(),'"+Orig+"')]/../..//td[contains(text(),'Delay')]/../td)[2]")).getText().trim();
				sStatus = driver.findElement(By.xpath("(//tr/th[contains(@airport-code,'DepartureAirport') and contains(text(),'"+Orig+"')]/../..//td[contains(text(),'Status')]/../td)[2]")).getText().trim();
				sGate = driver.findElement(By.xpath("(//tr/th[contains(@airport-code,'DepartureAirport') and contains(text(),'"+Orig+"')]/../..//td[contains(text(),'Gate')]/../td)[2]")).getText().trim();
				long minsDiff = (new SimpleDateFormat("HH:mm").parse(sActual).getTime()-new SimpleDateFormat("HH:mm").parse(sSchedule).getTime())/(60 * 1000) % 60;
				long hrsDiff = (new SimpleDateFormat("HH:mm").parse(sActual).getTime()-new SimpleDateFormat("HH:mm").parse(sSchedule).getTime())/(60 * 60 * 1000);
				if ((minsDiff)<10 && (minsDiff)>=0) {
					TimeDiff =hrsDiff +":0"+ minsDiff;
				} else {
					TimeDiff = hrsDiff +":"+ minsDiff;
				}
				
				if (TimeDiff.contains("-")) {
					TimeDiff = "-"+TimeDiff.replace("-", "");
				}
				if (minsDiff==0 && hrsDiff==0) {
					if (sStatus.contains("Cancel")) {
						TimeDiff = "";
					} else {
						TimeDiff = "On time";
					}
				}
				
				/*//Yashodha code
				 * String StatusDiff="";
				sDelay =(hrsDiff * 60) +":"+ minsDiff;
				TimeDiff = (hrsDiff * 60) +":"+ minsDiff;
				if (TimeDiff.contains("-")) {
					TimeDiff = "-"+TimeDiff.replace("-", "");
				}
				if (sDelay.contains("-")) {
					sDelay = "-"+sDelay.replace("-", "");
				}//Yashodha code
*/				
				if (iSch.contains(sSchedule) && iAct.contains(sActual) && sDelay.contains(TimeDiff)) {
					queryObjects.logStatus(driver, Status.PASS, "FLIFO - Check flight's Scheduled, Actual and Delay time", "Flight's Scheduled, Actual and Delay time is updated "+sSchedule+", "+sActual+" and "+sDelay, null);
					if (sStatus.toUpperCase().contains(iStatus.toUpperCase())) {
						queryObjects.logStatus(driver, Status.PASS, "FLIFO - Check flight's Status", "Flight status is updated "+sStatus, null);
						if (!iGate.isEmpty()) {
							if (sGate.contains(iGate)) {
								queryObjects.logStatus(driver, Status.PASS, "FLIFO - Check Gate number for the flight", "Gate number displayed as per the updated value "+sGate, null);
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "FLIFO - Check Gate number for the flight", "Gate number is not correct. Expected is "+iGate+ "but the Actual Gate displayed is "+sGate, null);
							}
						}
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "FLIFO - Check Flight Status", "Status is not correct. Expected is "+iStatus+ "but the Actual flight Status is "+sStatus, null);
					}
				} else {
					queryObjects.logStatus(driver, Status.FAIL, "FLIFO - Check the Scheduled, Actual and Delay time for the flight", "Details are not correct. Expected is "+iSch+", "+iAct+ "but the Actual Scheduled time, Actual Time and Delay time is "+sSchedule+", "+sActual+" and "+sDelay, null);
				}
			} else {
				queryObjects.logStatus(driver, Status.FAIL, "FLIFO - Check Flight Number", "Flight Number is not correct. Expected is "+FlightNo+ "but the Actual flight displayed is "+Flight, null);
			}
			driver.findElement(By.xpath("//div[@ng-click='dlgCtrl.closeDialog()']/i[@class='icon-close']")).click();
			//tr/th[contains(@airport-code,'DepartureAirport') and contains(text(),'"+Orig+"')]/../..//span[contains(text(),'Aircraft')]/../../td
		}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		public static void NativeShares(WebDriver driver, BFrameworkQueryObjects queryObjects, String SEntry, String Validate) throws InterruptedException, IOException {
			String SResponse = ""; boolean isRes = false;
			try {
				driver.findElement(By.xpath("//i[@class='icon-tools']")).click();
				driver.findElement(By.xpath("//div[contains(text(),'Native SHARES')]/..")).click();


				Thread.sleep(500);
				if (driver.findElement(By.xpath("//div[contains(text(),'SHARES Function')]")).isDisplayed()) {
					Thread.sleep(500);
					driver.findElement(By.xpath("//textarea[@ng-model='nativeShares.model.command']")).sendKeys(SEntry);
					Thread.sleep(500);
					driver.findElement(By.xpath("//div[@model='dlgCtrl.model']//button[contains(text(),'Submit')]")).click();
					FlightSearch.loadhandling(driver);
					for (int rs = 1; rs <= driver.findElements(By.xpath("//pre[@class='ng-binding']")).size(); rs++) {
						SResponse = SResponse+";"+driver.findElement(By.xpath("(//pre[@class='ng-binding'])["+rs+"]")).getText().trim();
						if (SResponse.contains(Validate)) {
							isRes = true;
						}
					}
					if (isRes) {
						queryObjects.logStatus(driver, Status.PASS, "Native Shares, Check the flight response", "Flight message is updated", null);
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Native Shares, Check the flight response", "Flight message is not updated", null);
					}
					driver.findElement(By.xpath("//div[@model='dlgCtrl.model']//button[contains(text(),'Cancel')]")).click();



				} else {
					queryObjects.logStatus(driver, Status.FAIL, "Native Shares, Check the flight display", "Shares page is not display", null);
				}
			} catch (Exception e) {


				queryObjects.logStatus(driver, Status.FAIL, "Native Shares", "Unable to validate flight status", null);
			}
		}
		
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		public static final String AddDateStr(int addval, String Dateformat, String Datetype, Date DateVal) throws Exception {
			Calendar calendar = Calendar.getInstance();
			if (DateVal==null) {
				Date currentDate = new Date();			
				calendar.setTime(currentDate);
			} else {
				calendar.setTime(DateVal);
			}			
			if (Datetype.contains("day")) {
				int day = calendar.get(Calendar.DATE);
				calendar.set(Calendar.DATE, day + addval);
			}else if (Datetype.contains("hour")) {
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				calendar.set(Calendar.HOUR_OF_DAY, hour + addval);
			}else if (Datetype.contains("minute")) {
				int min = calendar.get(Calendar.MINUTE);
				calendar.set(Calendar.MINUTE, min + addval); 
			}else if (Datetype.contains("second")) {
				int sec = calendar.get(Calendar.SECOND);
				calendar.set(Calendar.SECOND, sec + addval);
			}else if (Datetype.contains("month")) {
				int mon = calendar.get(Calendar.MONTH);
				calendar.set(Calendar.MONTH, mon + addval);
			}else if (Datetype.contains("year")) {
				int year = calendar.get(Calendar.YEAR);
				calendar.set(Calendar.YEAR, year + addval);
			}	
			
			SimpleDateFormat DateFrmt = new SimpleDateFormat(Dateformat);
			return DateFrmt.format(calendar.getTime());
		}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		public static void Open_SwitchTab(WebDriver driver){
			((JavascriptExecutor)driver).executeScript("window.open()");
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
		}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		public static void Close_SwitchTab(WebDriver driver) {
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			driver.close();
			driver.switchTo().window(tabs.get(0));
		}
		
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		public static void GateLanding_Search(WebDriver driver, BFrameworkQueryObjects queryObjects, String gDate, String gDest, String gFrTime, String gFlight, String gGate, String gSTD, String gETD, String gStatus, String Act) throws IOException {
			String rGate = ""; String rStatus = ""; String rSTD = ""; String rETD = ""; int rCnt = 0;
			try {
				FlightSearch.loadhandling(driver);
				if (Act.contains("Search")) {
					if (!gDate.isEmpty()) {
						driver.findElement(By.xpath("//div[contains(@ng-if,'flightDepartArrival')]//input[@class='md-datepicker-input']")).clear();
						driver.findElement(By.xpath("//div[contains(@ng-if,'flightDepartArrival')]//input[@class='md-datepicker-input']")).sendKeys(gDate);
					}
					if (!gDest.isEmpty()) {
						driver.findElement(By.xpath("//div[contains(@ng-if,'flightDepartArrival')]//input[contains(@ng-model,'searchdestination')]")).clear();
						driver.findElement(By.xpath("//div[contains(@ng-if,'flightDepartArrival')]//input[contains(@ng-model,'searchdestination')]")).sendKeys(gDest);
					}
					if (!gFrTime.isEmpty()) {
						driver.findElement(By.xpath("//div[contains(@ng-if,'flightDepartArrival')]//input[contains(@ng-model,'fromTime')]")).clear();
						driver.findElement(By.xpath("//div[contains(@ng-if,'flightDepartArrival')]//input[contains(@ng-model,'fromTime')]")).sendKeys(gFrTime);
					}
					driver.findElement(By.xpath("(//div[contains(@ng-if,'flightDepartArrival')]//button[contains(text(),'Search')])[2]")).click();
					FlightSearch.loadhandling(driver);
				} else if(Act.contains("Refresh")) {
					driver.findElement(By.xpath(lRefresh)).click();
				}
				
				//Check the status
				if (!gStatus.isEmpty()) {
					rCnt = driver.findElements(By.xpath("(//tr[contains(@ng-repeat,'flightData')])")).size();
					for (int gr = 1; gr <= rCnt; gr++) {
						if (driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'flightData')])["+gr+"]/td[1]")).getText().contains(gFlight)) {
							rGate = driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'flightData')])["+gr+"]/td[3]")).getText();
							rSTD = driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'flightData')])["+gr+"]/td[5]")).getText();
							rETD = driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'flightData')])["+gr+"]/td[6]")).getText();
							rStatus = driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'flightData')])["+gr+"]/td[8]")).getText();
							if(gStatus.contains("Select")) {
								driver.findElement(By.xpath("(//tr[contains(@ng-repeat,'flightData')])["+gr+"]/td[1]")).click();
								FlightSearch.loadhandling(driver);
							}
							break;
						}
					}
					if (gStatus.toUpperCase().contains(rStatus.toUpperCase())|| gStatus.toUpperCase().contains("BOARDING")|| rStatus.toUpperCase().contains("BOARDING")) {
						queryObjects.logStatus(driver, Status.PASS, "Gate Landing Page - Check the Status for the given flight - "+gFlight, "Flight status displayed is correct. Flight Status "+gStatus , null);
						if (!gGate.isEmpty()){
							if (gGate.contains(rGate)) {
								queryObjects.logStatus(driver, Status.PASS, "Gate Landing Page - Check the Gate number for the given flight - "+gFlight, "Gate displayed is correct. Gate number "+gGate , null);
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Gate Landing Page - Check the Gate number for the given flight - "+gFlight, "Gate displayed is correct. Expected Gate number "+gGate+ "but the Actual Gate number "+rGate , null);
							}
						}
						if (!gSTD.isEmpty()) {
							if (gSTD.contains(rSTD)) {
								queryObjects.logStatus(driver, Status.PASS, "Gate Landing Page - Check the Scheduled/STD display for the given flight - "+gFlight, "Scheduled/STD displayed is correct. Scheduled/STD "+gETD , null);
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Gate Landing Page - Check the Scheduled/STD display for the given flight - "+gFlight, "Scheduled/STD displayed is correct. Expected Scheduled/STD is "+gETD+ "but the Actual Scheduled/STD is "+rETD , null);
							}
						}
						if (!gETD.isEmpty()) {
							if (gETD.contains(rETD)) {
								queryObjects.logStatus(driver, Status.PASS, "Gate Landing Page - Check the Actual/ETD display for the given flight - "+gFlight, "ETD displayed is correct. Actual/ETD "+gETD , null);
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Gate Landing Page - Check the Actual/ETD display for the given flight - "+gFlight, "ETD displayed is correct. Expected Actual/ETD is "+gETD+ "but the Actual Actual/ETD is "+rETD , null);
							}
						}
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Gate Landing Page - Check the Status for the given flight - "+gFlight, "Flight status displayed is not correct. Expected Status is "+gStatus+ "but the Actual Status displayed is "+rStatus , null);
					}
				}
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Gate Landing Page", "Flight details verification failed" , e);
			}			
		}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		public static void PaxList_FlightSearch(BFrameworkQueryObjects queryObjects, WebDriver driver, String setDate, String fOrigin, String gFlight) throws IOException{
			try {
									  
				if (!setDate.isEmpty()) {
					driver.findElement(By.xpath("//div[@class='md-datepicker-input-container']//input")).clear();
					driver.findElement(By.xpath("//input[@type='search' and @name='origin']")).click();
					Thread.sleep(500);
					driver.findElement(By.xpath("//div[@class='md-datepicker-input-container']//input")).sendKeys(setDate);
				}
				if (!fOrigin.isEmpty()) { 
					driver.findElement(By.xpath("//input[@type='search' and @name='origin']")).clear();
					driver.findElement(By.xpath("//input[@type='search' and @name='origin']")).sendKeys(fOrigin);
					//driver.findElement(By.xpath("//input[@type='search' and @name='origin']")).sendKeys(Keys.TAB);
					//Tab Alternate Solution
					try {
						driver.findElement(By.xpath(Login.SelList)).click();
					} catch (Exception e1) {
						try {
							driver.findElement(By.xpath(Login.SelList2)).click();
						} catch (Exception e) {
							try {
								driver.findElement(By.xpath("//md-virtual-repeat-container[contains(@aria-hidden,'false')]//span[text()='"+fOrigin+"']/ancestor::span[2]/following-sibling::span/span/../../..")).click();

								}catch(Exception e3) {
									driver.findElement(By.xpath("//input[@type='search' and @name='origin']")).sendKeys(Keys.TAB);
								}
						}
					}
				}
				if (!gFlight.isEmpty()) {
					driver.findElement(By.xpath("//input[@name='Flight']")).clear();
					driver.findElement(By.xpath("//input[@name='Flight']")).sendKeys(gFlight);
				}
				driver.findElement(By.xpath("//i[@class='icon-search active-state']")).click();
				FlightSearch.loadhandling(driver);//loading-message layout-align-center-center layout-row
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Passenger List - Flight Search", "Flight search failed" , e);
			}
			
		}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		public static void FlightHeaderStatus(WebDriver driver, BFrameworkQueryObjects queryObjects, String gDate, String gOrig, String gFlight, String gGate, String gStatus, String gSTD, String gETD) throws IOException {
			PaxList_FlightSearch(queryObjects, driver, gDate, gOrig, gFlight);
			String hETD = ""; String hGate = ""; String hSTD = ""; String hStatus="";
			String tColor = "";
			try {
				//Check the status
				hStatus = driver.findElement(By.xpath("//div[contains(@class,'mid-block-third')]//div[contains(@class,'page-title')][1]")).getText();
				tColor = driver.findElement(By.xpath("//div[contains(@class,'mid-block-third')]//div[contains(@class,'page-title')][1]")).getCssValue("color");
				if (hStatus.toUpperCase().contains(gStatus.toUpperCase())|| gStatus.toUpperCase().contains("BOARDING")|| hStatus.toUpperCase().contains("BOARDING")) {
					queryObjects.logStatus(driver, Status.PASS, "Gate Landing Page - Check the Status for the given flight - "+gFlight, "Flight status displayed is correct. Flight Status "+gStatus , null);
					if (ColorsValidation(tColor).contains("Green")) {
						queryObjects.logStatus(driver, Status.PASS, "Gate Landing Page - Check the Status text color displayed in green", "Flight status text color is displayed in green" , null);
						if (!gGate.isEmpty()){
							hGate = driver.findElement(By.xpath("//div[contains(@class,'mid-block-third')]//div[contains(@class,'page-title')][2]")).getText(); 
							if (gGate.contains(hGate)) {
								queryObjects.logStatus(driver, Status.PASS, "Gate Flight Header - Check the Gate number for the given flight - "+gFlight, "Gate displayed is correct. Gate number "+gGate , null);
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Gate Flight Header - Check the Gate number for the given flight - "+gFlight, "Gate displayed is not correct. Expected Gate number "+gGate+ "but the Actual Gate number "+hGate , null);
							}
						}
						if (!gETD.isEmpty()) {
							hETD = driver.findElement(By.xpath("//div[contains(@ng-class,'Estimated')]")).getText();
							if (gETD.contains(hETD)) {
								queryObjects.logStatus(driver, Status.PASS, "Gate Flight Header - Check the ETD display for the given flight - "+gFlight, "ETD displayed is correct. ETD "+gETD , null);
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Gate Flight Header - Check the ETD display for the given flight - "+gFlight, "ETD displayed is not correct. Expected ETD is "+gETD+ "but the Actual ETD is "+hETD , null);
							}
						}
						
						if (!gSTD.isEmpty()) {
							hSTD = driver.findElement(By.xpath("//div[contains(@ng-class,'Estimated')]/preceding-sibling::div")).getText(); 
							if (gSTD.contains(hSTD)) {
								queryObjects.logStatus(driver, Status.PASS, "Gate Flight Header - Check the ETD display for the given flight - "+gFlight, "ETD displayed is correct. ETD "+gSTD , null);
							} else {
								queryObjects.logStatus(driver, Status.FAIL, "Gate Flight Header - Check the ETD display for the given flight - "+gFlight, "ETD displayed is not correct. Expected ETD is "+gSTD+ "but the Actual ETD is "+hSTD , null);
							}
						}
					} else {
						queryObjects.logStatus(driver, Status.FAIL, "Gate Landing Page - Check the Status text color displayed in green", "Flight status text color is not in green" , null);
					}				
				} else {
					queryObjects.logStatus(driver, Status.FAIL, "Gate Landing Page - Check the Status for the given flight - "+gFlight, "Flight status displayed is correct. Expected Status is "+gStatus+ "but the Actual Status displayed is "+hStatus , null);
				}
			} catch (Exception e) {
				queryObjects.logStatus(driver, Status.FAIL, "Flight Header", "Flight Details verification failed" , e);
			}			
		}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//Get the Flight Details for the PNR
		public static String SharePnrDetails(WebDriver driver, BFrameworkQueryObjects queryObjects, String PNR) throws Exception
		{
			String Flightnum= "";
			boolean contflag=false;
			if(PNR.equals("")) 
				queryObjects.logStatus(driver, Status.FAIL, "PNR was not exist", "Test data issue" , null);
			else
				contflag=true;
			if(contflag) 
			{
				try {
					WebDriverWait wait = new WebDriverWait(driver, 50);
					driver.findElement(By.xpath(ReservationXpath)).click();// clicking reservation menu bar
					Thread.sleep(2000);
					driver.findElement(By.xpath("//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Reservations')]")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//span[text()='Home']")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//div[div[text()='Search'] and @role='button']")).click();
					Thread.sleep(2000);
					//Entering PNR number in search input
					driver.findElement(By.xpath("//input[@aria-label='Search']")).click();
					driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys(PNR);

					//Clicking on Search button
					driver.findElement(By.xpath("//div[contains(@class,'itinerary-search')]//button[contains(text(),'Search')]")).click();
					FlightSearch.loadhandling(driver);
					String Pnris=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//div[@action='pnr']/div/div/div[1]/div[1]")).getText());
					Flightnum=FlightSearch.getTrimTdata(driver.findElement(By.xpath("//table[contains(@ng-table,'flightResult.tableParams')]/tbody[1]/tr/td[2]/span")).getText());
					Login.shareflightnm= Flightnum;
					queryObjects.logStatus(driver, Status.PASS, "Search PNR", "Search PNR successfully" , null);
		
				}
				catch(Exception e) {
					queryObjects.logStatus(driver, Status.FAIL, "Search PNR", "Search PNR failed: " + e.getLocalizedMessage() , e);
				}
			}
			return Flightnum;

			
		}
		
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//Get the Flight Details for the PNR
		public static void AddVolStandBy(WebDriver driver, BFrameworkQueryObjects queryObjects, String PNR, String AddVol) throws Exception
		{
			try
			{	
				String SplitPnr[] = null;
				String OverOldPNr=driver.findElement(By.xpath("//div[div[contains(text(),'Oversold')]]/div/div")).getText();

				if(OverOldPNr.equals("0"))
					queryObjects.logStatus(driver, Status.INFO, "Checking OverOld pax", "No Passanger has checked in found", null);  

				driver.findElement(By.xpath("//div[div[contains(text(),'Oversold')]]/div/div")).click();
				FlightSearch.loadhandling(driver);
				
				if (!PNR.isEmpty()) {
					if (!PNR.contains(";")) {
						PNR = PNR+";";
					}
					SplitPnr = PNR.split(";");
					for (int av = 0; av < SplitPnr.length; av++) {
						driver.findElement(By.xpath("//div[contains(@class,'hpe-pssgui airport-panel')]//button[@aria-label='Add VOL']")).click();
						FlightSearch.loadhandling(driver);
						FlightSearch.loadhandling(driver);
						int PaxCnt = driver.findElements(By.xpath("(//md-content[@on='airportSearch.paxView']//div[contains(@ng-if,'Table.pnr')])")).size();
						for (int pc = 1; pc <= PaxCnt; pc++) {
							if (PaxCnt>0) {
								if (driver.findElement(By.xpath("(//md-content[@on='airportSearch.paxView']//div[contains(@ng-if,'Table.pnr')]/div[1])["+pc+"]")).getText().contains(SplitPnr[av])) {
									driver.findElement(By.xpath("(//md-content[@on='airportSearch.paxView']//button[contains(@ng-click,'addVolPassenger')])["+pc+"]")).click();
									FlightSearch.loadhandling(driver);
									FlightSearch.loadhandling(driver);
									driver.findElement(By.xpath("//button[@ng-click='airportSearch.closePopup()']")).click();
									FlightSearch.loadhandling(driver);
									break;
								}
							}
						}
						try {
							driver.findElement(By.xpath("//div[contains(@ng-click,'dlgCtrl.closeDialog')]/i[@class='icon-close']")).click();
						} catch (Exception e) {}
					}
				} else {
					//adding vol for oversold
					driver.findElement(By.xpath("//div[contains(@class,'hpe-pssgui airport-panel')]//button[@aria-label='Add VOL']")).click();
					FlightSearch.loadhandling(driver);
					//driver.findElement(By.xpath("//input[@name='search']")).sendKeys("Adding to oversold ");

					driver.findElement(By.xpath("//div[contains(@class,'hpe-pssgui passenger-itinerary')]/div[1]//button[contains(@ng-click,'passengerItinerary.addVolPassenger')]")).click();
					FlightSearch.loadhandling(driver);
					driver.findElement(By.xpath("//button[@ng-click='airportSearch.closePopup()']")).click();
					FlightSearch.loadhandling(driver);
				}
				if (!AddVol.equalsIgnoreCase("yes")) {
					String Oversoldpax="Oversold Pax";   //suman
					for (int i = 0; i < Oversoldpax.length(); i++){

						char c = Oversoldpax.charAt(i);
						String s = new StringBuilder().append(c).toString();
						driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).sendKeys(s);
					}
					driver.findElement(By.xpath("//label[contains(text(),'Passenger List Options')]/following-sibling::input")).sendKeys(Keys.ENTER); 

					FlightSearch.loadhandling(driver);
				}
				List<WebElement> OverSoldWebElement=driver.findElements(By.xpath("//div[contains(@class,'hpe-pssgui passenger-itinerary')]/descendant::div//div/span[contains(@ng-click,'passenger-info')]"));
				List<String>  OverSoldName=new ArrayList<>();
				try {
					OverSoldWebElement.forEach(a->OverSoldName.add(a.getText().trim()));
					}catch(Exception e) {}
					String OverSoldNamelist="";
					for (String paxname : OverSoldName ) {
						if (paxname!="")

					OverSoldNamelist=OverSoldNamelist+"->"+OverSoldNamelist;

				}
				if (!OverSoldNamelist.isEmpty()) {
					queryObjects.logStatus(driver, Status.PASS, "Add Voluntary Standby Pax", "Passengers are added, list is"+OverSoldNamelist, null);
				}
				queryObjects.logStatus(driver, Status.INFO, "Displaying all oversold pax", " oversold pax->"+OverSoldNamelist, null);
			}
			catch(Exception e)
			{
				queryObjects.logStatus(driver, Status.FAIL, "Checking OverOld pax", "Unable to search oversold pax", e);    
			}
			
		}
		
		public static void Add_RemoveWeightBalRestriction(WebDriver driver, BFrameworkQueryObjects queryObjects, String Cbn_Cls, String Add_Remv) throws IOException {
			
			try {
				driver.findElement(By.xpath(FlightAct)).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//md-option[contains(@value,'weightAndBalance')]")).click();
				FlightSearch.loadhandling(driver);
				if (Add_Remv.equalsIgnoreCase("yes")) {
					if (!Cbn_Cls.isEmpty() && Cbn_Cls.contains(";")) {
						String SptCbn[] = null;
						if (!Cbn_Cls.contains("-")) {
							Cbn_Cls = Cbn_Cls+"-";
						}
						SptCbn = Cbn_Cls.split("-");
						for (int cb = 0; cb < SptCbn.length; cb++) {
							if (SptCbn[cb].contains("Y")) {
								driver.findElement(By.xpath("//md-radio-button[@aria-label='Economic']/div/div[1]")).click();
							} else if (SptCbn[cb].contains("C")) {
								driver.findElement(By.xpath("//md-radio-button[@aria-label='Business']/div/div[1]")).click();
							}
							driver.findElement(By.xpath("//input[contains(@ng-model,'weightBalCntrl.model')]")).sendKeys(SptCbn[cb].substring(SptCbn[cb].indexOf(";")+1));
							driver.findElement(By.xpath("//button[@aria-label='Restriction']")).click();
							Thread.sleep(1000);
							FlightSearch.loadhandling(driver);
						}
					} else {
						driver.findElement(By.xpath("//md-radio-button[@aria-label='Economic']/div/div[1]")).click();
						driver.findElement(By.xpath("//input[contains(@ng-model,'weightBalCntrl.model')]")).sendKeys("4");
						driver.findElement(By.xpath("//button[@aria-label='Restriction']")).click();
						Thread.sleep(1000);
						FlightSearch.loadhandling(driver);
					}				
					if(driver.findElements(By.xpath("//div/span[contains(text(),'Weight balance restriction applied')]")).size()>0) {
						queryObjects.logStatus(driver, Status.PASS, "Weight balance restriction applied checking", "Successfully applied", null);
					}
					else
					{
						queryObjects.logStatus(driver, Status.FAIL, "Weight balance restriction applied checking", "not Successfully applied", null);
					}
				} else if (Add_Remv.toUpperCase().contains("REMOVE")) {
					for (int rr = 1; rr <= 2; rr++) {
						if (rr==1) {
							driver.findElement(By.xpath("//md-radio-button[@aria-label='Economic']/div/div[1]")).click();
						} else {
							driver.findElement(By.xpath("//md-radio-button[@aria-label='Business']/div/div[1]")).click();
						}
						driver.findElement(By.xpath("//button[@aria-label='Remove Restriction']")).click();
						Thread.sleep(1000);
						FlightSearch.loadhandling(driver);
						if(driver.findElements(By.xpath("//div[contains(text(),'Restrictions Removed')]")).size()>0) {
							queryObjects.logStatus(driver, Status.PASS, "Remove the Weight balance restriction", "Restriction is removed", null);
						}
						else {
							queryObjects.logStatus(driver, Status.INFO, "Remove the Weight balance restriction", "Restriction is not removed for the cabins", null);
						}					
					}
				}				
				driver.findElement(By.xpath("//div[contains(@ng-click,'dlgCtrl.closeDialog')]/i[@class='icon-close']")).click();
			} catch (InterruptedException e) {
				queryObjects.logStatus(driver, Status.FAIL, "Remove the Weight balance restriction", "Unable to remove Restriction is removed", null);
			}
			
		}
		public static void VoidBagEMD(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception
		{
			try {
				Boolean isSearch =false;
				isSearch = PNRsearch.Search_PNR(driver,queryObjects);
				int EMDticketcount = PNRsearch.EMDticketcount(driver,queryObjects);
				
				if (EMDticketcount>=1) {
					try {
						
						driver.findElement(By.xpath("(//i[contains(@ng-click,'Toggle')])[2]")).click();
						Thread.sleep(1000);
						driver.findElement(By.xpath("(//div[contains(@class,'md-container md-ink-ripple')])[3]")).click();
						Thread.sleep(1000);
						driver.findElement(By.xpath("//md-select")).click();
						Thread.sleep(3000);
						driver.findElement(By.xpath("//div[contains(text(),'Void EMD')]")).click();
						FlightSearch.loadhandling(driver);
						driver.findElement(By.xpath("(//i[contains(@ng-click,'Toggle')])[2]")).click();
						Thread.sleep(3000);
						if (driver.findElements(By.xpath("//div[contains(text(),'Disassociated')]")).size()>0) {
							queryObjects.logStatus(driver, Status.PASS, "Void EMD for BAg", "Void EMD for Bag successful",null);	
						}
						
					}catch (Exception e) {
						queryObjects.logStatus(driver, Status.FAIL, "Void EMD for BAg", "Void EMD for Bag did not happen", e);
					}
				}
					//i[contains(@ng-click,'Toggle')])[2]
				
				
			}catch(Exception e) {}
			
			}
}

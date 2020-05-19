package com.copa.RESscripts;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import FrameworkCode.BFrameworkQueryObjects;

import com.aventstack.extentreports.Status;
import com.copa.Util.WebService;
import com.copa.Util.url_path;


public class callwebservice {
	public Document webServiceStream;
	public void call(WebDriver driver,BFrameworkQueryObjects queryObjects) throws Exception {
		
	
		String RequestXMLPath="C:\\MSmart\\CopaScript\\Lib\\webservice\\Request.xml";
		String webServiceURL= "http://wsmm.airservices.svcs.entsvcs.net:51032/efd/v1.0/CurrencyDisplayService";
		String Action = "//http://wsmm.airservices.svcs.entsvcs.net:51032/efd/v1.0/CurrencyDisplayService";
		String User = "TRR1000:TRR1000";
		String UpdateNode = "Amount;Ticket;CurrencyOptions;Ticket";
		String UpdateAttribute = "Value;Date;FromCurr;Time";
		String Value = "'571.00;20190618;COP;1901";
		String ResTagName = "ans2:Result";
		String expectedVal = "success";
		String ResAttribute ="status";
		String CaptureTagName = "ans1:CurrencyDisplayLine";
		String CaptureAttribute = "ToAmountRounded";
		String CaptureByName= "Compare_Currency";
		String Keyword= "Display_Currency";
		String ErrEpec = "";
		
		
		String webServiceResponse="";
		
		Double idecval=3200.00;
		Calendar retDate = Calendar.getInstance();
		String currentDT = new SimpleDateFormat("yyyyMMdd").format(retDate.getTime());
		Calendar retTime = Calendar.getInstance();
		String currentTime = new SimpleDateFormat("HHmm").format(retTime.getTime());
		String cur="COP";
		
		String Compare_Value=idecval+";"+currentDT+";"+cur+";"+currentTime;
		final String SummaryReportFilePath ="C:\\MSmart\\Report\\Responses_"+currentDT+".txt";
		
		 webServiceResponse = WebService.WebService(webServiceURL, RequestXMLPath, Action, UpdateNode,UpdateAttribute, Compare_Value, User,SummaryReportFilePath);
		System.out.println(webServiceResponse);
		
		////
		//Validate the response
		  webServiceStream = WebService.StringToDocument(webServiceResponse);
	         			        
	     // normalize text representation
		  
		  try {
		     webServiceStream.getDocumentElement().normalize();
		     
		  
		    	
		    	 String[] aResTagName = ResTagName.split(";");
				  String[] aResAttribute = ResAttribute.split(";");
				  String[] aexpectedVal = expectedVal.split(";");
				  int Count =0;
				  
				 // System.out.println(aResTagName.length);
					 for (int k = 0; k < aResTagName.length; k++){
						 ResTagName = aResTagName[k];
						 ResAttribute = aResAttribute[k];
						 expectedVal = aexpectedVal[k]; 
		     
						 if (ResTagName.contains("(")) {
							 ResTagName= ResTagName.replaceAll("\\(", "");
							 ResTagName= ResTagName.replaceAll("\\)", "");
							 String sCount =ResTagName.substring(ResTagName.length()-1, ResTagName.length());
							    Count =Integer.parseInt(sCount)-1;
							    ResTagName = ResTagName.substring(0,ResTagName.length()-1);
						 }
						 
		     NodeList nList = webServiceStream.getElementsByTagName(ResTagName);
		     
			     Node nNode = nList.item(Count);
			     boolean blnexist =false;
			     boolean errexist =false;
			     String eActual = "";
			     String Actual = "";
		     if (nList.getLength()>0) {
	    
			     		if (ResAttribute.length()!=0){
					     	
					     	 Element eElement = (Element) nNode;
					     	// System.out.println(ResAttribute);
							        Actual =  eElement.getAttribute(ResAttribute);
							      blnexist = Actual.contains(expectedVal);
							      // Assert.assertTrue(blnexist);				       
					     }else{
					     				        	
					     	  Actual = nNode.getTextContent();
					     	  blnexist = Actual.contains(expectedVal);
							      // Assert.assertTrue(blnexist);				       				       
					     }
		     }else {
		    	 NodeList neList = webServiceStream.getElementsByTagName("Error");
			     Node neNode = neList.item(0); 
			     eActual = neNode.getTextContent();
			     errexist = true;
			     BFrameworkQueryObjects.logStatus(null, Status.INFO, "Error on Response received for "+Keyword ,"Error :"+eActual, null);
			     
		     }
		     
	     	
		     if (blnexist) {
		    	 BFrameworkQueryObjects.logStatus(null, Status.PASS, "Response validated "+Keyword ,"Node value for Tag :"+ResTagName+"-"+ResAttribute+" was :"+Actual, null); 
		     }else if(errexist & ErrEpec.equalsIgnoreCase("Y")){
		    	 BFrameworkQueryObjects.logStatus(null, Status.PASS, "Error Response validated "+Keyword ,"Error Node value:"+eActual, null);
		     }else
		     {
		    	 BFrameworkQueryObjects.logStatus(null, Status.FAIL, "Response not validated "+Keyword,"Node value for Tag :"+ResTagName+" was :"+Actual, null);
		     }
		}//for
		
	     
		  }catch (Exception e) {
			  BFrameworkQueryObjects.logStatus(null, Status.INFO, "Response could not be validated "+Keyword,"Node value for Tag :"+ResTagName+" was :"+expectedVal, null);
		  }
		  
		  
		  
	     //Capture Value from response:
		  String Captureval="";
		     if(CaptureTagName.length()!=0) {
		    	 
		    	 String[] aCapTagName = CaptureTagName.split(";");
		    	 String[] aCapAtt = CaptureAttribute.split(";");
		    	 String[] aCapName = CaptureByName.split(";");
		    	 
		    	 for (int l = 0; l < aCapTagName.length; l++){
		    		 CaptureTagName =  aCapTagName[l];
		    		 CaptureAttribute = aCapAtt[l];
		    		 CaptureByName = aCapName[l];
		    		int Count=0;
		    		
		    		 if (CaptureTagName.contains("(")) {
		    			 CaptureTagName= CaptureTagName.replaceAll("\\(", "");
		    			 CaptureTagName= CaptureTagName.replaceAll("\\)", "");
						 String sCount =CaptureTagName.substring(CaptureTagName.length()-1, CaptureTagName.length());
						    Count =Integer.parseInt(sCount)-1;
						    CaptureTagName = CaptureTagName.substring(0,CaptureTagName.length()-1);
					 }
		    				 
					     NodeList nList1 = webServiceStream.getElementsByTagName(CaptureTagName);
					     if (nList1.getLength()>1 && Count == 0) {
					    	 for (int k=0; k<nList1.getLength(); k++) {
					    		  Node nNode1 = nList1.item(k);
					    		  if (CaptureAttribute.length()!=0){
					  		      	
					 		     	 Element eElement = (Element) nNode1;
					 		     	 System.out.println(CaptureAttribute);
					 		     	 
					 				       String Actual =  eElement.getAttribute(CaptureAttribute);
					 				       if(Actual.length()>0) {
					 				       if(CaptureAttribute.equalsIgnoreCase("TicketNumber") && (Actual.length()==10)) {
					 				    	  Actual="230"+Actual;
					 				       Captureval = Captureval+Actual.replace(CaptureAttribute, "")+"~";		       
					 		     }else{     				        	
					 		    	Captureval = Captureval+Actual+"~";      
					 		     }

								   }else{     				        	
									   if (Captureval =="") {
						    				  System.out.println(Captureval);
						    			  }else {
							 		    	 Captureval = Captureval+nNode1.getTextContent()+"~"; 
						    			  }       
						 		     }
					    		  
					    	 }
					    	 }
					     }
					     else if(Count>0) {
					    	 
						     
						     Node nNode1 = nList1.item(Count);
						     
						     
						     if (CaptureAttribute.length()!=0){
						      	
						     	 Element eElement = (Element) nNode1;
						     	 System.out.println(CaptureAttribute);
								       String Actual =  eElement.getAttribute(CaptureAttribute);
								       if(CaptureAttribute.equalsIgnoreCase("TicketNumber"))
					 				    	  Actual="230"+Actual;
								       Captureval = Actual.replace(CaptureAttribute, "");		       
						     }else{     				        	
						    	 Captureval = nNode1.getTextContent();       
						     }
							  }
					     
					     
					     else {
					    	 
					     
					     Node nNode1 = nList1.item(0);
					     
					     
					     if (CaptureAttribute.length()!=0){
					      	
					     	 Element eElement = (Element) nNode1;
					     	 System.out.println(CaptureAttribute);
							       String Actual =  eElement.getAttribute(CaptureAttribute);
							       if(CaptureAttribute.equalsIgnoreCase("TicketNumber"))
				 				    	  Actual="230"+Actual;
							       Captureval = Actual.replace(CaptureAttribute, "");		       
					     }else{     				        	
					    	 Captureval = nNode1.getTextContent();       
					     }
						  }
					     
					     envwrite(CaptureByName, Captureval);
						     
					     BFrameworkQueryObjects.logStatus(null, Status.PASS, "Capture Value from response"," Capturing Tag :"+CaptureTagName+" was :"+Captureval, null); 
		     }
		    	 
		     }
	     webServiceStream =  null;
		
		/////
		
		
	}
	
	
	public static void envwrite(String Var, String value) throws IOException{

		String FilePath = url_path.pEnvExcelPath;
		try {
		FileInputStream input=new FileInputStream(FilePath);
		XSSFWorkbook wb=new XSSFWorkbook(input);
		XSSFSheet sh=wb.getSheet("Envvalue");
		int cells =sh.getRow(0).getLastCellNum();
		
		boolean oldVar = false;
		for (int i=0; i<=cells; i++) {
			String Val = sh.getRow(0).getCell(i).getStringCellValue();
			if (Val.contentEquals(Var) ){
				XSSFCell c1 =sh.getRow(1).createCell(i);
				c1.setCellValue(value);
				oldVar = true;
				break;
			}
		}
		
		
		/* int v=sh.getLastRowNum();
	        XSSFCell c=sh.getRow(v).getCell(cellNum);*/
				if (oldVar=false) {
		XSSFCell c=sh.getRow(0).createCell(cells);
		c.setCellValue(Var);
		XSSFCell c1 =sh.getRow(1).createCell(cells);
		c1.setCellValue(value);
				}

		input.close();
		FileOutputStream output= new FileOutputStream(FilePath);
		wb.write(output);
		output.close();
		}catch(Exception e){
			BFrameworkQueryObjects.logStatus(null, Status.PASS, "WebService Call not successful","Exception was thrown ", e); 
		}
		
	}

}

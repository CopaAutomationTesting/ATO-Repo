package com.copa.Util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;


import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.aventstack.extentreports.Status;

/*import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;*/
import org.apache.log4j.Logger;
 
public class WebService 
{  
  public static String WebService(String webServiceURL, 
    String  requestXMLPath, String SOAPAction, String UpdateNode, String Attribute, String NodeValue, String UserCredentials,String SummaryReportFilePath) 
  throws FileNotFoundException, Exception  
  {    
   /* PostMethod post = null;    
    HttpClient client = new HttpClient();    
     */
    try     
    {  //LOGGER
		Logger LOG = Logger.getLogger(WebService.class.getName());
    	
    
    	BufferedReader br = new BufferedReader(new FileReader(requestXMLPath));

    	String line;
    	StringBuilder sb = new StringBuilder();

    	while((line=br.readLine())!= null){
    	    sb.append(line.trim());
    	}
    	
    	// update the xml with the dynamic value
    		Document doc = StringToDocument(sb.toString());
    		
    		if (UpdateNode.length()!=0){
    			if (NodeValue.contains("+")) {
    				if(NodeValue.contains(";")) {
    					String [] arrNode = NodeValue.split(";");
    					NodeValue="";
    					for (int l=0;l<arrNode.length;l++) {
    					
    						if(arrNode[l].contains("~")) {
    	    					String [] arrNodeq = arrNode[l].split("~");
    	    					//NodeValue="";
    	    					for (int m=0;m<arrNodeq.length;m++) {
    	    					String getdays= arrNodeq[m].replace("+", "");
    	        				int getDays = Integer.parseInt(getdays);
    	        				Calendar calc = Calendar.getInstance();
    	        				calc.add(Calendar.DATE, +getDays);
    	        				 //System.out.println(calc.getTime());
    	        				String DateValue = new SimpleDateFormat("yyyy-MM-dd").format(calc.getTime());
    	        			
    	        				NodeValue = NodeValue+DateValue+"T00:00:00"+"~";
    	    					}
    						}
    						else if (arrNode[l].contains("+")) {
    	    						String getdays= arrNode[l].replace("+", "");
    	            				int getDays = Integer.parseInt(getdays);
    	            				Calendar calc = Calendar.getInstance();
    	            				calc.add(Calendar.DATE, +getDays);
    	            				 //System.out.println(calc.getTime());
    	            				String DateValue = new SimpleDateFormat("yyyy-MM-dd").format(calc.getTime());
    	            				if (l>0) {
    	            					NodeValue =NodeValue+";"+DateValue+"T00:00:00";
    	            				}else {
    	            				NodeValue = NodeValue+DateValue+"T00:00:00"+";";
    	            				}
    	    					}
    						else {
    							NodeValue = NodeValue+arrNode[l]+";";
    						}
    				
    					}
    				
    					}
    				else {
    					
    					
    					if(NodeValue.contains("~")) {
	    					String [] arrNodeq = NodeValue.split("~");
	    					NodeValue="";
	    					for (int m=0;m<arrNodeq.length;m++) {
	    					String getdays= arrNodeq[m].replace("+", "");
	        				int getDays = Integer.parseInt(getdays);
	        				Calendar calc = Calendar.getInstance();
	        				calc.add(Calendar.DATE, +getDays);
	        				 //System.out.println(calc.getTime());
	        				String DateValue = new SimpleDateFormat("yyyy-MM-dd").format(calc.getTime());
	        			
	        				NodeValue = NodeValue+DateValue+"T00:00:00"+"~";
	    					}
    					}
	    					else {
    					String getdays= NodeValue.replace("+", "");
    					NodeValue="";
        				int getDays = Integer.parseInt(getdays);
        				Calendar calc = Calendar.getInstance();
        				calc.add(Calendar.DATE, +getDays);
        				 //System.out.println(calc.getTime());
        				String DateValue = new SimpleDateFormat("yyyy-MM-dd").format(calc.getTime());
        				
        				
        				
        				NodeValue = NodeValue+DateValue+"T00:00:00";
        				
    				
    				}
    				}
    			}
	        updateNodeValue(doc,UpdateNode,Attribute,NodeValue);
    		}
    		
	        String newxml = DocumentToString(doc);
	        FileWriter fw = new FileWriter(SummaryReportFilePath,true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("*****************************************************************************");
			 bw.newLine();
			 bw.write("Request");
			 bw.write(newxml);
	        System.out.println(newxml);
	        bw.newLine();
	        bw.close();
			fw.close();
	        // Request content will be retrieved directly from the input stream      
	           
	        /*RequestEntity entity = new StringRequestEntity(newxml, "text/xml","UTF-8");
	      	
	      
      // Read the SOAP request from the file      
	     
      post = new PostMethod(webServiceURL);
      post.setRequestEntity(entity);    
      post.setRequestHeader("Accept","application/soap+xml,application/dime,multipart/related,text/*");
      if (SOAPAction.length()!=0){
    	 
      post.setRequestHeader("SOAPAction", SOAPAction);  
      }*/
      //post.setRequestHeader("Operation", "QuoteReissue");
      
      if (UserCredentials.length()!=0){
    	  String authString = UserCredentials;
    	  byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);
			
	        /*post.setRequestHeader("Authorization", "Basic " + authStringEnc);*/ 
	      //System.out.println("Base64 encoded auth string: " + authStringEnc);

			//URL url = new URL(webPage);
			//URLConnection urlConnection = url.openConnection();
			//urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
			//post.setRequestEntity("Authorization","Basic " + authStringEnc);
          
	        //String normalString = "TRR1000:TRR1000";
	      //  String encodedString = encoder.encodeToString(UserCredentials.getBytes(),Base64.NO_WRAP );
	    }

    
       
    
	      
      // System.out.println();
   //   BFrameworkQueryObjects.logStatus(null, Status.INFO, "Request updated","Updated Request", null);
      // Returns a number indicating the status of response 
    // RequestEntity bb = post.
     //System.out.println(bb.toString());
      /*int result = client.executeMethod(post);      
       //InputStream response = post.getResponseBodyAsStream();   
       String response = post.getResponseBodyAsString();*/
      // LOG.info(response.toString());
       
      //return response;
      return "";
     
    }     
     
    catch (IOException e) {  
    	
    	return null; 
      // Release current connection to the connection pool once you are done      
      //post.releaseConnection();     
    }  
  }
  
  public static void updateNodeValue(Document doc, String UpdateNode, String Attribute, String NodeValue) {

	  if (NodeValue.contains(";")){
		  String[] UpdateReq = UpdateNode.split(";");
		  String[] UpdateAtt = Attribute.split(";");
		  String[] UpdateVal = NodeValue.split(";");
		  
		  System.out.println(UpdateReq.length);
	 for (int k = 0; k < UpdateReq.length; k++){
		 UpdateNode = UpdateReq[k];
		 Attribute = UpdateAtt[k];
		 NodeValue = UpdateVal[k];
      NodeList list = doc.getElementsByTagName(UpdateNode);
          //list.item(0).appendChild());
          
      System.out.println(list.getLength());
      if (list.getLength()==1){
    	  if( list.item(0).hasAttributes() & (Attribute.length()!=0)){
    		  NamedNodeMap attr = list.item(0).getAttributes();
    			Node nodeAttr = attr.getNamedItem(Attribute);
    			nodeAttr.setTextContent(NodeValue);
    	  }
    	  
    	  
    	  else{
    	  list.item(0).setTextContent(NodeValue);
    	  }
      }else  if (list.getLength()>1 & NodeValue.contains("~")){
		  String[] UpdateSameVal = NodeValue.split("~");
		  for (int j =0; j<list.getLength(); j++) {
			  
			  if( list.item(j).hasAttributes() & (Attribute.length()!=0)){
	    		  NamedNodeMap attr = list.item(j).getAttributes();
	    			Node nodeAttr = attr.getNamedItem(Attribute);
	    			nodeAttr.setTextContent(UpdateSameVal[j]);
	    	  }
			  else {
				  list.item(j).setTextContent(UpdateSameVal[j]);
			  }
		  }
	 }
      else  if (list.getLength()>1){
  		for (int j =0; j<list.getLength(); j++) {
  			  if( list.item(j).hasAttributes() & (Attribute.length()!=0)){
  	    		  NamedNodeMap attr = list.item(j).getAttributes();
  	    			Node nodeAttr = attr.getNamedItem(Attribute);
  	    			nodeAttr.setTextContent(NodeValue);
  	    	  } 
  			  else {
  				list.item(j).setTextContent(NodeValue);
  			  }
  	      }
  	}
      else{
    	  if( list.item(0).hasAttributes() & (Attribute.length()!=0)){
    		  NamedNodeMap attr = list.item(0).getAttributes();
    			Node nodeAttr = attr.getNamedItem(Attribute);
    			nodeAttr.setTextContent(NodeValue);
    	  }
    	  else{
        	  list.item(0).setTextContent(NodeValue);
        	  }
    	  }
      
      
	 }
	 }
	 else{
		  NodeList list = doc.getElementsByTagName(UpdateNode);
          
	      System.out.println(list.getLength());
	     
	      
	        if (list.getLength()>1 & NodeValue.contains("~")){
    		  String[] UpdateSameVal = NodeValue.split("~");
    		  for (int j =0; j<list.getLength(); j++) {
    			  if( list.item(j).hasAttributes() & (Attribute.length()!=0)){
    	    		  NamedNodeMap attr = list.item(j).getAttributes();
    	    			Node nodeAttr = attr.getNamedItem(Attribute);
    	    			nodeAttr.setTextContent(UpdateSameVal[j]);
    	    	  }
    		  }
    	  }
	        else  if (list.getLength()>1){
	    		for (int j =0; j<list.getLength(); j++) {
	    			  if( list.item(j).hasAttributes() & (Attribute.length()!=0)){
	    	    		  NamedNodeMap attr = list.item(j).getAttributes();
	    	    			Node nodeAttr = attr.getNamedItem(Attribute);
	    	    			nodeAttr.setTextContent(NodeValue);
	    	    	  } 
	    	      }
	    	}
	        else if( list.item(0).hasAttributes() & (Attribute.length()!=0)){
    		  NamedNodeMap attr = list.item(0).getAttributes();
    			Node nodeAttr = attr.getNamedItem(Attribute);
    			nodeAttr.setTextContent(NodeValue);
    	  }
    	  else{
    	  list.item(0).setTextContent(NodeValue);
    	  }
	     
		  
	 
	 }//if (NodeValue.contains(";"))
      
          
  }

  public static String DocumentToString(Document doc) throws Exception {

      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      StringWriter writer = new StringWriter();
      transformer.transform(new DOMSource(doc), new StreamResult(writer));
      String output = writer.getBuffer().toString();
      return output;
  }

  public static Document StringToDocument(String strXml) throws Exception {

      Document doc = null;
      try {
          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
          DocumentBuilder builder = factory.newDocumentBuilder();
          StringReader strReader = new StringReader(strXml);
          InputSource is = new InputSource(strReader);
          doc = (Document) builder.parse(is);
      } catch (Exception e) {
          e.printStackTrace();
          throw e;
      }

      return doc;
  }
  
  

}
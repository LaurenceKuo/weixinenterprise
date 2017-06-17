package com.wistron.armstrong.wechat.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Path("/receive")
public class ReceiveMsgController {
	 final static Logger logger = Logger.getLogger(ReceiveMsgController.class); 
	 @POST
	 @Path("/news")
	 @Consumes({MediaType.TEXT_XML})
	 public String receiveNews(@Context HttpServletRequest request){ 
		 System.out.println("接收成功！");
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();     
		 DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(request.getInputStream());
			String docfile=getXmlString(doc);
			System.out.println(docfile);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;  

	 }
	 public static String getValueByTagName(Document doc, String tagName){  
		 if(doc == null ){  
		    System.out.println("doc is null");  
		 }
		 NodeList pl = doc.getElementsByTagName(tagName); 
   	     if(pl != null && pl.getLength() > 0){  
   	    	 return  pl.item(0).getTextContent();  
   	     }  
         return "";  
	    } 
	 public static String getXmlString(Document doc){  
		 TransformerFactory tf = TransformerFactory.newInstance();  
		 try {  
             Transformer t = tf.newTransformer();  
             t.setOutputProperty(OutputKeys.ENCODING,"UTF-8");//解决中文问题，试过用GBK不行  
             t.setOutputProperty(OutputKeys.METHOD, "html");    
             t.setOutputProperty(OutputKeys.VERSION, "4.0");    
             t.setOutputProperty(OutputKeys.INDENT, "no");    
             ByteArrayOutputStream bos = new ByteArrayOutputStream();  
             t.transform(new DOMSource(doc), new StreamResult(bos));  
             return bos.toString();  
			 } catch (TransformerConfigurationException e) {  
	            e.printStackTrace();  
			 } catch (TransformerException e) {  
	            e.printStackTrace();  
			 }  
			 return "";  
	     } 

}


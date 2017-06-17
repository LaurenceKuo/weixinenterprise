package com.wistron.armstrong.wechat.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import com.wistron.armstrong.wechat.entity.DepartmentEntity;
import com.wistron.armstrong.wechat.entity.TagEntity;
import com.wistron.armstrong.wechat.factory.ObjectFactory;
import com.wistron.armstrong.wechat.service.ISendMsgService;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/send")
public class SendMsgController {
	
	final static Logger logger = Logger.getLogger(SendMsgController.class); 
	
	 @POST
	 @Path("/news")
	 @Consumes({MediaType.APPLICATION_JSON})
	 public String sendNews(@Context HttpServletRequest request) throws Exception
	 { 
		 String toUser = "";
		 String toParty ="";
		 String toTag="";
		 String articlesList ="";
	     JSONObject reqJsonObject = null;
	     StringBuffer buffer = new StringBuffer();
         // 将返回的输入流转换成字符串
         InputStream inputStream = request.getInputStream();
         InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
         BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		 try{

         String str = null;
         while ((str = bufferedReader.readLine()) != null)
             buffer.append(str);
         reqJsonObject = new JSONObject(buffer.toString());
         
         if(!reqJsonObject.isNull("touser")){
        	 toUser = reqJsonObject.getString("touser"); 
         }
         if(!reqJsonObject.isNull("toparty")){
        	 toParty = reqJsonObject.getString("toparty"); 
        	 if(toParty.length()>0){
	        	 System.out.println("party:"+toParty);
	        	 DeptController controller=new DeptController();
	        	 controller.getDeptList();
	        	 HashMap<String, Integer> deptMap=new HashMap<String, Integer>();
	        	 for(DepartmentEntity dept:DepartmentEntity.depts){
	        		 deptMap.put(dept.getDepName(),dept.getDepId());
	        	 }
	        	 String[] list=toParty.split("\\|");
	        	 toParty="";
	        	 for(String p:list){
	        		 toParty+=deptMap.get(p)+"|";
	        	 }
	        	 System.out.println("TOPARTY:"+toParty);
        	 }		 
         }
         if(!reqJsonObject.isNull("totag")){
	         toTag = reqJsonObject.getString("totag"); 
        	 if(toTag.length()>0){
	        	 System.out.println("tag:"+toTag);
	        	 TagController controller=new TagController();
	        	 controller.getTagList();
	        	 HashMap<String, Integer> tagMap=new HashMap<String, Integer>();
	        	 for(TagEntity tag:TagEntity.tags){
	        		 tagMap.put(tag.getTagName(),tag.getTagId());
	        	 }
	        	 String[] list=toTag.split("\\|");
	        	 toTag="";
	        	 for(String t:list){
	        		 toTag+=tagMap.get(t)+"|";
	        	 }
	        	 System.out.println("toTag:"+toTag);
        	 }
         }
         if(toUser.isEmpty()&&toParty.isEmpty()&&toTag.isEmpty())
         {
        	 logger.warn("Send News Fail: User, Department and Tag are null!");
        	 return "Send News Fail: User, Department and Tag are null!";
         }
         if (!reqJsonObject.isNull("message"))
        	 articlesList = "[" + reqJsonObject.getJSONObject("message").toString() + "]";
         
         if(articlesList.isEmpty()){
        	 logger.warn("Send News Fail: Message is null!");
        	 return "Send News Fail: Message is null!";
         }
         
		 System.out.println("articlesList1-1 :" + articlesList);
		//Get CorpID and Secret to get Token
		 ISendMsgService service=(ISendMsgService)ObjectFactory.newInstance("SendMsgServiceImpl");
		 return service.SendNewsMsg(toUser, toParty, toTag,  articlesList);
		 }catch (Exception e)
		 {
			 logger.error("Send News Fail: " +e.getMessage());
			 return "Send News Fail: " +e.getMessage();
		 }
		 finally
		 {
			 bufferedReader.close();
	         inputStreamReader.close();
	         inputStream.close();
	         inputStream = null;
		 }
	 }
	 
	 public static void main(String[] args){
		 SendMsgController controller=new SendMsgController();
		 try {
		    String party="{ toparty :IT|開發組2|開發組1|SMT|智慧製造PCBA工程部,totag:'', message:{title:title,description:測試,picUrl:'', url:'' } }";
		    String tag="{ toparty :'',totag:'', message:{title:title,description:測試,picUrl:'', url:'' } }";
		   // controller.sendNews(tag);
		 } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	 }
	 
}

package com.wistron.armstrong.wechat.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import com.wistron.armstrong.wechat.entities.NewsEntities;
import com.wistron.armstrong.wechat.utilities.CommonUtil;
import com.wistron.armstrong.wechat.utilities.SendMessage;
import com.wistron.armstrong.wechat.utilities.WeixinUtil;

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

@Path("/News")
public class NewsController extends SendMessage {
	
	final static Logger logger = Logger.getLogger(NewsController.class); 
	
	 @POST
	 @Path("/SendNews")
	 @Consumes({MediaType.APPLICATION_JSON})
	 public String sendNews(@Context HttpServletRequest request) throws Exception
	 { 
		 String toUser = "";
		 String toParty ="";
		 String toTag="";
		 String articlesList ="";
	     JSONObject reqJsonObject = null;
	     JSONObject rulJsonObject = null;
	     StringBuffer buffer = new StringBuffer();
         // 将返回的输入流转换成字符串
         InputStream inputStream = request.getInputStream();
         InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
         BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
         
	     //Get CorpID and Secret to get Token
		  CommonUtil common = new CommonUtil();
		  Properties prop =common.getConfigProperties("CorpID.properties") ;
	        
	      String corpId = prop.getProperty("corpId").trim(); 
	      String secret = prop.getProperty("secret").trim();
	      String agentid = prop.getProperty("logic_agentid").trim();
		 try{

         String str = null;
         while ((str = bufferedReader.readLine()) != null)
             buffer.append(str);
         reqJsonObject = new JSONObject(buffer.toString());
         
         if(!reqJsonObject.isNull("touser"))
        	 toUser = reqJsonObject.getString("touser"); 
         if(!reqJsonObject.isNull("toparty"))
        	 toParty = reqJsonObject.getString("toparty"); 
         if(!reqJsonObject.isNull("totag"))
        	 toTag = reqJsonObject.getString("totag"); 
         
         if(toUser.isEmpty()&&toParty.isEmpty()&&toTag.isEmpty())
         {
        	 logger.warn("Send News Fail: User, Department and Tag are null!");
        	 return "Send News Fail: User, Department and Tag are null!";
         }
         if (!reqJsonObject.isNull("message"))
        	 articlesList = "[" + reqJsonObject.getJSONObject("message").toString() + "]";
         
         if(articlesList.isEmpty())
         {
        	 logger.warn("Send News Fail: Message is null!");
        	 return "Send News Fail: Message is null!";
         }
         
		 System.out.println("articlesList1-1 :" + articlesList);
		 String postData = SNewsMsg(toUser, toParty, toTag, agentid, articlesList);  
		 logger.info("Send News Data: "+postData);
		 // 调取凭证  
	       String access_token = WeixinUtil.getAccessToken(corpId, secret).getToken();  
	         	       
	      // int result = WeixinUtil.PostMessage(access_token, "POST", POST_URL, postData);  
	       rulJsonObject = WeixinUtil.PostToWeiXin(access_token, "POST", POST_URL, postData);  
	       // 打印结果  
	        if(0==rulJsonObject.getInt("errcode")){  
	        	logger.info("Send News Successfully!");
	           return "Send News Successfully!";  
	        }  
	        else {  
	        	logger.warn("Send News Fail: " + rulJsonObject.getString("errmsg"));
	        	return "Send News Fail: " + rulJsonObject.getString("errmsg");  
	        	}
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
	 
	 
	 public static JSONArray convertToJSONObject (List<NewsEntities>  datas)
		{
			try
			{
			JSONArray jArray = new JSONArray();
			for(NewsEntities data: datas)
			{
				JSONObject newsJSON = new JSONObject();
				newsJSON.put("title", data.getTitle());
				newsJSON.put("description", data.getDesription());
				newsJSON.put("picUrl", data.getPicUrl());
				newsJSON.put("url", data.getUrl());
			    jArray.put(newsJSON);
			}
			return jArray;
			} catch (JSONException jse) {
			    jse.printStackTrace();
			    return null;
			}
		}
}

package com.wistron.amstrong.wechat.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import com.wistron.amstrong.wechat.entities.NewsEntities;
import com.wistron.amstrong.wechat.utilities.CommonUtil;
import com.wistron.amstrong.wechat.utilities.SendMessage;
import com.wistron.amstrong.wechat.utilities.WeixinUtil;

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

	/*
	 public static void main(String[] args) 
	 { 
		 try{
		 ArrayList<NewsEntities> newslist= new ArrayList<NewsEntities> ();
		 NewsEntities newsEntity1 = new  NewsEntities(); 
		 //Create 1 news article
		 newsEntity1.setTilte("Baidu");
		 newsEntity1.setDesription("Test Link Baidu");
		 newsEntity1.setPicUrl("https://www.baidu.com/img/bd_logo1.png");
		 newsEntity1.setUrl("https://www.baidu.com/");
		 
		 NewsEntities newsEntity2 = new  NewsEntities();
		 //Create 1 news article
		 newsEntity2.setTilte("阿里巴巴");
		 newsEntity2.setDesription("Test Link 阿里巴巴");
		 newsEntity2.setPicUrl("");
		 newsEntity2.setUrl("http://www.alibabagroup.com/cn/global/homew");
		 
		 //combine news in List
		 newslist.add(newsEntity1);
		 newslist.add(newsEntity2);
		 
		 
		 
		// News list Change to Json formate  
		 JSONArray jsonAry = convertToJSONObject(newslist);
		 String articlesList = jsonAry.toString(); 
		 
		 String postData = SNewsMsg("@ALL", "1", "@ALL", "1000002", articlesList);  
		 
		 // 调取凭证  
	       String access_token = WeixinUtil.getAccessToken(corpId, secret).getToken();  
	       //String access_token = "LDBneyDqSEsIQCseo7V8hJxxgE6KwbDeHTFG-UdM0Zj92gLPxwWi11-AKJL9QlyvufFgb4AzhJ64jjn5mIF-hBYFgzTGU6GFTxl8WJeLZz07YoNvsn4pMzehTcqXFMdxD2J0RC3YVvNkWVmeTRocNl7vS2BdHAX7cq32qWGHrn25CIABq3xPd5JgWZ4MPKj8F-ojGZ7oGjnXEw4U6DjSi9CWmJ6AoZoOTP2_HjRMQTJrXhSy_Wl4rclMym5Dmq6hLoGXbxTuIUriVi3ngV-AVvwKb3hUHF0whchoTzmOg3U";  
	       
	       int result = WeixinUtil.PostMessage(access_token, "POST", POST_URL, postData);  
	       // 打印结果  
	        if(0==result){  
	            System.out.println("操作成功");  
	        }  
	        else {  
	            System.out.println("操作失败");  
		 
	        	}
		 }catch (Exception e)
		 {
			 e.printStackTrace();
		 }
	 }*/
	 
	 @POST
	 @Path("/SendNews")
	 @Consumes({MediaType.APPLICATION_JSON})
	 public String sendNews(@Context HttpServletRequest request) throws Exception
	 { 
	     JSONObject reqJsonObject = null;
	     JSONObject rulJsonObject = null;
	     StringBuffer buffer = new StringBuffer();
         // 将返回的输入流转换成字符串
         InputStream inputStream = request.getInputStream();
         InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
         BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
         
		  Properties prop = new Properties(); 
		  CommonUtil common = new CommonUtil();
		  String path =  common.getWebInfPath(NewsController.class.getResource("").getPath());
		  System.out.println("Path: " + path);
     	  FileInputStream fis = new FileInputStream(path+"CorpID.properties");    
	      prop.load(fis); 
	        
	        String corpId = prop.getProperty("corpId").trim(); 
	        String secret = prop.getProperty("secret").trim();
         
		 try{

         String str = null;
         while ((str = bufferedReader.readLine()) != null)
             buffer.append(str);
         reqJsonObject = new JSONObject(buffer.toString());
         //寫入News參數
		  //ArrayList<NewsEntities> newslist= new ArrayList<NewsEntities> ();
		  //NewsEntities newsEntity1 = new  NewsEntities();
		 
		 //Create 1 news article
		  //newsEntity1.setTitle(reqJsonObject.getString("TITLE"));
		  //newsEntity1.setDesription(reqJsonObject.getString("DESCRIPTION"));
		  //newsEntity1.setPicUrl(reqJsonObject.getString("PICURL"));
		  //newsEntity1.setUrl(reqJsonObject.getString("URL"));
		 
		 //Create 1 news article
		  //newsEntity1.setTitle("Baidu");
		  //newsEntity1.setDesription("Test Link Baidu");
		  //newsEntity1.setPicUrl("https://www.baidu.com/img/bd_logo1.png");
		  //newsEntity1.setUrl("https://www.baidu.com/");
		 
		 //combine news in List
		  //newslist.add(newsEntity1);	 
		 
		// News list Change to Json formate  
		 //JSONArray jsonAry = convertToJSONObject(newslist);
		 //String articlesList = jsonAry.toString(); 
		 
		 String articlesList = "[" + reqJsonObject.toString() + "]";
		 
		 System.out.println("articlesList1-1 :" + articlesList);
		 String postData = SNewsMsg("@ALL", "1", "@ALL", "1000002", articlesList);  
		 
		 // 调取凭证  
	       String access_token = WeixinUtil.getAccessToken(corpId, secret).getToken();  
	         	       
	      // int result = WeixinUtil.PostMessage(access_token, "POST", POST_URL, postData);  
	       rulJsonObject = WeixinUtil.PostToWeiXin(access_token, "POST", POST_URL, postData);  
	       // 打印结果  
	        if(0==rulJsonObject.getInt("errcode")){  
	           return "操作成功";  
	        }  
	        else {  
	        	return "操作失败 : " + rulJsonObject.getString("errmsg");  
		 
	        	}
		 }catch (Exception e)
		 {
			 return e.getMessage();
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

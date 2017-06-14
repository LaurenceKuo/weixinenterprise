package com.wistron.armstrong.wechat.controller;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.wistron.armstrong.wechat.entities.DepartmentEntities;
import com.wistron.armstrong.wechat.entities.TagEntities;
import com.wistron.armstrong.wechat.utilities.CommonUtil;
import com.wistron.armstrong.wechat.utilities.WeixinUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.log4j.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/Manager")
public class ManagerController {
	 
	final static Logger logger = Logger.getLogger(ManagerController.class);
	
	private String GetPartiesList_Url="https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN";
	private String GetTagsList_Url="https://qyapi.weixin.qq.com/cgi-bin/tag/list?access_token=ACCESS_TOKEN";
	 @POST
	 @Path("/GetPartiesList")
	 @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	 public Response getPartiesList() throws Exception, JSONException
	 { 
	     JSONObject rulJsonObject = null;
	     StringBuffer buffer = new StringBuffer();
        // 将返回的输入流转换成字符串
        
	     //Get CorpID and Secret to get Token
		  CommonUtil common = new CommonUtil();
		  Properties prop =common.getConfigProperties("CorpID.properties") ;
	        
	      String corpId = prop.getProperty("corpId").trim(); 
	      String secret = prop.getProperty("secret").trim();
          
		 try{

		 // 调取凭证  
	       String access_token = WeixinUtil.getAccessToken(corpId, secret).getToken();  
	       
	       GetPartiesList_Url.replace("ACCESS_TOKEN", access_token);
	       
	       rulJsonObject = WeixinUtil.PostToWeiXin(access_token, "GET", GetPartiesList_Url, "");  
	       // Get result from weixin 
	        if(0==rulJsonObject.getInt("errcode")){  
	        	ArrayList<DepartmentEntities> departmentlist =new ArrayList<DepartmentEntities>();
	        	
	        	//Get Department List from Weixin 
	        	JSONArray departments = rulJsonObject.getJSONArray("department");
	        	for(int i=0;i<departments.length();i++)
	        	{
	        		JSONObject departmentJson = departments.getJSONObject(i);
	        		DepartmentEntities department = new DepartmentEntities(toUtf8(departmentJson.getString("name")), departmentJson.getInt("id"));
	        		departmentlist.add(department);
	        	}
	        	
	        	//Wrap list to GenericEntity
	            //For not show error when return list, array by Jersey
	        	GenericEntity<List<DepartmentEntities>> list = new GenericEntity<List<DepartmentEntities>>(departmentlist) {};
	        	logger.info("Department: " + departments.toString());
	            return Response.ok(list).build();
	        }         
	        else {  
	        	logger.warn("Get Department Fail : " + rulJsonObject.getString("errmsg"));
	        	return null;
		 
	        	}
		 }catch (Exception e)
		 {
			 logger.error("Get Department Fail : " + e.getMessage());
			 return null;
		 }
		 finally
		 {
			 if(rulJsonObject!=null) rulJsonObject=null;
			 if(buffer!=null) buffer=null;
			 
		 }
	 }
	 
	 
	 @POST
	 @Path("/GetTagsList")
	 @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	 public Response getTagsList() throws Exception, JSONException
	 { 
	     JSONObject rulJsonObject = null;
	     StringBuffer buffer = new StringBuffer();
        // 将返回的输入流转换成字符串
        
	     //Get CorpID and Secret to get Token		  
		  CommonUtil common = new CommonUtil();
		  Properties prop =common.getConfigProperties("CorpID.properties") ;
	        
	      String corpId = prop.getProperty("corpId").trim(); 
	      String secret = prop.getProperty("secret").trim();
        
		 try{

		 // 调取凭证  
	       String access_token = WeixinUtil.getAccessToken(corpId, secret).getToken();  
	       
	       GetTagsList_Url.replace("ACCESS_TOKEN", access_token);
	       
	       rulJsonObject = WeixinUtil.PostToWeiXin(access_token, "GET", GetTagsList_Url, "");  
	       // Get result from weixin 
	        if(0==rulJsonObject.getInt("errcode")){  
	        	ArrayList<TagEntities> taglist =new ArrayList<TagEntities>();
	        	
	        	//Get Department List from Weixin 
	        	JSONArray tags = rulJsonObject.getJSONArray("taglist");
	        	for(int i=0;i<tags.length();i++)
	        	{
	        		JSONObject tagJson = tags.getJSONObject(i);
	        		TagEntities tag = new TagEntities(toUtf8(tagJson.getString("tagname")), tagJson.getInt("tagid"));
	        		taglist.add(tag);
	        	}
	        	
	        	//Wrap list to GenericEntity
	            //For not show error when return list, array by Jersey
	        	GenericEntity<List<TagEntities>> list = new GenericEntity<List<TagEntities>>(taglist) {};
	        	logger.info("Tag: " + tags.toString());
	            return Response.ok(list).build();
	        }         
	        else {  
	        	logger.warn("Get Tag Fail : " + rulJsonObject.getString("errmsg"));
	        	return null;
		 
	        	}
		 }catch (Exception e)
		 {
			 logger.error("Get Tag Fail : " + e.getMessage());
			 return null;
		 }
		 finally
		 {
			 if(rulJsonObject!=null) rulJsonObject=null;
			 if(buffer!=null) buffer=null;
			 if(prop!=null) prop=null;
			 
		 }
	 }
	 
	 public String toUtf8(String str) {
		 try {
			return new String(str.getBytes("UTF-8"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}

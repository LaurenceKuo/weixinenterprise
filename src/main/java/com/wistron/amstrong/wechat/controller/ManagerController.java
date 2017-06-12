package com.wistron.amstrong.wechat.controller;


import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import com.wistron.amstrong.wechat.utilities.CommonUtil;
import com.wistron.amstrong.wechat.utilities.WeixinUtil;
import com.wistron.amstrong.wechat.entities.DepartmentEntities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/Manager")
public class ManagerController {
	 
	private String getPartiesList_Url="https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN";

	 @POST
	 @Path("/GetPartiesList")
	 public Response getPartiesList() throws Exception
	 { 
		 System.out.println("Go In M First");
	     JSONObject rulJsonObject = null;
	     StringBuffer buffer = new StringBuffer();
        // 将返回的输入流转换成字符串
        
	     //Get CorpID and Secret to get Token
		  Properties prop = new Properties(); 
		  CommonUtil common = new CommonUtil();
		  String path =  common.getWebInfPath(NewsController.class.getResource("").getPath());
		  System.out.println("Path: " + path);
    	  FileInputStream fis = new FileInputStream(path+"CorpID.properties");    
	      prop.load(fis); 
	        
	        String corpId = prop.getProperty("corpId").trim(); 
	        String secret = prop.getProperty("secret").trim();
        
		 try{

		 
		 // 调取凭证  
	       String access_token = WeixinUtil.getAccessToken(corpId, secret).getToken();  
	       
	       getPartiesList_Url.replace("ACCESS_TOKEN", access_token);
	       
	      // int result = WeixinUtil.PostMessage(access_token, "POST", POST_URL, postData);  
	       rulJsonObject = WeixinUtil.PostToWeiXin(access_token, "GET", getPartiesList_Url, "");  
	       // 打印结果  
	        if(0==rulJsonObject.getInt("errcode")){  
	        	ArrayList<DepartmentEntities> departmentlist =new ArrayList<DepartmentEntities>();
	        	//partiesMap = new HashMap();
	        	//Get Department List from Weixin 
	        	JSONArray departments = rulJsonObject.getJSONArray("department");
	        	for(int i=0;i<departments.length();i++)
	        	{
	        		JSONObject departmentJson = departments.getJSONObject(i);
	        		DepartmentEntities department = new DepartmentEntities(toUtf8(departmentJson.getString("name")), departmentJson.getInt("id"));
	        		departmentlist.add(department);
	        	}
	        	
	        	//Wrap list to 
	        	GenericEntity<List<DepartmentEntities>> list = new GenericEntity<List<DepartmentEntities>>(departmentlist) {};
	        	System.out.println("Department: " + departments.toString() );
	            return Response.ok(list).build();
	        }         
	        else {  
	        	System.out.println ("操作失败 : " + rulJsonObject.getString("errmsg"));
	        	return null;
		 
	        	}
		 }catch (Exception e)
		 {
			 System.out.println ("操作失败 : " + e.getMessage());
			 return null;
		 }
		 finally
		 {
			 if(rulJsonObject!=null) rulJsonObject=null;
			 if(buffer!=null) buffer=null;
			 
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

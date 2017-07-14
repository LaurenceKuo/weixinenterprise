package com.wistron.armstrong.wechat.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import com.wistron.armstrong.wechat.entity.DepartmentEntity;
import com.wistron.armstrong.wechat.entity.UrlEntity;
import com.wistron.armstrong.wechat.service.IDeptService;
import com.wistron.armstrong.wechat.utils.CommonUtil;
import com.wistron.armstrong.wechat.utils.WeixinUtil;

public class DeptServiceImpl implements IDeptService {
	final static Logger logger = Logger.getLogger(DeptServiceImpl.class);
	@Override
	public Response getDeptList() {
		JSONObject rulJsonObject = null;
	    StringBuffer buffer = new StringBuffer();
        // 将返回的输入流转换成字符串
		CommonUtil common = new CommonUtil();  
		try{
			Properties prop =common.getConfigProperties("CorpID.properties") ;
			String corpId = prop.getProperty("corpId").trim(); 
		    String secret = prop.getProperty("secret").trim();
		    // 调取凭证  
		    String access_token = WeixinUtil.getAccessToken(corpId, secret).getToken();  
		    String url=new UrlEntity().getGetPartiesUrl();
		    url.replace("ACCESS_TOKEN", access_token);
	       
	        rulJsonObject = WeixinUtil.PostToWeiXin(access_token, "GET", url, "");  
	        // Get result from weixin 
	        if(0==rulJsonObject.getInt("errcode")){  
	        	ArrayList<DepartmentEntity> departmentlist =new ArrayList<DepartmentEntity>();
	        	
	        	//Get Department List from Weixin 
	        	JSONArray departments = rulJsonObject.getJSONArray("department");
	        	for(int i=0;i<departments.length();i++)
	        	{
	        		JSONObject departmentJson = departments.getJSONObject(i);
	        		DepartmentEntity department = new DepartmentEntity(toUtf8(departmentJson.getString("name")), departmentJson.getInt("id"));
	        		departmentlist.add(department);
	        	}
	        	DepartmentEntity.depts=departmentlist;
	        	//Wrap list to GenericEntity
	            //For not show error when return list, array by Jersey
	        	GenericEntity<List<DepartmentEntity>> list = new GenericEntity<List<DepartmentEntity>>(departmentlist) {};
	        	logger.info("Department: " + departments.toString());
	        	//return list;
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

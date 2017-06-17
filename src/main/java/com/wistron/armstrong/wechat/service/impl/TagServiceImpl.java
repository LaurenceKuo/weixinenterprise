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
import com.wistron.armstrong.wechat.entity.TagEntity;
import com.wistron.armstrong.wechat.entity.UrlEntity;
import com.wistron.armstrong.wechat.service.ITagService;
import com.wistron.armstrong.wechat.utils.CommonUtil;
import com.wistron.armstrong.wechat.utils.WeixinUtil;

public class TagServiceImpl implements ITagService {
	 final static Logger logger = Logger.getLogger(DeptServiceImpl.class);
	 public String toUtf8(String str) {
		try {
			return new String(str.getBytes("UTF-8"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public Response getTagList() {
		JSONObject rulJsonObject = null;
		StringBuffer buffer = null;
		Properties prop =null;
		try{
			buffer= new StringBuffer(); 
			CommonUtil common= new CommonUtil();
			prop=common.getConfigProperties("CorpID.properties") ;   
		    String corpId = prop.getProperty("corpId").trim(); 
		    String secret = prop.getProperty("secret").trim();
		    UrlEntity url=new UrlEntity();
			// 调取凭证
			String access_token = WeixinUtil.getAccessToken(corpId, secret).getToken();  
		    url.getGetTagsUrl().replace("ACCESS_TOKEN", access_token);
		    // Get result from weixin 
		    rulJsonObject = WeixinUtil.PostToWeiXin(access_token, "GET", url.getGetTagsUrl(), ""); 
	        if(0==rulJsonObject.getInt("errcode")){  
	        	ArrayList<TagEntity> taglist =new ArrayList<TagEntity>();
	        	//Get Department List from Weixin 
	        	JSONArray tags = rulJsonObject.getJSONArray("taglist");
	        	for(int i=0;i<tags.length();i++)
	        	{
	        		JSONObject tagJson = tags.getJSONObject(i);
	        		TagEntity tag = new TagEntity(toUtf8(tagJson.getString("tagname")), tagJson.getInt("tagid"));
	        		taglist.add(tag);
	        	}
	        	TagEntity.tags=taglist;
	        	//Wrap list to GenericEntity
	            //For not show error when return list, array by Jersey
	        	GenericEntity<List<TagEntity>> list = new GenericEntity<List<TagEntity>>(taglist) {};
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
}

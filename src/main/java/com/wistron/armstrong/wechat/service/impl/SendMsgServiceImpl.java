package com.wistron.armstrong.wechat.service.impl;

import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import com.wistron.armstrong.wechat.entity.UrlEntity;
import com.wistron.armstrong.wechat.service.ISendMsgService;
import com.wistron.armstrong.wechat.utils.CommonUtil;
import com.wistron.armstrong.wechat.utils.MessageForm;
import com.wistron.armstrong.wechat.utils.WeixinUtil;

public class SendMsgServiceImpl implements ISendMsgService {
	final static Logger logger = Logger.getLogger(SendMsgServiceImpl.class); 
	@Override
	public String SendTextMsg(String touser, String toparty, String totag, String agentid, String content) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String SendImageMsg(String touser, String toparty, String agentid, String media_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String SendVoiceMsg(String touser, String toparty, String totag, String agentid, String media_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String SendVideoMsg(String touser, String toparty, String totag, String agentid, String media_id,
			String title, String description) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String SendFileMsg(String touser, String toparty, String totag, String agentid, String media_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String SendNewsMsg(String toUser, String toParty, String toTag, String articlesList) throws JSONException, SQLException, Exception {
		 JSONObject rulJsonObject = null;
		 CommonUtil common = new CommonUtil();
		 Properties prop =common.getConfigProperties("CorpID.properties") ;	        
	     String corpId = prop.getProperty("corpId").trim(); 
	     String secret = prop.getProperty("secret").trim();
	     String agentid = prop.getProperty("logic_agentid").trim(); 
		 String postData = MessageForm.SNewsMsg(toUser, toParty, toTag, agentid, articlesList);  
		 logger.info("Send News Data: "+postData);
		 // 调取凭证  
	     String access_token = WeixinUtil.getAccessToken(corpId, secret).getToken();  
	     UrlEntity url=new UrlEntity();    	       
	     // int result = WeixinUtil.PostMessage(access_token, "POST", POST_URL, postData);  
	     rulJsonObject = WeixinUtil.PostToWeiXin(access_token, "POST", url.getSendMsgUrl(), postData);  
	      // 打印结果  
	     if(0==rulJsonObject.getInt("errcode")){  
	         logger.info("Send News Successfully!");
	         return "Send News Successfully!";  
	     }  
         else {  
        	 logger.warn("Send News Fail: " + rulJsonObject.getString("errmsg"));
        	 return "Send News Fail: " + rulJsonObject.getString("errmsg");  
         }
	}

	@Override
	public String SendMpNewsMsg(String touser, String toparty, String totag, String agentid, String articlesList) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

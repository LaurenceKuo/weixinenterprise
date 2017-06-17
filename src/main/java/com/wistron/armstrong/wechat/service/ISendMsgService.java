package com.wistron.armstrong.wechat.service;

import java.sql.SQLException;

import org.json.JSONException;

public interface ISendMsgService {
	    // text消息  
	    public String SendTextMsg(String touser,String toparty,String totag,String agentid,String content);
	      
	    // image消息  
	    public String SendImageMsg(String touser,String toparty,String agentid ,String media_id);
	      
	    // voice消息 
	    public String SendVoiceMsg(String touser,String toparty,String totag,String agentid ,String media_id); 
	      
	    // video消息  
	    public String SendVideoMsg(String touser,String toparty,String totag,String agentid,String media_id,String title,String description);
	      
	    // file消息 
	    public String SendFileMsg(String touser,String toparty,String totag,String agentid ,String media_id);
	      
	    // news消息    
	    public String SendNewsMsg(String touser,String toparty,String totag,String articlesList) throws JSONException, SQLException, Exception;
	    
	    // mpnews消息 
	    public String SendMpNewsMsg(String touser,String toparty,String totag,String agentid , String articlesList);
}

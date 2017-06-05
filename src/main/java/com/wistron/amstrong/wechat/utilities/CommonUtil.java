package com.wistron.amstrong.wechat.utilities;

public class CommonUtil {

	public String getWebInfPath(String originalPath)
	{
		String path = "";
		
	    for(String st : originalPath.split("/"))
	    {
	    	if(st.equals("WEB-INF"))
	    	{
	    		path += st+"\\" ;
	    		break;
	    	}else
	    	{
	    		path += st+"\\" ;
	    	}
	    }
	    
		return path;
	}
	
}

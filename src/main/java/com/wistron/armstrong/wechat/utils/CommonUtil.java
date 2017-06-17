package com.wistron.armstrong.wechat.utils;

import java.io.InputStream;
import java.util.Properties;

public class CommonUtil {

/*	public String getWebInfPath(String originalPath)
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
	*/
	public Properties getConfigProperties(String fileName) throws Exception
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(fileName);
		Properties prop = new Properties(); 
	    prop.load(input); 
		return prop;
	}
	
}

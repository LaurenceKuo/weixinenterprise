package com.wistron.armstrong.wechat.utilities;

import java.io.FileInputStream;
import java.util.Properties;

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
	
	public Properties getConfigProperties(String fileName) throws Exception
	{
		Properties prop = new Properties(); 
		String path =  getWebInfPath(CommonUtil.class.getResource("").getPath());
		System.out.println("Path: " + path);
   	    FileInputStream fis = new FileInputStream(path+fileName);    
	    prop.load(fis); 
		return prop;
	}
	
}

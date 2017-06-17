package com.wistron.armstrong.wechat.factory;

import java.io.IOException;
import java.util.Properties;
import com.wistron.armstrong.wechat.utils.CommonUtil;

public class ObjectFactory {
	private static Properties p;
	
	static{
		CommonUtil common=new CommonUtil();
		try {
			p =common.getConfigProperties("config.properties") ;
			System.out.println("工厂静态块被加载!!!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Object newInstance(String key) throws Exception {
		String className=p.getProperty(key);		
		return Class.forName(className).newInstance();
	}
}

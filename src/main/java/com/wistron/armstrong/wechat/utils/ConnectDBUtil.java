package com.wistron.armstrong.wechat.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectDBUtil {
	 
	 public static void main(String[] args) throws Exception { 
		   
	        try { 
	   
			    //Get DB Configure
				CommonUtil common = new CommonUtil();
				Properties prop =common.getConfigProperties("DBInfo.properties") ;
		        
		        String driver = prop.getProperty("driver").trim(); 
		        String url = prop.getProperty("url").trim(); 
		        String user = prop.getProperty("user").trim(); 
		        String password = prop.getProperty("password").trim(); 
		        
	            Class.forName(driver); 
	            Connection conn = 
	               DriverManager.getConnection(url, 
	                                  user, password);
	 
	            if(conn != null && !conn.isClosed()) {
	                System.out.println("資料庫連線測試成功！"); 
	                conn.close();
	            }
	            
	        } 
	        catch(ClassNotFoundException e) { 
	            System.out.println("找不到驅動程式類別"); 
	            e.printStackTrace(); 
	        } 
	        catch(SQLException e) { 
	            e.printStackTrace(); 
	        } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    } 
	 
	 public Connection createConnection() throws Exception, SQLException {
		  Connection connection;
		  
		   //Get DB Configure
			CommonUtil common = new CommonUtil();
			Properties prop =common.getConfigProperties("DBInfo.properties") ;
	        
	        String driver = prop.getProperty("driver").trim(); 
	        String url = prop.getProperty("url").trim(); 
	        String user = prop.getProperty("user").trim(); 
	        String password = prop.getProperty("password").trim(); 
	        
	            Class.forName(driver); 
	            connection = 
	               DriverManager.getConnection(url, 
	                                  user, password);
	 
	            if(connection != null && !connection.isClosed()) {
	                //System.out.println("資料庫連線測試成功！"); 
	            }
	             
	        return connection;
	 
	        }
	 

}

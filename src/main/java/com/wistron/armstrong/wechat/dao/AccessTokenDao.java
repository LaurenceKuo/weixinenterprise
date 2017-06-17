package com.wistron.armstrong.wechat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wistron.armstrong.wechat.entity.AccessTokenEntity;

public class AccessTokenDao {
	
	public AccessTokenEntity getToken(Connection con, String corpid, String secretid )throws SQLException
	{
		AccessTokenEntity accessToken = null;
	     String sql = "SELECT TOKEN, EXPIRESIN FROM AMBTOKEN WHERE SYSDATE()- CREATETIME<EXPIRESIN AND CORPID = ? AND SECRETID = ?";
	     PreparedStatement stat = null;
	        ResultSet rs = null;
	        
	        try{
	            stat = con.prepareStatement(sql);
	            stat.setString(1, corpid);
	            stat.setString(2, secretid);
	         	rs = stat.executeQuery();
	         	
	         	while(rs.next())
	         	{
	         		accessToken = new AccessTokenEntity(rs.getString("TOKEN"),rs.getInt("EXPIRESIN"));
	         	}
	        }
	        finally
	        {
	   	         stat = null;
		         rs = null;
	        }
	        
	        return accessToken;
	}
	
	public void updateToken(Connection con, String corpid, String secretid, String token, int expiresIn )throws SQLException
	{
	      PreparedStatement stat = null;
	        
	        try{
	            stat = con.prepareStatement("DELETE FROM AMBTOKEN WHERE CORPID = ? AND SECRETID= ?");
	            stat.setString(1, corpid);
	            stat.setString(2, secretid);
	         	stat.executeUpdate();
	         	
	         	stat.close();
	         	stat.clearParameters();
	         	
	         	stat = con.prepareStatement("INSERT INTO AMBTOKEN (CORPID, SECRETID, TOKEN, EXPIRESIN, CREATETIME) VALUE (?,?,?,?,SYSDATE())");
	         	stat.setString(1, corpid);
	            stat.setString(2, secretid);
	            stat.setString(3, token);
	            stat.setInt(4, expiresIn);
	         	stat.executeUpdate();
	         	
	        }
	        finally
	        {
	        	 stat.close();
	   	         stat = null;
	        }
	}

}

package com.wistron.amstrong.wechat.entities;

public class AccessToken {
	
	private String Token;
	private Integer ExpiresIn;
    
	public String getToken()
	{
		return this.Token;
	}
	
	public void setToken(String Token)
	{
		this.Token=Token;
	}
	public Integer getExpiresIn()
	{
		return this.ExpiresIn;
	}
	
	public void setExpiresIn(Integer ExpiresIn)
	{
		this.ExpiresIn=ExpiresIn;
	}
	
	public AccessToken (String Token, Integer ExpiresIn)
	{
		this.Token=Token;
		this.ExpiresIn=ExpiresIn;
	}
	
	public AccessToken ()
	{
	}
}

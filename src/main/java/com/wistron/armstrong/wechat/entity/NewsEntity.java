package com.wistron.armstrong.wechat.entity;

public class NewsEntity {

	public String title;
	public String description;
	public String picUrl;
	public String url;
	
	public void setTitle(String title)
	{
	  this.title = title;
	}
	public void setDesription(String description)
	{
	  this.description = description;
	}
	public void setPicUrl(String picUrl)
	{
	  this.picUrl = picUrl;
	}
	public void setUrl(String url)
	{
	  this.url = url;
	}
	public String getTitle()
	{
		return this.title ;
	}
	public String getDesription()
	{
		return this.description ;
	}
	public String getPicUrl()
	{
		return this.picUrl ;
	}
	public String getUrl()
	{
		return this.url;
	}
	
	
}

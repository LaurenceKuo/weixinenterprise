package com.wistron.amstrong.wechat.entities;

public class TagEntities {
	
	private String TagName;
	private Integer TagId;
	
	public String getTagName()
	{
		return this.TagName;
	}
	
	public void setTagName(String TagName)
	{
		this.TagName=TagName;
	}
	public Integer getTagId()
	{
		return this.TagId;
	}
	
	public void setTagId(Integer TagId)
	{
		this.TagId=TagId;
	}
	
	public TagEntities (String TagName, Integer TagId)
	{
		this.TagName=TagName;
		this.TagId=TagId;
	}
}

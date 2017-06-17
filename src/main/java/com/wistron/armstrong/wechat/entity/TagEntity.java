package com.wistron.armstrong.wechat.entity;

import java.util.ArrayList;

public class TagEntity {
	
	private String TagName;
	private Integer TagId;
	public static ArrayList<TagEntity> tags=null;
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
	
	public TagEntity (String TagName, Integer TagId)
	{
		this.TagName=TagName;
		this.TagId=TagId;
	}
}

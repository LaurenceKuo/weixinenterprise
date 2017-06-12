package com.wistron.amstrong.wechat.entities;

public class DepartmentEntities {

	private String Name;
	private Integer Id;
	
	public String getName()
	{
		return this.Name;
	}
	
	public void setName(String Name)
	{
		this.Name=Name;
	}
	public Integer getId()
	{
		return this.Id;
	}
	
	public void setId(Integer Id)
	{
		this.Id=Id;
	}
	
	public DepartmentEntities (String Name, Integer Id)
	{
		this.Name=Name;
		this.Id=Id;
	}
}

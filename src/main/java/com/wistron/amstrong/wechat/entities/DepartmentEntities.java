package com.wistron.amstrong.wechat.entities;

public class DepartmentEntities {

	private String DepName;
	private Integer DepId;
	
	public String getDepName()
	{
		return this.DepName;
	}
	
	public void setDepName(String DepName)
	{
		this.DepName=DepName;
	}
	public Integer getDepId()
	{
		return this.DepId;
	}
	
	public void setDepId(Integer DepId)
	{
		this.DepId=DepId;
	}
	
	public DepartmentEntities (String DepName, Integer DepId)
	{
		this.DepName=DepName;
		this.DepId=DepId;
	}
	
}

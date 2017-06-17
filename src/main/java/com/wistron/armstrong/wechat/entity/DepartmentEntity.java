package com.wistron.armstrong.wechat.entity;

import java.util.ArrayList;
import java.util.List;

public class DepartmentEntity {

	private String DepName;
	private Integer DepId;
	public static ArrayList<DepartmentEntity> depts=null;
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
	
	public DepartmentEntity (String DepName, Integer DepId)
	{
		this.DepName=DepName;
		this.DepId=DepId;
	}
	
}

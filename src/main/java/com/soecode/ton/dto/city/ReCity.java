package com.soecode.ton.dto.city;

import java.util.List;

public class ReCity {
	private String id;
	private String name;
	private String parentId;
	private List<ReCity> subCitys;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ReCity> getSubCitys() {
		return subCitys;
	}
	public void setSubCitys(List<ReCity> subCitys) {
		this.subCitys = subCitys;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
}
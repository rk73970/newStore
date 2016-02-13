package com.example.pojo;

import java.util.Date;

public class Store {
	
	private String storeID;
	private String postCode;
	private String city;
	private String address;
	private Date opendate;
	private long opendays;
	public long getOpendays() {
		return opendays;
	}
	public void setOpendays(long opendays) {
		this.opendays = opendays;
	}
	public String getStoreID() {
		return storeID;
	}
	public void setStoreID(String storeID) {
		this.storeID = storeID;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getOpendate() {
		return opendate;
	}
	public void setOpendate(Date opendate) {
		this.opendate = opendate;
	}
	

}

package com.example.pojo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Store {
	
	private String storeID;
	private String postCode;
	private String city;
	private String address;
	private Date opendate;
	private long opendays;
	
	public Store(){}
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
	
	@Override
	public boolean equals(Object o){
	    if(o == null){
	    	return false;
	    }
	    if(!(o instanceof Store))
    	{
	    	return false;
    	}

	    Store other = (Store) o;
	    if(this.storeID != other.storeID){
	    	return false;
	    }
	    if(! this.postCode.equals(other.postCode)){
	    	return false;
	    }
	    if(!(this.opendays == other.opendays)){
	    	return false;
	    }

	    return true;
	  }
	
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 7 * hash + this.storeID.hashCode();
		hash = 7 * hash + this.postCode.hashCode();
		return hash;
	}

}

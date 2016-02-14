package com.example.dao;

import java.util.List;

import com.example.pojo.Store;

public interface StoreDetailsDao {

	public Store retrieveStoreById(String id);
	
	public List<Store> retrieveStoreBySort(String soryby);
	
	public List<Store> retrieveAllStores();
}

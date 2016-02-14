/**
 * 
 */
package com.example.service;

import java.util.List;

import com.example.pojo.Store;

/**
 * @author max
 *
 */
public interface StoreService {
	
	public Store getStoreByStoreId(String id);
	
	public List<Store> getStoresByOrder(String sortby);
	
	public List<Store> getAllStores();
	
}

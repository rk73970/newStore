/**
 * 
 */
package com.example.service.impl;

import java.util.List;

import com.example.dao.impl.StoreDetailsDaoImpl;
import com.example.pojo.Store;
import com.example.service.StoreService;

/**
 * @author max
 *
 */
public class StoreServiceImpl implements StoreService{
	
	private static final StoreServiceImpl INSTANCE = new StoreServiceImpl();
	StoreDetailsDaoImpl storeDetailsDao = new StoreDetailsDaoImpl();
	
	public Store getStoreByStoreId(String id) {
		System.out.println("storeDetailsDao  : "+storeDetailsDao);
		return storeDetailsDao.retrieveStoreById(id);
	}
	
	public static StoreServiceImpl getStoreService(){
		return INSTANCE;
	}

	@Override
	public List<Store> getStoresByOrder(String sortby) {
		return storeDetailsDao.retrieveStoreBySort(sortby);
	}

	@Override
	public List<Store> getAllStores() {
		return storeDetailsDao.retrieveAllStores();
	}

}

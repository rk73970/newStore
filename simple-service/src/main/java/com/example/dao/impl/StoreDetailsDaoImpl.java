package com.example.dao.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.cache.StoreCache;
import com.example.dao.StoreDetailsDao;
import com.example.pojo.Store;

public class StoreDetailsDaoImpl implements StoreDetailsDao{
	
	@Override
	public Store retrieveStoreById(String id) {
		Store store = null;
    	System.out.println("id :"+id);
		try {
			List<Store> storeList = StoreCache.getStoresFromCache();
			for(Store storeentry : storeList)
			{
				if(storeentry.getStoreID().equals(id))
				{
					System.out.println("id present");
					store = storeentry;
				}
			}
		}
		catch(Exception ex){
			System.out.println("Exception "+ex);
		}
		System.out.println("store object : "+store);
		return store;
	}
	
	

	@Override
	public List<Store> retrieveStoreBySort(String sortby) {
    	List<Store> storeList = StoreCache.getStoresFromCache();
    	try{
    		//storeList = getCache();
			if(storeList.size()>0)
			{
				//Sort by city name
				if(sortby.equals("byname"))
				{
					System.out.println("sortby : "+sortby);
				Collections.sort(storeList, new Comparator<Store>(){@Override
					        public int compare(Store s1, Store s2) {
					            return s1.getCity().compareTo(s2.getCity());
					        }});
				}
				//Sort by open date
				else if(sortby.equals("byopendate")){
					System.out.println("sortby >> : "+sortby);
					Collections.sort(storeList, new Comparator<Store>(){@Override
				        public int compare(Store s1, Store s2) {
				            return s1.getOpendate().compareTo(s2.getOpendate());
				        }});
				}
			}
    	}catch(Exception ex)
    	{
    		System.out.println("Exception ---"+ex);
    	}
		return storeList;
	}

	@Override
	public List<Store> retrieveAllStores() {
		return StoreCache.getStoresFromCache();
		//return getCache();
	}
}

package com.example.cache;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.csvreader.CsvReader;
import com.example.pojo.Store;

public class StoreCache {
	
	private static String storeURLString = "https://raw.githubusercontent.com/pearsonpmcuk/codingchallenge/master/stores.csv";
	private static CacheManager cacheManager = CacheManager.getInstance();
	
	private static Cache getStoreCache()
    {
		//CacheManager cacheManager = CacheManager.getInstance();
		if(cacheManager.getCache("mystorecache") == null){
		int oneHr = 1 * 60 * 60;
		Cache memoryOnlyCache = new Cache("mystorecache", 10000, false, false, oneHr, oneHr);
		cacheManager.addCache(memoryOnlyCache);
		}
		return cacheManager.getCache("mystorecache");
    }
	
	public static void shutDownCache()
    {
		cacheManager.shutdown();
    }
	private static Cache getCache()
    {
    	URL storeConnURL = null;
    	CsvReader csvReader = null;
    	Cache memoryOnlyCache = getStoreCache();
		try {
			if(memoryOnlyCache.getKeys().size()==0)
			{
				System.out.println("Cache size 0");
				storeConnURL = new URL(storeURLString);
				BufferedReader br = new BufferedReader(new InputStreamReader(storeConnURL.openStream()));
				csvReader  = new CsvReader(br);
				csvReader.readHeaders();
				while (csvReader.readRecord())
				{
					String storeId = csvReader.get("Store Id");
						//System.out.println("storeId  : ------------"+storeId);
						//System.out.println("storeId :>>>"+storeId);
						String city = csvReader.get("City");
						String postCode = csvReader.get("Post Code");
						String address = csvReader.get("Address");
						String openedDate = csvReader.get("Opened Date");
						
						Store store = new Store();
						store.setStoreID(storeId);
						store.setCity(city!=null?city:"");
						store.setPostCode(postCode!=null?postCode:"");
						store.setAddress(address!=null?address:"");
						if(openedDate!=null)
						{ 
						store.setOpendate(convertStringToDate(openedDate));
						store.setOpendays(calculateStoreOpenDays(openedDate));
						}
						memoryOnlyCache.put(new Element(storeId,store));
				}
				csvReader.close();
			}
				return memoryOnlyCache;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception while reading csv :---------- "+e);
			csvReader.close();
		}
		return memoryOnlyCache;
    }
	
	public static List<Store>  getStoresFromCache(){
		Cache cache = getCache();
		List<Store> storeList = new LinkedList<Store>();
		if(cache!=null)
		{
			for (Object key: cache.getKeys()) {
			    Element element = cache.get(key);
			   // System.out.println( "------------------------  :"   +((Store)element.getObjectValue()).getStoreID());
			    storeList.add((Store)element.getObjectValue());
			  }
		}
		return storeList;
		
	}
	
	public static long calculateStoreOpenDays(String openDate )
    {
        Date date = null;
        long diff = 0;
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
        try{
            date = df.parse(openDate);
            diff = new Date().getTime() - date.getTime();
            //System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        }
        catch ( Exception ex ){
            System.out.println(ex);
        }
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
    
    public static Date convertStringToDate(String openDate )
    {
        Date date = null;
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
        try{
            date = df.parse(openDate);
        }
        catch ( Exception ex ){
            System.out.println(ex);
        }
        return date;
    }
}

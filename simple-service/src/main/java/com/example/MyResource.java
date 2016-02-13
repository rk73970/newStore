package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.csvreader.CsvReader;
import com.example.pojo.Store;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

	private static String storeURLString = "https://raw.githubusercontent.com/pearsonpmcuk/codingchallenge/master/stores.csv";
	private CsvReader csvReader =null;
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    public String getIt() {
//    	getStoreById();
//        return "Got it!";
//    }
    
    @GET
    @Path("/storeid/{id}")
    @Produces (MediaType.APPLICATION_JSON)
    public Store getStoreById(@PathParam("id") String id){
    	String storeId = null;
    	Store store = null;
    	CsvReader csvReader = null;
    	System.out.println("id :"+id);
		try {
//			storeConnURL = new URL(storeURLString);
//			BufferedReader br = new BufferedReader(new InputStreamReader(storeConnURL.openStream()));
			
			csvReader = getReader(storeURLString);
			csvReader.readHeaders();
			while (csvReader.readRecord())
			{
				storeId = csvReader.get("Store Id");
				if(storeId.equals(id))
				{
					System.out.println("storeId :>>>"+storeId);
					System.out.println("id :>>>"+id);
					String city = csvReader.get("City");
					System.out.println("Store "+storeId +" is in City "+city);
					String postCode = csvReader.get("Post Code");
					String address = csvReader.get("Address");
					String openedDate = csvReader.get("Opened Date");
					
					store = new Store();
					store.setStoreID(storeId);
					store.setCity(city!=null?city:"");
					store.setPostCode(postCode!=null?postCode:"");
					store.setAddress(address!=null?address:"");
					if(openedDate!=null)
					{ 
					store.setOpendate(convertStringToDate(openedDate));
					store.setOpendays(calculateStoreOpenDays(openedDate));
					}
					//System.out.println(">>>>>>>>>>>>  "+calculateStoreOpenDays(openedDate));
				}
			}
			//csvReader.close();
			return store;
		}
		 catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception while reading csv :---------- "+e);
			//csvReader.close();
		}
		return store;
    }
    
    @GET
    @Path("/city/{sortby}")
    @Produces (MediaType.APPLICATION_JSON)
    public List<Store> getStoresByOrder(@PathParam("sortby") String sortby){
    	String storeId = null;
    	Store store = null;
    	CsvReader csvReader = null;
    	List<Store> storeList = new ArrayList<Store>();
		try {
			csvReader = getReader(storeURLString);
			csvReader.readHeaders();
			while (csvReader.readRecord())
			{
				storeId = csvReader.get("Store Id");
					//System.out.println("storeId :>>>"+storeId);
					String city = csvReader.get("City");
					//System.out.println("Store "+storeId +" is in City "+city);
					String postCode = csvReader.get("Post Code");
					String address = csvReader.get("Address");
					String openedDate = csvReader.get("Opened Date");
					
					store = new Store();
					store.setStoreID(storeId);
					store.setCity(city!=null?city:"");
					store.setPostCode(postCode!=null?postCode:"");
					store.setAddress(address!=null?address:"");
					if(openedDate!=null)
					{ 
					store.setOpendate(convertStringToDate(openedDate));
					store.setOpendays(calculateStoreOpenDays(openedDate));
					}
					storeList.add(store);
					//System.out.println(">>>>>>>>>>>>  "+calculateStoreOpenDays(openedDate));
			}
			if(storeList.size()>0)
			{
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
			//csvReader.close();
			System.out.println("storeList : "+storeList.size());
			return storeList;
		}
		 catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception while reading csv :---------- "+e);
			//csvReader.close();
		}
		return storeList;
    }
    public static class OrderByStoreId implements Comparator<Store> {

        @Override
        public int compare(Store s1, Store s2) {
            return s1.getStoreID().compareTo(s2.getStoreID());
        }
    }

    public long calculateStoreOpenDays(String openDate )
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
    
    public Date convertStringToDate(String openDate )
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
    
    public CsvReader getReader(String openDate )
    {
    	URL storeConnURL = null;
		try {
			if(csvReader==null)
			{
			storeConnURL = new URL(storeURLString);
			BufferedReader br = new BufferedReader(new InputStreamReader(storeConnURL.openStream()));
			csvReader = new CsvReader(br);
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception while reading csv :---------- "+e);
			csvReader.close();
		}
		return csvReader;
    }
}

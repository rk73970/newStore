package com.example;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.pojo.Store;
import com.example.service.impl.StoreServiceImpl;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("mystore")
public class MyStoreController {

    @GET
    @Path("/storeid/{id}")
    @Produces (MediaType.APPLICATION_JSON)
    public Response getStoreById(@PathParam("id") String id){
    	
    	if(id.trim() =="") {
    		return Response.status(Response.Status.BAD_REQUEST).entity("No store id in request").build();
    	}
    	StoreServiceImpl storeservice = StoreServiceImpl.getStoreService();
    	Store store =storeservice.getStoreByStoreId(id);
    	if(store==null)
    	{
    		return Response.status(Response.Status.NOT_FOUND).entity("Store with ID "+ id +" not found").build();
    	}
    	GenericEntity<Store> entity = new GenericEntity<Store>(store) {};
		    return Response.ok(entity).build();
    }
    
    @GET
    @Path("/city/{sortby}")
    @Produces (MediaType.APPLICATION_JSON)
    public Response getStoresByOrder(@PathParam("sortby") String sortby){
    	if(sortby.trim() =="") {
    		return Response.status(Response.Status.BAD_REQUEST).entity("No sort option found in request. Please provide sort option 'byname' or 'byname'.").build();
    	}
    	StoreServiceImpl storeservice = StoreServiceImpl.getStoreService();
    	GenericEntity<List<Store>> entity = new GenericEntity<List<Store>>(storeservice.getStoresByOrder(sortby)) {};
		    return Response.ok(entity).build();
    	
	}
    @GET
    @Path("/stores")
    @Produces (MediaType.APPLICATION_JSON)
    public Response getAllStores(){
    	StoreServiceImpl storeservice = StoreServiceImpl.getStoreService();
    	GenericEntity<List<Store>> entity = new GenericEntity<List<Store>>(storeservice.getAllStores()) {};
		    return Response.ok(entity).build();
    	
	}
    
    @POST
    @Path("/createstore")
    @Produces (MediaType.APPLICATION_JSON)
    public Response createNewStore(){
    	return Response.status(Response.Status.CREATED).entity("Congratulations. New store created.").build();
    	
	}
}



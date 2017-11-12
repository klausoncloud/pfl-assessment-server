package org.klausoncloud.pflservice;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/")
public class PFLService {
	
	static final String QUERY_PARM_PRODUCTID = "id";
	static final String BLANK_VALUE = "";
	
	@GET
	@Path("products")
	@Produces( "application/json;charset=utf-8" )
	public Response getProducts(@DefaultValue(BLANK_VALUE) @QueryParam(QUERY_PARM_PRODUCTID) String productID) {
		
		PFLGateWay pflGateWay = new PFLGateWay();
		Response response;
		
		if (productID.equals(BLANK_VALUE)) {
			response = pflGateWay.getAllProducts();			
		} else {
			response = pflGateWay.getProduct(productID);
		}
		
		String payload = "";
		if (response.hasEntity()) {
			payload = response.readEntity(String.class);
		}
		
		return Response.status(response.getStatus()).entity(payload).build();
	}
	
	@POST
	@Path("orders")
	@Produces( "application/json;charset=utf-8" )
	@Consumes("application/json")
	public Response postOrder(String inPayLoad) {
		
		// Post one
		System.out.println("Posting order");
		System.out.println(inPayLoad);
		
		PFLGateWay pflGateWay = new PFLGateWay();
		Response response = pflGateWay.postOrder(inPayLoad);
		
		String outPayload = "";
		if (response.hasEntity()) {
			outPayload = response.readEntity(String.class);
		}
		
		System.out.println(outPayload);
		
		return Response.status(response.getStatus()).entity(outPayload).build();
	}
	
	@GET
	@Path("orders/{orderID}")
	@Produces( "application/json;charset=utf-8" )
	public Response getOrder(@PathParam("orderID") String orderID) {
		
		PFLGateWay pflGateWay = new PFLGateWay();
		Response response = pflGateWay.getOrder(orderID);
		
		String payload = "";
		if (response.hasEntity()) {
			payload = response.readEntity(String.class);
		}
		
		return Response.status(response.getStatus()).entity(payload).build();
	}

}

package org.klausoncloud.pflservice;

import java.util.Base64;
import java.util.Base64.Encoder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

public class PFLGateWay {
	static final int TIMEOUT = 10000; // Milli Sec
	static final String API_URL = "https://testapi.pfl.com";
	static final String SERVICE_PATH_PRODUCTS = "/products";
	static final String SERVICE_PATH_ORDERS = "/orders";

	static final String QUERY_PARM_APIKEY = "apikey";
	static final String QUERY_PARM_PRODUCT = "id";
	
	static final String APIKEY = "136085";
	static final String UP = "miniproject:Pr!nt123";
	
	
	ClientConfig config = new ClientConfig();
    Client client = ClientBuilder.newClient(config);
    String encodedUP;

	public PFLGateWay() {
		client.property(ClientProperties.CONNECT_TIMEOUT, TIMEOUT);
		client.property(ClientProperties.READ_TIMEOUT, TIMEOUT);
    	
    	Encoder encoder = Base64.getEncoder();
    	encodedUP = encoder.encodeToString(UP.getBytes());
	}
	
	public Response getAllProducts() {
		WebTarget target = client.target(API_URL + SERVICE_PATH_PRODUCTS);
		Invocation.Builder builder = addCommon(target);
    	Response response = builder.get();
    	
    	return response;
	}
	
	public Response getProduct(String productID) {
		WebTarget target = client.target(API_URL + SERVICE_PATH_PRODUCTS)
				.queryParam(QUERY_PARM_PRODUCT, productID);
    	Invocation.Builder builder = addCommon(target);
    	Response response = builder.get();
    	
    	return response;
	}
	
	public Response postOrder(String payLoad) {
		WebTarget target = client.target(API_URL + SERVICE_PATH_ORDERS);
    	Invocation.Builder builder = addCommon(target);
    	Response response = builder.post(Entity.json(payLoad));
    	
    	return response;
	}
	
	public Response getOrder(String orderID) {
    	WebTarget target = client.target(API_URL + SERVICE_PATH_ORDERS + "/" + orderID);
    	Invocation.Builder builder = addCommon(target);
    	Response response = builder.get();
    	
    	return response;
	}
	
	Invocation.Builder addCommon(WebTarget client) {
		Invocation.Builder builder = client
				.queryParam(QUERY_PARM_APIKEY, APIKEY)
    	        .request(MediaType.APPLICATION_JSON)
    	        .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedUP);
		return builder;
	}
}

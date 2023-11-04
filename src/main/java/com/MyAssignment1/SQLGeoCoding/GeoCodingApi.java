package com.MyAssignment1.SQLGeoCoding;

import com.google.maps.GeoApiContext;

public class GeoCodingApi {
	String key = // Google Map API key;
	GeoApiContext context;
	
	GeoCodingApi(){
		this.context = setApiConnection(this.key);
	}
	
	public GeoApiContext setApiConnection(String key) {
		GeoApiContext context = null;
		
		try {
			context = new GeoApiContext.Builder().apiKey(key).build();
			System.out.println("Connection to GeoCoding Api Successful");
		}
		catch(Exception e) {
			System.out.println("Error Conneting to GeoCodingApi");
			e.printStackTrace();
		}
		
		return context;
	}
	
	public GeoApiContext getApiConnection() {
		return context;
	}
	
	public void getApiKey() {
		System.out.println("Google GeoCoding Api key = " + key);
	}
}

package com.MyAssignment1.SQLGeoCoding;

public class Stations {
	int stationID;
	String name;
	double latitude;
	double longitude;
	String zipCode;
	
	public void setStations(int stationID, String name, String latitude, String longitude){
		this.stationID = stationID;
		this.name = name;
		this.latitude = Double.valueOf(latitude);
		this.longitude = Double.valueOf(longitude);
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public int getStationID() {
		return stationID;
	}
	
	public String getName() {
		return name;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	
	public String toString() {
		return "StationID = " + this.getStationID() + 
			   ",\nName = "+this.getName() + 
			   ", \nLatitude = " + this.getLatitude() + 
			   ", \nLongitude = " + this.getLongitude() + 
			   ", \nZipCode = " + this.getZipCode();
	}
	
	public void display() {
		System.out.println(this.toString());
	}
}

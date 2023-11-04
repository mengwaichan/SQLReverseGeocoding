package com.MyAssignment1.SQLGeoCoding;

import java.util.concurrent.TimeUnit;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;


public class Main {
	
	public static void main(String[] args) {

		DataBase DB = new DataBase();
		Connection conn = DB.getConnection();
		GeoCodingApi api = new GeoCodingApi();
		GeoApiContext context = api.getApiConnection();
		
		try {
		Statement psCreateTable = conn.createStatement();
		psCreateTable.executeUpdate(DB.getCreateString());
		System.out.println("\nCREATE TABLE success");
		
		// Pulling Data out from SQL Stations Table
		String query = "SELECT * FROM Stations"; 
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		System.out.println("SELECT query success");
		
		// Saving SQL Data on temp memory
		Stations station = new Stations();
		
		while(rs.next()) {
			station = new Stations();
			station.setStations(rs.getInt("Id"),
								rs.getString("Name"),
								rs.getString("Latitude"),
								rs.getString("Longitude"));
			
			try {
			// Requesting google geocoder api for zipcode
			GeocodingResult[] results =  GeocodingApi.reverseGeocode(context, new LatLng(station.getLatitude(), station.getLongitude())).resultType(AddressType.POSTAL_CODE).await();
			station.setZipCode(results[0].addressComponents[0].longName);
			
			System.out.println("________________________________________");
			station.display();
			
			// Inserting the ZipCode into the new ZipCode Table created in SQLServer
			String insertSQL = DB.getinsertString(station.getStationID() , station.getName(), 
							   station.getLatitude(), station.getLongitude(), station.getZipCode());
			
			PreparedStatement psInsert = conn.prepareStatement(insertSQL);
			psInsert.executeUpdate();
			
			}
			// Catch and Changes the JSON incase the LAT/LNG does not return Postal Code
			catch(Exception e) {
				try {
					GeocodingResult[] results = GeocodingApi.reverseGeocode(context, new LatLng(station.getLatitude(), station.getLongitude())).await();
					station.setZipCode(results[0].addressComponents[7].longName);
					
					String insertSQL = DB.getinsertString(station.getStationID() , station.getName(), 
									   station.getLatitude(), station.getLongitude(), station.getZipCode());
					
					PreparedStatement psInsert = conn.prepareStatement(insertSQL);
					psInsert.executeUpdate();
					
				} catch (ApiException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			
			}
			// Limiting request to GeoCoder API to 30 request per seconds 
			TimeUnit.MILLISECONDS.sleep(35);
		}	
		
		// Closing SQL Connection, Google API connection
		conn.close();
		context.shutdown();
		}
		catch(SQLException e) {
			System.out.println("Error While Accessing SQL Server");
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

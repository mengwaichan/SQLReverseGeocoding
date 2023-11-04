package com.MyAssignment1.SQLGeoCoding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
	String URL=//DataBase URL;
	Connection connection;
	
	String createZipCodeTable = "IF EXISTS(SELECT 1 FROM sys.tables WHERE NAME = 'ZipCode') DROP TABLE ZipCode;"
							  + "CREATE TABLE ZipCode("
							  + "StationID INT,"
							  + "Name VARCHAR(128),"
							  + "Latitude VARCHAR(64),"
							  + "Longitude VARCHAR(64),"
							  + "ZipCode INT)";
	
	DataBase(){
		this.connection = setConnection(this.URL);
	}
	
	public Connection setConnection(String URL) {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(URL);
			System.out.println("Connection to SQL Server Successful");
		}
		catch(SQLException e) {
			System.out.println("Connection to SQL Server Failed");
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public String getCreateString() {
		return createZipCodeTable;
	}
	
	public String getinsertString(int id, String name, double lat, double lng, String zip) {
		return "INSERT INTO ZipCode VALUES(" + id + ", '" + name + "', '" + lat + "', '" + lng + "', " + zip + ");";
	}
}

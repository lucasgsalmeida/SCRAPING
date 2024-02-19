package com.AWN.model.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDominio {
	
	public static Connection getConnection() {

		String URL = "jdbc:sqlanywhere:host=192.168.25.254:2638;DatabaseName=web;ServerName=srvcontabil";

		try {
			return DriverManager.getConnection(URL, "admin", "admin");
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}
		return null;
	}

}

package it.polito.tdp.gestorecorsi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	
	public static Connection getConnectio() throws SQLException {
		String jdbcURL="jdbc:mysql://localhost/iscritticorsi?user=root&password=damianoienco";
		return DriverManager.getConnection(jdbcURL);
	}

}

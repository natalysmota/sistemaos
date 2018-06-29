package br.com.natalysoares.sistemaos.dao;

import java.sql.*;

public class ConnectionModule {

	public static Connection connector() {
		java.sql.Connection connection = null;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/dbsistemaos";
		String user = "natalysmota";
		String password = "html5css3js";
		
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
			return connection;
		} catch(Exception e) {
			return null;
		}
	}
}

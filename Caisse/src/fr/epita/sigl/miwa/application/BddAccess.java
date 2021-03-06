package fr.epita.sigl.miwa.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BddAccess {
	private String url;
	private Connection connection;
	public BddAccess ()
	{}
	public void connect () {
		url = "jdbc:postgresql://:5432/miwa";
		Connection db;
		try {
			db = DriverManager.getConnection(url, "user", "user");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		connection = db;
		}
	public ResultSet select (String sqlQuery) throws SQLException{
		Statement state = null;
		state = connection.createStatement();
		ResultSet rs = state.executeQuery(sqlQuery);
		return rs;
	}
	
	public void insert (String sqlQuery) throws SQLException {
		Statement state = null;
		state = connection.createStatement();
		state.executeUpdate(sqlQuery);
	}

}

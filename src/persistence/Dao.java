package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Dao {
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;

	public void open() throws Exception {
		Class.forName("org.hsqldb.jdbcDriver");
		con = DriverManager.getConnection("jdbc:hsqldb:file:cards", "admin", "");
	}

	public void close() throws Exception{
		con.close();
	}
}

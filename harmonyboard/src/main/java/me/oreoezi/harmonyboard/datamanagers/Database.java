package me.oreoezi.harmonyboard.datamanagers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Database {
    private Connection con;
    Statement statement;
    public Database(String host, String port, String user, String password, String database) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", user, password);
            statement = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Database(String path) {
		try {
			con = DriverManager.getConnection("jdbc:sqlite:" + path);
            statement = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    public ArrayList<Row> executeQuery(String query) {
        ArrayList<Row> output = new ArrayList<Row>();
        try {
            ResultSet resultset = statement.executeQuery(query);
            while (resultset.next()) {
                Row row = new Row();
                for (int i=1;i<=resultset.getMetaData().getColumnCount();i++) {
                    row.setColumnValue(resultset.getMetaData().getColumnName(i), resultset.getObject(i));
                }
                output.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }
    public void runQuery(String query) {
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

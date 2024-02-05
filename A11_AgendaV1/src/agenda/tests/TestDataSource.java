package agenda.tests;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import agenda.config.Config;

public class TestDataSource {
	
	public static void main(String[] args) throws SQLException {
		DataSource ds = Config.getDataSource();
		Connection connect = ds.getConnection();
		connect.close();
		System.out.println("Todo OK");
	}

}

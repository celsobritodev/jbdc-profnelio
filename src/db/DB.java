package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {


	private static Connection conn = null;
	

public static Connection getConnection() {
    if (conn == null) {
        try {
            Properties props = loadProperties();
            String url = props.getProperty("dburl");
            String driver = props.getProperty("dbdriver");
            String login = props.getProperty("dblogin");
            String passwd = props.getProperty("dbpasswd");
            Class.forName(driver);
            conn = DriverManager.getConnection(url, login, passwd);
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver não encontrado!");

        }
    }
    return conn;
}
	
	
	
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		}
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
}

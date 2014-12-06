package at.reisisoft.fsm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A helper class for retrieving SQL connections
 * 
 * @author Florian
 *
 */
public class SqlHelper {
	public static final String mainJdbcUrl = "jdbc:mysql://140.78.196.25/CE",
			mainUn = "root", mainPw = "JKUce2014";
	public static final String centralJdbcUrl = "jdbc:mysql://140.78.73.67:3306/airlineDB",
			centralUn = "ceue", centralPw = "ceair14db";

	/**
	 *
	 * @param url
	 *            JDBC URL
	 * @param un
	 *            username
	 * @param pw
	 *            PW
	 * @return NULL if an exception occured, a connection mit autocommit = false
	 *         otherwise
	 */
	public static Connection getConnection(String url, String un, String pw) {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, un, pw);
			connection.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e2) {
			System.out
			.println(e2.getMessage() + " Error: " + e2.getErrorCode());
		}
		return connection;
	}

	/**
	 *
	 * @return Returns the default connection Look at
	 *         {@link #getConnection(String, String, String)
	 *         getConnection(String, String, String) } and {@value #mainJdbcUrl}
	 *         , {@value #mainUn}, {@value #mainPw}
	 */
	public static Connection getConnection() {
		return getConnection(mainJdbcUrl, mainUn, mainPw);
	}
}

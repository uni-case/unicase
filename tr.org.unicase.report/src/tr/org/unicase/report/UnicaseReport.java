package tr.org.unicase.report;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import tr.org.unicase.report.internals.config.Configuration;

public class UnicaseReport {

	private static UnicaseReport INSTANCE = null;
	private static Connection CONN = null;

	private UnicaseReport() {
	}

	private void initConnection(String driverName, String url, String user, String pass) {
		try {
			Class.forName(driverName);
			System.out.println("Connecting to database...");
			CONN = DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	private Connection getConnection() {
		if (CONN == null)
			initConnection(Configuration.DB.DRIVER, Configuration.DB.URL, Configuration.DB.USER, Configuration.DB.PASSWORD);
		return CONN;
	}

	public static UnicaseReport getInstance() {
		if (INSTANCE == null)
			INSTANCE = new UnicaseReport();
		return INSTANCE;
	}

	public static void clear() {
		if (INSTANCE != null)
			INSTANCE = null;
		if (CONN != null) {
			try {
				CONN.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CONN = null;
			}
		}
	}

	public byte[] generateReport(InputStream stream, Map<String, Object> parameters) {
		byte[] result = null;
		try {
			result = JasperRunManager.runReportToPdf(stream, parameters, getConnection());
		} catch (JRException e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}
}

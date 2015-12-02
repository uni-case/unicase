package tr.org.unicase.report.internals.config;

public interface Configuration {

	static interface BUNDLE {
		public static final String NAME = "Unicase Report Bundle";
		public static final String MESSAGE_START = NAME + " STARTED";
		public static final String MESSAGE_STOP  = NAME + " STOPPED";
	}

	static interface DB {
		public static final String URL = "jdbc:mysql://localhost:3306/unicase";
		public static final String USER = "root";
		public static final String PASSWORD = "roottoor";
		public static final String DRIVER = "com.mysql.jdbc.Driver";
	}


}

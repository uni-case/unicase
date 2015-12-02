package tr.org.unicase.graylog2.log.config;

public interface Configuration {

	public static final String EMPTY_MESSAGE = "";

	public static interface KEYS {
		public static final String HOST="graylog.host";
		public static final String PORT="graylog.port";
		public static final String TRANSPORT="graylog.transport";
	}

	public static interface VALUES {
		public static final String HOST="localhost";
		public static final String PORT="12201";
		public static final String TRANSPORT="UDP";
	}

	static interface BUNDLE {
		public static final String NAME = "Unicase GrayLog2 Logger Bundle";
		public static final String START_MESSAGE = NAME + " STARTED";
		public static final String STOP_MESSAGE = NAME + " STOPPED";
		public static final String PID = "tr.org.unicase.graylog2.log";
	}

	static interface FIELDS {
		public static final String IP   = "_ip";
		public static final String USER = "_user";
		public static final String TYPE = "_type";
		public static final String DATE = "_date";
		public static final String RESULT = "_result";
		public static final String ACTION = "_action";
		public static final String ENTITY = "_entity";
	}
}

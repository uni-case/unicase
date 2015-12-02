package tr.org.unicase.web.controller.api.internals.config;

public interface Configuration {

	static interface BUNDLE {
		public static final String NAME = "Unicase Web Controller API Bundle";
		public static final String MESSAGE_START = NAME + " STARTED";
		public static final String MESSAGE_STOP  = NAME + " STOPPED";
	}
	
	public static final String FIELD_ID = "id";
	
	static interface ROLE {
		public static final String ADMIN = "admin";
		public static final String SEPERATOR = ",";
	}
	
}

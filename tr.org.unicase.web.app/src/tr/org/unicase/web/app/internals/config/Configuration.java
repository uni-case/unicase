package tr.org.unicase.web.app.internals.config;

public interface Configuration {

	static interface Bundle {
		public static final String NAME = "Unicase Web App Bundle";
		public static final String START_MESSAGE = NAME + " STARTED";
		public static final String STOP_MESSAGE  = NAME + " STOPPED";
	}
	
	static interface HttpTracker {
		public static final String WEB_CONTEXTPATH = "/unicase";
		public static final String HEALTH_CONTEXTPATH = "/check";
		public static final String HEALTH_CONTENT_TYPE = "text/html";
		public static final String HEALTH_MESSAGE = "PONG";
		public static final String RESOURCE_CONTEXTPATH = "/VAADIN";
		public static final String RESOURCE_BUNDLENAME = "com.vaadin";
	}
	
	static interface AuthenticationManager {
		public static final String DESCRIPTION = "Manages Authentication Service";
		public static final String LISTENING        = " ### AuthenticationManager # LISTENING FOR SERVICES";
		public static final String FORMAT_ADD       = " ### AuthenticationManager # SERVICE %s REGISTERED";
		public static final String FORMAT_REMOVE    = " ### AuthenticationManager # SERVICE %s UNREGISTERED";
	}
	
	static interface LogManager {
		public static final String DESCRIPTION = "Manages Logging Services";
		public static final String SEPERATOR = ", ";
		public static final String NO_SERVICE = "No Services Found In LogManager";
		public static final String LISTENING        = " ### LogManager # LISTENING FOR SERVICES";
		public static final String FORMAT_ADD       = " ### LogManager # SERVICE %s REGISTERED";
		public static final String FORMAT_REMOVE    = " ### LogManager # SERVICE %s UNREGISTERED";
		public static final String FORMAT_CLEAN     = " ### LogManager # CLEANING";
		
	}
	
	static interface ViewManager {
		public static final String DESCRIPTION = "Manages View Services";
		public static final String SEPERATOR = ", ";
		public static final String NO_SERVICE = "No Services Found In ViewManager";
		public static final String LISTENING        = " ### ViewManager # LISTENING FOR SERVICES";
		public static final String FORMAT_ADD       = " ### ViewManager # SERVICE %s REGISTERED";
		public static final String FORMAT_REMOVE    = " ### ViewManager # SERVICE %s UNREGISTERED";
		public static final String FORMAT_CLEAN     = " ### ViewManager # CLEANING";
		public static final String FORMAT_NOT_FOUND = " ### ViewManager # %s NOT FOUND IN REGISTERED SERVICES";
		
	}
	
	
	
}

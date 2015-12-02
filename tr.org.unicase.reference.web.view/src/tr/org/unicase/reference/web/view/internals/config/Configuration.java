package tr.org.unicase.reference.web.view.internals.config;

public interface Configuration {

	static interface Bundle {
		public static final String NAME = " Unicase Reference Web View Bundle";
		public static final String START_MESSAGE = NAME + " STARTED";
		public static final String STOP_MESSAGE = NAME + " STOPPED";
	}
	
	static interface View {
		public static final String EMPTY_STRING = "";
	}
	
}

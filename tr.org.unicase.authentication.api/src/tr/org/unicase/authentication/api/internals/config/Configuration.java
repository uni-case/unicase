package tr.org.unicase.authentication.api.internals.config;

public interface Configuration {

	static interface Bundle {
		public static final String NAME = "Unicase Authentication Service API Bundle";
		public static final String MESSAGE_START= NAME + " STARTED";
		public static final String MESSAGE_STOP = NAME + " STOPPED";
	}

}

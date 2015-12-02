package tr.org.unicase.reference.service.internals.config;

public interface Configuration {

	static interface Bundle {
		public static final String NAME = "Unicase Reference Service Bundle";
		public static final String MESSAGE_START = NAME + " STARTED";
		public static final String MESSAGE_STOP  = NAME + " STOPPED";
	}
	
	static interface SQL {
		
		public static final String TRUE = "1";
		
		static interface CoreKeyValue {
			public static final String TABLE = "core_keyvalue";
			public static final String STATUS = "STATUS";
			public static final String ENTITY_TYPE_ID = "entity_Type_Id";
		}
	}
	
}

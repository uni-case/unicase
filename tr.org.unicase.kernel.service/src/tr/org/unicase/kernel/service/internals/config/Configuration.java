package tr.org.unicase.kernel.service.internals.config;

public interface Configuration {

	static interface SQL {
		static interface EntityType {
			public static final String TABLE = "core_entity_type";
		}

		static interface Field {
			public static final String TABLE = "core_field";
		}
		
		static interface Generate {
			public static final String TABLE = "core_generate";
		}
	}
}

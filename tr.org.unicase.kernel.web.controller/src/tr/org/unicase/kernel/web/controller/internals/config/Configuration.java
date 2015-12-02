package tr.org.unicase.kernel.web.controller.internals.config;

public interface Configuration {

	static interface Bundle {
		public static final String NAME = "Unicase Kernel Web Controller";
	}

	static interface Hazelcast {
		public static final String GROUP_NAME = "dev";
		public static final String GROUP_PASS = "dev-pass";
		public static final boolean SMART_ROUTING = Boolean.TRUE;
		public static final String ADDRESS = "localhost";
	}

	static interface EntityType {
		public static final String[] NATURAL_COLUMNS = new String[] { "id", "entityType", "className", "name" };
		public static final String[] VISIBLE_COLUMNS = new String[] { "id", "entityType", "className", "name" };
		public static final String[] REFERENCE_ENTITY_FIELDS = new String[] { "code", "value", "shortvalue", "description", "desc_01", "desc_02", "desc_03", "desc_04", "desc_05", "desc_06", "desc_07", "desc_08", "desc_09", "desc_10", "ck_01", "ck_02", "ck_03", "ck_04", "ck_05", "ck_06", "ck_07", "ck_08", "ck_09", "ck_10", "value_01", "value_02", "value_03", "value_04", "value_05", "value_06", "value_07", "value_08", "value_09", "value_10" };
	}

	static interface Field {
		public static final String[] NATURAL_COLUMNS = new String[] { "id" };
		public static final String[] VISIBLE_COLUMNS = new String[] { "id" };
	}

}

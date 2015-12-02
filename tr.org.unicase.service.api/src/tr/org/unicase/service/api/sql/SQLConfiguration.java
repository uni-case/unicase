package tr.org.unicase.service.api.sql;

import java.text.SimpleDateFormat;
import java.util.Locale;

public interface SQLConfiguration {

	public static final Locale TR = Locale.forLanguageTag("tr-TR");
	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
	public static final String SESSIONFACTORY_BEANNAME = "sessionFactory";
	
	
	public static final String TRUE = "1";
	public static final String SEPERATOR = ",";
	public static final String EMPTY_STRING = "";
	public static final String NO_SESSION_FOUND_MESSAGE = "No Session Found";
	public static final Exception NO_SESSION_FOUND_EXCEPTION = new Exception(NO_SESSION_FOUND_MESSAGE);
	
	static interface SELECT {
		public static final String KEYWORD = "SELECT ";
		public static final String ALL = "*";
		public static final String AS = " as ";
	}

	static interface FROM {
		public static final String KEYWORD = " FROM ";
	}

	static interface WHERE {
		public static final String KEYWORD = " WHERE 1=1 ";
		public static final String PERCENTAGE = "%";
		public static final String EQUAL = " = ";
		public static final String AND = " and ";
		public static final String LIKE = " like ";
		public static final String SEMICOLON = ":";
		public static final String ORDER = " order by ";
		public static final String SINGLE_QUOTE = "'";
	}

}

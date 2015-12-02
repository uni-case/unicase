package tr.org.unicase.web.component.internals.config;

import java.text.SimpleDateFormat;

public interface Configuration {

	static interface Banner {
		public static final String DEFAULT = "Unicase";
		public static final String WIDTH = "100%";
		public static final String FORMAT_TITLE = "<h3>%s<strong>Unicase</strong></h3>";
		public static final String STYLE = "valo-menu-title";
	}
	
	static interface Combobox {
		public static final Boolean NULL_SELECTION_ALLOWED = Boolean.TRUE;
		public static final String CAPTION_PROPERTY_ID = "value";
	}
	
	static interface Statusbar {
		public static final int LIMIT = 100;
		public static final int THRESHOLD = 90;
		public static final String STYLE =  "v-scrollable";
	}
	
	static interface Table {
		public static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");
		public static final String EMPTY_STRING = "";
		public static final int PAGE_LENGTH = 17;
		public static final int CACHE_RATE  = 3;
	}
	
	static interface TwinColSelect {
		public static final String SEPERATOR = ",";
		public static final String CODE =  "code";
		public static final String VALUE=  "value";
	}
	
	static interface Uploadbox {
		public static final int BAOS_LEN = 10240;
		public static final long MAX_SIZE = 536870912;
		public static final String[] DEFAULT_MIMETYPES = new String[] { "application/wps-office.wps", "application/x-tar", "application/zip", "application/gzip", "application/pdf", "application/xml", "text/xml", "message/rfc822", "application/octet-stream", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.ms-excel", "application/x-msexcel", "application/wps-office.xls", "image/png" };
	}
	
	static interface ExcelExport {
		public static final String COLUMN_SEPERATOR = "\\.";
		public static final String FORMAT_METHODNAME = "get%s";
		public static final String YES = "Evet";
		public static final String NO  = "Hayır";
		public static final String EMPTY_STRING = "";
		public static final String DOT = ".";
		public static final String SHEET = "Sheet 1";
		public static final String FORMAT_FILENAME = "export%d.xls";
		public static final String MIMETYPE = "application/x-msexcel";
		
	}
	
	
	
	
}

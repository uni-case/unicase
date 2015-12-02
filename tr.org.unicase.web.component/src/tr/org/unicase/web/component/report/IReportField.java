package tr.org.unicase.web.component.report;

public interface IReportField {

	public static String EMPTY_REPORT_VALUE = "";
	public static Boolean VALID_REPORT_FIELD = Boolean.TRUE;
	public static String REPORT_ALIAS = "dk";

	public String getReportValue();
	
	public void setFieldName(String fieldName);
	
	public boolean isFieldValid();
	
	public void setReportAlias(String reportAlias);
	
}

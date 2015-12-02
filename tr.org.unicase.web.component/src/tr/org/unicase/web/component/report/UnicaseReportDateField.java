package tr.org.unicase.web.component.report;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.ui.DateField;

public class UnicaseReportDateField extends DateField implements IReportField {

	private String fieldName;
	private String reportAlias;
	
	public UnicaseReportDateField() {
		super();
	}
	
	@Override
	public String getReportValue() {
		Date theValue = this.getValue();
		if (theValue != null) {
			String newstring = new SimpleDateFormat("yyyy-MM-dd").format(theValue);
			return " and " + reportAlias + "." + this.fieldName + "=" + "'"+newstring+"'";
		}
		return IReportField.EMPTY_REPORT_VALUE;
	}

	@Override
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public boolean isFieldValid() {
		return VALID_REPORT_FIELD;
	}

	@Override
	public void setReportAlias(String reportAlias) {
		this.reportAlias = reportAlias;		
	}

}

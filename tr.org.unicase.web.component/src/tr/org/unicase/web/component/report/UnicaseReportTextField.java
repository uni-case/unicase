package tr.org.unicase.web.component.report;

import com.vaadin.ui.TextField;

public class UnicaseReportTextField extends TextField implements IReportField {

	private String fieldName;
	private String reportAlias;
	
	public UnicaseReportTextField() {
		super();
	}

	@Override
	public String getReportValue() {
		String theValue = this.getValue();
		if (theValue != null && !theValue.trim().isEmpty())
			return " and " + reportAlias + "." + this.fieldName + "=" + theValue.trim();
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

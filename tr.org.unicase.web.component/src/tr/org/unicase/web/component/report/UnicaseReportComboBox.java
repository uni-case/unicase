package tr.org.unicase.web.component.report;

import tr.org.unicase.kernel.model.IEntity;
import tr.org.unicase.web.component.UnicaseComboBox;


public class UnicaseReportComboBox extends UnicaseComboBox implements IReportField {

	private String fieldName;
	private String reportAlias;

	public UnicaseReportComboBox() {
		super();
	}

	@Override
	public String getReportValue() {

		IEntity theValue = (IEntity) this.getValue();
		if (theValue != null) {
			return " and " + reportAlias +"."+ this.fieldName + "=" + theValue.getId();
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

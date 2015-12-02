package tr.org.unicase.web.component.report;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;

public class UnicaseReportMultiDateField extends HorizontalLayout implements IReportField {

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private String fieldName;
	private DateField dateFieldStartDate;
	private DateField dateFieldEndDate;
	private String reportAlias;
	
	public UnicaseReportMultiDateField() {
		super();

		dateFieldStartDate = new DateField();
		dateFieldStartDate.setDateFormat("dd.MM.yyyy");
		dateFieldStartDate.setCaption("Baslangic Tarihi");
		this.addComponent(dateFieldStartDate);

		dateFieldEndDate = new DateField();
		dateFieldEndDate.setDateFormat("dd.MM.yyyy");
		dateFieldEndDate.setCaption("Bitis Tarihi");
		this.addComponent(dateFieldEndDate);
	}

	@Override
	public String getReportValue() {
		Date startDate = this.dateFieldStartDate.getValue();
		Date endDate = this.dateFieldEndDate.getValue();
		String startDateAsString = null;
		String endDateAsString = null;
		if (startDate != null && endDate != null) {
			// demekki 2side dolu between yapacaz.
			startDateAsString = FORMAT.format(startDate);
			endDateAsString = FORMAT.format(endDate);
			return " and " + reportAlias + "." + this.fieldName + " >= " + "'" + startDateAsString + "'" + " and " + reportAlias + "." + this.fieldName + " <= " + "'" + endDateAsString + "'";
		} else if (startDate != null) {
			// baslangic tarihi dolu sadece >=
			startDateAsString = FORMAT.format(startDate);
			return " and " + reportAlias + "." + this.fieldName + " >= " + "'" + startDateAsString + "'";
		} else if (endDate != null) {
			// bitis tarihi dolu sadece <=
			endDateAsString = FORMAT.format(endDate);
			return " and " + reportAlias + "." + this.fieldName + " <= " + "'" + endDateAsString + "'";
		}

		return IReportField.EMPTY_REPORT_VALUE;
	}

	@Override
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public boolean isFieldValid() {

		Date startDate = dateFieldStartDate.getValue();
		Date endDate = dateFieldEndDate.getValue();

		if (((startDate != null && endDate != null) && !(startDate.before(endDate))))
			return !IReportField.VALID_REPORT_FIELD;
		return (startDate != null || endDate != null);
	}

	@Override
	public void setReportAlias(String reportAlias) {
		this.reportAlias = reportAlias;		
	}

}

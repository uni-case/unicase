package tr.org.unicase.web.view.validator;

import com.vaadin.data.Validator;

public class OptionValidator implements Validator {

	private static final String MESSAGE_FORMAT = "%s alanına sadece %s değerleri girilebilir.";

	private String fieldName;
	private String[] options;

	@SuppressWarnings("unused")
	private OptionValidator() {

	}

	public OptionValidator(String fieldName, String... options) {
		setFieldName(fieldName);
		setOptions(options);
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		String theValue = (String) value;
		boolean flag = false;
		for (String option : options) {
			if (!theValue.trim().equals(option))
				flag = false;
			else {
				flag = true;
				break;
			}
		}

		if (!flag)
			throw new InvalidValueException(String.format(MESSAGE_FORMAT, getFieldName(), getOptionsAsString()));
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String[] getOptions() {
		return options;
	}

	public void setOptions(String[] options) {
		this.options = options;
	}

	private String getOptionsAsString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("[");
		for (String option : options) {
			buffer.append(option + ",");
		}

		buffer.append("]");

		return buffer.toString();
	}

}

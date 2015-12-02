package tr.org.unicase.web.view.validator;

import com.vaadin.data.Validator;

public class NullValidator implements Validator {

	private static final String MESSAGE_FORMAT = "%s boş bırakılamaz.";

	private String fieldName;

	public NullValidator() {
		setFieldName("İlgili Alan");
	}

	public NullValidator(String fieldName) {
		setFieldName(fieldName);
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		String message = String.format(MESSAGE_FORMAT, getFieldName());
		InvalidValueException exception = new InvalidValueException(message);
		if (value == null)
			throw exception;
		if (value instanceof String && ((String) value).trim().isEmpty())
			throw exception;

	}

}

package tr.org.unicase.web.component;

import java.util.Collection;

import tr.org.unicase.kernel.model.IEntity;
import tr.org.unicase.web.component.internals.config.Configuration;

import com.vaadin.ui.ComboBox;

public class UnicaseComboBox extends ComboBox {

	private Long typeId;
	private Long ownerId;
	private String refColumn;
	private IComboBoxValueConverter<? extends IEntity> valueConverter;

	public UnicaseComboBox() {
		super();
		setNullSelectionAllowed(Configuration.Combobox.NULL_SELECTION_ALLOWED);
		setItemCaptionPropertyId(Configuration.Combobox.CAPTION_PROPERTY_ID);
	}

	@Override
	public void select(Object itemId) {
		if (itemId == null) {
			super.select(null);
			return;
		}
		Collection<?> collection = getItemIds();
		Object entity = null;
		Long id = null;
		if (itemId instanceof IEntity) {
			id = ((IEntity) itemId).getId();
		} else if (itemId instanceof Long) {
			id = (Long) itemId;
		}

		Long objectAsLong = null;
		if (id != null && id.longValue() > 0) {
			for (Object object : collection) {
				objectAsLong = (Long) object;
				if (objectAsLong.longValue() == id.longValue()) {
					entity = object;
					break;
				}
			}
			super.select(entity);
		}
	}

	public Object getSelectedValue() {
		Object value = getValue();
		if (this.valueConverter != null)
			return this.valueConverter.convert(value);
		return value;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getRefColumn() {
		return refColumn;
	}

	public void setRefColumn(String refColumn) {
		this.refColumn = refColumn;
	}

	public void setAttributes(Long typeId, Long ownerId, String refColumn) {
		setTypeId(typeId);
		setOwnerId(ownerId);
		setRefColumn(refColumn);
	}

	public IComboBoxValueConverter<? extends IEntity> getValueConverter() {
		return valueConverter;
	}

	public void setValueConverter(IComboBoxValueConverter<? extends IEntity> valueConverter) {
		this.valueConverter = valueConverter;
	}

}

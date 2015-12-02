package tr.org.unicase.web.component;

import tr.org.unicase.kernel.model.IEntity;

public interface IComboBoxValueConverter<E extends IEntity> {

	public E convert(Object value);
	
}

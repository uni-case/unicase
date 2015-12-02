package tr.org.unicase.kernel.service;

import java.util.List;

import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.service.api.Service;

public interface FieldService extends Service<Field> {

	public List<Field> findFields(Long entityTypeId);

	public List<Field> findOwneredFields(Long entityTypeId);

	public List<Field> findSearchableFields(Long entityTypeId);

}

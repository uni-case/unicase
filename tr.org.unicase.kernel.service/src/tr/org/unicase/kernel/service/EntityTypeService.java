package tr.org.unicase.kernel.service;

import java.util.List;

import tr.org.unicase.kernel.model.EntityType;
import tr.org.unicase.service.api.Service;

public interface EntityTypeService extends Service<EntityType>  {

	public List<EntityType> findEntityTypes();
}

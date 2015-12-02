package tr.org.unicase.kernel.web.controller;

import java.util.List;

import tr.org.unicase.kernel.model.EntityType;
import tr.org.unicase.kernel.service.EntityTypeService;
import tr.org.unicase.kernel.service.EntityTypeServiceImp;
import tr.org.unicase.kernel.web.controller.internals.config.Configuration;
import tr.org.unicase.web.controller.api.AbstractController;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;

public class EntityTypeControllerImp extends AbstractController<EntityType> {

	private EntityTypeService service = new EntityTypeServiceImp();

	public EntityTypeControllerImp(Long entityTypeId, Long ownerId, String refOwner) {
		super(entityTypeId, ownerId, refOwner);
	}

	@Override
	public void generate() {
		EntityType entityType = new EntityType();
		setEntity(entityType);

	}

	@Override
	public void save(EntityType entity) throws Exception {
		super.save(entity);
		service.save(entity);

	}

	@Override
	public Container getDataSource() {
		BeanItemContainer<EntityType> container = new BeanItemContainer<EntityType>(EntityType.class, entityList);
		return container;
	}

	@Override
	public Container getDataSource(List<EntityType> entities) {
		BeanItemContainer<EntityType> container = new BeanItemContainer<EntityType>(EntityType.class, entities);
		return container;
	}

	@Override
	public String[] getNaturalColumnOrder() {
		return Configuration.EntityType.NATURAL_COLUMNS;
	}

	@Override
	public String[] getColumnHeaderValues() {
		return Configuration.EntityType.VISIBLE_COLUMNS;
	}

	@Override
	public List<EntityType> findAll(Long entityTypeId, Long ownerId, String refOwner) {
		entityList = service.findEntityTypes();
		return entityList;
	}

	public String[] getReferenceEntityFields() {
		return Configuration.EntityType.REFERENCE_ENTITY_FIELDS;
	}

	@Override
	public void open(EntityType entity) {

	}

	@Override
	public void details(EntityType entity) {

	}

	@Override
	public void search() {

	}

	@Override
	public Container getDataSource(Long entityTypeId, Long ownerId, String refOwner) {
		return null;
	}

	@Override
	public EntityType findById(Long id) {
		return null;
	}

}

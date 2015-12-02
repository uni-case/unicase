package tr.org.unicase.kernel.web.controller;

import java.util.List;

import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.kernel.service.FieldServiceImp;
import tr.org.unicase.kernel.web.controller.cache.CoreFieldCacheImpl;
import tr.org.unicase.kernel.web.controller.internals.config.Configuration;
import tr.org.unicase.web.controller.api.AbstractController;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;

public class FieldControllerImp extends AbstractController<Field> {

	private FieldServiceImp service = new FieldServiceImp();

	public FieldControllerImp(Long entityTypeId, Long ownerId, String refOwner) {
		super(entityTypeId, ownerId, refOwner);
	}

	@Override
	public void generate() {
		Field field = new Field();
		field.setEntityTypeId(getEntityTypeId());

		setEntity(field);

	}

	@Override
	public void save(Field entity) throws Exception {
		super.save(entity);
		service.save(entity);
		CoreFieldCacheImpl.getInstance().update(entity);
	}

	@Override
	public Container getDataSource() {
		BeanItemContainer<Field> container = new BeanItemContainer<Field>(Field.class, entityList);
		return container;
	}

	@Override
	public Container getDataSource(List<Field> entities) {
		BeanItemContainer<Field> container = new BeanItemContainer<Field>(Field.class, entities);
		return container;
	}

	@Override
	public String[] getNaturalColumnOrder() {
		return Configuration.Field.NATURAL_COLUMNS;
	}

	@Override
	public String[] getColumnHeaderValues() {
		return Configuration.Field.VISIBLE_COLUMNS;
	}

	@Override
	public List<Field> findAll(Long entityTypeId, Long ownerId, String refOwner) {
		entityList = service.findFields(entityTypeId);
		return entityList;
	}

	public void open(Field entity) {

	}

	public void details(Field entity) {

	}

	@Override
	public void search() {

	}

	@Override
	public Container getDataSource(Long entityTypeId, Long ownerId, String refOwner) {
		return null;
	}

	@Override
	public Field findById(Long id) {
		return null;
	}

}

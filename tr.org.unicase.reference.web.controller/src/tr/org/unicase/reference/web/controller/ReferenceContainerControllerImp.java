package tr.org.unicase.reference.web.controller;

import java.util.List;

import tr.org.unicase.reference.model.ReferenceEntity;
import tr.org.unicase.web.controller.api.AbstractController;

import com.vaadin.data.Container;

public class ReferenceContainerControllerImp extends AbstractController<ReferenceEntity> {

	public ReferenceContainerControllerImp(Long entityTypeId, Long ownerId, String refOwner) {
		super(entityTypeId, ownerId, refOwner);

	}

	@Override
	public Object[] getNaturalColumnOrder() {
		return null;
	}

	@Override
	public String[] getColumnHeaderValues() {
		return null;
	}

	@Override
	public List<ReferenceEntity> findAll(Long entityTypeId, Long ownerId, String refOwner) {

		return null;
	}

	@Override
	public Container getDataSource() {
		return null;
	}

	@Override
	public Container getDataSource(List<ReferenceEntity> entities) {
		return null;
	}

	@Override
	public void generate() {

	}

	@Override
	public void open(ReferenceEntity entity) {

	}

	@Override
	public void details(ReferenceEntity entity) {

	}

	@Override
	public void search() {

	}

	@Override
	public Container getDataSource(Long entityTypeId, Long ownerId, String refOwner) {
		return null;
	}

	@Override
	public ReferenceEntity findById(Long id) {
		return null;
	}

}

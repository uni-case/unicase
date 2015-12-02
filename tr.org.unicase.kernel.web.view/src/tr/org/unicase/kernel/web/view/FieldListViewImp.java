package tr.org.unicase.kernel.web.view;

import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.kernel.web.controller.FieldControllerImp;
import tr.org.unicase.web.component.UnicaseTable;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.action.api.UnicaseActionManager;
import tr.org.unicase.web.view.api.AbstractView;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

public class FieldListViewImp extends AbstractView<FieldControllerImp> implements Table.ValueChangeListener {

	public FieldListViewImp(Long entityTypeId, Long ownerId, String refOwner) {
		this.entityTypeId = entityTypeId;
		this.ownerId = ownerId;
		this.refOwner = refOwner;

		setController(new FieldControllerImp(entityTypeId, ownerId, refOwner));
		controller.findAll(entityTypeId, ownerId, refOwner);

		Action action = UnicaseActionManager.getInstance().createInstance("tr.org.unicase.kernel.web.view.action.OpenActionImpl", "1", null, "open.png", "Open", "tr.org.unicase.kernel.web.view.FieldViewImp", controller, this, null);
		if (action != null)
			actionList.add(action);

		action = null;

		createTable();
	}

	private void createTable() {

		table = new UnicaseTable();
		table.setPageLength(7);
		table.setImmediate(true);
		table.setSelectable(true);
		table.setWidth("100%");
		table.setColumnReorderingAllowed(true);
		table.setColumnCollapsingAllowed(true);

		table.setContainerDataSource(controller.getDataSource());

		table.addValueChangeListener(this);
	}

	@Override
	public void initLayout() {

	}

	@Override
	public Component getView() {
		mainVerticalLayout.addComponent(table);

		return mainVerticalLayout;
	}

	@Override
	public void update() {
		getController().findAll(entityTypeId, ownerId, refOwner);
		table.refreshTable();

	}

	@Override
	public void setViewToEntity() {

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		Field entity = (Field) (table.getValue());
		controller.setEntity(entity);

	}

	@Override
	public void refresh() {

	}

}

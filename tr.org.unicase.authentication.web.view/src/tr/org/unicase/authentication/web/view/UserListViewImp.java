package tr.org.unicase.authentication.web.view;

import tr.org.unicase.authentication.service.UserControllerImp;
import tr.org.unicase.authentication.service.model.User;
import tr.org.unicase.authentication.web.view.internals.config.Configuration;
import tr.org.unicase.web.component.UnicaseTable;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.action.api.UnicaseActionManager;
import tr.org.unicase.web.view.api.AbstractView;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Component;

public class UserListViewImp extends AbstractView<UserControllerImp> implements ValueChangeListener {

	public UserListViewImp(Long entityTypeId, Long ownerId, String refOwner) {
		this.entityTypeId = entityTypeId;
		this.ownerId = ownerId;
		this.refOwner = refOwner;

		setController(new UserControllerImp(entityTypeId, ownerId, refOwner));
		controller.findAllUser();

		Action action = UnicaseActionManager.getInstance().createInstance(Configuration.ViewAction.OPEN, Configuration.ViewAction.ID, null, Configuration.ViewAction.OPEN_ICON, Configuration.ViewAction.OPEN_CAPTION, Configuration.UserViewImp.NAME, controller, this, null);

		if (action != null)
			actionList.add(action);

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
		controller.findAllUser();
		table.refreshTable();

	}

	@Override
	public void setViewToEntity() {

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		User entity = (User) (table.getValue());
		controller.setEntity(entity);

	}

	@Override
	public void refresh() {

	}

}

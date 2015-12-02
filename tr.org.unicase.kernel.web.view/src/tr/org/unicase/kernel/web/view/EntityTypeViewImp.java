package tr.org.unicase.kernel.web.view;

import java.util.List;

import tr.org.unicase.kernel.model.EntityType;
import tr.org.unicase.kernel.web.controller.EntityTypeControllerImp;
import tr.org.unicase.kernel.web.view.internals.config.Configuration;
import tr.org.unicase.web.component.UnicaseTwinColSelect;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.action.api.UnicaseActionManager;
import tr.org.unicase.web.view.api.AbstractView;

import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

public class EntityTypeViewImp extends AbstractView<EntityTypeControllerImp> {

	private GridLayout mainGridLayout = new GridLayout(2, 1);
	private VerticalLayout leftVerticalLayout;

	private TextField textField_EntityType;
	private TextField textField_ClassName;
	private TextField textField_OwnerId;
	private TextField textField_OrderNo;
	private TextField textField_Name;
	private TextField textField_Status;
	private TextField textField_Description;
	private UnicaseTwinColSelect twinColSelectFields;

	public EntityTypeViewImp(Long entityTypeId, Long ownerId, String refOwner) {
		this.entityTypeId = entityTypeId;
		this.ownerId = ownerId;
		this.refOwner = refOwner;

		setController(new EntityTypeControllerImp(entityTypeId, ownerId, refOwner));
		buildLeftVerticalLayout();

		buildLayout();

		if (leftVerticalLayout != null)
			mainGridLayout.addComponent(leftVerticalLayout, 0, 0);
		
		Action action = UnicaseActionManager.getInstance().createInstance(Configuration.ViewAction.SAVE,Configuration.ViewAction.ID, null, "save.png", "Save", Configuration.ViewAction.ACTION_CLASS, controller, this,null); 
		if (action != null) 
			actionList.add(action);
		action = UnicaseActionManager.getInstance().createInstance(Configuration.ViewAction.GENERATE, Configuration.ViewAction.ID, null, "new.png", "New", Configuration.ViewAction.ACTION_CLASS, controller, this, null);
		if (action != null)
			actionList.add(action);
		
		action = null;

	}

	@Override
	public void initLayout() {

		for (String referenceEntityField : getController().getReferenceEntityFields()) {
			twinColSelectFields.addItem(referenceEntityField);
		}

	}

	private void buildLeftVerticalLayout() {
		leftVerticalLayout = new VerticalLayout();
		leftVerticalLayout.setSpacing(true);
		leftVerticalLayout.setMargin(true);
	}

	@Override
	public Component getView() {
		mainVerticalLayout.addComponent(mainGridLayout);
		return mainVerticalLayout;
	}

	private void buildLayout() {

		textField_Name = (TextField) createComponent(textField_Name, TextField.class, new Label("Name"));
		textField_ClassName = (TextField) createComponent(textField_ClassName, TextField.class, new Label("ClassName"));
		textField_OwnerId = (TextField) createComponent(textField_OwnerId, TextField.class, new Label("OwnerId"));
		textField_Status = (TextField) createComponent(textField_Status, TextField.class, new Label("Status(true,false)"));
		textField_EntityType = (TextField) createComponent(textField_EntityType, TextField.class, new Label("EntityType"));
		textField_OrderNo = (TextField) createComponent(textField_OrderNo, TextField.class, new Label("OrderNo"));
		textField_Description = (TextField) createComponent(textField_Description, TextField.class, new Label("Description"));
		twinColSelectFields = (UnicaseTwinColSelect) createComponent(twinColSelectFields, UnicaseTwinColSelect.class, new Label("RE String"));
		twinColSelectFields.setRightColumnCaption("Secilen Alanlar");
		twinColSelectFields.setLeftColumnCaption("Uygun Alanlar");

	}

	private Component createComponent(Object object, Class<?> clazz, Label label) {
		Component component = null;

		try {
			object = clazz.newInstance();
			component = (Component) object;
			if (!TwinColSelect.class.isAssignableFrom(clazz)) {
				component.setWidth("450px");
				component.setHeight("24px");
			}
			if (AbstractTextField.class.isAssignableFrom(clazz))
				((AbstractTextField) component).setNullRepresentation("");
			GridLayout gridLayout = new GridLayout(2, 1);
			gridLayout.setColumnExpandRatio(0, 3);
			gridLayout.setColumnExpandRatio(1, 2);
			gridLayout.setSpacing(false);
			gridLayout.setMargin(false);
			label.setWidth("150px");
			gridLayout.addComponent(label, 0, 0);
			gridLayout.addComponent(component, 1, 0);
			gridLayout.setComponentAlignment(component, Alignment.MIDDLE_LEFT);
			leftVerticalLayout.addComponent(gridLayout);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return component;
	}

	@Override
	public void update() {

		EntityType entity = controller.getEntity();
		if (entity == null)
			return;

		textField_Name.setValue(entity.getName());
		textField_ClassName.setValue(entity.getClassName());
		textField_OwnerId.setValue(String.valueOf(entity.getOwnerId()));
		textField_Status.setValue(String.valueOf(entity.getStatus()));
		textField_EntityType.setValue(entity.getEntityType());
		textField_OrderNo.setValue(String.valueOf(entity.getOrderno()));
		textField_Description.setValue(entity.getDescription());
		twinColSelectFields.select(entity.getStringElements());
	}

	@Override
	public void setViewToEntity() {

		EntityType entity = controller.getEntity();

		entity.setName((String) textField_Name.getValue());
		entity.setClassName((String) textField_ClassName.getValue());
		entity.setOwnerId(new Long(textField_OwnerId.getValue().toString()));
		entity.setStatus(new Boolean(textField_Status.getValue().toString()));
		entity.setEntityType((String) textField_EntityType.getValue());
		entity.setOrderno(new Integer(textField_OrderNo.getValue().toString()));
		entity.setDescription((String) textField_Description.getValue());
		entity.setStringElements(twinColSelectFields.getSelectedItems());
		
	}

	@Override
	public List<Action> getActions() {
		return actionList;
	}

	@Override
	public void refresh() {

	}

}

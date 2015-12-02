package tr.org.unicase.web.view.api;

import java.util.LinkedList;
import java.util.List;

import tr.org.unicase.web.component.UnicaseTable;
import tr.org.unicase.web.view.action.api.Action;

import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.VerticalLayout;

public abstract class AbstractView<C> implements View<C> {

	protected C controller;

	protected List<Action> actionList = new LinkedList<Action>();

	protected VerticalLayout mainVerticalLayout = new VerticalLayout();

	protected UnicaseTable table;

	private String icon;

	private String name;

	protected Long entityTypeId;
	
	protected Long reportTypeId;

	protected Long ownerId;

	protected String refOwner;

	public abstract void update();

	public abstract void setViewToEntity();

	@Override
	public Component getView() {
		return mainVerticalLayout;
	}

	@Override
	public String getIcon() {
		return this.icon;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setIcon(String icon) {
		this.icon = icon;

	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<Action> getActions() {
		return actionList;
	}

	@Override
	public void setController(C controller) {
		this.controller = controller;
	}

	@Override
	public C getController() {
		return controller;
	}

	public List<View<?>> getViewList() {
		return null;
	}

	public void setViewList(List<View<?>> viewList) {

	}
	
	public boolean validate() {
		return Boolean.TRUE;
	}
	
	@Override
	public List<Action> getChildsAction() {
		return null;
	}
	
	public String getOwnerInfo() {
		return null;
	}
	
	protected boolean valid(Component component) {
		Class<?> clazz = component.getClass();
		if (AbstractTextField.class.isAssignableFrom(clazz)) {
			((AbstractTextField) component).setValidationVisible(true);
			return ((AbstractTextField) component).isValid();
		} else if (DateField.class.isAssignableFrom(clazz)) {
			((DateField) component).setValidationVisible(true);
			return ((DateField) component).isValid();
		} else if (AbstractSelect.class.isAssignableFrom(clazz)) {
			((AbstractSelect) component).setValidationVisible(true);
			return ((AbstractSelect) component).isValid();
		}

		return false;
	}

	
	@Override
	public void addActionButton(Component component) {
		
	}

}

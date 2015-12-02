package tr.org.unicase.reference.web.view;

import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.kernel.web.view.PopupViewContent;
import tr.org.unicase.reference.web.controller.ReferenceControllerImp;
import tr.org.unicase.web.app.ViewDecoratorImpl;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.api.View;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class ReferenceViewContentImp implements PopupViewContent {

	private String valueAsHtml;
	private Field field;

	public ReferenceViewContentImp(Field field, String valueAsHtml) {
		setValueAsHtml(valueAsHtml);
		setField(field);
	}

	@Override
	public String getMinimizedValueAsHTML() {
		return this.valueAsHtml;
	}

	public void setValueAsHtml(String valueAsHtml) {
		this.valueAsHtml = valueAsHtml;
	}

	@Override
	public Component getPopupComponent() {
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(createView());
		return layout;
	}

	@SuppressWarnings("unchecked")
	private Component createView() {
		View<?> view = new ReferenceViewImp(field.getRefTypeId(), null, null);
		view.setIcon("open.png");
		view.setName("Ekle");

		view = new ViewDecoratorImpl((View<Controller<Object>>) view);

		((ReferenceControllerImp) view.getController()).generate();

		view.initLayout();
		view.update();
		Component component = view.getView();
		
		return component;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
}

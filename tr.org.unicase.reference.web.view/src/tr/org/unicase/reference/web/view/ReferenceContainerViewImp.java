package tr.org.unicase.reference.web.view;

import java.util.LinkedList;
import java.util.List;

import tr.org.unicase.reference.web.controller.ReferenceContainerControllerImp;
import tr.org.unicase.web.view.api.AbstractView;
import tr.org.unicase.web.view.api.View;

import com.vaadin.ui.Component;

public class ReferenceContainerViewImp extends AbstractView<ReferenceContainerControllerImp> {

	private List<View<?>> viewList = new LinkedList<View<?>>();

	public ReferenceContainerViewImp(Long entityTypeId, Long ownerId, String refColumn) {
		setController(new ReferenceContainerControllerImp(entityTypeId, ownerId, refColumn));
	}

	@Override
	public Component getView() {
		mainVerticalLayout.setSpacing(false);
		mainVerticalLayout.setMargin(false);
		mainVerticalLayout.setStyleName("v-scrollable");
		mainVerticalLayout.setId(this.getClass().getSimpleName().toLowerCase());

		for (View<?> view : viewList) {
			mainVerticalLayout.addComponent(view.getView());
		}

		return mainVerticalLayout;
	}

	@Override
	public void initLayout() {
		for (View<?> view : viewList) {
			view.initLayout();
		}

	}

	@Override
	public void update() {
		for (View<?> view : viewList) {
			view.update();
		}

	}

	@Override
	public void setViewToEntity() {
		for (View<?> view : viewList) {
			view.setViewToEntity();
		}

	}

	public List<View<?>> getViewList() {
		return viewList;
	}

	public void setViewList(List<View<?>> viewList) {
		this.viewList = viewList;
	}

	@Override
	public void refresh() {
		for (View<?> view : viewList) {
			if (view != null)
				view.refresh();
		}
			
	}

}

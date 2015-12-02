package tr.org.unicase.web.view.api;

import java.util.List;

import tr.org.unicase.web.view.action.api.Action;

import com.vaadin.ui.Component;

public interface View<C> {

	Component getView();

	String getIcon();

	void setIcon(String icon);

	String getName();

	void setName(String name);

	void setViewToEntity();

	List<Action> getActions();

	List<Action> getChildsAction();

	void setController(C controller);

	C getController();

	void initLayout();

	List<View<?>> getViewList();

	void addActionButton(Component component);

	void setViewList(List<View<?>> viewList);

	boolean validate();

	void update();

	void refresh();
}

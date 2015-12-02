package tr.org.unicase.web.app;

import java.util.HashMap;
import java.util.Map;

import tr.org.unicase.kernel.web.view.MainWindow;
import tr.org.unicase.web.app.api.ModuleOperation;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.api.Module;

import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

public class UnicaseMenuView extends CssLayout implements Accordion.SelectedTabChangeListener, ModuleOperation, ItemClickListener {

	private Accordion accordion = new Accordion();
	private Map<String, Tab> tabMap = new HashMap<String, Tab>();
	
	public UnicaseMenuView() {
		setId("menu");
		setSizeFull();
		
		this.setImmediate(false);
		accordion = new Accordion();
		accordion.addSelectedTabChangeListener(this);
		accordion.setImmediate(false);
		accordion.setSizeFull();
//		accordion.setWidth(300, Unit.PIXELS);
//		accordion.setHeight(100, Unit.PERCENTAGE);
//		accordion.setPrimaryStyleName(ValoTheme.ACCORDION_BORDERLESS);
		buildMenu();
		addComponent(accordion);
	}

	private void buildMenu() {

		HorizontalLayout top = new HorizontalLayout();
		top.setSpacing(true);
		top.setWidth("100%");
		top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		top.addStyleName("valo-menu-title");
		addComponent(top);
		// addComponent(createThemeSelect());

		Label title = new Label("<h3><strong>Unicase</strong></h3>", ContentMode.HTML);
		title.setSizeUndefined();
		top.addComponent(title);
		top.setExpandRatio(title, 1);
/*
		MenuBar settings = new MenuBar();
		settings.addStyleName("user-menu");
		MenuItem settingsItem = settings.addItem("Deneme", IconHelper.getIcon("profile-pic.jpg"), null);
		settingsItem.addItem("Edit Profile", null);
		settingsItem.addItem("Preferences", null);
		settingsItem.addSeparator();
		settingsItem.addItem("Sign Out", null);
		addComponent(settings);

		menuItemsLayout.setPrimaryStyleName("valo-menuitems");
		addComponent(menuItemsLayout);*/

	}

	@Override
	public void addModule(Module module) {

		try {
			for (Action action : module.getActions()) {
				Tree tree = new Tree();
				tree.setStyleName(ValoTheme.TREETABLE_COMPACT);
				tree.addItemClickListener(this);
				tree.setCaption(action.getText());
				tree.addContainerProperty("icon", Resource.class, null);
				tree.addContainerProperty("caption", String.class, null);
				tree.setItemIconPropertyId("icon");
				tree.setItemCaptionPropertyId("caption");
				tree.setItemCaptionMode(ItemCaptionMode.PROPERTY);
				addAccordionTab(tree, module, action);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void addAccordionTab(Tree tree, Module module, Action action) {
		Tab tab = accordion.addTab(tree, action.getText(), IconHelper.getIcon16(action.getIcon()));
		tabMap.put(module.getName(), tab);
		for (Action subAction : action.getChildren()) {
			recurse(subAction, tree, action);
		}
	}

	@SuppressWarnings("unchecked")
	private void recurse(Action action, Tree tree, Action owner) {

		Item item = tree.addItem(action);

		item.getItemProperty("icon").setValue(IconHelper.getIcon16(action.getIcon()));
		item.getItemProperty("caption").setValue(action.getText());

		if (owner != null) {
			tree.setParent(action, owner);
			if (action.getChildren() != null && action.getChildren().size() > 0)
				tree.setChildrenAllowed(action, true);
			else
				tree.setChildrenAllowed(action, false);
		}

		for (Action child : action.getChildren()) {
			recurse(child, tree, action);
		}

	}

	private void callActionView(Action action) {
		try {
			if (action == null || action.getClazz() == null)
				return;
			((MainWindow) UI.getCurrent()).executeAction(action);
		} catch (Exception e) {
			Notification.show("", Notification.Type.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	@Override
	public void itemClick(ItemClickEvent event) {
		Object value = event.getItemId();
		Action selectedAction = (Action) value;
		callActionView(selectedAction);
	}

	@Override
	public void removeModule(Module module) {

		try {
			accordion.removeTab(tabMap.get(module.getName()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void selectedTabChange(SelectedTabChangeEvent event) {
		// TODO Auto-generated method stub

	}

}

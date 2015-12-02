package tr.org.unicase.web.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tr.org.unicase.authentication.service.model.User;
import tr.org.unicase.kernel.web.view.MainWindow;
import tr.org.unicase.log.api.LogEvent;
import tr.org.unicase.reference.model.ReferenceEntity;
import tr.org.unicase.reference.service.ReferenceService;
import tr.org.unicase.reference.service.ReferenceServiceImp;
import tr.org.unicase.web.app.api.ModuleOperation;
import tr.org.unicase.web.component.UnicaseBannerComponent;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.action.api.UnicaseActionManager;
import tr.org.unicase.web.view.api.IEventListener;
import tr.org.unicase.web.view.api.Module;

import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class AccordionMenuImpl extends VerticalLayout implements IEventListener, Accordion.SelectedTabChangeListener, ModuleOperation, ItemClickListener {

	private Accordion accordion = new Accordion();
	private Map<String, Tab> tabMap = new HashMap<String, Tab>();
	private Action selectedAction;
	private MenuItem settingsItem;
	private List<ReferenceEntity> allMenuList;
	private Boolean roleIsAdmin = false;
	private List<ReferenceEntity> uniqueActiveActionList;
	private Map<Long, ReferenceEntity> moduleList = new HashMap<Long, ReferenceEntity>();
	private Map<Long, List<ReferenceEntity>> menuList = new HashMap<Long, List<ReferenceEntity>>();
	private List<ReferenceEntity> viewList = new ArrayList<ReferenceEntity>();

	public AccordionMenuImpl() {
		setSpacing(false);
		setMargin(false);
		this.setImmediate(false);
		accordion = new Accordion();
		accordion.addSelectedTabChangeListener(this);
		accordion.setImmediate(false);
		accordion.setSizeFull();

		addComponent(buildUserMenu());
		addBanner();
		addComponent(accordion);
		setComponentAlignment(accordion, Alignment.MIDDLE_CENTER);

	}

	private Component buildUserMenu() {
		final MenuBar settings = new MenuBar();
		settings.addStyleName("user-menu");
		final User user = getCurrentUser();
		String label = "UNICASE Kullanıcısı";
		if (user != null)
			label = user.getName();
		settingsItem = settings.addItem(label, IconHelper.getIcon96("contact-icon.png"), null);
		MenuItem addMenuItem = settingsItem.addItem("Çıkış", new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				VaadinSession.getCurrent().close();
				Page.getCurrent().reload();
			}

		});
		addMenuItem.setIcon(IconHelper.getIcon24("close-2-icon.png"));
		return settings;
	}
	
	private User getCurrentUser() {
		return (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
	}

	private void addBanner() {
		UnicaseBannerComponent top = new UnicaseBannerComponent();
		addComponent(top);
		setComponentAlignment(top, Alignment.TOP_CENTER);
	}
	
	public void addMenuTab(Module module) {
		
	}

	private List<ReferenceEntity> filterMenu(User user) {
		String userRole = null;
		List<ReferenceEntity> activeModuleList = null;
		
		if(user != null){
			if(user.getRole() != null){
				userRole = user.getRole().trim();
				activeModuleList = findActiveModules(userRole);
			} 
		}
		return activeModuleList;
	}

	private List<ReferenceEntity> findActiveModules(String userRoles) {
		
		ReferenceService dao = new ReferenceServiceImp();
		List<ReferenceEntity> userIdList = null;
		List<ReferenceEntity> activeModuleList = new ArrayList<>();
		List<ReferenceEntity> activeAdminModuleList = new ArrayList<>();
		uniqueActiveActionList = new ArrayList<>();
		String [] userActiveRoles = null;
		userActiveRoles = userRoles.split(",");
		
		if(userActiveRoles != null && userActiveRoles.length >0)
			for (String role : userActiveRoles) {
				if(role.trim().equalsIgnoreCase("admin")){
					activeAdminModuleList = dao.findAll(1l, null, null);
					roleIsAdmin = true;
					return activeAdminModuleList;
				}else{
					Long userId = null;
					userIdList = null;
					userIdList = dao.findUserId(role.trim());
					if(userIdList != null && userIdList.size() >0)
						userId = userIdList.get(0).getId();
					if(userId != null)
						activeModuleList.addAll(dao.findActiveModuleList(userId));
				}
			}
		if(activeModuleList != null && activeModuleList.size() > 0){
			for (final ReferenceEntity action : activeModuleList) {
				findUniqueActionList(action);
			}
			return uniqueActiveActionList;
		}
		else
			return null;
	}

	public void findUniqueActionList(ReferenceEntity action) {
		if(uniqueActiveActionList != null && uniqueActiveActionList.size() > 0){
			for(ReferenceEntity uniqueAction: uniqueActiveActionList){
				if(uniqueAction.getCk_01() != action.getCk_01()){
					uniqueActiveActionList.add(action);
					return;
				}
			}
		} else
			uniqueActiveActionList.add(action);		
	}

	private void addAccordionTab(Tree tree, String moduleName, Action action) {
		Tab tab = accordion.addTab(tree, action.getText(), IconHelper.getIcon16(action.getIcon()));
		tabMap.put(moduleName, tab);
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
	
	public void removeMenuTab(Module module) {
		try {
			accordion.removeTab(tabMap.get(module.getName()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void selectedTabChange(SelectedTabChangeEvent event) {
	}

	private void callActionView(Action action) {
		try {
			if (action == null || action.getClazz() == null)
				return;
			MainWindow mainWindow = null;
			mainWindow = (MainWindow) UI.getCurrent();
			mainWindow.executeAction(action);
		} catch (Exception e) {
			Notification.show("Hata Olustu : " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	@Override
	public void itemClick(ItemClickEvent event) {
		Object value = event.getItemId();
		selectedAction = (Action) value;
		callActionView(selectedAction);
	}

	@Override
	public void addModule(Module module) {
		addMenuTab(module);
	}

	@Override
	public void removeModule(Module module) {
		removeMenuTab(module);
	}

	
	
	public void decorateMenu(){
		List<Action> activeActionList = null; 
		activeActionList = getRelatedActions();
		try {
			if(activeActionList != null && activeActionList.size() > 0){
				for (Action action : activeActionList) {
					Tree tree = new Tree();
					tree.addItemClickListener(this);
					tree.addContainerProperty("icon", Resource.class, null);
					tree.addContainerProperty("caption", String.class, null);
					tree.setItemIconPropertyId("icon");
					tree.setItemCaptionPropertyId("caption");
					tree.setItemCaptionMode(ItemCaptionMode.PROPERTY);
					tree.setStyleName(Reindeer.TREE_CONNECTORS);
					tree.setSizeFull();
					addAccordionTab(tree, "Referans Yönetimi", action);
				}
			}
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
	}
	
	public List<Action> getRelatedActions() {
		
		User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());	
		Map<Long, Action> moduleActionList = new HashMap<Long, Action>();
		Map<Long, Action> menuActionList = new HashMap<Long, Action>();
		
		allMenuList = filterMenu(user);

		if(allMenuList != null && allMenuList.size() > 0)
		for (ReferenceEntity referenceEntity : allMenuList) {
				getMenuAcionRelation(referenceEntity);		
		}

		Action action = null;
		for (Long key : moduleList.keySet()) {
			action = createAction(moduleList.get(key));
			moduleActionList.put(key, action);			
		}
		
		List<ReferenceEntity> entityList = null;
		for (Long key : menuList.keySet()) {
			entityList = menuList.get(key);
			//action = createAction(entityList);
			if (moduleList.get(key) != null) {
				// module bagli
				for (ReferenceEntity entity : entityList) {
					action = createAction(entity);
					moduleActionList.get(key).addAction(action);
					menuActionList.put(key, action);
				}
			} else if (menuList.get(key) != null) {
				/*// o zaman baska bir menuye bagli.
				Action childAction = null;
				if (menuActionList.get(key) != null) {
					childAction = findAction(moduleActionList.values(), entityList.getId());
				} else {
					childAction = findAction(moduleActionList.values(), entityList.getCk_owner().getId());
					menuActionList.put(key, action);
				}
				childAction.addAction(action);*/
			}
		}
		
		Action rootAction = null;
		for (ReferenceEntity entity1 : viewList) {
			action = createAction(entity1);
			rootAction = findAction(moduleActionList.values(), entity1.getCk_owner().getId());
			if (rootAction != null)
				rootAction.addAction(action);
		}
		
		return new ArrayList<Action>(moduleActionList.values());

	}
	
	private void getMenuAcionRelation(ReferenceEntity referenceEntity) {
		
		if(roleIsAdmin){
			if (referenceEntity.getCk_01() != null && referenceEntity.getCk_01().getCode().equals("MD"))
				moduleList.put(referenceEntity.getId(), referenceEntity);
			else if (referenceEntity.getCk_01() != null && referenceEntity.getCk_01().getCode().equals("M")) {
				if (referenceEntity.getCk_owner() != null) {
					List<ReferenceEntity> list = menuList.get(referenceEntity.getCk_owner().getId());
					if (list == null)
						list = new ArrayList<ReferenceEntity>();
					list.add(referenceEntity);
					menuList.put(referenceEntity.getCk_owner().getId(), list);
				}
			} else if (referenceEntity.getCk_01() != null && referenceEntity.getCk_01().getCode().equals("W")) {
				if (referenceEntity.getCk_owner() != null && referenceEntity.getCk_owner().getId() != null) {
					viewList.add(referenceEntity);
				}
			}
		} else{
			
			if(referenceEntity.getCk_01() != null)
				if (referenceEntity.getCk_01().getCk_01() != null && referenceEntity.getCk_01().getCk_01().getCode().equals("MD"))
					moduleList.put(referenceEntity.getCk_01().getId(), referenceEntity.getCk_01());
				else if (referenceEntity.getCk_01().getCk_01() != null && referenceEntity.getCk_01().getCk_01().getCode().equals("M")) {
					if (referenceEntity.getCk_01().getCk_owner() != null) {
						List<ReferenceEntity> list = menuList.get(referenceEntity.getCk_01().getCk_owner().getId());
						if (list == null)
							list = new ArrayList<ReferenceEntity>();
						list.add(referenceEntity.getCk_01());
						menuList.put(referenceEntity.getCk_01().getCk_owner().getId(), list);
					}
				} else if (referenceEntity.getCk_01().getCk_01() != null && referenceEntity.getCk_01().getCk_01().getCode().equals("W")) {
					if (referenceEntity.getCk_01().getCk_owner() != null && referenceEntity.getCk_01().getCk_owner().getId() != null) {
						viewList.add(referenceEntity.getCk_01());
					}
				}
		}
	}

	private Action createAction(ReferenceEntity referenceEntity) {
		String code = referenceEntity.getCode() == null ? "0" : referenceEntity.getCode();
		Action menu = UnicaseActionManager.getInstance().createInstance("tr.org.unicase.kernel.web.view.action.internals.MenuActionImpl", referenceEntity.getId().toString(), null, "" + referenceEntity.getDescription(), referenceEntity.getValue(), referenceEntity.getShortvalue(), null, null, null);
		menu.setEntityTypeId(Long.parseLong(code));
		return menu;
	}
	
	private Action findAction(Collection<Action> actions, Long id) {
		Action result = null;
		for (Action action : actions) {
			if (action.getId().equals(id.toString())) {
				result = action;
				break;
			} else {
				result = findAction(action.getChildren(), id);
				if (result != null) 
					break;
			}
		}
		
		return result;
	}

	@Override
	public void fireEvent(LogEvent.Types type, Action action) {
		LogEvent logEvent = new LogEvent();
		logEvent.setType(type);
		logEvent.setIp(UI.getCurrent().getPage().getWebBrowser().getAddress());
		logEvent.setResult(action.getText() + " ekrani acildi");
		logEvent.setAction(action.getClass().getName());
		logEvent.setViewName(this.getClass().getSimpleName());
		logEvent.setEntity(null);
		fireEvent(logEvent);
	}

	@Override
	public void fireEvent(LogEvent event) {
		((IEventListener) UI.getCurrent()).fireEvent(event);
	}

	

}

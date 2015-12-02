package tr.org.unicase.web.app;

import tr.org.unicase.authentication.service.model.User;
import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.kernel.model.IEntity;
import tr.org.unicase.log.api.LogEvent;
import tr.org.unicase.web.app.api.WebUI;
import tr.org.unicase.web.app.internals.manager.UnicaseLogManager;
import tr.org.unicase.web.app.internals.manager.UnicaseViewManager;
import tr.org.unicase.web.component.UnicaseStatusBar;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.api.Module;
import tr.org.unicase.web.view.api.View;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalSplitPanel;

@Theme(value = "valo")
public class MainWindowImpl extends WebUI {

	private VerticalSplitPanel mainLayout = new VerticalSplitPanel();
	private HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();

	private AccordionMenuImpl menuPanel = new AccordionMenuImpl();
	private Component activePanel;
	private UnicaseStatusBar statusbarPanel = new UnicaseStatusBar();
	private Component component;
	private int count = 0;

	public MainWindowImpl() {
		super();
	}

	@Override
	protected void initUI(VaadinRequest request) {
		getPage().setTitle("Unicase");

		horizontalSplitPanel.setSplitPosition(300, Unit.PIXELS);
		horizontalSplitPanel.addComponent(menuPanel);

		mainLayout.addComponent(horizontalSplitPanel);
		mainLayout.setSplitPosition(95);
		mainLayout.addComponent(statusbarPanel);

		updateContent();
		mainLayout.setWidth(100, Unit.PERCENTAGE);
	}

	public void updateContent() {
		User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
		menuPanel.decorateMenu();
		if (user != null) {
			setMainLayout();
		} else {
			if (count != 0)
				Notification.show("Girmiş olduğunuz Kullanıcı Adı veya Şifre hatalıdır!", Type.ERROR_MESSAGE);
			count++;
			setContent(new LoginView(this));
		}
	}

	public void setMainLayout() {
		setContent(mainLayout);
		mainLayout.setWidth(100, Unit.PERCENTAGE);
		removeStyleName("loginview");
	}

	private void setContents(Component component) {
		setContents(component, true);
	}

	private void setContents(Component component, Boolean push) {
		horizontalSplitPanel.setSecondComponent(component);
		if (activePanel != null && push) {
			addViewToStack(activePanel);
		}
		activePanel = component;

	}

	public void removeMenu(Module module) {
		menuPanel.removeModule(module);
	}

	@Override
	public void executeAction(Action action) {
		callActionView(action);
		Runtime.getRuntime().gc();
		fireEvent(LogEvent.Types.INFO, action);
	}

	public void callActionView(Action action) {
		try {
			if (action == null || action.getClazz() == null)
				return;

			View<Controller<Object>> view = createComponent(action);
			if (view != null) {
				view.setIcon(action.getIcon());
				view.setName(action.getText());
				String viewName = view.getClass().getSimpleName();
				view = new ViewDecoratorImpl(view);

				if (action.getController() != null) {
					if (!viewName.endsWith("ReferenceDetailViewImp") && !viewName.endsWith("ReferenceParentViewImp"))
						view.getController().setEntity(action.getController().getEntity());
				}

				component = view.getView();
				setContents(component);

				view.initLayout();
				view.update();
			} else {
				throw new Exception(action.getClazz() + " bulundugu ekran paketi su an icin desteklenmiyor");
			}

		} catch (Exception e) {
			String errorMessage = "Hata: Ekran Acilmasi sirasinda hata [" + e.getClass().getSimpleName() + ", " + e.getMessage() + "] olustu. ";
			Notification.show(errorMessage, Notification.Type.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	private View<Controller<Object>> createComponent(Action action) throws Exception {
		String clazz = action.getClazz();
		Long firstArgument = null;
		Long ownerId = null;
		String refColumn = null;

		if (action.getOwnerEntityId() != null)
			ownerId = action.getOwnerEntityId();
		else if (action.getController() != null && action.getController().getEntity() != null)
			ownerId = ((IEntity) action.getController().getEntity()).getId();

		Field entityTypeRef = action.getField();

		if (entityTypeRef != null) {
			clazz = action.getClazz();

			refColumn = entityTypeRef.getRefColumn();
			if (entityTypeRef.getType() != null && entityTypeRef.getType().equals("NTO1")) {
				firstArgument = entityTypeRef.getParentTypeId();
			} else {
				firstArgument = entityTypeRef.getEntityTypeId();
			}
		} else {
			firstArgument = action.getEntityTypeId();
			refColumn = null;
		}

		return UnicaseViewManager.getInstance().createInstance(clazz, firstArgument, ownerId, refColumn);
	}

	@Override
	public void back() {
		if (isViewStackEmpty())
			return;
		component = getViewFromStack();
		setContents(component, false);
	}

	public void addMenu(Module module) {

	}

	@Override
	public void addModule(Module module) {
		addMenu(module);
	}

	@Override
	public void removeModule(Module module) {
		removeMenu(module);
	}

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
		String userName = ((User) VaadinSession.getCurrent().getAttribute(User.class.getName())).getUsername();
		event.setUserName(userName);
		statusbarPanel.addEvent(event.toString());
		UnicaseLogManager.getInstance().log(event);
	}

}

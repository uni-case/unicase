package tr.org.unicase.web.view.action.api;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.ConfirmDialog.Listener;

import tr.org.unicase.authentication.service.model.User;
import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.kernel.model.IEntity;
import tr.org.unicase.log.api.LogEvent;
import tr.org.unicase.log.api.LogEvent.Types;
import tr.org.unicase.web.component.UnicaseConfirmationDialog;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.api.IEventListener;
import tr.org.unicase.web.view.api.View;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

public abstract class AbstractAction<E> implements Action {

	private String id;

	protected Action ownerAction;

	private String icon;

	private String text;

	private String clazz;

	private List<Action> children = new LinkedList<Action>();

	protected Controller<?> controller;

	protected View<?> view;

	protected String name;

	private Long entityTypeId;

	private Long ownerEntityId;

	private Boolean needConfirmation = Boolean.FALSE;

	private Boolean checkLock = Boolean.TRUE;

	public AbstractAction(String id, Action ownerAction, String icon, String text, String clazz, Controller<?> controller, View<?> view, Long entityTypeId) {
		this(id, ownerAction, icon, text, clazz, controller, view, entityTypeId, Boolean.FALSE, Boolean.TRUE, null);

	}

	public AbstractAction(String id, Action ownerAction, String icon, String text, String clazz, Controller<?> controller, View<?> view, Long entityTypeId, Boolean needConfirmation, Boolean checkLock, Long ownerEntityId) {
		this.id = id;
		this.ownerAction = ownerAction;
		this.icon = icon;
		this.text = text;
		this.clazz = clazz;
		this.controller = controller;
		this.view = view;
		this.entityTypeId = entityTypeId;
		this.ownerEntityId = ownerEntityId;
		this.setNeedConfirmation(needConfirmation);
		this.setCheckLock(checkLock);
		if (ownerAction != null)
			ownerAction.addAction(this);

	}

	@Override
	public String getIcon() {

		return icon;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public Action getOwnerAction() {
		return this.ownerAction;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setOwnerAction(Action ownerAction) {
		this.ownerAction = ownerAction;
		if (ownerAction != null)
			ownerAction.addAction(this);
	}

	@Override
	public List<Action> getChildren() {
		return children;
	}

	@Override
	public void addAction(Action action) {
		children.add(action);

	}

	@Override
	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getStatusMessage() {
		return getName() + " is clicked!";
	}

	public void operate(String theName) throws Exception {
		if (controller.getEntity() != null)
			view.setViewToEntity();
		Method method = null;

		try {
			method = controller.getClass().getMethod(theName, controller.getEntity().getClass());
			if (method != null)
				method.invoke(controller, controller.getEntity());
			log();
			executeAfterController();
		} catch (Exception e) {
			Notification.show(theName + " Islemi Sirasinda Hata Olustu. " + e.getMessage(), Type.ERROR_MESSAGE);
			throw e;
		}

	}

	@Override
	public void execute() throws Exception {
		final String theName = getName();
		if (getNeedConfirmation()) {
			UnicaseConfirmationDialog.show(UI.getCurrent(), theName + " işlemini yapmak istediğinizden emin misiniz ?", new Listener() {

				public void onClose(ConfirmDialog dialog) {
					if (dialog.isConfirmed()) {
						// Confirmed to continue
						try {
							if (isCheckLock()) {
								if (!((IEntity) controller.getEntity()).isLocked() || (((IEntity) controller.getEntity()).getCk_owner() != null) && !(((IEntity) controller.getEntity()).getCk_owner().isLocked()))
									operate(theName);
								else
									Notification.show("Ekran verileri kilitlenmistir. Yaptiginiz degisiklikler uygulanmayacaktir. Kilidi kaldirmak icin sistem yoneticiniz ile gorusunuz.", Type.ERROR_MESSAGE);
							} else
								operate(theName);
						} catch (Exception e) {
							e.printStackTrace();
							Notification.show("HATA : " + e.getMessage(), Type.ERROR_MESSAGE);
						}
					}
				}
			});
		} else {
			try {
				if (isCheckLock()) {
					if (!((IEntity) controller.getEntity()).isLocked() || (((IEntity) controller.getEntity()).getCk_owner() != null) && !(((IEntity) controller.getEntity()).getCk_owner().isLocked()))
						operate(theName);
					else
						Notification.show("Ekran verileri kilitlenmistir. Yaptiginiz degisiklikler uygulanmayacaktir. Kilidi kaldirmak icin sistem yoneticiniz ile gorusunuz.", Type.ERROR_MESSAGE);
				} else {
					operate(theName);
				}
			} catch (Exception e) {
				Notification.show("HATA : " + e.getMessage(), Type.ERROR_MESSAGE);
			}
		}

	}

	protected abstract void executeAfterController() throws Exception;

	public String getName() {
		String simpleName = this.getClass().getSimpleName();
		name = simpleName.substring(0, simpleName.indexOf("Action")).toLowerCase();
		return name;
	}

	@Override
	public Long getEntityTypeId() {
		return entityTypeId;
	}

	public void setEntityTypeId(Long entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	@Override
	public Controller<?> getController() {
		return controller;
	}

	public Long getOwnerEntityId() {
		return ownerEntityId;
	}

	public void setOwnerEntityId(Long ownerEntityId) {
		this.ownerEntityId = ownerEntityId;
	}

	public Boolean getNeedConfirmation() {
		return needConfirmation;
	}

	public void setNeedConfirmation(Boolean needConfirmation) {
		this.needConfirmation = needConfirmation;
	}

	protected LogEvent prepareLogEvent(LogEvent.Types eventType) {
		LogEvent logEvent = new LogEvent();
		logEvent.setType(eventType);
		User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
		logEvent.setUserName(user.getName());
		user = null;
		logEvent.setIp(UI.getCurrent().getPage().getWebBrowser().getAddress());
		logEvent.setResult(this.getClass().getName());
		logEvent.setAction(this.getClass().getName());
		logEvent.setViewName(view.getName());
		logEvent.setCreateDate(new Date());
		logEvent.setEntity(null);

		return logEvent;
	}

	protected LogEvent prepareLogEvent() {
		return prepareLogEvent(Types.INFO);
	}

	protected void log(LogEvent logEvent) {
		((IEventListener) UI.getCurrent()).fireEvent(logEvent);
	}

	protected void log() {
		LogEvent logEvent = prepareLogEvent();
		log(logEvent);
	}

	public Boolean isCheckLock() {
		return checkLock;
	}

	public void setCheckLock(Boolean checkLock) {
		this.checkLock = checkLock;
	}

	@Override
	public Field getField() {
		return null;
	}

}

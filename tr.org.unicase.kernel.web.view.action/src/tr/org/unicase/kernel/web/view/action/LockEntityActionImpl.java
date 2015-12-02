package tr.org.unicase.kernel.web.view.action;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.ConfirmDialog.Listener;

import tr.org.unicase.kernel.model.IEntity;
import tr.org.unicase.log.api.LogEvent;
import tr.org.unicase.web.component.UnicaseConfirmationDialog;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.action.api.AbstractAction;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.api.View;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

public class LockEntityActionImpl<E> extends AbstractAction<E> {

	public LockEntityActionImpl(String id, Action ownerAction, String icon, String text, String clazz, Controller<E> controller, View<?> view) {
		super(id, ownerAction, icon, text, clazz, controller, view, controller.getEntityTypeId());
	}

	@Override
	public void execute() throws Exception {
		final IEntity entity = (IEntity) controller.getEntity();
		if (entity != null && entity.getId() != null && entity.getId() > 0) {
			UnicaseConfirmationDialog.show(UI.getCurrent(), entity.toString() + " 'i kilitlemek istediğinizden emin misiniz ?", new Listener() {

				public void onClose(ConfirmDialog dialog) {
					if (dialog.isConfirmed()) {
						// Confirmed to continue
						try {
							if (entity.isLocked())
								Notification.show("Ekran verileri zaten kilitlenmis.", Type.HUMANIZED_MESSAGE);
							else
								operate();
						} catch (Exception e) {
							Notification.show("HATA : " + e.getMessage(), Type.ERROR_MESSAGE);
						}
					}
				}
			});
		}
	}

	public void operate() throws Exception {
		operate("lock");
	}

	@Override
	protected void log() {
		IEntity entity = (IEntity) controller.getEntity();
		LogEvent logEvent = prepareLogEvent();
		logEvent.setEntity(entity);
		logEvent.setResult(entity.getId() + " kilitlendi");
		log(logEvent);
	}

	@Override
	public String getName() {
		return super.getName();
	}

	@Override
	protected void executeAfterController() {
		Notification.show("Kilitleme İşlemi Başarı İle Gerçekleştirildi.", Type.HUMANIZED_MESSAGE);
		view.update();
		view.refresh();
	}
}
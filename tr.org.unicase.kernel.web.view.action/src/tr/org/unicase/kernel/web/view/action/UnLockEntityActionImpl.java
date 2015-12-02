package tr.org.unicase.kernel.web.view.action;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.ConfirmDialog.Listener;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

import tr.org.unicase.kernel.model.IEntity;
import tr.org.unicase.log.api.LogEvent;
import tr.org.unicase.web.component.UnicaseConfirmationDialog;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.action.api.AbstractAction;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.api.View;


public class UnLockEntityActionImpl<E> extends AbstractAction<E> {

	public UnLockEntityActionImpl(String id, Action ownerAction, String icon, String text, String clazz, Controller<E> controller, View<?> view) {
		super(id, ownerAction, icon, text, clazz, controller, view, controller.getEntityTypeId());
	}

	@Override
	public void execute() throws Exception {
		final IEntity entity = (IEntity) controller.getEntity();
		if (entity != null && entity.getId() != null && entity.getId() > 0) {
			UnicaseConfirmationDialog.show(UI.getCurrent(), entity.toString() + " 'i kilitli kaydı açmak istediğinizden emin misiniz ?", new Listener() {

				public void onClose(ConfirmDialog dialog) {
					if (dialog.isConfirmed()) {
						// Confirmed to continue
						try {
							if (entity.isLocked())
								operate();
							else
								Notification.show("Ekran verileri zaten kitli degil.", Type.HUMANIZED_MESSAGE);
						} catch (Exception e) {
							e.printStackTrace();
							Notification.show("HATA : " + e.getMessage(), Type.ERROR_MESSAGE);
						}
					}
				}
			});
		}
	}

	public void operate() throws Exception {
		operate("unlock");
	}
	
	@Override
	protected void log() {
		IEntity entity = (IEntity) controller.getEntity();
		LogEvent logEvent = prepareLogEvent();
		logEvent.setEntity(entity);
		logEvent.setResult(entity.getId() + " uzerindeki kilit acildi");
		log(logEvent);
	}

	@Override
	public String getName() {
		return super.getName();
	}

	@Override
	protected void executeAfterController() {
		Notification.show("Kilit Açma İşlemi Başarı İle Gerçekleştirildi.", Type.HUMANIZED_MESSAGE);
		view.update();
		view.refresh();
	}
}
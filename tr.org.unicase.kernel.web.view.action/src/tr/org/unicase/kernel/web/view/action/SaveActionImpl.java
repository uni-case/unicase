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

public class SaveActionImpl<E> extends AbstractAction<E> {

	public SaveActionImpl(String id, Action ownerAction, String icon, String text, String clazz, Controller<E> controller, View<?> view) {
		super(id, ownerAction, icon, text, clazz, controller, view, controller.getEntityTypeId());
	}

	@Override
	public void execute() throws Exception {
		final String theName = getName();
		if (view.validate()) {
			if (getNeedConfirmation()) {
				UnicaseConfirmationDialog.show(UI.getCurrent(), theName + " işlemini yapmak istediğinizden emin misiniz ?", new Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							// Confirmed to continue
							try {
								if (isCheckLock()) {
									if ((IEntity) controller.getEntity() != null && (!((IEntity) controller.getEntity()).isLocked() || (((IEntity) controller.getEntity()).getCk_owner() != null) && !(((IEntity) controller.getEntity()).getCk_owner().isLocked()))) {
										operate(theName);
										Notification.show("Kaydetme İşlemi Başarı İle Gerçekleştirildi.", Type.HUMANIZED_MESSAGE);
									} else
										Notification.show("Ekran verileri kilitlenmistir. Yaptiginiz degisiklikler uygulanmayacaktir. Kilidi kaldirmak icin sistem yoneticiniz ile gorusunuz.", Type.ERROR_MESSAGE);
								} else {
									operate(theName);
									Notification.show("Kaydetme İşlemi Başarı İle Gerçekleştirildi.", Type.HUMANIZED_MESSAGE);
								}
							} catch (Exception e) {
								Notification.show("HATA : " + e.getMessage(), Type.ERROR_MESSAGE);
							}
						}
					}
				});
			} else {
				if (isCheckLock()) {
					// Notification.show((controller.getEntity() != null ?
					// controller.getEntity().toString() : "bos"));
					if ((IEntity) controller.getEntity() != null && (!((IEntity) controller.getEntity()).isLocked() || (((IEntity) controller.getEntity()).getCk_owner() != null) && !(((IEntity) controller.getEntity()).getCk_owner().isLocked()))) {
						operate(theName);
						Notification.show("Kaydetme İşlemi Başarı İle Gerçekleştirildi.", Type.HUMANIZED_MESSAGE);
					} else
						Notification.show("Ekran verileri kilitlenmistir. Yaptiginiz degisiklikler uygulanmayacaktir. Kilidi kaldirmak icin sistem yoneticiniz ile gorusunuz.", Type.ERROR_MESSAGE);
				} else {
					operate(theName);
				}
			}
		} else {
			Notification.show("Hata", "Alanlarin girisi sirasinda hata olustu. ! isareti olan alanlari kontrol ediniz.", Type.ERROR_MESSAGE);
		}
	}

	@Override
	protected void log() {
		IEntity entity = (IEntity) controller.getEntity();
		LogEvent logEvent = prepareLogEvent();
		logEvent.setEntity(entity);
		logEvent.setResult(entity.getId() + " save islemi yapildi");
		log(logEvent);
	}

	@Override
	protected void executeAfterController() {
		view.refresh();
		view.update();
	}

}

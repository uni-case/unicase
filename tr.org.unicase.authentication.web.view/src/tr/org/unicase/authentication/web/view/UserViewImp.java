package tr.org.unicase.authentication.web.view;

import java.util.Date;

import tr.org.unicase.authentication.service.UserControllerImp;
import tr.org.unicase.authentication.service.model.User;
import tr.org.unicase.authentication.web.view.internals.config.Configuration;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.action.api.UnicaseActionManager;
import tr.org.unicase.web.view.api.AbstractView;
import tr.org.unicase.web.view.validator.NullValidator;

import com.vaadin.data.Validator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class UserViewImp extends AbstractView<UserControllerImp> {
	private GridLayout mainGridLayout = new GridLayout(2, 1);
	private VerticalLayout leftVerticalLayout;

	private TextField textField_Name;
	private TextField textField_Surname;
	private TextField textField_Email;
	private TextField textField_UserName;
	private PasswordField textField_Password;
	private TextField textField_Role;
	private DateField dateField_ExpireDate;

	public UserViewImp(Long entityTypeId, Long ownerId, String refOwner) {
		this.entityTypeId = entityTypeId;
		this.ownerId = ownerId;
		this.refOwner = refOwner;
		setController(new UserControllerImp(entityTypeId, ownerId, refOwner));
		buildLeftVerticalLayout();

		buildLayout();

		if (leftVerticalLayout != null)
			mainGridLayout.addComponent(leftVerticalLayout, 0, 0);

		Action action = null;

		action = UnicaseActionManager.getInstance().createInstance(Configuration.ViewAction.SAVE, Configuration.ViewAction.ID, null, Configuration.ViewAction.SAVE_ICON, Configuration.ViewAction.SAVE_CAPTION, Configuration.ViewAction.ACTION_CLASS, controller, this, null);
		if (action != null)
			actionList.add(action);
		Boolean isRoleAdmin = controller.isUserAdmin();
		if (isRoleAdmin) {
			action = UnicaseActionManager.getInstance().createInstance(Configuration.ViewAction.GENERATE, Configuration.ViewAction.ID, null, Configuration.ViewAction.GENERATE_ICON, Configuration.ViewAction.GENERATE_CAPTION, Configuration.ViewAction.ACTION_CLASS, controller, this, null);
			if (action != null)
				actionList.add(action);
			action = UnicaseActionManager.getInstance().createInstance(Configuration.ViewAction.DELETE, Configuration.ViewAction.ID, null, Configuration.ViewAction.DELETE_ICON, Configuration.ViewAction.DELETE_CAPTION, Configuration.ViewAction.ACTION_CLASS, controller, this, null);
			if (action != null)
				actionList.add(action);
		}

		action = null;
	}

	private void buildLayout() {
		textField_Name = (TextField) createComponent(textField_Name, TextField.class, Configuration.UserViewImp.USER.NAME_HEADER);
		textField_Name.addValidator(getNullValidator(Configuration.UserViewImp.USER.NAME));

		textField_Surname = (TextField) createComponent(textField_Surname, TextField.class, Configuration.UserViewImp.USER.SURNAME_HEADER);
		textField_Surname.addValidator(getNullValidator(Configuration.UserViewImp.USER.SURNAME));

		textField_Email = (TextField) createComponent(textField_Email, TextField.class, Configuration.UserViewImp.USER.EMAIL_HEADER);
		textField_Email.addValidator(getNullValidator(Configuration.UserViewImp.USER.EMAIL));

		textField_UserName = (TextField) createComponent(textField_UserName, TextField.class, Configuration.UserViewImp.USER.USERNAME_HEADER);
		textField_UserName.addValidator(getNullValidator(Configuration.UserViewImp.USER.USERNAME));

		textField_Password = (PasswordField) createComponent(textField_Password, PasswordField.class, Configuration.UserViewImp.USER.PASSWORD_HEADER);
		textField_Password.addValidator(getNullValidator(Configuration.UserViewImp.USER.PASSWORD));

		if (controller.isUserAdmin()) {
			textField_Role = (TextField) createComponent(textField_Role, TextField.class, Configuration.UserViewImp.USER.ROLE_HEADER);
			textField_Role.addValidator(getNullValidator(Configuration.UserViewImp.USER.ROLE));

			dateField_ExpireDate = (DateField) createComponent(dateField_ExpireDate, DateField.class, Configuration.UserViewImp.USER.EXPIREDATE_HEADER);
			// dateField_ExpireDate.addValidator(getNullValidator(Configuration.UserViewImp.USER.EXPIREDATE));
		}
	}

	private Component createComponent(Object object, Class<?> clazz, Label label) {
		Component component = null;
		try {
			object = clazz.newInstance();
			component = (Component) object;
			component.setWidth(Configuration.UserViewImp.UI.WIDTH);
			component.setHeight(Configuration.UserViewImp.UI.HEIGHT);
			if (AbstractTextField.class.isAssignableFrom(clazz)) {
				((AbstractTextField) component).setNullRepresentation(Configuration.EMPTY);
				((AbstractTextField) component).setValidationVisible(false);
				((AbstractTextField) component).setImmediate(true);
			} else if (DateField.class.isAssignableFrom(clazz))
				((DateField) component).setDateFormat(Configuration.DATE_FORMAT);

			GridLayout gridLayout = new GridLayout(2, 1);
			gridLayout.setColumnExpandRatio(0, 3);
			gridLayout.setColumnExpandRatio(1, 2);
			gridLayout.setSpacing(false);
			gridLayout.setMargin(false);
			label.setWidth(Configuration.UserViewImp.UI.LABEL_WIDTH);
			label.setStyleName(ValoTheme.LABEL_SMALL);
			gridLayout.addComponent(label, 0, 0);
			gridLayout.addComponent(component, 1, 0);
			gridLayout.setComponentAlignment(component, Alignment.MIDDLE_LEFT);
			leftVerticalLayout.addComponent(gridLayout);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return component;
	}

	private void buildLeftVerticalLayout() {
		leftVerticalLayout = new VerticalLayout();
		leftVerticalLayout.setSpacing(true);
		leftVerticalLayout.setMargin(true);
	}

	@Override
	public void initLayout() {

	}

	@Override
	public Component getView() {
		mainVerticalLayout.addComponent(mainGridLayout);
		return mainVerticalLayout;
	}

	private Validator getNullValidator(final String fieldName) {
		return new NullValidator(fieldName);
	}

	@Override
	public void update() {
		User user = controller.getEntity();
		User currentUser = (User) VaadinSession.getCurrent().getAttribute(Configuration.USER_CLASS_NAME);
		if (user == null)
			return;
		textField_Name.setValue(user.getName());
		textField_Surname.setValue(user.getSurname());
		textField_Email.setValue(user.getEmail());
		textField_UserName.setValue(user.getUserName());
		if (currentUser.getRole().equalsIgnoreCase(Configuration.ROLE_ADMIN)) {
			textField_UserName.setEnabled(true);
			textField_Email.setEnabled(true);
		} else {
			textField_UserName.setEnabled(false);
			textField_Email.setEnabled(false);
		}
		textField_Password.setValue(user.getClear());
		if (textField_Role != null)
			textField_Role.setValue(user.getRole());

		if (dateField_ExpireDate != null)
			dateField_ExpireDate.setValue(user.getExpireDate());

	}

	@Override
	public void setViewToEntity() {
		User user = controller.getEntity();
		user.setName((String) textField_Name.getValue());
		user.setSurname((String) textField_Surname.getValue());
		user.setEmail((String) textField_Email.getValue());
		user.setUserName((String) textField_UserName.getValue());
		user.setClear((String) (textField_Password.getValue()));

		if (textField_Role != null)
			user.setRole((String) (textField_Role.getValue()));

		if (dateField_ExpireDate != null)
			user.setExpireDate((Date) dateField_ExpireDate.getValue());

		if (textField_Password.getValue() != null)
			user.setEncrypted(controller.encodePassword((String) textField_Password.getValue()));
	}

	@Override
	public void refresh() {

	}

}

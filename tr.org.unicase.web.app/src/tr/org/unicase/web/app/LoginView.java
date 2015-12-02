package tr.org.unicase.web.app;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import tr.org.unicase.authentication.service.model.User;
import tr.org.unicase.web.app.internals.manager.UnicaseAuthenticationManager;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class LoginView extends VerticalLayout {

	private static final Logger LOGGER = Logger.getLogger(LoginView.class.getName());

	private MainWindowImpl mainWindow = null;

	public LoginView(MainWindowImpl mainWindow) {
		setSizeFull();
		this.mainWindow = mainWindow;
		Component loginForm = buildLoginForm();
		addComponent(loginForm);
		setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
		removeStyleName(this.getStyleName());
		this.mainWindow.removeStyleName(getStyleName());
	}

	private Component buildLoginForm() {
		final VerticalLayout loginPanel = new VerticalLayout();
		loginPanel.setSizeUndefined();
		loginPanel.setSpacing(true);
		Responsive.makeResponsive(loginPanel);
		loginPanel.addComponent(buildLabels());
		loginPanel.addComponent(buildFields());
		return loginPanel;
	}

	private Component buildFields() {
		HorizontalLayout fields = new HorizontalLayout();
		fields.setSpacing(true);
		fields.addStyleName("fields");

		final TextField username = new TextField("Kullanıcı Adı");
		username.setIcon(FontAwesome.USER);
		username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		final PasswordField password = new PasswordField("Şifre");
		password.setIcon(FontAwesome.LOCK);
		password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		final Button signin = new Button("Giriş");
		signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
		signin.setClickShortcut(KeyCode.ENTER);
		signin.focus();

		fields.addComponents(username, password, signin);
		fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

		signin.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				String userName = username.getValue().trim();
				String pass = password.getValue().trim();
				if (!userName.isEmpty() && !pass.isEmpty()) {

					User user = null;
					Date currentDate = new Date();
					try {
						user = UnicaseAuthenticationManager.getInstance().authenticate(userName, pass);
						if (user != null) {
							VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
							if (user.getExpireDate() == null || currentDate.compareTo(user.getExpireDate()) < 0)
								mainWindow.updateContent();
							else {
								Notification.show("Hesap Geçerlilik Süreniz Dolmuştur. Lütfen Sistem Yöneticisi İle İrtibata geçiniz.", Type.ERROR_MESSAGE);
								VaadinSession.getCurrent().close();
							}
						} else {
							Notification.show("Login Sistemi Calismiyor.", Type.ERROR_MESSAGE);
						}
					} catch (Exception e) {
						Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
						LOGGER.log(Level.SEVERE, e.getMessage(), e);
						e.printStackTrace();
					}
				} else {
					Notification.show("Kullanıcı Adı ve Şifre Bilgileri Boş Bırakılamaz.", Type.ERROR_MESSAGE);
				}
			}
		});
		return fields;
	}

	private Component buildLabels() {
		CssLayout labels = new CssLayout();
		labels.addStyleName("labels");

		Label welcome = new Label("Unicase");
		welcome.setSizeUndefined();
		welcome.addStyleName(ValoTheme.LABEL_BOLD);
		welcome.addStyleName(ValoTheme.LABEL_H2);
		welcome.addStyleName(ValoTheme.LABEL_COLORED);
		labels.addComponent(welcome);

		Label title = new Label("UNICASE");
		title.setSizeUndefined();
		title.addStyleName(ValoTheme.LABEL_H2);
		title.addStyleName(ValoTheme.LABEL_LIGHT);
		labels.addComponent(title);
		return labels;
	}
}

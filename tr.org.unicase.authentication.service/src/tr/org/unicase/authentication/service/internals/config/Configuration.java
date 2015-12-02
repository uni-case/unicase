package tr.org.unicase.authentication.service.internals.config;

import com.vaadin.ui.Label;

import tr.org.unicase.authentication.service.model.User;

public interface Configuration {

	public static final String ROLE_SEPERATOR = ",";
	public static final String ROLE_ADMIN = "admin";
	public static final String DATE_FORMAT = "dd.MM.yyyy";
	public static final int STATUS_ADD = 1;
	public static final int STATUS_DELETE = 0;
	public static final String USER_CLASS_NAME = User.class.getName();
	public static final String EMPTY = "";

	static interface Bundle {
		public static final String NAME = "Unicase Authentication Service Bundle";
		public static final String START_MESSAGE = NAME + " Started";
		public static final String STOP_MESSAGE  = NAME + " Stopped";
	}
	
	static interface ExceptionMessage {
		public static final String BAD_CREDENTIALS = "Kullanıcı ve/veya Şifre Yanlış.";
	}
	
	static interface ViewAction {
		public static final String ID = "1";
		public static final String ACTION_CLASS = "clazz";
		public static final String SAVE = "tr.org.unicase.kernel.web.view.action.SaveActionImpl";
		public static final String SAVE_ICON = "save.png";
		public static final String SAVE_CAPTION = "Kaydet";
		public static final String GENERATE = "tr.org.unicase.kernel.web.view.action.GenerateActionImpl";
		public static final String GENERATE_ICON = "new.png";
		public static final String GENERATE_CAPTION = "Yeni";
		public static final String DELETE = "tr.org.unicase.kernel.web.view.action.DeleteActionImpl";
		public static final String DELETE_ICON = "delete.png";
		public static final String DELETE_CAPTION = "Sil";
		public static final String OPEN = "tr.org.unicase.kernel.web.view.action.OpenActionImpl";
		public static final String OPEN_ICON = "open.png";
		public static final String OPEN_CAPTION = "Incele";
	}
	
	static interface UserViewImp {

		public static final String NAME = UserViewImp.class.getName();
		
		static interface USER {
			public static final String NAME = "Name";
			public static final Label NAME_HEADER = new Label("Ad");
			public static final String SURNAME = "Surname";
			public static final Label SURNAME_HEADER = new Label("Soyad");
			public static final String EMAIL = "Email";
			public static final Label EMAIL_HEADER = new Label("Email");
			public static final String USERNAME = "UserName";
			public static final Label USERNAME_HEADER = new Label("Kullanici Adi");
			public static final String PASSWORD = "Password";
			public static final Label PASSWORD_HEADER = new Label("Sifre");
			public static final String ROLE = "Role";
			public static final Label ROLE_HEADER = new Label("Rol");
			public static final String EXPIREDATE = "expireDate";
			public static final Label EXPIREDATE_HEADER = new Label("Gecerlilik Suresi");
		}

		static interface UI {
			public static final String WIDTH = "450px";
			public static final String LABEL_WIDTH = "450px";
			public static final String HEIGHT = "24px";

		}

	}

}

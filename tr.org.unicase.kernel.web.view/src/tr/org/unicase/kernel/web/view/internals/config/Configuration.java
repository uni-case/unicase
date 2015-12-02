package tr.org.unicase.kernel.web.view.internals.config;

public interface Configuration {

	static interface Bundle {
		public static final String NAME = "Unicase Kernel Web View";
		public static final String START_MESSAGE = NAME + " STARTED";
		public static final String STOP_MESSAGE = NAME + " STOPPED";
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
	
}

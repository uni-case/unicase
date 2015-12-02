package tr.org.unicase.web.view.api.internals.config;

import java.util.logging.Logger;

import tr.org.unicase.web.view.action.api.UnicaseActionManager;


public interface Configuration {

	static interface Bundle {
		public static final String NAME = "Unicase Web View API Bundle";
	}
	
	static interface ActionManager {
		public static final Logger LOGGER = Logger.getLogger(UnicaseActionManager.class.getSimpleName());
		
		public static final String LISTENING        = " ### ActionManager # LISTENING FOR FACTORIES";
		public static final String FORMAT_ADD       = " ### ActionManager # FACTORY %s REGISTERED";
		public static final String FORMAT_REMOVE    = " ### ActionManager # FACTORY %s UNREGISTERED";
		public static final String FORMAT_NOT_FOUND = " ### ActionManager # %s NOT FOUND IN REGISTERED FACTORIES";
	}
	
}

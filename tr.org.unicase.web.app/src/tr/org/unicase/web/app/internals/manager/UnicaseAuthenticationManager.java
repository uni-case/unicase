package tr.org.unicase.web.app.internals.manager;

import java.util.logging.Logger;

import tr.org.unicase.authentication.api.IAuthenticationService;
import tr.org.unicase.authentication.service.model.User;
import tr.org.unicase.web.app.api.IUnicaseManager;
import tr.org.unicase.web.app.internals.config.Configuration;

public class UnicaseAuthenticationManager implements IAuthenticationService, IUnicaseManager<IAuthenticationService> {

	private static final Logger LOGGER = Logger.getLogger(UnicaseAuthenticationManager.class.getSimpleName());
	private static UnicaseAuthenticationManager INSTANCE = null;
	private IAuthenticationService service = null;

	private UnicaseAuthenticationManager() {
	}

	public static UnicaseAuthenticationManager getInstance() {
		if (INSTANCE == null)
			INSTANCE = new UnicaseAuthenticationManager();
		return INSTANCE;
	}

	@Override
	public String getDescription() {
		return Configuration.AuthenticationManager.DESCRIPTION;
	}

	@Override
	public void addService(IAuthenticationService service) {
		this.service = service;
		LOGGER.info(String.format(Configuration.AuthenticationManager.FORMAT_ADD, service.getName()));
	}

	@Override
	public void removeService(IAuthenticationService service) {
		LOGGER.info(String.format(Configuration.AuthenticationManager.FORMAT_REMOVE, service.getName()));
		this.service = null;
	}

	@Override
	public void clear() {
		this.service = null;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public String getServiceNames() {
		StringBuilder builder = new StringBuilder();

		if (this.service != null) {
			builder.append(this.service.getName());
		} else {
			builder.append("No Service");
		}

		return builder.toString();
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public User authenticate(String username, String password) throws Exception {
		if (this.service == null)
			return null;
		return this.service.authenticate(username, password);
	}

	@Override
	public int getFactoryId() {
		return getName().hashCode();
	}

}

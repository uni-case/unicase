package tr.org.unicase.web.app.internals.manager;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import tr.org.unicase.web.app.api.IUnicaseManager;
import tr.org.unicase.web.app.internals.config.Configuration;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.api.IViewFactory;
import tr.org.unicase.web.view.api.View;

public class UnicaseViewManager implements IViewFactory, IUnicaseManager<IViewFactory> {

	private static final Logger LOGGER = Logger.getLogger(UnicaseViewManager.class.getName());
	private static UnicaseViewManager INSTANCE = null;
	private List<IViewFactory> services = null;

	private UnicaseViewManager() {
		services = new LinkedList<IViewFactory>();
	}

	public static UnicaseViewManager getInstance() {
		if (INSTANCE == null)
			INSTANCE = new UnicaseViewManager();
		return INSTANCE;
	}

	@Override
	public String getDescription() {
		return Configuration.ViewManager.DESCRIPTION;
	}

	@Override
	public void addService(IViewFactory service) {
		this.services.add(service);
		LOGGER.info(String.format(Configuration.ViewManager.FORMAT_ADD, service.getName()));
	}

	@Override
	public void removeService(IViewFactory service) {
		this.services.remove(service);
		LOGGER.info(String.format(Configuration.ViewManager.FORMAT_REMOVE, service.getName()));
	}

	@Override
	public void clear() {
		LOGGER.info(Configuration.ViewManager.FORMAT_CLEAN);
		if (this.services.size() > 0)
			this.services.clear();
	}

	@Override
	public int size() {
		return this.services.size();
	}

	@Override
	public String getServiceNames() {
		StringBuilder builder = new StringBuilder();

		if (this.services != null && this.services.size() > 0) {
			for (IViewFactory factory : this.services)
				builder.append(factory.getName()).append(Configuration.ViewManager.SEPERATOR);
		} else {
			builder.append(Configuration.ViewManager.NO_SERVICE);
		}

		return builder.toString();
	}

	@Override
	public int getFactoryId() {
		return this.getName().hashCode();
	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}

	@Override
	public View<Controller<Object>> createInstance(String clazz, Long entityTypeId, Long ownerId, String refOwner) {
		View<Controller<Object>> view = null;

		if (this.services != null && this.services.size() > 0) {
			for (IViewFactory factory : this.services) {
				view = factory.createInstance(clazz, entityTypeId, ownerId, refOwner);
				if (view != null)
					break;
			}
		}

		if (view == null)
			LOGGER.severe(String.format(Configuration.ViewManager.FORMAT_NOT_FOUND, clazz));

		return view;
	}
}

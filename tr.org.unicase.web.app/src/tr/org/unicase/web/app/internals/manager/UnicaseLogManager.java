package tr.org.unicase.web.app.internals.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import tr.org.unicase.log.api.IUnicaseLogService;
import tr.org.unicase.log.api.LogEvent;
import tr.org.unicase.web.app.api.IUnicaseManager;
import tr.org.unicase.web.app.internals.config.Configuration;

public class UnicaseLogManager implements IUnicaseLogService, IUnicaseManager<IUnicaseLogService> {

	private static final Logger LOGGER = Logger.getLogger(UnicaseLogManager.class.getSimpleName());
	private static UnicaseLogManager INSTANCE = null;
	private List<IUnicaseLogService> services = null;

	private UnicaseLogManager() {
		services = new ArrayList<IUnicaseLogService>(4);
	}

	public static UnicaseLogManager getInstance() {
		if (INSTANCE == null)
			INSTANCE = new UnicaseLogManager();
		return INSTANCE;
	}

	@Override
	public String getDescription() {
		return Configuration.LogManager.DESCRIPTION;
	}

	@Override
	public void addService(IUnicaseLogService service) {
		this.services.add(service);
		LOGGER.info(String.format(Configuration.LogManager.FORMAT_ADD, service.getName()));
	}

	@Override
	public void removeService(IUnicaseLogService service) {
		this.services.remove(service);
		LOGGER.info(String.format(Configuration.LogManager.FORMAT_REMOVE, service.getName()));
	}

	@Override
	public void clear() {
		LOGGER.info(Configuration.LogManager.FORMAT_CLEAN);
		if (this.services.size() > 0)
			this.services.clear();
	}

	@Override
	public int size() {
		return (this.services != null ? this.services.size() : -1);
	}

	@Override
	public String getServiceNames() {
		StringBuilder builder = new StringBuilder();

		if (this.services.size() > 0)
			for (IUnicaseLogService factory : this.services)
				builder.append(factory.getName()).append(Configuration.LogManager.SEPERATOR);
		else
			builder.append(Configuration.LogManager.NO_SERVICE);

		return builder.toString();
	}

	@Override
	public void log(final LogEvent logEvent) {
		if (this.services != null && this.services.size() > 0) {
			ExecutorService threadPool = Executors.newFixedThreadPool(this.services.size());
			for (final IUnicaseLogService service : this.services) {
				threadPool.submit(new Runnable() {

					@Override
					public void run() {
						service.log(logEvent);
					}
				});
			}
		}
	}

	@Override
	public int getFactoryId() {
		return this.getName().hashCode();
	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}

}

package tr.org.unicase.web.view.action.api;

import java.util.HashMap;
import java.util.Map;

import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.api.View;
import tr.org.unicase.web.view.api.internals.config.Configuration;

public class UnicaseActionManager implements IActionFactory {

	private static UnicaseActionManager INSTANCE = null;
	private Map<Integer, IActionFactory> factoryMap = null;

	private UnicaseActionManager() {
		factoryMap = new HashMap<Integer, IActionFactory>();
	}

	public void addActionFactory(IActionFactory factory) {
		if (factory != null) {
			if (!factoryMap.containsKey(factory.getFactoryId())) {
				factoryMap.put(factory.getFactoryId(), factory);
				Configuration.ActionManager.LOGGER.info(String.format(Configuration.ActionManager.FORMAT_ADD, factory.getName()));
			}
		}
	}

	public void removeActionFactory(IActionFactory factory) {
		if (factory != null) {
			if (factoryMap.containsKey(factory.getFactoryId())) {
				this.factoryMap.remove(factory.getFactoryId());
				Configuration.ActionManager.LOGGER.info(String.format(Configuration.ActionManager.FORMAT_REMOVE, factory.getName()));
			}
		}
	}

	public static UnicaseActionManager getInstance() {
		if (INSTANCE == null)
			INSTANCE = new UnicaseActionManager();
		return INSTANCE;
	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}

	@Override
	public int getFactoryId() {
		return this.getName().hashCode();
	}

	@Override
	public Action createInstance(String clazz, String id, Action ownerAction, String icon, String text, String actionClass, Controller<?> controller, View<?> view, Field field) {
		Action result = null;

		for (IActionFactory factory : this.factoryMap.values()) {
			result = factory.createInstance(clazz, id, ownerAction, icon, text, actionClass, controller, view, field);
			if (result != null) 
				break;
		}

		if (result == null)
			Configuration.ActionManager.LOGGER.severe(String.format(Configuration.ActionManager.FORMAT_NOT_FOUND, clazz));
		
		return result;
	}

}
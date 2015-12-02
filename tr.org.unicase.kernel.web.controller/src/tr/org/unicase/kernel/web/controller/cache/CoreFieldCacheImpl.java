package tr.org.unicase.kernel.web.controller.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.kernel.service.FieldServiceImp;
import tr.org.unicase.kernel.web.controller.ICachable;
import tr.org.unicase.kernel.web.controller.activator.KernelControllerActivator;
import tr.org.unicase.service.api.Service;

import com.hazelcast.core.MultiMap;
import com.hazelcast.monitor.LocalMapStats;

public class CoreFieldCacheImpl implements ICachable<Field> {

	private static CoreFieldCacheImpl INSTANCE = null;

	private FieldServiceImp service;

	private CoreFieldCacheImpl() {
		setService(new FieldServiceImp());
	}

	public static CoreFieldCacheImpl getInstance() {
		if (INSTANCE == null)
			INSTANCE = new CoreFieldCacheImpl();
		return INSTANCE;
	}

	private MultiMap<String, Field> getMap() {
		if (KernelControllerActivator.client != null)
			return KernelControllerActivator.client.getMultiMap("fields");
		return null;
	}
	
	public LocalMapStats getStatistics() {
		return getMap().getLocalMultiMapStats();
	}

	@Override
	public void addAll(List<Field> list) {
		MultiMap<String, Field> map = getMap();
		for (Field field : list) {
			map.put(field.getEntityTypeId().toString(), field);
		}
	}

	@Override
	public void add(Field entity) {
		MultiMap<String, Field> multiMap = getMap();
		multiMap.put(entity.getEntityTypeId().toString(), entity);
	}

	@Override
	public void update(Field entity) {
		Long entityTypeId = entity.getEntityTypeId();
		MultiMap<String, Field> multiMap = getMap();
		Collection<Field> collection = multiMap.get(entityTypeId.toString());
		Field result = null;
		if (collection != null && collection.size() > 0) {
			for (Field field : collection) {
				if (field.equals(entity)) {
					result = field;
					break;
				}
			}

			if (result != null)
				multiMap.remove(entityTypeId.toString(), result);
		}

		multiMap.put(entityTypeId.toString(), entity);
	}

	@Override
	public void remove(Field entity) {
		MultiMap<String, Field> multiMap = getMap();
		multiMap.remove(entity.getEntityTypeId().toString(), entity);
	}

	@Override
	public void clear(Long key) {
		MultiMap<String, Field> multiMap = getMap();
		multiMap.lock(key.toString());
		multiMap.remove(key.toString());
		multiMap.unlock(key.toString());
	}

	@Override
	public List<Field> getCache(Long key) {
		MultiMap<String, Field> multiMap = getMap();
		Collection<Field> collection = multiMap.get(key.toString());
		List<Field> result = null;
		if (collection != null && collection.size() <= 0) {
			result = service.findFields(key);
			addAll(result);
		} else {
			result = new ArrayList<Field>(collection);
		}

		return result;
	}

	@Override
	public void setService(Service<Field> service) {
		this.service = (FieldServiceImp) service;
	}
}

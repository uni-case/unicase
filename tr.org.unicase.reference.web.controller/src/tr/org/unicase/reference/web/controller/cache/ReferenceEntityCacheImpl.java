package tr.org.unicase.reference.web.controller.cache;

import java.util.ArrayList;
import java.util.List;

import tr.org.unicase.kernel.web.controller.ICachable;
import tr.org.unicase.reference.model.ReferenceEntity;
import tr.org.unicase.reference.service.ReferenceService;
import tr.org.unicase.reference.service.ReferenceServiceImp;
import tr.org.unicase.reference.web.controller.activator.ReferenceControllerActivator;
import tr.org.unicase.service.api.Service;

import com.hazelcast.core.IMap;

public class ReferenceEntityCacheImpl implements ICachable<ReferenceEntity> {

	private static ReferenceEntityCacheImpl INSTANCE = null;

	private ReferenceService service;

	private ReferenceEntityCacheImpl() {
		setService(new ReferenceServiceImp());
	}

	public static ReferenceEntityCacheImpl getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ReferenceEntityCacheImpl();
		return INSTANCE;
	}

	private IMap<Long, ReferenceEntity> getMap(String key) {
		if (ReferenceControllerActivator.client != null) {
			return ReferenceControllerActivator.client.getMap(key);
		}

		return null;
	}

	@Override
	public void addAll(List<ReferenceEntity> list) {
		for (ReferenceEntity referenceEntity : list) {
			add(referenceEntity);
		}
	}

	@Override
	public void add(ReferenceEntity entity) {
		Long entityTypeId = entity.getEntityTypeId().getId();
		IMap<Long, ReferenceEntity> map = ReferenceControllerActivator.client.getMap(entityTypeId.toString());
		map.set(entity.getId(), entity);
	}

	@Override
	public void update(ReferenceEntity entity) {
		Long entityTypeId = entity.getEntityTypeId().getId();
		ReferenceEntity result = null;
		IMap<Long, ReferenceEntity> map = getMap(entityTypeId.toString());
		if (map != null && map.size() > 0) {
			result = map.get(entity.getId());
			if (result != null) {
				map.remove(result);
			}
		}
		map.set(entity.getId(), entity);
	}

	@Override
	public void remove(ReferenceEntity entity) {
		Long entityTypeId = entity.getEntityTypeId().getId();
		IMap<Long, ReferenceEntity> map = getMap(entityTypeId.toString());
		map.remove(entity);
	}

	@Override
	public void clear(Long key) {
		IMap<Long, ReferenceEntity> map = getMap(key.toString());
		map.clear();
	}

	@Override
	public void setService(Service<ReferenceEntity> service) {
		this.service = (ReferenceService) service;
	}

	public List<ReferenceEntity> getCache(Long entityType, Long ownerId) {
		List<ReferenceEntity> tempResult = getCache(entityType);
		if (ownerId != null) {
			List<ReferenceEntity> result = new ArrayList<ReferenceEntity>();
			ReferenceEntity owner = null;
			for (ReferenceEntity referenceEntity : tempResult) {
				owner = referenceEntity.getCk_owner();
				if (owner != null && owner.getId().equals(ownerId))
					result.add(referenceEntity);
			}

			return result;
		}

		return tempResult;
	}

	@Override
	public List<ReferenceEntity> getCache(Long key) {
		List<ReferenceEntity> result = null;

		IMap<Long, ReferenceEntity> map = getMap(key.toString());
		if (map != null && map.size() <= 0) {
			result = service.findAll(key, null, null);
			addAll(result);
		} else {
			result = new ArrayList<ReferenceEntity>(map.values());
		}

		return result;
	}

}

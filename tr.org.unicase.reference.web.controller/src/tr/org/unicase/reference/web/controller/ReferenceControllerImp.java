package tr.org.unicase.reference.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.hibernate.Session;
import org.hibernate.Transaction;

import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.kernel.model.Generate;
import tr.org.unicase.kernel.service.FieldService;
import tr.org.unicase.kernel.service.FieldServiceImp;
import tr.org.unicase.kernel.web.controller.GenerateControllerImp;
import tr.org.unicase.reference.model.ReferenceEntity;
import tr.org.unicase.reference.service.ReferenceService;
import tr.org.unicase.reference.service.ReferenceServiceImp;
import tr.org.unicase.reference.web.controller.cache.ReferenceEntityCacheImpl;
import tr.org.unicase.web.controller.api.AbstractController;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;

public class ReferenceControllerImp extends AbstractController<ReferenceEntity> {

	private ReferenceService service = new ReferenceServiceImp();
	private FieldService fieldService = new FieldServiceImp();

	public ReferenceControllerImp() {
		super(null, null, null);
	}

	public ReferenceControllerImp(Long entityTypeId, Long ownerId, String refOwner) {
		super(entityTypeId, ownerId, refOwner);
		setFieldsList(fieldService.findFields(entityTypeId));
		setOwneredFieldsList(fieldService.findOwneredFields(entityTypeId));
		setSearchableFieldsList(fieldService.findSearchableFields(entityTypeId));
		getFields().put("id", new Field());
		for (Field field : getFieldsList()) {
			getFields().put(field.getName(), field);
		}

	}

	@Override
	public void generate() {
		ReferenceEntity referenceEntity = new ReferenceEntity();
		referenceEntity.setEntityTypeId(getEntityType());

		if (refOwner != null) {
			switch (refOwner.toUpperCase()) {
			case "CK_OWNER":
				referenceEntity.setCk_owner(new ReferenceEntity(ownerId));
				break;
			case "CK_01":
				referenceEntity.setCk_01(new ReferenceEntity(ownerId));
				break;
			case "CK_02":
				referenceEntity.setCk_02(new ReferenceEntity(ownerId));
				break;
			case "CK_03":
				referenceEntity.setCk_03(new ReferenceEntity(ownerId));
				break;
			case "CK_04":
				referenceEntity.setCk_04(new ReferenceEntity(ownerId));
				break;
			case "CK_05":
				referenceEntity.setCk_05(new ReferenceEntity(ownerId));
				break;
			case "CK_06":
				referenceEntity.setCk_06(new ReferenceEntity(ownerId));
				break;
			case "CK_07":
				referenceEntity.setCk_07(new ReferenceEntity(ownerId));
				break;
			case "CK_08":
				referenceEntity.setCk_08(new ReferenceEntity(ownerId));
				break;
			case "CK_09":
				referenceEntity.setCk_09(new ReferenceEntity(ownerId));
				break;
			case "CK_10":
				referenceEntity.setCk_10(new ReferenceEntity(ownerId));
				break;

			default:
				break;
			}
		}
		setEntity(referenceEntity);

	}

	private void setValue(Field field, Generate generate, ReferenceEntity entity) {
		String name = field.getName();
		String generatedValue = generate.toString();
		if (name.equals("code")) {
			entity.setCode(generatedValue);
		} else if (name.equals("value")) {
			entity.setValue(generatedValue);
		} else if (name.equals("shortvalue")) {
			entity.setShortvalue(generatedValue);
		} else if (name.equals("description")) {
			entity.setDescription(generatedValue);
		} else if (name.equals("desc_01")) {
			entity.setDesc_01(generatedValue);
		} else if (name.equals("desc_02")) {
			entity.setDesc_02(generatedValue);
		} else if (name.equals("desc_03")) {
			entity.setDesc_03(generatedValue);
		} else if (name.equals("desc_04")) {
			entity.setDesc_04(generatedValue);
		} else if (name.equals("desc_05")) {
			entity.setDesc_05(generatedValue);
		} else if (name.equals("desc_06")) {
			entity.setDesc_06(generatedValue);
		} else if (name.equals("desc_07")) {
			entity.setDesc_07(generatedValue);
		} else if (name.equals("desc_08")) {
			entity.setDesc_08(generatedValue);
		} else if (name.equals("desc_09")) {
			entity.setDesc_09(generatedValue);
		} else if (name.equals("desc_10")) {
			entity.setDesc_10(generatedValue);
		} else if (name.equals("value_01")) {
			entity.setValue_01(generatedValue);
		} else if (name.equals("value_02")) {
			entity.setValue_02(generatedValue);
		} else if (name.equals("value_03")) {
			entity.setValue_03(generatedValue);
		} else if (name.equals("value_04")) {
			entity.setValue_04(generatedValue);
		} else if (name.equals("value_05")) {
			entity.setValue_05(generatedValue);
		} else if (name.equals("value_06")) {
			entity.setValue_06(generatedValue);
		} else if (name.equals("value_07")) {
			entity.setValue_07(generatedValue);
		} else if (name.equals("value_08")) {
			entity.setValue_08(generatedValue);
		} else if (name.equals("value_09")) {
			entity.setValue_09(generatedValue);
		} else if (name.equals("value_10")) {
			entity.setValue_10(generatedValue);
		}
	}

	private String getValue(Field field, ReferenceEntity entity) {
		String result = null;
		String name = field.getName();
		if (name.equals("code")) {
			result = entity.getCode();
		} else if (name.equals("value")) {
			result = entity.getValue();
		} else if (name.equals("shortvalue")) {
			result = entity.getShortvalue();
		} else if (name.equals("description")) {
			result = entity.getDescription();
		} else if (name.equals("desc_01")) {
			result = entity.getDesc_01();
		} else if (name.equals("desc_02")) {
			result = entity.getDesc_02();
		} else if (name.equals("desc_03")) {
			result = entity.getDesc_03();
		} else if (name.equals("desc_04")) {
			result = entity.getDesc_04();
		} else if (name.equals("desc_05")) {
			result = entity.getDesc_05();
		} else if (name.equals("desc_06")) {
			result = entity.getDesc_06();
		} else if (name.equals("desc_07")) {
			result = entity.getDesc_07();
		} else if (name.equals("desc_08")) {
			result = entity.getDesc_08();
		} else if (name.equals("desc_09")) {
			result = entity.getDesc_09();
		} else if (name.equals("desc_10")) {
			result = entity.getDesc_10();
		} else if (name.equals("value_01")) {
			result = entity.getValue_01();
		} else if (name.equals("value_02")) {
			result = entity.getValue_02();
		} else if (name.equals("value_03")) {
			result = entity.getValue_03();
		} else if (name.equals("value_04")) {
			result = entity.getValue_04();
		} else if (name.equals("value_05")) {
			result = entity.getValue_05();
		} else if (name.equals("value_06")) {
			result = entity.getValue_06();
		} else if (name.equals("value_07")) {
			result = entity.getValue_07();
		} else if (name.equals("value_08")) {
			result = entity.getValue_08();
		} else if (name.equals("value_09")) {
			result = entity.getValue_09();
		} else if (name.equals("value_10")) {
			result = entity.getValue_10();
		}

		return result;
	}

	@Override
	public void saveAll(List<ReferenceEntity> entities) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = service.getDao().getSession();
			if (session != null) {
				transaction = session.beginTransaction();
				for (ReferenceEntity referenceEntity : entities) {
					referenceEntity.setStatus(ReferenceEntity.TRUE);
					service.save(session, referenceEntity);
				}
				transaction.commit();
				ReferenceEntityCacheImpl.getInstance().addAll(entities);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null)
				transaction.rollback();
		} finally {
			if (session != null) {
				session.close();
				session = null;
				transaction = null;
			}
		}

	}

	public boolean exists(ReferenceEntity entity) {
		boolean result = false;
		if (entity != null) {
			Long int_01 = entity.getInt_01();
			Long int_02 = entity.getInt_02();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("int_01", int_01);
			parameters.put("int_02", int_02);
			result = exists(entity.getEntityTypeId().getId(), parameters);

			int_01 = null;
			int_02 = null;
			parameters.clear();
			parameters = null;
		}

		return result;
	}

	public boolean exists(Long entity, Map<String, Object> parameters) {
		boolean result = false;
		if (entity != null) {
			List<ReferenceEntity> list = this.service.findAll(entity, null, null, parameters);
			result = list != null && list.size() > 0;
			list.clear();
			list = null;
		}

		return result;
	}

	@Override
	public void save(ReferenceEntity entity) throws Exception {
		super.save(entity);
		entity.setStatus(ReferenceEntity.TRUE);
		Session session = null;
		Transaction transaction = null;
		try {
			session = service.getDao().getSession();
			if (session != null) {
				transaction = session.beginTransaction();
				String value = null;
				Generate generate = null;
				for (Field field : getFieldsList()) {
					if (field.isGenerated()) {
						value = getValue(field, entity);
						if (value == null || value.trim().isEmpty() || value.equals("Sistem No") ) {
							generate = GenerateControllerImp.getInstance().getGenerateButNotUpdate(entityTypeId, field.getName());
							GenerateControllerImp.getInstance().save(session, generate);
							setValue(field, generate, entity);
						}
					}
				}

				service.save(session, entity);

				transaction.commit();

				if (entityList != null)
					entityList.add(entity);
				ReferenceEntityCacheImpl.getInstance().update(entity);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.clear();
				session.close();
				session = null;
			}
		}

	}

	@Override
	public void unlock(ReferenceEntity Entity) throws Exception {
		Entity.setLock(null);
		service.save(Entity);

		Long id = Entity.getId();
		List<ReferenceEntity> list = service.findAllChilds(id);
		if (list != null && list.size() > 0) {
			for (ReferenceEntity childEntity : list) {
				childEntity.setLock(null);
				service.save(childEntity);
			}
		}
	}

	@Override
	public void lock(ReferenceEntity Entity) throws Exception {
		String lock = "111";
		Entity.setLock(lock);
		service.save(Entity);

		Long id = Entity.getId();
		List<ReferenceEntity> list = service.findAllChilds(id);
		if (list != null && list.size() > 0) {
			for (ReferenceEntity childEntity : list) {
				childEntity.setLock(lock);
				service.save(childEntity);
			}
		}
	}

	@Override
	public void delete(ReferenceEntity Entity) throws Exception {
		super.delete(Entity);
		ReferenceEntityCacheImpl.getInstance().remove(Entity);
		entity.setStatus(ReferenceEntity.FALSE);
		service.save(Entity);
		if (entityList != null && entityList.contains(Entity))
			entityList.remove(Entity);

		Long id = Entity.getId();
		List<ReferenceEntity> list = service.findAllChilds(id);
		if (list != null && list.size() > 0) {
			for (ReferenceEntity childEntity : list) {
				ReferenceEntityCacheImpl.getInstance().remove(childEntity);
				childEntity.setStatus(ReferenceEntity.FALSE);
				service.save(childEntity);
			}
		}

	}

	@Override
	public List<ReferenceEntity> findAll(Long entityTypeId, Long ownerId, String refOwner) {
		entityList = service.findAll(entityTypeId, ownerId, refOwner);
		return entityList;
	}

	@Override
	public List<ReferenceEntity> findAll(Long entityTypeId, Long ownerId, String refOwner, Map<String, Object> parameters) {
		return service.findAll(entityTypeId, ownerId, refOwner, parameters);
	}

	public ReferenceEntity findById(Long id) {
		return service.findById(id);
	}

	@Override
	public Container getDataSource() {
		return new BeanItemContainer<ReferenceEntity>(ReferenceEntity.class, entityList);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Container getDataSource(List<ReferenceEntity> entities) {
		IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("id", Long.class, null);
		container.addContainerProperty("value", String.class, null);

		for (ReferenceEntity c : entities) {
			Long id = c.getId();
			Item item = container.addItem(id);

			item.getItemProperty("id").setValue(id);
			item.getItemProperty("value").setValue(c.toString());
		}

		return container;

	}

	@Override
	public String[] getColumnHeaderValues() {
		if (columnsHeaders == null || columnsHeaders.size() == 0) {
			Field field = null;
			for (String key : getFields().keySet()) {
				field = getFields().get(key);
				if (field != null && field.getVisible() && field.getVisibleInTableColumn().equals("1"))
					columnsHeaders.add(field.getLabelText());
			}
		}
		return columnsHeaders.toArray(new String[columnsHeaders.size()]);
	}

	@Override
	public String[] getNaturalColumnOrder() {
		if (columnsOrders == null || columnsOrders.size() == 0) {
			Field field = null;
			for (String key : getFields().keySet()) {
				field = getFields().get(key);
				if (field != null && field.getVisible() && (!field.getVisibleInTableColumn().equals(null) && field.getVisibleInTableColumn().equals("1")))
					columnsOrders.add(field.getName());
			}
		}

		return columnsOrders.toArray(new String[columnsOrders.size()]);
	}

	@Override
	public void open(ReferenceEntity entity) {

	}

	@Override
	public void details(ReferenceEntity entity) {

	}

	public List<Field> getTypeFields(Long id) {
		setOwneredFieldsList(fieldService.findOwneredFields(id));
		return getTypeFields();
	}

	public List<Field> getTypeFields() {
		List<Field> typeFeilds = new LinkedList<Field>();

		for (Field field : getOwneredFieldsList()) {
			if (field.getType() != null)
				typeFeilds.add(field);
		}

		return typeFeilds;
	}

	@Override
	public void search() {
		entityList = service.findAll(entityTypeId, ownerId, refOwner, this.getParameters());
	}

	@Override
	public Container getDataSource(Long entityTypeId, Long ownerId, String refOwner) {
		return getDataSource(ReferenceEntityCacheImpl.getInstance().getCache(entityTypeId, ownerId));
	}

	@Override
	public List<ReferenceEntity> findReports(Long entityTypeId) {
		return service.findReports(entityTypeId);
	}

	public String[] getMimeTypes() {
		String[] mimeTypes = null;
		List<ReferenceEntity> avaliableMimeTypes = service.findMimeTypes();
		if (avaliableMimeTypes != null && avaliableMimeTypes.size() > 0) {
			int size = avaliableMimeTypes.size();
			mimeTypes = new String[size];
			for (int i = 0; i < size; i++) {
				if (avaliableMimeTypes.get(i).getValue() != null) {
					mimeTypes[i] = avaliableMimeTypes.get(i).getValue();
				}
			}

			avaliableMimeTypes.clear();
			avaliableMimeTypes = null;
		}
		return mimeTypes;
	}

	public List<ReferenceEntity> findRelatedActionList(Long entityTypeId) {
		return service.findRelatedActionList(entityTypeId);
	}

	public List<ReferenceEntity> findActiveActionList(String userRoles) {
		if (isUserAdmin(userRoles)) {
			return findRelatedActionList(entityTypeId);
		}

		Map<Long, ReferenceEntity> tempMap = new HashMap<Long, ReferenceEntity>();
		StringTokenizer tokenizer = new StringTokenizer(userRoles, ROLE_SEPERATOR);
		List<ReferenceEntity> userIdList = null;
		List<ReferenceEntity> result = null;
		List<ReferenceEntity> tempActionList = null;
		Long userId = null;
		Long tempActionId = null;

		String role = null;
		while (tokenizer.hasMoreElements()) {
			role = tokenizer.nextToken().trim();
			userIdList = service.findUserId(role);
			if (userIdList != null && userIdList.size() > 0) {
				userId = userIdList.get(0).getId();
				if (userId != null) {
					tempActionList = service.findActiveActionList(userId);
					if (tempActionList != null && tempActionList.size() > 0) {
						for (ReferenceEntity tempAction : tempActionList) {
							if (tempAction.getCk_01() != null && entityTypeId.toString().equals(tempAction.getCk_01().getValue_05())) {
								tempActionId = tempAction.getCk_01().getId();
								if (tempActionId != null && tempActionId.longValue() > 0 && !tempMap.containsKey(tempActionId))
									tempMap.put(tempActionId, tempAction.getCk_01());
								else {
									System.out.println(tempAction.getId() + " icindeki ck_01 yok");
								}
							}
						}
						tempActionId = null;
						tempActionList.clear();
						tempActionList = null;
					}
					userId = null;
				}
				userIdList.clear();
				userIdList = null;
			}
		}

		if (tempMap.size() > 0) {
			result = new ArrayList<ReferenceEntity>(tempMap.values());
			tempMap.clear();
			tempMap = null;

		}

		return result;
	}
}

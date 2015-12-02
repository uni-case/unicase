package tr.org.unicase.reference.web.controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.kernel.model.Generate;
import tr.org.unicase.kernel.service.FieldService;
import tr.org.unicase.kernel.service.FieldServiceImp;
import tr.org.unicase.reference.model.ReferenceEntity;
import tr.org.unicase.reference.service.ReferenceService;
import tr.org.unicase.reference.service.ReferenceServiceImp;
import tr.org.unicase.reference.web.controller.cache.ReferenceEntityCacheImpl;
import tr.org.unicase.web.controller.api.AbstractController;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;

public class ReferenceReportControllerImp extends AbstractController<ReferenceEntity> {

	private ReferenceService service = new ReferenceServiceImp();
	private FieldService fieldService = new FieldServiceImp();

	List<ReferenceEntity> list = null;
	List<ReferenceEntity> reportList = null;
	String reportQuery = null;

	public ReferenceReportControllerImp(Long entityTypeId) {
		super(entityTypeId, null, null);

		setReportList(service.findReports(entityTypeId));

		setFieldsList(fieldService.findFields(entityTypeId));
		setOwneredFieldsList(fieldService.findOwneredFields(entityTypeId));
		setSearchableFieldsList(fieldService.findSearchableFields(entityTypeId));
		getFields().put("id", new Field());
		for (Field field : getFieldsList()) {
			getFields().put(field.getName(), field);
		}

	}

	private void setReportList(List<ReferenceEntity> list) {
		this.reportList = list;
	}

	public List<ReferenceEntity> getReportList() {
		return this.reportList;
	}

	public void setReferenceEntityList(List<ReferenceEntity> list) {
		this.list = list;
	}

	public List<ReferenceEntity> getReferenceEntityList() {
		return this.list;
	}

	@Override
	public Object[] getNaturalColumnOrder() {
		return null;
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
	public List<ReferenceEntity> findAll(Long entityTypeId, Long ownerId, String refOwner) {
		return null;
	}

	@Override
	public Container getDataSource(Long entityTypeId, Long ownerId, String refOwner) {
		return getDataSource(ReferenceEntityCacheImpl.getInstance().getCache(entityTypeId, ownerId));
	}

	@Override
	public Container getDataSource() {
		BeanItemContainer<ReferenceEntity> container = new BeanItemContainer<ReferenceEntity>(ReferenceEntity.class, entityList);
		return container;
	}

	@Override
	public Container getDataSource(List<ReferenceEntity> entities) {
		BeanItemContainer<ReferenceEntity> container = new BeanItemContainer<ReferenceEntity>(ReferenceEntity.class, entities);

		return container;
	}

	@Override
	public void search() {

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

	@Override
	public void open(ReferenceEntity entity) {

	}

	@Override
	public void details(ReferenceEntity entity) {

	}

	private void setValue(Field field, ReferenceEntity entity) {
		Generate generate = field.getGenerate();

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
		}
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
				for (Field field : getFieldsList()) {
					if (field.isGenerated() && field.getGenerate() != null)
						setValue(field, entity);
				}

				service.save(session, entity);
				transaction.commit();
				if (entityList != null)
					entityList.add(entity);
				ReferenceEntityCacheImpl.getInstance().add(entity);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			throw e;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}


	}

	@Override
	public void delete(ReferenceEntity Entity) throws Exception {
		super.delete(Entity);
		entity.setStatus(ReferenceEntity.FALSE);
		service.save(Entity);
		if (entityList != null && entityList.contains(Entity))
			entityList.remove(Entity);
		ReferenceEntityCacheImpl.getInstance().remove(Entity);
	}

	@Override
	public ReferenceEntity findById(Long id) {
		return null;
	}

}

package tr.org.unicase.web.controller.api;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import tr.org.unicase.kernel.model.EntityType;
import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.web.controller.api.internals.config.Configuration;

public abstract class AbstractController<E> implements Controller<E> {

	protected static final String ROLE_SEPERATOR = Configuration.ROLE.SEPERATOR;
	protected List<Field> fieldsList;
	private List<Field> owneredFieldsList;
	private List<Field> searchableFieldsList;
	private Map<String, Object> parameters = new WeakHashMap<String, Object>();

	private Map<String, Field> fields = new WeakHashMap<String, Field>();

	protected Long entityTypeId;
	protected Long ownerId;

	protected String refOwner;

	protected E entity;

	protected List<E> entityList;

	protected List<String> columnsHeaders = new LinkedList<String>();
	protected List<String> columnsOrders = new LinkedList<String>();

	public AbstractController(Long entityTypeId, Long ownerId, String refOwner) {
		this.entityTypeId = entityTypeId;
		this.ownerId = ownerId;
		this.refOwner = refOwner;
		this.clearParameters();

		fields.put(Configuration.FIELD_ID, new Field());
		generate();

	}

	public abstract void search();

	public Map<String, Object> getParameters() {
		return this.parameters;
	}

	public void addParameter(String key, Object value) {
		this.parameters.put(key, value);
	}

	public void clearParameters() {
		this.parameters.clear();
	}

	@Override
	public E getEntity() {
		return entity;
	}

	@Override
	public void setEntity(E entity) {
		this.entity = entity;
	}

	@Override
	public void saveAll(List<E> entities) throws Exception {

	}

	@Override
	public List<E> findAll(Long entityTypeId, Long ownerId, String refOwner, Map<String, Object> parameters) {
		return null;
	}

	public void save(E entity) throws Exception {

	}

	public void delete(E entity) throws Exception {

	}

	public void update(E entity) throws Exception {

	}
	
	@Override
	public void lock(E entity) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void unlock(E entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public E get(Long Id) {
		return null;
	}

	@Override
	public abstract void generate();

	@Override
	public Field getField(String name) {
		if (getFields().containsKey(name))
			return getFields().get(name);
		else
			return new Field();
	}

	@Override
	public Map<String, Field> getFields() {
		return fields;
	}

	public void setFields(Map<String, Field> fields) {
		this.fields = fields;
	}

	@Override
	public List<Field> getFieldsList() {
		return fieldsList;
	}

	public void setEntityTypeId(Long entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	public List<E> getEntityList() {
		return entityList;

	}

	public void setFieldsList(List<Field> fieldsList) {
		this.fieldsList = fieldsList;
	}

	public void back() {

	}

	public void refresh() {

	}

	public Long getEntityTypeId() {
		return entityTypeId;
	}

	public EntityType getEntityType() {
		return new EntityType(entityTypeId);
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getRefOwner() {
		return refOwner;
	}

	public void setRefOwner(String refOwner) {
		this.refOwner = refOwner;
	}

	public abstract void open(E entity);

	public abstract void details(E entity);

	public List<Field> getOwneredFieldsList() {
		return owneredFieldsList;
	}

	public void setOwneredFieldsList(List<Field> owneredFieldsList) {
		this.owneredFieldsList = owneredFieldsList;
	}

	public List<Field> getSearchableFieldsList() {
		return searchableFieldsList;
	}

	public void setSearchableFieldsList(List<Field> searchableFieldsList) {
		this.searchableFieldsList = searchableFieldsList;
	}

	public List<E> findReports(Long entityTypeId) {
		return null;
	}

	public boolean isUserAdmin(String roles) {
		boolean result = false;
		String[] userActiveRoles = roles.split(ROLE_SEPERATOR);
		for (String role : userActiveRoles) {
			if (role.trim().equalsIgnoreCase(Configuration.ROLE.ADMIN)) {
				result = true;
				break;
			}
		}

		for (int i = 0; i < userActiveRoles.length; i++)
			userActiveRoles[i] = null;
		userActiveRoles = null;

		return result;
	}

}

package tr.org.unicase.web.controller.api;

import java.util.List;
import java.util.Map;

import com.vaadin.data.Container;

import tr.org.unicase.kernel.model.Field;

public interface Controller<E> {

	E getEntity();

	void setEntity(E entity);

	void save(E entity) throws Exception;
	
	void saveAll(List<E> list) throws Exception;

	void delete(E entity) throws Exception;
	
	void lock(E entity) throws Exception;
	
	void unlock(E entity) throws Exception;

	void update(E entity) throws Exception;

	void generate();

	E get(Long Id);

	public Long getEntityTypeId();

	Field getField(String name);

	Object[] getNaturalColumnOrder();

	String[] getColumnHeaderValues();

	List<E> findAll(Long entityTypeId, Long ownerId, String refOwner);
	
	List<E> findAll(Long entityTypeId, Long ownerId, String refOwner, Map<String, Object> parameters);
	
	E findById(Long id);
	
	Container getDataSource(Long entityTypeId, Long ownerId, String refOwner);

	Container getDataSource();

	Map<String, Field> getFields();

	List<Field> getFieldsList();
	
	Container getDataSource(List<E> entities);
	
	List<E> findReports(Long entityTypeId);

}

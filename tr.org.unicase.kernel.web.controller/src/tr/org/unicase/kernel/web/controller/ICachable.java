package tr.org.unicase.kernel.web.controller;

import java.util.List;

import tr.org.unicase.kernel.model.IEntity;
import tr.org.unicase.service.api.Service;

public interface ICachable<E extends IEntity> {

	public void addAll(List<E> list);
	
	public void add(E entity);
	
	public void update(E entity);
	
	public void remove(E entity);
	
	public void clear(Long key);
	
	public List<E> getCache(Long key);
	
	public void setService(Service<E> service);
		
}

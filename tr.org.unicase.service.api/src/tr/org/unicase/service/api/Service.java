package tr.org.unicase.service.api;


public interface Service<E> {

	void save(E entity) throws Exception;
	
	DefaultHibernateDAO<E> getDao();

}

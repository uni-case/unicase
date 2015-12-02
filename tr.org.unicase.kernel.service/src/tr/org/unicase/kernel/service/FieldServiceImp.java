package tr.org.unicase.kernel.service;

import java.util.List;

import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.kernel.service.internals.dao.CoreFieldHibernateDAO;
import tr.org.unicase.service.api.DefaultHibernateDAO;

public class FieldServiceImp implements FieldService {

	private CoreFieldHibernateDAO dao = null;

	public FieldServiceImp() {
		dao = new CoreFieldHibernateDAO();
	}

	@Override
	public DefaultHibernateDAO<Field> getDao() {
		return dao;
	}

	@Override
	public void save(Field entity) throws Exception {
		dao.save(entity);
	}

	public List<Field> findFields(Long entityTypeId) {
		return dao.findFields(entityTypeId);
	}

	@Override
	public List<Field> findOwneredFields(Long entityTypeId) {
		return dao.findOwneredFields(entityTypeId);
	}

	@Override
	public List<Field> findSearchableFields(Long entityTypeId) {
		return dao.findSearchableFields(entityTypeId);
	}

}

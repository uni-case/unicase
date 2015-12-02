package tr.org.unicase.kernel.service;

import java.util.List;

import tr.org.unicase.kernel.model.EntityType;
import tr.org.unicase.kernel.service.internals.dao.CoreEntityTypeHibernateDAO;
import tr.org.unicase.service.api.DefaultHibernateDAO;

public class EntityTypeServiceImp implements EntityTypeService {

	private CoreEntityTypeHibernateDAO dao = null;

	public EntityTypeServiceImp() {
		dao = new CoreEntityTypeHibernateDAO();
	}

	@Override
	public void save(EntityType entity) throws Exception {
		dao.save(entity);
	}

	public List<EntityType> findEntityTypes() {
		return dao.findEntityTypes();
	}

	@Override
	public DefaultHibernateDAO<EntityType> getDao() {
		return dao;
	}

}

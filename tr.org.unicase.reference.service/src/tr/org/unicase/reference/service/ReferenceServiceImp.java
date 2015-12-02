package tr.org.unicase.reference.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import tr.org.unicase.reference.model.ReferenceEntity;
import tr.org.unicase.service.api.DefaultHibernateDAO;

public class ReferenceServiceImp implements ReferenceService {

	private ReferenceEntityHibernateDAO dao = null;

	public ReferenceServiceImp() {
		dao = new ReferenceEntityHibernateDAO();
	}

	@Override
	public DefaultHibernateDAO<ReferenceEntity> getDao() {
		return dao;
	}

	@Override
	public void save(ReferenceEntity entity) throws Exception {
		dao.save(entity);
	}

	@Override
	public ReferenceEntity findById(Long id) {
		return dao.findById(id);
	}

	@Override
	public List<ReferenceEntity> findAll(Long entityTypeId, Long ownerId, String refOwner) {
		return dao.findAll(entityTypeId, ownerId, refOwner);
	}

	@Override
	public List<ReferenceEntity> findAll(Long entityTypeId, Long ownerId, String refOwner, Map<String, Object> parameters) {
		return dao.findAll(entityTypeId, ownerId, refOwner, parameters);
	}

	@Override
	public List<ReferenceEntity> findAllChilds(Long id) {
		List<ReferenceEntity> list = dao.findAllChilds(id, "ck_owner");
		return list;
	}

	@Override
	public List<ReferenceEntity> findUserId(String userRole) {
		return dao.find(75l, "value", userRole);
	}

	@Override
	public List<ReferenceEntity> findActiveActionList(Long userId) {
		return dao.findAll(76l, userId, "ck_owner");
	}

	@Override
	public List<ReferenceEntity> findDistinctTwitter() {
		List<ReferenceEntity> list = null;

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("value", "twitter.com");

		list = dao.findAll(28l, null, null, parameters);

		parameters.clear();
		parameters = null;

		return list;
	}

	public List<ReferenceEntity> findActiveModuleList(Long userId) {
		return dao.findAll(77l, userId, "ck_owner");
	}

	@Override
	public List<ReferenceEntity> findReports(Long entityTypeId) {
		return dao.find(58l, "code", entityTypeId.toString());
	}

	@Override
	public List<ReferenceEntity> findRelatedActionList(Long entityTypeId) {
		return dao.find(74l, "value_05", entityTypeId.toString());
	}

	@Override
	public void save(Session session, ReferenceEntity entity) throws Exception {
		dao.save(session, entity);
	}

	@Override
	public List<ReferenceEntity> findMimeTypes() {
		return dao.findAll(62l, null, null);
	}

}

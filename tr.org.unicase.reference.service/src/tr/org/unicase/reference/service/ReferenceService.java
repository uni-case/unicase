package tr.org.unicase.reference.service;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import tr.org.unicase.reference.model.ReferenceEntity;
import tr.org.unicase.service.api.Service;

public interface ReferenceService extends Service<ReferenceEntity> {

	void save(Session session, ReferenceEntity entity) throws Exception;

	void save(ReferenceEntity entity) throws Exception;

	ReferenceEntity findById(Long id);

	List<ReferenceEntity> findAll(Long entityTypeId, Long ownerId, String refOwner);

	List<ReferenceEntity> findAll(Long entityTypeId, Long ownerId, String refOwner, Map<String, Object> parameters);

	List<ReferenceEntity> findAllChilds(Long id);

	List<ReferenceEntity> findUserId(String userRole);

	List<ReferenceEntity> findActiveActionList(Long userId);

	List<ReferenceEntity> findDistinctTwitter();

	List<ReferenceEntity> findActiveModuleList(Long userId);

	List<ReferenceEntity> findReports(Long entityTypeId);

	List<ReferenceEntity> findRelatedActionList(Long entityTypeId);
	
	List<ReferenceEntity> findMimeTypes();

}

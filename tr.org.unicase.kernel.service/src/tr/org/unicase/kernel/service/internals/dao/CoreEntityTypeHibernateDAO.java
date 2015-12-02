package tr.org.unicase.kernel.service.internals.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import tr.org.unicase.kernel.model.EntityType;
import tr.org.unicase.kernel.service.internals.config.Configuration;
import tr.org.unicase.service.api.DefaultHibernateDAO;
import tr.org.unicase.service.api.sql.SQLConfiguration;
import tr.org.unicase.service.api.sql.UnicaseQueryBuilder;

public class CoreEntityTypeHibernateDAO extends DefaultHibernateDAO<EntityType> {

	private UnicaseQueryBuilder selectAllFromCoreEntityType() {
		return selectAllFromTable(Configuration.SQL.EntityType.TABLE);
	}

	@SuppressWarnings("unchecked")
	public List<EntityType> findEntityTypes() {
		List<EntityType> result = null;
		Session session = null;
		UnicaseQueryBuilder queryBuilder = null;
		try {
			session = getSession();
			if (session != null) {
				// "select * from core_entity_type where 1=1";
				queryBuilder = selectAllFromCoreEntityType();
				String queryString = queryBuilder.build();
				SQLQuery query = session.createSQLQuery(queryString);
				query.addEntity(EntityType.class);
				result = query.list();
			} else {
				throw SQLConfiguration.NO_SESSION_FOUND_EXCEPTION;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
			if (queryBuilder != null) {
				queryBuilder.clean();
				queryBuilder = null;
			}
		}

		return result;
	}

	@Override
	public EntityType findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}

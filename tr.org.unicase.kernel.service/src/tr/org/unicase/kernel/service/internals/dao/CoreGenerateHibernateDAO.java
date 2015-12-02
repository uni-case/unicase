package tr.org.unicase.kernel.service.internals.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import tr.org.unicase.kernel.model.Generate;
import tr.org.unicase.kernel.service.internals.config.Configuration;
import tr.org.unicase.service.api.DefaultHibernateDAO;
import tr.org.unicase.service.api.sql.SQLConfiguration;
import tr.org.unicase.service.api.sql.UnicaseQueryBuilder;

public class CoreGenerateHibernateDAO extends DefaultHibernateDAO<Generate> {

	private UnicaseQueryBuilder selectAllFromCoreGenerate() {
		return selectAllFromTable(Configuration.SQL.Generate.TABLE);
	}

	@SuppressWarnings("unchecked")
	public Generate getLastGenerated(String code) {
		Generate result = null;
		Session session = null;
		UnicaseQueryBuilder queryBuilder = null;
		try {
			session = getSession();
			if (session != null) {
				// "select * from core_generate where entity_type = :entityType order by prefix desc"
				queryBuilder = selectAllFromCoreGenerate();
				String queryString = queryBuilder.whereParameterized("entity_type", "entityType").build() + " order by prefix desc";
				SQLQuery query = session.createSQLQuery(queryString);
				query.addEntity(Generate.class);
				query.setParameter("entityType", code);
				List<Generate> list = query.list();
				if (list != null && list.size() > 0) {
					result = list.get(0);
					list.clear();
					list = null;
				} else
					result = new Generate(code, "", 0l, "");
			} else {
				throw SQLConfiguration.NO_SESSION_FOUND_EXCEPTION;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new Generate(code, "", 0l, "");
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
	public Generate findById(Long id) {
		return null;
	}
	
}

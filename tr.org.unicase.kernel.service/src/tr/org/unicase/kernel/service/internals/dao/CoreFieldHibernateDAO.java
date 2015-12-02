package tr.org.unicase.kernel.service.internals.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.kernel.service.internals.config.Configuration;
import tr.org.unicase.service.api.DefaultHibernateDAO;
import tr.org.unicase.service.api.sql.SQLConfiguration;
import tr.org.unicase.service.api.sql.UnicaseQueryBuilder;

public class CoreFieldHibernateDAO extends DefaultHibernateDAO<Field> {

	private UnicaseQueryBuilder selectAllFromCoreField() {
		return selectAllFromTable(Configuration.SQL.Field.TABLE);
	}
	
	@SuppressWarnings("unchecked")
	public List<Field> findOwneredFields(Long entityTypeId) {
		List<Field> result = null;
		Session session = null;
		UnicaseQueryBuilder queryBuilder = null;
		
		try {
			session = getSession();
			if (session != null) {
				queryBuilder = selectAllFromCoreField();
				String queryString = queryBuilder.whereParameterized("ref_type_Id", "refTypeId").build();
				SQLQuery query = session.createSQLQuery(queryString);
				query.addEntity(Field.class);
				query.setParameter("refTypeId", entityTypeId);
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
				queryBuilder= null;
			}
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Field> findFields(Long entityTypeId) {
		List<Field> result = null;
		Session session = null;
		UnicaseQueryBuilder queryBuilder = null;
		try {
			session = getSession();
			if (session != null) {
				queryBuilder = selectAllFromCoreField();
				String queryString = queryBuilder.whereParameterized("entity_Type_Id", "entityTypeId").build();
				SQLQuery query = session.createSQLQuery(queryString);
				query.addEntity(Field.class);
				query.setParameter("entityTypeId", entityTypeId);
				return query.list();
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
	
	@SuppressWarnings("unchecked")
	public List<Field> findSearchableFields(Long entityTypeId) {
		List<Field> result = null;
		Session session = null;
		UnicaseQueryBuilder queryBuilder = null;
		try {
			session = getSession();
			if (session != null) {
				queryBuilder = selectAllFromCoreField();
				String queryString = queryBuilder.whereParameterized("entity_Type_Id", "entityTypeId").where("searchable", SQLConfiguration.TRUE).build();
				/*
				StringBuilder builder = new StringBuilder(Configuration.SQL.FINDALL_FIELDS);
				builder
				.append(Configuration.SQL.OPERATION.AND)
				.append(Configuration.SQL.FIELD.ENTITY_TYPE_ID)
				.append(Configuration.SQL.OPERATION.EQUAL)
				.append(Configuration.SQL.OPERATION.SEMICOLON)
				.append(Configuration.SQL.PARAMETER.ENTITY_TYPE_ID)
				.append(Configuration.SQL.OPERATION.AND)
				.append(Configuration.SQL.FIELD.SEARCHABLE)
				.append(Configuration.SQL.OPERATION.EQUAL)
				.append(Configuration.SQL.TRUE);
				*/
				SQLQuery query = session.createSQLQuery(queryString);
				query.addEntity(Field.class);
				query.setParameter("entityTypeId", entityTypeId);
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

	@SuppressWarnings("unchecked")
	@Override
	public Field findById(Long id) {
		Field result = null;
		List<Field> list = null;
		Session session = null;
		UnicaseQueryBuilder queryBuilder = null;
		try {
			session = getSession();
			if (session != null) {
				queryBuilder = selectAllFromCoreField();
				String queryString = queryBuilder.whereParameterized("id", "entityTypeId").build();
				SQLQuery query = session.createSQLQuery(queryString);
				query.addEntity(Field.class);
				query.setParameter("entityTypeId", id);
				list = query.list();
			} else {
				throw SQLConfiguration.NO_SESSION_FOUND_EXCEPTION;
			}
		} catch (Exception e) {
			e.printStackTrace();
			list = null;
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
		
		if (list != null && list.size() > 0) {
			result = list.get(0);
			list.clear();
			list = null;
		}
			
		
		return result;
	}


}

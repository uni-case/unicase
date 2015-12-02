package tr.org.unicase.reference.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import tr.org.unicase.reference.model.ReferenceEntity;
import tr.org.unicase.reference.service.internals.config.Configuration;
import tr.org.unicase.service.api.DefaultHibernateDAO;
import tr.org.unicase.service.api.sql.SQLConfiguration;
import tr.org.unicase.service.api.sql.UnicaseQueryBuilder;

public class ReferenceEntityHibernateDAO extends DefaultHibernateDAO<ReferenceEntity> {

	public ReferenceEntityHibernateDAO() {
		
	}
	
	private UnicaseQueryBuilder selectAllFromCoreKeyvalue() {
		return selectAllFromTable(Configuration.SQL.CoreKeyValue.TABLE).where(Configuration.SQL.CoreKeyValue.STATUS, Configuration.SQL.TRUE);
	}

	private UnicaseQueryBuilder selectAllFromCoreKeyvalue(Long entityTypeId) {
		return selectAllFromCoreKeyvalue().where(Configuration.SQL.CoreKeyValue.ENTITY_TYPE_ID, entityTypeId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public ReferenceEntity findById(Long id) {
		ReferenceEntity result = null;
		SQLQuery query = null;
		Session session = null;
		UnicaseQueryBuilder queryBuilder = null;
		try {
			session = getSession();
			if (session != null) {
				// String queryString = "select * from core_keyvalue where Id = :entityTypeId and status = '1'";
				queryBuilder = selectAllFromCoreKeyvalue().whereParameterized("Id", "id");
				query = session.createSQLQuery(queryBuilder.build());
				query.addEntity(ReferenceEntity.class);
				query.setParameter("id", id);
				List<ReferenceEntity> list = query.list();
				if (list != null && list.size() > 0) {
					result = list.get(0);
					list.clear();
					list = null;
				}
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
	public List<ReferenceEntity> findAll(Long entityTypeId, Long ownerId, String refOowner) {
		List<ReferenceEntity> result = null;
		Session session = null;
		UnicaseQueryBuilder queryBuilder = null;

		try {
			session = getSession();
			if (session != null) {
				// "select * from core_keyvalue where entity_Type_Id = :entityTypeId and status = '1'";
				queryBuilder = selectAllFromCoreKeyvalue(entityTypeId);
				if (ownerId != null) 
					queryBuilder = queryBuilder.whereParameterized(refOowner, "ownerId");
				SQLQuery query = session.createSQLQuery(queryBuilder.build());
				query.addEntity(ReferenceEntity.class);

				if (ownerId != null) {
					query.setParameter("ownerId", ownerId);
				}

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
	public List<ReferenceEntity> find(Long entityTypeId, String column, String value) {
		List<ReferenceEntity> result = null;
		Session session = null;
		UnicaseQueryBuilder queryBuilder = null;
		
		try {
			session = getSession();
			if (session != null) {
				queryBuilder = selectAllFromCoreKeyvalue(entityTypeId).whereParameterized(column, column);
				SQLQuery query = session.createSQLQuery(queryBuilder.build());
				query.addEntity(ReferenceEntity.class);
				query.setParameter(column, value);
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
	public List<ReferenceEntity> findAll(Long entityTypeId, Long ownerId, String refOowner, Map<String, Object> parameters) {
		List<ReferenceEntity> result = null;
		if (parameters != null && parameters.size() > 0) {
			Session session = null;
			UnicaseQueryBuilder queryBuilder = null;
			try {
				session = getSession();
				if (session != null) {
					queryBuilder = selectAllFromCoreKeyvalue().whereParameterized("entity_Type_Id", "entityTypeId");
					// StringBuilder builder = new StringBuilder("select * from core_keyvalue where entity_Type_Id = :entityTypeId and status = '1'");
					
					if (ownerId != null) 
						queryBuilder = queryBuilder.whereParameterized(refOowner, "ownerId");

					Object value = null;
					for (String key : parameters.keySet()) {
						value = parameters.get(key);
						if (value != null) {
							if (value instanceof String){
								/*builder
									.append(Configuration.SQL.OPERATION.AND)
									.append(key)
									.append(Configuration.SQL.OPERATION.LIKE)
									.append(Configuration.SQL.OPERATION.SEMICOLON)
									.append(key);
								*/
								queryBuilder = queryBuilder.whereParameterized(key, SQLConfiguration.WHERE.LIKE, key);
							} else{
								queryBuilder = queryBuilder.whereParameterized(key, key);
								/*builder
									.append(Configuration.SQL.OPERATION.AND)
									.append(key)
									.append(Configuration.SQL.OPERATION.EQUAL)
									.append(Configuration.SQL.OPERATION.SEMICOLON)
									.append(key);
									*/
							}
						}
					}

					SQLQuery query = session.createSQLQuery(queryBuilder.build());
					query.addEntity(ReferenceEntity.class);
					query.setParameter("entityTypeId", entityTypeId);

					if (ownerId != null) {
						query.setParameter("ownerId", ownerId);
					}

					value = null;
					for (String key : parameters.keySet()) {
						value = parameters.get(key);
						if (value != null) {
							if (value instanceof String)
								query.setParameter(key, SQLConfiguration.WHERE.PERCENTAGE + ((String) value).toUpperCase(SQLConfiguration.TR) + SQLConfiguration.WHERE.PERCENTAGE);
							else if (value instanceof Date) {
								try {
									query.setParameter(key, SQLConfiguration.SDF.parse(SQLConfiguration.SDF.format(value)));
								} catch (ParseException e) {
									e.printStackTrace();
								}
							} else
								query.setParameter(key, value);
						}
					}

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

		} else {
			result = findAll(entityTypeId, ownerId, refOowner);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<ReferenceEntity> findAllChilds(Long ownerId, String refOowner) {
		List<ReferenceEntity> result = null;
		Session session = null;
		UnicaseQueryBuilder queryBuilder = null;
		
		try {
			session = getSession();
			if (session != null) {
				queryBuilder = selectAllFromCoreKeyvalue();
				if (ownerId != null)
					queryBuilder = queryBuilder.whereParameterized(refOowner, "ownerId");
				/*StringBuilder builder = new StringBuilder(Configuration.SQL.FIND_ALL);
				if (ownerId != null) {
					builder
						.append(Configuration.SQL.OPERATION.AND)
						.append(refOowner)
						.append(Configuration.SQL.OPERATION.EQUAL)
						.append(Configuration.SQL.OPERATION.SEMICOLON)
						.append(Configuration.SQL.PARAMETER.OWNER_ID);
				}*/

				SQLQuery query = session.createSQLQuery(queryBuilder.build());
				query.addEntity(ReferenceEntity.class);

				if (ownerId != null) {
					query.setParameter("ownerId", ownerId);
				}

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
	
}

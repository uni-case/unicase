package tr.org.unicase.service.api;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;

import tr.org.unicase.service.api.sql.SQLConfiguration;
import tr.org.unicase.service.api.sql.UnicaseQueryBuilder;

public abstract class DefaultHibernateDAO<E> {

	private SessionFactory sessionFactory;

	public DefaultHibernateDAO() {
	}

	private SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			ApplicationContext applicationContext = ApplicationContextUtils.getApplicationContext();
			sessionFactory = (SessionFactory) applicationContext.getBean(SQLConfiguration.SESSIONFACTORY_BEANNAME);
		}

		return this.sessionFactory;
	}

	public Session getSession() {
		if (this.sessionFactory == null)
			getSessionFactory();
		return (this.sessionFactory == null ? null : this.sessionFactory.openSession());
	}

	public void save(Session session, E entity) throws Exception {
		session.saveOrUpdate(entity);
	}

	public void save(E entity) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			if (session != null) {
				transaction = session.beginTransaction();
				session.saveOrUpdate(entity);
				transaction.commit();
			} else {
				throw SQLConfiguration.NO_SESSION_FOUND_EXCEPTION;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null)
				transaction.rollback();
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}
	}

	protected UnicaseQueryBuilder selectAllFromTable(String tableName) {
		return new UnicaseQueryBuilder().selectAll().from(tableName);
	}

	public abstract E findById(Long id);

}

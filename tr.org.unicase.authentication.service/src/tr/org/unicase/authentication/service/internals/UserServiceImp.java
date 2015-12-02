package tr.org.unicase.authentication.service.internals;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import tr.org.unicase.authentication.service.UserService;
import tr.org.unicase.authentication.service.model.User;

public class UserServiceImp implements UserService {

	private SessionFactory sessionFactory;

	private Session openSession() {
		if (sessionFactory != null)
			return sessionFactory.openSession();
		return null;
	}

	private void closeSession(Session session) {
		session.flush();
		session.close();
	}

	@Override
	public void save(User user) {
		Session session = openSession();
		if (session != null) {
			session.saveOrUpdate(user);
			closeSession(session);
		} else {
			System.out.println("hata1");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findAllUser() {
		List<User> userList = null;
		Session session = openSession();
		SQLQuery query = session.createSQLQuery("select * from core_user where status = '1' ");
		query.addEntity(User.class);
		userList = query.list();
		closeSession(session);
		return userList;
	}

	@SuppressWarnings("unchecked")
	public User findByUserName(String userName) {
		User result = null;
		Session session = openSession();
		SQLQuery query = session.createSQLQuery("select * from core_user where username = :userName and status = '1' ");
		query.addEntity(User.class);
		query.setParameter("userName", userName);
		List<User> user = query.list();
		if (user != null && user.size() > 0)
			result = user.get(0);

		closeSession(session);
		return result;

	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}

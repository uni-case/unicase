package tr.org.unicase.kernel.service.generate;

import org.hibernate.Session;

import tr.org.unicase.kernel.model.Generate;
import tr.org.unicase.kernel.service.internals.dao.CoreGenerateHibernateDAO;
import tr.org.unicase.service.api.DefaultHibernateDAO;

public class GenerateServiceImp implements GenerateService {

	private CoreGenerateHibernateDAO dao = null;
	
	public GenerateServiceImp() {
		dao = new CoreGenerateHibernateDAO();
	}

	public synchronized Generate first(String code) {
		Generate result = null;
		result = dao.getLastGenerated(code);
		result.incrementGeneratedValue();
		return result;
	}
	
	public DefaultHibernateDAO<Generate> getDao() {
		return dao;
	}

	@Override
	public void save(Session session, Generate entity) throws Exception{
		dao.save(session, entity);
	}


	@Override
	public void save(Generate entity) throws Exception {
		dao.save(entity);
	}

	
}

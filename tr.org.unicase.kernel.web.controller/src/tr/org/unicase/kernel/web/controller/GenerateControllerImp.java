package tr.org.unicase.kernel.web.controller;

import org.hibernate.Session;

import tr.org.unicase.kernel.model.Generate;
import tr.org.unicase.kernel.service.generate.GenerateService;
import tr.org.unicase.kernel.service.generate.GenerateServiceImp;

public class GenerateControllerImp {

	private static GenerateControllerImp INSTANCE = null;

	private GenerateService service = null;

	private GenerateControllerImp() {
		service = new GenerateServiceImp();
	}

	public static GenerateControllerImp getInstance() {
		if (INSTANCE == null)
			INSTANCE = new GenerateControllerImp();
		return INSTANCE;
	}

	public GenerateService getService() {
		return service;
	}

	public synchronized Generate getGenerateButNotUpdate(Long entityTypeId, String fieldName) {
		String code = entityTypeId + "_" + fieldName.trim();
		return service.first(code);
	}
	
	public synchronized Generate save(Session session, Long entityTypeId, String fieldName) throws Exception {
		Generate generate = getGenerateButNotUpdate(entityTypeId, fieldName);
		save(session, generate);
		return generate;
	}

	public synchronized void save(Session session, Generate entity) throws Exception {
		service.save(session, entity);
	}
}

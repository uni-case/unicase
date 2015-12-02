package tr.org.unicase.kernel.service.generate;

import org.hibernate.Session;

import tr.org.unicase.kernel.model.Generate;
import tr.org.unicase.service.api.Service;

public interface GenerateService extends Service<Generate> {

	void save(Session session, Generate entity) throws Exception;

	Generate first(String code);

}

package tr.org.unicase.web.app.internals;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.service.http.HttpContext;

public class ResourceProvider implements HttpContext {

	private List<Bundle> resources = new ArrayList<Bundle>();

	@Override
	public URL getResource(String uri) {
		URL resource = null;
		for (Bundle bundle : resources) {
			resource = bundle.getResource(uri);
			if (resource != null) {
				break;
			}
		}
		return resource;
	}

	public void add(Bundle bundle) {
		if (!resources.contains(bundle)) {
			resources.add(bundle);
		}
	}

	public void remove(Bundle bundle) {
		resources.remove(bundle);
	}

	@Override
	public String getMimeType(String arg0) {
		return null;
	}

	@Override
	public boolean handleSecurity(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return true;
	}
}

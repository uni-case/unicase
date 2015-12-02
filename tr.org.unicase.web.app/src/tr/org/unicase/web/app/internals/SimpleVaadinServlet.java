package tr.org.unicase.web.app.internals;

import javax.servlet.ServletException;

import org.osgi.service.http.NamespaceException;

import tr.org.unicase.web.app.MainWindowImpl;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.UIProvider;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;

@SuppressWarnings("serial")
@VaadinServletConfiguration(ui = MainWindowImpl.class, productionMode = false)
public class SimpleVaadinServlet extends VaadinServlet {

	private UIProvider provider;

	public SimpleVaadinServlet() throws ServletException, NamespaceException {
		this.provider = new SimpleUIProvider();
	}

	@Override
	protected VaadinServletService createServletService(DeploymentConfiguration deploymentConfiguration) throws ServiceException {
		VaadinServletService servletService = super.createServletService(deploymentConfiguration);
		if (provider != null) {
			servletService.addSessionInitListener(new SessionInitListener() {
				@Override
				public void sessionInit(SessionInitEvent sessionInitEvent) throws ServiceException {
					sessionInitEvent.getSession().addUIProvider(provider);
				}
			});
		}
		return servletService;
	}
}

package tr.org.unicase.web.app.internals;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tr.org.unicase.web.app.internals.config.Configuration;

public class HealthCheckServlet extends HttpServlet {

	public HealthCheckServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(Configuration.HttpTracker.HEALTH_CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.println(Configuration.HttpTracker.HEALTH_MESSAGE);
	}

}

package tr.org.unicase.reference.web.view.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tr.org.unicase.log.api.LogEvent;
import tr.org.unicase.reference.model.ReferenceEntity;
import tr.org.unicase.reference.web.controller.ReferenceReportControllerImp;
import tr.org.unicase.reference.web.view.ReferenceReportViewImp;
import tr.org.unicase.report.UnicaseReport;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.action.api.AbstractAction;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.api.View;

import com.vaadin.server.StreamResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ReportActionImpl<E> extends AbstractAction<E> {

	private ReferenceEntity refEntity = null;

	public ReportActionImpl(String id, Action ownerAction, String icon, String text, String clazz, Controller<?> controller, View<?> view) {
		super(id, ownerAction, icon, text, clazz, controller, view, controller.getEntityTypeId());
	}

	@SuppressWarnings("deprecation")
	private void createShowReport(String reportQuery) {
		final Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("parameter_id", reportQuery);

		final List<ReferenceEntity> reportList = ((ReferenceReportControllerImp) controller).getReportList();

		StreamResource.StreamSource source = new StreamResource.StreamSource() {
			public InputStream getStream() {
				byte[] b = null;
				byte[] resourceStream = null;
				try {

					for (int i = 0; i < reportList.size(); i++) {
						refEntity = (ReferenceEntity) reportList.get(i);
						if (refEntity.getByte_01() != null) {
							resourceStream = refEntity.getByte_01();
							break;
						}
					}
					if (resourceStream != null) {
						InputStream resourceAsStream = new ByteArrayInputStream(resourceStream);
						b = UnicaseReport.getInstance().generateReport(resourceAsStream, parameterMap);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				} 
				return new ByteArrayInputStream(b);
			}
		};

		String resourceName = "report3" + System.currentTimeMillis() + ".pdf";
		StreamResource resource = new StreamResource(source, resourceName);
		resource.setMIMEType("application/pdf");

		VerticalLayout v = new VerticalLayout();
		v.setSizeFull();
		Embedded embd = new Embedded("", resource);

		embd.setSizeFull();
		embd.setType(Embedded.TYPE_BROWSER);
		v.addComponent(embd);

		Window w = getWindow();

		w.setContent(v);
		UI.getCurrent().addWindow(w);
	}

	private Window getWindow() {
		Window w = new Window();
		w.setWidth("900px");
		w.setHeight("600px");
		w.center();
		return w;
	}

	@Override
	public void execute() throws Exception {

		if (view.validate()) {
			String reportQuery = ((ReferenceReportViewImp) view).buildReportParameters();
			System.out.println(reportQuery);
			if (reportQuery != null && !reportQuery.trim().isEmpty()) {
				createShowReport(reportQuery);
				LogEvent logEvent = prepareLogEvent();
				logEvent.setResult(reportQuery);
				log(logEvent);
			}
		} else {
			Notification.show("Başlangıç ya da Bitiş tarihlerinden en az biri seçilmelidir. Başlangıç tarihi Bitiş tarihinden önce olmalıdır. ");
		}

	}

	@Override
	protected void executeAfterController() {
		Notification.show("Rapor Oluşturma İşlemi Başarı İle Gerçekleştirildi.", Type.HUMANIZED_MESSAGE);
	}
}

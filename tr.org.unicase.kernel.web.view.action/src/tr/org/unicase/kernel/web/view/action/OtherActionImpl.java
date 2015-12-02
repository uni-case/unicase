package tr.org.unicase.kernel.web.view.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.log.api.LogEvent;
import tr.org.unicase.web.component.excel.ExcelExporter;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.action.api.AbstractAction;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.api.View;

import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class OtherActionImpl<E> extends AbstractAction<E> {

	private Button downloadButton;
	private Window w;

	public OtherActionImpl(String id, Action ownerAction, String icon, String text, String clazz, Controller<E> controller, View<?> view) {
		super(id, ownerAction, icon, text, clazz, controller, view, controller.getEntityTypeId());
	}

	@Override
	public void execute() throws Exception {
		w = new Window("Şablonu Buradan İndirebilirsiniz", createWindowContent());
		w.setResizable(true);
		w.center();
		UI.getCurrent().addWindow(w);
		log();
	}
	
	@Override
	protected void log() {
		LogEvent logEvent = prepareLogEvent();
		logEvent.setResult("Sablon indiriliyor");
		log(logEvent);
	}
	
	private Component createWindowContent() throws Exception {
		HorizontalLayout layout = new HorizontalLayout();

		downloadButton = new Button("Download Template");
		downloadButton.addClickListener(new ClickListener() {
			
			@Override
			@SuppressWarnings("rawtypes")
			public void buttonClick(ClickEvent event) {
				HSSFWorkbook workbook;
				try {
					workbook = ExcelExporter.export(new LinkedList(), getFieldsAsArray(), getFieldsAsArray());
					export(downloadButton, workbook);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		
		layout.addComponent(downloadButton);
		
		
		
		return layout;
	}
	
	private String[] getFieldsAsArray() {
		Set<String> fieldsList = controller.getFields().keySet();
		String[] fieldsAsArray = new String[fieldsList.size()];
		Field field = null;
		int i = 0;
		for (String string : fieldsList) {
			field = controller.getField(string);
			if (field != null && field.getVisible() && !string.startsWith("ck_"))
				fieldsAsArray[i++] = string;
		}
		
		return fieldsAsArray;
	}
	
	protected void export(Button button, HSSFWorkbook hssfWorkbook) {
		if (hssfWorkbook == null)
			return;
		else {
			try {
				final ByteArrayOutputStream out = new ByteArrayOutputStream();
				hssfWorkbook.write(out);

				StreamResource.StreamSource source = new StreamResource.StreamSource() {
					public InputStream getStream() {
						byte[] runReportToXls = null;
						try {
							return new ByteArrayInputStream(out.toByteArray());
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						return new ByteArrayInputStream(runReportToXls);
					}
				};

				String filename = "export" + System.currentTimeMillis() + ".xls";
				StreamResource resource = new StreamResource(source, filename);
				resource.setCacheTime(1);
				resource.setMIMEType("application/x-msexcel");
				resource.getStream().setParameter("Content-Disposition", "attachment; filename=" + filename);
				BrowserWindowOpener open = new BrowserWindowOpener(resource);
				open.extend(button);
				
			} catch (Exception e) {
				e.printStackTrace();
				Notification.show("Excel e dönüştürülemiyor.", Notification.Type.HUMANIZED_MESSAGE);
			}
		}

	}

	@Override
	protected void executeAfterController() {

	}
}

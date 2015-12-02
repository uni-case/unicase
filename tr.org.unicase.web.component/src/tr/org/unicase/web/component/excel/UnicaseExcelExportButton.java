package tr.org.unicase.web.component.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import tr.org.unicase.web.component.internals.config.Configuration;

import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;

public class UnicaseExcelExportButton extends Button {

	public UnicaseExcelExportButton() {
		super();
	}

	protected void export(HSSFWorkbook hssfWorkbook) {
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

				String filename = String.format(Configuration.ExcelExport.FORMAT_FILENAME, System.currentTimeMillis());
				StreamResource resource = new StreamResource(source, filename);
				resource.setCacheTime(1);
				resource.setMIMEType(Configuration.ExcelExport.MIMETYPE);
				resource.getStream().setParameter("Content-Disposition", "attachment; filename=" + filename);
				BrowserWindowOpener opener = new BrowserWindowOpener(resource);
				opener.extend(this);

			} catch (Exception e) {
				e.printStackTrace();
				Notification.show("Excel e dönüştürülemiyor.", Notification.Type.HUMANIZED_MESSAGE);
			}
		}

	}

	public void export(List<?> entityList, String[] columnNaturalNames, String[] columnNames) {
		try {
			HSSFWorkbook workbook = ExcelExporter.export(entityList, columnNaturalNames, columnNames);
			export(workbook);
		} catch (Exception e) {
			e.printStackTrace();
			Notification.show("Excel e dönüştürülemiyor.", Notification.Type.ERROR_MESSAGE);
		}
	}

}

package tr.org.unicase.reference.web.view.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import tr.org.unicase.kernel.model.EntityType;
import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.log.api.LogEvent;
import tr.org.unicase.reference.model.ReferenceEntity;
import tr.org.unicase.web.app.IconHelper;
import tr.org.unicase.web.component.UnicaseUploadBox;
import tr.org.unicase.web.component.UnicaseUploadBox.UnicaseUploadFinisedEvent;
import tr.org.unicase.web.component.excel.ExcelExporter;
import tr.org.unicase.web.controller.api.AbstractController;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.action.api.AbstractAction;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.api.View;

import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class UploadActionImpl extends AbstractAction<ReferenceEntity> {

	public UploadActionImpl(String id, Action ownerAction, String icon, String text, String clazz, Controller<?> controller, View<?> view) {
		super(id, ownerAction, icon, text, clazz, controller, view, controller.getEntityTypeId());
	}

	private boolean isRightFile(Row row) {
		Set<String> fieldsList = controller.getFields().keySet();
		Iterator<Cell> cellIterator = row.cellIterator();
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			String value = cell.getStringCellValue();
			String[] valueDescriptor = null;
			if (value != null && !value.trim().isEmpty()) {

				valueDescriptor = value.split("/");
				value = valueDescriptor[0];

				if (!fieldsList.contains(value)) {
					return false;
				}
			}
		}

		return true;
	}

	private void convertRowToMap(Row row, Map<Integer, String> map) {
		Iterator<Cell> cellIterator = row.cellIterator();
		Cell cell = null;
		String header = null;
		String[] headerDescription = null;
		while (cellIterator.hasNext()) {
			cell = cellIterator.next();
			if (cell.getStringCellValue() != null && !cell.getStringCellValue().trim().isEmpty()) {
				header = cell.getStringCellValue();
				headerDescription = header.split("/");
				header = headerDescription[0];
			}
			map.put(cell.getColumnIndex(), header);
		}

		cell = null;
		cellIterator = null;
	}

	@SuppressWarnings("unchecked")
	private ReferenceEntity convert(List<ExcelValue> excelValues) {
		ReferenceEntity entity = new ReferenceEntity();

		String methodName = null;
		Method method = null;

		for (ExcelValue excelValue : excelValues) {

			methodName = "set" + excelValue.getKey().substring(0, 1).toUpperCase() + "" + excelValue.getKey().substring(1);
			try {

				if (excelValue.getKey().startsWith("ck_")) {
					if (excelValue.getValue() != null) {
						double doubleValue = (double) excelValue.getValue();
						long longValue = (new Double(doubleValue)).longValue();
						ReferenceEntity referenceValue = new ReferenceEntity(longValue);
						method = entity.getClass().getMethod(methodName, ReferenceEntity.class);
						method.invoke(entity, referenceValue);
					}
				} else {
					method = entity.getClass().getMethod(methodName, excelValue.getClazz());
					method.invoke(entity, excelValue.getValue());
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				continue;
			} catch (SecurityException e) {
				e.printStackTrace();
				continue;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				continue;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				continue;
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				continue;
			}
		}

		entity.setEntityTypeId(new EntityType(controller.getEntityTypeId()));
		Long ownerId = ((AbstractController<ReferenceEntity>) controller).getOwnerId();
		if (ownerId != null && ownerId.longValue() > 0) {
			entity.setCk_owner(new ReferenceEntity(ownerId));
		}

		return entity;
	}

	private List<ExcelValue> convertRowToExcelValueObject(Row row, Map<Integer, String> lookUpMap) {
		List<ExcelValue> list = new ArrayList<ExcelValue>();

		Iterator<Cell> cellIterator = row.cellIterator();
		Cell cell = null;
		while (cellIterator.hasNext()) {
			cell = cellIterator.next();
			list.add(createExcelValue(cell, lookUpMap));
		}

		return list;
	}

	private ExcelValue createExcelValue(Cell cell, Map<Integer, String> lookUpMap) {
		ExcelValue excelValue = new ExcelValue();
		excelValue.setKey(lookUpMap.get(cell.getColumnIndex()));

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			excelValue.setValue(cell.getStringCellValue());
			excelValue.setClazz(String.class);
			break;
		case Cell.CELL_TYPE_NUMERIC:
			excelValue.setValue(cell.getNumericCellValue());
			excelValue.setClazz(Double.class);
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			excelValue.setValue(cell.getBooleanCellValue());
			excelValue.setClazz(Boolean.class);

		}

		return excelValue;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception {
		final Window window = new Window("");

		final Button downloadButton;
		final List<ReferenceEntity> list = new ArrayList<ReferenceEntity>();
		UnicaseUploadBox unicaseUploadBox = new UnicaseUploadBox(new UnicaseUploadBox.FinishedListener() {

			@Override
			public void fireEvent(UnicaseUploadFinisedEvent event) {
				byte[] fileContent = event.getSource().getFileContent();
				ByteArrayInputStream d = new ByteArrayInputStream(fileContent);
				Workbook workbook = null;
				Map<Integer, String> columnMap = new WeakHashMap<Integer, String>();

				try {
					workbook = new HSSFWorkbook(d);
					Sheet sheet = workbook.getSheetAt(0);
					Iterator<Row> iterator = sheet.iterator();
					Row next = null;
					boolean first = true;
					while (iterator.hasNext()) {
						next = iterator.next();
						if (first) {
							convertRowToMap(next, columnMap);
							if (!isRightFile(next)) {
								Notification.show("Dosya verilen sablon ile tutmuyor lutfen salonu tekrar indirin", Type.ERROR_MESSAGE);
								window.close();
								return;
							}
							first = false;
						} else {
							List<ExcelValue> result = convertRowToExcelValueObject(next, columnMap);
							list.add(convert(result));
						}
					}
					window.close();
					((AbstractController<ReferenceEntity>) controller).saveAll(list);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		downloadButton = new Button();
		downloadButton.setCaption("Şablonu Buradan İndirebilisiniz");
		downloadButton.setIcon(IconHelper.getIcon64("back-icon.png"));
		downloadButton.setStyleName(ValoTheme.BUTTON_BORDERLESS);
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

		VerticalLayout layout2 = new VerticalLayout();

		// layout
		layout2.setSpacing(true);
		layout2.setMargin(true);
		layout2.addComponent(downloadButton);
		layout2.addComponent(unicaseUploadBox);
		layout2.addStyleName("$v-layout-spacing-vertical");
		layout2.setSizeFull();
		window.setContent(layout2);
		window.setHeight("200");
		window.setWidth("400");
		window.center();
		UI.getCurrent().addWindow(window);

	}

	private String[] getFieldsAsArray() {
		Set<String> fieldsList = controller.getFields().keySet();
		String[] fieldsAsArray = new String[fieldsList.size()];
		Field field = null;
		int i = 0;
		for (String string : fieldsList) {
			field = controller.getField(string);
			// if (field != null && field.getVisible() &&
			// !string.startsWith("ck_"))
			if (field != null && field.getVisible())
				fieldsAsArray[i++] = string;
		}

		System.out.println("FieldsArray Boyutu:" + fieldsAsArray.length);
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
		Notification.show("Islem basari ile tamamlandi.", Type.HUMANIZED_MESSAGE);
		LogEvent logEvent = prepareLogEvent();
		logEvent.setResult("Excel upload islemi tamamlandi");
		log(logEvent);
	}

	private static class ExcelValue {
		private String key;
		private Object value;
		private Class<?> clazz;

		public ExcelValue() {

		}

		@SuppressWarnings("unused")
		public ExcelValue(String key, Object value, Class<?> clazz) {
			setKey(key);
			setValue(value);
			setClazz(clazz);
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public void setClazz(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getClazz() {
			return this.clazz;
		}

		@Override
		public String toString() {
			return "ExcelValue [key=" + key + ", value=" + value + "]";
		}

	}

}

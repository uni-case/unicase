package tr.org.unicase.web.component.excel;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import tr.org.unicase.web.component.internals.config.Configuration;

import com.vaadin.data.Container;
import com.vaadin.data.Item;

public class ExcelExporter {

	private static Object getColumnValue(Object object, String columnName) throws Exception {
		if (columnName.isEmpty())
			return object;

		String[] split = columnName.split(Configuration.ExcelExport.COLUMN_SEPERATOR);

		String column = split[0];
		column = column.substring(0, 1).toUpperCase(Locale.ENGLISH) + column.substring(1);
		String methodName = String.format(Configuration.ExcelExport.FORMAT_METHODNAME, column);
		Object invoke = object.getClass().getMethod(methodName).invoke(object);

		if (invoke instanceof Boolean) {
			Boolean result = (Boolean) invoke;
			invoke = (result ? Configuration.ExcelExport.YES : Configuration.ExcelExport.NO);
		}

		String substring = null;

		if (split.length == 1)
			substring = Configuration.ExcelExport.EMPTY_STRING;
		else
			substring = columnName.substring(columnName.indexOf(Configuration.ExcelExport.DOT) + 1);

		return getColumnValue(invoke, substring);
	}

	public static HSSFWorkbook export(List<?> entityList, String[] columnNaturalNames, String[] columnNames) throws Exception {
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook();
			HSSFSheet firstSheet = workbook.createSheet(Configuration.ExcelExport.SHEET);
			boolean first = true;
			int row = 0;
			if (entityList.size() > 0) {
				for (Object object : entityList) {
					HSSFRow rowA = firstSheet.createRow(row);
					if (first) {
						int cell = 0;
						for (String column : columnNames) {
							HSSFCell cellA = rowA.createCell(cell++);
							cellA.setCellValue(new HSSFRichTextString(column));
						}
						first = false;
						row++;
						rowA = firstSheet.createRow(row);
					}

					int cell = 0;
					for (String column : columnNaturalNames) {
						Object columnValue;
						columnValue = getColumnValue(object, column);
						HSSFCell cellA = rowA.createCell(cell++);
						if (columnValue != null)
							cellA.setCellValue(new HSSFRichTextString(columnValue.toString()));
						else
							cellA.setCellValue(new HSSFRichTextString(Configuration.ExcelExport.EMPTY_STRING));
					}
					row++;
				}
			} else {
				HSSFRow rowA = firstSheet.createRow(row);
				if (first) {
					int cell = 0;
					for (String column : columnNames) {
						HSSFCell cellA = rowA.createCell(cell++);
						cellA.setCellValue(new HSSFRichTextString(column));
					}
					first = false;
					row++;
					rowA = firstSheet.createRow(row);
				}
			}

		} catch (Exception e) {
			throw e;
		}

		return workbook;
	}

	public static HSSFWorkbook export(Container container, String[] columnNaturalNames) throws Exception {
		HSSFWorkbook workbook = null;

		try {
			workbook = new HSSFWorkbook();
			HSSFSheet firstSheet = workbook.createSheet(Configuration.ExcelExport.SHEET);
			boolean first = true;
			int row = 0;

			for (Iterator<?> i = container.getItemIds().iterator(); i.hasNext();) {
				int iid = (Integer) i.next();

				// Now get the actual item from the container.
				Item item = container.getItem(iid);
				HSSFRow rowA = firstSheet.createRow(row);
				if (first) {
					int cell = 0;
					for (String column : columnNaturalNames) {
						HSSFCell cellA = rowA.createCell(cell++);
						cellA.setCellValue(new HSSFRichTextString(column));
					}
					first = false;
					row++;
					rowA = firstSheet.createRow(row);
				}

				int cell = 0;
				for (String column : columnNaturalNames) {
					Object columnValue;
					columnValue = item.getItemProperty(column).getValue();
					HSSFCell cellA = rowA.createCell(cell++);
					if (columnValue != null)
						cellA.setCellValue(new HSSFRichTextString(columnValue.toString()));
					else
						cellA.setCellValue(new HSSFRichTextString(Configuration.ExcelExport.EMPTY_STRING));
				}
				row++;
			}

		} catch (Exception e) {
			throw e;
		}

		return workbook;
	}

}

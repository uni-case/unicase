package tr.org.unicase.web.component;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.WeakHashMap;

import tr.org.unicase.web.component.internals.config.Configuration;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;

public class UnicaseTable extends Table {

	private final HashSet<Object> collapsedColumns = new HashSet<Object>();
	private final Map<Object, ColumnGenerator> columnGenerators = new WeakHashMap<Object, ColumnGenerator>();
	private LinkedList<Object> visibleColumns = new LinkedList<Object>();
	private final Map<Object, String> columnHeaders = new WeakHashMap<Object, String>();

	public UnicaseTable() {
		this.setPageLength(Configuration.Table.PAGE_LENGTH);
		this.setCacheRate(Configuration.Table.CACHE_RATE);
	}


	@Override
	@SuppressWarnings("rawtypes")
	protected String formatPropertyValue(Object rowId, Object colId, Property property) {
		if (property == null)
			return Configuration.Table.EMPTY_STRING;

		Object value = property.getValue();

		if (value == null)
			return Configuration.Table.EMPTY_STRING;

		if (value instanceof Date) {
			Date dateValue = (Date) value;
			return Configuration.Table.FORMAT.format(dateValue);
		}
		return super.formatPropertyValue(rowId, colId, property);
	}

	public void setVisibleColumns(String... visibleColumns) {
		for (Object object : visibleColumns) {
			this.visibleColumns.add(object);
		}
		super.setVisibleColumns((Object[]) visibleColumns);
	}

	public void addGeneratedColumn(Object id, ColumnGenerator generatedColumn) {
		try {
			super.addGeneratedColumn(id, generatedColumn);
			columnGenerators.put(id, generatedColumn);
			if (!visibleColumns.contains(id)) {
				visibleColumns.add(id);
			}
		} catch (IllegalArgumentException e) {
			this.columnGenerators.remove(id);
			e.printStackTrace();
		}
	}

	public void setColumnHeaders(String... columnHeaders) {

		try {
			super.setColumnHeaders(columnHeaders);
			this.columnHeaders.clear();
			int i = 0;
			for (final Iterator<Object> it = visibleColumns.iterator(); it.hasNext() && i < columnHeaders.length; i++) {
				this.columnHeaders.put(it.next(), columnHeaders[i]);
			}
		} catch (IllegalArgumentException ex) {
			this.columnHeaders.clear();
		}
	}

	@SuppressWarnings("rawtypes")
	public void refreshTable() {
		disableContentRefreshing();

		// Resets page position
		setCurrentPageFirstItemId(null);
		setCurrentPageFirstItemIndex(0);

		// Resets column properties
		if (collapsedColumns != null) {
			collapsedColumns.clear();
		}

		// columnGenerators 'override' properties, don't add the same id twice
		Collection<Object> col = new LinkedList<Object>();
		for (Iterator it = getContainerPropertyIds().iterator(); it.hasNext();) {
			Object id = it.next();
			if (columnGenerators == null || !columnGenerators.containsKey(id)) {
				col.add(id);
			}
		}
		// generators added last
		if (columnGenerators != null && columnGenerators.size() > 0) {
			col.addAll(columnGenerators.keySet());
		}

		// setVisibleColumns(col.toArray());
		setColumnHeaders(getColumnHeaders());

		// Assure visual refresh
		resetPageBuffer();

		enableContentRefreshing(true);
	}

	@Override
	public void setContainerDataSource(Container newDataSource) {
		super.setContainerDataSource(newDataSource);
	}

	public void setColumnCollapsed(Object propertyId, boolean collapsed) {
		try {
			if (!isColumnCollapsingAllowed()) {
				throw new IllegalAccessException("Column collapsing not allowed!");
			}

			if (collapsed) {
				collapsedColumns.add(propertyId);
			} else {
				collapsedColumns.remove(propertyId);
			}

			// Assures the visual refresh
			resetPageBuffer();
			refreshRenderedCells();
		} catch (IllegalAccessException e) {
			collapsedColumns.clear();

		}

	}
	
	@Override
	public void select(Object itemId) {
		if (itemId == null)
			return;
		Collection<?> collection = getItemIds();
		Object entity = null;
		for (Object object : collection) {
			if (object.equals(itemId))
				entity = object;
		}
		super.select(entity);
	}
}
package tr.org.unicase.web.component;

import java.util.ArrayList;
import java.util.List;

import tr.org.unicase.web.component.internals.config.Configuration;

import com.vaadin.data.Property;
import com.vaadin.ui.TwinColSelect;

public class UnicaseTwinColSelect extends TwinColSelect implements Property.ValueChangeListener {

	private String selectedItems;

	public UnicaseTwinColSelect() {
		super();
		this.addValueChangeListener(this);
	}

	public String getSelectedItems() {
		return this.selectedItems;
	}

	@Override
	public void valueChange(Property.ValueChangeEvent event) {
		if (event.getProperty() != null) {
			selectedItems = String.valueOf(event.getProperty().getValue());
			if (selectedItems != null && selectedItems.length() > 0)
				selectedItems = selectedItems.substring(1, selectedItems.length() -1);
		}
	}
		
	public void select(String stringElements) {
		List<String> result = new ArrayList<String>();
		if (stringElements != null && !stringElements.trim().isEmpty()) {
			String[] strings = stringElements.split(Configuration.TwinColSelect.SEPERATOR);
			for (String string : strings)
				result.add(string);
		} else {
			result.add(Configuration.TwinColSelect.CODE);
			result.add(Configuration.TwinColSelect.VALUE);
		}
		
		for (String r : result) {
			select((Object) r.trim());
		}
	}
	
	@Override
	public void select(Object itemId) {
		super.select(itemId);
	}

}

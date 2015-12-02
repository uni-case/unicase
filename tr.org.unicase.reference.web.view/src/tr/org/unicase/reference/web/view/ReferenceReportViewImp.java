package tr.org.unicase.reference.web.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.reference.model.ReferenceEntity;
import tr.org.unicase.reference.web.controller.ReferenceReportControllerImp;
import tr.org.unicase.web.component.UnicaseComboBox;
import tr.org.unicase.web.component.report.IReportField;
import tr.org.unicase.web.component.report.UnicaseReportComboBox;
import tr.org.unicase.web.component.report.UnicaseReportMultiDateField;
import tr.org.unicase.web.component.report.UnicaseReportTextArea;
import tr.org.unicase.web.component.report.UnicaseReportTextField;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.action.api.UnicaseActionManager;
import tr.org.unicase.web.view.api.AbstractView;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ReferenceReportViewImp extends AbstractView<ReferenceReportControllerImp> {

	private GridLayout mainGridLayout = new GridLayout(2, 1);
	private VerticalLayout leftVerticalLayout;
	private VerticalLayout rightVerticalLayout;

	private UnicaseReportTextField textField_Code;
	private UnicaseReportTextField textField_Value;
	private UnicaseReportTextField textField_Shortvalue;
	private UnicaseReportTextArea textField_Description;
	private UnicaseReportTextField textField_Orderno;

	private UnicaseReportTextArea textArea_Desc_01;
	private UnicaseReportTextArea textArea_Desc_02;
	private UnicaseReportTextArea textArea_Desc_03;
	private UnicaseReportTextArea textArea_Desc_04;
	private UnicaseReportTextArea textArea_Desc_05;
	private UnicaseReportTextArea textArea_Desc_06;
	private UnicaseReportTextArea textArea_Desc_07;
	private UnicaseReportTextArea textArea_Desc_08;
	private UnicaseReportTextArea textArea_Desc_09;
	private UnicaseReportTextArea textArea_Desc_10;

	private UnicaseReportTextField textField_Int_01;
	private UnicaseReportTextField textField_Int_02;
	private UnicaseReportTextField textField_Int_03;
	private UnicaseReportTextField textField_Int_04;
	private UnicaseReportTextField textField_Int_05;
	private UnicaseReportTextField textField_Int_06;
	private UnicaseReportTextField textField_Int_07;
	private UnicaseReportTextField textField_Int_08;
	private UnicaseReportTextField textField_Int_09;
	private UnicaseReportTextField textField_Int_10;

	private UnicaseReportTextField textField_Mon_01;
	private UnicaseReportTextField textField_Mon_02;
	private UnicaseReportTextField textField_Mon_03;
	private UnicaseReportTextField textField_Mon_04;
	private UnicaseReportTextField textField_Mon_05;
	private UnicaseReportTextField textField_Mon_06;
	private UnicaseReportTextField textField_Mon_07;
	private UnicaseReportTextField textField_Mon_08;
	private UnicaseReportTextField textField_Mon_09;
	private UnicaseReportTextField textField_Mon_10;

	private UnicaseReportMultiDateField dateField_Date_01;
	private UnicaseReportMultiDateField dateField_Date_02;
	private UnicaseReportMultiDateField dateField_Date_03;
	private UnicaseReportMultiDateField dateField_Date_04;
	private UnicaseReportMultiDateField dateField_Date_05;

	private UnicaseReportComboBox comboBox_Ck_owner;
	private UnicaseReportComboBox comboBox_Ck_01;
	private UnicaseReportComboBox comboBox_Ck_02;
	private UnicaseReportComboBox comboBox_Ck_03;
	private UnicaseReportComboBox comboBox_Ck_04;
	private UnicaseReportComboBox comboBox_Ck_05;
	private UnicaseReportComboBox comboBox_Ck_06;
	private UnicaseReportComboBox comboBox_Ck_07;
	private UnicaseReportComboBox comboBox_Ck_08;
	private UnicaseReportComboBox comboBox_Ck_09;
	private UnicaseReportComboBox comboBox_Ck_10;
	private UnicaseReportComboBox comboBox_Ck_11;
	private UnicaseReportComboBox comboBox_Ck_12;
	private UnicaseReportComboBox comboBox_Ck_13;
	private UnicaseReportComboBox comboBox_Ck_14;
	private UnicaseReportComboBox comboBox_Ck_15;

	private UnicaseReportTextField textField_Value_01;
	private UnicaseReportTextField textField_Value_02;
	private UnicaseReportTextField textField_Value_03;
	private UnicaseReportTextField textField_Value_04;
	private UnicaseReportTextField textField_Value_05;
	private UnicaseReportTextField textField_Value_06;
	private UnicaseReportTextField textField_Value_07;
	private UnicaseReportTextField textField_Value_08;
	private UnicaseReportTextField textField_Value_09;
	private UnicaseReportTextField textField_Value_10;

	private List<Field> fieldsList;

	private Map<Field, Component> layoutOrganization = new TreeMap<Field, Component>();

	public ReferenceReportViewImp(Long entityTypeId, Long reportTypeId, String stringValue) {
		this.entityTypeId = entityTypeId;
		layoutOrganization.clear();

		setController(new ReferenceReportControllerImp(entityTypeId));

		if (ownerId != null && ownerId.longValue() > 0)
			controller.findAll(entityTypeId, ownerId, refOwner);

		fieldsList = new ArrayList<Field>();

		buildLeftVerticalLayout();
		buildRightVerticalLayout();

		buildLayout();

		mainGridLayout.setId("mainGridLayoutId");

		if (leftVerticalLayout != null)
			mainGridLayout.addComponent(leftVerticalLayout, 0, 0);

		if (rightVerticalLayout != null)
			mainGridLayout.addComponent(rightVerticalLayout, 1, 0);

		Action action = UnicaseActionManager.getInstance().createInstance("tr.org.unicase.reference.web.view.action.ReportActionImpl", "1", null, "save.png", "Rapor", "clazz", controller, this, null);
		if (action != null) {
			action.setEntityTypeId(entityTypeId);
			actionList.add(action);
		}
	}

	private UnicaseReportComboBox getComboBox(String fieldName) {
		switch (fieldName) {
		case "ck_01":
			return comboBox_Ck_01;
		case "ck_02":
			return comboBox_Ck_02;
		case "ck_03":
			return comboBox_Ck_03;
		case "ck_04":
			return comboBox_Ck_04;
		case "ck_05":
			return comboBox_Ck_05;
		case "ck_06":
			return comboBox_Ck_06;
		case "ck_07":
			return comboBox_Ck_07;
		case "ck_08":
			return comboBox_Ck_08;
		case "ck_09":
			return comboBox_Ck_09;
		case "ck_10":
			return comboBox_Ck_10;
		case "ck_11":
			return comboBox_Ck_11;
		case "ck_12":
			return comboBox_Ck_12;
		case "ck_13":
			return comboBox_Ck_13;
		case "ck_14":
			return comboBox_Ck_14;
		case "ck_15":
			return comboBox_Ck_15;
		case "ck_owner":
			return comboBox_Ck_owner;
		default:
			return null;
		}
	}

	@Override
	public void initLayout() {
		for (final Field field : fieldsList) {
			if (field.getName().indexOf("ck_") >= 0) {
				final UnicaseComboBox combobox = getComboBox(field.getName());
				if (combobox != null) {
					combobox.setImmediate(true);
					Object selectedParent = null;
					if (combobox.getContainerDataSource() != null && combobox.getContainerDataSource().size() > 0) {
						selectedParent = combobox.getValue();
					}
					final String depends = field.getDepends();
					if (depends != null && !depends.trim().isEmpty() && depends.indexOf("ck_") >= 0) {
						final UnicaseComboBox parentComboBox = getComboBox(depends);
						if (parentComboBox != null) {
							parentComboBox.addValueChangeListener(new ValueChangeListener() {

								@Override
								public void valueChange(ValueChangeEvent event) {
									ReferenceEntity parentEntity = (ReferenceEntity) parentComboBox.getValue();
									if (parentEntity != null) {
										combobox.setAttributes(field.getRefTypeId(), parentEntity.getId(), "ck_owner");
										combobox.setContainerDataSource(getController().getDataSource(combobox.getTypeId(), combobox.getOwnerId(), combobox.getRefColumn()));
									}
								}
							});
						}
					} else {
						combobox.setAttributes(field.getRefTypeId(), null, field.getRefColumn());
						combobox.setContainerDataSource(getController().getDataSource(field.getRefTypeId(), null, field.getRefColumn()));
						combobox.select(selectedParent);
					}
				}
			}

		}
	}

	private void buildRightVerticalLayout() {
		rightVerticalLayout = new VerticalLayout();
		rightVerticalLayout.setSpacing(true);
		rightVerticalLayout.setMargin(true);
	}

	private void buildLeftVerticalLayout() {
		leftVerticalLayout = new VerticalLayout();
		leftVerticalLayout.setSpacing(true);
		leftVerticalLayout.setMargin(true);
	}

	@Override
	public Component getView() {
		mainVerticalLayout.addComponent(mainGridLayout);
		return mainVerticalLayout;
	}

	private void buildLayout() {

		textField_Code = (UnicaseReportTextField) createComponent(textField_Code, "code", UnicaseReportTextField.class);

		textField_Value = (UnicaseReportTextField) createComponent(textField_Value, "value", UnicaseReportTextField.class);

		textField_Shortvalue = (UnicaseReportTextField) createComponent(textField_Shortvalue, "shortvalue", UnicaseReportTextField.class);

		textField_Description = (UnicaseReportTextArea) createComponent(textField_Description, "description", UnicaseReportTextArea.class);

		textField_Orderno = (UnicaseReportTextField) createComponent(textField_Orderno, "orderno", UnicaseReportTextField.class);

		textArea_Desc_01 = (UnicaseReportTextArea) createComponent(textArea_Desc_01, "desc_01", UnicaseReportTextArea.class);
		textArea_Desc_02 = (UnicaseReportTextArea) createComponent(textArea_Desc_02, "desc_02", UnicaseReportTextArea.class);
		textArea_Desc_03 = (UnicaseReportTextArea) createComponent(textArea_Desc_03, "desc_03", UnicaseReportTextArea.class);
		textArea_Desc_04 = (UnicaseReportTextArea) createComponent(textArea_Desc_04, "desc_04", UnicaseReportTextArea.class);
		textArea_Desc_05 = (UnicaseReportTextArea) createComponent(textArea_Desc_05, "desc_05", UnicaseReportTextArea.class);
		textArea_Desc_06 = (UnicaseReportTextArea) createComponent(textArea_Desc_06, "desc_06", UnicaseReportTextArea.class);
		textArea_Desc_07 = (UnicaseReportTextArea) createComponent(textArea_Desc_07, "desc_07", UnicaseReportTextArea.class);
		textArea_Desc_08 = (UnicaseReportTextArea) createComponent(textArea_Desc_08, "desc_08", UnicaseReportTextArea.class);
		textArea_Desc_09 = (UnicaseReportTextArea) createComponent(textArea_Desc_09, "desc_09", UnicaseReportTextArea.class);
		textArea_Desc_10 = (UnicaseReportTextArea) createComponent(textArea_Desc_10, "desc_10", UnicaseReportTextArea.class);

		textField_Int_01 = (UnicaseReportTextField) createComponent(textField_Int_01, "int_01", UnicaseReportTextField.class);
		textField_Int_02 = (UnicaseReportTextField) createComponent(textField_Int_02, "int_02", UnicaseReportTextField.class);
		textField_Int_03 = (UnicaseReportTextField) createComponent(textField_Int_03, "int_03", UnicaseReportTextField.class);
		textField_Int_04 = (UnicaseReportTextField) createComponent(textField_Int_04, "int_04", UnicaseReportTextField.class);
		textField_Int_05 = (UnicaseReportTextField) createComponent(textField_Int_05, "int_05", UnicaseReportTextField.class);
		textField_Int_06 = (UnicaseReportTextField) createComponent(textField_Int_06, "int_06", UnicaseReportTextField.class);
		textField_Int_07 = (UnicaseReportTextField) createComponent(textField_Int_07, "int_07", UnicaseReportTextField.class);
		textField_Int_08 = (UnicaseReportTextField) createComponent(textField_Int_08, "int_08", UnicaseReportTextField.class);
		textField_Int_09 = (UnicaseReportTextField) createComponent(textField_Int_09, "int_09", UnicaseReportTextField.class);
		textField_Int_10 = (UnicaseReportTextField) createComponent(textField_Int_10, "int_10", UnicaseReportTextField.class);

		textField_Mon_01 = (UnicaseReportTextField) createComponent(textField_Mon_01, "mon_01", UnicaseReportTextField.class);
		textField_Mon_02 = (UnicaseReportTextField) createComponent(textField_Mon_02, "mon_02", UnicaseReportTextField.class);
		textField_Mon_03 = (UnicaseReportTextField) createComponent(textField_Mon_03, "mon_03", UnicaseReportTextField.class);
		textField_Mon_04 = (UnicaseReportTextField) createComponent(textField_Mon_04, "mon_04", UnicaseReportTextField.class);
		textField_Mon_05 = (UnicaseReportTextField) createComponent(textField_Mon_05, "mon_05", UnicaseReportTextField.class);
		textField_Mon_06 = (UnicaseReportTextField) createComponent(textField_Mon_06, "mon_06", UnicaseReportTextField.class);
		textField_Mon_07 = (UnicaseReportTextField) createComponent(textField_Mon_07, "mon_07", UnicaseReportTextField.class);
		textField_Mon_08 = (UnicaseReportTextField) createComponent(textField_Mon_08, "mon_08", UnicaseReportTextField.class);
		textField_Mon_09 = (UnicaseReportTextField) createComponent(textField_Mon_09, "mon_09", UnicaseReportTextField.class);
		textField_Mon_10 = (UnicaseReportTextField) createComponent(textField_Mon_10, "mon_10", UnicaseReportTextField.class);

		dateField_Date_01 = (UnicaseReportMultiDateField) createComponent(dateField_Date_01, "date_01", UnicaseReportMultiDateField.class);
		dateField_Date_02 = (UnicaseReportMultiDateField) createComponent(dateField_Date_02, "date_02", UnicaseReportMultiDateField.class);
		dateField_Date_03 = (UnicaseReportMultiDateField) createComponent(dateField_Date_03, "date_03", UnicaseReportMultiDateField.class);
		dateField_Date_04 = (UnicaseReportMultiDateField) createComponent(dateField_Date_04, "date_04", UnicaseReportMultiDateField.class);
		dateField_Date_05 = (UnicaseReportMultiDateField) createComponent(dateField_Date_05, "date_05", UnicaseReportMultiDateField.class);

		comboBox_Ck_01 = (UnicaseReportComboBox) createComponent(comboBox_Ck_01, "ck_01", UnicaseReportComboBox.class);
		comboBox_Ck_02 = (UnicaseReportComboBox) createComponent(comboBox_Ck_02, "ck_02", UnicaseReportComboBox.class);
		comboBox_Ck_03 = (UnicaseReportComboBox) createComponent(comboBox_Ck_03, "ck_03", UnicaseReportComboBox.class);
		comboBox_Ck_04 = (UnicaseReportComboBox) createComponent(comboBox_Ck_04, "ck_04", UnicaseReportComboBox.class);
		comboBox_Ck_05 = (UnicaseReportComboBox) createComponent(comboBox_Ck_05, "ck_05", UnicaseReportComboBox.class);
		comboBox_Ck_06 = (UnicaseReportComboBox) createComponent(comboBox_Ck_06, "ck_06", UnicaseReportComboBox.class);
		comboBox_Ck_07 = (UnicaseReportComboBox) createComponent(comboBox_Ck_07, "ck_07", UnicaseReportComboBox.class);
		comboBox_Ck_08 = (UnicaseReportComboBox) createComponent(comboBox_Ck_08, "ck_08", UnicaseReportComboBox.class);
		comboBox_Ck_09 = (UnicaseReportComboBox) createComponent(comboBox_Ck_09, "ck_09", UnicaseReportComboBox.class);
		comboBox_Ck_10 = (UnicaseReportComboBox) createComponent(comboBox_Ck_10, "ck_10", UnicaseReportComboBox.class);
		comboBox_Ck_11 = (UnicaseReportComboBox) createComponent(comboBox_Ck_11, "ck_11", UnicaseReportComboBox.class);
		comboBox_Ck_12 = (UnicaseReportComboBox) createComponent(comboBox_Ck_12, "ck_12", UnicaseReportComboBox.class);
		comboBox_Ck_13 = (UnicaseReportComboBox) createComponent(comboBox_Ck_13, "ck_13", UnicaseReportComboBox.class);
		comboBox_Ck_14 = (UnicaseReportComboBox) createComponent(comboBox_Ck_14, "ck_14", UnicaseReportComboBox.class);
		comboBox_Ck_15 = (UnicaseReportComboBox) createComponent(comboBox_Ck_15, "ck_15", UnicaseReportComboBox.class);

		textField_Value_01 = (UnicaseReportTextField) createComponent(textField_Value_01, "value_01", UnicaseReportTextField.class);
		textField_Value_02 = (UnicaseReportTextField) createComponent(textField_Value_02, "value_02", UnicaseReportTextField.class);
		textField_Value_03 = (UnicaseReportTextField) createComponent(textField_Value_03, "value_03", UnicaseReportTextField.class);
		textField_Value_04 = (UnicaseReportTextField) createComponent(textField_Value_04, "value_04", UnicaseReportTextField.class);
		textField_Value_05 = (UnicaseReportTextField) createComponent(textField_Value_05, "value_05", UnicaseReportTextField.class);
		textField_Value_06 = (UnicaseReportTextField) createComponent(textField_Value_06, "value_06", UnicaseReportTextField.class);
		textField_Value_07 = (UnicaseReportTextField) createComponent(textField_Value_07, "value_07", UnicaseReportTextField.class);
		textField_Value_08 = (UnicaseReportTextField) createComponent(textField_Value_08, "value_08", UnicaseReportTextField.class);
		textField_Value_09 = (UnicaseReportTextField) createComponent(textField_Value_09, "value_09", UnicaseReportTextField.class);
		textField_Value_10 = (UnicaseReportTextField) createComponent(textField_Value_10, "value_10", UnicaseReportTextField.class);

		addComponentInOrder();
	}

	private Component createComponent(Object object, String name, Class<?> clazz) {
		Component component = null;
		Field field = controller.getField(name);

		if (field == null || !field.getVisible())
			return component;
		try {

			object = clazz.newInstance();
			component = (Component) object;

			((IReportField) component).setFieldName(name);
			((IReportField) component).setReportAlias(field.getreportAlias());

			if (AbstractTextField.class.isAssignableFrom(clazz)) {
				((AbstractTextField) component).setImmediate(true);
				component.setWidth(field.getWidth());
				component.setHeight(field.getHeight());
			}
			if (DateField.class.isAssignableFrom(clazz)) {
				((DateField) component).setDateFormat("dd.MM.yyyy");
				((DateField) component).setImmediate(true);
				component.setWidth(field.getWidth());
				component.setHeight(field.getHeight());
			}
			if (ComboBox.class.isAssignableFrom(clazz)) {
				((UnicaseComboBox) component).setFilteringMode(FilteringMode.CONTAINS);
				((UnicaseComboBox) component).setImmediate(true);
				component.setWidth(field.getWidth());
				component.setHeight(field.getHeight());
			}

			if (HorizontalLayout.class.isAssignableFrom(clazz)) {
				((UnicaseReportMultiDateField) component).setFieldName(name);
				((UnicaseReportMultiDateField) component).setReportAlias(field.getreportAlias());
			}

			if (component != null && field != null) {
				layoutOrganization.put(field, component);
				fieldsList.add(field);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return component;

	}

	private String setValue(String fieldName, IReportField component) {
		if (component != null) {
			return component.getReportValue();
		}

		return "";
	}

	public String buildReportParameters() {
		String query = "";

		query += setValue("code", textField_Code);
		query += setValue("value", textField_Value);
		query += setValue("shortvalue", textField_Shortvalue);
		query += setValue("description", textField_Description);
		query += setValue("orderno", textField_Orderno);
		query += setValue("desc_01", textArea_Desc_01);
		query += setValue("desc_02", textArea_Desc_02);
		query += setValue("desc_03", textArea_Desc_03);
		query += setValue("desc_04", textArea_Desc_04);
		query += setValue("desc_05", textArea_Desc_05);
		query += setValue("desc_06", textArea_Desc_06);
		query += setValue("desc_07", textArea_Desc_07);
		query += setValue("desc_08", textArea_Desc_08);
		query += setValue("desc_09", textArea_Desc_09);
		query += setValue("desc_10", textArea_Desc_10);
		query += setValue("int_01", textField_Int_01);
		query += setValue("int_02", textField_Int_02);
		query += setValue("int_03", textField_Int_03);
		query += setValue("int_04", textField_Int_04);
		query += setValue("int_05", textField_Int_05);
		query += setValue("int_06", textField_Int_06);
		query += setValue("int_07", textField_Int_07);
		query += setValue("int_08", textField_Int_08);
		query += setValue("int_09", textField_Int_09);
		query += setValue("int_10", textField_Int_10);
		query += setValue("mon_01", textField_Mon_01);
		query += setValue("mon_02", textField_Mon_02);
		query += setValue("mon_03", textField_Mon_03);
		query += setValue("mon_04", textField_Mon_04);
		query += setValue("mon_05", textField_Mon_05);
		query += setValue("mon_06", textField_Mon_06);
		query += setValue("mon_07", textField_Mon_07);
		query += setValue("mon_08", textField_Mon_08);
		query += setValue("mon_09", textField_Mon_09);
		query += setValue("mon_10", textField_Mon_10);
		query += setValue("date_01", dateField_Date_01);
		query += setValue("date_02", dateField_Date_02);
		query += setValue("date_03", dateField_Date_03);
		query += setValue("date_04", dateField_Date_04);
		query += setValue("date_05", dateField_Date_05);
		query += setValue("ck_01", comboBox_Ck_01);
		query += setValue("ck_02", comboBox_Ck_02);
		query += setValue("ck_03", comboBox_Ck_03);
		query += setValue("ck_04", comboBox_Ck_04);
		query += setValue("ck_05", comboBox_Ck_05);
		query += setValue("ck_06", comboBox_Ck_06);
		query += setValue("ck_07", comboBox_Ck_07);
		query += setValue("ck_08", comboBox_Ck_08);
		query += setValue("ck_09", comboBox_Ck_09);
		query += setValue("ck_10", comboBox_Ck_10);
		query += setValue("ck_11", comboBox_Ck_11);
		query += setValue("ck_12", comboBox_Ck_12);
		query += setValue("ck_13", comboBox_Ck_13);
		query += setValue("ck_14", comboBox_Ck_14);
		query += setValue("ck_15", comboBox_Ck_15);

		query += setValue("value_01", textField_Value_01);
		query += setValue("value_02", textField_Value_02);
		query += setValue("value_03", textField_Value_03);
		query += setValue("value_04", textField_Value_04);
		query += setValue("value_05", textField_Value_05);
		query += setValue("value_06", textField_Value_06);
		query += setValue("value_07", textField_Value_07);
		query += setValue("value_08", textField_Value_08);
		query += setValue("value_09", textField_Value_09);
		query += setValue("value_10", textField_Value_10);

		return query;
	}

	private void addComponentInOrder() {
		Component component = null;

		for (Field field : layoutOrganization.keySet()) {
			component = layoutOrganization.get(field);
			GridLayout gridLayout = new GridLayout(2, 1);
			gridLayout.setSpacing(false);
			gridLayout.setMargin(false);
			Label label = new Label(field.getLabelText());
			label.setWidth("200px");
			gridLayout.addComponent(label, 0, 0);
			gridLayout.addComponent(component, 1, 0);
			gridLayout.setComponentAlignment(component, Alignment.MIDDLE_LEFT);

			if (field.getColumnIndex().intValue() == 0) {
				leftVerticalLayout.addComponent(gridLayout);
			} else
				rightVerticalLayout.addComponent(gridLayout);
		}

		layoutOrganization.clear();
	}

	@Override
	public void update() {

	}

	@Override
	public void setViewToEntity() {

	}

	@Override
	protected boolean valid(Component component) {
		if (component != null) {
			IReportField reportField = (IReportField) component;
			return reportField.isFieldValid();
		}

		return true;
	}

	@Override
	public boolean validate() {
		boolean result = true;

		if (textField_Code != null)
			result &= valid(textField_Code);
		if (textField_Value != null)
			result &= valid(textField_Value);
		if (textField_Shortvalue != null)
			result &= valid(textField_Shortvalue);
		if (textField_Description != null)
			result &= valid(textField_Description);
		if (textField_Orderno != null)
			result &= valid(textField_Orderno);
		if (textArea_Desc_01 != null)
			result &= valid(textArea_Desc_01);
		if (textArea_Desc_02 != null)
			result &= valid(textArea_Desc_02);
		if (textArea_Desc_03 != null)
			result &= valid(textArea_Desc_03);
		if (textArea_Desc_04 != null)
			result &= valid(textArea_Desc_04);
		if (textArea_Desc_05 != null)
			result &= valid(textArea_Desc_05);
		if (textArea_Desc_06 != null)
			result &= valid(textArea_Desc_06);
		if (textArea_Desc_07 != null)
			result &= valid(textArea_Desc_07);
		if (textArea_Desc_08 != null)
			result &= valid(textArea_Desc_08);
		if (textArea_Desc_09 != null)
			result &= valid(textArea_Desc_09);
		if (textArea_Desc_10 != null)
			result &= valid(textArea_Desc_10);

		if (textField_Int_01 != null)
			result &= valid(textField_Int_01);
		if (textField_Int_02 != null)
			result &= valid(textField_Int_02);
		if (textField_Int_03 != null)
			result &= valid(textField_Int_03);
		if (textField_Int_04 != null)
			result &= valid(textField_Int_04);
		if (textField_Int_05 != null)
			result &= valid(textField_Int_05);
		if (textField_Int_06 != null)
			result &= valid(textField_Int_06);
		if (textField_Int_07 != null)
			result &= valid(textField_Int_07);
		if (textField_Int_08 != null)
			result &= valid(textField_Int_08);
		if (textField_Int_09 != null)
			result &= valid(textField_Int_09);
		if (textField_Int_10 != null)
			result &= valid(textField_Int_10);

		if (textField_Mon_01 != null)
			result &= valid(textField_Mon_01);
		if (textField_Mon_02 != null)
			result &= valid(textField_Mon_02);
		if (textField_Mon_03 != null)
			result &= valid(textField_Mon_03);
		if (textField_Mon_04 != null)
			result &= valid(textField_Mon_04);
		if (textField_Mon_05 != null)
			result &= valid(textField_Mon_05);
		if (textField_Mon_06 != null)
			result &= valid(textField_Mon_06);
		if (textField_Mon_07 != null)
			result &= valid(textField_Mon_07);
		if (textField_Mon_08 != null)
			result &= valid(textField_Mon_08);
		if (textField_Mon_09 != null)
			result &= valid(textField_Mon_09);
		if (textField_Mon_10 != null)
			result &= valid(textField_Mon_10);

		if (dateField_Date_01 != null)
			result &= valid(dateField_Date_01);
		if (dateField_Date_02 != null)
			result &= valid(dateField_Date_02);
		if (dateField_Date_03 != null)
			result &= valid(dateField_Date_03);
		if (dateField_Date_04 != null)
			result &= valid(dateField_Date_04);
		if (dateField_Date_05 != null)
			result &= valid(dateField_Date_05);

		if (comboBox_Ck_01 != null)
			result &= valid(comboBox_Ck_01);
		if (comboBox_Ck_02 != null)
			result &= valid(comboBox_Ck_02);
		if (comboBox_Ck_03 != null)
			result &= valid(comboBox_Ck_03);
		if (comboBox_Ck_04 != null)
			result &= valid(comboBox_Ck_04);
		if (comboBox_Ck_05 != null)
			result &= valid(comboBox_Ck_05);
		if (comboBox_Ck_06 != null)
			result &= valid(comboBox_Ck_06);
		if (comboBox_Ck_07 != null)
			result &= valid(comboBox_Ck_07);
		if (comboBox_Ck_08 != null)
			result &= valid(comboBox_Ck_08);
		if (comboBox_Ck_09 != null)
			result &= valid(comboBox_Ck_09);
		if (comboBox_Ck_10 != null)
			result &= valid(comboBox_Ck_10);
		if (comboBox_Ck_11 != null)
			result &= valid(comboBox_Ck_11);
		if (comboBox_Ck_12 != null)
			result &= valid(comboBox_Ck_12);
		if (comboBox_Ck_13 != null)
			result &= valid(comboBox_Ck_13);
		if (comboBox_Ck_14 != null)
			result &= valid(comboBox_Ck_14);
		if (comboBox_Ck_15 != null)
			result &= valid(comboBox_Ck_15);

		if (textField_Value_01 != null)
			result &= valid(textField_Value_01);
		if (textField_Value_02 != null)
			result &= valid(textField_Value_02);
		if (textField_Value_03 != null)
			result &= valid(textField_Value_03);
		if (textField_Value_04 != null)
			result &= valid(textField_Value_04);
		if (textField_Value_05 != null)
			result &= valid(textField_Value_05);
		if (textField_Value_06 != null)
			result &= valid(textField_Value_06);
		if (textField_Value_07 != null)
			result &= valid(textField_Value_07);
		if (textField_Value_08 != null)
			result &= valid(textField_Value_08);
		if (textField_Value_09 != null)
			result &= valid(textField_Value_09);
		if (textField_Value_10 != null)
			result &= valid(textField_Value_10);

		return result;
	}

	@Override
	public List<Action> getActions() {
		return actionList;
	}

	@Override
	public void refresh() {
		update();
		initLayout();
	}

}

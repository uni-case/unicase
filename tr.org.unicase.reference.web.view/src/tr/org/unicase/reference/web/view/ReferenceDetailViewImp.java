package tr.org.unicase.reference.web.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import tr.org.unicase.authentication.service.model.User;
import tr.org.unicase.kernel.model.AbstractEntity;
import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.kernel.model.IEntity;
import tr.org.unicase.reference.model.ReferenceEntity;
import tr.org.unicase.reference.web.controller.ReferenceControllerImp;
import tr.org.unicase.reference.web.view.internals.config.Configuration;
import tr.org.unicase.web.app.IconHelper;
import tr.org.unicase.web.component.IComboBoxValueConverter;
import tr.org.unicase.web.component.UnicaseComboBox;
import tr.org.unicase.web.component.UnicaseFileDownloader;
import tr.org.unicase.web.component.UnicaseTable;
import tr.org.unicase.web.component.UnicaseUploadBox;
import tr.org.unicase.web.component.excel.UnicaseExcelExportButton;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.action.api.UnicaseActionManager;
import tr.org.unicase.web.view.api.AbstractView;
import tr.org.unicase.web.view.validator.NullValidator;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.ValoTheme;

public class ReferenceDetailViewImp extends AbstractView<ReferenceControllerImp> implements Property.ValueChangeListener {

	private GridLayout mainGridLayout = new GridLayout(2, 1);
	private VerticalLayout leftVerticalLayout;
	private VerticalLayout rightVerticalLayout;

	private TextField textField_Code;
	private TextField textField_Value;
	private TextField textField_Shortvalue;
	private TextArea textField_Description;
	private TextField textField_Orderno;

	private TextArea textArea_Desc_01;
	private TextArea textArea_Desc_02;
	private TextArea textArea_Desc_03;
	private TextArea textArea_Desc_04;
	private TextArea textArea_Desc_05;
	private TextArea textArea_Desc_06;
	private TextArea textArea_Desc_07;
	private TextArea textArea_Desc_08;
	private TextArea textArea_Desc_09;
	private TextArea textArea_Desc_10;

	private TextField textField_Int_01;
	private TextField textField_Int_02;
	private TextField textField_Int_03;
	private TextField textField_Int_04;
	private TextField textField_Int_05;
	private TextField textField_Int_06;
	private TextField textField_Int_07;
	private TextField textField_Int_08;
	private TextField textField_Int_09;
	private TextField textField_Int_10;

	private TextField textField_Mon_01;
	private TextField textField_Mon_02;
	private TextField textField_Mon_03;
	private TextField textField_Mon_04;
	private TextField textField_Mon_05;
	private TextField textField_Mon_06;
	private TextField textField_Mon_07;
	private TextField textField_Mon_08;
	private TextField textField_Mon_09;
	private TextField textField_Mon_10;

	private DateField dateField_Date_01;
	private DateField dateField_Date_02;
	private DateField dateField_Date_03;
	private DateField dateField_Date_04;
	private DateField dateField_Date_05;

	private UnicaseComboBox comboBox_Ck_01;
	private UnicaseComboBox comboBox_Ck_02;
	private UnicaseComboBox comboBox_Ck_03;
	private UnicaseComboBox comboBox_Ck_04;
	private UnicaseComboBox comboBox_Ck_05;
	private UnicaseComboBox comboBox_Ck_06;
	private UnicaseComboBox comboBox_Ck_07;
	private UnicaseComboBox comboBox_Ck_08;
	private UnicaseComboBox comboBox_Ck_09;
	private UnicaseComboBox comboBox_Ck_10;
	private UnicaseComboBox comboBox_Ck_11;
	private UnicaseComboBox comboBox_Ck_12;
	private UnicaseComboBox comboBox_Ck_13;
	private UnicaseComboBox comboBox_Ck_14;
	private UnicaseComboBox comboBox_Ck_15;

	private TextField textField_Value_01;
	private TextField textField_Value_02;
	private TextField textField_Value_03;
	private TextField textField_Value_04;
	private TextField textField_Value_05;
	private TextField textField_Value_06;
	private TextField textField_Value_07;
	private TextField textField_Value_08;
	private TextField textField_Value_09;
	private TextField textField_Value_10;

	private ReferenceEntity owner = null;

	private Map<Field, Component> layoutOrganization = new TreeMap<Field, Component>();

	private UnicaseUploadBox textField_byte_01;

	private Button downloadButton;

	private List<Field> typeFields;
	private List<Field> fieldsList;
	private List<Action> childActions;
	private HorizontalLayout buttonLayout = new HorizontalLayout();

	public ReferenceDetailViewImp(Long entityTypeId, Long ownerId, String refOwner) {
		this.entityTypeId = entityTypeId;
		this.ownerId = ownerId;
		this.refOwner = refOwner;
		layoutOrganization.clear();

		setController(new ReferenceControllerImp(entityTypeId, ownerId, refOwner));
		if (ownerId != null && ownerId.longValue() > 0) {
			owner = controller.findById(ownerId);
			controller.findAll(entityTypeId, ownerId, refOwner);
		}
		buildLeftVerticalLayout();
		buildRightVerticalLayout();

		buildLayout();

		mainGridLayout.setId("mainGridLayoutId");

		if (leftVerticalLayout != null)
			mainGridLayout.addComponent(leftVerticalLayout, 0, 0);

		if (rightVerticalLayout != null)
			mainGridLayout.addComponent(rightVerticalLayout, 1, 0);

		typeFields = getController().getTypeFields();
		displayActiveButtonActions();

		fieldsList = getController().getFieldsList();

		createTable();
	}

	private void displayActiveButtonActions() {

		List<ReferenceEntity> activeActionList = null;
		User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
		String userRole = user.getRole();
		childActions = new ArrayList<Action>();
		if (userRole != null) {
			activeActionList = controller.findActiveActionList(userRole);
			if (activeActionList != null && activeActionList.size() > 0) {
				String clazz = null;
				Action tempAction = null;
				for (final ReferenceEntity action : activeActionList) {
					if (action.getValue_04() != null && action.getValue_04().equalsIgnoreCase("tr.org.unicase.reference.web.view.ReferenceDetailViewImp")) {
						if (action.getValue_02() != null) {
							clazz = action.getValue_02();
							tempAction = UnicaseActionManager.getInstance().createInstance(clazz, "1", null, action.getValue_01(), action.getValue_03(), "clazz", controller, this, null);
							if (tempAction != null)
								actionList.add(tempAction);
						}
					}
					if (action.getInt_01() != null && typeFields.size() > 0) {
						clazz = "tr.org.unicase.kernel.web.view.action.internals.DetailsActionImpl";
						for (final Field field : typeFields) {
							if (field.getEntityTypeId() != null && field.getRefColumn() != null) {
								if (action.getInt_01() == field.getEntityTypeId().intValue() && field.getRefColumn().equalsIgnoreCase("ck_owner")) {
									tempAction = UnicaseActionManager.getInstance().createInstance(clazz, "1", null, field.getDetailIcon(), field.getLabelText(), "tr.org.unicase.reference.web.view.ReferenceDetailViewImp", controller, this, field);
									if (tempAction != null)
										childActions.add(tempAction);
								}
							}
						}
					}
				}
				clazz = null;
				tempAction = null;
				activeActionList.clear();
			}
			activeActionList = null;
		}

		userRole = null;
		user = null;
	}

	@Override
	public String getOwnerInfo() {
		if (owner != null)
			return owner.toString();
		return null;
	}

	private void createTable() {

		table = new UnicaseTable();
		table.setStyleName(ValoTheme.TABLE_COMPACT);
		table.setPageLength(7);
		table.setImmediate(true);
		table.setSelectable(true);
		table.setWidth("100%");
		table.setColumnReorderingAllowed(true);
		table.setColumnCollapsingAllowed(true);
		table.setNullSelectionAllowed(false);

		table.setContainerDataSource(controller.getDataSource());

		if (table.getContainerDataSource() != null && table.getContainerDataSource().size() > 0) {
			table.setPageLength(7);
			table.setCaption("Tabloda " + table.size() + " adet kayıt bulunmaltadır.");
		} else {
			table.setPageLength(2);
			table.setCaption("Tabloda kayıt bulunmamaktadır.");
		}
		table.setVisibleColumns(controller.getNaturalColumnOrder());
		table.setColumnHeaders(controller.getColumnHeaderValues());

		for (String col : controller.getNaturalColumnOrder()) {
			table.setColumnCollapsible(col, true);
		}

		List<ReferenceEntity> list = getController().getEntityList();
		if (list != null && list.size() > 0) {
			ReferenceEntity entity = list.get(0);
			getController().setEntity(entity);
			table.select(entity);
		}

		table.addValueChangeListener(this);
	}

	private UnicaseComboBox getComboBox(String fieldName) {
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
					final String depends = field.getDepends();
					if (depends != null && !depends.trim().isEmpty() && depends.indexOf("ck_") >= 0) {
						final UnicaseComboBox parentComboBox = getComboBox(depends);
						if (parentComboBox != null) {
							parentComboBox.addValueChangeListener(new ValueChangeListener() {

								@Override
								public void valueChange(ValueChangeEvent event) {
									ReferenceEntity parentEntity = (ReferenceEntity) parentComboBox.getSelectedValue();
									if (parentEntity != null) {
										combobox.setContainerDataSource(getController().getDataSource(field.getRefTypeId(), parentEntity.getId(), "ck_owner"));
									}
								}
							});
						}
					} else {
						combobox.setContainerDataSource(getController().getDataSource(field.getRefTypeId(), null, null));
					}
				}
			}

		}

	}

	private boolean isFieldGenerated(String code) {
		return (controller.getField(code).isGenerated());
	}

	private boolean isViewEmpty() {
		boolean result = true;

		if (isSearchField("code")) {
			if (!isComponentEmpty(textField_Code))
				return false;
		}

		if (isSearchField("value")) {
			if (!isComponentEmpty(textField_Value))
				return false;
		}

		if (isSearchField("shortvalue")) {
			if (!isComponentEmpty(textField_Shortvalue))
				return false;
		}

		if (isSearchField("description")) {
			if (!isComponentEmpty(textField_Description))
				return false;
		}

		if (isSearchField("orderno")) {
			if (!isComponentEmpty(textField_Orderno))
				return false;

		}

		if (isSearchField("desc_01")) {
			if (!isComponentEmpty(textArea_Desc_01))
				return false;
		}

		if (isSearchField("desc_02")) {
			if (!isComponentEmpty(textArea_Desc_02))
				return false;
		}

		if (isSearchField("desc_03")) {
			if (!isComponentEmpty(textArea_Desc_03))
				return false;
		}

		if (isSearchField("desc_04")) {
			if (!isComponentEmpty(textArea_Desc_04))
				return false;
		}

		if (isSearchField("desc_05")) {
			if (!isComponentEmpty(textArea_Desc_05))
				return false;
		}

		if (isSearchField("desc_06")) {
			if (!isComponentEmpty(textArea_Desc_06))
				return false;
		}

		if (isSearchField("desc_07")) {
			if (!isComponentEmpty(textArea_Desc_07))
				return false;
		}

		if (isSearchField("desc_08")) {
			if (!isComponentEmpty(textArea_Desc_08))
				return false;
		}

		if (isSearchField("desc_09")) {
			if (!isComponentEmpty(textArea_Desc_09))
				return false;
		}

		if (isSearchField("desc_10")) {
			if (!isComponentEmpty(textArea_Desc_10))
				return false;
		}

		if (isSearchField("int_01")) {
			if (!isComponentEmpty(textField_Int_01))
				return false;
		}

		if (isSearchField("int_02")) {
			if (!isComponentEmpty(textField_Int_02))
				return false;
		}

		if (isSearchField("int_03")) {
			if (!isComponentEmpty(textField_Int_03))
				return false;
		}

		if (isSearchField("int_04")) {
			if (!isComponentEmpty(textField_Int_04))
				return false;
		}

		if (isSearchField("int_05")) {
			if (!isComponentEmpty(textField_Int_05))
				return false;
		}

		if (isSearchField("int_06")) {
			if (!isComponentEmpty(textField_Int_06))
				return false;
		}
		if (isSearchField("int_07")) {
			if (!isComponentEmpty(textField_Int_07))
				return false;
		}
		if (isSearchField("int_08")) {
			if (!isComponentEmpty(textField_Int_08))
				return false;
		}
		if (isSearchField("int_09")) {
			if (!isComponentEmpty(textField_Int_09))
				return false;
		}
		if (isSearchField("int_10")) {
			if (!isComponentEmpty(textField_Int_10))
				return false;
		}

		if (isSearchField("mon_01")) {
			if (!isComponentEmpty(textField_Mon_01))
				return false;
		}
		if (isSearchField("mon_02")) {
			if (!isComponentEmpty(textField_Mon_02))
				return false;
		}
		if (isSearchField("mon_03")) {
			if (!isComponentEmpty(textField_Mon_03))
				return false;
		}
		if (isSearchField("mon_04")) {
			if (!isComponentEmpty(textField_Mon_04))
				return false;
		}
		if (isSearchField("mon_05")) {
			if (!isComponentEmpty(textField_Mon_05))
				return false;
		}
		if (isSearchField("mon_06")) {
			if (!isComponentEmpty(textField_Mon_06))
				return false;
		}
		if (isSearchField("mon_07")) {
			if (!isComponentEmpty(textField_Mon_07))
				return false;
		}
		if (isSearchField("mon_08")) {
			if (!isComponentEmpty(textField_Mon_08))
				return false;
		}

		if (isSearchField("mon_09")) {
			if (!isComponentEmpty(textField_Mon_09))
				return false;
		}

		if (isSearchField("mon_10")) {
			if (!isComponentEmpty(textField_Mon_10))
				return false;
		}

		if (isSearchField("date_01")) {
			if (!isComponentEmpty(dateField_Date_01))
				return false;
		}

		if (isSearchField("date_02")) {
			if (!isComponentEmpty(dateField_Date_02))
				return false;
		}

		if (isSearchField("date_03")) {
			if (!isComponentEmpty(dateField_Date_03))
				return false;
		}

		if (isSearchField("date_04")) {
			if (!isComponentEmpty(dateField_Date_04))
				return false;
		}

		if (isSearchField("date_05")) {
			if (!isComponentEmpty(dateField_Date_05))
				return false;
		}

		if (isSearchField("ck_01")) {
			if (!isComponentEmpty(comboBox_Ck_01))
				return false;
		}

		if (isSearchField("ck_02")) {
			if (!isComponentEmpty(comboBox_Ck_02))
				return false;
		}

		if (isSearchField("ck_03")) {
			if (!isComponentEmpty(comboBox_Ck_03))
				return false;
		}

		if (isSearchField("ck_04")) {
			if (!isComponentEmpty(comboBox_Ck_04))
				return false;
		}

		if (isSearchField("ck_05")) {
			if (!isComponentEmpty(comboBox_Ck_05))
				return false;
		}

		if (isSearchField("ck_06")) {
			if (!isComponentEmpty(comboBox_Ck_06))
				return false;
		}

		if (isSearchField("ck_07")) {
			if (!isComponentEmpty(comboBox_Ck_07))
				return false;
		}

		if (isSearchField("ck_08")) {
			if (!isComponentEmpty(comboBox_Ck_08))
				return false;
		}

		if (isSearchField("ck_09")) {
			if (!isComponentEmpty(comboBox_Ck_09))
				return false;
		}

		if (isSearchField("ck_10")) {
			if (!isComponentEmpty(comboBox_Ck_10))
				return false;
		}

		if (isSearchField("ck_11")) {
			if (!isComponentEmpty(comboBox_Ck_11))
				return false;
		}

		if (isSearchField("ck_12")) {
			if (!isComponentEmpty(comboBox_Ck_12))
				return false;
		}

		if (isSearchField("ck_13")) {
			if (!isComponentEmpty(comboBox_Ck_13))
				return false;
		}

		if (isSearchField("ck_14")) {
			if (!isComponentEmpty(comboBox_Ck_14))
				return false;
		}

		if (isSearchField("ck_15")) {
			if (!isComponentEmpty(comboBox_Ck_15))
				return false;
		}

		if (isSearchField("value_01")) {
			if (!isComponentEmpty(textField_Value_01))
				return false;
		}
		if (isSearchField("value_02")) {
			if (!isComponentEmpty(textField_Value_02))
				return false;
		}
		if (isSearchField("value_03")) {
			if (!isComponentEmpty(textField_Value_03))
				return false;
		}
		if (isSearchField("value_04")) {
			if (!isComponentEmpty(textField_Value_04))
				return false;
		}
		if (isSearchField("value_05")) {
			if (!isComponentEmpty(textField_Value_05))
				return false;
		}
		if (isSearchField("value_06")) {
			if (!isComponentEmpty(textField_Value_06))
				return false;
		}
		if (isSearchField("value_07")) {
			if (!isComponentEmpty(textField_Value_07))
				return false;
		}
		if (isSearchField("value_08")) {
			if (!isComponentEmpty(textField_Value_08))
				return false;
		}

		if (isSearchField("value_09")) {
			if (!isComponentEmpty(textField_Value_09))
				return false;
		}

		if (isSearchField("value_10")) {
			if (!isComponentEmpty(textField_Value_10))
				return false;
		}

		return result;
	}

	private boolean isSearchField(String fieldName) {
		return (AbstractEntity.TRUE.equals(controller.getField(fieldName).getSearchable()));
	}

	private boolean isComponentEmpty(Component fieldComponent) {
		boolean result = true;

		Class<?> clazz = fieldComponent.getClass();
		if (AbstractTextField.class.isAssignableFrom(clazz)) {
			String value = ((AbstractTextField) fieldComponent).getValue();
			if (value != null && !value.trim().isEmpty())
				return false;
		} else if (DateField.class.isAssignableFrom(clazz)) {
			Date value = ((DateField) fieldComponent).getValue();
			if (value != null)
				return false;
		} else if (ComboBox.class.isAssignableFrom(clazz)) {
			Object value = ((ComboBox) fieldComponent).getValue();
			if (value != null)
				return false;
		}

		return result;
	}

	private void search() {
		controller.clearParameters();

		if (!isViewEmpty()) {

			addComponentValueAsParameter("code", textField_Code);

			addComponentValueAsParameter("value", textField_Value);

			addComponentValueAsParameter("shortvalue", textField_Shortvalue);

			addComponentValueAsParameter("description", textField_Description);

			addComponentValueAsParameter("orderno", textField_Orderno);

			addComponentValueAsParameter("desc_01", textArea_Desc_01);
			addComponentValueAsParameter("desc_02", textArea_Desc_02);
			addComponentValueAsParameter("desc_03", textArea_Desc_03);
			addComponentValueAsParameter("desc_04", textArea_Desc_04);
			addComponentValueAsParameter("desc_05", textArea_Desc_05);
			addComponentValueAsParameter("desc_06", textArea_Desc_06);
			addComponentValueAsParameter("desc_07", textArea_Desc_07);
			addComponentValueAsParameter("desc_08", textArea_Desc_08);
			addComponentValueAsParameter("desc_09", textArea_Desc_09);
			addComponentValueAsParameter("desc_10", textArea_Desc_10);

			addComponentValueAsParameter("int_01", textField_Int_01);
			addComponentValueAsParameter("int_02", textField_Int_02);
			addComponentValueAsParameter("int_03", textField_Int_03);
			addComponentValueAsParameter("int_04", textField_Int_04);
			addComponentValueAsParameter("int_05", textField_Int_05);
			addComponentValueAsParameter("int_06", textField_Int_06);
			addComponentValueAsParameter("int_07", textField_Int_07);
			addComponentValueAsParameter("int_08", textField_Int_08);
			addComponentValueAsParameter("int_09", textField_Int_09);
			addComponentValueAsParameter("int_10", textField_Int_10);

			addComponentValueAsParameter("mon_01", textField_Mon_01);
			addComponentValueAsParameter("mon_02", textField_Mon_02);
			addComponentValueAsParameter("mon_03", textField_Mon_03);
			addComponentValueAsParameter("mon_04", textField_Mon_04);
			addComponentValueAsParameter("mon_05", textField_Mon_05);
			addComponentValueAsParameter("mon_06", textField_Mon_06);
			addComponentValueAsParameter("mon_07", textField_Mon_07);
			addComponentValueAsParameter("mon_08", textField_Mon_08);
			addComponentValueAsParameter("mon_09", textField_Mon_09);
			addComponentValueAsParameter("mon_10", textField_Mon_10);

			addComponentValueAsParameter("date_01", dateField_Date_01);
			addComponentValueAsParameter("date_02", dateField_Date_02);
			addComponentValueAsParameter("date_03", dateField_Date_03);
			addComponentValueAsParameter("date_04", dateField_Date_04);
			addComponentValueAsParameter("date_05", dateField_Date_05);

			addComponentValueAsParameter("ck_01", comboBox_Ck_01);
			addComponentValueAsParameter("ck_02", comboBox_Ck_02);
			addComponentValueAsParameter("ck_03", comboBox_Ck_03);
			addComponentValueAsParameter("ck_04", comboBox_Ck_04);
			addComponentValueAsParameter("ck_05", comboBox_Ck_05);
			addComponentValueAsParameter("ck_06", comboBox_Ck_06);
			addComponentValueAsParameter("ck_07", comboBox_Ck_07);
			addComponentValueAsParameter("ck_08", comboBox_Ck_08);
			addComponentValueAsParameter("ck_09", comboBox_Ck_09);
			addComponentValueAsParameter("ck_10", comboBox_Ck_10);
			addComponentValueAsParameter("ck_11", comboBox_Ck_11);
			addComponentValueAsParameter("ck_12", comboBox_Ck_12);
			addComponentValueAsParameter("ck_13", comboBox_Ck_13);
			addComponentValueAsParameter("ck_14", comboBox_Ck_14);
			addComponentValueAsParameter("ck_15", comboBox_Ck_15);

			addComponentValueAsParameter("value_01", textField_Value_01);
			addComponentValueAsParameter("value_02", textField_Value_02);
			addComponentValueAsParameter("value_03", textField_Value_03);
			addComponentValueAsParameter("value_04", textField_Value_04);
			addComponentValueAsParameter("value_05", textField_Value_05);
			addComponentValueAsParameter("value_06", textField_Value_06);
			addComponentValueAsParameter("value_07", textField_Value_07);
			addComponentValueAsParameter("value_08", textField_Value_08);
			addComponentValueAsParameter("value_09", textField_Value_09);
			addComponentValueAsParameter("value_10", textField_Value_10);

		}

		controller.search();
		refreshTable();

	}

	private void addComponentValueAsParameter(String fieldName, Component fieldComponent) {
		if (isSearchField(fieldName) && !isFieldGenerated(fieldName)) {
			if (!isComponentEmpty(fieldComponent)) {
				controller.addParameter(fieldName, getComponentValue(fieldComponent));
			}
		}
	}

	private Object getComponentValue(Component fieldComponent) {
		Class<?> clazz = fieldComponent.getClass();
		Object value = null;
		if (AbstractTextField.class.isAssignableFrom(clazz)) {
			value = ((AbstractTextField) fieldComponent).getValue();
		} else if (DateField.class.isAssignableFrom(clazz)) {
			value = ((DateField) fieldComponent).getValue();
		} else if (ComboBox.class.isAssignableFrom(clazz)) {
			value = ((IEntity) ((ComboBox) fieldComponent).getValue()).getId();
		}

		return value;
	}

	private void buildRightVerticalLayout() {
		rightVerticalLayout = new VerticalLayout();
		rightVerticalLayout.setId("rightVerticalLayoutId");
		rightVerticalLayout.setSpacing(true);
		rightVerticalLayout.setMargin(true);
	}

	private void buildLeftVerticalLayout() {
		leftVerticalLayout = new VerticalLayout();
		leftVerticalLayout.setId("leftVerticalLayoutId");
		leftVerticalLayout.setSpacing(true);
		leftVerticalLayout.setMargin(true);
	}

	private VerticalLayout getTableLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.setId("tableLayout01");
		Button buttonSearch = new Button();
		// buttonSearch.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		buttonSearch.setStyleName(BaseTheme.BUTTON_LINK);
		buttonSearch.setDescription("Sorgula");
		buttonSearch.setCaption(buttonSearch.getDescription());
		buttonSearch.setIcon(IconHelper.getIcon32("zoom-search-2-icon.png"));
		buttonSearch.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				search();
			}
		});

		Button buttonClear = new Button();
		// buttonClear.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		buttonClear.setStyleName(BaseTheme.BUTTON_LINK);
		buttonClear.setDescription("Temizle");
		buttonClear.setCaption(buttonClear.getDescription());
		buttonClear.setIcon(IconHelper.getIcon32("recyclebin-icon.png"));
		buttonClear.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				clearForm();
			}
		});

		final UnicaseExcelExportButton excelExportButton = new UnicaseExcelExportButton();
		excelExportButton.setStyleName(BaseTheme.BUTTON_LINK);
		excelExportButton.setDescription("Excel Export");
		excelExportButton.setCaption("Excel Export");
		excelExportButton.setIcon(IconHelper.getIcon32("Excel-icon.png"));
		excelExportButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				excelExportButton.export(getController().getEntityList(), getController().getNaturalColumnOrder(), getController().getColumnHeaderValues());
			}
		});

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setId("buttonLayout");
		buttonLayout.addComponent(excelExportButton);
		buttonLayout.addComponent(buttonSearch);
		buttonLayout.addComponent(buttonClear);

		layout.addComponent(buttonLayout);
		layout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);
		layout.addComponent(table);

		return layout;
	}

	protected void clearForm() {
		if (isSearchField("code") && !isFieldGenerated("code")) {
			textField_Code.setValue("");
		}

		if (isSearchField("value") && !isFieldGenerated("value")) {
			textField_Value.setValue("");
		}

		if (isSearchField("shortvalue") && !isFieldGenerated("shortvalue")) {
			textField_Shortvalue.setValue("");
		}

		if (isSearchField("description") && !isFieldGenerated("description")) {
			textField_Description.setValue("");
		}

		if (isSearchField("orderno") && !isFieldGenerated("orderno")) {
			textField_Orderno.setValue("");
		}

		if (isSearchField("desc_01") && !isFieldGenerated("desc_01")) {
			textArea_Desc_01.setValue("");
		}

		if (isSearchField("desc_02") && !isFieldGenerated("desc_02")) {
			textArea_Desc_02.setValue("");
		}

		if (isSearchField("desc_03") && !isFieldGenerated("desc_03")) {
			textArea_Desc_03.setValue("");
		}

		if (isSearchField("desc_04") && !isFieldGenerated("desc_04")) {
			textArea_Desc_04.setValue("");
		}

		if (isSearchField("desc_05") && !isFieldGenerated("desc_05")) {
			textArea_Desc_05.setValue("");
		}

		if (isSearchField("desc_06") && !isFieldGenerated("desc_06")) {
			textArea_Desc_06.setValue("");
		}

		if (isSearchField("desc_07") && !isFieldGenerated("desc_07")) {
			textArea_Desc_07.setValue("");
		}

		if (isSearchField("desc_08") && !isFieldGenerated("desc_08")) {
			textArea_Desc_08.setValue("");
		}

		if (isSearchField("desc_09") && !isFieldGenerated("desc_09")) {
			textArea_Desc_09.setValue("");
		}

		if (isSearchField("desc_10") && !isFieldGenerated("desc_10")) {
			textArea_Desc_10.setValue("");
		}

		if (isSearchField("int_01") && !isFieldGenerated("int_01")) {
			textField_Int_01.setValue("");
		}

		if (isSearchField("int_02") && !isFieldGenerated("int_02")) {
			textField_Int_02.setValue("");
		}

		if (isSearchField("int_03") && !isFieldGenerated("int_03")) {
			textField_Int_03.setValue("");
		}

		if (isSearchField("int_04") && !isFieldGenerated("int_04")) {
			textField_Int_04.setValue("");
		}

		if (isSearchField("int_05") && !isFieldGenerated("int_05")) {
			textField_Int_05.setValue("");
		}

		if (isSearchField("int_06") && !isFieldGenerated("int_06")) {
			textField_Int_06.setValue("");
		}
		if (isSearchField("int_07") && !isFieldGenerated("int_07")) {
			textField_Int_07.setValue("");
		}
		if (isSearchField("int_08") && !isFieldGenerated("int_08")) {
			textField_Int_08.setValue("");
		}
		if (isSearchField("int_09") && !isFieldGenerated("int_09")) {
			textField_Int_09.setValue("");
		}
		if (isSearchField("int_10") && !isFieldGenerated("int_10")) {
			textField_Int_10.setValue("");
		}

		if (isSearchField("mon_01") && !isFieldGenerated("mon_01")) {
			textField_Mon_01.setValue("");
		}
		if (isSearchField("mon_02") && !isFieldGenerated("mon_02")) {
			textField_Mon_02.setValue("");
		}
		if (isSearchField("mon_03") && !isFieldGenerated("mon_03")) {
			textField_Mon_03.setValue("");
		}
		if (isSearchField("mon_04") && !isFieldGenerated("mon_04")) {
			textField_Mon_04.setValue("");
		}
		if (isSearchField("mon_05") && !isFieldGenerated("mon_05")) {
			textField_Mon_05.setValue("");
		}
		if (isSearchField("mon_06") && !isFieldGenerated("mon_06")) {
			textField_Mon_06.setValue("");
		}
		if (isSearchField("mon_07") && !isFieldGenerated("mon_07")) {
			textField_Mon_07.setValue("");
		}
		if (isSearchField("mon_08") && !isFieldGenerated("mon_08")) {
			textField_Mon_08.setValue("");
		}

		if (isSearchField("mon_09") && !isFieldGenerated("mon_09")) {
			textField_Mon_09.setValue("");
		}

		if (isSearchField("mon_10") && !isFieldGenerated("mon_10")) {
			textField_Mon_10.setValue("");
		}

		if (isSearchField("date_01") && !isFieldGenerated("date_01")) {
			dateField_Date_01.setValue(null);
		}

		if (isSearchField("date_02") && !isFieldGenerated("date_02")) {
			dateField_Date_02.setValue(null);
		}

		if (isSearchField("date_03") && !isFieldGenerated("date_03")) {
			dateField_Date_03.setValue(null);
		}

		if (isSearchField("date_04")) {
			dateField_Date_04.setValue(null);
		}

		if (isSearchField("date_05")) {
			dateField_Date_05.setValue(null);
		}

		ReferenceEntity nullEntity = new ReferenceEntity();

		if (isSearchField("ck_01")) {
			comboBox_Ck_01.select(nullEntity);
		}

		if (isSearchField("ck_02")) {
			comboBox_Ck_02.select(nullEntity);
		}

		if (isSearchField("ck_03")) {
			comboBox_Ck_03.select(nullEntity);
		}

		if (isSearchField("ck_04")) {
			comboBox_Ck_04.select(nullEntity);
		}

		if (isSearchField("ck_05")) {
			comboBox_Ck_05.select(nullEntity);
		}

		if (isSearchField("ck_06")) {
			comboBox_Ck_06.select(nullEntity);
		}

		if (isSearchField("ck_07")) {
			comboBox_Ck_07.select(nullEntity);
		}

		if (isSearchField("ck_08")) {
			comboBox_Ck_08.select(nullEntity);
		}

		if (isSearchField("ck_09")) {
			comboBox_Ck_09.select(nullEntity);
		}

		if (isSearchField("ck_10")) {
			comboBox_Ck_10.select(nullEntity);
		}

		if (isSearchField("ck_11")) {
			comboBox_Ck_11.select(nullEntity);
		}

		if (isSearchField("ck_12")) {
			comboBox_Ck_12.select(nullEntity);
		}

		if (isSearchField("ck_13")) {
			comboBox_Ck_13.select(nullEntity);
		}

		if (isSearchField("ck_14")) {
			comboBox_Ck_14.select(nullEntity);
		}

		if (isSearchField("ck_15")) {
			comboBox_Ck_15.select(nullEntity);
		}

		if (isSearchField("value_01")) {
			textField_Value_01.setValue("");
		}
		if (isSearchField("value_02")) {
			textField_Value_02.setValue("");
		}
		if (isSearchField("value_03")) {
			textField_Value_03.setValue("");
		}
		if (isSearchField("value_04")) {
			textField_Value_04.setValue("");
		}
		if (isSearchField("value_05")) {
			textField_Value_05.setValue("");
		}
		if (isSearchField("value_06")) {
			textField_Value_06.setValue("");
		}
		if (isSearchField("value_07")) {
			textField_Value_07.setValue("");
		}
		if (isSearchField("value_08")) {
			textField_Value_08.setValue("");
		}

		if (isSearchField("value_09")) {
			textField_Value_09.setValue("");
		}

		if (isSearchField("value_10")) {
			textField_Value_10.setValue("");
		}
	}

	@Override
	public Component getView() {
		mainVerticalLayout.setId("mainVerticalLayoutId");
		mainVerticalLayout.addComponent(mainGridLayout);
		mainVerticalLayout.addComponent(buttonLayout);
		mainVerticalLayout.addComponent(getTableLayout());
		mainVerticalLayout.setExpandRatio(mainGridLayout, 0);

		return mainVerticalLayout;
	}

	@Override
	public void addActionButton(Component component) {
		buttonLayout.addComponent(component);
	}

	private void buildLayout() {

		textField_Code = (TextField) createComponent(textField_Code, "code", TextField.class);

		textField_Value = (TextField) createComponent(textField_Value, "value", TextField.class);

		textField_Shortvalue = (TextField) createComponent(textField_Shortvalue, "shortvalue", TextField.class);

		textField_Description = (TextArea) createComponent(textField_Description, "description", TextArea.class);

		textField_Orderno = (TextField) createComponent(textField_Orderno, "orderno", TextField.class);

		textArea_Desc_01 = (TextArea) createComponent(textArea_Desc_01, "desc_01", TextArea.class);
		textArea_Desc_02 = (TextArea) createComponent(textArea_Desc_02, "desc_02", TextArea.class);
		textArea_Desc_03 = (TextArea) createComponent(textArea_Desc_03, "desc_03", TextArea.class);
		textArea_Desc_04 = (TextArea) createComponent(textArea_Desc_04, "desc_04", TextArea.class);
		textArea_Desc_05 = (TextArea) createComponent(textArea_Desc_05, "desc_05", TextArea.class);
		textArea_Desc_06 = (TextArea) createComponent(textArea_Desc_06, "desc_06", TextArea.class);
		textArea_Desc_07 = (TextArea) createComponent(textArea_Desc_07, "desc_07", TextArea.class);
		textArea_Desc_08 = (TextArea) createComponent(textArea_Desc_08, "desc_08", TextArea.class);
		textArea_Desc_09 = (TextArea) createComponent(textArea_Desc_09, "desc_09", TextArea.class);
		textArea_Desc_10 = (TextArea) createComponent(textArea_Desc_10, "desc_10", TextArea.class);

		textField_Int_01 = (TextField) createComponent(textField_Int_01, "int_01", TextField.class);
		textField_Int_02 = (TextField) createComponent(textField_Int_02, "int_02", TextField.class);
		textField_Int_03 = (TextField) createComponent(textField_Int_03, "int_03", TextField.class);
		textField_Int_04 = (TextField) createComponent(textField_Int_04, "int_04", TextField.class);
		textField_Int_05 = (TextField) createComponent(textField_Int_05, "int_05", TextField.class);
		textField_Int_06 = (TextField) createComponent(textField_Int_06, "int_06", TextField.class);
		textField_Int_07 = (TextField) createComponent(textField_Int_07, "int_07", TextField.class);
		textField_Int_08 = (TextField) createComponent(textField_Int_08, "int_08", TextField.class);
		textField_Int_09 = (TextField) createComponent(textField_Int_09, "int_09", TextField.class);
		textField_Int_10 = (TextField) createComponent(textField_Int_10, "int_10", TextField.class);

		textField_Mon_01 = (TextField) createComponent(textField_Mon_01, "mon_01", TextField.class);
		textField_Mon_02 = (TextField) createComponent(textField_Mon_02, "mon_02", TextField.class);
		textField_Mon_03 = (TextField) createComponent(textField_Mon_03, "mon_03", TextField.class);
		textField_Mon_04 = (TextField) createComponent(textField_Mon_04, "mon_04", TextField.class);
		textField_Mon_05 = (TextField) createComponent(textField_Mon_05, "mon_05", TextField.class);
		textField_Mon_06 = (TextField) createComponent(textField_Mon_06, "mon_06", TextField.class);
		textField_Mon_07 = (TextField) createComponent(textField_Mon_07, "mon_07", TextField.class);
		textField_Mon_08 = (TextField) createComponent(textField_Mon_08, "mon_08", TextField.class);
		textField_Mon_09 = (TextField) createComponent(textField_Mon_09, "mon_09", TextField.class);
		textField_Mon_10 = (TextField) createComponent(textField_Mon_10, "mon_10", TextField.class);

		dateField_Date_01 = (DateField) createComponent(dateField_Date_01, "date_01", PopupDateField.class);
		dateField_Date_02 = (DateField) createComponent(dateField_Date_02, "date_02", PopupDateField.class);
		dateField_Date_03 = (DateField) createComponent(dateField_Date_03, "date_03", PopupDateField.class);
		dateField_Date_04 = (DateField) createComponent(dateField_Date_04, "date_04", PopupDateField.class);
		dateField_Date_05 = (DateField) createComponent(dateField_Date_05, "date_05", PopupDateField.class);

		comboBox_Ck_01 = (UnicaseComboBox) createComponent(comboBox_Ck_01, "ck_01", UnicaseComboBox.class);
		comboBox_Ck_02 = (UnicaseComboBox) createComponent(comboBox_Ck_02, "ck_02", UnicaseComboBox.class);
		comboBox_Ck_03 = (UnicaseComboBox) createComponent(comboBox_Ck_03, "ck_03", UnicaseComboBox.class);
		comboBox_Ck_04 = (UnicaseComboBox) createComponent(comboBox_Ck_04, "ck_04", UnicaseComboBox.class);
		comboBox_Ck_05 = (UnicaseComboBox) createComponent(comboBox_Ck_05, "ck_05", UnicaseComboBox.class);
		comboBox_Ck_06 = (UnicaseComboBox) createComponent(comboBox_Ck_06, "ck_06", UnicaseComboBox.class);
		comboBox_Ck_07 = (UnicaseComboBox) createComponent(comboBox_Ck_07, "ck_07", UnicaseComboBox.class);
		comboBox_Ck_08 = (UnicaseComboBox) createComponent(comboBox_Ck_08, "ck_08", UnicaseComboBox.class);
		comboBox_Ck_09 = (UnicaseComboBox) createComponent(comboBox_Ck_09, "ck_09", UnicaseComboBox.class);
		comboBox_Ck_10 = (UnicaseComboBox) createComponent(comboBox_Ck_10, "ck_10", UnicaseComboBox.class);
		comboBox_Ck_11 = (UnicaseComboBox) createComponent(comboBox_Ck_11, "ck_11", UnicaseComboBox.class);
		comboBox_Ck_12 = (UnicaseComboBox) createComponent(comboBox_Ck_12, "ck_12", UnicaseComboBox.class);
		comboBox_Ck_13 = (UnicaseComboBox) createComponent(comboBox_Ck_13, "ck_13", UnicaseComboBox.class);
		comboBox_Ck_14 = (UnicaseComboBox) createComponent(comboBox_Ck_14, "ck_14", UnicaseComboBox.class);
		comboBox_Ck_15 = (UnicaseComboBox) createComponent(comboBox_Ck_15, "ck_15", UnicaseComboBox.class);

		textField_Value_01 = (TextField) createComponent(textField_Value_01, "value_01", TextField.class);
		textField_Value_02 = (TextField) createComponent(textField_Value_02, "value_02", TextField.class);
		textField_Value_03 = (TextField) createComponent(textField_Value_03, "value_03", TextField.class);
		textField_Value_04 = (TextField) createComponent(textField_Value_04, "value_04", TextField.class);
		textField_Value_05 = (TextField) createComponent(textField_Value_05, "value_05", TextField.class);
		textField_Value_06 = (TextField) createComponent(textField_Value_06, "value_06", TextField.class);
		textField_Value_07 = (TextField) createComponent(textField_Value_07, "value_07", TextField.class);
		textField_Value_08 = (TextField) createComponent(textField_Value_08, "value_08", TextField.class);
		textField_Value_09 = (TextField) createComponent(textField_Value_09, "value_09", TextField.class);
		textField_Value_10 = (TextField) createComponent(textField_Value_10, "value_10", TextField.class);

		textField_byte_01 = (UnicaseUploadBox) createComponent(textField_byte_01, "byte_01", UnicaseUploadBox.class);

		downloadButton = (Button) createComponent(downloadButton, "byte_01_name", Button.class);
		if (downloadButton != null)
			downloadButton.setStyleName(BaseTheme.BUTTON_LINK);

		addComponentInOrder();

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

	private void addComponentInOrder() {
		Component component = null;

		for (Field field : layoutOrganization.keySet()) {
			component = layoutOrganization.get(field);

			GridLayout gridLayout = new GridLayout(2, 1);
			gridLayout.setColumnExpandRatio(0, 3);
			gridLayout.setColumnExpandRatio(1, 2);
			gridLayout.setSpacing(false);
			gridLayout.setMargin(false);
			Label label = new Label(field.getLabelText());
			label.setWidth("100px");
			gridLayout.addComponent(label, 0, 0);
			gridLayout.addComponent(component, 1, 0);
			gridLayout.setComponentAlignment(component, Alignment.MIDDLE_LEFT);

			if (field.getColumnIndex() == 0) {
				leftVerticalLayout.addComponent(gridLayout);
			} else
				rightVerticalLayout.addComponent(gridLayout);
		}

		layoutOrganization.clear();
	}

	private void addValidatorToComponent(Component component, Validator validator) {
		Class<?> clazz = component.getClass();
		if (AbstractTextField.class.isAssignableFrom(clazz)) {
			if (validator instanceof RegexpValidator || validator instanceof NullValidator) {
				((AbstractTextField) component).addValidator(validator);
				((AbstractTextField) component).setValidationVisible(false);
			}
		} else if (DateField.class.isAssignableFrom(clazz)) {
			if ((validator instanceof NullValidator)) {
				((DateField) component).setValidationVisible(false);
				((DateField) component).addValidator(validator);
			}
		} else if (ComboBox.class.isAssignableFrom(clazz)) {
			if ((validator instanceof NullValidator)) {
				((ComboBox) component).setValidationVisible(false);
				((ComboBox) component).addValidator(validator);
			}
		}
	}

	private void addValidator(Field field, Component component) {

		if (AbstractEntity.TRUE.equals(field.getValidate())) {
			String fieldLabel = field.getLabelText();
			if (AbstractEntity.TRUE.equals(field.getNotNull()))
				addValidatorToComponent(component, new NullValidator(fieldLabel));
			String regex = field.getValidationRegex();
			if (regex != null && !regex.trim().isEmpty())
				addValidatorToComponent(component, new RegexpValidator(regex, fieldLabel + " " + field.getValidationErrorMessage()));
		}

	}

	private Component createComponent(Object object, String name, Class<?> clazz) {
		Component component = null;
		Field field = controller.getField(name);

		if (field == null || !field.getVisible())
			return component;

		try {
			object = clazz.newInstance();
			component = (Component) object;
			component.setWidth(field.getWidth());
			component.setHeight(field.getHeight());
			if (AbstractTextField.class.isAssignableFrom(clazz))
				((AbstractTextField) component).setNullRepresentation("");

			if (DateField.class.isAssignableFrom(clazz)) {
				((PopupDateField) component).setDateFormat("dd.MM.yyyy HH:mm:ss");
				((PopupDateField) component).setResolution(Resolution.MINUTE);
			}
			if (ComboBox.class.isAssignableFrom(clazz)) {
				((UnicaseComboBox) component).setFilteringMode(FilteringMode.CONTAINS);
				((UnicaseComboBox) component).setValueConverter(new IComboBoxValueConverter<ReferenceEntity>() {

					@Override
					public ReferenceEntity convert(Object value) {
						if (value == null)
							return null;
						return new ReferenceEntity((Long) value);
					}
				});
			}
			addValidator(field, component);

			if (field.isGenerated() || field.isReadonly()) {
				component.setEnabled(false);
			}

			layoutOrganization.put(field, component);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return component;
	}

	private void setStringValue(Field field, AbstractTextField component, String value) {
		if (field.isGenerated()) {
			if ((value == null || value.trim().isEmpty())) {
				component.setValue("Sistem No");
			} else {
				field.setGenerate(null);
				component.setValue(value);
			}
		} else {
			component.setValue(value);
		}
	}

	@Override
	public void update() {
		ReferenceEntity entity = controller.getEntity();
		if (entity == null) {
			return;
		}

		if (controller.getField("code").getVisible())
			setStringValue(controller.getField("code"), textField_Code, entity.getCode());

		if (controller.getField("value").getVisible())
			setStringValue(controller.getField("value"), textField_Value, entity.getValue());

		if (controller.getField("shortvalue").getVisible())
			setStringValue(controller.getField("shortvalue"), textField_Shortvalue, entity.getShortvalue());

		if (controller.getField("description").getVisible())
			setStringValue(controller.getField("description"), textField_Description, entity.getDescription());

		if (controller.getField("orderno").getVisible())
			setStringValue(controller.getField("orderno"), textField_Orderno, entity.getOrderno());

		if (controller.getField("desc_01").getVisible())
			setStringValue(controller.getField("desc_01"), textArea_Desc_01, entity.getDesc_01());
		if (controller.getField("desc_02").getVisible())
			setStringValue(controller.getField("desc_02"), textArea_Desc_02, entity.getDesc_02());
		if (controller.getField("desc_03").getVisible())
			setStringValue(controller.getField("desc_03"), textArea_Desc_03, entity.getDesc_03());
		if (controller.getField("desc_04").getVisible())
			setStringValue(controller.getField("desc_04"), textArea_Desc_04, entity.getDesc_04());
		if (controller.getField("desc_05").getVisible())
			setStringValue(controller.getField("desc_05"), textArea_Desc_05, entity.getDesc_05());
		if (controller.getField("desc_06").getVisible())
			setStringValue(controller.getField("desc_06"), textArea_Desc_06, entity.getDesc_06());
		if (controller.getField("desc_07").getVisible())
			setStringValue(controller.getField("desc_07"), textArea_Desc_07, entity.getDesc_07());
		if (controller.getField("desc_08").getVisible())
			setStringValue(controller.getField("desc_08"), textArea_Desc_08, entity.getDesc_08());
		if (controller.getField("desc_09").getVisible())
			setStringValue(controller.getField("desc_09"), textArea_Desc_09, entity.getDesc_09());
		if (controller.getField("desc_10").getVisible())
			setStringValue(controller.getField("desc_10"), textArea_Desc_10, entity.getDesc_10());

		if (controller.getField("int_01").getVisible())
			textField_Int_01.setValue(String.valueOf(entity.getInt_01()));
		if (controller.getField("int_02").getVisible())
			textField_Int_02.setValue(String.valueOf(entity.getInt_02()));
		if (controller.getField("int_03").getVisible())
			textField_Int_03.setValue(String.valueOf(entity.getInt_03()));
		if (controller.getField("int_04").getVisible())
			textField_Int_04.setValue(String.valueOf(entity.getInt_04()));
		if (controller.getField("int_05").getVisible())
			textField_Int_05.setValue(String.valueOf(entity.getInt_05()));
		if (controller.getField("int_06").getVisible())
			textField_Int_06.setValue(String.valueOf(entity.getInt_06()));
		if (controller.getField("int_07").getVisible())
			textField_Int_07.setValue(String.valueOf(entity.getInt_07()));
		if (controller.getField("int_08").getVisible())
			textField_Int_08.setValue(String.valueOf(entity.getInt_08()));
		if (controller.getField("int_09").getVisible())
			textField_Int_09.setValue(String.valueOf(entity.getInt_09()));
		if (controller.getField("int_10").getVisible())
			textField_Int_10.setValue(String.valueOf(entity.getInt_10()));

		if (controller.getField("mon_01").getVisible())
			textField_Mon_01.setValue(String.valueOf(entity.getMon_01()));
		if (controller.getField("mon_02").getVisible())
			textField_Mon_02.setValue(String.valueOf(entity.getMon_02()));
		if (controller.getField("mon_03").getVisible())
			textField_Mon_03.setValue(String.valueOf(entity.getMon_03()));
		if (controller.getField("mon_04").getVisible())
			textField_Mon_04.setValue(String.valueOf(entity.getMon_04()));
		if (controller.getField("mon_05").getVisible())
			textField_Mon_05.setValue(String.valueOf(entity.getMon_05()));
		if (controller.getField("mon_06").getVisible())
			textField_Mon_06.setValue(String.valueOf(entity.getMon_06()));
		if (controller.getField("mon_07").getVisible())
			textField_Mon_07.setValue(String.valueOf(entity.getMon_07()));
		if (controller.getField("mon_08").getVisible())
			textField_Mon_08.setValue(String.valueOf(entity.getMon_08()));
		if (controller.getField("mon_09").getVisible())
			textField_Mon_09.setValue(String.valueOf(entity.getMon_09()));
		if (controller.getField("mon_10").getVisible())
			textField_Mon_10.setValue(String.valueOf(entity.getMon_10()));

		if (controller.getField("date_01").getVisible()) {
			if (entity.getId() == null)
				dateField_Date_01.setValue(new Date());
			else
				dateField_Date_01.setValue(entity.getDate_01());
		}

		if (controller.getField("date_02").getVisible()) {
			if (entity.getId() == null)
				dateField_Date_02.setValue(new Date());
			else
				dateField_Date_02.setValue(entity.getDate_02());
		}
		if (controller.getField("date_03").getVisible()) {
			if (entity.getId() == null)
				dateField_Date_03.setValue(new Date());
			else
				dateField_Date_03.setValue(entity.getDate_03());
		}
		if (controller.getField("date_04").getVisible()) {
			if (controller.getField("date_04").getLabelText().equalsIgnoreCase("işlem tarihi") && controller.getField("date_04") != null && entity.getId() == null)
				dateField_Date_04.setValue(new Date());
			else
				dateField_Date_04.setValue(entity.getDate_04());
		}
		if (controller.getField("date_05").getVisible()) {
			if (controller.getField("date_05").getLabelText().equalsIgnoreCase("işlem tarihi") && controller.getField("date_05") != null && entity.getId() == null)
				dateField_Date_05.setValue(new Date());
			else
				dateField_Date_05.setValue(entity.getDate_05());
		}

		if (controller.getField("ck_01").getVisible()) {
			comboBox_Ck_01.select(entity.getCk_01());
		}
		if (controller.getField("ck_02").getVisible()) {
			comboBox_Ck_02.select(entity.getCk_02());
		}
		if (controller.getField("ck_03").getVisible()) {
			comboBox_Ck_03.select(entity.getCk_03());
		}
		if (controller.getField("ck_04").getVisible()) {
			comboBox_Ck_04.select(entity.getCk_04());
		}
		if (controller.getField("ck_05").getVisible()) {
			comboBox_Ck_05.select(entity.getCk_05());
		}
		if (controller.getField("ck_06").getVisible()) {
			comboBox_Ck_06.select(entity.getCk_06());
		}
		if (controller.getField("ck_07").getVisible()) {
			comboBox_Ck_07.select(entity.getCk_07());
		}
		if (controller.getField("ck_08").getVisible()) {
			comboBox_Ck_08.select(entity.getCk_08());
		}
		if (controller.getField("ck_09").getVisible()) {
			comboBox_Ck_09.select(entity.getCk_09());
		}
		if (controller.getField("ck_10").getVisible()) {
			comboBox_Ck_10.select(entity.getCk_10());
		}
		if (controller.getField("ck_11").getVisible()) {
			comboBox_Ck_11.select(entity.getCk_11());
		}
		if (controller.getField("ck_12").getVisible()) {
			comboBox_Ck_12.select(entity.getCk_12());
		}
		if (controller.getField("ck_13").getVisible()) {
			comboBox_Ck_13.select(entity.getCk_13());
		}
		if (controller.getField("ck_14").getVisible()) {
			comboBox_Ck_14.select(entity.getCk_14());
		}
		if (controller.getField("ck_15").getVisible()) {
			comboBox_Ck_15.select(entity.getCk_15());
		}

		if (controller.getField("value_01").getVisible())
			setStringValue(controller.getField("value_01"), textField_Value_01, entity.getValue_01());
		if (controller.getField("value_02").getVisible())
			setStringValue(controller.getField("value_02"), textField_Value_02, entity.getValue_02());
		if (controller.getField("value_03").getVisible())
			setStringValue(controller.getField("value_03"), textField_Value_03, entity.getValue_03());
		if (controller.getField("value_04").getVisible())
			setStringValue(controller.getField("value_04"), textField_Value_04, entity.getValue_04());
		if (controller.getField("value_05").getVisible())
			setStringValue(controller.getField("value_05"), textField_Value_05, entity.getValue_05());
		if (controller.getField("value_06").getVisible())
			setStringValue(controller.getField("value_06"), textField_Value_06, entity.getValue_06());
		if (controller.getField("value_07").getVisible())
			setStringValue(controller.getField("value_07"), textField_Value_07, entity.getValue_07());
		if (controller.getField("value_08").getVisible())
			setStringValue(controller.getField("value_08"), textField_Value_08, entity.getValue_08());
		if (controller.getField("value_09").getVisible())
			setStringValue(controller.getField("value_09"), textField_Value_09, entity.getValue_09());
		if (controller.getField("value_10").getVisible())
			setStringValue(controller.getField("value_10"), textField_Value_10, entity.getValue_10());

		if (controller.getField("byte_01").getVisible()) {

			byte[] bs = entity.getByte_01();
			String status = entity.getStatus();
			if (bs != null && bs.length > 0) {
				downloadButton.setCaption(entity.getByte_01_name());
				UnicaseFileDownloader downloader = new UnicaseFileDownloader(entity.getByte_01_name(), bs);
				downloader.extend(downloadButton);
				textField_byte_01.setFileContent(bs);
				textField_byte_01.setFileName(entity.getByte_01_name());
				textField_byte_01.setEnabled(false);
			} else {
				if (downloadButton != null)
					downloadButton.setCaption("");
				textField_byte_01.setEnabled(true);
			}

			if (status != null && status.equals("0"))
				downloadButton.setCaption("");
		}

	}

	private void refreshTable() {
		ReferenceEntity selectedValue = (ReferenceEntity) table.getValue();
		if (table.size() > 0)
			table.removeAllItems();
		table.setContainerDataSource(controller.getDataSource());
		table.setVisibleColumns(controller.getNaturalColumnOrder());
		table.setColumnHeaders(controller.getColumnHeaderValues());
		table.select(selectedValue);
		if (table.size() > 0)
			table.setCaption("Tabloda " + table.size() + " adet kayıt bulunmaltadır.");
		else
			table.setCaption("Tabloda kayıt bulunmamaktadır.");
	}

	@Override
	public void setViewToEntity() {

		ReferenceEntity entity = controller.getEntity();
		String textFieldComponentValue = null;

		if (controller.getField("code").getVisible()) {
			textFieldComponentValue = (String) textField_Code.getValue();
			entity.setCode((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("value").getVisible()) {
			textFieldComponentValue = (String) textField_Value.getValue();
			entity.setValue((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("shortvalue").getVisible()) {
			textFieldComponentValue = (String) textField_Shortvalue.getValue();
			entity.setShortvalue((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("description").getVisible()) {
			textFieldComponentValue = (String) textField_Description.getValue();
			entity.setDescription((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("orderno").getVisible()) {
			textFieldComponentValue = (String) textField_Orderno.getValue();
			entity.setOrderno((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("desc_01").getVisible()) {
			textFieldComponentValue = (String) textArea_Desc_01.getValue();
			entity.setDesc_01((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("desc_02").getVisible()) {
			textFieldComponentValue = (String) textArea_Desc_02.getValue();
			entity.setDesc_02((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("desc_03").getVisible()) {
			textFieldComponentValue = (String) textArea_Desc_03.getValue();
			entity.setDesc_03((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("desc_04").getVisible()) {
			textFieldComponentValue = (String) textArea_Desc_04.getValue();
			entity.setDesc_04((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("desc_05").getVisible()) {
			textFieldComponentValue = (String) textArea_Desc_05.getValue();
			entity.setDesc_05((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("desc_06").getVisible()) {
			textFieldComponentValue = (String) textArea_Desc_06.getValue();
			entity.setDesc_06((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("desc_07").getVisible()) {
			textFieldComponentValue = (String) textArea_Desc_07.getValue();
			entity.setDesc_07((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("desc_08").getVisible()) {
			textFieldComponentValue = (String) textArea_Desc_08.getValue();
			entity.setDesc_08((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("desc_09").getVisible()) {
			textFieldComponentValue = (String) textArea_Desc_09.getValue();
			entity.setDesc_09((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("desc_10").getVisible()) {
			textFieldComponentValue = (String) textArea_Desc_10.getValue();
			entity.setDesc_10((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}

		if (controller.getField("int_01").getVisible()) {
			if (textField_Int_01 != null && !textField_Int_01.getValue().trim().isEmpty())
				entity.setInt_01((Long.parseLong(textField_Int_01.getValue().trim())));
		}
		if (controller.getField("int_02").getVisible()) {
			if (textField_Int_02.getValue() != null && !textField_Int_02.getValue().trim().isEmpty())
				entity.setInt_02((Long.parseLong(textField_Int_02.getValue().trim())));
		}

		if (controller.getField("int_03").getVisible()) {
			if (textField_Int_03.getValue() != null && !textField_Int_03.getValue().trim().isEmpty())
				entity.setInt_03((Long.parseLong(textField_Int_03.getValue().trim())));
		}
		if (controller.getField("int_04").getVisible()) {
			if (textField_Int_04.getValue() != null && !textField_Int_04.getValue().trim().isEmpty())
				entity.setInt_04((Integer.parseInt(textField_Int_04.getValue().trim())));
		}
		if (controller.getField("int_05").getVisible()) {
			if (textField_Int_05.getValue() != null && !textField_Int_05.getValue().trim().isEmpty())
				entity.setInt_05((Integer.parseInt(textField_Int_05.getValue().trim())));
		}
		if (controller.getField("int_06").getVisible()) {
			if (textField_Int_06.getValue() != null && !textField_Int_06.getValue().trim().isEmpty())
				entity.setInt_06((Integer.parseInt(textField_Int_06.getValue().trim())));
		}
		if (controller.getField("int_07").getVisible()) {
			if (textField_Int_07.getValue() != null && !textField_Int_07.getValue().trim().isEmpty())
				entity.setInt_07((Integer.parseInt(textField_Int_07.getValue().trim())));
		}
		if (controller.getField("int_08").getVisible()) {
			if (textField_Int_08.getValue() != null && !textField_Int_08.getValue().trim().isEmpty())
				entity.setInt_08((Integer.parseInt(textField_Int_08.getValue().trim())));
		}
		if (controller.getField("int_09").getVisible()) {
			if (textField_Int_09.getValue() != null && !textField_Int_09.getValue().trim().isEmpty())
				entity.setInt_09((Integer.parseInt(textField_Int_09.getValue().trim())));
		}
		if (controller.getField("int_10").getVisible()) {
			if (textField_Int_10.getValue() != null && !textField_Int_10.getValue().trim().isEmpty())
				entity.setInt_10((Integer.parseInt(textField_Int_10.getValue().trim())));
		}

		if (controller.getField("mon_01").getVisible()) {
			if (textField_Mon_01.getValue() != null && !textField_Mon_01.getValue().trim().isEmpty())
				entity.setMon_01((Double.parseDouble(textField_Mon_01.getValue().trim())));
		}
		if (controller.getField("mon_02").getVisible()) {
			if (textField_Mon_02.getValue() != null && !textField_Mon_02.getValue().trim().isEmpty())
				entity.setMon_02((Double.parseDouble(textField_Mon_02.getValue().trim())));
		}
		if (controller.getField("mon_03").getVisible()) {
			if (textField_Mon_03.getValue() != null && !textField_Mon_03.getValue().trim().isEmpty())
				entity.setMon_03((Double.parseDouble(textField_Mon_03.getValue().trim())));
		}
		if (controller.getField("mon_04").getVisible()) {
			if (textField_Mon_04.getValue() != null && !textField_Mon_04.getValue().trim().isEmpty())
				entity.setMon_04((Double.parseDouble(textField_Mon_04.getValue().trim())));
		}
		if (controller.getField("mon_05").getVisible()) {
			if (textField_Mon_05.getValue() != null && !textField_Mon_05.getValue().trim().isEmpty())
				entity.setMon_05((Double.parseDouble(textField_Mon_05.getValue().trim())));
		}
		if (controller.getField("mon_06").getVisible()) {
			if (textField_Mon_06.getValue() != null && !textField_Mon_06.getValue().trim().isEmpty())
				entity.setMon_06((Double.parseDouble(textField_Mon_06.getValue().trim())));
		}
		if (controller.getField("mon_07").getVisible()) {
			if (textField_Mon_07.getValue() != null && !textField_Mon_07.getValue().trim().isEmpty())
				entity.setMon_07((Double.parseDouble(textField_Mon_07.getValue().trim())));
		}
		if (controller.getField("mon_08").getVisible()) {
			if (textField_Mon_08.getValue() != null && !textField_Mon_08.getValue().trim().isEmpty())
				entity.setMon_08((Double.parseDouble(textField_Mon_08.getValue().trim())));
		}
		if (controller.getField("mon_09").getVisible()) {
			if (textField_Mon_09.getValue() != null && !textField_Mon_09.getValue().trim().isEmpty())
				entity.setMon_09((Double.parseDouble(textField_Mon_09.getValue().trim())));
		}
		if (controller.getField("mon_10").getVisible()) {
			if (textField_Mon_10.getValue() != null && !textField_Mon_10.getValue().trim().isEmpty())
				entity.setMon_10((Double.parseDouble(textField_Mon_10.getValue().trim())));
		}

		if (controller.getField("date_01").getVisible())
			entity.setDate_01((Date) dateField_Date_01.getValue());
		if (controller.getField("date_02").getVisible())
			entity.setDate_02((Date) dateField_Date_02.getValue());
		if (controller.getField("date_03").getVisible())
			entity.setDate_03((Date) dateField_Date_03.getValue());
		if (controller.getField("date_04").getVisible())
			entity.setDate_04((Date) dateField_Date_04.getValue());
		if (controller.getField("date_05").getVisible())
			entity.setDate_05((Date) dateField_Date_05.getValue());

		if (controller.getField("ck_01").getVisible())
			entity.setCk_01((ReferenceEntity) comboBox_Ck_01.getSelectedValue());
		if (controller.getField("ck_02").getVisible())
			entity.setCk_02((ReferenceEntity) comboBox_Ck_02.getSelectedValue());
		if (controller.getField("ck_03").getVisible())
			entity.setCk_03((ReferenceEntity) comboBox_Ck_03.getSelectedValue());
		if (controller.getField("ck_04").getVisible())
			entity.setCk_04((ReferenceEntity) comboBox_Ck_04.getSelectedValue());
		if (controller.getField("ck_05").getVisible())
			entity.setCk_05((ReferenceEntity) comboBox_Ck_05.getSelectedValue());
		if (controller.getField("ck_06").getVisible())
			entity.setCk_06((ReferenceEntity) comboBox_Ck_06.getSelectedValue());
		if (controller.getField("ck_07").getVisible())
			entity.setCk_07((ReferenceEntity) comboBox_Ck_07.getSelectedValue());
		if (controller.getField("ck_08").getVisible())
			entity.setCk_08((ReferenceEntity) comboBox_Ck_08.getSelectedValue());
		if (controller.getField("ck_09").getVisible())
			entity.setCk_09((ReferenceEntity) comboBox_Ck_09.getSelectedValue());
		if (controller.getField("ck_10").getVisible())
			entity.setCk_10((ReferenceEntity) comboBox_Ck_10.getSelectedValue());
		if (controller.getField("ck_11").getVisible())
			entity.setCk_11((ReferenceEntity) comboBox_Ck_11.getSelectedValue());
		if (controller.getField("ck_12").getVisible())
			entity.setCk_12((ReferenceEntity) comboBox_Ck_12.getSelectedValue());
		if (controller.getField("ck_13").getVisible())
			entity.setCk_13((ReferenceEntity) comboBox_Ck_13.getSelectedValue());
		if (controller.getField("ck_14").getVisible())
			entity.setCk_14((ReferenceEntity) comboBox_Ck_14.getSelectedValue());
		if (controller.getField("ck_15").getVisible())
			entity.setCk_15((ReferenceEntity) comboBox_Ck_15.getSelectedValue());

		if (controller.getField("value_01").getVisible()) {
			textFieldComponentValue = (String) textField_Value_01.getValue();
			entity.setValue_01((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("value_02").getVisible()) {
			textFieldComponentValue = (String) textField_Value_02.getValue();
			entity.setValue_02((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("value_03").getVisible()) {
			textFieldComponentValue = (String) textField_Value_03.getValue();
			entity.setValue_03((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("value_04").getVisible()) {
			textFieldComponentValue = (String) textField_Value_04.getValue();
			entity.setValue_04((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("value_05").getVisible()) {
			textFieldComponentValue = (String) textField_Value_05.getValue();
			entity.setValue_05((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("value_06").getVisible()) {
			textFieldComponentValue = (String) textField_Value_06.getValue();
			entity.setValue_06((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("value_07").getVisible()) {
			textFieldComponentValue = (String) textField_Value_07.getValue();
			entity.setValue_07((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("value_08").getVisible()) {
			textFieldComponentValue = (String) textField_Value_08.getValue();
			entity.setValue_08((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("value_09").getVisible()) {
			textFieldComponentValue = (String) textField_Value_09.getValue();
			entity.setValue_09((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("value_10").getVisible()) {
			textFieldComponentValue = (String) textField_Value_10.getValue();
			entity.setValue_10((textFieldComponentValue != null ? textFieldComponentValue.trim() : Configuration.View.EMPTY_STRING));
		}
		if (controller.getField("byte_01").getVisible()) {
			entity.setByte_01(textField_byte_01.getFileContent());
			entity.setByte_01_name(textField_byte_01.getFileName().trim());
		}

	}

	@Override
	@SuppressWarnings("unchecked")
	public void valueChange(ValueChangeEvent event) {
		Object itemId = table.getValue();
		BeanItem<ReferenceEntity> entityItem = (BeanItem<ReferenceEntity>) table.getItem(itemId);
		if (entityItem != null)
			controller.setEntity(entityItem.getBean());
		update();
	}

	@Override
	public void refresh() {
		refreshTable();
		initLayout();
		if (textField_byte_01 != null)
			textField_byte_01.setCaption(Configuration.View.EMPTY_STRING);
	}

	@Override
	public List<Action> getActions() {
		return actionList;
	}

	@Override
	public List<Action> getChildsAction() {
		return this.childActions;
	}

}

package tr.org.unicase.reference.web.view;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Session;

import tr.org.unicase.authentication.service.model.User;
import tr.org.unicase.kernel.model.AbstractEntity;
import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.reference.model.ReferenceEntity;
import tr.org.unicase.reference.web.controller.ReferenceControllerImp;
import tr.org.unicase.report.UnicaseReport;
import tr.org.unicase.web.app.IconHelper;
import tr.org.unicase.web.component.UnicaseComboBox;
import tr.org.unicase.web.component.UnicaseTable;
import tr.org.unicase.web.component.excel.UnicaseExcelExportButton;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.action.api.UnicaseActionManager;
import tr.org.unicase.web.view.api.AbstractView;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.StreamResource;
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
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

public class ReferenceListViewImp extends AbstractView<ReferenceControllerImp> implements Property.ValueChangeListener {

	private HorizontalLayout mainGridLayout = new HorizontalLayout();

	private TextField textField_Code;
	private TextField textField_Value;
	private TextField textField_Shortvalue;
	private TextField textField_Description;
	private TextField textField_Orderno;

	private TextField textArea_Desc_01;
	private TextField textArea_Desc_02;
	private TextField textArea_Desc_03;
	private TextField textArea_Desc_04;
	private TextField textArea_Desc_05;
	private TextField textArea_Desc_06;
	private TextField textArea_Desc_07;
	private TextField textArea_Desc_08;
	private TextField textArea_Desc_09;
	private TextField textArea_Desc_10;

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

	private Map<Field, Component> layoutOrganization = new TreeMap<Field, Component>();

	Connection connection = null;
	Session session = null;
	ReferenceEntity selectedValue = null;
	Long parameterId;
	int windowCount = 0;
	private List<ReferenceEntity> reportList = null;
	private List<com.vaadin.event.Action> reportActions = null;
	private com.vaadin.event.Action[] reportActionList = null;
	protected List<Action> adminActionList = new LinkedList<Action>();

	private List<Field> fieldsList;
	private List<Field> typeFields;

	public ReferenceListViewImp(Long entityTypeId, Long ownerId, String refOwner) {
		this.entityTypeId = entityTypeId;
		this.ownerId = ownerId;
		this.refOwner = refOwner;
		layoutOrganization.clear();

		setController(new ReferenceControllerImp(entityTypeId, ownerId, refOwner));

		reportList = controller.findReports(entityTypeId);
		convertReportListToReportActionList();

		mainGridLayout.setSpacing(true);
		mainGridLayout.setMargin(true);

		buildLayout();

		typeFields = getController().getTypeFields();

		displayActiveButtonActions();

		fieldsList = getController().getSearchableFieldsList();

		createTable();
	}

	private void displayActiveButtonActions() {
		User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());

		if (user.getRole() != null) {
			String userRole = user.getRole().trim();
			List<ReferenceEntity> activeActionList = controller.findActiveActionList(userRole);
			if (activeActionList != null && activeActionList.size() > 0) {
				String clazz = null;
				Action tempAction = null;
				for (final ReferenceEntity action : activeActionList) {
					if (action.getValue_04() != null && action.getValue_04().equalsIgnoreCase("tr.org.unicase.reference.web.view.ReferenceListViewImp")) {
						if (action.getValue_02() != null) {
							clazz = action.getValue_02();
							tempAction = UnicaseActionManager.getInstance().createInstance(clazz, "1", null, action.getValue_01(), action.getValue_03(), "tr.org.unicase.reference.web.view.ReferenceViewImp", controller, this, null);
							if (tempAction != null)
								actionList.add(tempAction);
						}
					}
					if (action.getInt_01() != null && typeFields.size() > 0) {
						clazz = "tr.org.unicase.kernel.web.view.action.internals.DetailsActionImpl";
						for (final Field field : typeFields) {
							if (field.getEntityTypeId() != null && field.getRefColumn() != null) {
								if (action.getInt_01() == field.getEntityTypeId().intValue()) {
									tempAction = UnicaseActionManager.getInstance().createInstance(clazz, "1", null, field.getDetailIcon(), field.getLabelText(), "tr.org.unicase.reference.web.view.ReferenceDetailViewImp", controller, this, field);
									if (tempAction != null)
										actionList.add(tempAction);
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
			userRole = null;
		}

		user = null;

	}

	private void convertReportListToReportActionList() {
		com.vaadin.event.Action tempAction = null;
		if (reportList != null && reportList.size() > 0) {
			reportActions = new ArrayList<com.vaadin.event.Action>();
			for (int i = 0; i < reportList.size(); i++) {
				if (reportList.get(i).getCk_owner() == null) {
					tempAction = new com.vaadin.event.Action(reportList.get(i).getValue_01());
					reportActions.add(tempAction);
				}
			}
			if (reportActions != null && reportActions.size() > 0) {
				reportActionList = new com.vaadin.event.Action[reportActions.size()];
				reportActionList = reportActions.toArray(reportActionList);
			}
			tempAction = null;
		}
	}

	private ReferenceEntity getReferenceEntityOfAction(com.vaadin.event.Action action) {
		String actionName = action.getCaption();

		for (ReferenceEntity referenceEntity : reportList) {
			if (referenceEntity.getValue_01().equals(actionName))
				return referenceEntity;

		}

		return null;
	}

	private void createTable() {

		table = new UnicaseTable();
		// table.setPageLength(7);
		table.setImmediate(true);
		table.setSelectable(true);
		table.setWidth("100%");
		table.setHeight(100.0f, Unit.PERCENTAGE);
		table.setColumnReorderingAllowed(true);
		table.setColumnCollapsingAllowed(true);

		table.setContainerDataSource(controller.getDataSource());
		table.setVisibleColumns(controller.getNaturalColumnOrder());
		table.setColumnHeaders(controller.getColumnHeaderValues());

		if (table.size() > 0)
			table.setCaption("Tabloda " + table.size() + " adet kayıt bulunmaltadır.");
		else
			table.setCaption("Tabloda kayıt bulunmamaktadır.");

		// table.addListener(this);
		table.addValueChangeListener(this);

		if (reportActionList != null && reportActionList.length > 0) {
			table.addActionHandler(new Handler() {
				public com.vaadin.event.Action[] getActions(Object target, Object sender) {
					return reportActionList;
				};

				@Override
				public void handleAction(com.vaadin.event.Action action, Object sender, Object target) {
					selectedValue = (ReferenceEntity) table.getValue();

					if (selectedValue != null) {
						parameterId = selectedValue.getId();

						ReferenceEntity referenceEntity = getReferenceEntityOfAction(action);
						if (referenceEntity != null) {
							if (referenceEntity.getByte_01() != null) {
								createShowReport(referenceEntity.getByte_01(), parameterId);
							} else {
								if (reportList != null && reportList.size() > 0) {
									for (ReferenceEntity subReportReferenceEntity : reportList) {
										if (subReportReferenceEntity.getCk_owner() != null) {
											if (subReportReferenceEntity.getCk_owner().getId().equals(referenceEntity.getId())) {
												if (subReportReferenceEntity.getByte_01() != null) {
													windowCount++;
													createShowReport(subReportReferenceEntity.getByte_01(), parameterId);
												}
											}
										}
									}
								}
							}

							windowCount = 0;
						} else {
							Notification.show("", Type.ERROR_MESSAGE);
						}
					}
				}
			});
		}
	}

	@SuppressWarnings("deprecation")
	private void createShowReport(final byte[] resourceStream, Long parameterId) {
		final Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("parameter_id", parameterId.intValue());
		StreamResource.StreamSource source = new StreamResource.StreamSource() {
			public InputStream getStream() {
				byte[] b = null;
				if (resourceStream != null) {
					InputStream resourceAsStream = new ByteArrayInputStream(resourceStream);
					b = UnicaseReport.getInstance().generateReport(resourceAsStream, parameterMap);
				}

				return new ByteArrayInputStream(b);
			}
		};

		String resourceName = "report3_" + System.currentTimeMillis() + ".pdf";
		StreamResource resource = new StreamResource(source, resourceName);
		resource.setMIMEType("application/pdf");

		VerticalLayout v = new VerticalLayout();
		v.setSizeFull();
		Embedded embd = new Embedded("", resource);

		embd.setSizeFull();
		embd.setType(Embedded.TYPE_BROWSER);
		v.addComponent(embd);

		Window w = new Window();
		w.setContent(v);
		// w.setSizeFull();
		if (windowCount == 0) {
			w.center();
			w.setHeight("800px");
			w.setWidth("600px");
		} else if (windowCount == 1) {
			w.setHeight("700px");
			w.setWidth("525px");
			w.setPositionX(300);
			w.setPositionY(90);
		} else if (windowCount == 2) {
			w.setHeight("700px");
			w.setWidth("520px");
			w.setPositionX(900);
			w.setPositionY(90);
		}
		UI.getCurrent().addWindow(w);
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
									ReferenceEntity parentEntity = (ReferenceEntity) parentComboBox.getValue();
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

	private VerticalLayout getTableLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.setId("tableLayout01");

		Button buttonSearch = new Button();
		// buttonSearch.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		buttonSearch.setStyleName(BaseTheme.BUTTON_LINK);
		buttonSearch.setDescription("Sorgula");
		buttonSearch.setCaption(buttonSearch.getDescription());
		buttonSearch.setIcon(IconHelper.getIcon32("zoom-search-2-icon.png"));
		buttonSearch.setClickShortcut(KeyCode.ENTER);
		buttonSearch.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				search();
			}
		});

		Button buttonClear = new Button();
		buttonClear.setStyleName(BaseTheme.BUTTON_LINK);
		buttonClear.setDescription("Temizle");
		buttonClear.setCaption(buttonClear.getDescription());
		buttonClear.setIcon(IconHelper.getIcon32("recyclebin-icon.png"));
		buttonClear.setClickShortcut(KeyCode.DELETE);
		buttonClear.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				clearForm();
			}
		});

		final UnicaseExcelExportButton excelExportButton = new UnicaseExcelExportButton();
		excelExportButton.setImmediate(true);
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

	@Override
	public Component getView() {
		mainVerticalLayout.addComponent(mainGridLayout);
		mainVerticalLayout.addComponent(getTableLayout());
		// mainVerticalLayout.addComponent(table);
		return mainVerticalLayout;
	}

	private void buildLayout() {

		textField_Code = (TextField) createComponent(textField_Code, "code", TextField.class);

		textField_Value = (TextField) createComponent(textField_Value, "value", TextField.class);

		textField_Shortvalue = (TextField) createComponent(textField_Shortvalue, "shortvalue", TextField.class);

		textField_Description = (TextField) createComponent(textField_Description, "description", TextField.class);

		textField_Orderno = (TextField) createComponent(textField_Orderno, "orderno", TextField.class);

		textArea_Desc_01 = (TextField) createComponent(textArea_Desc_01, "desc_01", TextField.class);
		textArea_Desc_02 = (TextField) createComponent(textArea_Desc_02, "desc_02", TextField.class);
		textArea_Desc_03 = (TextField) createComponent(textArea_Desc_03, "desc_03", TextField.class);
		textArea_Desc_04 = (TextField) createComponent(textArea_Desc_04, "desc_04", TextField.class);
		textArea_Desc_05 = (TextField) createComponent(textArea_Desc_05, "desc_05", TextField.class);
		textArea_Desc_06 = (TextField) createComponent(textArea_Desc_06, "desc_06", TextField.class);
		textArea_Desc_07 = (TextField) createComponent(textArea_Desc_07, "desc_07", TextField.class);
		textArea_Desc_08 = (TextField) createComponent(textArea_Desc_08, "desc_08", TextField.class);
		textArea_Desc_09 = (TextField) createComponent(textArea_Desc_09, "desc_09", TextField.class);
		textArea_Desc_10 = (TextField) createComponent(textArea_Desc_10, "desc_10", TextField.class);

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

		addComponentInOrder();
	}

	private void clearComponent(String field, Component component) {
		if (isSearchField(field)) {
			if (component != null) {
				Class<?> clazz = component.getClass();
				if (AbstractTextField.class.isAssignableFrom(clazz))
					((AbstractTextField) component).setValue("");
				else if (DateField.class.isAssignableFrom(clazz))
					((DateField) component).setValue(null);
				else if (ComboBox.class.isAssignableFrom(clazz)) {
					((UnicaseComboBox) component).setValue(null);
				}
			}
		}
	}

	protected void clearForm() {

		clearComponent("code", textField_Code);
		clearComponent("value", textField_Value);
		clearComponent("shortvalue", textField_Shortvalue);
		clearComponent("description", textField_Description);
		clearComponent("orderno", textField_Orderno);

		clearComponent("desc_01", textArea_Desc_01);
		clearComponent("desc_02", textArea_Desc_02);
		clearComponent("desc_03", textArea_Desc_03);
		clearComponent("desc_04", textArea_Desc_04);
		clearComponent("desc_05", textArea_Desc_05);
		clearComponent("desc_06", textArea_Desc_06);
		clearComponent("desc_07", textArea_Desc_07);
		clearComponent("desc_08", textArea_Desc_08);
		clearComponent("desc_09", textArea_Desc_09);
		clearComponent("desc_10", textArea_Desc_10);

		clearComponent("int_01", textField_Int_01);
		clearComponent("int_02", textField_Int_02);
		clearComponent("int_03", textField_Int_03);
		clearComponent("int_04", textField_Int_04);
		clearComponent("int_05", textField_Int_05);
		clearComponent("int_06", textField_Int_06);
		clearComponent("int_07", textField_Int_07);
		clearComponent("int_08", textField_Int_08);
		clearComponent("int_09", textField_Int_09);
		clearComponent("int_10", textField_Int_10);

		clearComponent("mon_01", textField_Mon_01);
		clearComponent("mon_02", textField_Mon_02);
		clearComponent("mon_03", textField_Mon_03);
		clearComponent("mon_04", textField_Mon_04);
		clearComponent("mon_05", textField_Mon_05);
		clearComponent("mon_06", textField_Mon_06);
		clearComponent("mon_07", textField_Mon_07);
		clearComponent("mon_08", textField_Mon_08);
		clearComponent("mon_09", textField_Mon_09);
		clearComponent("mon_10", textField_Mon_10);

		clearComponent("date_01", dateField_Date_01);
		clearComponent("date_02", dateField_Date_02);
		clearComponent("date_03", dateField_Date_03);
		clearComponent("date_04", dateField_Date_04);
		clearComponent("date_05", dateField_Date_05);

		clearComponent("ck_01", comboBox_Ck_01);
		clearComponent("ck_02", comboBox_Ck_02);
		clearComponent("ck_03", comboBox_Ck_03);
		clearComponent("ck_04", comboBox_Ck_04);
		clearComponent("ck_05", comboBox_Ck_05);
		clearComponent("ck_06", comboBox_Ck_06);
		clearComponent("ck_07", comboBox_Ck_07);
		clearComponent("ck_08", comboBox_Ck_08);
		clearComponent("ck_09", comboBox_Ck_09);
		clearComponent("ck_10", comboBox_Ck_10);
		clearComponent("ck_11", comboBox_Ck_11);
		clearComponent("ck_12", comboBox_Ck_12);
		clearComponent("ck_13", comboBox_Ck_13);
		clearComponent("ck_14", comboBox_Ck_14);
		clearComponent("ck_15", comboBox_Ck_15);

		clearComponent("value_01", textField_Value_01);
		clearComponent("value_02", textField_Value_02);
		clearComponent("value_03", textField_Value_03);
		clearComponent("value_04", textField_Value_04);
		clearComponent("value_05", textField_Value_05);
		clearComponent("value_06", textField_Value_06);
		clearComponent("value_07", textField_Value_07);
		clearComponent("value_08", textField_Value_08);
		clearComponent("value_09", textField_Value_09);
		clearComponent("value_10", textField_Value_10);

	}

	private boolean isSearchField(String fieldName) {
		return (AbstractEntity.TRUE.equals(controller.getField(fieldName).getSearchable()));
	}

	private boolean isComponentEmpty(Component fieldComponent) {
		boolean result = true;

		if (fieldComponent != null) {
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
		}

		return result;
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

	private void refreshTable() {
		ReferenceEntity selectedValue = (ReferenceEntity) table.getValue();

		if (table.size() > 0) {
			table.removeAllItems();
		}
		table.setContainerDataSource(controller.getDataSource());
		table.setVisibleColumns(controller.getNaturalColumnOrder());
		table.setColumnHeaders(controller.getColumnHeaderValues());
		table.select(selectedValue);
		if (table.size() > 0) {
			table.setCaption("Tabloda " + table.size() + " adet kayıt bulunmaltadır.");
		} else if (table.size() == 0) {
			Notification.show("Sorgulama kriterlerine uygun sonuc bulunamadi.", Type.HUMANIZED_MESSAGE);
			table.setCaption("Tabloda kayıt bulunmamaktadır.");
		}

	}

	private Object getComponentValue(Component fieldComponent) {
		Class<?> clazz = fieldComponent.getClass();
		Object value = null;
		if (AbstractTextField.class.isAssignableFrom(clazz)) {
			value = ((AbstractTextField) fieldComponent).getValue();
		} else if (DateField.class.isAssignableFrom(clazz)) {

			Date searchDate = ((DateField) fieldComponent).getValue();

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String DateToStr = format.format(searchDate);
			value = DateToStr;
		} else if (ComboBox.class.isAssignableFrom(clazz)) {
			value = ((UnicaseComboBox) fieldComponent).getValue();
		}

		return value;
	}

	private void addComponentValueAsParameter(String fieldName, Component fieldComponent) {
		if (fieldComponent != null) {
			if (isSearchField(fieldName)) {
				if (!isComponentEmpty(fieldComponent))
					getController().addParameter(fieldName, getComponentValue(fieldComponent));
			}
		}
	}

	private void search() {
		getController().clearParameters();

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

	private void addComponentInOrder() {
		Component component = null;

		for (Field field : layoutOrganization.keySet()) {
			component = layoutOrganization.get(field);
			mainGridLayout.addComponent(component);
		}

		layoutOrganization.clear();
	}

	private Component createComponent(Object object, String name, Class<?> clazz) {
		Component component = null;
		Field field = controller.getField(name);

		if (field == null || !field.getVisible() || !(AbstractEntity.TRUE.equals(field.getSearchable())))
			return component;

		try {
			object = clazz.newInstance();
			component = (Component) object;
			component.setWidth(field.getWidth());
			component.setHeight(field.getHeight());

			if (AbstractTextField.class.isAssignableFrom(clazz)) {
				((AbstractTextField) component).setCaption(field.getLabelText());
				((AbstractTextField) component).setImmediate(true);
			}
			if (DateField.class.isAssignableFrom(clazz)) {
				((PopupDateField) component).setDateFormat("dd.MM.yyyy HH:mm:ss");
				((PopupDateField) component).setResolution(Resolution.MINUTE);
				((DateField) component).setCaption(field.getLabelText());
				((DateField) component).setImmediate(true);
				if (entityTypeId != null && entityTypeId == 5 && field.getName().equals("date_04"))
					((DateField) component).setValue(new Date());
			}
			if (ComboBox.class.isAssignableFrom(clazz)) {
				((UnicaseComboBox) component).setFilteringMode(FilteringMode.CONTAINS);
				((UnicaseComboBox) component).setCaption(field.getLabelText());
				((UnicaseComboBox) component).setImmediate(true);
			}
			layoutOrganization.put(field, component);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return component;
	}

	@Override
	public void update() {

	}

	@Override
	public void setViewToEntity() {

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		ReferenceEntity entity = (ReferenceEntity) (table.getValue());
		controller.setEntity(entity);
		update();
	}

	@Override
	public List<Action> getActions() {
		return actionList;
	}

	@Override
	public void refresh() {
		controller.findAll(entityTypeId, ownerId, refOwner);
		refreshTable();
	}

}

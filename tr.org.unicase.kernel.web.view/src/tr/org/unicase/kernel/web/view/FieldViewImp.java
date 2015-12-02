package tr.org.unicase.kernel.web.view;

import java.util.List;

import tr.org.unicase.kernel.model.AbstractEntity;
import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.kernel.web.controller.FieldControllerImp;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.action.api.UnicaseActionManager;
import tr.org.unicase.web.view.api.AbstractView;
import tr.org.unicase.web.view.validator.NullValidator;
import tr.org.unicase.web.view.validator.OptionValidator;

import com.vaadin.data.Validator;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class FieldViewImp extends AbstractView<FieldControllerImp> {

	private GridLayout mainGridLayout = new GridLayout(2, 1);
	private VerticalLayout leftVerticalLayout;

	private TextField textField_Name;
	private TextField textField_EntityTypeId;
	private TextField textField_Width;
	private TextField textField_Height;
	private TextField textField_Visible;
	private TextField textField_ColumnIndex;
	private TextField textField_LabelText;
	private TextField textField_RefTypeId;
	private TextField textField_Type;
	private TextField textField_RefColumn;
	private TextField textField_OrderNo;
	private TextField textField_Depends;
	private TextField textField_ValidationRegex;
	private TextField textField_ValidationErrorMessage;
	private CheckBox checkBox_Searchable;
	private CheckBox checkBox_NotNull;
	private CheckBox checkBox_Validate;
	private CheckBox checkBox_VisibleAsTableColumn;
	private CheckBox checkBox_Readonly;
	private CheckBox checkBox_Generated;
	private TextField textField_DetailIcon;
	private TextField textField_ReportAlias;
	private TextField textField_ParentTypeId;

	public FieldViewImp(Long entityTypeId, Long ownerId, String refOwner) {
		this.entityTypeId = entityTypeId;
		this.ownerId = ownerId;
		this.refOwner = refOwner;
		
		setName(this.getClass().getSimpleName());

		setController(new FieldControllerImp(entityTypeId, ownerId, refOwner));
		buildLeftVerticalLayout();

		buildLayout();

		if (leftVerticalLayout != null)
			mainGridLayout.addComponent(leftVerticalLayout, 0, 0);

		Action action = UnicaseActionManager.getInstance().createInstance("tr.org.unicase.kernel.web.view.action.SaveActionImpl", "1", null, "save.png", "Save", "clazz", controller, this, null);
		if (action != null)
			actionList.add(action);

		action = UnicaseActionManager.getInstance().createInstance("tr.org.unicase.kernel.web.view.action.GenerateActionImpl", "1", null, "new.png", "New", "clazz", controller, this, null);
		if (action != null)
			actionList.add(action);

		action = null;

	}

	@Override
	public void initLayout() {

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

	private Validator getNullValidator(final String fieldName) {
		return new NullValidator(fieldName);
	}

	private Validator getOptionValidator(final String fieldName, final String... options) {
		return new OptionValidator(fieldName, options);
	}

	private void buildLayout() {

		textField_Name = (TextField) createComponent(textField_Name, TextField.class, new Label("Name"));
		textField_Name.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		textField_Name.addValidator(getNullValidator("Name"));

		textField_Width = (TextField) createComponent(textField_Width, TextField.class, new Label("Width"));
		textField_Width.addValidator(getNullValidator("Width"));

		textField_Height = (TextField) createComponent(textField_Height, TextField.class, new Label("Height"));
		textField_Height.addValidator(getNullValidator("Height"));

		textField_LabelText = (TextField) createComponent(textField_LabelText, TextField.class, new Label("LabelText"));
		textField_LabelText.addValidator(getNullValidator("LabelText"));

		textField_EntityTypeId = (TextField) createComponent(textField_EntityTypeId, TextField.class, new Label("EntityTypeId"));
		textField_EntityTypeId.addValidator(getNullValidator("EntityTypeId"));

		textField_Visible = (TextField) createComponent(textField_Visible, TextField.class, new Label("Visible(true,false)"));
		textField_Visible.addValidator(getNullValidator("Visible"));
		textField_Visible.addValidator(getOptionValidator("Visible", "true", "false"));

		textField_ColumnIndex = (TextField) createComponent(textField_ColumnIndex, TextField.class, new Label("ColumnIndex(0,1)"));
		textField_ColumnIndex.addValidator(getNullValidator("ColumnIndex"));
		textField_ColumnIndex.addValidator(getOptionValidator("ColumnIndex", "0", "1"));

		textField_RefTypeId = (TextField) createComponent(textField_RefTypeId, TextField.class, new Label("RefTypeId"));

		textField_Type = (TextField) createComponent(textField_Type, TextField.class, new Label("Type(1TON,1TO1,NTO1)"));

		textField_RefColumn = (TextField) createComponent(textField_RefColumn, TextField.class, new Label("RefColumn"));

		textField_ParentTypeId = (TextField) createComponent(textField_ParentTypeId, TextField.class, new Label("ParentTypeId"));

		textField_OrderNo = (TextField) createComponent(textField_OrderNo, TextField.class, new Label("Order No"));

		textField_Depends = (TextField) createComponent(textField_Depends, TextField.class, new Label("Depends"));
		textField_Depends.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				String theValue = (String) value;
				if (theValue != null) {
					if (theValue.trim().length() > 0) {
						if (!theValue.startsWith("ck_"))
							throw new InvalidValueException("Depends sadece ck ile baslayan alanlar icin tanimlanmaktadir.");
					}
				}
			}
		});

		checkBox_Searchable = (CheckBox) createComponent(checkBox_Searchable, CheckBox.class, new Label("Sorgulanabilir"));
		checkBox_Searchable.setStyleName(ValoTheme.CHECKBOX_SMALL);
		checkBox_NotNull = (CheckBox) createComponent(checkBox_NotNull, CheckBox.class, new Label("Not Null"));
		checkBox_NotNull.setStyleName(ValoTheme.CHECKBOX_SMALL);
		checkBox_Validate = (CheckBox) createComponent(checkBox_NotNull, CheckBox.class, new Label("Validate"));
		checkBox_Validate.setStyleName(ValoTheme.CHECKBOX_SMALL);
		checkBox_VisibleAsTableColumn = (CheckBox) createComponent(checkBox_VisibleAsTableColumn, CheckBox.class, new Label("Kolon Olarak Gor ?"));
		checkBox_VisibleAsTableColumn.setStyleName(ValoTheme.CHECKBOX_SMALL);

		checkBox_Generated = (CheckBox) createComponent(checkBox_Generated, CheckBox.class, new Label("Generated"));
		checkBox_Generated.setStyleName(ValoTheme.CHECKBOX_SMALL);
		
		checkBox_Readonly= (CheckBox) createComponent(checkBox_Readonly, CheckBox.class, new Label("Read Only"));
		checkBox_Readonly.setStyleName(ValoTheme.CHECKBOX_SMALL);

		textField_ValidationRegex = (TextField) createComponent(textField_ValidationRegex, TextField.class, new Label("Validation Regex"));
		textField_ValidationErrorMessage = (TextField) createComponent(textField_ValidationErrorMessage, TextField.class, new Label("Validation Error Message"));

		textField_DetailIcon = (TextField) createComponent(textField_DetailIcon, TextField.class, new Label("Detail Icon"));

		textField_ReportAlias = (TextField) createComponent(textField_ReportAlias, TextField.class, new Label("Report Alias"));

	}

	private Component createComponent(Object object, Class<?> clazz, Label label) {
		Component component = null;
		try {
			object = clazz.newInstance();
			component = (Component) object;
			component.setWidth("450px");
			component.setHeight("24px");
			if (AbstractTextField.class.isAssignableFrom(clazz)) {
				((AbstractTextField) component).setNullRepresentation("");
				((AbstractTextField) component).setValidationVisible(false);
				((AbstractTextField) component).setImmediate(true);
			}
			GridLayout gridLayout = new GridLayout(2, 1);
			gridLayout.setColumnExpandRatio(0, 3);
			gridLayout.setColumnExpandRatio(1, 2);
			gridLayout.setSpacing(false);
			gridLayout.setMargin(false);
			label.setWidth("150px");
			label.setStyleName(ValoTheme.LABEL_SMALL);
			gridLayout.addComponent(label, 0, 0);
			gridLayout.addComponent(component, 1, 0);
			gridLayout.setComponentAlignment(component, Alignment.MIDDLE_LEFT);
			leftVerticalLayout.addComponent(gridLayout);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return component;
	}

	@Override
	public void update() {

		Field entity = controller.getEntity();
		if (entity == null)
			return;

		textField_Name.setValue(entity.getName());

		textField_Width.setValue(entity.getWidth());

		textField_Height.setValue(entity.getHeight());
		textField_LabelText.setValue(entity.getLabelText());
		textField_EntityTypeId.setValue(String.valueOf(entity.getEntityTypeId()));
		textField_Visible.setValue(String.valueOf(entity.getVisible()));
		textField_ColumnIndex.setValue(String.valueOf(entity.getColumnIndex()));
		textField_RefTypeId.setValue(String.valueOf(entity.getRefTypeId()));
		textField_RefColumn.setValue(entity.getRefColumn());
		textField_Type.setValue(entity.getType());
		textField_ParentTypeId.setValue((entity.getParentTypeId() != null ? String.valueOf(entity.getParentTypeId()) : ""));
		textField_OrderNo.setValue(String.valueOf(entity.getOrderNo()));
		textField_Depends.setValue(String.valueOf(entity.getDepends()));
		checkBox_Searchable.setValue((entity.getSearchable().equals(AbstractEntity.TRUE) ? true : false));
		checkBox_NotNull.setValue((entity.getNotNull().equals(AbstractEntity.TRUE) ? true : false));
		checkBox_Validate.setValue((entity.getValidate().equals(AbstractEntity.TRUE) ? true : false));
		checkBox_Readonly.setValue((entity.getReadonly().equals(AbstractEntity.TRUE) ? true : false));
		checkBox_VisibleAsTableColumn.setValue(entity.getVisibleInTableColumn().equals(AbstractEntity.TRUE) ? true : false);
		checkBox_Generated.setValue(entity.getGenerated().equals(AbstractEntity.TRUE) ? true : false);
		textField_ValidationRegex.setValue(entity.getValidationRegex());
		textField_ValidationErrorMessage.setValue(entity.getValidationErrorMessage());
		textField_DetailIcon.setValue(entity.getDetailIcon());
		textField_ReportAlias.setValue(entity.getreportAlias());
	}

	@Override
	public boolean validate() {
		boolean result = true;

		result &= valid(textField_Name);
		result &= valid(textField_EntityTypeId);
		result &= valid(textField_Width);
		result &= valid(textField_Height);
		result &= valid(textField_Visible);
		result &= valid(textField_ColumnIndex);
		result &= valid(textField_LabelText);
		result &= valid(textField_RefTypeId);
		result &= valid(textField_Type);
		result &= valid(textField_RefColumn);
		result &= valid(textField_ParentTypeId);
		result &= valid(textField_OrderNo);
		result &= valid(textField_Depends);
		result &= valid(textField_ReportAlias);
		return result;
	}

	@Override
	public void setViewToEntity() {

		Field entity = controller.getEntity();

		entity.setName((String) textField_Name.getValue());
		entity.setWidth((String) textField_Width.getValue());
		entity.setHeight((String) textField_Height.getValue());
		entity.setLabelText((String) textField_LabelText.getValue());
		entity.setEntityTypeId(new Long(textField_EntityTypeId.getValue()));
		entity.setVisible(new Boolean(textField_Visible.getValue().toString()));
		entity.setColumnIndex(new Integer(textField_ColumnIndex.getValue().toString()));
		if (textField_RefTypeId.getValue() != null && !textField_RefTypeId.getValue().trim().isEmpty())
			entity.setRefTypeId(new Long(textField_RefTypeId.getValue().toString()));
		entity.setRefColumn((String) textField_RefColumn.getValue());
		entity.setType((String) textField_Type.getValue());
		if (textField_ParentTypeId.getValue() != null && !textField_ParentTypeId.getValue().trim().isEmpty())
			entity.setParentTypeId(new Long(textField_ParentTypeId.getValue().toString()));
		entity.setOrderNo(Long.parseLong(textField_OrderNo.getValue()));
		entity.setDepends(textField_Depends.getValue());
		entity.setSearchable((checkBox_Searchable.getValue() ? AbstractEntity.TRUE : AbstractEntity.FALSE));
		entity.setNotNull((checkBox_NotNull.getValue() ? AbstractEntity.TRUE : AbstractEntity.FALSE));
		entity.setValidate((checkBox_Validate.getValue() ? AbstractEntity.TRUE : AbstractEntity.FALSE));
		entity.setReadonly((checkBox_Readonly.getValue() ? AbstractEntity.TRUE : AbstractEntity.FALSE));
		entity.setValidationRegex(textField_ValidationRegex.getValue());
		entity.setValidationErrorMessage(textField_ValidationErrorMessage.getValue());
		entity.setDetailIcon(textField_DetailIcon.getValue());
		entity.setVisibleInTableColumn((checkBox_VisibleAsTableColumn.getValue() ? AbstractEntity.TRUE : AbstractEntity.FALSE));
		entity.setGenerated(checkBox_Generated.getValue() ? AbstractEntity.TRUE : AbstractEntity.FALSE);
		entity.setreportAlias((String) textField_ReportAlias.getValue());
	}

	@Override
	public List<Action> getActions() {
		return actionList;
	}

	@Override
	public void refresh() {

	}

}

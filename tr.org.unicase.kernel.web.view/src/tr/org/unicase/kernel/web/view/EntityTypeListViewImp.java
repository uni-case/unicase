package tr.org.unicase.kernel.web.view;

import tr.org.unicase.kernel.model.EntityType;
import tr.org.unicase.kernel.web.controller.EntityTypeControllerImp;
import tr.org.unicase.kernel.web.view.internals.config.Configuration;
import tr.org.unicase.web.component.UnicaseTable;
import tr.org.unicase.web.component.excel.UnicaseExcelExportButton;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.action.api.UnicaseActionManager;
import tr.org.unicase.web.view.api.AbstractView;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class EntityTypeListViewImp extends AbstractView<EntityTypeControllerImp> implements Table.ValueChangeListener {

	private TextField textFieldClazz;

	public EntityTypeListViewImp(Long entityTypeId, Long ownerId, String refOwner) {
		this.entityTypeId = entityTypeId;
		this.ownerId = ownerId;
		this.refOwner = refOwner;

		setController(new EntityTypeControllerImp(entityTypeId, ownerId, refOwner));
		controller.findAll(entityTypeId, ownerId, refOwner);

		Action openAction = UnicaseActionManager.getInstance().createInstance(Configuration.ViewAction.OPEN,Configuration.ViewAction.ID, null, Configuration.ViewAction.OPEN_ICON, Configuration.ViewAction.OPEN_CAPTION, EntityTypeViewImp.class.getName(), controller, this, null);
		if (openAction != null)
			actionList.add(openAction);

		createTable();
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

		table.addValueChangeListener(this);
	}

	@Override
	public void initLayout() {

	}

	private HorizontalLayout getSearchLayout() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);

		textFieldClazz = new TextField();
		textFieldClazz.setImmediate(true);
		textFieldClazz.setCaption("Class");
		layout.addComponent(textFieldClazz);

		return layout;
	}

	private VerticalLayout getTableLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.setId("tableLayout01");
		Button buttonSearch = new Button();
		// buttonSearch.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		buttonSearch.setStyleName(BaseTheme.BUTTON_LINK);
		buttonSearch.setDescription("Sorgula");
		buttonSearch.setCaption(buttonSearch.getDescription());
		buttonSearch.setIcon(new ThemeResource("../unicase/icons/32/zoom-search-2-icon.png"));
		buttonSearch.setClickShortcut(KeyCode.ENTER);
		buttonSearch.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// search();
			}
		});

		Button buttonClear = new Button();
		buttonClear.setStyleName(BaseTheme.BUTTON_LINK);
		buttonClear.setDescription("Temizle");
		buttonClear.setCaption(buttonClear.getDescription());
		buttonClear.setIcon(new ThemeResource("../unicase/icons/32/recyclebin-icon.png"));
		buttonClear.setClickShortcut(KeyCode.DELETE);
		buttonClear.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// clearForm();
			}
		});

		final UnicaseExcelExportButton excelExportButton = new UnicaseExcelExportButton();
		excelExportButton.setImmediate(true);
		excelExportButton.setStyleName(BaseTheme.BUTTON_LINK);
		excelExportButton.setDescription("Excel Export");
		excelExportButton.setCaption("Excel Export");
		excelExportButton.setIcon(new ThemeResource("../unicase/icons/32/Excel-icon.png"));
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
		mainVerticalLayout.addComponent(getSearchLayout());
		mainVerticalLayout.addComponent(getTableLayout());

		return mainVerticalLayout;
	}

	@Override
	public void update() {
		getController().findAll(entityTypeId, ownerId, refOwner);
		table.refreshTable();

	}

	@Override
	public void setViewToEntity() {

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		EntityType entity = (EntityType) (table.getValue());
		controller.setEntity(entity);

	}

	@Override
	public void refresh() {
		if (table.size() > 0)
			table.removeAllItems();
		table.setContainerDataSource(controller.getDataSource());
		table.setVisibleColumns(controller.getNaturalColumnOrder());
		table.setColumnHeaders(controller.getColumnHeaderValues());
	}

}

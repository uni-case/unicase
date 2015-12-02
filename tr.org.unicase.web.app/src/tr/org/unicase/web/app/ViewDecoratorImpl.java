package tr.org.unicase.web.app;

import java.util.List;

import tr.org.unicase.authentication.service.model.User;
import tr.org.unicase.kernel.web.view.FieldListViewImp;
import tr.org.unicase.kernel.web.view.FieldViewImp;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.action.api.UnicaseActionManager;
import tr.org.unicase.web.view.api.AbstractView;
import tr.org.unicase.web.view.api.View;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.ValoTheme;

public class ViewDecoratorImpl extends AbstractView<Controller<Object>> {

	private View<Controller<Object>> view;
	private boolean decorateHeader = true;

	public ViewDecoratorImpl(View<Controller<Object>> view) {
		this.view = view;
		setController(view.getController());
		decorateHeader = true;
	}

	public ViewDecoratorImpl(View<Controller<Object>> view, boolean decorateHeader) {
		this(view);
		this.decorateHeader = decorateHeader;
	}

	@Override
	public void initLayout() {
		view.initLayout();

	}

	private Button createActionButton(final Action action) {
		Button button = new Button();
		button.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		button.setDescription(action.getText());

		button.setIcon(IconHelper.getIcon32(action.getIcon()));

		button.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					action.execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		return button;
	}

	private Component createContent() {
		// menu leri ekran uzerinde cikartiyor.
		if (view != null && view.getClass().getName().equals("tr.org.unicase.reference.web.view.ReferenceDetailViewImp")) {
			if (view.getChildsAction() != null && view.getChildsAction().size() > 0) {
				HorizontalLayout actionLayout = new HorizontalLayout();
				actionLayout.setSpacing(true);
				for (final Action action : view.getChildsAction()) {
					Button button = new Button();
					button.setIcon(IconHelper.getIcon32(action.getIcon()));

					button.addClickListener(new ClickListener() {

						@Override
						public void buttonClick(ClickEvent event) {
							try {
								action.execute();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					button.setCaption(action.getText());
					button.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
					actionLayout.addComponent(button);
				}
				mainVerticalLayout.addComponent(actionLayout);
				mainVerticalLayout.setComponentAlignment(actionLayout, Alignment.MIDDLE_RIGHT);
			}
		}

		mainVerticalLayout.setWidth(100, Unit.PERCENTAGE);
		mainVerticalLayout.setHeight(null);
		return mainVerticalLayout;

	}

	@SuppressWarnings("rawtypes")
	@Override
	public Component getView() {
		mainVerticalLayout.setId("mVL" + this.getClass().getSimpleName().toLowerCase());
		mainVerticalLayout.setStyleName("v-scrollable");
		mainVerticalLayout.setHeight(100, Unit.PERCENTAGE);
		CssLayout layout = new CssLayout();
		layout.setId("css" + this.getClass().getSimpleName().toLowerCase());
		layout.addStyleName("card");

		HorizontalLayout panelCaption = new HorizontalLayout();
		panelCaption.addStyleName("v-panel-caption");
		panelCaption.setWidth(100, Unit.PERCENTAGE);
		Button iconButton = new Button();
		iconButton.setStyleName(BaseTheme.BUTTON_LINK);
		iconButton.setIcon(IconHelper.getIcon48(view.getIcon()));
		panelCaption.addComponent(iconButton);

		String caption = view.getName();

		if (((AbstractView) view).getOwnerInfo() != null)
			caption = caption + " - " + ((AbstractView) view).getOwnerInfo();

		Label label = new Label("<strong><h4>" + caption + "</h4></strong>", ContentMode.HTML);
		panelCaption.addComponent(label);
		panelCaption.setExpandRatio(label, 1);
		panelCaption.setComponentAlignment(label, Alignment.MIDDLE_CENTER);

		if (decorateHeader) {
			Action action = UnicaseActionManager.getInstance().createInstance("tr.org.unicase.kernel.web.view.action.internals.BackActionImpl", "1", null, "back.png", "Back", "", view.getController(), view, null);
			if (action != null) {
				Button backButton = createActionButton(action);
				backButton.addStyleName("borderless-colored");
				backButton.addStyleName("icon-only");
				backButton.setClickShortcut(KeyCode.F4);
				panelCaption.addComponent(backButton);
			}

			action = UnicaseActionManager.getInstance().createInstance("tr.org.unicase.kernel.web.view.action.internals.RefreshActionImpl", "1", null, "refresh.png", "Refresh", "", view.getController(), view, null);
			if (action != null) {
				Button refreshButton = createActionButton(action);
				refreshButton.addStyleName("borderless-colored");
				refreshButton.addStyleName("icon-only");
				refreshButton.setClickShortcut(KeyCode.F5);
				panelCaption.addComponent(refreshButton);
			}
			User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
			if (user != null) {
				if ("admin".equals(user.getRole())) {
					if (!view.getClass().getName().equals(FieldListViewImp.class.getName()) || !view.getClass().getName().equals(FieldViewImp.class.getName())) {
						action = UnicaseActionManager.getInstance().createInstance("tr.org.unicase.kernel.web.view.action.internals.SettingsActionImpl", "1", null, "settings.png", "Ayarlar", "", view.getController(), view, null);
						if (action != null) {
							Button settingsButton = createActionButton(action);
							settingsButton.addStyleName("borderless-colored");
							settingsButton.addStyleName("icon-only");
							panelCaption.addComponent(settingsButton);
						}
					}
				}
			}

		}

		layout.addComponent(panelCaption);
		layout.addComponent(createContent());
		Panel panel = new Panel(view.getView());
		panel.setId("panelId");
		panel.setHeight(82, Unit.PERCENTAGE);
		panel.setStyleName(ValoTheme.PANEL_BORDERLESS);
		layout.addComponent(panel);
		layout.addComponent(createActionLayout());
		layout.setSizeFull();

		return layout;

	}

	private Component createActionLayout() {
		HorizontalLayout buttonLayout = new HorizontalLayout();

		List<Action> actions = view.getActions();

		if (actions != null) {
			Button button = null;
			for (final Action action : actions) {
				button = new Button();
				button.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
				button.setIcon(IconHelper.getIcon32(action.getIcon()));
				if (action.getText().equals("Sil")) {
					button.setWidth("100px");
					button.setClickShortcut(KeyCode.F9);
				} else if (action.getText().equals("Yeni")) {
					button.setWidth("100px");
					button.setClickShortcut(KeyCode.F3);
				} else if (action.getText().equals("Kaydet")) {
					button.setWidth("100px");
					button.setClickShortcut(KeyCode.F2);
				} else {

				}
				button.setDescription(action.getText());
				button.setCaption(action.getText());
				button.addClickListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							action.execute();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				buttonLayout.addComponent(button);
			}
		}

		return buttonLayout;
	}

	@Override
	public void update() {
		view.update();

	}

	@Override
	public void setViewToEntity() {
		view.setViewToEntity();

	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

}

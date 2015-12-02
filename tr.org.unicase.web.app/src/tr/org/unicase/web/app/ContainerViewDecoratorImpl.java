package tr.org.unicase.web.app;

import java.util.List;

import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.api.AbstractView;
import tr.org.unicase.web.view.api.View;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ContainerViewDecoratorImpl extends AbstractView<Controller<Object>> {

	private View<Controller<Object>> view;

	public ContainerViewDecoratorImpl(View<Controller<Object>> view) {
		this.view = view;
		setController(view.getController());
	}

	@Override
	public void initLayout() {
		view.initLayout();

	}
	
	@Override
	public List<Action> getActions() {
		return view.getActions();
	}
	
	@Override
	public List<Action> getChildsAction() {
		return view.getChildsAction(); 
	}
	

	@Override
	public Component getView() {
		mainVerticalLayout.setId(this.getClass().getSimpleName().toLowerCase());
		mainVerticalLayout.setStyleName("v-scrollable");
		mainVerticalLayout.setHeight(100, Unit.PERCENTAGE);

		if (view != null && view.getClass().getName().equals("tr.org.unicase.reference.web.view.ReferenceDetailViewImp")) {
			HorizontalLayout actionLayout = new HorizontalLayout();
			actionLayout.setSpacing(true);
			
			for (final Action action : view.getChildsAction()) {
				Button button = new Button();
				button.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
				button.setCaption(action.getText());
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
				actionLayout.addComponent(button);
			}
			mainVerticalLayout.addComponent(actionLayout);
			mainVerticalLayout.setComponentAlignment(actionLayout, Alignment.MIDDLE_LEFT);
		}

		Component view2 = view.getView();
		mainVerticalLayout.addComponent(view2);

		List<Action> actions = view.getActions();

		if (actions != null) {

			for (final Action action : view.getActions()) {
				Button button = new Button();
				button.setIcon(IconHelper.getIcon32(action.getIcon()));
				button.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
				if (action.getText().equals("Sil")) {
					button.setWidth("100px");
				} else if (action.getText().equals("Yeni")) {
					button.setWidth("100px");
				} else if (action.getText().equals("Kaydet")) {
					button.setWidth("100px");
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
				view.addActionButton(button);
			}
		}


		return mainVerticalLayout;

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

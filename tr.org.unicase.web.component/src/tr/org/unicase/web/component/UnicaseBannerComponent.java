package tr.org.unicase.web.component;

import tr.org.unicase.web.component.internals.config.Configuration;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class UnicaseBannerComponent extends HorizontalLayout {

	public UnicaseBannerComponent(String banner) {
		super();
		buildComponent(banner);
	}

	public UnicaseBannerComponent() {
		this(Configuration.Banner.DEFAULT);
	}

	private void buildComponent(String banner) {
		this.setWidth(Configuration.Banner.WIDTH);
		Label title = new Label(String.format(Configuration.Banner.FORMAT_TITLE, banner), ContentMode.HTML);
		title.setSizeUndefined();
		this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		this.addStyleName(Configuration.Banner.STYLE);
		this.addComponent(title);
		this.setExpandRatio(title, 1);
	}

}

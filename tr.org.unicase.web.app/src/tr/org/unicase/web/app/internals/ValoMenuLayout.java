package tr.org.unicase.web.app.internals;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

public class ValoMenuLayout extends HorizontalLayout {

	private CssLayout contentArea = new CssLayout();

	public ValoMenuLayout() {
		setSizeFull();
		
		contentArea.setPrimaryStyleName("valo-content");
		contentArea.addStyleName("v-scrollable");
		contentArea.setSizeFull();

		addComponents(contentArea);
		setExpandRatio(contentArea, 2);
	}

	public ComponentContainer getContentContainer() {
		return contentArea;
	}
	
}

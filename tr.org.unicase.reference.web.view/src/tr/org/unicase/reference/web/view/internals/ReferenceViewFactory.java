package tr.org.unicase.reference.web.view.internals;

import tr.org.unicase.reference.web.view.ReferenceContainerViewImp;
import tr.org.unicase.reference.web.view.ReferenceDetailViewImp;
import tr.org.unicase.reference.web.view.ReferenceListViewImp;
import tr.org.unicase.reference.web.view.ReferenceParentViewImp;
import tr.org.unicase.reference.web.view.ReferenceReportViewImp;
import tr.org.unicase.reference.web.view.ReferenceViewImp;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.api.IViewFactory;
import tr.org.unicase.web.view.api.View;

public class ReferenceViewFactory implements IViewFactory {

	@Override
	public int getFactoryId() {
		return getName().hashCode();
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	@SuppressWarnings("unchecked")
	public View<Controller<Object>> createInstance(String clazz, Long entityTypeId, Long ownerId, String refOwner) {
		View<?> view = null;

		if (ReferenceViewImp.class.getName().equals(clazz))
			view = new ReferenceViewImp(entityTypeId, ownerId, refOwner);
		else if (ReferenceDetailViewImp.class.getName().equals(clazz))
			view = new ReferenceDetailViewImp(entityTypeId, ownerId, refOwner);
		else if (ReferenceListViewImp.class.getName().equals(clazz))
			view = new ReferenceListViewImp(entityTypeId, ownerId, refOwner);
		else if (ReferenceParentViewImp.class.getName().equals(clazz))
			view = new ReferenceParentViewImp(entityTypeId, ownerId, refOwner);
		else if (ReferenceReportViewImp.class.getName().equals(clazz))
			view = new ReferenceReportViewImp(entityTypeId, ownerId, refOwner);
		else if (ReferenceContainerViewImp.class.getName().equals(clazz))
			view = new ReferenceContainerViewImp(entityTypeId, ownerId, refOwner);
		
		/*
		 else if (ReferenceViewContentImp.class.getName().equals(clazz))
			view = new ReferenceViewContentImp(null, null);
			*/
		
		return (View<Controller<Object>>) view;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof IViewFactory)) {
			return false;
		}

		return (this.getFactoryId() == ((IViewFactory) obj).getFactoryId());
	}

}
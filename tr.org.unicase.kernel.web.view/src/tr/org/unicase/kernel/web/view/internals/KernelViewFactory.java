package tr.org.unicase.kernel.web.view.internals;

import tr.org.unicase.kernel.web.view.EntityTypeListViewImp;
import tr.org.unicase.kernel.web.view.EntityTypeViewImp;
import tr.org.unicase.kernel.web.view.FieldListViewImp;
import tr.org.unicase.kernel.web.view.FieldViewImp;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.api.IViewFactory;
import tr.org.unicase.web.view.api.View;

public class KernelViewFactory implements IViewFactory {

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

		if (EntityTypeListViewImp.class.getName().equals(clazz))
			view = new EntityTypeListViewImp(entityTypeId, ownerId, refOwner);
		else if (EntityTypeViewImp.class.getName().equals(clazz))
			view = new EntityTypeViewImp(entityTypeId, ownerId, refOwner);
		else if (FieldListViewImp.class.getName().equals(clazz))
			view = new FieldListViewImp(entityTypeId, ownerId, refOwner);
		else if (FieldViewImp.class.getName().equals(clazz))
			view = new FieldViewImp(entityTypeId, ownerId, refOwner);

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

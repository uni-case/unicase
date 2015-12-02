package tr.org.unicase.reference.web.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tr.org.unicase.reference.model.ReferenceEntity;
import tr.org.unicase.reference.web.controller.ReferenceControllerImp;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.action.api.UnicaseActionManager;
import tr.org.unicase.web.view.api.Module;

public class ModuleImpl implements Module {

	ReferenceControllerImp controller = new ReferenceControllerImp();

	private List<ReferenceEntity> allMenuList;

	public List<Action> getActions() {
		allMenuList = controller.findAll(1l, null, null);

		Map<Long, ReferenceEntity> moduleList = new HashMap<Long, ReferenceEntity>();
		Map<Long, Action> moduleActionList = new HashMap<Long, Action>();
		Map<Long, List<ReferenceEntity>> menuList = new HashMap<Long, List<ReferenceEntity>>();
		Map<Long, Action> menuActionList = new HashMap<Long, Action>();
		List<ReferenceEntity> viewList = new ArrayList<ReferenceEntity>();

		for (ReferenceEntity referenceEntity : allMenuList) {
			if (referenceEntity.getCk_01() != null && referenceEntity.getCk_01().getCode().equals("MD"))
				moduleList.put(referenceEntity.getId(), referenceEntity);
			else if (referenceEntity.getCk_01() != null && referenceEntity.getCk_01().getCode().equals("M")) {
				if (referenceEntity.getCk_owner() != null) {
					List<ReferenceEntity> list = menuList.get(referenceEntity.getCk_owner().getId());
					if (list == null)
						list = new ArrayList<ReferenceEntity>();
					list.add(referenceEntity);
					menuList.put(referenceEntity.getCk_owner().getId(), list);
				}
			} else if (referenceEntity.getCk_01() != null && referenceEntity.getCk_01().getCode().equals("W")) {
				if (referenceEntity.getCk_owner() != null && referenceEntity.getCk_owner().getId() != null) {
					viewList.add(referenceEntity);
				}
			}

			
		}

		Action action = null;
		for (Long key : moduleList.keySet()) {
			action = createAction(moduleList.get(key));
			moduleActionList.put(key, action);			
		}
		
		List<ReferenceEntity> entityList = null;
		for (Long key : menuList.keySet()) {
			entityList = menuList.get(key);
			//action = createAction(entityList);
			if (moduleList.get(key) != null) {
				// module bagli
				for (ReferenceEntity entity : entityList) {
					action = createAction(entity);
					moduleActionList.get(key).addAction(action);
					menuActionList.put(key, action);
				}
			} else if (menuList.get(key) != null) {
				/*// o zaman baska bir menuye bagli.
				Action childAction = null;
				if (menuActionList.get(key) != null) {
					childAction = findAction(moduleActionList.values(), entityList.getId());
				} else {
					childAction = findAction(moduleActionList.values(), entityList.getCk_owner().getId());
					menuActionList.put(key, action);
				}
				childAction.addAction(action);*/
			}
		}
		
		Action rootAction = null;
		for (ReferenceEntity entity1 : viewList) {
			action = createAction(entity1);
			rootAction = findAction(moduleActionList.values(), entity1.getCk_owner().getId());
			if (rootAction != null)
				rootAction.addAction(action);
		}
		
		return new ArrayList<Action>(moduleActionList.values());

	}
	
	private Action findAction(Collection<Action> actions, Long id) {
		Action result = null;
		for (Action action : actions) {
			if (action.getId().equals(id.toString())) {
				result = action;
				break;
			} else {
				result = findAction(action.getChildren(), id);
				if (result != null) 
					break;
			}
		}
		
		return result;
	}

	private Action createAction(ReferenceEntity referenceEntity) {
		String code = referenceEntity.getCode() == null ? "0" : referenceEntity.getCode();
		Action menu = UnicaseActionManager.getInstance().createInstance("tr.org.unicase.kernel.web.view.action.internals.MenuActionImpl", referenceEntity.getId().toString(), null, "" + referenceEntity.getDescription(), referenceEntity.getValue(), referenceEntity.getShortvalue(), null, null, null);
		menu.setEntityTypeId(Long.parseLong(code));
		return menu;
	}

	@Override
	public String getName() {
		return "Referans Yönetimi";
	}

	@Override
	public String getIcon() {
		return "folder.png";
	}

}

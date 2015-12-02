package tr.org.unicase.reference.web.controller.cache;

import java.util.List;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;

import tr.org.unicase.reference.model.ReferenceEntity;

public class ReferenceEntityCacheCommandProvider implements CommandProvider {

	
	
	@Override
	public String getHelp() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("---UniCase ReferenceEntity Cache Management Commands---\n\t");
		buffer.append("referenceEntityCacheReset <entityTypeId> - Reset Cache of Given EntityTypeId\n\t");
		buffer.append("referenceEntityCacheInfo <entityTypeId> - Information Cache of Given EntityTypeId\n\t");
		buffer.append("referenceEntityCacheReload <entityTypeId> - Reload Cache of Given EntityTypeId\n\t");
		return buffer.toString();
	}
	
	private void printUsage(CommandInterpreter ci, String commandName){
		ci.println("Usage: " + commandName + " <entityTypeId>");
	}
	
	public void _referenceEntityCacheReset(CommandInterpreter ci) {
		String typeIdAsString = ci.nextArgument();
		String methodName = "referenceEntityCacheReset";
		if (typeIdAsString != null && !typeIdAsString.trim().isEmpty()) {
			try {
				Long typeId = Long.parseLong(typeIdAsString.trim());
				ReferenceEntityCacheImpl.getInstance().clear(typeId);
				ci.println(typeId + " icin cache temizleme islemi tamamlandi.");
			} catch (NumberFormatException nfe) {
				printUsage(ci, methodName);
			}
		} else {
			printUsage(ci, methodName);
		}
	}

	public void _referenceEntityCacheInfo(CommandInterpreter ci) {
		String typeIdAsString = ci.nextArgument();
		String methodName = "referenceEntityCacheInfo";
		if (typeIdAsString != null && !typeIdAsString.trim().isEmpty()) {
			try {
				Long typeId = Long.parseLong(typeIdAsString.trim());
				List<ReferenceEntity> cache = ReferenceEntityCacheImpl.getInstance().getCache(typeId);
				ci.println(typeIdAsString.trim() + " tipinde " + ((cache != null && cache.size() > 0) ? "toplam " + cache.size() + " adet kayit var" : " hic kayit yok"));
			} catch (NumberFormatException nfe) {
				printUsage(ci, methodName);
			}
		} else {
			printUsage(ci, methodName);
		}

	}

	public void _referenceEntityCacheReload(CommandInterpreter ci) {
		String methodName = "referenceEntityCacheReload";
		String typeIdAsString = ci.nextArgument();
		if (typeIdAsString != null && !typeIdAsString.trim().isEmpty()) {
			try {
				Long typeId = Long.parseLong(typeIdAsString.trim());
				ReferenceEntityCacheImpl.getInstance().clear(typeId);
				ci.println(typeId + " ile ilgili cache kayitlari temizlendi.");
				ReferenceEntityCacheImpl.getInstance().getCache(typeId);
				ci.println(typeId + " ile ilgili reload islemi tamamlandi.");
			} catch (NumberFormatException nfe) {
				printUsage(ci, methodName);
			}
		} else {
			printUsage(ci, methodName);
		}
	}
	
}

package tr.org.unicase.kernel.web.controller.cache;

import java.util.List;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;

import tr.org.unicase.kernel.model.Field;

public class CoreFieldCacheCommandProvider implements CommandProvider {

	@Override
	public String getHelp() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("---UniCase CoreField Cache Management Commands---\n\t");
		buffer.append("fieldCacheReset <entityTypeId> - Reset Cache of Given EntityTypeId\n\t");
		buffer.append("fieldCacheInfo <entityTypeId> - Information Cache of Given EntityTypeId\n\t");
		buffer.append("fieldCacheReload <entityTypeId> - Reload Cache of Given EntityTypeId\n\t");
		return buffer.toString();
	}

	public void _fieldCacheReset(CommandInterpreter ci) {
		String typeIdAsString = ci.nextArgument();
		if (typeIdAsString != null && !typeIdAsString.trim().isEmpty()) {
			try {
				Long typeId = Long.parseLong(typeIdAsString.trim());
				CoreFieldCacheImpl.getInstance().clear(typeId);
				ci.println(typeId + " icin cache temizleme islemi tamamlandi.");
			} catch (NumberFormatException nfe) {
				ci.println("Usage: fieldCacheReset <entityTypeId>");
			}
		} else {
			ci.println("Usage: fieldCacheReset <entityTypeId>");
		}
	}

	public void _fieldCacheInfo(CommandInterpreter ci) {
		String typeIdAsString = ci.nextArgument();
		if (typeIdAsString != null && !typeIdAsString.trim().isEmpty()) {
			try {
				Long typeId = Long.parseLong(typeIdAsString.trim());
				List<Field> cache = CoreFieldCacheImpl.getInstance().getCache(typeId);
				ci.println(typeIdAsString.trim() + " tipinde " + ((cache != null && cache.size() > 0) ? "toplam " + cache.size() + " adet kayit var" : " hic kayit yok"));
			} catch (NumberFormatException nfe) {
				ci.println("Usage: fieldCacheInfo <entityTypeId>");
			}
		} else {
			ci.println("Usage: fieldCacheInfo <entityTypeId>");
		}

	}

	public void _fieldCacheReload(CommandInterpreter ci) {
		String usage = "Usage: fieldCacheReload <entityTypeId>";
		String typeIdAsString = ci.nextArgument();
		if (typeIdAsString != null && !typeIdAsString.trim().isEmpty()) {
			try {
				Long typeId = Long.parseLong(typeIdAsString.trim());
				CoreFieldCacheImpl.getInstance().clear(typeId);
				ci.println(typeId + " ile ilgili cache kayitlari temizlendi.");
				CoreFieldCacheImpl.getInstance().getCache(typeId);
				ci.println(typeId + " ile ilgili reload islemi tamamlandi.");
			} catch (NumberFormatException nfe) {
				ci.println(usage);
			}
		} else {
			ci.println(usage);
		}
	}

}

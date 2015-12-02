package tr.org.unicase.web.app;

import com.vaadin.server.ThemeResource;

public class IconHelper {

	private static final String BASE = "../unicase";
	private static final String BASE16 = BASE + "/icons/16/";
	private static final String BASE24 = BASE + "/icons/24/";
	private static final String BASE32 = BASE + "/icons/32/";
	private static final String BASE48 = BASE + "/icons/48/";
	private static final String BASE64 = BASE + "/icons/64/";
	private static final String BASE96 = BASE + "/icons/96/";
	private static final String IMAGE  = BASE + "/img/";
	
	private static ThemeResource getIcon(String basePath, String iconName) {
		String resourceId = basePath + "" + iconName;
		return new ThemeResource(resourceId);
	}
	
	public static ThemeResource getIcon(String iconName) {
		return getIcon(IMAGE, iconName);
	}
	
	public static ThemeResource getIcon16(String iconName) {
		return getIcon(BASE16, iconName);
	}
	
	public static ThemeResource getIcon24(String iconName) {
		return getIcon(BASE24, iconName);
	}
	
	public static ThemeResource getIcon32(String iconName) {
		return getIcon(BASE32, iconName);
	}

	public static ThemeResource getIcon48(String iconName) {
		return getIcon(BASE48, iconName);
	}
	
	public static ThemeResource getIcon64(String iconName) {
		return getIcon(BASE64, iconName);
	}

	public static ThemeResource getIcon96(String iconName) {
		return getIcon(BASE96, iconName);
	}
	
}

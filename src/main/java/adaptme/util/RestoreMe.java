package adaptme.util;

import java.util.prefs.Preferences;

import adaptme.Main;

public class RestoreMe {

    private static Preferences prefs;

    static {
	prefs = Preferences.userNodeForPackage(Main.class);
    }

    private static final String LAST_OPEN_PATH = "last_open_path";
    private static final String LAST_SAVE_PATH = "last_save_path";
    // private static final String PAUSE_ON_END_OF_DEVELOPMENT_SESSION =
    // "pause_on_end_of_development_session";
    // private static final String PAUSE_ON_END_OF_SPRINT =
    // "pause_on_end_of_sprint";
    // private static final String PAUSE_ON_END_OF_BUSINESS_DAY =
    // "pause_on_end_of_business_day";

    public static void storeLastOpenPath(String path) {
	prefs.put(LAST_OPEN_PATH, path);
    }

    public static String restoreLastOpenPath() {
	return prefs.get(LAST_OPEN_PATH, System.getProperty("user.home"));
    }

    public static void storeSavePath(String path) {
	prefs.put(LAST_SAVE_PATH, path);
    }

    public static String restoreSaveOpenPath() {
	return prefs.get(LAST_SAVE_PATH, System.getProperty("user.home"));
    }
}

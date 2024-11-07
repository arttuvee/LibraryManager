package model;

import java.util.prefs.Preferences;

public class UserPreferences {

    private static final Preferences preferences = Preferences.userNodeForPackage(UserPreferences.class);

    public static void setLanguage(String language) {
        preferences.put("language", language);
    }

    public static String getLanguage() {
        return preferences.get("language", "en");
    }
}

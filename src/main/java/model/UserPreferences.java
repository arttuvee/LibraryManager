package model;

import java.util.prefs.Preferences;

public class UserPreferences {

    private static final String LANGUAGE_KEY = "language";
    private static final Preferences prefs = Preferences.userNodeForPackage(UserPreferences.class);

    public static void setLanguage(String language) {
        prefs.put(LANGUAGE_KEY, language);
    }

    public static String getLanguage() {
        return prefs.get(LANGUAGE_KEY, "English"); // Default to English if no preference is set
    }
}
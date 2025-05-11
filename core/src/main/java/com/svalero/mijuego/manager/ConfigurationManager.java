package com.svalero.mijuego.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import static com.svalero.mijuego.util.Constants.GAME_NAME;

public class ConfigurationManager {

    private static Preferences prefs;
    private static boolean easyMode = false;

    public static void loadPreferences() {
        prefs = Gdx.app.getPreferences(GAME_NAME);
    }

    public static boolean isSoundEnabled() {
        return prefs.getBoolean("sound", true);
    }

    public static boolean isEasyMode() {
        return prefs.getBoolean("easyMode", false);
    }

    public static void setEasyMode(boolean value) {
        prefs.putBoolean("easyMode", value);
        prefs.flush();
    }

}

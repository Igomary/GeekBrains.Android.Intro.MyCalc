package igomary.android.intro.mycalc;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeUtils {
    protected static boolean setDark() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        return false;
    }

    protected static boolean setLight() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        return true;
    }
}

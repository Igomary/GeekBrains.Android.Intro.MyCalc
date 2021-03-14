package igomary.android.intro.mycalc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.internal.ThemeEnforcement;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private final static String DARK = "DarkTheme";
    private final static String LIGHT = "LightTheme";
    private boolean themeFlag; // false - dark, true - light


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        sharedPreferences = getSharedPreferences("Theme", MODE_PRIVATE);
        checkTheme();

        Button btnChange = findViewById(R.id.btnTheme);
        onClickListener(btnChange);
    }


    private void onClickListener(Button btn) {
        btn.setOnClickListener((view)->{
            themeFlag = !themeFlag;
            String name = themeFlag == true ? LIGHT : DARK;
            setTheme(name);
        });
    }


    private void checkTheme() {
        String theme = sharedPreferences.getString("ThemeName", LIGHT);
        if (theme.equalsIgnoreCase(DARK)){
            setDark();
        } else{
            setLight();
        }
    }

    public void setTheme (String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ThemeName", name);
        editor.apply();
        recreate();
    }

    private void setDark() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        themeFlag = false;
    }

    private void setLight() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        themeFlag = true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("Theme",themeFlag);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        themeFlag = savedInstanceState.getBoolean("Theme");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("ThemeName", sharedPreferences.getString("ThemeName", LIGHT));
        setResult(RESULT_OK, intent);
        finish();
    }
}

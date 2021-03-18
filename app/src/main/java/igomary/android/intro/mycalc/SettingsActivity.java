package igomary.android.intro.mycalc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
            themeFlag = ThemeUtils.setDark();
        } else{
            themeFlag = ThemeUtils.setLight();
        }
    }

    public void setTheme (String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ThemeName", name);
        editor.apply();
        recreate();
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

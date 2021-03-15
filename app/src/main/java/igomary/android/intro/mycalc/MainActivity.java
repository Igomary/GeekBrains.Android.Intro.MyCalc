package igomary.android.intro.mycalc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private final int[] mNumButtons =
            {R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9};
    private TextView mTextView;
    private final String PLUS = "Plus";
    private final static String DARK = "DarkTheme";
    private final static String LIGHT = "LightTheme";
    private final static String keyCalculator = "Calculator";
    private Calculator calculator;
    private boolean mThemeMode; // false - night, tue - light
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences= getSharedPreferences("Theme",MODE_PRIVATE);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        mTextView = findViewById(R.id.textView);
        if (savedInstanceState != null) {
            calculator = savedInstanceState.getParcelable(keyCalculator);
            mTextView.setText(calculator.getText());
            mThemeMode = calculator.isLightNightMode();
        } else{
            calculator = new Calculator();
        }

        setNumericOnClickListener();
        setNonNumericOnClickListener();
    }

    private boolean checkTheme(Menu menu) {
        MenuItem dark = menu.findItem(R.id.dark);
        MenuItem light = menu.findItem(R.id.light);

        String theme = sharedPreferences.getString("ThemeName", LIGHT);
        if (theme.equalsIgnoreCase(DARK)){
            dark.setChecked(true);
            return ThemeUtils.setDark();
        } else{
            light.setChecked(true);
            return ThemeUtils.setLight();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar, menu);
        mThemeMode = checkTheme(menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dark:
                setTheme(DARK,ThemeUtils.setDark());
                return true;
            case R.id.light:
                setTheme(LIGHT,ThemeUtils.setDark());
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent,RESULT_OK);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setTheme (String name, boolean isLight) {
        mThemeMode = isLight;
        calculator.setLightNightMode(mThemeMode);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ThemeName", name);
        editor.apply();
        recreate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putParcelable(keyCalculator,calculator);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle instanceState) {
        super.onRestoreInstanceState(instanceState);
        calculator = instanceState.getParcelable(keyCalculator);
    }

    private void setNumericOnClickListener() {
        View.OnClickListener listener = view -> {
            Button button = (Button) view;
            if (calculator.ismLastNumeric()) {
                mTextView.append(button.getText());
            } else {
                mTextView.setText(button.getText());
            }
            calculator.setmLastNumeric(true);
            calculator.setmErr(false);
            calculator.setText(mTextView.getText().toString());
        };
        for (int id : mNumButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setNonNumericOnClickListener() {
        findViewById(R.id.button_plus).setOnClickListener(v -> {
            Log.d(PLUS, "плюс нажали");
            if (!calculator.ismIsCounted()) {
                Log.d(PLUS, "isCounted = false");
                readFirst();
            } else {
                readSecond();
                count();
            }
            calculator.setmOperation(0);
        });

        findViewById(R.id.button_minus).setOnClickListener(v -> {
            if (!calculator.ismErr()) {
                if (!calculator.ismIsCounted()) {
                    Log.d(PLUS, "isCounted = false");
                    readFirst();
                } else {
                    readSecond();
                    count();
                }
                calculator.setmOperation(1);
            }
        });

        findViewById(R.id.button_mult).setOnClickListener(v -> {
            if (!calculator.ismErr()) {
                if (!calculator.ismIsCounted()) {
                    Log.d(PLUS, "isCounted = false");
                    readFirst();
                } else {
                    readSecond();
                    count();
                }
                calculator.setmOperation(2);
            }
        });

        findViewById(R.id.button_dev).setOnClickListener(v -> {
            if (!calculator.ismErr()) {
                if (!calculator.ismIsCounted()) {
                    Log.d(PLUS, "isCounted = false");
                    readFirst();
                } else {
                    readSecond();
                    count();
                }
                calculator.setmOperation(3);
            }
        });

        findViewById(R.id.button_point).setOnClickListener(v -> {
            if (calculator.ismLastNumeric()&& !calculator.ismLastDot()) {
                calculator.setmLastDot(true);
                mTextView.append(".");
            }
        });

        findViewById(R.id.button_clear).setOnClickListener(v -> {
            clear();
        });

        findViewById(R.id.button_equal).setOnClickListener(v -> {
            if (!calculator.ismLastNumeric()) {
                return;
            }
            readSecond();
            count();
            calculator.setmIsCounted(false);
        });

    }

    private void count() {
        mTextView.setText(calculator.count());
        readFirst();
    }

    private void readFirst() {
        if (!calculator.ismErr()) {
            calculator.setmFirstNum(Double.parseDouble(mTextView.getText().toString()));
            calculator.setmIsCounted(true);
        }
    }

    private void readSecond() {
        calculator.setmErr(false);
        calculator.setmSecondNum(Double.parseDouble(mTextView.getText().toString()));
    }

    private void clear() {
        calculator.setmErr(false);
        calculator.setmIsCounted(false);
        mTextView.setText("");
        calculator.setText("");
    }
}
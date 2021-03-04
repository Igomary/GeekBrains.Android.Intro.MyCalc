package igomary.android.intro.mycalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final int[] mNumButtons =
            {R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9};
    private TextView mTextView;
    private final String PLUS = "Plus";
    private final static String keyCalculator = "Calculator";
    private Calculator calculator = new Calculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.textView);
        if (savedInstanceState != null) {
            calculator = savedInstanceState.getParcelable(keyCalculator);
            mTextView.setText(calculator.getText());
        }
        Log.e(keyCalculator, ""+savedInstanceState);


        setNumericOnClickListener();
        setNonNumericOnClickListener();
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
            Log.e(PLUS, "плюс нажали");
            if (!calculator.ismIsCounted()) {
                Log.e(PLUS, "isCounted = false");
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
                    Log.e(PLUS, "isCounted = false");
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
                    Log.e(PLUS, "isCounted = false");
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
                    Log.e(PLUS, "isCounted = false");
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
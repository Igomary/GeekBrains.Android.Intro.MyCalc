package igomary.android.intro.mycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final int[] mNumButtons =
            {R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9};
    private TextView mTextView;
    private boolean mLastNumeric; //флаг введено ли последним число
    private boolean mLastDot;       //ставили ли точку в числе
    private boolean mIsCounted;        //выполняли ли какие-либо операции
    private double mFirstNum;
    private double mSecondNum;
    private double mResult;
    private int mOperation; // 0 - сложение, 1- вычитание, 2 - умножение, 3 - деление
    private final String PLUS = "Plus"; // все что ниже - для дебага
    private final String RESULT = "Result";
    private final String NUMERO = "Ввели число";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIsCounted = false;
        mLastDot = false;
        mTextView = findViewById(R.id.textView);
        setNumericOnClickListener();
        setNonNumericOnClickListener();
    }

    private void setNumericOnClickListener() {
        View.OnClickListener listener = view -> {
            Button button = (Button) view;
            if (mLastNumeric) {
                mTextView.append(button.getText());
            } else {
                mTextView.setText(button.getText());
            }
            mLastNumeric = true;
        };
        for (int id : mNumButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setNonNumericOnClickListener() {
        findViewById(R.id.button_plus).setOnClickListener(v -> {
            Log.e(PLUS, "плюс нажали");
           if (!mIsCounted) {
               readFirst();
               mIsCounted = true;
           } else{
               readSecond();
               count();
           }
            mOperation = 0;
            zeroDotAndNumeric();
        });

        findViewById(R.id.button_minus).setOnClickListener(v -> {
            if (!mIsCounted) {
                readFirst();
                mIsCounted = true;
            } else {
                readSecond();
                count();
            }
            mOperation = 1;
            zeroDotAndNumeric();
        });

        findViewById(R.id.button_mult).setOnClickListener(v -> {
            if (!mIsCounted) {
                readFirst();
                mIsCounted = true;
            } else {
                readSecond();
                count();
            }
            mOperation = 2;
            zeroDotAndNumeric();
        });

        findViewById(R.id.button_dev).setOnClickListener(v -> {
            if (!mLastNumeric) {
                return;
            }
            if (!mIsCounted) {
                readFirst();
                mIsCounted = true;
            } else {
                readSecond();
                count();
            }
            mOperation = 3;
            zeroDotAndNumeric();
        });

        findViewById(R.id.button_point).setOnClickListener(v -> {
            if (mLastNumeric && !mLastDot) {
                mLastDot = true;
                mTextView.append(".");
            }
        });

        findViewById(R.id.button_clear).setOnClickListener(v -> {
            clear();
        });

        findViewById(R.id.button_equal).setOnClickListener(v -> {
            if (!mLastNumeric) {
                return;
            }
            readSecond();
            count();
            mIsCounted = false;
        });

    }

    private void count() {
        mIsCounted = false;
        switch (mOperation) {
            case 0:
                Log.e(NUMERO, "складываем "+Double.toString(mFirstNum));
                mResult = mFirstNum + mSecondNum;
                break;
            case 1:
                mResult = mFirstNum - mSecondNum;
                break;
            case 2:
                mResult = mFirstNum * mSecondNum;
                break;
            case 3:
                if (Double.compare(mSecondNum,0.0)==0) {
                    devZeroErr();
                    return;
                }
                mResult = mFirstNum / mSecondNum;
                break;
        }
        Log.e(RESULT, "результат: " + Double.toString(mResult));
        String string;
        if (mResult % 1 == 0) {
            string = Integer.toString((int) mResult);
        } else {
            string = Double.toString(mResult);
        }
        Log.e(RESULT, "результат после обработки: " + string);
        mTextView.setText(string);
        readFirst();
    }

    private void readFirst() {
        mFirstNum = Double.parseDouble(mTextView.getText().toString());
        Log.e(NUMERO, "first: "+Double.toString(mFirstNum));
        mIsCounted = true;
    }

    private void readSecond() {
        mSecondNum = Double.parseDouble(mTextView.getText().toString());
        Log.e(NUMERO, "second: "+Double.toString(mSecondNum));
    }

    private void clear() {
        mTextView.setText("");
    }

    private void devZeroErr() {
        mTextView.setText("Деление на 0");
        mLastNumeric = false;
        mIsCounted = false;
    }

    private void zeroDotAndNumeric() {
        mLastDot = false;
        mLastNumeric = false;
    }


}
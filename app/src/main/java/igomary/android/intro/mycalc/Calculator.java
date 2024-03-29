package igomary.android.intro.mycalc;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Calculator implements Parcelable {
    private boolean mLastNumeric; //флаг введено ли последним число
    private boolean mLastDot;       //ставили ли точку в числе
    private boolean mIsCounted;        //выполняли ли какие-либо операции
    private boolean mErr;
    private double mFirstNum;
    private double mSecondNum;
    private double mResult;
    private String mText;
    private int mOperation; // 0 - сложение, 1- вычитание, 2 - умножение, 3 - деление
    private boolean mLightNightMode; // false - night, true - light
    private final String PLUS = "Plus"; // все что ниже - для дебага
    private final String RESULT = "Result";
    private final String NUMERO = "Ввели число";

    public Calculator() {
        mIsCounted = false;
        mLastDot = false;
        mText = "";
    }

    protected Calculator(Parcel in) {
        mLastNumeric = in.readByte() != 0;
        mLastDot = in.readByte() != 0;
        mIsCounted = in.readByte() != 0;
        mErr = in.readByte() != 0;
        mFirstNum = in.readDouble();
        mSecondNum = in.readDouble();
        mResult = in.readDouble();
        mText = in.readString();
        mOperation = in.readInt();
        mLightNightMode = in.readByte()!= 0;
    }

    public static final Creator<Calculator> CREATOR = new Creator<Calculator>() {
        @Override
        public Calculator createFromParcel(Parcel in) {
            return new Calculator(in);
        }

        @Override
        public Calculator[] newArray(int size) {
            return new Calculator[size];
        }
    };

    public boolean ismLastNumeric() {
        return mLastNumeric;
    }

    public void setmLastNumeric(boolean mLastNumeric) {
        this.mLastNumeric = mLastNumeric;
    }

    public boolean ismIsCounted() {
        return mIsCounted;
    }

    public void setmIsCounted(boolean mIsCounted) {
        this.mIsCounted = mIsCounted;
    }

    public void setmOperation(int mOperation) {
        this.mOperation = mOperation;
        mLastDot = false;
        mLastNumeric = false;
        Log.d(RESULT, Boolean.toString(mLastNumeric));
    }

    public String count() {
        mLastNumeric = false;
        mIsCounted = false;
        switch (mOperation) {
            case 0:
                Log.d(NUMERO, "складываем с первым " + Double.toString(mFirstNum));
                mResult = mFirstNum + mSecondNum;
                break;
            case 1:
                Log.d(NUMERO, "вычитаем из первого " + Double.toString(mFirstNum));
                mResult = mFirstNum - mSecondNum;
                break;
            case 2:
                Log.d(NUMERO, "умножаем на первое " + Double.toString(mFirstNum));
                mResult = mFirstNum * mSecondNum;
                break;
            case 3:
                Log.d(NUMERO, "делим первое " + Double.toString(mFirstNum));
                if (Double.compare(mSecondNum, 0.0) == 0) {
                    mErr = true;
                    Log.d(NUMERO, "деление на 0");
                    return devZeroErr();
                }
                mResult = mFirstNum / mSecondNum;
                break;
        }
        Log.d(RESULT, "результат: " + Double.toString(mResult));
        String string;
        if (mResult < Integer.MAX_VALUE && mResult % 1 == 0) {
            string = Integer.toString((int) mResult);
        } else {
            string = Double.toString(mResult);
        }
        Log.d(RESULT, "результат после обработки: " + string);
        mText = string;
        return string;
    }

    public void setmFirstNum(double mFirstNum) {
        this.mFirstNum = mFirstNum;
        Log.d(NUMERO, "first: " + Double.toString(mFirstNum));
        mIsCounted = true;
    }

    public boolean ismErr() {
        return mErr;
    }

    public void setmErr(boolean mErr) {
        this.mErr = mErr;
    }

    public boolean ismLastDot() {
        return mLastDot;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }

    public void setmLastDot(boolean mLastDot) {
        this.mLastDot = mLastDot;
    }

    public void setmSecondNum(double mSecondNum) {
        this.mSecondNum = mSecondNum;
        Log.d(NUMERO, "second: " + Double.toString(mSecondNum));
    }

    private String devZeroErr() {
        mLastNumeric = false;
        mIsCounted = false;
        return "Деление на 0";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isLightNightMode() {
        return mLightNightMode;
    }

    public void setLightNightMode(boolean mLightNightMode) {
        this.mLightNightMode = mLightNightMode;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (mLastNumeric ? 1 : 0));
        dest.writeByte((byte) (mLastDot ? 1 : 0));
        dest.writeByte((byte) (mIsCounted ? 1 : 0));
        dest.writeByte((byte) (mErr ? 1 : 0));
        dest.writeDouble(mFirstNum);
        dest.writeDouble(mSecondNum);
        dest.writeDouble(mResult);
        dest.writeString(mText);
        dest.writeInt(mOperation);
        dest.writeByte((byte) (mLightNightMode? 1 : 0));
    }
}

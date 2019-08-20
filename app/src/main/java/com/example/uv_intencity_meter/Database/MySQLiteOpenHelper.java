package com.example.uv_intencity_meter.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "uvDATA";

    public static final String TABLE_M = "measurement";
    public static final String TABLE_A = "analysis";

    public static final String ID = "id";

    //TopBAR
    public static final String KEY_WAVE = "wave";
    public static final String KEY_TEMP = "temperature";
    public static final String KEY_HUMID = "humidity";

    //Measurement
    public static final String KEY_TITLE_M = "mtitle";

    //Analysis9
    public static final String KEY_TITLE_A = "title";
    public static final String KEY_AVG_A = "average";
    public static final String KEY_UNI_A = "uni";
    public static final String KEY_NUM1 = "num1";
    public static final String KEY_NUM2 = "num2";
    public static final String KEY_NUM3 = "num3";
    public static final String KEY_NUM4 = "num4";
    public static final String KEY_NUM5 = "num5";
    public static final String KEY_NUM6 = "num6";
    public static final String KEY_NUM7 = "num7";
    public static final String KEY_NUM8 = "num8";
    public static final String KEY_NUM9 = "num9";

    public static MySQLiteOpenHelper instance;

    public static synchronized MySQLiteOpenHelper getHelper(Context context) {
        if (instance == null)
            instance = new MySQLiteOpenHelper(context);
        return instance;
    }

    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_TABLE_M = "CREATE TABLE "
            + TABLE_M + "(" + ID + " INTEGER PRIMARY KEY, "
            + KEY_TITLE_M + " TEXT, " + KEY_WAVE + " DOUBLE, " + KEY_TEMP + " DOUBLE, " + KEY_HUMID + " DOUBLE, "
            + KEY_NUM1 + " DOUBLE, " + KEY_NUM2 + " DOUBLE, " + KEY_NUM3 + " DOUBLE, " + KEY_NUM4 + " DOUBLE, "
            + KEY_NUM5 + " DOUBLE, " + KEY_AVG_A + " DOUBLE, " + KEY_UNI_A + " DOUBLE  );";

    private static final String CREATE_TABLE_A = "CREATE TABLE "
            + TABLE_A + "(" + ID + " INTEGER PRIMARY KEY, "
            + KEY_TITLE_A + " TEXT, " + KEY_WAVE + " DOUBLE, " + KEY_TEMP + " DOUBLE, " + KEY_HUMID + " DOUBLE, "
            + KEY_NUM1 + " DOUBLE, " + KEY_NUM2 + " DOUBLE, " + KEY_NUM3 + " DOUBLE, " + KEY_NUM4 + " DOUBLE, "
            + KEY_NUM5 + " DOUBLE, " + KEY_NUM6 + " DOUBLE, " + KEY_NUM7 + " DOUBLE, " + KEY_NUM8 + " DOUBLE, "
            + KEY_NUM9 + " DOUBLE, " + KEY_AVG_A + " DOUBLE, " + KEY_UNI_A + " DOUBLE );";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_M);
        db.execSQL(CREATE_TABLE_A);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onOpen(SQLiteDatabase db){super.onOpen(db);}
}

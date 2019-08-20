package com.example.uv_intencity_meter.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import static com.example.uv_intencity_meter.Database.MySQLiteOpenHelper.TABLE_M;

public class ContentDAO extends ContentDBDAO {
    public ContentDAO(Context context){
        super(context);
    }

    public long saveM(ContentPoint5 content) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteOpenHelper.ID, System.currentTimeMillis());
        contentValues.put(MySQLiteOpenHelper.KEY_TITLE_M, content.getTitle());
        contentValues.put(MySQLiteOpenHelper.KEY_WAVE, content.getUvTopbar());
        contentValues.put(MySQLiteOpenHelper.KEY_TEMP, content.getTempTopbar());
        contentValues.put(MySQLiteOpenHelper.KEY_HUMID, content.getHumidTopbar());
        contentValues.put(MySQLiteOpenHelper.KEY_UNI_A, content.getUni());
        contentValues.put(MySQLiteOpenHelper.KEY_AVG_A, content.getAvg());
        contentValues.put(MySQLiteOpenHelper.KEY_NUM1, content.getA1());
        contentValues.put(MySQLiteOpenHelper.KEY_NUM2, content.getA2());
        contentValues.put(MySQLiteOpenHelper.KEY_NUM3, content.getA3());
        contentValues.put(MySQLiteOpenHelper.KEY_NUM4, content.getA4());
        contentValues.put(MySQLiteOpenHelper.KEY_NUM5, content.getA5());

        return database.insert(TABLE_M, null, contentValues);
    }

    public long deleteM(ContentPoint5 content) {
        return database.delete(TABLE_M, MySQLiteOpenHelper.ID + "= " + content.getId(), null);
    }

    public ArrayList<ContentPoint5> getContentPoint5() {
        ArrayList<ContentPoint5> contents = new ArrayList<ContentPoint5>();
        Cursor cursor = database.query(TABLE_M, new String[]{
                        MySQLiteOpenHelper.ID, MySQLiteOpenHelper.KEY_TITLE_M,
                        MySQLiteOpenHelper.KEY_WAVE, MySQLiteOpenHelper.KEY_TEMP, MySQLiteOpenHelper.KEY_HUMID,
                        MySQLiteOpenHelper.KEY_NUM1, MySQLiteOpenHelper.KEY_NUM2, MySQLiteOpenHelper.KEY_NUM3,
                        MySQLiteOpenHelper.KEY_NUM4, MySQLiteOpenHelper.KEY_NUM5,
                        MySQLiteOpenHelper.KEY_AVG_A, MySQLiteOpenHelper.KEY_UNI_A},
                null, null, null, null, "ID DESC");

        while (cursor.moveToNext()) {
            ContentPoint5 content = new ContentPoint5();
            content.setId(cursor.getLong(0));
            content.setTitle(cursor.getString(1));
            content.setUvTopbar(cursor.getDouble(2));
            content.setTempTopbar(cursor.getDouble(3));
            content.setHumidTopbar(cursor.getDouble(4));
            content.setA1(cursor.getDouble(5));
            content.setA2(cursor.getDouble(6));
            content.setA3(cursor.getDouble(7));
            content.setA4(cursor.getDouble(8));
            content.setA5(cursor.getDouble(9));
            content.setAvg(cursor.getDouble(10));
            content.setUni(cursor.getDouble(11));

            contents.add(content);
        }
        return contents;
    }

    public long saveA(ContentPoint9 content) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteOpenHelper.ID, System.currentTimeMillis());
        contentValues.put(MySQLiteOpenHelper.KEY_TITLE_A, content.getTitle());
        contentValues.put(MySQLiteOpenHelper.KEY_WAVE, content.getUvTopbar());
        contentValues.put(MySQLiteOpenHelper.KEY_TEMP, content.getTempTopbar());
        contentValues.put(MySQLiteOpenHelper.KEY_HUMID, content.getHumidTopbar());
        contentValues.put(MySQLiteOpenHelper.KEY_UNI_A, content.getUni());
        contentValues.put(MySQLiteOpenHelper.KEY_AVG_A, content.getAvg());
        contentValues.put(MySQLiteOpenHelper.KEY_NUM1, content.getA1());
        contentValues.put(MySQLiteOpenHelper.KEY_NUM2, content.getA2());
        contentValues.put(MySQLiteOpenHelper.KEY_NUM3, content.getA3());
        contentValues.put(MySQLiteOpenHelper.KEY_NUM4, content.getA4());
        contentValues.put(MySQLiteOpenHelper.KEY_NUM5, content.getA5());
        contentValues.put(MySQLiteOpenHelper.KEY_NUM6, content.getA6());
        contentValues.put(MySQLiteOpenHelper.KEY_NUM7, content.getA7());
        contentValues.put(MySQLiteOpenHelper.KEY_NUM8, content.getA8());
        contentValues.put(MySQLiteOpenHelper.KEY_NUM9, content.getA9());

        Log.d("####","saveA");

        return database.insert(MySQLiteOpenHelper.TABLE_A, null, contentValues);
    }

    public ArrayList<ContentPoint9> getContentPoint9() {
        ArrayList<ContentPoint9> contents = new ArrayList<ContentPoint9>();
        Cursor cursor = database.query(MySQLiteOpenHelper.TABLE_A, new String[]{
                MySQLiteOpenHelper.ID, MySQLiteOpenHelper.KEY_TITLE_A,
                MySQLiteOpenHelper.KEY_WAVE, MySQLiteOpenHelper.KEY_TEMP, MySQLiteOpenHelper.KEY_HUMID,
                MySQLiteOpenHelper.KEY_NUM1, MySQLiteOpenHelper.KEY_NUM2, MySQLiteOpenHelper.KEY_NUM3,
                MySQLiteOpenHelper.KEY_NUM4, MySQLiteOpenHelper.KEY_NUM5, MySQLiteOpenHelper.KEY_NUM6,
                MySQLiteOpenHelper.KEY_NUM7, MySQLiteOpenHelper.KEY_NUM8, MySQLiteOpenHelper.KEY_NUM9,
                MySQLiteOpenHelper.KEY_AVG_A, MySQLiteOpenHelper.KEY_UNI_A}, null, null, null, null, "ID DESC");

        while (cursor.moveToNext()) {
            ContentPoint9 content = new ContentPoint9();
            content.setId(cursor.getLong(0));
            content.setTitle(cursor.getString(1));
            content.setUvTopbar(cursor.getDouble(2));
            content.setTempTopbar(cursor.getDouble(3));
            content.setHumidTopbar(cursor.getDouble(4));
            content.setA1(cursor.getDouble(5));
            content.setA2(cursor.getDouble(6));
            content.setA3(cursor.getDouble(7));
            content.setA4(cursor.getDouble(8));
            content.setA5(cursor.getDouble(9));
            content.setA6(cursor.getDouble(10));
            content.setA7(cursor.getDouble(11));
            content.setA8(cursor.getDouble(12));
            content.setA9(cursor.getDouble(13));
            content.setAvg(cursor.getDouble(14));
            content.setUni(cursor.getDouble(15));

            contents.add(content);
        }
        return contents;
    }

    public long deleteA(ContentPoint9 content) {
        return database.delete(MySQLiteOpenHelper.TABLE_A, MySQLiteOpenHelper.ID + " = " + content.getId(), null);
    }

}

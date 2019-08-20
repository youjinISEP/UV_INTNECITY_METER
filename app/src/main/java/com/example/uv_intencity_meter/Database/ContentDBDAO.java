package com.example.uv_intencity_meter.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ContentDBDAO {
    protected SQLiteDatabase database;
    private MySQLiteOpenHelper openHelper;
    private Context mContext;

    public ContentDBDAO(Context context) {
        this.mContext = context;
        openHelper = MySQLiteOpenHelper.getHelper(mContext);
        open();
    }

    public void open() throws SQLException {
        if(openHelper == null)
            openHelper = MySQLiteOpenHelper.getHelper(mContext);
        database = openHelper.getWritableDatabase();
    }
}

package com.example.dimazatolokin.myapplication.data.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DaoSession {

    private static final String DB_NAME = "book2word.db";
    private static final int DB_VERSION = 1;

    private final SQLiteOpenHelper dbHelper;
    private OperationsDao operationsDao;

    public DaoSession(Context context) {
        dbHelper = new SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(OperationsDao.obtainCreateInstancesQuery());
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
    }

    public OperationsDao getOperationBookDao() {
        if (operationsDao == null) {
            operationsDao = new OperationsDao(dbHelper.getWritableDatabase());
        }
        return operationsDao;
    }
}

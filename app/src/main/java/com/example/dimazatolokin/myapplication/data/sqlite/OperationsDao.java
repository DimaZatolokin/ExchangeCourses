package com.example.dimazatolokin.myapplication.data.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dimazatolokin.myapplication.data.model.OperationDb;

import java.util.ArrayList;
import java.util.List;

public class OperationsDao {

    private static final String TAG = OperationDb.class.getSimpleName();
    private static final String TABLE_NAME = "operations_history_table";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_BASE = "base";
    private static final String COLUMN_TARGET = "target";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_VOLUME = "volume";
    private static final String COLUMN_CHANGE = "change";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    private SQLiteDatabase sqLiteDatabase;

    OperationsDao(SQLiteDatabase database) {
        sqLiteDatabase = database;
    }

    static String obtainCreateInstancesQuery() {
        return "CREATE TABLE IF NOT EXISTS '" + TABLE_NAME + "' ('" + COLUMN_ID + "' INTEGER PRIMARY KEY, '" + COLUMN_BASE + "' TEXT NOT NULL, " +
                "'" + COLUMN_TARGET + "' TEXT NOT NULL, '" + COLUMN_PRICE + "' TEXT NOT NULL, '" + COLUMN_VOLUME + "' TEXT, '" + COLUMN_CHANGE + "' TEXT NOT NULL, " +
                "'" + COLUMN_TIMESTAMP + "' INTEGER NOT NULL );";
    }

    public List<OperationDb> getAllOperations() {
        List<OperationDb> operationDbList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TABLE_NAME, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                final int columnIdIndex = cursor.getColumnIndex(COLUMN_ID);
                final int columnBaseIndex = cursor.getColumnIndex(COLUMN_BASE);
                final int columnTargetIndex = cursor.getColumnIndex(COLUMN_TARGET);
                final int columnPriceIndex = cursor.getColumnIndex(COLUMN_PRICE);
                final int columnVolumeIndex = cursor.getColumnIndex(COLUMN_VOLUME);
                final int columnChangeIndex = cursor.getColumnIndex(COLUMN_CHANGE);
                final int columnTimestampIndex = cursor.getColumnIndex(COLUMN_TIMESTAMP);
                do {
                    OperationDb operationDb = new OperationDb(
                            cursor,
                            columnBaseIndex,
                            columnTargetIndex,
                            columnPriceIndex,
                            columnVolumeIndex,
                            columnChangeIndex,
                            columnTimestampIndex);
                    operationDbList.add(operationDb);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return operationDbList;
    }

    public void save(OperationDb operation) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BASE, operation.getTicker().getBase());
        contentValues.put(COLUMN_TARGET,operation.getTicker().getTarget());
        contentValues.put(COLUMN_PRICE, operation.getTicker().getPrice());
        contentValues.put(COLUMN_VOLUME, operation.getTicker().getVolume());
        contentValues.put(COLUMN_CHANGE, operation.getTicker().getChange());
        contentValues.put(COLUMN_TIMESTAMP, operation.getTimestamp());
        long id = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        Log.i(TAG, "save: " + id);
    }
}

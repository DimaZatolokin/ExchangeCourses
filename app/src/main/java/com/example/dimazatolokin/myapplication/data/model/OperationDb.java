package com.example.dimazatolokin.myapplication.data.model;

import android.database.Cursor;

public class OperationDb {

    private Ticker ticker;
    private long timestamp;

    public OperationDb(Ticker ticker, long timestamp) {
        this.ticker = ticker;
        this.timestamp = timestamp;
    }

    public OperationDb(Cursor cursor, int base, int target, int price, int volume, int change, int timestamp) {
        this(new Ticker(cursor.getString(base),
                        cursor.getString(target),
                        cursor.getString(price),
                        cursor.getString(volume),
                        cursor.getString(volume)),
                        cursor.getLong(timestamp));
    }

    public Ticker getTicker() {
        return ticker;
    }

    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}

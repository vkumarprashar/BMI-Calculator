package com.marathon.bmicalculator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.marathon.bmicalculator.models.BodyMassIndex;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BMICalculator";
    private static final String TABLE_NAME = "History";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_HEIGHT = "height";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_RESULT = "result";
    private static final String COLUMN_DATE_TIME = "datetime";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +"(" + COLUMN_GENDER + " text, " + COLUMN_HEIGHT + " text, " + COLUMN_WEIGHT +" text, " + COLUMN_RESULT +" text, " + COLUMN_DATE_TIME + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String getDatabase() {
        return this.getDatabase();
    }


    public boolean saveHistory(String gender, String height, String weight, String result) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(COLUMN_GENDER, gender);
            contentValues.put(COLUMN_RESULT, result);
            contentValues.put(COLUMN_HEIGHT, height);
            contentValues.put(COLUMN_WEIGHT, weight);
            contentValues.put(COLUMN_DATE_TIME, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));

            database.insert(TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("INSERT", "saveHistory: "+ e.getMessage());
            return false;
        }
        return true;
    }

    public List<BodyMassIndex> fetchData() {
        List<BodyMassIndex> bmi = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        try  {
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String height = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HEIGHT));
                Log.d("HEIGHT", "fetchData: " + height);
                System.out.println("Height = " + height);
                bmi.add(new BodyMassIndex(height,
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEIGHT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_TIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESULT))));
                cursor.moveToNext();
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("FETCH EXCEPTION", "fetchData: " + e.getMessage());
            return null;
        }
        return bmi;
    }
}

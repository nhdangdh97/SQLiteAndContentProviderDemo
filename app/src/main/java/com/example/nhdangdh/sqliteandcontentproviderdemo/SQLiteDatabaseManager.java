package com.example.nhdangdh.sqliteandcontentproviderdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nhdangdh on 7/16/2017.
 */

public class SQLiteDatabaseManager extends SQLiteOpenHelper {

    public SQLiteDatabaseManager(Context context) {
        super(context, ContractStudent.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + ContractStudent.Student.TABLE_NAME + " (" +
                ContractStudent.Student.ID + " integer primary key," +
                ContractStudent.Student.NAME + " TEXT," +
                ContractStudent.Student.AGE + " INTEGER," +
                ContractStudent.Student.GRADE + " TEXT)";
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ContractStudent.Student.TABLE_NAME);
        onCreate(db);
    }
}

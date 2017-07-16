package com.example.nhdangdh.sqliteandcontentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by nhdangdh on 7/16/2017.
 */

public class StudentProvider extends ContentProvider {

    private SQLiteDatabaseManager dbManager;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(ContractStudent.AUTHORITY, ContractStudent.PATH, 1);
        sUriMatcher.addURI(ContractStudent.AUTHORITY, ContractStudent.PATH + "/#", 2);
    }

    @Override
    public boolean onCreate() {
        dbManager = new SQLiteDatabaseManager(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        Cursor mCursor = null;

        switch (sUriMatcher.match(uri)) {
            case 1:
                mCursor = db.query(ContractStudent.Student.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, null);
                break;
            case 2:
                selection = selection + ContractStudent.Student.ID + " = " + uri.getPathSegments();
                mCursor = db.query(ContractStudent.Student.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, null);
                break;
            default:
                Toast.makeText(getContext(), "Invalid content uri", Toast.LENGTH_SHORT).show();
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }

        mCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return mCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case 1:
                return ContractStudent.CONTENT_STUDENT_LIST;
            case 2:
                return ContractStudent.CONTENT_STUDENT_ITEM;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        if(sUriMatcher.match(uri) != 1) {
            throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
        long rowId = db.insert(ContractStudent.Student.TABLE_NAME, null, values);
        if(rowId > 0) {
            Uri articleUri = ContentUris.withAppendedId(ContractStudent.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(articleUri, null);
            return articleUri;
        }
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbManager.getWritableDatabase();

        int count = 0;
        switch(sUriMatcher.match(uri)) {
            case 1:
                count = db.delete(ContractStudent.Student.TABLE_NAME, selection, selectionArgs);
                break;
            case 2:
                String rowId = uri.getPathSegments().get(1);
                count = db.delete(ContractStudent.Student.TABLE_NAME, ContractStudent.Student.ID + " = " + rowId
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbManager.getWritableDatabase();

        int count = 0;
        switch (sUriMatcher.match(uri)){
            case 1:
                count = db.update(ContractStudent.Student.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 2:
                String rowId = uri.getPathSegments().get(1);
                count = db.update(ContractStudent.Student.TABLE_NAME, values, ContractStudent.Student.ID + " = " + rowId +
                        (!TextUtils.isEmpty(selection) ? " AND (" + ")" : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri );
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }
}

package com.example.nhdangdh.sqliteandcontentproviderdemo;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by nhdangdh on 7/16/2017.
 */

public class ContractStudent {

    public static final String AUTHORITY = "com.example.nhdangdh.sqliteandcontentproviderdemo.Student";
    public static final String PATH = "/student";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + PATH);
    public static final String CONTENT_STUDENT_LIST = "vnd.android.cursor.dir/vnd.com.example" +
            ".nhdangdh.sqliteandcontentproviderdemo.student";
    public static final String CONTENT_STUDENT_ITEM = "vnd.android.cursor.item/vnd.com.example." +
            "nhdangdh.sqliteandcontentproviderdemo.student";
    public static final String DATABASE_NAME = "student_db";

    public static class Student implements BaseColumns {
        private Student(){};
        public static final String TABLE_NAME = "student";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String AGE = "age";
        public static final String GRADE = "grade";
    }
}

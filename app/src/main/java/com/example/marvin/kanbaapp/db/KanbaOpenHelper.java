package com.example.marvin.kanbaapp.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Marvin! on 2017/2/22.
 */

public class KanbaOpenHelper extends SQLiteOpenHelper {
    private Context context;
    public KanbaOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static final String CREATE_COMMENT="create table Comment("+"id integer primary key autoincrement,"
            +"comment text)";
    public static final String CREATE_INFO="create table Info("+"id integer primary key autoincrement,"
            +"name text,"
            +"password text,"
            +"picId integer)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COMMENT);
        db.execSQL(CREATE_INFO);
        Toast.makeText(context,"创建成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

package no.android.androidexam

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table bitmapsParent(id integer primary key AUTOINCREMENT, bitmapImage blob)")
        db.execSQL("create table bitmapsChildren(" +
                "id integer primary key AUTOINCREMENT, " +
                "bitmapImage blob,"+
                "parentId integer,  " +
                "FOREIGN KEY(parentId) REFERENCES bitmapsParent(id))"
        )

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table bitmapsParent")
        db.execSQL("drop table bitmapsChildren")
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object{
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "bitmaps.db"
    }
}
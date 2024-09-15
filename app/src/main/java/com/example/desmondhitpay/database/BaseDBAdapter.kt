package com.example.desmondhitpay.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.desmondhitpay.util.ContextObjects

abstract class BaseDBAdapter(
    protected val context: Context,
    private val dbName: String,
    private val createStatement: String
): SQLiteOpenHelper(
    context,
    ContextObjects.DATABASE_NAME,
    null,
    ContextObjects.DATABASE_VERSION
){

    init {
        onCreate(this.writableDatabase)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createStatement)
    }

    fun dropDatabase() {
        writableDatabase.apply {
            execSQL("DROP TABLE IF EXISTS $dbName")
            close()
        }
    }
}
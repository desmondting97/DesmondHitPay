package com.example.desmondhitpay.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.desmondhitpay.model.ToDoItem
import com.example.desmondhitpay.util.ContextObjects


private const val DB_TABLE_TODO_LIST = "ToDoTable"
private const val TODO_COLUMN_ID = "id"
private const val TODO_TITLE = "title"
private const val TODO_DESC = "description"
private const val TODO_TIMESTAMP = "timestamp"

private const val CREATE_TODO_TABLE =
    ("create table if not exists " + DB_TABLE_TODO_LIST + " ( "
            + TODO_COLUMN_ID + " integer primary key autoincrement, "
            + TODO_TITLE + " text not null, "
            + TODO_DESC + " text not null, "
            + TODO_TIMESTAMP + " text not null)")

class ToDoDBAdapter(
    context: Context
) : BaseDBAdapter(
    context,
    DB_TABLE_TODO_LIST,
    CREATE_TODO_TABLE
) {
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            if (newVersion > oldVersion) {
                // Perform migration here if ever
            }
        } catch (e: SQLiteException) {
            Log.e("TODO Database", "SQLite Database Upgrade fail")
        }
    }

    fun addToDoItem(item: ToDoItem): Boolean {
        var insertStatus = -1L
        val db = writableDatabase

        try {
            val cv = ContentValues().apply {
                put(TODO_TITLE, item.title)
                put(TODO_DESC, item.description)
                put(TODO_TIMESTAMP, item.timeStamp)
            }
            insertStatus = db.insertWithOnConflict(
                DB_TABLE_TODO_LIST,
                null,
                cv,
                SQLiteDatabase.CONFLICT_REPLACE
            )
        } catch (ex: Exception) {
            Log.e(ContextObjects.DATABASE_NAME, ex.toString())
        } finally {
            db?.close()
            close()
        }
        return insertStatus > 0
    }

    fun addToDoItems(items: List<ToDoItem>): Boolean {
        var insertStatus = -1L
        val db = writableDatabase

        try {
            items.forEach { item ->
                val cv = ContentValues().apply {
                    put(TODO_TITLE, item.title)
                    put(TODO_DESC, item.description)
                    put(TODO_TIMESTAMP, item.timeStamp)
                }
                insertStatus = db.insertWithOnConflict(
                    DB_TABLE_TODO_LIST,
                    null,
                    cv,
                    SQLiteDatabase.CONFLICT_REPLACE
                )
            }
        } catch (ex: Exception) {
            Log.e(ContextObjects.DATABASE_NAME, ex.toString())
        } finally {
            db?.close()
            close()
        }
        return insertStatus > 0
    }

    fun deleteToDoItem(rowID: String): Boolean {
        var deleteStatus = -1
        val db = writableDatabase

        try {
            deleteStatus = db.delete(DB_TABLE_TODO_LIST, "$TODO_COLUMN_ID = ?", arrayOf(rowID))
        } catch (ex: Exception) {
            Log.e(ContextObjects.DATABASE_NAME, ex.toString())
        } finally {
            db?.close()
            close()
        }

        return deleteStatus > 0
    }

    fun fetchToDoItem(rowID: String): ToDoItem? {
        val db = readableDatabase

        try{
            val cursor = db.rawQuery("SELECT * FROM $DB_TABLE_TODO_LIST WHERE $TODO_COLUMN_ID = '$rowID'", null)
            if (cursor != null) {
                val toDoItem = ToDoItem("",
//                    cursor.getString(cursor.getColumnIndexOrThrow(TODO_COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TODO_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TODO_DESC)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TODO_TIMESTAMP))
                )
                cursor.close()
                db.close()
                return toDoItem
            }
        } catch (ex: Exception) {
            Log.e(ContextObjects.DATABASE_NAME, ex.toString())
        } finally {
            db?.close()
            close()
        }
        return null
    }

    fun fetchAllToDoItems(): ArrayList<ToDoItem> {
        var toDoItemList = ArrayList<ToDoItem>()
        val db = readableDatabase

        try {
            val cursor = db.rawQuery("SELECT * FROM $DB_TABLE_TODO_LIST", null)
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val toDoItem = ToDoItem(
                        cursor.getString(cursor.getColumnIndexOrThrow(TODO_COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TODO_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TODO_DESC)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TODO_TIMESTAMP))
                    )
                    toDoItemList.add(toDoItem)
                }
                cursor.close()
                db.close()
            }
        } catch (ex: Exception) {
            Log.e(ContextObjects.DATABASE_NAME, ex.toString())
        } finally {
            db?.close()
            close()
        }

        return toDoItemList
    }

    fun dropToDoDatabase(): Boolean {
        writableDatabase.apply {
            execSQL("DROP TABLE IF EXISTS $DB_TABLE_TODO_LIST")
            close()
            return true
        }
    }

}

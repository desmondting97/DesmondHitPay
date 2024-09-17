package com.example.desmondhitpay.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.desmondhitpay.model.ToDoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [ToDoItem::class], version = 1, exportSchema = false)
abstract class ToDoListDatabase: RoomDatabase() {

    abstract fun toDoDao(): ToDoItemDao

    companion object {
        @Volatile
        private var INSTANCE: ToDoListDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ToDoListDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoListDatabase::class.java,
                    "toDo_database"
                )
                    .addCallback(ToDoDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class ToDoDatabaseCallback(
            private val scope: CoroutineScope
        ): RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.toDoDao())
                    }
                }
            }

            suspend fun populateDatabase(toDoItemDao: ToDoItemDao) {
                toDoItemDao.deleteAll()

                var toDoItem = ToDoItem(1, "hi", "description", "20240914 0800")
                toDoItemDao.insert(toDoItem)
            }
        }
    }
}
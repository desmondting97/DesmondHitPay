package com.example.desmondhitpay.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.desmondhitpay.model.ToDoItem

@Dao
interface ToDoItemDao {

    @Transaction
    @Query("SELECT * FROM todolist_table")
    fun getAll(): List<ToDoItem>

    @Query("SELECT * FROM todolist_table ORDER BY rowId ASC LIMIT :limit OFFSET :offset")
    suspend fun getPaged(limit: Int, offset: Int): List<ToDoItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(toDoItem: ToDoItem)

    @Delete
    suspend fun delete(toDoItem: ToDoItem)

    @Query("DELETE FROM todolist_table")
    suspend fun deleteAll()
}
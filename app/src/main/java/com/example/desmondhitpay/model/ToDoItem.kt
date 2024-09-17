package com.example.desmondhitpay.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todolist_table")
data class ToDoItem(
    @PrimaryKey(autoGenerate = true) val rowId: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "desc") val desc: String,
    @ColumnInfo(name = "timeStamp") val timeStamp: String
)

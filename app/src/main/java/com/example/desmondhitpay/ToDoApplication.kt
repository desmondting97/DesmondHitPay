package com.example.desmondhitpay

import android.app.Application
import com.example.desmondhitpay.database.ToDoListDatabase
import com.example.desmondhitpay.repository.ToDoListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ToDoApplication: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { ToDoListDatabase.getDatabase(this, applicationScope)}
    val repository by lazy { ToDoListRepository(database.toDoDao()) }
}
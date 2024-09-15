package com.example.desmondhitpay.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.desmondhitpay.model.ToDoItem
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*


@RunWith(AndroidJUnit4::class)
class ToDoDBAdapterTest {

    @Test
    fun addToDoItem() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val toDoDBAdapter = ToDoDBAdapter(appContext)
        val addToDoItem = ToDoItem("1", "title", "desc", "20240912-0800")

        toDoDBAdapter.addToDoItem(addToDoItem)
        val toDoItem = toDoDBAdapter.fetchToDoItem("1")

        assertEquals(addToDoItem, toDoItem)
    }
}
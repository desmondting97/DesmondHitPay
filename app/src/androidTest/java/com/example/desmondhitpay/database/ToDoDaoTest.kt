package com.example.desmondhitpay.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.desmondhitpay.model.ToDoItem
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class ToDoDaoTest {

    private lateinit var database: ToDoListDatabase
    private lateinit var toDoItemDao: ToDoItemDao

    @Before
    fun createDB() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(appContext, ToDoListDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        toDoItemDao = database.toDoDao()
    }

    @Test
    fun addToDoItem() = runBlocking {
        val toDoItem = ToDoItem(0, "title", "desc", "20240915")
        toDoItemDao.insert(toDoItem)
        val allToDoItems = toDoItemDao.getAll()

        assertEquals(allToDoItems[0].title, toDoItem.title)
    }

    @Test
    fun deleteAllToDoItems() = runBlocking {
        val toDoItem = ToDoItem(0, "title", "desc", "20240915")
        val toDoItem2 = ToDoItem(1, "title1", "desc1", "20240916")

        toDoItemDao.insert(toDoItem)
        toDoItemDao.insert(toDoItem2)
        toDoItemDao.deleteAll()

        val toDoItems = toDoItemDao.getAll()
        assertTrue(toDoItems.isEmpty())
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        database.close()
    }
}
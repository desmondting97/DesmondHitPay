package com.example.desmondhitpay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.desmondhitpay.model.ToDoItem
import com.example.desmondhitpay.repository.ToDoListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoListViewModel(private val toDoListRepository: ToDoListRepository): ViewModel() {

    val pagedToDoItems = toDoListRepository.getPagedToDoItems().cachedIn(viewModelScope)

    fun addToDoListItem(toDoItem: ToDoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoListRepository.addToDoItem(toDoItem)
        }
    }

    fun deleteToDoListItem(toDoItem: ToDoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoListRepository.deleteToDoItem(toDoItem)
        }
    }

    fun dropToDoListDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            toDoListRepository.dropToDoDatabase()
        }
    }

    fun prepopulateToDoListDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            toDoListRepository.prePopulateToDoList()
        }
    }
}
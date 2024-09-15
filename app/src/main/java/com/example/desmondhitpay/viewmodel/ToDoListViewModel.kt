package com.example.desmondhitpay.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.desmondhitpay.model.ToDoItem
import com.example.desmondhitpay.repository.ToDoListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoListViewModel(private val toDoListRepository: ToDoListRepository): ViewModel() {

    val getToDoListItems: LiveData<PagingData<ToDoItem>> =
        toDoListRepository.getToDoList().cachedIn(viewModelScope)

    private val toDoListLiveDataInternal = MutableLiveData<List<ToDoItem>>()
    val toDoListLiveData: LiveData<List<ToDoItem>>
        get() = toDoListLiveDataInternal

    fun addToDoListItem(toDoItem: ToDoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            if (toDoListRepository.addToDoItem(toDoItem)) {
                fetchAllToDoItems()
            }
        }
    }

    fun deleteToDoListItem(rowID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (toDoListRepository.deleteToDoItem(rowID)) {
                fetchAllToDoItems()
            }
        }
    }

    fun fetchToDoListItems() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchAllToDoItems()
        }
    }

    fun dropToDoListDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            if (toDoListRepository.dropToDoDatabase()) {
                fetchAllToDoItems()
            }
        }
    }

    fun prepopulateToDoListDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            toDoListRepository.prePopulateToDoList()
            fetchAllToDoItems()
        }
    }

    private fun fetchAllToDoItems() {
        toDoListLiveDataInternal.postValue(toDoListRepository.fetchAllToDoItems())
    }

}
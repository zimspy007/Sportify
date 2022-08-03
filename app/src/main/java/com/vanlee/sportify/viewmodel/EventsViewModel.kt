package com.vanlee.sportify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanlee.sportify.database.objectbox.ObjectBox
import com.vanlee.sportify.database.objectbox.entities.DbEventItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventsViewModel : ViewModel() {
    private val events: MutableLiveData<List<DbEventItem>> by lazy {
        MutableLiveData<List<DbEventItem>>()
    }

    fun loadEvents() {
        viewModelScope.launch() {
            doLoadEvents()
        }
    }

    private suspend fun doLoadEvents() {
        withContext(Dispatchers.IO) {
            val eventsList = ObjectBox.store.boxFor(DbEventItem::class.java).all
            events.postValue(eventsList)
        }
    }

    fun getEvents(): LiveData<List<DbEventItem>> = events

    companion object {
        private val TAG = EventsViewModel::class.java.simpleName
    }
}
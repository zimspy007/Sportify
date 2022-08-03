package com.vanlee.sportify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanlee.sportify.database.objectbox.ObjectBox
import com.vanlee.sportify.database.objectbox.entities.DbScheduleItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SchedulesViewModel : ViewModel() {
    private val events: MutableLiveData<List<DbScheduleItem>> by lazy {
        MutableLiveData<List<DbScheduleItem>>()
    }

    fun loadSchedules() {
        viewModelScope.launch() {
            doLoadSchedules()
        }
    }

    private suspend fun doLoadSchedules() {
        withContext(Dispatchers.IO) {
            val schedulesList = ObjectBox.store.boxFor(DbScheduleItem::class.java).all
            events.postValue(schedulesList)
        }
    }

    fun getSchedules(): LiveData<List<DbScheduleItem>> = events

    companion object {
        private val TAG = SchedulesViewModel::class.java.simpleName
    }
}
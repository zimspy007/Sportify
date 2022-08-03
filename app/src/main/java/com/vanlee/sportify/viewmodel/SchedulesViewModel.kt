package com.vanlee.sportify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanlee.sportify.database.objectbox.ObjectBox
import com.vanlee.sportify.database.objectbox.entities.DbScheduleItem
import com.vanlee.sportify.database.objectbox.entities.DbScheduleItem_
import io.objectbox.android.AndroidScheduler
import io.objectbox.query.Query
import io.objectbox.reactive.DataSubscription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SchedulesViewModel : ViewModel() {
    private val schedules: MutableLiveData<List<DbScheduleItem>> by lazy {
        MutableLiveData<List<DbScheduleItem>>()
    }

    private lateinit var subscription: DataSubscription

    fun loadSchedules() {
        viewModelScope.launch {
            doLoadSchedules()
        }
    }

    private suspend fun doLoadSchedules() {
        withContext(Dispatchers.IO) {
            val schedulesBox = ObjectBox.store.boxFor(DbScheduleItem::class.java)

            val query: Query<DbScheduleItem> =
                schedulesBox.query().order(DbScheduleItem_.rawTime).build()

            subscription = query.subscribe()
                .on(AndroidScheduler.mainThread())
                .observer { data -> schedules.postValue(data) }
        }
    }

    fun getSchedules(): LiveData<List<DbScheduleItem>> = schedules

    override fun onCleared() {
        subscription.cancel()

        super.onCleared()
    }

    companion object {
        val TAG: String = SchedulesViewModel::class.java.simpleName
    }
}
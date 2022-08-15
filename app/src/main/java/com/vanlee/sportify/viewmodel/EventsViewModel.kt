package com.vanlee.sportify.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanlee.sportify.database.objectbox.ObjectBox
import com.vanlee.sportify.database.objectbox.entities.DbEventItem
import com.vanlee.sportify.database.objectbox.entities.DbEventItem_
import io.objectbox.android.AndroidScheduler
import io.objectbox.query.Query
import io.objectbox.reactive.DataSubscription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EventsViewModel : ViewModel() {
    private val events: MutableLiveData<List<DbEventItem>> by lazy {
        MutableLiveData<List<DbEventItem>>()
    }

    private lateinit var subscription: DataSubscription

    fun loadEvents() {
        viewModelScope.launch {
            doLoadEvents()
        }
    }

    private suspend fun doLoadEvents() {
        withContext(Dispatchers.IO) {
            val eventsBox = ObjectBox.store.boxFor(DbEventItem::class.java)

            val query: Query<DbEventItem> = eventsBox.query().order(DbEventItem_.rawTime).build()

            subscription = query.subscribe()
                .on(AndroidScheduler.mainThread())
                .onError {
                    Log.e(SchedulesViewModel.TAG, it.message!!)
                    events.postValue(null)
                }
                .observer { data -> events.postValue(data) }
        }
    }

    fun getEvents(): LiveData<List<DbEventItem>> = events

    override fun onCleared() {
        subscription.cancel()

        super.onCleared()
    }

    companion object {
        private val TAG = EventsViewModel::class.java.simpleName
    }
}
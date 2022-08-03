package com.vanlee.sportify.viewmodel

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

class EventViewModel : ViewModel() {
    private val event: MutableLiveData<DbEventItem> by lazy {
        MutableLiveData<DbEventItem>()
    }

    private lateinit var subscription: DataSubscription

    fun loadEvent(eventId: Long) {
        viewModelScope.launch {
            doLoadEvent(eventId)
        }
    }

    private suspend fun doLoadEvent(eventId: Long) {
        withContext(Dispatchers.IO) {
            val eventsBox = ObjectBox.store.boxFor(DbEventItem::class.java)

            val query: Query<DbEventItem> = eventsBox.query().equal(DbEventItem_.id, eventId).order(DbEventItem_.rawTime).build()

            subscription = query.subscribe()
                .on(AndroidScheduler.mainThread())
                .observer { data -> event.postValue(data.first()) }
        }
    }

    fun getEvent(eventId: Long): LiveData<DbEventItem> = event

    override fun onCleared() {
        subscription.cancel()

        super.onCleared()
    }
}
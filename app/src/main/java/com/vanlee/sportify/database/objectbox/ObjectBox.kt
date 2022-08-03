package com.vanlee.sportify.database.objectbox

import android.content.Context
import com.vanlee.sportify.database.objectbox.entities.MyObjectBox
import io.objectbox.BoxStore

object ObjectBox {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context)
            .build()
    }
}
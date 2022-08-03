package com.vanlee.sportify

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.multidex.MultiDex
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.pranavpandey.android.dynamic.toasts.DynamicToast

class CustomApplication : Application() {

    init {
        instance = this

        initDynamicToast()

        Glide.get(this).setMemoryCategory(MemoryCategory.HIGH)
    }

    private fun initDynamicToast() {
        DynamicToast.Config.getInstance()
            .setDefaultBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorPrimaryDark
                )
            )// Background color for default toast
            .setDefaultTintColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorPrimaryDark
                )
            ) // Tint color for default toast.
            .setErrorBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.red
                )
            ) // Background color for error toast.
            .setSuccessBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorPrimary
                )
            ) // Background color for success toast.
            .setWarningBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.red
                )
            )// Background color for warning toast.
            .setErrorIcon(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_error
                )
            ) // Custom icon for error toast. Pass `null` to use default icon.
            .setSuccessIcon(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_success
                )
            ) // Custom icon for success toast. Pass `null` to use default icon.
            .setWarningIcon(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_warning
                )
            ) // Custom icon for warning toast. Pass `null` to use default icon.
            .setToastBackground(null) // Custom background drawable for all the toasts. Pass `null` to use default background.
            .apply()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        private var instance: CustomApplication? = null

        fun applicationContext(): CustomApplication {
            return instance as CustomApplication
        }
    }
}
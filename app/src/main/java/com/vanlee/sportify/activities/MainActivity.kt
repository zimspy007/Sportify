package com.vanlee.sportify.activities

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.vanlee.sportify.CustomApplication
import com.vanlee.sportify.R
import com.vanlee.sportify.databinding.ActivityMainBinding
import com.vanlee.sportify.entities.HttpResponse
import com.vanlee.sportify.fragments.keys.BaseKey
import com.vanlee.sportify.fragments.keys.EventsFragmentKey
import com.vanlee.sportify.fragments.keys.ScheduleFragmentKey
import com.vanlee.sportify.network.NetworkRequests
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.SimpleStateChanger
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentStateChanger
import com.zhuinden.simplestackextensions.navigatorktx.backstack
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import okhttp3.OkHttpClient


class MainActivity : AppCompatActivity(), SimpleStateChanger.NavigationHandler {

    private lateinit var fragmentStateChanger: DefaultFragmentStateChanger

    private lateinit var binding: ActivityMainBinding

    private lateinit var eventsHttpResponse: HttpResponse

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val app = application as CustomApplication
        val globalServices = app.globalServices

        binding.navigation.setOnItemSelectedListener { item ->
            val destination = when (item.itemId) {
                R.id.navigation_events -> EventsFragmentKey()
                R.id.navigation_schedule -> ScheduleFragmentKey()
                else -> null
            }

            destination?.let { key ->
                backstack.replaceTop(key, StateChange.FORWARD)
                true
            } ?: false
        }

        fragmentStateChanger = DefaultFragmentStateChanger(supportFragmentManager, R.id.container)

        Navigator.configure()
            .setStateChanger(SimpleStateChanger(this))
            .setGlobalServices(globalServices)
            .setScopedServices(DefaultServiceProvider())
            .install(this, binding.container, History.single(EventsFragmentKey()))

        // Fetch data from server
        Thread {
            eventsHttpResponse = NetworkRequests.getEvents(client)
            runOnUiThread { }
        }.start()

    }

    override fun onBackPressed() {
        if (!backstack.goBack()) {
            super.onBackPressed()
        }
    }

    override fun onNavigationEvent(stateChange: StateChange) {
        val topNewKey: BaseKey = stateChange.topNewKey()

        // Hide the menu if you are not in the top fragments
        if (topNewKey is EventsFragmentKey || topNewKey is ScheduleFragmentKey) {
            binding.navigation.visibility = View.VISIBLE
        } else {
            binding.navigation.visibility = View.GONE
        }

        fragmentStateChanger.handleStateChange(stateChange)
    }
}
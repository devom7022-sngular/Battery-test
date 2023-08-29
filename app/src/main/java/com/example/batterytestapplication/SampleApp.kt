package com.example.batterytestapplication

import android.app.Application
import android.content.IntentFilter
import com.example.batterytestapplication.location.ACTION_PROCESS_UPDATES
import com.example.batterytestapplication.location.LocationUpdatesBroadcastReceiver

class SampleApp : Application() {

    companion object {
        private var instance: SampleApp? = null

        @JvmStatic
        fun getInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        val locationIntentFilter = IntentFilter().apply {
            addAction(ACTION_PROCESS_UPDATES)
        }
        registerReceiver(LocationUpdatesBroadcastReceiver(), locationIntentFilter)
    }
}
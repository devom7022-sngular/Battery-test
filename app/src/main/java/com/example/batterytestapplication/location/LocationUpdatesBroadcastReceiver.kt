package com.example.batterytestapplication.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.gms.location.LocationResult

private const val TAG = ">>>>LocationBroadcast"

const val ACTION_PROCESS_UPDATES = "com.example.batterytestapplication.location.ACTION_PROCESS_UPDATES"
class LocationUpdatesBroadcastReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (ACTION_PROCESS_UPDATES == action) {
                val result = LocationResult.extractResult(intent)
                if (result != null) {
                    val locations = result.locations
                    val locationResultHelper = LocationResultHelper(
                        context, locations)
                    // Save the location data to SharedPreferences.
                    locationResultHelper.saveResults()
                    // Show notification with the location data.
                    locationResultHelper.showNotification()
                    Log.i(TAG, LocationResultHelper.getSavedLocationResult(context))
                }
            }
        }
    }
}
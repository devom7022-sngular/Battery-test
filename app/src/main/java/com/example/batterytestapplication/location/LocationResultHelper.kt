package com.example.batterytestapplication.location

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.preference.PreferenceManager
import androidx.annotation.RequiresApi
import com.example.batterytestapplication.MainActivity
import com.example.batterytestapplication.R
import java.lang.StringBuilder
import java.text.DateFormat
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
internal class LocationResultHelper(
    private val mContext: Context,
    private val mLocations: List<Location>
) {

    private val notificationManager: NotificationManager by lazy {
        mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    /**
     * Returns the title for reporting aboit a list of [Location] objects.
     */
    private val locationResultTitle: String
        get() {
            val numLocationsReported = mContext
                .resources
                .getQuantityString(
                    R.plurals.num_locations_reported,
                    mLocations.size,
                    mLocations.size
                )
            return numLocationsReported + ": " + DateFormat.getDateTimeInstance().format(Date())
        }

    private val locationResultText: String
        get() {
            if (mLocations.isEmpty()) {
                return mContext.getString(R.string.unknown_location)
            }
            val sb = StringBuilder()
            for (location in mLocations) {
                sb.append("(")
                sb.append(location.latitude)
                sb.append(", ")
                sb.append(location.longitude)
                sb.append(")")
                sb.append("\n")
            }
            return sb.toString()
        }

    init {
        val channel = NotificationChannel(
            PRIMARY_CHANNEL,
            mContext.getString(R.string.default_channel),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.lightColor = Color.GREEN
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        notificationManager.createNotificationChannel(channel)
    }

    /**
     * Saves location result as a string to [android.content.SharedPreferences].
     */
    fun saveResults() {
        PreferenceManager.getDefaultSharedPreferences(mContext)
            .edit()
            .putString(KEY_LOCATION_UPDATES_RESULT, locationResultTitle + "\n" +
                locationResultText)
            .apply()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotification() {
        val notificationIntent = Intent(mContext, MainActivity::class.java)

        // Construct a task stack.
        val stackBuilder = TaskStackBuilder.create(mContext)

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(MainActivity::class.java)

        // push the content Intent onto rhe stack.
        stackBuilder.addNextIntent(notificationIntent)

        // Get a PendingIntent containing the entire back stack.
        val notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationBuilder = Notification
            .Builder(mContext, PRIMARY_CHANNEL)
            .setContentTitle(locationResultTitle)
            .setContentText(locationResultText)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setContentIntent(notificationPendingIntent)

        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {
        const val KEY_LOCATION_UPDATES_RESULT = "location-update-result"

        private const val PRIMARY_CHANNEL = "default"

        fun getSavedLocationResult(context: Context): String {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY_LOCATION_UPDATES_RESULT, "") ?: ""
        }
    }

}
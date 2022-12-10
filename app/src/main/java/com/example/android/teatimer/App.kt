package com.example.android.teatimer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.android.teatimer.Constants.CHANNEL_ID

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        CreateNotificationChannel()

    }

    fun CreateNotificationChannel() {

        if (Build.VERSION.SDK_INT >Build.VERSION_CODES.O) {

            val channel = NotificationChannel(CHANNEL_ID, "Tea Timer", NotificationManager.IMPORTANCE_DEFAULT)

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

}
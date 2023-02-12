package com.example.android.teatimer.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.android.teatimer.AfterDoneActivity
import com.example.android.teatimer.Constants
import com.example.android.teatimer.Constants.CHANNEL_ID
import com.example.android.teatimer.R
import kotlinx.coroutines.*

class MyService : Service() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private var isOn = false
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isOn) {
            val minutes = intent!!.getIntExtra(Constants.MINUTES, 0)
            val seconds = intent.getIntExtra(Constants.SECONDS, 0)

            scope.launch {
                startTimer(minutes, seconds)
            }
            isOn=true
        }
        return START_STICKY
    }

    private suspend fun startTimer(minutes: Int, seconds: Int) {
        var thisMinutes = minutes
        var thisSeconds = seconds
        // timer
        while (thisMinutes >= 0) {
            while (thisSeconds > 0) {
                Constants.minutes = thisMinutes
                Constants.seconds = thisSeconds
                createNotification(
                    "$thisMinutes M , $thisSeconds S",
                    false,
                    thisMinutes,
                    thisSeconds
                )
                delay(1000)
                thisSeconds--

                if (thisMinutes == 0 && thisSeconds == 0) {
                    createNotification("Tea is ready ya sayed", true, thisMinutes, thisSeconds)
                    isOn=false
                }
            }
            thisMinutes--
        }
    }

    private fun createNotification(time: String, teaIsReady: Boolean, minutes: Int, seconds: Int) {

        val intent = Intent(this, AfterDoneActivity::class.java)

        if (teaIsReady)
            intent.putExtra(Constants.IS_TEAE_RADY, true)
        else
            intent.putExtra(Constants.IS_TEAE_RADY, false)

        intent.putExtra(Constants.MINUTES, minutes)
        intent.putExtra(Constants.SECONDS, seconds)

        val pendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT
            )
        }
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Tea Timer")
            .setContentText(time)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_baseline_access_time_filled_24)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(1, notification.build())
        startForeground(1, notification.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}
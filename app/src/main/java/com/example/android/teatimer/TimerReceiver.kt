package com.example.android.teatimer

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class TimerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val intent = Intent(context, TimerIsFinished::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK )
        // | Intent.FLAG_ACTIVITY_NEW_TASK

        var pendingIntent : PendingIntent = PendingIntent.getActivity(context!!,0,intent,0)


        var builder : NotificationCompat.Builder = NotificationCompat.Builder(context!!, "timer").apply {
            setSmallIcon(R.drawable.ic_baseline_access_time_filled_24)
            setContentTitle("Timer")
            setContentText("Your tea is ready")
            setAutoCancel(true)
            setDefaults(NotificationCompat.DEFAULT_ALL)
            setPriority(NotificationCompat.PRIORITY_HIGH)
            setContentIntent(pendingIntent)
        }

        var notificationManagerCompat : NotificationManagerCompat = NotificationManagerCompat.from(
            context!!
        )
        notificationManagerCompat.notify(123,builder.build())
    }
}
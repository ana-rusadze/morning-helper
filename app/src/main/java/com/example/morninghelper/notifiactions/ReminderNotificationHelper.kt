package com.example.morninghelper.notifiactions

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.morninghelper.R
import com.example.morninghelper.application.App
import com.example.morninghelper.ui.dashboard_activity.fragments.notes.NotesFragment

class ReminderNotificationHelper{

private val channelId = App.instance.getContext().getString(R.string.reminder)

 fun createNotificationChannel(
    showBadge: Boolean,
    name: String,
    description: String
) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance)
        channel.description = description
        channel.setShowBadge(showBadge)
        val notificationManager = App.instance.getContext().getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(1, NotificationBuilder().build())
    }

}


private fun pendingIntent() = PendingIntent.getActivity(
    App.instance.getContext(),
    0,
    Intent(App.instance.getContext(), NotesFragment::class.java),
    PendingIntent.FLAG_UPDATE_CURRENT
)


private fun NotificationBuilder(): NotificationCompat.Builder =
    NotificationCompat.Builder(App.instance.getContext(), channelId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("title")
        .setContentText("description")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent())
        .setAutoCancel(true)


}
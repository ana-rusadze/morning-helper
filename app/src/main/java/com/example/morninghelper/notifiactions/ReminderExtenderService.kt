package com.example.morninghelper.notifiactions

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.morninghelper.R
import com.example.morninghelper.ui.dashboard_activity.fragments.notes.NotesFragment
//import com.onesignal.NotificationExtenderService
//import com.onesignal.OSNotificationReceivedResult

//
//class ReminderExtenderService : NotificationExtenderService() {
//    private var actionId = 0
//    private lateinit var title: String
//    private lateinit var description: String
//    override fun onNotificationProcessing(receivedResult: OSNotificationReceivedResult): Boolean {
//        val json = receivedResult.payload.additionalData
//        actionId = json.getInt("action_id")
//        title = json.getString("title")
//        description = json.getString("description")
//        createNotificationChannel()
//        return false
//    }
//
//
//    private fun createNotificationChannel() {
//        val notificationManager: NotificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = getString(R.string.)
//            val descriptionText = getString(R.string.)
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(actionId.toString(), name, importance).apply {
//                description = descriptionText
//            }
////
////            ExampleNotificationOpenedHandler.createNotificationChannel(this,
////                NotificationManagerCompat.IMPORTANCE_DEFAULT, false,
////                getString(R.string.app_name), "App notification channel.")
////            notificationManager.createNotificationChannel(channel)
//            notificationManager.notify(1, builder.build())
//        }
//    }
//
//
//    private val intent = Intent(applicationContext, NotesFragment::class.java)
//    private val pendingIntent: PendingIntent =
//        PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//
//    private val builder = NotificationCompat.Builder(this, actionId.toString())
//        .setSmallIcon(R.mipmap.ic_launcher)
//        .setContentTitle(title)
//        .setContentText(description)
//        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//        .setContentIntent(pendingIntent)
//        .setAutoCancel(true)
//
//
//}
package com.example.morninghelper.application

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
//import com.example.morninghelper.notifiactions.ExampleNotificationOpenedHandler
import com.example.morninghelper.room.AppDatabase
//import com.onesignal.OneSignal

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
//        OneSignal.startInit(this).setNotificationOpenedHandler(ExampleNotificationOpenedHandler())
//            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//            .unsubscribeWhenNotificationsAreDisabled(true)
//            .init()
//
//

    }

    companion object {
        lateinit var instance: App
        private lateinit var context: Context

    }

    fun getContext() =
        context

}
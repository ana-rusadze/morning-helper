package com.example.morninghelper.application

import android.app.Application
import android.content.Context

import com.example.morninghelper.R
import com.example.morninghelper.notifiactions.ReminderNotificationHelper


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
        ReminderNotificationHelper().createNotificationChannel(
            true,
          instance.getString(R.string.reminder),
            "description"
        )
    }

    companion object {
        lateinit var instance: App
        private lateinit var context: Context

    }

    fun getContext() =
        context

}
package com.example.morninghelper.shared_preferences

import android.content.Context
import com.example.morninghelper.application.App
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object AppSharedPreferences{

    const val FIRST_OPEN = "FIRST_OPEN"
    const val USER_NAME = "USER_NAME"

    private val preference by lazy {
        App.instance.getContext().getSharedPreferences("USER", Context.MODE_PRIVATE)
    }
    private val editor by lazy {
        preference.edit()
    }

    fun saveString(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    fun getString(key: String) = preference.getString(key, "")

    fun removeString(key: String) {
        if (preference.contains(key))
            editor.remove(key)
        editor.commit()
    }

    fun clearAll() {
        editor.clear()
        editor.commit()
    }

    fun <T> setList(key: String?, list: List<T>?) {
        val gson = Gson()
        val json = gson.toJson(list)
        set(key, json)
    }

    operator fun set(key: String?, value: String?) {
        editor.putString(key, value)
        editor.commit()
    }




}
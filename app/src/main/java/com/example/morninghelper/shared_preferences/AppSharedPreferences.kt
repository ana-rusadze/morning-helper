package com.example.morninghelper.shared_preferences

import android.content.Context
import com.example.morninghelper.application.App

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

}
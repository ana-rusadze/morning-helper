package com.example.morninghelper.ui.dashboard_activity.fragments.habits_tracker

import android.os.Parcelable
import com.example.morninghelper.room.Notes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReminderModelClass(
    val date: String? = null,
    val time: String? = null,
    var repeat: String = "daily",
    val note: Notes? = null
):Parcelable


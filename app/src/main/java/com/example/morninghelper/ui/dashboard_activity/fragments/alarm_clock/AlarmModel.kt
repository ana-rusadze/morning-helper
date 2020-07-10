package com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class AlarmModel(
    val alarmTime: String,
    val repeat: String,
    val label: String?,
    val ringtone: Uri,
    val dismissWith: String,
    val snoozeTime: String?,
    val number: String?,
    val message: String?,
    val missedAlarms: String?,
    val switchOn: Boolean
) : Parcelable

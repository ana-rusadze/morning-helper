package com.example.morninghelper.room.alarms

import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "alarms")
@Parcelize
data class Alarms(
    @PrimaryKey(autoGenerate = true) val aid: Int = 0,
    @ColumnInfo(name ="alarmTime") var alarmTime: String? = "",
    @ColumnInfo(name = "repeat") var repeat: String? = "",
    @ColumnInfo(name = "label")  var label: String? = "",
    @ColumnInfo(name = "ringtone") var ringtone: String? = null,
    @ColumnInfo(name = "dismissWith")  var dismissWith: String? = null,
    @ColumnInfo(name = "snoozeTime")  var snoozeTime: String? = null,
    @ColumnInfo(name = "number")   var number: String? = null,
    @ColumnInfo(name = "message")   var message: String? = null,
    @ColumnInfo(name = "missedAlarms")  var missedAlarms: String? = null,
    @ColumnInfo(name = "switchOn") var switchOn: Int? = 0
):Parcelable
package com.example.morninghelper.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class Notes(
    @PrimaryKey(autoGenerate = true) val nid: Int = 0,
    @ColumnInfo(name = "color") var color: Int? = 0,
    @ColumnInfo(name = "title") var title: String? = "",
    @ColumnInfo(name = "description") var description: String? = ""

) : Parcelable
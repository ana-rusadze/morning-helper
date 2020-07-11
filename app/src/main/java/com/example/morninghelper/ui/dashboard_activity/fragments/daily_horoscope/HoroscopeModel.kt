package com.example.morninghelper.ui.dashboard_activity.fragments.daily_horoscope

import com.google.gson.annotations.SerializedName


class HoroscopeModel {
    var result: ResultModel? = null

    class ResultModel {
        val date: String? = null
        val description: String? = null
    }

    var sign: SignModel? = null

    class SignModel {
        val alias: String? = null
        val birthday: String? = null
        val name: String? = null
    }

    val status: Boolean? = null
    val type: String? = null

}

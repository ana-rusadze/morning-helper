package com.example.morninghelper.ui.dashboard_activity.fragments.daily_horoscope

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.morninghelper.networking.EndPoints
import com.example.morninghelper.networking.HoroscopeCallback
import com.example.morninghelper.networking.HoroscopeDataLoader
import com.google.gson.Gson

class HoroscopeViewModel : ViewModel() {

    private val _horoscopeLiveData = MutableLiveData<HoroscopeModel>().apply{
//        getDailyHoroscope()
    }


    val horoscopeLiveData:LiveData<HoroscopeModel> = _horoscopeLiveData



}
package com.example.morninghelper.ui.dashboard_activity.fragments.daily_horoscope

import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.example.morninghelper.R
import com.example.morninghelper.application.App
import com.example.morninghelper.networking.EndPoints
import com.example.morninghelper.networking.HoroscopeCallback
import com.example.morninghelper.networking.HoroscopeDataLoader
import com.example.morninghelper.tools.setViewVisibility
import com.example.morninghelper.ui.BaseFragment
import com.example.morninghelper.ui.dashboard_activity.DashboardActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.horoscope_fragment.view.*
import kotlinx.android.synthetic.main.loader_layout.view.*
import java.util.*
import kotlin.reflect.typeOf

class HoroscopeFragment : BaseFragment() {

    companion object {
        fun newInstance() = HoroscopeFragment()
    }

//    private lateinit var horoscopeViewModel: HoroscopeViewModel

    override fun getLayoutResource(): Int = R.layout.horoscope_fragment

    override fun start(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        getHoroscope()
        rootView!!.readHoroscopeBT.setOnClickListener{getHoroscope()}
    }

    private fun getHoroscope() {
        val sign = rootView!!.searchSignET.text.toString()
        val parameters = mutableMapOf<String, String>()
        if (sign.isNotEmpty()) {
            parameters["sign"] = "taurus"
            parameters["date"] = "2020-05-02"
            HoroscopeDataLoader.getRequest(
                rootView!!.spinKitLoaderView,
                EndPoints.DAILY,
                parameters,
                object : HoroscopeCallback {
                    override fun onSuccess(response: String) {
                        d("horoscope", response)
                        val json = Gson().fromJson(response, HoroscopeModel::class.java)
                        rootView!!.horoscopeDescriptionTV.setViewVisibility(true)
                        rootView!!.horoscopeDescriptionTV.text = json.result!!.description
                    }

                    override fun onError(error: String, body: String) {

                    }
                })
        } else
            Toast.makeText(activity, "please tell us your horoscope sign yet", Toast.LENGTH_SHORT)
                .show()
    }




}
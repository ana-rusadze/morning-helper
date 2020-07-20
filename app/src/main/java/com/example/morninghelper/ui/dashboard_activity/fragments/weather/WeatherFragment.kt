package com.example.morninghelper.ui.dashboard_activity.fragments.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.morninghelper.R
import com.example.morninghelper.networking.EndPoints
import com.example.morninghelper.networking.ApiCallback
import com.example.morninghelper.networking.HoroscopeDataLoader
import com.example.morninghelper.networking.WeatherData
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.tools.extensions.setViewVisibility
import com.example.morninghelper.ui.BaseFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.weather_fragment.*
import kotlinx.android.synthetic.main.weather_fragment.view.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class WeatherFragment : BaseFragment() {

    private var weather = ArrayList<WeatherModel>()
    private lateinit var adapter: WeatherRecyclerViewAdapter


    override fun getLayoutResource() = R.layout.weather_fragment
    override fun start(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        init()
    }

    private fun init(){
        adapter = WeatherRecyclerViewAdapter(weather)
        rootView!!.weatherRecyclerView.layoutManager = GridLayoutManager(activity, 3)
        rootView!!.weatherRecyclerView.adapter = adapter
        getCurrentWeather("tbilisi")

        rootView!!.searchLocation.setOnClickListener {
            val location = changeLocationEditText.text.toString()
            if(location.isEmpty()){
                Toast.makeText(rootView!!.context, "the field must not be empty", Toast.LENGTH_SHORT).show()
            }else{
                getCurrentWeather(location)
                rootView!!.locationWeather.text = location
            }
        }

    }

    private fun getCurrentWeather(location: String) {
        weather.clear()
        val parameters = mutableMapOf<String, String>()
        parameters["appid"] = WeatherData.WEATHER_KEY
        parameters["q"] = location
        WeatherData.getRequest(EndPoints.CURRENT_WEATHER, parameters, object : ApiCallback {
            override fun onError(error: String, body: String) {
            }
            override fun onSuccess(response: String) {
                if(response == "null"){
                    rootView!!.weatherRecyclerView.setViewVisibility(false)
                    Tools.animation(rootView!!.context, R.anim.fade_in, rootView!!.locationErrorLayout)
                    rootView!!.locationErrorLayout.setViewVisibility(true)
                }else{
                    rootView!!.locationErrorLayout.setViewVisibility(false)
                    rootView!!.weatherRecyclerView.setViewVisibility(true)
                    parseJSON(response)
                }

            }
        })

    }


    @SuppressLint("SetTextI18n")
    private fun parseJSON(response: String) {
        val json = JSONObject(response)
        if (json.has("weather")) {
            val value = json.getJSONArray("weather")
            (0 until value.length()).forEach {
                val obj = value.getJSONObject(it)
                val desc = obj.getString("description")
                val main = obj.getString("main")
                Tools.weatherAnimation(rootView!!.weatherAnim, main)
                rootView!!.descriptionWeather.text = desc

            }
        }

        if (json.has("sys")) {
            val value = json.getJSONObject("sys")
            val sunrise = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(value.getLong("sunrise")*1000))
            val sunset = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(value.getLong("sunset")*1000))

            weather.add(WeatherModel(R.drawable.weather_sunrise, "sunrise", sunrise.toString()))
            weather.add(WeatherModel(R.drawable.weather_sunset, "sunset", sunset.toString()))
        }



        if (json.has("wind")) {
            val value = json.getJSONObject("wind")
            val speed = value.getString("speed")
            weather.add(WeatherModel(R.drawable.weather_wind, "wind", "$speed m/s"))
        }


        if (json.has("main")) {
            val value = json.getJSONObject("main")
            val temp = value.getString("temp")
            val tempInC = (temp.toFloat() - 273.15).roundToInt()
            rootView!!.temperature.text = "$tempInC°C"

            val feelsLike = (value.getInt("feels_like").toFloat() - 273.15).roundToInt()
            val pressure = value.getInt("pressure")
            val humidity = value.getInt("humidity")

            weather.add(WeatherModel(R.drawable.weather_pressure, "pressure", "$pressure hPa"))
            weather.add(WeatherModel(R.drawable.weather_humidity, "humidity", "$humidity%"))
            weather.add(WeatherModel(R.drawable.weather_temperature, "feels like", "$feelsLike°C"))
        }

        adapter.notifyDataSetChanged()

    }


}
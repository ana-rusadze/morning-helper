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
import com.example.morninghelper.networking.HoroscopeCallback
import com.example.morninghelper.networking.WeatherData
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.ui.BaseFragment
import kotlinx.android.synthetic.main.activity_home.*
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

    }

    private fun getCurrentWeather(location: String) {

        val parameters = mutableMapOf<String, String>()
        parameters["appid"] = WeatherData.WEATHER_KEY
        parameters["q"] = location
        WeatherData.getRequest(EndPoints.CURRENT_WEATHER, parameters, object : HoroscopeCallback {
            override fun onError(error: String, body: String) {
                Toast.makeText(
                    rootView!!.context,
                    body,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onSuccess(response: String) {
                parseJSON(response)
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

            weather.add(WeatherModel(R.drawable.sunrise, "sunrise", sunrise.toString()))
            weather.add(WeatherModel(R.drawable.sunset, "sunset", sunset.toString()))
        }



        if (json.has("wind")) {
            val value = json.getJSONObject("wind")
            val speed = value.getString("speed")
            weather.add(WeatherModel(R.drawable.wind, "wind", speed))
        }


        if (json.has("main")) {
            val value = json.getJSONObject("main")
            val temp = value.getString("temp")
            val tempInC = (temp.toFloat() - 273.15).roundToInt()
            rootView!!.temperature.text = "$tempInC°C"

            val feelsLike = value.getInt("feels_like")
            val pressure = value.getInt("pressure")
            val humidity = value.getInt("humidity")

            weather.add(WeatherModel(R.drawable.pressure, "pressure", pressure.toString()))
            weather.add(WeatherModel(R.drawable.humidity, "humidity", humidity.toString()))
            weather.add(WeatherModel(R.drawable.temperature, "feels like", feelsLike.toString()))
        }

        adapter.notifyDataSetChanged()

    }


}
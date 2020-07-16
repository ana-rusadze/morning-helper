package com.example.morninghelper.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.morninghelper.R
import com.example.morninghelper.networking.EndPoints
import com.example.morninghelper.networking.HoroscopeCallback
import com.example.morninghelper.networking.WeatherData
import com.example.morninghelper.shared_preferences.AppSharedPreferences
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.ui.dashboard_activity.DashboardActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONObject
import kotlin.math.roundToInt


class HomeActivity : AppCompatActivity() {

    var y2: Float = 0.0f
    var y1: Float = 0.0f

    companion object {
        const val MIN_DISTANCE = 150
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()

    }


    private fun init() {
        helloTextView.text =
            "Hello, ${AppSharedPreferences.getString(AppSharedPreferences.USER_NAME)}!"
        animations()
        getCurrentWeather()
    }

    private fun animations() {
        Tools.animation(this, R.anim.fade_in_slow, helloTextView)
        Tools.animation(this, R.anim.fade_in_slow, goodDayTextView)
        Tools.animation(this, R.anim.slide_up, whiteLayout)
        Tools.animation(this, R.anim.blink, swipeToSeeMore)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.actionMasked) {
            0 -> {
                y1 = event.y
            }
            1 -> {
                y2 = event.y
                val valueY: Float = y2 - y1

                if (kotlin.math.abs(valueY) > MIN_DISTANCE && y2 < y1) {
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down)
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun getCurrentWeather() {

        val parameters = mutableMapOf<String, String>()
        parameters["appid"] = WeatherData.WEATHER_KEY
        parameters["q"] = "tbilisi"
        WeatherData.getRequest(EndPoints.CURRENT_WEATHER, parameters, object : HoroscopeCallback {
            override fun onError(error: String, body: String) {
                Toast.makeText(
                    applicationContext,
                    body,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onSuccess(response: String) {
                d("response", response)
                parseJSON(response)
            }
        })

    }


    @SuppressLint("SetTextI18n")
    private fun parseJSON(response: String) {
        val json = JSONObject(response)
        if (json.has("weather")) {
            val value = json.getJSONArray("weather")
            d("weather", value.toString())
            (0 until value.length()).forEach {
                val obj = value.getJSONObject(it)
                val desc = obj.getString("description")
                val main = obj.getString("main")
                Tools.weatherAnimation(weatherAnim, main)
                tempDescription.text = desc
            }

        }

        if (json.has("main")) {
            val value = json.getJSONObject("main")
            val temp = value.getString("temp")
            val tempInC = (temp.toFloat() - 273.15).roundToInt()
            tempTextView.text = "$tempInC°C"
        }
    }




}






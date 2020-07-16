package com.example.morninghelper.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


import com.example.morninghelper.networking.EndPoints
import com.example.morninghelper.networking.HoroscopeCallback
import com.example.morninghelper.networking.WeatherData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.morninghelper.R
import com.example.morninghelper.application.App
import com.example.morninghelper.room.AppDatabase
import com.example.morninghelper.shared_preferences.AppSharedPreferences
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.ui.dashboard_activity.DashboardActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONObject
import kotlin.math.roundToInt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeActivity : AppCompatActivity(), View.OnClickListener {

    var y2: Float = 0.0f
    var y1: Float = 0.0f

    companion object {
        const val MIN_DISTANCE = 150
    }

    private val db by lazy {
        Room.databaseBuilder(
            App.instance.getContext(),
            AppDatabase::class.java, "database-name"
        ).build()

    }

    private var latestNotesList = ArrayList<String>()
    private lateinit var latestNotesRecyclerView: LatestNotesRecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
        CoroutineScope(Dispatchers.Main).launch { getLatestNotes() }
        initRecyclerView()


    }


    private fun init() {
        alarmsTV.setOnClickListener(this)
        newsTV.setOnClickListener(this)
        horoscopeTV.setOnClickListener(this)
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

    private fun onClickItem(position: Int) {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.putExtra("currentPosition", position)
        startActivity(intent)
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.alarmsTV -> onClickItem(0)
            R.id.newsTV -> onClickItem(1)
            R.id.weatherCardView ->onClickItem(2)
            R.id.horoscopeTV -> onClickItem(3)
            R.id.notesCardView -> onClickItem(4)

        }

    }

    private fun initRecyclerView() {
        latestNoteRecyclerView.layoutManager = LinearLayoutManager(this)
        latestNotesRecyclerView = LatestNotesRecyclerView(latestNotesList)
        latestNoteRecyclerView.adapter = latestNotesRecyclerView
        latestNotesRecyclerView.notifyDataSetChanged()

    }


    private suspend fun getLatestNotes() {
        val notes = db.notesDao().getAll()
        if (notes.isNotEmpty()) {
            val latestNotes = notes.take(5)
            for (i in latestNotes)
                latestNotesList.add(i.title!!)
            latestNotesRecyclerView.notifyDataSetChanged()
        }


    }
}







package com.example.morninghelper.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MotionEventCompat
import com.example.morninghelper.R
import com.example.morninghelper.shared_preferences.AppSharedPreferences
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.ui.dashboard_activity.DashboardActivity
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    var y2: Float = 0.0f
    var y1: Float = 0.0f

    companion object {
        const val MIN_DISTANCE = 150
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }


    private fun init() {
        helloTextView.text =
            "Hello, ${AppSharedPreferences.getString(AppSharedPreferences.USER_NAME)}!"
        animations()
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
}



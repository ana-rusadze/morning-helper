package com.example.morninghelper.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.util.Log.d
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.MotionEventCompat
import com.example.morninghelper.R
import com.example.morninghelper.application.App
import com.example.morninghelper.shared_preferences.AppSharedPreferences
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.ui.dashboard_activity.DashboardActivity
import kotlinx.android.synthetic.main.activity_home.*
import java.util.jar.Manifest

private const val DEBUG_TAG = "Gestures"

class HomeActivity : AppCompatActivity(), GestureDetector.OnGestureListener {

    private lateinit var mDetector: GestureDetectorCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mDetector = GestureDetectorCompat(this, this)
        init()
//        permission.setOnClickListener { checkForSmsPermission() }

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
        return if (mDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onDown(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onDown: $event")
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down)

        return true
    }

    override fun onFling(
        event1: MotionEvent,
        event2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        Log.d(DEBUG_TAG, "onFling: $event1 $event2")
        return true
    }

    override fun onLongPress(event: MotionEvent) {
        Log.d(DEBUG_TAG, "onLongPress: $event")
    }

    override fun onScroll(
        event1: MotionEvent,
        event2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        Log.d(DEBUG_TAG, "onScroll: $event1 $event2")
        return true
    }

    override fun onShowPress(event: MotionEvent) {
        Log.d(DEBUG_TAG, "onShowPress: $event")
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onSingleTapUp: $event")
        return true
    }

    fun test(){
        d("TestLog", "Ana and Lika are the best android developers ever in the world")
    }

    fun anotherTest(){
        d("123", "123")
    }





}
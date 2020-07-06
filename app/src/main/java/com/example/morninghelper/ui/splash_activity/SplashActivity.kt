package com.example.morninghelper.ui.splash_activity

import android.app.Activity
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.example.morninghelper.ui.intro_activity.IntroActivity
import com.example.morninghelper.ui.HomeActivity
import com.example.morninghelper.R
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.shared_preferences.AppSharedPreferences
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private val handler = Handler()
    private val runnable = Runnable {
        openActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        Tools.animation(this, R.anim.slide_up, helloImageView )
    }

    override fun onStart() {
        super.onStart()
        postDelayed()
    }

    override fun onPause() {
        super.onPause()
        removeCallBack()
    }


    private fun openActivity() {
        val activity: Activity =
            if (AppSharedPreferences.getString(AppSharedPreferences.FIRST_OPEN)!!.isEmpty())
                IntroActivity()
            else
                HomeActivity()

        Tools.startActivity(this, activity::class.java, null, true)


    }

    private fun postDelayed() {
        handler.postDelayed(runnable, 3000)
    }

    private fun removeCallBack() {
        handler.removeCallbacks(runnable)
    }

    private fun animation() {
//        val animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
//        helloImageView.startAnimation(animation)
    }



}


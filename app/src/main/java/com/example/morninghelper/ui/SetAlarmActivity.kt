package com.example.morninghelper.ui
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.morninghelper.R
import kotlinx.android.synthetic.main.activity_set_alarm.*

class SetAlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_alarm)
        init()
    }

    private fun init() {
        timePicker.setIs24HourView(true)
//        animateDayNight()
        onClickTime()
    }

    private fun onClickTime() {
        timePicker.setOnTimeChangedListener { _, _, _ ->
//            animateDayNight()
        }
    }

//    private fun animateDayNight() {
//        if (timePicker.hour in 6..18)
//            motionLayout.transitionToStart()
//        else
//            motionLayout.transitionToEnd()
//
//    }
}
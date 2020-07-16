package com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.example.morninghelper.application.App
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.ui.HomeActivity
import kotlinx.android.synthetic.main.activity_set_alarm.*
import java.time.DayOfWeek
import java.util.*

object AlarmTools {

    private val alarmManager by lazy {
        App.instance.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private val intent by lazy{
       Intent(App.instance, MyBroadcastReceiver::class.java)
    }

    private val pendingIntent by lazy{
        PendingIntent.getBroadcast(
            App.instance,
            SetAlarmActivity.ALARM_REQUEST,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    fun setAlarm(
        ringtone: String,
        dismissWith: String,
        snoozeTime: String,
        timePicker: TimePicker?,
        inputTime: Int
    ) {


        intent.putExtra("ringtone", ringtone)
        intent.putExtra("dismiss", dismissWith)
        intent.putExtra("snoozeTime", snoozeTime)

        if (timePicker!= null){
            alarmWithTimePicker( timePicker.hour, timePicker.minute)
        }
        else{
            alarmWithInputTime(inputTime)
        }
    }

    fun alarmWithTimePicker(hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    private fun alarmWithInputTime(time: Int) {
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, pendingIntent)
    }

    fun cancelAlarm() {
        alarmManager.cancel(pendingIntent)
    }


    fun finishAlarm(context: Context, toastString: String, activity: Activity) {
        Toast.makeText(context, toastString, Toast.LENGTH_LONG).show()
        Tools.startActivity(activity, HomeActivity::class.java, null, true)
    }

    fun setReminder(hour:Int, minute: Int){
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, hour, minute)
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

}
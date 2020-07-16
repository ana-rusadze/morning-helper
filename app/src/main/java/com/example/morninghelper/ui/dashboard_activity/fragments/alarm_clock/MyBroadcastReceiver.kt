package com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf


class MyBroadcastReceiver : BroadcastReceiver() {

//    override fun onReceive(p0: Context?, p1: Intent?) {
////        val intent = Intent(p0, AlarmRingingActivity::class.java)
////        val fooString = intent.getStringExtra("KEY_FOO_STRING")
////        Toast.makeText(context, fooString, Toast.LENGTH_LONG).show()
////        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
////        p0?.startActivity(intent)
////    }

    override fun onReceive(context: Context, intent: Intent) {
            val dismiss = intent.getStringExtra("dismiss")
            val ringtone = intent.getStringExtra("ringtone")
            val snoozeTime = intent.getStringExtra("snoozeTime")

            val i = Intent(context, AlarmRingingActivity::class.java)
            i.putExtra("dismiss", dismiss)
            i.putExtra("ringtone", ringtone)
            i.putExtra("snoozeTime", snoozeTime)

            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(context, i, null)


//        }else{
//            val fooString = intent.getStringExtra("KEY_FOO_STRING")
//            Toast.makeText(context, fooString, Toast.LENGTH_LONG).show()
//        }


//        Intent(context, AlarmRingingActivity::class.java)
//        val keyid = intent.getStringExtra("string")
//        d("got extra", keyid!!)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(context, intent, null)

    }

//    @SuppressLint("InvalidWakeLockTag")
//    override fun onReceive(context: Context, intent: Intent?) {
//        val pm =
//            context.getSystemService(Context.POWER_SERVICE) as PowerManager
//        val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "")
//        wl.acquire(10*60*1000L /*10 minutes*/)
//
//        // Put here YOUR code.
//        Toast.makeText(context, "Alarm !!!!!!!!!!", Toast.LENGTH_LONG).show() // For example
//        wl.release()
//    }

//    fun setAlarm(context: Context) {
//        val am =
//            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val i = Intent(context, AlarmRingingActivity::class.java)
//        val pi = PendingIntent.getBroadcast(context, 0, i, 0)
//        am.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            System.currentTimeMillis(),
//            1000 * 60 * 10.toLong(),
//            pi
//        ) // Millisec * Second * Minute
//    }
//
//    fun cancelAlarm(context: Context) {
//        val intent = Intent(context, AlarmRingingActivity::class.java)
//        val sender = PendingIntent.getBroadcast(context, 0, intent, 0)
//        val alarmManager =
//            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        alarmManager.cancel(sender)
//    }


}

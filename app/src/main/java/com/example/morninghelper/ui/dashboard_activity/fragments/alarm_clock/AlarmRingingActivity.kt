package com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.morninghelper.R
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.game.AlarmRingingGameActivity
import kotlinx.android.synthetic.main.activity_alarm_ringing.*



class AlarmRingingActivity : AppCompatActivity() {

    private lateinit var r: Ringtone
    private lateinit var dismiss : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_ringing)
        init()

    }

    private fun init() {
        dismiss = intent.getStringExtra("dismiss")!!
        val ringtone = intent.getStringExtra("ringtone")
        val myUri = Uri.parse(ringtone)
        r = RingtoneManager.getRingtone(applicationContext, myUri)
        r.play()


        dismissButton.setOnClickListener {
            if (dismiss == "Default") {
                AlarmTools.finishAlarm(this,"Good Morning!", this)
            } else {
                Tools.startActivity(this, AlarmRingingGameActivity::class.java, ringtone.toString(), true)
            }
            r.stop()
        }

        snoozeButton.setOnClickListener{
            val snoozeTime = intent.getStringExtra("snoozeTime")!!
            AlarmTools.setAlarm(ringtone.toString(), dismiss, snoozeTime, null, snoozeTime.toInt() *60000 )
            Toast.makeText(this, "alarm is snoozed for $snoozeTime minute(s)", Toast.LENGTH_SHORT).show()
            r.stop()
            finish()
        }


    }

    override fun onBackPressed() {
        Toast.makeText(this, "Please dismiss or snooze alarm", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        r.stop()
    }



    private fun checkForSmsPermission() {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.SEND_SMS
//            ) !=
//            PackageManager.PERMISSION_GRANTED
//        ) {
//            Log.d("tag", "not granted")
//            ActivityCompat.requestPermissions(
//                this, arrayOf(Manifest.permission.SEND_SMS),
//                1
//            )
//        } else {
//            Log.d("tag", "granted")
//            val obj = SmsManager.getDefault()
//            obj.sendTextMessage("+995557277974", null, "welcome", null, null)
//        }
    }

    private fun setRepeat(){

    }




}
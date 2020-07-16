package com.example.morninghelper.ui

import android.app.Activity
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.morninghelper.R
import com.example.morninghelper.dialog.ChooseItemRecyclerViewAdapter
import com.example.morninghelper.tools.extensions.setColor
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmClockFragment
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmInterface
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.shape.MaterialShapeUtils
import kotlinx.android.synthetic.main.activity_set_alarm.*
import kotlinx.android.synthetic.main.chooser_dialog_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class SetAlarmActivity : AppCompatActivity() {

    private lateinit var selectedRingtone: Uri
    private lateinit var chooserAdapter: ChooseItemRecyclerViewAdapter
    private val chooseItems = mutableListOf<String>()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_alarm)
        init()
    }

    private fun init() {
        selectedRingtone = RingtoneManager.getActualDefaultRingtoneUri(
            this,
            RingtoneManager.TYPE_ALARM
        )
        timePicker.setIs24HourView(true)

        onClickTime()
        listeners()
        setRingtoneText(selectedRingtone)
        attachToolbar()
        initChooser()
    }

    private fun onClickTime() {
        timePicker.setOnTimeChangedListener { _, hour, _ ->
            if (hour in 6..18)
                motionLayout.transitionToStart()
            else
                motionLayout.transitionToEnd()
        }

    }


    private fun accessRingtone() {

        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
        intent.putExtra(
            RingtoneManager.EXTRA_RINGTONE_TYPE,
            RingtoneManager.TYPE_RINGTONE
        )
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Ringtone")
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, selectedRingtone)
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
        startActivityForResult(intent, 999)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 999 && resultCode == Activity.RESULT_OK) {
            selectedRingtone =
                data!!.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)!!
            setRingtoneText(selectedRingtone)

        }
    }

    private fun setRingtoneText(ringtoneURi: Uri?) {
        val ringtone: Ringtone? = RingtoneManager.getRingtone(this, ringtoneURi)
        selectedRingtoneTV.text = ringtone!!.getTitle(this)
    }

    private fun attachToolbar() {

        setSupportActionBar(toolBar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        MaterialShapeUtils.setElevation(toolBar, 0f)
        titleTV.setColor(
            "Set Alarm",
            ContextCompat.getColor(this, android.R.color.white)
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            super.onBackPressed()
        return true
    }

    private fun listeners() {
        ringtoneCardView.setOnClickListener {
            accessRingtone()
        }
        saveAlarmButton.setOnClickListener {
//            addNewAlarm()
        }
        repeatCardView.setOnClickListener {
            showChooser(arrayOf("Never", "Mon - Fri", "Daily"), R.id.repeatTextView)
        }
        dismissWithCardView.setOnClickListener {
            showChooser(arrayOf("Default", "game1", "game2"), R.id.dismissWithTextView)
        }

    }


//    es sxva activitystvis mchirdeba da cota xani iyos

//    private fun checkForSmsPermission() {
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
//    }

    private fun initChooser() {
        bottomSheetBehavior = BottomSheetBehavior.from<View>(bottomSheet)
        chooseItemsRecyclerView.layoutManager = LinearLayoutManager(this)
        chooserAdapter = ChooseItemRecyclerViewAdapter(chooseItems, object : AlarmInterface {
            override fun onClick(position: Int) {
                if (chooserAdapter.selectedViewId == R.id.repeatTextView)
                    repeatTextView.text = chooseItems[position]
                else if (chooserAdapter.selectedViewId == R.id.dismissWithTextView)
                    dismissWithTextView.text = chooseItems[position]
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
        })

        chooseItemsRecyclerView.adapter = chooserAdapter
    }

    private fun showChooser(items: Array<String>, selectedViewId: Int) {
        chooserAdapter.selectedViewId = selectedViewId
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        chooseItems.clear()
        chooseItems.addAll(items)
        chooserAdapter.notifyDataSetChanged()
    }

//    private fun addNewAlarm(){
//        val intent = Intent(this, AlarmClockFragment::class.java)
//        val alarmModel = AlarmModel(
//            "${timePicker.hour}:${timePicker.minute}",
//            repeatTextView.text.toString(),
//            labelEditText.text.toString(),
//            selectedRingtone,
//            dismissWithTextView.text.toString(),
//            snoozeTimeEditText.text.toString(),
//            numberEditText.text.toString(),
//            messageEditText.text.toString(),
//            missedAlarmsEditText.text.toString(),
//            true
//        )
//        intent.putExtra("new alarm", alarmModel)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//        setResult(Activity.RESULT_OK, intent)
//        finish()
//    }

    private fun backToNotesFragment() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out)

    }

    override fun onBackPressed() {
        backToNotesFragment()
    }
}
package com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock

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
import androidx.room.Room
import com.example.morninghelper.R
import com.example.morninghelper.application.App
import com.example.morninghelper.dialog.ChooseItemRecyclerViewAdapter
import com.example.morninghelper.room.AppDatabase
import com.example.morninghelper.room.alarms.Alarms
import com.example.morninghelper.tools.extensions.setColor
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.shape.MaterialShapeUtils
import kotlinx.android.synthetic.main.activity_set_alarm.*
import kotlinx.android.synthetic.main.chooser_dialog_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SetAlarmActivity : AppCompatActivity() {

    companion object {
        const val ALARM_REQUEST = 3
        const val RINGTONE_REQUEST = 4
    }

    private lateinit var selectedRingtone: Uri
    private lateinit var chooserAdapter: ChooseItemRecyclerViewAdapter
    private val chooseItems = mutableListOf<String>()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    private lateinit var alarmModel: AlarmModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_alarm)
        init()
        attachToolbar()
        timePickerAnim()
    }

    private fun init() {
        selectedRingtone = RingtoneManager.getActualDefaultRingtoneUri(
            this,
            RingtoneManager.TYPE_ALARM
        )
        setRingtoneText(selectedRingtone)

        listeners()
        initChooser()


        timePicker.setOnTimeChangedListener { _, _, _ ->
            timePickerAnim()
        }
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

    private fun timePickerAnim(){
        timePicker.setIs24HourView(true)
        if (timePicker.hour in 6..18)
            motionLayout.transitionToStart()
        else
            motionLayout.transitionToEnd()
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
        startActivityForResult(intent, RINGTONE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RINGTONE_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedRingtone =
                data!!.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)!!
            setRingtoneText(selectedRingtone)

        }
    }

    private fun setRingtoneText(ringtoneURi: Uri?) {
        val ringtone: Ringtone? = RingtoneManager.getRingtone(this, ringtoneURi)
        selectedRingtoneTV.text = ringtone!!.getTitle(this)
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
            setAlarm()
            addNewAlarm()

        }
        repeatCardView.setOnClickListener {
            showChooser(arrayOf("Never", "Mon - Fri", "Daily"), R.id.repeatTextView)
        }
        dismissWithCardView.setOnClickListener {
            showChooser(arrayOf("Default", "Game"), R.id.dismissWithTextView)
        }

    }

    private fun setAlarm() {

//        set alarm with time picker
//        AlarmTools.setAlarm(
//            this,
//            selectedRingtone.toString(),
//            dismissWithTextView.text.toString(),
//            snoozeTimeEditText.text.toString(),
//            timePicker,
//            0
//        )

//        set Alarm with input time

        AlarmTools.setAlarm(
            selectedRingtone.toString(),
            dismissWithTextView.text.toString(),
            snoozeTimeEditText.text.toString(),
            null,
            10000
        )
    }

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


    private  fun addNewAlarm(){
        val intent = Intent(this, AlarmClockFragment::class.java)
        val alarmModel = AlarmModel(
            "${timePicker.hour}:${timePicker.minute}",
            repeatTextView.text.toString(),
            labelEditText.text.toString(),
            selectedRingtone,
            dismissWithTextView.text.toString(),
            snoozeTimeEditText.text.toString(),
            numberEditText.text.toString(),
            messageEditText.text.toString(),
            missedAlarmsEditText.text.toString(),
            true
        )

        intent.putExtra("new alarm", alarmModel)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }





}
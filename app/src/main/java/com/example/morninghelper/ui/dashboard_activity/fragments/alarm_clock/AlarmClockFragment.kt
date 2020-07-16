package com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.morninghelper.R
import com.example.morninghelper.application.App
import com.example.morninghelper.dialog.CustomDialogInterface
import com.example.morninghelper.room.AppDatabase
import com.example.morninghelper.room.alarms.Alarms
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.tools.extensions.setViewVisibility
import com.example.morninghelper.ui.BaseFragment
import com.example.morninghelper.ui.SetAlarmActivity
import com.example.morninghelper.ui.dashboard_activity.DashboardActivity
import kotlinx.android.synthetic.main.alarm_clock_fragment.*
import kotlinx.android.synthetic.main.alarm_clock_fragment.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmClockFragment : BaseFragment() {

    private val db by lazy {
        Room.databaseBuilder(
            App.instance.getContext(),
            AppDatabase::class.java, "database-name"
        ).build()

    }

    companion object {
        const val REQUEST_CODE = 1
    }

    private var listOfAlarms = ArrayList<Alarms>()
    private lateinit var adapter: AlarmRecyclerViewAdapter

    override fun getLayoutResource() = R.layout.alarm_clock_fragment
    override fun start(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        init()
    }

    private fun init() {
        rootView!!.addAlarmButton.setOnClickListener {
            val intent = Intent(activity, SetAlarmActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
        setRecyclerView()
        initAlarms()
    }


    private fun setRecyclerView() {
        adapter = AlarmRecyclerViewAdapter(listOfAlarms, object : AlarmInterface {
            override fun onClick(position: Int) {
                Tools.initDialog(
                    activity as DashboardActivity,
                    "Alarm`s options",
                    object : CustomDialogInterface {
                        override fun delete() {
                            CoroutineScope(Dispatchers.Main).launch { delete(position) }
                        }

                        override fun edit() {

                        }
                    })


            }
        })

        rootView!!.alarmsRecyclerView.layoutManager = LinearLayoutManager(activity)
        rootView!!.alarmsRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun initAlarms() {
        CoroutineScope(Dispatchers.Main).launch {
            noAlarms()
        }
    }

    private suspend fun noAlarms() {
        val alarms = db.alarmsDao().getAll()
        if (alarms.isEmpty())
            rootView!!.noAlarmsLayout.setViewVisibility(true)
        else {
            rootView!!.noAlarmsLayout.setViewVisibility(false)

            alarms.forEach { listOfAlarms.add(0, it) }
            adapter.notifyDataSetChanged()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            d("shemovida", "ki")
            val alarmModel = data!!.extras?.getParcelable<Alarms>("new alarm")
            d("logAlarm", alarmModel!!.label!!)
            listOfAlarms.add(alarmModel)
            adapter.notifyDataSetChanged()
            rootView!!.noAlarmsLayout.setViewVisibility(false)

        }
        super.onActivityResult(requestCode, resultCode, data)

    }

    private suspend fun delete(position: Int) {
        val alarm = listOfAlarms[position]
        listOfAlarms.remove(alarm)
        db.alarmsDao().delete(alarm)
        adapter.notifyItemRemoved(position)
    }

}
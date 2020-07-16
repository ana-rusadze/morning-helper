package com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.morninghelper.R
import com.example.morninghelper.dialog.CustomDialogInterface
import com.example.morninghelper.shared_preferences.AppSharedPreferences
import com.example.morninghelper.application.App
import com.example.morninghelper.room.AppDatabase
import com.example.morninghelper.room.alarms.Alarms
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.tools.extensions.setViewVisibility
import com.example.morninghelper.ui.BaseFragment
import com.example.morninghelper.ui.dashboard_activity.DashboardActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.alarm_clock_fragment.view.*
import java.lang.reflect.Type
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmClockFragment : BaseFragment() {


    companion object {
        const val ADD_ALARM_REQUEST_CODE = 1
        const val EDIT_ALARM_REQUEST_CODE =2
    }

    private var listOfAlarms = ArrayList<AlarmModel>()
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
            startActivityForResult(intent, ADD_ALARM_REQUEST_CODE)
        }
        noListAlarms()
        setRecyclerView()

    }

    private fun noListAlarms(){
        if(listOfAlarms.size ==0)
            rootView!!.noAlarmsLayout.setViewVisibility(true)
        else
            rootView!!.noAlarmsLayout.setViewVisibility(false)

    }


    private fun setRecyclerView() {
        adapter = AlarmRecyclerViewAdapter(listOfAlarms, object : AlarmInterface {
            override fun onClick(position: Int) {
                Tools.initDialog(rootView!!.context, "Alarm Options", object: CustomDialogInterface{
                    override fun delete() {
                        listOfAlarms.removeAt(position)
                        noListAlarms()
                        Toast.makeText(activity, "alarm removed successfully", Toast.LENGTH_SHORT).show()
                        adapter.notifyItemRemoved(position)
                    }

                    override fun edit() {
                        val intent = Intent(activity, SetAlarmActivity::class.java)
                        startActivityForResult(intent, ADD_ALARM_REQUEST_CODE)
                    }
                })


            }
        })

        rootView!!.alarmsRecyclerView.layoutManager = LinearLayoutManager(activity)
        rootView!!.alarmsRecyclerView.adapter = adapter

        noListAlarms()
        adapter.notifyDataSetChanged()
    }





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == ADD_ALARM_REQUEST_CODE) {
            d("shemovida", "ki")
            val alarmModel = data!!.extras?.getParcelable<AlarmModel>("new alarm")
            d("logAlarm", alarmModel!!.label!!)
            listOfAlarms.add(alarmModel)
            adapter.notifyDataSetChanged()
            rootView!!.noAlarmsLayout.setViewVisibility(false)

        }
        super.onActivityResult(requestCode, resultCode, data)

    }



}

//class AlarmClockFragment : BaseFragment() {
//
//    private val db by lazy {
//        Room.databaseBuilder(
//            App.instance.getContext(),
//            AppDatabase::class.java, "database-name"
//        ).build()
//
//    }
//
//    companion object {
//        const val REQUEST_CODE = 1
//    }
//
//    private var listOfAlarms = ArrayList<Alarms>()
//    private lateinit var adapter: AlarmRecyclerViewAdapter
//
//    override fun getLayoutResource() = R.layout.alarm_clock_fragment
//    override fun start(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ) {
//        init()
//    }
//
//    private fun init() {
//        rootView!!.addAlarmButton.setOnClickListener {
//            val intent = Intent(activity, SetAlarmActivity::class.java)
//            startActivityForResult(intent, REQUEST_CODE)
//        }
//        noListAlarms()
//        setRecyclerView()
//        initAlarms()
//    }
//
//
//    private fun setRecyclerView() {
//        adapter = AlarmRecyclerViewAdapter(listOfAlarms, object : AlarmInterface {
//            override fun onClick(position: Int) {
//                Tools.initDialog(
//                    activity as DashboardActivity,
//                    "Alarm options",
//                    object : CustomDialogInterface {
//                        override fun delete() {
//                            Toast.makeText(activity, "alarm removed successfully", Toast.LENGTH_SHORT).show()
////                            CoroutineScope(Dispatchers.Main).launch { delete(position) }
//                            noListAlarms()
//
//                        }
//
//                        override fun edit() {
//
//                        }
//                    })
//
//
//            }
//        })
//
//        rootView!!.alarmsRecyclerView.layoutManager = LinearLayoutManager(activity)
//        rootView!!.alarmsRecyclerView.adapter = adapter
//        adapter.notifyDataSetChanged()
//    }
//
//    private fun initAlarms() {
//        CoroutineScope(Dispatchers.Main).launch {
//            noAlarms()
//        }
//    }
//
//    private suspend fun noAlarms() {
//        val alarms = db.alarmsDao().getAll()
//        if (alarms.isEmpty())
//            rootView!!.noAlarmsLayout.setViewVisibility(true)
//        else {
//            rootView!!.noAlarmsLayout.setViewVisibility(false)
//
//            alarms.forEach { listOfAlarms.add(0, it) }
//            adapter.notifyDataSetChanged()
//
//        }
//
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
//            d("shemovida", "ki")
//            val alarmModel = data!!.extras?.getParcelable<Alarms>("new alarm")
//            d("logAlarm", alarmModel!!.label!!)
//            listOfAlarms.add(alarmModel)
//            adapter.notifyDataSetChanged()
//            noListAlarms()
//            rootView!!.noAlarmsLayout.setViewVisibility(false)
//
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//
//    }
//
//    private suspend fun delete(position: Int) {
//        val alarm = listOfAlarms[position]
//        listOfAlarms.remove(alarm)
//        db.alarmsDao().delete(alarm)
//        adapter.notifyItemRemoved(position)
//    }
//
//
////

//}
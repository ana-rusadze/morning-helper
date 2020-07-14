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
import com.example.morninghelper.R
import com.example.morninghelper.dialog.CustomDialogInterface
import com.example.morninghelper.shared_preferences.AppSharedPreferences
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.tools.setViewVisibility
import com.example.morninghelper.ui.BaseFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.alarm_clock_fragment.view.*
import java.lang.reflect.Type


class AlarmClockFragment : BaseFragment() {

    companion object {
        const val ADD_ALARM_REQUEST_CODE = 1
        const val EDIT_ALARM_REQUEST_CODE =2
    }

    private var listOfAlarms = ArrayList<AlarmModel>()
    private lateinit var adapter: AlarmRecyclerViewAdapter
    private lateinit var viewModel: AlarmClockViewModel

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
        noAlarms()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        adapter = AlarmRecyclerViewAdapter(listOfAlarms, object : AlarmInterface {
            override fun onClick(position: Int) {
                Tools.initDialog(rootView!!.context, "Alarm Options", object: CustomDialogInterface{
                    override fun delete(dialog: Dialog) {
                        listOfAlarms.removeAt(position)
                        dialog.dismiss()
                        Toast.makeText(activity, "alarm removed successfully", Toast.LENGTH_SHORT).show()
                        noAlarms()
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
        noAlarms()
        adapter.notifyDataSetChanged()
    }

    private fun noAlarms(){
        if(listOfAlarms.size ==0)
            rootView!!.noAlarmsLayout.setViewVisibility(true)
        else
            rootView!!.noAlarmsLayout.setViewVisibility(false)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == ADD_ALARM_REQUEST_CODE) {
            val alarmModel = data?.extras?.getParcelable<AlarmModel>("new alarm")
            listOfAlarms.add(0, alarmModel!!)
            noAlarms()
            adapter.notifyDataSetChanged()

        }
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun save(){
        val json = Gson().toJson(listOfAlarms)
        AppSharedPreferences.saveString("saved", json)
    }

    private fun load(){
        val json = AppSharedPreferences.getString("saved")
        val type: Type = object : TypeToken<ArrayList<AlarmModel>>() {}.type
        d("alarms", "$json")
        listOfAlarms = Gson().fromJson(json, type)
    }

}
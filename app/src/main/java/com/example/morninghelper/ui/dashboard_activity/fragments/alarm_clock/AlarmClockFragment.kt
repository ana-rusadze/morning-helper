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
import com.example.morninghelper.R
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.ui.BaseFragment
import com.example.morninghelper.ui.SetAlarmActivity
import com.example.morninghelper.ui.dashboard_activity.DashboardActivity
import kotlinx.android.synthetic.main.alarm_clock_fragment.*
import kotlinx.android.synthetic.main.alarm_clock_fragment.view.*

class AlarmClockFragment : BaseFragment() {

    companion object {
        const val REQUEST_CODE = 1
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
            startActivityForResult(intent, REQUEST_CODE)
        }
        setRecyclerView()
    }

    private fun setRecyclerView() {
        adapter = AlarmRecyclerViewAdapter(listOfAlarms, object : AlarmInterface {
            override fun onClick(position: Int) {
            }
        })

        rootView!!.alarmsRecyclerView.layoutManager = LinearLayoutManager(activity)
        rootView!!.alarmsRecyclerView.adapter = adapter
//        noAlarms()
        adapter.notifyDataSetChanged()
    }
//
//    private fun noAlarms(){
//        if(listOfAlarms.size ==0){
//            alarmsRecyclerView.visibility = View.GONE
//            noAlarmsLayout.visibility = View.VISIBLE
//        }else{
//            alarmsRecyclerView.visibility = View.VISIBLE
//            noAlarmsLayout.visibility = View.GONE
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            d("shemovida", "ki")
            val alarmModel = data?.extras?.getParcelable<AlarmModel>("new alarm")
            listOfAlarms.add(alarmModel!!)
            adapter.notifyDataSetChanged()

        }
        super.onActivityResult(requestCode, resultCode, data)

    }
}
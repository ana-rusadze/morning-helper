package com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.morninghelper.R

class AlarmClockFragment : Fragment() {

    companion object {
        fun newInstance() =
            AlarmClockFragment()
    }

    private lateinit var viewModel: AlarmClockViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.alarm_clock_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AlarmClockViewModel::class.java)

    }

}
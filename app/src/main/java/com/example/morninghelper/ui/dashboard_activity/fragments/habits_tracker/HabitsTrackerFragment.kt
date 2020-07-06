package com.example.morninghelper.ui.dashboard_activity.fragments.habits_tracker

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.morninghelper.R

class HabitsTrackerFragment : Fragment() {

    companion object {
        fun newInstance() = HabitsTrackerFragment()
    }

    private lateinit var viewModel: HabitsTrackerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.habits_tracker_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HabitsTrackerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
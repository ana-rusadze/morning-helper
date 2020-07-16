package com.example.morninghelper.ui.dashboard_activity.fragments.habits_tracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.morninghelper.R
import com.example.morninghelper.application.App
import com.example.morninghelper.ui.BaseFragment
import kotlinx.android.synthetic.main.reminders_fragment.*
import kotlinx.android.synthetic.main.reminders_fragment.view.*
import kotlinx.android.synthetic.main.reminders_fragment.view.remindersRecyclerView

class RemindersFragment : BaseFragment() {

    companion object {
        fun newInstance() = RemindersFragment()
    }


    override fun getLayoutResource(): Int = R.layout.reminders_fragment
    override fun start(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
    }

}
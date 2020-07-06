package com.example.morninghelper.ui.intro_activity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.morninghelper.R
import com.example.morninghelper.ui.BaseFragment

class IntroMorningActivities : BaseFragment() {

    override fun getLayoutResource() = R.layout.fragment_intro_morning_activities
    override fun start(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ) {
        init()
    }

    private fun init() {

    }
}
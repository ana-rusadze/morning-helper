package com.example.morninghelper.ui.intro_activity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.morninghelper.R
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_intro_welcome.view.*

class IntroWelcome : BaseFragment() {

    override fun getLayoutResource() = R.layout.fragment_intro_welcome
    override fun start(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ) {
        init()
    }

    private fun init() {

        Tools.animation(rootView!!.context, R.anim.fade_in, rootView!!.welcomeTextView)

//        val fadeIn = AnimationUtils.loadAnimation(rootView!!.context, R.anim.fade_in)
//        rootView!!.welcomeTextView.startAnimation(fadeIn)

    }
}
package com.example.morninghelper.ui.dashboard_activity

import android.os.Bundle

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.morninghelper.R
import com.example.morninghelper.tools.Tools.tabSelectedListener
import com.example.morninghelper.application.App
import com.example.morninghelper.tools.extensions.setColor
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmClockFragment
import com.example.morninghelper.ui.dashboard_activity.fragments.daily_horoscope.HoroscopeFragment
import com.example.morninghelper.ui.dashboard_activity.fragments.notes.NotesFragment
import com.example.morninghelper.view_pager_adapter.ViewPagerAdapter
import com.google.android.material.shape.MaterialShapeUtils.setElevation
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class DashboardActivity : AppCompatActivity() {


    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var fragmentsItems = mutableListOf<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        init()
    }


    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out)
    }



    private fun init() {
        fragmentsItems.add(AlarmClockFragment())
        fragmentsItems.add(NotesFragment())
        fragmentsItems.add(HoroscopeFragment())
        viewPagerAdapter =
            ViewPagerAdapter(
                supportFragmentManager,
                fragmentsItems
            )
        viewPager.adapter = viewPagerAdapter
        viewPagerAdapter.notifyDataSetChanged()
        val currentPos = intent.extras?.getInt("currentPosition")
        if (currentPos != null)
            viewPager.currentItem = currentPos!!
        else
            viewPager.currentItem = 0

        addTabItem()
        attachToolbar()
    }


    private fun addTabItem() {

        tabLayout.addTab(
            tabLayout.newTab().setText(App.instance.getContext().getString(R.string.alarms))
        )

        tabLayout.addTab(
            tabLayout.newTab().setText(App.instance.getContext().getString(R.string.notes))
        )
        tabLayout.addTab(
            tabLayout.newTab().setText(App.instance.getContext().getString(R.string.horoscope))
        )
        onTabClickedPageChangeListener()
    }


    private fun onTabClickedPageChangeListener() {
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.tabSelectedListener(viewPager)

    }

    private fun attachToolbar() {
        setSupportActionBar(toolBar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        setElevation(toolBar, 0f)
        settingsBT.setBackgroundResource(R.mipmap.ic_settings_white)
        titleTV.setColor(
            getString(R.string.morning),
            ContextCompat.getColor(this, R.color.brown)
        )
        titleTV.setColor(
            getString(R.string.helper),
            ContextCompat.getColor(this, R.color.slothSkinColor)
        )
    }


}
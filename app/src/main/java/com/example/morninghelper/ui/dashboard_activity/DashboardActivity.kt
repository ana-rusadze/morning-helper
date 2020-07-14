package com.example.morninghelper.ui.dashboard_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.MotionEvent

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.morninghelper.R
import com.example.morninghelper.tools.Tools.tabSelectedListener
import com.example.morninghelper.application.App
import com.example.morninghelper.networking.EndPoints
import com.example.morninghelper.networking.HoroscopeCallback
import com.example.morninghelper.networking.HoroscopeDataLoader
import com.example.morninghelper.tools.setColor
import com.example.morninghelper.ui.HomeActivity
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmClockFragment
import com.example.morninghelper.ui.dashboard_activity.fragments.daily_horoscope.HoroscopeFragment
import com.example.morninghelper.ui.dashboard_activity.fragments.daily_horoscope.HoroscopeModel
import com.example.morninghelper.ui.dashboard_activity.fragments.habits_tracker.HabitsTrackerFragment
import com.example.morninghelper.ui.dashboard_activity.fragments.music.MusicFragment
import com.example.morninghelper.ui.dashboard_activity.fragments.news.NewsFragment
import com.example.morninghelper.view_pager_adapter.ViewPagerAdapter
import com.google.android.material.shape.MaterialShapeUtils.setElevation
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import okhttp3.OkHttpClient
import okhttp3.Request


class DashboardActivity : AppCompatActivity() {


    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var fragmentsItems = mutableListOf<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        init()

    }

    private fun init() {
        fragmentsItems.add(AlarmClockFragment())
        fragmentsItems.add(HabitsTrackerFragment())
        fragmentsItems.add(NewsFragment())
        fragmentsItems.add(MusicFragment())
        fragmentsItems.add(HoroscopeFragment())
        viewPagerAdapter =
            ViewPagerAdapter(
                supportFragmentManager,
                fragmentsItems
            )
        viewPager.adapter = viewPagerAdapter
        viewPagerAdapter.notifyDataSetChanged()
        addTabItem()
        attachToolbar()
    }


    private fun addTabItem() {

        tabLayout.addTab(
            tabLayout.newTab().setText(App.instance.getContext().getString(R.string.alarms))
        )
        tabLayout.addTab(
            tabLayout.newTab().setText(App.instance.getContext().getString(R.string.habits_tracker))
        )
        tabLayout.addTab(
            tabLayout.newTab().setText(App.instance.getContext().getString(R.string.news))
        )
        tabLayout.addTab(
            tabLayout.newTab().setText(App.instance.getContext().getString(R.string.music))
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
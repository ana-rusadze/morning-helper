package com.example.morninghelper.ui.dashboard_activity

import android.app.Dialog
import android.os.Bundle
import android.util.Log.d
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.morninghelper.R
import com.example.morninghelper.tools.Tools.tabSelectedListener
import com.example.morninghelper.application.App
import com.example.morninghelper.shared_preferences.AppSharedPreferences
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmClockFragment
import com.example.morninghelper.ui.dashboard_activity.fragments.daily_horoscope.HoroscopeFragment
import com.example.morninghelper.ui.dashboard_activity.fragments.weather.WeatherFragment
import com.example.morninghelper.ui.dashboard_activity.fragments.news.NewsFragment
import com.example.morninghelper.tools.extensions.setColor
import com.example.morninghelper.ui.HomeActivity
import com.example.morninghelper.ui.dashboard_activity.fragments.notes.NotesFragment
import com.example.morninghelper.view_pager_adapter.ViewPagerAdapter
import com.google.android.material.shape.MaterialShapeUtils.setElevation
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.change_settings_dialog.*
import kotlinx.android.synthetic.main.dialog_layout.*
import kotlinx.android.synthetic.main.fragment_intro_enter_your_name.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class DashboardActivity : AppCompatActivity() {


    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var fragmentsItems = mutableListOf<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        init()
    }


    private fun init() {
        settingsBT.setOnClickListener { changeUserName() }
        fragmentsItems.add(AlarmClockFragment())
        fragmentsItems.add(NewsFragment())
        fragmentsItems.add(WeatherFragment())
        fragmentsItems.add(HoroscopeFragment())
        fragmentsItems.add(NotesFragment())
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
            tabLayout.newTab().setText(App.instance.getContext().getString(R.string.news))
        )

        tabLayout.addTab(
            tabLayout.newTab().setText(App.instance.getContext().getString(R.string.weather))
        )
        tabLayout.addTab(
            tabLayout.newTab().setText(App.instance.getContext().getString(R.string.horoscope))
        )
        tabLayout.addTab(
            tabLayout.newTab().setText(App.instance.getContext().getString(R.string.notes))
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


    private fun changeUserName() {
        val dialog = Dialog(this)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.change_settings_dialog)
        val params: WindowManager.LayoutParams = dialog.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = params
        dialog.changeUserNameET.setText(AppSharedPreferences.getString(AppSharedPreferences.USER_NAME))

        dialog.saveNewUserNameBT.setOnClickListener {
            val newName = dialog.changeUserNameET.text.toString()
            AppSharedPreferences.saveString(
                AppSharedPreferences.USER_NAME, newName
            )
            Tools.startActivity(this, HomeActivity::class.java, null, true)
            dialog.dismiss()
        }
        dialog.show()
    }


}
package com.example.morninghelper.ui.intro_activity


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.morninghelper.R
import com.example.morninghelper.shared_preferences.AppSharedPreferences
import com.example.morninghelper.ui.HomeActivity
import com.example.morninghelper.ui.intro_activity.fragments.*
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.view_pager_adapter.ViewPagerAdapter
import com.example.morninghelper.ui.intro_activity.fragments.IntroAlarm
import com.example.morninghelper.ui.intro_activity.fragments.IntroHabits
import com.example.morninghelper.ui.intro_activity.fragments.IntroMorningActivities
import com.example.morninghelper.ui.intro_activity.fragments.IntroWelcome
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.fragment_intro_enter_your_name.*

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        init()
    }

    private fun init() {
        val fragments = mutableListOf<Fragment>()
        fragments.add(IntroWelcome())
        fragments.add(IntroAlarm())
        fragments.add(IntroMorningActivities())
        fragments.add(IntroHabits())
        fragments.add(IntroEnterYourName())
        introPagerLayout.offscreenPageLimit = 5
        introPagerLayout.adapter =
            ViewPagerAdapter(
                supportFragmentManager,
                fragments
            )
        viewPagerIssues()


        listeners()
    }

    private fun viewPagerIssues() {
        introPagerLayout.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (introPagerLayout.currentItem == 4) {
                    nextButton.visibility = View.GONE
                    beginButton.visibility = View.VISIBLE

                } else {
                    nextButton.visibility = View.VISIBLE
                    beginButton.visibility = View.GONE
                }

            }
        })

    }

    private fun listeners() {
        nextButton.setOnClickListener {
            introPagerLayout.currentItem = introPagerLayout.currentItem + 1
        }
        beginButton.setOnClickListener {
            if (nameEditText.text.isNotEmpty())
                openActivity()
            else
                Toast.makeText(this, "We need your name first", Toast.LENGTH_SHORT).show()
        }
        skipButton.setOnClickListener {
            introPagerLayout.currentItem = 4
        }
    }


    private fun openActivity() {
//        AppSharedPreferences.saveString(
//            AppSharedPreferences.FIRST_OPEN,
//            "done"
//        )
        AppSharedPreferences.saveString(AppSharedPreferences.USER_NAME, nameEditText.text.toString())
        Tools.startActivity(this, HomeActivity::class.java, null, true)
    }

}

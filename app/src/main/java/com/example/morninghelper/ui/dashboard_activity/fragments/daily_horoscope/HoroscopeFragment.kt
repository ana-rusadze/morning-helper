package com.example.morninghelper.ui.dashboard_activity.fragments.daily_horoscope

import android.os.Build
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.morninghelper.R
import com.example.morninghelper.application.App
import com.example.morninghelper.dialog.ChooseItemRecyclerViewAdapter
import com.example.morninghelper.networking.EndPoints
import com.example.morninghelper.networking.HoroscopeCallback
import com.example.morninghelper.networking.HoroscopeDataLoader
import com.example.morninghelper.tools.Tools
import com.example.morninghelper.tools.extensions.setViewVisibility
import com.example.morninghelper.ui.BaseFragment
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmInterface
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import kotlinx.android.synthetic.main.chooser_dialog_layout.view.*
import kotlinx.android.synthetic.main.horoscope_fragment.view.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class HoroscopeFragment : BaseFragment() {

    companion object {
        fun newInstance() = HoroscopeFragment()
    }

    private lateinit var doubleBounce: Sprite
    private lateinit var chooserAdapter: ChooseItemRecyclerViewAdapter
    private val chooseSignItem = arrayListOf<String>()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    override fun getLayoutResource(): Int = R.layout.horoscope_fragment

    override fun start(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        init()

    }


    private fun init() {
        doubleBounce = DoubleBounce()
        doubleBounce.color = ContextCompat.getColor(App.instance.getContext(), R.color.greenColor)
        rootView!!.spinKitLoaderProgressBar.indeterminateDrawable = doubleBounce
        rootView!!.readHoroscopeBT.setOnClickListener {
            showChooser(
                arrayOf(
                    "aries",
                    "taurus",
                    "gemini",
                    "cancer",
                    "leo",
                    "virgo",
                    "libra",
                    "scorpio",
                    "sagittarius",
                    "capricorn",
                    "aquarius",
                    "pisces"
                )
            )
        }
        initRecyclerView()
    }


    private fun getCurrentDate() = answer

    private val answer: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        current.format(formatter)

    } else {
        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        formatter.format(date)

    }


    private fun getHoroscope(hSign: String) {
        rootView!!.horoscopeDescriptionTV.setViewVisibility(false)
        rootView!!.horoscopeDescriptionCV.setViewVisibility(false)
        val parameters = mutableMapOf<String, String>()
        if (hSign.isNotEmpty()) {
            parameters["sign"] = hSign
            parameters["date"] = getCurrentDate()
            if (rootView!!.spinKitLoaderProgressBar.visibility != View.VISIBLE)
                rootView!!.horoscopeDescriptionCV.setViewVisibility(false)

            rootView!!.spinKitLoaderProgressBar.visibility = View.VISIBLE

            HoroscopeDataLoader.getRequest(
                rootView!!.spinKitLoaderProgressBar,
                EndPoints.DAILY,
                parameters,
                object : HoroscopeCallback {
                    override fun onSuccess(response: String) {
                        d("horoscope", response)
                        val json = Gson().fromJson(response, HoroscopeModel::class.java)
                        Tools.animation(
                            App.instance.getContext(),
                            R.anim.zoom_in,
                            rootView!!.horoscopeDescriptionCV
                        )
                        rootView!!.horoscopeDescriptionTV.text = json.result!!.description
                        rootView!!.horoscopeDescriptionTV.setViewVisibility(true)
                        rootView!!.horoscopeDescriptionCV.setViewVisibility(true)
                    }

                    override fun onError(error: String, body: String) {

                    }
                })
        }
    }


    private fun initRecyclerView() {
        bottomSheetBehavior = BottomSheetBehavior.from(rootView!!.bottomSheetLayout)
        rootView!!.chooseItemsRecyclerView.layoutManager =
            LinearLayoutManager(App.instance.getContext())
        chooserAdapter = ChooseItemRecyclerViewAdapter(chooseSignItem, object : AlarmInterface {
            override fun onClick(position: Int) {
                if (chooserAdapter.selectedViewId == R.id.readHoroscopeBT) {
                    rootView!!.horoscopeDescriptionCV.setViewVisibility(false)
                    chooserAdapter.selectedPosition = position
                    rootView!!.readHoroscopeBT.text =
                        chooseSignItem[chooserAdapter.selectedPosition]
                    getHoroscope(chooseSignItem[chooserAdapter.selectedPosition])
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN


                }
            }

        })
        rootView!!.chooseItemsRecyclerView.adapter = chooserAdapter

    }

    private fun showChooser(items: Array<String>) {
        chooserAdapter.selectedViewId = R.id.readHoroscopeBT
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        chooseSignItem.clear()
        chooseSignItem.addAll(items)
        chooserAdapter.notifyDataSetChanged()
    }


}
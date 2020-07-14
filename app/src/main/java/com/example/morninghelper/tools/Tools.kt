package com.example.morninghelper.tools

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.fragment_intro_welcome.view.*
import androidx.viewpager.widget.ViewPager
import com.example.morninghelper.R
import com.example.morninghelper.dialog.CustomDialogInterface
import com.example.morninghelper.ui.HomeActivity
import com.example.morninghelper.ui.dashboard_activity.DashboardActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.dialog_layout.*


object Tools {
    fun initDialog(context: Context, title: String, customDialogInterface: CustomDialogInterface) {
        val dialog = Dialog(context)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_layout)
        val params: WindowManager.LayoutParams = dialog.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = params
        dialog.dialogTitle.text = title
        dialog.deleteButton.setOnClickListener {
            customDialogInterface.delete(dialog)
        }
        dialog.editButton.setOnClickListener {
            customDialogInterface.edit()
        }
        dialog.show()

    }

    fun <T> startActivity(activity: Activity, cls: Class<T>, data: String?, clearTasks: Boolean) {
        val intent = Intent(activity, cls)
        if (data!= null)
            intent.putExtra("data",data)
        if (clearTasks)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

    }

    fun <T> startActivityForResult(
        activity: Activity,
        cls: Class<T>,
        requestCode: Int,
        bundle: Bundle?
    ) {
        val intent = Intent(activity, cls)
        if (bundle != null)
            intent.putExtras(bundle)
        activity.startActivityForResult(intent, requestCode)
        activity.overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        )

    }

    fun animation(context: Context, anim: Int, view: View) {
        val animation = AnimationUtils.loadAnimation(context, anim)
        view.startAnimation(animation)
    }


    fun TabLayout.tabSelectedListener(pager: ViewPager) {
        addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                pager.currentItem = tab!!.position
            }

        })
    }
//
//    fun <T> slideUpGesture(event: MotionEvent, activity: Activity, cls: Class<T>) {
//
//        val MIN_DISTANCE = 150
//        var y2: Float = 0.0f
//        var y1: Float = 0.0f
//        when (event.actionMasked) {
//            0 -> {
//                y1 = event.y
//            }
//            1 -> {
//                y2 = event.y
//                val valueY: Float = y2 - y1
//
//                if (kotlin.math.abs(valueY) > MIN_DISTANCE && y2 < y1) {
//                    val intent = Intent(activity, cls)
//                    activity.startActivity(intent)
//                    activity.overridePendingTransition(R.anim.slide_up, R.anim.slide_down)
//                }
//            }
//        }
//    }
}



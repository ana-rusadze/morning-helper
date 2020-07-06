package com.example.morninghelper.tools

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.fragment_intro_welcome.view.*
import androidx.viewpager.widget.ViewPager
import com.example.morninghelper.R
import com.google.android.material.tabs.TabLayout


object Tools {
//    fun initDialog(context: Context, title: String, desc: String) {
//        val dialog = Dialog(context)
//        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
//        dialog.setContentView(R.layout.dialog_layout)
//
//        val params: WindowManager.LayoutParams = dialog.window!!.attributes
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT
//        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
//        dialog.window!!.attributes = params as WindowManager.LayoutParams
//        dialog.dialogTitle.text = title
//        dialog.dialogDescription.text = desc
//        dialog.okButton.setOnClickListener {
//            dialog.dismiss()
//        }
//
//        dialog.show()

//    }

    fun <T> startActivity(activity: Activity, cls: Class<T>, bundle: Bundle?, clearTasks: Boolean) {
        val intent = Intent(activity, cls)
        if(bundle != null)
            intent.putExtras(bundle)
        if(clearTasks)
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


    fun TabLayout.tabSelectedListener( pager: ViewPager){
        addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                pager.currentItem = tab!!.position
            }

        })
    }

}



package com.example.morninghelper.ui.dashboard_activity.fragments

import androidx.databinding.BindingAdapter
import de.hdodenhof.circleimageview.CircleImageView

object DataBindingComponents {
    @JvmStatic
    @BindingAdapter("setCircleBackgroundColorResource")
    fun setResourceColor(circleImageView: CircleImageView, color:Int ){
            circleImageView.setCircleBackgroundColorResource(color)
    }
}
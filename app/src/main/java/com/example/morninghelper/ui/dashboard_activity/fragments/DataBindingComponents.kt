package com.example.morninghelper.ui.dashboard_activity.fragments

import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.morninghelper.R
import com.example.morninghelper.application.App
import de.hdodenhof.circleimageview.CircleImageView

object DataBindingComponents {
    @JvmStatic
    @BindingAdapter("setCircleBackgroundColorResource")
    fun setResourceColor(circleImageView: CircleImageView, color: Int?) {
        if (color != null)
            circleImageView.circleBackgroundColor = color
        else {
            circleImageView.circleBackgroundColor = ContextCompat.getColor(App.instance
                .getContext(), R.color.greenColor)
        }
    }
}
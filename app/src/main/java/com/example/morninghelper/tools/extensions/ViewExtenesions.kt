package com.example.morninghelper.tools.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

fun View.setViewVisibility(visible: Boolean) {
    visibility = if (visible)
        VISIBLE
    else
        GONE
}

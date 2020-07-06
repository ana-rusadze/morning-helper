package com.example.morninghelper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {
    var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if(rootView == null){
            rootView= inflater.inflate(getLayoutResource(),container, false)
            start(inflater,container, savedInstanceState)
        }
        return rootView
    }

    abstract fun getLayoutResource(): Int

    abstract fun start(inflater: LayoutInflater,
                       container: ViewGroup?,
                       savedInstanceState: Bundle?)

}
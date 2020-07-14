package com.example.morninghelper.ui.dashboard_activity.fragments.music

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.morninghelper.R

class MusicFragment : Fragment() {

    companion object {
        fun newInstance() =
            MusicFragment()
    }

    private lateinit var viewModel: MusicViewModel
    private lateinit var itemView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        itemView =  inflater.inflate(R.layout.music_fragment, container, false)
        return itemView


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MusicViewModel::class.java)


    }



}
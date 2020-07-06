package com.example.morninghelper.ui.dashboard_activity.fragments.music

import android.graphics.PorterDuff
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.morninghelper.R
import kotlinx.android.synthetic.main.music_fragment_start.view.*
import java.util.EnumSet.of

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
        itemView = inflater.inflate(R.layout.music_fragment_start, container, false)
        return itemView
    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders(this).get(MusicViewModel::class.java)
//
//    }

    private fun init(){
        itemView.seekBar.thumb.setColorFilter(resources.getColor(R.color.yellowColor), PorterDuff.Mode.MULTIPLY)
    }

}
package com.example.morninghelper.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.morninghelper.R
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmInterface
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmModel
import kotlinx.android.synthetic.main.alarms_recyclerview_layout.view.*
import kotlinx.android.synthetic.main.chooser_dialog_layout.view.*
import kotlinx.android.synthetic.main.item_chooser_recyclerview_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*

class ChooseItemRecyclerViewAdapter(
    private val items: MutableList<String>,
    private val alarmInterface: AlarmInterface
) : RecyclerView.Adapter<ChooseItemRecyclerViewAdapter.ViewHolder>() {

    var selectedViewId = 0
    var selectedPosition = 0
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun onBind() {
            itemView.chooseItemTitle.text = items[adapterPosition]
            itemView.setOnClickListener{
                alarmInterface.onClick(adapterPosition)
                selectedPosition = adapterPosition
            }
            if(adapterPosition == selectedPosition)
                itemView.selectImageView.visibility = View.VISIBLE
            else{
                itemView.selectImageView.visibility = View.INVISIBLE
            }

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chooser_recyclerview_layout, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind()
    }



}
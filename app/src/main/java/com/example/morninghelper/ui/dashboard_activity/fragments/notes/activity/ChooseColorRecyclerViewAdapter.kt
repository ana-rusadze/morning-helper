package com.example.morninghelper.ui.dashboard_activity.fragments.notes.activity

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.morninghelper.R
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmInterface
import kotlinx.android.synthetic.main.color_chooser_item.view.*

class ChooseColorRecyclerViewAdapter(
    private val colorDataSet: MutableList<Int>,
    private val alarmInterface: AlarmInterface
) : RecyclerView.Adapter<ChooseColorRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        fun onBind() {
            val color = colorDataSet[adapterPosition]

            itemView.colorChooserItem.circleBackgroundColor = color
        }

        override fun onClick(v: View?) {
            alarmInterface.onClick(adapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.color_chooser_item, parent, false
        )
    )

    override fun getItemCount(): Int = colorDataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind()
    }
}
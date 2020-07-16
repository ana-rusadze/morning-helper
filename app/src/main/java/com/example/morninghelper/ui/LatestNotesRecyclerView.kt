package com.example.morninghelper.ui

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
import kotlinx.android.synthetic.main.latest_notes_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*

class LatestNotesRecyclerView(
    private val items: ArrayList<String>
) : RecyclerView.Adapter<LatestNotesRecyclerView.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun onBind() {
            val item = items[adapterPosition]
            itemView.noteTitle.text = item

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.latest_notes_layout, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind()
    }
}
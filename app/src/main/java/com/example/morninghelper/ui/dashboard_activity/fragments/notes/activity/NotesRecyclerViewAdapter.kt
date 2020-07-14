package com.example.morninghelper.ui.dashboard_activity.fragments.notes.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.morninghelper.R
import com.example.morninghelper.databinding.NoteItemLayoutBinding
import com.example.morninghelper.room.Notes
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmInterface

class NotesRecyclerViewAdapter(private var dataSet: ArrayList<Notes>, private val alarmInterface: AlarmInterface) :
    RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder>() {
    fun filterNoteList(filteredNotes: ArrayList<Notes>) {
       dataSet = filteredNotes
        notifyDataSetChanged()
    }
    inner class ViewHolder(private val binding: NoteItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun onBind() {
            val model = dataSet[adapterPosition]
            binding.noteItem = model


        }

        override fun onClick(v: View?) {
            alarmInterface.onClick(adapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.note_item_layout, parent, false
        )
    )

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind()
    }
//  fun swipeDelete(recyclerView: RecyclerView){
//       val itemTouchHelperCallback =
//          object :
//              ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//              override fun onMove(
//                  recyclerView: RecyclerView,
//                  viewHolder: RecyclerView.ViewHolder,
//                  target: RecyclerView.ViewHolder
//              ): Boolean {
//
//
//              }
//
//              override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//
//              }
//
//          }
//
//      val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
//          itemTouchHelper.attachToRecyclerView(recyclerView)
//

//  }

}
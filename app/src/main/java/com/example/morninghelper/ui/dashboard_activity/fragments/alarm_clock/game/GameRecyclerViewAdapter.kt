package com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.game

import android.text.Editable
import android.text.TextWatcher
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.morninghelper.R
import com.example.morninghelper.tools.setViewVisibility
import com.example.morninghelper.ui.dashboard_activity.fragments.alarm_clock.AlarmInterface
import kotlinx.android.synthetic.main.game_recyclerview_layout.view.*
import kotlin.properties.Delegates

class GameRecyclerViewAdapter(
    private val items: MutableList<GameModel>,
    private val alarmInterface: AlarmInterface
) : RecyclerView.Adapter<GameRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


        lateinit var model: GameModel
        fun onBind() {
             model = items[adapterPosition]

            itemView.firstInt.text = model.firstInt.toString()
            itemView.secondInt.text = model.secondInt.toString()
            itemView.operator.text = model.operator

            alarmInterface.onClick(adapterPosition)


             val textWatcher = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString() == model.answer.toString()){
                        itemView.rightAnswerImageView.setViewVisibility(true)
                        model.right = true

                    }else
                        itemView.rightAnswerImageView.visibility = View.INVISIBLE
                }
             }

            itemView.answerEditText.addTextChangedListener(textWatcher)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.game_recyclerview_layout, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind()
    }



}
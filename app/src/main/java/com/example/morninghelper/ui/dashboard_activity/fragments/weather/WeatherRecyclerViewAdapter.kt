package com.example.morninghelper.ui.dashboard_activity.fragments.weather

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.morninghelper.R
import com.example.morninghelper.ui.dashboard_activity.fragments.news.NewsModel
import kotlinx.android.synthetic.main.news_recyclerview_layout.view.*
import kotlinx.android.synthetic.main.weather_recyclerview_layout.view.*

class WeatherRecyclerViewAdapter(
    private val weather: ArrayList<WeatherModel>
) : RecyclerView.Adapter<WeatherRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var model: WeatherModel

        fun onBind() {
            model = weather[adapterPosition]
            itemView.weatherItemTitle.text = model.title
            itemView.weatherDetail.text = model.detail
            Glide.with(itemView.context).load(model.image).into(itemView.weatherImageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_recyclerview_layout, parent, false)
        )
    }

    override fun getItemCount() = weather.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind()
    }


}
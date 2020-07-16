package com.example.morninghelper.ui.dashboard_activity.fragments.news

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.morninghelper.R
import kotlinx.android.synthetic.main.news_recyclerview_layout.view.*

class NewsRecyclerViewAdapter(
    private val news: ArrayList<NewsModel>
) : RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private lateinit var model : NewsModel

        fun onBind() {
            model = news[adapterPosition]
            itemView.newsTitle.text = model.title
            itemView.newsDescription.text = model.description!!
            Glide.with(itemView.context).load(model.imageUrl).into(itemView.newsImageView)
            itemView.newsSeeMore.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(model.url))
                startActivity(itemView.context, browserIntent, null)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.news_recyclerview_layout, parent, false)
        )
    }

    override fun getItemCount() = news.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind()
    }


}
package com.example.morninghelper.ui.dashboard_activity.fragments.news

import android.os.Bundle
import android.os.Handler
import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.morninghelper.R
import com.example.morninghelper.networking.EndPoints
import com.example.morninghelper.networking.ApiCallback
import com.example.morninghelper.networking.NewsData
import com.example.morninghelper.tools.extensions.setViewVisibility
import com.example.morninghelper.ui.BaseFragment
import kotlinx.android.synthetic.main.news_fragment.view.*
import org.json.JSONObject

class NewsFragment : BaseFragment() {

    private var news = ArrayList<NewsModel>()
    private lateinit var adapter: NewsRecyclerViewAdapter
    private var pageCount = 1

    override fun getLayoutResource() = R.layout.news_fragment
    override fun start(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        init()
    }

    private fun init(){
        rootView!!.loaderLayout.setViewVisibility(false)
        adapter = NewsRecyclerViewAdapter(news)
        rootView!!.newsRecyclerView.layoutManager = LinearLayoutManager(activity)
        rootView!!.newsRecyclerView.adapter = adapter

        getNewsData(pageCount.toString())

        rootView!!.swipeRefreshLayout.setOnRefreshListener {
            rootView!!.swipeRefreshLayout.isRefreshing = true
            rootView!!.loaderLayout.setViewVisibility(true)
            refresh()

            Handler().postDelayed({
                rootView!!.swipeRefreshLayout.isRefreshing = false
                rootView!!.loaderLayout.setViewVisibility(false)
            }, 2000)
        }
    }

    private fun refresh() {
        if(pageCount != 4)
            pageCount +=1
        news.clear()
        getNewsData(pageCount.toString())
        adapter.notifyDataSetChanged()

    }

    private fun getNewsData(page: String){

        val parameters = mutableMapOf<String, String>()
        parameters["q"] = "art"
        parameters["sortBy"] ="relevancy"
        parameters["language"] = "en"
        parameters["page"] = page
        parameters["apiKey"] = NewsData.NEWS_KEY
        NewsData.getRequest(EndPoints.EVERYTHING, parameters, object : ApiCallback {
            override fun onError(error: String, body: String) {
                Toast.makeText(
                    rootView!!.context,
                    "check your internet connection and try again!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onSuccess(response: String) {
                parseJSON(response)
            }
        })

    }


    private fun parseJSON(response: String){
        val json = JSONObject(response)
        if (json.has("articles")) {
            val articles = json.getJSONArray("articles")
            d("result", articles.toString())
            (0 until articles.length()).forEach {
                val value = articles.getJSONObject(it)
                val title = value.getString("title")
                val description = value.getString("description")
                val url = value.getString("url")
                val imageUrl = value.getString("urlToImage")
                val result = NewsModel(title, description,imageUrl,url)
                news.add(result)
            }
        }
        adapter.notifyDataSetChanged()
    }



}
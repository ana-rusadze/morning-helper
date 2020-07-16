package com.example.morninghelper.networking

import android.util.Log.d
import android.view.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object NewsData {


    const val NEWS_KEY= "dfe20a7ab25f4a409a3c83f153674b6b"
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()


    private var service = retrofit.create(ApiServices::class.java)

    fun getRequest(
        path: String,
        parameters: MutableMap<String, String>,
        callBack: HoroscopeCallback
    ) {
        val call = service.getRequest(path, parameters)
        call!!.enqueue(
            onCallBack(
                 callBack
            )
        )


    }
    private fun onCallBack( horoscopeCallback: HoroscopeCallback) =
        object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                horoscopeCallback.onError("error", t.message.toString())
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                d("logSuccess", response.body().toString())
                horoscopeCallback.onSuccess(response.body().toString())
            }

        }



}
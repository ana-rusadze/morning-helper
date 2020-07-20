package com.example.morninghelper.networking

import android.util.Log.d
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object WeatherData {

    const val WEATHER_KEY= "a195b93fc70ab475d373a7bd1332fe20"
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()


    private var service = retrofit.create(ApiServices::class.java)

    fun getRequest(
        path: String,
        parameters: MutableMap<String, String>,
        callBack: ApiCallback
    ) {
        val call = service.getRequest(path, parameters)
        call!!.enqueue(
            onCallBack(
                 callBack
            )
        )
    }

    private fun onCallBack(apiCallback: ApiCallback) =
        object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                apiCallback.onError("error", t.message.toString())
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                d("logSuccess", response.body().toString())
                apiCallback.onSuccess(response.body().toString())
            }

        }



}
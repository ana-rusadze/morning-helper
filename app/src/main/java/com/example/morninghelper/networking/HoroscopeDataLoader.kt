package com.example.morninghelper.networking

import android.util.Log.d
import android.view.View
import com.example.morninghelper.R
import com.example.morninghelper.application.App
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


object HoroscopeDataLoader {
    private val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val request =
            chain.request().newBuilder().addHeader("x-rapidapi-host", "horoscope5.p.rapidapi.com")
                .addHeader(
                    "x-rapidapi-key",
                    App.instance.getContext().getString(R.string.horoscope_key)
                )
        chain.proceed(request.build())
    }
    private var retrofit = Retrofit.Builder()
        .baseUrl(App.instance.getContext().getString(R.string.horoscope_domain))
        .addConverterFactory(ScalarsConverterFactory.create())
        .client(httpClient.build())
        .build()
    private var service: ApiServices = retrofit.create(ApiServices::class.java)



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
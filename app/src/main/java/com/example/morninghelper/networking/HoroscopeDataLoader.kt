package com.example.morninghelper.networking

import android.util.Log.d
import android.view.View
import com.example.morninghelper.R
import com.example.morninghelper.application.App
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
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
        loaderView: View? = null,
        path: String,
        parameters: MutableMap<String, String>,
        callBack: HoroscopeCallback
    ) {
        loaderView!!.visibility = View.GONE
        val call = service.getRequest(path, parameters)
        call!!.enqueue(
            onCallBack(
                loaderView, callBack
            )
        )


    }

    private fun onCallBack(loaderView: View? = null, horoscopeCallback: HoroscopeCallback) =
        object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                horoscopeCallback.onError("error", t.message.toString())
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                loaderView!!.visibility = View.GONE
                d("logSuccess", response.body().toString())
                horoscopeCallback.onSuccess(response.body().toString())
            }

        }


}
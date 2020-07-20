package com.example.morninghelper.networking

import retrofit2.Call
import retrofit2.http.*


interface ApiServices {

    @GET("{path}")
    fun getRequest(
        @Path("path") path: String?,
        @QueryMap parameters: MutableMap<String, String>
    ): Call<String>?

}


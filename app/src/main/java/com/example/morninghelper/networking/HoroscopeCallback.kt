package com.example.morninghelper.networking

interface HoroscopeCallback {
    fun onSuccess(response:String){}
    fun onError(error:String, body:String){
    }
}
package com.example.morninghelper.networking

interface ApiCallback {
    fun onSuccess(response:String){}
    fun onError(error:String, body:String){
    }
}
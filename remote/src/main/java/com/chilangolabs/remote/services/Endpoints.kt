package com.chilangolabs.remote.services

import com.chilangolabs.remote.models.RequestSaveBTDevice
import com.chilangolabs.remote.models.ResponseSaveBTDevice
import com.chilangolabs.remote.models.ResponseSavedBTDevice
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Endpoints {

    @GET("devices")
    fun getSavedDevices(): Call<List<ResponseSavedBTDevice>>

    @POST("add")
    fun saveDevice(
        @Body rq: RequestSaveBTDevice
    ): Call<ResponseSaveBTDevice>

}
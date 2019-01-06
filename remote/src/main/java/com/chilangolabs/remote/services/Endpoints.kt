package com.chilangolabs.remote.services

import com.chilangolabs.remote.models.ResponseSavedDevice
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoints {

    @GET("{url}")
    fun getSavedDevices(@Path(value = "url", encoded = true) url: String): List<ResponseSavedDevice>

}
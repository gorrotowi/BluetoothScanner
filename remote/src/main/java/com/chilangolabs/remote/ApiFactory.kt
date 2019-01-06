package com.chilangolabs.remote

import com.chilangolabs.remote.models.RequestSaveBTDevice
import com.chilangolabs.remote.models.ResponseSaveBTDevice
import com.chilangolabs.remote.models.ResponseSavedBTDevice
import com.chilangolabs.remote.services.Endpoints
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory {

    private fun createRetrofit(): Retrofit? {
        return Retrofit.Builder().apply {
            baseUrl("https://grin-bluetooth-api.herokuapp.com/")
            addConverterFactory(GsonConverterFactory.create())
            client(createOkhttpClient())
        }.build()
    }

    private fun createOkhttpInterceptor() = Interceptor { chain ->
        return@Interceptor chain.proceed(chain.request().newBuilder().apply {
            addHeader("Content-Type", "application/json")
            addHeader("Accept", "application/json")
        }.build())
    }

    private fun createLogginInterceptor(): HttpLoggingInterceptor? {
        val loggingInterceptor = HttpLoggingInterceptor()
        val levelInterceptor = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return loggingInterceptor.setLevel(levelInterceptor)
    }

    private fun createOkhttpClient() = OkHttpClient.Builder().apply {
        interceptors().add(createOkhttpInterceptor())
        interceptors().add(createLogginInterceptor())
        cache(null)
    }.build()

    fun getSavedDeviceList(listener: OnRequestListener<List<ResponseSavedBTDevice?>?>) {
        val endpoint = createRetrofit()?.create(Endpoints::class.java)
        val call = endpoint?.getSavedDevices()
        call?.enqueue(object : Callback<List<ResponseSavedBTDevice>> {
            override fun onFailure(call: Call<List<ResponseSavedBTDevice>>, t: Throwable) {
                listener.onError(t)
            }

            override fun onResponse(
                call: Call<List<ResponseSavedBTDevice>>,
                response: Response<List<ResponseSavedBTDevice>>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    listener.onSuccess(response.body())
                } else {
                    listener.onError(Throwable("Error code: ${response.code()}"))
                }
            }
        })
    }

    fun saveDevice(rq: RequestSaveBTDevice, listener: OnRequestListener<ResponseSaveBTDevice?>) {
        val endpoints = createRetrofit()?.create(Endpoints::class.java)
        val call = endpoints?.saveDevice(rq)
        call?.enqueue(object : Callback<ResponseSaveBTDevice> {
            override fun onFailure(call: Call<ResponseSaveBTDevice>, t: Throwable) {
                listener.onError(t)
            }

            override fun onResponse(call: Call<ResponseSaveBTDevice>, response: Response<ResponseSaveBTDevice>) {
                if (response.isSuccessful && response.code() == 200) {
                    listener.onSuccess(response.body())
                } else {
                    listener.onError(Throwable("Error code: ${response.code()}"))
                }
            }

        })
    }

    companion object {

        val API: ApiFactory
            get() = ApiFactory()
    }

}
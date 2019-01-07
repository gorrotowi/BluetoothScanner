package com.chilangolabs.remote

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager


class CheckNetworkStatus {

    companion object {
        @SuppressLint("MissingPermission")
        fun checkIsConnected(ctx: Context): Boolean {
            val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetWork = connectivityManager?.activeNetworkInfo
            return activeNetWork != null && activeNetWork.isConnected
        }
    }

}
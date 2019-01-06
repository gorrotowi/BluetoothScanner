package com.chilangolabs.remote

interface OnRequestListener<in T> {
    fun onSuccess(response: T)
    fun onError(error: Throwable)
}
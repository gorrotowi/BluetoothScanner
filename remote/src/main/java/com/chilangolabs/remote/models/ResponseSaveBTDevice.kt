package com.chilangolabs.remote.models

import com.google.gson.annotations.SerializedName

data class ResponseSaveBTDevice(
    val name: String? = null,
    val address: String? = null,
    val strength: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null
)
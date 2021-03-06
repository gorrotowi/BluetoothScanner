package com.chilangolabs.remote.models

import com.google.gson.annotations.SerializedName

data class ResponseSavedBTDevice(
    @SerializedName("_id")
    val id: String? = null,
    val name: String? = null,
    val address: String? = null,
    val strength: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null
)

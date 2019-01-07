package com.chilangolabs.btscanner.models

data class BTDeviceModel(
    val name: String,
    val address: String,
    val strength: Int,
    val created_at: String? = null
)
package com.chilangolabs.btscanner.models

import java.util.*

data class BTDeviceModel(
    val name: String,
    val address: String,
    val strength: Int,
    val created_at: Date? = null
)
package com.chilangolabs.core.models

data class BTDevice(
    val name: String? = "Unknow name",
    val address: String? = "Unknow Address",
    val rssiStrength: Int? = -100
)
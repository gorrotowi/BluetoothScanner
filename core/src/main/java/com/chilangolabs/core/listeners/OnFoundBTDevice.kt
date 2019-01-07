package com.chilangolabs.core.listeners

import com.chilangolabs.core.models.BTDevice

interface OnFoundBTDevice {
    fun onFoundedDevice(device: BTDevice)
    fun onError(error: Throwable)
}
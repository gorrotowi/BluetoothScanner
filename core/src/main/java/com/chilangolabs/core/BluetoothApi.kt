package com.chilangolabs.core

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.util.Log
import com.chilangolabs.core.listeners.OnFoundBTDevice
import com.chilangolabs.core.models.BTDevice

class BluetoothApi {

    private var apiContext: Context? = null

    val REQUEST_ENABLE_BT = 101

    fun initialize(ctx: Context) {
        Log.e("Init BLe API", "**********")
        apiContext = ctx
        btManager = apiContext?.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = btManager?.adapter
        btLeScanner = btAdapter?.bluetoothLeScanner
    }

    @SuppressLint("MissingPermission")
    fun startScanning(deviceListener: OnFoundBTDevice) {
        Log.e("startScanning", "**********")
        listener = deviceListener
        btLeScanner?.startScan(callbackBLEScan)
    }

    @SuppressLint("MissingPermission")
    fun stopScanning() {
        Log.e("stopScanning", "**********")
        listener = null
        btLeScanner?.stopScan(callbackBLEScan)
    }

    @SuppressLint("MissingPermission")
    fun isBluetoothEnable(): Boolean = !(btAdapter != null && btAdapter?.isEnabled == false)

    fun enableBluetooth(ctx: Activity?) {
        val enableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        ctx?.startActivityForResult(enableIntent, REQUEST_ENABLE_BT)
    }

    private val callbackBLEScan = object : ScanCallback() {
        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            listener?.onError(Throwable("Ble Scanner Error: $errorCode"))
        }

        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            val resultDevice = result?.device
            val device = BTDevice(resultDevice?.name, resultDevice?.address, result?.rssi)
            listener?.onFoundedDevice(device)
        }
    }

    companion object {
        val instance: BluetoothApi
            get() = BluetoothApi()
        private var listener: OnFoundBTDevice? = null
        private var btAdapter: BluetoothAdapter? = null
        private var btLeScanner: BluetoothLeScanner? = null
        private var btManager: BluetoothManager? = null
    }
}
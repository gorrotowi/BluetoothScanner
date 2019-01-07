package com.chilangolabs.btscanner

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chilangolabs.btscanner.adapters.AdapterBluetoothDevices
import com.chilangolabs.btscanner.models.BTDeviceModel
import com.chilangolabs.core.BluetoothApi
import com.chilangolabs.core.listeners.OnFoundBTDevice
import com.chilangolabs.core.models.BTDevice
import com.kotlinpermissions.KotlinPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val dataBTDevice = mutableListOf<BTDeviceModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbarMain?.let {
            it.title = getString(R.string.app_name)
            setSupportActionBar(it)
        }

        initBT()
        initViews()

    }

    private fun initViews() {

        rcMainBluetooth?.layoutManager = LinearLayoutManager(this)
        rcMainBluetooth?.adapter = AdapterBluetoothDevices()

        progressLoad?.setProgressMessage("Scan BT devices", "Scanning devices")

        progressLoad?.setOnClickListener {
            checkBTIsEnable()
        }
    }

    private fun checkBTIsEnable() {
        if (BluetoothApi.instance.isBluetoothEnable()) {
            startBTScanning()
        } else {
            BluetoothApi.instance.enableBluetooth(this)
        }
    }

    private fun startBTScanning() {
        if (progressLoad?.isLoading() == true) {
            progressLoad?.finishProgress()
            BluetoothApi.instance.stopScanning()
        } else {
            progressLoad?.startProgress()
            BluetoothApi.instance.startScanning(object : OnFoundBTDevice {
                override fun onFoundedDevice(device: BTDevice) {
                    Log.e("BLE Device", device.toString())
                    val name = device.name ?: "Unknow Device "
                    val address = device.address ?: "Unknow Address"
                    val rssi = device.rssiStrength ?: -100
                    dataBTDevice.add(BTDeviceModel(name, address, rssi))
                    (rcMainBluetooth?.adapter as? AdapterBluetoothDevices)?.updateData(dataBTDevice)
                }

                override fun onError(error: Throwable) {
                    Log.e("Bluetooth Error", "Error", error)
                }
            })
        }
    }

    private fun initBT() {
        Log.e("permissions", "--------")
        KotlinPermissions.with(this)
            .permissions(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .onAccepted { permissions ->
                permissions.map {
                    Log.e("accepted", it)
                }
                BluetoothApi.instance.initialize(this@MainActivity)
            }
            .onDenied { permissions ->
                permissions.map {
                    Log.e("denied", it)
                }
                Toast.makeText(this, "Denied permissions", Toast.LENGTH_SHORT).show()
            }
            .onForeverDenied { permissions ->
                permissions.map {
                    Log.e("forever", it)
                }

            }
            .ask()
    }
}

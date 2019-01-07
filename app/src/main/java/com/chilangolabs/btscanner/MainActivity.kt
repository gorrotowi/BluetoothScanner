package com.chilangolabs.btscanner

import android.Manifest
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chilangolabs.btscanner.adapters.AdapterBluetoothDevices
import com.chilangolabs.btscanner.adapters.OnRecyclerClickItem
import com.chilangolabs.btscanner.models.BTDeviceModel
import com.chilangolabs.core.BluetoothApi
import com.chilangolabs.core.listeners.OnFoundBTDevice
import com.chilangolabs.core.models.BTDevice
import com.chilangolabs.remote.ApiFactory
import com.chilangolabs.remote.OnRequestListener
import com.chilangolabs.remote.models.RequestSaveBTDevice
import com.chilangolabs.remote.models.ResponseSaveBTDevice
import com.kotlinpermissions.KotlinPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val dataBTDevice = mutableListOf<BTDeviceModel>()
    var progressDialog: ProgressDialog? = null

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_see_remote) {
            //todo startactivity
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViews() {

        val adapter = AdapterBluetoothDevices()

        rcMainBluetooth?.layoutManager = LinearLayoutManager(this)
        rcMainBluetooth?.adapter = adapter

        adapter.setOnItemClickListener(object : OnRecyclerClickItem {
            override fun onItem(device: BTDeviceModel) {
                Log.e("Device", device.toString())
                uploadDevice(device)
            }
        })

        progressDialog = ProgressDialog(this)
        progressDialog?.isIndeterminate = true
        progressDialog?.setTitle("Wait a moment please...")

        progressLoad?.setProgressMessage("Scan BT devices", "Scanning devices. Tap to stop")

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
            stopScanning()
        } else {
            startScanning()
        }
    }

    fun startScanning() {
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

    fun stopScanning() {
        BluetoothApi.instance.stopScanning()
        progressLoad?.finishProgress()
    }

    private fun uploadDevice(device: BTDeviceModel) {
        progressDialog?.show()
        val rq = RequestSaveBTDevice(device.name, device.address, "${device.strength}dbm")
        ApiFactory.API.saveDevice(rq, object : OnRequestListener<ResponseSaveBTDevice?> {
            override fun onSuccess(response: ResponseSaveBTDevice?) {
                progressDialog?.dismiss()
                Toast.makeText(this@MainActivity, "Device saved", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: Throwable) {
                progressDialog?.dismiss()
                Toast.makeText(this@MainActivity, "Something goes wrong, please try again", Toast.LENGTH_SHORT).show()
            }
        })
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

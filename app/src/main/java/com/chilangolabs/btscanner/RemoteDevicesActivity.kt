package com.chilangolabs.btscanner

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chilangolabs.btscanner.adapters.AdapterBluetoothDevices
import com.chilangolabs.btscanner.models.BTDeviceModel
import com.chilangolabs.remote.ApiFactory
import com.chilangolabs.remote.CheckNetworkStatus
import com.chilangolabs.remote.OnRequestListener
import com.chilangolabs.remote.models.ResponseSavedBTDevice
import com.chilangolabs.widgets.gone
import com.chilangolabs.widgets.showErrorConnection
import kotlinx.android.synthetic.main.activity_remote_devices.*
import java.text.SimpleDateFormat
import java.util.*

class RemoteDevicesActivity : AppCompatActivity() {

    private var listDevices: List<BTDeviceModel>? = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote_devices)

        toolbarRemoteDevices?.let {
            it.title = getString(R.string.title_remote)
            setSupportActionBar(it)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        initViews()
        startRequest()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.menu_remote, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
            R.id.action_sort_date -> sortDeviceList(getSortedListByDate(listDevices))
            R.id.action_sort_name -> sortDeviceList(getSortedListByName(listDevices))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startRequest() {
        if (CheckNetworkStatus.checkIsConnected(this)) {
            swipeRefreshRemote?.isRefreshing = true
            ApiFactory.API.getSavedDeviceList(object : OnRequestListener<List<ResponseSavedBTDevice?>?> {
                override fun onSuccess(response: List<ResponseSavedBTDevice?>?) {
                    swipeRefreshRemote?.isRefreshing = false
                    listDevices = response?.map { responseSavedBTDevice ->
                        val name = responseSavedBTDevice?.name ?: "Unknow Device"
                        val address = responseSavedBTDevice?.address ?: "Unknow Address"
                        val rssi = try {
                            responseSavedBTDevice?.strength
                                ?.toUpperCase()
                                ?.replace("D", "")
                                ?.replace("B", "")
                                ?.replace("M", "")
                                ?.toInt()
                                ?: -100
                        } catch (e: NumberFormatException) {
                            -100
                        }
                        val createdAt = responseSavedBTDevice?.createdAt ?: ""
                        val dateCreated = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).getDate(createdAt)
                        BTDeviceModel(name, address, rssi, dateCreated)
                    }

                    listDevices?.let { (rcRemote?.adapter as? AdapterBluetoothDevices)?.updateData(it) }
                    emptyView?.gone()

                }

                override fun onError(error: Throwable) {
                    swipeRefreshRemote?.isRefreshing = false
                    Toast.makeText(
                        this@RemoteDevicesActivity,
                        "Something goes wrong, please try again",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })
        } else {
            swipeRefreshRemote?.isRefreshing = false
            showErrorConnection(R.string.error_connection)
        }

    }

    private fun initViews() {
        val adapter = AdapterBluetoothDevices()
        rcRemote?.layoutManager = LinearLayoutManager(this)
        rcRemote?.adapter = adapter

        swipeRefreshRemote?.setOnRefreshListener {
            startRequest()
        }
    }

    fun getSortedListByDate(list: List<BTDeviceModel>?) = list?.sortedByDescending { it.created_at }
    fun getSortedListByName(list: List<BTDeviceModel>?) = list?.sortedByDescending { it.name }

    fun SimpleDateFormat.getDate(date: String) = this.parse(date)

    private fun sortDeviceList(func: List<BTDeviceModel>?) {
        val adapter = rcRemote?.adapter as? AdapterBluetoothDevices
        adapter?.clearData()
        func?.let { adapter?.updateData(it) }
    }

}

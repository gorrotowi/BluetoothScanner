package com.chilangolabs.btscanner.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chilangolabs.btscanner.R
import com.chilangolabs.btscanner.models.BTDeviceModel
import com.chilangolabs.widgets.BluetoothSignalScale
import com.chilangolabs.widgets.LatoTextView
import com.chilangolabs.widgets.inflate

class AdapterBluetoothDevices() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<BTDeviceModel>? = null
    var itemListener: OnRecyclerClickItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        0 -> SaveViewHolder(parent.inflate(R.layout.item_bluetooth_device))
        1 -> RemoteViewHolder(parent.inflate(R.layout.item_bluetooth_remote_device))
        else -> SaveViewHolder(parent.inflate(R.layout.item_bluetooth_device))
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        data?.let { deviceList ->
            when (holder) {
                is SaveViewHolder -> {
                    holder.bindView(deviceList[position])
                }
                is RemoteViewHolder -> {
                    holder.bindView(deviceList[position])
                }
            }
            holder.itemView.setOnClickListener {
                itemListener?.onItem(deviceList[position])
            }
        }
    }

    fun updateData(newData: List<BTDeviceModel>) {
        data = newData
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnRecyclerClickItem) {
        itemListener = listener
    }

    override fun getItemViewType(position: Int): Int = when {
        data?.get(position)?.created_at == null -> 0
        else -> 1
    }

    class SaveViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(device: BTDeviceModel) {
            val btSignal = itemView.findViewById<BluetoothSignalScale>(R.id.btnSignalScaleItemBTDevice)
            val txtName = itemView.findViewById<LatoTextView>(R.id.txtItemBTDeviceName)
            val txtAddress = itemView.findViewById<LatoTextView>(R.id.txtItemBTDeviceAddress)
            view.context?.let { ctx ->
                txtName?.text = String.format(ctx.getString(R.string.device_name), device.name)
                txtAddress?.text = String.format(ctx.getString(R.string.device_address), device.address)
            }
            btSignal?.setBTScale(device.strength)
        }
    }

    class RemoteViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(device: BTDeviceModel) {

        }
    }
}

interface OnRecyclerClickItem {
    fun onItem(device: BTDeviceModel)
}
package com.chilangolabs.btscanner.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chilangolabs.btscanner.R
import com.chilangolabs.btscanner.models.BTDeviceModel
import com.chilangolabs.widgets.BluetoothSignalScale
import com.chilangolabs.widgets.LatoTextView
import com.chilangolabs.widgets.inflate

class AdapterBluetoothDevices() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<BTDeviceModel> = mutableListOf()
    var itemListener: OnRecyclerClickItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        0 -> SaveViewHolder(parent.inflate(R.layout.item_bluetooth_device))
        1 -> RemoteViewHolder(parent.inflate(R.layout.item_bluetooth_remote_device))
        else -> SaveViewHolder(parent.inflate(R.layout.item_bluetooth_device))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SaveViewHolder -> {
                holder.bindView(data[position])
            }
            is RemoteViewHolder -> {
                holder.bindView(data[position])
            }
        }
        holder.itemView.setOnClickListener {
            itemListener?.onItem(data[position])
        }

        holder.itemView.findViewById<TextView>(R.id.btnItemBTDeviceUpload)?.setOnClickListener {
            itemListener?.onItem(data[position])
        }
    }

    fun updateData(newData: List<BTDeviceModel>) {
        val diffCallBack = BTDeviceDiffCallBack(data, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        data = newData
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(listener: OnRecyclerClickItem) {
        itemListener = listener
    }

    override fun getItemViewType(position: Int): Int = when {
        data[position].created_at == null -> 0
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
            val btSignal = itemView.findViewById<BluetoothSignalScale>(R.id.btnSignalScaleItemBTDevice)
            val txtName = itemView.findViewById<LatoTextView>(R.id.txtItemBTDeviceName)
            val txtAddress = itemView.findViewById<LatoTextView>(R.id.txtItemBTDeviceAddress)
            val txtCreatedDate = itemView.findViewById<LatoTextView>(R.id.txtItemBTDeviceCreateDate)
            val txtCreatedHour = itemView.findViewById<LatoTextView>(R.id.txtItemBTDeviceCreateHour)
            view.context?.let { ctx ->
                txtName?.text = String.format(ctx.getString(R.string.device_name), device.name)
                txtAddress?.text = String.format(ctx.getString(R.string.device_address), device.address)
            }
            btSignal?.setBTScale(device.strength)
        }
    }
}

interface OnRecyclerClickItem {
    fun onItem(device: BTDeviceModel)
}
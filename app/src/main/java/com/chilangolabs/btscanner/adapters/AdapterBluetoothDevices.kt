package com.chilangolabs.btscanner.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chilangolabs.btscanner.R
import com.chilangolabs.btscanner.models.BTDeviceModel
import com.chilangolabs.widgets.BluetoothSignalScale
import com.chilangolabs.widgets.inflate
import java.util.*

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

    fun clearData() {
        data = mutableListOf()
        notifyDataSetChanged()
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
            val txtName = itemView.findViewById<TextView>(R.id.txtItemBTDeviceName)
            val txtAddress = itemView.findViewById<TextView>(R.id.txtItemBTDeviceAddress)
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
            val txtName = itemView.findViewById<TextView>(R.id.txtItemBTDeviceName)
            val txtAddress = itemView.findViewById<TextView>(R.id.txtItemBTDeviceAddress)
            val txtCreatedDate = itemView.findViewById<TextView>(R.id.txtItemBTDeviceCreateDate)
            val txtCreatedHour = itemView.findViewById<TextView>(R.id.txtItemBTDeviceCreateHour)

            view.context?.let { ctx ->
                txtName?.text = String.format(ctx.getString(R.string.device_name), device.name)
                txtAddress?.text = String.format(ctx.getString(R.string.device_address), device.address)
            }
            btSignal?.setBTScale(device.strength)
            txtCreatedDate?.text = device.created_at?.getDateString()
            txtCreatedHour?.text = device.created_at?.getDateHour()
        }
    }
}

fun Date.getDateString(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    return "$year/$month/$day"
}

fun Date.getDateHour(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    val seconds = calendar.get(Calendar.SECOND)
    return "HRS $hour:$minute:$seconds"
}

interface OnRecyclerClickItem {
    fun onItem(device: BTDeviceModel)
}
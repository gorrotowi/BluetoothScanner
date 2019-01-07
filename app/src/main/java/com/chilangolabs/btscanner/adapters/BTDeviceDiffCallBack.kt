package com.chilangolabs.btscanner.adapters

import androidx.recyclerview.widget.DiffUtil
import com.chilangolabs.btscanner.models.BTDeviceModel

class BTDeviceDiffCallBack(val oldListDevice: List<BTDeviceModel>, val newListDevice: List<BTDeviceModel>) :
    DiffUtil.Callback() {


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldListDevice[oldItemPosition].address == newListDevice[newItemPosition].address

    override fun getOldListSize(): Int = oldListDevice.size

    override fun getNewListSize(): Int = newListDevice.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldListDevice[oldItemPosition].address == newListDevice[newItemPosition].address
}
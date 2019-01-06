package com.chilangolabs.widgets

import android.content.Context
import android.net.wifi.WifiManager
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

class BluetoothSignalScale @JvmOverloads constructor(ctx: Context, attributeSet: AttributeSet? = null) :
    FrameLayout(ctx, attributeSet) {

    private lateinit var imgSignalScale: ImageView
    private lateinit var txtSignalRSSI: TextView

    init {
        initializeLayout(ctx)
    }

    private fun initializeLayout(ctx: Context) {
        View.inflate(ctx, R.layout.layout_bluetooth_signal, this)?.let { view ->
            imgSignalScale = view.findViewById(R.id.imgBTSignalScale)
            txtSignalRSSI = view.findViewById(R.id.txtBTSignalRSSI)
        }
    }

    fun setBTScale(rssi: Int) {
        txtSignalRSSI.text = "$rssi"
        imgSignalScale.setSignalScale(rssi)
    }

    private fun ImageView.setSignalScale(rssi: Int) {
        val scale = getScaleFromRssi(rssi)
        val drawableScale = getDrawableScale(scale)
        this.setImageResource(drawableScale)
    }

    private fun getScaleFromRssi(rssi: Int): Int = WifiManager.calculateSignalLevel(rssi, 7)

    private fun getDrawableScale(scale: Int): Int = when (scale) {
        0 -> R.drawable.ic_hexagon_empty
        1 -> R.drawable.ic_hexagon_one
        2 -> R.drawable.ic_hexagon_two
        3 -> R.drawable.ic_hexagone_three
        4 -> R.drawable.ic_hexagon_four
        5 -> R.drawable.ic_hexagon_five
        6 -> R.drawable.ic_hexagon_six
        else -> R.drawable.ic_hexagon_empty
    }

}
package com.chilangolabs.btscanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chilangolabs.widgets.BluetoothSignalScale
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btd1?.findViewById<BluetoothSignalScale>(R.id.btnSignalScaleItemBTDevice)?.setBTScale(-100)
        btd2?.findViewById<BluetoothSignalScale>(R.id.btnSignalScaleItemBTDevice)?.setBTScale(-90)
        btd3?.findViewById<BluetoothSignalScale>(R.id.btnSignalScaleItemBTDevice)?.setBTScale(-80)
        btd4?.findViewById<BluetoothSignalScale>(R.id.btnSignalScaleItemBTDevice)?.setBTScale(-75)
        btd5?.findViewById<BluetoothSignalScale>(R.id.btnSignalScaleItemBTDevice)?.setBTScale(-70)
        btd6?.findViewById<BluetoothSignalScale>(R.id.btnSignalScaleItemBTDevice)?.setBTScale(-60)
        btd7?.findViewById<BluetoothSignalScale>(R.id.btnSignalScaleItemBTDevice)?.setBTScale(-55)

    }
}

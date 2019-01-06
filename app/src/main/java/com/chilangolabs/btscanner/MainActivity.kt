package com.chilangolabs.btscanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        loaderActionView?.setProgressMessage("Scan devices", "Scanning...")
        loaderActionView?.setOnClickListener {
            if (loaderActionView?.isLoading() == true) {
                loaderActionView?.finishProgress()
            } else {
                loaderActionView?.startProgress()
            }
        }
//        loaderActionView?.finishProgress()
    }
}

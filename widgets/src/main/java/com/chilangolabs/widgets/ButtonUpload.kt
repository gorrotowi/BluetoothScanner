package com.chilangolabs.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.Button

class ButtonUpload @JvmOverloads constructor(ctx: Context, attributeSet: AttributeSet? = null) :
    Button(ctx, attributeSet) {

    init {
        initializeStyle()
    }

    private fun initializeStyle() {
        this.setBackgroundResource(R.drawable.selector_cloud_upload)
        this.text = ""
    }

}
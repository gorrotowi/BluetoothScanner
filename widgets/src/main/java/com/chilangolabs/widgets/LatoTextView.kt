package com.chilangolabs.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class LatoTextView @JvmOverloads constructor(ctx: Context, attrs: AttributeSet? = null) : TextView(ctx, attrs) {
    init {
        FontManager(ctx).initStyle(this, attrs)
    }
}
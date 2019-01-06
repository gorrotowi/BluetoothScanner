package com.chilangolabs.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class FontManager(val ctx: Context?) {

    fun initStyle(view: View, attributeSet: AttributeSet? = null) {
        if (attributeSet != null) {
            val typedArray = ctx?.theme?.obtainStyledAttributes(attributeSet, R.styleable.lato_font, 0, 0)
            val tpPosition = typedArray?.getInteger(R.styleable.lato_font_stylefont, defaultFont) ?: defaultFont
            val tp = Typeface.createFromAsset(ctx?.assets, "fonts/${fontList[tpPosition]}")
            setTypeFace(view, tp)
            typedArray?.recycle()
        } else {
            setTypeFace(view)
        }
    }

    private fun setTypeFace(
        view: View,
        tp: Typeface = Typeface.createFromAsset(ctx?.assets, "fonts/Lato-Regular.ttf")
    ) {
        when (view) {
            is TextView -> view.typeface = tp
            is EditText -> view.typeface = tp
            is Button -> view.typeface = tp
        }
    }

    companion object {
        const val defaultFont = 13
        val fontList = arrayListOf(
            "Lato-Black.ttf",
            "Lato-BlackItalic.ttf",
            "Lato-Bold.ttf",
            "Lato-BoldItalic.ttf",
            "Lato-Hairline.ttf",
            "Lato-HairlineItalic.ttf",
            "Lato-Heavy.ttf",
            "Lato-HeavyItalic.ttf",
            "Lato-Italic.ttf",
            "Lato-Light.ttf",
            "Lato-LightItalic.ttf",
            "Lato-Medium.ttf",
            "Lato-MediumItalic.ttf",
            "Lato-Regular.ttf",
            "Lato-Semibold.ttf",
            "Lato-SemiboldItalic.ttf",
            "Lato-Thin.ttf",
            "Lato-ThinItalic.ttf"
        )
    }

}
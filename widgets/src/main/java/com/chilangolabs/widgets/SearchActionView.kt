package com.chilangolabs.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.widget.ContentLoadingProgressBar

class ProgressActionView @JvmOverloads constructor(ctx: Context, attributeSet: AttributeSet? = null) :
    FrameLayout(ctx, attributeSet) {

    private lateinit var txtLoadData: LatoTextView
    private lateinit var imgLoadData: ImageView
    private lateinit var progressLoadData: ContentLoadingProgressBar

    private lateinit var stanbayMessage: String
    private lateinit var loadingMessage: String

    private var isLoagingProgress: Boolean = false


    init {
        initializeLayout(ctx)
    }

    private fun initializeLayout(ctx: Context) {
        View.inflate(ctx, R.layout.layout_load_data, this)?.let { view ->
            txtLoadData = view.findViewById(R.id.txtLoadData)
            imgLoadData = view.findViewById(R.id.imgLoadData)
            progressLoadData = view.findViewById(R.id.progressLoadData)
        }
    }

    fun setProgressMessage(stanbayMessage: String, loadingMessage: String) {
        this.stanbayMessage = stanbayMessage
        this.loadingMessage = loadingMessage
        txtLoadData.text = this.stanbayMessage
    }

    fun startProgress() {
        if (::loadingMessage.isInitialized) {
            setProgress(loadingMessage, true)
        } else {
            throw IllegalArgumentException("The loadingMessage must been initialized, call setProgressMessage() function before using startProgress()")
        }
    }

    fun finishProgress() {
        if (::stanbayMessage.isInitialized) {
            setProgress(stanbayMessage, false)
        } else {
            throw IllegalArgumentException("The stanbyMessage must been initialized, call setProgressMessage() function before using finishProgress()")
        }
    }

    fun isLoading() = isLoagingProgress

    private fun setProgress(message: String, isLoading: Boolean = false) {
        txtLoadData.text = message
        isLoagingProgress = isLoading
        return if (isLoading) {
            imgLoadData.visibility = View.INVISIBLE
            progressLoadData.show()
        } else {
            imgLoadData.visibility = View.VISIBLE
            progressLoadData.hide()
        }
    }

}
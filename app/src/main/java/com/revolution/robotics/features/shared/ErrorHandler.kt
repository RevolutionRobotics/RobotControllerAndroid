package com.revolution.robotics.features.shared

import android.content.Context
import android.widget.Toast
import com.revolution.robotics.R

class ErrorHandler {

    private var context: Context? = null

    fun registerContext(context: Context) {
        this.context = context
    }

    fun releaseContext(context: Context) {
        if (this.context == context) {
            this.context = null
        }
    }

    fun onError(customMessage: Int = R.string.error_general) {
        context?.let {
            Toast.makeText(it, customMessage, Toast.LENGTH_LONG).show()
        }
    }

    fun onError(customMessage: String) {
        context?.let {
            Toast.makeText(it, customMessage, Toast.LENGTH_LONG).show()
        }
    }
}

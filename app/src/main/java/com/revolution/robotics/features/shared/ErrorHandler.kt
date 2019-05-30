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

    fun onError() {
        Toast.makeText(context, R.string.error_general, Toast.LENGTH_LONG).show()
    }
}

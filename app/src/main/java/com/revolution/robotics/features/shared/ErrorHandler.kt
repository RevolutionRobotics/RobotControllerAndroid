package com.revolution.robotics.features.shared

import android.content.Context
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import com.revolution.robotics.BuildConfig
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

    fun onError(throwable: Throwable? = null, customMessage: Int = R.string.error_general) {

        if (throwable != null && !BuildConfig.DEBUG) {
            Crashlytics.logException(throwable)
        }

        context?.let { Toast.makeText(it, customMessage, Toast.LENGTH_LONG).show() }
    }

    fun onError(throwable: Throwable? = null, customMessage: String) {
        if (throwable != null && !BuildConfig.DEBUG) {
            Crashlytics.logException(throwable)
        }
        context?.let { Toast.makeText(it, customMessage, Toast.LENGTH_LONG).show() }
    }
}

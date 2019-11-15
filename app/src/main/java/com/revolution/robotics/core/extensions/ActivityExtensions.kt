package com.revolution.robotics.core.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import com.revolution.robotics.R
import com.revolution.robotics.features.shared.ErrorHandler

@Suppress("SwallowedException")
fun Activity.openUrl(url: String, errorHandler: ErrorHandler? = null) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    } catch (exception: ActivityNotFoundException) {
        errorHandler?.onError(exception, R.string.error_cannot_open_url)
    }
}

package com.revolution.robotics.analytics

import android.net.Uri
import androidx.core.os.bundleOf
import java.io.File

fun reportUploadedToBrain(reporter:Reporter, name:String, uri:Uri, startTimeMillis:Long) {
    val size = File(uri.path).length()
    val time = System.currentTimeMillis() - startTimeMillis
    val speed = size * 1000L / time
    reporter.reportEvent(
        Reporter.Event.UPLOADED_TO_BRAIN,
        bundleOf(
            "name" to name,
            "size" to size,
            "time" to time / 1000.0f,  // transfer time in seconds
            "speed" to speed  // transfer speed in byte per second
        )
    )
}
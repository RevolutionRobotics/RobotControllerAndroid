package com.revolution.robotics.core.interactor.firebase

import android.net.Uri
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.features.shared.ErrorHandler
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.net.URL

class FirebaseFileDownloader(
    private val applicationContextProvider: ApplicationContextProvider,
    private val errorHandler: ErrorHandler
) {

    private var onResponse: ((uri: Uri) -> Unit)? = null
    private var onError: ((throwable: Throwable) -> Unit)? = null

    fun downloadFirestoreFile(
        outputFileName: String,
        gsUrl: String,
        onResponse: (uri: Uri) -> Unit
    ) {
        downloadFirestoreFile(outputFileName, gsUrl, onResponse, null)
    }

    fun downloadFirestoreFile(
        outputFileName: String,
        url: String,
        onResponse: (uri: Uri) -> Unit,
        onError: ((throwable: Throwable) -> Unit)?
    ) {
        this.onResponse = onResponse
        this.onError = onError

        val outputFile = File("${applicationContextProvider.applicationContext.filesDir}/$outputFileName")
        try {
            URL(url).openStream().use { input ->
                FileOutputStream(outputFile).use { output ->
                    input.copyTo(output)
                    this.onResponse?.invoke(Uri.fromFile(outputFile))
                }
            }
        } catch (exception: Exception) {
            this.onError?.invoke(exception) ?: errorHandler.onError(exception)
        }
    }
}

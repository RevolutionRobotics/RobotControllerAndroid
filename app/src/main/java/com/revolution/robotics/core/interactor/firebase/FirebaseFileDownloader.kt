package com.revolution.robotics.core.interactor.firebase

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.features.shared.ErrorHandler
import java.io.File

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
        gsUrl: String,
        onResponse: (uri: Uri) -> Unit,
        onError: ((throwable: Throwable) -> Unit)?
    ) {
        this.onResponse = onResponse
        this.onError = onError

        val reference =
            FirebaseStorage.getInstance().getReferenceFromUrl(gsUrl)
        val outputFile = File("${applicationContextProvider.applicationContext.filesDir}/$outputFileName")
        reference.getFile(outputFile).addOnSuccessListener {
            this.onResponse?.invoke(Uri.fromFile(outputFile))
        }.addOnFailureListener {
            this.onError?.invoke(it) ?: errorHandler.onError()
        }
    }
}

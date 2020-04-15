package com.revolution.robotics.core.interactor.api

import android.net.Uri
import com.revolution.robotics.core.interactor.Interactor
import com.revolution.robotics.core.utils.FileDownloader
import java.net.URL


class DownloadFileInteractor(
    private val fileDownloader: FileDownloader,
    private val downloadUrl: URL,
    private val outputFileName: String
) : Interactor<Uri>() {
    override fun getData(): Uri {
        return fileDownloader.download(downloadUrl, outputFileName)
    }
}
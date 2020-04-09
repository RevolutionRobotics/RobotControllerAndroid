package com.revolution.robotics.core.interactor.api

import com.revolution.robotics.core.utils.FileDownloader
import java.net.URL

class DownloadFileInteractorBuilder(
    fileDownloader: FileDownloader
) {
    private var downloader = fileDownloader
    private var interactors = HashMap<Pair<URL, String>, DownloadFileInteractor>()

    fun createDownloader(src: URL, dst: String): DownloadFileInteractor {
        return interactors.getOrPut(Pair(src, dst)) {
            DownloadFileInteractor(downloader, src, dst)
        }
    }
}
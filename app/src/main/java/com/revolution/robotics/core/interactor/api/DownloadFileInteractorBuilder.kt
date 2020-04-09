package com.revolution.robotics.core.interactor.api

import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.core.utils.FileDownloader
import java.net.URL

class DownloadFileInteractorBuilder(
    applicationContextProvider: ApplicationContextProvider
) {
    private var downloader = FileDownloader(applicationContextProvider)
    private var interactors = HashMap<Pair<URL, String>, DownloadFileInteractor>()

    fun createDownloader(src: URL, dst: String): DownloadFileInteractor {
        return interactors.getOrPut(Pair(src, dst)) {
            DownloadFileInteractor(downloader, src, dst)
        }
    }
}
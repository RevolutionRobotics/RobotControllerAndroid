package com.revolution.robotics.core.interactor.api

import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.core.utils.FileDownloader
import java.lang.ref.WeakReference
import java.net.URL

class DownloadFileInteractorBuilder(
    applicationContextProvider: ApplicationContextProvider
) {
    private var downloader = FileDownloader(applicationContextProvider)
    private var interactors = HashMap<Pair<URL, String>, WeakReference<DownloadFileInteractor>>()

    fun createDownloader(src: URL, dst: String): DownloadFileInteractor {
        val key = Pair(src, dst)
        val weakRef = interactors.getOrPut(key) {
            WeakReference(DownloadFileInteractor(downloader, src, dst))
        }

        var instance = weakRef.get()
        if (instance == null) {
            instance = DownloadFileInteractor(downloader, src, dst)
            interactors[key] = WeakReference(instance)
        }

        return instance
    }
}
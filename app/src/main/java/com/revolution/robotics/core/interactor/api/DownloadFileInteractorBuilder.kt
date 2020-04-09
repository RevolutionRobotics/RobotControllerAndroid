package com.revolution.robotics.core.interactor.api

import com.revolution.robotics.core.utils.FileDownloader
import java.net.URL

class DownloadFileInteractorBuilder(
    fileDownloader: FileDownloader
) {
    private var downloader = fileDownloader
    private var interactors = HashMap<Pair<URL, String>, DownloadFileInteractor>()

    fun createDownloader(src: URL, dst: String): DownloadFileInteractor {
        // Store the created interactors in a hash map so that a
        // single interactor belongs to a (source, destination) pair.
        // This results in a similar solution to having a different interactor instance for
        // each download, except the instances are created dynamically for cases where one or both
        // of the file paths are not known in advance.
        return interactors.getOrPut(Pair(src, dst)) {
            DownloadFileInteractor(downloader, src, dst)
        }
    }
}
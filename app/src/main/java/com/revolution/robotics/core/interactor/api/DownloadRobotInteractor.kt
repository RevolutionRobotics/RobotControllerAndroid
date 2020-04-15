package com.revolution.robotics.core.interactor.api

import android.content.Context
import android.util.Log
import com.revolution.robotics.core.cache.ImageCache
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.core.interactor.Interactor
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.zip.ZipFile


class DownloadRobotInteractor(
    private val applicationContextProvider: ApplicationContextProvider,
    private val imageCache: ImageCache,
    private val imageDownloader: ImageDownloader
) : Interactor<Unit>() {

    var robot: Robot? = null

    override fun getData() {
        val missingImages =
            robot?.buildSteps?.map { it.image }?.filter { it != null && !imageCache.isSaved(it) }
                ?.toList()
        missingImages?.let {
            if (it.size > 5)
                downloadRobotZip()
            else {
                imageDownloader.downloadImages(it) > 0
            }
        }
    }

    private fun downloadRobotZip() {
        val outputFile =
            File("${applicationContextProvider.applicationContext.filesDir}/" + robot?.id + ".zip")
        URL(robot?.buildStepsArchive).openStream().use { input ->
            FileOutputStream(outputFile).use { output ->
                input.copyTo(output)
                unzip(
                    outputFile.absolutePath,
                    applicationContextProvider.applicationContext.getDir(
                        "ImageCache",
                        Context.MODE_PRIVATE
                    ).absolutePath
                )
            }
        }
    }


    private fun unzip(
        zipFile: String?,
        targetLocation: String
    ) {
        ZipFile(zipFile).use { zip ->
            zip.entries().asSequence().forEach { entry ->
                val fileName = imageCache.urlToFileName(robot?.buildSteps?.find {
                    it.image?.substringAfterLast("/") == entry.name
                }?.image)
                Log.d("ZIP", "Unzipping " + entry.name + " to " + fileName)
                zip.getInputStream(entry).use { input ->
                    File(targetLocation, fileName).outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            }
        }
    }

    private fun dirChecker(targetLocation: String) {
        val file = File(targetLocation)
        file.mkdirs()
    }
}
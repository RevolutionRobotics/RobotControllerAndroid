package com.revolution.robotics.core.interactor.api

import android.content.Context
import android.util.Log
import com.revolution.robotics.core.cache.ImageCache
import com.revolution.robotics.core.domain.remote.Challenge
import com.revolution.robotics.core.domain.remote.ChallengeCategory
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.core.interactor.Interactor
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.zip.ZipFile


class DownloadChallengeCategoryInteractor(
    private val applicationContextProvider: ApplicationContextProvider,
    private val imageCache: ImageCache
) : Interactor<Unit>() {

    var challengeCategory: ChallengeCategory? = null

    override fun getData() {
        for (challenge in challengeCategory?.challenges ?: emptyList()) {
            val missingImages =
                challenge.steps.map { it.image }.filter { it != null && !imageCache.isSaved(it) }
                    .toList()
            if (missingImages.isNotEmpty()) {
                    downloadChallengeZip(challenge)
            }
        }
    }

    private fun downloadChallengeZip(challenge: Challenge) {
        val outputFile =
            File("${applicationContextProvider.applicationContext.filesDir}/" + challenge.id + ".zip")
        URL(challenge.stepsArchive).openStream().use { input ->
            FileOutputStream(outputFile).use { output ->
                input.copyTo(output)
                unzip(
                    challenge,
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
        challenge: Challenge,
        zipFile: String?,
        targetLocation: String
    ) {
        ZipFile(zipFile).use { zip ->
            zip.entries().asSequence().forEach { entry ->
                val fileName = imageCache.urlToFileName(challenge.steps.find {
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
}
package com.revolution.robotics.features.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.cache.ImageCache
import com.revolution.robotics.core.cache.RemoteDataCache
import com.revolution.robotics.core.interactor.api.DownloadChallengesInteractor
import com.revolution.robotics.core.interactor.api.DownloadRobotsInteractor

class SplashPresenter(
    private val downloadRobotsInteractor: DownloadRobotsInteractor,
    private val downloadChallengesInteractor: DownloadChallengesInteractor,
    private val imageCache: ImageCache,
    private val remoteDataCache: RemoteDataCache
) : SplashMvp.Presenter {

    override var view: SplashMvp.View? = null
    override var model: ViewModel? = null

    override fun register(view: SplashMvp.View, model: ViewModel?) {
        super.register(view, model)
        downloadRobots()
    }

    private fun downloadRobots() {
        downloadRobotsInteractor.execute(
            onResponse = {
                downloadChallenges()
            },
            onError = {
                downloadChallenges()
            }
        )
    }

    private fun downloadChallenges() {
        downloadChallengesInteractor.execute(
            onResponse = {
                deleteUnusedImages()
                this.view?.startApp()
            },
            onError = {
                this.view?.startApp()
            }
        )
    }

    private fun deleteUnusedImages() {
        val usedImages = remoteDataCache.getAllImageUrls()
        val downloadedImages = imageCache.getAllImages()
        for (imageFile in downloadedImages) {
            if (!usedImages.contains(imageFile)) {
                imageCache.deleteImage(imageFile)
            }
        }
    }
}

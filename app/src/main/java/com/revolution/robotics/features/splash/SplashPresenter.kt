package com.revolution.robotics.features.splash

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.cache.RemoteDataCache
import com.revolution.robotics.core.interactor.api.DownloadChallengesInteractor
import com.revolution.robotics.core.interactor.api.DownloadRobotsInteractor

class SplashPresenter(
    private val downloadRobotsInteractor: DownloadRobotsInteractor,
    private val downloadChallengesInteractor: DownloadChallengesInteractor
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
                this.view?.startApp()
            },
            onError = {
                this.view?.startApp()
            }
        )
    }
}

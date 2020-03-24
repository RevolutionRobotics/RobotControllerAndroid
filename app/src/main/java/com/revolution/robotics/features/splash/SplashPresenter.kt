package com.revolution.robotics.features.splash

import androidx.lifecycle.ViewModel
import com.revolution.robotics.BuildConfig
import com.revolution.robotics.core.cache.RemoteDataCache
import com.revolution.robotics.core.interactor.api.DownloadChallengesInteractor
import com.revolution.robotics.core.interactor.api.DownloadRobotsInteractor
import com.revolution.robotics.core.interactor.api.RefreshDataCacheInteractor

class SplashPresenter(
    private val refreshDataCacheInteractor: RefreshDataCacheInteractor,
    private val downloadRobotsInteractor: DownloadRobotsInteractor,
    private val downloadChallengesInteractor: DownloadChallengesInteractor,
    private val remoteDataCache: RemoteDataCache
) : SplashMvp.Presenter {

    override var view: SplashMvp.View? = null
    override var model: ViewModel? = null

    override fun register(view: SplashMvp.View, model: ViewModel?) {
        super.register(view, model)
        refreshDataCacheInteractor.execute(
            onResponse = {
                if (remoteDataCache.data.minVersion.android > BuildConfig.VERSION_CODE) {
                    this.view?.showUpdateNeededDialog()
                } else {
                    downloadRobots()
                }
            },
            onError = {
                downloadRobots()
            }
        )
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

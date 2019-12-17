package com.revolution.robotics.features.splash

import androidx.lifecycle.ViewModel
import com.revolution.robotics.BuildConfig
import com.revolution.robotics.core.cache.RemoteDataCache
import com.revolution.robotics.core.interactor.api.RefreshDataCacheInteractor

class SplashPresenter(
    private val refreshDataCacheInteractor: RefreshDataCacheInteractor,
    private val remoteDataCache: RemoteDataCache
) : SplashMvp.Presenter {

    override var view: SplashMvp.View? = null
    override var model: ViewModel? = null

    override fun register(view: SplashMvp.View, model: ViewModel?) {
        super.register(view, model)
        refreshDataCacheInteractor.execute (
            onResponse = {
                if (remoteDataCache.data.minVersion.android > BuildConfig.VERSION_CODE) {
                    this.view?.showUpdateNeededDialog()
                } else {
                    this.view?.startApp()
                }
            },
            onError = {
                this.view?.startApp()
            }
        )
    }
}

package com.revolution.robotics.features.splash

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.interactor.FirebaseInitInteractor

class SplashPresenter(private val firebaseInitInteractor: FirebaseInitInteractor) : SplashMvp.Presenter {

    override var view: SplashMvp.View? = null
    override var model: ViewModel? = null

    override fun register(view: SplashMvp.View, model: ViewModel?) {
        super.register(view, model)
        firebaseInitInteractor.execute({
            this.view?.startApp()
        }, {
            // TODO Error handling
        })
    }
}

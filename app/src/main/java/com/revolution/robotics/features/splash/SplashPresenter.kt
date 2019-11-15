package com.revolution.robotics.features.splash

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.revolution.robotics.core.interactor.FirebaseInitInteractor
import com.revolution.robotics.core.interactor.firebase.FirebaseConnectionInteractor
import com.revolution.robotics.core.interactor.firebase.ForceUpdateInteractor

class SplashPresenter(
    private val firebaseConnectionInteractor: FirebaseConnectionInteractor,
    private val firebaseInitInteractor: FirebaseInitInteractor,
    private val forceUpdateInteractor: ForceUpdateInteractor
) : SplashMvp.Presenter {

    override var view: SplashMvp.View? = null
    override var model: ViewModel? = null

    override fun register(view: SplashMvp.View, model: ViewModel?) {
        super.register(view, model)
        FirebaseDatabase.getInstance().goOnline()
        firebaseConnectionInteractor.execute { connected ->
            if (connected) {
                firebaseInitInteractor.execute({
                    forceUpdateInteractor.checkUpdateNeeded { updateNeeded ->
                        if (updateNeeded) {
                            this.view?.showUpdateNeededDialog()
                        } else {
                            this.view?.startApp()
                        }
                    }
                }, {
                    this.view?.startApp()
                })
            } else {
                this.view?.startApp()
            }
        }
    }
}

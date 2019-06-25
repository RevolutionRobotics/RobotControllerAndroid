package com.revolution.robotics.features.splash

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.interactor.FirebaseInitInteractor
import com.revolution.robotics.core.interactor.SaveUserProgramsInteractor
import com.revolution.robotics.core.interactor.firebase.ProgramsInteractor

class SplashPresenter(
    private val firebaseInitInteractor: FirebaseInitInteractor,
    private val programsInteractor: ProgramsInteractor,
    private val saveUserProgramsInteractor: SaveUserProgramsInteractor
) : SplashMvp.Presenter {

    override var view: SplashMvp.View? = null
    override var model: ViewModel? = null

    override fun register(view: SplashMvp.View, model: ViewModel?) {
        super.register(view, model)
        firebaseInitInteractor.execute {
            programsInteractor.downloadAllPrograms {programs ->
                this.view?.startApp()
                saveUserProgramsInteractor.programs = programs
                saveUserProgramsInteractor.execute()
            }
        }
    }
}

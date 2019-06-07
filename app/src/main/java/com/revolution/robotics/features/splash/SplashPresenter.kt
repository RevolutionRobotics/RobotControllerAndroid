package com.revolution.robotics.features.splash

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.domain.local.ProgramLocalFiles
import com.revolution.robotics.core.domain.remote.Program
import com.revolution.robotics.core.interactor.FirebaseInitInteractor
import com.revolution.robotics.core.interactor.SaveUserProgramsInteractor
import com.revolution.robotics.core.interactor.firebase.FirebaseProgramDownloader
import com.revolution.robotics.core.interactor.firebase.ProgramsInteractor
import com.revolution.robotics.core.utils.AppPrefs

class SplashPresenter(
    private val firebaseInitInteractor: FirebaseInitInteractor,
    private val programsInteractor: ProgramsInteractor,
    private val firebaseProgramDownloader: FirebaseProgramDownloader,
    private val saveUserProgramsInteractor: SaveUserProgramsInteractor,
    private val appPrefs: AppPrefs
) : SplashMvp.Presenter {

    override var view: SplashMvp.View? = null
    override var model: ViewModel? = null

    override fun register(view: SplashMvp.View, model: ViewModel?) {
        super.register(view, model)
        firebaseInitInteractor.execute {
            if (!appPrefs.allProgramsDownloaded) {
                appPrefs.allProgramsDownloaded = true
                downloadPrograms()
            }
            // this is intentional - we do not wait for the result
            this.view?.startApp()
        }
    }

    private fun downloadPrograms() {
        programsInteractor.downloadAllPrograms { programs ->
            firebaseProgramDownloader.downloadProgramFiles(programs) { localFiles ->
                onProgramsDownloaded(localFiles, programs)
            }
        }
    }

    private fun onProgramsDownloaded(programLocalFiles: List<ProgramLocalFiles>, programs: List<Program>) {
        saveUserProgramsInteractor.programs = programs
        saveUserProgramsInteractor.programLocalFiles = programLocalFiles
        saveUserProgramsInteractor.execute()
    }
}

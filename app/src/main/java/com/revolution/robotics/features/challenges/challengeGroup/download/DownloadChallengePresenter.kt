package com.revolution.robotics.features.challenges.challengeGroup.download

import android.util.Log
import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.interactor.api.DownloadChallengeCategoryInteractor
import com.revolution.robotics.core.interactor.firebase.ChallengeCategoriesInteractor

class DownloadChallengePresenter(
    private val challengeCategoriesInteractor: ChallengeCategoriesInteractor,
    private val downloadChallengeCategoryInteractor: DownloadChallengeCategoryInteractor
) : DownloadChallengeMVP.Presenter {

    override var view: DownloadChallengeMVP.View? = null
    override var model: ViewModel? = null

    override fun downloadChallenge(challengeId: String) {
        var start = System.currentTimeMillis()
        challengeCategoriesInteractor.execute() { challenges ->
            val challenge = challenges.find { it.id == challengeId }
            downloadChallengeCategoryInteractor.challengeCategory = challenge
            downloadChallengeCategoryInteractor.execute(
                onResponse = {
                    Log.d(
                        "CHALLENGES",
                        challenge?.name?.en + " downloaded in " + (System.currentTimeMillis() - start) / 1000 + " sec"
                    )
                    view?.showSuccess()
                }, onError = {
                    Log.d("CHALLENGES", "Failed to download " + challenge?.name?.en)
                    view?.showError()
                }
            )
        }
    }
}
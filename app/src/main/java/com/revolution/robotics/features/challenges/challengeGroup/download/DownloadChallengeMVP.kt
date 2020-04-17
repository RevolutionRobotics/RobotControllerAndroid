package com.revolution.robotics.features.challenges.challengeGroup.download

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.Mvp

interface DownloadChallengeMVP: Mvp {
    interface View : Mvp.View {
        fun showError()
        fun showSuccess()
    }

    interface Presenter : Mvp.Presenter<View, ViewModel> {
        fun downloadChallenge(challengeId: String)
    }
}
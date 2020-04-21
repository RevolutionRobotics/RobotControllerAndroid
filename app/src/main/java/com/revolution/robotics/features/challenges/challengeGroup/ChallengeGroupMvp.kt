package com.revolution.robotics.features.challenges.challengeGroup

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.remote.ChallengeCategory

interface ChallengeGroupMvp : Mvp {

    interface View : Mvp.View {
        fun showDownloadDialog(challengeCategoryId: String?)
    }

    interface Presenter : Mvp.Presenter<View, ChallengeGroupViewModel> {
        fun loadChallengeCategories()
        fun onItemClicked(challengeCategory: ChallengeCategory)
        fun onDeleteClicked(challengeCategory: ChallengeCategory)
    }
}

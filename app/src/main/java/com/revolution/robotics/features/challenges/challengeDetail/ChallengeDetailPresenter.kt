package com.revolution.robotics.features.challenges.challengeDetail

import com.revolution.robotics.core.domain.local.UserChallengeCategory
import com.revolution.robotics.core.domain.remote.Challenge
import com.revolution.robotics.core.domain.remote.ChallengeStep
import com.revolution.robotics.core.interactor.GetUserChallengeCategoriesInteractor
import com.revolution.robotics.core.interactor.SaveUserChallengeCategoryInteractor
import com.revolution.robotics.core.interactor.firebase.ChallengeCategoriesInteractor
import com.revolution.robotics.features.challenges.challengeDetail.adapter.ChallengePartItemViewModel

class ChallengeDetailPresenter(
    private val saveUserChallengeCategoryInteractor: SaveUserChallengeCategoryInteractor,
    private val getCategoriesInteractor: ChallengeCategoriesInteractor,
    private val getUserChallengeCategoriesInteractor: GetUserChallengeCategoriesInteractor
) :
    ChallengeDetailMvp.Presenter, ChallengeDetailSlider.ChallengeStepSelectedListener {

    override var view: ChallengeDetailMvp.View? = null
    override var model: ChallengeDetailViewModel? = null
    override var toolbarViewModel: ChallengeDetailToolbarViewModel? = null

    var categoryId: String? = null
    var challengeId: String? = null

    override fun setChallenge(challenge: Challenge, categoryId: String?) {
        this.categoryId = categoryId
        this.challengeId = challenge.id
        view?.initSlider(challenge.challengeSteps, this)
        setChallengeStep(challenge.challengeSteps.first())
    }

    override fun onChallengeStepSelected(challengeStep: ChallengeStep, fromUser: Boolean) {
        setChallengeStep(challengeStep)
    }

    private fun setChallengeStep(challengeStep: ChallengeStep) {
        toolbarViewModel?.title?.set(challengeStep.title)
        model?.apply {
            if (challengeStep.parts.isEmpty()) {
                image.value = challengeStep.image
                title.value = challengeStep.description
                isPartStep.value = false
                parts.value = emptyList()
            } else {
                image.value = null
                title.value = null
                isPartStep.value = true
                parts.value = challengeStep.parts.map {
                    ChallengePartItemViewModel(it)
                }
            }
        }
    }

    override fun onChallengeFinished() {
        getCurrentProgress(categoryId)
    }

    private fun saveProgress(currentProgress: Int) {
        getCategoriesInteractor.execute({ categories ->
            categories.find { it.id == categoryId }?.let { category ->
                category.challenges.indexOfFirst { it.id == challengeId }.let { index ->
                    if (index + 1 > currentProgress) {
                        saveProgress(categoryId, index + 1)
                    }
                    view?.showChallengeFinishedDialog(category.challenges.getOrNull(index + 1))
                }
            }
        }, {
            // TODO Error handling
        })
    }

    private fun getCurrentProgress(categoryId: String?) {
        getUserChallengeCategoriesInteractor.execute({ categories ->
            saveProgress(categories.firstOrNull { it.challengeCategoryId == categoryId }?.progress ?: 0)
        }, {
            // TODO Error handling
        })
    }

    private fun saveProgress(categoryId: String?, progress: Int) {
        categoryId?.let {
            saveUserChallengeCategoryInteractor.userChallengeCategory = UserChallengeCategory(categoryId, progress)
            saveUserChallengeCategoryInteractor.execute({}, {
                // TODO Error handling
            })
        }
    }
}
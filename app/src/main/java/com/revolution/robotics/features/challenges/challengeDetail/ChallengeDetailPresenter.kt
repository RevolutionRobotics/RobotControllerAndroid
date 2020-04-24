package com.revolution.robotics.features.challenges.challengeDetail

import com.revolution.robotics.core.cache.ImageCache
import com.revolution.robotics.core.domain.local.ChallengeType
import com.revolution.robotics.core.domain.local.UserChallengeCategory
import com.revolution.robotics.core.domain.remote.Challenge
import com.revolution.robotics.core.domain.remote.ChallengeStep
import com.revolution.robotics.core.interactor.GetUserChallengeCategoriesInteractor
import com.revolution.robotics.core.interactor.SaveCompletedChallengeInteractor
import com.revolution.robotics.core.interactor.firebase.ChallengeCategoriesInteractor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.features.challenges.challengeDetail.adapter.ChallengePartItemViewModel

class ChallengeDetailPresenter(
    private val saveCompletedChallengeInteractor: SaveCompletedChallengeInteractor,
    private val getChallengeCategoriesInteractor: ChallengeCategoriesInteractor,
    private val imageCache: ImageCache,
    private val resourceResolver: ResourceResolver
) :
    ChallengeDetailMvp.Presenter, ChallengeDetailSlider.ChallengeStepSelectedListener {

    override var view: ChallengeDetailMvp.View? = null
    override var model: ChallengeDetailViewModel? = null
    override var toolbarViewModel: ChallengeDetailToolbarViewModel? = null

    private var challenge: Challenge? = null
    private var challengeStep: ChallengeStep? = null

    override fun register(view: ChallengeDetailMvp.View, model: ChallengeDetailViewModel?) {
        super.register(view, model)
        model?.presenter = this
    }

    override fun setChallenge(challenge: Challenge) {
        this.challenge = challenge
        view?.initSlider(challenge.steps, this)
        setChallengeStep(challenge.steps[0])
    }

    override fun onChallengeStepSelected(
        challengeStep: ChallengeStep,
        actualStep: Int,
        allSteps: Int,
        fromUser: Boolean
    ) {
        setChallengeStep(challengeStep)
        model?.apply {
            updateStepText(actualStep + 1, allSteps)
        }
    }

    private fun setChallengeStep(challengeStep: ChallengeStep) {
        this.challengeStep = challengeStep
        toolbarViewModel?.title?.set(challengeStep.title?.getLocalizedString(resourceResolver) ?: "")
        model?.apply {
            when (val challengeType = ChallengeType.fromId(challengeStep.type)) {
                ChallengeType.HORIZONTAL, ChallengeType.VERTICAL -> {
                    image.value = imageCache.getImagePath(challengeStep.image)
                    zoomableImage.value = null
                    title.value = challengeStep.text
                    type.value = challengeType
                    parts.value = emptyList()
                }
                ChallengeType.ZOOMABLE -> {
                    zoomableImage.value = imageCache.getImagePath(challengeStep.image)
                    image.value = null
                    title.value = challengeStep.text
                    type.value = challengeType
                    parts.value = emptyList()
                }
                ChallengeType.PART_LIST -> {
                    image.value = null
                    title.value = null
                    type.value = ChallengeType.PART_LIST
                    parts.value = challengeStep.parts.toList().sortedBy { it.second.order }.map {
                        ChallengePartItemViewModel(it.second)
                    }.sortedBy { it.part.order }
                }
                ChallengeType.BUTTON -> {
                    image.value = null
                    zoomableImage.value = null
                    title.value = challengeStep.text
                    type.value = challengeType
                    parts.value = emptyList()
                    buttonText.value = challengeStep.buttonText
                }
            }
        }
    }

    override fun onButtonClicked() {
        challengeStep?.buttonUrl?.apply { view?.openUrl(this) }
    }

    override fun onChallengeFinished() {
        saveProgress()
        getChallengeCategoriesInteractor.execute { categories ->
            val category = categories.find { it.challenges.map { challenge -> challenge.id }.contains(challenge?.id) }
            challenge?.order?.let { order ->
                val nextChallenge = category?.challenges?.find { it.order == order + 1 }
                view?.showChallengeFinishedDialog(nextChallenge)
            }
        }
    }

    private fun saveProgress() {
        challenge?.id?.let {
            saveCompletedChallengeInteractor.challengeId = it
            saveCompletedChallengeInteractor.execute()
        }
    }
}

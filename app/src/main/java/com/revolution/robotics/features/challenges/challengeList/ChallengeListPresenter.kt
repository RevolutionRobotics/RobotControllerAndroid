package com.revolution.robotics.features.challenges.challengeList

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Challenge
import com.revolution.robotics.core.domain.remote.ChallengeCategory
import com.revolution.robotics.core.interactor.GetUserChallengeCategoriesInteractor
import com.revolution.robotics.features.challenges.challengeList.adapter.ChallengeListItem

class ChallengeListPresenter(private val getUserChallengeCategoriesInteractor: GetUserChallengeCategoriesInteractor) :
    ChallengeListMvp.Presenter {

    override var view: ChallengeListMvp.View? = null
    override var model: ChallengeListViewModel? = null

    override fun setChallengeCategory(challengeCategory: ChallengeCategory) {
        getUserChallengeCategoriesInteractor.execute({ userCategories ->
            model?.description?.value = challengeCategory.description
            val progress = userCategories.find { it.challengeCategoryId == challengeCategory.id }?.progress ?: 0
            model?.items?.value = challengeCategory.challenges.mapIndexed { index, challenge ->
                ChallengeListItem(
                    name = challenge.name ?: "",
                    position = "${index + 1}.",
                    challenge = challenge,
                    isLineVisible = index < challengeCategory.challenges.size - 1,
                    isBottomItem = index % 2 != 0,
                    backgroundResource = getBackgroundResource(index, progress),
                    textColor = getTextColor(index, progress),
                    indexTextColor = getIndexTextColor(index, progress),
                    lineBackground = getLineBackground(index, progress),
                    presenter = this
                )
            }
        }, {
            // TODO Error handling
        })
    }

    private fun getLineBackground(index: Int, progress: Int): Int =
        when {
            index < progress -> R.drawable.ic_connect_line_red
            index == progress -> R.drawable.ic_connect_line_grey
            else -> R.drawable.ic_connect_line_dark_grey
        }

    private fun getIndexTextColor(index: Int, progress: Int): Int =
        when {
            index <= progress -> R.color.grey_28
            index == progress + 1 -> R.color.white
            else -> R.color.grey_6d
        }

    private fun getBackgroundResource(index: Int, progress: Int): Int =
        when {
            index <= progress -> R.drawable.bg_challenge_step_complete
            index == progress + 1 -> R.drawable.bg_challenge_step_incomplete
            else -> R.drawable.bg_challenge_step_unavailable
        }

    private fun getTextColor(index: Int, progress: Int): Int =
        when {
            index <= progress + 1 -> R.color.white
            else -> R.color.grey_6d
        }

    override fun onChallengeClicked(challengeStep: Challenge) {
        // TODO Navigate to the challenge
    }
}

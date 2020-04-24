package com.revolution.robotics.features.challenges.challengeList

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Challenge
import com.revolution.robotics.core.domain.remote.ChallengeCategory
import com.revolution.robotics.core.interactor.GetCompletedChallengesInteractor
import com.revolution.robotics.core.interactor.GetUserChallengeCategoriesInteractor
import com.revolution.robotics.core.interactor.firebase.ChallengeCategoriesInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.challenges.challengeList.adapter.ChallengeListItem

class ChallengeListPresenter(
    private val challengeCategoriesInteractor: ChallengeCategoriesInteractor,
    private val getCompletedChallengesInteractor: GetCompletedChallengesInteractor,
    private val navigator: Navigator
) :
    ChallengeListMvp.Presenter {

    override var view: ChallengeListMvp.View? = null
    override var model: ChallengeListViewModel? = null

    private var categoryId: String? = null

    override fun loadChallangeCategory(challengeCategoryId: String) {
        categoryId = challengeCategoryId
        challengeCategoriesInteractor.execute { challengeCategories ->
            challengeCategories.find { it.id == challengeCategoryId }?.let { challengeCategory ->
                view?.displayChallengeCategory(challengeCategory)
                getCompletedChallengesInteractor.execute { completedChallenges ->
                    model?.description?.value = challengeCategory.description
                    model?.items?.value =
                        challengeCategory.challenges.sortedBy { it.order }
                            .mapIndexed { index, challenge ->
                                val completed = completedChallenges.map { it.challengeId }
                                    .contains(challenge.id)
                                ChallengeListItem(
                                    name = challenge.name,
                                    position = "${index + 1}.",
                                    challenge = challenge,
                                    isLineVisible = index < challengeCategory.challenges.size - 1,
                                    isBottomItem = index % 2 != 0,
                                    backgroundResource = getBackgroundResource(completed),
                                    textColor = R.color.white,
                                    indexTextColor = getIndexTextColor(completed),
                                    lineBackground = getLineBackground(completed),
                                    onClickEnabled = true,
                                    presenter = this
                                )
                            }
                }
            }
        }

    }

    private fun getLineBackground(completed: Boolean): Int =
        when {
            completed -> R.drawable.ic_connect_line_red
            else -> R.drawable.ic_connect_line_grey
        }

    private fun getIndexTextColor(completed: Boolean): Int =
        when {
            completed -> R.color.grey_28
            else -> R.color.white
        }

    private fun getBackgroundResource(completed: Boolean): Int =
        when {
            completed -> R.drawable.bg_challenge_item_gold_selector
            else -> R.drawable.bg_challenge_item_grey_selector
        }

    override fun onChallengeClicked(challengeStep: Challenge) {
        categoryId?.let {
            navigator.navigate(ChallengeListFragmentDirections.toChallengeDetail(challengeStep, it))
        }
    }
}

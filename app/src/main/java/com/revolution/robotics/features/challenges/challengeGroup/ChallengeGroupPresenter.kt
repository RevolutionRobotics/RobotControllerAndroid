package com.revolution.robotics.features.challenges.challengeGroup

import com.revolution.robotics.core.domain.remote.ChallengeCategory
import com.revolution.robotics.core.interactor.GetUserChallengeCategoriesInteractor
import com.revolution.robotics.core.interactor.firebase.ChallengeCategoriesInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.challenges.challengeGroup.adapter.ChallengeGroupItem

class ChallengeGroupPresenter(
    private val challengeCategoriesInteractor: ChallengeCategoriesInteractor,
    private val userChallengeInteractor: GetUserChallengeCategoriesInteractor,
    private val navigator: Navigator
) :
    ChallengeGroupMvp.Presenter {

    override var view: ChallengeGroupMvp.View? = null
    override var model: ChallengeGroupViewModel? = null

    override fun register(view: ChallengeGroupMvp.View, model: ChallengeGroupViewModel?) {
        super.register(view, model)
        challengeCategoriesInteractor.execute { populateChallengeGroups(it) }
    }

    private fun populateChallengeGroups(groups: List<ChallengeCategory>) {
        userChallengeInteractor.execute { userCategories ->
            model?.items?.value = groups.map { remoteCategory ->
                ChallengeGroupItem(
                    iconUrl = remoteCategory.image ?: "",
                    name = remoteCategory.name,
                    currentChallenge = userCategories.find { it.challengeCategoryId == remoteCategory.id }?.progress
                        ?: 0,
                    totalChallenge = remoteCategory.challenges.size,
                    challengeCategory = remoteCategory,
                    presenter = this
                )
            }
        }
    }

    override fun onItemClicked(challenge: ChallengeCategory) {
        navigator.navigate(ChallengeGroupFragmentDirections.toChallengeList(challenge.id?:""))
    }
}

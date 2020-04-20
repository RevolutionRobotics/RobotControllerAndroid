package com.revolution.robotics.features.challenges.challengeGroup

import com.revolution.robotics.core.cache.ImageCache
import com.revolution.robotics.core.domain.remote.ChallengeCategory
import com.revolution.robotics.core.interactor.GetUserChallengeCategoriesInteractor
import com.revolution.robotics.core.interactor.api.DownloadChallengeCategoryInteractor
import com.revolution.robotics.core.interactor.api.DownloadChallengesInteractor
import com.revolution.robotics.core.interactor.firebase.ChallengeCategoriesInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.challenges.challengeGroup.adapter.ChallengeGroupItem

class ChallengeGroupPresenter(
    private val downloadChallengesInteractor: DownloadChallengesInteractor,
    private val challengeCategoriesInteractor: ChallengeCategoriesInteractor,
    private val userChallengeInteractor: GetUserChallengeCategoriesInteractor,
    private val imageCache: ImageCache,
    private val navigator: Navigator
) :
    ChallengeGroupMvp.Presenter {

    override var view: ChallengeGroupMvp.View? = null
    override var model: ChallengeGroupViewModel? = null

    override fun register(view: ChallengeGroupMvp.View, model: ChallengeGroupViewModel?) {
        super.register(view, model)
        loadChallengeCategories()
        downloadChallengesInteractor.execute(
            onResponse = {changed ->
                if (changed) {
                    loadChallengeCategories()
                }
            },
            onError = {}
        )
    }

    override fun loadChallengeCategories() {
        challengeCategoriesInteractor.execute { populateChallengeGroups(it) }
    }

    private fun populateChallengeGroups(groups: List<ChallengeCategory>) {
        userChallengeInteractor.execute { userCategories ->
            model?.items?.value = groups.map { remoteCategory ->
                ChallengeGroupItem(
                    iconUrl = remoteCategory.image ?: "",
                    name = remoteCategory.name,
                    downloaded = isDownloaded(remoteCategory),
                    currentChallenge = userCategories.find { it.challengeCategoryId == remoteCategory.id }?.progress
                        ?: 0,
                    totalChallenge = remoteCategory.challenges.size,
                    challengeCategory = remoteCategory,
                    presenter = this
                )
            }
        }
    }

    private fun isDownloaded(category: ChallengeCategory): Boolean {
        for (challenge in category.challenges) {
            for (step in challenge.steps) {
                if (step.image != null && !imageCache.isSaved(step.image!!)) {
                    return false
                }
            }
        }
        return true
    }

    override fun onItemClicked(challengeCategory: ChallengeCategory) {
        if (isDownloaded(challengeCategory)) {
            navigator.navigate(ChallengeGroupFragmentDirections.toChallengeList(challengeCategory.id?:""))
        } else {
            view?.showDownloadDialog(challengeCategory.id)
        }
    }

    override fun onDeleteClicked(challengeCategory: ChallengeCategory) {
        for (challenge in challengeCategory.challenges) {
            for (step in challenge.steps) {
                if (step.image != null && imageCache.isSaved(step.image!!)) {
                    imageCache.deleteImage(step.image!!)
                }
            }
        }
        loadChallengeCategories()
    }
}

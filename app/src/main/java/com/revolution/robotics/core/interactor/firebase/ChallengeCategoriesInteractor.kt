package com.revolution.robotics.core.interactor.firebase

import com.revolution.robotics.core.cache.RemoteDataCache
import com.revolution.robotics.core.domain.remote.ChallengeCategory
import com.revolution.robotics.core.interactor.Interactor

class ChallengeCategoriesInteractor(
    private val remoteDataCache: RemoteDataCache
) : Interactor<List<ChallengeCategory>>() {

    override fun getData(): List<ChallengeCategory> = remoteDataCache.data.challengeCategory.values.sortedBy { it.order }
}

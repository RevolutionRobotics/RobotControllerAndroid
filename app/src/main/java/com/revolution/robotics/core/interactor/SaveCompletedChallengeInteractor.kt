package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.CompletedChallenge
import com.revolution.robotics.core.domain.local.CompletedChallengeDao
import com.revolution.robotics.core.domain.local.UserChallengeCategory
import com.revolution.robotics.core.domain.local.UserChallengeCategoryDao

class SaveCompletedChallengeInteractor(private val completedChallengeDao: CompletedChallengeDao) :
    Interactor<Unit>() {

    lateinit var challengeId: String

    override fun getData() = completedChallengeDao.saveCompletedChallenge(CompletedChallenge(challengeId))
}

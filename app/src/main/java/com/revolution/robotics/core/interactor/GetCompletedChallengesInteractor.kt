package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.CompletedChallenge
import com.revolution.robotics.core.domain.local.CompletedChallengeDao

class GetCompletedChallengesInteractor(private val completedChallengeDao: CompletedChallengeDao) :
    Interactor<List<CompletedChallenge>>() {

    override fun getData(): List<CompletedChallenge> = completedChallengeDao.getCompletedChallenges()
}
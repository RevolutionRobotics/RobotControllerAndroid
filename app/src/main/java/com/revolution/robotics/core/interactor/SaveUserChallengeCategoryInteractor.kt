package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserChallengeCategory
import com.revolution.robotics.core.domain.local.UserChallengeCategoryDao

class SaveUserChallengeCategoryInteractor(private val userChallengeCategoryDao: UserChallengeCategoryDao) :
    Interactor<Unit>() {

    lateinit var userChallengeCategory: UserChallengeCategory

    override fun getData() = userChallengeCategoryDao.saveUserChallengeCategory(userChallengeCategory)
}

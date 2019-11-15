package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserChallengeCategory
import com.revolution.robotics.core.domain.local.UserChallengeCategoryDao

class GetUserChallengeCategoriesInteractor(private val userChallengeCategoryDao: UserChallengeCategoryDao) :
    Interactor<List<UserChallengeCategory>>() {

    override fun getData(): List<UserChallengeCategory> = userChallengeCategoryDao.getUserChallengeCategories()
}

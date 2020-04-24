package com.revolution.robotics.core.domain.local

import androidx.room.*

@Entity
data class UserChallengeCategory(
    @PrimaryKey
    var challengeCategoryId: String = "",
    var progress: Int = 0
)

@Dao
interface UserChallengeCategoryDao {

    @Query("SELECT * FROM UserChallengeCategory")
    fun getUserChallengeCategories(): List<UserChallengeCategory>
}

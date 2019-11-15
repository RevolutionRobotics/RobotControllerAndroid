package com.revolution.robotics.core.domain.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserChallengeCategory(userChallengeCategory: UserChallengeCategory)
}

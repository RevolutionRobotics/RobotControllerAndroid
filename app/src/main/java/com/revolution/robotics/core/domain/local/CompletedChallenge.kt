package com.revolution.robotics.core.domain.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class CompletedChallenge(
    @PrimaryKey
    var challengeId: String = ""
)

@Dao
interface CompletedChallengeDao {

    @Query("SELECT * FROM CompletedChallenge")
    fun getCompletedChallenges(): List<CompletedChallenge>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCompletedChallenge(completedChallenge: CompletedChallenge)
}

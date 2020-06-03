package com.revolution.robotics.core.interactor.api

import android.icu.lang.UCharacterCategory
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.revolution.robotics.core.api.RoboticsService
import com.revolution.robotics.core.cache.RemoteDataCache
import com.revolution.robotics.core.domain.local.CompletedChallenge
import com.revolution.robotics.core.domain.local.CompletedChallengeDao
import com.revolution.robotics.core.domain.local.UserChallengeCategory
import com.revolution.robotics.core.domain.local.UserChallengeCategoryDao
import com.revolution.robotics.core.domain.remote.Challenge
import com.revolution.robotics.core.domain.remote.ChallengeCategory
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.core.interactor.GetUserChallengeCategoriesInteractor
import com.revolution.robotics.core.interactor.Interactor
import com.revolution.robotics.core.utils.FileManager
import java.lang.reflect.Type
import java.net.HttpURLConnection

class DownloadChallengesInteractor(
    private val roboticsService: RoboticsService,
    private val fileManager: FileManager,
    private val imageDownloader: ImageDownloader,
    private val remoteDataCache: RemoteDataCache,
    private val userChallengeCategoryDao: UserChallengeCategoryDao,
    private val completedChallengeDao: CompletedChallengeDao
) : Interactor<Boolean>() {

    companion object {
        private const val challengesJsonFileName = "challenges.json"
    }

    private val challengeCategoryListType: Type =
        object : TypeToken<List<ChallengeCategory?>?>() {}.type

    override fun getData() : Boolean {
        try {
            val response = roboticsService.getChallenges().execute()
            val changed = response.raw().networkResponse() != null
                    && response.raw().networkResponse()?.code() != HttpURLConnection.HTTP_NOT_MODIFIED
            val challengesString = response.body()
            if (challengesString != null) {
                remoteDataCache.challenges =
                    Gson().fromJson(challengesString, challengeCategoryListType)
                fileManager.write(challengesJsonFileName, challengesString)
            } else {
                remoteDataCache.challenges =
                    Gson().fromJson(
                        fileManager.read(challengesJsonFileName),
                        challengeCategoryListType
                    )
            }
            migrateDataFromLegacyTable()
            val startTime = System.currentTimeMillis()
            val downloadedImageCount = imageDownloader.downloadImages(remoteDataCache.challenges.map { challengeCategory -> challengeCategory.image })
            Log.d("Image downloader", downloadedImageCount.toString() + " challenge profile pictures downloaded in " + (System.currentTimeMillis() - startTime) + " ms")

            return changed
        } catch (e: Exception) {
            remoteDataCache.challenges =
                Gson().fromJson(fileManager.read(challengesJsonFileName), challengeCategoryListType)
            return false
        }
    }

    private fun migrateDataFromLegacyTable() {
        Log.d("CHALLENGES", "Completed challenges: " + completedChallengeDao.getCompletedChallenges().size)
        val challengeCategories =  userChallengeCategoryDao.getUserChallengeCategories()
        for (userChallengeCategory: UserChallengeCategory in challengeCategories) {
            remoteDataCache.challenges.find { it.id == userChallengeCategory.challengeCategoryId }?.let { challengeCategory->
                Log.d("CHALLENGES", "Category " + challengeCategory.name + " has progress " + userChallengeCategory.progress)
                for (challenge: Challenge in challengeCategory.challenges) {
                    if (challenge.id != null &&
                        challenge.order < userChallengeCategory.progress + 1) {
                        completedChallengeDao.saveCompletedChallenge(CompletedChallenge(challenge.id!!))
                    }
                }
            }
        }
    }
}
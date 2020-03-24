package com.revolution.robotics.core.interactor.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.revolution.robotics.core.api.RoboticsService
import com.revolution.robotics.core.cache.RemoteDataCache
import com.revolution.robotics.core.domain.remote.ChallengeCategory
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.core.interactor.Interactor
import com.revolution.robotics.core.utils.FileManager
import java.lang.reflect.Type

class DownloadChallengesInteractor(
private val roboticsService: RoboticsService,
private val fileManager: FileManager,
private val remoteDataCache: RemoteDataCache
) : Interactor<Unit>() {

    companion object {
        private const val challengesJsonFileName = "challenges.json"
    }

    private val challengeCategoryListType: Type = object : TypeToken<List<ChallengeCategory?>?>() {}.type

    override fun getData() {
        try {
            val challengesString = roboticsService.getChallenges().execute().body()
            if (challengesString != null) {
                remoteDataCache.challenges = Gson().fromJson(challengesString, challengeCategoryListType)
                fileManager.write(challengesJsonFileName, challengesString)
            } else {
                remoteDataCache.challenges =
                    Gson().fromJson(fileManager.read(challengesJsonFileName), challengeCategoryListType)
            }
        } catch (e: Exception) {
            remoteDataCache.challenges =
                Gson().fromJson(fileManager.read(challengesJsonFileName), challengeCategoryListType)
        }
    }
}
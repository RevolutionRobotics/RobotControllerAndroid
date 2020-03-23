package com.revolution.robotics.core.interactor.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.revolution.robotics.core.api.RoboticsService
import com.revolution.robotics.core.cache.RemoteDataCache
import com.revolution.robotics.core.domain.remote.ChallengeCategory
import com.revolution.robotics.core.domain.remote.FirebaseData
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.core.interactor.Interactor
import com.revolution.robotics.core.utils.FileManager
import java.lang.reflect.Type

class RefreshDataCacheInteractor(
    private val roboticsService: RoboticsService,
    private val fileManager: FileManager,
    private val remoteDataCache: RemoteDataCache
) : Interactor<Unit>() {

    private val robotListType: Type = object : TypeToken<List<Robot?>?>() {}.type
    private val challengeCategoryListType: Type = object : TypeToken<List<ChallengeCategory?>?>() {}.type

    override fun getData() {
        refreshLegacyData()
        refreshRobots()
        refreshChallenges()
    }

    private fun refreshLegacyData() {
        try {
            val databaseContents = roboticsService.getDatabaseContents().execute().body()
            if (databaseContents != null) {
                remoteDataCache.data = Gson().fromJson(databaseContents, FirebaseData::class.java)
                fileManager.write(jsonFileName, databaseContents)
            } else {
                remoteDataCache.data =
                    Gson().fromJson(fileManager.read(jsonFileName), FirebaseData::class.java)
            }
        } catch (e: Exception) {
            remoteDataCache.data =
                Gson().fromJson(fileManager.read(jsonFileName), FirebaseData::class.java)
        }
    }

    private fun refreshRobots() {
        try {
            val robotsString = roboticsService.getRobots().execute().body()
            if (robotsString != null) {
                remoteDataCache.robots = Gson().fromJson(robotsString, robotListType)
                fileManager.write(robotsJsonFileName, robotsString)
            } else {
                remoteDataCache.robots =
                    Gson().fromJson(fileManager.read(robotsJsonFileName), robotListType)
            }
        } catch (e: Exception) {
            remoteDataCache.robots =
                Gson().fromJson(fileManager.read(robotsJsonFileName), robotListType)
        }
    }

    private fun refreshChallenges() {
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

    companion object {
        private const val jsonFileName = "database.json"
        private const val robotsJsonFileName = "robots.json"
        private const val challengesJsonFileName = "challenges.json"
    }
}
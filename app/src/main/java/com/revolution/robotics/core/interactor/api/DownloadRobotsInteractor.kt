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

class DownloadRobotsInteractor(
    private val roboticsService: RoboticsService,
    private val fileManager: FileManager,
    private val remoteDataCache: RemoteDataCache
) : Interactor<Unit>() {

    companion object {
        private const val robotsJsonFileName = "robots.json"
    }

    private val robotListType: Type = object : TypeToken<List<Robot?>?>() {}.type

    override fun getData() {
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
}
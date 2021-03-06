package com.revolution.robotics.core.interactor.api

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.revolution.robotics.core.api.RoboticsService
import com.revolution.robotics.core.cache.RemoteDataCache
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.core.interactor.Interactor
import com.revolution.robotics.core.utils.FileManager
import java.lang.reflect.Type
import java.net.HttpURLConnection

class DownloadRobotsInteractor(
    private val roboticsService: RoboticsService,
    private val imageDownloader: ImageDownloader,
    private val fileManager: FileManager,
    private val remoteDataCache: RemoteDataCache
) : Interactor<Boolean>() {

    companion object {
        private const val robotsJsonFileName = "robots.json"
    }

    private val robotListType: Type = object : TypeToken<List<Robot?>?>() {}.type

    override fun getData() : Boolean {
        try {
            val response = roboticsService.getRobots().execute()
            val changed = response.raw().networkResponse() != null
                    && response.raw().networkResponse()?.code() != HttpURLConnection.HTTP_NOT_MODIFIED
            val robotsString = response.body()
            if (robotsString != null) {
                remoteDataCache.robots = Gson().fromJson(robotsString, robotListType)
                fileManager.write(robotsJsonFileName, robotsString)
            } else {
                remoteDataCache.robots =
                    Gson().fromJson(fileManager.read(robotsJsonFileName), robotListType)
            }
            val startTime = System.currentTimeMillis()
            val downloadedImageCount = imageDownloader.downloadImages(remoteDataCache.robots.map { robot -> robot.coverImage })
            Log.d("Image downloader", downloadedImageCount.toString() + " robot profile pictures downloaded in " + (System.currentTimeMillis() - startTime) + " ms")
            return changed
        } catch (e: Exception) {
            remoteDataCache.robots =
                Gson().fromJson(fileManager.read(robotsJsonFileName), robotListType)
            return false
        }
    }
}
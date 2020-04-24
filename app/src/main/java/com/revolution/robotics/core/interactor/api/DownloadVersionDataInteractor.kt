package com.revolution.robotics.core.interactor.api

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.revolution.robotics.core.api.RoboticsService
import com.revolution.robotics.core.domain.remote.VersionData
import com.revolution.robotics.core.interactor.Interactor
import com.revolution.robotics.core.utils.FileManager
import java.lang.reflect.Type
import java.net.HttpURLConnection

class DownloadVersionDataInteractor(
    private val roboticsService: RoboticsService,
    private val fileManager: FileManager
) : Interactor<Int>() {

    private companion object {
        private const val cacheFileName = "versionData.json"
        private const val LOG_TAG = "DownloadVersionData"
    }

    private val versionDataType: Type = object : TypeToken<VersionData>() {}.type

    override fun getData() : Int {
        try {
            val response = roboticsService.getVersionData().execute()
            val changed = response.raw().networkResponse() != null
                    && response.raw().networkResponse()?.code() != HttpURLConnection.HTTP_NOT_MODIFIED

            if (changed) {
                val versionData = response.body()
                if (versionData != null) {
                    fileManager.write(cacheFileName, Gson().toJson(versionData))
                    return versionData.android
                }
            }

            val cachedData: VersionData = Gson().fromJson(fileManager.read(cacheFileName), versionDataType)
            return cachedData.android

        } catch (e: Exception) {
            Log.w(LOG_TAG, e)
        }

        return 0
    }
}
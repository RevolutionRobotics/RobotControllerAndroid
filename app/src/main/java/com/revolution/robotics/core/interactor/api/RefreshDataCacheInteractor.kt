package com.revolution.robotics.core.interactor.api

import com.google.gson.Gson
import com.revolution.robotics.core.api.RoboticsService
import com.revolution.robotics.core.cache.RemoteDataCache
import com.revolution.robotics.core.domain.remote.FirebaseData
import com.revolution.robotics.core.interactor.Interactor
import com.revolution.robotics.core.utils.FileManager
import java.lang.Exception

class RefreshDataCacheInteractor(
    private val roboticsService: RoboticsService,
    private val fileManager: FileManager,
    private val remoteDataCache: RemoteDataCache
) : Interactor<Unit>() {

    override fun getData() {
        try {
            val databaseContents = roboticsService.getDatabaseContents().execute().body()
            if (databaseContents != null) {
                remoteDataCache.data = Gson().fromJson(databaseContents, FirebaseData::class.java)
                fileManager.write(jsonFileName, databaseContents)
            } else {
                remoteDataCache.data = Gson().fromJson(fileManager.read(jsonFileName), FirebaseData::class.java)
            }
        }
        catch (e: Exception) {
            remoteDataCache.data = Gson().fromJson(fileManager.read(jsonFileName), FirebaseData::class.java)
        }
    }

    companion object {
        private const val jsonFileName = "database.json"
    }
}
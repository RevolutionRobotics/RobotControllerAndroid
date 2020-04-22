package com.revolution.robotics.core.interactor.api

import android.util.Log
import com.revolution.robotics.core.api.RoboticsService
import com.revolution.robotics.core.interactor.Interactor

class DownloadVersionDataInteractor(
    private val roboticsService: RoboticsService
) : Interactor<Int>() {

    private companion object {
        const val LOG_TAG = "DownloadVersionData"
    }

    override fun getData() : Int {
        try {
            val response = roboticsService.getVersionData().execute()

            val versionDataString = response.body()
            if (versionDataString != null) {
                return versionDataString.android
            }
        } catch (e: Exception) {
            Log.w(LOG_TAG, e)
        }

        return 0
    }
}
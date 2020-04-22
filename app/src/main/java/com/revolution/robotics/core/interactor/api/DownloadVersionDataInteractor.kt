package com.revolution.robotics.core.interactor.api

import android.util.Log
import com.revolution.robotics.core.api.RoboticsService
import com.revolution.robotics.core.interactor.Interactor
import com.revolution.robotics.core.utils.VersionNumber

class DownloadVersionDataInteractor(
    private val roboticsService: RoboticsService
) : Interactor<VersionNumber>() {

    private companion object {
        const val LOG_TAG = "DownloadVersionData"
    }

    override fun getData() : VersionNumber {
        try {
            val response = roboticsService.getVersionData().execute()

            val versionDataString = response.body()
            if (versionDataString != null) {
                return VersionNumber.parse(versionDataString)
            }
        } catch (e: Exception) {
            Log.w(LOG_TAG, e)
        }

        return VersionNumber(0, 0, 0)
    }
}
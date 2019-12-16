package com.revolution.robotics.core.interactor.api

import com.revolution.robotics.core.api.RoboticsService
import com.revolution.robotics.core.interactor.Interactor

class LoadJsonFromFirebaseInteractor(
    private val roboticsService: RoboticsService
) : Interactor<String>() {

    override fun getData(): String {
        return roboticsService.getDatabaseContents().execute().body()!! //exception is cached
    }
}
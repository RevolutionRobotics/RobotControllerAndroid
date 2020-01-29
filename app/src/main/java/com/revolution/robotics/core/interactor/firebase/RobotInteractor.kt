package com.revolution.robotics.core.interactor.firebase

import com.revolution.robotics.core.cache.RemoteDataCache
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.core.interactor.Interactor

class RobotInteractor(
    private val remoteDataCache: RemoteDataCache
) : Interactor<Robot?>() {

    lateinit var robotId: String

    override fun getData(): Robot? = remoteDataCache.data.robot.values.firstOrNull { it.id == robotId }
}
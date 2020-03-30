package com.revolution.robotics.core.interactor.firebase

import com.revolution.robotics.core.cache.RemoteDataCache
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.core.interactor.Interactor

class RobotsInteractor(
    private val remoteDataCache: RemoteDataCache
) : Interactor<List<Robot>>() {

    override fun getData(): List<Robot> = remoteDataCache.robots.sortedBy { it.order }
}

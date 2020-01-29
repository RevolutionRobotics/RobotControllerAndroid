package com.revolution.robotics.core.interactor.firebase

import com.revolution.robotics.core.cache.RemoteDataCache
import com.revolution.robotics.core.domain.remote.Controller
import com.revolution.robotics.core.interactor.Interactor

class ControllerInteractor(
    private val remoteDataCache: RemoteDataCache
) : Interactor<List<Controller>>() {

    lateinit var configurationId: String

    override fun getData(): List<Controller> = remoteDataCache.data.controller.values.filter { it.configurationId == configurationId } .sortedBy { it.lastModified }
}

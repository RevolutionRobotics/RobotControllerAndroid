package com.revolution.robotics.core.interactor.firebase

import com.revolution.robotics.core.cache.RemoteDataCache
import com.revolution.robotics.core.domain.remote.Configuration
import com.revolution.robotics.core.interactor.Interactor

class ConfigurationInteractor(
    private val remoteDataCache: RemoteDataCache
) : Interactor<Configuration>() {

    lateinit var configId: String

    override fun getData(): Configuration = remoteDataCache.data.configuration.values.first { it.id == configId }

}

package com.revolution.robotics.core.interactor.firebase

import com.revolution.robotics.core.cache.RemoteDataCache
import com.revolution.robotics.core.domain.remote.BuildStep
import com.revolution.robotics.core.interactor.Interactor

class BuildStepInteractor(
    private val remoteDataCache: RemoteDataCache
) : Interactor<List<BuildStep>>() {

    lateinit var robotId: String

    override fun getData(): List<BuildStep> = remoteDataCache.data.buildStep.values.filter { it.robotId == robotId }.sortedBy { it.stepNumber }
}

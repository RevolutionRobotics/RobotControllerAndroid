package com.revolution.robotics.core.interactor.firebase

import com.revolution.robotics.core.cache.RemoteDataCache
import com.revolution.robotics.core.domain.remote.Program
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.core.interactor.Interactor

class GetProgramsForRobotInteractor(
    private val remoteDataCache: RemoteDataCache
) : Interactor<List<Program>>() {

    lateinit var robot: Robot

    override fun getData(): List<Program> = remoteDataCache.data.program.values.filter { it.robotId == robot.id }.sortedBy { it.lastModified }
}

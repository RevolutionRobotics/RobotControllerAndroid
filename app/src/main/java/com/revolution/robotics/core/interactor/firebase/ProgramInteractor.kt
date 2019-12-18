package com.revolution.robotics.core.interactor.firebase

import com.revolution.robotics.core.cache.RemoteDataCache
import com.revolution.robotics.core.domain.remote.Program
import com.revolution.robotics.core.interactor.Interactor

class ProgramInteractor(
    private val remoteDataCache: RemoteDataCache
) : Interactor<Program?>() {

    lateinit var programId: String

    override fun getData(): Program? = remoteDataCache.data.program.values.firstOrNull { it.id == programId }
}

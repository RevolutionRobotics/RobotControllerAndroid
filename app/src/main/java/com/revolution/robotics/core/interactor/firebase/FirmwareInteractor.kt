package com.revolution.robotics.core.interactor.firebase

import com.revolution.robotics.core.cache.RemoteDataCache
import com.revolution.robotics.core.domain.remote.Firmware
import com.revolution.robotics.core.interactor.Interactor

class FirmwareInteractor(
    private val remoteDataCache: RemoteDataCache
) : Interactor<Firmware?>() {
    override fun getData(): Firmware? = remoteDataCache.data.firmware.values.firstOrNull()
}

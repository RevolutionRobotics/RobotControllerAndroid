package com.revolution.robotics.core.interactor.api

import com.google.gson.Gson
import com.revolution.robotics.core.api.RoboticsService
import com.revolution.robotics.core.domain.remote.Firmware
import com.revolution.robotics.core.interactor.Interactor

class GetFirmwareInteractor(
    private val roboticsService: RoboticsService
) : Interactor<Firmware?>() {

    override fun getData(): Firmware? {
        return try {
            val firmwareString = roboticsService.getFirmware().execute().body()
            if (firmwareString != null) {
                Gson().fromJson(firmwareString, Firmware::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
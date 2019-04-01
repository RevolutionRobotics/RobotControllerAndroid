package com.revolution.robotics

import android.app.Application
import com.revolution.robotics.core.koin.appModule
import org.koin.android.ext.android.startKoin

class RoboticsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule))
    }

}
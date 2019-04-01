package com.revolution.robotics

import android.app.Application
import com.revolution.robotics.core.koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RoboticsApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // Koin
        startKoin {
            // TODO only enable logger for release builds
            androidLogger()
            androidContext(this@RoboticsApplication)
            modules(appModule)
        }
    }

}
package com.revolution.robotics

import android.app.Application
import android.content.pm.ApplicationInfo
import android.webkit.WebView
import com.crashlytics.android.Crashlytics
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.revolution.robotics.core.kodein.createAppModule
import com.revolution.robotics.core.kodein.createDbModule
import com.revolution.robotics.core.kodein.createInteractorModule
import com.revolution.robotics.core.kodein.createMainModule
import com.revolution.robotics.core.kodein.createPresenterModule
import com.revolution.robotics.core.kodein.createViewModelModule
import io.fabric.sdk.android.Fabric
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class RoboticsApplication : Application(), KodeinAware {

    companion object {
        lateinit var kodein: Kodein
    }
    // Challenge group items
    // Challenge list items
    // Settings  social icons
    // Blockly direction selector items
    // Blockly dialpad numbers & backspace arrow
    // Blockly option selector items
    // Blockly sound picker items
    // Blockly color picker items
    // Blockly donut selector items
    // Carousel page arrows

    // ControllerTypeSelector items
    // MyRobots robot items
    // WhoToBuild robot items
    // Firmware update - brain item

    override var kodein = Kodein {
        import(createMainModule())
        import(createAppModule(this@RoboticsApplication))
        import(createDbModule(this@RoboticsApplication))
        import(createInteractorModule())
        import(createPresenterModule())
        import(createViewModelModule())
    }

    override fun onCreate() {
        super.onCreate()
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, Crashlytics())
        }
        FirebaseApp.initializeApp(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        RoboticsApplication.kodein = kodein
        if (0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
    }
}

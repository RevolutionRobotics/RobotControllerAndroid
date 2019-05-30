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
    // Home options
    // ChippedBox elements
    // WhoToBuild items
    // Toolbar back button & icons
    // Bluetooth status button
    // Build robot - Seekbar arrows & checkmark
    // MyRobots items
    // Configure tabs & buttons
    // Configure motor & sensor panel items
    // ControllerTypeSelector items
    // Setup buttons
    // Most recent buttons
    // Program selector filter & ordering
    // Program selector items
    // Background program filter & ordering
    // Background program items
    // Challenge group items
    // Challenge list items
    // Challenge details seekbar arrows & checkmark
    // Settings About buttons & social icons
    // Firmware update - brain item
    // Blockly direction selector items
    // Blockly dialpad numbers & backspace arrow
    // Blockly option selector items
    // Blockly sound picker items
    // Blockly color picker items
    // Blockly donut selector items

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

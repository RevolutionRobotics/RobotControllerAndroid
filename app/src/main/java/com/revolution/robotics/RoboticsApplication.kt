package com.revolution.robotics

import android.app.Application
import android.content.pm.ApplicationInfo
import android.webkit.WebView
import com.crashlytics.android.Crashlytics
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
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

    /*
    blocking issues
        - "Turn on the brain" tips
        - Privacy Policy url
        - T&C url
    */

    companion object {
        const val DOWNLOAD_RETRY_TIME = 15000L

        lateinit var kodein: Kodein
    }

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
        FirebaseStorage.getInstance().maxDownloadRetryTimeMillis = DOWNLOAD_RETRY_TIME
        RoboticsApplication.kodein = kodein
        if (0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
    }
}

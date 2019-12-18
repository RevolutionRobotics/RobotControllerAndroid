package com.revolution.robotics

import android.app.Application
import android.content.pm.ApplicationInfo
import android.webkit.WebView
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import com.revolution.robotics.core.kodein.*
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
        FirebaseApp.initializeApp(this)
        FirebaseStorage.getInstance().maxDownloadRetryTimeMillis = DOWNLOAD_RETRY_TIME
        RoboticsApplication.kodein = kodein
        if (0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
    }
}

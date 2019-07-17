package com.revolution.robotics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.revolution.robotics.features.splash.SplashMvp
import com.revolution.robotics.features.splash.forceUpdate.ForceUpdateDialog
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.erased.instance

class SplashActivity : AppCompatActivity(), KodeinAware, SplashMvp.View {

    override val kodein by kodein()

    private val presenter: SplashMvp.Presenter by instance()

    override fun onStart() {
        super.onStart()
        presenter.register(this, null)
    }

    override fun onStop() {
        presenter.unregister()
        super.onStop()
    }

    override fun startApp() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun showUpdateNeededDialog() {
        ForceUpdateDialog.newInstance().show(supportFragmentManager)
    }
}

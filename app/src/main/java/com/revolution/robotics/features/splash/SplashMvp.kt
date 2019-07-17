package com.revolution.robotics.features.splash

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.Mvp

interface SplashMvp : Mvp {

    interface View : Mvp.View {
        fun startApp()
        fun showUpdateNeededDialog()
    }

    interface Presenter : Mvp.Presenter<View, ViewModel>
}

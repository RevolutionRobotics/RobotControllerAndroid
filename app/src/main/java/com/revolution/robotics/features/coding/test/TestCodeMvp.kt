package com.revolution.robotics.features.coding.test

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.Mvp

interface TestCodeMvp : Mvp {

    interface View : Mvp.View {
        fun showError(message: String)
        fun showConfigurationSent()
    }

    interface Presenter : Mvp.Presenter<View, ViewModel> {
        fun runProgram(python: String, robotId: Int)
    }
}
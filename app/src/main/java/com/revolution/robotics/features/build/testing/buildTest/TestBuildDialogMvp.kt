package com.revolution.robotics.features.build.testing.buildTest

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.Mvp

interface TestBuildDialogMvp : Mvp {
    interface View : Mvp.View {
        fun dismiss()
        fun activateBuildFace()
        fun showTips()
    }

    interface Presenter : Mvp.Presenter<View, ViewModel> {
        fun sendTestCode(code: String)
    }
}

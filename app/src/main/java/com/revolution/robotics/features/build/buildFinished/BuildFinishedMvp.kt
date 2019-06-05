package com.revolution.robotics.features.build.buildFinished

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.Mvp

interface BuildFinishedMvp : Mvp {

    interface View : Mvp.View {
        fun showBuildFinishedDialogFace()
    }

    interface Presenter : Mvp.Presenter<View, ViewModel> {
        fun navigateHome()
    }
}

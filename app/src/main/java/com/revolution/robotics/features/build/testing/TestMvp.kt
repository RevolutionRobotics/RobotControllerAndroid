package com.revolution.robotics.features.build.testing

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.Mvp

interface TestMvp : Mvp {

    interface View : Mvp.View {
        fun onTestUploaded()
    }

    interface Presenter : Mvp.Presenter<View, ViewModel> {
        fun uploadTest(assetName: String, replaceablePairs: List<Pair<String, String>>)
    }
}

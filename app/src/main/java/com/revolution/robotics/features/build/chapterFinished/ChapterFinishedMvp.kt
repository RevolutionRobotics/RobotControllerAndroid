package com.revolution.robotics.features.build.chapterFinished

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.remote.Milestone

interface ChapterFinishedMvp : Mvp {

    interface View : Mvp.View {
        fun onTestUploaded()
    }

    interface Presenter : Mvp.Presenter<View, ViewModel> {

        var milestone: Milestone?

        fun uploadTest()
    }
}

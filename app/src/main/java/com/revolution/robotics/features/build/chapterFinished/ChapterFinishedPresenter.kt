package com.revolution.robotics.features.build.chapterFinished

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.domain.remote.Milestone

class ChapterFinishedPresenter : ChapterFinishedMvp.Presenter {

    override var view: ChapterFinishedMvp.View? = null
    override var model: ViewModel? = null

    override var milestone: Milestone? = null

    override fun uploadTest() {
        // TODO Upload & run test
        view?.onTestUploaded()
    }
}

package com.revolution.robotics.features.build.chapterFinished

import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Milestone
import com.revolution.robotics.core.utils.Navigator

class ChapterFinishedPresenter(private val navigator: Navigator) : ChapterFinishedMvp.Presenter {

    override var view: ChapterFinishedMvp.View? = null
    override var model: ViewModel? = null

    override var milestone: Milestone? = null

    override fun startTestingFlow() {
        view?.startTestingFlow()
    }

    override fun navigateHome() {
        navigator.popUntil(R.id.mainMenuFragment)
    }
}

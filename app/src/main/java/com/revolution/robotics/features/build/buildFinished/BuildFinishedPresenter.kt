package com.revolution.robotics.features.build.buildFinished

import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.core.utils.Navigator

class BuildFinishedPresenter(private val navigator: Navigator) : BuildFinishedMvp.Presenter {

    override var view: BuildFinishedMvp.View? = null
    override var model: ViewModel? = null

    override fun navigateHome() {
        navigator.popUntil(R.id.mainMenuFragment)
    }
}

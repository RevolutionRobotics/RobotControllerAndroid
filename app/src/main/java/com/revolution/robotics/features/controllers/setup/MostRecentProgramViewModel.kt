package com.revolution.robotics.features.controllers.setup

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class MostRecentProgramViewModel(private val programs: List<UserProgram>, private val presenter: SetupMvp.Presenter) {

    companion object {
        private val BACKGROUND_BASE = ChippedBoxConfig.Builder()
            .chipSize(R.dimen.dimen_4dp)
            .backgroundColorResource(R.color.grey_1e)

        private val BACKGROUND = BACKGROUND_BASE
            .chipTopLeft(true)
            .chipBottomRight(true)
            .create()
        private val SHOW_MORE_BACKGROUND = BACKGROUND_BASE
            .borderColorResource(R.color.white)
            .create()
    }

    fun getProgram(index: Int) = programs[index - 1]

    fun getBackground() = BACKGROUND

    fun getShowMoreBackground() = SHOW_MORE_BACKGROUND

    fun onShowMoreClicked() {
        presenter.showAllPrograms()
    }
}

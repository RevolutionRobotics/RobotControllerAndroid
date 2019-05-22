package com.revolution.robotics.features.controllers.setup.mostRecent

import com.revolution.robotics.R
import com.revolution.robotics.features.controllers.setup.SetupMvp
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class MostRecentProgramViewModel(
    private val items: List<MostRecentItem>,
    val hasMorePrograms: Boolean,
    private val presenter: SetupMvp.Presenter
) {

    companion object {
        private val BACKGROUND_BASE = ChippedBoxConfig.Builder()
            .chipSize(R.dimen.dimen_4dp)
            .backgroundColorResource(R.color.grey_1e)

        private val BACKGROUND = BACKGROUND_BASE
            .chipTopLeft(true)
            .chipBottomRight(true)
            .create()
        private val BACKGROUND_BOUND = BACKGROUND_BASE
            .borderColorResource(R.color.robotics_red)
            .create()
        private val SHOW_MORE_BACKGROUND = BACKGROUND_BASE
            .borderColorResource(R.color.white)
            .create()
    }

    private fun getItem(index: Int) =
        if (index <= items.size) {
            items[index - 1]
        } else {
            null
        }

    fun getProgram(index: Int) = getItem(index)?.program

    fun getBackground(index: Int) =
        if (getItem(index)?.isBound == true) {
            BACKGROUND_BOUND
        } else {
            BACKGROUND
        }

    fun getShowMoreBackground() =
        SHOW_MORE_BACKGROUND

    fun onShowMoreClicked() {
        presenter.showAllPrograms()
    }

    fun onProgramClicked(index: Int) {
        getItem(index)?.let { item ->
            if (item.isBound) {
                presenter.removeProgram(item.program)
            } else {
                presenter.addProgram(item.program)
            }
        }
    }
}

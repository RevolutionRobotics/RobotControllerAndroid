package com.revolution.robotics.features.controllers.setup.mostRecent

import com.revolution.robotics.R
import com.revolution.robotics.features.controllers.setup.ConfigureControllerMvp
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class MostRecentProgramViewModel(
    private val items: List<MostRecentItem>,
    private val presenter: ConfigureControllerMvp.Presenter
) {

    companion object {
        private val BACKGROUND_BASE = ChippedBoxConfig.Builder()
            .chipSize(R.dimen.dimen_4dp)
            .backgroundColorResource(R.color.grey_1d)

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

    fun isEmpty() = items.isEmpty()

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
            presenter.onProgramSelected(item.program, item.isBound)
        }
    }
}

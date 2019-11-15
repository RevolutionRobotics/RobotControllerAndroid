package com.revolution.robotics.features.whoToBuild.adapter

import androidx.annotation.DrawableRes
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.features.whoToBuild.WhoToBuildMvp

class RobotsBuildYourOwnItem(
    private val presenter: WhoToBuildMvp.Presenter
) : RobotsItem(
    Robot(), presenter
) {
    @DrawableRes
    override val selectedResource: Int = R.drawable.card_border_build_new
    @DrawableRes
    override val defaultResource: Int = R.drawable.card_border_build_new_gray

    override fun onClicked() {
        if (isSelected.get()) {
            presenter.onBuildYourOwnSelected()
        } else {
            super.onClicked()
        }
    }
}
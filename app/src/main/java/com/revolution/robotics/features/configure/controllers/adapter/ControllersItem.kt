package com.revolution.robotics.features.configure.controllers.adapter

import androidx.annotation.DrawableRes
import androidx.databinding.ObservableBoolean
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserController
import com.revolution.robotics.features.configure.controllers.ConfigureControllersMvp

class ControllersItem(
    val userController: UserController,
    val name: String,
    @DrawableRes
    val normalImageRes: Int,
    val iconDescription: String,
    isCurrentlyActive: Boolean,
    private val presenter: ConfigureControllersMvp.Presenter
) {

    @DrawableRes
    val selectedResource: Int = R.drawable.configure_controllers_card_bg_red
    @DrawableRes
    val defaultResource: Int = R.drawable.configure_controllers_card_bg_white

    var isSelected: ObservableBoolean = ObservableBoolean(false)
    val isCurrentlyActive = ObservableBoolean(isCurrentlyActive)

    fun onSelectClicked() {
        presenter.onItemSelectionChanged(this)
    }

    fun onEditClicked() {
        presenter.onEditSelected(this)
    }

    fun onDeleteClicked() {
        presenter.onDeleteSelected(this)
    }

    fun onInfoClicked() {
        presenter.onInfoSelected(this)
    }
}

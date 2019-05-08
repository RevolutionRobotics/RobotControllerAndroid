package com.revolution.robotics.features.configure.controllers.adapter

import androidx.annotation.DrawableRes
import androidx.databinding.ObservableBoolean
import com.revolution.robotics.R
import com.revolution.robotics.features.configure.controllers.ConfigureControllersMvp

class ControllersItem(
    val id: Int,
    val name: String,
    @DrawableRes
    val normalImageRes: Int,
    @DrawableRes
    val smallImageRes: Int,
    val iconDescription: String,
    val isCurrentlyActive: Boolean,
    private val presenter: ConfigureControllersMvp.Presenter
) {

    @DrawableRes
    val selectedResource: Int = R.drawable.configure_controllers_card_bg_red
    @DrawableRes
    val defaultResource: Int = R.drawable.configure_controllers_card_bg_white

    var isSelected: ObservableBoolean = ObservableBoolean(false)

    fun onSelectClicked() {
        presenter.onItemSelected(id)
    }

    fun onEditClicked() {
        presenter.onEditSelected(id)
    }

    fun onDeleteClicked() {
        presenter.onDeleteSelected(id)
    }

    fun onInfoClicked() {
        presenter.onInfoSelected(id)
    }
}

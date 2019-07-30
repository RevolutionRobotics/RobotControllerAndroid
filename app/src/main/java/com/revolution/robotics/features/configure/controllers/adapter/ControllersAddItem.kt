package com.revolution.robotics.features.configure.controllers.adapter

import com.revolution.robotics.core.domain.local.UserController
import com.revolution.robotics.features.configure.controllers.ConfigureControllersMvp

class ControllersAddItem(

    private val presenter: ConfigureControllersMvp.Presenter
) : ControllersItem(
    UserController(),
    "",
    0,
    "",
    false,
    presenter
) {
    override fun onItemClicked() {
        presenter.onCreateNewClick()
    }
}

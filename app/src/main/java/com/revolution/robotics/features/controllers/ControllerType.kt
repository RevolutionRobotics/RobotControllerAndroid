package com.revolution.robotics.features.controllers

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Controller

enum class ControllerType(@DrawableRes val imageResource: Int, @StringRes val nameRes: Int, val id: String) {
    GAMER(R.drawable.controller_gamer, R.string.controller_type_gamer, Controller.TYPE_GAMER),
    MULTITASKER(R.drawable.controller_multitasker, R.string.controller_type_multitasker, Controller.TYPE_MULTITASKER),
    DRIVER(R.drawable.controller_driver, R.string.controller_type_driver, Controller.TYPE_DRIVER);

    companion object {

        fun fromId(id: String?): ControllerType? = when (id) {
            Controller.TYPE_GAMER -> GAMER
            Controller.TYPE_MULTITASKER -> MULTITASKER
            Controller.TYPE_DRIVER -> DRIVER
            else -> null
        }
    }

    override fun toString() =
        super.toString().toLowerCase()
}

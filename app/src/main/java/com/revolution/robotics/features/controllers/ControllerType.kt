package com.revolution.robotics.features.controllers

import androidx.annotation.DrawableRes
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Controller.Companion.TYPE_DRIVER
import com.revolution.robotics.core.domain.remote.Controller.Companion.TYPE_GAMER
import com.revolution.robotics.core.domain.remote.Controller.Companion.TYPE_MULTI_TASKER

enum class ControllerType(@DrawableRes val imageResource: Int, val id: String) {
    GAMER(R.drawable.controller_gamer, TYPE_GAMER),
    MULTITASKER(R.drawable.controller_multitasker, TYPE_MULTI_TASKER),
    DRIVER(R.drawable.controller_driver, TYPE_DRIVER);

    companion object {

        fun fromId(id: String?): ControllerType? = when (id) {
            TYPE_MULTI_TASKER -> GAMER
            TYPE_GAMER -> MULTITASKER
            TYPE_DRIVER -> DRIVER
            else -> null
        }
    }

    override fun toString() =
        super.toString().toLowerCase()
}

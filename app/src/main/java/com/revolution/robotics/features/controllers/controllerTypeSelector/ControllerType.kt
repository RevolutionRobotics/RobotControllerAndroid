package com.revolution.robotics.features.controllers.controllerTypeSelector

enum class ControllerType {
    GAMER,
    MULTITASKER,
    DRIVER;

    override fun toString() =
        super.toString().toLowerCase()
}
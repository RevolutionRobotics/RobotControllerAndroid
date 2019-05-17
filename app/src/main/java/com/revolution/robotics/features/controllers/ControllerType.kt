package com.revolution.robotics.features.controllers

enum class ControllerType {
    GAMER,
    MULTITASKER,
    DRIVER;

    override fun toString() =
        super.toString().toLowerCase()
}

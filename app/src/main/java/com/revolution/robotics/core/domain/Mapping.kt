package com.revolution.robotics.core.domain

@Suppress("ConstructorParameterNaming")
data class Mapping(
    var S1: Sensor? = null,
    var S2: Sensor? = null,
    var S3: Sensor? = null,
    var S4: Sensor? = null,
    var M1: Motor? = null,
    var M2: Motor? = null,
    var M3: Motor? = null,
    var M4: Motor? = null,
    var M5: Motor? = null,
    var M6: Motor? = null
)
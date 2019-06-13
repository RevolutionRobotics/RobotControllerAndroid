package com.revolution.robotics.features.play

import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserControllerWithPrograms

data class FullControllerData(
    val userConfiguration: UserConfiguration?,
    val controller: UserControllerWithPrograms?,
    val sources: HashMap<String, String>
)
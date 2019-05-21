package com.revolution.robotics.features.controllers.setup.mostRecent

import com.revolution.robotics.core.domain.local.UserProgram

data class MostRecentItem(val program: UserProgram, val isBound: Boolean = false)

package com.revolution.robotics.core.domain.local

import androidx.room.Entity
import com.revolution.robotics.core.domain.PortMapping

@Entity
class UserMapping : PortMapping {
    var userConfigId: Int = 0

    constructor() : super()

    constructor(
        userConfigId: Int = 0,
        portMapping: PortMapping
    ) : super(portMapping) {
        this.userConfigId = userConfigId
    }
}

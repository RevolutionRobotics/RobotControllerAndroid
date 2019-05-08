package com.revolution.robotics.core.domain.remote

import com.revolution.robotics.core.domain.shared.RobotDescriptor
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Robot(
    override var id: Int = 0,
    override var name: String? = null,
    override var description: String? = null,
    override var coverImage: String? = null,
    var defaultProgram: String? = null,
    var buildTime: String? = null,
    var configurationId: Int = 0
) : RobotDescriptor

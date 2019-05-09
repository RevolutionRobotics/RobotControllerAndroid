package com.revolution.robotics.core.domain.shared

import android.os.Parcelable

interface RobotDescriptor : Parcelable {
    val id: Int
    val name: String?
    var configurationId: Int
    var description: String?
    var coverImage: String?
}

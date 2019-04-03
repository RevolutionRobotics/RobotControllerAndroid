package com.revolution.robotics.core.db.converter

import androidx.room.TypeConverter
import com.revolution.robotics.core.domain.local.BuildStatus

class BuildStatusConverter {
    @TypeConverter
    fun toBuildStatus(buildStatus: Int): BuildStatus = BuildStatus.values()[buildStatus]

    @TypeConverter
    fun fromBuildStatus(buildStatus: BuildStatus): Int = buildStatus.ordinal
}

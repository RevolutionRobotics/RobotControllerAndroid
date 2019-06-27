package com.revolution.robotics.core.domain.local

enum class ChallengeType(val firebaseId: String) {
    HORIZONTAL("horizontal"), VERTICAL("vertical"), ZOOMABLE("zoomable"), PART_LIST("partList");

    companion object {
        fun fromId(firebaseId: String?): ChallengeType? = values().find { it.firebaseId == firebaseId }
    }
}
package com.revolution.robotics.core.cache

import com.revolution.robotics.core.domain.remote.ChallengeCategory
import com.revolution.robotics.core.domain.remote.Robot

class RemoteDataCache {
    var robots: List<Robot> = mutableListOf()
    var challenges: List<ChallengeCategory> = mutableListOf()
}
package com.revolution.robotics.core.cache

import com.revolution.robotics.core.domain.remote.FirebaseData
import com.revolution.robotics.core.domain.remote.Robot

class RemoteDataCache {
    var data: FirebaseData = FirebaseData()
    var robots: List<Robot> = mutableListOf()
}
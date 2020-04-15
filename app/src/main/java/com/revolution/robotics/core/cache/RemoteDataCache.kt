package com.revolution.robotics.core.cache

import com.revolution.robotics.core.domain.remote.ChallengeCategory
import com.revolution.robotics.core.domain.remote.Robot

class RemoteDataCache {
    var robots: List<Robot> = mutableListOf()
    var challenges: List<ChallengeCategory> = mutableListOf()

    public fun getAllImageUrls() : List<String> {
        var urls = mutableListOf<String?>()
        for (robot in robots) {
            urls.add(robot.coverImage)
            for (step in robot.buildSteps) {
                urls.add(step.image)
                step.milestone?.let { urls.add(it.testImage) }
            }
        }
        for (challengeCategory in challenges) {
            urls.add(challengeCategory.image)
            for (challenge in challengeCategory.challenges) {
                for (step in challenge.steps) {
                    urls.add(step.image)
                }
            }
        }
        return urls.filterNotNull()
    }
}
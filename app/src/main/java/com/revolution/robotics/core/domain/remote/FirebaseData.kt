package com.revolution.robotics.core.domain.remote

class FirebaseData {
    var challengeCategory: HashMap<String, ChallengeCategory> = hashMapOf()
    var firmware: HashMap<String, Firmware> = hashMapOf()
    var minVersion: MinVersion = MinVersion()
}
package com.revolution.robotics.core.domain.remote

class FirebaseData {
    var challengeCategory: HashMap<String, ChallengeCategory> = hashMapOf()
    var buildStep: HashMap<String, BuildStep> = hashMapOf()
    var controller: HashMap<String, Controller> = hashMapOf()
    var firmware: HashMap<String, Firmware> = hashMapOf()
    var minVersion: MinVersion = MinVersion()
    var program: HashMap<String, Program> = hashMapOf()
}
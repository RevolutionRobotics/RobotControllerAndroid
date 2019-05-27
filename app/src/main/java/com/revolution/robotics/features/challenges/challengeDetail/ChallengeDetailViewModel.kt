package com.revolution.robotics.features.challenges.challengeDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChallengeDetailViewModel : ViewModel() {
    val title = MutableLiveData<String?>()
    val image = MutableLiveData<String?>()
    val isPartStep = MutableLiveData<Boolean>()
}

package com.revolution.robotics.features.challenges.challengeGroup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.features.challenges.challengeGroup.adapter.ChallengeGroupItem

class ChallengeGroupViewModel : ViewModel() {

    val items = MutableLiveData<List<ChallengeGroupItem>>()
}

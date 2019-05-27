package com.revolution.robotics.features.challenges.challengeList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.features.challenges.challengeList.adapter.ChallengeListItem

class ChallengeListViewModel : ViewModel() {

    val description = MutableLiveData<String>()
    val items = MutableLiveData<List<ChallengeListItem>>()
}

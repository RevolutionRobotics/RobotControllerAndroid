package com.revolution.robotics.features.challenges.challengeList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.domain.remote.LocalizedString
import com.revolution.robotics.features.challenges.challengeList.adapter.ChallengeListItem

class ChallengeListViewModel : ViewModel() {

    val description = MutableLiveData<LocalizedString?>()
    val items = MutableLiveData<List<ChallengeListItem>>()
}

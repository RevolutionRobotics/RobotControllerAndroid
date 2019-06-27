package com.revolution.robotics.features.challenges.challengeDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.domain.local.ChallengeType
import com.revolution.robotics.features.challenges.challengeDetail.adapter.ChallengePartItemViewModel

class ChallengeDetailViewModel : ViewModel() {
    val title = MutableLiveData<String?>()
    val image = MutableLiveData<String?>()
    val type = MutableLiveData<ChallengeType>()
    val parts = MutableLiveData<List<ChallengePartItemViewModel>>()
}

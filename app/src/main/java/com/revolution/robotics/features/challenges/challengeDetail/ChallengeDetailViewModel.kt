package com.revolution.robotics.features.challenges.challengeDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.domain.local.ChallengeType
import com.revolution.robotics.core.domain.remote.LocalizedString
import com.revolution.robotics.features.challenges.challengeDetail.adapter.ChallengePartItemViewModel

class ChallengeDetailViewModel : ViewModel() {

    var presenter: ChallengeDetailMvp.Presenter? = null

    val title = MutableLiveData<LocalizedString?>()
    val image = MutableLiveData<String?>()
    val zoomableImage = MutableLiveData<String?>()
    val type = MutableLiveData<ChallengeType>()
    val buttonText = MutableLiveData<LocalizedString?>()
    val parts = MutableLiveData<List<ChallengePartItemViewModel>>()

    fun onButtonCLicked() = presenter?.onButtonClicked()
}

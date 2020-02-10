package com.revolution.robotics.features.challenges.challengeDetail

import android.text.Spannable
import android.text.SpannableStringBuilder
import androidx.core.text.toSpannable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.ChallengeType
import com.revolution.robotics.core.domain.remote.LocalizedString
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.CustomTypefaceSpan
import com.revolution.robotics.features.challenges.challengeDetail.adapter.ChallengePartItemViewModel

class ChallengeDetailViewModel(
    private val resourceResolver: ResourceResolver
) : ViewModel() {

    var presenter: ChallengeDetailMvp.Presenter? = null

    val title = MutableLiveData<LocalizedString?>()

    val step: MutableLiveData<Spannable?> = MutableLiveData()
    val image = MutableLiveData<String?>()
    val zoomableImage = MutableLiveData<String?>()
    val type = MutableLiveData<ChallengeType>()
    val buttonText = MutableLiveData<LocalizedString?>()
    val parts = MutableLiveData<List<ChallengePartItemViewModel>>()

    fun onButtonCLicked() = presenter?.onButtonClicked()

    fun updateStepText(actualStep: Int, totalSteps: Int) {
        step.value = SpannableStringBuilder().apply {
            append("$actualStep", CustomTypefaceSpan(resourceResolver.font(R.font.barlow_bold)), 0)
            append(
                " / $totalSteps",
                CustomTypefaceSpan(resourceResolver.font(R.font.barlow_regular)),
                0
            )
        }.toSpannable()
    }
}

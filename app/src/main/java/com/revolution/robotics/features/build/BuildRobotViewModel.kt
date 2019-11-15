package com.revolution.robotics.features.build

import android.text.Spannable
import android.text.SpannableStringBuilder
import androidx.core.text.toSpannable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.BuildStep
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.CustomTypefaceSpan

class BuildRobotViewModel(
    private val resourceResolver: ResourceResolver
) : ViewModel() {
    val imageUrl: MutableLiveData<String?> = MutableLiveData()
    val step: MutableLiveData<Spannable?> = MutableLiveData()
    val partImage: MutableLiveData<String?> = MutableLiveData()
    val part2Image: MutableLiveData<String?> = MutableLiveData()
    val hasTwoParts: MutableLiveData<Boolean> = MutableLiveData()
    val hasParts: MutableLiveData<Boolean> = MutableLiveData()

    fun setBuildStep(buildStep: BuildStep, totalSteps: Int) {
        imageUrl.value = buildStep.image
        step.value = createStepText(buildStep.stepNumber, totalSteps)
        partImage.value = buildStep.partImage
        part2Image.value = buildStep.part2Image
        hasTwoParts.value = !part2Image.value.isNullOrEmpty()
        hasParts.value = !partImage.value.isNullOrEmpty()
    }

    private fun createStepText(actualStep: Int, totalSteps: Int) =
        SpannableStringBuilder().apply {
            append("$actualStep", CustomTypefaceSpan(resourceResolver.font(R.font.barlow_bold)), 0)
            append(" / $totalSteps", CustomTypefaceSpan(resourceResolver.font(R.font.barlow_regular)), 0)
        }.toSpannable()
}

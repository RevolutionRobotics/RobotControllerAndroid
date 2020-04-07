package com.revolution.robotics.features.build

import android.text.Spannable
import android.text.SpannableStringBuilder
import androidx.core.text.toSpannable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.core.cache.ImageCache
import com.revolution.robotics.core.domain.remote.BuildStep
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.CustomTypefaceSpan
import com.revolution.robotics.features.build.adapter.BuildRobotAdapter
import com.revolution.robotics.features.build.adapter.BuildStepViewModel



class BuildRobotViewModel(
    private val resourceResolver: ResourceResolver,
    private val imageCache: ImageCache
) : ViewModel() {
    val step: MutableLiveData<Spannable?> = MutableLiveData()
    val adapter: MutableLiveData<BuildRobotAdapter> = MutableLiveData()

    fun setBuildSteps(buildSteps: List<BuildStep>) {
        val buildRobotAdapter = BuildRobotAdapter()
        buildRobotAdapter.buildSteps = buildSteps.map { step -> BuildStepViewModel(imageCache.getImagePath(step.image)) }
        adapter.value = buildRobotAdapter
    }

    fun setBuildStep(buildStep: BuildStep, totalSteps: Int) {
        step.value = createStepText(buildStep.stepNumber, totalSteps)
    }

    private fun createStepText(actualStep: Int, totalSteps: Int) =
        SpannableStringBuilder().apply {
            append("$actualStep", CustomTypefaceSpan(resourceResolver.font(R.font.barlow_bold)), 0)
            append(
                " / $totalSteps",
                CustomTypefaceSpan(resourceResolver.font(R.font.barlow_regular)),
                0
            )
        }.toSpannable()

}

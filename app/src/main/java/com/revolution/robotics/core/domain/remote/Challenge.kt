package com.revolution.robotics.core.domain.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Challenge(
    var id: String? = null,
    var name: LocalizedString? = null,
    var order: Int = 0,
    var steps: List<ChallengeStep> = emptyList(),
    var stepsArchive: String? = null
) : Parcelable

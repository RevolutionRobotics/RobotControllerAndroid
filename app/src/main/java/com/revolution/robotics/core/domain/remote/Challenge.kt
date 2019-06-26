package com.revolution.robotics.core.domain.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Challenge(
    var id: String? = null,
    var name: String? = null,
    var order: Int = 0,
    var challengeSteps: HashMap<String, ChallengeStep> = hashMapOf()
) : Parcelable

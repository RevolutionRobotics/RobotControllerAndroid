package com.revolution.robotics.core.domain.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChallengeStep(
    var id: String? = null,
    var title: String? = null,
    var description: String? = null,
    var image: String? = null,
    var parts: HashMap<String, Part> = hashMapOf()
) : Parcelable

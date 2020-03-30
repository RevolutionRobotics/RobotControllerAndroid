package com.revolution.robotics.core.domain.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChallengeCategory(
    var id: String? = null,
    var name: LocalizedString? = null,
    var image: String? = null,
    var description: LocalizedString? = null,
    var order: Int = 0,
    var challenges: List<Challenge> = emptyList()
) : Parcelable

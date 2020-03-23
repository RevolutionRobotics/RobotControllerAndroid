package com.revolution.robotics.core.domain.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChallengeStep(
    var id: String? = null,
    var title: LocalizedString? = null,
    var text: LocalizedString? = null,
    var image: String? = null,
    var type: String? = null,
    var buttonText: LocalizedString? = null,
    var buttonUrl: String? = null,
    var parts: HashMap<String, Part> = hashMapOf()
) : Parcelable

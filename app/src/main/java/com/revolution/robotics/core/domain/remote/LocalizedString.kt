package com.revolution.robotics.core.domain.remote

import android.os.Parcelable
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import kotlinx.android.parcel.Parcelize

@Suppress("DataClassContainsFunctions")
@Parcelize
data class LocalizedString(var en: String? = null) : Parcelable {

    fun getLocalizedString(resourceResolver: ResourceResolver): String? {
        val id = resourceResolver.string(R.string.language_id)
        if (id == "en") return en
        // TODO Return other languages here
        return en
    }
}

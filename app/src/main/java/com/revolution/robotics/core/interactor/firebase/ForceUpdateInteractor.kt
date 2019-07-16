package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.revolution.robotics.BuildConfig
import com.revolution.robotics.core.domain.remote.FirebaseException

class ForceUpdateInteractor : FirebaseInteractor<Long>() {

    override fun onDataChange(snapShot: DataSnapshot) {
        val response = snapShot.getValue(Long::class.java)
        if (response != null) {
            onResponse?.invoke(response)
        } else {
            onError?.invoke(FirebaseException("Data is null"))
        }
    }

    override fun getDatabaseReference(database: FirebaseDatabase): Query = database.getReference("minVersion/android")

    fun checkUpdateNeeded(onResponse: (Boolean) -> Unit) {
        execute(onResponse = { minVersion ->
            onResponse.invoke(minVersion > BuildConfig.VERSION_CODE)
        }, onError = {
            onResponse.invoke(false)
        })
    }
}

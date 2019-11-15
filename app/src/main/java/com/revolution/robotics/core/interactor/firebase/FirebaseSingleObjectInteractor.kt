package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.GenericTypeIndicator
import com.revolution.robotics.core.domain.remote.FirebaseException

abstract class FirebaseSingleObjectInteractor<T> : FirebaseInteractor<T>() {

    abstract val genericTypeIndicator: GenericTypeIndicator<HashMap<String, T>>

    override fun onDataChange(snapShot: DataSnapshot) {
        val response = snapShot.getValue(genericTypeIndicator)?.toList()?.firstOrNull()
        if (response != null) {
            onResponse?.invoke(response.second)
        } else {
            onError?.invoke(FirebaseException("Data is null"))
        }
    }
}

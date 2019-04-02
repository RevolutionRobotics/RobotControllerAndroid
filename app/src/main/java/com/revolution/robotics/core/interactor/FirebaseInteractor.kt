package com.revolution.robotics.core.interactor

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener

abstract class FirebaseInteractor<T> : ValueEventListener {

    private val db = FirebaseDatabase.getInstance("https://revolutionrobotics-afeb5.firebaseio.com/")

    abstract val genericTypeIndicator: GenericTypeIndicator<T>

    var onResponse: ((data: T) -> Unit)? = null
    var onError: (() -> Unit)? = null

    fun execute(onResponse: (data: T) -> Unit, onError: () -> Unit = {}) {
        this.onResponse = onResponse
        this.onError = onError
        getDatabaseReference(db).addListenerForSingleValueEvent(this)
    }

    override fun onDataChange(snapShot: DataSnapshot) {
        val response = snapShot.getValue(genericTypeIndicator)
        if (response != null) {
            onResponse?.invoke(response)
        } else {
            onError?.invoke()
        }
    }

    override fun onCancelled(p0: DatabaseError) = Unit

    abstract fun getDatabaseReference(database: FirebaseDatabase): DatabaseReference
}

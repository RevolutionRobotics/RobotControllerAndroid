package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

abstract class FirebaseInteractor<T> : ValueEventListener {

    private val db = FirebaseDatabase.getInstance("https://revolutionrobotics-afeb5.firebaseio.com/")

    var onResponse: ((data: T) -> Unit)? = null
    var onError: ((throwable: Throwable) -> Unit)? = null

    fun execute(onResponse: (data: T) -> Unit, onError: (Throwable) -> Unit = {}) {
        this.onResponse = onResponse
        this.onError = onError
        getDatabaseReference(db).addListenerForSingleValueEvent(this)
    }

    override fun onCancelled(error: DatabaseError) {
        onError?.invoke(error.toException())
    }

    abstract fun getDatabaseReference(database: FirebaseDatabase): Query
}

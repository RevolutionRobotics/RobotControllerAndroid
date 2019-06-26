package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.revolution.robotics.RoboticsApplication
import com.revolution.robotics.features.shared.ErrorHandler
import org.kodein.di.erased.instance

abstract class FirebaseInteractor<T> : ValueEventListener {

    private val db = FirebaseDatabase.getInstance()
    private val errorHandler: ErrorHandler by RoboticsApplication.kodein.instance()

    var onResponse: ((data: T) -> Unit)? = null
    var onError: ((throwable: Throwable) -> Unit)? = null

    fun execute(onResponse: (data: T) -> Unit) =
        execute(onResponse, null)

    fun execute(onResponse: (data: T) -> Unit, onError: ((Throwable) -> Unit)? = null) {
        this.onResponse = onResponse
        this.onError = onError
        getDatabaseReference(db).addListenerForSingleValueEvent(this)
    }

    override fun onCancelled(error: DatabaseError) {
        onError?.invoke(error.toException()) ?: errorHandler.onError()
    }

    abstract fun getDatabaseReference(database: FirebaseDatabase): Query
}

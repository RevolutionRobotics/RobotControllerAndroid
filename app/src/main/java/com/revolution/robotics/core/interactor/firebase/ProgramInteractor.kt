package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.revolution.robotics.core.domain.remote.Program

class ProgramInteractor : FirebaseSingleObjectInteractor<Program>() {

    override val genericTypeIndicator: GenericTypeIndicator<ArrayList<Program>> =
        object : GenericTypeIndicator<ArrayList<@JvmSuppressWildcards Program>>() {}

    lateinit var programId: String

    override fun getDatabaseReference(database: FirebaseDatabase): Query =
        database.getReference("program").orderByChild("id").equalTo(programId).limitToFirst(1)
}

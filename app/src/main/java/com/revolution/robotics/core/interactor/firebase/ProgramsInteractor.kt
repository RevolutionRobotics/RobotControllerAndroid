package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.revolution.robotics.core.domain.remote.Program

class ProgramsInteractor : FirebaseListInteractor<Program>() {
    override val genericTypeIndicator: GenericTypeIndicator<ArrayList<Program>> =
        object : GenericTypeIndicator<ArrayList<Program>>() {}

    lateinit var programIds: List<String>

    override fun filter(item: Program): Boolean = programIds.contains(item.id)

    override fun getDatabaseReference(database: FirebaseDatabase): Query = database.getReference("program")
}

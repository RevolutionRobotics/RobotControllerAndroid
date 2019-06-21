package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.revolution.robotics.core.domain.remote.Program

class ProgramsInteractor : FirebaseListInteractor<Program>() {
    override val genericTypeIndicator: GenericTypeIndicator<Map<String, Program>> =
        object : GenericTypeIndicator<Map<String, Program>>() {}

    lateinit var programIds: List<String>
    private var downloadAllPrograms = false

    override fun filter(item: Program): Boolean = programIds.contains(item.id) || downloadAllPrograms

    override fun getDatabaseReference(database: FirebaseDatabase): Query = database.getReference("program")

    fun downloadAllPrograms(onResponse: (List<Program>) -> Unit) {
        downloadAllPrograms = true
        programIds = emptyList()
        execute(onResponse)
    }
}

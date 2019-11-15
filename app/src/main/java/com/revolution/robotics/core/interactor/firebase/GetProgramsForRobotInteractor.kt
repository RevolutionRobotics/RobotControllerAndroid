package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.revolution.robotics.core.domain.remote.Program
import com.revolution.robotics.core.domain.remote.Robot

class GetProgramsForRobotInteractor : FirebaseListInteractor<Program>() {
    override val genericTypeIndicator: GenericTypeIndicator<HashMap<String, @JvmSuppressWildcards Program>> =
        object : GenericTypeIndicator<HashMap<String, Program>>() {}

    lateinit var robot: Robot

    override fun filter(item: Program): Boolean = item.robotId == robot.id
    override fun order(list: List<Program>): List<Program> = list.sortedBy { it.lastModified }

    override fun getDatabaseReference(database: FirebaseDatabase): Query = database.getReference("program")
}

package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.revolution.robotics.core.domain.remote.BuildStep

class BuildStepInteractor : FirebaseListInteractor<BuildStep>() {

    lateinit var robotId: String

    override val genericTypeIndicator: GenericTypeIndicator<HashMap<String, @JvmSuppressWildcards BuildStep>> =
        object : GenericTypeIndicator<HashMap<String, BuildStep>>() {}

    override fun getDatabaseReference(database: FirebaseDatabase): Query = database.getReference("buildStep")

    override fun filter(item: BuildStep) = item.robotId == robotId
    override fun order(list: List<BuildStep>): List<BuildStep> = list.sortedBy { it.stepNumber }
}

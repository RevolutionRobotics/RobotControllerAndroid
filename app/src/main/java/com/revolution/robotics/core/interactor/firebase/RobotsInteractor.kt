package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.revolution.robotics.core.domain.remote.Robot

class RobotsInteractor : FirebaseListInteractor<Robot>() {

    override val genericTypeIndicator: GenericTypeIndicator<HashMap<String, @JvmSuppressWildcards Robot>> =
        object : GenericTypeIndicator<HashMap<String, Robot>>() {}

    override fun getDatabaseReference(database: FirebaseDatabase): Query = database.getReference("robot")
    override fun order(list: List<Robot>): List<Robot> = list.sortedBy { it.order }

    override fun filter(item: Robot) = true
}
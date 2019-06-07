package com.revolution.robotics.core.interactor

import com.google.firebase.database.FirebaseDatabase

class FirebaseInitInteractor : Interactor<Unit>() {

    private val references =
        listOf("buildStep", "challengeCategory", "configuration", "controller", "program", "robot", "testCode")

    override fun getData() {
        FirebaseDatabase.getInstance().apply {
            references.forEach { reference ->
                getReference(reference).keepSynced(true)
            }
        }
    }
}

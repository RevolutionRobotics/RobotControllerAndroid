package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.revolution.robotics.core.domain.remote.Firmware

class FirmwareInteractor : FirebaseSingleObjectInteractor<Firmware>() {

    override val genericTypeIndicator: GenericTypeIndicator<HashMap<String, Firmware>> =
        object : GenericTypeIndicator<HashMap<String, Firmware>>() {}

    override fun getDatabaseReference(database: FirebaseDatabase): Query = database.getReference("firmware")
}

package com.revolution.robotics.core.interactor

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.revolution.robotics.core.domain.remote.TestCode

class TestCodeInteractor : FirebaseSingleObjectInteractor<TestCode>() {
    override val genericTypeIndicator: GenericTypeIndicator<ArrayList<TestCode>> =
        object : GenericTypeIndicator<ArrayList<TestCode>>() {}

    var codeId = 0

    override fun getDatabaseReference(database: FirebaseDatabase): Query =
        database.getReference("testCode").orderByChild("id").equalTo(codeId.toString()).limitToFirst(1)
}

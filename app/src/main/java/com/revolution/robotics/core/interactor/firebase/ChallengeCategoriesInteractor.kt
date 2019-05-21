package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.revolution.robotics.core.domain.remote.ChallengeCategory

class ChallengeCategoriesInteractor : FirebaseListInteractor<ChallengeCategory>() {

    override val genericTypeIndicator: GenericTypeIndicator<ArrayList<ChallengeCategory>> =
        object : GenericTypeIndicator<ArrayList<ChallengeCategory>>() {}

    override fun filter(item: ChallengeCategory): Boolean = true

    override fun getDatabaseReference(database: FirebaseDatabase): Query = database.getReference("challengeCategory")
}
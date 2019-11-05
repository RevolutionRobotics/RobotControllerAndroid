package com.revolution.robotics.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class Reporter(private val firebaseAnalytics: FirebaseAnalytics) {

    fun setUserProperty(userProperty: UserProperty, value: String) {
        firebaseAnalytics.setUserProperty(userProperty.reportName, value)
    }

    fun reportEvent(event: Event, bundle: Bundle? = null) {
        firebaseAnalytics.logEvent(event.reportName, bundle)
    }

    enum class Event(val reportName: String) {
        SELECTED_USER_TYPE("selected_user_type"),
        REGISTERED_ROBOT("registered_robot"),
        SKIPPED_ROBOT_REGISTRATION("skipped_robot_registration"),
        UPLOADED_TO_BRAIN("uploaded_to_brain")
    }

    enum class UserProperty(val reportName: String) {
        USER_TYPE("user_type"),
        YEAR_OF_BIRTH("year_of_birth"),
        ROBOT_ID("robot_id")
    }
}
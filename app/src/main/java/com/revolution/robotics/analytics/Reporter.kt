package com.revolution.robotics.analytics

import android.app.Activity
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class Reporter(private val firebaseAnalytics: FirebaseAnalytics) {

    fun setUserProperty(userProperty: UserProperty, value: String) {
        firebaseAnalytics.setUserProperty(userProperty.reportName, value)
    }

    fun reportEvent(event: Event, bundle: Bundle? = null) {
        firebaseAnalytics.logEvent(event.reportName, bundle)
    }

    fun setScreen(activity: Activity, screen: Screen) {
        firebaseAnalytics.setCurrentScreen(activity, screen.screenName, null)
    }

    enum class Event(val reportName: String) {
        SELECTED_USER_TYPE("select_user_type"),
        REGISTERED_ROBOT("register_robot"),
        SKIPPED_ROBOT_REGISTRATION("skip_robot_registration"),
        UPLOADED_TO_BRAIN("upload_to_brain")
    }

    enum class UserProperty(val reportName: String) {
        USER_TYPE("user_type"),
        YEAR_OF_BIRTH("year_of_birth"),
        ROBOT_ID("robot_id")
    }

    enum class Screen(val screenName: String) {
        MAIN_MENU("Main menu"),
        USER_TYPE_SELECTION("User type selection"),
        HAVE_YOU_BUILT("Have you built"),
        MY_ROBOTS("My robots"),
        CREATE_NEW_ROBOT("Create new robot"),
        BUILD_INSTRUCTIONS("Build instructions"),
        CHALLENGE_DETAILS("Challenge details"),
        CHALLENGE_GROUP("Challenge group"),
        CHALLENGE_LIST("Challenge list"),
        CODING("Coding"),
        COMMUNITY("Community"),
        CONFIGURE_CONNECTIONS("Configure connections"),
        CONFIGURE_CONTROLLER("Configure controller"),
        PROGRAM_PRIORITY("Program priority"),
        BACKGROUND_PROGRAMS("Background programs"),
        PROGRAM_SELECTOR("Program selector"),
        SETTINGS("Settings"),
        ABOUT("About"),
        FIRMWARE_UPDATE("Firmware update"),
        PLAY("Play")
    }
}
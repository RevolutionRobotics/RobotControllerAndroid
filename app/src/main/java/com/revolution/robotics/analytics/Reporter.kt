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
        SKIPPED_ROBOT_REGISTRATION("skip_robot_registration"),
        UPLOAD_TO_BRAIN("upload_to_brain"),
        SELECT_YEAR_OF_BIRTH("select_year_of_birth"),
        BUILD_BASIC_ROBOT_OFFLINE("build_basic_robot_offline"),
        BUILD_BASIC_ROBOT_ONLINE("build_basic_robot_online"),
        SKIP_ONBOARDING("skip_onboarding"),
        DRIVE_BASIC_ROBOT("drive_basic _robot"),
        RESET_TUTORIAL("reset_tutorial"),
        UPDATE_FIRMWARE("update_firmware"),
        START_BASIC_ROBOT("start_basic_robot"),
        FINISH_BASIC_ROBOT("finish_basic_robot"),
        CREATE_CUSTOM_ROBOT("create_custom_robot"),
        ADD_MOTOR("add_motor"),
        ADD_SENSOR("add_sensor"),
        REMOVE_MOTOR("remove_motor"),
        REMOVE_SENSOR("remove_sensor"),
        CLICK_BACKGROUND_PROGRAMS("click_background_programs"),
        ADD_BACKGROUND_PROGRAM("add_background_program"),
        CLICK_PRIORITY_BUTTON("click_priority_button"),
        CHANGE_PRIORITY("change_priority"),
        OPEN_OVERFLOW_MENU("open_overflow_menu"),
        CHANGE_CONTROLLER_TYPE("change_controller_type"),
        DELETE_ROBOT("delete_robot"),
        DUPLICATE_ROBOT("duplicate_robot"),
        RENAME_ROBOT("rename_robot"),
        CHANGE_ROBOT_IMAGE("change_robot_image"),
        ASSIGN_PROGRAM_TO_BUTTON("assign_program_to_button"),
        CREATE_NEW_PROGRAM("create_new_program"),
        VIEW_GENERATED_CODE("view_generated_code"),
        OPEN_PROGRAM("open_program"),
        START_NEW_CHALLENGE("start_new_challenge"),
        CONTINUE_CHALLENGE("continue_challenge"),
        FINISH_CHALLENGE("finish_challenge"),
        OPEN_FORUM("open_forum"),
        OPEN_BT_CONNECT_DIALOG("open_bt_connect_dialog"),
        OPEN_BT_DEVICE_LIST("open_bt_device_list"),
        CONNECT_TO_BRAIN("connect_to_brain"),
        LEAVE_APP("leave_app")
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
package com.revolution.robotics.core.utils

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class AppPrefs constructor(context: Context) {

    companion object {
        const val PREFS_NAME = "robotics_prefs"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    open var yearOfBirthSelected by PreferenceFieldDelegate.Boolean(preferences, "year_of_birth_selected", false)
    open var onboardingRobotBuild by PreferenceFieldDelegate.Boolean(preferences, "carby_built", false)
    open var onboardingRobotDriven by PreferenceFieldDelegate.Boolean(preferences, "carby_driven", false)
    open var finishedOnboarding by PreferenceFieldDelegate.Boolean(preferences, "finished_onboarding", false)
    open var lastOpenedProgramName by PreferenceFieldDelegate.String(preferences, "last_opened_program_name")
    open var lastOpenedProgramRobotId by PreferenceFieldDelegate.Integer(preferences, "last_opened_program_robot_id")
    open var useAsiaApi by PreferenceFieldDelegate.Boolean(preferences, "use_asia_api", false)
}

sealed class PreferenceFieldDelegate<T>(
    protected val preferences: SharedPreferences,
    protected val key: kotlin.String,
    protected val defaultValue: T
) : ReadWriteProperty<Any, T> {

    class Boolean(preferences: SharedPreferences, key: kotlin.String, defaultValue: kotlin.Boolean = false) :
        PreferenceFieldDelegate<kotlin.Boolean>(preferences, key, defaultValue) {

        override fun getValue(thisRef: Any, property: KProperty<*>) = preferences.getBoolean(key, defaultValue)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: kotlin.Boolean) =
            preferences.edit().putBoolean(key, value).apply()
    }

    class Integer(preferences: SharedPreferences, key: kotlin.String, defaultValue: Int = 0) :
        PreferenceFieldDelegate<Int>(preferences, key, defaultValue) {

        override fun getValue(thisRef: Any, property: KProperty<*>) = preferences.getInt(key, defaultValue)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) =
            preferences.edit().putInt(key, value).apply()
    }

    class String(preferences: SharedPreferences, key: kotlin.String, defaultValue: kotlin.String = "") :
        PreferenceFieldDelegate<kotlin.String>(preferences, key, defaultValue) {

        override fun getValue(thisRef: Any, property: KProperty<*>) = preferences.getString(key, defaultValue)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: kotlin.String) =
            preferences.edit().putString(key, value).apply()
    }

    class Long(preferences: SharedPreferences, key: kotlin.String, defaultValue: kotlin.Long = 0L) :
        PreferenceFieldDelegate<kotlin.Long>(preferences, key, defaultValue) {

        override fun getValue(thisRef: Any, property: KProperty<*>) = preferences.getLong(key, defaultValue)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: kotlin.Long) =
            preferences.edit().putLong(key, value).apply()
    }
}

package com.revolution.robotics.core.utils

import android.content.Intent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

sealed class IntentExtraDelegate<T>(protected val key: kotlin.String, protected val defaultValue: T) :
    ReadWriteProperty<Intent?, T> {

    class Boolean(key: kotlin.String, defaultValue: kotlin.Boolean = false) : IntentExtraDelegate<kotlin.Boolean>(key, defaultValue) {

        override fun getValue(thisRef: Intent?, property: KProperty<*>) = thisRef?.getBooleanExtra(key, defaultValue) ?: defaultValue

        override fun setValue(thisRef: Intent?, property: KProperty<*>, value: kotlin.Boolean) {
            thisRef?.putExtra(key, value)
        }
    }

    class Int(key: kotlin.String, defaultValue: kotlin.Int = 0) : IntentExtraDelegate<kotlin.Int>(key, defaultValue) {

        override fun getValue(thisRef: Intent?, property: KProperty<*>) = thisRef?.getIntExtra(key, defaultValue) ?: defaultValue

        override fun setValue(thisRef: Intent?, property: KProperty<*>, value: kotlin.Int) {
            thisRef?.putExtra(key, value)
        }
    }

    class String(key: kotlin.String, defaultValue: kotlin.String = "") : IntentExtraDelegate<kotlin.String>(key, defaultValue) {

        override fun getValue(thisRef: Intent?, property: KProperty<*>) = thisRef?.getStringExtra(key) ?: defaultValue

        override fun setValue(thisRef: Intent?, property: KProperty<*>, value: kotlin.String) {
            thisRef?.putExtra(key, value)
        }
    }
}
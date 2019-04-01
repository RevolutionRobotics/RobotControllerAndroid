package com.revolution.robotics.core.utils

import android.os.Bundle
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

sealed class BundleArgumentDelegate<T>(protected val key: kotlin.String) : ReadWriteProperty<Bundle?, T> {

    class Boolean(key: kotlin.String) : BundleArgumentDelegate<kotlin.Boolean>(key) {

        override fun getValue(thisRef: Bundle?, property: KProperty<*>) = thisRef?.getBoolean(key, false) ?: false

        override fun setValue(thisRef: Bundle?, property: KProperty<*>, value: kotlin.Boolean) = thisRef?.putBoolean(key, value) ?: Unit
    }

    class Int(key: kotlin.String) : BundleArgumentDelegate<kotlin.Int>(key) {

        override fun getValue(thisRef: Bundle?, property: KProperty<*>) = thisRef?.getInt(key) ?: 0

        override fun setValue(thisRef: Bundle?, property: KProperty<*>, value: kotlin.Int) = thisRef?.putInt(key, value) ?: Unit
    }

    class String(key: kotlin.String) : BundleArgumentDelegate<kotlin.String>(key) {

        override fun getValue(thisRef: Bundle?, property: KProperty<*>) = thisRef?.getString(key, "") ?: ""

        override fun setValue(thisRef: Bundle?, property: KProperty<*>, value: kotlin.String) = thisRef?.putString(key, value) ?: Unit
    }

    class Float(key: kotlin.String) : BundleArgumentDelegate<kotlin.Float>(key) {

        override fun getValue(thisRef: Bundle?, property: KProperty<*>) = thisRef?.getFloat(key, 0f) ?: 0f

        override fun setValue(thisRef: Bundle?, property: KProperty<*>, value: kotlin.Float) = thisRef?.putFloat(key, value) ?: Unit
    }

    class StringNullable(key: kotlin.String) : BundleArgumentDelegate<kotlin.String?>(key) {

        override fun getValue(thisRef: Bundle?, property: KProperty<*>) = thisRef?.getString(key)

        override fun setValue(thisRef: Bundle?, property: KProperty<*>, value: kotlin.String?) = thisRef?.putString(key, value) ?: Unit
    }

    class LongNullable(key: kotlin.String) : BundleArgumentDelegate<kotlin.Long?>(key) {

        override fun getValue(thisRef: Bundle?, property: KProperty<*>): kotlin.Long? = if (thisRef?.containsKey(key) == true) thisRef.getLong(key) else null

        override fun setValue(thisRef: Bundle?, property: KProperty<*>, value: kotlin.Long?) = value?.let { thisRef?.putLong(key, value) } ?: Unit
    }

    class Long(key: kotlin.String) : BundleArgumentDelegate<kotlin.Long>(key) {

        override fun getValue(thisRef: Bundle?, property: KProperty<*>) = thisRef?.getLong(key, 0L) ?: 0L

        override fun setValue(thisRef: Bundle?, property: KProperty<*>, value: kotlin.Long) = thisRef?.putLong(key, value) ?: Unit
    }

    class Serialazble(key: kotlin.String) : BundleArgumentDelegate<Serializable?>(key) {

        override fun getValue(thisRef: Bundle?, property: KProperty<*>) = thisRef?.getSerializable(key)

        override fun setValue(thisRef: Bundle?, property: KProperty<*>, value: Serializable?) = thisRef?.putSerializable(key, value) ?: Unit
    }

    class StringArray(key: kotlin.String) : BundleArgumentDelegate<Array<kotlin.String>>(key) {

        override fun getValue(thisRef: Bundle?, property: KProperty<*>): Array<kotlin.String> = thisRef?.getStringArray(key) ?: arrayOf()

        override fun setValue(thisRef: Bundle?, property: KProperty<*>, value: Array<kotlin.String>) = thisRef?.putStringArray(key, value) ?: Unit
    }

    class Parcelable<out U : android.os.Parcelable>(key: kotlin.String) : BundleArgumentDelegate<android.os.Parcelable>(key) {
        override fun getValue(thisRef: Bundle?, property: KProperty<*>) = thisRef?.getParcelable(key) as U

        override fun setValue(thisRef: Bundle?, property: KProperty<*>, value: android.os.Parcelable) = thisRef?.putParcelable(key, value) ?: Unit
    }

    class ParcelableArrayList<U : android.os.Parcelable>(key: kotlin.String) : BundleArgumentDelegate<ArrayList<U>>(key) {

        override fun getValue(thisRef: Bundle?, property: KProperty<*>) = thisRef?.getParcelableArrayList<U>(key) ?: arrayListOf()

        override fun setValue(thisRef: Bundle?, property: KProperty<*>, value: ArrayList<U>) {
            thisRef?.putParcelableArrayList(key, value)
        }
    }
}
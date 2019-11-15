package com.revolution.robotics.core.extensions

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

fun ObservableBoolean.toggle() = set(!get())

inline fun ObservableInt.onPropertyChanged(crossinline callback: (Int) -> Unit) {
    addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) = callback(get())
    })
}

inline fun ObservableBoolean.onPropertyChanged(crossinline callback: (Boolean) -> Unit) {
    addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) = callback(get())
    })
}

inline fun <T> ObservableField<T>.onPropertyChanged(crossinline callback: (T?) -> Unit) {
    addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) = callback(get())
    })
}

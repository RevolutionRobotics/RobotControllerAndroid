package com.revolution.robotics.core.extensions

fun <T> MutableCollection<T>.swap(newCollection: List<T>) {
    clear()
    addAll(newCollection)
}

fun <T> List<T>?.isEmptyOrNull() = this == null || this.isEmpty()


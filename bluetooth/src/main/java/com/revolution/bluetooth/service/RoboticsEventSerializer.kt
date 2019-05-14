package com.revolution.bluetooth.service

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class RoboticsEventSerializer {

    companion object {
        private const val DEFAULT_DELAY = 100.toLong()
    }

    private val events = mutableListOf<() -> Boolean>()

    fun registerEvent(f: () -> Boolean) {
        events.add(f)
        if (events.size == 1) {
            startSerializationThread()
        }
    }

    fun clear() {
        events.clear()
    }

    private fun startSerializationThread() {
        runBlocking {
            delay(DEFAULT_DELAY)
            while (events.isNotEmpty()) {
                if (events.first().invoke()) {
                    events.removeAt(0)
                }
                delay(DEFAULT_DELAY)
            }
        }
    }
}

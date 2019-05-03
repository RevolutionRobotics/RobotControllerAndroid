package com.revolution.robotics.core.eventBus.dialog

import android.os.Bundle

class DialogEventBus {

    enum class Event {
        NEGATIVE, POSITIVE, OTHER;

        val extras = Bundle()
    }

    private val listeners = ArrayList<Listener>()

    fun register(listener: Listener) {
        listeners.add(listener)
    }

    fun unregister(listener: Listener) {
        listeners.remove(listener)
    }

    fun publish(dialog: DialogId, event: Event) {
        listeners.forEach { listener ->
            listener.onDialogEvent(dialog, event)
        }
    }

    interface Listener {
        fun onDialogEvent(dialog: DialogId, event: Event)
    }
}

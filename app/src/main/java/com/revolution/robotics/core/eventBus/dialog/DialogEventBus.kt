package com.revolution.robotics.core.eventBus.dialog

class DialogEventBus {

    private val listeners = ArrayList<Listener>()

    fun register(listener: Listener) {
        listeners.add(listener)
    }

    fun unregister(listener: Listener) {
        listeners.remove(listener)
    }

    fun publish(event: DialogEvent) {
        listeners.forEach { listener ->
            listener.onDialogEvent(event)
        }
    }

    interface Listener {
        fun onDialogEvent(event: DialogEvent)
    }
}

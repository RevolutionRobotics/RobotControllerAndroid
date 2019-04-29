package com.revolution.robotics.core.eventBus

import com.revolution.robotics.views.dialogs.RoboticsDialog

class DialogEventBus {

    enum class Event {
        NEGATIVE, POSITIVE, OTHER
    }

    private val listeners = ArrayList<Listener>()

    fun register(listener: Listener) {
        listeners.add(listener)
    }

    fun unregister(listener: Listener) {
        listeners.remove(listener)
    }

    fun publish(dialog: RoboticsDialog, event: Event) =
        publish(dialog.getFragmentTag(), event)

    fun publish(tag: String, event: Event) {
        listeners.forEach { listener ->
            listener.onDialogEvent(tag, event)
        }
    }

    interface Listener {
        fun onDialogEvent(tag: String, event: Event)
    }
}

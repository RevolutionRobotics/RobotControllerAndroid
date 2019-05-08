package com.revolution.robotics.features.configure

class ConfigurationEventBus {

    private val listeners = ArrayList<Listener>()

    fun register(listener: Listener) {
        listeners.add(listener)
    }

    fun unregister(listener: Listener) {
        listeners.remove(listener)
    }

    fun publishMotorUpdateEvent(event: MotorUpdateEvent) {
        listeners.forEach { listener ->
            listener.onMotorConfigChangedEvent(event)
        }
    }

    fun publishSensorUpdateEvent(event: SensorUpdateEvent) {
        listeners.forEach { listener ->
            listener.onSensorConfigChangedEvent(event)
        }
    }

    interface Listener {
        fun onMotorConfigChangedEvent(event: MotorUpdateEvent)
        fun onSensorConfigChangedEvent(event: SensorUpdateEvent)
    }
}

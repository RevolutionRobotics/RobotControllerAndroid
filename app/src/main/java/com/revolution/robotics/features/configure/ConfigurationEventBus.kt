package com.revolution.robotics.features.configure

class ConfigurationEventBus {

    private val listeners = ArrayList<Listener>()

    fun register(listener: Listener) {
        listeners.add(listener)
    }

    fun unregister(listener: Listener) {
        listeners.remove(listener)
    }

    fun publishMotorUpdateEvent(event: MotorPort) {
        listeners.forEach { listener ->
            listener.onMotorConfigChangedEvent(event)
        }
    }

    fun publishSensorUpdateEvent(event: SensorPort) {
        listeners.forEach { listener ->
            listener.onSensorConfigChangedEvent(event)
        }
    }

    fun publishOpenMotorConfiguration(event: MotorPort) {
        listeners.forEach { listener ->
            listener.onOpenMotorConfigEvent(event)
        }
    }

    fun publishOpenSensorConfiguration(event: SensorPort) {
        listeners.forEach { listener ->
            listener.onOpenSensorConfigEvent(event)
        }
    }

    interface Listener {
        fun onMotorConfigChangedEvent(event: MotorPort)
        fun onSensorConfigChangedEvent(event: SensorPort)
        fun onOpenMotorConfigEvent(event: MotorPort)
        fun onOpenSensorConfigEvent(event: SensorPort)
    }
}

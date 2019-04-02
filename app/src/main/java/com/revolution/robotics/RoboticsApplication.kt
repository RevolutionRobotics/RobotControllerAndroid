package com.revolution.robotics

import android.app.Application
import com.revolution.robotics.core.kodein.createAppModule
import com.revolution.robotics.core.kodein.createMainModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class RoboticsApplication : Application(), KodeinAware {

    override var kodein = Kodein {
        import(createMainModule())
        import(createAppModule(this@RoboticsApplication))
    }
}

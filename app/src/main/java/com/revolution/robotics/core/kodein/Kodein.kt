package com.revolution.robotics.core.kodein

import org.kodein.di.Kodein

val kodein = Kodein {
    import(appModule)
}

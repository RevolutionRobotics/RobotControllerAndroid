package com.revolution.robotics.features.configure

import android.content.Context
import android.util.AttributeSet
import androidx.drawerlayout.widget.DrawerLayout

abstract class ConfigurationDrawerLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    DrawerLayout(context, attrs) {

    init {
        setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
    }
}
package com.revolution.robotics.core

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel

interface Mvp {

    interface View

    interface Presenter<V : View, M : ViewModel?> {
        var view: V?
        var model: M?

        @CallSuper
        fun register(view: V, model: M?) {
            this.view = view
            this.model = model
        }

        @CallSuper
        fun unregister(view: V? = null) {
            if (view == null || view == this.view) {
                this.view = null
                this.model = null
            }
        }
    }
}
